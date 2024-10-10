package com.example.rest_service.service;

public interface SoapService<Request, Response> {
    Response callSoapWebService(Request request) throws Exception;
}
