package com.edu.prathm.mybim.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.edu.prathm.mybim.Fragments.Profile;
import com.edu.prathm.mybim.R;
import com.edu.prathm.mybim.extra.FileOperator;
import com.edu.prathm.mybim.extra.L;
import com.edu.prathm.mybim.network.ConnectionDetector;
import com.edu.prathm.mybim.network.VollySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import static com.edu.prathm.mybim.extra.key.KEY_LOCAL_PIC_PATH;
import static com.edu.prathm.mybim.extra.key.KEY_SUCCESS;
import static com.edu.prathm.mybim.extra.key.KEY_TRUE;
import static com.edu.prathm.mybim.extra.key.KEY_USER_FIRSTNAME;
import static com.edu.prathm.mybim.extra.key.KEY_USER_ID;
import static com.edu.prathm.mybim.extra.key.KEY_USER_LASTNAME;
import static com.edu.prathm.mybim.extra.key.KEY_USER_PROFILE_IMAGE;

public class ProfileActivity extends AppCompatActivity {

    ImageView imageView;
    ImageView edit;
    private Toolbar toolbar;
    private String firstName;
    private String lastName, id;
    private static final int RESULT_LOAD_IMG = 1;
    String encodedString;
    String pic_path;
    String fileName;
    Intent imagedata;
    Uri selectedImage;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        imageView = (ImageView) findViewById(R.id.profile_img);
        edit = (ImageView) findViewById(R.id.edit);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("loading..");


        progressDialog.setCancelable(false);

        SharedPreferences sharedpreferences = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        firstName = sharedpreferences.getString(KEY_USER_FIRSTNAME, null);
        lastName = sharedpreferences.getString(KEY_USER_LASTNAME, null);
        id = sharedpreferences.getString(KEY_USER_ID, null);
        if (FileOperator.getEntryOfSharedPreference(ProfileActivity.this, KEY_LOCAL_PIC_PATH) != null) {


            Bitmap bmImg = BitmapFactory.decodeFile(FileOperator.getEntryOfSharedPreference(ProfileActivity.this, KEY_LOCAL_PIC_PATH));
            if (bmImg != null) {
                imageView.setImageBitmap(bmImg);

            }
        }
        L.t(ProfileActivity.this, id + "");
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create intent to Open Image applications like Gallery, Google Photos
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // Start the Intent
                startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        progressDialog.show();
        if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK && null != data) {
            selectedImage = data.getData();
            imagedata = data;
            compress_image();

        }
    }

    private void compress_image() {

        Thread background = new Thread(new Runnable() {
            @Override
            public void run() {

                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                for (String filepath : filePathColumn) {
                    Log.d("filepath", filepath);
                }
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                pic_path = picturePath;

                cursor.close();

                String fileNameSegments[] = picturePath.split("/");
                fileName = fileNameSegments[fileNameSegments.length - 1];

                Bitmap myImg = BitmapFactory.decodeFile(picturePath);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                // Must compress the Image to reduce image size to make upload easy
                myImg.compress(Bitmap.CompressFormat.JPEG,50, stream);
                byte[] byte_arr = stream.toByteArray();
                // Encode Image to String
                String temp=null;
                try{
                    System.gc();
                    temp = Base64.encodeToString(byte_arr, Base64.DEFAULT);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }catch (OutOfMemoryError outOfMemoryError)
                {
                    stream=new  ByteArrayOutputStream();
                    myImg.compress(Bitmap.CompressFormat.JPEG,25, stream);
                    byte_arr=stream.toByteArray();
                    temp=Base64.encodeToString(byte_arr, Base64.DEFAULT);
                    Log.e("EWN", "Out of memory error catched");
                }
                encodedString=temp;

                ConnectionDetector detector=new ConnectionDetector(ProfileActivity.this);
                if(detector.isConnectingToInternet())
                {
                    uploadImage();
                }

            }
        });

        background.start();
    }

    public void uploadImage() {

        RequestQueue rq = VollySingleton.getInstance().getRequestQueue();
        String url = "http://pupus.web44.net/uploadProfilePic.php";
        Log.d("URL", url);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    Log.e("RESPONSE", response);
                    JSONObject json = new JSONObject(response);
                    if (json.has(KEY_SUCCESS)) {
                        if (json.getString(KEY_SUCCESS).equals(KEY_TRUE)) {

                            imageView.setImageURI(imagedata.getData());
                            FileOperator.addEntryToSharedPreference(ProfileActivity.this, KEY_LOCAL_PIC_PATH, pic_path);
                            progressDialog.dismiss();
                            L.t(ProfileActivity.this, "ImageUploaded");


                        } else {
                            progressDialog.dismiss();
                            L.t(ProfileActivity.this, "Something went wrong");
                        }
                    }
                    if (json.has(KEY_USER_PROFILE_IMAGE)) {
                        String UserImageUrl = json.getString(KEY_USER_PROFILE_IMAGE);
                        Log.d("url", UserImageUrl);
                        FileOperator.addEntryToSharedPreference(ProfileActivity.this, KEY_USER_PROFILE_IMAGE, UserImageUrl);
                        progressDialog.dismiss();

                    }


                } catch (JSONException e) {
                    Log.d("JSON Exception", e.toString());
                    progressDialog.dismiss();
                    imageView.setImageResource(R.drawable.avatar);
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("VolleyERROR", "Error [" + error + "]");
                progressDialog.dismiss();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("image", encodedString);
                params.put("filename", fileName);
                params.put("user_id", id);
                return params;

            }

        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        rq.add(stringRequest);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        toolbar.setTitle(firstName + " " + lastName);
        toolbar.setSubtitle(id);

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
}
