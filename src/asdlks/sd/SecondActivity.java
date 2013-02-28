/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package asdlks.sd;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;

/**
 *
 * @author user
 */
public class SecondActivity extends Activity{
    
    private MainGLSurfaceView glView;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.second);
        glView = new MainGLSurfaceView(this);
        
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics metricsB = new DisplayMetrics();
        display.getMetrics(metricsB);
        glView.setScreenParam(metricsB.widthPixels, metricsB.heightPixels);
        
        setContentView(glView);

    }
}
