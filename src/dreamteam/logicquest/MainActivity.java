package dreamteam.logicquest;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ConfigurationInfo;
import android.os.Bundle;
import android.os.IBinder;

public class MainActivity extends Activity {

    public GLView mGLView;
    public static MainActivity singleton = null;

    public MainActivity() {
        singleton = this;
    }

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        


       
        mGLView = new GLView(this);

        // Check if the system supports OpenGL ES 2.0.
        final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        final ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
        final boolean supportsEs2 = configurationInfo.reqGlEsVersion >= 0x20000;

        if (supportsEs2) {
            // Request an OpenGL ES 2.0 compatible context.
            mGLView.setEGLContextClientVersion(2);
            // Set the renderer to our demo renderer, defined below.
            mGLView.setRenderer(GLRenderer.INSTANSE);
            //GLRenderer.INSTANCE.setContext(this);
            setContentView(mGLView);
            //  mGLView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        } else {
            // This is where you could create an OpenGL ES 1.x compatible
            // renderer if you wanted to support both ES 1 and ES 2.
            return;
        }

        setContentView(mGLView);

    }

    @Override
    protected void onResume() {
        // The activity must call the GL surface view's onResume() on activity onResume().
        super.onResume();
       // mGLView.onResume();
    }

    @Override
    protected void onPause() {
        // The activity must call the GL surface view's onPause() on activity onPause().
        super.onPause();
        //doUnbindService();
        //mGLView.onPause();
    }

    @Override
    public void onBackPressed() {
        mGLView.onBackPressed();
    }
    
    private boolean mIsBound = false;
private MusicService mServ;
private ServiceConnection Scon =new ServiceConnection(){

	public void onServiceConnected(ComponentName name, IBinder
     binder) {
	mServ = ((MusicService.ServiceBinder) binder).getService();
	}

	public void onServiceDisconnected(ComponentName name) {
		mServ = null;
	}
	};

	void doBindService(){
 		bindService(new Intent(this,MusicService.class),
				Scon,Context.BIND_AUTO_CREATE);
		mIsBound = true;
	}

	void doUnbindService()
	{
		if(mIsBound)
		{
			unbindService(Scon);
      		mIsBound = false;
		}
	}
}