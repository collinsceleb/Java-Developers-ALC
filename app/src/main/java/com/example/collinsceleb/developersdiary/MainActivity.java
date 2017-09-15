package com.example.collinsceleb.developersdiary;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    CustomAdapter customAdapter;
    ProgressDialog loading;
    ProgressBar load;
    List<Developer> developer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!isDeviceConnected(MainActivity.this)) buildDialog(MainActivity.this).show();

        recyclerView = (RecyclerView) findViewById(R.id.per_developer);
        recyclerView.setHasFixedSize(true);
        load = (ProgressBar) findViewById(R.id.loading);
        loading = new ProgressDialog(MainActivity.this);

        developer = new ArrayList<>();

        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.headerTop);
        setSwipeRefreshLayout();
        addOnScrollListener();
        new myTask().execute();
    }
    // Setting the connectivity of the device
    public boolean isDeviceConnected(Context context){
        ConnectivityManager connect = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo dNetInfo = connect.getActiveNetworkInfo();
        return dNetInfo != null && dNetInfo.isConnected();
    }

    // Building the alert dialog for the connectivity
    public AlertDialog.Builder buildDialog(Context c){
        AlertDialog.Builder builder = new AlertDialog.Builder(c);

        // Title of the connectivity
        builder.setTitle("Internet Connection Error");

        // Message of the connectivity
        builder.setMessage("Check your internet connection, \"OK\" to EXIT");

        // Image to determine its connectivity
        builder.setIcon(R.drawable.no_connection);


        // Set a button to exit the UI after a connection fails
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                System.exit(0);
            }
        });
        return builder;
    }

    private class myTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            super.onPreExecute();
            loading.setMessage("Loading...");
            loading.setCancelable(false);
            loading.show();
        }

        @Override

        // Requesting the URL to populate the data form the API
        protected String doInBackground(String... strings) {
            OkHttpClient client = new OkHttpClient();
            Request.Builder builder = new Request.Builder();
            builder.url("https://api.github.com/search/users?q=+location:lagos+language:java&page=");
            Request request = builder.build();

            try {
                Response response = client.newCall(request).execute();

                JSONObject output = new JSONObject(response.body().string());
                JSONArray items = output.getJSONArray("items");

                for (int i=0; i<items.length(); i++){
                    JSONObject object = items.getJSONObject(i);

                    // JSON response for the login(username) of developers
                    String userId = object.getString("login");

                    // JSON response for the profile picture of developers
                    String devProfilePictureUrl = object.getString("avatar_url");

                    // JSON response for the profile URL of developers
                    String developerUrl = object.getString("html_url");

                    Developer javaDevelopers = new Developer(devProfilePictureUrl, userId, developerUrl);
                    developer.add(javaDevelopers);
                }

            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            }catch (JSONException e){
                e.getMessage();
                return e.toString();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            loading.dismiss();
            recyclerView = (RecyclerView) findViewById(R.id.per_developer);
            //set Adapter
            customAdapter = new CustomAdapter(MainActivity.this, developer);
            recyclerView.setAdapter(customAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

            customAdapter.notifyDataSetChanged();
        }
    }

    public void setSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new myTask().execute();
            }
        });
    }
    public void addOnScrollListener(){
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(!recyclerView.canScrollVertically(1)) {
                    loading.dismiss();
                    new myTask().execute();
                }
            }
        });
    }
}