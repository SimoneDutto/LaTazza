package it.polito.latazza;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Sell {
	private Integer bevarageId;
	private Integer numberOfCapsules;
	private Date date;
	
	/* String date in format "dd/mm/yyyy" */
	public Sell(Integer beverageId, Integer numberOfCapsules, String date) throws ParseException {
		this.bevarageId = beverageId;
		this.numberOfCapsules = numberOfCapsules;
		this.date = new SimpleDateFormat("dd/MM/yyyy").parse(date);
	}
	
	public Integer getBevarageId() {
		return this.bevarageId;
	}
	
	public Integer getNumberOfCapsules() {
		return this.numberOfCapsules;
	}
	
	public String getDate() {
		return this.date.toString();
	}
}
