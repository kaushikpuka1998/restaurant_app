package com.example.resturent_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.LoginStatusCallback;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class LoginPanelActivity extends AppCompatActivity {


    Button signInButton,facebooksignIn,PhoneSignIn;
    LoginButton facebookob;
    int RC_SIGN_IN =0;
    private FirebaseAuth firebaseAuth;

    GoogleSignInClient mgoogleSignInClient;
    CallbackManager callbackManager;
    EditText phone;
    private long backPressedtime;
    @Override
    public void onBackPressed() {//Double Back Pressed  and Exit Function

        if(backPressedtime + 2000 >System.currentTimeMillis())
        {
            super.onBackPressed();
            Intent in = new Intent(Intent.ACTION_MAIN);
            in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            in.addCategory(Intent.CATEGORY_HOME);
            startActivity(in);
            finish();
            System.exit(0);
        }else
        {
            Toast.makeText(this, "Press Back again to exit", Toast.LENGTH_SHORT).show();
        }

        backPressedtime = System.currentTimeMillis();



    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_panel);

        facebookob = findViewById(R.id.facebookb);

        PhoneSignIn = findViewById(R.id.phonesign);

        phone = findViewById(R.id.loginPhone);

        firebaseAuth = FirebaseAuth.getInstance();


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();

        mgoogleSignInClient = GoogleSignIn.getClient(this, gso);

        PhoneSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ph = phone.getText().toString();
                if(!ph.isEmpty() && ph.length()==10)
                {
                    Intent i = new Intent(getApplicationContext(),MobileAuthenticationActivity.class);
                    i.putExtra("Phone",ph);
                    startActivity(i);
                }else
                {
                    Toast.makeText(LoginPanelActivity.this, "Please Enter Valid Phone Number", Toast.LENGTH_SHORT).show();
                }

            }
        });
        signInButton = findViewById(R.id.googleloginbutton);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (view.getId()) {
                    case R.id.googleloginbutton:
                        signIn();


                        break;
                }

            }
        });

        facebooksignIn = findViewById(R.id.facebookloginbutton);
        facebookob.setReadPermissions(Arrays.asList(
                "public_profile", "email"));
        callbackManager = CallbackManager.Factory.create();

        facebooksignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                facebookob.performClick();
                loginWithFB();
            }
        });
    }
    String FEmail;
    private void loginWithFB()
    {

            LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                AccessToken accessToken = loginResult.getAccessToken();
                Profile profile = Profile.getCurrentProfile();

                Intent y= new Intent(getApplicationContext(),MainActivity.class);
                startActivity(y);
                // Facebook Email address

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                Log.v("LoginActivity Response ", response.toString());

                                try {
                                    FEmail = object.getString("email");
                                    Log.v("Email = ", " " + FEmail);



                                    





                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender, birthday");
                request.setParameters(parameters);
                request.executeAsync();


            }


            @Override
            public void onCancel() {
                Toast.makeText(LoginPanelActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                Log.d("Successful:"," not Successful Facebook Integration");
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(LoginPanelActivity.this, "Error", Toast.LENGTH_SHORT).show();
                Log.d("Successful:"," Error");
            }
        });


    }









//Gmail Login
    private void signIn()
    {
        Intent intent = mgoogleSignInClient.getSignInIntent();
        startActivityForResult(intent,RC_SIGN_IN);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }else
        {

            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.

            Intent y= new Intent(getApplicationContext(),MainActivity.class);
            startActivity(y);



        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("signInResult" , String.valueOf(e.getStatusCode()));

        }
    }
}