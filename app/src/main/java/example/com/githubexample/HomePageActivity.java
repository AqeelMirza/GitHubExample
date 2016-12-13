package example.com.githubexample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Aqeel.mirza on 12/13/2016.
 */

public class HomePageActivity extends AppCompatActivity {
    JSONObject logindata;
    ImageView userimage;
    TextView user_name, user_login, user_company, user_blog, user_email, user_location, user_followers, user_following, toolbar_text;
    Button repos_btn, feeds_btn;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userimage = (ImageView) findViewById(R.id.user_image);
        user_name = (TextView) findViewById(R.id.user_name);
        user_login = (TextView) findViewById(R.id.user_login);
        user_company = (TextView) findViewById(R.id.user_company);
        user_blog = (TextView) findViewById(R.id.user_blog);
        user_email = (TextView) findViewById(R.id.user_email);
        user_location = (TextView) findViewById(R.id.user_location);
        user_followers = (TextView) findViewById(R.id.user_followers);
        user_following = (TextView) findViewById(R.id.user_following);
        toolbar_text = (TextView) findViewById(R.id.toolbar_logo);
        repos_btn = (Button) findViewById(R.id.repos_btn);
        feeds_btn = (Button) findViewById(R.id.feeds_btn);
        try {
            logindata = new JSONObject(getIntent().getStringExtra("logindata"));
            Picasso.with(this)
                    .load(logindata.getString("avatar_url"))
                    .into(userimage);
            user_name.setText(logindata.getString("name"));
            username = logindata.getString("login");
            user_login.setText(logindata.getString("login"));
            user_company.setText("Company - " + logindata.getString("company"));
            user_blog.setText(logindata.getString("blog"));
            user_email.setText(logindata.getString("email"));
            user_location.setText(logindata.getString("location"));
            user_followers.setText("Followers - " + logindata.getString("followers"));
            user_following.setText("Following - " + logindata.getString("following"));
            toolbar_text.setText(logindata.getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        feeds_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFeeds();
            }
        });

        repos_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePageActivity.this, ReposActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });
    }

    void getFeeds() {
        Intent intent = new Intent(HomePageActivity.this, FeedsActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);

    }
}
