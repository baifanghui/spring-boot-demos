package cn.tongdun.gwl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.karent.entity.User;
import cn.karent.repository.UserRepository;

@Controller
@RequestMapping("/hibernate")
@EnableAutoConfiguration
public class HibernateController {

	@Autowired
	private UserRepository userRepository;

	@RequestMapping("getUserById")
	@ResponseBody
	public User getUserById(Long id) {
		User u = userRepository.findOne(id);
		return u;
	}

	@RequestMapping("saveUser")
	@ResponseBody
	public void saveUser() {
		User u = new User();
		u.setUserName("wan");
		u.setAddress("浙江省杭州市滨江区");
		u.setBirthDay(new Date());
		u.setSex("男");
		userRepository.save(u);
	}
}