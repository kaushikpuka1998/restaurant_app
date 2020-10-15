package com.example.resturent_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.PixelCopy;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.internal.ImageRequest;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Parameter;

import okhttp3.internal.Util;

public class ProfileActivity extends AppCompatActivity {

    int RC_SIGN_IN =0;
    TextView Username,usermail;
    CircularImageView profile;
    Button logout,goprofile,address;

    GoogleSignInClient mgoogleSignInClient;
    GoogleSignInAccount gacc;
    AccessToken accessToken;
    Profile pf;
    LoginResult loginResult;
    LoginButton lg;
    FirebaseUser firebaseUser;



    public static final String[] PERMISSIONS = new String[] {"email"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mgoogleSignInClient = GoogleSignIn.getClient(this,gso);

        Username = findViewById(R.id.profileusername);
        address = findViewById(R.id.addressdetails);
        profile = findViewById(R.id.profile);
        logout = findViewById(R.id.profilelogout);
        usermail = findViewById(R.id.profileuserEmail);
        goprofile = findViewById(R.id.profileorder);
        accessToken = AccessToken.getCurrentAccessToken();//Facebook
        pf = Profile.getCurrentProfile();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();//For Mobile

        goprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go = new Intent(getApplicationContext(),OrderActivity.class);
                startActivity(go);
            }
        });
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent r = new Intent(getApplicationContext(),AddressActivity.class);
                startActivity(r);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                switch(view.getId()){
                    case R.id.profilelogout:
                    signOut();
                    break;
                }

            }
        });

       gacc = GoogleSignIn.getLastSignedInAccount(this);//for Google
        if(gacc!=null)
        {
            Username.setText(gacc.getDisplayName());
            usermail.setText(gacc.getEmail());
            Uri photourl = gacc.getPhotoUrl();


            Glide.with(this).load(String.valueOf(photourl)).into(profile);//image pasting
        }else if(accessToken!=null && !accessToken.isExpired())
        {

            GraphRequest request = GraphRequest.newMeRequest(
                    accessToken,
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(
                                JSONObject object,
                                GraphResponse response) {
                            Log.v("LoginActivity Response ", response.toString());

                            try {
                                if(object.getString("email")!=null)//retriving Mail ID from Facebook Through Graph Request
                                {
                                    usermail.setText(object.getString("email"));
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });


            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,email,gender, birthday");
            request.setParameters(parameters);
            request.executeAsync();
                Username.setText(pf.getName());


                String photourl =  "http://graph.facebook.com/"+accessToken.getUserId() +"/picture?type=large";
                Picasso.get().load(photourl).into(profile);

        }else if(firebaseUser!=null)
        {
            Username.setText(firebaseUser.getPhoneNumber());
            usermail.setText(firebaseUser.getEmail());
        }




    }


    private void signOut()
        {
        if(gacc!=null)
        {
                mgoogleSignInClient.signOut()
                        .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(ProfileActivity.this, "You signed out Successfully", Toast.LENGTH_SHORT).show();
                                Intent y = new Intent(getApplicationContext(),LoginPanelActivity.class);
                                startActivity(y);
                                finish();
                            }
                        });
        }else if(pf!=null)
        {
                LoginManager.getInstance().logOut();
                Intent y = new Intent(getApplicationContext(),LoginPanelActivity.class);
                startActivity(y);
                Toast.makeText(ProfileActivity.this, "You signed out Successfully", Toast.LENGTH_SHORT).show();
        }
        else if(firebaseUser!=null)
        {
            FirebaseAuth.getInstance().signOut();
            Intent y = new Intent(getApplicationContext(),LoginPanelActivity.class);
            startActivity(y);
            Toast.makeText(ProfileActivity.this, "You signed out Successfully", Toast.LENGTH_SHORT).show();
        }



    }
}