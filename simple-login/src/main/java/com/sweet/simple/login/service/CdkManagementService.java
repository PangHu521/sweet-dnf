package com.sweet.simple.login.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sweet.simple.login.entity.CdkManagement;

public interface CdkManagementService extends IService<CdkManagement> {

    boolean editStatus(String cdkCode);
}
