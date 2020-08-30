package edu.fudan.backend.utils;


import lombok.extern.slf4j.Slf4j;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Slf4j
public class FileUtils {
    public static void fileUpload(byte[] file, String filePath, String fileName) throws IOException {
        File target = new File(filePath + fileName);
        if (!target.getParentFile().exists()) {
            boolean result = target.getParentFile().mkdirs();
            if (!result) {
                log.error("create file path error.");
            }
        }
        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(target));
        stream.write(file);
        stream.close();
    }
}
