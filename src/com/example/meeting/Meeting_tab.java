package com.example.meeting;

import java.io.File;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;

public class Meeting_tab extends Activity {
  LocalActivityManager lam;
  TabHost tabHost;
  File tmpFile;
  Uri imageUri;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.meeting_tab);
    Log.e("onCreate", "onCreate");

    tabHost = (TabHost) findViewById(android.R.id.tabhost);
    lam = new LocalActivityManager(Meeting_tab.this, false);
    lam.dispatchCreate(savedInstanceState);
    tabHost.setup(lam);

    tabHost.addTab(tabHost.newTabSpec("Tab1").setIndicator("")
        .setContent(new Intent(Meeting_tab.this, Meeting_friends_group.class)));
    tabHost.addTab(tabHost.newTabSpec("Tab2").setIndicator("")
        .setContent(new Intent(Meeting_tab.this, Meeting_chat_group.class)));
    tabHost
        .addTab(tabHost
            .newTabSpec("Tab3")
            .setIndicator("")
            .setContent(
                new Intent(Meeting_tab.this, Meeting_settings_group.class)));
    tabHost.setCurrentTab(0);

    for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
      View tab = tabHost.getTabWidget().getChildAt(i);
      tab.getLayoutParams().height = 80;
      if (i == 0) {
        tab.setBackgroundResource(R.drawable.icon_tabbar_03_pressed);
      } else if (i == 1) {
        tab.setBackgroundResource(R.drawable.icon_tabbar_02_normal);
      } else if (i == 2) {
        tab.setBackgroundResource(R.drawable.icon_tabbar_04_normal);

      }
    }

    tabHost.setOnTabChangedListener(new OnTabChangeListener() {

      @Override
      public void onTabChanged(String tabId) {

        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
          View tab = tabHost.getTabWidget().getChildAt(i);

          if (i == 0) {
            if (tabHost.getCurrentTab() == 0) {

              tab.setBackgroundResource(R.drawable.icon_tabbar_03_pressed);
            } else {
              tab.setBackgroundResource(R.drawable.icon_tabbar_03_normal);
            }
          } else if (i == 1) {
            if (tabHost.getCurrentTab() == 1) {
              tab.setBackgroundResource(R.drawable.icon_tabbar_02_pressed);
            } else {
              tab.setBackgroundResource(R.drawable.icon_tabbar_02_normal);
            }
          } else if (i == 2) {
            if (tabHost.getCurrentTab() == 2) {
              tab.setBackgroundResource(R.drawable.icon_tabbar_04_pressed);
            } else {
              tab.setBackgroundResource(R.drawable.icon_tabbar_04_normal);
            }
          }

        }
      }
    });

  }
}
