package frc.robot;
import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PneumaticHub;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {
  private final CANSparkMax left_leader = new CANSparkMax(2, MotorType.kBrushed);
  private final CANSparkMax left_follower = new CANSparkMax(1, MotorType.kBrushed);
  private final CANSparkMax right_leader = new CANSparkMax(3,MotorType.kBrushed);
  private final CANSparkMax right_follower = new CANSparkMax(4, MotorType.kBrushed);
  private final XboxController controller = new XboxController(0);
  private final DifferentialDrive drive = new DifferentialDrive(left_leader, right_leader);
  private final PneumaticHub pneumaticHub = new PneumaticHub(5);
  private final Encoder left_Encoder = new Encoder(0, 1);
  private final AHRS navx = new AHRS(SPI.Port.kMXP);
  double cm;
  int b = 0;
  
  


  
  @Override
  public void robotInit() {
    right_leader.setInverted(true);
    right_follower.follow(right_leader);
    left_follower.follow(left_leader);
    CameraServer.startAutomaticCapture("Camera", 0);
    pneumaticHub.enableCompressorAnalog(20, 60); 
    left_Encoder.setDistancePerPulse(26);
    left_Encoder.reset();
    b = 0;


    
  }
  @Override
  public void robotPeriodic() {
    SmartDashboard.putBoolean("Compressor working", pneumaticHub.getCompressor());
    SmartDashboard.putNumber("Storage Pressure", pneumaticHub.getPressure(0));
    SmartDashboard.putNumber("Compressor drawn(I)", pneumaticHub.getCompressorCurrent());
    SmartDashboard.putNumber("Compressor voltage", pneumaticHub.getAnalogVoltage(0));

    SmartDashboard.putNumber("Navx Yaw",navx.getYaw());
    SmartDashboard.putNumber("Navx angle", navx.getAngle());

    SmartDashboard.putNumber("cm", left_Encoder.get()/13);
    SmartDashboard.putNumber("encoder count", left_Encoder.get());
    SmartDashboard.putNumber("encoder distance", left_Encoder.getDistance());
    SmartDashboard.putNumber("b", b);
    
    
  }
  public void sago(){
    if(navx.getYaw()>-5 && navx.getYaw()<80){
      drive.tankDrive(-0.5, 0.5);
    }else{
      b++;
    } 
  }

  public void sola(){
    if(navx.getYaw()>20&& navx.getYaw()<100){
      drive.tankDrive(0.5, -0.5);
    }
  }

  public void ileri(){
    if(left_Encoder.get()<2604){
      drive.tankDrive(0.5, 0.5);
      pneumaticHub.setSolenoids(6, 5);
    }else{
      left_Encoder.reset();
      b++;
    }
  }
public void geri(){
  if(left_Encoder.get() >-2604){
      drive.tankDrive(-0.5, -0.5);
      pneumaticHub.setSolenoids(7,-5);
    }else{
      b++;
    }
  }


  @Override
  public void autonomousInit() {
    navx.reset();
    left_Encoder.reset();
    b = 0;
  }
  @Override
  public void autonomousPeriodic(){
    if(b==0){
      sago();
    }else if( b==1){
      ileri();
    }else if(b==2){
      geri();
    }else if(b==3){
      sola();
    }
      
    
  }  
  
  
  @Override
  public void teleopInit(){
    navx.reset();
  }
  @Override
  public void teleopPeriodic() {
    drive.arcadeDrive(-controller.getLeftY(), controller.getRightX());
    if(controller.getLeftBumper()){
      pneumaticHub.setSolenoids(6, 5);
    }else if(controller.getRightBumper()){
      pneumaticHub.setSolenoids(7,-5);
    }else{
      pneumaticHub.setSolenoids(0, 0);
    } 
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
  public void simulationPeriodic() {
  
  }
}

