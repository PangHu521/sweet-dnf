package com.sweet.simple.login.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sweet.simple.login.entity.CharacInfo;

public interface CharacInfoService extends IService<CharacInfo> {
    String[] getAllCharacterNames(Integer uid);
}
