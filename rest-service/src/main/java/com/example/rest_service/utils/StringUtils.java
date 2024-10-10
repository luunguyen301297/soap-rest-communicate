package com.example.rest_service.utils;

public class StringUtils {

    public static String parsePascalToCamel(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        String[] parts = input.split("(?=[A-Z])");
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            if (i == 0) {
                result.append(parts[i].toLowerCase());
            } else {
                result.append(parts[i]);
            }
        }

        return result.toString();
    }

}
