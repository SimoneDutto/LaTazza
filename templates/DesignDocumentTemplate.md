# Design Document Template

Authors: Vito Tassielli, Isabella Romita

Date: 16/04/2019

Version: 1.0

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

note "we decided to define only one package since the application is basically made only by the graphical interface" as n
```


# Class diagram
We implement the *MVC Model*, so the LaTazza View can be changed in future and the application model will remain the same with a lot of time saved.

```plantuml
class LaTazzaView{
    
} 


class LaTazzaLogic{
- employees
- inventory
- transactions

{method} + sellCapsules(employeeId, beverageId, numberOfCapsules, fromAccount)
{method} + sellCapsulesToVisitor(beverageId, numberOfCapsules)
{method} + rechargeAccount(employeeId, amountInCents) 
{method} + buyBoxes(beverageId, boxQuantity)
{method} + getEmployeeReport(employeeId, startDate, endDate)
{method} + getReport(startDate, endDate)

{method} + getBeverageCapsules(beverageId)
{method} + getBeverageName(beverageIdbeverageId)
{method} + getBeverageCapsulesPerBox(beverageId)
{method} + getBeverageBoxPrice(beverageId)
{method} + updateBeverage(beverageId, name, capsulesPerBox, boxPrice)
{method} + getBeveragesId()
{method} + getBeverages()

{method} + updateEmployee(employeeId, name, surname)
{method} + getEmployeeName(employeeId)
{method} + getEmployeeSurname(employeeId)
{method} + getEmployeeBalance(employeeId)
{method} + getEmployeesId()
{method} + getEmployees()
}

class Beverage {
+ name
+ boxPrice
+ capsulesPerBox
+ quantity

{method} + createBeverage(name, capsulesPerBox, boxPrice)
{method} + getBeverageName()
{method} + getBoxPrice()
{method} + getCapsulesPerBox()
{method} + getQuantity()
}

class Employee {
+ name
+ surname
+ balance
+ inventory
+ transactions

{method} + createEmployee(name, surname)
{method} + getName()
{method} + getSurname()
{method} + getBalance()
{method} + updateInventory(beverageId, quantityCapsules)
{method} + updateBalance(amount)
{method} + recordEmployeeTransaction(date, amount)
}

class LaTazzaAccount {
+ username
+ password

{method} + createAccount(username, password)
{method} + getUsername()
{method} + getPassword()
}

class Transaction {
+ type
+ date
+ amount

{method} + recordTransaction(type, date, amount)
}

LaTazzaLogic -- LaTazzaView : "Ask for data to compose JFrames with correct information"

LaTazzaLogic -- LaTazzaAccount
LaTazzaLogic -- Employee
LaTazzaLogic -- Transaction
LaTazzaLogic -- Beverage

Employee -- Beverage
Employee -- Transaction


```

| Name | Description |
| ------------- |:-------------:|
| sellCapsules(employeeId, beverageId, numberOfCapsules, fromAccount) | updates the general inventory and the employee's one and also the list of transactions |
| sellCapsulesToVisitor(beverageId, numberOfCapsules) | updates the general inventory and the list of transactions |
| rechargeAccount(employeeId, amountInCents)  | updates the balance of the given employee |
| buyBoxes(beverageId, boxQuantity) | updates the general inventory |
| getEmployeeReport(employeeId, startDate, endDate) | returns the list of transactions of the given employee during the given range of dates |
| getReport(startDate, endDate) | returns the list of all transactions during the given range of dates  |
| getBeverageCapsules(beverageId) | returns the number of capsules given the beverage ID |
| getBeverageName(beverageId) | returns the name of the capsule given the beverage ID |
| getBeverageCapsulesPerBox(beverageId) | returns the number of capsules per box given the beverage ID |
| getBeverageBoxPrice(beverageId) | returns the box price given the beverage ID |
| updateBeverage(beverageId, name, capsulesPerBox, boxPrice) | updates the given beverage in the inventory |
| getBeveragesId() | returns the list of all beverages' IDs |
| getBeverages() | returns the list of all beverages names |
| updateEmployee(employeeId, name, surname) | updates tha data of the given employee |
| getEmployeeName(employeeId) | returns the name of the given employee |
| getEmployeeSurname(employeeId) | returns the surname of the given employee |
| getEmployeeBalance(employeeId) | returns the balance of the given employee |
| getEmployeesId() | returns the list of all employees' IDs |
| getEmployees() | returns the map of all employees' IDs and their names and surnames |
| createBeverage(name, capsulesPerBox, boxPrice) | creates a new type of beverage |
| getBeverageName() | returns the name of the beverage |
| getBoxPrice() | returns the box price of the beverage |
| getCapsulesPerBox() | returns the number of cupsules per box of the beverage |
| getQuantity() | returns the available quantity of the beverage |
| createEmployee(name, surname) | creates a new employee |
| getName() | returns the name of the employee |
| getSurname() | returns the surname of the employee |
| getBalance() | returns the balance of the employee |
| updateInventory(beverageId, quantityCapsules) | updates the inventory of the employee after a purchase (or a consumption) |
| updateBalance(amount) | updates the balance of the employee  |
| recordEmployeeTransaction(date, amount) | updates the list of transactions of the employee |
| createAccount(username, password) | creates a new account |
| getUsername() | returns the username of the account |
| getPassword() | returns the password of the account |
| recordTransaction(type, date, amount) | creates a new transaction (type can be purchase or sell) |

# Verification traceability matrix

\<for each functional requirement from the requirement document, list which classes concur to implement it>


|  | LaTazzaView | LaTazzaLogic  | Employee | LaTazzaAccount | Beverage | Transaction|
| ------------- |:-------------:| -----:| -----:| -----:| -----:| -----:|
| FR1  | X | X | X |  |  |   |
| FR2  | X | X |   |  |  |   |
| FR3  | X | X | X |  |  | X |
| FR4  | X | X |   |  | X |  |
| FR5  | X | X | X |  |   |  |
| FR6  | X | X |   |  |   |  |
| FR7  | X | X |  |    | X |  |
| FR8  | X | X |  | | | |
# Verification sequence diagrams 
\<select key scenarios from the requirement document. For each of them define a sequence diagram showing that the scenario can be implemented by the classes and methods in the design>

```plantuml
": Class X" -> ": Class Y": "1: message1()"
": Class X" -> ": Class Y": "2: message2()"
```