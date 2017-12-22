package org.firstinspires.ftc.teamcode.autotele;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.MatOfPoint;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ContourList extends HashMap<MatOfPoint, MyMatOfPoint> {

    private static final int SAME_ROW_TOLERANCE = 50;//Pixels

    public ContourList() {

    }

    public void comapre(ContourList last, Telemetry telemetry) {
        if(last == null || (last.isEmpty() && !this.isEmpty())) {
            int count = 0;
            for(Entry<MatOfPoint, MyMatOfPoint> contour : this.entrySet()) {
                contour.getValue().id = count++;
            }
        } else {
            telemetry.addData("starting non id setting loop" ,"");
            for(Entry<MatOfPoint, MyMatOfPoint> contour : this.entrySet()) {
                MyMatOfPoint nearest = null;
                for(Entry<MatOfPoint, MyMatOfPoint> other : this.entrySet()) {
                    if(nearest == null || distance(contour.getValue().x, contour.getValue().y, other.getValue().x, other.getValue().y) < distance(contour.getValue().x, contour.getValue().y, nearest.x, nearest.y)) {
                        nearest = other.getValue();
                    }
                }
                telemetry.addData("Last time [X: " + nearest.x + ", Y: " + nearest.x + "] current [X: " + contour.getValue().x +", Y: " + contour.getValue().y + "] size " + Imgproc.contourArea(contour.getKey()) + " id " + contour.getValue().id, "");
            }
        }
        telemetry.update();
    }

    private static double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    public int getVisibleColumnCount() {
        if(this.isEmpty()) return 0;
        int count = 0;
        MyMatOfPoint minY = (MyMatOfPoint) this.entrySet().toArray()[0];
        for(Entry<MatOfPoint, MyMatOfPoint> point : this.entrySet()) {
            if(point.getValue().y < minY.y)
                minY = point.getValue();
        }
        for(Entry<MatOfPoint, MyMatOfPoint> point : this.entrySet()) {
            if(Math.abs(point.getValue().y - minY.y) < SAME_ROW_TOLERANCE) {
                count++;
            }
        }
        return count;
    }

}
