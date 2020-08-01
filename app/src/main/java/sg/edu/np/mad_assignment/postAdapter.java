package sg.edu.np.mad_assignment;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class postAdapter extends RecyclerView.Adapter<postAdapter.ViewHolder> {

    private Context mContext;
    private List<Post> postList;

    public postAdapter(Context context, List<Post> posts)
    {
        mContext = context;
        postList = posts;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.post_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Post postCurrent = postList.get(position);
        holder.textViewCaption.setText("Caption: " + postCurrent.getCaption());
        holder.textViewUsername.setText("@ " + postCurrent.getUsername());
        holder.textViewName.setText(postCurrent.getName());
        Picasso.get()
                .load(postCurrent.getImageUrl())
                .fit()
                .centerCrop()
                .into(holder.imageView);
        if (postCurrent.getProfileUrl() == null || postCurrent.getProfileUrl().equals(""))
        {
            holder.pfpView.setImageResource(R.drawable.profile_icon);
        }
        else
        {
            Picasso.get()
                    .load(postCurrent.getProfileUrl())
                    .fit()
                    .centerCrop()
                    .into(holder.pfpView);
        }
        holder.pfpView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), OtherUserProfilePage.class);
                intent.putExtra("Username", postCurrent.getUsername());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewCaption, textViewUsername, textViewName;
        public ImageView imageView, pfpView, delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewCaption = itemView.findViewById(R.id.captionView);
            textViewUsername = itemView.findViewById(R.id.usernameView);
            imageView = itemView.findViewById(R.id.postImageView);
            textViewName = itemView.findViewById(R.id.nameView);
            pfpView = itemView.findViewById(R.id.pfpView);

        }
    }
}
