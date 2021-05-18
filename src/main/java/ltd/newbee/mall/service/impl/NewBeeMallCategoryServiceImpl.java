package ltd.newbee.mall.service.impl;

import ltd.newbee.mall.common.Constants;
import ltd.newbee.mall.common.NewBeeMallCategoryLevelEnum;
import ltd.newbee.mall.common.ServiceResultEnum;
import ltd.newbee.mall.controller.vo.NewBeeMallIndexCategoryVO;
import ltd.newbee.mall.controller.vo.SearchPageCategoryVO;
import ltd.newbee.mall.controller.vo.SecondLevelCategoryVO;
import ltd.newbee.mall.controller.vo.ThirdLevelCategoryVO;
import ltd.newbee.mall.dao.CategoryMapper;
import ltd.newbee.mall.entity.Category;
import ltd.newbee.mall.service.NewBeeMallCategoryService;
import ltd.newbee.mall.util.BeanUtil;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.PageResult;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

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
        int            total           = categoryMapper.getTotalGoodsCategories(pageUtil);
        
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
        Category temp = categoryMapper.selectByPrimaryKey(category.getCategoryId());
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
    
    @Override
    public List<Category> selectByLevelAndParentIdsAndNumber(List<Long> parentIds, int categoryLevel) {
        // 0 代表查询所有
        return categoryMapper.selectByLevelAndParentIdsAndNumber(parentIds, categoryLevel, 0);
    }
    
    @Override
    public Category getCategoryById(Long categoryId) {
        return categoryMapper.selectByPrimaryKey(categoryId);
    }
    
    @Override
    public List<NewBeeMallIndexCategoryVO> getCategoriesForIndex() {
        List<NewBeeMallIndexCategoryVO> newBeeMallIndexCategoryVOS = new ArrayList<>();
        // 获取一级分类的固定数量的数据
        List<Category> firstLevelCategories = categoryMapper.selectByLevelAndParentIdsAndNumber(
                Collections.singletonList(0L),
                NewBeeMallCategoryLevelEnum.LEVEL_ONE.getLevel(),
                Constants.INDEX_CATEGORY_NUMBER);
        
        if (!CollectionUtils.isEmpty(firstLevelCategories)) {
            List<Long> firstLevelCategoryIds = firstLevelCategories.stream().map(Category::getCategoryId).collect(Collectors.toList());
            // 获取二级分类的数据
            List<Category> secondLevelCategories = categoryMapper.selectByLevelAndParentIdsAndNumber(
                    firstLevelCategoryIds,
                    NewBeeMallCategoryLevelEnum.LEVEL_TWO.getLevel(),
                    0);
            if (!CollectionUtils.isEmpty(secondLevelCategories)) {
                List<Long> secondLevelCategoryIds = secondLevelCategories.stream().map(Category::getCategoryId).collect(Collectors.toList());
                //获取三级分类的数据
                List<Category> thirdLevelCategories = categoryMapper.selectByLevelAndParentIdsAndNumber(
                        secondLevelCategoryIds,
                        NewBeeMallCategoryLevelEnum.LEVEL_THREE.getLevel(),
                        0);
                if (!CollectionUtils.isEmpty(thirdLevelCategories)) {
                    // 根据 parentId 将 thirdLevelCategories 分组
                    Map<Long, List<Category>>   thirdLevelCategoryMap  = thirdLevelCategories.stream().collect(groupingBy(Category::getParentId));
                    List<SecondLevelCategoryVO> secondLevelCategoryVOS = new ArrayList<>();
                    // 处理二级分类
                    for (Category secondLevelCategory : secondLevelCategories) {
                        SecondLevelCategoryVO secondLevelCategoryVO = new SecondLevelCategoryVO();
                        BeanUtil.copyProperties(secondLevelCategory, secondLevelCategoryVO);
                        // 如果该二级分类下有数据则放入 secondLevelCategoryVOS 对象中
                        if (thirdLevelCategoryMap.containsKey(secondLevelCategory.getCategoryId())) {
                            // 根据二级分类的id取出thirdLevelCategoryMap分组中的三级分类list
                            List<Category> tempGoodsCategories = thirdLevelCategoryMap.get(secondLevelCategory.getCategoryId());
                            secondLevelCategoryVO.setThirdLevelCategoryVOS((BeanUtil.copyList(tempGoodsCategories, ThirdLevelCategoryVO.class)));
                            secondLevelCategoryVOS.add(secondLevelCategoryVO);
                        }
                    }
                    
                    // 处理一级分类
                    if (!CollectionUtils.isEmpty(secondLevelCategoryVOS)) {
                        //根据 parentId 将 thirdLevelCategories 分组
                        Map<Long, List<SecondLevelCategoryVO>> secondLevelCategoryVOMap = secondLevelCategoryVOS.stream().collect(groupingBy(SecondLevelCategoryVO::getParentId));
                        for (Category firstCategory : firstLevelCategories) {
                            NewBeeMallIndexCategoryVO newBeeMallIndexCategoryVO = new NewBeeMallIndexCategoryVO();
                            BeanUtil.copyProperties(firstCategory, newBeeMallIndexCategoryVO);
                            //如果该一级分类下有数据则放入 newBeeMallIndexCategoryVOS 对象中
                            if (secondLevelCategoryVOMap.containsKey(firstCategory.getCategoryId())) {
                                //根据一级分类的id取出secondLevelCategoryVOMap分组中的二级级分类list
                                List<SecondLevelCategoryVO> tempGoodsCategories = secondLevelCategoryVOMap.get(firstCategory.getCategoryId());
                                newBeeMallIndexCategoryVO.setSecondLevelCategoryVOS(tempGoodsCategories);
                                newBeeMallIndexCategoryVOS.add(newBeeMallIndexCategoryVO);
                            }
                        }
                    }
                }
            }
            
            return newBeeMallIndexCategoryVOS;
        }
        
        return null;
    }
    
    @Override
    public SearchPageCategoryVO getCategoriesForSearch(Long categoryId) {
        SearchPageCategoryVO searchPageCategoryVO    = new SearchPageCategoryVO();
        Category             thirdLevelGoodsCategory = categoryMapper.selectByPrimaryKey(categoryId);
        if (thirdLevelGoodsCategory != null && thirdLevelGoodsCategory.getCategoryLevel() == NewBeeMallCategoryLevelEnum.LEVEL_THREE.getLevel()) {
            //获取当前三级分类的二级分类
            Category secondLevelGoodsCategory = categoryMapper.selectByPrimaryKey(thirdLevelGoodsCategory.getParentId());
            if (secondLevelGoodsCategory != null && secondLevelGoodsCategory.getCategoryLevel() == NewBeeMallCategoryLevelEnum.LEVEL_TWO.getLevel()) {
                //获取当前二级分类下的三级分类List
                List<Category> thirdLevelCategories = categoryMapper.selectByLevelAndParentIdsAndNumber(
                        Collections.singletonList(secondLevelGoodsCategory.getCategoryId()),
                        NewBeeMallCategoryLevelEnum.LEVEL_THREE.getLevel(), Constants.SEARCH_CATEGORY_NUMBER);
                searchPageCategoryVO.setCurrentCategoryName(thirdLevelGoodsCategory.getCategoryName());
                searchPageCategoryVO.setSecondLevelCategoryName(secondLevelGoodsCategory.getCategoryName());
                searchPageCategoryVO.setThirdLevelCategoryList(thirdLevelCategories);
                
                return searchPageCategoryVO;
            }
        }
        
        return null;
    }
}
