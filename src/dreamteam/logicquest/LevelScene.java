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
    Button[] aStickerButtons = new Button[9]; //additional stickers for animation
    QuadImage mBackground;
    float curr_time;
    //change scene animation
    boolean streight_animation = false;
    boolean reverse_animation = false;
    //change level list
    boolean list_animation = false;
    int curr_page = 0;
    float prev_x = 0;//previous x coordinate for geture recognition
    float d_x, d_y;
    float d_scale_x, d_scale_y;
    int start_i;

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
    }

    @Override
    public void draw(GLRenderer aRenderer) {

        updateChangingScene(aRenderer);
        updateChangeListAnimation(aRenderer);
        mBackground.draw(aRenderer);
        for (int i = 0; i < 9; i++) {
            mStickerButtons[i].draw(aRenderer);
        }

        if (list_animation) {
            for (int i = 0; i < 9; i++) {
                aStickerButtons[i].draw(aRenderer);
            }
        }
    }

    public void onResize(GLRenderer aRenderer) {
        scale_val_x = aRenderer.mScreenSize * aRenderer.mRatio;
        scale_val_y = aRenderer.mScreenSize * aRenderer.mRatio_y;

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
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onTouchUp(float aX, float aY) {
        //throw new UnsupportedOperationException("Not supported yet.");
        for (Button stickerButton : mStickerButtons) {
            stickerButton.onTouchUp(this, aX, aY);
        }

        if (Math.abs(prev_x - aX) > 0.25f) {
            changeListAnimation(aX < prev_x);
        }
    }

    @Override
    public void onClick(Button aButton) {


        // throw new UnsupportedOperationException("Not supported yet.");
        for (int i = 0; i < 9; i++) {
            if (aButton.mName.equals(Integer.toString(i))) {
                changeSceneAnimation(i, 4);
            }
        }
    }

    public void setDefaultAnimationList() {

        for (int i = 0; i < 9; i++) {
            float start_shift_x = (-0.5f + (i % 3) * 0.5f - 0.1f + 0.1f * (i % 3)) * scale_val_x; // magic placement
            float start_shift_y = (0.5f - (i / 3) * 0.5f - 0.1f + 0.1f * (i / 3)) * scale_val_y;

            mStickerButtons[i].translate(start_shift_x, start_shift_y, 0.0f);
            TextHelper.INSTANCE.setText(mStickerButtons[i].bitmap,
                    GLRenderer.INSTANSE.mTextureLevelDataHandle[i],
                    Integer.toString(i + 9 * curr_page), 128, true);
        }

    }

    public void changeSceneAnimation(int _start_i, int _end_i) {

        if (reverse_animation || streight_animation || list_animation) {
            return;
        }

        curr_time = 0.0f;
        streight_animation = true;
        start_i = _start_i;
        //calculate distance params

        float final_shift_x = (-0.5f + (_end_i % 3) * 0.5f - 0.1f + 0.1f * (_end_i % 3)) * scale_val_x;
        float final_shift_y = (0.5f - (_end_i / 3) * 0.5f - 0.1f + 0.1f * (_end_i / 3)) * scale_val_y;

        float start_shift_x = (-0.5f + (start_i % 3) * 0.5f - 0.1f + 0.1f * (start_i % 3)) * scale_val_x;
        float start_shift_y = (0.5f - (start_i / 3) * 0.5f - 0.1f + 0.1f * (start_i / 3)) * scale_val_y;

        d_x = final_shift_x - start_shift_x;
        d_y = final_shift_y - start_shift_y;

        d_scale_x = 6.0f - 0.24f * scale_val_x;
        d_scale_y = 9.f - 0.16f * scale_val_y;
    }

    public void changeListAnimation(boolean right) {

        if (reverse_animation || streight_animation || list_animation) {
            return;
        }

        if (curr_page <= 0 && !right) { //nothing to animate
            return;
        } else {
            curr_page += right ? 1 : -1; //changing page
        }

        list_animation = true;
        float start_shift_x1 = (-0.5f + (1 % 3) * 0.5f - 0.1f + 0.1f * (1 % 3)) * scale_val_x;
        float start_shift_x2 = (-0.5f + (2 % 3) * 0.5f - 0.1f + 0.1f * (2 % 3)) * scale_val_x;

        float distance = start_shift_x1 - start_shift_x2;//distance between 2 stickers

        curr_time = 0.0f;

        if (right) {
            d_x = start_shift_x1 + 3 * distance;
        } else {
            d_x = start_shift_x1 - 3 * distance;
        }

        for (int i = 0; i < 9; i++) {
            start_shift_x1 = (-0.5f + (i % 3) * 0.5f - 0.1f + 0.1f * (i % 3)) * scale_val_x;
            float final_shift_y = (0.5f - (i / 3) * 0.5f - 0.1f + 0.1f * (i / 3)) * scale_val_y;//default position
            aStickerButtons[i].translate(start_shift_x1 - d_x, final_shift_y, 0.0f); //set starting position
            TextHelper.INSTANCE.setText(aStickerButtons[i].bitmap,
                    GLRenderer.INSTANSE.aTextureLevelDataHandle[i],
                    Integer.toString(i + 9 * curr_page), 128, true);
        }

    }

    public void updateChangeListAnimation(GLRenderer aRenderer) {

        if (list_animation) {

            if (curr_time > 1.0f) {
                curr_time = 0.0f;
                list_animation = false;
                setDefaultAnimationList();
                return;
            }

            curr_time += 0.03f;

            for (int i = 0; i < 9; i++) {
                float start_shift_x = (-0.5f + (i % 3) * 0.5f - 0.1f + 0.1f * (i % 3)) * scale_val_x; // magic placement
                float a_start_shift_x = start_shift_x;
                float start_shift_y = (0.5f - (i / 3) * 0.5f - 0.1f + 0.1f * (i / 3)) * scale_val_y;

                start_shift_x += d_x * curr_time;


                mStickerButtons[i].translate(start_shift_x, start_shift_y, 0.0f);

                a_start_shift_x = a_start_shift_x - d_x;//starting position
                a_start_shift_x += d_x * curr_time;
                aStickerButtons[i].translate(a_start_shift_x, start_shift_y, 0.0f);
            }

        }

    }

    public void updateChangingScene(GLRenderer aRenderer) {

        if (streight_animation) {
            //main sticker moves to central position, others follow him 
            float[] add_tr_x = new float[9];
            float[] add_tr_y = new float[9];
            if (curr_time > 1.0f && !reverse_animation) {
                streight_animation = false;
                curr_time = 0.0f;//reset time stample for next scale animation
                return;
            } else if (reverse_animation && curr_time < 0.0f) {
                streight_animation = false;
                reverse_animation = false;
                return;
            }

            curr_time += reverse_animation ? -0.02f : +0.02f;

            for (int i = 0; i < 9; i++) {
                float start_shift_x = (-0.5f + (i % 3) * 0.5f - 0.1f + 0.1f * (i % 3)) * scale_val_x; // magic placement
                float start_shift_y = (0.5f - (i / 3) * 0.5f - 0.1f + 0.1f * (i / 3)) * scale_val_y;

                start_shift_x += d_x * curr_time;
                start_shift_y += d_y * curr_time;

                add_tr_x[i] = d_x * curr_time;
                add_tr_y[i] = d_y * curr_time;
                mStickerButtons[i].translate(start_shift_x, start_shift_y, 0.0f);
            }

            //scale all stickers, move others around the main
            float start_shift_x;
            float start_shift_y;
            if (curr_time > 1.0f && !reverse_animation) {
                reverse_animation = true;
                GLRenderer.INSTANSE.changeSceneType(GLRenderer.SceneType.QUEST_SCENE);
                GLRenderer.INSTANSE.mQuestScene.updateQuestion(start_i + 9 * curr_page);
            }

            if (reverse_animation && curr_time < 0.0f) {
                streight_animation = false;
                reverse_animation = false;
                return;
            }

            int start_col = start_i % 3;
            int start_row = start_i / 3;

            for (int i = 0; i < 9; i++) {
                float dd_x = 20.0f;
                float dd_y = 20.0f;
                mStickerButtons[i].scale(0.24f * scale_val_x + d_scale_x * curr_time,
                        0.16f * scale_val_y + d_scale_y * curr_time,
                        1.0f);
                if (i == start_i) {
                    continue;
                }

                start_shift_x = (-0.5f + (i % 3) * 0.5f - 0.1f + 0.1f * (i % 3)) * scale_val_x; // magic placement
                start_shift_y = (0.5f - (i / 3) * 0.5f - 0.1f + 0.1f * (i / 3)) * scale_val_y;

                int curr_row = i / 3;
                int curr_col = i % 3;

                if (curr_row < start_row) {
                    if (start_row - curr_row == 2) {
                        dd_y *= 2;
                    }
                    start_shift_y += dd_y * curr_time;
                } else if (curr_row > start_row) {
                    if (curr_row - start_row == 2) {
                        dd_y *= 2;
                    }
                    start_shift_y -= dd_y * curr_time;
                }

                if (curr_col < start_col) {
                    if (start_col - curr_col == 2) {
                        dd_x *= 2;
                    }
                    start_shift_x -= dd_x * curr_time;
                } else if (curr_col > start_col) {
                    if (curr_col - start_col == 2) {
                        dd_x *= curr_col - start_col;
                    }
                    start_shift_x += dd_x * curr_time;
                }

                mStickerButtons[i].translate(start_shift_x + add_tr_x[i], start_shift_y + add_tr_y[i], 0.0f);

            }
        }
    }
}
