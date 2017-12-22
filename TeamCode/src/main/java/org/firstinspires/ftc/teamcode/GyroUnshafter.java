package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;

import java.util.Stack;

public class GyroUnshafter {
    private ModernRoboticsI2cGyro gyro;
    private int offset;

    public GyroUnshafter(ModernRoboticsI2cGyro gyro) {
        this.gyro = gyro;
    }

    public boolean isCalibrating() {
        return gyro.isCalibrating();
    }

    public int heading() {
        return absoluteHeading() - offset;
    }

    public void realCalibrate() {
        offset = 0;
        gyro.calibrate();
    }

    public void fakeCalibrate() {
        offset = absoluteHeading();
    }


    public int absoluteHeading() {
        return gyro.getHeading();
    }

    public int getZThing() {
        return gyro.getIntegratedZValue();
    }
}
