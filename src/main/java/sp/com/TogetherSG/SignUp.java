package sp.com.TogetherSG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth mAuth;
    private EditText FullName;
    private EditText Email;
    private EditText Password;
    private Button Register;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        FullName = findViewById(R.id.full_name);
        Email = findViewById(R.id.email);
        Password = findViewById(R.id.password);
        Register = findViewById(R.id.button_register);
        Register.setOnClickListener(this);
        progressBar = findViewById(R.id.progressBar);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_register:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        String email= Email.getText().toString().trim();
        String fullname= FullName.getText().toString().trim();
        String password= Password.getText().toString().trim();

        if (fullname.isEmpty()){
            FullName.setError("Full name is required!");
            FullName.requestFocus();
            return;
        }

        if (email.isEmpty()){
            Email.setError("Email Address is required!");
            Email.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Email.setError("Please provide a valid email!");
            Email.requestFocus();
            return;
        }

        if (password.isEmpty()){
            Password.setError("Password is required!");
            Password.requestFocus();
            return;
        }

        if(password.length() < 6){
            Password.setError("Min password length should be 6 character!");
            Password.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            User user = new User(fullname, email);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(SignUp.this, "User has been registered successfully!", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);

                                        //redirect to login layout
                                        startActivity(new Intent(SignUp.this, login.class));
                                    }else{
                                        Toast.makeText(SignUp.this, "Failed to register! Try Again!", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }else {
                            Toast.makeText(SignUp.this, "Failed to register!", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }

}