package food.taim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ButtonBarLayout;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ProfileActivity extends AppCompatActivity{

    TextView profileName;
    DatabaseReference reff;
    FirebaseAuth mAuth;
    private Button updateBtn;
    private TextView locationProfile;
    private TextView bDateProfile;
    private TextView infoProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        profileName = (TextView) findViewById(R.id.nameProfile);
        updateBtn = (Button) findViewById(R.id.btnEdit);
        locationProfile = (TextView) findViewById(R.id.locationProfile);
        bDateProfile = (TextView) findViewById(R.id.birthdateProfile);
        infoProfile = (TextView) findViewById(R.id.infoProfile);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this,EditProfileActivity.class));
                finish();
            }
        });
        mAuth = FirebaseAuth.getInstance();
        reff = FirebaseDatabase.getInstance().getReference().child("MUsers").child(mAuth.getUid().toString());
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String nameProfile = dataSnapshot.child("firstname").getValue().toString();
                nameProfile += " " + dataSnapshot.child("lastname").getValue().toString();
                String location = dataSnapshot.child("location").getValue().toString();
                String bdated = dataSnapshot.child("birthdate").getValue().toString();
                String moreInfo = dataSnapshot.child("moreinfo").getKey().toString();
                profileName.setText(nameProfile);
                locationProfile.setText(location);
                bDateProfile.setText(bdated);
                infoProfile.setText(moreInfo);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}