package com.sweet.simple.login.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sweet.simple.login.entity.CharacInfo;
import com.sweet.simple.login.mapper.CharacInfoMapper;
import com.sweet.simple.login.service.CharacInfoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: [ph]
 * @Date: 2024/6/3 22:58
 * @Description: 角色表具体业务实现
 **/
@Service
public class CharacInfoServiceImpl extends ServiceImpl<CharacInfoMapper, CharacInfo> implements CharacInfoService {

    @Override
    public String[] getAllCharacterNames(Integer uid) {
        List<String> nameList = lambdaQuery().eq(CharacInfo::getmId, uid)
                .list().stream().map(CharacInfo::getCharacName)
                .collect(Collectors.toList());
        String[] nameArray = nameList.stream().toArray(String[]::new);
        return nameArray.length > 0 ? nameArray : new String[0];
    }
}
