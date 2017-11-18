package org.firstinspires.ftc.teamcode;

/**
 * Created by almal on 11/18/2017.
 */

 public class CameraUtil {
    final static int RED_R = 255;
    final static int RED_G = 0;
    final static int RED_B = 0;

    public boolean jewelRed(int[] left, int[] right) {
        int tolerance = 50, sumLeft = 0, sumRight = 0;
        for (int i = 0; i < right.length; i++) {
            if ((Math.abs(RED_R - (right[i] >>> 16) & 0xFF) < tolerance) && (Math.abs(RED_G - (right[i] >>> 8) & 0xFF) < tolerance) && (Math.abs(RED_B - (right[i] >>> 0) & 0xFF) < tolerance))
                sumLeft++;
        }
        for (int i = 0; i < right.length; i++) {
            if ((Math.abs(RED_R - (left[i] >>> 16) & 0xFF) < tolerance) && (Math.abs(RED_G - (left[i] >>> 8) & 0xFF) < tolerance) && (Math.abs(RED_B - (left[i] >>> 0) & 0xFF) < tolerance))
                sumRight++;
        } //prefers left
        return sumLeft >= sumRight;
    }
}