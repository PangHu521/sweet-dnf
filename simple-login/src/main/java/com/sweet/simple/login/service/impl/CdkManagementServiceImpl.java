package com.sweet.simple.login.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sweet.simple.login.entity.CdkManagement;
import com.sweet.simple.login.mapper.CdkManagementMapper;
import com.sweet.simple.login.service.CdkManagementService;
import org.springframework.stereotype.Service;

/**
 * @Author: [ph]
 * @Date: 2024/6/1 17:05
 * @Description: cdk表具体业务实现
 **/
@Service
public class CdkManagementServiceImpl extends ServiceImpl<CdkManagementMapper, CdkManagement> implements CdkManagementService {

    /**
     * @Author 「ph」
     * @Description 修改cdk状态为已使用
     * @param: cdkCode - [String] cdk码
     * @return: boolean
     * @Version 1.0.0
     * @Date 2024/06/02 09:31
     */
    @Override
    public boolean editStatus(String cdkCode) {
        return lambdaUpdate().eq(CdkManagement::getCdkCode, cdkCode)
                .set(CdkManagement::getUseFlag, 1).update();
    }
}
