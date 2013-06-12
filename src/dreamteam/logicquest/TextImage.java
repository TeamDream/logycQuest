/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dreamteam.logicquest;

import android.graphics.Bitmap;
import java.nio.FloatBuffer;

/**
 *
 * @author user
 */
public abstract class TextImage {

    protected GLRenderer mRenderer;
    protected FloatBuffer mVertices;
    protected FloatBuffer mTextureCoordinates;
    public float[] mModelMatrix = new float[16];
    protected float mScaleParameterX = 1.0f;
    protected float mScaleParameterY = 1.0f;
    protected float mScaleParameterZ = 1.0f;
    protected float mTranslateX = 0.0f;
    protected float mTranslateY = 0.0f;
    protected float mTranslateZ = 0.0f;
    protected Bitmap bitmap;
    protected int mTextureDataHandle;

    public void setTexture(int TextureID) {
        mTextureDataHandle = TextureID;
    }

    public void createBitmap() {
        // Create an empty, mutable bitmap
        bitmap = Bitmap.createBitmap(256, 256, Bitmap.Config.ARGB_8888);
    }

    public void scale(float aScaleParameterX, float aScaleParameterY, float aScaleParameterZ) {
        mScaleParameterX = aScaleParameterX;
        mScaleParameterY = aScaleParameterY;
        mScaleParameterZ = aScaleParameterZ;
    }

    public void translate(float aTranslateX, float aTranslateY, float aTranslateZ) {
        mTranslateX = aTranslateX;
        mTranslateY = aTranslateY;
        mTranslateZ = aTranslateZ;
    }

    public abstract void draw(GLRenderer aRenderer);
}
