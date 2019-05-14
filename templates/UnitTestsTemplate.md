# Unit Testing Documentation template

Authors:

Date:

Version:

# Contents

- [Black Box Unit Tests](#black-box-unit-tests)




- [White Box Unit Tests](#white-box-unit-tests)


# Black Box Unit Tests

 ### **Class *DataImpl* - method *buyBoxes***



**Criteria for method *buyBoxes*:**
	

 - Existence of BeverageID
 - Sign of boxQuantity

I decided not to consider the type of the aguments because Java Compiler already does control the type.
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
| Yes | Major| Minor| Invalid| Test the function with boxQuantity exceding limit| TestBuyBoxes.testBoxQuantityTooBig() | 
| Yes | Minor| Major| Invalid| Test the function with MAXINT as boxQuantity| TestBuyBoxes.testMaxBoxQuantityNotValid()|



 ### **Class *DataImpl* - method *getBeverageName***

**Criteria for method *getBeverageName*:**
	

 - Existence of BeverageID

I decided not to consider the type of the aguments because Java Compiler already does control the type.
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

I decided not to consider the type of the aguments because Java Compiler already does control the type.
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

I decided not to consider the type of the aguments because Java Compiler already does control the type.
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

I decided not to consider the type of the aguments because Java Compiler already does control the type.
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

I decided not to consider the type of the aguments because Java Compiler already does control the type.
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



