/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dreamteam.logicquest;

import android.opengl.GLES20;
import android.opengl.Matrix;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 *
 * @author Дима
 */
public class MenuScene extends Scene {

    /**
     * Store our model data in a float buffer.
     */
    Button[] mButtons = new Button[3];

    public MenuScene() {
        // Define points for equilateral triangles.

        for (int i = 0;
                i < 3; ++i) {
            mButtons[i] = new Button(this);
            mButtons[i].translate(0.f, 1.f - i, 0.f);
            mButtons[i].scale(0.5f, 0.25f, 0.5f);
        }
    }

    public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
    }

    public void draw(GLRenderer aRenderer) {
        for (Button button : mButtons) {
            button.draw(aRenderer, this);
        }
    }
}
