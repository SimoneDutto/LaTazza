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

 ### **Class *Database* - method *rechargeAccount***



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
| No | > 0 | V | Test the function when the EmployeeException is thrown| TestRecharge.TestRechargeException()|





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



