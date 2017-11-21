package org.firstinspires.ftc.teamcode;


import android.graphics.Color;

public class CameraUtil {
    final static int RED_R = 255;
    final static int RED_G = 50;
    final static int RED_B = 50;
    final static int tolerance = 50;

    public static boolean jewelRed(int[] left, int[] right) {
        int sumLeft = 0, sumRight = 0;
        for (int i = 0; i < right.length; i++) {
            if ((Math.abs(RED_R - Color.red(left[i])) < tolerance) && (Math.abs(RED_G - Color.green(left[i])) < tolerance) && (Math.abs(RED_B - Color.blue(left[i])) < tolerance))
                sumLeft++;
        }
        for (int i = 0; i < right.length; i++) {
            if ((Math.abs(RED_R - Color.red(left[i])) < tolerance) && (Math.abs(RED_G - Color.green(left[i])) < tolerance) && (Math.abs(RED_B - Color.blue(left[i])) < tolerance))
                sumRight++;
        } //prefers left
        return sumLeft >= sumRight;
    }
}