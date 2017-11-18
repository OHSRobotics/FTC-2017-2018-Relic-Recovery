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
    public void runOpMode() throws InterruptedException {
        getVuMark();
        waitForStart();
        //extend tail
        helper.drive(1.0, -12.0, 2);
        //detect jewel, detect vumark
        //hit jewel
        helper.drive(1.0, 12.0, 2);
        helper.rotate(180, 1.0, 3, 1.0);
        if (vuMark == RelicRecoveryVuMark.LEFT){
            helper.goSideways(1.0, -36.0, 6);
        }
        else if (vuMark == RelicRecoveryVuMark.CENTER){
            helper.goSideways(1.0, 28.0, 5);
        }
        else if((vuMark ==  RelicRecoveryVuMark.RIGHT) || (vuMark == RelicRecoveryVuMark.UNKNOWN)){
            helper.goSideways(1.0, 20.0, 4);
        }
        helper.drive(1.0, -12.0, 3);
    }
}