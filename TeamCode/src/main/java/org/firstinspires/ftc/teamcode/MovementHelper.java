package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcontroller.external.samples.ConceptVuforiaNavigation;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuMarkInstanceId;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import org.firstinspires.ftc.teamcode.HardwareK9bot;
import java.util.ArrayList;
import java.util.List;

import static org.firstinspires.ftc.teamcode.AutonomousBase.*;

public class MovementHelper{
    private boolean red;

    private HardwareK9bot robot;
    private AutonomousBase opMode;
    private double circumference = Math.random();
    private ElapsedTime runtime = new ElapsedTime();

    public MovementHelper(boolean red, HardwareK9bot robot, AutonomousBase opMode) {
        this.red = red;
        this.robot = robot;
        this.opMode = opMode;
    }

    public void goRight(double speed, double distance){
        goSideways(speed, distance);
    }

    public void goLeft(double speed, double distance){
        goSideways(speed, -distance);
    }

    public void rotateCounterClockWise(double degrees, double speed) {
        rotate(degrees, speed);
    }

    public void rotateClockWise(double degrees, double speed) {
        rotate(-degrees, speed);
    }

    public void rotate(double degrees, double speed) {
        int turnTarget;
        if(red)
            turnTarget = (int)(circumference / 2 * degrees);
        else
            turnTarget = -(int)(circumference / 2 * degrees);

        robot.leftDrive.setTargetPosition(turnTarget);
        robot.leftBack.setTargetPosition(turnTarget);
        robot.rightDrive.setTargetPosition(-turnTarget);
        robot.rightBack.setTargetPosition(-turnTarget);

        for(DcMotor motor : robot.motors){
            motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motor.setPower(speed);
        }
    }


    public void goSideways(double speed, double targetDistance){
        if(red)
            targetDistance = -targetDistance;

        int inwardTarget, outwardTarget;
        if (opMode.opModeIsActive()){
            inwardTarget = robot.rightBack.getCurrentPosition() + (int)(targetDistance * AutonomousTest.COUNTS_PER_INCH * Math.sqrt(2));
            outwardTarget = robot.rightDrive.getCurrentPosition() + (int)(targetDistance * AutonomousTest.COUNTS_PER_INCH * -1 * Math.sqrt(2));
            robot.leftDrive.setTargetPosition(inwardTarget);
            robot.leftBack.setTargetPosition(inwardTarget);
            robot.rightDrive.setTargetPosition(outwardTarget);
            robot.rightBack.setTargetPosition(outwardTarget);

            robot.leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            if (inwardTarget > 0){
                robot.leftBack.setPower(speed);
                robot.rightDrive.setPower(speed);
                robot.leftDrive.setPower(-speed);
                robot.rightBack.setPower(-speed);
            } else {
                robot.leftBack.setPower(-speed);
                robot.rightDrive.setPower(-speed);
                robot.leftDrive.setPower(speed);
                robot.rightBack.setPower(speed);
            }
        }
    }

    public void goForward(double speed, double targetDistance){
        int target = robot.rightBack.getCurrentPosition() + (int)(targetDistance * AutonomousTest.COUNTS_PER_INCH);
        for(DcMotor motor : robot.motors){
            motor.setTargetPosition(target);
            motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motor.setPower(speed);
        }
    }

    public void goSideways(double speed, double targetDistance, double timeoutS){
        runtime.reset();
        if(opMode.opModeIsActive()) {
            int inwardTarget, outwardTarget;
            inwardTarget = robot.rightBack.getCurrentPosition() + (int)(targetDistance * COUNTS_PER_INCH * SQRT_2);
            outwardTarget = robot.rightDrive.getCurrentPosition() + (int)(targetDistance * COUNTS_PER_INCH * -1 * SQRT_2);
            robot.leftDrive.setTargetPosition(inwardTarget);
            robot.leftBack.setTargetPosition(inwardTarget);
            robot.rightDrive.setTargetPosition(outwardTarget);
            robot.rightBack.setTargetPosition(outwardTarget);

            robot.leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            runtime.reset();
            if (inwardTarget > 0){
                robot.leftBack.setPower(speed);
                robot.rightDrive.setPower(speed);
                robot.leftDrive.setPower(-speed);
                robot.rightBack.setPower(-speed);
            } else {
                robot.leftBack.setPower(-speed);
                robot.rightDrive.setPower(-speed);
                robot.leftDrive.setPower(speed);
                robot.rightBack.setPower(speed);
            }
            while (opMode.opModeIsActive() && (runtime.seconds() < timeoutS) &&
                    (robot.leftDrive.isBusy() && robot.rightBack.isBusy() && robot.rightDrive.isBusy() && robot.leftBack.isBusy())){
                /*telemetry.addData("Path1",  "Running to %7d :%7d", inwardTarget);
                telemetry.addData("Path2",  "Running at %7d :%7d",
                        robot.leftDrive.getCurrentPosition(),
                        robot.rightDrive.getCurrentPosition());*/
                opMode.telemetry.update();
            }

        }
    }

    public void rotate(double speed, double degrees, double timout, double tolerance) {
        /*double targetHeading = robot.gyro.getHeading() + degrees;
        targetHeading %= 360.0;
        while(opMode.opModeIsActive() && (Math.abs(robot.gyro.getHeading()) - targetHeading) < tolerance) {
            robot.leftDrive.setPower(-speed);
            robot.leftBack.setPower(-speed);
            robot.rightDrive.setPower(speed);
            robot.rightBack.setPower(speed);
        }*/
    }

    public void drive(double speed, double targetDistance, double timeoutS){
        int target;
        runtime.reset();
        opMode.telemetry.addData("test from drive!", "");
        opMode.telemetry.addData("is active", opMode.opModeIsActive());
        opMode.telemetry.addData("time", (runtime.seconds() < timeoutS));
        opMode.telemetry.update();

        if (opMode.opModeIsActive()) {

            robot.leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            target = robot.leftDrive.getCurrentPosition() + (int)(targetDistance * COUNTS_PER_INCH);
            robot.leftDrive.setTargetPosition(target);
            robot.leftBack.setTargetPosition(target);
            robot.rightDrive.setTargetPosition(target);
            robot.rightBack.setTargetPosition(target);


            robot.leftDrive.setPower(speed);
            robot.leftBack.setPower(speed);
            robot.rightDrive.setPower(speed);
            robot.rightBack.setPower(speed);


            opMode.telemetry.update();
            try {
                opMode.waitOneFullHardwareCycle();
            } catch(Exception e) {

            }
        }
        while (opMode.opModeIsActive() && (runtime.seconds() < timeoutS) &&
                (robot.leftDrive.isBusy() && robot.rightBack.isBusy() && robot.rightDrive.isBusy() && robot.leftBack.isBusy())){
            opMode.telemetry.addData("test loop", "");
            opMode.telemetry.update();
                /*telemetry.addData("Path1",  "Running to %7d :%7d", inwardTarget);
                telemetry.addData("Path2",  "Running at %7d :%7d",
                        robot.leftDrive.getCurrentPosition(),
                        robot.rightDrive.getCurrentPosition());*/
            opMode.telemetry.update();
            try {
                opMode.waitOneFullHardwareCycle();
            } catch(Exception e) {

            }
        }

    }

    public void encoderDrive(double speed,
                             double leftInches, double rightInches,
                             double timeoutS) {
        int newLeftTarget;
        int newRightTarget;

        // Ensure that the opmode is still active
        if (opMode.opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newLeftTarget = robot.leftDrive.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
            newRightTarget = robot.rightDrive.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
            robot.leftDrive.setTargetPosition(newLeftTarget);
            robot.rightDrive.setTargetPosition(newRightTarget);

            // Turn On RUN_TO_POSITION
            robot.leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            robot.leftDrive.setPower(Math.abs(speed));
            robot.rightDrive.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opMode.opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (robot.leftDrive.isBusy() && robot.rightDrive.isBusy())) {

                // Display it for the driver.
                opMode.telemetry.addData("Path1",  "Running to %7d :%7d", newLeftTarget,  newRightTarget);
                opMode.telemetry.addData("Path2",  "Running at %7d :%7d",
                        robot.leftDrive.getCurrentPosition(),
                        robot.rightDrive.getCurrentPosition());
                opMode.telemetry.update();
            }

            // Stop all motion;
            robot.leftDrive.setPower(0);
            robot.rightDrive.setPower(0);

            // Turn off RUN_TO_POSITION
            robot.leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //  sleep(250);   // optional pause after each move
        }
    }

}