package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.HardwareK9bot;

@TeleOp(name="K9bot: Telop Tank", group="K9bot")
@Disabled
public class K9botTeleopTank_Linear extends LinearOpMode {

    /* Declare OpMode members. */
    HardwareK9bot   robot           = new HardwareK9bot();              // Use a K9'shardware

    @Override
    public void runOpMode() {
        double left;
        double right;

        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Hello Driver");    //
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // Run wheels in tank mode (note: The joystick goes negative when pushed forwards, so negate it)
            /*left = -gamepad1.left_stick_y;
            right = -gamepad1.right_stick_y;
            robot.leftDrive.setPower(left);
            robot.rightDrive.setPower(right);
            */
            double turn = gamepad1.right_stick_x;
            double lateral = gamepad1.left_stick_x;
            double forward = -gamepad1.left_stick_y;

            robot.leftDrive.setPower(forward + lateral + turn);
            robot.leftBack.setPower(forward - lateral +  turn);
            robot.rightDrive.setPower(forward - lateral - turn);
            robot.rightBack.setPower(forward + lateral - turn);
            /*
            // Use gamepad Y & A raise and lower the arm
            if (gamepad1.a)
                armPosition += ARM_SPEED;
            else if (gamepad1.y)
                armPosition -= ARM_SPEED;

            // Use gamepad X & B to open and close the claw
            if (gamepad1.x)
                clawPosition += CLAW_SPEED;
            else if (gamepad1.b)
                clawPosition -= CLAW_SPEED;

            // Move both servos to new position.
            armPosition  = Range.clip(armPosition, robot.ARM_MIN_RANGE, robot.ARM_MAX_RANGE);
            robot.arm.setPosition(armPosition);
            clawPosition = Range.clip(clawPosition, robot.CLAW_MIN_RANGE, robot.CLAW_MAX_RANGE);
            robot.claw.setPosition(clawPosition);

            // Send telemetry message to signify robot running;
            telemetry.addData("arm",   "%.2f", armPosition);
            telemetry.addData("claw",  "%.2f", clawPosition);
            telemetry.addData("left",  "%.2f", left);
            telemetry.addData("right", "%.2f", right);
            telemetry.update();
            */
            // Pause for 40 mS each cycle = update 25 times a second.
            sleep(40);
        }
    }
}