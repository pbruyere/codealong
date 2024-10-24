package com.example.codealong;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class RegisterActivity extends AbstractLoginActivity {

    @Override
    protected void MyOnClick() {
        //ISSUES WITH SNACKBAR BEHIND ONSCREEN KEYBOARD - ATTEMPTED FIX(s), FAILED
        //Snackbar.make(findViewById(R.id.myCoordinatorLayout), "oncomplete!", Snackbar.LENGTH_SHORT).show();
        String _szEmail = m_txtEmail.getText().toString();
        String _szPassword = m_txtPassword.getText().toString();

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

    @Override
    protected void MyOnCreate() {
        m_btnAction.setText("Register Account");
        String txtPW = m_txtPassword.getHint().toString();
        txtPW += " (6 minimum)";
        m_txtPassword.setHint(txtPW);
    }
}