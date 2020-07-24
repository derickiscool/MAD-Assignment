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
                foodPage();
            }
        });
        wellnessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wellnessPage();
            }
        });
        healthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                healthPage();
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(Categories.this, Dashboard.class);
                startActivity(back);
            }
        });
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.v(TAG, "Paused Dashboard!");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.v(TAG, "Stopped Dashboard!");
        finish();
    }


    private void foodPage(){
        Intent advancePage = new Intent(Categories.this, foodFeed.class);
        startActivity(advancePage);
    }

    private void wellnessPage(){
        Intent advancedPage = new Intent(Categories.this, wellnessFeed.class);
        startActivity(advancedPage);
    }

    private void healthPage(){
        //Intent advancedPage = new Intent(Dashboard.this, healthFeed.class);
        //startActivity(advancedPage);
    }
}
