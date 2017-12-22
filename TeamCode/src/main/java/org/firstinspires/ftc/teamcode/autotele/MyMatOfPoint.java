package org.firstinspires.ftc.teamcode.autotele;

import org.opencv.core.MatOfPoint;


public class MyMatOfPoint {
    public int x, y, id;
    public MyMatOfPoint last;

    public MyMatOfPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
