package sg.edu.np.mad_assignment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class Categories extends AppCompatActivity {

    private Button foodButton, wellnessButton, healthButton;
    private ImageButton backButton;
    final String TAG = "Categories";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        foodButton = findViewById(R.id.category_food);
        wellnessButton = findViewById(R.id.category_wellness);
        healthButton = findViewById(R.id.category_health);
        backButton = findViewById(R.id.daashboardBackButton);

        foodButton.setBackgroundResource(R.drawable.food);
        wellnessButton.setBackgroundResource(R.drawable.wellness);
        healthButton.setBackgroundResource(R.drawable.health);

        Log.v(TAG, "Finished Dashboard Pre-Initialisation!");
    }

    @Override
    protected void onStart(){
        super.onStart();
        foodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Proceeding onto Food Feed!");
                foodPage(); // moves to food feed
            }
        });
        wellnessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Proceeding onto Wellness Feed!");
                wellnessPage(); // moves to wellness feed
            }
        });
        healthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Proceeding onto Health Feed!");
                healthPage(); // moves to health feed
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Proceeding back to Dashboard!");
                finish(); // moves back to dashboard
            }
        });
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.d(TAG, "Paused Application!");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.d(TAG, "Stopped Application!");
        finish();
    }


    private void foodPage(){
        Intent advancePage = new Intent(Categories.this, foodFeed.class);
        startActivity(advancePage);
    }

    private void wellnessPage(){
        Intent advancePage = new Intent(Categories.this, wellnessFeed.class);
        startActivity(advancePage);
    }

    private void healthPage(){
        Intent advancePage = new Intent(Categories.this, healthFeed.class);
        startActivity(advancePage);
    }
}
