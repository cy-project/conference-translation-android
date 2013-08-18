package com.example.meeting;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
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
import org.apache.http.util.EncodingUtils;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Meeting_sign_up extends Activity {

  // 手機號碼 mobile_phone
  //
  // 信箱 email
  //
  // 密碼 password
  //
  // 姓名 name

  EditText ed_mobile_phone;
  Button btn_login;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.meeting_sign_up);
    findid();

    String fileName = Environment.getExternalStorageDirectory()
        .getAbsolutePath() + File.separator + "phone.txt";

    // 也可以用String fileName = "mnt/sdcard/Y.txt";

    String res = "";

    try {

      FileInputStream fin = new FileInputStream(fileName);

      // FileInputStream fin = openFileInput(fileName);

      // 用這個就不行了，必須用FileInputStream

      int length = fin.available();

      byte[] buffer = new byte[length];

      fin.read(buffer);

      res = EncodingUtils.getString(buffer, "UTF-8");

      fin.close();

    } catch (Exception e) {

      e.printStackTrace();

    }

    if (!res.equals("")) {
      startActivity(new Intent(Meeting_sign_up.this, Meeting_tab.class));
      finish();
    }

    Log.e("text", res);

    btn_login.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        // TODO Auto-generated method stub
        // http://120.119.77.60/~ali/Conference-Translation/service/register.php
        String input[] = { ed_mobile_phone.getText().toString() };

        php sign_up = (php) new php()
            .execute(
                "http://120.119.77.60/~ali/conference-translation-php/service/register.php",
                json_sign_up(input));

      }
    });

  }

  public void findid() {
    ed_mobile_phone = (EditText) findViewById(R.id.ed_mobile_phone);

    btn_login = (Button) findViewById(R.id.btn_login);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.meeting_main, menu);
    return true;
  }

  public String json_sign_up(String tmp[]) {
    JSONObject jsonObject = new JSONObject();
    try {

      jsonObject.put("mobile_phone", sha1(tmp[0]));
    } catch (JSONException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return jsonObject.toString();
  }

  class php extends AsyncTask<String, Void, Void> {

    String url;
    String contact;
    String re;
    ProgressDialog mProgressDialog;

    @Override
    protected void onPreExecute() {
      // TODO Auto-generated method stub
      super.onPreExecute();
      mProgressDialog = new ProgressDialog(Meeting_sign_up.this);
      mProgressDialog = ProgressDialog.show(Meeting_sign_up.this, "註冊", "正在處理中...", true, true);
    }

    protected Void doInBackground(String... params) {
      // TODO Auto-generated method stub
      url = params[0];
      contact = params[1];
      post();
      Log.e("c", url);
      Log.e("c", contact);
      return null;
    }

    @Override
    protected void onPostExecute(Void result) {
      // TODO Auto-generated method stub
      super.onPostExecute(result);
      mProgressDialog.dismiss();
      
      Log.e("re", re);
      if (re.contains("true")) {
        try {
          FileWriter fw = new FileWriter(Environment
              .getExternalStorageDirectory().getAbsolutePath()
              + File.separator
              + "phone.txt", false);
          BufferedWriter bw = new BufferedWriter(fw); // 將BufferedWeiter與FileWrite物件做連結
          bw.write(ed_mobile_phone.getText().toString());
          bw.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
        startActivity(new Intent(Meeting_sign_up.this, Meeting_tab.class));
        finish();
      } else {
        Toast
            .makeText(Meeting_sign_up.this, "請你重新再輸入一次!!!", Toast.LENGTH_SHORT)
            .show();
      }

     
    }

    public void post() {
      try {

        HttpPost httpRequest = new HttpPost(url);
        /*
         * Post運作傳送變數必須用NameValuePair[]陣列儲存
         */
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("content", contact));
        httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
        /* 取得HTTP response */
        HttpResponse httpResponse = new DefaultHttpClient()
            .execute(httpRequest);
        /* 若狀態碼為200 ok */
        /* 取出回應字串 */

        String strResult = EntityUtils.toString(httpResponse.getEntity());
        // Log.e("abc2",new aes().Decrypt(strResult));

        re = strResult;

      } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

    }
  }

  private String sha1(String s) {
    try {
      MessageDigest digest = java.security.MessageDigest.getInstance("SHA-1");
      digest.update(s.getBytes());
      byte messageDigest[] = digest.digest();

      // Create Hex String
      StringBuffer hexString = new StringBuffer();
      for (int i = 0; i < messageDigest.length; i++)
        hexString.append(String.format("%02X", 0xFF & messageDigest[i]));

      return hexString.toString().toLowerCase();

    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return "";
  }
}
