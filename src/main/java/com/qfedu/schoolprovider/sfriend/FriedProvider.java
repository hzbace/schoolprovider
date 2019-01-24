package com.qfedu.schoolprovider.sfriend;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qfedu.dao.sfriend.SFriendMapper;
import com.qfedu.entity.sfriend.SFriend;
import com.qfedu.entity.suser.SUser;
import com.qfedu.service.sfriend.ISFriendService;
import com.qfedu.tool.token.TokenUtil;
import com.qfedu.tool.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("friendprovider")
public class FriedProvider implements ISFriendService {
    @Autowired
    private SFriendMapper dao;
    @Override
    public ResultVo getAllFriend(String token) {
        SUser user = TokenUtil.GetToken(token);
        Integer fUid = user.getUserid();
        QueryWrapper<SFriend> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("f_uid",fUid);
        List<SFriend> list = dao.selectList(queryWrapper);
        if (list!=null){
            return ResultVo.setOK(list);
        }else{
            return ResultVo.setERROR(null);
        }



    }
}
