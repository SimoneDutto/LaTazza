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

m <-- (Manage Capsule Purchase)

(Manage Capsule Purchase) .> (Sell Capsules to Clients): include
(Manage Capsule Purchase) .> (Buy Boxes of Capsules): include
(Manage Capsule Purchase) .> (Manage Credit and Debt of the Employees): include
(Manage Capsule Purchase) .> (Check the Inventory): include
(Manage Capsule Purchase) .> (Check the Cash Account): include

(Manage Credit and Debt of the Employees) .> (Record Payments): include

u <-- (Buy Capsules)

(Buy Capsules) .> (Pay with Cash): include
(Buy Capsules) .> (Pay through Account): include

s <-- (Sell Capsules to Manager)
```


## Use Cases
1. Buy Capsules from Supplier
2. Sell Capsules to User
3. Manage Capsule Purchase

### Use case 1, UC1
| Actors Involved        | Manager, Supplier |
| ------------- |:-------------:| 
|  Precondition     | The manager has to order n boxes of a certain type of beverage |  
|  Post condition     | The order has been successful |
|  Nominal Scenario     | The manager selects the number of boxes and the type of beverage and sends the order |
|  Variants     | If there is insufficient money an error is displayed and the order is rejected |

### Use case 2, UC2
| Actors Involved        | Manager, User |
| ------------- |:-------------:| 
|  Precondition     | There is a pending request done by a user |  
|  Post condition     | The request has been satisfied |
|  Nominal Scenario     | The manager reads the user's request and makes the order selecting the beverage type, number of capsule and payment type (by cash or credits) |
|  Variants     | If there is insufficient money (or credits) an error is displayed and the order is rejected |

### Use case 3, UC3
| Actors Involved        | Manager |
| ------------- |:-------------:| 
|  Precondition     | An order has been completed |  
|  Post condition     | The inventory has been updated |
|  Nominal Scenario     | The manager updates the quantities of avaible capsules in the inventory and records the payment |


# Relevant scenarios
State at which UC the scenario refers to
\<a scenario is a sequence of steps that corresponds to a particular execution of one use case>
\<a scenario is more formal description of a story>
\<only relevant scenarios should be described>

## Scenario 1

| Scenario ID: SC1        | Corresponds to UC:  |
| ------------- |:-------------:| 
| Step#        | Description  |
|  1     |  |  
|  2     |  |
|  ...     |  |

## Scenario 2

...

# Glossary

\<use UML class diagram to define important concepts in the domain of the system, and their relationships>  <concepts are used consistently all over the document, ex in use cases, requirements etc>

# System Design
\<describe here system design> <must be consistent with Context diagram>
