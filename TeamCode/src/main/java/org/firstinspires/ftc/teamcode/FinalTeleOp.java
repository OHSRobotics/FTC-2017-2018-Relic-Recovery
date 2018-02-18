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

@TeleOp(name="Telop Final", group="K9bot")
public class FinalTeleOp extends OpModeBase {
    HardwareK9bot   robot           = new HardwareK9bot();
    private static boolean useSingleController = false;

    @Override
    public void runOpMode() {

        robot.init(hardwareMap);
        for(DcMotor motor :robot.motors){
            motor.setPower(0);
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "haha justin is an idi0t");    //
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        boolean slowMode = false;
        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            double turn = getGamepad(1).right_stick_x;
            double lateral = getGamepad(1).left_stick_x;
            double forward = -getGamepad(1).left_stick_y;

            double leftDrive, leftBack, rightDrive, rightBack;

            leftDrive = forward + lateral + turn;
            leftBack = forward - lateral +  turn;
            rightDrive = forward - lateral - turn;
            rightBack = forward + lateral - turn;


            if(gamepad1.y)
                slowMode = true;
            else if (gamepad1.x)
                slowMode = false;


            if(slowMode) {
                leftDrive /= 3.0;
                leftBack /= 3.0;
                rightDrive /= 3.0;
                rightBack /= 3.0;
            }
            robot.shaftController.setPower(getGamepad(2).right_trigger - getGamepad(2).left_trigger);
            robot.leftDrive.setPower(leftDrive);
            robot.leftBack.setPower(leftBack);
            robot.rightDrive.setPower(rightDrive);
            robot.rightBack.setPower(rightBack);

            if(getGamepad(2).a){//A is grab
                robot.grabberL.setPosition(.9);
                robot.grabberR.setPosition(.45);
            } else if (getGamepad(2).b){//B is releasehhftt
                robot.grabberL.setPosition(.45);
                robot.grabberR.setPosition(.9);
            } else if(getGamepad(2).x) {//A is grab
                robot.grabberL.setPosition(.75);
                robot.grabberR.setPosition(.7);
            }
            robot.tail.setPosition(0);
            telemetry.addData("Lift Power: ", getGamepad(2).right_trigger - getGamepad(2).left_trigger);
            telemetry.addData("Left Front", leftDrive);
            telemetry.addData("Left Back", leftBack);
            telemetry.addData("Right Front", rightDrive);
            telemetry.addData("Right Back", rightBack);
            telemetry.addData("Gyro:", robot.gyro.getHeading());
            telemetry.update();

            // Pause for 40 mS each cycle = update 25 times a second.
            sleep(20);
        }
    }

    private Gamepad getGamepad(int number) {
        if(useSingleController) return gamepad1;
        if(number == 1) return gamepad1;
        else return gamepad2;
    }
}
