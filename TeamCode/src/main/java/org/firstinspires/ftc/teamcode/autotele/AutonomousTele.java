package org.firstinspires.ftc.teamcode.autotele;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.corningrobotics.enderbots.endercv.CameraViewDisplay;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.teamcode.HardwareK9bot;
import org.firstinspires.ftc.teamcode.MovementHelper;
import org.firstinspires.ftc.teamcode.OpModeBase;

@TeleOp(name="Troy's Auto Tele 2", group="K9bot")
public class AutonomousTele extends OpModeBase {

    private HardwareK9bot robot = new HardwareK9bot();
    private MovementHelper helper;
    private AutoTeleVision vision;

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);
        for(DcMotor motor : robot.motors){
            motor.setPower(0);
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
        helper = new MovementHelper(false, robot, this);
        robot.gyro.realCalibrate();
        vision = new AutoTeleVision(this);
        vision.init(hardwareMap.appContext, CameraViewDisplay.getInstance());
        waitForStart();
        vision.enable();
        while(opModeIsActive()) {
            
        }
        vision.disable();
    }
}
