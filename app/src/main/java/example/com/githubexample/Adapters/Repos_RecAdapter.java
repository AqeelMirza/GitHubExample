package example.com.githubexample.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response;

import java.util.ArrayList;

import example.com.githubexample.Models.ReposData_Model;
import example.com.githubexample.R;

/**
 * Created by Aqeel.mirza on 12/13/2016.
 */

public class Repos_RecAdapter extends RecyclerView.Adapter<Repos_RecAdapter.RecyclerViewHolders> {
    Context con;
    int recId;
    ArrayList<ReposData_Model> reposData_modelArrayList;

    public Repos_RecAdapter(Context context, int rec_items, ArrayList<ReposData_Model> reposData_modelArrayList) {
        this.con = context;
        this.recId = rec_items;
        this.reposData_modelArrayList = reposData_modelArrayList;

    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(recId, parent, false);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);

        return rcv;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {

        holder.title.setText(reposData_modelArrayList.get(position).getTitle());
        if (reposData_modelArrayList.get(position).getDesc().equalsIgnoreCase("null")) {
            holder.desc.setVisibility(View.GONE);
        } else {
            holder.desc.setVisibility(View.VISIBLE);
            holder.desc.setText(reposData_modelArrayList.get(position).getDesc());
        }
        if (reposData_modelArrayList.get(position).getLanguage().equalsIgnoreCase("null")) {
            holder.language.setVisibility(View.GONE);
        } else {
            holder.language.setVisibility(View.VISIBLE);
            holder.language.setText("Language - " + reposData_modelArrayList.get(position).getLanguage());
        }
        if (reposData_modelArrayList.get(position).getStars() == 0) {
            holder.stars.setVisibility(View.GONE);
        } else {
            holder.stars.setVisibility(View.VISIBLE);
            holder.stars.setText("Stars - " + reposData_modelArrayList.get(position).getStars());
        }
        holder.forks.setText("Forks - " + reposData_modelArrayList.get(position).getForks());


    }

    @Override
    public int getItemCount() {
        return reposData_modelArrayList.size();
    }

    public class RecyclerViewHolders extends RecyclerView.ViewHolder {
        TextView title, desc, language, forks, stars;

        public RecyclerViewHolders(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.repos_title);
            desc = (TextView) itemView.findViewById(R.id.repos_desc);
            language = (TextView) itemView.findViewById(R.id.language);
            forks = (TextView) itemView.findViewById(R.id.repos_forks);
            stars = (TextView) itemView.findViewById(R.id.repos_stars);
        }
    }
}
