package com.sweet.simple.login.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sweet.simple.login.entity.CdkManagement;

/**
* @author 24868
* @description 针对表【cdk_management(cdk表)】的数据库操作Service
* @createDate 2024-06-01 17:12:00
*/
public interface CdkManagementService extends IService<CdkManagement> {

    boolean editStatus(String cdkCode);
}
