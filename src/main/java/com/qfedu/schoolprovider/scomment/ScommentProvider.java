package com.qfedu.schoolprovider.scomment;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qfedu.dao.sblog.SBlogMapper;
import com.qfedu.dao.scomment.SCommentMapper;
import com.qfedu.entity.sblog.SBlog;
import com.qfedu.entity.scomment.SComment;
import com.qfedu.entity.suser.SUser;
import com.qfedu.service.scomment.ISCommentService;
import com.qfedu.tool.contant.SystemConst;
import com.qfedu.tool.token.LoginToken;
import com.qfedu.tool.token.TokenUtil;
import com.qfedu.tool.util.Base64Util;
import com.qfedu.tool.util.EncryptUtil;
import com.qfedu.tool.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("scommentprovider")
public class ScommentProvider implements ISCommentService {
    @Autowired
    private SCommentMapper dao;
    @Autowired
    private SBlogMapper bdao;
    @Override
    public ResultVo showAllComment(Integer cmId) {
        QueryWrapper<SComment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cm_id",cmId);
        List<SComment> list = dao.selectList(queryWrapper);
        if (list!=null){
            return ResultVo.setOK(list);
        }else{
            return ResultVo.setERROR("评论为空");
        }

    }

    @Override
    public ResultVo addComment(Integer cmId, String token, String cmContent) {
        SUser user = TokenUtil.GetToken(token);

        int cmUid = user.getUserid();
        SComment sComment = new SComment();
        sComment.setCmUid(cmUid);
        sComment.setCmId(cmId);
        sComment.setCmContent(cmContent);
        if (sComment != null) {
            Integer blId = cmId;
            QueryWrapper<SBlog> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("bl_Id", blId);
            SBlog blog = bdao.selectOne(queryWrapper);
            Integer blCommentnum = blog.getBlCommentnum();
            dao.insert(sComment);

            if (bdao.updateComment(blId, blCommentnum) > 0) {
                return ResultVo.setOK(null);
            } else {
                return ResultVo.setERROR("添加失败");
            }

        } else {
            return ResultVo.setERROR("发表评论发生错误");
        }


    }
}
