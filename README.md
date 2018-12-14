# Advent of Code 2018

Advent of code: https://adventofcode.com/2018

Don't know what to expect? Check out the previous years: [2017](https://adventofcode.com/2017), [2016](https://adventofcode.com/2016), [2015](https://adventofcode.com/2015).

## How does it work

- New puzzles are added each day at midnight EST (that is 06:00 CET)
- Solve the puzzle and post the solution to earn a star :star:,​ two stars can be earned each day
- The quicker you are the more points you get
- Document and share your frustrations, eureka's and black magic in #AoC2018

## How to join

- Create an account / login: https://adventofcode.com/2018/auth/login
- Join the Kunlabora leaderbord [on this link](https://adventofcode.com/2018/leaderboard/private) with this code: `239979-3c68168f`
- Fork this repository and start hacking

## Log

To improve knowledge sharing and experimentation, please document how to build and run your code. It can also be nice to keep a log here:

### _28/11_

_Today I forked this repository in preparation of the start._

### _01/12 (~1h)_ 

_Started working on the problem quite late after it was made available._  
_The first exercise proved to be pretty simple (just a summation of numbers)._  
_The second exercise was a bit harder. I started out wanting to use Java 8's streams to solve it, but was unable to find a readable solution. Eventually I reverted to a good old for loop_  
_Quite a bit of time was also wasted on getting IntelliJ to pick up the file with the input data -\_-_

### _02/12 (~1h 15m)_

_The problem wasn't as easy as on the first day, but it was easy to break down into sub-problems._  
_I started of writing some tests for these sub-problems before beginning to write the actual implementation_  
_Finding the correct solution proved to be quite painless because of this_  

_I also worked on a different machine which made me have to do the project setup again. Took me quite a while to get IntelliJ to execute my code again today -\_-_

_(EDIT) Updated the folder structure since I think I'm committed to Kotlin now (and this makes it so I don't have to setup a new module in IntelliJ every day)_

### _03/12 (~30m)_

_Learned a bit more about regexps in Kotlin_   
_Part 2 of the exercise was a trivial extension_

### _04/12 (~3h)_

_Phew, what a day..._  

_Started off well using TDD to create something of which I know it works, but I kinda dropped this after a while because I wanted to finish the exercise_  
_That was a mistake..._
  
_I picked it up again in the evening, removed some hacky code and refactored the whole thing to make more sense_  
_Was a breeze from there on out_

_Also had some new insights in how the collection extension functions work in kotlin (mainly groupBy)_  
_It's pretty awesome :)_

### _05/12 (~2h)_

_This took me way longer than I'd like to admit_  
_Don't know if it was an issue with my mood or just that I'm stupid_  
_The final solution I delivered also takes about a minute to run, which is not great :/_

_I'd like to revisit this one if I find the time_

### _06/12 (~2h 30m)_

_Could have maybe re-used some code from day 3 (with the fabric)??_  
_The Manhattan distance constraint brought up some interesting discussions with my co-workers about when an area is infinite_

_I think I should really make some sort of generic Grid class though._   
_I'm expecting more of these types of 2D problems_

### _07/12 (~?)_

_Cool exercise, forgot to update this log the day I finished it_  
_Kinda brute-forced and hacked together part 2_

### _08/12 (~40m)_

_Pretty straightforward_   
_Parsing the data was the hardest part of the exercise_

### _09/12 (~45m)_

_Went into this thinking performance was going to be a large issue (a colleague talked about 30+ mins of runtime with his solution.)_  
_Was pleasantly surprised that using a linked list like structure can solve it in under a second_  

_Making the node structure for the linked list was the hardest part of the exercise for me_  
_All the other logic for playing the game is encapsulated in a single class (but remains pretty simple)_

### _10/12 (~1h)_

_Very fun exercise! Was able to make use of my limited Swing knowledge here to make a visualisation tool_  
_Searching around for the correct scale and position on which you can see the word felt quite hackish though :p_  
_I'm wondering if there is a good stopping-condition you can define_

### _11/12 (~1h 30m)_

_Finally took the time to create some sort of generic Grid2D util (since it seems there are a lot of exercises that require this)_  
_Learned some new stuff about type-aliases and generics in kotlin (reified -> NO RUNTIME TYPE ERASURE :D)_  

_The exercise itself went wuite well. I had some  issues with performance for part 2 though._  
_Not sure how to solve this without brute-forcing the solution..._

### _12/12 ()_

_First of all, before I begin with this problem:_  
_The problem description is worded too hard_