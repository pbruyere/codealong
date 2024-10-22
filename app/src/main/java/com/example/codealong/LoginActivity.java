package com.example.codealong;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText m_txtEmail;
    private EditText m_txtPassword;
    private Button m_btnLogin;
    private FirebaseAuth m_fbAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        m_txtEmail = findViewById(R.id.txtEmail);
        m_txtPassword = findViewById(R.id.txtPassword);
        m_btnLogin = findViewById(R.id.btnLogin);
        m_fbAuth = FirebaseAuth.getInstance();

        m_btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String szEmail = m_txtEmail.getText().toString();
                String szPassword = m_txtPassword.getText().toString();
                DoLogin(szEmail, szPassword);
            }
        });
    }

    // test@email.com / password
    private void DoLogin(String _szEmail, String _szPassword) {
        m_fbAuth.signInWithEmailAndPassword(_szEmail, _szPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    Toast.makeText(LoginActivity.this, "login successful!", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                {
                    //for each (var e in task.Exception.Flatten().InnerExceptions)
                    //Debug.LogWarning($"Received Exception: {e.Message}");
                    Toast.makeText(LoginActivity.this, task.getException().getMessage().toString(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}