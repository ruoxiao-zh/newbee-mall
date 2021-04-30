package ltd.newbee.mall.controller.admin;

import ltd.newbee.mall.common.ServiceResultEnum;
import ltd.newbee.mall.entity.Carousel;
import ltd.newbee.mall.service.NewBeeMallCarouselService;
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
 * @Date 2021/4/23 3:31 PM
 */
@Controller
@RequestMapping(value = "/admin")
public class NewBeeMallCarouselController {
    
    @Resource
    NewBeeMallCarouselService newBeeMallCarouselService;
    
    @GetMapping("/carousels")
    public String carouselPage(HttpServletRequest request) {
        request.setAttribute("path", "newbee_mall_carousel");
        
        return "admin/newbee_mall_carousel";
    }
    
    /**
     * 列表
     */
    @GetMapping("/carousels/list")
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> requestParam) {
        if (StringUtils.isEmpty((String) requestParam.get("page")) ||
                StringUtils.isEmpty((String) requestParam.get("limit"))) {
            return ResultGenerator.genFailResult("参数异常");
        }
        
        PageQueryUtil pageUtil = new PageQueryUtil(requestParam);
        System.out.println(pageUtil);
        
        return ResultGenerator.genSuccessResult(newBeeMallCarouselService.getCarouselPage(pageUtil));
    }
    
    /**
     * 删除
     */
    @RequestMapping(value = "/carousels/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result delete(@RequestBody Integer[] ids) {
        if (ids.length < 1) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        
        if (newBeeMallCarouselService.deleteBatch(ids)) {
            return ResultGenerator.genSuccessResult();
        }
        
        return ResultGenerator.genFailResult("删除失败");
    }
    
    /**
     * 添加
     */
    @RequestMapping(value = "/carousels/save", method = RequestMethod.POST)
    @ResponseBody
    public Result save(@RequestBody Carousel carousel) {
        if (org.springframework.util.StringUtils.isEmpty(carousel.getCarouselUrl())
                || Objects.isNull(carousel.getCarouselRank())) {
            return ResultGenerator.genFailResult("参数异常");
        }
        
        String result = newBeeMallCarouselService.saveCarousel(carousel);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        }
        
        return ResultGenerator.genFailResult(result);
    }
    
    @RequestMapping(value = "carousels/info/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Result show(@PathVariable("id") Integer id) {
        Carousel carousel = newBeeMallCarouselService.getCarouselById(id);
        if (carousel == null) {
            return ResultGenerator.genFailResult(ServiceResultEnum.DATA_NOT_EXIST.getResult());
        }
        
        return ResultGenerator.genSuccessResult(carousel);
    }
    
    @RequestMapping(value = "/carousels/update", method = RequestMethod.POST)
    @ResponseBody
    public Result update(@RequestBody Carousel carousel) {
        if (org.springframework.util.StringUtils.isEmpty(carousel.getCarouselUrl())
                || Objects.isNull(carousel.getCarouselRank())) {
            return ResultGenerator.genFailResult("参数异常");
        }
    
        String result = newBeeMallCarouselService.updateCarousel(carousel);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        }
    
        return ResultGenerator.genFailResult(result);
    }
}
