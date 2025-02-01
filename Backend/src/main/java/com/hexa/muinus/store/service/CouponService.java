package com.hexa.muinus.coupon.service;

import com.google.zxing.WriterException;
import com.hexa.muinus.common.util.QRCodeGenerator;
import com.hexa.muinus.coupon.dto.*;
import com.hexa.muinus.coupon.entity.*;
import com.hexa.muinus.coupon.repository.CouponHistoryRepository;
import com.hexa.muinus.coupon.repository.CouponRepository;
import com.hexa.muinus.coupon.repository.UserCouponHistoryRepository;
import com.hexa.muinus.store.domain.coupon.Coupon;
import com.hexa.muinus.store.domain.coupon.CouponHistory;
import com.hexa.muinus.store.domain.coupon.CouponHistoryId;
import com.hexa.muinus.store.domain.store.repository.StoreRepository;
import com.hexa.muinus.users.domain.user.Users;
import com.hexa.muinus.users.domain.user.repository.UsersRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CouponService {

    private final CouponRepository couponRepository;
    private final CouponHistoryRepository couponHistoryRepository;
    private final UserCouponHistoryRepository userCouponHistoryRepository;
    private final StoreRepository storeRepository;
    private final UsersRepository usersRepository;


    @Transactional
    public void createCoupon(CouponRequestDto couponRequestDto) {
        // 가게 이름을 통해 가게 번호 조회
        Integer storeNo = storeRepository.findStoreNoByName(couponRequestDto.getStoreName())
                .orElseThrow(() -> new IllegalArgumentException("해당 가게가 존재하지 않습니다. storeName: "+couponRequestDto.getStoreName()));

        // 쿠폰이 존재하는지 확인
        Coupon coupon = couponRepository.findById(couponRequestDto.getCouponId())
                .orElseThrow(() -> new IllegalArgumentException("해당 쿠폰이 존재하지 않습니다. couponId: "+couponRequestDto.getCouponId()));

        // 복합 키 생성
        CouponHistoryId couponHistoryId = new CouponHistoryId(storeNo, couponRequestDto.getCouponId());

        // 쿠폰 발급 내역 생성
        CouponHistory couponHistory = new CouponHistory(couponHistoryId, couponRequestDto.getCount(), couponRequestDto.getExpirationDate(), LocalDateTime.now());
        couponHistoryRepository.save(couponHistory);
    }

    @Transactional
    public void receiveCoupon(ReceiveCouponRequestDto receiveCouponRequestDto) {
        // 가게 존재 여부 확인
        boolean storeExists = storeRepository.existsById(receiveCouponRequestDto.getStoreNo());
        if(!storeExists) {
            throw new IllegalArgumentException("해당 가게가 존재하지 않습니다. storeNo: "+receiveCouponRequestDto.getStoreNo());
        }

        // 쿠폰 존재 여부 확인
        Coupon coupon = couponRepository.findById((receiveCouponRequestDto.getCouponId()))
                .orElseThrow(() -> new IllegalArgumentException("해당 쿠폰이 존재하지 않습니다. couponId: "+receiveCouponRequestDto.getCouponId()));

        // 사용자 존재 여부 확인
        Users user = usersRepository.findById(receiveCouponRequestDto.getUserNo())
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다. userNo: "+receiveCouponRequestDto.getUserNo()));

        // 복합 키 생성
        UserCouponHistoryId userCouponHistoryId = new UserCouponHistoryId(receiveCouponRequestDto.getStoreNo(), receiveCouponRequestDto.getCouponId(), receiveCouponRequestDto.getUserNo());

        // 중복 수령 방지
        if(userCouponHistoryRepository.existsById(userCouponHistoryId)){
            throw new IllegalArgumentException("이미 해당 쿠폰을 수령하였습니다.");
        }

        // CouponHistoryId 생성
        CouponHistoryId couponHistoryId = new CouponHistoryId(
                receiveCouponRequestDto.getStoreNo(),
                receiveCouponRequestDto.getCouponId()
        );

        // 쿠폰 히스토리 생성
        CouponHistory couponHistory = couponHistoryRepository.findById(couponHistoryId)
                .orElseThrow(() -> new IllegalArgumentException("쿠폰 히스토리가 존재하지 않습니다."));

        // 발급 가능 수량 감소
        if(couponHistory.getCount() <= 0){
            throw new IllegalStateException("더 이상 발급 가능한 쿠폰이 없습니다.");
        }
        couponHistory.setCount(couponHistory.getCount() - 1);
        couponHistoryRepository.save(couponHistory);

        // 새로운 쿠폰 수령
        UserCouponHistory userCouponHistory = new UserCouponHistory(
                userCouponHistoryId,
                couponHistory,
                user,
                LocalDateTime.now(),
                null
        );
        userCouponHistoryRepository.save(userCouponHistory);
    }

    @Transactional
    public List<ReceiveCouponResponseDto> getUserCoupons(Integer userNo){
        // UserCouponHistory 조회
        List<UserCouponHistory> userCouponHistories = userCouponHistoryRepository.findByUser_userNo(userNo);

        // 변환(ReceiveCouponResponseDto)
        return userCouponHistories.stream()
                .map(history -> {
                    CouponHistory couponHistory = history.getCouponHistory();
                    Coupon coupon = couponHistory.getCoupon();
                    return new ReceiveCouponResponseDto(
                            couponHistory.getStore().getName(),
                            coupon.getName(),
                            coupon.getContent(),
                            coupon.getDiscountRate(),
                            couponHistory.getExpirationDate(),
                            history.getUsedAt()
                    );
                }).toList();

    }

    @Transactional
    public UseCouponResponseDto useCoupon(UseCouponRequestDto useCouponRequestDto) {

        // 사용 가능한 쿠폰 조회
        UserCouponHistory userCouponHistory = userCouponHistoryRepository.findById_UserNoAndId_CouponIdAndUsedAtIsNull(
                useCouponRequestDto.getUserNo(),
                useCouponRequestDto.getCouponId()
        ).orElseThrow(() -> new IllegalArgumentException("사용 가능한 쿠폰이 존재하지 않습니다."));

        // 쿠폰 히스토리 ID 생성
        CouponHistoryId couponHistoryId = new CouponHistoryId(
                useCouponRequestDto.getStoreNo(),
                useCouponRequestDto.getCouponId()
        );

        // 쿠폰 히스토리 조회
        log.info("coupon history start");
        CouponHistory couponHistory = couponHistoryRepository.findById(couponHistoryId)
                .orElseThrow(() -> new IllegalArgumentException("쿠폰 히스토리가 존재하지 않습니다."));
        log.info("coupon history end");
        // 쿠폰 유효 기간 확인
        if(couponHistory.getExpirationDate().isBefore(LocalDateTime.now())){
            throw new IllegalArgumentException("쿠폰의 유효 기간이 만료되었습니다.");
        }

        // QR 코드 생성
        String qrCode;
        try {
            String qrCodeData = generateQRCodeData(useCouponRequestDto);
            qrCode = QRCodeGenerator.generateQRCodeImage(qrCodeData, 200, 200);//QR 코드 크기
        } catch (WriterException | IOException e){
            throw new RuntimeException("QR 코드 생성에 실패했습니다.");
        }

        // 쿠폰 사용 처리(사용 시간 기록 및 상태 업데이트)
        userCouponHistory.setUsedAt(LocalDateTime.now());
        userCouponHistoryRepository.save(userCouponHistory);

        return new UseCouponResponseDto(qrCode);
    }

    /**
     * QR 코드에 포함될 데이터를 생성하는 메서드.
     *
     * @param useCouponRequestDto 쿠폰 사용 요청 DTO
     * @return QR 코드 데이터 문자열
     */
    private String generateQRCodeData(UseCouponRequestDto useCouponRequestDto){
        return String.format("coupon_id:%d,store_no:%d,user_no:%d",
                useCouponRequestDto.getCouponId(),
                useCouponRequestDto.getStoreNo(),
                useCouponRequestDto.getUserNo()
        );
    }

//    @Transactional
//    public ApplyDiscountResponseDto applyDiscount(ApplyDisCountRequestDto applyDisCountRequestDto){
//        String qrCodeData = applyDisCountRequestDto.getQrCodeData();
//
//        // QR 코드 데이터 파싱
//        String[] dataParts = qrCodeData.split(",");
//        Integer couponId = null;
//        Integer storeNo = null;
//        Integer userNo = null;
//
////        try {
////            for (String part : dataParts) {
////                String[] keyValue = part.split
////            }
////        }
//    }

}
