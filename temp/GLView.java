package vgvgf.g;

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

class GLView extends GLSurfaceView {

    public static GLView singleton = null;

    public GLView(Context context) {
        super(context);
        singleton = this;
        //setEGLContextFactory(new ContextFactory());
        setRenderer(new GLRenderer());
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    public void stop() {
        queueEvent(new Runnable() {
            public void run() {
            }
        });
    }
}
