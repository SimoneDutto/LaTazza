# Requirements Document Template

Authors: Simone Dutto

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
\<A Persona is a realistic impersonation of an actor. Define here a few personas and describe in plain text how a persona interacts with the system>

\<Persona is-an-instance-of actor>  \<stories will be formalized later as use cases>


# Functional and non functional requirements

## Functional Requirements

\<In the form DO SOMETHING, or VERB NOUN, describe high level capabilities of the system> <will match to high level use cases>

| ID        | Description  |
| ------------- |:-------------:| 
|  FR1     |  |  
|  FR2     |  |
|  ...     |  |

## Non Functional Requirements

\<Describe constraints on functional requirements>

| ID        | Type (efficiency, reliability, ..)           | Description  | Refers to |
| ------------- |:-------------:| :-----:| -----:|
|  NFR1     |  |  | FR\<x>|
|  NFR2     |  |  | FR\<y>|
|  ...     |  |  | FR\<x>|


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

### Use case 1, UC1
| Actors Involved        | Manager, Supplier |
| ------------- |:-------------:| 
|  Precondition     | The manager has to order n boxes of a certain type of beverage |  
|  Post condition     | The manager has received the boxes, the inventory has been updated and the supplier has received the money |
|  Nominal Scenario     | The manager selects the number of boxes and the type of beverage and sends the order |
|  Variants     | If there is insufficient money an error is displayed and the order is rejected |

### Use case 2, UC2
| Actors Involved        | Manager, User |
| ------------- |:-------------:| 
|  Precondition     | There is a pending request done by a user |  
|  Post condition     | The user has received the capsules, the inventory has been updated and the manager has received the money |
|  Nominal Scenario     | The manager reads the user's request and makes the order selecting the beverage type, number of capsule and payment type (by cash or credits) |
|  Variants     | If there is insufficient money (or credits) or capsules an error is displayed and the order is rejected |


# Relevant scenarios
## Scenario 1

| Scenario ID: SC1        | Corresponds to UC: UC1 |
| ------------- |:-------------:| 
| Step#        | Description  |
|  1     | The manager chooses the number of boxes |  
|  2     | The manager chooses the type of beverage |
|  3     | The manager confirms the order clicking the "Buy" button |
|  4     | The capsule supplier receives the order and delivers the boxes |
|  5     | The inventory is updated |

## Scenario 2

| Scenario ID: SC2        | Corresponds to UC: UC2 |
| ------------- |:-------------:| 
| Step#        | Description  |
|  1     | The manager receives the capsule order from the user |  
|  2     | The manager checks the inventory to see if there is enough quantity of the ordered capsules |
|  3     | The manager checks the cash account of the user |
|  4     | The manager selects the payment method, the user name, beverage type and quantity and clicks the "Sell" button |
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
\<describe here system design> <must be consistent with Context diagram>
