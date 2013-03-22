/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dreamteam.logicquest;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 *
 * @author Дима
 */
public class QuestScene extends Scene {

    QuadImage mBackground;
    Sticker mSticker;

    public QuestScene(GLRenderer aRenderer) {
        // Define points for equilateral triangles.
        mBackground = new QuadImage(aRenderer);
        mBackground.setTexture("some texture");

        mSticker = new Sticker(aRenderer, 2.f / 30);
        mSticker.scale(5.0f, 5.f, 1.0f);
        mSticker.setName("StartGameButton");
    }

    public void onResize(GLRenderer aRenderer) {
        mBackground.scale(aRenderer.mRatio * aRenderer.mScreenSize, aRenderer.mScreenSize, 1.f);
    }

    public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
    }

    public void draw(GLRenderer aRenderer) {
        mBackground.draw(aRenderer);
        mSticker.draw(aRenderer);
    }

    public void onTouchDown(float aX, float aY) {
        mSticker.startAnimation();
    }

    public void onTouchMove(float aX, float aY) {
    }

    public void onTouchUp(float aX, float aY) {
    }

    public void onClick(Button aButton) {
    }
}
