package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.teamcode.HardwareK9bot;

@TeleOp(name="K9bot: Telop Tank", group="K9bot")
public class K9botTeleopTank_Linear extends OpModeBase {

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

            // Run wheels in tank mode (note: The joystick goes negative when pushed forwards, so negate it)
            /*  left = -gamepad1.left_stick_y;
            right = -gamepad1.right_stick_y;
            robot.leftDrive.setPower(left);
            robot.rightDrive.setPower(right);
             */
            double turn = gamepad1.left_stick_x;
            double lateral = gamepad1.right_stick_x;
            double forward = -gamepad1.right_stick_y;

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
            */
            telemetry.addData("lateral",  "%.2f", lateral);
            telemetry.addData("forward", "%.2f", forward);
            telemetry.update();

            // Pause for 40 mS each cycle = update 25 times a second.
            sleep(10);
        }
    }
}