# Acceptance Testing Documentation template

Authors:

Date:

Version:

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

# Coverage of Scenarios

```
<Report in the following table the coverage of the scenarios listed above. Report at least an API test (defined on the functions of DataImpl only) and a GUI test (created with EyeAutomate) for each of the scenarios. For each scenario, report the functional requirements it covers.
In the API Tests column, report the name of the method of the API Test JUnit class you created. In the GUI Test column, report the name of the .txt file with the test case you created.>
```

### 

| Scenario ID | Functional Requirements covered | API Test(s) | GUI Test(s) |
| ----------- | ------------------------------- | ----------- | ----------- |
| 1           | FR1                             |             |             |
| 2           | FR1                             |             |             |
| ...         |                                 |             |             |
| ...         |                                 |             |             |
| ...         |                                 |             |             |
| ...         |                                 |             |             |



# Coverage of Non Functional Requirements

```
<Report in the following table the coverage of the Non Functional Requirements of the application - only those that can be tested with automated testing frameworks.>
```

### 

| Non Functional Requirement | Test name |
| -------------------------- | --------- |
|                            |           |

