package cn.huawei.gwl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "cn")
public class MyTest {

	public static void main(String[] args) {
		SpringApplication.run(MyTest.class, args);
	}
}