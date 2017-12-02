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
        //extend tail
        helper.drive(0.1, -7.0);
        hitJewel();
        helper.drive(0.4, 35);
        helper.rotate(-85, 0.1, false);
        helper.drive(0.2, 33);
        helper.rotate(-45, 0.1, true);
        if (vuMark == RelicRecoveryVuMark.RIGHT) {
            helper.diaganolDrive(0.4, 4, 'l');
        }
        else if (vuMark == RelicRecoveryVuMark.CENTER) {
            helper.diaganolDrive(0.4, 12, 'l');
        }
        else if((vuMark ==  RelicRecoveryVuMark.LEFT) || (vuMark == RelicRecoveryVuMark.UNKNOWN)) {
            helper.diaganolDrive(0.4, 20, 'l');
        }
        helper.diaganolDrive(0.2, 20, 'r');


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
