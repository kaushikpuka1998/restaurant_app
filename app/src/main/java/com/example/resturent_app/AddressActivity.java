package com.example.resturent_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.resturent_app.model.address;
import com.example.resturent_app.model.orderdetails;
import com.facebook.AccessToken;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AddressActivity extends AppCompatActivity {


    Button addAddress;
    Button imageAdd;
    RecyclerView addressrecycle;
    ImageView  backi;

    AccessToken accessToken;
    FirebaseUser firebaseUser;
    GoogleSignInAccount gacc;

    EditText ad1,ad2,pin,phn;

    AlladdressRecycleradapter alladdressRecycleradapter;

    String userid;
    DatabaseReference mRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);



        addAddress = findViewById(R.id.addAddress);
        addressrecycle = findViewById(R.id.addressrecycle);
        backi = findViewById(R.id.backImage);

        imageAdd = findViewById(R.id.imageAddress);
        backi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        mRef = FirebaseDatabase.getInstance().getReference();
        accessToken = AccessToken.getCurrentAccessToken();//Facebook
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();//For Mobile


        gacc = GoogleSignIn.getLastSignedInAccount(this);//for Google
        gacc = GoogleSignIn.getLastSignedInAccount(getApplicationContext());//for Google
        if(gacc!=null)
        {

            userid  = gacc.getId();//for google


        }else if(accessToken!=null && !accessToken.isExpired())
        {
            userid =accessToken.getUserId();//for facebook

        }else if(firebaseUser!=null)
        {
            userid = firebaseUser.getUid();//for Phone
        }
        FirebaseRecyclerOptions<address> options =
                new FirebaseRecyclerOptions.Builder<address>()
                        .setQuery(mRef.child("Address").child(userid), address.class)
                        .build();

        addressrecycle.setLayoutManager(new LinearLayoutManager(this));



        alladdressRecycleradapter = new AlladdressRecycleradapter(options,getApplicationContext());
        addressrecycle.setAdapter(alladdressRecycleradapter);
        addAddress.setOnClickListener(new View.OnClickListener()//imageview of address activity
        {
            @Override
            public void onClick(View view) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(AddressActivity.this);
                bottomSheetDialog.setContentView(R.layout.addressdetailsbottomsheet);

                bottomSheetDialog.setCanceledOnTouchOutside(true);

                //If  .show not used then bottomsheet will not come

                ad1 =bottomSheetDialog. findViewById(R.id.Add1);
                ad2 = bottomSheetDialog.findViewById(R.id.Add2);
                pin = bottomSheetDialog.findViewById(R.id.pincd);
                phn = bottomSheetDialog.findViewById(R.id.phonebottom);
                imageAdd = bottomSheetDialog.findViewById(R.id.imageAddress);
                bottomSheetDialog.show();
                accessToken = AccessToken.getCurrentAccessToken();//Facebook
                firebaseUser = FirebaseAuth.getInstance().getCurrentUser();//For Mobile
                imageAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String addline1 = ad1.getText().toString();
                        String addline2 = ad2.getText().toString();
                        String pinc = pin.getText().toString();
                        String phnn =  phn.getText().toString();

                        HashMap<String,Object> mp= new HashMap<>();

                        mp.put("UserId", userid);
                        mp.put("Addresslineone",addline1);
                        mp.put("Addresslinetwo",addline2);
                        mp.put("Pincode",pinc);
                        mp.put("Phone",phnn);
                        mRef = FirebaseDatabase.getInstance().getReference();
                        mRef.child("Address").child(userid).push().setValue(mp).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                bottomSheetDialog.hide();
                                Toast.makeText(AddressActivity.this, "Address Addded Successfully", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AddressActivity.this, "Error,Not Added", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });



            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        alladdressRecycleradapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        alladdressRecycleradapter.stopListening();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}