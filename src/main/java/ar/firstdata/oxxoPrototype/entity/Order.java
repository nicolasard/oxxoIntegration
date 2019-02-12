package ar.firstdata.oxxoPrototype.entity;

import java.awt.List;

public class Order {
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	String currency;
	
	public Integer getAmount() {
		return Amount;
	}

	public void setAmount(Integer amount) {
		Amount = amount;
	}

	Integer Amount;
	
	public LineItem[] getLine_items() {
		return line_items;
	}

	public void setLine_items(LineItem[] line_items) {
		this.line_items = line_items;
	}

	LineItem[] line_items;
	
	public Charge[] getCharges() {
		return charges;
	}

	public void setCharges(Charge[] charges) {
		this.charges = charges;
	}

	Charge[] charges;
	
	CustomerInfo customer_info;

	public CustomerInfo getCustomer_info() {
		return customer_info;
	}

	public void setCustomer_info(CustomerInfo customer_info) {
		this.customer_info = customer_info;
	}

}
