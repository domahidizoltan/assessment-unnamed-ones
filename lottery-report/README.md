# Lottery Report

author: Zoltán Domahidi  
date: February 16, 2019


#### The problem:

This problem is related to the Hungarian lottery. In case you do not know it:
players pick 5 distinct numbers from 1 to 90. There is a weekly lottery draw event
when the lottery organization picks 5 distinct numbers randomly between 1 and
90 – just like the players did. The player’s prize then depends on how many of the
player’s numbers match with the ones picked at the lotto drawing. A player won
if he/she has 2, 3, 4 or 5 matching numbers.

Now, the problem: at the lottery event, right after drawing the numbers, a
computer shall be able to report quickly that how many winners are in each
category, for example:

Numbers matching | Winners
---|---
5 | 0
4 | 12
3 | 818
2 | 22613

This report shall be generated within a couple of seconds after picking the winner
number. The player’s numbers are known in advance – at least 1 hour ahead of
the show. In peak periods there are about 5 million players, but to be sure we can
assume it is not exceeding 10 million.

#### Technical specifcation
Write a console application in a freely chosen programming language that can be
compiled on Linux. Your application will be called like this:

./yourapp input.txt

where input.txt fle exists in the same folder and is an ascii fle, in which each line
contains 5 space separated integers (in the range of 1-90) representing one
player’s numbers.

You can download a sample file here.

When your application fnished processing the player’s data, it should write a line
to the standard output like this:

READY

Note that it should be newline terminated. After that, the program may receive
multiple lines (identical format to the fle’s lines) representing the lottery’s picks
and it should be able re report 4 space separated numbers in the standard output
as fast as possible (line should be newline terminated). The four numbers shall
mean the number of winners with 2, 3, 4 and 5 matches respectively.

Example C boilerplate code:
```
#include <stdio.h>

int main() {

    int a,b,c,d,e;
    
    printf("READY\n");
    int i = 5;
    while(i == 5) {
        i = scanf("%d %d %d %d %d", &a, &b, &c, &d, &e);
        if(i == 5) {
            printf("%d %d %d %d\n", 0, 1, 2, 3);
        }
    }
    
    return 0;
}
```

Expectations
- Write an optimized code that can report the results in a couple of seconds
– or faster, even better.
- Document the asymptotic run time of your solution
- Use code comments (enough to make it easy to understand)
- Document your ideas how you could further improve the calculation
speed, if any

---

### Outcome

This was the senior backend software engineer interview assessment of a telecommunication company in Budapest.      
This assignment had no serious time limit. The result wasn't flawless, but the problems and possible improvements were discussed.  
The interview had a positive vibe overall ;)  