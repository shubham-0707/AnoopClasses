package android.example.anoopclassesproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signup extends AppCompatActivity {

    EditText mFullName, mPassword, mEmail, mPhoneNo;
    Button signUp;
    TextView mLoginBtn;
    FirebaseAuth fAuth;
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mFullName = findViewById(R.id.fullName);
        mPassword = findViewById(R.id.editPassword);
        mEmail = findViewById(R.id.editEmail);
        mPhoneNo = findViewById(R.id.editPhone);
        signUp = findViewById(R.id.loginButton);
        mLoginBtn = findViewById(R.id.AloginButton);
        mProgressBar = findViewById(R.id.progressBar);

        fAuth = FirebaseAuth.getInstance();


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getText().toString().trim();
                String pass = mPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(pass)) {
                    mPassword.setError("Password is required");
                    return;
                }

                if (pass.length() <= 7) {
                    mPassword.setError("Password must be at least 8 characters long");
                    return;
                }
                mProgressBar.setVisibility(view.VISIBLE);

                fAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Successfully signed up!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(signup.this, login.class));
                            mProgressBar.setVisibility(view.INVISIBLE);
                        } else {
                            Toast.makeText(signup.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            mProgressBar.setVisibility(view.INVISIBLE);
                        }
                        if (fAuth.getCurrentUser() != null) {
                            Toast.makeText(signup.this, "User is already registered ,Login Now...", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(signup.this, login.class));
                            finish();
                        }
                    }
                });
            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(signup.this, login.class));
            }
        });
    }
}