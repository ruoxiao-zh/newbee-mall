package ltd.newbee.mall.service.impl;

import ltd.newbee.mall.common.ServiceResultEnum;
import ltd.newbee.mall.dao.CategoryMapper;
import ltd.newbee.mall.entity.Category;
import ltd.newbee.mall.service.NewBeeMallCategoryService;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.PageResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
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
    
    @Override
    public String saveCategory(Category category) {
        Category temp = categoryMapper.selectByLevelAndName(category.getCategoryLevel(), category.getCategoryName());
        if (temp != null) {
            return ServiceResultEnum.SAME_CATEGORY_EXIST.getResult();
        }
        
        if (categoryMapper.insertSelective(category) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        
        return ServiceResultEnum.DB_ERROR.getResult();
    }
    
    @Override
    public Boolean deleteBatch(Integer[] ids) {
        if (ids.length < 1) {
            return false;
        }
    
        return categoryMapper.deleteBatch(ids) > 0;
    }
    
    @Override
    public String updateCategory(Category category) {
        Category temp = categoryMapper.getCategoryById(category.getCategoryId());
        if (temp != null) {
            return ServiceResultEnum.SAME_CATEGORY_EXIST.getResult();
        }
    
        Category temp2 = categoryMapper.selectByLevelAndName(category.getCategoryLevel(), category.getCategoryName());
        if (temp2 != null && !temp2.getCategoryId().equals(category.getCategoryId())) {
            // 同名且不同id 不能继续修改
            return ServiceResultEnum.SAME_CATEGORY_EXIST.getResult();
        }
    
        category.setUpdateTime(new Date());
        if (categoryMapper.updateByPrimaryKeySelective(category) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
    
        return ServiceResultEnum.DB_ERROR.getResult();
    }
}
