package com.mtattab.reverseshell.util;

import com.mtattab.reverseshell.service.commands.PowerShellScripts;
import lombok.Cleanup;
import lombok.experimental.UtilityClass;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@UtilityClass
public class ResourceClassPathWriterUtil {


    public static File writeFileFromClassToDir(String fileName, File dir) throws IOException {
        String filePath= dir.getAbsolutePath()+ File.separator+fileName;
        System.out.println(filePath);
        @Cleanup
        InputStream file = PowerShellScripts.class.getClassLoader().getResourceAsStream(fileName);;

        DataManipulationUtil.writeInputStreamToFile(file,filePath);

        return new File(filePath);

    }
}
