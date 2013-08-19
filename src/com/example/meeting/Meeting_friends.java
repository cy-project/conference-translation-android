package com.example.meeting;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Meeting_friends extends Activity {

  static Meeting_friends mf;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    // TODO Auto-generated method stub
    super.onCreate(savedInstanceState);
    setContentView(R.layout.meeting_friends);
    mf = Meeting_friends.this;

  }

  public String post(String url) {
    try {

      String tmp = "";
      Cursor contacts_name = getContentResolver().query(
          ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

      while (contacts_name.moveToNext()) {
        String phoneNumber = "";
        long id = contacts_name.getLong(contacts_name
            .getColumnIndex(ContactsContract.Contacts._ID));
        Cursor contacts_number = getContentResolver().query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "="
                + Long.toString(id), null, null);

        while (contacts_number.moveToNext()) {
          phoneNumber = contacts_number.getString(contacts_number
              .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
        }
        contacts_number.close();

        Log.e("contact", phoneNumber);
        if (!phoneNumber.equals("")) {
          tmp += phoneNumber + ",";
        }
      }
      Log.e("a", tmp);
      String contacts[] = tmp.split(",");

      HttpPost httpRequest = new HttpPost(url);
      
      List<NameValuePair> params = new ArrayList<NameValuePair>();
      params.add(new BasicNameValuePair("content", json_friend(contacts)));
      httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
      
      HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);

      String strResult = EntityUtils.toString(httpResponse.getEntity());
      // Log.e("abc2",new aes().Decrypt(strResult));

      return strResult;

    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return "";
  }

  public String json_friend(String tmp[]) {
    JSONObject jsonObject = new JSONObject();
    JSONArray jsonarray = new JSONArray();
    try {

      for (int i = 0; i < tmp.length; i++) {
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("mobile_phone", sha1(tmp[i]));
        jsonarray.put(jsonObject1);

      }
      jsonObject.put("contacts", jsonarray);
    } catch (JSONException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return jsonObject.toString();
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
