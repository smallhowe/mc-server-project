package com.smallhowe.utils;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Component
public class ImageUtils {
    @Value("${my.address}")
    private String address;
    @Value("${server.port}")
    private String port;
    @Value("${my.image.path}")
    private String imagePath;
    //支持传入的图片类型


    @PostConstruct
    private void initPath(){
        if (imagePath.equals("default") || imagePath.isEmpty()) {
            imagePath = System.getProperty("user.dir").replace("\\", "/") + "/static/images/";
        }else if (!imagePath.substring(imagePath.length()-1).equals("/")) {
            imagePath = imagePath + "/";
        }

    }
    /**
     * @param img 图片文件
     * @return  Map：{url:头像地址,path:保存路径}
     */

    public Map<String,String> saveAvatar(MultipartFile img)  {
        String savePath = imagePath.replace("\\", "/")+"user_avatar/";

        String newImgName = saveImage(img, savePath);
        if(newImgName == null) return null;
        String avatarUrl =  address + ":" + port + "/img/user_avatar/" + newImgName;

        Map<String, String> map = new HashMap<>();
        map.put("url", avatarUrl);
        map.put("path", savePath + newImgName);

        return map;
    }

    public Map<String,String> saveImgToImages(MultipartFile img)  {
        String savePath = imagePath;
        savePath = savePath.replace("\\", "/");
        String newImgName = saveImage(img, savePath);
        if (newImgName == null) return null;
        String imgUrl= address + ":" + port + "/img/" + newImgName;
        Map<String, String> map = new HashMap<>();
        map.put("url", imgUrl);
        map.put("path", savePath + newImgName);
        return map;
    }


    private String checkImageType(MultipartFile img)  {
        List<String> IMAGE_TYPES = List.of("jpg", "jpeg", "png");
        //判断图片是否为空
        if (Objects.requireNonNull(img.getOriginalFilename()).isEmpty()) return null;
        //获取图片后缀
        String fileSuffix = img.getOriginalFilename().
                substring(img.getOriginalFilename().lastIndexOf(".") + 1)
                .toLowerCase();
        //判断图片类型是否符合要求
        if (!IMAGE_TYPES.contains(fileSuffix)) return null;

        return fileSuffix;
    }

    private String saveImage(MultipartFile img, String savePath) {
        //获取图片后缀，为空则非支持的图片格式
        String imgSuffix = checkImageType(img);
        if (imgSuffix == null) return null;

        String newImgName=null;
        //判断路径是否以/结尾
        if (savePath.endsWith("/")){
            newImgName = UUID.randomUUID().toString().replace("-", "") + "." + imgSuffix;
        }else {
            newImgName = "/" + UUID.randomUUID().toString().replace("-", "") + "." + imgSuffix;
        }

        //判断目录是否存在，不存在则创建
        File checkDir = new File(savePath);
        if (!checkDir.exists()) {
            checkDir.mkdirs();
        }

        File saveImg = new File(savePath, newImgName);
        try {
            img.transferTo(saveImg);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return newImgName;
    }

    /**
     * @param imgPath 图片路径
     */
    public void deleteImage(String imgPath) {
        File file = new File(imgPath);
        boolean isDeleted = false;
        if (file.exists()) {
            isDeleted=file.delete();
        }
    }
}
