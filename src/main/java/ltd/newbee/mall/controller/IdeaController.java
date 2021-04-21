package ltd.newbee.mall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author richard
 */
@Controller
public class IdeaController {

    @GetMapping("/info")
    @ResponseBody
    public String getInfoFromIdea() {
        return "this is a spring boot project from idea";
    }
}
