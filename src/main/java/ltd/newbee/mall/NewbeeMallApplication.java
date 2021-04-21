package ltd.newbee.mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Richard
 */
@SpringBootApplication
@MapperScan("ltd.newbee.mall.dao") //添加 @Mapper 注解
public class NewbeeMallApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewbeeMallApplication.class, args);
	}

}
