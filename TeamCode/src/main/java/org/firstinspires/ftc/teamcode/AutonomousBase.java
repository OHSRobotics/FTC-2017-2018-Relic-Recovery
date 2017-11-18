package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

public abstract class AutonomousBase extends OpModeBase {

    private HardwareK9bot   robot           = new HardwareK9bot();   // Use a Pushbot's hardware

    protected static final double     COUNTS_PER_MOTOR_REV    = 1440;    // eg: TETRIX Motor Encoder
    protected static final double     DRIVE_GEAR_REDUCTION    = 2.0;     // This is < 1.0 if geared UP
    protected static final double     WHEEL_DIAMETER_INCHES   = 4.0;     // For figuring circumference
    protected static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * Math.PI);
    protected static final double     DRIVE_SPEED             = 0.6;
    protected static final double     TURN_SPEED              = 0.5;
    protected static RelicRecoveryVuMark vuMark;
    public static final double SQRT_2 = Math.sqrt(2.0);
    public MovementHelper helper;

    public AutonomousBase(boolean red) {
        this.helper = new MovementHelper(red, robot, this);
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
        while (true) {
            vuMark = RelicRecoveryVuMark.from(relicTemplate);
            if (vuMark != RelicRecoveryVuMark.UNKNOWN)
                break;
        }

        telemetry.addData("VuMark", "%s visible", vuMark);
    }

}
