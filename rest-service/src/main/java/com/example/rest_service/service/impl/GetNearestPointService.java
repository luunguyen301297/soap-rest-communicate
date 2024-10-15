package com.example.rest_service.service.impl;

import com.example.rest_service.model.GetNearestPointRequest;
import com.example.rest_service.model.GetNearestPointResponse;
import com.example.rest_service.service.SoapService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GetNearestPointService extends SoapService<GetNearestPointRequest, GetNearestPointResponse> {

    public GetNearestPointService(RestTemplate restTemplate) {
        super(restTemplate);
    }

    @Override
    protected GetNearestPointResponse createResponseInstance() {
        return new GetNearestPointResponse();
    }

}
