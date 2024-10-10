package com.example.rest_service.model;

import lombok.Data;

@Data
public class GetNearestPointRequest implements SoapRequest {

    private double latitude;
    private double longitude;

}
