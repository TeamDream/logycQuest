/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package asdlks.sd;

import java.util.ArrayList;
import android.app.Activity; 
/**
 *
 * @author user
 */
public class Storage {
    ArrayList <String> answers;
    ArrayList <String> questions;
    Activity act;
    int currText;
   
    Storage(Activity _act) {
        act = _act;
        currText = 0;     
    }
    
    public String getCurrAnswer() {
        return act.getString(R.string.quest1 + currText);
    }
    public String getCurrQuection() {
        return act.getString(R.string.quest1 + currText);
    }
    public void moveLeft() {
        if (currText > 0)
            currText--;
    }
    public void moveRight() {
        if (currText < R.string.quest9 - R.string.quest1)
            currText++;
    }
    
}
