### Status Switch Robot

Switch status based on your choice and time to take action.
Now supported multi-task to queue up multiple task and switch accordingly.

workflow:
- Choose status
- Add delay time
- Choose to add more task nor not

## Usage for Java
```
java SwitchStatus.java
```
Follow prompt to set the desired status and delay time

---

For user java version lower than 11, please compile first and then execute
```
javac SwitchStatus.java
java SwitchStatus
```
---

## Requirement
Java (Version >= 11)

Designed for main monitor resolution 1600x900 and Windows scale for this monitor should be 100%.

For Mac, please set this scale based on your monitor's physical resolution and logical resolution(can find in setting)
ratio = physical resolution / logical resolution

The input coordinate is based on physical resolution.

The coordinate for the button may changed due to UI change, please adjust to fit your own laptop
