package it.polito.latazza.data;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import it.polito.latazza.exceptions.BeverageException;
import it.polito.latazza.exceptions.EmployeeException;

public class TestCheck {
	DataImpl data = new DataImpl("test_db");
  
  @Test
  public void testCheckE() throws EmployeeException{
    data.reset();
    
    data.createEmployee("lisa", "romita");
    try {
      DataBase.getInstance().checkEmp(2);
      assert(false);
    }catch(EmployeeException e) {
      assertEquals("ID of the employee is not valid", e.getMessage());
    }
    
  }
  
  @Test
  public void testCheckB() throws BeverageException{
    data.reset();
    
    data.createBeverage("caffe",20,20);
    try {
      DataBase.getInstance().checkBeverageId(2);
      assert(false);
    }catch(BeverageException e) {
      assertEquals("ID of the beverage is not valid", e.getMessage());
    }
    
  }

}