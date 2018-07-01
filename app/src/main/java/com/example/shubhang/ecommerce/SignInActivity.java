package com.example.shubhang.ecommerce;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignInActivity extends AppCompatActivity {

    private static final String EMAIL = "email";

    public GoogleSignInClient mGoogleSignInClient;
    private int RC_SIGN_IN = 123;
    SignInButton SignIn;
    EditText userLogin,userPassword;
    Button userSignIn,skip,register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        SignIn = findViewById(R.id.sign_in_button);
        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.sign_in_button:
                        signIn();
                        break;
                    // ...
                }
            }
        });

        userLogin = findViewById(R.id.user_login);
        userPassword = findViewById(R.id.user_password);
        skip = findViewById(R.id.btn_skip);
        register = findViewById(R.id.btn_register);
        userSignIn = findViewById(R.id.login);

        userSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Login();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToRegister();
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToAccount();
            }
        });

    }

    private void goToRegister() {
        Intent register_intent = new Intent(SignInActivity.this,RegisterActivity.class);
        startActivity(register_intent);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
        Toast.makeText(this, "SignIn Successful", Toast.LENGTH_SHORT).show();
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
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            goToAccount();
            // Signed in successfully, show authenticated UI.
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information
        }
    }

    private void goToAccount() {
        Intent intent  = new Intent(SignInActivity.this,FilterActivity.class);
        startActivity(intent);
    }

    public void Login(){
        final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();

        userSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = userLogin.getText().toString();
                String password = userPassword.getText().toString();
                if(dbRef.child("users") != null && dbRef.child("users").child(username) != null){
                    if(dbRef.child("users").child(username).child("password").getKey().equals(password)){
                        goToAccount();
                        Toast.makeText(SignInActivity.this, "Successfully Signed In", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(SignInActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                else
                    return;
            }
        });
    }
}
