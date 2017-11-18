package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import static android.R.attr.left;
import static android.R.attr.right;
import static android.R.transition.move;

/**
 * Created by Do on 11/28/2016.
 */
@TeleOp(name="tele", group="Pushbot")
//@Disabled
public class TeleOpTest extends LinearOpMode{


    private DcMotor lf_motor;
    private DcMotor lb_motor;
    private DcMotor rf_motor;
    private DcMotor rb_motor;

    HardwareMap hwMap           =  null;

    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;
        lf_motor   = hwMap.dcMotor.get("lf_drive");
        lb_motor  = hwMap.dcMotor.get("lb_drive");
        rf_motor   = hwMap.dcMotor.get("rf_drive");
        rb_motor  = hwMap.dcMotor.get("rb_drive");
    }
    @Override
    public void runOpMode() throws InterruptedException{

        init(hardwareMap);
        telemetry.addData("Status", "Ready to run");
        telemetry.update();
        waitForStart();
        while(opModeIsActive()) {
            float straight;
            float sideways;
            float orientation;

            straight = -gamepad1.left_stick_y;
            sideways = -gamepad1.left_stick_x;

            orientation = -gamepad1.right_stick_x;

            driveStraight(straight);
            driveSideways(sideways);

            turn(orientation);

        }
    }
    public void driveStraight(float power) {
        lf_motor.setPower(-power);
        lb_motor.setPower(-power);
        rf_motor.setPower(-power);
        rb_motor.setPower(-power);
    }
    public void driveSideways(float power){
        lf_motor.setPower(-power);
        lb_motor.setPower(power);
        rf_motor.setPower(power);
        rb_motor.setPower(-power);
    }
    public void turn(float power){
        lf_motor.setPower(power);//(power)
        lb_motor.setPower(power);
        rf_motor.setPower(-power);
        rb_motor.setPower(-power);//(-power)``
    }
}


