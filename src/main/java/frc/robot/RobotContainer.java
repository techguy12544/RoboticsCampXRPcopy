// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.ArcadeDrive;
import frc.robot.commands.AutonomousDistance;
import frc.robot.commands.AutonomousFollow;
import frc.robot.commands.BackAndForth;
import frc.robot.commands.DriveForward;
import frc.robot.commands.TurnDegrees;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.xrp.XRPRangefinder;
import edu.wpi.first.wpilibj.xrp.XRPReflectanceSensor;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

/** The container for the robot. Contains subsystems and teleop controls. */
public class RobotContainer {

  // The robot's subsystems (drivetrain also contains the gyro and accelerometer sensors)
  private final Drivetrain drivetrain = new Drivetrain();
  private final Arm arm = new Arm();

  // External sensors plugged into the robot
  private final XRPRangefinder rangefinder = new XRPRangefinder();
  private final XRPReflectanceSensor lineFollower = new XRPReflectanceSensor();

  // The controller plugged into the computer
  private final CommandXboxController controller = new CommandXboxController(0);

  // SmartDashboard chooser for autonomous routines
  private final SendableChooser<Command> autonomousChooser = new SendableChooser<>();
  
  public RobotContainer() {
    // Configure the controller bindings for teleop
    configureTeleopBindings();

    // Configure autonomous routines
    configureAutonomousRoutines();
  }

  private void configureTeleopBindings() {
    // Default command when nothing else is happening
    drivetrain.setDefaultCommand(getArcadeDriveCommand());
    controller.y().whileTrue(new DriveForward(drivetrain, 1));


    // Quickly rotate left or right with the bumper buttons
    controller.leftBumper().onTrue( new TurnDegrees(drivetrain,1,-90 ));
     controller.rightBumper().onTrue(new TurnDegrees(drivetrain,1,90 ) );

    // Hold the A button to follow a line
    // controller.a().whileTrue(new FollowLine(drivetrain, lineFollower));

    /////////////////// Hold the B button to ram
     controller.b().whileTrue(new BackAndForth(drivetrain));

    // Hold the X button to rotate the servo out
    controller.x()
      .onTrue(new InstantCommand(() -> arm.setAngle(180), arm))
      .onFalse(new InstantCommand(() -> arm.setAngle(90), arm));
  }
  
  private void configureAutonomousRoutines() {
    // Add all autonomous routines to the chooser so they can be selected from the dashboard
    autonomousChooser.setDefaultOption("Distance", new AutonomousDistance(drivetrain, 10));
    autonomousChooser.addOption("Follow", new AutonomousFollow(drivetrain, rangefinder));
   autonomousChooser.addOption("Back and Forth", new BackAndForth(drivetrain));

    SmartDashboard.putData(autonomousChooser);
  }

  public void dashboardPeriodic() {
    // Update the dashboard
    SmartDashboard.putNumber("Rangefinder", rangefinder.getDistanceInches());
    SmartDashboard.putNumber("Line Follower Left", lineFollower.getLeftReflectanceValue());
SmartDashboard.putNumber("Line Follower Right", lineFollower.getRightReflectanceValue());

  }

  // Returns the autonomous routine selected on the dashboard (used in Robot.java)
  public Command getAutonomousCommand() {
    return autonomousChooser.getSelected();
  }

  // Returns the default driving command for teleop
  public Command getArcadeDriveCommand() {
    return new ArcadeDrive(drivetrain, controller);
  }
}
