package sg.edu.np.mad_assignment;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;

    private static final String TAG = "Dashboard";
    private ImageButton profile, categories, task, achievements;
    private String tempUsername;
    private Button logout;

    /*
    * This is the main code area for dashboard
    * stored here are the buttons for the dashboard which is the task, achievements,
    * categories, profile and logout respectively
    * OnBack stores the fragment so that when the button is clicked it will call
    * upon the fragment for each page respectively
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        drawer.setFocusableInTouchMode(false);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        profile = (ImageButton) findViewById(R.id.profileButton);
        categories = (ImageButton) findViewById(R.id.categoriesButton);
        task = (ImageButton) findViewById(R.id.taskButton);
        achievements = (ImageButton) findViewById(R.id.achievementButton);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Moving to profile page");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ProfilePage()).commit();

            }
        });

        categories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Moving to categories page");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Categories()).commit();
            }
        });
        task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Moving to task page");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new TaskPage()).commit();

            }
        });
        achievements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AchievementPage()).commit();

            }
        });
        

    }
    @Override
    public void onBackPressed(){
        Log.v(TAG,"Drawer is open! Closing!");
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId())
        {
            case R.id.nav_Calendar:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Calendar()).commit();
                break;

            case R.id.nav_Profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ProfilePage()).commit();
                break;
            case R.id.nav_Tasks:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new TaskPage()).commit();
                break;

            case R.id.nav_Achievements:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AchievementPage()).commit();
                break;

            case R.id.nav_Categories:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Categories()).commit();
                break;


            case R.id.nav_Logout:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Logout()).commit();
                break;


        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    protected void onStop(){
        Log.d(TAG,"Stopping application!");
        super.onStop();

    }
    protected void onPause(){
        Log.d(TAG,"Pausing Application!");
        super.onPause();
    }
}

