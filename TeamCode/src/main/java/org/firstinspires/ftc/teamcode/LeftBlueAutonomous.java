package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;

@Autonomous(name="Left Blue Autonomous", group="K9bot")
public class LeftBlueAutonomous extends AutonomousBase {

    public LeftBlueAutonomous() {
        super(false);
    }

    @Override
    public void runOpModeImpl() {
        helper.calibrate();
        helper.drive(SLOW_SPEED, -7.0);
        hitJewel();
        helper.drive(FAST_SPEED, DRIVE_BACK_INCHES);
        helper.rotate(-87, SLOW_SPEED, false);
        helper.drive(MEDIUM_SPEED, 22);
        helper.rotate(-45, SLOW_SPEED, true);
        if (vuMark == RelicRecoveryVuMark.RIGHT) {
            helper.diaganolDrive(MEDIUM_SPEED, 0, 'l');
        }
        else if (vuMark == RelicRecoveryVuMark.CENTER) {
            helper.diaganolDrive(MEDIUM_SPEED, 8, 'l');
        }
        else if((vuMark ==  RelicRecoveryVuMark.LEFT) || (vuMark == RelicRecoveryVuMark.UNKNOWN)) {
            helper.diaganolDrive(MEDIUM_SPEED,16 , 'l');
        }
        helper.diaganolDrive(MEDIUM_SPEED, 20, 'r');


        /*+
        se if (vuMark == RelicRecoveryVuMark.CENTER) {
            helper.goSideways(1.0, 16.0);
        }
        else if((vuMark ==  RelicRecoveryVuMark.LEFT) || (vuMark == RelicRecoveryVuMark.UNKNOWN)) {
            helper.goSideways(1.0, 20.0);
        }
        helper.drive(1.0, -48.0); */
    }
}
