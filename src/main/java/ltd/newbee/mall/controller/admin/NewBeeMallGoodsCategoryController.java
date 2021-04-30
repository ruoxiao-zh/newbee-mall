package ltd.newbee.mall.controller.admin;

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
}
