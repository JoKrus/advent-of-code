# Advent of Code solutions
This repository hosts the [Advent of Code](https://www.adventofcode.com)
solutions made by me, JoKrus. Solutions will be added whenever I manage 
to solve one.

## Prerequisites
This project uses
* Maven 3
* Java 11
* External Java libraries through Maven (see pom.xml)

## Usage
This project is not meant to be built to a jar and usually just executed from
within the IDE of choice. That is IntelliJ in my case, but it also should work
in any other Java IDE that supports Maven 3 and Java 11.   

## How are challenges executed?
App.main makes use of reflections to gather all classes in the package of App
and subpackages that extend the abstract class Day. You can either start them
with timing if you want to show your friends that use python that Java is way
cooler, or you can start it without timing if your C friend asks how slow your
solution is.

## How do I benefit when I look at this repo?
Hmm, good question. There are 2 things that come to my mind.
1. You can either take my infrastructure for your own repository. That would
make me really happy but please keep the license in mind when doing that. :)
2. You can clone my repository and feel great about having a solution that is
way better than mine and probably even faster.

So no matter what you try to do, there is not really a downside that I can
think of except that my bad code will occupy some of your precious storage. 
