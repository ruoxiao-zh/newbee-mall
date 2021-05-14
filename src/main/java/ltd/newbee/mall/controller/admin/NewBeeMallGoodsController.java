package ltd.newbee.mall.controller.admin;

import ltd.newbee.mall.common.Constants;
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
    
    @GetMapping("/goods/edit/{goodsId}")
    public String edit(HttpServletRequest request, @PathVariable("goodsId") Long goodsId) {
        request.setAttribute("path", "edit");
        
        Goods goods = newBeeMallGoodsService.getGoodsById(goodsId);
        if (goods == null) {
            return "error/error_400";
        }
        
        if (goods.getGoodsCategoryId() > 0) {
            if (goods.getGoodsCategoryId() != null || goods.getGoodsCategoryId() > 0) {
                // 有分类字段则查询相关分类数据返回给前端以供分类的三级联动显示
                Category category = newBeeMallCategoryService.getCategoryById(goods.getGoodsCategoryId());
                // 商品表中存储的分类id字段为三级分类的id，不为三级分类则是错误数据
                if (category != null && category.getCategoryLevel() == NewBeeMallCategoryLevelEnum.LEVEL_THREE.getLevel()) {
                    // 查询所有的一级分类
                    List<Category> firstLevelCategories = newBeeMallCategoryService.selectByLevelAndParentIdsAndNumber(
                            Collections.singletonList(0L),
                            NewBeeMallCategoryLevelEnum.LEVEL_ONE.getLevel());
                    // 根据parentId查询当前parentId下所有的三级分类
                    List<Category> thirdLevelCategories = newBeeMallCategoryService.selectByLevelAndParentIdsAndNumber(
                            Collections.singletonList(category.getParentId()),
                            NewBeeMallCategoryLevelEnum.LEVEL_THREE.getLevel());
                    // 查询当前三级分类的父级二级分类
                    Category secondCategory = newBeeMallCategoryService.getCategoryById(category.getParentId());
                    if (secondCategory != null) {
                        // 根据parentId查询当前parentId下所有的二级分类
                        List<Category> secondLevelCategories = newBeeMallCategoryService.selectByLevelAndParentIdsAndNumber(
                                Collections.singletonList(secondCategory.getParentId()),
                                NewBeeMallCategoryLevelEnum.LEVEL_TWO.getLevel());
                        // 查询当前二级分类的父级一级分类
                        Category firestCategory = newBeeMallCategoryService.getCategoryById(secondCategory.getParentId());
                        if (firestCategory != null) {
                            //所有分类数据都得到之后放到request对象中供前端读取
                            request.setAttribute("firstLevelCategories", firstLevelCategories);
                            request.setAttribute("secondLevelCategories", secondLevelCategories);
                            request.setAttribute("thirdLevelCategories", thirdLevelCategories);
                            request.setAttribute("firstLevelCategoryId", firestCategory.getCategoryId());
                            request.setAttribute("secondLevelCategoryId", secondCategory.getCategoryId());
                            request.setAttribute("thirdLevelCategoryId", category.getCategoryId());
                        }
                    }
                }
            }
        }
        
        if (goods.getGoodsCategoryId() == 0) {
            //查询所有的一级分类
            List<Category> firstLevelCategories = newBeeMallCategoryService.selectByLevelAndParentIdsAndNumber(
                    Collections.singletonList(0L),
                    NewBeeMallCategoryLevelEnum.LEVEL_ONE.getLevel());
            if (!CollectionUtils.isEmpty(firstLevelCategories)) {
                //查询一级分类列表中第一个实体的所有二级分类
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
                }
            }
        }
        
        request.setAttribute("goods", goods);
        request.setAttribute("path", "goods-edit");
        
        return "admin/newbee_mall_goods_edit";
    }
    
    @RequestMapping(value = "/goods/update", method = RequestMethod.POST)
    @ResponseBody
    public Result update(@RequestBody Goods goods) {
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
    
        String result = newBeeMallGoodsService.updateNewBeeMallGoods(goods);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        }
    
        return ResultGenerator.genFailResult(result);
    }
    
    @RequestMapping(value = "/goods/status/{sellStatus}", method = RequestMethod.PUT)
    @ResponseBody
    public Result updateStatus(@RequestBody Long[] ids, @PathVariable("sellStatus") int sellStatus) {
        if (ids.length < 1) {
            return ResultGenerator.genFailResult("参数异常！");
        }
    
        if (sellStatus != Constants.SELL_STATUS_UP && sellStatus != Constants.SELL_STATUS_DOWN) {
            return ResultGenerator.genFailResult("状态异常！");
        }
    
        if (newBeeMallGoodsService.updateSellStatus(ids, sellStatus)) {
            return ResultGenerator.genSuccessResult();
        }
    
        return ResultGenerator.genFailResult("删除失败");
    }
}
