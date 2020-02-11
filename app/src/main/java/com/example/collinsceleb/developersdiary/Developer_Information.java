package com.example.collinsceleb.developersdiary;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.collinsceleb.developersdiary.R.id.developer_button;
import static com.example.collinsceleb.developersdiary.R.id.developer_image;

public class Developer_Information extends AppCompatActivity implements View.OnClickListener{
    /**
     *  This gives the information of each of the developer
     *  The username of the developer
     *  The developer profile url
     *  The developer profile photo using circle image view
     *  The button to share
     *  THe link to view each profile on browser
     */
    CircleImageView developer_photo;
    TextView username, developer_url;

    Button developer_button_share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer_information);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        developer_photo = (CircleImageView) findViewById(developer_image);
        username = (TextView) findViewById(R.id.username);
        developer_url = (TextView) findViewById(R.id.developer_url);
        developer_button_share= (Button) findViewById(developer_button);

        developer_url.setOnClickListener(this);
        developer_button_share.setOnClickListener(this);

        Intent intent = getIntent();
        String mUsername = intent.getStringExtra("username");
        String mUser_image = intent.getStringExtra("avatar");
        String user_url = intent.getStringExtra("url");

        username.setText(mUsername);
        Picasso.with(this).load(mUser_image).fit().placeholder(R.drawable.avatar).into(developer_photo);


        developer_url.setText(user_url);
        developer_url.setPaintFlags(developer_url.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        String title = getIntent().getStringExtra("username");
        setTitle(title +"'s" + " Profile");
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.developer_url:
                String url = getIntent().getStringExtra("url");
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
                break;

            case R.id.developer_button:
                String share = getIntent().getStringExtra("username");
                String share_url = getIntent().getStringExtra("url");
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT,"Check out this awesome developer \n" + "Username: " + share + "\n" + "GitHub Link: " + share_url);
                startActivity(i);
                break;
        }
    }
}
