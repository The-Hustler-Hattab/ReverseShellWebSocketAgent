package com.mtattab.reverseshell.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import lombok.extern.java.Log;

import java.util.Arrays;
import java.util.List;

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

    public static List<String> stringToList(String inputString, String delimiter) {
        try {
            // Split the input string by space and convert it to a list
            return Arrays.asList(inputString.split(delimiter));

        }catch (Exception e){
            return List.of(inputString);
        }
    }
}
