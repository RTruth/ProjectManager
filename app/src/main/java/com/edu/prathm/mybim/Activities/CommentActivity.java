package com.edu.prathm.mybim.Activities;

import android.app.ListActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import com.edu.prathm.mybim.R;
import com.edu.prathm.mybim.extra.L;
import com.edu.prathm.mybim.pojo.Comment;
import com.edu.prathm.mybim.pojo.Project;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class CommentActivity extends AppCompatActivity {
MyCommentAdapter myCommentAdapter;
    ListView listView;
    Button addButton;
    HashSet<String> commentSet;
    EditText getCommText;
ExpandableListView expcomments;
    ArrayList<Comment> comments;
    int id=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commnt);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
     commentSet=new HashSet<>();

expcomments= (ExpandableListView) findViewById(R.id.elist);
      // listView = (ListView) findViewById(R.id.list);
        getCommText= (EditText) findViewById(R.id.commentField);
        addButton= (Button) findViewById(R.id.addbtn);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               String commentS= getCommText.getText().toString();
                if(commentS.matches(""))
                {
                    L.t(CommentActivity.this,"Enter comment first");

                }
                else
                {Comment c=new Comment();
                    addComment(c,id,commentS,"date is default","name of person commented");
                    ++id;
                }
            }
        });
        comments = new ArrayList<Comment>();
        SharedPreferences sharedpreferences = this.getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        int count_of_comments = sharedpreferences.getInt("count_of_comments",0);
        for (int i=0;i<=count_of_comments;i++)
        {
          HashSet<String> comm= (HashSet<String>) sharedpreferences.getStringSet("comments"+i,null);
            Iterator iterator=comm.iterator();
           while (iterator.hasNext())
           {

           }
        }

        myCommentAdapter = new MyCommentAdapter(getBaseContext(),comments);

        expcomments.setAdapter(myCommentAdapter);
    }
    private  void addComment(Comment c,int id,String comm,String date,String name)
    {
        c.setId(id);
        c.setComment(comm);
        c.setDatetime(date);
        c.setName(name);

        SharedPreferences sharedpreferences = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt("count_of_comments",addEnterSharedPreference(c));

        comments.add(c);



    }
    int count=0;
    private  int addEnterSharedPreference(Comment c)
    {int id=1;

      commentSet.add(c.getId()+"");
        commentSet.add(c.getComment());
        commentSet.add(c.getDatetime());
        commentSet.add(c.getName());
        commentSet.add(c.getImageUrl());

        SharedPreferences sharedpreferences = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putStringSet("comment"+id,commentSet);

        editor.apply();
        ++count;
        ++id;
return  count;

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

    class MyCommentAdapter extends BaseExpandableListAdapter {
        ArrayList<Comment> comments;
        Context context;
        private TextView nameTV;
        private TextView timeTV;

        public MyCommentAdapter(Context context, ArrayList<Comment> comments) {
            this.context = context;
            this.comments = comments;

        }

        @Override
        public int getGroupCount() {
            return comments.size();
        }

        @Override
        public int getChildrenCount(int i) {
            return 0;
        }

        @Override
        public Object getGroup(int i) {
            return comments;
        }

        @Override
        public Object getChild(int i, int i2) {
            return null;
        }

        @Override
        public long getGroupId(int i) {
            return i;
        }

        @Override
        public long getChildId(int i, int i2) {
            return i2;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
            if(view==null)
            {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
              view = inflater.inflate(R.layout.comment_row, null);
            }
            Comment current = comments.get(i);
            nameTV = (TextView) view.findViewById(R.id.name);
            timeTV = (TextView)view.findViewById(R.id.time);
            TextView commentTV = (TextView)view.findViewById(R.id.comment);

            nameTV.setText(current.getName());
            timeTV.setText(current.getDatetime());
            commentTV.setText(current.getComment());
            return view;

        }

        @Override
        public View getChildView(int i, int i2, boolean b, View view, ViewGroup viewGroup) {
            return null;
        }

        @Override
        public boolean isChildSelectable(int i, int i2) {
            return false;
        }

      /*  @Override
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
        */
    }
}
