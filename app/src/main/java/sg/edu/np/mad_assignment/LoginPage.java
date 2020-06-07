package sg.edu.np.mad_assignment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class LoginPage extends AppCompatActivity{
    private static final String TAG = "LoginPage";
    TextView signUp;
    Button loginButton;
    EditText userId, userPassword;

    public String GLOBAL_PREFS = "MyPrefs";
    public String MY_USERNAME= "MyUsername";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Log.d(TAG,"Creating Login Page!");
        setContentView(R.layout.activity_login_page);
        signUp = (TextView) findViewById(R.id.signUpText2);
        loginButton = (Button) findViewById(R.id.loginButton);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"SignUp hyperlink clicked!");
                Log.d(TAG,"Redirecting to SignUp Page");
                Intent SignUp = new Intent(LoginPage.this, SignUpPage.class );
                startActivity(SignUp);//Redirecting to Sign Up Page;
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"Login Button Clicked!");
                Log.d(TAG,"Retrieving user input!");
                userId = (EditText) findViewById(R.id.userLoginID); //Receiving user input
                userPassword = (EditText) findViewById(R.id.userPassword);
                Log.d(TAG,"Checking for valid input!");
                checkUserInput(userId.getText().toString(),userPassword.getText().toString());




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

    public void checkUserInput(String id, String password){
        if (id.isEmpty()){
            Log.d(TAG,"Empty Username");
            userId.setError("Username cannot be empty!");

        }
        else if (password.isEmpty()){
            Log.d(TAG,"Empty Password");
            userPassword.setError("Password cannot be empty!");

        }
      else UserCheck(id, password);

        //need to check if credentials are inside database!!!

    }
    private void UserCheck(final String id, final String password)
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Member");

        Query checkUsername =  reference.orderByChild("username").equalTo(id);
        checkUsername.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if  (dataSnapshot.exists())
                {
                    String passwordFromDB = dataSnapshot.child(id).child("password").getValue(String.class);
                    Log.d(TAG,"password:" + passwordFromDB);

                    if (passwordFromDB.equals(password) )
                    {
                        sharedPreferences = getSharedPreferences(GLOBAL_PREFS, MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(MY_USERNAME, id.toString());
                        editor.apply();
                        Intent Dashboard = new Intent(LoginPage.this, Dashboard.class);
                        startActivity(Dashboard);

                    }
                    else{
                        userPassword.setError("Wrong password");
                    }

                }
                else{
                    userId.setError("No such user exists");
                    userId.requestFocus();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }





}
