/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dreamteam.logicquest;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import android.content.Context;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.os.SystemClock;

public enum GLRenderer implements android.opengl.GLSurfaceView.Renderer {

    INSTANSE;
    private final Context mActivityContext = MainActivity.singleton;
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
    /**
     * Size of the texture coordinate data in elements.
     */
    protected final int mTextureCoordinateDataSize = 2;

    enum SceneType {

        MENU_SCENE, LEVEL_SCENE, QUEST_SCENE, ANIMATION_BETWEEN_SCENES
    }
    MenuScene mMenuScene;
    LevelScene mLevelScene;
    QuestScene mQuestScene;
    Scene curr_scene;
    private float colors[] = new float[4];//JUST FOR TESTING MOUS EVENT.
    protected float mScreenSize = 10.f;
    protected float mRatio = 1.f;
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
     * This will be used to pass in the transformation matrix.
     */
    protected int mMVPMatrixHandle;
    /**
     * This will be used to pass in model position information.
     */
    protected int mPositionHandle;
    /**
     * This will be used to pass in model color information.
     */
    protected int mColorHandle;
    protected int mProgramHandle;
    /**
     * This will be used to pass in model texture coordinate information.
     */
    protected int mTextureCoordinateHandle;
    /**
     * This will be used to pass in the texture.
     */
    protected int mTextureUniformHandle;

    /**
     * Initialize the model data.
     */
    /**
     * Initialize the model data.
     */
    private GLRenderer() {
    }

    @Override
    public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
        // Set the background clear color to gray.
        GLES20.glClearColor(0.5f, 0.5f, 0.5f, 0.5f);

        // Position the eye behind the origin.
        final float eyeX = 0.0f;
        final float eyeY = 0.0f;
        final float eyeZ = mScreenSize;


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

        final String vertexShader = getVertexShader();


        final String fragmentShader = getFragmentShader();

        final int vertexShaderHandle = ShaderHelper.compileShader(GLES20.GL_VERTEX_SHADER, vertexShader);
        final int fragmentShaderHandle = ShaderHelper.compileShader(GLES20.GL_FRAGMENT_SHADER, fragmentShader);
        mProgramHandle = ShaderHelper.createAndLinkProgram(vertexShaderHandle, fragmentShaderHandle,
                new String[]{"a_Position", "a_Color", "a_TexCoordinate"});


        // Set program handles. These will later be used to pass in values to the program.
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgramHandle, "u_MVPMatrix");
        mPositionHandle = GLES20.glGetAttribLocation(mProgramHandle, "a_Position");
        mColorHandle = GLES20.glGetAttribLocation(mProgramHandle, "a_Color");
        mTextureUniformHandle = GLES20.glGetUniformLocation(mProgramHandle, "u_Texture");
        mTextureCoordinateHandle = GLES20.glGetAttribLocation(mProgramHandle, "a_TexCoordinate");

        // Tell OpenGL to use this program when rendering.
        GLES20.glUseProgram(mProgramHandle);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        GLES20.glEnable(GLES20.GL_BLEND);
        // Load the texture

        mTextureDataHandle = TextureHelper.loadTexture(MainActivity.singleton, R.drawable.images);
        mTextureDataHandle1 = TextureHelper.loadTexture(MainActivity.singleton, R.drawable.ic_launcher);
        mTextureDataHandle2 = TextureHelper.loadTexture(MainActivity.singleton, R.drawable.st1);


        mMenuScene = new MenuScene(this);
        mLevelScene = new LevelScene(this);
        mQuestScene = new QuestScene(this);

        curr_scene = mMenuScene;
    }

    String getVertexShader() {
        return "uniform mat4 u_MVPMatrix;         \n" // A constant representing the combined model/view/projection matrix.

                + "attribute vec4 a_Position;     \n" // Per-vertex position information we will pass in.
                + "attribute vec4 a_Color;        \n" // Per-vertex color information we will pass in.			  
                + "attribute vec2 a_TexCoordinate;\n" // Per-vertex texture coordinate information we will pass in. 				  

                + "varying vec3 v_Position;	  \n" // This will be passed into the fragment shader.
                + "varying vec4 v_Color;          \n" // This will be passed into the fragment shader.
                + "varying vec2 v_TexCoordinate;  \n" // This will be passed into the fragment shader.

                + "void main()                    \n" // The entry point for our vertex shader.
                + "{                              \n"
                + "   v_TexCoordinate = a_TexCoordinate;                              \n"
                + "   v_Color = a_Color;          \n" // Pass the color through to the fragment shader. 
                // It will be interpolated across the triangle.
                + "   gl_Position = u_MVPMatrix   \n" // gl_Position is a special variable used to store the final position.
                + "               * a_Position;   \n" // Multiply the vertex by the matrix to get the final point in 			                                            			 
                + "}                              \n";    // normalized screen coordinates.

    }

    String getFragmentShader() {
        return "precision mediump float;       \n" // Set the default precision to medium. We don't need as high of a 
                // precision in the fragment shader.				
                + "uniform sampler2D u_Texture;          \n" // This is the color from the vertex shader interpolated across the 
                + "varying vec4 v_Color;          \n" // This is the color from the vertex shader interpolated across the 
                + "varying vec2 v_TexCoordinate;          \n" // This is the color from the vertex shader interpolated across the 
                // triangle per fragment.			  
                + "void main()                    \n" // The entry point for our fragment shader.
                + "{                              \n"
                + "  gl_FragColor = (v_Color * texture2D(u_Texture, v_TexCoordinate));      \n" // Pass the color directly through the pipeline.		  
                + "}                              \n";

    }

    public void onTouchDown(float aX, float aY) {
        curr_scene.onTouchDown(aX, aY);
    }

    public void onTouchMove(float aX, float aY) {
        curr_scene.onTouchMove(aX, aY);
    }

    public void onTouchUp(float aX, float aY) {
        curr_scene.onTouchUp(aX, aY);
    }

    @Override
    public void onSurfaceChanged(GL10 glUnused, int width, int height) {
        
        // Set the OpenGL viewport to the same size as the surface.
        GLES20.glViewport(0, 0, width, height);
        // Create a new perspective projection matrix. The height will stay the same
        // while the width will vary as per aspect ratio.
        mRatio = (float) width / height;
        final float left = -mRatio;
        final float right = mRatio;
        final float bottom = -1.0f;
        final float top = 1.0f;
        final float near = 1.0f;
        final float far = 10.0f;

        Matrix.frustumM(mProjectionMatrix, 0, left, right, bottom, top, near, far);

        //curr_scene.onResize(this);

        mMenuScene.onResize(this);
        mLevelScene.onResize(this);
        mQuestScene.onResize(this);
    }
    /**
     * This is a handle to our texture data.
     */
    protected int mTextureDataHandle;
    protected int mTextureDataHandle1;
    protected int mTextureDataHandle2;

    @Override
    public void onDrawFrame(GL10 glUnused) {

        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
        /*       // Set the active texture unit to texture unit 0.
         GLES20.glActiveTexture(GLES20.GL_TEXTURE0);

         // Bind the texture to this unit.
         GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureDataHandle);

         // Tell the texture uniform sampler to use this texture in the shader by binding to texture unit 0.
         GLES20.glUniform1i(mTextureUniformHandle, 0);*/
        // GLES20.glClearColor(mred, mgreen, mblue, 0.5f);
        // Do a complete rotation every 10 seconds.
        long time = SystemClock.uptimeMillis() % 10000L;

        // Draw the triangle facing straight on.
        Matrix.setIdentityM(mModelMatrix, 0);
        curr_scene.draw(this);

    }

    public void setColorBackground(float _mred, float _mgreen, float _mblue) {
        colors[0] = _mred;
        colors[1] = _mgreen;
        colors[2] = _mblue;
        colors[3] = 1.0f;

        ByteBuffer b1 = ByteBuffer.allocateDirect(16);
        b1.order(ByteOrder.nativeOrder());
        FloatBuffer vertexBuffer = b1.asFloatBuffer();
        vertexBuffer.position(0);
        vertexBuffer.put(colors);
        vertexBuffer.position(0);

        //WTF???? Why it is not working?
        GLES20.glEnableVertexAttribArray(mColorHandle);
        GLES20.glVertexAttribPointer(mColorHandle, 4, GLES20.GL_FLOAT, false, 0, vertexBuffer);
        GLES20.glDisableVertexAttribArray(mColorHandle);
        onDrawFrame(null);
    }

    public void changeSceneType(SceneType _type) {
        mSceneType = _type;
        switch (mSceneType) {
            case MENU_SCENE:
                curr_scene = mMenuScene;
                break;
            case LEVEL_SCENE:
                curr_scene = mLevelScene;
                break;
            case QUEST_SCENE:
                curr_scene = mQuestScene;
                break;
        }
    }
}