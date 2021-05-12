package ltd.newbee.mall.service;

import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.PageResult;

/**
 * @Author Richard
 * @Date 2021/5/11 4:26 PM
 */
public interface NewBeeMallGoodsService {
    
    PageResult getGoodsPage(PageQueryUtil pageQueryUtil);
}
