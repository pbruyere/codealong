package com.example.codealong;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public abstract class AbstractLoginActivity extends AppCompatActivity
{
    protected EditText m_txtEmail;
    protected EditText m_txtPassword;
    protected Button m_btnAction;
    protected FirebaseAuth m_fbAuth;
    protected TextView m_txtErrorOutput;

    protected abstract void MyOnClick();
    protected abstract void MyOnCreate();

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

        // set all members
        m_txtEmail = findViewById(R.id.txtRegisterEmail);
        m_txtPassword = findViewById(R.id.txtRegisterPassword);
        m_btnAction = findViewById(R.id.btnAction);
        m_fbAuth = FirebaseAuth.getInstance();
        m_txtErrorOutput = findViewById(R.id.txtErrorOutput);

        // init stuff
        m_btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyOnClick();
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

        MyOnCreate();
    }

    private void CheckUserInput()
    {
        String szResult = szGeneral.szGeneral.ValidateInput(m_txtEmail.getText().toString(), m_txtPassword.getText().toString());
        //if (szResult.equals("")) // == does pointer comparison
        m_btnAction.setEnabled(szResult.isEmpty());
        m_txtErrorOutput.setText(szResult);
    }
}
