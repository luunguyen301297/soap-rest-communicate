package com.example.rest_service.utils;

import org.springframework.util.ReflectionUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.lang.reflect.Field;

public class SoapUtils {
    private static final String SOAP_NAMESPACE = "\"http://schemas.xmlsoap.org/soap/envelope/\"";
    private static final String NAMESPACE_URI = "\"http://dfotiou.com/api/soap/geolocation\"";
    private static final String NAMESPACE_PREFIX = "geo";

    public static String toXml(Object request) {
        String classNameCamel = StringUtils.parsePascalToCamel(request.getClass().getSimpleName());

        StringBuilder xml = new StringBuilder();

        xml.append("<SOAP-ENV:Envelope xmlns:SOAP-ENV=").append(SOAP_NAMESPACE).append(" ");
        xml.append("xmlns:").append(NAMESPACE_PREFIX).append("=").append(NAMESPACE_URI).append(">");
        xml.append("<SOAP-ENV:Header/>");
        xml.append("<SOAP-ENV:Body>");
        xml.append("<").append(NAMESPACE_PREFIX).append(":").append(classNameCamel).append(">");

        for (Field field : request.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                xml.append("<").append(NAMESPACE_PREFIX).append(":").append(field.getName()).append(">")
                        .append(field.get(request))
                        .append("</").append(NAMESPACE_PREFIX).append(":").append(field.getName()).append(">");
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Error accessing field value", e);
            }
        }

        xml.append("</").append(NAMESPACE_PREFIX).append(":").append(classNameCamel).append(">");
        xml.append("</SOAP-ENV:Body>");
        xml.append("</SOAP-ENV:Envelope>");

        return xml.toString();
    }

    public static void fromXml(String xml, Class<?> responseClass, Object responseInstance) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(xml)));

            for (Field field : responseClass.getDeclaredFields()) {
                ReflectionUtils.makeAccessible(field);
                String fieldName = field.getName();
                NodeList nodeList = doc.getElementsByTagNameNS("*", fieldName);
                if (nodeList.getLength() > 0) {
                    String value = nodeList.item(0).getTextContent();
                    Object convertedValue = convertValue(value, field.getType());
                    field.set(responseInstance, convertedValue);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error parsing SOAP response", e);
        }
    }

    private static Object convertValue(String value, Class<?> targetType) {
        if (value == null || value.isEmpty()) return null;

        return switch (targetType.getSimpleName()) {
            case "String" -> value;
            case "Integer" -> Integer.valueOf(value);
            case "Boolean" -> Boolean.valueOf(value);
            case "Double" -> Double.valueOf(value);
            case "Long" -> Long.valueOf(value);
            default ->
                    throw new UnsupportedOperationException("Type " + targetType + " is not supported for conversion");
        };
    }

}
