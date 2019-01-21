package com.qfedu.schoolprovider.sudetail;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qfedu.dao.sudetail.SUdetailMapper;
import com.qfedu.entity.sudetail.SUdetail;
import com.qfedu.service.sudetail.ISUdetailService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service("userdetailprovider")
public class UserDetailProvider extends ServiceImpl<SUdetailMapper, SUdetail> implements ISUdetailService {

}
