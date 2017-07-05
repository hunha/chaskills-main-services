package com.realife.services.realife_services.controlleres;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersController {
	
	@RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }
}
