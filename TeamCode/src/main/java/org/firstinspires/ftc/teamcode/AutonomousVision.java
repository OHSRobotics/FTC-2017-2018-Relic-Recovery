package org.firstinspires.ftc.teamcode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.corningrobotics.enderbots.endercv.OpenCVPipeline;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import static com.sun.tools.doclint.Entity.image;
import static org.opencv.imgproc.Imgproc.THRESH_BINARY;
import static org.opencv.imgproc.Imgproc.fillPoly;
import static org.opencv.imgproc.Imgproc.rectangle;

/**
 * Created by guinea on 10/5/17.
 * A nice demo class for using OpenCVPipeline. This one also demonstrates how to use OpenCV to threshold
 * for a certain color (blue), which is very common in robotics OpenCV applications.
 */

public class AutonomousVision extends OpenCVPipeline {
    // To keep it such that we don't have to instantiate a new Mat every call to processFrame,
    // we declare the Mats up here and reuse them. This is easier on the garbage collector.
    private Mat hsv = new Mat();
    private Mat thresholdedBlue = new Mat();
    private Mat thresholdedRed = new Mat();
    private Mat thresholdedRed1 = new Mat();
    private Mat thresholdedRed2 = new Mat();
    private Mat thresholded_rgba = new Mat();
    private OpModeBase opModeBase;
    private AtomicBoolean done = new AtomicBoolean(false), isRedLeft = new AtomicBoolean(false);

    public AutonomousVision(OpModeBase opModeBase) {
        this.opModeBase = opModeBase;

    }

    // This is called every camera frame.
    @Override
    public Mat processFrame(Mat rgba, Mat gray) {
        // First, we change the colorspace from RGBA to HSV, which is usually better for color
        Imgproc.cvtColor(rgba, hsv, Imgproc.COLOR_RGB2HSV, 3);
        // Then, we threshold our hsv image so that we get a black/white binary image where white
        // is the blues listed in the specified range
        Core.inRange(hsv, new Scalar( 90, 64, 32), new Scalar(150, 255, 255), thresholdedBlue);
        Core.inRange(hsv, new Scalar(150, 64, 32), new Scalar( 180, 255, 255), thresholdedRed1);
        Core.inRange(hsv, new Scalar(0, 64, 32), new Scalar( 30, 255, 255), thresholdedRed2);
        Core.bitwise_xor(thresholdedRed1, thresholdedRed2, thresholdedRed, new Mat());

        {//set the right half to black
            int width =thresholdedRed.width(), height = thresholdedRed.height();
            /*List<MatOfPoint> points = new ArrayList<MatOfPoint>(1);
            points.add(new MatOfPoint(new Point(0, 0), new Point(width / 2, 0), new Point(width / 2, height), new Point(0, height)));
            fillPoly(thresholdedRed, points, new Scalar(0, 0, 0));*/

            List<MatOfPoint> points = new ArrayList<MatOfPoint>();
            double x = width / 1.8;
            points.add(new MatOfPoint(new Point(0, 0), new Point(x, 0), new Point(x, height), new Point(0, height)));
            fillPoly(thresholdedRed, points, new Scalar(0, 0, 255));

        }

        List<MatOfPoint> redContours = new ArrayList<MatOfPoint>();
        List<MatOfPoint> blueContours = new ArrayList<MatOfPoint>();

        Imgproc.findContours(thresholdedBlue, blueContours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
        Imgproc.findContours(thresholdedRed, redContours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
        double redX = 0, blueX = 0;
        if(blueContours.size() > 0) {
            MatOfPoint max = blueContours.get(0);
            for (int i = 1; i < blueContours.size(); i++) {
                if(Imgproc.contourArea(blueContours.get(i)) > Imgproc.contourArea(max)) {
                    max = blueContours.get(i);
                }
            }
            Point[] points =  max.toArray();
            for(Point point : points) {
                blueX += point.x;
            }
            blueX /= points.length;
            opModeBase.telemetry.addData("blue largest: ", Imgproc.contourArea(max));
            opModeBase.telemetry.addData("blue x", " " + blueX);
        }
        if(redContours.size() > 0) {
            MatOfPoint max = redContours.get(0);
            for (int i = 0; i < redContours.size(); i++) {
                if(Imgproc.contourArea(redContours.get(i)) > Imgproc.contourArea(max)) {
                    max = redContours.get(i);
                }
            }
            Point[] points =  max.toArray();
            for(Point point : points) {
                redX += point.x;
            }
            redX /= points.length;
            opModeBase.telemetry.addData("red largest: ", Imgproc.contourArea(max));
            opModeBase.telemetry.addData("red x", " " + redX);
        }
        boolean redLeft = redX < blueX;
        isRedLeft.set(redLeft);
        done.set(true);
        opModeBase.telemetry.addData("result: is left red: ", Boolean.toString(redLeft));

        opModeBase.telemetry.update();

        // Then we display our nice little binary threshold on screen
        // since the thresholded image data is a black and white image, we have to convert it back to rgba
        Imgproc.cvtColor(thresholdedRed, thresholded_rgba, Imgproc.COLOR_GRAY2RGBA);
        return thresholded_rgba;
    }

    public boolean leftRed() {

        while(!done.get() && opModeBase.opModeIsActive()) {
            try {
                Thread.sleep(10);
            } catch(Exception e) {

            }
        }
        return isRedLeft.get();
    }
}
