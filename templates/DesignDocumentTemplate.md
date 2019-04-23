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
package LaTazzaLogic
package LaTazzaException

note "One package containting the application logic, one containing exceptions and one package for the View" as n

```


# Class diagram
We implement the *MVC Model*, so the LaTazza View can be changed in future and the application model will remain the same with a lot of time saved.

```plantuml
package LaTazzaException{
 class BeverageException 
 class DateException 
 class EmployeeException 
 class NotEnoughBalance 
 class NotEnoughCapsules 
}

package LaTazzaLogic{
class LaTazzaLogic {
- employees "map of [employeeId, Employee]"
- beverages "map of [beverageId, Beverage]"
- rechages "map of [employeeId, Recharge]"
- sells "map of [employeeId, Sell]"

{method} + sellCapsules(employeeId, beverageId, numberOfCapsules, fromAccount)
{method} + sellCapsulesToVisitor(beverageId, numberOfCapsules)
{method} + rechargeAccount(employeeId, amountInCents) 
{method} + buyBoxes(beverageId, boxQuantity)
{method} + getEmployeeReport(employeeId, startDate, endDate)
{method} + getReport(startDate, endDate)

{method} + getBeverageCapsules(beverageId)
{method} + getBeverageName(beverageId)
{method} + getBevarageId(beverageName)
{method} + getBeverageCapsulesPerBox(beverageId)
{method} + getBeverageBoxPrice(beverageId)
{method} + updateBeverage(beverageId, capsulesPerBox, boxPrice)
{method} + getBeveragesId()
{method} + getBeverages()

{method} + updateEmployee(employeeId, name, surname)
{method} + getEmployeeName(employeeId)
{method} + getEmployeeSurname(employeeId)
{method} + getEmployeeBalance(employeeId)
{method} + getEmployeeId(name, surname)
{method} + getEmployeesId()
{method} + getEmployees()
}

class Beverage {
- name
- boxPrice
- capsulesPerBox
- quantity

{method} + Beverage(name, capsulesPerBox, boxPrice)
{method} + getBeverageName()
{method} + getBoxPrice()
{method} + getCapsulesPerBox()
{method} + getQuantity()
{method} + getPricePerCapsule()
{method} + updateQuantity(numberOfCapsules)
}

class Employee {
- name
- surname
- balance

{method} + Employee(name, surname)
{method} + getName()
{method} + getSurname()
{method} + getBalance()
{method} + updateBalance(amount)
}

class LaTazzaAccount {
- username
- password

{method} + Account(username, password)
{method} + getUsername()
{method} + getPassword()
}

class Sell{
- beverageId
- numberOfCapsules
- date
{method} + Sell(beverageId, numberOfCapsules, date)
{method} + getBeverageId()
{method} + getNumberOfCapsules()
{method} + getDate()
}

class Recharge {
- date
- amount
{method} + Recharge(type, date, amount)
{method} + getDate()
{method} + getAmount()
}

LaTazzaLogic -- LaTazzaAccount
LaTazzaLogic -- Employee
LaTazzaLogic -- Recharge
LaTazzaLogic -- Beverage
LaTazzaLogic -- Sell
}

package GUI{
class LaTazzaView{
    
} 
}

GUI -- LaTazzaLogic
LaTazzaException -- LaTazzaLogic




```

| Name | Description |
| ------------- |:-------------:|
| sellCapsules(employeeId, beverageId, numberOfCapsules, fromAccount) | updates the general inventory and the list of transactions |
| sellCapsulesToVisitor(beverageId, numberOfCapsules) | updates the general inventory and the list of transactions |
| rechargeAccount(employeeId, amountInCents)  | updates the balance of the given employee |
| buyBoxes(beverageId, boxQuantity) | updates the general inventory |
| getEmployeeReport(employeeId, startDate, endDate) | returns the list of transactions of the given employee during the given range of dates |
| getReport(startDate, endDate) | returns the list of all transactions during the given range of dates  |
| getBeverageCapsules(beverageId) | returns the number of capsules given the beverage ID |
| getBeverageName(beverageId) | returns the name of the capsule given the beverage ID |
| getBevarageId(beverageName) | returns the beverage ID given the name of the bevarage |
| getBeverageCapsulesPerBox(beverageId) | returns the number of capsules per box given the beverage ID |
| getBeverageBoxPrice(beverageId) | returns the box price given the beverage ID |
| updateBeverage(beverageId, name, capsulesPerBox, boxPrice) | updates the given beverage in the inventory |
| getBeveragesId() | returns the list of all beverages' IDs |
| getBeverages() | returns the list of all beverages names |
| updateEmployee(employeeId, name, surname) | updates tha data of the given employee |
| getEmployeeName(employeeId) | returns the name of the given employee |
| getEmployeeId(name, surname) | returns the employee ID given name and surname of the employee |
| getEmployeeSurname(employeeId) | returns the surname of the given employee |
| getEmployeeBalance(employeeId) | returns the balance of the given employee |
| getEmployeesId() | returns the list of all employees' IDs |
| getEmployees() | returns the map of all employees' IDs and their names and surnames |
| Beverage(name, capsulesPerBox, boxPrice) | creates a new type of beverage |
| getBeverageName() | returns the name of the beverage |
| getBoxPrice() | returns the box price of the beverage |
| getCapsulesPerBox() | returns the number of cupsules per box of the beverage |
| getQuantity() | returns the available quantity of the beverage |
| getPricePerCapsule() | returns the prince per single capsule of the beverage |
| updateQuantity(numberOfCapsules) | updates the total amount of capsules of the beverage |
| Employee(name, surname) | creates a new employee |
| getName() | returns the name of the employee |
| getSurname() | returns the surname of the employee |
| getBalance() | returns the balance of the employee |
| updateBalance(amount) | updates the balance of the employee  |
| recordEmployeeTransaction(date, amount) | updates the list of transactions of the employee |
| Account(username, password) | creates a new account |
| getUsername() | returns the username of the account |
| getPassword() | returns the password of the account |
| Sell(beverageId, numberOfCapsules, date)| create Sell object |
| getBeverageId() | returns the beverageId |
| getNumberOfCapsules() | returns the number of capsules |
| getDate() | returns the date |
| Recharge(date, amount)| create Recharge object |
| getAmount | returns the amount |
| getDate() | returns the date |

# Verification traceability matrix

|  | LaTazzaView | LaTazzaLogic  | Employee | LaTazzaAccount | Beverage | Recharge | Sell | 
| ------------- |:-------------:|:-----:|:-----:|:-----:|:-----:|:-----:|:-----:|
| FR1  | X | X | X |  | X |   | X |
| FR2  | X | X |   |  | X |   |  |
| FR3  | X | X | X |  |  | X |  |
| FR4  | X | X | X |  |  |  | X |
| FR5  | X | X | X |  |   | X | X |
| FR6  | X | X |   |  |   | X | X |
| FR7  | X | X |  |    | X |  |  |
| FR8  | X | X | X | X | | |  |

We didn't build the traceability matrix for the exceptions because they are implemented for robustness and not for satisfying functional requirements.

# Verification sequence diagrams 
\<select key scenarios from the requirement document. For each of them define a sequence diagram showing that the scenario can be implemented by the classes and methods in the design>
## Scenario 1: Colleague uses one capsule of type T
Assumption: beverages have unique names; an Employee is uniquely identified by his/her name and surname.
The following sequence diagram displays both the cases in which the account of the employee results to be positive or negative at the end of the transaction.
```plantuml
actor Administrator
participant LaTazzaView
participant LaTazzaLogic
participant Employee
participant Beverage
participant Sell
participant LaTazzaException
autonumber

Administrator -> LaTazzaView: Sell capsules
Administrator -> LaTazzaView: select 'Buy credits' (y/n)
Administrator -> LaTazzaView: select 'Employee'
Administrator -> LaTazzaView: select 'Beverage'
Administrator -> LaTazzaView: select 'Number of Capsules'
Administrator -> LaTazzaView: click 'Sell'

LaTazzaView -> LaTazzaLogic: fromAccount
LaTazzaView -> LaTazzaLogic: employeeName, employeeSurname
LaTazzaView -> LaTazzaLogic: beverageName
LaTazzaView -> LaTazzaLogic: numberOfCapsules

critical try 
    LaTazzaLogic -> LaTazzaLogic:getEmployeeId(name, surname)
end
opt catch 
    LaTazzaLogic -> LaTazzaException: throw EmployeeException
end

critical try
    LaTazzaLogic -> LaTazzaLogic: getBevarageId(bevarageName)
end
opt catch
    LaTazzaLogic -> LaTazzaException: throw BeverageException
end

critical try
    LaTazzaLogic -> Beverage: getPricePerCapsule()
    Beverage --> LaTazzaLogic: price
end
opt catch
    LaTazzaLogic -> LaTazzaException: throw BeverageException
end

critical try
    LaTazzaLogic -> Employee: getBalance()
Employee --> LaTazzaLogic: balance
end
opt catch
    LaTazzaLogic -> LaTazzaException: throw EmployeeException
end

alt balance-price*numberOfCapsules>0
    critical try
    LaTazzaLogic -> LaTazzaLogic: sellCapsules(employeeId, beverageId, numberOfCapsules, fromAccount)
    LaTazzaLogic -> Sell: Sell(beverageId, numberOfCapsules, date)
    Sell --> LaTazzaLogic: sellId
    LaTazzaLogic -> Beverage: getQuantity()
    Beverage --> LaTazzaLogic: quantity
    LaTazzaLogic -> Beverage: updateQuantity(quantity - numberOfCapsules)
    LaTazzaLogic -> Employee: updateBalance(balance-price*numberOfCapsules)
    LaTazzaLogic --> LaTazzaView: return success message
    LaTazzaView --> Administrator: show success message
    end
    opt catch
    LaTazzaLogic -> LaTazzaException: throw EmployeeException, BeverageException, NotEnoughCapsules
    end
    
else issue warning
    LaTazzaLogic --> LaTazzaView: return error message
    LaTazzaView --> Administrator: show error message
end
    
```

