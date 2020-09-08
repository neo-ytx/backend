package edu.fudan.backend.utils;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Slf4j
public class FileUtils {
    public static void saveJson(List<Map<String, String>> list, String filename) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(list);
        File target = new File(filename);
        try (Writer writer = new FileWriter(target)) {
            writer.write(content);
        } catch (Exception e) {
            log.error("write json", e);
        }
    }

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

    public static void saveBase64Img(String base64, String filePath, String fileName) throws IOException {
        if (base64 != null && base64.contains("image/png;base64")) {
            String input = base64.split(",")[1];
            OutputStream out;
            try {
                Base64.Decoder decoder = Base64.getDecoder();
                byte[] buffer = decoder.decode(input);
                out = new FileOutputStream(filePath + fileName);
                out.write(buffer);
                out.flush();
                out.close();
            } catch (FileNotFoundException e) {
                log.error("create image error", e);
            }
        } else {
            log.error("base64 String is empty or don not meet requirement.");
        }

    }
}
