package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Robot extends TimedRobot {
  
  private final CANSparkMax left_leader = new CANSparkMax(2, MotorType.kBrushed);
  private final CANSparkMax left_follower = new CANSparkMax(1, MotorType.kBrushed);
  private final CANSparkMax right_leader = new CANSparkMax(3, MotorType.kBrushed);
  private final CANSparkMax right_follower = new CANSparkMax(4, MotorType.kBrushed);
  private final XboxController controller = new XboxController(0);
  DifferentialDrive drive = new DifferentialDrive(left_leader, right_leader);
  
  
  @Override
  public void robotInit() {
    right_leader.setInverted(true);
    right_follower.follow(right_leader);
    left_follower.follow(left_leader);
  }
  @Override
  public void robotPeriodic() {}
  @Override
  public void autonomousInit() {}
  @Override
  public void autonomousPeriodic() {}
  @Override
  public void teleopInit() {}
  @Override
  public void teleopPeriodic() {
    drive.arcadeDrive(-controller.getLeftY(), controller.getLeftX());
  }
  @Override
  public void disabledInit() {}
  @Override
  public void disabledPeriodic() {}
  @Override
  public void testInit() {}
  @Override
  public void testPeriodic() {}
  @Override
  public void simulationInit() {}
  @Override
  public void simulationPeriodic() {}
}
