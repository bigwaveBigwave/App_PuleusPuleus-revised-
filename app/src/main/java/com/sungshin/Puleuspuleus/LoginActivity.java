package com.sungshin.Puleuspuleus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    TextInputEditText eID, ePW;
    Button button_login;
    TextView join;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        eID = (TextInputEditText)findViewById(R.id.ed_id);
        ePW = (TextInputEditText)findViewById(R.id.ed_pw);
        button_login = (Button) findViewById(R.id.button_login);
        join = (TextView) findViewById(R.id.member1);
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SignupActivity.class);
                startActivity(intent);
            }
        });

    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            reload();
        }
    }
    // [END on_start_check_user]

    private void attemptLogin() {
        eID.setError(null);
        ePW.setError(null);

        String email = eID.getText().toString().trim();
        String password = ePW.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // 패스워드의 유효성 검사
        if (password.isEmpty()) {
            ePW.setError("비밀번호를 입력해주세요.");
            focusView = ePW;
            cancel = true;
        } else if (!isPasswordValid(password)) {
            ePW.setError("6자 이상의 비밀번호를 입력해주세요.");
            focusView = ePW;
            cancel = true;
        }

        // 이메일의 유효성 검사
        if (email.isEmpty()) {
            eID.setError("이메일을 입력해주세요.");
            focusView = eID;
            cancel = true;
        } else if (!isEmailValid(email)) {
            eID.setError("@를 포함한 유효한 이메일을 입력해주세요.");
            focusView = eID;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            signIn(email, password);
        }
    }

    private void signIn(String email, String password) {
        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:successful");
                            Toast.makeText(LoginActivity.this, "로그인에 성공하였습니다.",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            Intent intent = new Intent(getApplicationContext(),MainActivity_Movie.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "아이디나 비밀번호가 틀렸습니다.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
        // [END sign_in_with_email]
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }
    private boolean isPasswordValid(String password) {
        return password.length() >= 6;
    }

    private void reload() { }

    private void updateUI(FirebaseUser user) {

    }
}