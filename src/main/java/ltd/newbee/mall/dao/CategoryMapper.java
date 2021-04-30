package ltd.newbee.mall.dao;

import ltd.newbee.mall.entity.Category;
import ltd.newbee.mall.util.PageQueryUtil;

import java.util.List;

/**
 * @Author Richard
 * @Date 2021/4/30 4:11 PM
 */
public interface CategoryMapper {
    List<Category> findGoodsCategoryList(PageQueryUtil pageQueryUtil);
    
    Integer getTotalGoodsCategories(PageQueryUtil pageQueryUtil);
}
