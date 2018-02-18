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
        newJewel();
        helper.rotate(-87, 0.15, false);
        helper.drive(0.2, 27);
        helper.rotate(-45, -0.15, false);
        if (vuMark == RelicRecoveryVuMark.LEFT) {
            helper.diaganolDrive(-0.2, -8, 'r');
        }
        else if (vuMark == RelicRecoveryVuMark.RIGHT) {
            helper.diaganolDrive(.2, 5, 'r');
        }
        else if((vuMark ==  RelicRecoveryVuMark.CENTER) || (vuMark == RelicRecoveryVuMark.UNKNOWN)) {
            helper.diaganolDrive(-.2, -2, 'r');
        }
        helper.diaganolDrive(0.4, 10, 'l');
    }
}
