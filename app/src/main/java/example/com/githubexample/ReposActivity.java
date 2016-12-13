package example.com.githubexample;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import example.com.githubexample.Adapters.Repos_RecAdapter;
import example.com.githubexample.Models.ReposData_Model;
import example.com.githubexample.R;
import example.com.githubexample.Utils.GitHubExample;
import example.com.githubexample.Utils.Global;
import example.com.githubexample.Utils.RecyclerItemClickListener;

/**
 * Created by Aqeel.mirza on 12/13/2016.
 */

public class ReposActivity extends Activity {
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManagerVertical;
    ReposData_Model reposData_model;
    ArrayList<ReposData_Model> reposData_modelArrayList;
    String repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repos_layout);

        recyclerView = (RecyclerView) findViewById(R.id.rec_view);
        linearLayoutManagerVertical = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManagerVertical);
        recyclerView.setHasFixedSize(true);
        final String username = getIntent().getStringExtra("username");
        getRepos(username);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        Intent in = new Intent(ReposActivity.this, RepoDetailsActivity.class);
                        in.putExtra("username", username);
                        in.putExtra("repo", reposData_modelArrayList.get(position).getName());
                        startActivity(in);
                    }
                }));
    }

    void getRepos(String username) {

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Please wait...");
        pd.show();

        String RequestUrl = Global.url + "users/" + username + "/repos";

        StringRequest postRequest_Categories = new StringRequest(Request.Method.GET, RequestUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("ReposResponse", "" + response);
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            reposData_modelArrayList = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                reposData_model = new ReposData_Model();
                                if (jsonArray.length() == 0) {
                                    Toast.makeText(ReposActivity.this, "Your repository is empty", Toast.LENGTH_LONG).show();
                                } else {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    repo = jsonObject.getString("name");
                                    String title = jsonObject.getString("full_name");
                                    String desc = jsonObject.getString("description");
                                    String lang = jsonObject.getString("language");
                                    int stars = jsonObject.getInt("stargazers_count");
                                    int forks = jsonObject.getInt("forks");
                                    reposData_model.setName(repo);
                                    reposData_model.setTitle(title);
                                    reposData_model.setDesc(desc);
                                    reposData_model.setLanguage(lang);
                                    reposData_model.setForks(forks);
                                    reposData_model.setStars(stars);
                                }
                                reposData_modelArrayList.add(reposData_model);
                            }
                            Repos_RecAdapter repos_recAdapter = new Repos_RecAdapter(ReposActivity.this, R.layout.rec_items, reposData_modelArrayList);
                            recyclerView.setAdapter(repos_recAdapter);
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
                        Toast.makeText(ReposActivity.this, "Error occurred", Toast.LENGTH_SHORT).show();
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
