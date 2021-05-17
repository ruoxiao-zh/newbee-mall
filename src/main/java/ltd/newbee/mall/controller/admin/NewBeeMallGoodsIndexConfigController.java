package ltd.newbee.mall.controller.admin;

import ltd.newbee.mall.common.IndexConfigTypeEnum;
import ltd.newbee.mall.common.ServiceResultEnum;
import ltd.newbee.mall.entity.IndexConfig;
import ltd.newbee.mall.service.NewBeeMallIndexConfigService;
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
 * @author Richard
 * @date 2021/5/17 11:36 AM
 */
@RequestMapping(value = "/admin")
@Controller
public class NewBeeMallGoodsIndexConfigController {
    
    @Resource
    NewBeeMallIndexConfigService newBeeMallIndexConfigService;
    
    @GetMapping("/indexConfigs")
    public String index(HttpServletRequest request, @RequestParam("configType") int configType) {
        IndexConfigTypeEnum indexConfigTypeEnum = IndexConfigTypeEnum.getIndexConfigTypeEnumByType(configType);
        if (indexConfigTypeEnum.equals(IndexConfigTypeEnum.DEFAULT)) {
            return "error/error_5xx";
        }
        
        request.setAttribute("path", indexConfigTypeEnum.getName());
        request.setAttribute("configType", configType);
        
        return "admin/newbee_mall_index_config";
    }
    
    @RequestMapping(value = "/indexConfigs/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty((String) params.get("page")) || StringUtils.isEmpty((String) params.get("limit"))) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        
        return ResultGenerator.genSuccessResult(newBeeMallIndexConfigService.getConfigsPage(pageUtil));
    }
    
    @RequestMapping(value = "/indexConfigs/save", method = RequestMethod.POST)
    @ResponseBody
    public Result save(@RequestBody IndexConfig indexConfig) {
        if (Objects.isNull(indexConfig.getConfigType())
                || org.springframework.util.StringUtils.isEmpty(indexConfig.getConfigName())
                || Objects.isNull(indexConfig.getConfigRank())) {
            
            return ResultGenerator.genFailResult("参数异常！");
        }
        
        String result = newBeeMallIndexConfigService.saveIndexConfig(indexConfig);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        }
        
        return ResultGenerator.genFailResult(result);
    }
    
    @RequestMapping(value = "/indexConfigs/update", method = RequestMethod.POST)
    @ResponseBody
    public Result update(@RequestBody IndexConfig indexConfig) {
        if (Objects.isNull(indexConfig.getConfigType())
                || Objects.isNull(indexConfig.getConfigId())
                || org.springframework.util.StringUtils.isEmpty(indexConfig.getConfigName())
                || Objects.isNull(indexConfig.getConfigRank())) {
            
            return ResultGenerator.genFailResult("参数异常！");
        }
        
        String result = newBeeMallIndexConfigService.updateIndexConfig(indexConfig);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        }
        
        return ResultGenerator.genFailResult(result);
    }
    
    @GetMapping("/indexConfigs/info/{id}")
    @ResponseBody
    public Result info(@PathVariable("id") Long id) {
        IndexConfig config = newBeeMallIndexConfigService.getIndexConfigById(id);
        if (config == null) {
            return ResultGenerator.genFailResult("未查询到数据");
        }
        
        return ResultGenerator.genSuccessResult(config);
    }
    
    @RequestMapping(value = "/indexConfigs/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result delete(@RequestBody Long[] ids) {
        if (ids.length < 1) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        
        if (newBeeMallIndexConfigService.deleteBatch(ids)) {
            return ResultGenerator.genSuccessResult();
        }
        
        return ResultGenerator.genFailResult("删除失败");
    }
}
