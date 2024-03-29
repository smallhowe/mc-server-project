package com.smallhowe.controller;

import com.smallhowe.entity.Account;
import com.smallhowe.entity.Res;
import com.smallhowe.entity.RestBean;
import com.smallhowe.service.ResService;
import com.smallhowe.utils.FileUtils;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author smallhowe
 * @since 2024-03-23
 */
@RestController
@RequestMapping("/api/res")
public class ResController {

    @Resource
    private ResService resService;

    @PostMapping("/upload")
    public RestBean<Object> uploadRes(Res res, MultipartFile file, @SessionAttribute("account")Account account){
        if (account.getGroups()!=1)
            return RestBean.failure(403,"权限不足");
        //设置操作者
        res.setOperator(account.getUsername());
        int flag = resService.updateRes(res, file);
        if (flag==1) return RestBean.success("上传成功");
        else return RestBean.failure(500,"上传失败");

    }
    @GetMapping("/list")
    public RestBean<Object> getResList(){
        List<Res> list = resService.list();
        if (list.isEmpty())
            return RestBean.failure(404,"没有资源");
        return RestBean.success(null,list);
    }
}
