package ru.mmtr.translationdictionary.domainservice.common;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;

public class FileUtils {
    public static MultipartFile convertDataFromListToFile(ArrayList<String> data) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        for (String item : data) {
            stringBuilder.append(item).append("\n");
        }

        byte[] contentBytes = stringBuilder.toString().getBytes();
        InputStream inputStream = new ByteArrayInputStream(contentBytes);

        return new MultipartFile() {
            @Override
            public String getName() {
                return "data.txt";
            }

            @Override
            public String getOriginalFilename() {
                return "data.txt";
            }

            @Override
            public String getContentType() {
                return "text/plain";
            }

            @Override
            public boolean isEmpty() {
                return contentBytes.length == 0;
            }

            @Override
            public long getSize() {
                return contentBytes.length;
            }

            @Override
            public byte[] getBytes() throws IOException {
                return contentBytes;
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return inputStream;
            }

            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {

            }
        };
    }

    public static String saveMultipartFile(MultipartFile multipartFile, String destinationPath) throws IOException {
        File file = new File(destinationPath);
        file.getParentFile().mkdirs();
        file.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        FileCopyUtils.copy(multipartFile.getInputStream(), fileOutputStream);
        fileOutputStream.close();
        return file.getAbsolutePath();
    }
}
