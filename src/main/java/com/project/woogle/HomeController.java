package com.project.woogle;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.model.User;
import com.project.model.Woogle;
import com.project.service.WoogleService;


@RestController
@RequestMapping("/user")
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private WoogleService woogleService;
	
	
	@GetMapping("/woogle")
	   public ResponseEntity<Set<Woogle>> getAllWoogles(){
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
			Set<Woogle> woogles = user.getWoogles();
			 logger.debug(user.toString());
	        return new ResponseEntity<Set<Woogle>>(woogles, HttpStatus.OK);
	   }
	
	@PostMapping("/woogle")
		public ResponseEntity<Set<Woogle>> saveWoogle(@RequestBody Woogle woogle){
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		return new ResponseEntity<Set<Woogle>>(woogleService.saveWoogle(woogle, user), HttpStatus.OK);
	}
	
	@DeleteMapping("/woogle")
		public ResponseEntity<Set<Woogle>> deleteWoogle(@RequestBody Woogle woogle){
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		return new ResponseEntity<Set<Woogle>>(woogleService.deleteWoogle(woogle, user), HttpStatus.OK);
	}
}
