package org.firstinspires.ftc.teamcode;

import android.graphics.Bitmap;

import com.vuforia.Frame;
import com.vuforia.Image;
import com.vuforia.PIXEL_FORMAT;
import com.vuforia.Renderer;
import com.vuforia.State;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.internal.vuforia.VuforiaLocalizerImpl;

import java.nio.ByteBuffer;

public class BitmapStealer {

    public static Bitmap STEAL_BITMAP() {
        State state = Renderer.getInstance().begin();
        Frame frame = state.getFrame();
        for (int i = 0; i < frame.getNumImages(); ++i) {
            Image image = frame.getImage(i);
            if (image.getFormat() == PIXEL_FORMAT.RGB565) {
                ByteBuffer pixels = image.getPixels();
                Bitmap bitmap = Bitmap.createBitmap(image.getWidth(), image.getHeight(), Bitmap.Config.RGB_565);
                bitmap.copyPixelsFromBuffer(pixels);
                return bitmap;
            }
        }
        throw new RuntimeException("Unable to find image with the proper format!");
    }
}
