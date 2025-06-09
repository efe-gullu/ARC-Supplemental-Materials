// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ExampleSubsystem extends SubsystemBase {  
  
  public ArmSubsystem() {}
  
  private final TalonFX arm = new TalonFX(20); 
  private final double gearRatio = 10.0;    //given reduction ratio
  private double currentRotations = 0.0;

  
  private double degreesToRotations(double degrees) { // method to convert degrees to motor rotations
      return degrees / 360.0 * gearRatio;
  }

  
  private double rotationsToDegrees(double rotations) { // method to converts motor rotations  to degrees
      return rotations / gearRatio * 360.0;
  }


  public void setAngle(double degrees) {    //method the set the desired angle given in degrees
      currentRotations = degreesToRotations(degrees);          //rotations from the given degree
      System.out.println("Setting arm to " + degrees + " degrees (" + currentRotations + " motor rotations)");
  }

 
  public double getAngle() {      // method to get the current arm angle given in degrees
      double angle = rotationsToDegrees(currentRotations);     //angle from the rotation
      return angle;
  }
}
