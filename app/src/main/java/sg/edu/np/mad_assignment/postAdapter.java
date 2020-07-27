package sg.edu.np.mad_assignment;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.content.ContentValues.TAG;

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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Post postCurrent = postList.get(position);
        holder.textViewCaption.setText("Caption: " + postCurrent.getCaption());
        holder.textViewUsername.setText(Html.fromHtml("@ " + postCurrent.getUsername()));
        Picasso.get()
                .load(postCurrent.getImageUrl())
                .fit()
                .centerCrop()
                .into(holder.imageView);
        holder.textViewUsername.setOnClickListener(new View.OnClickListener() {
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
        public TextView textViewCaption, textViewUsername;
        public ImageView imageView, pfpView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewCaption = itemView.findViewById(R.id.captionView);
            textViewUsername = itemView.findViewById(R.id.usernameView);
            imageView = itemView.findViewById(R.id.postImageView);
        }
    }

    public String getPFP(final String id)
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Member");
        final String[] currentURL = {""};
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) { // listen for change in database
                currentURL[0] = dataSnapshot.child(id).child("profilePicture").getValue(String.class);
                Log.d(TAG, "PFP URL: " + currentURL[0]);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "Reading from database failed: " + databaseError.getCode());
            }
        });
        return currentURL[0];
    }
}
