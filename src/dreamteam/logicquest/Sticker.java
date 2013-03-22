/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dreamteam.logicquest;

import android.graphics.Canvas;
import android.opengl.GLES20;
import android.opengl.Matrix;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 *
 * @author Дима
 */
public class Sticker {

    public String mName;
    protected GLRenderer mRenderer;
    private final FloatBuffer mVertices;
    public float[] mModelMatrix = new float[16];
    protected float mScaleParameterX = 1.0f;
    protected float mScaleParameterY = 1.0f;
    protected float mScaleParameterZ = 1.0f;
    protected float mTranslateX = 0.0f;
    protected float mTranslateY = 0.0f;
    protected float mTranslateZ = 0.0f;
    private int mVertecesCount = 0;
    
    public Sticker(GLRenderer aRenderer , float aDetalization) {
        mRenderer = aRenderer;

        //float detalization = 0.5f;
        int quadsInRaw = (int) (2.f / aDetalization);
        int quadsInCol = (int) (2.f / aDetalization);
        int numberOfQuads = quadsInRaw * quadsInCol;
        int dataForVerticeSize = 7;// x,y,z,r,g,b,a
        int dataForQuadSize = 6 * dataForVerticeSize; //2 triangles - 6 vertices
        // This triangle is red, green, and blue.
        mVertecesCount = numberOfQuads * 6;
        final float[] VerticesData = new float[numberOfQuads * dataForQuadSize];
        for (int i = 0; i < quadsInRaw; ++i) {
            for (int j = 0; j < quadsInCol; ++j) {

                int dataIndex = dataForQuadSize * (i * quadsInRaw + j);
                //first triangle
                // X, Y, Z, 
                float leftBottomX = -1.f + aDetalization * i;
                float leftBottomY = -1.f + aDetalization * j;
                VerticesData[dataIndex] = leftBottomX;
                VerticesData[dataIndex + 1] = leftBottomY;
                VerticesData[dataIndex + 2] = 0.0f;

                // R, G, B, A            
                VerticesData[dataIndex + 3] = 1.0f;
                VerticesData[dataIndex + 4] = 0.0f;
                VerticesData[dataIndex + 5] = 0.0f;
                VerticesData[dataIndex + 6] = 1.0f;

                // X, Y, Z, 
                VerticesData[dataIndex + 7] = leftBottomX + aDetalization;
                VerticesData[dataIndex + 8] = leftBottomY;
                VerticesData[dataIndex + 9] = 0.0f;

                // R, G, B, A            
                VerticesData[dataIndex + 10] = 0.0f;
                VerticesData[dataIndex + 11] = 0.0f;
                VerticesData[dataIndex + 12] = 1.0f;
                VerticesData[dataIndex + 13] = 1.0f;

                // X, Y, Z, 
                VerticesData[dataIndex + 14] = leftBottomX;
                VerticesData[dataIndex + 15] = leftBottomY + aDetalization;
                VerticesData[dataIndex + 16] = 0.0f;

                // R, G, B, A            
                VerticesData[dataIndex + 17] = 0.0f;
                VerticesData[dataIndex + 18] = 1.0f;
                VerticesData[dataIndex + 19] = 0.0f;
                VerticesData[dataIndex + 20] = 1.0f;

                //second triangle  

                // X, Y, Z, 
                VerticesData[dataIndex + 21] = leftBottomX + aDetalization;
                VerticesData[dataIndex + 22] = leftBottomY;
                VerticesData[dataIndex + 23] = 0.0f;

                // R, G, B, A            
                VerticesData[dataIndex + 24] = 0.0f;
                VerticesData[dataIndex + 25] = 0.0f;
                VerticesData[dataIndex + 26] = 1.0f;
                VerticesData[dataIndex + 27] = 1.0f;

                // X, Y, Z, 
                VerticesData[dataIndex + 28] = leftBottomX;
                VerticesData[dataIndex + 29] = leftBottomY + aDetalization;
                VerticesData[dataIndex + 30] = 0.0f;

                // R, G, B, A            
                VerticesData[dataIndex + 31] = 0.0f;
                VerticesData[dataIndex + 32] = 1.0f;
                VerticesData[dataIndex + 33] = 0.0f;
                VerticesData[dataIndex + 34] = 1.0f;

                // X, Y, Z, 
                VerticesData[dataIndex + 35] = leftBottomX + aDetalization;
                VerticesData[dataIndex + 36] = leftBottomY + aDetalization;
                VerticesData[dataIndex + 37] = 0.0f;

                // R, G, B, A            
                VerticesData[dataIndex + 38] = 1.0f;
                VerticesData[dataIndex + 39] = 0.0f;
                VerticesData[dataIndex + 40] = 0.0f;
                VerticesData[dataIndex + 41] = 1.0f;
            }
        }
        // Initialize the buffers.
        mVertices = ByteBuffer.allocateDirect(VerticesData.length * aRenderer.mBytesPerFloat)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();

        mVertices.put(VerticesData).position(0);
    }

    public void setName(String aName) {
        mName = aName;
    }

    public void setTexture(String aTexture) {
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
        mVertices.position(aRenderer.mPositionOffset);
        GLES20.glVertexAttribPointer(aRenderer.mPositionHandle, aRenderer.mPositionDataSize, GLES20.GL_FLOAT, false,
                aRenderer.mStrideBytes, mVertices);

        GLES20.glEnableVertexAttribArray(aRenderer.mPositionHandle);

        // Pass in the color information
        mVertices.position(aRenderer.mColorOffset);
        GLES20.glVertexAttribPointer(aRenderer.mColorHandle, aRenderer.mColorDataSize, GLES20.GL_FLOAT, false,
                aRenderer.mStrideBytes, mVertices);

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
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0,mVertecesCount);
    }
}
