package com.spring.stock.service;

import java.util.Date;

public class Stocks {

	private Long stockCode;
	private Double stockPrice;
	private Date createdDate;
	private Long companyCode;
	
	public Long getStockCode() {
		return stockCode;
	}
	public void setStockCode(Long stockCode) {
		this.stockCode = stockCode;
	}
	public Double getStockPrice() {
		return stockPrice;
	}
	public void setStockPrice(Double stockPrice) {
		this.stockPrice = stockPrice;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Long getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(Long companyCode) {
		this.companyCode = companyCode;
	}
		 
}
