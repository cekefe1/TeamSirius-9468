
package frc.robot;

import frc.robot.Constants.OIConstants;
import frc.robot.commands.SwerveDriveControllersirius;
import frc.robot.subsystems.SwerveSubsystem;
import edu.wpi.first.wpilibj.XboxController;


public class RobotContainer {
  private final SwerveSubsystem swerveSubsystem = new SwerveSubsystem();
  private final XboxController controller = new XboxController(0);
      

  
  public RobotContainer() {
    swerveSubsystem.setDefaultCommand(new SwerveDriveControllersirius(
                swerveSubsystem,
                () -> -controller.getRawAxis(OIConstants.kDriverYAxis),
                () -> controller.getRawAxis(OIConstants.kDriverXAxis),
                () -> controller.getRawAxis(OIConstants.kDriverRotAxis),
                () -> !controller.getRawButton(OIConstants.kDriverFieldOrientedButtonIdx)));
    configureBindings();
  }

  
  private void configureBindings() {
   
  }

 
 /*  public Command getAutonomousCommand() {
    
  }*/
}
