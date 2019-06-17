# suite
Generic Test suite for Pojo
Test framework to test pojo classes

Description
---------------------
It is tough for developers to write test cases for Pojo - Getters/setters. While it is tough to skip the test cases since code coverage shows less percentage. In order to achieve this, here is a framework to test certain methods

1. Getters/setters
2. Constructors (Basic, Parameterized)
3. equals(), hashCode()
4. toString()

How to use this
---------------
Copy the following files to test case folder
1. PojoTestCase
2. PojoExplorer
3. DataHelper

Open PojoTestCase file, in @Before - setup() method, add the Pojo class that needs to be tested

To verify the coverage, please run -> Coverage As in eclipse / Sonar execution.


Note:
Employee, Department are sample Pojo classes used here for test purpose. 
This API may fail in certain unknown/uncovered conditions. Let me know in such cases to incorporate the particular flow level changes. 

Happy Coding :) 
