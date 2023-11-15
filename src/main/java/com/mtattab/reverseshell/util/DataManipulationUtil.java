package com.mtattab.reverseshell.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import lombok.extern.java.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
@Log
public class DataManipulationUtil {

    public static <T> String convertObjToJson(T obj){
        ObjectMapper objectMapper = new ObjectMapper();

        try {

            return objectMapper.writeValueAsString(obj) ;

        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Exception occurred with convertObjToJson");
        }
    }

    public static <T> T jsonToObject(String jsonString, Class<T> clazz) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(jsonString, clazz);
        } catch (Exception e) {
//            e.printStackTrace();
            return null;
        }
    }

    public static List<String> stringToList(String inputString, String delimiter) {
        try {
            // Split the input string by space and convert it to a list
            return Arrays.asList(inputString.split(delimiter));

        }catch (Exception e){
            return List.of(inputString);
        }
    }

    public static String getFirstNCharacters(String input, int length) {
        if (input == null) {
            return null;
        } else if (input.length() <= length) {
            return input;
        }
        return input.substring(0, Math.min(input.length(), length));
    }

    public static ArrayList<String> splitString(String input, int stringLength) {
        ArrayList<String> result = new ArrayList<>();
        if (input == null) {
            return result;
        }
        int length = input.length();
        int startIndex = 0;
        while (startIndex < length) {
            int endIndex = Math.min(startIndex + stringLength, length);
            result.add(input.substring(startIndex, endIndex));
            startIndex = endIndex;
        }
        return result;
    }

    public static String removeFirstCharacter(String input) {
        // Check if the string is not empty and has more than one character
        if (input != null && input.length() > 1) {
            return input.substring(1);
        } else {
            // Return the original string if it is empty or has only one character
            return input;
        }
    }
    public static String removeFirstLines(String input, int linesToRemove) {
        if (input == null || input.isEmpty()) {
            // Return the original string if it is null or empty
            return input;
        }

        // Split the string into lines
        String[] lines = input.split("\\r?\\n");

        // Check if there are at least 4 lines
        if (lines.length > linesToRemove) {
            // Join the remaining lines starting from the 5th line
            return String.join(System.lineSeparator(), Arrays.copyOfRange(lines, linesToRemove, lines.length));
        } else {
            // Return an empty string if there are fewer than 4 lines
            return "";
        }
    }
    public static String extractByRegex(String content, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);

        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return null; // Return null if no match is found
        }
    }


}
