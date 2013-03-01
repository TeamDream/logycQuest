/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package asdlks.sd;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import javax.microedition.khronos.opengles.GL10;

/**
 *
 * @author user
 */
public class MainRenderer implements GLSurfaceView.Renderer {

    SmallSticker stickers[];
    private float mRed;
    private float mGreen;
    private float mBlue;
    private int MAX_HEIGHT, MAX_WIDTH;
    boolean initialized;
    int[] textures = new int[1];
    Context context;
    
    public MainRenderer(Context _context) {
        context = _context;
        
        stickers = new SmallSticker[9];
        for (int i = 0; i < 9; i++) {
            stickers[i] = new SmallSticker();
        }
    }

    public void onSurfaceChanged(GL10 gl, int w, int h) {
        gl.glViewport(0, 0, w, h);
    }

    public void onDrawFrame(GL10 gl) {
        gl.glClearColor(mRed, mGreen, mBlue, 1.0f);
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        gl.glLoadIdentity();
        if (initialized) {
            for (int i = 0; i < 9; i++) {
                stickers[i].draw(gl, textures[0]);
            }
        }
    }

    public void onSurfaceCreated(GL10 gl, javax.microedition.khronos.egl.EGLConfig config) {
        // throw new UnsupportedOperationException("Not supported yet.");
         gl.glPixelStorei(GL10.GL_UNPACK_ALIGNMENT, 1);
        gl.glGenTextures(1, textures, 0);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);

        gl.glTexParameterx(GL10.GL_TEXTURE_2D,
                GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
        gl.glTexParameterx(GL10.GL_TEXTURE_2D, 
                GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
       int idpicture = R.drawable.small_sticker;
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),idpicture);  
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
        bitmap.recycle();
    }

    void setColor(float r, float g, float b) {
        mRed = r;
        mGreen = g;
        mBlue = b;

    }

    void init(int _width, int _height) {
        MAX_HEIGHT = _height;
        MAX_WIDTH = _width;
        int view_x = 0, view_y = 0;
        for (int i = 0; i < 9; i++) {


            view_x = (MAX_WIDTH / 4) * (i % 3) + MAX_WIDTH/8;
            if (i % 3 == 0) {
                view_y = (MAX_WIDTH / 4) * (i / 3) ;
            }
            stickers[i].setScreenParams(MAX_WIDTH, MAX_HEIGHT);
            stickers[i].init(view_x, view_y);

        }
        initialized = true;
    }

    SmallSticker getSticker(int i) {
        return stickers[i];
    }
}
