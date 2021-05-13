package ltd.newbee.mall.controller.admin;

import ltd.newbee.mall.common.NewBeeMallCategoryLevelEnum;
import ltd.newbee.mall.common.ServiceResultEnum;
import ltd.newbee.mall.entity.Category;
import ltd.newbee.mall.entity.Goods;
import ltd.newbee.mall.service.NewBeeMallCategoryService;
import ltd.newbee.mall.service.NewBeeMallGoodsService;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.Result;
import ltd.newbee.mall.util.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Author Richard
 * @Date 2021/5/11 4:24 PM
 */
@Controller
@RequestMapping(value = "/admin")
public class NewBeeMallGoodsController {
    
    @Resource
    private NewBeeMallGoodsService newBeeMallGoodsService;
    
    @Resource
    private NewBeeMallCategoryService newBeeMallCategoryService;
    
    @GetMapping("/goods")
    public String goodsPage(HttpServletRequest request) {
        request.setAttribute("path", "newbee_mall_goods");
        
        return "admin/newbee_mall_goods";
    }
    
    @GetMapping("/goods/list")
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty((String) params.get("page")) ||
                StringUtils.isEmpty((String) params.get("limit"))) {
            return ResultGenerator.genFailResult("参数异常!");
        }
        
        PageQueryUtil pageQueryUtil = new PageQueryUtil(params);
        
        return ResultGenerator.genSuccessResult(newBeeMallGoodsService.getGoodsPage(pageQueryUtil));
    }
    
    @GetMapping("/goods/edit")
    public String eidt(HttpServletRequest request) {
        request.setAttribute("path", "edit");
        
        // 查询所有的一级分类
        List<Category> firstLevelCategories = newBeeMallCategoryService.selectByLevelAndParentIdsAndNumber(
                Collections.singletonList(0L),
                NewBeeMallCategoryLevelEnum.LEVEL_ONE.getLevel());
        if (!CollectionUtils.isEmpty(firstLevelCategories)) {
            List<Category> secondLevelCategories = newBeeMallCategoryService.selectByLevelAndParentIdsAndNumber(
                    Collections.singletonList(firstLevelCategories.get(0).getCategoryId()),
                    NewBeeMallCategoryLevelEnum.LEVEL_TWO.getLevel());
            
            if (!CollectionUtils.isEmpty(secondLevelCategories)) {
                //查询二级分类列表中第一个实体的所有三级分类
                List<Category> thirdLevelCategories = newBeeMallCategoryService.selectByLevelAndParentIdsAndNumber(
                        Collections.singletonList(secondLevelCategories.get(0).getCategoryId()),
                        NewBeeMallCategoryLevelEnum.LEVEL_THREE.getLevel());
                
                request.setAttribute("firstLevelCategories", firstLevelCategories);
                request.setAttribute("secondLevelCategories", secondLevelCategories);
                request.setAttribute("thirdLevelCategories", thirdLevelCategories);
                request.setAttribute("path", "goods-edit");
                
                return "admin/newbee_mall_goods_edit";
            }
        }
        
        
        return "error/error_5xx";
    }
    
    @RequestMapping(value = "/goods/save", method = RequestMethod.POST)
    @ResponseBody
    public Result save(@RequestBody Goods goods) {
        if (StringUtils.isEmpty(goods.getGoodsName())
                || StringUtils.isEmpty(goods.getGoodsIntro())
                || StringUtils.isEmpty(goods.getTag())
                || Objects.isNull(goods.getOriginalPrice())
                || Objects.isNull(goods.getGoodsCategoryId())
                || Objects.isNull(goods.getSellingPrice())
                || Objects.isNull(goods.getStockNum())
                || Objects.isNull(goods.getGoodsSellStatus())
                || StringUtils.isEmpty(goods.getGoodsCoverImg())
                || StringUtils.isEmpty(goods.getGoodsDetailContent())) {
            
            return ResultGenerator.genFailResult("参数异常！");
        }
        
        String result = newBeeMallGoodsService.saveNewBeeMallGoods(goods);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        }
        
        return ResultGenerator.genFailResult(result);
    }
}
