package dreamteam.logicquest;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author dshevchyk
 */
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;

class GLView extends GLSurfaceView  {

    public static GLView singleton = null;
    private GestureDetectorCompat mDetector;
  
    public GLView(Context context) {
        super(context);
        singleton = this;      
        mDetector = new GestureDetectorCompat(context, new MyGestureListener());
    }

    public void stop() {
        queueEvent(new Runnable() {
            public void run() {
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.

        final float x = e.getX() / getWidth();
        final float y = e.getY() / getHeight();

        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                queueEvent(new Runnable() {
                    public void run() {
                        GLRenderer.INSTANSE.onTouchDown(x, y);
                    }
                });
                break;
            case MotionEvent.ACTION_MOVE:
                queueEvent(new Runnable() {
                    public void run() {
                        GLRenderer.INSTANSE.onTouchMove(x, y);
                    }
                });
                break;
            case MotionEvent.ACTION_UP:
                queueEvent(new Runnable() {
                    public void run() {
                        GLRenderer.INSTANSE.onTouchUp(x, y);
                    }
                });
                break;
        }
        this.mDetector.onTouchEvent(e);//gesture events detector

        super.onTouchEvent(e);

        
        return true;
    }
     
}


class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        
        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2, 
                float velocityX, float velocityY) {
            return true;
        }
        
        public boolean onDoubleTap(MotionEvent e) {
            GLRenderer.INSTANSE.onDoubleTap();
            return false;
        }
        
 }
