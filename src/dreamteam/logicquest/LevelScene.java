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
    float curr_time;
    boolean animate = false;
    float d_x, d_y;
    int start_i;

    public LevelScene(GLRenderer aRenderer) {
        float shift_x, shift_y;
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
        update();
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
        } else if (aButton.mName.equals("1")) {
            startAnimation(1, 4);
        } else if (aButton.mName.equals("1")) {
            startAnimation(1, 4);
        } else if (aButton.mName.equals("2")) {
            startAnimation(2, 4);
        } else if (aButton.mName.equals("3")) {
            startAnimation(3, 4);
        } else if (aButton.mName.equals("5")) {
            startAnimation(5, 4);
        } else if (aButton.mName.equals("6")) {
            startAnimation(6, 4);
        } else if (aButton.mName.equals("7")) {
            startAnimation(7, 4);
        } else if (aButton.mName.equals("8")) {
            startAnimation(8, 4);
        }
    }

    //animation methods:
    public void startAnimation(int _start_i, int _end_i) {
        curr_time = 0.0f;
        animate = true;
        start_i = _start_i;
        //calculate distance params
        float final_shift_x = (-0.5f + (_end_i % 3) * 0.5f) * scale_val;
        float final_shift_y = (0.5f - (_end_i / 3) * 0.5f) * scale_val;

        float start_shift_x = (-0.5f + (start_i % 3) * 0.5f) * scale_val;
        float start_shift_y = (0.5f - (start_i / 3) * 0.5f) * scale_val;

        d_x = final_shift_x - start_shift_x;
        d_y = final_shift_y - start_shift_y;
    }

    public void update() {

        if (!animate || curr_time > 1.0f) {
            return;
        }

        curr_time += 0.01f;

        float start_shift_x = (-0.5f + (start_i % 3) * 0.5f) * scale_val;
        float start_shift_y = (0.5f - (start_i / 3) * 0.5f) * scale_val;

        start_shift_x += d_x * curr_time;
        start_shift_y += d_y * curr_time;
        mStickerButtons[start_i].translate(start_shift_x, start_shift_y, 0.0f);
    }
}
