/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dreamteam.logicquest;

import android.opengl.GLES20;
import android.opengl.Matrix;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 *
 * @author Дима
 */

public class QuadImage {

    private final FloatBuffer mVertices;
    public float[] mModelMatrix = new float[16];
    protected float mScaleParameterX = 1.0f;
    protected float mScaleParameterY = 1.0f;
    protected float mScaleParameterZ = 1.0f;
    protected float mTranslateX = 0.0f;
    protected float mTranslateY = 0.0f;
    protected float mTranslateZ = 0.0f;

    public QuadImage(Scene aScene) {
        // This triangle is red, green, and blue.
        final float[] VerticesData = {
            // X, Y, Z, 
            // R, G, B, A
            -1.0f, -1.0f, 0.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, -1.0f, 0.0f,
            0.0f, 0.0f, 1.0f, 1.0f,
            -1.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f, 1.0f,
            1.0f, -1.0f, 0.0f,
            0.0f, 0.0f, 1.0f, 1.0f,
            -1.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 0.0f,
            1.0f, 0.0f, 0.0f, 1.0f};
        // Initialize the buffers.
        mVertices = ByteBuffer.allocateDirect(VerticesData.length * aScene.mBytesPerFloat)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();

        mVertices.put(VerticesData).position(0);
    }
    public void setTexture(String aTexture)
    {
        
    }
    
    public void scale(float aScaleParameterX,float aScaleParameterY, float aScaleParameterZ) {
        mScaleParameterX = aScaleParameterX;
        mScaleParameterY = aScaleParameterY;
        mScaleParameterZ = aScaleParameterZ;
    }

    public void translate(float aTranslateX, float aTranslateY, float aTranslateZ) {
        mTranslateX = aTranslateX;
        mTranslateY = aTranslateY;
        mTranslateZ = aTranslateZ;
    }

    public void draw(GLRenderer aRenderer, Scene aScene) {        // Pass in the position information
        mVertices.position(aScene.mPositionOffset);
        GLES20.glVertexAttribPointer(aRenderer.mPositionHandle, aScene.mPositionDataSize, GLES20.GL_FLOAT, false,
                aScene.mStrideBytes, mVertices);

        GLES20.glEnableVertexAttribArray(aRenderer.mPositionHandle);

        // Pass in the color information
        mVertices.position(aScene.mColorOffset);
        GLES20.glVertexAttribPointer(aRenderer.mColorHandle, aScene.mColorDataSize, GLES20.GL_FLOAT, false,
                aScene.mStrideBytes, mVertices);

        GLES20.glEnableVertexAttribArray(aRenderer.mColorHandle);

        // This multiplies the view matrix by the model matrix, and stores the result in the MVP matrix
        // (which currently contains model * view).
        Matrix.setIdentityM(mModelMatrix, 0);
        Matrix.translateM(mModelMatrix, 0, mTranslateX, mTranslateY, mTranslateZ);

        Matrix.scaleM(mModelMatrix, 0, mScaleParameterX, mScaleParameterY, mScaleParameterZ);

        Matrix.multiplyMM(mModelMatrix, 0, mModelMatrix, 0, aRenderer.mModelMatrix, 0);
        Matrix.multiplyMM(aRenderer.mMVPMatrix, 0, aRenderer.mViewMatrix, 0, mModelMatrix, 0);

        // This multiplies the modelview matrix by the projection matrix, and stores the result in the MVP matrix
        // (which now contains model * view * projection).
        Matrix.multiplyMM(aRenderer.mMVPMatrix, 0, aRenderer.mProjectionMatrix, 0, aRenderer.mMVPMatrix, 0);

        GLES20.glUniformMatrix4fv(aRenderer.mMVPMatrixHandle, 1, false, aRenderer.mMVPMatrix, 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 6);
    }
}
