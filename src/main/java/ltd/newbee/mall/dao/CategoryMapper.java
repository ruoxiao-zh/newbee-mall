package ltd.newbee.mall.dao;

import ltd.newbee.mall.entity.Category;
import ltd.newbee.mall.util.PageQueryUtil;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author Richard
 * @Date 2021/4/30 4:11 PM
 */
public interface CategoryMapper {
    List<Category> findGoodsCategoryList(PageQueryUtil pageQueryUtil);
    
    Integer getTotalGoodsCategories(PageQueryUtil pageQueryUtil);
    
    Category selectByLevelAndName(@Param("categoryLevel") Byte categoryLevel,
                                  @Param("categoryName") String categoryName);
    
    int insertSelective(Category category);
    
    int deleteBatch(Integer[] ids);
    
    Category selectByPrimaryKey(Long categoryId);
    
    int updateByPrimaryKeySelective(Category category);
    
    List<Category> selectByLevelAndParentIdsAndNumber(@Param("parentIds") List<Long> parentIds, @Param("categoryLevel") int categoryLevel, @Param("number") int number);
}
