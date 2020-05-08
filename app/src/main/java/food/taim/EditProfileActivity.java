package food.taim;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditProfileActivity extends AppCompatActivity {
    private EditText cityEdit;
    private EditText dateEdit;
    private EditText infoEdit;
    private EditText firstName;
    private EditText lastName;
    private Button updateEditBtn;
    private DatabaseReference mDatabaseReference;
    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        cityEdit = (EditText) findViewById(R.id.cityEdit);
        dateEdit = (EditText) findViewById(R.id.dateEdit);
        infoEdit = (EditText) findViewById(R.id.infoEdit);
        firstName = (EditText) findViewById(R.id.firstNameEdit);
        lastName = (EditText) findViewById(R.id.lastNameEdit);
        updateEditBtn = (Button) findViewById(R.id.updateProfileBtn);


        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference().child("MUsers");
        mAuth = FirebaseAuth.getInstance();

        mProgressDialog = new ProgressDialog(this);

        updateEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });

    }

    private void updateProfile() {

        final String firstNameString = firstName.getText().toString().trim();
        final String lastNameString = lastName.getText().toString().trim();
        final String cityString = cityEdit.getText().toString().trim();
        final String dateString = dateEdit.getText().toString().trim();
        final String infoString = infoEdit.getText().toString().trim();


            mProgressDialog.setMessage("מעדכן פרופיל...");
            mProgressDialog.show();

                        String  userid = mAuth.getCurrentUser().getUid();
                        DatabaseReference currentUserDb = mDatabaseReference.child(userid);

                        if(!firstNameString.isEmpty())
                        currentUserDb.child("firstname").setValue(firstNameString);
                        if(!lastNameString.isEmpty())
                        currentUserDb.child("lastname").setValue(lastNameString);
                        if(!cityString.isEmpty())
                        currentUserDb.child("location").setValue(cityString);
                        if(!dateString.isEmpty())
                        currentUserDb.child("birthdate").setValue(dateString);
                        if(!infoString.isEmpty())
                        currentUserDb.child("moreinfo").setValue(infoString);

                        mProgressDialog.dismiss();

                        //send users to profile

                        Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }

}
