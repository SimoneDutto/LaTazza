# Unit Testing Documentation template

Authors:

Date:

Version:

# Contents

- [Black Box Unit Tests](#black-box-unit-tests)




- [White Box Unit Tests](#white-box-unit-tests)


# Black Box Unit Tests

 ### **Class *DataImpl* - method *createBeverage***



**Criteria for method *createBeverage*:**
	

 - Valid beverage name 
 - Range of number of capsules per box
 - Range of box price
 - Duplicate beverage
 




**Predicates for method *createBeverage*:**

|         Criteria           | Predicate |
| -------------------------- | --------- |
| Beverage name              | Empty string     |
|							 | Not empty string |
| Number of capsules per box | Valid            |
|							 | Not valid        |
| Box price                  | Valid            |
|							 | Not valid        |
| Duplicate beverage		 | Beverage with same name already exists |
|							 | Beverage with same name does not exist yet |


**Boundaries**:

|          Criteria         | Boundary values     |
| ------------------------- | ------------------- |
| Beverage name				| String length = 0   |
|							| String length > 0   |
| Range of capsules per box | > 0 and <= Integer.MAX_VALUE |
| 							| <= 0 or > Integer.MAX_VALUE |
| Range of box price        | > 0 and <= Integer.MAX_VALUE |
|							| <= 0 or > Integer.MAX_VALUE |



**Combination of predicates**:


| Valid beverage name | Range of number of capsules per box | Range of box price    | Duplicate employee | Valid / Invalid | Description of the test case    | JUnit test case |
|---------------------|---------------------------|-----------------------|-----------------|--------|--------------------------------|-------|
| Yes 				  | > 0	and <= Integer.MAX_VALUE | > 0 and <= Integer.MAX_VALUE | No | Valid | Test the method to add a new beverage in the database | TestCreateBeverage.testValidInputs() |
| Yes  				  | > 0	and <= Integer.MAX_VALUE | < 0 and <= Integer.MAX_VALUE | Yes | Invalid | Test the method to add a new beverage in the database with already existing employee | TestCreateBeverage.testDuplicateBeverage() |
| Yes 				  | > 0	and <= Integer.MAX_VALUE | < 0  | No | Invalid | Test the method to add a new beverage in the database with negative box price | TestCreateBeverage.testNegativeBoxPrice() |
| Yes 				  | > 0	and <= Integer.MAX_VALUE | = 0 | No | Invalid | Test the method to add a new beverage in the database with null box price | TestCreateBeverage.testNullBoxPrice() |
| Yes 				  | > 0	and <= Integer.MAX_VALUE | > Integer.MAX_VALUE | No | Invalid | Test the method to add a new beverage in the database with too big box price | TestCreateBeverage.testOverflowBoxPrice() |
| Yes 				  | < 0	| > 0 and <= Integer.MAX_VALUE | No |Invalid | Test the method to add a new beverage in the database with negative number of capsules per box | TestCreateBeverage.testNegativeNumberOfCapsules() |
| Yes 				  | = 0	| > 0 and <= Integer.MAX_VALUE | No |Invalid | Test the method to add a new beverage in the database with null number of capsules per box | TestCreateBeverage.testNullNumberOfCapsules() |
| Yes 				  | > Integer.MAX_VALUE	| > 0 and <= Integer.MAX_VALUE | No |Invalid | Test the method to add a new beverage in the database with too big number of capsules per box | TestCreateBeverage.testOverflowNumberOfCapsules() |
| No  				  | > 0 and <= Integer.MAX_VALUE | > 0 and <= Integer.MAX_VALUE | No | Invalid |Test the method to add a new beverage in the database with wrong beverage name | TestCreateBeverage.testWrongBeverageName() |


### **Class *DataImpl* - method *createEmployee***

**Criteria for method *createEmployee*:**
	

 - Valid employee name 
 - Valid employee surname
 - Duplicate employee
 
Null values are not allowed by the GUI interface.


**Predicates for method *createEmployee*:**

|         Criteria           | Predicate |
| -------------------------- | --------- |
| Employee name              | Empty string     |
|							 | Not empty string |
| Employee surname           | Empty string     |
|							 | Not empty string |
| Duplicate employee		 | Employee with same name and surname already exists |
|							 | Employee with same name and surname does not exist yet |



**Boundaries**:

|          Criteria         | Boundary values     |
| ------------------------- | ------------------- |
| Employee name 			| String length = 0   |
| 							| String length > 0   |
| Employee surname 			| String length = 0   |
| 							| String length > 0   |


**Combination of predicates**:


| Valid employee name | Valid employee surname | Duplicate Employee    | Valid / Invalid | Description of the test case    | JUnit test case |
|---------------------|---------------------------|-----------------------|-----------------|---------------------------------|-------|
| Yes | Yes | No | Valid | Test method to add new employee in the database | TestCreateEmployee.testValidInputs() |
| Yes | Yes | Yes | Invalid | Test method to add new employee in the database with duplicate employee | TestCreateEmployee.testDuplicateEmployee() |
| Yes | No | No | Invalid | Test method to add new employee in the database with not valid surname | TestCreateEmployee.testWrongSurname() |
| No | Yes | No | Invalid | Test method to add new employee in the database with not valid name | TestCreateEmployee.testWrongName() |



 ### **Class *DataImpl* - method *sellCapsules***


**Criteria for method *sellCapsules*:**
	

 - Existence of EmployeeId
 - Existence of BeverageId
 - Sign of NumberOfCapsules
 - Value of fromAccount

I decided not to consider the type of the arguments because Java Compiler already does control the type.

**Predicates for method *sellCapsules*:**

| Criteria | Predicate |
| -------- | --------- |
|  Existence of EmployeeId        |   It exists        |
|                                 |   It doesn't exist          |
| Existence of BeverageId         |   It exists        |
|                                 |   It doesn't exist          |
|  NumberOfCapsules       |   Major than NumberOfCapsules present          |
|                                 |   Minor |
| Value of fromAccount            |   True    |
|                                 |   False    |
| Range of NumberOfCapsules |   Minor of maximum
|  |     Major of maximum |

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |
|  Range Number       | MAXINT                 |
|                     | 0   |
| NumberOfCapsule | = NumberOfCapsules bought by manager |


**Combination of predicates**:


| Existence of EmployeeId |  Existence of BeverageId  |NumberOfCapsules  | Value of fromAccount | Range of NumberOfCapsules | Valid / Invalid | Description of the test case | JUnit test case |
|-------|-------|-------|-------|-------|-------|-------|------|
|Yes| Yes| Minor | True| Minor|  Valid| Test the function to sell to Employee with account  | TestSellCapsules.testSellCapsuleAccount() |
|Yes| Yes| Minor | False| Minor|  Valid| Test the function to sell to Employee without account  | TestSellCapsules.testSellCapsuleNoAccount() |
|No| Yes| Minor | True| Minor| Invalid| Test the function with wrong EmployeeId| TestSellCapsules.testEmployeeIdNotValid() |
|Yes | No| Minor| True| Minor| Invalid| Test the function with wrong BeverageId| TestSellCapsules.testBeverageIdNotValid() |
|Yes | Yes | Major| True| Minor| Invalid| Test the function with NumberOfCapsules exceeding limit| TestSellCapsules.testNumberOfCapsulesTooBig() | 
|Yes | Yes | Minor| True | Major| Invalid| Test the function with MAXINT as NumberOfCapsules| TestSellCapsules.testMaxNumberOfCapsulesNotValid()|


 ### **Class *DataImpl* - method *sellCapsulesToVisitor***


**Criteria for method **sellCapsulesToVisitor:**
	
 - Existence of BeverageId
 - Sign of NumberOfCapsules

I decided not to consider the type of the arguments because Java Compiler already does control the type.

**Predicates for method *sellCapsulesToVisitor*:**

| Criteria | Predicate |
| -------- | --------- |
| Existence of BeverageId         |   It exists        |
|                                 |   It doesn't exist          |
|  NumberOfCapsules       |   Major than NumberOfCapsules present          |
|                                 |   Minor |
| Range of NumberOfCapsules |   Minor of maximum
|  |     Major of maximum |

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |
|  Range Number       | MAXINT                 |
|                     | 0   |
| NumberOfCapsule | = NumberOfCapsules bought by manager |


**Combination of predicates**:

Existence of BeverageId  |NumberOfCapsules  | Range of NumberOfCapsules | Valid / Invalid | Description of the test case | JUnit test case |
|-------|-------|-------|-------|-------|-------|
| Yes| Minor |  Minor|  Valid| Test the function to sell to Visitor  | TestSellCapsulesToVisitor.testSellCapsules() |
| No| Minor|  Minor| Invalid| Test the function with wrong BeverageId| TestSellCapsulesToVisitor.testBeverageIdNotValid() |
| Yes | Major| Minor| Invalid| Test the function with NumberOfCapsules exceeding limit| TestSellCapsulesToVisitor.testNumberOfCapsulesTooBig() | 
| Yes | Minor| Major| Invalid| Test the function with MAXINT as NumberOfCapsules| TestSellCapsulesToVisitor.testMaxNumberOfCapsulesNotValid()|

### **Class *DataImpl* - method *getEmployeeReport***


**Criteria for method **getEmployeeReport:**
	
 - Existence of EmployeeId
 - Valid Date Range

I decided not to consider the type of the arguments because Java Compiler already does control the type.

**Predicates for method *getEmployeeReport*:**

| Criteria | Predicate |
| -------- | --------- |
| Existence of EmployeeId         |   It exists        |
|                                 |   It doesn't exist          |
|  Range Date       |  Valid         |
|                   |  Invalid |
| Date | Not null |
| | Null | 

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |
|  Range Date      | EndDate = StartDate                 |


**Combination of predicates**:


Existence of EmployeeId  | Date Range | Date | Valid / Invalid | Description of the test case | JUnit test case |
|-------|-------|-------|-------|-------|-----------|
| Yes| Valid | Not null | Valid| Test the function to get report of employee withing a date range  | TestGetReportEmployee.testGetEmployeeReport() |
| No| Valid | Not null |  Invalid| Test the function with wrong EmployeeId| TestGetReportEmployee.testEmployeeIdNotValid() |
| Yes | Invalid | Not null | Invalid | Test the function with startDate > EndDate | TestGetReportEmployee.testWrongDate() |
| Yes | Valid| Not null | Valid| Test the function with StartDate = EndDate | TestGetReportEmployee.testEqualDates() |
| Yes | Valid | Null | Invalid | Test the function with one date = null | TestGetReportEmployee.testNullDates() | 

### **Class *DataImpl* - method *getReport***


**Criteria for method **geReport:**
	
 - Existence of EmployeeId
 - Valid Date Range

I decided not to consider the type of the arguments because Java Compiler already does control the type.

**Predicates for method *getReport*:**

| Criteria | Predicate |
| -------- | --------- |
| Existence of EmployeeId         |   It exists        |
|                                 |   It doesn't exist          |
|  Range Date       |  Valid         |
|                   |  Invalid |
| Date | Not null |
| | Null | 

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |
|  Range Date      | EndDate = StartDate                 |

**Combination of predicates**:

| Date Range | Date | Valid / Invalid | Description of the test case | JUnit test case |
|-------|-------|-------|-------|-------|
| Valid | Not null | Valid| Test the function to get report of employee withing a date range  | TestGetReport.testGetReport() |
| Invalid | Not null |  Invalid| Test the function with startDate > EndDate| TestGetReport.testWrongDates() |
| Valid | Null | Invalid | Test the function  with null date| TestGetReport.testNullDate() |
| Valid| Not null | Valid| Test the function with StartDate = EndDate | TestGetReportEmployee.testEqualDates() |


### **Class *DataImpl* - method *rechargeAccount***

**Criteria for method *rechargeAccount*:**
	

 - Existence of employeeId
 - Value of amountInCents

**Predicates for method *rechargeAccount*:**

| Criteria | Predicate |
| -------- | --------- |
| Existence of employeeId | it exists |
|          | it does not exist|
| Value of amountInCents| > 0 |
|  | <= 0 |

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |
| Value of amountInCents | MAXINT, 0|


**Combination of predicates**:


| Existence of employeeId | Value of amountInCents | Valid / Invalid | Description of the test case | JUnit test case |
|-------|-------|-------|-------|-------|
|Yes | > 0 | V | Test the function in standard conditions| TestRecharge.TestRechargeBalance()|
||MAXINT| V | Test the function with amountInCents = MAXINT| TestRecharge.TestRechargeMAXINT()|
||0|V| Test the function with amountInCents = 0 | TestRecharge.TestRechargeWithZero()|
| No | > 0 | I | Test the function when the EmployeeException is thrown| TestRecharge.TestRechargeException()|


### **Class *DataImpl* - method *getEmployeeSurname***

**Criteria for method *getEmployeeSurname*:**
	

 - Valid employee ID


**Predicates for method *getEmployeeSurname*:**

|         Criteria           | Predicate |
| -------------------------- | --------- |
| Employee ID                | Exists in the database     |
|							 | Does not exist in the database |

**Boundaries**:

|          Criteria         | Boundary values     |
| ------------------------- | ------------------- |
| Employee ID				| > 0 and <= MAX(EmployeeID)   |
|							| <= 0 or > MAX(EmployeeID)   |


**Combination of predicates**:


| Employee ID | Valid / Invalid | Description of the test case    | JUnit test case |
|---------------------|---------------------------|-----------------------|-----------------|
| > 0	and <= MAX(EmployeeID) | Valid | Test the method to retrieve the surname of the employee with given ID | TestGetEmployeeSurname.testValidInputs() |
| < 0 | Invalid | Test the method to retrieve the surname of the employee with given negative ID | TestGetEmployeeSurname.testNegativeId() |
| = 0 | Invalid | Test the method to retrieve the surname of the employee with given null ID | TestGetEmployeeSurname.testNullId() |
| > MAX(EmployeeID) | Invalid | Test the method to retrieve the surname of the employee with given out of maximum boundary ID | TestGetEmployeeSurname.testOutOfMaxBoundaryId() |


 ### **Class *DataImpl* - method *updateBeverage***

**Criteria for method *updateBeverage*:**
	

 - Existence of BeverageId
 - name of the beverage is null
 - boxPrice is 0
 - capsulesPerBox is 0


**Predicates for method *updateBeverage*:**

| Criteria | Predicate |
| -------- | --------- |
| Existence of beverageId | it exists |
|          | it does not exist|
| name of the beverage| null |
|  | not null |
| boxPrice | = 0 |
|| != 0 |
| capsulesPerBox | = 0 |
| | != 0|


**Combination of predicates**:

I decided not to consider the type of the arguments because Java Compiler already does control the type.
I didn't considered null input of any arguments because GUI didn't allow null inputs.

| Existence of beverageId | name of the beverage| boxPrice | capsulesPerBox | Valid / Invalid | Description of the test case | JUnit test case |
|-------|-------|-------|-------|-------|-------|-------|
|Yes | not null| != 0 | != 0 | V | Test the function in standard conditions| TestUpdateBeverage.TestUpdateBev()|
| Yes | null | | | I | Test the function when BeverageException is thrown because the name is an empty string| TestUpdateBeverage.TestExceptionName()|
|Yes ||= 0|| I | Test the function when BeverageException is thrown because boxPrice = 0| TestUpdateBeverage.TestExceptionPrice()|
|Yes |||0|I| Test the function when BeverageException is thrown because capsulesPerBox = 0 | TestUpdateBeverage.TestExceptionBox()|
| No ||| | I | Test the function when the BeverageException is thrown because ID not valid| TestUpdateBeverage.TestExceptionId()|

 
 ### **Class *DataImpl* - method *updateEmployee***



**Criteria for method *updateEmployee*:**
	

 - Existence of employeeId
 - name of the employee is null
 - surname of the employee is null


**Predicates for method *updateEmployee*:**

| Criteria | Predicate |
| -------- | --------- |
| Existence of employeeId | it exists |
|          | it does not exist|
| name of the employee| null |
|  | not null |
| surname of the employee| null |
|  | not null |


**Combination of predicates**:


| Existence of employeeId | name of the employee| surname of the employee | Valid / Invalid | Description of the test case | JUnit test case |
|-------|-------|-------|-------|-------|-------|
|Yes | not null| not null | V | Test the function in standard conditions| TestUpdateEmployee.TestUpdateEmp()|
| Yes | null |  | I | Test the function when EmployeeException is thrown because the name is an empty string| TestUpdateEmployee.TestExceptionName()|
| Yes |  | null | I | Test the function when EmployeeException is thrown because the surname is an empty string| TestUpdateEmployee.TestExceptionName()|
| No || | I | Test the function when the EmployeeException is thrown because ID not valid| TestUpdateEmployee.TestExceptionId()|


### **Class *DataImpl* - method *getEmployeeBalance***



**Criteria for method *getEmployeeBalance*:**
	

 - Valid employee ID


**Predicates for method *getEmployeeBalance*:**

|         Criteria           | Predicate |
| -------------------------- | --------- |
| Employee ID                | Exists in the database     |
|							 | Does not exist in the database |


**Boundaries**:

|          Criteria         | Boundary values     |
| ------------------------- | ------------------- |
| Employee ID				| > 0 and <= MAX(EmployeeID)   |
|							| <= 0 or > MAX(EmployeeID)   |

**Combination of predicates**:


| Employee ID | Valid / Invalid | Description of the test case    | JUnit test case |
|---------------------|---------------------------|-----------------------|-----------------|
| > 0 and <= MAX(EmployeeID) | Valid | Test the method to retrieve the balance of the employee with given ID | TestGetEmployeeBalance.testValidInputs() |
| < 0 | Invalid | Test the method to retrieve the balance of the employee with given negative ID | TestGetEmployeeBalance.testNegativeId() |
| = 0 | Invalid | Test the method to retrieve the balance of the employee with given null ID | TestGetEmployeeBalance.testNullId() |
| > MAX(EmployeeID) | Invalid | Test the method to retrieve the balance of the employee with given out of maximum boundary ID | TestGetEmployeeBalance.testOutOfMaxBoundaryId() |


### **Class *DataImpl* - method *buyBoxes***



**Criteria for method *buyBoxes*:**
	

 - Existence of BeverageID
 - Sign of boxQuantity

I decided not to consider the type of the arguments because Java Compiler already does control the type.
I didn't considered null input of any arguments because GUI didn't allow null inputs.


**Predicates for method *buyBoxes*:**

| Criteria | Predicate |
| -------- | --------- |
| Existence of BeverageId         |   It exists        |
|                                 |   It doesn't exist          |
|  boxQuantity         |   Major than managerBalance/boxPrice          |
|                                 |   Minor |
| Range of boxQuantity |   Minor of maximum
|  |     Major of maximum |




**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |
|  Range Number       | MAXINT                 |
|                     | 0   |
| boxQuantity | = managerBalance/boxPrice |



**Combination of predicates**:


Existence of BeverageId  |boxQuantity  | Range of boxQuantity | Valid / Invalid | Description of the test case | JUnit test case |
|-------|-------|-------|-------|-------|-------|
| Yes| Minor |  Minor|  Valid| Test the function to buy boxes  | TestBuyBoxes.testBuyBoxes() |
| No| Minor|  Minor| Invalid| Test the function with wrong BeverageId| TestBuyBoxes.testBeverageIdNotValid() |
| Yes | Major| Minor| Invalid| Test the function with boxQuantity exceeding limit| TestBuyBoxes.testBoxQuantityTooBig() | 
| Yes | Minor| Major| Invalid| Test the function with MAXINT as boxQuantity| TestBuyBoxes.testMaxBoxQuantityNotValid()|



 ### **Class *DataImpl* - method *getBeverageName***

**Criteria for method *getBeverageName*:**
	

 - Existence of BeverageID

I decided not to consider the type of the arguments because Java Compiler already does control the type.
I didn't considered null input of any arguments because GUI didn't allow null inputs.


**Predicates for method *getBeverageName*:**

| Criteria | Predicate |
| -------- | --------- |
| Existence of BeverageId         |   It exists        |
|                                 |   It doesn't exist          |


**Combination of predicates**:


Existence of BeverageId  | Valid / Invalid | Description of the test case | JUnit test case |
|-------|-------|-------|-------|
| Yes  | Valid | Test the function to get beverage name | TestGetBeverageName.testGetNameValid() |
| No  | Invalid | Test the function to check beverageId | TestGetBeverageName.testBeverageIdNotValid() |


 ### **Class *DataImpl* - method *getBeverageCapsulesPerBox***

**Criteria for method *getBeverageCapsulesPerBox*:**
	

 - Existence of BeverageID

I decided not to consider the type of the arguments because Java Compiler already does control the type.
I didn't considered null input of any arguments because GUI didn't allow null inputs.


**Predicates for method *getBeverageCapsulesPerBox*:**

| Criteria | Predicate |
| -------- | --------- |
| Existence of BeverageId         |   It exists        |
|                                 |   It doesn't exist          |


**Combination of predicates**:


Existence of BeverageId  | Valid / Invalid | Description of the test case | JUnit test case |
|-------|-------|-------|-------|
| Yes  | Valid | Test the function to get beverage capsules per box | TestGetBeverageCapsulesPerBox.testCapsulesPerBoxValid() |
| No  | Invalid | Test the function to check beverageId | TestGetBeverageCapsulesPerBox.testBeverageIdNotValid() |


 ### **Class *DataImpl* - method *getBeverageBoxPrice***

**Criteria for method *getBeverageBoxPrice*:**
	

 - Existence of BeverageID

I decided not to consider the type of the arguments because Java Compiler already does control the type.
I didn't considered null input of any arguments because GUI didn't allow null inputs.


**Predicates for method *getBeverageBoxPrice*:**

| Criteria | Predicate |
| -------- | --------- |
| Existence of BeverageId         |   It exists        |
|                                 |   It doesn't exist          |


**Combination of predicates**:


Existence of BeverageId  | Valid / Invalid | Description of the test case | JUnit test case |
|-------|-------|-------|-------|
| Yes  | Valid | Test the function to get beverage box price | TestGetBeverageBoxPrice.testGetBoxPriceValid() |
| No  | Invalid | Test the function to check beverageId | TestGetBeverageBoxPrice.testBeverageIdNotValid() |


 ### **Class *DataImpl* - method *getBeverageCapsules***

**Criteria for method *getBeverageCapsules*:**
	

 - Existence of BeverageID

I decided not to consider the type of the arguments because Java Compiler already does control the type.
I didn't considered null input of any arguments because GUI didn't allow null inputs.


**Predicates for method *getBeverageCapsules*:**

| Criteria | Predicate |
| -------- | --------- |
| Existence of BeverageId         |   It exists        |
|                                 |   It doesn't exist          |


**Combination of predicates**:


Existence of BeverageId  | Valid / Invalid | Description of the test case | JUnit test case |
|-------|-------|-------|-------|
| Yes  | Valid | Test the function to get beverage capsules | TestGetBeverageCapsules.testGetBeverageCapsulesValid() |
| No  | Invalid | Test the function to check beverageId | TestGetBeverageCapsules.testBeverageIdNotValid() |


 ### **Class *DataImpl* - method *getEmployeeName***

**Criteria for method *getEmployeeName*:**
	

 - Existence of EmployeeID

I decided not to consider the type of the arguments because Java Compiler already does control the type.
I didn't considered null input of any arguments because GUI didn't allow null inputs.


**Predicates for method *getEmployeeName*:**

| Criteria | Predicate |
| -------- | --------- |
| Existence of EmployeeID         |   It exists        |
|                                 |   It doesn't exist          |


**Combination of predicates**:


Existence of EmployeeID  | Valid / Invalid | Description of the test case | JUnit test case |
|-------|-------|-------|-------|
| Yes  | Valid | Test the function to get employee name | TestGetEmployeeName.testGetNameValid() |
| No  | Invalid | Test the function to check employeeID | TestGetEmployeeName.testEmployeeIdNotValid() |


# White Box Unit Tests

### Test cases definition

| Unit name | JUnit test case |
|--|--|
| DataImpl | it.polito.latazza.data.TestBuyBoxes() |
| DataImpl | it.polito.latazza.data.TestCreateBeverages() |
| DataImpl | it.polito.latazza.data.TestCreateEmployee() |
| DataImpl | it.polito.latazza.data.TestGetBeverageBoxPrice() |
| DataImpl | it.polito.latazza.data.TestGetBeverageCapsules() |
| DataImpl | it.polito.latazza.data.TestGetBeveragesCapulesPerBox() |
| DataImpl | it.polito.latazza.data.TestGetBeverageName() |
| DataImpl | it.polito.latazza.data.TestGetBeverages() |
| DataImpl | it.polito.latazza.data.TestGetBevId() |
| DataImpl | it.polito.latazza.data.TestGetEmployeeBalance() |
| DataImpl | it.polito.latazza.data.TestGetEmployeeName() |
| DataImpl | it.polito.latazza.data.TestGetEmployeeSurname() |
| DataImpl | it.polito.latazza.data.TestGetEmployeeReport() |
| DataImpl | it.polito.latazza.data.TestGetReport() |
| DataImpl | it.polito.latazza.data.TestRecharge() |
| DataImpl | it.polito.latazza.data.TestSellCapsules() |
| DataImpl | it.polito.latazza.data.TestSellCapsulesToVisitor() |
| DataImpl | it.polito.latazza.data.TestUpdateBeverage() |
| DataImpl | it.polito.latazza.data.TestUpdateEmployee() |
| DataImpl | it.polito.latazza.data.TestUtilityFunction() |
| DataBase | it.polito.latazza.data.TestCheck() |

### Code coverage report

    <Add here the screenshot report of the code and branch coverage obtained using
    the Jacoco tool. >


### Loop coverage analysis

    <Identify significant loops in the units and reports the test cases
    developed to cover zero, one or multiple iterations >

|Unit name | Loop rows | Number of iterations | JUnit test case |
|---|---|---|---|
|||||
|||||
||||||



