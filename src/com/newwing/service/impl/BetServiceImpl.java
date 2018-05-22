package com.newwing.service.impl;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.springframework.stereotype.Service;

import com.newwing.entity.BetBO;
import com.newwing.entity.LotteryBO;
import com.newwing.entity.PlatformBO;
import com.newwing.service.IBetService;
import com.newwing.service.ILotteryService;
import com.newwing.service.IPlatformService;
import com.newwing.util.CommonConstant;
import com.newwing.util.DateUtil;
import com.newwing.util.ExcelUtil;
import com.newwing.util.Logger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@SuppressWarnings("all")
@Service("betService")
public class BetServiceImpl extends BaseServiceImpl implements IBetService {
	
	protected Logger logger = Logger.getLogger(this.getClass());
	
	@Resource(name = "betService")
	private IBetService betService;
	
	@Resource(name = "lotteryService")
	private ILotteryService lotteryService;
	
	@Resource(name = "platformService")
	private IPlatformService platformService;
	
	public void login(HttpServletRequest request, WebDriver driver) throws Exception {
		try {
			String hql = "FROM PlatformBO WHERE platform = '" + request.getParameter("platform") + "'";
			List<PlatformBO> platformBOList = this.platformService.find(hql);
			String url = platformBOList.get(0).getLoginUrl();
			driver.get(url);
			
			WebElement txt_U_name = driver.findElement(By.id("username_login"));// 用户名
			WebElement txt_U_Password = driver.findElement(By.id("passwd_login"));// 密码
			WebElement txt_validate = driver.findElement(By.id("logVerifyCode"));// 验证码框
			WebElement longinbtn = driver.findElement(By.id("btn-sub"));// 登录按钮
			
			txt_validate.click();
			
			// 获取验证码文字
			while (true) {
				String checkCode = this.getCheckCode(driver);
		            
				txt_U_name.clear();
				txt_U_Password.clear();
				txt_validate.clear();
				
				txt_U_name.sendKeys("hyf123");
				txt_U_Password.sendKeys("xctklt881008");
				txt_validate.sendKeys(checkCode);
				longinbtn.click();
				Thread.sleep(1000);
				System.out.println(driver.getCurrentUrl());
				
				if (url.equals(driver.getCurrentUrl())) {// 停留在登陆页
					continue;
				}
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取验证码
	 * @param driver
	 * @return
	 * @throws Exception
	 */
	private String getCheckCode(WebDriver driver) throws Exception {
		WebElement yzm_img_div = driver.findElement(By.name("yzm_img_div")).findElement(By.tagName("a"));// 刷新验证码
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		String code = "";
		WebElement imgCheckNum = driver.findElement(By.id("logVerifyImg"));// 验证码图片
		Point p = imgCheckNum.getLocation();
		int width = imgCheckNum.getSize().getWidth();
		int higth = imgCheckNum.getSize().getHeight();
		Rectangle rect = new Rectangle(width, higth);
		BufferedImage img = ImageIO.read(scrFile);
		BufferedImage dest = img.getSubimage(p.getX(), p.getY(), width, higth);
		ImageIO.write(dest, "png", scrFile);
		Thread.sleep(1000);
		File fng = new File("D:/yzm.png");
		if(fng.exists()){
			fng.delete();
		}
		FileUtils.copyFile(scrFile, fng);
            
		Runtime rt = Runtime.getRuntime();
		rt.exec("cmd.exe /C tesseract.exe D:\\yzm.png  D:\\yzm -1 ");
		Thread.sleep(1000);
		File file = new File("D:\\yzm.txt");
		if(file.exists()) {
			FileHandler fh = new FileHandler();
			code = fh.readAsString(file).trim();
			System.out.println(code);
			if (code == null || "".equals(code.trim())
					|| code.length() != 4) {
				yzm_img_div.click();
				Thread.sleep(500);
				this.getCheckCode(driver);
			}
		} else {
			System.out.print("yzm.txt不存在");
			this.getCheckCode(driver);
		}
		return code;
	}

	public void bet(HttpServletRequest request, WebDriver driver) throws Exception {
		logger.info(">>>>>>>>>>>>>>>>>>>> 自动投注开始 <<<<<<<<<<<<<<<<<<<<");
		boolean flag = true;
		// 平台、彩种
		String platform = request.getParameter("platform");
		String lotCode = request.getParameter("lotCode");
		
		String status = (String) request.getSession().getAttribute("status");
		if (!"start".equals(status)) {
			flag = false;
		}
		
		String hql = "FROM LotteryBO WHERE platform = '" + request.getParameter("platform") + "' AND lotCode = '" + request.getParameter("lotCode") + "'";
		List<LotteryBO> lotteryBOList = this.lotteryService.find(hql);
		// 投注地址、变化是否继续、连续是否继续
		String betPath = lotteryBOList.get(0).getBetPath();
		String isChange = lotteryBOList.get(0).getIsChange();
		String isContinuous = lotteryBOList.get(0).getIsContinuous();
		// 初始投注倍数、初始投注底金、投注轮次、投注周期、开奖刷新时间、变化期数、连续期数
		int defaultMultiple = lotteryBOList.get(0).getMultiple();
		double defaultBetamt = lotteryBOList.get(0).getBetamt();
		int betSort = lotteryBOList.get(0).getBetSort();
		int betTime = lotteryBOList.get(0).getBetTime();
		int refreshTime = lotteryBOList.get(0).getRefreshTime();
		int changeNper = lotteryBOList.get(0).getChangeNper();
		int continuousNper = lotteryBOList.get(0).getContinuousNper();
		
		driver.get(betPath);
		Thread.sleep(2000);
		
		// 选择前一
//		WebElement qy = driver.findElement(By.id("tznavhd")).findElements(By.tagName("a")).get(2);
//		qy.click();
//		Thread.sleep(1000);
		
		// 当前余额
		WebElement header_user_money = driver.findElement(By.id("header_user_money"));
		String amtStr = header_user_money.getText();
		int sort = 0;
		int lastMultiple = 0;
		double lastBetAmt = 0;
		while (flag) {
			try {
				long startTime = new Date().getTime();
				// 自动止盈
//				WebElement header_show_money = driver.findElement(By.id("header_show_money"));// 当前余额
//				header_show_money.click();
//				WebElement header_user_money = driver.findElement(By.id("header_user_money"));// 当前余额
//				double amtToday = new Double(header_user_money.getText());
//				LotteryBO lotteryBO = (LotteryBO)this.lotteryService.get(LotteryBO.class, 1);
//				double balance = amtToday - lotteryBO.getAmtYestoday();// 当日盈利
//				if (balance >= CommonConstant.DEFAULT_BETAMT * CommonConstant.DEFAULT_WIN_STOP_MULTIPLE) {
//					lotteryBO.setAmtYestoday(amtToday);
//					this.lotteryService.update(lotteryBO);
//					request.getSession().setAttribute("status", "停止");
//				}
//				String status = (String)request.getSession().getAttribute("status");
//				if (!"开始".equals(status)) {
//					flag = false;
//					break;
//				}
//				String url = "http://190376.com/lotteryV3/lotDetail.do?lotCode=FKSC";// 疯狂赛车
//				driver.get(url);
//				Thread.sleep(4000);
				
				if ("*****".equals(amtStr)) {
					// 当前余额
					WebElement header_show_money = driver.findElement(By.id("header_show_money"));
					header_show_money.click();
				}
				header_user_money = driver.findElement(By.id("header_user_money"));
				amtStr = header_user_money.getText();
				double amtToday = new Double(amtStr);
				
				// 当期期号
				WebElement current_issue = driver.findElement(By.id("current_issue")); 
				String batchNo = current_issue.getText();
				
				// 选择近一期开奖结果
				WebElement showgd_box = driver.findElement(By.id("showgd-box")).findElements(By.tagName("a")).get(0);
				showgd_box.click();
				Thread.sleep(1000);
				
				// 上期期号
				WebElement last_qihao = driver.findElement(By.id("last_qihao")); 
				String lastBatchNo = last_qihao.getText();
				
				// 判断上一期是否在开奖中
				boolean isKaijiang = true;
				while (isKaijiang) {
					// 开奖中
					WebElement readyOpen = driver.findElement(By.id("readyOpen"));
					String kaijiang = readyOpen.getAttribute("style");
					if ("".equals(kaijiang) || "display: inline;".equals(kaijiang)) {
						System.out.println("第" + lastBatchNo + "期 正在开奖...");
						Thread.sleep(refreshTime*1000);
					} else {
						isKaijiang = false;
					}
					continue;
				}
				
				// 获取上一期冠军中奖号码
				WebElement last_result_0 = driver.findElement(By.id("last_result_0"));
				String winNo = last_result_0.getAttribute("class");
				winNo = winNo.substring(winNo.length() - 2, winNo.length());
				System.out.println("第" + lastBatchNo + "期 开奖号码：" + winNo);
				
				// 上期中奖内容、中奖金额、投注倍数、投注金额 
				String lastWinContent = "";
				double lastWinAmt = 0;
				
				String lastStatus = "未中奖";
				double amt = 0.00;
				double balance = amtToday;
				DecimalFormat df = new DecimalFormat(".00");
				List<BetBO> betList = this.betService.find("FROM BetBO where batchNo = '" + lastBatchNo + "'");
				if (betList != null && betList.size() > 0) {
					BetBO lastBetBO = betList.get(0);
					
					lastWinContent = this.getOddOrEvenResult(winNo);
					// 中奖
					if (lastWinContent.equals(lastBetBO.getBetContent())) {
						lastWinAmt = lastBetBO.getBetAmt() * 1.98;
						lastStatus = "已中奖";
						amt = (Double.valueOf(df.format(lastWinAmt))-lastBetBO.getBetAmt());
						balance = lastBetBO.getBalance() + lastWinAmt;
						System.out.println("第" + lastBatchNo + "期 已中奖，盈利金额："+ amt);
					} else {
						lastWinAmt = 0;
						lastStatus = "未中奖";
						amt = lastWinAmt - lastBetBO.getBetAmt();
						balance = lastBetBO.getBalance();
						System.out.println("第" + lastBatchNo + "期 未中奖，亏损金额："+ amt);
					}
					lastBetBO.setWinContent(lastWinContent);
					lastBetBO.setWinNo(winNo);
					lastBetBO.setWinAmt(lastWinAmt);
					lastBetBO.setBalance(balance);
					lastBetBO.setStatus(lastStatus);
					lastBetAmt = lastBetBO.getBetAmt();
					lastMultiple = lastBetBO.getMultiple();
					sort = lastBetBO.getSort();
					this.betService.update(lastBetBO);
				} else {
					BetBO lastBetBO = new BetBO();
					lastBetBO.setLotCode(lotCode);
					lastBetBO.setBetDate(null);
					lastBetBO.setBetTime(null);
					lastBetBO.setBatchNo(lastBatchNo);
					lastBetBO.setMultiple(0);
					lastBetBO.setBetAmt(0.00);
					lastBetBO.setBetContent("/");
					lastBetBO.setWinNo(winNo);
					lastWinContent = this.getOddOrEvenResult(winNo);
					lastBetBO.setWinContent(lastWinContent);
					lastBetBO.setWinAmt(0.00);
					lastBetBO.setBalance(balance);
					lastBetBO.setStatus("/");
					lastBetBO.setSort(0);
					this.betService.save(lastBetBO);
				}
				
				Map map = new HashMap();
				map.put("defaultMultiple", defaultMultiple);
				map.put("defaultBetamt", defaultBetamt);
				map.put("lastBetAmt", lastBetAmt);
				map.put("lastMultiple", lastMultiple);
				map.put("betSort", betSort);
				map.put("lastWinContent", lastWinContent);
				map.put("lastStatus", lastStatus);
				map.put("lotCode", lotCode);
				map.put("balance", balance);
				map.put("sort", sort);
				map.put("isContinuous", isContinuous);
				map.put("isChange", isChange);
				map.put("batchNo", batchNo);
				
				// 获取近几期连续变化或连续不变
				String changeFlag = this.getChangeOrContinuous(changeNper, continuousNper, driver);
				
				String betContent = "";
				int multiple = 0;
				double betAmt = 0;
				// 出现连续不变或连续变化
				if (!"not".equals(changeFlag)) {
					// 开始投注
					this.startBet(changeFlag, map, driver);
				} else {
					System.out.println("第" + batchNo + "期 不满足投注条件，继续等待");
				}
				long endTime = new Date().getTime();
				logger.info(">>>>>>>>>>>>>>>>>>>> 自动投注结束 <<<<<<<<<<<<<<<<<<<<" );
				Thread.sleep(betTime*1000 - (endTime - startTime));
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
		driver.close();
		driver.quit();
	}
	
	/**
	 * 开始投注
	 * @param changeFlag 
	 * @param map
	 * @param driver
	 * @throws InterruptedException
	 */
	private void startBet(String changeFlag, Map map, WebDriver driver) throws InterruptedException {
		String betContent = "";
		String lotCode = map.get("lotCode").toString();
		String batchNo = map.get("batchNo").toString();
		
		int multiple = 0;
		double betAmt = 0;
		int lastMultiple = Integer.valueOf(map.get("lastMultiple").toString());
		double lastBetAmt = Double.valueOf(map.get("lastBetAmt").toString());
		double balance = Double.valueOf(map.get("balance").toString());
		String lastWinContent = map.get("lastWinContent").toString();
		String lastStatus = map.get("lastStatus").toString();
		int betSort = Integer.valueOf(map.get("betSort").toString());
		int sort = Integer.valueOf(map.get("sort").toString());
		String isChange = map.get("isChange").toString();
		String isContinuous = map.get("isContinuous").toString();
		
		// 选号
		betContent = this.selectNums(changeFlag, isChange, isContinuous, lastWinContent, driver);
		
		// 未中奖，翻倍投注
		if ("未中奖".equals(lastStatus) || "/".equals(lastStatus)) {
			if (lastBetAmt == 0 && sort == 0) {
				multiple = Integer.valueOf(map.get("defaultMultiple").toString());
				betAmt = Double.valueOf(map.get("defaultBetamt").toString());
			} else {
				System.out.println("第" + batchNo + "期 未中奖，倍投");
				multiple = lastMultiple * 2;
				betAmt = lastBetAmt * 2;
			}
			sort = sort + 1;
			if (sort == betSort) {
				sort = 1;
				multiple = Integer.valueOf(map.get("defaultMultiple").toString());
				betAmt = Double.valueOf(map.get("defaultBetamt").toString());
			}
		} else if ("已中奖".equals(lastStatus)) {
			sort = 1;
			multiple = Integer.valueOf(map.get("defaultMultiple").toString());
			betAmt = Double.valueOf(map.get("defaultBetamt").toString());
		}
		
		// 停顿1秒
		Thread.sleep(1000);
		
		// 输入投注倍数
		WebElement beiShu = driver.findElement(By.id("beiShu"));
		beiShu.clear();
		beiShu.sendKeys(String.valueOf(multiple));
		
		Thread.sleep(2000);
        	
		// 选号成功
		if (!"".equals(betContent)) {
			// 添加选号
			this.addNums(driver);
		}
//		else {
//			this.selectNums(changeFlag, isChange, isContinuous, lastWinContent, driver);
//			this.addNums(driver);
//		}
		
		// 投注：判断选号区有投注内容，则投注
		String selectContent = this.getSelectContent(batchNo, driver);
		// 添加选号成功
		if (!"".equals(selectContent)) {
			// 投注下单
			this.betBill(driver);
		}
//		else {// 添加选号失败，重新添加选号
//			this.addNums(driver);
//			this.betBill(driver);
//		}
		
		// 新增本期的数据
		BetBO betBO = new BetBO();
		betBO.setLotCode(lotCode);
		betBO.setBetDate(new Date());
		betBO.setBetTime(new Date());
		betBO.setBatchNo(batchNo);
		betBO.setMultiple(multiple);
		betBO.setBetAmt(betAmt);
		betBO.setBetContent(betContent);
		betBO.setWinNo("");
		betBO.setWinContent("");
		betBO.setWinAmt(0.00);
		betBO.setBalance(balance-betAmt);
		betBO.setStatus("未开奖");
		betBO.setSort(sort);
		this.betService.save(betBO);
		System.out.println("第" + batchNo + "期 成功投注倍数：" + multiple + "，投注金额：" + betAmt);
	}

	/**
	 * 获取添加选号的内容
	 * @param batchNo
	 * @param driver
	 * @return
	 */
	private String getSelectContent(String batchNo, WebDriver driver) {
		WebElement multiple_select = driver.findElement(By.id("multiple_select"));
		String bettingContent = multiple_select.getText();
		int contentLen = bettingContent.length();
		System.out.println("第" + batchNo + "期 投注内容："+bettingContent.substring(0, contentLen-3));
		return bettingContent;
	}

	/**
	 * 添加选号
	 * @param driver
	 * @throws InterruptedException
	 */
	private void addNums(WebDriver driver) throws InterruptedException {
		WebElement tz_box_but = driver.findElement(By.className("tz_box_but"));
		tz_box_but.click();
		WebElement add_to_list_zs_standard = driver.findElement(By.id("add_to_list_zs_standard"));
		Thread.sleep(1000);
		add_to_list_zs_standard.click();
		Thread.sleep(1000);
	}

	/**
	 * 投注下单
	 * @param driver
	 * @throws InterruptedException
	 */
	private void betBill(WebDriver driver) throws InterruptedException {
		// 立即投注
		WebElement submit_lottery = driver.findElement(By.id("submit_lottery"));
		submit_lottery.click();
		Thread.sleep(3000);
		
		// 确认投注
		WebElement confirmButton = driver.findElement(By.className("layui-layer-btn0"));
		confirmButton.click();
	}

	/**
	 * 选号
	 * @param changeFlag
	 * @param isChange
	 * @param isContinuous
	 * @param lastWinContent
	 * @param driver
	 * @return
	 */
	private String selectNums(String changeFlag, String isChange,
			String isContinuous, String lastWinContent, WebDriver driver) {
		String betContent = "";
		// 选号区
		List<WebElement> divList = driver.findElements(By.id("dxdsq"));
		WebElement odd = divList.get(0).findElement(By.name("odd"));// 单
		WebElement even = divList.get(0).findElement(By.name("even"));;// 双
				
		// 变化
		if ("change".equals(changeFlag)) {
			// 投连续
			if ("0".equals(isChange)) {
				if ("单".equals(lastWinContent)) {
					odd.click();// 选中单
					betContent = "单";
				} else if ("双".equals(lastWinContent)) {
					even.click();// 选中双
					betContent = "双";
				}
			} else {// 投变化
				if ("单".equals(lastWinContent)) {
					even.click();// 选中双
					betContent = "双";
				} else if ("双".equals(lastWinContent)) {
					odd.click();// 选中单
					betContent = "单";
				}
			}
		} else if ("continuous".equals(changeFlag)) {// 连续
			// 投变化
			if ("0".equals(isContinuous)) {
				if ("单".equals(lastWinContent)) {
					even.click();// 选中双
					betContent = "双";
				} else if ("双".equals(lastWinContent)) {
					odd.click();// 选中单
					betContent = "单";
				}
			} else {// 投连续
				if ("单".equals(lastWinContent)) {
					odd.click();// 选中单
					betContent = "单";
				} else if ("双".equals(lastWinContent)) {
					even.click();// 选中双
					betContent = "双";
				}
			}
		}
		
		return betContent;
	}

	/**
	 * 获取近5期开奖中奖号码连续或变化结果
	 * @param changeNper 
	 * @param continuousNper 
	 * @param driver
	 * @return
	 * @throws InterruptedException 
	 */
	private String getChangeOrContinuous(int changeNper, int continuousNper, WebDriver driver) throws InterruptedException {
		// 近几期未出现连续不变或连续变化
		String resultFlag = "not";
		
		// 选择近5期开奖结果
		WebElement showgd_box = driver.findElement(By.id("showgd-box")).findElements(By.tagName("a")).get(1);
		showgd_box.click();
		Thread.sleep(2000);
		
		// 获取近5期期号及开奖号码
		WebElement gd_box2 = driver.findElement(By.id("gd-box2"));
		List<WebElement> pList = gd_box2.findElements(By.tagName("p"));
		String batchNo1 = pList.get(0).findElements(By.className("gd-box-q")).get(0).getText();
		String winNo1 = pList.get(0).findElements(By.className("xyft-box-h")).get(0).getText();
		String batchNo2 = pList.get(1).findElements(By.className("gd-box-q")).get(0).getText();
		String winNo2 = pList.get(1).findElements(By.className("xyft-box-h")).get(0).getText();
		String batchNo3 = pList.get(2).findElements(By.className("gd-box-q")).get(0).getText();
		String winNo3 = pList.get(2).findElements(By.className("xyft-box-h")).get(0).getText();
		String batchNo4 = pList.get(3).findElements(By.className("gd-box-q")).get(0).getText();
		String winNo4 = pList.get(3).findElements(By.className("xyft-box-h")).get(0).getText();
		String batchNo5 = pList.get(4).findElements(By.className("gd-box-q")).get(0).getText();
		String winNo5 = pList.get(4).findElements(By.className("xyft-box-h")).get(0).getText();
		
		// 获取近5期中奖号码单双结果
		String winNo1Result = this.getOddOrEvenResult(winNo1);
		String winNo2Result = this.getOddOrEvenResult(winNo2);
		String winNo3Result = this.getOddOrEvenResult(winNo3);
		String winNo4Result = this.getOddOrEvenResult(winNo4);
		String winNo5Result = this.getOddOrEvenResult(winNo5);
		
		// 变化期数
		if (changeNper == 2) {
			if (!winNo1Result.equals(winNo2Result)) {
				resultFlag = "change";
			}
		} else if (changeNper == 3) {
			if (!winNo1Result.equals(winNo2Result) && !winNo2Result.equals(winNo3Result)) {
				resultFlag = "change";
			}
		} else if (changeNper == 4) {
			if (!winNo1Result.equals(winNo2Result) && !winNo2Result.equals(winNo3Result) && !winNo3Result.equals(winNo4Result)) {
				resultFlag = "change";
			}
			
		} else if (changeNper == 5) {
			if (!winNo1Result.equals(winNo2Result) && !winNo2Result.equals(winNo3Result) && !winNo3Result.equals(winNo4Result) && !winNo4Result.equals(winNo5Result)) {
				resultFlag = "change";
			}
		}
		
		// 连续期数
		if (continuousNper == 2) {
			if (winNo1Result.equals(winNo2Result)) {
				resultFlag = "continuous";
			}
		} else if (continuousNper == 3) {
			if (winNo1Result.equals(winNo2Result) && winNo2Result.equals(winNo3Result)) {
				resultFlag = "continuous";
			}
		} else if (continuousNper == 4) {
			if (winNo1Result.equals(winNo2Result) && winNo2Result.equals(winNo3Result) && winNo3Result.equals(winNo4Result)) {
				resultFlag = "continuous";
			}
			
		} else if (continuousNper == 5) {
			if (winNo1Result.equals(winNo2Result) && winNo2Result.equals(winNo3Result) && winNo3Result.equals(winNo4Result) && winNo4Result.equals(winNo5Result)) {
				resultFlag = "continuous";
			}
		}
		
		System.out.println("第" + batchNo5 + "期 开奖号码：" + winNo5 + "，为 " + winNo5Result);
		System.out.println("第" + batchNo4 + "期 开奖号码：" + winNo4 + "，为 " + winNo4Result);
		System.out.println("第" + batchNo3 + "期 开奖号码：" + winNo3 + "，为 " + winNo3Result);
		System.out.println("第" + batchNo2 + "期 开奖号码：" + winNo2 + "，为 " + winNo2Result);
		System.out.println("第" + batchNo1 + "期 开奖号码：" + winNo1 + "，为 " + winNo1Result);
		
		return resultFlag;
	}

	/**
	 * 获取中奖号码单双结果
	 * @param winNo
	 * @return
	 */
	private String getOddOrEvenResult(String winNo) {
		if ("01".equals(winNo) || "03".equals(winNo)  || "05".equals(winNo) 
				 || "07".equals(winNo)  || "09".equals(winNo) ) {
			return "单";
		} else {
			return "双";
		}
	}

	public void tryLogin(HttpServletRequest request, WebDriver driver) throws Exception {
		try {
			String hql = "FROM PlatformBO WHERE platform = '" + request.getParameter("platform") + "'";
			List<PlatformBO> platformList = this.platformService.find(hql);
			
			if (platformList != null && platformList.size() > 0) {
				PlatformBO platformBO = platformList.get(0);
				
				driver.get(platformBO.getTestUrl());
				driver.manage().window().maximize();
				Thread.sleep(2000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}