package com.hexa.muinus.store.controller;

import com.hexa.muinus.store.domain.item.RequestReceiving;
import com.hexa.muinus.store.dto.item.ItemRequestDTO;
import com.hexa.muinus.store.dto.item.ItemResponseDTO;
import com.hexa.muinus.store.service.RequestReceivingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/item")
@RequiredArgsConstructor
public class ItemController {

    private final RequestReceivingService requestReceivingService;

    @PostMapping("/register")
    public ResponseEntity<?> createRequestReceiving(@RequestBody ItemRequestDTO dto) {
        RequestReceiving savedRequest = requestReceivingService.createRequestReceiving(dto.getUserId(), dto.getStoreId(), dto.getItemId());
        return ResponseEntity.ok("register is successful");
    }

    @GetMapping("/regist_list")
    public ResponseEntity<List<ItemResponseDTO>> getRegistList(@RequestParam("email") String email) {
        List<ItemResponseDTO> result = requestReceivingService.getItemRequestCounts(email);
        return ResponseEntity.ok(result);
    }
}
