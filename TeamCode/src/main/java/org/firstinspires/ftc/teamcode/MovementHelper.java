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

    public void rotateCounterClockWise(int degrees, double speed) {
        rotate(degrees, speed);
    }

    public void rotateClockWise(int degrees, double speed) {
        rotate(-degrees, speed);
    }

    public void rotate(int degrees, double speed) {
        /*
        double turnTargetR = -(15.65 * Math.PI * (degrees / 360));
        double turnTargetL = -turnTargetR;
        */
        robot.gyro.calibrate();
        for(DcMotor motor : robot.motors){
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
        if (degrees > 0) {
            robot.leftDrive.setPower(speed);
            robot.leftBack.setPower(speed);
            robot.rightBack.setPower(-speed);
            robot.rightDrive.setPower(-speed);
        } else {
            robot.leftDrive.setPower(-speed);
            robot.leftBack.setPower(-speed);
            robot.rightBack.setPower(speed);
            robot.rightDrive.setPower(speed);
        }
        /*
        while (opMode.opModeIsActive() && Math.abs(-robot.leftBack.getCurrentPosition() - (int)(turnTargetR / 12.57 * 1120)) > 50){
            double currentInches = robot.leftBack.getCurrentPosition();
            opMode.telemetry.addData("target", turnTargetR);
            opMode.telemetry.addData("current", currentInches);
            opMode.telemetry.addData("offset", -robot.leftBack.getCurrentPosition() - (int)(turnTargetR / 12.57 * 1120));
            opMode.telemetry.update();
        }*/
        int heading = 0;
        while (opMode.opModeIsActive() && Math.abs(heading - degrees) > 3){
            heading = robot.gyro.getHeading();
            if(degrees < 0)
                heading -= 360;
            opMode.telemetry.addData("target", degrees);
            opMode.telemetry.addData("current", heading);
            opMode.telemetry.addData("offset", Math.abs(robot.gyro.getHeading() - degrees));
            opMode.telemetry.update();
        }
        for(DcMotor motor : robot.motors){
            motor.setPower(0);
        }
    }


    public void goSideways(double speed, double targetDistance){
        if(red)
            targetDistance *= -1;

        for(DcMotor motor : robot.motors){
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
        int target = (int)(targetDistance / 12.57 * 1120 * Math.sqrt(2));
        if (opMode.opModeIsActive()){
            if (target > 0){
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
        while (opMode.opModeIsActive() && (Math.abs(-robot.rightBack.getCurrentPosition() - target)) > 50 ){
            double currentTicks = robot.rightBack.getCurrentPosition();
            opMode.telemetry.addData("target", target);
            opMode.telemetry.addData("current", currentTicks);
            opMode.telemetry.update();
        }
        for(DcMotor motor : robot.motors){
            motor.setPower(0);
        }
    }

    public void drive(double speed, double targetDistance, double timeoutS){

        if(targetDistance < 0) {
            speed = -Math.abs(speed);
        }
        int target;
        runtime.reset();
        opMode.telemetry.addData("test from drive!", "");
        opMode.telemetry.addData("is active", opMode.opModeIsActive());
        opMode.telemetry.addData("time", (runtime.seconds() < timeoutS));
        opMode.telemetry.update();

        if (opMode.opModeIsActive()) {
            opMode.telemetry.addData("", "op mode active!");
            opMode.telemetry.update();
            for(DcMotor motor : robot.motors){
                motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            }
            for(DcMotor motor : robot.motors){
                motor.setPower(speed);
            }


            opMode.telemetry.update();
            try {
                opMode.waitOneFullHardwareCycle();
            } catch(Exception e) {

            }
        }
        while (opMode.opModeIsActive() && (Math.abs(robot.rightBack.getCurrentPosition() - (int)(targetDistance / 12.57 * 1120))) > 50 ){
                //(robot.leftDrive.isBusy() && robot.rightBack.isBusy() && robot.rightDrive.isBusy() && robot.leftBack.isBusy())){
            double currentTicks = robot.rightBack.getCurrentPosition();
            opMode.telemetry.addData("test loop", currentTicks);
            opMode.telemetry.update();
            try {
                opMode.waitOneFullHardwareCycle();
            } catch(Exception e) {

            }
        }
        for (DcMotor motor : robot.motors){
            motor.setPower(0);
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

            robot.leftDrive.setPower(0);
            robot.leftBack.setPower(0);
            robot.rightDrive.setPower(0);
            robot.rightBack.setPower(0);

            //  sleep(250);   // optional pause after each move
        }
    }

}