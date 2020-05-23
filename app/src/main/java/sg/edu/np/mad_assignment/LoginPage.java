package sg.edu.np.mad_assignment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginPage extends AppCompatActivity{
    private static final String TAG = "LoginPage";
    TextView signUp;
    Button loginButton;
    EditText userId, userPassword;

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
                boolean check = checkUserInput(userId.getText().toString(),userPassword.getText().toString());
                if (check){
                    //Intent Dashboard = new Intent(LoginPage.this,);

                }



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

    public boolean checkUserInput(String id, String password){
        if (id.isEmpty()){
            Log.d(TAG,"Empty Username");
            userId.setError("Username cannot be empty!");
            return false;
        }
        else if (password.isEmpty()){
            Log.d(TAG,"Empty Password");
            userPassword.setError("Password cannot be empty!");
            return false;
        }
        return true;
        //need to check if credentials are inside database!!!

    }





}
