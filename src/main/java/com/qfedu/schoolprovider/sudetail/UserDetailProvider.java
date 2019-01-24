package com.qfedu.schoolprovider.sudetail;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qfedu.dao.sudetail.SUdetailMapper;
import com.qfedu.entity.sudetail.SUdetail;
import com.qfedu.service.sudetail.ISUdetailService;
import com.qfedu.tool.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service("userdetailprovider")
public class UserDetailProvider implements ISUdetailService {

    @Autowired
    SUdetailMapper detailDao;

    @Override
    public ResultVo updateUserDetail(SUdetail sUdetail) {
        QueryWrapper queryWrapper = new QueryWrapper();
        if (detailDao.updateById(sUdetail)> 0){
            return ResultVo.setOK(1);
        }
        return ResultVo.setERROR("资料更新失败");
    }
}
