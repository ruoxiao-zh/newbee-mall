package ltd.newbee.mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @author richard
 */
@RestController
public class JdbcController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("/queryAll")
    public List<Map<String, Object>> queryAll() {
        return jdbcTemplate.queryForList("select * from jdbc_test");
    }

    @GetMapping("/insert")
    public Boolean insert(String type, String name) {
        if (StringUtils.isEmpty(type) || StringUtils.isEmpty(name)) {
            return false;
        }

        jdbcTemplate.execute("insert into jdbc_test(`type`,`name`) value (\"" + type + "\",\"" + name + "\")");

        return true;
    }
}
