package com.sweet.simple.login;

import cn.hutool.json.JSONUtil;
import com.sweet.simple.login.entity.Accounts;
import com.sweet.simple.login.service.AccountsService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@SpringBootTest
public class DbTest {

    @Resource
    private AccountsService accountsService;

    @Test
    public void getAccountListTest() {
        List<Accounts> accounts = accountsService.list();
        log.info(JSONUtil.toJsonStr(accounts));
    }
}
