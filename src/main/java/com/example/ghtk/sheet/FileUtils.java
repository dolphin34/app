package com.example.ghtk.sheet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtils {
    private FileUtils() {
    }

    public static InputStream getResourceAsStream(String path) {
        try {
            File file = new File(path);
            return (file.exists())
                    ? new FileInputStream(file)
                    : FileUtils.class.getClassLoader().getResourceAsStream(path);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String readFileWithDefaultCharset(String path) throws IOException {
        return readFile(path, Charset.defaultCharset());
    }

    public static String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return encoding.decode(ByteBuffer.wrap(encoded)).toString();
    }
}
