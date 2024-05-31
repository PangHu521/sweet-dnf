package com.sweet.simple.login.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sweet.simple.login.entity.Accounts;
import com.sweet.simple.login.mapper.AccountsMapper;
import org.springframework.stereotype.Service;

/**
 * 玩家账号服务
 *
 * @author 大师兄
 */
@Service
public class AccountsService extends ServiceImpl<AccountsMapper, Accounts> {

}
