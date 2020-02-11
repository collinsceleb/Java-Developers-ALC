package com.example.collinsceleb.developersdiary;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Collinsceleb on 9/8/2017.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private Context context;
    private List<Developer> developerList;

    public CustomAdapter(Context context, List<Developer> developerList) {
        this.context = context;
        this.developerList = developerList;
    }

    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }
    @Override
    public int getItemCount() {
        return developerList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView developer_profile_image;
        private TextView developer_username;
        private TextView developer_Url;

        public ViewHolder (View v){
            super(v);
            developer_profile_image = (CircleImageView)  itemView.findViewById(R.id.developer_profile_image);
            developer_username = (TextView) itemView.findViewById(R.id.developer_username);
            developer_Url = (TextView) itemView.findViewById(R.id.developer_url);
        }
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Developer eachDeveloper = developerList.get(position);

        // A holder for each developer username
        holder.developer_username.setText(eachDeveloper.login);

        // A holder for each developer profile picture
        Picasso.with(context).load(eachDeveloper.avatar_url).fit().placeholder(R.drawable.avatar).into(holder.developer_profile_image);

        // Creating intent for each developer to populate full information from the API
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Developer_Information.class);
                String username = developerList.get(holder.getAdapterPosition()).login;
                String avatar = developerList.get(holder.getAdapterPosition()).avatar_url;
                String url = developerList.get(holder.getAdapterPosition()).html_url;

                intent.putExtra("username", username);
                intent.putExtra("avatar", avatar);
                intent.putExtra("url", url);
                context.startActivity(intent);
            }
        });
    }
}
