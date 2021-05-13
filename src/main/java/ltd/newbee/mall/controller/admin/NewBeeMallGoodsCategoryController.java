package ltd.newbee.mall.controller.admin;

import ltd.newbee.mall.common.NewBeeMallCategoryLevelEnum;
import ltd.newbee.mall.common.ServiceResultEnum;
import ltd.newbee.mall.entity.Category;
import ltd.newbee.mall.service.NewBeeMallCategoryService;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.Result;
import ltd.newbee.mall.util.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @Author Richard
 * @Date 2021/4/30 4:08 PM
 */
@Controller
@RequestMapping(value = "/admin")
public class NewBeeMallGoodsCategoryController {
    
    @Resource
    NewBeeMallCategoryService newBeeMallCategoryService;
    
    @GetMapping("/categories")
    public String categoriesPage(HttpServletRequest request, @RequestParam("categoryLevel") Byte categoryLevel, @RequestParam("parentId") Long parentId, @RequestParam("backParentId") Long backParentId) {
        if (categoryLevel == null || categoryLevel < 1 || categoryLevel > 3) {
            return "error/error_5xx";
        }
        request.setAttribute("path", "newbee_mall_category");
        request.setAttribute("parentId", parentId);
        request.setAttribute("backParentId", backParentId);
        request.setAttribute("categoryLevel", categoryLevel);
        
        return "admin/newbee_mall_category";
    }
    
    @RequestMapping(value = "/categories/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty((String) params.get("page")) ||
                StringUtils.isEmpty((String) params.get("limit"))) {
            return ResultGenerator.genFailResult("参数异常!");
        }
        
        PageQueryUtil pageQueryUtil = new PageQueryUtil(params);
        
        return ResultGenerator.genSuccessResult(newBeeMallCategoryService.getCategorisPage(pageQueryUtil));
    }
    
    @RequestMapping(value = "/categories/save", method = RequestMethod.POST)
    @ResponseBody
    public Result save(@RequestBody Category category) {
        if (Objects.isNull(category.getCategoryLevel())
                || StringUtils.isEmpty(category.getCategoryName())
                || Objects.isNull(category.getParentId())
                || Objects.isNull(category.getCategoryRank())) {
            
            return ResultGenerator.genFailResult("参数异常!");
        }
    
        String result = newBeeMallCategoryService.saveCategory(category);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        }
    
        return ResultGenerator.genFailResult(result);
    }
    
    @RequestMapping(value = "/categories/update", method = RequestMethod.POST)
    @ResponseBody
    public Result update(@RequestBody Category category) {
        if (Objects.isNull(category.getCategoryLevel())
                || StringUtils.isEmpty(category.getCategoryName())
                || Objects.isNull(category.getParentId())
                || Objects.isNull(category.getCategoryRank())) {
        
            return ResultGenerator.genFailResult("参数异常!");
        }
    
        String result = newBeeMallCategoryService.updateCategory(category);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        }
    
        return ResultGenerator.genFailResult(result);
    }
    
    @RequestMapping(value = "/categories/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result delete(@RequestBody Integer[] ids) {
        if (ids.length < 1) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        
        if (newBeeMallCategoryService.deleteBatch(ids)) {
            return ResultGenerator.genSuccessResult();
        }
        
        return ResultGenerator.genFailResult("删除失败");
    }
    
    /**
     * 列表
     */
    @RequestMapping(value = "/categories/listForSelect", method = RequestMethod.GET)
    @ResponseBody
    public Result listForSelect(@RequestParam("categoryId") Long categoryId) {
        System.out.println(categoryId);
        if (categoryId == null || categoryId < 1) {
            return ResultGenerator.genFailResult("缺少参数！");
        }
        Category category = newBeeMallCategoryService.getCategoryById(categoryId);
        // 既不是一级分类也不是二级分类则为不返回数据
        if (category == null || category.getCategoryLevel() == NewBeeMallCategoryLevelEnum.LEVEL_THREE.getLevel()) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        Map categoryResult = new HashMap(4);
        if (category.getCategoryLevel() == NewBeeMallCategoryLevelEnum.LEVEL_ONE.getLevel()) {
            //如果是一级分类则返回当前一级分类下的所有二级分类，以及二级分类列表中第一条数据下的所有三级分类列表
            //查询一级分类列表中第一个实体的所有二级分类
            List<Category> secondLevelCategories = newBeeMallCategoryService.selectByLevelAndParentIdsAndNumber(
                    Collections.singletonList(categoryId),
                    NewBeeMallCategoryLevelEnum.LEVEL_TWO.getLevel());
            if (!CollectionUtils.isEmpty(secondLevelCategories)) {
                //查询二级分类列表中第一个实体的所有三级分类
                List<Category> thirdLevelCategories = newBeeMallCategoryService.selectByLevelAndParentIdsAndNumber(
                        Collections.singletonList(secondLevelCategories.get(0).getCategoryId()),
                        NewBeeMallCategoryLevelEnum.LEVEL_THREE.getLevel());
                
                categoryResult.put("secondLevelCategories", secondLevelCategories);
                categoryResult.put("thirdLevelCategories", thirdLevelCategories);
            }
        }
        if (category.getCategoryLevel() == NewBeeMallCategoryLevelEnum.LEVEL_TWO.getLevel()) {
            //如果是二级分类则返回当前分类下的所有三级分类列表
            List<Category> thirdLevelCategories = newBeeMallCategoryService.selectByLevelAndParentIdsAndNumber(
                    Collections.singletonList(categoryId),
                    NewBeeMallCategoryLevelEnum.LEVEL_THREE.getLevel());
            
            categoryResult.put("thirdLevelCategories", thirdLevelCategories);
        }
        
        return ResultGenerator.genSuccessResult(categoryResult);
    }
}
