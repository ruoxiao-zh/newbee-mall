package ltd.newbee.mall.service.impl;

import ltd.newbee.mall.dao.CategoryMapper;
import ltd.newbee.mall.entity.Category;
import ltd.newbee.mall.service.NewBeeMallCategoryService;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.PageResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author Richard
 * @Date 2021/4/30 4:10 PM
 */
@Service
public class NewBeeMallCategoryServiceImpl implements NewBeeMallCategoryService {
    
    @Resource
    CategoryMapper categoryMapper;
    
    @Override
    public PageResult getCategorisPage(PageQueryUtil pageUtil) {
        List<Category> goodsCategories = categoryMapper.findGoodsCategoryList(pageUtil);
        int total = categoryMapper.getTotalGoodsCategories(pageUtil);
        
        return new PageResult(goodsCategories, total, pageUtil.getLimit(), pageUtil.getPage());
    }
}
