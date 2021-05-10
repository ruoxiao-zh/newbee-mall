package ltd.newbee.mall.service;

import ltd.newbee.mall.entity.Category;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.PageResult;

/**
 * @Author Richard
 * @Date 2021/4/30 4:10 PM
 */
public interface NewBeeMallCategoryService {
    PageResult getCategorisPage(PageQueryUtil pageUtil);
    
    String saveCategory(Category category);
    
    Boolean deleteBatch(Integer[] ids);
    
    String updateCategory(Category category);
}
