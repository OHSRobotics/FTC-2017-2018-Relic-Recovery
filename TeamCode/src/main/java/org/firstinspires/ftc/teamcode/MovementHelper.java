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

    public void rotate(int degrees, double speed, boolean reset) {
        /*
        double turnTargetR = -(15.65 * Math.PI * (degrees / 360));
        double turnTargetL = -turnTargetR;
        */
        if(reset)
            robot.gyro.calibrate();
        while(robot.gyro.isCalibrating()){
            opMode.telemetry.addData("chill out yo", "calibrating");
            opMode.telemetry.update();
        }
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
            heading = -robot.gyro.getIntegratedZValue();
            //if(degrees > 0)
            //    heading = -(robot.gyro.getHeading() - 360);
            opMode.telemetry.addData("target", degrees);
            opMode.telemetry.addData("current", heading);
            opMode.telemetry.addData("offset", Math.abs(heading - degrees));
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

    public void drive(double speed, double targetDistance){

        if(targetDistance < 0) {
            speed = -Math.abs(speed);
        }
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

    public void diaganolDrive(double speed, int targetDistance, char direction){

        if (opMode.opModeIsActive()) {
            opMode.telemetry.addData("", "op mode active!");
            opMode.telemetry.update();
            for(DcMotor motor : robot.motors){
                motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            }
            if(direction =='l'){
                robot.rightDrive.setPower(speed);
                robot.leftBack.setPower(speed);
            } else {
                robot.leftDrive.setPower(speed);
                robot.rightBack.setPower(speed);
            }

        }
        while (opMode.opModeIsActive() && (Math.abs(robot.rightBack.getCurrentPosition() + robot.leftBack.getCurrentPosition() - (int)(targetDistance / 12.57 * 1120 * Math.sqrt(2) * 1.16))) > 50 ){
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

}