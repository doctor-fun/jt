package com.jt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class IndexController {


	//实现页面的通用跳转
	@RequestMapping("/page/{moduleName}")
	public String itemAdd(@PathVariable String moduleName){
		return moduleName;
	}
//
//
//
	@GetMapping("/user")
	public String getUser(){
		return null;
	}
	@PostMapping("/user")
	public String postUser(){
		return null;
	}

}
