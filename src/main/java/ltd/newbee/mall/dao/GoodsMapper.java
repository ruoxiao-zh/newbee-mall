package ltd.newbee.mall.dao;

import ltd.newbee.mall.entity.Goods;
import ltd.newbee.mall.util.PageQueryUtil;

import java.util.List;

/**
 * @author Richard
 * @date 2021/5/12 3:53 PM
 */
public interface GoodsMapper {
    
    List<Goods> findGoodsList(PageQueryUtil pageQueryUtil);
    
    int getTotalGoodsMapper(PageQueryUtil pageQueryUtil);
    
    int insertGoods(Goods goods);
    
    Goods selectById(Long goodsId);
    
    int updateByPrimaryKey(Goods goods);
    
    int updateSellStatusBatch(Long[] ids, int goodsSellStatus);
    
    List<Goods> selectByPrimaryKeys(List<Long> goodsIds);
}
