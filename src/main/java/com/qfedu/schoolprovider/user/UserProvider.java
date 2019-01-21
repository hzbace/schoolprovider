package com.qfedu.schoolprovider.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qfedu.dao.suser.SUserMapper;
import com.qfedu.entity.suser.SUser;
import com.qfedu.service.suser.ISUserService;
import com.qfedu.tool.contant.SystemConst;
import com.qfedu.tool.token.TokenUtil;
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
}
