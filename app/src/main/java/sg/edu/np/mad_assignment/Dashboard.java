package sg.edu.np.mad_assignment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {
    private DrawerLayout drawer;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferenceTasks, mReferenceAchievements;

    public String GLOBAL_PREFS = "MyPrefs";
    SharedPreferences sharedPreferences;

    private static final String TAG = "Dashboard";
    private ImageButton profile, categories, task, achievements;
    private String tempUsername;
    private Button logout;
    public static ArrayList<Task> taskArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        sharedPreferences = getSharedPreferences(GLOBAL_PREFS, MODE_PRIVATE); //Only accessible to calling application.

        Toolbar toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        taskArrayList = createListData();


        profile = (ImageButton) findViewById(R.id.profileButton);
        categories = (ImageButton) findViewById(R.id.categoriesButton);
        task = (ImageButton) findViewById(R.id.taskButton);
        achievements = (ImageButton) findViewById(R.id.achievementButton);
        logout = (Button) findViewById(R.id.logoutButton);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Moving to profile page");
                Intent intent = new Intent(Dashboard.this, ProfilePage.class);
                startActivity(intent);

            }
        });
        categories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Moving to categories page");
                Intent intent = new Intent(Dashboard.this , Categories.class);
                startActivity(intent);
            }
        });
        task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Moving to task page");
                Intent intent = new Intent(Dashboard.this, TaskPage.class);
                startActivity(intent);
            }
        });
        achievements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Moving to achievements page");
                Intent intent = new Intent(Dashboard.this, AchievementPage.class);
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(Dashboard.this);
                alert.setTitle("Logout");
                alert.setMessage("Are you sure you want to logout?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences.Editor e = sharedPreferences.edit();
                        e.clear();

                        Intent logout = new Intent(Dashboard.this, MainActivity.class);
                        startActivity(logout);
                        Dashboard.this.finish();
                        Log.d(TAG, "Logging Out!");
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        Log.d(TAG, "Logging Out Declined!");
                    }
                });
                alert.show();
            }
        });

    }
    @Override
    public void onBackPressed(){
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }
    protected void onStop(){
        Log.d(TAG,"Stopping application!");
        super.onStop();

    }
    protected void onPause(){
        Log.d(TAG,"Pausing Application!");
        super.onPause();
    }

    //Create task list, links each achievement to task
    private ArrayList<Task> createListData(){
        ArrayList<Task> taskList = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance();
        mReferenceTasks = mDatabase.getReference("Tasks");
        mReferenceTasks.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Task t1 = new Task("Read a book");
        Achievement ac1 = new Achievement(R.drawable.badge_readabook);
        t1.setAchievement(ac1);
        taskList.add(t1);

        Task t2 = new Task("Listen to a podcast");
        Achievement ac2 = new Achievement(R.drawable.badge_podcast);
        t2.setAchievement(ac2);
        taskList.add(t2);

        Task t3 = new Task("Watch the news");
        Achievement ac3 = new Achievement(R.drawable.badge_news);
        t3.setAchievement(ac3);
        taskList.add(t3);

        Task t4 = new Task("Clean your room");
        Achievement ac4 = new Achievement(R.drawable.badge_cleanroom);
        t4.setAchievement(ac4);
        taskList.add(t4);

        Task t5 = new Task("Tidy your bed");
        Achievement ac5 = new Achievement(R.drawable.badge_cleanbed);
        t5.setAchievement(ac5);
        taskList.add(t5);

        Task t6 = new Task("Wash your clothes");
        Achievement ac6 = new Achievement(R.drawable.badge_washclothes);
        t6.setAchievement(ac6);
        taskList.add(t6);

        Task t7 = new Task("Fold clothing");
        Achievement ac7 = new Achievement(R.drawable.badge_foldclothes);
        t7.setAchievement(ac7);
        taskList.add(t7);

        Task t8 = new Task("Organize your belongings");
        Achievement ac8 = new Achievement(R.drawable.badge_organizethings);
        t8.setAchievement(ac8);
        taskList.add(t8);

        Task t9 = new Task("Clear the mail");
        Achievement ac9 = new Achievement(R.drawable.badge_clearmail);
        t9.setAchievement(ac9);
        taskList.add(t9);

        Task t10 = new Task("Create a meal plan");
        Achievement ac10 = new Achievement(R.drawable.badge_mealplan);
        t10.setAchievement(ac10);
        taskList.add(t10);

        Task t11 = new Task("Exercise");
        Achievement ac11 = new Achievement(R.drawable.badge_exercise);
        t11.setAchievement(ac11);
        taskList.add(t11);

        Task t12 = new Task("Meditate");
        Achievement ac12 = new Achievement(R.drawable.badge_meditate);
        t12.setAchievement(ac12);
        taskList.add(t12);

        Task t13 = new Task("Pick up a new skill");
        Achievement ac13 = new Achievement(R.drawable.badge_newskill);
        t13.setAchievement(ac13);
        taskList.add(t13);

        Task t14 = new Task("Learn to cook something new");
        Achievement ac14 = new Achievement(R.drawable.badge_cookanewdish);
        t14.setAchievement(ac14);
        taskList.add(t14);

        Task t15 = new Task("Learn a new language");
        Achievement ac15 = new Achievement(R.drawable.badge_newlanguage);
        t15.setAchievement(ac15);
        taskList.add(t15);

        Task t16 = new Task("Learn to play a new musical instrument");
        Achievement ac16 = new Achievement(R.drawable.badge_newinstrument);
        t16.setAchievement(ac16);
        taskList.add(t16);

        Task t17 = new Task("Learn to draw");
        Achievement ac17 = new Achievement(R.drawable.badge_learntodraw);
        t17.setAchievement(ac17);
        taskList.add(t17);

        Task t18 = new Task("Learn to paint");
        Achievement ac18 = new Achievement(R.drawable.badge_learntopaint);
        t18.setAchievement(ac18);
        taskList.add(t18);

        Task t19 = new Task("Wash the dishes");
        Achievement ac19 = new Achievement(R.drawable.badge_washdishes);
        t19.setAchievement(ac19);
        taskList.add(t19);

            Task t20 = new Task("Compose a song");
        Achievement ac20 = new Achievement(R.drawable.badge_composesong);
        t20.setAchievement(ac20);
        taskList.add(t20);

        Task t21 = new Task("Call a friend");
        Achievement ac21 = new Achievement(R.drawable.badge_callfriend);
        t21.setAchievement(ac21);
        taskList.add(t21);

        Task t22 = new Task("Do a reflection on a piece of paper");
        Achievement ac22 = new Achievement(R.drawable.badge_reflection);
        t22.setAchievement(ac22);
        taskList.add(t22);

        Task t23 = new Task("Buy food for the family or yourself");
        Achievement ac23 = new Achievement(R.drawable.badge_buyfood);
        t23.setAchievement(ac23);
        taskList.add(t23);

        Task t24 = new Task("Encourage a friend with a Gift");
        Achievement ac24 = new Achievement(R.drawable.badge_encouragefriend);
        t24.setAchievement(ac24);
        taskList.add(t24);

        return taskList;
    }
}
