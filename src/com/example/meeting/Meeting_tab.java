package com.example.meeting;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;

public class Meeting_tab extends Activity {
  LocalActivityManager lam;
  TabHost tabHost;
  File tmpFile;
  Uri imageUri;
  Button btn_addfriend;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.meeting_tab);
    Log.e("onCreate", "onCreate");

    tabHost = (TabHost) findViewById(android.R.id.tabhost);
    lam = new LocalActivityManager(Meeting_tab.this, false);
    lam.dispatchCreate(savedInstanceState);
    tabHost.setup(lam);
    btn_addfriend = (Button) findViewById(R.id.btn_addfriend);

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
    btn_addfriend.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        // TODO Auto-generated method stub
        new php()
            .execute("http://120.119.77.60/~ali/conference-translation-php/service/friends/getFriends.php");
      }
    });
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

  class php extends AsyncTask<String, Void, Void> {

    String url;
    String re;
    ProgressDialog mProgressDialog;

    @Override
    protected void onPreExecute() {
      // TODO Auto-generated method stub
      super.onPreExecute();
      mProgressDialog = new ProgressDialog(Meeting_tab.this);
      mProgressDialog = ProgressDialog.show(Meeting_tab.this, "好友", "正在處理中...",
          true, true);
    }

    protected Void doInBackground(String... params) {
      // TODO Auto-generated method stub
      url = params[0];
      re = Meeting_friends.mf.post(url);
      Log.e("c", url);
      return null;
    }

    @Override
    protected void onPostExecute(Void result) {
      // TODO Auto-generated method stub
      super.onPostExecute(result);
      mProgressDialog.dismiss();

      Log.e("re", re);

      Intent it = new Intent(Meeting_tab.this, Meeting_friends_add.class);
      Bundle bundle = new Bundle();
      bundle.putString("friend", re);
      it.putExtras(bundle);
      startActivity(it);

    }

  }

}
