package com.example.rest_service.model;

import com.example.rest_service.utils.SoapUtils;

public interface SoapResponse {

    default void fromXml(String xml) {
        Class<?> responseClass = this.getClass();
        SoapUtils.fromXml(xml, responseClass, this);
    }

}
