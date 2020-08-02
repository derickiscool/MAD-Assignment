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
import java.util.List;

public class SignUpPage extends AppCompatActivity {
    private static final String TAG = "SignUpPage";
    private Button registerButton;
    private TextView logIn;
    private String userName, userMail, userPassword, userPhone;
    private EditText etUserName, etUserMail, etUserPassword, etUserPhone;
    DatabaseReference reference;
    private ArrayList<String> usernameList = new ArrayList<>();
    private ArrayList<String> phoneList = new ArrayList<>();
    private ArrayList<String> emailList = new ArrayList<>();
    private Member member;
    String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]3+)*(\\.[A-Za-z]{2,})$";
    String phonePattern = "^[689][0-9]{7}$";

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

        initUsername(usernameList);
        initPhone(phoneList);
        initEmail(emailList);

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

    }

    private Boolean validateName(String id) {
        if (checkUsername(id, usernameList)) {
            etUserName.setError("Username is taken");
            return false;

        } else if (id.isEmpty()) {
            etUserName.setError("Field cannot be empty");
            return false;

        } else if (id.length() > 15) {
            etUserName.setError("Username must be less than 15 characters");
            return false;

        } else {
            etUserName.setError(null);
            return true;
        }
    }

    private Boolean validateEmail(String email) {
        if (checkEmail(email, emailList)) {
            etUserMail.setError("Email has been taken");
            return false;

        } else if (email.isEmpty()) {
            etUserMail.setError("Field cannot be empty");
            return false;

        } else if (email.matches(emailPattern)) {
            etUserMail.setError(null);
            return true;

        } else {
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
        if (checkPhone(num, phoneList)) {
            etUserPhone.setError("Phone number is already is use");
            return false;

        } else if (num.isEmpty()) {
            etUserPhone.setError("Field cannot be empty");
            return false;

        } else if (num.matches(phonePattern))
        {
            etUserPhone.setError(null);
            return true;

        } else {
            etUserPhone.setError("Invalid Phone number");
            return false;
        }
    }

    private ArrayList<String> initUsername(final ArrayList<String> mList) {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot memberSnapshot : snapshot.getChildren()) {
                    mList.add(memberSnapshot.getKey());
                    Log.d(TAG, "Username: " + memberSnapshot.getKey());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "Error: " + error.getMessage());
            }
        });
        return mList;
    }

    private ArrayList<String> initPhone(final ArrayList<String> mList) {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot memberSnapshot : snapshot.getChildren()) {
                    String key = memberSnapshot.getKey();
                    String num = snapshot.child(key).child("phoneNumber").getValue(String.class);
                    Log.d(TAG, "Phone Number: " + num);
                    mList.add(num);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "Error: " + error.getMessage());
            }
        });
        return mList;
    }

    private ArrayList<String> initEmail(final ArrayList<String> mList) {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot memberSnapshot : snapshot.getChildren()) {
                    String key = memberSnapshot.getKey();
                    String email = snapshot.child(key).child("email").getValue(String.class);
                    Log.d(TAG, "Email: " + email);
                    mList.add(email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "Error: " + error.getMessage());
            }
        });
        return mList;
    }

    private boolean checkUsername(String id, List<String> mList) {
        boolean usernameExist = false;
        for (String m : mList) {
            if (id.equals(m)) {
                usernameExist = true;
                break;
            }
        }
        return usernameExist;
    }

    private boolean checkPhone(String phone, List<String> mList) {
        boolean phoneExist = false;
        for (String m : mList) {
            if (phone.equals(m)) {
                phoneExist = true;
                break;
            }
        }
        return phoneExist;
    }

    private boolean checkEmail(String email, List<String> mList) {
        boolean emailExist = false;
        for (String m : mList) {
            if (email.equals(m)) {
                emailExist = true;
                break;
            }
        }
        return emailExist;
    }

}
