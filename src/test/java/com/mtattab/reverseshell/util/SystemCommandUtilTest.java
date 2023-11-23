package com.mtattab.reverseshell.util;

import org.junit.Test;

import static com.mtattab.reverseshell.util.SystemCommandUtil.runCommand;
import static org.junit.Assert.assertNotNull;


public class SystemCommandUtilTest {


    @Test
    public void executeCommandTest() {
        String output = runCommand("ls");
        System.out.println(output);
        System.out.println(runCommand( "cd c:/")); // For Windows users list files

        System.out.println(runCommand( "pwd")); // For Windows users list files
        System.out.println(runCommand( "powershell")); // For Windows users list files
        System.out.println(runCommand( "pwd")); // For Windows users list files

        System.out.println(runCommand( "cd ..")); // For Windows users list files
        System.out.println(runCommand( "dir")); // For Windows users list files
        assertNotNull(output);


    }

    @Test
    public void executeCommandTest2() {
        String output = runCommand("tasklist");

        System.out.println(output);
//        output = runCommand("time");
        System.out.println(output);

        assertNotNull(output);


    }
}
