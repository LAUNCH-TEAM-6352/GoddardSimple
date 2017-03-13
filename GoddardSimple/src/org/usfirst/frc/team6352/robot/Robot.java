package org.usfirst.frc.team6352.robot;

import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.XboxController;

/**
 * This is a demo program showing the use of the RobotDrive class, specifically
 * it contains the code necessary to operate a robot with tank drive.
 *
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SampleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 *
 * WARNING: While it may look like a good choice to use for your code if you're
 * inexperienced, don't. Unless you know what you are doing, complex code will
 * be much more difficult under this system. Use IterativeRobot or Command-Based
 * instead if you're new.
 */
public class Robot extends SampleRobot {
	//RobotDrive myRobot = new RobotDrive(0, 1); // class that handles basic drive
	RobotDrive myRobot = new RobotDrive(new Victor(0), new Victor(1)); // class that handles basic drive
												// operations
	//Joystick leftStick = new Joystick(0); // set to ID 1 in DriverStation
	//Joystick rightStick = new Joystick(1); // set to ID 2 in DriverStation
	
	Joystick joystick = new Joystick(0);
	
	SpeedController gearDelivery = new Spark(2);
	Servo servo = new Servo(9);
	SpeedController ropeClimber = new Victor(3);
	
	XboxController xboxController = new XboxController(0);

	public Robot() {
		myRobot.setExpiration(0.1);
	}

	/**
	 * Runs the motors with tank steering.
	 */
	@Override
	public void operatorControl() {
		myRobot.setSafetyEnabled(true);
		myRobot.setInvertedMotor(MotorType.kRearLeft, true);
		myRobot.setInvertedMotor(MotorType.kRearRight, true);
		while (isOperatorControl() && isEnabled()) {
			myRobot.arcadeDrive(joystick);
			int gearDrive = 0;
			if (xboxController.getAButton())
			{
				//open gear catcher
				servo.setPosition(0);
			}
			else
			{
				//close gear catcher
				servo.setPosition(.80);
			}
			if (xboxController.getPOV() == 0)
			{
				gearDrive = 1;
			}
			if (xboxController.getPOV() == 180)
			{
				gearDrive = -1;
			}
			if (gearDrive == 1)
			{
				gearDelivery.set(.50);
			}
			else if(gearDrive == -1)
			{
				gearDelivery.set(-.33);
			}
			
			if (xboxController.getStartButton() == true)
			{
				ropeClimber.set(.20);
			}
			else
			{
				ropeClimber.set(0);
			}
			
			//myRobot.arcadeDrive(xboxController);
			Timer.delay(0.005); // wait for a motor update time
		}
	}
}
