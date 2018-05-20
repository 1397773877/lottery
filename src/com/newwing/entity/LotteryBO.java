package com.newwing.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 接口表
 */
@Entity
@Table(name="t_lottery")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "t_lottery_sequence")
public class LotteryBO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int id;// 主键ID
	private String platform;// 平台
	private String lotCode;// 彩种
	private String name;// 名称
	private String unit;// 单位
	private int multiple;// 初始投注倍数
	private double betamt;// 初始投注底金
	private String betPath;// 投注地址
	private int betSort;// 投注轮次
	private int betTime;// 投注周期
	private int refreshTime;// 开奖刷新时间
	private double amtYestoday;// 昨日余额
	private int changeNper;// 变化期数
	private String isChange;// 变化是否继续
	private int continuousNper;// 连续期数
	private String isContinuous;// 连续是否继续
	private String status;// 状态
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "sequenceGenerator")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}
	
	public String getLotCode() {
		return lotCode;
	}

	public void setLotCode(String lotCode) {
		this.lotCode = lotCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public int getMultiple() {
		return multiple;
	}

	public void setMultiple(int multiple) {
		this.multiple = multiple;
	}
	
	public double getBetamt() {
		return betamt;
	}

	public void setBetamt(double betamt) {
		this.betamt = betamt;
	}
	
	public String getBetPath() {
		return betPath;
	}

	public void setBetPath(String betPath) {
		this.betPath = betPath;
	}
	
	public int getBetSort() {
		return betSort;
	}

	public void setBetSort(int betSort) {
		this.betSort = betSort;
	}
	
	public int getBetTime() {
		return betTime;
	}

	public void setBetTime(int betTime) {
		this.betTime = betTime;
	}
	
	public int getRefreshTime() {
		return refreshTime;
	}

	public void setRefreshTime(int refreshTime) {
		this.refreshTime = refreshTime;
	}
	
	public double getAmtYestoday() {
		return amtYestoday;
	}

	public void setAmtYestoday(double amtYestoday) {
		this.amtYestoday = amtYestoday;
	}
	
	public int getChangeNper() {
		return changeNper;
	}

	public void setChangeNper(int changeNper) {
		this.changeNper = changeNper;
	}
	
	public String getIsChange() {
		return isChange;
	}

	public void setIsChange(String isChange) {
		this.isChange = isChange;
	}
	
	public int getContinuousNper() {
		return continuousNper;
	}

	public void setContinuousNper(int continuousNper) {
		this.continuousNper = continuousNper;
	}
	
	public String getIsContinuous() {
		return isContinuous;
	}

	public void setIsContinuous(String isContinuous) {
		this.isContinuous = isContinuous;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}