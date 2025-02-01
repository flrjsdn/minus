package com.hexa.muinus.store.controller;

import com.hexa.muinus.store.service.KioskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/kiosk")
public class KioskController {

    private final KioskService kioskService;

    @GetMapping("/scan")
    public ResponseEntity<?> scanBarcode(@RequestParam(name = "storeNo") Integer storeNo, @RequestParam(name = "barcode") String barcode) {
        return ResponseEntity.ok(kioskService.scanBarcode(storeNo, barcode));
    }

    @GetMapping("/flea-item")
    public ResponseEntity<?> putFliItem(@RequestParam(name = "storeNo") Integer storeNo, @RequestParam(name = "sectionId") Integer sectionId) {
        return ResponseEntity.ok(kioskService.putFliItem(storeNo, sectionId));
    }
}
