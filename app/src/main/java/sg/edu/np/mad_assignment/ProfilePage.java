package sg.edu.np.mad_assignment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import static android.content.Context.MODE_PRIVATE;

public class ProfilePage extends Fragment {

    private TextView username, name, bio;
    private Button editProfile;
    private ImageButton dashboard;
    private ImageView profilePic;
    String myUsername;
    final String TAG = "Profile Page";

    public String GLOBAL_PREFS = "MyPrefs";
    public String MY_USERNAME= "MyUsername";
    SharedPreferences sharedPreferences;



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_profile_page, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View v = getView();

        sharedPreferences = this.getActivity().getSharedPreferences(GLOBAL_PREFS, MODE_PRIVATE); //Only accessible to calling application.
        myUsername= sharedPreferences.getString(MY_USERNAME, "");


        username = v.findViewById(R.id.username);
        name = v.findViewById(R.id.name);
        bio = v.findViewById(R.id.bio);
        profilePic = v.findViewById(R.id.profileImage);
        editProfile = v.findViewById(R.id.editProfile);



        // Retrieve from database and set OnCreate
        username.setText(String.valueOf("@" + myUsername));
        setProfile(String.valueOf(myUsername));


        Log.v(TAG, "Finished Profile Pre-Initialisation!");

    }

    @Override
    public void onStart(){
        super.onStart();
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditProfilePage();
            }
        });

    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG, "Paused Profile Page!");
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG, "Stopped Profile Page!");

    }


    private void EditProfilePage(){
        Intent editPage = new Intent(getActivity(), EditProfile.class);
        startActivity(editPage);
        Log.d(TAG, "Proceeding to edit page!");
    }

    private void setProfile(final String id) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Member");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) { // listen for change in database
                String nameFromDB = dataSnapshot.child(id).child("name").getValue(String.class);
                String bioFromDB = dataSnapshot.child(id).child("bio").getValue(String.class);
                String currentURL = dataSnapshot.child(id).child("profilePicture").getValue(String.class);
                if(currentURL.equals(""))
                {
                    profilePic.setImageResource(R.drawable.profile_icon);
                }
                else
                {
                    Picasso.get().load(currentURL).fit().centerCrop().into(profilePic);
                }
                name.setText(String.valueOf(nameFromDB));
                bio.setText(String.valueOf(bioFromDB));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "Reading from database failed: " + databaseError.getCode());
            }
        });
    }
}
