package com.hexa.muinus.store.controller;

import com.hexa.muinus.store.dto.kiosk.PaymentRequestDTO;
import com.hexa.muinus.store.service.KioskService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/payment")
    public ResponseEntity<?> payForItems(@RequestBody PaymentRequestDTO requestDTO, HttpServletRequest request) {
        return ResponseEntity.ok(kioskService.payForItems(requestDTO, request));
    }
}
