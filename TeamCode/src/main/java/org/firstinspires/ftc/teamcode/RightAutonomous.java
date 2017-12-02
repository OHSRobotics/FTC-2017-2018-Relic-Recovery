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
        helper.drive(1.0, -12.0);
        hitJewel();
        helper.drive(1.0, 12.0);
        if (vuMark == RelicRecoveryVuMark.LEFT){
            helper.goSideways(1.0, -36.0);
        }
        else if (vuMark == RelicRecoveryVuMark.CENTER){
            helper.goSideways(1.0, 28.0);
        }
        else if((vuMark ==  RelicRecoveryVuMark.RIGHT) || (vuMark == RelicRecoveryVuMark.UNKNOWN)){
            helper.goSideways(1.0, 20.0);
        }
        helper.drive(1.0, -12.0);
    }
}