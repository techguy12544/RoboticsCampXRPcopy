// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

/**
 * This autonomous routine drives out a specified distance, then turns around and drives back.
 */
public class AutonomousDistance extends SequentialCommandGroup {

  private static final double SPEED = 0.75;

  public AutonomousDistance(Drivetrain drivetrain, double inches) {
    addCommands(
      new DriveDistance(drivetrain, SPEED, inches),
      new TurnRandom(drivetrain, SPEED),
      new DriveDistance(drivetrain, SPEED, inches),
      new TurnRandom(drivetrain, SPEED)
    );
  }

}
