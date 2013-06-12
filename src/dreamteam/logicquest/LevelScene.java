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

    Button[] mStickerButtons = new Button[9];
    Button[] aStickerButtons = new Button[9]; //additional stickers for animation
    QuadImage mBackground;
    AnimationLevelScene animate;
    float prev_x = 0;//previous x coordinate for geture recognition
  
    public LevelScene(GLRenderer aRenderer) {
        float shift_x, shift_y;
        scale_val_x = aRenderer.mScreenSize * aRenderer.mRatio;
        scale_val_y = aRenderer.mScreenSize * aRenderer.mRatio_y;

        for (int i = 0;
                i < 9; ++i) {

            shift_x = (-0.5f + (i % 3) * 0.5f - 0.1f + 0.1f * (i % 3)) * scale_val_x; // magic placement
            shift_y = (0.5f - (i / 3) * 0.5f - 0.1f + 0.1f * (i / 3)) * scale_val_y;

            mStickerButtons[i] = new Button(aRenderer);
            mStickerButtons[i].translate(shift_x, shift_y, 0.f);
            mStickerButtons[i].scale(0.24f * scale_val_x, 0.16f * scale_val_y, 1.0f);
            mStickerButtons[i].setName(Integer.toString(i));
            mStickerButtons[i].setTexture(aRenderer.mTextureLevelDataHandle[i]);
            mStickerButtons[i].createBitmap();

            aStickerButtons[i] = new Button(aRenderer);
            aStickerButtons[i].translate(shift_x, shift_y, 0.f);
            aStickerButtons[i].scale(0.24f * scale_val_x, 0.16f * scale_val_y, 1.0f);
            aStickerButtons[i].setName(Integer.toString(i));
            aStickerButtons[i].setTexture(aRenderer.aTextureLevelDataHandle[i]);
            aStickerButtons[i].createBitmap();

            TextHelper.INSTANCE.setText(mStickerButtons[i].bitmap, aRenderer.mTextureLevelDataHandle[i], Integer.toString(i), 128, true);
        }

        // Define points for equilateral triangles.
        mBackground = new QuadImage(aRenderer);
        mBackground.scale(scale_val_x, scale_val_y, 1.0f);
        mBackground.setTexture(aRenderer.mTextureDataHandle);
        
        animate = new AnimationLevelScene(mStickerButtons, aStickerButtons);
        animate.setScaleVals(scale_val_x, scale_val_y);
    }

    @Override
    public void draw(GLRenderer aRenderer) {

        animate.updateChangingScene(aRenderer);
        animate.updateChangeListAnimation(aRenderer);
        
        mBackground.draw(aRenderer);
        for (int i = 0; i < 9; i++) {
            mStickerButtons[i].draw(aRenderer);
        }

        if (animate.list_animation) {
            for (int i = 0; i < 9; i++) {
                aStickerButtons[i].draw(aRenderer);
            }
        }
    }

    public void onResize(GLRenderer aRenderer) {
        scale_val_x = aRenderer.mScreenSize * aRenderer.mRatio;
        scale_val_y = aRenderer.mScreenSize * aRenderer.mRatio_y;

        animate.setScaleVals(scale_val_x, scale_val_y);
        float shift_x, shift_y;
        for (int i = 0; i < 9; ++i) {

            shift_x = (-0.5f + (i % 3) * 0.5f - 0.1f + 0.1f * (i % 3)) * scale_val_x; // magic placement

            shift_y = (0.5f - (i / 3) * 0.5f - 0.1f + 0.1f * (i / 3)) * scale_val_y;

            mStickerButtons[i].translate(shift_x, shift_y, 0.f);
            mStickerButtons[i].scale(0.24f * scale_val_x, 0.16f * scale_val_y, 1.0f);

            aStickerButtons[i].translate(shift_x, shift_y, 0.f);
            aStickerButtons[i].scale(0.24f * scale_val_x, 0.16f * scale_val_y, 1.0f);
        }
        mBackground.scale(scale_val_x, aRenderer.mScreenSize, 1.f);
    }

    @Override
    public void onTouchDown(float aX, float aY) {
        prev_x = aX;
        for (Button stickerButton : mStickerButtons) {
            stickerButton.onTouchDown(this, aX, aY);
        }
    }

    @Override
    public void onTouchMove(float aX, float aY) {
    }

    @Override
    public void onTouchUp(float aX, float aY) {
        //throw new UnsupportedOperationException("Not supported yet.");
        for (Button stickerButton : mStickerButtons) {
            stickerButton.onTouchUp(this, aX, aY);
        }

        if (Math.abs(prev_x - aX) > 0.25f) {
            animate.startChangeListAnimation(aX < prev_x);
        }
    }

    @Override
    public void onClick(Button aButton) {
        for (int i = 0; i < 9; i++) {
            if (aButton.mName.equals(Integer.toString(i))) {
                animate.startChangeSceneAnimation(i, 4);
            }
        }
    }
}
