package com.project.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.project.model.User;
import com.project.model.Woogle;
import com.project.repo.WoogleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
public class WoogleService {
	
	private Set<Woogle> woogles = new HashSet<Woogle>();

    @Autowired
    private WoogleRepository woogleRepository;

    public Set<Woogle> saveWoogle(Woogle woogle, User user){
    	woogles = user.getWoogles();
    	woogle.setUserId(user.getId());
    	Woogle tmp = woogleRepository.save(woogle);
    	if(tmp != null) {
    		woogles.add(tmp);
    		return woogles;
    	}
    	else return null;
    }
    
    public Set<Woogle> deleteWoogle(Woogle woogle, User user) {
    		woogles = user.getWoogles();
    	 Iterator<Woogle> iter = woogles.iterator();
    	 while(iter.hasNext()) {
    		 Woogle tmp = iter.next();
    		 if(tmp.getId() == woogle.getId()) {
    			 woogleRepository.delete(woogle);
    			 iter.remove();
    		 }
    	 }
    	 return woogles;
    }
}