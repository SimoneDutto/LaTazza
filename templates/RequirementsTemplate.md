# Requirements Document Template

Authors: Simone Dutto, Isabella Romita, Vito Tassielli, Debora Caldarola

Date: 27/03/2019

Version: 1.0

# Contents

- [Stakeholders](#stakeholders)
- [Context Diagram and interfaces](#context-diagram-and-interfaces)
	+ [Context Diagram](#context-diagram)
	+ [Interfaces](#interfaces) 
	
- [Stories and personas](#stories-and-personas)
- [Functional and non functional requirements](#functional-and-non-functional-requirements)
	+ [Functional Requirements](#functional-requirements)
	+ [Non functional requirements](#non-functional-requirements)
- [Use case diagram and use cases](#use-case-diagram-and-use-cases)
	+ [Use case diagram](#use-case-diagram)
	+ [Use cases](#use-cases)
	+ [Relevant scenarios](#relevant-scenarios)
- [Glossary](#glossary)
- [System design](#system-design)

# Stakeholders


| Stakeholder name  | Description | 
| ----------------- |:-----------:|
| Manager           | Manage capsules purchases and sales and employees accounts  |
| Employees | Buy capsules with their account or in cash |
| Visitors | Buy capsules in cash |
| Capsules Supplier| Supply capsules ordered by manager|
| Credit Card System | Handle payment of Manager to capsules supplier and payment of employees to manager |


# Context Diagram and interfaces
## Context Diagram
```plantuml
left to right direction
skinparam packageStyle rectangle

Actor Manager as m
Actor User as u
Actor "Credit Card System" as cc
Actor "Capsules Supplier" as cs

rectangle system {
  (LaTazza) as lt
  m -- lt
  u -- lt
  lt -- cc
  lt -- cs
}
note "Users are those who consume capsules, so Employee and Visitors" as n
```
## Interfaces
| Actor | Logical Interface | Physical Interface  |
| ------------- |:-------------:| -----:|
| Manager | GUI | Screen and keyboard |
| User | GUI | Screen and keyboard |
| Credit Card System | API to manage payments | Internet |
| Capsule supplier | API to place orders | Internet |

# Stories and personas

### Persona 1
<img src="Mary.jpg" width="200"><br/>
Mary is employed at Idlab, a company that uses LaTazza system. She is middle aged, married and has two sons. They are five and eight.   
Every morning she drives her children to school and then goes to work: that usually takes her forty minutes. When she gets to the office, she is usually already tired so a coffee is the first thing she thinks of!
Her workday usually implies dealing with people and giving speeches. That is what brings her to use capsules a lot: a cup of coffee or tea is always a good way to get along with someone else! 
At the same time, her payroll does not allow her to exploit those occasions as much as she wished.  
Once she finishes working, she goes back home, where her children and husband waits for her to dine all together.

### Persona 2
<img src="Josh.jpg" width="200"><br/>
John is the manager of Idlab, a company that uses LaTazza system. He lives with his girlfriend, with whom he usually argues a lot because he is never home with her.   
He always gets to the office before than anyone else to enjoy his coffee in peace. His wish is to create a friendly environment at work for all his employees. That is why he decided to introduce LaTazza system inside
the office: he would like to see relationships growing among his employees. Even if that takes time to be managed he does not mind doing it, for his collegues' sake.  
His workday ends very late: once he is home is so tired he goes immediately to sleep.

### Persona 3
<img src="John.jpg" width="200"><br/>
Josh is Mary's husband. He is fifty and loves his family. He is a High School teacher and his school is near Idlab. He teaches English and is passionate about literature.  
He sometimes visits his wife at work to say hi when he has some spare time. They usually have a cup of coffee together and chat for a few minutes. 


# Functional and non functional requirements

## Functional Requirements

| ID        | Description  |
| ------------- |:-------------:| 
|  FR1     | Set up manager account |
|  FR2     | Set up employees account |  
|  FR3     | Set up cash account for employees |
|  Fr4     | Set up cash account for the company |
|  FR5     | Authenticate account |
|  FR6     | Log in |
|  FR7     | Log out |
|  FR8     | Add employee |
|  FR9     | Define manager authorization level |
|  FR10    | Define user authorization level |
|  FR11    | Show summary |
|  FR12    | Show employees names |
|  FR13    | Show sells |
|  FR14    | Start sale transaction |
|  FR15    | End sale transaction |
|  FR16    | Delete transaction |
|  FR17    | Store transactions in database |
|  FR18    | Store incomes in database |
|  FR19    | Store outcomes in database |
|  FR20    | Store inventory in database |
|  FR21    | Manager buys supplies of capsules |
|  FR22    | Management of credits and debts |
|  FR23    | Manager checks inventory |
|  FR24    | Employee buys capsules |
|  FR25    | Visitor buys capsules |
|  FR26    | Payment of debts by employees |
|  FR27    | Reject order in case of error |


## Non Functional Requirements

| ID        | Type (efficiency, reliability, ..)           | Description  | Refers to |
| ------------- |:-------------:| :-----:| -----:|
|  NFR1     | Usability | Default language is English | FR\<6>, FR\<7>, FR\<11>, FR\<12>, FR\<13> |
|  NFR2     | Portability | Application runs on Windows, UNIX/Linux and MacOS systems | FR\<1>, FR\<2> |
|  NFR3     | Domain | Currency is € | FR\<13>, FR\<14>, FR\<15>, FR\<16>, FR\<17>, FR\<18>, FR\<19> |
|  NFR4     | Usability | Manager sets up accounts for employees | FR\<2> |
|  NFR5     | Usability | User can buy credits to purchase capsules | FR\<24> |
|  NFR6     | Usability | Employees can buy with cash or using their credits | FR\<24>|
|  NFR7     | Usability | Visitors can only buy capsules with cash | FR\<25>|
|  NFR8     | Usability | Users may contract debts and pay them off later | FR\<24> |
|  NFR9     | Usability | Manager manages credits and debts through his interface | FR\<22> |
|  NFR10    | Privacy | Users have access to their data only | FR\<10>|
|  NFR11    | Privacy | Manager has access to cash account, inventory and users info | FR\<9>|
|  NFR12    | Platform constraints | Manager interface allows handling of sells, payments of credits/debts, buyings from suppliers, inventory | FR\<1>, FR\<9>, FR\<21>, FR\<22>, Fr\<23> |
|  NFR12    | Platform constraints | Different kind of capsules may be bought (Coffee, Arabic coffee, Tea, Lemon-tea, Camomile-tea) | FR\<21>, FR\<24>, FR\<25> |
|  NFR13    | Platform constraints| The inventory shows the total amount of bought capsules per type and the remaining ones | FR\<20>, FR\<23> |
|  NFR14    | Platform constraints | The inventory may be showed within a chosen date | FR\<20>, FR\<23> |
|  NFR15    | Platform constraints | Display errors in case of insufficient money or credits and reject order | FR\<27> |
|  NFR16    | Efficiency | Each transaction takes less than 1/2 sec | FR\<14>, FR\<15>, FR\<17>|
|  NFR17    | Data Integrity | Each transaction is committed to the database | FR\<17> |
|  NFR18    | Data Integrity | Inventory is updated when an order or supply of boxes is received | FR\<20>, FR\<23> |
|  NFR19    | Reliability | In case of system failures data is retrieved using a log file | FR\<17>, FR\<18>, FR\<19>, FR<20> |

# Use case diagram and use cases


## Use case diagram
```plantuml

left to right direction
skinparam packageStyle rectangle

actor Manager as m
actor User as u
actor "Capsule Supplier" as s
actor "CreditCard System" as c

(Buy Boxes from Supplier) <-- m
s <-- (Buy Boxes from Supplier)

u --> (Buy Capsules from Manager)
(Sell Capsules to User) --> m

(Buy Boxes from Supplier) --> c
(Buy Capsules from Manager) --> c
(Sell Capsules to User) --> c

(Sell Capsules to User) .> (Check the Inventory): include
(Sell Capsules to User) .> (Check the Cash Account): include
(Sell Capsules to User) .> (Manage Credit and Debt of the Employees): include


(Record Payments) <. (Manage Credit and Debt of the Employees): include


(Buy Capsules from Manager) .> (Pay by Cash or by Credits): include
note "If the User is a visitor the payment can be done only by cash" as n
(Pay by Cash or by Credits) -- n

```


## Use Cases
1. Buy Boxes from Supplier
2. Sell Capsules to User

### Use case 1, Buy Boxes from Supplier
| Actors Involved        | Manager, Supplier |
| ------------- |:-------------:| 
|  Precondition     | The manager has to order n boxes of a certain type of beverage |  
|  Post condition     | The manager has received the boxes, the inventory has been updated and the supplier has received the money |
|  Nominal Scenario     | The manager selects the number of boxes and the type of beverage and sends the order |
|  Variants     | If there is insufficient money an error is displayed and the order is rejected |

### Use case 2, Sell Capsules to User
| Actors Involved        | Manager, User |
| ------------- |:-------------:| 
|  Precondition     | There is a pending request done by a user |  
|  Post condition     | The user has received the capsules, the inventory has been updated and the manager has received the money |
|  Nominal Scenario     | The manager reads the user's request and makes the order selecting the beverage type, number of capsule and payment type (by cash or credits) |
|  Variants     | If there is insufficient money (or credits) or capsules an error is displayed and the order is rejected |


# Relevant scenarios
## Scenario 1 : Buy boxes

| Scenario ID: SC1        | Corresponds to UC: Buy Boxes from Supplier |
| ------------- |:-------------:| 
| Step#        | Description  |
|  1     | The manager chooses the number of boxes |  
|  2     | The manager chooses the type of beverage |
|  3     | The manager confirms the order clicking the "Buy" button |
|  4     | The capsule supplier receives the order and the money |
|  5     | The capsule supplier delivers the boxes  |
|  6     | The manager receives the boxes |
|  7     | The inventory is updated |

## Scenario 2 : Sell Capsules to an Employee

| Scenario ID: SC2        | Corresponds to UC: Sell Capsules to User |
| ------------- |:-------------:| 
| Step#        | Description  |
|  1     | The manager receives the capsule order from the Employee |  
|  2     | The manager checks the inventory to see if there is enough quantity of the ordered capsules |
|  3     | The manager checks the cash account of the Employee (through the account) |
|  4     | The manager selects the payment method (indicated from the Employee), the user name, beverage type and quantity and clicks the "Sell" button |
|  5     | The inventory is updated |

## Scenario 3 : Sell Capsules to a Visitor

| Scenario ID: SC3        | Corresponds to UC: Sell Capsules to User |
| ------------- |:-------------:| 
| Step#        | Description  |
|  1     | The manager takes the capsule order from the Visitor |  
|  2     | The manager checks the inventory to see if there is enough quantity of the ordered capsules |
|  3     | The manager receives the money from the Visitor (by cash) |
|  4     | The manager delivers the capsules to the Visitor  |
|  5     | The inventory is updated |

# Glossary
```plantuml

class Employee{
  ID
}

class Manager{
  ID
  name
  surname
}

class User{
  name
  surname
}

class Visitor{
}

class Supplier{
  ID
  name
  surname
}

class Account{
  ID
  password
  availableCredits
}

class CreditCard{
  IBAN
  expirationDate
  balance
}

class Inventory{
  productID
  availableQty
}

User <|-- Employee
User <|-- Visitor
User "*" -- "1..*" Manager
Employee "1"--"1" Account
Account "1"-- "1" CreditCard
Manager "1..*"--"1..*" Inventory
Manager "*"--"1..*" Supplier
Manager "1"--"1" Account

note "the Manager is also an Employee, but since he is not a User we decided to separate them" as n
n -- Manager

```

# System Design
```plantuml
@startuml
class LaTazzaSystem{
}
class Server{
 +authenticateUser()
}
class ServerUser{
 +buyCapsules()
}
class ServerManager{
 +sellCapsules()
 +checkInventory()
 +buyCapsules()
 +checkCash()
 +manageCredits()
 +manageDebts()
}
class Inventory{
}


LaTazzaSystem o-- Server
LaTazzaSystem o-- Inventory
Server <|-- ServerManager
Server <|-- ServerUser
note "Server receives requests and grants permissions to access functions" as n
@enduml
```
```
