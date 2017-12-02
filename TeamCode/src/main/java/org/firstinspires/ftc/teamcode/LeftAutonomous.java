package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;

@Autonomous(name="Left Autonomous", group="K9bot")
public class LeftAutonomous extends AutonomousBase {

    public LeftAutonomous() {
        super(true);
    }

    @Override
    public void runOpModeImpl() {
        helper.drive(SLOW_SPEED, -7.0);
        hitJewel();
        helper.drive(FAST_SPEED, 35);
        helper.rotate(-85, SLOW_SPEED, false);
        helper.drive(MEDIUM_SPEED, 33);
        helper.rotate(-45, SLOW_SPEED, true);
        if (vuMark == RelicRecoveryVuMark.RIGHT) {
            helper.diaganolDrive(FAST_SPEED, 4, 'l');
        }
        else if (vuMark == RelicRecoveryVuMark.CENTER) {
            helper.diaganolDrive(FAST_SPEED, 12, 'l');
        }
        else if((vuMark ==  RelicRecoveryVuMark.LEFT) || (vuMark == RelicRecoveryVuMark.UNKNOWN)) {
            helper.diaganolDrive(FAST_SPEED, 20, 'l');
        }
        helper.diaganolDrive(MEDIUM_SPEED, 20, 'r');


        /*
        helper.drive(1.0, 12.0);
        helper.rotate(90, 1.0);
        if (vuMark == RelicRecoveryVuMark.RIGHT) {
            helper.goSideways(1.0, -8.0);
        }
        else if (vuMark == RelicRecoveryVuMark.CENTER) {
            helper.goSideways(1.0, 16.0);
        }
        else if((vuMark ==  RelicRecoveryVuMark.LEFT) || (vuMark == RelicRecoveryVuMark.UNKNOWN)) {
            helper.goSideways(1.0, 20.0);
        }
        helper.drive(1.0, -48.0); */
    }
}
