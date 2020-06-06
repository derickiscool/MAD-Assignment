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
    private DatabaseReference reference;
    private Member member;
    private long maxId;

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
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists())
                            {
                                maxId=(dataSnapshot.getChildrenCount());
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    member.setEmail(userMail);
                    member.setUsername(userName);
                    member.setPassword(userPassword);
                    member.setPhoneNumber(userPhone);
                    reference.child(String.valueOf(maxId+1)).setValue("Member");
                    Toast.makeText(getApplicationContext(), "User Created Successfully",Toast.LENGTH_SHORT).show();
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
            etUserName.setError("Username cannot be empty!");
            return false;
        }
        else if (password.isEmpty()){
            Log.d(TAG,"Empty Password");
            etUserPassword.setError("Password cannot be empty!");
            return false;
        }
        else if (email.isEmpty()){
            Log.d(TAG,"Empty Email");
            etUserMail.setError("Email cannot be Empty!!");
        }
        else if (phone.isEmpty()){
            Log.d(TAG,"Empty Phone Number");
            etUserPhone.setError("Phone number cannot be empty");
        }
        return true;
        //need to check if credentials are inside database!!!

    }


}
