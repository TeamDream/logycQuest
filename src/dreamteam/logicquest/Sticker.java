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
import java.util.Vector;

/**
 *
 * @author Дима
 */
public class Sticker {

    private class IndexBuffer {

        public Vector<Integer> buffer = new Vector<Integer>();
    }
    private IndexBuffer[][] mControlPoints;
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
    private int mQuadsInRaw = 0;
    private int mQuadsInCol = 0;
    private float mTime = 10;
    private float mDetalization = 0.f;

    public Sticker(GLRenderer aRenderer, float aDetalization) {
        mRenderer = aRenderer;
        mDetalization = aDetalization;
        //float detalization = 0.5f;
        mQuadsInRaw = (int) (2.f / aDetalization);
        mQuadsInCol = (int) (2.f / aDetalization);
        int numberOfQuads = mQuadsInRaw * mQuadsInCol;
        int dataForVerticeSize = 7;// x,y,z,r,g,b,a
        int dataForQuadSize = 6 * dataForVerticeSize; //2 triangles - 6 vertices
        // This triangle is red, green, and blue.
        mVertecesCount = numberOfQuads * 6;
        final float[] VerticesData = new float[numberOfQuads * dataForQuadSize];

        mControlPoints = new IndexBuffer[mQuadsInRaw + 1][mQuadsInCol + 1];

        for (int i = 0; i < mQuadsInRaw + 1; ++i) {
            for (int j = 0; j < mQuadsInRaw + 1; ++j) {
                mControlPoints[i][j] = new IndexBuffer();
            }
        }
        for (int i = 0; i < mQuadsInRaw; ++i) {
            for (int j = 0; j < mQuadsInCol; ++j) {

                int dataIndex = dataForQuadSize * (i * mQuadsInRaw + j);
                //first triangle
                // X, Y, Z, 
                float leftBottomX = -1.f + aDetalization * i;
                float leftBottomY = -1.f + aDetalization * (mQuadsInCol - j - 1);
                VerticesData[dataIndex] = leftBottomX;
                VerticesData[dataIndex + 1] = leftBottomY;
                VerticesData[dataIndex + 2] = 0.0f;
                mControlPoints[i][j + 1].buffer.add(new Integer(dataIndex));
                // R, G, B, A            
                VerticesData[dataIndex + 3] = 1.0f;
                VerticesData[dataIndex + 4] = 0.0f;
                VerticesData[dataIndex + 5] = 0.0f;
                VerticesData[dataIndex + 6] = 1.0f;

                // X, Y, Z, 
                VerticesData[dataIndex + 7] = leftBottomX + aDetalization;
                VerticesData[dataIndex + 8] = leftBottomY;
                VerticesData[dataIndex + 9] = 0.0f;
                mControlPoints[i + 1][j + 1].buffer.add(new Integer(dataIndex + 7));
                // R, G, B, A            
                VerticesData[dataIndex + 10] = 0.0f;
                VerticesData[dataIndex + 11] = 0.0f;
                VerticesData[dataIndex + 12] = 1.0f;
                VerticesData[dataIndex + 13] = 1.0f;

                // X, Y, Z, 
                VerticesData[dataIndex + 14] = leftBottomX;
                VerticesData[dataIndex + 15] = leftBottomY + aDetalization;
                VerticesData[dataIndex + 16] = 0.0f;
                mControlPoints[i][j].buffer.add(new Integer(dataIndex + 14));

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

                mControlPoints[i + 1][j + 1].buffer.add(new Integer(dataIndex + 21));
                // R, G, B, A            
                VerticesData[dataIndex + 24] = 0.0f;
                VerticesData[dataIndex + 25] = 0.0f;
                VerticesData[dataIndex + 26] = 1.0f;
                VerticesData[dataIndex + 27] = 1.0f;

                // X, Y, Z, 
                VerticesData[dataIndex + 28] = leftBottomX;
                VerticesData[dataIndex + 29] = leftBottomY + aDetalization;
                VerticesData[dataIndex + 30] = 0.0f;
                mControlPoints[i][j].buffer.add(new Integer(dataIndex + 28));
                // R, G, B, A            
                VerticesData[dataIndex + 31] = 0.0f;
                VerticesData[dataIndex + 32] = 1.0f;
                VerticesData[dataIndex + 33] = 0.0f;
                VerticesData[dataIndex + 34] = 1.0f;

                // X, Y, Z, 
                VerticesData[dataIndex + 35] = leftBottomX + aDetalization;
                VerticesData[dataIndex + 36] = leftBottomY + aDetalization;
                VerticesData[dataIndex + 37] = 0.0f;
                mControlPoints[i + 1][j].buffer.add(new Integer(dataIndex + 35));
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

    public void draw(GLRenderer aRenderer) {

                float deltaTime = .0005f;
        update(deltaTime);
        //moveControlPoint(mQuadsInRaw - 1, mQuadsInCol - 1, -0.001f, 0.001f, 0.f);
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
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, mVertecesCount);
    }

    void moveControlPoint(int aIndexX, int aIndexY, float aDeltaX, float aDeltaY, float aDeltaZ) {
        float currentPositionX = mVertices.get(mControlPoints[aIndexX][aIndexY].buffer.get(0));
        float currentPositionY = mVertices.get(mControlPoints[aIndexX][aIndexY].buffer.get(0) + 1);
        float currentPositionZ = mVertices.get(mControlPoints[aIndexX][aIndexY].buffer.get(0) + 2);

        for (Integer index : mControlPoints[aIndexX][aIndexY].buffer) {
            mVertices.put(index, currentPositionX + aDeltaX);
            mVertices.put(index + 1, currentPositionY + aDeltaY);
            mVertices.put(index + 2, currentPositionZ + aDeltaZ);
        }
    }

    public void setControlPointPosition(int aIndexX, int aIndexY, float aX, float aY, float aZ) {

        for (Integer index : mControlPoints[aIndexX][aIndexY].buffer) {
            mVertices.put(index, aX);
            mVertices.put(index + 1, aY);
            mVertices.put(index + 2, aZ);
        }
    }

    public void startAnimation()
    {
        mTime  = 0.f;
    }
    public void update(float aDeltaTime) {
        float t = (0.3f - mTime);
        if(t < 0.12f)
        {
            return;
        }
        float A = -15 *t;
        float h = 3.14f / 1.2f * t;
        mTime += aDeltaTime;
        for (int i = 0; i < mQuadsInRaw + 1; ++i) {
            for (int j = 0; j < mQuadsInCol + 1; ++j) {
                float pX = -1.f + mDetalization * i;
                float pY = -1.f + mDetalization * (mQuadsInCol - j);
                float pZ = 0.0f;
                float R = (float) Math.sqrt((pX + 1) * (pX + 1) + (pY - A) * (pY - A));
                float d = R * (float) Math.sin(h);
                float alpha = (float) Math.asin((pX + 1) / R);
                float beta = alpha / (float) Math.sin(h);
                float x = d * (float) Math.sin(beta);
                float y = R + A - d * (1 - (float) Math.cos(beta) * (float) Math.sin(h));
                float z = d * (1 - (float) Math.cos(beta)) * (float) Math.cos(h);
                setControlPointPosition(i, j, x - 1, y +  1 / GLRenderer.INSTANSE.mRatio, z + 0.5f);
            }
        }
    }
}
