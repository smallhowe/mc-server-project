package com.smallhowe.service;

import com.smallhowe.entity.Res;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author smallhowe
 * @since 2024-03-23
 */
public interface ResService extends IService<Res> {
    public int updateClient(Res res, MultipartFile file);

    public int updateMods(Res res, MultipartFile file);
}
