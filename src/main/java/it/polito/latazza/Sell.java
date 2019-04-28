package it.polito.latazza;

import it.polito.latazza.exceptions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Sell {
	private Integer beverageId;
	private Integer numberOfCapsules;
	private Date date;
	
	/* String date in format "dd/mm/yyyy" */
	public Sell(Integer beverageId, Integer numberOfCapsules, String date) 
			throws ParseException, DateException, BeverageException, NotEnoughCapsules {
		this.beverageId = beverageId;
		this.numberOfCapsules = numberOfCapsules;
		this.date = new SimpleDateFormat("dd/MM/yyyy").parse(date);
	}
	
	public Integer getBevarageId() throws BeverageException {
		return this.beverageId;
	}
	
	public Integer getNumberOfCapsules() throws NotEnoughCapsules {
		return this.numberOfCapsules;
	}
	
	public String getDate() throws DateException {
		return this.date.toString();
	}
}
