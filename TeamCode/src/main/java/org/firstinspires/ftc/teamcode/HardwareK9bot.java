package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This is NOT an opmode.
 *
 * This class can be used to define all the specific hardware for a single robot.
 * In this case that robot is a K9 robot.
 *
 * This hardware class assumes the following device names have been configured on the robot:
 * Note:  All names are lower case and some have single spaces between words.
 *
 * Motor channel:  Left  drive motor:        "left_drive"
 * Motor channel:  Right drive motor:        "right_drive"
 * Servo channel:  Servo to raise/lower arm: "arm"
 * Servo channel:  Servo to open/close claw: "claw"
 *
 * Note: the configuration of the servos is such that:
 *   As the arm servo approaches 0, the arm position moves up (away from the floor).
 *   As the claw servo approaches 0, the claw opens up (drops the game element).
 */
public class HardwareK9bot
{
    /* Public OpMode members. */

    public DcMotor leftBack = null;
    public DcMotor rightBack = null;
    public DcMotor  leftDrive   = null;
    public DcMotor  rightDrive  = null;
    public DcMotor shaftController = null;
    public DcMotor[] motors = new DcMotor[4];
    public Servo grabber = null;
    public Servo tail = null;

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
        shaftController = hwMap.get(DcMotor.class, "shaft_controller");
        grabber = hwMap.get(Servo.class, "grip");
        tail = hwMap.get(Servo.class, "tail");

        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.REVERSE);
        //lifter = hwMap.get(Dc.Motor.class, "lifter");

        motors[0] = rightDrive;
        motors[1] = leftDrive;
        motors[2] = leftBack;
        motors[3] = rightBack;

        for(DcMotor motor : motors){
            motor.setPower(0);
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
        shaftController.setPower(0);
        shaftController.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Define and initialize ALL installed servos.
        /*
        armR  = hwMap.get(Servo.class, "armR");
        armL = hwMap.get(Servo.class, "armL");
        arm.setPosition(ARM_HOME);
        claw.setPosition(CLAW_HOME); */
    }
}