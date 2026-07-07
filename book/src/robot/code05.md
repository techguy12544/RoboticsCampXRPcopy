# Drive straight

You may have noticed that the robot doesn't drive particularly straight when going over longer distances. This is due to small differences and electrical variations between the two motors. Even if both motors are told to spin at full speed, they won't spin at exactly the same speed, causing the robot to turn slightly as it drives.

We can correct for this using the gyroscope. If the robot starts to veer left, it should correct itself by turning right, and if it starts to veer right, it should turn left. What we need to do is remember the value of the gyroscope when the robot starts driving, then compare it to the current value to see which way it needs to turn and how much. The `DriveForward` command is already set up to do this, but it's missing a little code.

> Open commands/DriveForward.java

This command saves the value of the gyroscope when it starts. If you look in `initialize()`, you'll see this line of code: `m_forwardAngle = m_drivetrain.getGyroAngleZ();`

Now we just need code in `execute()` to compare the current value of the gyroscope (`m_drivetrain.getGyroAngleZ()`) with the starting value (`m_forwardAngle`). We could use an if statement to check if it's greater or less, but there's an easier way. We're trying to get a number that tells the robot how much to turn. It should be positive to turn left, and negative to turn right. If we subtract the current value from the starting value, we'll get a number that's positive if the current value is less than the starting value, and negative if it's not. That's just what we need!

```java
double rotate = (m_forwardAngle - m_drivetrain.getGyroAngleZ()) / 100;
```

Replace the line in `execute()` with the "TODO" comment with the line above. Notice we're also dividing by 100. This slows down how quickly the robot turns so it doesn't overcorrect.

Let's try it out by attaching the command to a controller button. We did this earlier with `TurnDegrees`.

> Open RobotContainer.java and look for the `configureTeleopBindings()` function.

At the bottom of the function add this code:

```java
// Hold the Y button to drive straight forward
controller.y().whileTrue(new DriveForward(drivetrain, 1));
```

Now try holding down the Y button in teleop mode. Does the robot drive straighter than usual?