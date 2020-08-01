package sg.edu.np.mad_assignment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class AchievementPage extends Fragment {
    RecyclerView recyclerView;
    ImageButton backButton;
    AchievementAdaptor achievementAdaptor;
    //ArrayList<Achievement> achievementArrayList;
    private ArrayList<Task> taskList = new ArrayList<Task>();;
    public String GLOBAL_PREFS = "MyPrefs";
    public String MY_USERNAME= "MyUsername";
    SharedPreferences sharedPreferences;
    String myUsername;
    private static final String TAG = "AchievementPage";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_achievement_page, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        View v = getView();
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerViewAchievement);
        GridLayoutManager glm = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(glm);
        //achievementArrayList = new ArrayList<>();
        achievementAdaptor = new AchievementAdaptor(taskList);
        recyclerView.setAdapter(achievementAdaptor);
        taskList = createListData();


    }
    public ArrayList<Task> createListData()
    {
        sharedPreferences = this.getActivity().getSharedPreferences(GLOBAL_PREFS, MODE_PRIVATE); //Only accessible to calling application.
        myUsername= sharedPreferences.getString(MY_USERNAME, "");
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("Member").child(myUsername).child("tasks");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for( DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    Boolean isAchieved = (Boolean) snapshot.child("achievement").child("isAchieved").getValue();

                    if (isAchieved)
                    {
                        String taskName = snapshot.child("text").getValue(String.class);
                        Task task = new Task(taskName);
                        String achievementName = snapshot.child("achievement").child("imageUrl").getValue(String.class);
                        Achievement achievement2 = new Achievement(achievementName);
                        task.setAchievement(achievement2);
                        taskList.add(task);
                        achievementAdaptor.notifyDataSetChanged();

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        achievementAdaptor.notifyDataSetChanged();
        return taskList;

    }

/* //Unused
    private void createListData(){
        //Add achievement images here
        Achievement ac1 = new Achievement(R.drawable.badge_readabook, "");
        achievementArrayList.add(ac1);
        Achievement ac2 = new Achievement(R.drawable.badge_podcast,"");
        achievementArrayList.add(ac2);
        Achievement ac3 = new Achievement(R.drawable.badge_news,"");
        achievementArrayList.add(ac3);
        Achievement ac4 = new Achievement(R.drawable.badge_cleanroom,"");
        achievementArrayList.add(ac4);
        Achievement ac5 = new Achievement(R.drawable.badge_cleanbed,"");
        achievementArrayList.add(ac5);
        Achievement ac6 = new Achievement(R.drawable.badge_washclothes,"");
        achievementArrayList.add(ac6);
        Achievement ac7 = new Achievement(R.drawable.badge_foldclothes,"");
        achievementArrayList.add(ac7);
        Achievement ac8 = new Achievement(R.drawable.badge_organizethings,"");
        achievementArrayList.add(ac8);
        Achievement ac9 = new Achievement(R.drawable.badge_clearmail,"");
        achievementArrayList.add(ac9);
        Achievement ac10 = new Achievement(R.drawable.badge_mealplan,"");
        achievementArrayList.add(ac10);
        Achievement ac11 = new Achievement(R.drawable.badge_exercise,"");
        achievementArrayList.add(ac11);
        Achievement ac12 = new Achievement(R.drawable.badge_meditate,"");
        achievementArrayList.add(ac12);
        Achievement ac13 = new Achievement(R.drawable.badge_newskill,"");
        achievementArrayList.add(ac13);
        Achievement ac14 = new Achievement(R.drawable.badge_cookanewdish,"");
        achievementArrayList.add(ac14);
        Achievement ac15 = new Achievement(R.drawable.badge_newlanguage,"");
        achievementArrayList.add(ac15);
        Achievement ac16 = new Achievement(R.drawable.badge_newinstrument,"");
        achievementArrayList.add(ac16);
        Achievement ac17 = new Achievement(R.drawable.badge_learntodraw,"");
        achievementArrayList.add(ac17);
        Achievement ac18 = new Achievement(R.drawable.badge_learntopaint,"");
        achievementArrayList.add(ac18);
        Achievement ac19 = new Achievement(R.drawable.badge_washdishes,"");
        achievementArrayList.add(ac19);
        Achievement ac20 = new Achievement(R.drawable.badge_composesong,"");
        achievementArrayList.add(ac20);
        Achievement ac21 = new Achievement(R.drawable.badge_callfriend,"");
        achievementArrayList.add(ac21);
        Achievement ac22 = new Achievement(R.drawable.badge_reflection,"");
        achievementArrayList.add(ac22);
        Achievement ac23 = new Achievement(R.drawable.badge_buyfood,"");
        achievementArrayList.add(ac23);
        Achievement ac24 = new Achievement(R.drawable.badge_encouragefriend,"");
        achievementArrayList.add(ac24);
    }
*/
}
