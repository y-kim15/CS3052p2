# CS3052 Practical 2 - Turing Machines
This is a repo for second practical of CS3052 Computation Complexity Module.

### Overview
This practical covered implementation and analysis of Turing machines for solving
binary problems such as language recognition. Its model of computation is explored and
its theoretical computation is compared with that of experimental. The implementation of
Non-deterministic Turing machine is also investigated.

The test suite is a Maven project written in Java.
3 main tasks are delivered including one extension.
* Write a program that can simulate a given (one-tape, deterministic) Turing machine (or
TM) on a given input string. Specifically, you should create a program *runtm* that takes
as input a TM description file and a text file, and runs that TM on that input.
* Devise Turing machines to solve the following problems. Also devise Turing machines
to solve at least two other problems, of your choice. Submit the TM description of your
solutions in the format described above, and in your report describe how you tested your
solutions for correctness, and why you think they correctly solve the problems given
* Analyse, theoretically or experimentally or both, the complexity of your TM algorithms.
You will have to count how many transitions are taken by the Turing machine on inputs
of length n, as a function of n. Submit your experimental results, and your analysis
(theoretical and experimental), as part of your report. If you have data files, scripts to
produce graphs, or statistical analyses, submit those as well.

##### Extension
Implement a simulator for a Nondeterministic TM, and demonstrate its power by programming
either palindrome recognition or recognition of some other language by a Nondeterministic
TM.
