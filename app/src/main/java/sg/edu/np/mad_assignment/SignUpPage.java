package sg.edu.np.mad_assignment;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpPage extends AppCompatActivity {
    private static final String TAG="SignUpPage";
    Button registerButton;
    TextView logIn;
    EditText userName,userMail,userPassword,userPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
        Log.d(TAG,"Creating Sign Up Page!");
        registerButton = (Button) findViewById(R.id.registerButton);
        logIn = (TextView) findViewById(R.id.registerText2);
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"Log In Hyperlink clicked!");
                Log.d(TAG,"Redirecting to login page!");
                Intent LogIn = new Intent(SignUpPage.this,LoginPage.class);
                startActivity(LogIn);
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"Register Button Clicked!");
                Log.d(TAG,"Retrieving user input!");
                userName = (EditText) findViewById(R.id.registerUserName);
                userMail = (EditText) findViewById(R.id.registerEmail);
                userPassword = (EditText) findViewById(R.id.registerPassword);
                userPhone = (EditText) findViewById(R.id.registerPhoneNumber);
                Log.d(TAG,"Validating user input");
                boolean check = checkUserInput(userName.getText().toString(),userPassword.getText().toString(),userMail.getText().toString(),userPhone.getText().toString());
                if (check){
                    Intent LogIn = new Intent(SignUpPage.this,LoginPage.class);
                    startActivity(LogIn);
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
    public boolean checkUserInput(String id, String password, String email, String phone){
        if (id.isEmpty()){
            Log.d(TAG,"Empty Username");
            userName.setError("Username cannot be empty!");
            return false;
        }
        else if (password.isEmpty()){
            Log.d(TAG,"Empty Password");
            userPassword.setError("Password cannot be empty!");
            return false;
        }
        else if (email.isEmpty()){
            Log.d(TAG,"Empty Email");
            userMail.setError("Email cannot be Empty!!");
        }
        else if (phone.isEmpty()){
            Log.d(TAG,"Empty Phone Number");
            userPhone.setError("Phone number cannot be empty");
        }
        return true;
        //need to check if credentials are inside database!!!

    }


}
