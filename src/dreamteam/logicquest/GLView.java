package dreamteam.logicquest;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author dshevchyk
 */
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

class GLView extends GLSurfaceView {

    public static GLView singleton = null;
    public GLRenderer mRenderer;
    public GLView(Context context) {
        super(context);
        singleton = this;

    }

    public void stop() {
        queueEvent(new Runnable() {
            public void run() {
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
    
        public boolean onTouchEvent(final MotionEvent event) {
        queueEvent(new Runnable(){
            public void run() {
                if(GLRenderer.INSTANSE.mMenuScene.mButtons[0].mouseOnClick(event.getX() / getWidth(),
                                event.getY() / getHeight()))
                GLRenderer.INSTANSE.setColorBackground(event.getX() / getWidth(),
                               event.getY() / getHeight(), 1.0f);
                
               // GLRenderer.INSTANSE.
//                for(int i = 0; i < 9; i++) {
//                    if(mRenderer.getSticker(i).mouse_on(event.getX(), event.getY())) {
//                        mRenderer.setColor(event.getX() / getWidth(),
//                                event.getY() / getHeight(), 1.0f);
//                    }
//                }
            }});
            return true;
        }
}
