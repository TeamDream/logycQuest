/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dreamteam.logicquest;

/**
 *
 * @author user
 */
public class LevelScene extends Scene {

    Button[] mStickerButtons = new Button[9]; // at first abstraction level. Not sure if it should be some special class
    QuadImage mBackground;

    public LevelScene(GLRenderer aRenderer) {
        float shift_x, shift_y, scale_val;
        scale_val = aRenderer.mScreenSize * aRenderer.mRatio;
        for (int i = 0;
                i < 9; ++i) {

            shift_x = (-0.5f + (i % 3) * 0.5f) * scale_val;
            shift_y = (0.5f - (i / 3) * 0.5f) * scale_val;
            mStickerButtons[i] = new Button(aRenderer);
            mStickerButtons[i].translate(shift_x, shift_y, 0.f);
            mStickerButtons[i].scale(0.16f * scale_val, 0.16f * scale_val, 1.0f);
            mStickerButtons[i].setName(Integer.toString(i));
            mStickerButtons[i].setTexture(aRenderer.mTextureDataHandle1);
        }

        // Define points for equilateral triangles.
        mBackground = new QuadImage(aRenderer);
        mBackground.scale(scale_val, scale_val, 1.0f);
        mBackground.setTexture(aRenderer.mTextureDataHandle);
    }

    @Override
    public void draw(GLRenderer aRenderer) {
        mBackground.draw(aRenderer);
        for (int i = 0; i < 9; i++) {
            mStickerButtons[i].draw(aRenderer);
        }
    }

    public void onResize(GLRenderer aRenderer) {
        mBackground.scale(aRenderer.mRatio * aRenderer.mScreenSize, aRenderer.mScreenSize, 1.f);
    }

    @Override
    public void onTouchDown(float aX, float aY) {
        for (Button stickerButton : mStickerButtons) {
            stickerButton.onTouchDown(this, aX, aY);
        }
    }

    @Override
    public void onTouchMove(float aX, float aY) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onTouchUp(float aX, float aY) {
        //throw new UnsupportedOperationException("Not supported yet.");
        for (Button stickerButton : mStickerButtons) {
            stickerButton.onTouchUp(this, aX, aY);
        }
    }

    @Override
    public void onClick(Button aButton) {
        //throw new UnsupportedOperationException("Not supported yet.");
        if (aButton.mName.equals("0")) {
            GLRenderer.INSTANSE.changeSceneType(GLRenderer.SceneType.QUEST_SCENE);
        }
    }
}
