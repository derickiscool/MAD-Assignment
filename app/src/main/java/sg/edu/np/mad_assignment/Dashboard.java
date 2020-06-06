package sg.edu.np.mad_assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

public class Dashboard extends AppCompatActivity {

    private static final String TAG = "Dashboard";
    private ImageButton profile, categories, task, achievements;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        profile = (ImageButton) findViewById(R.id.profileButton);
        categories = (ImageButton) findViewById(R.id.categoriesButton);
        task = (ImageButton) findViewById(R.id.taskButton);
        achievements = (ImageButton) findViewById(R.id.achievementButton);

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
