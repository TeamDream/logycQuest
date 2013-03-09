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

    public Button(Scene aScene) {
        super(aScene);
    }

    public void onTouchDown(Scene aScene, float aX, float aY) {
        if (mouseOnClick(aX, aY)) {
            scale(0.5f / 2, 0.25f / 2, 0.5f);
        }
    }

    public void onTouchMove(Scene aScene, float aX, float aY) {
    }

    public void onTouchUp(Scene aScene, float aX, float aY) {
        scale(0.5f, 0.25f, 0.5f);
        aScene.onClick(this);
    }

    public boolean mouseOnClick(float _x, float _y) {
        float x = _x * 2 - 1;
        float y = (1 -_y) * 2 - 1;
        float l_b_x = (-1.0f) * mScaleParameterX + mTranslateX;
        float l_b_y = (-1.0f) * mScaleParameterY + mTranslateY;

        float r_t_x = (1.0f) * mScaleParameterX + mTranslateX;
        float r_t_y = (1.0f) * mScaleParameterY + mTranslateY;

        if (x > l_b_x && x < r_t_x) {
            if (y > l_b_y && y < r_t_y) {
                return true;
            }
        }
        return false;
    }
}
