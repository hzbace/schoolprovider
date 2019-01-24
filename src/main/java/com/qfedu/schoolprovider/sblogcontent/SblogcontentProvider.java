package com.qfedu.schoolprovider.sblogcontent;

import com.qfedu.dao.sblogcontent.SBlogcontentMapper;
import com.qfedu.entity.sblogcontent.SBlogcontent;
import com.qfedu.service.sblogcontent.ISBlogcontentService;
import com.qfedu.tool.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sblogcontentprovider")
public class SblogcontentProvider implements ISBlogcontentService {
    @Autowired
    private SBlogcontentMapper dao;
    @Override
    public ResultVo addBlogContent(Integer blcBid, String blcContent) {
        if (blcBid != 0 && blcBid !=null || blcContent != null && blcContent.equals("")){
            SBlogcontent sBlogcontent = new SBlogcontent();
            sBlogcontent.setBlcBid(blcBid);
            sBlogcontent.setBlcContent(blcContent);
            dao.insert(sBlogcontent);
            return ResultVo.setOK(null);

        }else{
            return ResultVo.setERROR("评论失败");
        }

    }
}
