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

    protected GLRenderer mRenderer;
    private final FloatBuffer mVertices;
    private final FloatBuffer mTextureCoordinates;
    public float[] mModelMatrix = new float[16];
    protected float mScaleParameterX = 1.0f;
    protected float mScaleParameterY = 1.0f;
    protected float mScaleParameterZ = 1.0f;
    protected float mTranslateX = 0.0f;
    protected float mTranslateY = 0.0f;
    protected float mTranslateZ = 0.0f;
    /**
     * This is a handle to our texture data.
     */
    private int mTextureDataHandle;

    public QuadImage(GLRenderer aRenderer) {
        mRenderer = aRenderer;
        // This triangle is red, green, and blue.
        final float[] VerticesData = {
            // X, Y, Z, 
            // R, G, B, A
            -1.0f, -1.0f, 0.0f,
            1.0f, 1.0f, 1.0f, 1.0f,
            1.0f, -1.0f, 0.0f,
            1.0f, 1.0f, 1.0f, 1.0f,
            -1.0f, 1.0f, 0.0f,
            1.0f, 1.0f, 1.0f, 1.0f,
            1.0f, -1.0f, 0.0f,
            1.0f, 1.0f, 1.0f, 1.0f,
            -1.0f, 1.0f, 0.0f,
            1.0f, 1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 0.0f,
            1.0f, 1.0f, 1.0f, 1.0f};

        final float[] textureCoordinateData = {
            // Front face
            1.0f, 1.0f,
            0.0f, 1.0f,
            1.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 0.0f,
            0.0f, 0.0f};
        // Initialize the buffers.
        mVertices = ByteBuffer.allocateDirect(VerticesData.length * aRenderer.mBytesPerFloat)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();

        mVertices.put(VerticesData).position(0);

        mTextureCoordinates = ByteBuffer.allocateDirect(textureCoordinateData.length * aRenderer.mBytesPerFloat)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        mTextureCoordinates.put(textureCoordinateData).position(0);
    }

    public void setTexture(int TextureID) {

        mTextureDataHandle = TextureID;//TextureHelper.loadTexture(MainActivity.singleton, TextureID);
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

    public void draw(GLRenderer aRenderer) {        // Pass in the position information


        // Set the active texture unit to texture unit 0.
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);

        // Tell the texture uniform sampler to use this texture in the shader by binding to texture unit 0.
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureDataHandle);
        GLES20.glUniform1i(aRenderer.mTextureUniformHandle, 0);
        mVertices.position(aRenderer.mPositionOffset);
        GLES20.glVertexAttribPointer(aRenderer.mPositionHandle, aRenderer.mPositionDataSize, GLES20.GL_FLOAT, false,
                aRenderer.mStrideBytes, mVertices);

        GLES20.glEnableVertexAttribArray(aRenderer.mPositionHandle);

        // Pass in the color information
        mVertices.position(aRenderer.mColorOffset);
        GLES20.glVertexAttribPointer(aRenderer.mColorHandle, aRenderer.mColorDataSize, GLES20.GL_FLOAT, false,
                aRenderer.mStrideBytes, mVertices);

        GLES20.glEnableVertexAttribArray(aRenderer.mColorHandle);
        // Pass in the texture coordinate information
        mTextureCoordinates.position(0);
        GLES20.glVertexAttribPointer(aRenderer.mTextureCoordinateHandle, aRenderer.mTextureCoordinateDataSize, GLES20.GL_FLOAT, false,
                0, mTextureCoordinates);

        GLES20.glEnableVertexAttribArray(aRenderer.mTextureCoordinateHandle);

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
