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
    QuadImage mBackground;

    public MenuScene() {
        // Define points for equilateral triangles.
        mBackground = new QuadImage(this);
        mBackground.setTexture("some texture");
        for (int i = 0;
                i < 3; ++i) {
            mButtons[i] = new Button(this);
            mButtons[i].translate(0.f, 1.f - i, 0.f);
            mButtons[i].scale(0.5f, 0.25f, 0.5f);
        }
        mButtons[0].setName("StartGameButton");
        mButtons[1].setName("some name");
        mButtons[2].setName("some name");
    }

    public void onResize(int aWidth, int aHeight) {
        mBackground.scale((float) aWidth / aHeight * 1.5f, 1.f * 1.5f, 1.f);
    }

    public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
    }

    public void draw(GLRenderer aRenderer) {
        mBackground.draw(aRenderer, this);
        for (Button button : mButtons) {
            button.draw(aRenderer, this);
        }
    }

    public void onTouchDown(float aX, float aY) {
        for (Button button : mButtons) {
            button.onTouchDown(this, aX, aY);
        }
    }

    public void onTouchMove(float aX, float aY) {

        for (Button button : mButtons) {
            button.onTouchMove(this, aX, aY);
        }
    }

    public void onTouchUp(float aX, float aY) {
        for (Button button : mButtons) {
            button.onTouchUp(this, aX, aY);
        }
    }

    public void onClick(Button aButton) {
        if (aButton.mName.equals("StartGameButton")) {
        }
    }
}
