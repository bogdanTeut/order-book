Considerations:

* I believe there is no 'refactoring done', or no 'done' in general.

* I used TDD and tried to be disciplined about doing teeny tiny steps, which is visible if you take a look at git log.

* I choose a simple implementation thinking a more complex one wasn't in the scope of this exercise.

* More negative cases tests are necessary(haven't added any for totalSize and orders like I added for price).

* More tests are necessary in general for corner cases. 

* Took the liberty(there was no mention in the spec) to return a null when the desired price level was not found (there are better ways to do it).  

* Package name should've contained mizuho but it is public repo :) 


* In order to see test report you can run jacoco plugin from intellij or from command line: ./gradlew clean test



* Part B:
    * side -> should be an Enum Bid and Offer.
    * We should use BigDecimals and BigIntegers.
    * There are better(real life, latency sensitive) solutions.
  For example, instead of generating the price levels map on each method call(price, totalSize, orders) and make use of the data which is time-consuming(considering high volume of data),
  we could've implemented a tree like structure(similar to nested maps) with *side* as main partition key followed by *price* levels keys associated with buckets of Orders, 
  which would have speed up read operations. E.g. 
    - Bid ->
        - 50 ->
            - Order: 1 B 50 10
            - Order: 2 B 50 10 t + 1 sec
            - Order: 3 B 50 15 t + 2 sec
        - 30 ->
            - Order: 4 B 30 25 
            - Order: 5 B 30 15 t + 2 sec
           -  ........ 
    - Offer -> 
        - 10 -> 
            - Order: 6 O 10 10 

          

    