package sp.com.TogetherSG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.content.Intent;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import android.os.Bundle;
import android.widget.TimePicker;
import android.widget.Toast;

public class login extends AppCompatActivity implements View.OnClickListener{
    private TextView register, forgotPassword;
    private EditText editTextEmail, editTextPassword;
    private Button logIn;
    private ProgressBar progressBar;
    private CallbackManager mCallbackManager;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private AccessTokenTracker accessTokenTracker;
    private LoginButton fbButton;
    private static final String TAG = "FacebookAuthentication";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mFirebaseAuth = FirebaseAuth.getInstance();
        FacebookSdk.sdkInitialize(getApplicationContext());
        register = findViewById(R.id.textView3);
        register.setOnClickListener(this);
        forgotPassword = findViewById(R.id.textView2);
        forgotPassword.setOnClickListener(this);
        logIn = findViewById(R.id.normallogin);
        logIn.setOnClickListener(this);
        fbButton =findViewById(R.id.login_button);
        fbButton.setReadPermissions("email", "public_profile");
        mCallbackManager = CallbackManager.Factory.create();

        editTextEmail = findViewById(R.id.editTextTextEmailAddress);
        editTextPassword= findViewById(R.id.editTextTextPassword);
        progressBar = findViewById(R.id.progressBar2);

        fbButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "onSuccess" + loginResult);
                handleFacebookToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "onError" + error);
            }
        });

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user!=null){
                    updateUI(user);
                }
                else {
                    updateUI(null);
                }
            }
        };

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if(currentAccessToken ==null){
                    mFirebaseAuth.signOut();
                }
            }
        };
    }

    private void handleFacebookToken(AccessToken token){
        Log.d(TAG, "handleFacebookToken" + token);
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mFirebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d(TAG, "sign in with credential: successful");
                    FirebaseUser user = mFirebaseAuth.getCurrentUser();
                    startActivity(new Intent(login.this, ActivitiesList.class));
                    updateUI(user);
                }else {
                    Log.d(TAG, "sign in with credential: unsuccessful", task.getException());
                    Toast.makeText(login.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void updateUI(FirebaseUser user){
        if(user != null){

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(authStateListener != null){
            mFirebaseAuth.removeAuthStateListener(authStateListener);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textView3:
                startActivity(new Intent(this, SignUp.class));
                break;
            case R.id.normallogin:
                UserLogin();
                break;
            case R.id.textView2:
                startActivity(new Intent(this, ForgetPassword.class));
                break;
        }
    }

    private void UserLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty()){
            editTextEmail.setError("Email is required!");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please enter a valid email!");
            editTextEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editTextPassword.setError("Password is required!");
            editTextPassword.requestFocus();
            return;
        }

        if(password.length() <6){
            editTextPassword.setError("Min password length is 6 characters!");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                   FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if(user.isEmailVerified()) {
                        //redirect to user profile
                        startActivity(new Intent(login.this, ActivitiesList.class));
                    }else {
                        user.sendEmailVerification();
                        Toast.makeText(login.this, "Check your email to verify your account!", Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(login.this, "Failed to login! Please check your credentials.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}