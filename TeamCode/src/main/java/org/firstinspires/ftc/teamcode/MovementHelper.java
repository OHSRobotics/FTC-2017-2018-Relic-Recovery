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

public class MovementHelper{
    private boolean red;

    private HardwareK9bot map;
    private AutonomousTest opMode;
    private double circumference = 40.82;

    public MovementHelper(boolean red, HardwareK9bot map, AutonomousTest opMode) {
        this.red = red;
        this.map = map;
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


        map.leftDrive.setTargetPosition(turnTarget);
        map.leftBack.setTargetPosition(turnTarget);
        map.rightDrive.setTargetPosition(-turnTarget);
        map.rightBack.setTargetPosition(-turnTarget);

        for(DcMotor motor : map.motors){
            motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motor.setPower(speed);
        }
    }


    public void goSideways(double speed, double targetDistance){
        if(red)
            targetDistance = -targetDistance;

        int inwardTarget, outwardTarget;
        if (opMode.opModeIsActive()){
            inwardTarget = map.rightBack.getCurrentPosition() + (int)(targetDistance * AutonomousTest.COUNTS_PER_INCH * Math.sqrt(2));
            outwardTarget = map.rightDrive.getCurrentPosition() + (int)(targetDistance * AutonomousTest.COUNTS_PER_INCH * -1 * Math.sqrt(2));
            map.leftDrive.setTargetPosition(inwardTarget);
            map.leftBack.setTargetPosition(inwardTarget);
            map.rightDrive.setTargetPosition(outwardTarget);
            map.rightBack.setTargetPosition(outwardTarget);

            map.leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            map.leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            map.rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            map.rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            if (inwardTarget > 0){
                map.leftBack.setPower(speed);
                map.rightDrive.setPower(speed);
                map.leftDrive.setPower(-speed);
                map.rightBack.setPower(-speed);
            } else {
                map.leftBack.setPower(-speed);
                map.rightDrive.setPower(-speed);
                map.leftDrive.setPower(speed);
                map.rightBack.setPower(speed);
            }
        }
    }

    public void goForward(double speed, double targetDistance){
        int target = map.rightBack.getCurrentPosition() + (int)(targetDistance * AutonomousTest.COUNTS_PER_INCH);
        for(DcMotor motor : map.motors){
            motor.setTargetPosition(target);
            motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motor.setPower(speed);
        }
    }

}