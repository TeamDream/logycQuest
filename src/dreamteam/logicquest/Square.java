/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dreamteam.logicquest;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 *
 * @author Дима
 */
public class Square {
    // Our vertices.

    // Our vertex buffer.
    private FloatBuffer mVertexBuffer;
    // Our index buffer.
    private ShortBuffer mIndexBuffer;
    // Our texture buffer.
    private FloatBuffer mTextureCoordinates;
    private float mStep = 0.2f;
    long mCurrentTime = System.currentTimeMillis();
    long mAnimationTime = 1000;//
    int mIndexCount;
    public Square() {

        mIndexCount = 6 * 4;
        float[] vertices = new float[4 * 3 * 4];
        float[] texture = new float[4 * 3 * 4];
        
        short[] indices = new short[mIndexCount];
        for (int i = 0; i < vertices.length; i += 12) {
            // 0, Top Left

            int indexStep = i / 12;
            vertices[i] = -1.0f + indexStep * 2 * mStep;
            vertices[i + 1] = 1.0f;
            vertices[i + 2] = 0.0f;
            
            // 1, Bottom Left     
            vertices[i + 3] = -1.0f + indexStep * 2 * mStep;
            vertices[i + 4] = 1.0f - mStep;
            vertices[i + 5] = 0.0f;

            // 2, Bottom Right
            vertices[i + 6] = -1.0f + mStep + indexStep * 2 * mStep;
            vertices[i + 7] = 1.0f - mStep;
            vertices[i + 8] = 0.0f;

            // 3, Top Right
            vertices[i + 9] = -1.0f + mStep + indexStep * 2 * mStep;
            vertices[i + 10] = 1.0f;
            vertices[i + 11] = 0.0f;
        }

        for (short i = 0; i < indices.length; i += 6) {

            short indexStep = (short) (i / 6 * 4);
            indices[i] = (short) (0 + indexStep);
            indices[i + 1] = (short) (1 + indexStep);
            indices[i + 2] = (short) (2 + indexStep);
            indices[i + 3] = (short) (0 + indexStep);
            indices[i + 4] = (short) (2 + indexStep);
            indices[i + 5] = (short) (3 + indexStep);
        }



        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        mVertexBuffer = vbb.asFloatBuffer();
        mVertexBuffer.put(vertices);
        mVertexBuffer.position(0);

        // short is 2 bytes, therefore we multiply the number if
        // vertices with 2.
        ByteBuffer ibb = ByteBuffer.allocateDirect(indices.length * 2);
        ibb.order(ByteOrder.nativeOrder());
        mIndexBuffer = ibb.asShortBuffer();
        mIndexBuffer.put(indices);
        mIndexBuffer.position(0);

        ByteBuffer tbb = ByteBuffer.allocateDirect(texture.length * 4);
        tbb.order(ByteOrder.nativeOrder());
        mTextureCoordinates = tbb.asFloatBuffer();
        mTextureCoordinates.put(texture);
        mTextureCoordinates.position(0);
    }

    /**
     * This function draws our square on screen.
     *
     * @param gl
     */
    public void draw(GL10 gl) {
        long newTime = System.currentTimeMillis();
        long delta = newTime - mCurrentTime;
        if (delta > mAnimationTime) {
            mVertexBuffer.put(0, mVertexBuffer.get(0) + 0.1f);
            mCurrentTime = newTime;
        }
        // Counter-clockwise winding.
        gl.glFrontFace(GL10.GL_CCW); // OpenGL docs
        // Enable face culling.
        gl.glEnable(GL10.GL_CULL_FACE); // OpenGL docs
        // What faces to remove with the face culling.
        gl.glCullFace(GL10.GL_BACK); // OpenGL docs

        // Enabled the vertices buffer for writing and to be used during
        // rendering.
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);// OpenGL docs.
        // Specifies the location and data format of an array of vertex
        // coordinates to use when rendering.
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, // OpenGL docs
                mVertexBuffer);
        gl.glColor4f(1.f, 0.f, 0.f, 1.f);
        // Replace the current matrix with the identity matrix
        gl.glLoadIdentity(); // OpenGL docs
        gl.glTranslatef(0, 0, -4); // OpenGL docs
        gl.glDrawElements(GL10.GL_TRIANGLES, mIndexCount,// OpenGL docs
                GL10.GL_UNSIGNED_SHORT, mIndexBuffer);

        // Disable the vertices buffer.
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY); // OpenGL docs
        // Disable face culling.
        gl.glDisable(GL10.GL_CULL_FACE); // OpenGL docs
    }
}