package com.edu.prathm.mybim.Activities;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.edu.prathm.mybim.R;
import com.edu.prathm.mybim.pojo.Comment;
import com.edu.prathm.mybim.pojo.Project;

import java.util.ArrayList;

public class CommentActivity extends AppCompatActivity {
MyCommentAdapter myCommentAdapter;
    ListView listView;
    ArrayList<Comment> comments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commnt);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.list);
        comments = new ArrayList<Comment>();
        Comment c = new Comment();
        c.setId(1);
        c.setComment("kkkkkkkkk");
        c.setDatetime("10 july 2015");
        c.setName("RAju");
comments.add(c);
        comments.add(c);

        myCommentAdapter = new MyCommentAdapter(getBaseContext(),comments);

        listView.setAdapter(myCommentAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_commnt, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class MyCommentAdapter extends BaseAdapter {
        ArrayList<Comment> comments;
        Context context;
        private TextView nameTV;
        private TextView timeTV;

        public MyCommentAdapter(Context context, ArrayList<Comment> comments) {
            this.context = context;
            this.comments = comments;

        }

        @Override
        public int getCount() {
            return comments.size();
        }

        @Override
        public Object getItem(int position) {
            return comments.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.comment_row, null);

            }

            Comment current = comments.get(position);
            nameTV = (TextView) convertView.findViewById(R.id.name);
            timeTV = (TextView) convertView.findViewById(R.id.time);
            TextView commentTV = (TextView) convertView.findViewById(R.id.comment);

            nameTV.setText(current.getName());
            timeTV.setText(current.getDatetime());
            commentTV.setText(current.getComment());

            return convertView;

        }
    }
}
