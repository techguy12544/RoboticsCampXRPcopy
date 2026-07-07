# Field-centric driving

Now we'll make the controls for teleop use field-centric driving. There are a few parts to this, but we should start by making a new command to put our code in. Since this is a command for driving, we'll use the current driving command, ArcadeDrive, as a starting point.

> Duplicate commands/ArcadeDrive.java by copying the file and pasting it in the commands folder.

> Rename the new file to FieldDrive.java

> Replace `ArcadeDrive` in the new file with `FieldDrive` (on lines 14 and 18).

Usually the sticks on a controller are considered two separate inputs, one for the x-axis and one for the y-axis. For this, though, we need to consider the stick's direction and have the robot turn that way. We'll compare the angle that we want the robot to be facing with the current angle from the gyroscope, then turn left or right until it's facing the right way.

This might sound complicated, but you already have code to do the second part! In the `DriveForward` command, the robot uses the gyroscope to keep facing the same direction. This is basically the same as turning to face a specific direction.

The only problem is we need to take our x- and y-axis inputs from the controller and convert it to an angle to compare it with the gyroscope. We can use some of the math utilities included in WPILib for this:

```java
Translation2d stickPosition = new Translation2d(-m_controller.getLeftX(), m_controller.getLeftY());
```

The Translation2d object has a function called `getAngle()` that does the calculation we want, but it returns another object instead of a double. That object has a function called `getDegrees()` to get the angle in degrees, which is what we need. It returns an angle between -180 and 180, but the angles measured by the gyroscope range from 0 to 360, so we'll also have to add 180 so it's in the same range as the gyroscope. So the code to get the angle is:

```java
double angle = stickPosition.getAngle().getDegrees() + 180;
```

There's one other thing we need, which is how fast the robot should go. We also want to control this with the stick, meaning we need to know how far the stick has moved in any direction. There's also a function for this:

```java
double speed = stickPosition.getNorm();
```

> Replace the contents of `execute()` with the three lines of code above.

> Add the code from `execute()` in DriveForward.java to the bottom of `execute()` in FieldDrive.java.

> In the code you added, change `m_forwardAngle` to `angle` and change `m_speed` to `speed`

Now let's try it out! Go to RobotContainer.java and find the `getArcadeDriveCommand()` function. Replace `new ArcadeDrive` with `new FieldDrive`, and see what happens when you drive in teleop mode.

It mostly works, but when you're not touching the stick, the robot still turns to face forward. This is because it's always trying to face a direction, even when it's not moving. We'd prefer if it only moves in response to the stick, which we can fix easily. We already have a variable called `speed` for how far the stick has moved, so we can use that to limit the rotation. Just change the `rotate` argument of `m_drivetrain.arcadeDrive` to `rotate * speed`

### Bonus Challenge

Combine FieldDrive and ArcadeDrive into a single command that does the regular arcade drive with the left stick and field-centric driving with the right stick. Make sure to only call `m_drivetrain.arcadeDrive` once (you will need to combine the arguments to `m_drivetrain.arcadeDrive` from the two sticks by adding them together).