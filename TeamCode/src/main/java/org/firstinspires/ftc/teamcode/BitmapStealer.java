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

    public static Bitmap STEEL_BITMAP() {
        Bitmap result = null;

        State state = Renderer.getInstance().begin();
        Frame frame = state.getFrame();
        Image imageRGB565 = null;
        for (int i = 0; i < frame.getNumImages(); ++i) {
            Image image = frame.getImage(i);
            if (image.getFormat() == PIXEL_FORMAT.RGB565) {
                imageRGB565 = image;
                break;
            }
        }
        if (imageRGB565 != null) {
            ByteBuffer pixels = imageRGB565.getPixels();
            byte[] pixelArray = new byte[pixels.remaining()];
            pixels.get(pixelArray, 0, pixelArray.length);
            result = Bitmap.createBitmap(imageRGB565.getWidth(), imageRGB565.getHeight(), Bitmap.Config.RGB_565);
            result.copyPixelsFromBuffer(ByteBuffer.wrap(pixelArray));
        }
        return result;
    }
}
