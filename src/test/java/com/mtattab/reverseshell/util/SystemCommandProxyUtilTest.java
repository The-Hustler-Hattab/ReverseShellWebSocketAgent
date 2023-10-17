package com.mtattab.reverseshell.util;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class SystemCommandProxyUtilTest {


    @Test
    public void executeCommandTest() {
        String output = SystemCommandProxyUtil.runCommand("ls");
        System.out.println(output);

        assertNotNull(output);


    }
}
