package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class HardwareK9bot
{

    public DcMotor leftBack = null;
    public DcMotor rightBack = null;
    public DcMotor  leftDrive   = null;
    public DcMotor  rightDrive  = null;
    public DcMotor shaftController = null;
    public DcMotor[] motors = new DcMotor[4];
    public Servo grabber = null;
    public Servo tail = null;

    HardwareMap hwMap  = null;

    public HardwareK9bot() {
    }

    public void init(HardwareMap ahwMap) {
        hwMap = ahwMap;

        leftDrive  = hwMap.get(DcMotor.class, "left_drive");
        rightDrive = hwMap.get(DcMotor.class, "right_drive");
        leftBack = hwMap.get(DcMotor.class, "left_back");
        rightBack = hwMap.get(DcMotor.class, "right_back");
        shaftController = hwMap.get(DcMotor.class, "shaft_controller");
        grabber = hwMap.get(Servo.class, "grip");
        tail = hwMap.get(Servo.class, "tail");

        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.REVERSE);

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
    }
}