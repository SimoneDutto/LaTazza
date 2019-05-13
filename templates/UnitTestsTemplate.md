
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
	

 - Valid Beverage name 
 - Range of number of capsules per box
 - Range of box price
 
Null values are not allowed by the GUI interface.




**Predicates for method *createBeverage*:**

|         Criteria           | Predicate |
| -------------------------- | --------- |
| Beverage name              | Empty string     |
|							 | Not empty string |
| Number of capsules per box | Valid            |
|							 | Not valid        |
| Box price                  | Valid            |
|							 | Not valid        |






**Boundaries**:

|          Criteria         | Boundary values     |
| ------------------------- | ------------------- |
| Range of capsules per box | > 0                 |
| 							| <= Integer.MAX_VALUE |
| Range of box price        | > 0                 |
|							| <= Integer.MAX_VALUE |



**Combination of predicates**:


| Valid Beverage name | Range of capsules per box | Range of box price    | Valid / Invalid | Description of the test case    | JUnit test case |
|---------------------|---------------------------|-----------------------|-----------------|---------------------------------|-------|
| Yes 				  | > 0	and <= Integer.MAX_VALUE | > 0 and <= Integer.MAX_VALUE | Valid | Test the method to add a new beverage in the database | TestCreateBeverage.testValidInputs() |
| Yes 				  | > 0	and <= Integer.MAX_VALUE | < 0 or > Integer.MAX_VALUE | Invalid | Test the method to add a new beverage in the database with wrong box price | TestCreateBeverage.testWrongBoxPrice() |
| Yes 				  | <= 0 or > Integer.MAX_VALUE	| > 0 and <= Integer.MAX_VALUE | Invalid | Test the method to add a new beverage in the database with wrong number of capsules per box | TestCreateBeverage.testWrongNumberOfCapsules() |
| No  				  | > 0 and <= Integer.MAX_VALUE | > 0 and <= Integer.MAX_VALUE | Invalid |Test the method to add a new beverage in the database with wrong beverage name | TestCreateBeverage.testWrongBeverageName() |



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



