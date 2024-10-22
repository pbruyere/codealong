package com.example.codealong;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private EditText m_txtEmail;
    private EditText m_txtPassword;
    private Button m_btnRegister;
    private FirebaseAuth m_fbAuth;
    private TextView m_txtErrorOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // set all members
        m_txtEmail = findViewById(R.id.txtRegisterEmail);
        m_txtPassword = findViewById(R.id.txtRegisterPassword);
        m_btnRegister = findViewById(R.id.btnRegister);
        m_fbAuth = FirebaseAuth.getInstance();
        m_txtErrorOutput = findViewById(R.id.txtErrorOutput);

        // init stuff
        m_btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String szEmail = m_txtEmail.getText().toString();
                String szPassword = m_txtPassword.getText().toString();

                // impossible now with my self-implemented button disabling based on the below requirements
                /*if (TextUtils.isEmpty(szEmail) || TextUtils.isEmpty(szPassword)) {
                    Toast.makeText(RegisterActivity.this, "credentials cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (szPassword.length() < 6) {
                    Toast.makeText(RegisterActivity.this, "password too short", Toast.LENGTH_SHORT).show();
                    return;
                }*/

                // register can be attempted now
                registerUser(szEmail, szPassword);
            }
        });

        TextWatcher tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                CheckUserInput();
            }
        };
        m_txtPassword.addTextChangedListener(tw);
        m_txtEmail.addTextChangedListener(tw);
    }

    private void registerUser(String _szEmail, String _szPassword) {
        Snackbar.make(findViewById(R.id.myCoordinatorLayout), "oncomplete!", Snackbar.LENGTH_SHORT).show();

        m_fbAuth.createUserWithEmailAndPassword(_szEmail, _szPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    //https://www.youtube.com/watch?v=SAuGoHmFwTM
                    //https://developer.android.com/develop/ui/views/notifications/snackbar/showing
                    //lets try snackbar instead of toast!
                    Toast.makeText(RegisterActivity.this, "registered successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                {
                    //for each (var e in task.Exception.Flatten().InnerExceptions)
                        //Debug.LogWarning($"Received Exception: {e.Message}");
                    Toast.makeText(RegisterActivity.this, task.getException().getMessage().toString(), Toast.LENGTH_LONG).show();
                    //Snackbar.make(findViewById(R.id.myCoordinatorLayout), task.getException().getMessage().toString(), Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void CheckUserInput()
    {
        String szResult = szGeneral.szGeneral.ValidateInput(m_txtEmail.getText().toString(), m_txtPassword.getText().toString());
        //if (szResult.equals("")) // == does pointer comparison
        m_btnRegister.setHint(szResult);
        m_btnRegister.setEnabled(szResult.isEmpty());
        m_txtErrorOutput.setText(szResult);
    }
}