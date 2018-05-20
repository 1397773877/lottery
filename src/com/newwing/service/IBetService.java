package com.newwing.service;

import javax.servlet.http.HttpServletRequest;

import org.openqa.selenium.WebDriver;

public interface IBetService extends IBaseService {
	
	public void login(HttpServletRequest request, WebDriver driver) throws Exception;
	
	public void bet(HttpServletRequest request, WebDriver driver) throws Exception;
	
	public void tryLogin(HttpServletRequest request, WebDriver driver) throws Exception;
	
}
