package br.com.micheltank.challenge.entities;

import org.springframework.data.annotation.Id;

public class FinancialMovementEntity {

	@Id
	private String id;
	private Double value;
	private String company;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	
}
