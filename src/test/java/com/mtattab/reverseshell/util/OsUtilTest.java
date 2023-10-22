package com.mtattab.reverseshell.util;


import org.junit.Test;

public class OsUtilTest {


    @Test
    public void getComputerInfoTest() {

        System.out.println(OSUtil.getComputerInfo());

        System.out.println(System.getProperty("user.region"));
    }
}
