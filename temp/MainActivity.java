package vgvgf.g;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

public class MainActivity extends Activity {

    public GLView mGLView;
    public static MainActivity singleton = null;

    public MainActivity() {
        singleton = this;
    }

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGLView = new GLView(this);
        setContentView(mGLView);
    }
}