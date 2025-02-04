package com.hexa.muinus.store.domain.information.respository;

import com.hexa.muinus.store.domain.information.Announcement;
import com.hexa.muinus.store.dto.AnnouncementDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnnouncementRepository extends JpaRepository<Announcement, Integer> {

    @Query("""
    SELECT new com.hexa.muinus.store.dto.AnnouncementDTO(
        a.boardId, a.title, a.content, a.boardImageUrl, a.createdAt, a.updatedAt)
    FROM Announcement a WHERE a.store.storeNo = :storeNo
""")
    List<AnnouncementDTO> findAnnouncementsByStore(@Param("storeNo") int storeNo);


}
