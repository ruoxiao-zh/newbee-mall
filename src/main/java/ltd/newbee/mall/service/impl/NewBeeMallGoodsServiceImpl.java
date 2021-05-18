package ltd.newbee.mall.service.impl;

import ltd.newbee.mall.common.ServiceResultEnum;
import ltd.newbee.mall.controller.vo.NewBeeMallSearchGoodsVO;
import ltd.newbee.mall.dao.GoodsMapper;
import ltd.newbee.mall.entity.Goods;
import ltd.newbee.mall.service.NewBeeMallGoodsService;
import ltd.newbee.mall.util.BeanUtil;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.PageResult;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
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
    
    @Override
    public String saveNewBeeMallGoods(Goods goods) {
        if (goodsMapper.insertGoods(goods) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        
        return ServiceResultEnum.DB_ERROR.getResult();
    }
    
    @Override
    public Goods getGoodsById(long goodsId) {
        return goodsMapper.selectById(goodsId);
    }
    
    @Override
    public String updateNewBeeMallGoods(Goods goods) {
        Goods temp = goodsMapper.selectById(goods.getGoodsId());
        if (temp == null) {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult();
        }
        
        goods.setCreateTime(new Date());
        goods.setUpdateTime(new Date());
        goods.setCreateUser(0);
        goods.setUpdateUser(0);
        if (goodsMapper.updateByPrimaryKey(goods) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        
        return ServiceResultEnum.DB_ERROR.getResult();
    }
    
    @Override
    public Boolean updateSellStatus(Long[] ids, int sellStatus) {
        if (ids.length < 1) {
            return false;
        }
        
        return goodsMapper.updateSellStatusBatch(ids, sellStatus) > 0;
    }
    
    @Override
    public Goods getNewBeeMallGoodsById(Long goodsId) {
        if (goodsId < 1) {
            return null;
        }
        
        return goodsMapper.selectById(goodsId);
    }
    
    @Override
    public PageResult searchNewBeeMallGoods(PageQueryUtil pageUtil) {
        List<Goods>                   goodsList                = goodsMapper.findNewBeeMallGoodsListBySearch(pageUtil);
        int                           total                    = goodsMapper.getTotalNewBeeMallGoodsBySearch(pageUtil);
        List<NewBeeMallSearchGoodsVO> newBeeMallSearchGoodsVOS = new ArrayList<>();
        if (!CollectionUtils.isEmpty(goodsList)) {
            newBeeMallSearchGoodsVOS = BeanUtil.copyList(goodsList, NewBeeMallSearchGoodsVO.class);
            for (NewBeeMallSearchGoodsVO newBeeMallSearchGoodsVO : newBeeMallSearchGoodsVOS) {
                String goodsName  = newBeeMallSearchGoodsVO.getGoodsName();
                String goodsIntro = newBeeMallSearchGoodsVO.getGoodsIntro();
                // 字符串过长导致文字超出的问题
                if (goodsName.length() > 28) {
                    goodsName = goodsName.substring(0, 28) + "...";
                    newBeeMallSearchGoodsVO.setGoodsName(goodsName);
                }
                
                if (goodsIntro.length() > 30) {
                    goodsIntro = goodsIntro.substring(0, 30) + "...";
                    newBeeMallSearchGoodsVO.setGoodsIntro(goodsIntro);
                }
            }
        }
        
        return new PageResult(newBeeMallSearchGoodsVOS, total, pageUtil.getLimit(), pageUtil.getPage());
    }
}
