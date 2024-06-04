package com.sweet.simple.login.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sweet.simple.login.entity.CharacInfo;
import com.sweet.simple.login.mapper.CharacInfoMapper;
import com.sweet.simple.login.service.CharacInfoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 24868
 * @description 针对表【charac_info】的数据库操作Service实现
 * @createDate 2024-06-03 20:34:29
 */
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
