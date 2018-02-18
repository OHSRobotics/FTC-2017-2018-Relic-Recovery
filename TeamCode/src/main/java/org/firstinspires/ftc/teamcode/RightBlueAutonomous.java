package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
@Autonomous(name="Right Blue Autonomous", group="K9bot")
public class RightBlueAutonomous extends AutonomousBase {

    public RightBlueAutonomous() {
        super(false);
}
    @Override// Sudo code by Justin Kim
    public void runOpModeImpl() {

        helper.calibrate();
        helper.drive(SLOW_SPEED, -7.0);
        hitJewel();
        helper.drive(FAST_SPEED, DRIVE_BACK_INCHES);
        helper.rotate(-87, SLOW_SPEED, false);

        double middle = 36 - 5;
        if (vuMark == RelicRecoveryVuMark.RIGHT) {
            helper.drive(MEDIUM_SPEED, middle + 8);
        }
        else if (vuMark == RelicRecoveryVuMark.CENTER) {
            helper.drive(MEDIUM_SPEED, middle);
        }
        else if((vuMark ==  RelicRecoveryVuMark.LEFT) || (vuMark == RelicRecoveryVuMark.UNKNOWN)) {
            helper.drive(MEDIUM_SPEED, middle - 8);
        }
        helper.rotate(-42, SLOW_SPEED, false);
        helper.diaganolDrive(MEDIUM_SPEED, 60, 'l');
    }
}