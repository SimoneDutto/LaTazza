# Design Document Template

Authors: Vito Tassielli, Isabella Romita, Simone Dutto, Debora Caldarola

Date: 07/06/2019

Version: 4.0

Change history

| Version | Changes | 
| ----------------- |:-----------|
| 4 | Added methods in DataBase to handle old price of capsules |
| 3 | Added DataInterface in Class Diagram |
| 2 | Modified class implementation |
| | Insertion of DataBase class to handle interaction with SQL Lite databases |
| | Deleted unused classes |

# Contents

- [Package diagram](#package-diagram)
- [Class diagram](#class-diagram)
- [Verification traceability matrix](#verification-traceability-matrix)
- [Verification sequence diagrams](#verification-sequence-diagrams)

# Instructions

The design document has to comply with:
1. [Official Requirement Document](../Official\ Requirements\ Document.md)
2. [DataInterface.java](../src/main/java/it/polito/latazza/data/DataInterface.java)

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
interface DataInterface {

{method} + Integer sellCapsules(employeeId, beverageId, numberOfCapsules, fromAccount)
{method} + void sellCapsulesToVisitor(beverageId, numberOfCapsules)
{method} + Integer rechargeAccount(employeeId, amountInCents) 
{method} + void buyBoxes(beverageId, boxQuantity)
{method} + List<String> getEmployeeReport(employeeId, startDate, endDate)
{method} + List<String> getReport(startDate, endDate)
{method} + Integer createBeverage()
{method} + Integer getBeverageCapsules(beverageId)
{method} + String getBeverageName(beverageId)
{method} + Integer getBeverageCapsulesPerBox(beverageId)
{method} + Integr getBeverageBoxPrice(beverageId)
{method} + void updateBeverage(beverageId, capsulesPerBox, boxPrice)
{method} + List<String> getBeveragesId()
{method} + Map<Integer, String> getBeverages()
{method} + Integer createEmployee()
{method} + void updateEmployee(employeeId, name, surname)
{method} + String getEmployeeName(employeeId)
{method} + String getEmployeeSurname(employeeId)
{method} + Integer getEmployeeBalance(employeeId)
{method} + List<String> getEmployeesId()
{method} + Map<Integer, String> getEmployees()
{method} + Integer getBalance()
{method} + void reset()
    
}

class DataImpl {

{method} + DataImpl()
{method} + DataImpl(name_db)

{method} + Integer sellCapsules(employeeId, beverageId, numberOfCapsules, fromAccount)
{method} + void sellCapsulesToVisitor(beverageId, numberOfCapsules)
{method} + Integer rechargeAccount(employeeId, amountInCents) 
{method} + void buyBoxes(beverageId, boxQuantity)
{method} + List<String> getReport(startDate, endDate)
{method} + Integer getBalance()

{method} + Integer createBeverage()
{method} + Integer getBeverageCapsules(beverageId)
{method} + String getBeverageName(beverageId)
{method} + Integer getBevarageId(beverageName)
{method} + Integer getBeverageCapsulesPerBox(beverageId)
{method} + Integer getBeverageBoxPrice(beverageId)
{method} + void updateBeverage(beverageId, capsulesPerBox, boxPrice)
{method} + List<String> getBeveragesId()
{method} + Map<Integer, String> getBeverages()

{method} + Integer createEmployee()
{method} + void updateEmployee(employeeId, name, surname)
{method} + String getEmployeeName(employeeId)
{method} + String getEmployeeSurname(employeeId)
{method} + Integer getEmployeeBalance(employeeId)
{method} + Integer getEmployeeId(name, surname)
{method} + List<String> getEmployeesId()
{method} + Map<Integer, String> getEmployees()
{method} + List<String> getEmployeeReport(employeeId, startDate, endDate)

{method} + void reset()
}

class DataBase{
{static} DataBase instance
- String dbname
- Connection connection

{method} - DataBase()

{method} - void connect()
{method} + void change_name_db(new_name_db)
{method} {static} + DataBase getInstance()
{method} + void createDatabase()

{method} + int sellCap(employeeId, beverageId, numberOfCapsules,fromAccount)
{method} + int sellVis(beverageId, numberOfCapsules)
{method} + int recharge(id, amountInCents)
{method} + void buyB(beverageId,  boxQuantity)
{method} + List<String> getEmplRep( employeeId, startDate, endDate)
{method} + List<String> getRep(startDate, endDate)

{method} + Integer addBeverage(name, capsulesPerBox, boxPrice)
{method} + boolean beverageIsDuplicate(name)
{method} + Integer updateBeverage( id, String name, capsulesPerBox, boxPrice)
{method} + String getBeverageName(beverageId)
{method} + Integer getBeverageBoxInformation(beverageId, requiredInformation)
{method} + List<String> getBeverageIds() 
{method} + Map<Integer, String> getBeverages()
{method} + Integer getBeverageAvailableCapsules(beverageId)
{method} + void checkBeverageId(BeverageId)
{method} + Integer getBeverageNumberOfOldCapsules(beverageId)
{method} + Integer getBeverageOldCapsulesPrice(beverageId)
 
{method} + int addEmployee(name, surname)
{method} + boolean employeeIsDuplicate(name, surname)
{method} + int updateEmp(id,  name,  surname)
{method} + int getEmpName(id)
{method} + String getEmpSurname(id)
{method} + int getEmpBalance(id)
{method} + List<String> getIds()
{method} + Map<Integer, String> getMap()
{method} + checkEmp(EmployeeId)

{method} + int getBal()
{method} + void resetDatabase()    
}
}

DataInterface <|-- DataImpl 
DataImpl -- DataBase

package GUI{
class LaTazzaView{
    
} 
}

GUI -- LaTazzaData
LaTazzaException -- LaTazzaData

note "DataBase is used to interact with SQLite Database" as n1

```
**DataImpl**

| Name | Description |
| ------------- |:-------------:|
| Integer sellCapsules(employeeId, beverageId, numberOfCapsules, fromAccount) | updates the general inventory and the list of transactions. Returns sell ID |
| void sellCapsulesToVisitor(beverageId, numberOfCapsules) | updates the general inventory and the list of transactions |
| Integer rechargeAccount(employeeId, amountInCents)  | updates the balance of the given employee; returns the updated amount of the account in cents  |
| void buyBoxes(beverageId, boxQuantity) | updates the general inventory |
| List<String> getEmployeeReport(Integer employeeId, Date startDate, Date endDate) | returns the list of transactions of the given employee during the given range of dates |
| List<String> getReport(Date startDate, Date endDate) | returns the list of all transactions during the given range of dates  |
| Integer createBeverage() | inserts new beverage; returns beverage ID |
| Integer getBeverageCapsules(beverageId) | returns the number of capsules given the beverage ID |
| String getBeverageName(beverageId) | returns the name of the capsule given the beverage ID |
| Integer getBevarageId(beverageName) | returns the beverage ID given the name of the bevarage |
| Integer getBeverageCapsulesPerBox(beverageId) | returns the number of capsules per box given the beverage ID |
| Integer getBeverageBoxPrice(beverageId) | returns the box price given the beverage ID |
| void updateBeverage(beverageId, name, capsulesPerBox, boxPrice) | updates the given beverage in the inventory |
| List<String> getBeveragesId() | returns the list of all beverages' IDs |
| Map<Integer, String> getBeverages() | returns the list of all beverages names |
| Integer createEmployee() | inserts new employee; returns employee ID |
| void updateEmployee(employeeId, name, surname) | updates tha data of the given employee |
| String getEmployeeName(employeeId) | returns the name of the given employee |
| Integer getEmployeeId(name, surname) | returns the employee ID given name and surname of the employee |
| String getEmployeeSurname(employeeId) | returns the surname of the given employee |
| Integer getEmployeeBalance(employeeId) | returns the balance of the given employee |
| List<String> getEmployeesId() | returns the list of all employees' IDs |
| Map<Integer, String> getEmployees() | returns the map of all employees' IDs and their names and surnames |
| Integer getBalance() | returns total balance |
| void reset() | clears all data structures and restores initial status of the application |

**DataBase**

| Name | Description |
| ------------- |:-------------:|
| beverageIsDuplicate(name) | checks if the beverage with given name already exists in the database |
| employeeIsDuplicate(name, surname) | checks if the employee with given name and surname already exists in the database |
| checkBeverageId(BeverageId) | checks if the given beverage ID exists in the database |
| checkEmp(EmployeeId) | checks if the given employee ID exists in the database |
|getBeverageNumberOfOldCapsules(beverageId) |returns the quantity of remaining capsules having old price |
|getBeverageOldCapsulesPrice(beverageId) |returns the old price per capsule |

As for the other methods, there is a 1-1 correspondance between the functions in DataImpl and the ones in Database. 

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

