# Acceptance Testing Documentation template

Authors: Vito Tassielli, Isabella Romita, Simone Dutto, Debora Caldarola

Date: 23/5/2019

Version: 1.0

# Contents

- [Scenarios](#scenarios)

- [Coverage of scenarios](#scenario-coverage)
- [Coverage of non-functional requirements](#nfr-coverage)



# Scenarios

| Scenario ID: SC1 | Corresponds to UC1                             |
| ---------------- | ---------------------------------------------- |
| Description      | Colleague uses one capsule of type 1           |
| Precondition     | account of C has enough money to buy capsule T |
| Postcondition    | account of C updated, count of T updated       |
| Step#            | Step description                               |
| 1                | Administrator selects capsule type T           |
| 2                | Administrator selects colleague C              |
| 3                | Deduce one for quantity of capsule T           |
| 4                | Deduce price of T from account of C            |

| Scenario ID: SC2 | Corresponds to UC1                                     |
| ---------------- | ------------------------------------------------------ |
| Description      | Colleague uses one capsule of type T, account negative |
| Precondition     | account of C has not enough money to buy capsule T     |
| Postcondition    | account of C updated, count of T updated               |
| Step#            | Step description                                       |
| 1                | Administrator selects capsule type T                   |
| 2                | Administrator selects colleague C                      |
| 3                | Deduce one for quantity of capsule T                   |
| 4                | Deduce price of T from account of C                    |
| 5                | Account of C is negative, issue warning                |

| Scenario ID: SC3 | Corresponds to UC2 |
| ----------------------- | ------------------ |
| Description      | Visitor uses one capsule of type T |
| Precondition     | Capsule T exists, visitor has no account |
| Postcondition    | T.quantity_post < T.quantity_pre |
|     | LaTazzaAccount.amount_post > LaTazzaAccount.amount_pre |
| Step#            | Step description                           |
| 1                | Administrator selects capsule type T |
| 2                | Administrator selects Visitor |
| 3                | Deduce quantity for capsule T |add price of T on LaTazzaAccount.amount
| 4                | Add price of T on LaTazzaAccount.amount |

| Scenario ID: SC4 | Corresponds to UC3 |
| ---------------- | ------------------ |
| Description      | Colleague C recharge his account A balance               |
| Precondition     | Account A exists                |
| Postcondition    | A.amount_post > A.amount_pre                |
| Step#            | Step description                |
| 1                | Administrator selects account A of colleague C                |
| 2                | Increase account of a certain quantity                   |

| Scenario ID: SC5 | Corresponds to UC4 |
| ---------------- | ------------------ |
| Description      | Administrator buy boxes of capsule type CT |
| Precondition     | Capsule type CT exists                |
| Postcondition    | CT.quantity_post > CT.quantity _pre                |
| Step#            | Step description                |
| 1                | At time of order Administrator records money spent for order                |
| 2                | At time of reception administrator selects capsule type CT                 |
| 3                | Increases its quantity by a given number                |

| Scenario ID: SC6 | Corresponds to UC5 |
| ---------------- | ------------------ |
| Description      | Administrator produce report on consumption of colleague C |
| Precondition     | Colleague C exists                |
| Postcondition    |                 |
| Step#            | Step description                |
| 1                | Administrator selects colleague C                |
| 2                | Defines a time range                   |
| 3                | Application collects all transactions for C (recharges and capsules taken) in the time range and presents them                   |

| Scenario ID: SC7 | Corresponds to UC6 |
| ---------------- | ------------------ |
| Description      | Administrator produce report on all consumptions |
| Precondition     |                 |
| Postcondition    |                 |
| Step#            | Step description                |
| 1                | Administrator defines a time range                |
| 2                | application collects all transactions (recharges, purchases, and capsules taken) in the time range and presents them                 |


| Scenario ID: SC8 | Corresponds to FR7 |
| ---------------- | ------------------ |
| Description      | Administrator create Beverage B |
| Precondition     | Beverage B doesn't exist in database                |
| Postcondition    | Beverage B exists in database                |
| Step#            | Step description                |
| 1                | Administrator writes beverage name                |
| 2                | Administrator writes beverage capsules per box      |
| 3                | Administrator writes beverage price per box      |


| Scenario ID: SC9 | Corresponds to FR8 |
| ---------------- | ------------------ |
| Description      | Administrator create Employee E |
| Precondition     | Employee E doesn't exist in database                |
| Postcondition    | Employee E exists in database                |
| Step#            | Step description                |
| 1                | Administrator writes employee name                |
| 2                | Administrator writes employee surname      |

# Coverage of Scenarios

### 

| Scenario ID | Functional Requirements covered | API Test(s) | GUI Test(s) |
| ----------- | ------------------------------- | ----------- | ----------- |
| 1           | FR1                             | TestSellCapsules.testSellCapsuleAccount() | TestSellCapsules.txt            |
| 2           | FR1                             |             | TestNegativeB.txt            |
| 3           | FR2                             | TestSellCapsulesToVisitor.testSellCapsules()            | TestSellCapsulesToVisitor.txt   |
| 4           | FR3                             | TestRecharge.TestRechargeBalance()            | TestRecharge.txt                |
| 5           | FR4                             | TestBuyBoxes.testBuyBoxes()            | TestBuyBoxes.txt                |
| 6           | FR5                             | TestGetReportEmployee.testGetEmployeeReport()            | TestGetEmployeeReport.txt       |
| 7           | FR6                             | TestGetReport.testGetReport()            | TestGetReport.txt               |
| 8           | FR7                             | TestCreateBeverage.testValidInputs()            | TestCreateBeverage.txt          |
| 9           | FR8                             | TestCreateEmployee.testValidInputs()            | TestCreateEmployee.txt          |


# Coverage of Non Functional Requirements

### 

| Non Functional Requirement | Test name |
| -------------------------- | --------- |
| NFR2                       |it.polito.latazza.timing.TestBeverageTiming() |
| NFR2                       |it.polito.latazza.timing.TestEmployeeTiming() |
| NFR2                       |it.polito.latazza.timing.TestSellTiming() |

