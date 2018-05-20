package com.newwing.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.newwing.entity.BetBO;
import com.newwing.service.IBetService;
import com.newwing.service.ILotteryService;
import com.newwing.util.Logger;

@Controller
@RequestMapping(value = "/")
@SuppressWarnings("unchecked")
public class BetController {
	
	protected Logger logger = Logger.getLogger(this.getClass());
	
	@Resource(name = "betService")
	private IBetService betService;
	
	@Resource(name = "lotteryService")
	private ILotteryService lotteryService;
	
	@RequestMapping("index.do")
	public String index(Model model) throws Exception {
		return "index";
	}

	@RequestMapping("queryBetList")
	public String queryBetList(HttpServletRequest request, Model model) throws Exception {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		String hql = "FROM BetBO where betDate = '" + sf.format(c.getTime()) + "' order by id desc";
		List<BetBO> betList = this.betService.find(hql);
		String status = (String) request.getSession().getAttribute("status");
		if (status == null || "".equals(status)) {
			status = "stop";// 默认停止
		}
		
		model.addAttribute("betList", betList);
		model.addAttribute("status", status);
		return "admin/bet/betList";
	}
	
	@RequestMapping("bet")
	public String bet(HttpServletRequest request, Model model) throws Exception {
		request.getSession().setAttribute("status", "start");
		
//		LotteryBO lotteryBO = (LotteryBO)this.lotteryService.get(LotteryBO.class, 1);// TODO 
//		lotteryBO.setStatus("开始");
//		this.lotteryService.update(lotteryBO);
		
		WebDriver driver = new FirefoxDriver();
		driver.manage().window().maximize();
//		this.betService.login(request, driver);
		this.betService.tryLogin(request, driver);
		this.betService.bet(request, driver);
		return "redirect:/queryBetList.do";
	}
	
	@RequestMapping("stop")
	public String stop(HttpServletRequest request, Model model) throws Exception {
		request.getSession().setAttribute("status", "stop");// 启停标识
		
//		LotteryBO lotteryBO = (LotteryBO)this.lotteryService.get(LotteryBO.class, 1);// TODO 
//		lotteryBO.setStatus("停止");
//		this.lotteryService.update(lotteryBO);
		return "redirect:/queryBetList.do";
	}

}
