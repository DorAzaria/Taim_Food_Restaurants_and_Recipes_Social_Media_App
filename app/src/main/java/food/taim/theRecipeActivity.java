package food.taim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class theRecipeActivity extends AppCompatActivity {

    private TextView mainTitle, ownerName, foodType;
    private TextView prop1, prop2, prop3, prop4, prop5;
    private TextView description, likes, views, comments;
    SliderView sliderView;
    SliderAdapter adapter;
    DatabaseReference reff;
    FirebaseAuth mAuth;
    LinearLayout container2, container3;
    int toggleViews =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_recipe);

        mainTitle = (TextView) findViewById(R.id.recipeName);
        ownerName = (TextView) findViewById(R.id.ownerRecipe);
        foodType = (TextView) findViewById(R.id.foodTypeRecipe);
        prop1 = (TextView) findViewById(R.id.prop1);
        prop2 = (TextView) findViewById(R.id.prop2);
        prop3 = (TextView) findViewById(R.id.prop3);
        prop4 = (TextView) findViewById(R.id.prop4);
        prop5 = (TextView) findViewById(R.id.prop5);
        description = (TextView) findViewById(R.id.recipeDescription);
        likes = (TextView) findViewById(R.id.likesRecipe);
        views = (TextView) findViewById(R.id.viewsRecipe);
        comments = (TextView) findViewById(R.id.commentsRecipe);
        sliderView=findViewById(R.id.pictureSlideRecipe);
        container2 = (LinearLayout) findViewById(R.id.container2);
        container3 = (LinearLayout) findViewById(R.id.container3);

        ///Slider Pictures
        adapter=new SliderAdapter(this);
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(8); //set scroll delay in seconds :
        sliderView.startAutoCycle();
        sliderView.setOnIndicatorClickListener(new DrawController.ClickListener() {
            @Override
            public void onIndicatorClicked(int position) {
                sliderView.setCurrentPagePosition(position);
            }
        });


        //Firebase Data
        reff = FirebaseDatabase.getInstance().getReference().child("MRecipes").child("g0P6lIoNYwPNNVTf6XYe6noil4l1").child("-M6neyxHtfGmeIOqcIVj");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(toggleViews == 0) {
                    String previousScore = dataSnapshot.child("views").getValue(String.class);
                    if (previousScore != null) {
                        int finalScore = Integer.parseInt(previousScore) + 1;
                        reff.child("views").setValue(String.valueOf(finalScore));
                        toggleViews = 1;
                    }
                }
                String ownerName2 = dataSnapshot.child("recipeBy").getValue().toString();
                String recipeName = dataSnapshot.child("recipeName").getValue().toString();
                String miniDetails = dataSnapshot.child("miniDetails").getValue().toString();
                String isKosher = dataSnapshot.child("isKosher").getValue().toString();
                String foodType2 = dataSnapshot.child("foodType").getValue().toString();
                String checkCheese = dataSnapshot.child("checkBoxes").child("cheese").getKey().toString();
                String checkMeat = dataSnapshot.child("checkBoxes").child("meat").getKey().toString();
                String checkGluten = dataSnapshot.child("checkBoxes").child("glutenFree").getKey().toString();
                String checkGreen = dataSnapshot.child("checkBoxes").child("green").getKey().toString();
                String checkVegan = dataSnapshot.child("checkBoxes").child("vegan").getKey().toString();
                String countViews = dataSnapshot.child("views").getValue().toString();
                ///
                ownerName.setText(ownerName2);
                mainTitle.setText(recipeName);
                foodType.setText(foodType2);
                description.setText(miniDetails);
                views.setText(countViews);

                addAllIngredients();
                listAllIngredients();
                addAllSteps();
                listAllSteps();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void addAllIngredients(){
        reff.child("ingredients").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    LayoutInflater layoutInflater =
                            (LayoutInflater) theRecipeActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View addView = layoutInflater.inflate(R.layout.ingredients_list, null);
                    View thisChild = container2.getChildAt(Integer.valueOf(snapshot.getKey().toString()));
                    TextView textOut = (TextView)addView.findViewById(R.id.ingredientName);
                    textOut.setText(snapshot.child("ingredient").getValue().toString());
                    TextView textOut2 = (TextView)addView.findViewById(R.id.ingredientSum);
                    textOut2.setText(snapshot.child("sum").getValue().toString());
                    container2.addView(addView);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    private void listAllIngredients(){

        int childCount = container2.getChildCount();
        for(int i=1; i<childCount; i++){
            View thisChild = container2.getChildAt(i);
        }
    }

    private void addAllSteps(){
        reff.child("theRecipe").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    LayoutInflater layoutInflater2 =
                            (LayoutInflater) theRecipeActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View addView = layoutInflater2.inflate(R.layout.recipe_steps, null);
                    View thisChild2 = container3.getChildAt(Integer.valueOf(snapshot.getKey().toString()));
                    TextView textOut3 = (TextView)addView.findViewById(R.id.recipeSteps2);
                    textOut3.setText(snapshot.getKey().toString());
                    TextView textOut4 = (TextView)addView.findViewById(R.id.textoutStep2);
                    textOut4.setText(snapshot.getValue().toString());
                    container3.addView(addView);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    private void listAllSteps(){

        int childCount = container3.getChildCount();
        for(int i=1; i<childCount; i++){
            View thisChild2 = container3.getChildAt(i);
        }
    }
}
