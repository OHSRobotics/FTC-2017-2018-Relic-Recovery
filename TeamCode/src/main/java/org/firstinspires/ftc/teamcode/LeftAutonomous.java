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
        telemetry.addData("Starting opmode!", "");
        telemetry.update();
        //getVuMark();
        //waitForStart();
        //extend tail
        helper.drive(1.0, -12.0, 2);
        telemetry.addData("done driving", "");
        telemetry.update();
        //detect jewel, detect vumark
        //hit jewel
        helper.drive(1.0, 12.0, 2);
        //helper.rotate(90, 1.0, 1.5, 1.0);
        telemetry.addData("done rotation", "");
        telemetry.update();
        if (vuMark == RelicRecoveryVuMark.RIGHT){
            helper.goSideways(1.0, -8.0, 3);
        }
        else if (vuMark == RelicRecoveryVuMark.CENTER){
            helper.goSideways(1.0, 16.0, 4);
        }
        else if((vuMark ==  RelicRecoveryVuMark.LEFT) || (vuMark == RelicRecoveryVuMark.UNKNOWN)){
            helper.goSideways(1.0, 20.0, 4);
        }
        helper.drive(1.0, -48.0, 12);
    }
}
