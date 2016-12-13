package example.com.githubexample;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import example.com.githubexample.Models.LoginData_Model;
import example.com.githubexample.Utils.GitHubExample;
import example.com.githubexample.Utils.Global;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LandingActivity extends AppCompatActivity {

    // UI references.
    private EditText password_et, username_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_landing);
        // Set up the login form.
        username_et = (EditText) findViewById(R.id.username);
        password_et = (EditText) findViewById(R.id.password);

        Button login = (Button) findViewById(R.id.email_sign_in_button);
        login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
    }

    private void attemptLogin() {
        // Reset errors.
        username_et.setError(null);
        password_et.setError(null);

        // Store values at the time of the login attempt.
        String usernamestr = username_et.getText().toString();
        String passwordstr = password_et.getText().toString();
        if (usernamestr.isEmpty() || passwordstr.isEmpty()) {
            Toast.makeText(this, "Please enter values", Toast.LENGTH_SHORT).show();
        } else {
            getLoginDetails(usernamestr);
        }

    }

    /**
     * Represents an asynchronous login task used to authenticate
     * the user.
     */
    void getLoginDetails(String username) {

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Please wait...");
        pd.show();
        String RequestUrl = Global.url + "users/" + username;

        StringRequest postRequest_Categories = new StringRequest(Request.Method.GET, RequestUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("LoginResponse", "" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Intent in = new Intent(LandingActivity.this, HomePageActivity.class);
                            in.putExtra("logindata", jsonObject.toString());
                            startActivity(in);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        pd.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        pd.dismiss();
                        Toast.makeText(LandingActivity.this, "Error occurred", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
        };
        postRequest_Categories.setRetryPolicy(new DefaultRetryPolicy(8000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        GitHubExample.getInstance().addToRequestQueue(postRequest_Categories);


    }
}

