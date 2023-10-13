package com.mtattab.reverseshell.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;

@UtilityClass
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
}
