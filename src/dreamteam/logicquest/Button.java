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

        scale(0.5f / 2, 0.25f / 2, 0.5f);
    }

    public void onTouchMove(Scene aScene, float aX, float aY) {
    }

    public void onTouchUp(Scene aScene, float aX, float aY) {
        scale(0.5f, 0.25f, 0.5f);
        aScene.onClick(this);
    }

    public boolean mouseOnClick(float _x, float _y) {
        _x = _x * 2 - 1;
        _y = _y * 2 - 1;
        float l_b_x = (-1.0f + mTranslateX) * mScaleParameterX;
        float l_b_y = (-1.0f + mTranslateY) * mScaleParameterY;

        float r_t_x = (1.0f + mTranslateX) * mScaleParameterX;
        float r_t_y = (1.0f + mTranslateY) * mScaleParameterY;

        if (_x > l_b_x && _x < r_t_x) {
            if (_y > l_b_y && _y < r_t_y) {
                return true;
            }
        }
        return false;
    }
}
