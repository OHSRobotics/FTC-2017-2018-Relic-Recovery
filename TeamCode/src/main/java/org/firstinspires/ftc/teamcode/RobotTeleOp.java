package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Troy Neubauer on 10/28/2017.
 */

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOP", group = "K9Bot")
public class RobotTeleOp extends OpModeBase {
    HardwareK9bot   robot           = new HardwareK9bot();

    @Override
    public void runOpMode() {

        robot.init(hardwareMap);

        robot.leftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.rightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.leftBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.rightBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "haha justin is an idi0t");    //
        telemetry.update();

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
            telemetry.addData("lateral",  "%.2f", lateral);
            telemetry.addData("forward", "%.2f", forward);
            telemetry.update();

            // Pause for 40 mS each cycle = update 25 times a second.
            sleep(10);
        }
    }
}
