package com.example.resturent_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONObject;



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



public class Payment_Gateway_activity extends AppCompatActivity implements PaymentResultListener {


    private static final String TAG = Payment_Gateway_activity.class.getSimpleName();
    Button razpbutton,gpayButton,payubutton;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    private String refValue="";
  
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_payment__gateway_activity);


        razpbutton = findViewById(R.id.RazorpayButton);
        gpayButton = findViewById(R.id.GpayButton);
        payubutton = findViewById(R.id.Payubutton);

        Intent u = getIntent();
        String  val = u.getStringExtra("val");
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
                Toast.makeText(Payment_Gateway_activity.this, "GPay gateway Selected", Toast.LENGTH_SHORT).show();
            }
        });
        payubutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Payment_Gateway_activity.this, "Payu gateway Selected", Toast.LENGTH_SHORT).show();
            }
        });

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
            Toast.makeText(this, "Payment Successful", Toast.LENGTH_LONG).show();
            Intent l = getIntent();
            String  Username = l.getStringExtra("Username");
            String item = l.getStringExtra("item");
            String total = l.getStringExtra("val");
            String qt = l.getStringExtra("Quantity:");


            mDatabase = FirebaseDatabase.getInstance();
            mRef = mDatabase.getReference();

            Calendar calender = Calendar.getInstance();
            String CurrentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calender.getTime());

            HashMap<String,Object> map= new HashMap<>();


            map.put("Username:",Username);
            map.put("Item",item);
            map.put("Total Amount",total);
            map.put("OrderID:",refValue);
            map.put("Quantity",qt);
            map.put("Payment Done By:","Razorpay Gateway");
            map.put("Date:",CurrentDate);

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

        Intent df = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(df);
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