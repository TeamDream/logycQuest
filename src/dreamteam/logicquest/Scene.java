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

    /**
     * How many bytes per float.
     */
    protected final int mBytesPerFloat = 4;
    /**
     * How many elements per vertex.
     */
    protected final int mStrideBytes = 7 * mBytesPerFloat;
    /**
     * Offset of the position data.
     */
    protected final int mPositionOffset = 0;
    /**
     * Size of the position data in elements.
     */
    protected final int mPositionDataSize = 3;
    /**
     * Offset of the color data.
     */
    protected final int mColorOffset = 3;
    /**
     * Size of the color data in elements.
     */
    protected final int mColorDataSize = 4;

    public abstract void draw(GLRenderer aRenderer);

    public abstract void onTouchDown(float aX, float aY);

    public abstract void onTouchMove(float aX, float aY);

    public abstract void onTouchUp(float aX, float aY);
}
