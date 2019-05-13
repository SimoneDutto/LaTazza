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



