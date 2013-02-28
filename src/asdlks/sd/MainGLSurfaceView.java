/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package asdlks.sd;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

/**
 *
 * @author user
 */
public class MainGLSurfaceView extends GLSurfaceView{
    
    public MainGLSurfaceView(Context context) {
        super(context);
        mRenderer = new MainRenderer(context);
        setRenderer(mRenderer);
       
    }
    public void setScreenParam(int width, int height) {
        mRenderer.init(width, height);
    }
    public boolean onTouchEvent(final MotionEvent event) {
        queueEvent(new Runnable(){
            public void run() {
                for(int i = 0; i < 9; i++) {
                    if(mRenderer.getSticker(i).mouse_on(event.getX(), event.getY())) {
                        mRenderer.setColor(event.getX() / getWidth(),
                                event.getY() / getHeight(), 1.0f);
                    }
                }
            }});
            return true;
        }

        MainRenderer mRenderer;
        
}

