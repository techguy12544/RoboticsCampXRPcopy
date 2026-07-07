# Random turn command

Now let's add a new command to turn the robot a random amount. As we've seen, commands can be used in both teleop and autonomous modes.

This time you're going to copy an existing command, `TurnDegrees`, and modify it.

> Duplicate commands/TurnDegrees.java by copying the file and pasting it in the commands folder.

> Rename the new file to TurnRandom.java

> Replace `TurnDegrees` in the new file with `TurnRandom` (on lines 13 and 18).

> Remove the word `final` from `private final double m_degrees;` and `private final double m_speed;`

Now change the constructor (the function at the top that you just renamed to `TurnRandom`) to remove all uses of `degrees`. It should look like this:

```java
public TurnRandom(Drivetrain drivetrain, double speed) {
    m_drivetrain = drivetrain;
    m_speed = speed;
    addRequirements(drivetrain);
}
```

We now have a new command called `TurnRandom`. It still works like `TurnDegrees`, only we never set how many degrees it should turn, so if you use it right now it will always turn 0 degrees, which is the same as doing nothing.

What we want is for every time this command is run it picks a random number of degrees to turn. This way if it gets run repeatedly, it will do something different each time. To do something every time the command starts, we need to add code to the `initialize()` function.

We can use `Math.random()` to get a random number between 0 and 1. What we'd like, though, is a random number between -90 and 90. We can do a little arithmatic with `Math.random()` to get there. Add this code to `initialize()`:

```java
m_degrees = (Math.random() * 180) - 90;
```

Almost done! There's one small problem, though: the way this command works requires `m_degrees` to be positive, but we just set it to a random number between -90 and 90, so it will sometimes be negative. There used to be an `if` statement in the constructor that handled negative degrees, but we removed it. Let's add it to `initialize()`, below the `m_degrees` line you just added:

```java
if (m_degrees < 0) {
    m_degrees = -m_degrees;
    m_speed = -m_speed;
}
```

Now if `m_degrees` is negative, `m_degrees` will be set to negative `m_degrees`, which makes it positive (the negative of a negative number is a positive number). At the same time, `m_speed` will be set to negative `m_speed`, which reverses the direction the robot turns.

Let's try it out!

> Open commands/AutonomousDistance.java and change `TurnDegrees` to `TurnRandom`

You will also need to remove the last argument from the commands you changed, since `TurnRandom` doesn't have an argument for the number of degrees to turn. You may also want to surround the commands with `Commands.repeatingSequence`, like in commands/AutonomousFollow.java, so the routine repeats forever. Now, if you run the "Distance" autonomous routine, the robot should move around randomly.