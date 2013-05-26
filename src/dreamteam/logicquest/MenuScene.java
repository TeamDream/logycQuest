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

    public MenuScene(GLRenderer aRenderer) {
        // Define points for equilateral triangles.

        scale_val_x = aRenderer.mScreenSize * aRenderer.mRatio;

        mBackground = new QuadImage(aRenderer);
        mBackground.setTexture(aRenderer.mTextureDataHandle);

        for (int i = 0;
                i < 3; ++i) {
            mButtons[i] = new Button(aRenderer);
            mButtons[i].translate(0.f, 6.f - 6 * i, 0.f);
            mButtons[i].scale(0.16f * scale_val_x, 0.16f * scale_val_x, 1.0f);
            mButtons[i].setTexture(aRenderer.mMenuImage);
        }
        mButtons[0].setName("StartGameButton");
        mButtons[1].setName("some name");
        mButtons[2].setName("some name");
    }

    public void onResize(GLRenderer aRenderer) {
       
        scale_val_x = aRenderer.mScreenSize * aRenderer.mRatio;
       
        for (int i = 0; i < 3; ++i) {
            mButtons[i].translate(0.f, 6.f - 6 * i, 0.f);
            mButtons[i].scale(0.16f * scale_val_x, 0.16f * scale_val_x, 1.0f);
            mButtons[i].setTexture(aRenderer.mMenuImage);
        }
        
        mBackground.scale(aRenderer.mRatio * aRenderer.mScreenSize, aRenderer.mScreenSize, 1.f);
     
    }

    public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
    }

    public void draw(GLRenderer aRenderer) {
        mBackground.draw(aRenderer);
        for (Button button : mButtons) {
            button.draw(aRenderer);
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
            GLRenderer.INSTANSE.changeSceneType(GLRenderer.SceneType.LEVEL_SCENE);
        }
    }
}
