package sg.edu.np.mad_assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "StartUpPage";
    ConstraintLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout = (ConstraintLayout) findViewById(R.id.main_layout);
        Log.d(TAG,"Creating Page Layout");
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"Moving to Login Page");
                Intent start = new Intent(MainActivity.this,LoginPage.class ); //Redirecting to Login Page upon click of layout.
                startActivity(start);
            }
        });

    }
    protected void OnStop(){
        Log.d(TAG,"App Stopping!");
        super.onStop();

    }
    protected void OnPause(){
        Log.d(TAG,"App Pausing!");
        super.onPause();
    }
    protected void OnDestroy(){
        Log.d(TAG,"Destroying App!");
        super.onDestroy();
    }

}
