package com.example.shubhang.ecommerce;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    Button signUp;
    EditText userLogin,userPassword,phoneNumber,Nationality;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        signUp = findViewById(R.id.login);
        userLogin = findViewById(R.id.user_login);
        userPassword = findViewById(R.id.user_password);
        phoneNumber= findViewById(R.id.phone_number);
        Nationality = findViewById(R.id.nationality);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Register();
                Toast.makeText(RegisterActivity.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                goToAccount();
            }
        });
    }

    public void Register(){
        final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = userLogin.getText().toString();
                String password = userPassword.getText().toString();
                String phone_no = phoneNumber.getText().toString();
                String nationality = Nationality.getText().toString();
                DatabaseReference userReference = dbRef.child(username).push();
                Log.d("dbref",userReference.toString());
                userReference.child("password").push().setValue(password);
                userReference.child("phone_no").push().setValue(phone_no);
                userReference.child("nationality").push().setValue(nationality);
            }
        });
    }

    private void goToAccount() {
        Intent intent  = new Intent(RegisterActivity.this,FilterActivity.class);
        startActivity(intent);
    }

}
