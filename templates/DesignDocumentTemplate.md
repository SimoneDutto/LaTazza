# Design Document Template

Authors:

Date:

Version:

# Contents

- [Package diagram](#package-diagram)
- [Class diagram](#class-diagram)
- [Verification traceability matrix](#verification-traceability-matrix)
- [Verification sequence diagrams](#verification-sequence-diagrams)

# Instructions

The design document has to comply with:
1. [Official Requirement Document](../Official\ Requirements\ Document.md)
2. [DataInterface.java](../src/main/java/it/polito/latazza/data/DataInterface.java)

UML diagrams **MUST** be written using plantuml notation.

# Package diagram

```plantuml
package GUI
```


# Class diagram

```plantuml
class LaTazza{
+ employees
+ inventory
+ transactions

{method} + sellCapsules(employeeId, beverageId, numberOfCapsules, fromAccount)
{method} + sellCapsulesToVisitor(beverageId, numberOfCapsules)
{method} + rechargeAccount(id, amountInCents) 
{method} + buyBoxes(beverageId, boxQuantity)
{method} + getEmployeeReport(employeeId, startDate, endDate)
{method} + getReport(startDate, endDate)

{method} + getBeverageCapsules(id)
{method} + getBeverageName(id)
{method} + getBeverageCapsulesPerBox(id)
{method} + getBeverageBoxPrice(id)
{method} + updateBeverage(id, name, capsulesPerBox, boxPrice)
{method} + getBeveragesId()
{method} + getBeverages()

{method} + updateEmployee(id, name, surname)
{method} + getEmployeeName(id)
{method} + getEmployeeSurname(id)
{method} + getEmployeeBalance(id)
{method} + getEmployeesId()
{method} + getEmployees()
{method} + getBalance()
}

class Beverage {
+ name
+ boxPrice
+ capsulesPerBox
+ quantity

{method} + createBeverage(name, capsulesPerBox, boxPrice)
{method} + getName()
{method} + getBoxPrice()
{method} + getCapsulesPerBox()
{method} + getQuantity ()
}
```

# Verification traceability matrix

\<for each functional requirement from the requirement document, list which classes concur to implement it>


|  | Class x | Class y  | .. |
| ------------- |:-------------:| -----:| -----:|
| Functional requirement x  |  |  | |
| Functional requirement y  |  |  | |
| .. |  |  | |

# Verification sequence diagrams 
\<select key scenarios from the requirement document. For each of them define a sequence diagram showing that the scenario can be implemented by the classes and methods in the design>

```plantuml
": Class X" -> ": Class Y": "1: message1()"
": Class X" -> ": Class Y": "2: message2()"
```