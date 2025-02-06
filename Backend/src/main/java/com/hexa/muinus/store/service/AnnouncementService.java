package com.hexa.muinus.store.service;

import com.hexa.muinus.common.exception.api.store.BoardForbiddenException;
import com.hexa.muinus.store.domain.information.Announcement;
import com.hexa.muinus.store.domain.information.respository.AnnouncementRepository;
import com.hexa.muinus.store.dto.information.AnnouncementDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;

    /**
     * 공지사항 조회(ALL)
     * @param storeNo 매장 번호
     * @return List<AnnouncementDTO> 매장 공지사항 전체
     */
    public List<AnnouncementDTO> getAllAnnouncementsByStoreNo(int storeNo) {
       return announcementRepository.findAllAnnouncementsByStoreNo(storeNo);
    }

    /**
     * insert Announcement
     * @param announcement Announcement 객체
     * @return boardId가 추가된 Announcement 객체
     */
    public Announcement saveAnnouncement(Announcement announcement){
        Announcement savedAnnouncement = announcementRepository.save(announcement);
        log.info("Announcement saved successfully with ID: {}", savedAnnouncement.getBoardId());
        return savedAnnouncement;
    }

    /**
     * 사용자, 게시글 번호로 공지사항 찾기
     * @param userEmail 로그인한 사용자
     * @param boardId 작성 게시글 번호
     * @return Announcement
     */
    @Transactional(readOnly = true)
    public Announcement findAnnouncementByUserEmailAndBoardId(String userEmail, int boardId) {
        Announcement announcement =  announcementRepository.findByStore_User_EmailAndBoardId(userEmail, boardId);
        // 게시글 유효성 검사
        if(announcement == null){
            throw new BoardForbiddenException(userEmail, boardId);
        }
        return announcement;
    }

    /**
     * 공지 삭제
     * @param announcement 삭제할 공지 사항
     */
    @Transactional
    public void removeAnnouncement(Announcement announcement) {
        announcementRepository.delete(announcement);
        log.info("Announcement {} has been deleted successfully", announcement);
    }
}
