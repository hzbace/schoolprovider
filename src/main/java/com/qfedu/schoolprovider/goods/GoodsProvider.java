package com.qfedu.schoolprovider.goods;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qfedu.dao.sgoods.SGoodsMapper;
import com.qfedu.entity.sgoods.SGoods;
import com.qfedu.service.sgoods.ISGoodsService;
import com.qfedu.tool.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("goodsprovider")
public class GoodsProvider implements ISGoodsService {
    @Autowired
    private SGoodsMapper dao;
    @Override
    public ResultVo getAllGoods() {
        QueryWrapper<SGoods> queryWrapper = new QueryWrapper<>();
        List<SGoods> list = dao.selectList(queryWrapper);
        if (list!=null){
            return ResultVo.setOK(list);
        }else{
            return ResultVo.setERROR("查询数据为空");
        }

    }

    @Override
    public ResultVo addGoods(String gName, String gContent,String gUwant,String gUsernum) {
        SGoods goods = new SGoods();
        goods.setgName(gName);
        goods.setgContent(gContent);
        goods.setgUwant(gUwant);
        goods.setgUsernum(gUsernum);
        if (goods!=null){
            dao.insert(goods);
            return ResultVo.setOK(null);
        }else {
            return ResultVo.setERROR("添加失败");
        }


    }


}
