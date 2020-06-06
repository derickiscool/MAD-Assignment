package sg.edu.np.mad_assignment;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUpPage extends AppCompatActivity {
    private static final String TAG="SignUpPage";
    private Button registerButton;
    private TextView logIn;
    private String userName, userMail, userPassword, userPhone;
    private EditText etUserName,etUserMail,etUserPassword,etUserPhone;
    DatabaseReference reference;
    private Member member;

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
                etUserName = (EditText) findViewById(R.id.registerUserName);
                etUserMail = (EditText) findViewById(R.id.registerEmail);
                etUserPassword = (EditText) findViewById(R.id.registerPassword);
                etUserPhone = (EditText) findViewById(R.id.registerPhoneNumber);
                userName = etUserName.getText().toString();
                userMail = etUserMail.getText().toString();
                userPassword = etUserPassword.getText().toString();
                userPhone = etUserPhone.getText().toString();
                Log.d(TAG,"Validating user input");
                boolean check = checkUserInput(userName,userMail,userPassword,userPhone);
                if (check){
                    member = new Member();
                    reference = FirebaseDatabase.getInstance().getReference().child("Member");

                    member.setEmail(userMail);
                    member.setUsername(userName);
                    member.setPassword(userPassword);
                    member.setPhoneNumber(userPhone);
                    Log.d(TAG,"Member details set");
                    reference.child(userName).setValue(member); 
                    Toast.makeText(getApplicationContext(), "User Created Successfully",Toast.LENGTH_SHORT).show();
                    Log.d(TAG,"User created successfully");
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
    private boolean checkUserInput(String Id, String Email, String Password, String Num){
        return (!validatePhone(Num) | !validateName(Id) | validateEmail(Email) | validatePassword(Password));
        //need to check if credentials are inside database!!!

    }
    private Boolean validateName(String id) {
        if (id.isEmpty()) {
            etUserName.setError("Field cannot be empty");
            return false;
        } else if (id.length() > 15) {
            etUserName.setError("Username too long");
            return false;

        } else {
            etUserName.setError(null);
            return true;
        }
    }
    private Boolean validateEmail(String email){
        if (email.isEmpty()){
            etUserMail.setError("Field cannot be empty");
            return false;

        }
        else{
            etUserMail.setError(null);
            return true;
        }
    }
    private Boolean validatePassword(String pass){
        if (pass.isEmpty()){
            etUserPassword.setError("Field cannot be empty");
            return false;
        }
        else{
            etUserPassword.setError(null);
            return true;
        }
    }
    private Boolean validatePhone(String num){
        if (num.isEmpty()){
            etUserPhone.setError("Field cannot be empty");
            return false;
        }
        else{
            etUserPhone.setError(null);
            return true;
        }
    }





}
