package ltd.newbee.mall.service.impl;

import ltd.newbee.mall.dao.GoodsMapper;
import ltd.newbee.mall.entity.Goods;
import ltd.newbee.mall.service.NewBeeMallGoodsService;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.PageResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author Richard
 * @Date 2021/5/11 4:26 PM
 */
@Service
public class NewBeeMallGoodsServiceImpl implements NewBeeMallGoodsService {
    
    @Resource
    GoodsMapper goodsMapper;
    
    @Override
    public PageResult getGoodsPage(PageQueryUtil pageQueryUtil) {
        List<Goods> goods = goodsMapper.findGoodsList(pageQueryUtil);
        int         total = goodsMapper.getTotalGoodsMapper(pageQueryUtil);
        
        return new PageResult(goods, total, pageQueryUtil.getLimit(), pageQueryUtil.getPage());
    }
}