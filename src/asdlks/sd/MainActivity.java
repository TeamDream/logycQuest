package asdlks.sd;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {
    Storage st;
    /**
     * Called when the activity is first created.
     */
    public void updateView() {
        TextView text = (TextView) findViewById(R.id.text1);
        text.setText(st.getCurrQuection());
        text = (TextView) findViewById(R.id.text2);
        text.setText("");
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        

        setContentView(R.layout.main);
        
        st = new Storage(this);
        TextView text = (TextView) findViewById(R.id.text1);
        text.setText(st.getCurrQuection());
    }

    public void onClick(View view) {

        TextView text = (TextView) findViewById(R.id.text2);
        text.setText(st.getCurrAnswer());
    }
    public void onClickLeft(View view) {
        st.moveLeft();
        updateView();
    }
    
    public void onClickRight(View view) {
        st.moveRight();
        updateView(); 
    }
}
