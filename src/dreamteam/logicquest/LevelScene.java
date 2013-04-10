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
    boolean animate_translation = false;
    boolean animate_scale = false;
    boolean reverse_animation = false;
    float d_x, d_y;
    float d_scale_x, d_scale_y;
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
            // mStickerButtons[i].scale(scale_val, scale_val, 1.0f);
            mStickerButtons[i].scale(0.16f * scale_val, 0.16f * scale_val, 1.0f);
            mStickerButtons[i].setName(Integer.toString(i));
            mStickerButtons[i].setTexture(aRenderer.mTextureDataHandle2);
        }

        // Define points for equilateral triangles.
        mBackground = new QuadImage(aRenderer);
        mBackground.scale(scale_val, scale_val, 1.0f);
        mBackground.setTexture(aRenderer.mTextureDataHandle);
    }

    @Override
    public void draw(GLRenderer aRenderer) {
        update(aRenderer);
        mBackground.draw(aRenderer);
        for (int i = 0; i < 9; i++) {
            mStickerButtons[i].draw(aRenderer);
        }
    }

    public void onResize(GLRenderer aRenderer) {
        scale_val = aRenderer.mScreenSize * aRenderer.mRatio;
        float shift_x, shift_y;
        for (int i = 0; i < 9; ++i) {
            shift_x = (-0.5f + (i % 3) * 0.5f) * scale_val;
            shift_y = (0.5f - (i / 3) * 0.5f) * scale_val;
            mStickerButtons[i].translate(shift_x, shift_y, 0.f);
            mStickerButtons[i].scale(0.16f * scale_val, 0.16f * scale_val, 1.0f);
        }
        mBackground.scale(scale_val, aRenderer.mScreenSize, 1.f);
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
        for(int i = 0; i < 9; i++) {
            if (aButton.mName.equals(Integer.toString(i))) {
                startAnimation(i, 4);
            }
        }
    }

    //animation methods:
    public void reverseAnimation() {
        curr_time = 0.0f;
        animate_translation = true;
        reverse_animation = true;
    }
    public void startAnimation(int _start_i, int _end_i) {
        curr_time = 0.0f;
        animate_translation = true;
        //animate_scale = false;
        start_i = _start_i;
        //calculate distance params
        float final_shift_x = (-0.5f + (_end_i % 3) * 0.5f) * scale_val;
        float final_shift_y = (0.5f - (_end_i / 3) * 0.5f) * scale_val;

        float start_shift_x = (-0.5f + (start_i % 3) * 0.5f) * scale_val;
        float start_shift_y = (0.5f - (start_i / 3) * 0.5f) * scale_val;

        d_x = final_shift_x - start_shift_x;
        d_y = final_shift_y - start_shift_y;

        d_scale_x = 6.0f - 0.16f * scale_val;
        d_scale_y = 9.f - 0.16f * scale_val;
    }

    public void update(GLRenderer aRenderer) {

        if (animate_translation) {
            //main sticker moves to central position, others follow him 
            float[] add_tr_x = new float[9];
            float[] add_tr_y = new float[9];
            if (curr_time > 1.0f && !reverse_animation) {
                animate_translation = false;
                animate_scale = true;
                curr_time = 0.0f;//reset time stample for next scale animation
                return;
            }
            else if (reverse_animation && curr_time < 0.0f) {
                animate_translation = false;
                reverse_animation = false;
                return;
            }
            
            curr_time += reverse_animation? -0.02f: +0.02f;

            for (int i = 0; i < 9; i++) {
                float start_shift_x = (-0.5f + (i % 3) * 0.5f) * scale_val;
                float start_shift_y = (0.5f - (i / 3) * 0.5f) * scale_val;

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
                animate_scale = false;
                if(start_i == 0) {
                    reverse_animation = true;
                } else {
                    GLRenderer.INSTANSE.changeSceneType(GLRenderer.SceneType.QUEST_SCENE);
                }
            }
            
            if (reverse_animation && curr_time < 0.0f) {
                animate_translation = false;
                reverse_animation = false;
                return;
            }

            int start_col = start_i % 3;
            int start_row = start_i / 3;
           // float dd_x = 20.0f;
           // float dd_y = 20.0f;
            for (int i = 0; i < 9; i++) {
            float dd_x = 20.0f;
            float dd_y = 20.0f;
                mStickerButtons[i].scale(0.16f * scale_val + d_scale_x * curr_time,
                        0.16f * scale_val + d_scale_y * curr_time,
                        1.0f);
//                mBackground.scale(scale_val + d_scale_x * curr_time,
//                        aRenderer.mScreenSize + d_scale_x * curr_time,
//                        1.0f);
                if (i == start_i) {
                    continue;
                }
                
                start_shift_x = (-0.5f + (i % 3) * 0.5f) * scale_val;
                start_shift_y = (0.5f - (i / 3) * 0.5f) * scale_val;
                int curr_row = i / 3;
                int curr_col = i % 3;
                
                if (curr_row < start_row) {
                    if(start_row - curr_row == 2) {
                        dd_y *=2;
                    }
                    start_shift_y += dd_y * curr_time;
                } else if (curr_row > start_row) {
                        if(curr_row - start_row  == 2) {
                        dd_y *=2;
                    }
                    start_shift_y -= dd_y * curr_time;
                }
 
                if (curr_col < start_col) {
                    if(start_col - curr_col == 2) {
                        dd_x *=2;
                    }
                    start_shift_x -= dd_x * curr_time;
                } else if (curr_col > start_col) {
                    if(curr_col - start_col == 2) {
                        dd_x *= curr_col - start_col;
                    }
                    start_shift_x += dd_x * curr_time;
                }

                mStickerButtons[i].translate(start_shift_x + add_tr_x[i], start_shift_y + add_tr_y[i], 0.0f);

            }
        }
    }
}
