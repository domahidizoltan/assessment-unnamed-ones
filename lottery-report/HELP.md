# Lottery Application

author: Zoltan Domahidi  
date: 16th of February, 2019  


## Problem solving

The application has the following components:
- `PlayersNumbersRepository`:
  - reads the input file with players numbers (takes 8-10 seconds on a i7-7500U CPU @ 2.70GHz machine)
  - creates a sorted list out of each line (some search algorithms could have better performance on sorted lists)
  - stores all the players numbers in memory for later usage (using `List<List<Byte>>` for space optimisation)
- `LotteryService`:
  - validates the winning numbers input (must have 5 numbers within range [1, 90] without duplicates)
  - converts the winning numbers to sorted list
  - returns a key-value map having the numbers matching as key, and the number of players as value
- `LotteryApplication`:
  - creates the repository and loads the data from the input file
  - reads the winning numbers
  - calls the service to count the matching numbers
  - outputs the formatted result
  - repeats the read-call-output cycle until a blank line is entered
 
The algorithm simply pulls all the numbers from the repository (in-memory) and counts for all the number sets the number 
of matches. This takes about 1-2 seconds for each set of winning numbers.  
The calculation is done in a work-stealing manner by using parallel stream processing on multiple threads.   
This is done by using the Java 8 streams do describe the operation in a declarative way, to hide the complexity of 
thread manipulation (using `ForkJoinPool` by default) and to keep the code readable.  
The algorithm has O(n) run time.

The service and repository is tested with unit tests having 100% line coverage.


## Running the application

1. Place the input file to the same folder (or use relative path)

2. Build the application:  
`$ ./gradlew clean build`

3. Run the application:  
`$ java -jar build/libs/lottery-0.0.1-SNAPSHOT.jar input.txt`  
or use the bash script  
`$ ./lottery input.txt` (looking for `input.txt` by default if the file is not given)


## Possible improvements

The current solution was easy to implement while still having reasonable performance. This was my second attempt to 
solve the problem.  
My first approach was to group the players by the numbers they used. After this we need to pick the players having the 
winning numbers and get the common ones by using intersection (using `List.retainAll()`).  
This solution could have better performance even if the lists could have millions of items, but it is harder to implement
properly. The player groups should be intersected with all of them one-by-one, otherwise it is possible to eliminate all 
the players and having a false result.  

At the moment the most time consuming part of the application is processing the input file. It is not that effective to 
read chunks of data parallel of the same file, but it could be faster if the file is split into multiple smaller file so 
the application could process them parallel.  
