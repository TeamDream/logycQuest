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
    final private int left_border = 25;
    final private int right_border = 220;
    final private int upper_border = 70;
    final private int down_border = 225;

    public void setText(Bitmap bitmap, int mTextureDataHandle, int id,
                        int text_size, boolean center) {
        setText(bitmap,mTextureDataHandle, getTextById(id), text_size, center);
    }
    
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
        if(text_size > 100) {
            textPaint.setShadowLayer(12, 12, 12, 0xFF555555); 
        }
        // draw the text centered
        int offset_x = center ? 100 : left_border;
        int offset_y = center ? 175 : upper_border;
       
        //starting text drawing. Parse strings ending
        int max_symb_count = (right_border - left_border)/ (text_size/2);
        int symb_to_draw = text.length();
        int start,curr, end ;
        start = curr = end = 0;
        int strings_count = 0;
        while(symb_to_draw > 0 ) {   
            if (symb_to_draw > max_symb_count) {
                start = text.length() - symb_to_draw;
                curr = end = start;
                while(curr < text.length()) {
                    
                    if (curr - start > max_symb_count) {
                        break;
                    }

                    if (text.charAt(curr) == ' ') {
                        end = curr;
                    } 
                    curr++;
                }
                canvas.drawText(text.substring(start, end), offset_x, offset_y + strings_count*(text_size), textPaint);
                strings_count++;
                symb_to_draw = text.length() - end;
            }
            else {
                canvas.drawText(text.substring(end), offset_x, offset_y + strings_count*(text_size), textPaint);
                break;
            }       
        }
        //end parsing
         
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        // Tell the texture uniform sampler to use this texture in the shader by binding to texture unit 0.
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureDataHandle);
        
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
    }
    
    public String getTextById(int id) {
      if (id > 11 ) {
          return " other";
      }
        return MainActivity.singleton.getString(R.string.quest1 +id);
    } 
    
}
