
# Unit Testing Documentation template

Authors:

Date:

Version:

# Contents

- [Black Box Unit Tests](#black-box-unit-tests)




- [White Box Unit Tests](#white-box-unit-tests)


# Black Box Unit Tests

    <Define here criteria, predicates and the combination of predicates for each function of each class.
    Define test cases to cover all equivalence classes and boundary conditions.
    In the table, report the description of the black box test case and the correspondence with the JUnit black box test case name/number>

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
| Beverage name				| String length > 0   |
|							| String length = 0   |
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
| Employee name 			| String length > 0   |
| 							| String length = 0   |
| Employee surname 			| String length > 0   |
| 							| String length = 0   |


**Combination of predicates**:


| Valid employee name | Valid employee surname | Duplicate Employee    | Valid / Invalid | Description of the test case    | JUnit test case |
|---------------------|---------------------------|-----------------------|-----------------|---------------------------------|-------|
| Yes | Yes | No | Valid | Test method to add new employee in the database | TestCreateEmployee.testValidInputs() |
| Yes | Yes | Yes | Invalid | Test method to add new employee in the database with duplicate employee | TestCreateEmployee.testDuplicateEmployee() |
| Yes | No | No | Invalid | Test method to add new employee in the database with not valid surname | TestCreateEmployee.testWrongSurname() |
| No | Yes | No | Invalid | Test method to add new employee in the database with not valid name | TestCreateEmployee.testWrongName() |




# White Box Unit Tests

### Test cases definition

    <Report here all the created JUnit test cases, and the units/classes they test >


| Unit name | JUnit test case |
|--|--|
|||
|||
||||

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



