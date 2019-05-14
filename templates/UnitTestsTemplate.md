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

 ### **Class *DataImpl* - method *sellCapsules***


**Criteria for method *name*:**
	

 - Existence of EmployeeId
 - Existence of BeverageId
 - Sign of NumberOfCapsules
 - Value of fromAccount

I decided not to consider the type of the aguments because Java Compiler already does control the type.

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
|Yes | Yes | Major| True| Minor| Invalid| Test the function with NumberOfCapsules exceding limit| TestSellCapsules.testNumberOfCapsulesTooBig() | 
|Yes | Yes | Minor| True | Major| Invalid| Test the function with MAXINT as NumberOfCapsules| TestSellCapsules.testMaxNumberOfCapsulesNotValid()|


 ### **Class *DataImpl* - method *sellCapsulesToVisitor***


**Criteria for method **sellCapsulesToVisitor:**
	
 - Existence of BeverageId
 - Sign of NumberOfCapsules

I decided not to consider the type of the aguments because Java Compiler already does control the type.

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
| Yes | Major| Minor| Invalid| Test the function with NumberOfCapsules exceding limit| TestSellCapsulesToVisitor.testNumberOfCapsulesTooBig() | 
| Yes | Minor| Major| Invalid| Test the function with MAXINT as NumberOfCapsules| TestSellCapsulesToVisitor.testMaxNumberOfCapsulesNotValid()|

### **Class *DataImpl* - method *getEmployeeReport***


**Criteria for method **getEmployeeReport:**
	
 - Existence of EmployeeId
 - Valid Date Range

I decided not to consider the type of the aguments because Java Compiler already does control the type.

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
|               |    |


**Combination of predicates**:


Existence of EmployeeId  | Date Range | Date | Valid / Invalid | Description of the test case | JUnit test case |
|-------|-------|-------|-------|-------|-----------|
| Yes| Valid | Not null | Valid| Test the function to get report of employee withing a date range  | TestGetReportEmployee.testGetEmployeeReport() |
| No| Valid | Not null |  Invalid| Test the function with wrong EmployeeId| TestGetReportEmployee.testEmployeeIdNotValid() |
| Yes | Invalid | Not null | Invalid | Test the funtion with startDate > EndDate | TestGetReportEmployee.testWrongDate() |
| Yes | Valid| Not null | Valid| Test the function with StartDate = EndDate | TestGetReportEmployee.testEqualDates() |
| Yes | Valid | Null | Invalid | Test the function with one date = null | TestGetReportEmployee.testNullDates() | 

### **Class *DataImpl* - method *getReport***


**Criteria for method **geReport:**
	
 - Existence of EmployeeId
 - Valid Date Range

I decided not to consider the type of the aguments because Java Compiler already does control the type.

**Predicates for method *sellReport*:**

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
|               |    |


**Combination of predicates**:

| Date Range | Date | Valid / Invalid | Description of the test case | JUnit test case |
|-------|-------|-------|-------|-------|
| Valid | Not null | Valid| Test the function to get report of employee withing a date range  | TestGetReport.testGetReport() |
| Invalid | Not null |  Invalid| Test the function with startDate > EndDate| TestGetReport.testWrongDates() |
| Valid | Null | Invalid | Test the funtion  with null date| TestGetReport.testNullDate() |
| Valid| Not null | Valid| Test the function with StartDate = EndDate | TestGetReportEmployee.testEqualDates() |


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



