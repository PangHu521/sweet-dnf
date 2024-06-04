package com.sweet.simple.login.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sweet.simple.login.entity.Postal;
import com.sweet.simple.login.mapper.PostalMapper;
import com.sweet.simple.login.service.PostalService;
import org.springframework.stereotype.Service;

/**
 * @Author: [ph]
 * @Date: 2024/6/3 22:58
 * @Description:
 **/
@Service
public class PostalServiceImpl extends ServiceImpl<PostalMapper, Postal> implements PostalService {
}
