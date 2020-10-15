package com.example.resturent_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import java.text.DateFormat;
import java.time.chrono.HijrahChronology;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONObject;


import com.example.resturent_app.databinding.ActivityMainBinding;
import com.google.firebase.database.annotations.NotNull;

import com.google.firebase.database.core.Context;
import com.razorpay.Payment;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;



import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;




public class Payment_Gateway_activity<ActivityMainBinding> extends AppCompatActivity implements PaymentResultListener {


    private static final String TAG = Payment_Gateway_activity.class.getSimpleName();
    Button razpbutton,gpayButton;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    private String refValue;
    private String transnote ;



    //Gpay
    private ActivityMainBinding binding;
    String name = "Kaushik Ghosh";
    String UPIid="9775288755@ybl";
    Uri uri;
    String status;
    public static final String GOOGLE_PAY_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";
    int GOOGLE_PAY_REQUEST_CODE = 123;
  
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_payment__gateway_activity);


        razpbutton = findViewById(R.id.RazorpayButton);
        gpayButton = findViewById(R.id.GpayButton);


        Intent u = getIntent();
        final String  val = u.getStringExtra("val");
        final int y = Integer.parseInt(val);
        razpbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPayment(y*100);
            }
        });
        gpayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(Payment_Gateway_activity.this, "GPay gateway Selected", Toast.LENGTH_SHORT).show();
                transnote=String.valueOf(System.currentTimeMillis());
                if(!val.isEmpty())
                {
                    uri = getUPIPUri(name,UPIid,transnote,val);
                    payWithGPay();
                }





            }
        });

    }

    private void payWithGPay() {
        if (isAppInstalled(GOOGLE_PAY_PACKAGE_NAME))
        {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(uri);
                intent.setPackage(GOOGLE_PAY_PACKAGE_NAME);
                startActivityForResult(intent,GOOGLE_PAY_REQUEST_CODE);
        }else
        {
            Toast.makeText(this, "Please Install GPay", Toast.LENGTH_SHORT).show();
        }
    }

    public void onActivityResult(int requestCode,int resultcode,Intent data) {

        super.onActivityResult(requestCode, resultcode, data);
        if(data!=null)
        {
            status = data.getStringExtra("Status").toLowerCase();
        }
        if((RESULT_OK == resultcode) && (status.equals("success")))
        {


            Intent l = getIntent();
            String  Username = l.getStringExtra("Username");
            String item = l.getStringExtra("item");
            String total = l.getStringExtra("val");
            String qt = l.getStringExtra("Quantity:");
            String imageur = l.getStringExtra("Image");
            String address = l.getStringExtra("address");
            String deliownname = l.getStringExtra("ownname");


            mDatabase = FirebaseDatabase.getInstance();
            mRef = mDatabase.getReference();

            Calendar calender = Calendar.getInstance();
            String CurrentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calender.getTime());

            HashMap<String,Object> map= new HashMap<>();


            map.put("UserID",Username);
            map.put("Item",item);
            map.put("Totalamount",total);
            map.put("OrderID",transnote);
            map.put("Quantity",qt);
            map.put("Gateway","GPay Gateway");
            map.put("Date",CurrentDate);
            map.put("Image",imageur);
            map.put("Address",address);
            map.put("Name",deliownname);

            mRef.child("Order").child(Username).push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Log.i("Complete","Successfully Added Data into Firebase");




                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.i("failure","Failure to add data in firebase");
                }
            });

            Intent lok = new Intent(getApplicationContext(),SuccessfulPaymentActivity.class);
            startActivity(lok);




        }else
        {
            Toast.makeText(this, "Transaction Failed", Toast.LENGTH_LONG).show();
        }
    }
    private boolean isAppInstalled(String uri)
    {
        PackageManager pm = getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(uri,PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }catch(PackageManager.NameNotFoundException e)
        {
           Log.d("error:",e.getMessage());
        }

        return app_installed;
    }


    private static Uri getUPIPUri(String name,String upiID,String transNote,String amount)
    {
            return new Uri.Builder()
                    .scheme("upi")
                    .authority("pay")
                    .appendQueryParameter("pa", upiID)
                    .appendQueryParameter("pn", name)


                    .appendQueryParameter("tn", transNote)
                    .appendQueryParameter("am", amount)
                    .appendQueryParameter("cu","INR")
                    .build();
    }

    long refval;
    public void startPayment(float amount)
    {




        /**
         * Instantiate Checkout
         */
        final Checkout checkout = new Checkout();
        Random rand = new Random();


        /**
         * Set your logo here
         */


        refval = System.currentTimeMillis()/1000;
        refValue = String.valueOf(refval);

        /**
         * Reference to current activity
         */
        final Activity activity = this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         *
         */

        try {
            JSONObject options = new JSONObject();

            options.put("name", "Food Grazo");
            options.put("description", "Reference No."+refval);
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            //options.put("order_id", refValue);//from response of step 3.
            //options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("receipt",refValue);
            options.put("amount", amount);//pass amount in currency subunits
            //options.put("prefill.email", "gaurav.kumar@example.com");
            //options.put("prefill.contact","9988776655");

            checkout.open(activity, options);
        } catch(Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }

    }


    @Override
    public void onPaymentSuccess(String s) {
        try {

            Intent l = getIntent();
            String  Username = l.getStringExtra("Username");
            String item = l.getStringExtra("item");
            String total = l.getStringExtra("val");
            String qt = l.getStringExtra("Quantity:");
            String imageur = l.getStringExtra("Image");
            String address = l.getStringExtra("address");
            String deliownname = l.getStringExtra("ownname");


            mDatabase = FirebaseDatabase.getInstance();
            mRef = mDatabase.getReference();

            Calendar calender = Calendar.getInstance();
            String CurrentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calender.getTime());

            HashMap<String,Object> map= new HashMap<>();


            map.put("UserID",Username);
            map.put("Item",item);
            map.put("Totalamount",total);
            map.put("OrderID",refValue);
            map.put("Quantity",qt);
            map.put("Image",imageur);
            map.put("Gateway","Razorpay Gateway");
            map.put("Date",CurrentDate);
            map.put("Address",address);
            map.put("Name",deliownname);



            mRef.child("Order").child(Username).push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Log.i("Complete","Complete");

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.i("failure","Failure");
                }
            });

        }catch (Exception e)
        {
            Toast.makeText(this, "Error in Payment"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        Intent lok = new Intent(getApplicationContext(),SuccessfulPaymentActivity.class);
        startActivity(lok);


    }

    @Override
    public void onPaymentError(int i, String s) {

        try {
            Toast.makeText(this,s.toString(), Toast.LENGTH_LONG).show();
        }catch(Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
}