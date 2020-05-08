package food.taim;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class NewRecipeDetails extends DialogFragment implements View.OnClickListener {


    private Callback callback;
    TextInputLayout textIn;
    Button buttonAdd;
    LinearLayout container2;
    TextView countSteps;
    private String recipeKey;
    private DatabaseReference mDatabaseReference;
    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;

    static NewRecipeDetails newInstance() {
        return new NewRecipeDetails();
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullscreenDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_recipe_write, container, false);
        ImageButton close = view.findViewById(R.id.fullscreen_dialog_close2);
        TextView action = view.findViewById(R.id.updateRecipeDetails);

        textIn = (TextInputLayout)view.findViewById(R.id.textinStep);
        buttonAdd = (Button)view.findViewById(R.id.addAnotherStep);
        container2 = (LinearLayout)view.findViewById(R.id.containerDetailsSteps);
        recipeKey = getArguments().getString("key");
        mAuth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("MRecipes").child(mAuth.getUid().toString()).child(recipeKey.toString());
        buttonAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater =
                        (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View addView = layoutInflater.inflate(R.layout.step_recipe_details, null);
                TextView textOut = (TextView)addView.findViewById(R.id.textoutStep);
                textOut.setText(textIn.getEditText().getText().toString());
                Button buttonRemove = (Button)addView.findViewById(R.id.deleteStep);
                textIn.getEditText().clearComposingText();
                final View.OnClickListener thisListener = new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        ((LinearLayout)addView.getParent()).removeView(addView);

                        listAllAddView();
                    }
                };

                buttonRemove.setOnClickListener(thisListener);
                container2.addView(addView);

                listAllAddView();
            }
        });


        close.setOnClickListener(this);
        action.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {

            case R.id.fullscreen_dialog_close2:
                dismiss();
                break;

            case R.id.updateRecipeDetails:
                callback.onActionClick("המתכון עודכן");
                addAllAddView();
                dismiss();
                break;

        }

    }

    public interface Callback {

        void onActionClick(String name);

    }

    private void listAllAddView(){
        //reList.setText("");

        int childCount = container2.getChildCount();
        for(int i=0; i<childCount; i++){
            View thisChild = container2.getChildAt(i);
            countSteps = thisChild.findViewById(R.id.recipeSteps);
            countSteps.setText(String.valueOf(i+1));

            //reList.append(thisChild + "\n");
        }
    }
    private void addAllAddView(){
        int childCount = container2.getChildCount();
        for(int i=0; i<childCount; i++){
            View thisChild = container2.getChildAt(i);
            TextView textOut = (TextView)thisChild.findViewById(R.id.textoutStep);
            mDatabaseReference.child("theRecipe").child(String.valueOf(i+1)).setValue(textOut.getText().toString());
        }
    }

}