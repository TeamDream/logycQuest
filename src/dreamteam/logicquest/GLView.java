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
import android.view.MotionEvent;

class GLView extends GLSurfaceView {

    public static GLView singleton = null;
    GLRenderer mRenderer;

    public GLView(Context context) {
        super(context);
        singleton = this;
        //setEGLContextFactory(new ContextFactory());
        //setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);           

 
    }

    public void setGLRenderer(GLRenderer aRenderer)
    {
               // Set the renderer to our demo renderer, defined below.
        mRenderer = aRenderer;
        setRenderer(mRenderer);
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

        final float x = e.getX();
        final float y = e.getY();

        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                queueEvent(new Runnable() {
                    public void run() {
                        mRenderer.onTouchDown(x, y);
                    }
                });
                break;
            case MotionEvent.ACTION_MOVE:
                queueEvent(new Runnable() {
                    public void run() {
                        mRenderer.onTouchMove(x, y);
                    }
                });
                break;
            case MotionEvent.ACTION_UP:
                queueEvent(new Runnable() {
                    public void run() {
                        mRenderer.onTouchUp(x, y);
                    }
                });
                break;
        }
        return true;
    }
}
