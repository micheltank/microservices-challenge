package br.com.micheltank.challenge.entities;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

public class LastPurchaseEntity {

	@Id
	private String id;
	private LocalDateTime date;
	private Double value;
	private String description;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
