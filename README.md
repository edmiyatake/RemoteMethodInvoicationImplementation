# Software Architecture Lab 2 & 3
 Remote Method Invocation

## Required Modifications (Lab 2)
- Augment the system to support logging by adding a new component. All output that goes to the
screen should also be written to a log file. (Note: Do not put this functionality inside of the
ClientOutput component.)
- Suppose we want to know when a course becomes overbooked. Add a new component to the
system so that it announces when a class is overbooked. Note that it does not need to prohibit
registrations for overbooked classes, but simply announce that fact. For the purposes of this
assignment, consider that a class is overbooked when more than three students are registered.
(“30” is more realistic, but “3” makes testing easier.)
- Extract the course-conflict checking from the RegisterStudentHandler and put it into its own
component. The observable system behavior should not change. (By "observable" we mean to
the user of the system, not to someone viewing the architecture or the source code.)


## Changes Applied

- Created a component that supports logging while maintaining the remote method invocation style pattern
- Implemented a warning that alerts the user when a course becomes overbooked (should be around 30 but set at 3 for testing purposes)
- Replaced the course conflict checker function with a course-conflict check component

