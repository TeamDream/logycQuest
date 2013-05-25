/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dreamteam.logicquest;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.opengl.GLES20;
import android.opengl.GLUtils;

/**
 *
 * @author user
 */
public enum TextHelper {
    INSTANCE;
    public void setText(Bitmap bitmap, int mTextureDataHandle, String text, 
                        int text_size, boolean center) {
        // Draw the text
        Canvas canvas = new Canvas(bitmap);
        bitmap.eraseColor(0);

        // get a background image from resources
        // note the image format must match the bitmap format
        Drawable background = GLRenderer.INSTANSE.mActivityContext.getResources().getDrawable(R.drawable.st1);
        background.setBounds(0, 0, 256, 256);
        background.draw(canvas); // draw the background to our bitmap

        // Draw the text
        Paint textPaint = new Paint();
        textPaint.setTextSize(text_size);
        textPaint.setAntiAlias(true);
        textPaint.setARGB(0xff, 0x00, 0x00, 0x00);
        // draw the text centered
        int offset_x = center ? 100 : 16;
        int offset_y = center ? 175 : 112;
        canvas.drawText(text, offset_x, offset_y, textPaint);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        // Tell the texture uniform sampler to use this texture in the shader by binding to texture unit 0.
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureDataHandle);
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
    }
}
