package com.smallhowe.utils;


import com.smallhowe.entity.Res;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


public class FileUtils {
    public String saveFile(Res res, MultipartFile file, String path) {
        File createFile = new File(path);
        if (!createFile.exists()) {
            createFile.mkdirs();
        }
        createFile = new File(path + "\\" + file.getOriginalFilename());
        try {
            file.transferTo(createFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return createFile.getPath().replace("\\","/");
    }
    public void deleteFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
    }
    /**
     * 格式化文件大小
     * */
    public String formatFileSize(long size){
        final String[] units = new String[] { "B","KB", "MB", "GB", "TB" };
        int unitIndex = 0;
        double bd = (double) size;
        while (bd >= 1024D) {
            bd /= 1024D;
            unitIndex++;
        }

        String formatSize = String.format("%.2f", bd);

        return formatSize + " " + units[unitIndex];
    }
}
