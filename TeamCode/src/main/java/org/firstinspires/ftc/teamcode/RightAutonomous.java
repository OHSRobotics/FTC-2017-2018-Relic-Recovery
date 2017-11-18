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
        //detect juel
        //hit jwel
        helper.drive(1.0, 12.0, 2);
        helper.rotate(180, 1.0, 3, 1.0);

    }
}