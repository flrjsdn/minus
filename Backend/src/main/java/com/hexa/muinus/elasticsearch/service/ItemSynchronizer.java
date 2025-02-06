package com.hexa.muinus.elasticsearch.service;

import com.hexa.muinus.common.exception.ESErrorCode;
import com.hexa.muinus.common.exception.MuinusException;
import com.hexa.muinus.elasticsearch.domain.ESItem;
import com.hexa.muinus.store.domain.item.Item;
import com.hexa.muinus.store.domain.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemSynchronizer {

    private final ItemRepository itemRepository;
    private final ESItemService esItemService;

    /**
     * 매일 한시
     * MySQL ITEM, ES ITEM 자동 동기화
     */
    @Scheduled(cron = "30 19 6 * * ?")
    public void scheduledSync() {
        try {
            log.info("MySQL Item -> ES Item 동기화 시작 :{}", LocalDateTime.now());
            synchronizeAll();
            log.info("MySQL Item -> ES Item 동기화 완료 :{}", LocalDateTime.now());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("MySQL Item -> ES Item 동기화 실패: " + e.getMessage());
        }
    }

    public void synchronizeAll() {
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        log.debug("yesterday: {}", yesterday);

        try {
            List<Item> items = itemRepository.findItemByUpdatedAtAfter(yesterday);

            log.debug("items size: {}", items.size());
            log.debug("items: {}", items);

            // 변경 사항 없으면 종료
            if (items.isEmpty()) {
                return;
            }

            log.debug("Converting items into ES Item");
            List<ESItem> esItems = items.stream()
                    .map(this::convertToESItem)
                    .toList();
            log.debug("esItems size: {}", esItems.size());
            log.debug("esItems: {}", esItems);

            esItemService.saveItemsInBulk(esItems);

        } catch (DataAccessResourceFailureException e){
            throw new MuinusException(ESErrorCode.ES_CONNECTION_ERROR);
        } catch (Exception e) {
            throw new MuinusException(ESErrorCode.ES_SYNC_FAILED);
        }
    }

    private ESItem convertToESItem(Item item) {
        ESItem esItem = new ESItem();
        esItem.setItemId(item.getItemId());
        esItem.setBarcode(item.getBarcode());
        esItem.setItemName(item.getItemName());
        esItem.setBrand(item.getBrand());
        esItem.setCalories(item.getCalories());
        esItem.setProtein(item.getProtein());
        esItem.setFat(item.getFat().floatValue());
        esItem.setCarbohydrate(item.getCarbohydrate());
        esItem.setSugars(item.getSugars());
        esItem.setWeight(item.getWeight());
        esItem.setItemImageUrl(item.getItemImageUrl());
        esItem.setUpdatedAt(item.getUpdatedAt().toLocalDate());
        return esItem;
    }
}
