package com.qfedu.schoolprovider.user;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qfedu.dao.suser.SUserMapper;
import com.qfedu.entity.suser.SUser;
import com.qfedu.service.suser.ISUserService;
import com.qfedu.tool.contant.SystemConst;
import com.qfedu.tool.token.LoginToken;
import com.qfedu.tool.token.TokenUtil;
import com.qfedu.tool.util.Base64Util;
import com.qfedu.tool.util.EncryptUtil;
import com.qfedu.tool.util.JedisUtil;
import com.qfedu.tool.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service("userprovider")
public class UserProvider implements ISUserService {
    @Autowired
    private SUserMapper dao;

    @Autowired
    private JedisUtil jedisUtil;

    @Override
    public ResultVo check(String usernum) {
        QueryWrapper<SUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("usernum",usernum);
        SUser user = dao.selectOne(queryWrapper);
        if (user!=null){
            return ResultVo.setERROR("学号重复 请重新输入");
        }else {
            return ResultVo.setOK(null);
        }

    }

    @Override
    public ResultVo login(String usernum, String userpassword) {
        QueryWrapper<SUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("usernum",usernum);
        SUser user = dao.selectOne(queryWrapper);
        if (user!=null){
            if (Objects.equals(user.getUserpassword(),userpassword)){
                //1.生成Token
                String token = TokenUtil.createToken(user);
                //2.将Token到Redis
                jedisUtil.save("usertokens","user:" + token, user.getUsernum());
                //3.将Token发向前端
                return ResultVo.setOK(token);

            }
        }
        return ResultVo.setERROR("登陆异常");



    }

    @Override
    public ResultVo loginout(String token) {
        if (jedisUtil.checkFiled(SystemConst.TOKENMAP,"user:" + token)){
            jedisUtil.del(SystemConst.TOKENMAP,"user:" + token);
        }
        return ResultVo.setOK(null);
    }

    @Override
    public ResultVo updatePassword(String userpassword, String newpassword, String token, String newpassword2) {
        String json = EncryptUtil.AESDec(Base64Util.base64Dec(SystemConst.TOKENKEY),token);
        LoginToken loginToken = JSON.parseObject(json,LoginToken.class);
        int userid = loginToken.getId();
        SUser user = dao.selectById(userid);
        String oldpassword = user.getUserpassword();
        if (oldpassword != userpassword){
            return ResultVo.setERROR("密码输入错误");
        }else{
            if (newpassword==newpassword2){
                return ResultVo.setOK(null);
            }else{
                return ResultVo.setERROR("两次密码输入不符");
            }

        }


    }

    @Override
    public ResultVo getUsernum(String token) {
        SUser user = TokenUtil.GetToken(token);
        Integer id = user.getUserid();
        QueryWrapper<SUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid",id);
        SUser sUser = dao.selectOne(queryWrapper);
        String usernum = sUser.getUsernum();
        if (usernum!=null){
            return ResultVo.setOK(usernum);
        }else{
            return ResultVo.setERROR("查询结果为空");
        }
    }


}
