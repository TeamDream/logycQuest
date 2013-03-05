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
    }
}
