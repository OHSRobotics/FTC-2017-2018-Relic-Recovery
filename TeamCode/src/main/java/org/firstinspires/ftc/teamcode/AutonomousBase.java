package org.firstinspires.ftc.teamcode;

import android.graphics.Bitmap;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.corningrobotics.enderbots.endercv.CameraViewDisplay;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

public abstract class AutonomousBase extends OpModeBase {

    private HardwareK9bot   robot           = new HardwareK9bot();   // Use a Pushbot's hardware

    protected static final double     COUNTS_PER_MOTOR_REV    = 280;    // eg: TETRIX Motor Encoder
    protected static final double     DRIVE_GEAR_REDUCTION    = 40.0;     // This is < 1.0 if geared UP
    protected static final double     WHEEL_DIAMETER_INCHES   = 4.0;     // For figuring circumference
    protected static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * Math.PI);
    protected static RelicRecoveryVuMark vuMark;
    public static final double SQRT_2 = Math.sqrt(2.0);
    public MovementHelper helper;
    protected AutonomousVision vision;
    private boolean red;

    public AutonomousBase(boolean red) {
        this.helper = new MovementHelper(red, robot, this);
        this.red = red;
    }

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);
        for(DcMotor motor : robot.motors){
            motor.setPower(0);
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }

        vision = new AutonomousVision(this);
        vision.init(hardwareMap.appContext, CameraViewDisplay.getInstance());
        vision.enable();
        getVuMark();

        waitForStart();
        robot.grabberL.setPosition(.45);// Grab the block
        robot.grabberR.setPosition(.9);
        robot.tail.setPosition(0.5);//Extend tail
        runOpModeImpl();
    }

   /* public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch(Exception e) {
        }
    }*/

    public abstract void runOpModeImpl();

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
        while (true) {
            vuMark = RelicRecoveryVuMark.from(relicTemplate);
            if (vuMark != RelicRecoveryVuMark.UNKNOWN)
                break;
        }

        telemetry.addData("VuMark", "%s visible", vuMark);
    }

    public boolean hitRight() {
        Bitmap bitmap = BitmapStealer.STEAL_BITMAP();
        int width = bitmap.getWidth(), halfWidth = width / 2, height = bitmap.getHeight();
        int[] left = new int[halfWidth * height];
        int[] right = new int[halfWidth * height];
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                if(x < halfWidth) {
                    left[(x % halfWidth) + y * halfWidth] = bitmap.getPixel(x, y);
                } else {
                    right[(x % halfWidth) + y * halfWidth] = bitmap.getPixel(x, y);
                }
            }
        }
        return CameraUtil.jewelRed(left, right);
    }

    public void hitJewel() {
        boolean leftRed = vision.leftRed();
        vision.disable();
        /*
        if(leftRed && red) {
            hitLeft = false;
        }
        if(!leftRed && red) {
            hitLeft = true;
        }
        if(leftRed && !red) {
            hitLeft = true;
        }
        if(!leftRed && !red) {
            hitLeft = false;
        }*/
        boolean hitLeft = leftRed ^ red;
        if(hitLeft) robot.tail.setPosition(1);
        else robot.tail.setPosition(0);
        try {
            waitOneFullHardwareCycle();
        } catch(Exception e) {

        }
        telemetry.update();
        sleep(200);
    }

}
