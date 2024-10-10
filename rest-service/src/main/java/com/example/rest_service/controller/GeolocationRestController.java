package com.example.rest_service.controller;

import com.example.rest_service.model.GetNearestPointRequest;
import com.example.rest_service.model.GetNearestPointResponse;
import com.example.rest_service.service.impl.GetNearestPointService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/points")
public class GeolocationRestController {

    private final GetNearestPointService getNearestPointService;

    @PostMapping("/nearest")
    public ResponseEntity<GetNearestPointResponse> getNearestPoint(@RequestBody GetNearestPointRequest request) {
        GetNearestPointResponse response = getNearestPointService.callSoapWebService(request);
        return ResponseEntity.ok(response);
    }

}
