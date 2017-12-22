package org.firstinspires.ftc.teamcode.autotele;

import org.corningrobotics.enderbots.endercv.OpenCVPipeline;
import org.firstinspires.ftc.robotcontroller.external.samples.ConceptRampMotorSpeed;
import org.firstinspires.ftc.teamcode.OpModeBase;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.opencv.imgproc.Imgproc.fillPoly;

public class AutoTeleVision extends OpenCVPipeline {

    private static final double MIN_CONTOUR_AREA = 1000;//1000 square pixels

    private Mat hsv = new Mat(), thresholded = new Mat();
    private Mat thresholded_rgba = new Mat();
    private OpModeBase opModeBase;
    private ContourList last = null;


    public AutoTeleVision(OpModeBase opModeBase) {
        this.opModeBase = opModeBase;

    }
    @Override
    public Mat processFrame(Mat rgba, Mat gray) {
        // First, we change the colorspace from RGBA to HSV, which is usually better for color
        Imgproc.cvtColor(rgba, hsv, Imgproc.COLOR_RGB2HSV, 3);
        // Then, we threshold our hsv image so that we get a black/white binary image where white
        // is the blues listed in the specified range
        Core.inRange(hsv, new Scalar( 90, 0.5*255, 0.40*255), new Scalar(150, 255, 255), thresholded);

        List<MatOfPoint> rawContours = new ArrayList<MatOfPoint>();
        ContourList current = new ContourList();
        Imgproc.findContours(thresholded, rawContours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);

        for(MatOfPoint contour : rawContours) {
            int x = 0, y = 0;
            Point[] points =  contour.toArray();
            for(Point point : points) {
                x += point.x;
                y += point.y;
            }
            x /= points.length;
            y /= points.length;
            if(Imgproc.contourArea(contour) > MIN_CONTOUR_AREA) {
                current.put(contour, new MyMatOfPoint(x, y));
            }
        }
        opModeBase.telemetry.addData("visible ", "" + current.getVisibleColumnCount());
        current.comapre(last, opModeBase.telemetry);

        Imgproc.cvtColor(thresholded, thresholded_rgba, Imgproc.COLOR_GRAY2RGBA);
        last  = current;
        return thresholded_rgba;
    }
}
