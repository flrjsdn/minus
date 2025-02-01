package com.hexa.muinus.store.service;

import com.hexa.muinus.common.jwt.JwtProvider;
import com.hexa.muinus.store.domain.item.FliItem;
import com.hexa.muinus.store.domain.item.Item;
import com.hexa.muinus.store.domain.item.StoreItem;
import com.hexa.muinus.store.domain.item.repository.FliItemRepository;
import com.hexa.muinus.store.domain.item.repository.ItemRepository;
import com.hexa.muinus.store.domain.item.repository.StoreItemRepository;
import com.hexa.muinus.store.domain.store.Store;
import com.hexa.muinus.store.domain.store.repository.StoreRepository;
import com.hexa.muinus.store.domain.transaction.GuestTransactionDetails;
import com.hexa.muinus.store.domain.transaction.GuestTransactions;
import com.hexa.muinus.store.domain.transaction.TransactionDetails;
import com.hexa.muinus.store.domain.transaction.Transactions;
import com.hexa.muinus.store.domain.transaction.repository.GuestTransactionDetailsRepository;
import com.hexa.muinus.store.domain.transaction.repository.GuestTransactionsRepository;
import com.hexa.muinus.store.domain.transaction.repository.TransactionDetailsRepository;
import com.hexa.muinus.store.domain.transaction.repository.TransactionsRepository;
import com.hexa.muinus.store.dto.kiosk.PaymentRequestDTO;
import com.hexa.muinus.store.dto.kiosk.PaymentResponseDTO;
import com.hexa.muinus.store.dto.kiosk.PutFliItemResponseDTO;
import com.hexa.muinus.store.dto.kiosk.ScanBarcodeResponseDTO;
import com.hexa.muinus.users.domain.user.GuestUser;
import com.hexa.muinus.users.domain.user.Users;
import com.hexa.muinus.users.domain.user.repository.GuestUserRepository;
import com.hexa.muinus.users.domain.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KioskService {

    private final StoreItemRepository storeItemRepository;
    private final ItemRepository itemRepository;
    private final FliItemRepository fliItemRepository;
    private final JwtProvider jwtProvider;
    private final GuestUserRepository guestUserRepository;
    private final GuestTransactionsRepository guestTransactionsRepository;
    private final StoreRepository storeRepository;
    private final GuestTransactionDetailsRepository guestTransactionDetailsRepository;
    private final UserRepository userRepository;
    private final TransactionsRepository transactionsRepository;
    private final TransactionDetailsRepository transactionDetailsRepository;

    @Transactional(readOnly = true)
    public ScanBarcodeResponseDTO scanBarcode(Integer storeNo, String barcode) {
        // 바코드 번호로 item 테이블에서 상품 번호 및 이름 조회
        Item item = itemRepository.findItemByBarcode(barcode)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 바코드 번호입니다."));

        // storeNo, itemNo로 store_item 테이블에서 상품 가격 조회
        Integer price = storeItemRepository.getPriceByStoreNoAndItemNo(storeNo, item.getItemId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "판매하지 않는 상품입니다."));

        return ScanBarcodeResponseDTO.builder()
                .itemName(item.getItemName())
                .price(price)
                .build();
    }

    @Transactional(readOnly = true)
    public PutFliItemResponseDTO putFliItem(Integer storeNo, Integer sectionId) {
        Store store = storeRepository.findById(storeNo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 매장입니다."));
        FliItem fliItem = fliItemRepository.findByStoreAndSectionId(store, sectionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 상품입니다."));

        return PutFliItemResponseDTO.builder()
                .itemName(fliItem.getFliItemName())
                .price(fliItem.getPrice())
                .build();
    }

    @Transactional
    public PaymentResponseDTO payForItems(PaymentRequestDTO requestDTO, HttpServletRequest request) {
        String receiptCode = UUID.randomUUID().toString();
        Integer storeNo = requestDTO.getStoreNo();
        Integer totalAmount = requestDTO.getTotalAmount();
        String userEmail = jwtProvider.getUserEmailFromAccessToken(request);

        Store store = storeRepository.findById(storeNo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 매장입니다."));

        StoreItem storeItem = storeItemRepository.findByStore(store)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 상품입니다."));

        // transactions 테이블에 receipt_code, store_no, user_no, totalAmount, status 저장
        // transaction_detail 테이블 -> transaction_id, store_item_id, unit_price, quantity, sub_total
        if (userEmail != null) {
            Users user = userRepository.findByEmail(userEmail);
            Transactions transactions = Transactions.builder()
                    .receiptCode(receiptCode)
                    .store(store)
                    .user(user)
                    .totalAmount(totalAmount)
                    .status(GuestTransactions.Status.SUCCESS)
                    .build();
            transactionsRepository.save(transactions);

            for (int i=0;i<requestDTO.getItemsForPayment().size();i++) {
                TransactionDetails transactionDetails = TransactionDetails.builder()
                        .transaction(transactions)
                        .storeItem(storeItem)
                        .unitPrice(requestDTO.getItemsForPayment().get(i).getPrice())
                        .quantity(requestDTO.getItemsForPayment().get(i).getQuantity())
                        .subTotal(requestDTO.getItemsForPayment().get(i).getSubtotal())
                        .build();

                transactionDetailsRepository.save(transactionDetails);
            }

            for (int i=0;i<requestDTO.getFliItemsForPayment().size();i++) {
                TransactionDetails transactionDetails = TransactionDetails.builder()
                        .transaction(transactions)
                        .storeItem(storeItem)
                        .unitPrice(requestDTO.getFliItemsForPayment().get(i).getPrice())
                        .quantity(requestDTO.getFliItemsForPayment().get(i).getQuantity())
                        .subTotal(requestDTO.getFliItemsForPayment().get(i).getSubtotal())
                        .build();

                transactionDetailsRepository.save(transactionDetails);
            }
        }


        // 비회원 결제
        // guest 테이블에 guest_name 저장
        // guest_transactions 테이블 -> receipt_code, store_no, guest_no, total_amount, status
        // guest_transaction_detail 테이블 -> transaction_id, store_item_id, unit_price, quantity, sub_total
        else if (userEmail == null) {
            GuestUser guestUser = GuestUser.builder()
                    .guestName(UUID.randomUUID().toString())
                    .build();
            guestUserRepository.save(guestUser);

            GuestTransactions guestTransactions = GuestTransactions.builder()
                    .receiptCode(receiptCode)
                    .store(store)
                    .guest(guestUser)
                    .totalAmount(totalAmount)
                    .status(GuestTransactions.Status.SUCCESS)
                    .build();
            guestTransactionsRepository.save(guestTransactions);

            for (int i=0;i<requestDTO.getItemsForPayment().size();i++) {
                GuestTransactionDetails guestTransactionDetails = GuestTransactionDetails.builder()
                        .transactions(guestTransactions)
                        .storeItem(storeItem)
                        .unitPrice(requestDTO.getItemsForPayment().get(i).getPrice())
                        .quantity(requestDTO.getItemsForPayment().get(i).getQuantity())
                        .subTotal(requestDTO.getItemsForPayment().get(i).getSubtotal())
                        .build();
                guestTransactionDetailsRepository.save(guestTransactionDetails);
            }

            for (int i=0;i<requestDTO.getFliItemsForPayment().size();i++) {
                GuestTransactionDetails guestTransactionDetails = GuestTransactionDetails.builder()
                        .transactions(guestTransactions)
                        .storeItem(storeItem)
                        .unitPrice(requestDTO.getFliItemsForPayment().get(i).getPrice())
                        .quantity(requestDTO.getFliItemsForPayment().get(i).getQuantity())
                        .subTotal(requestDTO.getFliItemsForPayment().get(i).getSubtotal())
                        .build();
                guestTransactionDetailsRepository.save(guestTransactionDetails);
            }
        }

        return PaymentResponseDTO.builder()
                .receiptCode(receiptCode)
                .build();
    }

}
