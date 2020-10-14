package com.example.resturent_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import static com.example.resturent_app.R.drawable.green_button;
import static com.example.resturent_app.R.drawable.greenshape;

public class MobileAuthenticationActivity extends AppCompatActivity {


    PinView otpText;
    FirebaseAuth mAuth;
    Button verifyl;
    ImageView backl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_authentication);
        TextView ph = findViewById(R.id.otpPhoneNumber);
        verifyl =findViewById(R.id.otpverify);
        otpText = findViewById(R.id.pin_view);
        backl = findViewById(R.id.backbutton);

        mAuth = FirebaseAuth.getInstance();


        Intent o = getIntent();
        String h = o.getStringExtra("Phone");

        h = "+91"+h;
        ph.setText(h);

        sendVerificationCodetoUser(h);

        backl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent op = new Intent(getApplicationContext(),LoginPanelActivity.class);
                startActivity(op);
            }
        });


    }

    private void sendVerificationCodetoUser(String ph) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                ph,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                TaskExecutors.MAIN_THREAD,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks

    }
    String codeBySystem;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {


                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    codeBySystem = s;

                }

                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                    String code = phoneAuthCredential.getSmsCode();
                    if(code!=null)
                    {
                        otpText.setText(code);
                        verifyCode(code);
                        verifyl.setText("Verified");
                        verifyl.setBackgroundResource(R.color.colorAccent);

                    }
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    Toast.makeText(MobileAuthenticationActivity.this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            };

    private void verifyCode(String code) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeBySystem,code);
        signInusingCredential(credential);
    }

    private void signInusingCredential(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful())
                {
                    Toast.makeText(MobileAuthenticationActivity.this, "OTP verified", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = task.getResult().getUser();
                    Intent po =new Intent(getApplicationContext(),MainActivity.class);
                    po.putExtra("User",user);
                    startActivity(po);
                }else
                {
                    Toast.makeText(MobileAuthenticationActivity.this, "Not Verified", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

}