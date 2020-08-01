package sg.edu.np.mad_assignment;

import android.content.Intent;
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

import java.util.ArrayList;

public class SignUpPage extends AppCompatActivity {
    private static final String TAG = "SignUpPage";
    private Button registerButton;
    private TextView logIn;
    private String userName, userMail, userPassword, userPhone;
    private EditText etUserName, etUserMail, etUserPassword, etUserPhone;
    DatabaseReference reference;
    private Member member;
    String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        Log.d(TAG, "Creating Sign Up Page!");
        registerButton = (Button) findViewById(R.id.registerButton);
        logIn = (TextView) findViewById(R.id.registerText2);

        etUserName = (EditText) findViewById(R.id.registerUserName);
        etUserMail = (EditText) findViewById(R.id.registerEmail);
        etUserPassword = (EditText) findViewById(R.id.registerPassword);
        etUserPhone = (EditText) findViewById(R.id.registerPhoneNumber);

        reference = FirebaseDatabase.getInstance().getReference("Member");

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Log In Hyperlink clicked!");
                Log.d(TAG, "Redirecting to login page!");
                Intent LogIn = new Intent(SignUpPage.this, LoginPage.class);
                startActivity(LogIn);
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Register Button Clicked!");
                Log.d(TAG, "Retrieving user input!");
                userName = etUserName.getText().toString().trim();
                userMail = etUserMail.getText().toString().trim();
                userPassword = etUserPassword.getText().toString().trim();
                userPhone = etUserPhone.getText().toString().trim();
                ArrayList<Task> Tasks = new ArrayList<>();
                ArrayList<Achievement> Achievements = new ArrayList<>();
                Tasks = MainActivity.taskArrayList;
                Log.d(TAG, "Validating user input");
                boolean check = checkUserInput(userName, userMail, userPassword, userPhone);
                if (check) {
                    member = new Member();
                    member.setEmail(userMail);
                    member.setUsername(userName);
                    member.setPassword(userPassword);
                    member.setPhoneNumber(userPhone);
                    member.setTasks(Tasks);
                    member.setAchievements(Achievements);
                    member.setBio("");
                    member.setName("");
                    member.setProfilePicture("");
                    Log.d(TAG, "Member details set");
                    reference.child(userName).setValue(member);
                    Toast.makeText(getApplicationContext(), "User Created Successfully", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "User created successfully");
                    Intent LogIn = new Intent(SignUpPage.this, LoginPage.class);
                    startActivity(LogIn);
                }
            }
        });


    }

    protected void onStop() {
        Log.d(TAG, "Stopping application!");
        super.onStop();

    }

    protected void onPause() {
        Log.d(TAG, "Pausing Application!");
        super.onPause();
    }

    private boolean checkUserInput(String Id, String Email, String Password, String Num) {
        return (validatePhone(Num) && validateName(Id) && validateEmail(Email) && validatePassword(Password));
        //need to check if credentials are inside database!!!

    }

    private Boolean validateName(String id) {
        Boolean usernameExist = checkUsername(id);
        Log.d(TAG, "Function return: " + usernameExist);
        if (usernameExist) { // check if username exist in the database
            etUserName.setError("Username has already been taken");
            Log.d(TAG, "User Exists!");
            return false;

        } else if (id.length() > 15) {
            etUserName.setError("Username must be less than 15 characters");
            return false;

        } else if(id.isEmpty()) {
            etUserName.setError("Field cannot be empty");
            return false;

        } else {
            etUserName.setError(null);
            return true;
        }
    }

    private Boolean validateEmail(String email) {
        if (email.isEmpty()) {
            etUserMail.setError("Field cannot be empty");
            return false;

        }
        else if (email.matches(emailPattern)) {
            etUserMail.setError(null);
            return true;
        }
        else {
            etUserMail.setError("Email does not match format");
            return false;
        }
    }

    private Boolean validatePassword(String pass) {
        if (pass.isEmpty()) {
            etUserPassword.setError("Field cannot be empty");
            return false;
        } else {
            etUserPassword.setError(null);
            return true;
        }
    }

    private Boolean validatePhone(String num) {
        if (num.isEmpty()) {
            etUserPhone.setError("Field cannot be empty");
            return false;
        } else {
            etUserPhone.setError(null);
            return true;
        }
    }

    private Boolean checkUsername(String id) {
        final Boolean[] usernameExist = {false};
        reference.child(id);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    usernameExist[0] = true;
                    Log.d(TAG, "User exists in the database!");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG,"Failed to read from database: " + databaseError.getMessage());
            }
        });
        Log.d(TAG, "Type : " + usernameExist[0].getClass().getName());
        return usernameExist[0];
    }
}
