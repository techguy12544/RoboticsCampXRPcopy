// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj2.command.Command;

/**
 * This command drives the robot at a given speed, while using the gyro to keep facing the same direction.
 */
public class DriveForward extends Command {
  private final Drivetrain m_drivetrain;
  private final double m_speed;

  private double m_forwardAngle;

  public DriveForward(Drivetrain drivetrain, double speed) {
    m_drivetrain = drivetrain;
    m_speed = speed;
    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_drivetrain.arcadeDrive(0, 0);
    m_drivetrain.resetEncoders();
    m_forwardAngle = m_drivetrain.getGyroAngleZ();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
double rotate = (m_forwardAngle - m_drivetrain.getGyroAngleZ()) / 100;
    m_drivetrain.arcadeDrive(m_speed, rotate);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drivetrain.arcadeDrive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
