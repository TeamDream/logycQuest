/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package asdlks.sd;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;

/**
 *
 * @author user
 */
public class SmallSticker {

    private int x, y;
    private int width, height, MAX_HEIGHT, MAX_WIDTH;
    FloatBuffer texcoordBuffer;
    FloatBuffer rectBuffer;
    //texture willl be here
    float[] rect = {
        -1f, -1f, 0, // A
        1f, -1f, 0, // B
        1f, 1f, 0, // C
        -1f, 1f, 0  //D?
    };

    SmallSticker() {
        x = y = 0;
        width = height = 100;
        ByteBuffer bb = ByteBuffer.allocateDirect(36);
        bb.order(ByteOrder.nativeOrder());
        texcoordBuffer = bb.asFloatBuffer();

        float [] texcoordBufferArray={0,1,  1,1,  1,0,  0,0,0.5f}; //альфа=1
        texcoordBuffer.position(0);
        texcoordBuffer.put(texcoordBufferArray);
        texcoordBuffer.position(0);
        
        ByteBuffer bbb = ByteBuffer.allocateDirect(48);
        bbb.order(ByteOrder.nativeOrder());
        rectBuffer = bbb.asFloatBuffer();
        rectBuffer.put(rect);
        rectBuffer.position(0);
    }

    void setScreenParams(int _width, int _height) {
        MAX_HEIGHT = _height;
        MAX_WIDTH = _width;
    }

    void init(int _x, int _y) {

        x = _x;
        y = MAX_HEIGHT / 2 - _y;// as temp solution

        width = MAX_WIDTH / 6;
        height = width; 
    }

    void draw(GL10 gl, int texture_id) {
        gl.glViewport(x, y, width, height);

        gl.glEnable(GL10.GL_TEXTURE_2D);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, texture_id);
        gl.glTexEnvx(GL10.GL_TEXTURE_ENV,
                GL10.GL_TEXTURE_ENV_MODE,
                GL10.GL_MODULATE);


        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        // указываем программе, что в буфере vertexBuffer содержатся координаты вершин
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, rectBuffer);
        // включаем использование массивов (буферов) координат текстур
        
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        gl.glTexCoordPointer(2,GL10.GL_FLOAT,0,texcoordBuffer);
       //width++;
       // height++;
        gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, 4);
    }
    //mouse click on viewport( positive coordinate part)

    boolean mouse_on(float _x, float _y) {

        if (_x > x && _x < x + width) {
            if (_y < MAX_HEIGHT - y && _y > MAX_HEIGHT - height - y) {
                return true;
            }
        }
        return false;
    }
}
