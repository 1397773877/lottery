package com.newwing.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="t_bet")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "t_bet_sequence")
public class BetBO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int id;// 主键ID
	private String lotCode;// 彩种码
	private Date betDate;// 投注日期
	private Date betTime;// 投注时间
	private String batchNo;// 期号
	private int multiple;// 倍数
	private Double betAmt;// 投注金额
	private String betContent;// 投注内容
	private String winNo;// 中奖号
	private String winContent;// 中奖内容
	private Double winAmt;// 中奖金额
	private Double balance;// 中奖金额
	private String status;// 状态
	private int sort;// 轮次
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "sequenceGenerator")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLotCode() {
		return lotCode;
	}

	public void setLotCode(String lotCode) {
		this.lotCode = lotCode;
	}

	public Date getBetDate() {
		return betDate;
	}

	public void setBetDate(Date betDate) {
		this.betDate = betDate;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public int getMultiple() {
		return multiple;
	}

	public void setMultiple(int multiple) {
		this.multiple = multiple;
	}

	public Double getBetAmt() {
		return betAmt;
	}

	public void setBetAmt(Double betAmt) {
		this.betAmt = betAmt;
	}

	public String getBetContent() {
		return betContent;
	}

	public void setBetContent(String betContent) {
		this.betContent = betContent;
	}

	public String getWinNo() {
		return winNo;
	}

	public void setWinNo(String winNo) {
		this.winNo = winNo;
	}

	public String getWinContent() {
		return winContent;
	}

	public void setWinContent(String winContent) {
		this.winContent = winContent;
	}

	public Double getWinAmt() {
		return winAmt;
	}

	public void setWinAmt(Double winAmt) {
		this.winAmt = winAmt;
	}
	
	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getBetTime() {
		return betTime;
	}

	public void setBetTime(Date betTime) {
		this.betTime = betTime;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}
	
}