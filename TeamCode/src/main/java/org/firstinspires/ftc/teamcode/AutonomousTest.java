/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.provider.MediaStore;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcontroller.external.samples.ConceptVuforiaNavigation;
import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuMarkInstanceId;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.firstinspires.ftc.teamcode.HardwareK9bot;
import java.util.ArrayList;
import java.util.List;


@Autonomous(name="Autonomous Program Mode", group="K9bot")
public class AutonomousTest extends AutonomousBase {

    private HardwareK9bot   robot           = new HardwareK9bot();   // Use a Pushbot's hardware
    private ElapsedTime     runtime = new ElapsedTime();

    protected static final double     COUNTS_PER_MOTOR_REV    = 1440 ;    // eg: TETRIX Motor Encoder
    protected static final double     DRIVE_GEAR_REDUCTION    = 2.0 ;     // This is < 1.0 if geared UP
    protected static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
    protected static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);
    protected static final double     DRIVE_SPEED             = 0.6;
    protected static final double     TURN_SPEED              = 0.5;
    protected static RelicRecoveryVuMark vuMark;
    public static final double SQRT_2 = Math.sqrt(2.0);

    public AutonomousTest() {
        super(false);
    }

    @Override
    public void runOpModeImpl() {

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Resetting Encoders");    //
        telemetry.update();

        robot.leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Send telemetry message to indicate successful Encoder reset
        telemetry.addData("Path0",  "Starting at %7d :%7d",
                robot.leftDrive.getCurrentPosition(),
                robot.rightDrive.getCurrentPosition());
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)

        /*
        // Step through each leg of the path,
        // Note: Reverse movement is obtained by setting a negative distance (not speed)
        encoderDrive(DRIVE_SPEED,  48,  48, 5.0);  // S1: Forward 47 Inches with 5 Sec timeout
        encoderDrive(TURN_SPEED,   12, -12, 4.0);  // S2: Turn Right 12 Inches with 4 Sec timeout
        encoderDrive(DRIVE_SPEED, -24, -24, 4.0);  // S3: Reverse 24 Inches with 4 Sec timeout
        */
        //getVuMark();
        telemetry.addData("Path", "Complete");

        takePicture();
        telemetry.update();
        try {
            Thread.sleep(5000);
        } catch(Exception e) {

        }
        waitForStart();
    }

    private void takePicture() {
        Camera.open().takePicture(null, new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                int bpp = ImageFormat.getBitsPerPixel(camera.getParameters().getPictureFormat());
                if(bpp != 24) {
                    telemetry.addData("We're screwed! Not 24 bpp", Integer.valueOf(bpp));
                } else {
                    telemetry.addData("Good bpp", "");
                }
            }
        }, null);
        final int REQUEST_IMAGE_CAPTURE = 1;
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(hardwareMap.appContext.getPackageManager()) != null) {
            AppUtil.getInstance().getActivity().startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
        while(FtcRobotControllerActivity.bitmap == null) {
            try {
                telemetry.addData("Waiting for picture to be taken", null);
                Thread.sleep(100);
            } catch(Exception e) {
            }
        }
        telemetry.addData("Width " + FtcRobotControllerActivity.bitmap.getWidth(), null);
        telemetry.addData("Height " + FtcRobotControllerActivity.bitmap.getHeight(), null);
    }

    /*
     *  Method to perfmorm a relative move, based on encoder counts.
     *  Encoders are not reset as the move is based on the current position.
     *  Move will stop if any of three conditions occur:
     *  1) Move gets to the desired position
     *  2) Move runs out of time
     *  3) Driver stops the opmode running.
     */
    VuforiaLocalizer vuforia;
    public void getVuMark() {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "AVopUEP/////AAAAGWa6aLJRdEtZt4aOLr0I7ccF3KzNPNBTy+OBLJgM9NQYaJO8oMs2D+AYRl8btGOYhTX2/RLw7aPkSAshIAXVGzqJX9oHKdv0+P9iK4j516iEiuYROYb006Wl/WnluQ6gXpntcnLGxRt8ZhXU7xI2F7unR9CCIjRT2flUoMjM0EnJyRCwmU4m2v7gSdD3v+W4dhMrbO7jsJrHcoYsRExNfFoolV98sokj+p1aDfXTL73gnWaDaMatrwCBsAka8fsWYkEWSPwml3Uxlzym1C3T1rL5bEtIbD+LveBYDv1djxvNLfVbVVTn0b7Zoh4L5kFXDz5pbIDkui+m4j5cu9Y7N2zbSfEA0DP0ZLvLbTYHDxkn";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);
        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTrackables.activate();
        while(true) {
            vuMark = RelicRecoveryVuMark.from(relicTemplate);
            if (vuMark != RelicRecoveryVuMark.UNKNOWN)
                break;
        }

        telemetry.addData("VuMark", "%s visible", vuMark);
    }
    public void goSideways(double speed, double targetDistance, double timeoutS){
        int inwardTarget, outwardTarget;
        if (opModeIsActive()){
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
            /*
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (robot.leftDrive.isBusy() && robot.rightBack.isBusy() && robot.rightDrive.isBusy() && robot.leftBack.isBusy())){
                telemetry.addData("Path1",  "Running to %7d :%7d", inwardTarget);
                telemetry.addData("Path2",  "Running at %7d :%7d",
                        robot.leftDrive.getCurrentPosition(),
                        robot.rightDrive.getCurrentPosition());
                telemetry.update();
            }
            */
        }
    }
    public void drive(double speed, double targetDistance, double timeoutS){
        int target;
        if (opModeIsActive()) {
            target = robot.leftDrive.getCurrentPosition() + (int)(targetDistance * COUNTS_PER_INCH);
            robot.leftDrive.setTargetPosition(target);
            robot.leftBack.setTargetPosition(target);
            robot.rightBack.setTargetPosition(target);
            robot.rightDrive.setTargetPosition(target);

            runtime.reset();
            robot.leftBack.setPower(speed);
            robot.leftDrive.setPower(speed);
            robot.rightBack.setPower(speed);
            robot.rightDrive.setPower(speed);

        }
    }

    public void encoderDrive(double speed,
                             double leftInches, double rightInches,
                             double timeoutS) {
        int newLeftTarget;
        int newRightTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

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
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (robot.leftDrive.isBusy() && robot.rightDrive.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Path1",  "Running to %7d :%7d", newLeftTarget,  newRightTarget);
                telemetry.addData("Path2",  "Running at %7d :%7d",
                        robot.leftDrive.getCurrentPosition(),
                        robot.rightDrive.getCurrentPosition());
                telemetry.update();
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

    public ElapsedTime getElapsedTime() {
        return runtime;
    }
}
