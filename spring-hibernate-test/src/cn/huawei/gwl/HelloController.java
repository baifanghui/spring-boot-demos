package cn.huawei.gwl;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@RequestMapping("/say")
	String home() {
		System.out.println("get into");
		return "hello world";
	}

	@RequestMapping("/hello/{name}")
	String hello(@PathVariable String name) {
		return "hello" + name;
	}
}