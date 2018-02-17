package com.example.demo;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}

@RestController
class RestController1 {

	public String fallback() {
		return "Not found. Error Occured";
	}
	@Autowired
	public DataService dataService;
	@RequestMapping(method = RequestMethod.GET, value = "/greet/{s}")
	public String greeting(@PathVariable String s) {
		return "Hello " +s ;
	}

	@HystrixCommand(fallbackMethod = "fallback")
	@RequestMapping(method = RequestMethod.GET , value="/set/{key}/{value}")
	public String setValue(@PathVariable String key , @PathVariable String value){
		return dataService.storeValue(key, value);
	}

	@HystrixCommand(fallbackMethod = "fallback")
	@RequestMapping(method = RequestMethod.GET , value = "/get/{key}")
	public String getValue(@PathVariable String key) {
		return dataService.getValue(key);
	}

	@HystrixCommand(fallbackMethod = "fallback")
	@RequestMapping(method = RequestMethod.GET , value = "count")
	public String getCount() {
		return dataService.getCount();
	}

	@HystrixCommand(fallbackMethod = "fallback")
	@RequestMapping(method = RequestMethod.GET , value="setmc/{key}/{value}")
	public Object setMCValue(@PathVariable String key , @PathVariable String value){
		return dataService.storeMCValue(key, value);
	}

	@HystrixCommand(fallbackMethod = "fallback")
	@RequestMapping(method = RequestMethod.GET , value = "/getmc/{key}")
	public Object getMCValue(@PathVariable String key) {
		return dataService.getMCValue(key);
	}

	@HystrixCommand(fallbackMethod = "fallback")
	@RequestMapping(method = RequestMethod.GET , value = "/countmc")
	public Object getMCCount() {
		return dataService.getMCCount();
	}

	@HystrixCommand(fallbackMethod = "fallback")
	@RequestMapping(method = RequestMethod.GET , value = "/fallbacktest")
	public String fallackTest() {
		int a = 5/0;
		return dataService.getCount();
	}
}
