package com.sweet.simple.login.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sweet.simple.login.entity.CharacInfo;

/**
 * @author 24868
 * @description 针对表【charac_info】的数据库操作Service
 * @createDate 2024-06-03 20:34:29
 */
public interface CharacInfoService extends IService<CharacInfo> {
    String[] getAllCharacterNames(Integer uid);
}
