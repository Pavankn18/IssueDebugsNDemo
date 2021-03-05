package springdoc.swagger.swaggerdemo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import springdoc.swagger.swaggerdemo.model.User;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@GetMapping
	public User getUser() {
		return new User(20, "Pavan");
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public String saveUser() {
		return "user saved";
	}
	
}
