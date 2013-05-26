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
    Sticker mSticker, aSticker;
    int question_id;
    //animation part. Need to move it into independent module soon
    float prev_x, prev_y;
    float move_time = 0.0f;
    float curr_time = 0.0f;
    float d_x;
    boolean animate_scene;
    //

    public QuestScene(GLRenderer aRenderer) {
        // Define points for equilateral triangles.
        mBackground = new QuadImage(aRenderer);
        mBackground.setTexture(aRenderer.mTextureDataHandle);

        mSticker = new Sticker(aRenderer, 2.f / 30);
        mSticker.scale(6.0f, 9.f, 1.0f);
        mSticker.setName("StartGameButton");
        mSticker.setTexture(aRenderer.mQuestionImage);
        mSticker.createBitmap();

        aSticker = new Sticker(aRenderer, 2.f / 30);
        aSticker.scale(6.0f, 9.f, 1.0f);
        aSticker.setName("StartGameButton");
        aSticker.setTexture(aRenderer.aQuestionImage);
        aSticker.createBitmap();
    }

    public void onResize(GLRenderer aRenderer) {
        mBackground.scale(aRenderer.mRatio * aRenderer.mScreenSize, aRenderer.mScreenSize, 1.f);
    }

    public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
    }

    public void draw(GLRenderer aRenderer) {
        updateAnimation();



        mBackground.draw(aRenderer);
        mSticker.draw(aRenderer);
        if (animate_scene) {
            aSticker.draw(aRenderer);
        }
    }

    public void onTouchDown(float aX, float aY) {
        mSticker.onTouchDown(aX, aY);
        prev_x = aX;
        prev_y = aY;
        move_time = 0.0f;
    }

    public void onTouchMove(float aX, float aY) {
        if(!animate_scene) {
            mSticker.onTouchMove(aX, aY);
        }
        move_time += 0.01;
    }

    public void onTouchUp(float aX, float aY) {
        if (move_time < 0.5f
                && Math.abs(prev_x - aX) > 0.5f
                && Math.abs(prev_y - aY) < 0.5f) {//y settings too 
            startAnimation(aX < prev_x);
             
        }
        else {
            mSticker.onTouchUp(aX, aY);
        }
    }

    public void onClick(Button aButton) {
    }

    public void updateQuestion(int id) {
        question_id = id;
        TextHelper.INSTANCE.setText(mSticker.bitmap, GLRenderer.INSTANSE.mQuestionImage,
                question_id, 16, false);
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
}
