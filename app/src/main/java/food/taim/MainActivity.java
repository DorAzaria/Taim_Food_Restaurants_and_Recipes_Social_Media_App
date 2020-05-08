package food.taim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText emailID, passwordID;
    private Button signInBtn;
    private Button registerBtn;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseDatabase database;
    private  FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailID = (EditText) findViewById(R.id.emailLogin);
        passwordID = (EditText) findViewById(R.id.passwordLogin);
        signInBtn = (Button) findViewById(R.id.btnLogin);
        registerBtn = (Button) findViewById(R.id.btnReg);
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                mUser = firebaseAuth.getCurrentUser();

                if (mUser != null) {
                    Toast.makeText(MainActivity.this, "ברוכים הבאים", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(MainActivity.this,splashActivity.class));
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "ההתחברות נכשלה", Toast.LENGTH_LONG).show();
                }
            }
        };

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,RegisterActivity.class));
                finish();
            }
        });

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!TextUtils.isEmpty(emailID.getText().toString())
                        && !TextUtils.isEmpty(passwordID.getText().toString())) {
                    String email = emailID.getText().toString();
                    String password = passwordID.getText().toString();   
                    login(email,password);
                } else {

                }
            }
        });
    }

    private void login(String email, String password) {
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "התחבר בהצלחה, בתיאבון", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(MainActivity.this,HomeActivity.class));
                    }else {

                    }
                }
            });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if(mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
