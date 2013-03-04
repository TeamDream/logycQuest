/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dreamteam.logicquest;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.os.SystemClock;

public class GLRenderer implements android.opengl.GLSurfaceView.Renderer {

    enum SceneType {

        MENU_SCENE, QUEST_SCENE, ANIMATION_BETWEEN_SCENES
    }
    MenuScene mMenuScene;
    private SceneType mSceneType = SceneType.MENU_SCENE;
    /**
     * Store the model matrix. This matrix is used to move models from object
     * space (where each model can be thought of being located at the center of
     * the universe) to world space.
     */
    public float[] mModelMatrix = new float[16];
    /**
     * Store the view matrix. This can be thought of as our camera. This matrix
     * transforms world space to eye space; it positions things relative to our
     * eye.
     */
    public float[] mViewMatrix = new float[16];
    /**
     * Store the projection matrix. This is used to project the scene onto a 2D
     * viewport.
     */
    public float[] mProjectionMatrix = new float[16];
    /**
     * Allocate storage for the final combined matrix. This will be passed into
     * the shader program.
     */
    public float[] mMVPMatrix = new float[16];

    /**
     * Initialize the model data.
     */
    /**
     * Initialize the model data.
     */
    public GLRenderer() {

        mMenuScene = new MenuScene();
    }

    @Override
    public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
        // Set the background clear color to gray.
        GLES20.glClearColor(0.5f, 0.5f, 0.5f, 0.5f);

        // Position the eye behind the origin.
        final float eyeX = 0.0f;
        final float eyeY = 0.0f;
        final float eyeZ = 1.5f;

        // We are looking toward the distance
        final float lookX = 0.0f;
        final float lookY = 0.0f;
        final float lookZ = -5.0f;

        // Set our up vector. This is where our head would be pointing were we holding the camera.
        final float upX = 0.0f;
        final float upY = 1.0f;
        final float upZ = 0.0f;

        // Set the view matrix. This matrix can be said to represent the camera position.
        // NOTE: In OpenGL 1, a ModelView matrix is used, which is a combination of a model and
        // view matrix. In OpenGL 2, we can keep track of these matrices separately if we choose.
        Matrix.setLookAtM(mViewMatrix, 0, eyeX, eyeY, eyeZ, lookX, lookY, lookZ, upX, upY, upZ);
        mMenuScene.onSurfaceCreated(glUnused, config);

    }

    @Override
    public void onSurfaceChanged(GL10 glUnused, int width, int height) {
        // Set the OpenGL viewport to the same size as the surface.
        GLES20.glViewport(0, 0, width, height);

        // Create a new perspective projection matrix. The height will stay the same
        // while the width will vary as per aspect ratio.
        final float ratio = (float) width / height;
        final float left = -ratio;
        final float right = ratio;
        final float bottom = -1.0f;
        final float top = 1.0f;
        final float near = 1.0f;
        final float far = 10.0f;

        Matrix.frustumM(mProjectionMatrix, 0, left, right, bottom, top, near, far);
    }

    @Override
    public void onDrawFrame(GL10 glUnused) {

        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
        if (mSceneType == SceneType.MENU_SCENE) {
            // Do a complete rotation every 10 seconds.
            long time = SystemClock.uptimeMillis() % 10000L;

            // Draw the triangle facing straight on.
            Matrix.setIdentityM(mModelMatrix, 0);
            mMenuScene.draw(this);
        }
    }
}