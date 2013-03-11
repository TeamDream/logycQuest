/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dreamteam.logicquest;

/**
 *
 * @author Дима
 */
public abstract class Scene {



    public abstract void draw(GLRenderer aRenderer);

    public abstract void onTouchDown(float aX, float aY);

    public abstract void onTouchMove(float aX, float aY);

    public abstract void onTouchUp(float aX, float aY);
    public abstract void onClick(Button aButton);
    
    public abstract void onResize(GLRenderer aRenderer);
    
}
