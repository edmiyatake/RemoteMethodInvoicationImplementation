# Software Architecture Lab 2 & 3
 Remote Method Invocation

## Required Modifications (Lab 3)
- Your task is to turn the existing student registration system from Lab 2, including the required
  modifications, which are the logging, overbooking, and course-conflict checking components, into a
  distributed system. You will be provided with the architecture diagram below to help you develop a
  functional system.


## Changes Applied

- Created a server using Remote Registry that listen on ports
- Rewrote the SystemMain code into a client that looks for listening ports
- Created a database and activity interface for methods to be remotely called
- Implemented the methods based off the interfaces

## How To Run

Operating System: Windows 11 Version 23H2 Computer Architecture: AMD64 IDE: Visual Studio Code 1.96.4

- Ensure that it is Java 1.8
- javac *.java
- Make sure that the registry is on port 1099 because the handler registry is on 1100
- "rmiregistry 1099"
- java Database
- java Server
- java Client
- Enter user input and press "x" to exit

## Test Cases

Part B

Student IDs

G00123456
G00123432
G45643133
G01234567
Course IDs

CS112
Section IDs

A
Part C

Student ID

G00123456
Conflicting Course IDs

CS211 A (M 11:00 - 12:30, Kurtz)

CS112 A (M 10:30 - 12:00, Horton)

CS421 A (T 11:00 - 12:30, Schmidt)

CS211 B (T 09:30 - 12:00, Worth)

Non-Conflicting Course ID

CS475 A (M 09:00 - 10:30, Delmar)
CS211 A (M 11:00 - 12:30, Kurtz)