package com.example.meeting;

import java.io.File;
import java.util.ArrayList;

import android.app.ActivityGroup;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

public class Meeting_friends_group extends ActivityGroup{

  
  public static Meeting_friends_group group;

  // Need to keep track of the history if you want the back-button to work properly, don't use this if your activities requires a lot of memory.
  ArrayList<View> history;

  static Uri imageUri;
  static File tmpFile;
  
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    
    this.history = new ArrayList<View>();
      group = this;
      
      if(savedInstanceState != null)
        Log.e("Activit 1 saveInstanceState", "ABC" + savedInstanceState.getInt("ABC"));
      else
        Log.e("Activit 1 saveInstanceState", "is null");
      
      View view = getLocalActivityManager().startActivity("Activity2", new
        Intent(Meeting_friends_group.this, Meeting_friends.class)
        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
            .getDecorView();
      replaceView(view);
  }

  public void replaceView(View v) {
    // animation of activitygroup transition
    
        // Adds the old one to history
    history.add(v);
        // Changes this Groups View to the new View.
    setContentView(v);
    //v.setVisibility(View.GONE);
    
    //v.setVisibility(View.VISIBLE);
    Log.e("replaceView", "" + history.size());
  }
  
  public void back() {
    Log.e("back1", "" + history.size());
    if(history.size() > 1) {
      history.remove(history.size()-1);
      View v = history.get(history.size()-1);
      setContentView(v);
    }else {
      finish();
    }
    Log.e("back2", "" + history.size());
  }
  
  public void replaceContentView(String id, Intent newIntent) {
    View view = getLocalActivityManager().startActivity(id,
        newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
        .getDecorView();
    this.setContentView(view);
  }
  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    switch (keyCode) {
        case KeyEvent.KEYCODE_BACK:
          back();
          break;
    }
    return true;
  }
}
