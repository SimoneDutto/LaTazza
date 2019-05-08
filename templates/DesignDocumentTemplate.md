# Design Document Template

Authors: Vito Tassielli, Isabella Romita, Simone Dutto, Debora Caldarola

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
package LaTazzaData
package LaTazzaException

note "One package containting the application logic, one containing exceptions and one package for the View" as n

```


# Class diagram
We implement the *MVC Model*. 
LaTazza View interacts with DataBaseImpl, which is a wrapper to call correctly functions in DataBase. Those functions query the SQLite database.

```plantuml
package LaTazzaException{
 class BeverageException 
 class DateException 
 class EmployeeException 
 class NotEnoughBalance 
 class NotEnoughCapsules 
}

package LaTazzaData{
class DataImpl {

{method} + sellCapsules(employeeId, beverageId, numberOfCapsules, fromAccount)
{method} + sellCapsulesToVisitor(beverageId, numberOfCapsules)
{method} + rechargeAccount(employeeId, amountInCents) 
{method} + buyBoxes(beverageId, boxQuantity)
{method} + getEmployeeReport(employeeId, startDate, endDate)
{method} + getReport(startDate, endDate)

{method} + createBeverage()
{method} + getBeverageCapsules(beverageId)
{method} + getBeverageName(beverageId)
{method} + getBevarageId(beverageName)
{method} + getBeverageCapsulesPerBox(beverageId)
{method} + getBeverageBoxPrice(beverageId)
{method} + updateBeverage(beverageId, capsulesPerBox, boxPrice)
{method} + getBeveragesId()
{method} + getBeverages()

{method} + createEmployee()
{method} + updateEmployee(employeeId, name, surname)
{method} + getEmployeeName(employeeId)
{method} + getEmployeeSurname(employeeId)
{method} + getEmployeeBalance(employeeId)
{method} + getEmployeeId(name, surname)
{method} + getEmployeesId()
{method} + getEmployees()

{method} + getBalance()
{method} + reset()
}

class DataBase{
- dbname

{method} + sellCap(employeeId, beverageId, numberOfCapsules,fromAccount)
{method} + sellVis(beverageId, numberOfCapsules)
{method} + recharge(id, amountInCents)
{method} + buyB(beverageId,  boxQuantity)
{method} + getEmplRep( employeeId, startDate, endDate)
{method} + getRep(startDate, endDate)

{method} + addBeverage(name, capsulesPerBox, boxPrice)
{method} + updateBeverage( id, String name, capsulesPerBox, boxPrice)
{method} + getBeverageName(beverageId)
{method} + getBeverageBoxInformation(beverageId, requiredInformation)
{method} + getBeverageIds() 
{method} + getBeverages()
{method} + getBeverageAvailableCapsules(beverageId)

{method} + addEmployee(name, surname)
{method} + updateEmp(id,  name,  surname)
{method} + getEmpName(id)
{method} + getEmpSurname(id)
{method} + getEmpBalance(id)
{method} + getIds()
{method} + getMap()

{method} + getBal()
{method} + reset()    
}
}

DataImpl -- DataBase

package GUI{
class LaTazzaView{
    
} 
}

GUI -- DataImpl
LaTazzaException -- DataImpl

note "DataBase is used to interact with SQLite Database" as n1

```
**DataImpl**

| Name | Description |
| ------------- |:-------------:|
| sellCapsules(employeeId, beverageId, numberOfCapsules, fromAccount) | updates the general inventory and the list of transactions. Returns sell ID |
| sellCapsulesToVisitor(beverageId, numberOfCapsules) | updates the general inventory and the list of transactions |
| rechargeAccount(employeeId, amountInCents)  | updates the balance of the given employee; returns the updated amount of the account in cents  |
| buyBoxes(beverageId, boxQuantity) | updates the general inventory |
| getEmplRep(Integer employeeId, Date startDate, Date endDate) | returns the list of transactions of the given employee during the given range of dates |
| getRep(Date startDate, Date endDate) | returns the list of all transactions during the given range of dates  |
| createBeverage() | inserts new beverage; returns beverage ID |
| checkBeverageId(Integer beverageId) | function to check the number of beverages |
| getBeverageCapsules(beverageId) | returns the number of capsules given the beverage ID |
| getBeverageName(beverageId) | returns the name of the capsule given the beverage ID |
| getBevarageId(beverageName) | returns the beverage ID given the name of the bevarage |
| getBeverageCapsulesPerBox(beverageId) | returns the number of capsules per box given the beverage ID |
| getBeverageBoxPrice(beverageId) | returns the box price given the beverage ID |
| updateBeverage(beverageId, name, capsulesPerBox, boxPrice) | updates the given beverage in the inventory |
| getBeveragesId() | returns the list of all beverages' IDs |
| getBeverages() | returns the list of all beverages names |
| createEmployee() | inserts new employee; returns employee ID |
| updateEmployee(employeeId, name, surname) | updates tha data of the given employee |
| getEmployeeName(employeeId) | returns the name of the given employee |
| getEmployeeId(name, surname) | returns the employee ID given name and surname of the employee |
| getEmployeeSurname(employeeId) | returns the surname of the given employee |
| getEmployeeBalance(employeeId) | returns the balance of the given employee |
| getEmployeesId() | returns the list of all employees' IDs |
| getEmployees() | returns the map of all employees' IDs and their names and surnames |
| getBalance() | returns total balance |
| reset() | clears all data structures and restores initial status of the application |

**DataBase**

There is a 1-1 correspondance between the functions in DataImpl and in Database. 

# Verification traceability matrix

|  | LaTazzaView | DataImpl  | DataBase |  
| ------------- |:-------------:|:-----:| :-----:|
| FR1  | X | X | X |
| FR2  | X | X | X |
| FR3  | X | X | X |
| FR4  | X | X | X |
| FR5  | X | X | X | 
| FR6  | X | X | X | 
| FR7  | X | X | X | 
| FR8  | X | X | X |

We didn't build the traceability matrix for the exceptions because they are implemented for robustness and not for satisfying functional requirements.

# Verification sequence diagrams 
## Scenario 1: Colleague uses one capsule of type T
Assumption: beverages have unique names; an Employee is uniquely identified by his/her name and surname.
The following sequence diagram displays both the cases in which the account of the employee results to be positive or negative at the end of the transaction.
```plantuml
actor Administrator
participant LaTazzaView
participant DataImpl
participant DataBase
participant LaTazzaException
autonumber

Administrator -> LaTazzaView: Sell capsules
Administrator -> LaTazzaView: select 'Buy credits' (y/n)
Administrator -> LaTazzaView: select 'Employee'
Administrator -> LaTazzaView: select 'Beverage'
Administrator -> LaTazzaView: select 'Number of Capsules'
Administrator -> LaTazzaView: click 'Sell'


critical try
    LaTazzaView -> DataImpl: sellCapsules(employeeId, beverageId, numberOfCapsules, fromAccount)
end
DataImpl --> DataBase: sellCap(employeeId, beverageId, numberOfCapsules, fromAccount)
DataBase --> DataImpl: balance
alt balance == -3
 DataImpl -->  LaTazzaException: throw EmployeeException
else balance == -2
    DataImpl -->  LaTazzaException: throw NotEnoughCapsules
else balance == -1
    DataImpl -->  LaTazzaException: throw BeverageException
    
else balance > 0
    DataImpl --> LaTazzaView : balanceUpdate
    LaTazzaView --> Administrator: show success message
end
    
opt catch
    LaTazzaView -> LaTazzaException: throw BeverageException
    LaTazzaView -> LaTazzaException: throw EmployeeException
    LaTazzaView -> LaTazzaException: throw NotEnoughCapsules
    LaTazzaView -> Administrator: show error message
end


    
```

