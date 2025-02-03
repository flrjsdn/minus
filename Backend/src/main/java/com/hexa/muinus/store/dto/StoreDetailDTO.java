package com.hexa.muinus.store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreDetailDTO {
    private StoreDTO store;
    private List<AnnouncementDTO> announcements;
    private List<StoreItemDTO> storeItems;
    private List<FliItemDTO> fliItems;
}
