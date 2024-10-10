package com.example.rest_service.model;

import com.example.rest_service.utils.SoapUtils;

public interface SoapRequest {

    default String toXml() {
        return SoapUtils.toXml(this);
    }

}
