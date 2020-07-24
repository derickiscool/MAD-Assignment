package sg.edu.np.mad_assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

public class ForgetPassword extends AppCompatActivity {
    private static final String TAG = "ForgetPassword";
    EditText userInput;
    Button submitButton;
    FirebaseAuth auth;
    String passwordFromDB;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;
    String verificationCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        Log.d(TAG,"Creating Forget Password page");
        userInput = (EditText) findViewById(R.id.eTEmailOrNumber);
        submitButton = (Button) findViewById(R.id.forgetSubmitButton);
        auth = FirebaseAuth.getInstance();
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"SubmitButton clicked");
                validateInput(userInput.getText().toString());


            }
        });


    }
    private void validateInput(final String input)
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Member");
        if (input.matches("-?(0|[1-9]\\d*)"))
        {

            Query checkNumber = reference.orderByChild("phoneNumber").equalTo(input);
            checkNumber.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists())
                    {
                        mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {

                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(s, forceResendingToken);
                                verificationCode = s;
                                Toast.makeText(getApplicationContext(),"Code Sent!",Toast.LENGTH_SHORT).show();
                            }
                        };
                        sendSMS(input);
                        passwordFromDB = dataSnapshot.child("password").getValue(String.class);
                        Log.d(TAG, "Password: "+ passwordFromDB);
                        AlertDialog.Builder builder = new AlertDialog.Builder(ForgetPassword.this);
                        LayoutInflater inflater = getLayoutInflater();
                        View layout = inflater.inflate(R.layout.alert_otp,(ViewGroup) findViewById(R.id.alert_otp_root));
                        builder.setView(layout);
                        builder.show();

                    }
                    else{
                        userInput.setError("Not a valid phone number/Email!");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
        else if (input.matches("^(.+)@(.+)$")){
            Query checkEmail = reference.orderByChild("email").equalTo(input);

            checkEmail.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists())
                    {
                        for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                            String id = childSnapshot.getKey();
                            Log.i(TAG, id);

                            String passwordFromDB = dataSnapshot.child(id).child("password").getValue(String.class);
                            Toast.makeText(getApplicationContext(), "Your password is " + passwordFromDB, Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"No such user!",Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
        else{
            userInput.setError("Not a valid phone number/Email!");
        }
    }
    private void sendSMS(String input)
    {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(input,60,TimeUnit.SECONDS,this,mCallback);
    }
    private void signInWithPhone(PhoneAuthCredential credential)
    {
        auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(),"Your password is "+ passwordFromDB, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void verify(String input)
    {
        EditText otp = (EditText) findViewById(R.id.otp_answer);
        input = otp.getText().toString();
        if(verificationCode.equals(""))
        {
            verifyPhoneNumber(verificationCode,input);
        }


    }

    private void verifyPhoneNumber(String verifyCode, String input)
    {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verifyCode,input);
        signInWithPhone(credential);
    }


}
