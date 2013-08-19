package com.example.meeting;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Meeting_friends_add extends Activity {

  ListView list_addfriend;
  List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    // TODO Auto-generated method stub
    super.onCreate(savedInstanceState);
    setContentView(R.layout.meeting_friends_add);
    findid();

    Bundle bundle = getIntent().getExtras();
    String name = bundle.getString("friend");

    Log.e("name", name);

  }

  public void findid() {
    list_addfriend = (ListView) findViewById(R.id.list_addfriend);
  }

  public final class MyView {
    public TextView title;
    public ImageView img;
  }

  public class MyAdapter extends BaseAdapter {
    private LayoutInflater inflater;

    public MyAdapter(Context context) {
      inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
      // TODO Auto-generated method stub

      // 回傳這個 List 有幾個 item

      return list.size();

    }

    @Override
    public Object getItem(int position) {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public long getItemId(int position) {
      // TODO Auto-generated method stub
      return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
      // TODO Auto-generated method stub
      MyView myviews = null;
      myviews = new MyView();
      convertView = inflater.inflate(R.layout.meeting_friends_add_list, null);
      myviews.title = (TextView) convertView.findViewById(R.id.addfriends_name);
      myviews.img = (ImageView) convertView.findViewById(R.id.addfriends_photo);

//      myviews.title.setText((String) list.get(position).get("title"));
//      myviews.img.setImageResource(imgArr[position]);

      return convertView;
    }
  }
}
