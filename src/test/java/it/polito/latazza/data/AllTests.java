package it.polito.latazza.data;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestBuyBoxes.class, TestCreateBeverage.class, TestCreateEmployee.class, TestGetBeverageBoxPrice.class,
		TestGetBeverageCapsules.class, TestGetBeverageCapsulesPerBox.class, TestGetBeverageName.class,
		TestGetBeverages.class, TestGetBevId.class, testGetEmployeeBalance.class, TestGetEmployeeName.class,
		TestGetEmployeeReport.class, TestGetEmployeeSurname.class, TestGetReport.class, TestRecharge.class,
		TestSellCapsules.class, TestSellCapsulesToVisitor.class, TestUpdateBeverage.class, TestUpdateEmployee.class,
		TestUtilityFunction.class })
public class AllTests {

}
