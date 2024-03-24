package com.smallhowe.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.smallhowe.entity.Res;
import com.smallhowe.mapper.ResMapper;
import com.smallhowe.service.ResService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smallhowe.utils.FileUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author smallhowe
 * @since 2024-03-23
 */
@Service
public class ResServiceImpl extends ServiceImpl<ResMapper, Res> implements ResService {
    @Value("${my.address}")
    private String address;
    @Value("${server.port}")
    private String port;
    @Value("${my.file.path}")
    private String path;
    @Resource
    private ResMapper resMapper;
    public int updateClient(Res res, MultipartFile file) {
        res.setId(1);
        boolean flag = updateRes(res, file);
        if (flag){
            return 1;
        }

        return 0;
    }
    public int updateMods(Res res, MultipartFile file) {
        res.setId(2);
        boolean flag = updateRes(res, file);
        if (flag){
            return 1;
        }

        return 0;
    }
    private boolean updateRes(Res res,MultipartFile file) {
        FileUtils fileUtils = new FileUtils();
        Res selRes = resMapper.selectById(res.getId());
//        System.out.println("查询到的数据"+selRes);
        //如果有文件，则执行删除
        if (selRes.getPath()!=null){
//            System.out.println("删除文件");
            fileUtils.deleteFile(selRes.getPath());
        }

        selRes.setName(res.getName());
        selRes.setContent(res.getContent());
        selRes.setVersion(res.getVersion());

        String savePath = fileUtils.saveFile(res, file, path);
        selRes.setPath(savePath);

        selRes.setSize(file.getSize());

        String url = address + ":" + port + "/resource/download/" + file.getOriginalFilename();
        selRes.setUrl(url);

        resMapper.updateById(selRes);

        return true;
    }
}
