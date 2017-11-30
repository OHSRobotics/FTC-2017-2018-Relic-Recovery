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
        telemetry.addData("starting first drive!", "");
        telemetry.update();
        helper.drive(0.1, -12.0);
        telemetry.addData("Done with first drive!", "");
        telemetry.update();
        sleep(5000);
        boolean leftRed = vision.leftRed();
        //hit jewel
        telemetry.addData("starting second drive!", "");
        telemetry.update();
        helper.drive(1.0, 12.0);
        telemetry.addData("Done with second drive!", "");
        telemetry.update();
        sleep(5000);
        telemetry.addData("starting rotate!", "");
        telemetry.update();
        helper.rotate(90, 1.0);
        telemetry.addData("Done with rotate!", "");
        telemetry.update();
        sleep(5000);

        if (vuMark == RelicRecoveryVuMark.RIGHT){
            helper.goSideways(1.0, -8.0);
        }
        else if (vuMark == RelicRecoveryVuMark.CENTER){
            helper.goSideways(1.0, 16.0);
        }
        else if((vuMark ==  RelicRecoveryVuMark.LEFT) || (vuMark == RelicRecoveryVuMark.UNKNOWN)){
            helper.goSideways(1.0, 20.0);
        }
        helper.drive(1.0, -48.0);
    }
}
