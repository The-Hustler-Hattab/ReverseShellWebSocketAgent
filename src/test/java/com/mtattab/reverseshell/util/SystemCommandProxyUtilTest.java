package com.mtattab.reverseshell.util;

import com.mtattab.reverseshell.model.CommandRestOutput;
import org.junit.Test;

import static com.mtattab.reverseshell.util.SystemCommandProxyUtil.runCommand;
import static org.junit.Assert.assertNotNull;


public class SystemCommandProxyUtilTest {


    @Test
    public void executeCommandTest() {
        CommandRestOutput output = runCommand("ls");
        System.out.println(output);
        System.out.println(runCommand( "cd c:/")); // For Windows users list files

        System.out.println(runCommand( "pwd")); // For Windows users list files
        System.out.println(runCommand( "powershell")); // For Windows users list files
        System.out.println(runCommand( "pwd")); // For Windows users list files

        System.out.println(runCommand( "cd ..")); // For Windows users list files
        System.out.println(runCommand( "dir")); // For Windows users list files
        assertNotNull(output);


    }
}
