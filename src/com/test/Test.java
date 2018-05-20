package com.test;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import com.newwing.entity.BetBO;

public class Test {

	public static WebDriver driver;
	
	public static void main(String[] args) {
//		testLogin();// 自动登陆
//		testTry();// 免费试玩
//		testSelect();// 自动投注
//		testResult();
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		System.out.println(sf.format(c.getTime()));
	}
	
	public static void testLogin() {
		try {
			String url = "http://190376.com/loginPage.do";
			driver = new FirefoxDriver();
			driver.manage().window().maximize();
			driver.get(url);
			WebElement txt_U_name = driver.findElement(By.id("username_login"));// 用户名
			WebElement txt_U_Password = driver.findElement(By.id("passwd_login"));// 密码
			WebElement txt_validate = driver.findElement(By.id("logVerifyCode"));// 验证码框
			WebElement longinbtn = driver.findElement(By.id("btn-sub"));// 登录按钮
			
			txt_validate.click();
			// 获取验证码文字
			while (true) {
				File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				String code = "";
				WebElement imgCheckNum = driver.findElement(By.id("logVerifyImg"));// 验证码图片
		        try {
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
//		                if (code == null || "".equals(code.trim())
//		                		|| code.length() != 4 || !code.trim().matches("^[0-9]*$")) {
//		                	imgCheckNum.click();
//		                	Thread.sleep(500);
//		                	continue;
//		                }
		            } else {
		                System.out.print("yzm.txt不存在");
		                continue;
		            }
		            
		            txt_U_name.sendKeys("88999");
					txt_U_Password.sendKeys("88999");
					txt_validate.sendKeys(code);
					longinbtn.click();
		        } catch(UnhandledAlertException e) {
		        	e.printStackTrace();
		        	continue;
		        }
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void testTry() {
		try {
			driver = new FirefoxDriver();
			String url = "http://190376.com/lotteryV3/registerTestGuest.do";
			driver.get(url);
			driver.manage().window().maximize();
			Thread.sleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void testSelect() {
		driver = new FirefoxDriver();
		while (true) {
			try {
				String url = "http://190376.com/lotteryV3/lotDetail.do?lotCode=BJSC";
				driver.get(url);
				Thread.sleep(3000);
				
				WebElement current_issue = driver.findElement(By.id("current_issue"));// 期号 
				String batchNo = current_issue.getText();// TODO 需要存储到数据库 需要根据数据库得值进行判断和过滤
				WebElement last_qihao = driver.findElement(By.id("last_qihao"));// 上期期号 
				String lastBatchNo = last_qihao.getText();
				WebElement last_result_0 = driver.findElement(By.id("last_result_0"));// 冠军
				String winNo = last_result_0.getAttribute("class");
				winNo = winNo.substring(winNo.length() - 2, winNo.length());
				System.out.println("batchNo ===> " + batchNo);
				System.out.println("lastBatchNo ===> " + lastBatchNo);
				System.out.println("winNo ===> " + winNo);

				// TODO 根据期号获取数据库的值
				
				List<WebElement> divList = driver.findElements(By.id("dxdsq"));
				WebElement a3 = divList.get(0).findElement(By.name("odd"));// 单
//				WebElement a4 = divList.get(0).findElement(By.name("even"));;// 双
				a3.click();// 选中单 TODO 根据策略选择单或者双
//				a4.click();// 选中双
				Thread.sleep(1000);// 停顿1秒
		        	
//				String beishu = this.getBeishu();// 获取当前的投入倍数 TODO
				String beishu = "1";
				WebElement beiShu = driver.findElement(By.id("beiShu"));// 倍数
				beiShu.clear();
				beiShu.sendKeys(beishu);
		        	
				Thread.sleep(2000);// 停顿1秒
				
				WebElement tz_box_but = driver.findElement(By.className("tz_box_but"));
				tz_box_but.click();
				WebElement add_to_list_zs_standard = tz_box_but.findElement(By.id("add_to_list_zs_standard"));// 添加选号
				System.out.println("add_to_list_zs_standard ===> " + add_to_list_zs_standard.getText());
				Thread.sleep(1500);
				add_to_list_zs_standard.click();// 进行投注
				Thread.sleep(1500);
				
				WebElement submit_lottery = driver.findElement(By.id("submit_lottery"));// 立即投注
				submit_lottery.click();// 进行投注
				
//				WebElement confirmButton = driver.findElement(By.className("layui-layer2"));// 确认按钮
//				confirmButton.click();// 最终投注
				
//				BetBO betBO = new BetBO();
//				betBO.setBatchNo(new Integer(current_issue.getText()));
//				betBO.setBetAmt(betAmt);
//				betBO.setBetContent(betContent);
//				betBO.setBetDate(new Date());
//				betBO.setLotteryId(lotteryId);
//				betBO.setMultiple(multiple);
//				betBO.setStatus("未开奖");
//				betBO.setWinAmt(winAmt);
//				betBO.setWinContent(winContent);
//				betBO.setWinNo(winNo);
				Thread.sleep(5*60*1000);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			} finally {
//				driver.close();
//				driver.quit();
			}
		}
		
	}
	
	// 采集历史记录
	public static void testResult() {
		WebDriver driver = null;
		try {
			System.setProperty("phantomjs.binary.path", "D:/phantomjs.exe");
			driver = new PhantomJSDriver();
			String url = "http://190376.com/lotteryV3/draw/hisresult.do?lotCode=BJSC&startTime=2018-04-30";
			driver.get(url);
			WebElement table = driver.findElement(By.id("draw_list"));
			List<WebElement> trList = table.findElements(By.tagName("tr"));
			for (int i = 1; i < trList.size(); i++) {
				WebElement tr1 = trList.get(i);// 最近一期开奖结果
				List<WebElement> tdList = tr1.findElements(By.tagName("td"));
				String openDate = tdList.get(0).getText();// 开奖日期 
				String openTime = tdList.get(1).getText();// 开奖时间
				String batchNo = tdList.get(2).getText();// 期号
				WebElement result = tdList.get(3).findElement(By.className("result"));// 结果
				WebElement span = result.findElement(By.tagName("span"));
				String spanResult = span.getAttribute("class");
//				System.out.println(spanResult);
				String finalResult = spanResult.substring(spanResult.length() - 2, spanResult.length());// 最终结果
//				System.out.println(openDate + "|" + openTime + "|" + batchNo + "|" + finalResult);
				System.out.println(finalResult);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			driver.close();
			driver.quit();
		}
	}
	
}
