/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dreamteam.logicquest;

/**
 *
 * @author user
 */
public class AnimationQuestScene {


    protected float curr_time = 0.0f;
    protected float d_x;
    protected boolean animate_scene;

    protected Sticker mSticker, aSticker;
    protected int question_id;
    
    AnimationQuestScene(Sticker _mSticker, Sticker _aSticker, int _question_id){
        mSticker = _mSticker;
        aSticker = _aSticker;
        question_id = _question_id;
    }
    
    public void setDefaultAnimation() {
        mSticker.translate(0.0f, 0.0f, 0.0f);
        TextHelper.INSTANCE.setText(mSticker.bitmap, GLRenderer.INSTANSE.mQuestionImage,
                question_id, 16, false);
    }

    public void startAnimation(boolean right) {

        if (question_id <= 0 && !right) { //nothing to animate
            return;
        } else {
            question_id += right ? 1 : -1; //changing page
        }

        animate_scene = true;

        float start_shift_x1 = 0.0f;
        float start_shift_x2 = 2.0f * 7.0f;//6.0f???

        float distance = start_shift_x1 - start_shift_x2;//distance between 2 stickers

        curr_time = 0.0f;

        if (right) {
            d_x = distance;
        } else {
            d_x = -distance;
        }

        aSticker.translate(-d_x, 0.0f, 0.0f); //set starting position
        TextHelper.INSTANCE.setText(aSticker.bitmap,
                GLRenderer.INSTANSE.aQuestionImage,
                question_id, 16, false);

    }

    public void updateAnimation() {
        if (animate_scene) {
            if (curr_time > 1.0f) {
                curr_time = 0.0f;
                animate_scene = false;
                setDefaultAnimation();
                return;
            }

            curr_time += 0.04f;

            for (int i = 0; i < 9; i++) {
                float start_shift_x = 0.0f; // magic placement
                // float a_start_shift_x = start_shift_x;


                start_shift_x += d_x * curr_time;


                mSticker.translate(start_shift_x, 0.0f, 0.0f);

                float a_start_shift_x = -d_x + d_x * curr_time;//starting position
                aSticker.translate(a_start_shift_x, 0.0f, 0.0f);
            }
        }
    }
    
    public boolean isAnimateScene() {
        return animate_scene;
    }
}
