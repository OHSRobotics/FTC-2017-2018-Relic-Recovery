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
@Autonomous(name="Right Autonomous", group="K9bot")
public class RightAutonomous extends AutonomousBase {

    public RightAutonomous() {
        super(true);
    }

    @Override// Sudo code by Justin Kim
    public void runOpModeImpl() {
        helper.drive(SLOW_SPEED, -7.0);
        hitJewel();
        helper.drive(FAST_SPEED, 35);
        helper.rotate(-90, SLOW_SPEED, false);

        double middle = 36 - 5;
        if (vuMark == RelicRecoveryVuMark.RIGHT) {
            helper.drive(FAST_SPEED, middle + 8);
        }
        else if (vuMark == RelicRecoveryVuMark.CENTER) {
            helper.drive(FAST_SPEED, middle);
        }
        else if((vuMark ==  RelicRecoveryVuMark.LEFT) || (vuMark == RelicRecoveryVuMark.UNKNOWN)) {
            helper.drive(FAST_SPEED, middle - 8);
        }
        helper.rotate(-45, SLOW_SPEED, true);
        helper.diaganolDrive(FAST_SPEED, 60, 'l');
    }
}