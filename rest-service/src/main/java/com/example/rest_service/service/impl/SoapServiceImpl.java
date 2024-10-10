package com.example.rest_service.service.impl;

import com.example.rest_service.model.SoapRequest;
import com.example.rest_service.model.SoapResponse;
import com.example.rest_service.service.SoapService;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public abstract class SoapServiceImpl<Request extends SoapRequest, Response extends SoapResponse>
        implements SoapService<Request, Response> {

    private final RestTemplate restTemplate;

    private static final String SOAP_SERVICE_URL = "http://localhost:8080/ws";

    @Override
    public Response callSoapWebService(Request request) {
        try {
            String soapRequest = request.toXml();
            String soapResponse = callSoapWebServiceInternal(soapRequest);
            Response response = createResponseInstance();
            response.fromXml(soapResponse);
            return response;
        } catch (Exception e) {
            throw new RuntimeException("Error calling SOAP service", e);
        }
    }

    private String callSoapWebServiceInternal(String xmlRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_XML);

        HttpEntity<String> entity = new HttpEntity<>(xmlRequest, headers);

        ResponseEntity<String> response = restTemplate.exchange(SoapServiceImpl.SOAP_SERVICE_URL, HttpMethod.POST, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to call SOAP service");
        }
    }

    protected abstract Response createResponseInstance();

}
