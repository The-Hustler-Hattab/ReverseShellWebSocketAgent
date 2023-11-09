package com.mtattab.reverseshell.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mtattab.reverseshell.model.CommandRestOutput;
import com.mtattab.reverseshell.model.ManagerCommunicationModel;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;

public class DataManipulationTest {

    @Test
    public void executeCommandTest() throws JsonProcessingException {
        String jsonObj ="{\"masterSessionId\":\" dd2dcad1 - 4d c4 - 6 aaf - 29 c4 - 0f 46 a5cab04d \",\"msg\":\"whoami\",\"success\":false}";

        ManagerCommunicationModel managerCommunicationModel= DataManipulationUtil.jsonToObject(jsonObj, ManagerCommunicationModel.class);

        System.out.println(managerCommunicationModel);
    }

    @Test
    public void getFirstNCharactersTest()  {


        String word = DataManipulationUtil.getFirstNCharacters("hello", 6);

        System.out.println(word);
    }


    @Test
    public void splitStringTest()  {


        ArrayList<String> words = DataManipulationUtil.splitString("hello", 2);

        System.out.println(words);

         words = DataManipulationUtil.splitString("hello", 10);

        System.out.println(words);

    }
}
