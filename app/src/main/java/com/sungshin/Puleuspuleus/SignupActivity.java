package com.sungshin.Puleuspuleus;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.io.File;

public class SignupActivity extends AppCompatActivity {
    private EditText editTextEmail, editTextPassword, editTextName, editTextID, editTextCP;
    private Button buttonJoin;
    private FirebaseAuth mAuth;

    long time = 0;

    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis()-time>=1000) {
            time=System.currentTimeMillis();
            Toast.makeText(getApplicationContext(),"뒤로 가기 버튼을 한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
        else if(System.currentTimeMillis()-time<1000){ // 뒤로 가기 한번 더 눌렀을때의 시간간격 텀이 1초
            finishAffinity();
            System.runFinalization();
            System.exit(0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        editTextEmail = (EditText) findViewById(R.id.editText_email);
        editTextPassword = (EditText) findViewById(R.id.editText_passWord);
        editTextName = (EditText) findViewById(R.id.editText_name);
        editTextID = (EditText) findViewById(R.id.editText_id);
        editTextCP = (EditText) findViewById(R.id.editText_cpassWord);

        buttonJoin = (Button) findViewById(R.id.btn_join);

        buttonJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editTextEmail.getText().toString().equals("") && !editTextPassword.getText().toString().equals("")) {
                    // 이메일과 비밀번호가 공백이 아닌 경우
                    //createUser(editTextEmail.getText().toString(), editTextPassword.getText().toString(), editTextName.getText().toString(), editTextID.getText().toString());
                    attemptJoin();
                } else {
                    // 이메일과 비밀번호가 공백인 경우
                    Toast.makeText(SignupActivity.this, "계정과 비밀번호를 입력하세요.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void attemptJoin(){

        String name = editTextName.getText().toString();
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        String cPassword = editTextCP.getText().toString();
        String ID = editTextID.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if(password.isEmpty()){
            editTextPassword.setError("비밀번호를 입력해주세요.");
            focusView = editTextPassword;
            cancel = true;
        } else if(!isPasswordValid(password)){
            editTextPassword.setError("6자 이상의 비밀번호를 입력해주세요.");
            focusView = editTextPassword;
            cancel = true;
        }

        if(!password.equals(cPassword)){
            editTextCP.setError("동일한 비밀번호를 입력해주세요.");
            focusView = editTextCP;
            cancel = true;
        }

        // 이메일의 유효성 검사
        if(email.isEmpty()){
            editTextEmail.setError("이메일을 입력해주세요.");
            focusView = editTextEmail;
            cancel = true;
        } else if(!isEmailValid(email)){
            editTextEmail.setError("@를 포함한 유효한 이메일을 입력해주세요.");
            focusView = editTextEmail;
            cancel = true;
        }

        // 이름의 유효성 검사
        if(name.isEmpty()){
            editTextName.setError("이름을 입력해주세요.");
            focusView = editTextName;
            cancel = true;
        }

        //id
        if(ID.isEmpty()){
            editTextID.setError("아이디를 입력해주세요.");
            focusView = editTextID;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            createUser(email, password);
        }
    }

    private void createUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // 회원가입 성공시
                            Toast.makeText(SignupActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            // 계정이 중복된 경우
                            Toast.makeText(SignupActivity.this, "이미 존재하는 계정입니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 6;
    }




}
