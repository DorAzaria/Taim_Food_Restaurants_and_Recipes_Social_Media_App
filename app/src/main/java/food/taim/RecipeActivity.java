package food.taim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;


import android.animation.LayoutTransition;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RecipeActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {

    String[] foodType = { "בחר סוג מאכל", "עוף", "אסיאתי", "פסטות", "אורז וממולאים", "לחם ומאפים", "עוגות ועוגיות", "קינוחים", "בשרים","רטבים וממרחים", "מרקים","טבעוני" ,"שמירה על הגזרה"};

    private Button newIngredientsBtn;
    private Button newDetailsBtn;
    private Button newCameraBtn;
    private Button newPhotoBtn, uploadRecipeBtn;
    private EditText mainTitle, shortDetails;
    private DatabaseReference mDatabaseReference , mDatabaseReferenceUser;
    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;
    private CheckBox cB1,cB2,cB3,cB4,cB5;
    private RadioButton isKohser, isntKosher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        newIngredientsBtn= findViewById(R.id.write_new_ingredients_btn);
        newDetailsBtn = findViewById(R.id.write_new_recipe_btnn);
        newCameraBtn = findViewById(R.id.new_video_btn);
        newPhotoBtn = findViewById(R.id.new_image_btn);
        uploadRecipeBtn = findViewById(R.id.new_upload_recipe);
        mainTitle = findViewById(R.id.recipe_title_editor);
        shortDetails = findViewById(R.id.recipe_title_short);
        cB1 = findViewById(R.id.checkbox_meat);
        cB2 = findViewById(R.id.checkbox_cheese);
        cB3 = findViewById(R.id.checkbox_vegan);
        cB4 = findViewById(R.id.checkbox_green);
        cB5 = findViewById(R.id.checkbox_no_gluten);
        isKohser = findViewById(R.id.yesKosherOption);
        isntKosher = findViewById(R.id.noKosherOption);

        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference().child("MRecipes");
        mAuth = FirebaseAuth.getInstance();
        mDatabaseReferenceUser = FirebaseDatabase.getInstance().getReference().child("MUsers").child(mAuth.getUid().toString());
        String  userid = mAuth.getCurrentUser().getUid();
        final DatabaseReference currentUserDb = mDatabaseReference.child(userid).push();
        final String key = currentUserDb.getKey().toString();
        final Bundle bundle = new Bundle();
        bundle.putString("key",key);

        //////////////// WRITE Ingredients SECTION
        newIngredientsBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DialogFragment dialog = NewIngredients.newInstance();
                ((NewIngredients) dialog).setCallback(new NewIngredients.Callback() {
                    @Override
                    public void onActionClick(String name) {
                        Toast.makeText(RecipeActivity.this, name, Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.setArguments(bundle);
                dialog.show(getSupportFragmentManager(), "tag");

            }

        });

        ///////////////// WRITE RECIPE SECTION
        newDetailsBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DialogFragment dialog = NewRecipeDetails.newInstance();
                ((NewRecipeDetails) dialog).setCallback(new NewRecipeDetails.Callback() {
                    @Override
                    public void onActionClick(String name) {
                        Toast.makeText(RecipeActivity.this, name, Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.setArguments(bundle);
                dialog.show(getSupportFragmentManager(), "tag");
            }

        });

        newCameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialog = NewShareVideo.newInstance();
                ((NewShareVideo) dialog).setCallback(new NewShareVideo.Callback() {
                    @Override
                    public void onActionClick(String name) {
                        Toast.makeText(RecipeActivity.this, name, Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.show(getSupportFragmentManager(), "tag");

            }
        });

        newPhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialog = NewPhotoUpload.newInstance();
                ((NewPhotoUpload) dialog).setCallback(new NewPhotoUpload.Callback() {
                    @Override
                    public void onActionClick(String name) {
                        Toast.makeText(RecipeActivity.this, name, Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.setArguments(bundle);
                dialog.show(getSupportFragmentManager(), "tag");

            }
        });



        Spinner spin = (Spinner) findViewById(R.id.spinnerRecipeType);
        spin.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,foodType);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentUserDb.child("foodType").setValue(parent.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        uploadRecipeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadRecipe(currentUserDb,key);
            }
        });
    }


    private void uploadRecipe(final DatabaseReference currentUserDb,final String key) {

        final String mainTitleString = mainTitle.getText().toString().trim();
        final String shortDetailsString = shortDetails.getText().toString().trim();

        if(!mainTitleString.isEmpty())
            currentUserDb.child("recipeName").setValue(mainTitleString);
        if(!shortDetailsString.isEmpty())
            currentUserDb.child("miniDetails").setValue(shortDetailsString);
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        /// CHECK BOXES
            if(cB1.isChecked()) {
                currentUserDb.child("checkBoxes").child("meat").setValue("true");
            }else{
                currentUserDb.child("checkBoxes").child("meat").setValue("false");
            }
            if(cB2.isChecked()) {
            currentUserDb.child("checkBoxes").child("cheese").setValue("true");
            }else{
                currentUserDb.child("checkBoxes").child("cheese").setValue("false");
            }
             if(cB3.isChecked()) {
            currentUserDb.child("checkBoxes").child("vegan").setValue("true");
             }else{
                 currentUserDb.child("checkBoxes").child("vegan").setValue("false");
             }
            if(cB4.isChecked()) {
            currentUserDb.child("checkBoxes").child("green").setValue("true");
            }else{
                currentUserDb.child("checkBoxes").child("green").setValue("false");
            }
            if(cB5.isChecked()) {
            currentUserDb.child("checkBoxes").child("glutenFree").setValue("true");
            }else{
                currentUserDb.child("checkBoxes").child("glutenFree").setValue("false");
            }

            // RADIO BUTTON - IS KOSHER?
            if(isKohser.isChecked()) {
            currentUserDb.child("isKosher").setValue("true");
            }
            if(isntKosher.isChecked()) {
            currentUserDb.child("isKosher").setValue("false");
            }
            if(!isKohser.isChecked() && !isntKosher.isChecked()){
                currentUserDb.child("isKosher").setValue("unknown");
            }
            currentUserDb.child("views").setValue("0");

            //// User data types (full name)
        mDatabaseReferenceUser.addValueEventListener(new ValueEventListener() {
                                                         @Override
                                                         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                             String nameProfile = dataSnapshot.child("firstname").getValue().toString();
                                                             nameProfile += " " + dataSnapshot.child("lastname").getValue().toString();
                                                             currentUserDb.child("recipeBy").setValue(nameProfile);
                                                         }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //send users to profile
        Intent intent = new Intent(RecipeActivity.this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        foodType[0] = "אוכל טרנדי";
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}
