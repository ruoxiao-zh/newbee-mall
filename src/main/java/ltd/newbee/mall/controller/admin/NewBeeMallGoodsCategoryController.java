package ltd.newbee.mall.controller.admin;

import ltd.newbee.mall.common.ServiceResultEnum;
import ltd.newbee.mall.entity.Category;
import ltd.newbee.mall.service.NewBeeMallCategoryService;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.Result;
import ltd.newbee.mall.util.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

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
}
