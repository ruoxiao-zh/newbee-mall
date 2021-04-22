package ltd.newbee.mall.controller;

import ltd.newbee.mall.service.TestUserService;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.Result;
import ltd.newbee.mall.util.ResultGenerator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author Richard
 * @Date 2021/4/22 6:38 PM
 */
@RestController
@RequestMapping("/users")
public class TestUserController {
    
    @Resource
    TestUserService testUserService;
    
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result list(@RequestParam Map<String, Object> requestParam) {
        if (StringUtils.isEmpty((String) requestParam.get("page")) ||
                StringUtils.isEmpty((String) requestParam.get("limit"))) {
            return ResultGenerator.genFailResult("参数异常");
        }
        
        //查询列表数据
        PageQueryUtil pageUtil = new PageQueryUtil(requestParam);
        
        return ResultGenerator.genSuccessResult(testUserService.getUserPage(pageUtil));
    }
}
