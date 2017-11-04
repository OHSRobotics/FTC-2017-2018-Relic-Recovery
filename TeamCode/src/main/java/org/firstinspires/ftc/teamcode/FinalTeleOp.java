package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.teamcode.HardwareK9bot;

/**
 * Created by Troy Neubauer on 10/28/2017.
 */

@TeleOp(name="Telop Working", group="K9bot")
public class FinalTeleOp extends OpModeBase {
    HardwareK9bot   robot           = new HardwareK9bot();

    @Override
    public void runOpMode() {

        robot.init(hardwareMap);

        robot.leftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.rightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.leftBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.rightBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            double turn = gamepad1.left_stick_x;
            double lateral = gamepad1.right_stick_x;
            double forward = -gamepad1.right_stick_y;



            double leftDrive, leftBack, rightDrive, rightBack;

            leftDrive = forward + lateral + turn;
            leftBack = forward - lateral +  turn;
            rightDrive = forward - lateral - turn;
            rightBack = forward + lateral - turn;

            if(forward > .1){
                if(lateral > .1) {
                    lateral = .707;
                    forward = .707;
                } else if(lateral < -.1){
                    lateral = -.707;
                    forward = .707;
                }
            } else if (forward < -.1) {
                if(lateral > .1) {
                    lateral = .707;
                    forward = -.707;
                } else if(lateral < -.1){
                    lateral = -.707;
                    forward = -.707;
                }
            }

            if(gamepad1.x) {
                leftDrive /= 2.0;
                leftBack /= 2.0;
                rightDrive /= 2.0;
                rightBack /= 2.0;
            }
            robot.shaftController.setPower(gamepad1.right_trigger - gamepad1.left_trigger);

            robot.leftDrive.setPower(leftDrive);
            robot.leftBack.setPower(leftBack);
            robot.rightDrive.setPower(rightDrive);
            robot.rightBack.setPower(rightBack);

            if(gamepad1.a){
                robot.grabber.setPosition(.5);
            } else if (gamepad1.b){
                robot.grabber.setPosition(1);
            }

            if(gamepad1.right_bumper) {
                robot.tail.setPosition(0);
            } else if (gamepad1.left_bumper) {
                robot.tail.setPosition(1);
            } else {
                robot.tail.setPosition(.5);
            }
            telemetry.addData("tail position", "%.2f", robot.tail.getPosition());
            telemetry.addData("grabber position","%.2f", robot.grabber.getPosition());
            telemetry.addData("lateral",  "%.2f", lateral);
            telemetry.addData("forward", "%.2f", forward);
            telemetry.update();

            // Pause for 40 mS each cycle = update 25 times a second.
            sleep(10);
        }
    }
}
