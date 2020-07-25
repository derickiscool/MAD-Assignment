package sg.edu.np.mad_assignment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class postAdapter extends RecyclerView.Adapter<postAdapter.ImageViewHolder> {

    private Context mContext;
    private List<Post> postList;

    public postAdapter(Context context, List<Post> posts)
    {
        mContext = context;
        postList = posts;
    }
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.post_item, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Post postCurrent = postList.get(position);
        holder.textViewCaption.setText("Caption: " + postCurrent.getCaption());
        holder.textViewUsername.setText("@" + postCurrent.getUsername());
        Picasso.get()
                .load(postCurrent.getImageUrl())
                .fit()
                .centerCrop()
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewCaption, textViewUsername;
        public ImageView imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewCaption = itemView.findViewById(R.id.captionView);
            textViewUsername = itemView.findViewById(R.id.usernameView);
            imageView = itemView.findViewById(R.id.postImageView);
        }
    }
}
