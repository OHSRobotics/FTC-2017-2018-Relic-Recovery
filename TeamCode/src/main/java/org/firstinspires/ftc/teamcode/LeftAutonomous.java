package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by Troy Neubauer on 10/28/2017.
 */
@Autonomous(name="Left Autonomous", group="K9bot")
public class LeftAutonomous extends OpModeBase {
    HardwareK9bot   robot           = new HardwareK9bot();
    AutonomousTest aTest = new AutonomousTest();

    public MovementHelper mHelper = new MovementHelper(true, robot, aTest);
    public void runOpMode() throws InterruptedException {
        mHelper.goForward(.7, 10);
    }
}
