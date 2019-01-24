package com.qfedu.schoolprovider.sschedule;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qfedu.dao.sschedule.SScheduleMapper;
import com.qfedu.entity.sschedule.SSchedule;
import com.qfedu.service.sschedule.ISScheduleService;
import com.qfedu.tool.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("sscheduleprovider")
public class SscheduleProvider implements ISScheduleService {
    @Autowired
    private SScheduleMapper dao;


    @Override
    public ResultVo selectByMjid(Integer sdMjid) {
        QueryWrapper<SSchedule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sd_mjid" , sdMjid);
        List<SSchedule> list = dao.selectList(queryWrapper);
        if (list!=null){

            return ResultVo.setOK(list);

        }else{

            return ResultVo.setERROR("查询结果为空");
        }
    }
}
