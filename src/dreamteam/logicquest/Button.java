/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dreamteam.logicquest;

/**
 *
 * @author Дима
 */
public class Button extends QuadImage {

    public String mName;

    public void setName(String aName) {
        mName = aName;
    }

    public Button(GLRenderer aRenderer) {
        super(aRenderer);
    }

    public void onTouchDown(Scene aScene, float aX, float aY) {
        if (mouseOnClick(aX, aY)) {
            scale(5.0f / 2, 2.5f / 2, 1.0f);
        }
    }

    public void onTouchMove(Scene aScene, float aX, float aY) {
    }

    public void onTouchUp(Scene aScene, float aX, float aY) {
        scale(5.0f, 2.5f, 1.0f);
        
        if (mouseOnClick(aX, aY)) {
           aScene.onClick(this);
        }
        
    }

    public boolean mouseOnClick(float _x, float _y) {
        float x = 2 * (_x - 0.5f) * mRenderer.mScreenSize * mRenderer.mRatio;
        float y = 2 * (0.5f - _y) * mRenderer.mScreenSize;
        float l_b_x = (-1.0f) * mScaleParameterX + mTranslateX;
        float l_b_y = (-1.0f)* mScaleParameterY + mTranslateY;

        float r_t_x = ( 1.0f)* mScaleParameterX + mTranslateX;
        float r_t_y = ( 1.0f) * mScaleParameterY + mTranslateY;

        if (x > l_b_x && x < r_t_x) {
            if (y > l_b_y && y < r_t_y) {
                return true;
            }
        }
        return false;
    }
}
