package com.qfedu.schoolprovider.sblog;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qfedu.dao.sblog.SBlogMapper;
import com.qfedu.entity.sblog.SBlog;
import com.qfedu.entity.suser.SUser;
import com.qfedu.service.sblog.ISBlogService;
import com.qfedu.tool.contant.SystemConst;
import com.qfedu.tool.token.LoginToken;
import com.qfedu.tool.token.TokenUtil;
import com.qfedu.tool.util.Base64Util;
import com.qfedu.tool.util.EncryptUtil;
import com.qfedu.tool.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service("sblogprovider")
public class SblogProvider implements ISBlogService {
    @Autowired
    private SBlogMapper dao;
    @Override
    public ResultVo checkBlog(Integer blStyle, String token, String blItem) {
        if (blItem.contains("泥马") || blItem.contains("草") || blItem.contains("靠") || blItem.contains("尼玛")){
            return ResultVo.setERROR("输入标题不合法");
        }else {
            SUser user = TokenUtil.GetToken(token);
            int blUid = user.getUserid();
            SBlog sBlog = new SBlog();
            sBlog.setBlCreatetime(LocalDateTime.now());
            sBlog.setBlItem(blItem);
            sBlog.setBlStyle(blStyle);
            sBlog.setBlUid(blUid);
            dao.insert(sBlog);

            return ResultVo.setOK(null);


        }

    }

    @Override
    public ResultVo showAllBlog() {



        List<SBlog> list = dao.getAllBlog();
        if (list!=null){
            return ResultVo.setOK(list);
        }else{
            return ResultVo.setERROR("没有查询结果");
        }

    }

    @Override
    public ResultVo updateShare(Integer blId, Integer blSharenum) {
       if (dao.updateShare(blId, blSharenum)>0){
           return ResultVo.setOK(null);
       }else{
           return ResultVo.setERROR("修改失败");
       }

    }


}
