package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class HardwareK9bot
{
    /* Public OpMode members. */

    public DcMotor leftBack = null;
    public DcMotor rightBack = null;
    public DcMotor  leftDrive   = null;
    public DcMotor  rightDrive  = null;

    public DcMotor motors[] = new DcMotor[4];
    /*
    public DcMotor    lifter = null;
    public Servo     arm = null;
	*/
    /*
    public final static double ARM_HOME = 0.2;
    public final static double CLAW_HOME = 0.2;
    public final static double ARM_MIN_RANGE  = 0.20;
    public final static double ARM_MAX_RANGE  = 0.90;
    public final static double CLAW_MIN_RANGE  = 0.20;
    public final static double CLAW_MAX_RANGE  = 0.7;
    */
    /* Local OpMode members. */
    HardwareMap hwMap  = null;
    private ElapsedTime period  = new ElapsedTime();

    /* Constructor */
    public HardwareK9bot() {
    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // save reference to HW Map
        hwMap = ahwMap;

        // Define and Initialize Motors
        leftDrive  = hwMap.get(DcMotor.class, "left_drive");
        rightDrive = hwMap.get(DcMotor.class, "right_drive");
        leftBack = hwMap.get(DcMotor.class, "left_back");
        rightBack = hwMap.get(DcMotor.class, "right_back");
        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.REVERSE);
        //lifter = hwMap.get(Dc.Motor.class, "lifter");

        motors[0] = leftDrive;
        motors[1] = rightDrive;
        motors[2] = leftBack;
        motors[3] = rightBack;

        // Set all motors to zero power
        leftDrive.setPower(0);
        rightDrive.setPower(0);
        leftBack.setPower(0);
        rightBack.setPower(0);


        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        for(DcMotor motor : motors){
            motor.setPower(0);
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

        // Define and initialize ALL installed servos.
        /*
        armR  = hwMap.get(Servo.class, "armR");
        armL = hwMap.get(Servo.class, "armL");
        arm.setPosition(ARM_HOME);
        claw.setPosition(CLAW_HOME); */
    }
}