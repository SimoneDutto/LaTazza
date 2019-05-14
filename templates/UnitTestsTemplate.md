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
|Yes | < 0 | V | Test the function with amountInCents < 0| TestRecharge.TestRechargeNegative()|
||MAXINT| V | Test the function with amountInCents = MAXINT| TestRecharge.TestRechargeMAXINT()|
||0|V| Test the function with amountInCents = 0 | TestRecharge.TestRechargeWithZero()|
| No | > 0 | I | Test the function when the EmployeeException is thrown| TestRecharge.TestRechargeException()|


 ### **Class *DataImpl* - method *updateBeverage***



**Criteria for method *updateBeverage*:**
	

 - Existence of BeverageId
 - name of the beverage is null
 - boxPrice is 0
 - pricePerBox is 0





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


| Existence of beverageId | name of the employee| surname of the employee | Valid / Invalid | Description of the test case | JUnit test case |
|-------|-------|-------|-------|-------|-------|
|Yes | not null| not null | V | Test the function in standard conditions| TestUpdateEmployee.TestUpdateEmp()|
| Yes | null |  | I | Test the function when EmployeeException is thrown because the name is an empty string| TestUpdateEmployee.TestExceptionName()|
| Yes |  | null | I | Test the function when EmployeeException is thrown because the surname is an empty string| TestUpdateEmployee.TestExceptionName()|
| No || | I | Test the function when the EmployeeException is thrown because ID not valid| TestUpdateEmployee.TestExceptionId()|


 ### **Class *DataImpl* - method *getBeveragesId***



**Criteria for method *getBeveragesId*:**
	

 - Correctness of function


**Combination of predicates**:


| Correctness of function | Valid / Invalid | Description of the test case | JUnit test case |
|-------|-------|-------|-------|-------|-------|
|Yes | V | Test the function in standard conditions| TestGetBevId.TestGetBeveragesId()|


### **Class *DataImpl* - method *getBeverages***



**Criteria for method *getBeverages*:**
	

 - Correctness of function


**Combination of predicates**:


| Correctness of function | Valid / Invalid | Description of the test case | JUnit test case |
|-------|-------|-------|-------|-------|-------|
|Yes | V | Test the function in standard conditions| TestGetBeverages.TestGetBev()|


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



