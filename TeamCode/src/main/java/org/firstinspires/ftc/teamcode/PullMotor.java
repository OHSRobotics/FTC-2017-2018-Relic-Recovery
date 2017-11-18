package org.firstinspires.ftc.teamcode;

/**
 * Created by Troy Neubauer on 11/4/2017.
 */
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import static android.R.attr.left;
import static android.R.attr.right;
import static android.R.transition.move;
@TeleOp(name="lift", group="Pushbot")

public class PullMotor extends LinearOpMode {
    private DcMotor pull_motor;
    HardwareMap hwMap = null;
        public void init (HardwareMap ahwMap) {
            hwMap = ahwMap;
            pull_motor = hwMap.dcMotor.get("motor_pull");
        }

    @Override
    public void runOpMode() throws InterruptedException {
        init(hardwareMap);
        telemetry.addData("Status", "Ready to run");
        telemetry.update();
        waitForStart();
        while (opModeIsActive()) {
            float pull;
            float npull;
            pull =  -gamepad1.right_trigger;
            npull = -gamepad1.left_trigger;
            drivePull(pull);
            driveNpull(npull);
        }
    }
    public void drivePull (float power) {
            pull_motor.setPower(power);
    }
    public void driveNpull (float power) {
            pull_motor.setPower(-power);
    }
}
