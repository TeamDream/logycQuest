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
    AnimationQuestScene animate;
    
    float prev_x, prev_y;
    float move_time = 0.0f;
    
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
        
        animate = new AnimationQuestScene(mSticker, aSticker, question_id);
    }

    public void onResize(GLRenderer aRenderer) {
        mBackground.scale(aRenderer.mRatio * aRenderer.mScreenSize, aRenderer.mScreenSize, 1.f);
    }

    public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
    }

    public void draw(GLRenderer aRenderer) {
        animate.updateAnimation();
        
        mBackground.draw(aRenderer);
        mSticker.draw(aRenderer);
        
        if (animate.isAnimateScene()) {
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
        if(!animate.isAnimateScene()) {
            mSticker.onTouchMove(aX, aY);
        }
        move_time += 0.01;
    }

    public void onTouchUp(float aX, float aY) {
        if (move_time < 0.5f
                && Math.abs(prev_x - aX) > 0.5f
                && Math.abs(prev_y - aY) < 0.5f) {//y settings too 
            animate.startAnimation(aX < prev_x);
             
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
}
