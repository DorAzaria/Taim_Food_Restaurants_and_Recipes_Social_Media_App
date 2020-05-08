package food.taim;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;


import android.content.Context;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewIngredients extends DialogFragment implements View.OnClickListener {


    private Callback callback;
    EditText textIn, textIn2;
    Button buttonAdd;
    LinearLayout container2;
    private String recipeKey;
    private DatabaseReference mDatabaseReference;
    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;
    static NewIngredients newInstance() {
        return new NewIngredients();
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
        View view = inflater.inflate(R.layout.new_ingredients, container, false);
        ImageButton close = view.findViewById(R.id.fullscreen_dialog_close);
        TextView action = view.findViewById(R.id.updateIngredientsBtn);


        textIn = (EditText)view.findViewById(R.id.textin);
        textIn2 = (EditText)view.findViewById(R.id.textin2);
        buttonAdd = (Button)view.findViewById(R.id.add);
        container2 = (LinearLayout)view.findViewById(R.id.container);
        recipeKey = getArguments().getString("key");
        mAuth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("MRecipes").child(mAuth.getUid().toString()).child(recipeKey.toString());
        buttonAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater =
                        (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View addView = layoutInflater.inflate(R.layout.row_ingredients, null);
                TextView textOut = (TextView)addView.findViewById(R.id.textout);
                TextView textOut2 = (TextView)addView.findViewById(R.id.textout2);
                textOut.setText(textIn.getText().toString());
                textOut2.setText(textIn2.getText().toString());
                textIn.getText().clear();
                textIn2.getText().clear();
                Button buttonRemove = (Button)addView.findViewById(R.id.remove);

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

            case R.id.fullscreen_dialog_close:
                dismiss();
                break;

            case R.id.updateIngredientsBtn:
                callback.onActionClick("המצרכים עודכנו");
                addAllAddView();
                dismiss();
                break;

        }

    }

    public interface Callback {

        void onActionClick(String name);

    }

    private void listAllAddView(){

        int childCount = container2.getChildCount();
        for(int i=0; i<childCount; i++){
            View thisChild = container2.getChildAt(i);
            //reList.append(thisChild + "\n");
        }
    }

    private void addAllAddView(){
        int childCount = container2.getChildCount();
        for(int i=0; i<childCount; i++){
            View thisChild = container2.getChildAt(i);
            TextView textOut = (TextView)thisChild.findViewById(R.id.textout);
            TextView textOut2 = (TextView)thisChild.findViewById(R.id.textout2);
            mDatabaseReference.child("ingredients").child(String.valueOf(i+1)).child("ingredient").setValue(textOut.getText().toString());
            mDatabaseReference.child("ingredients").child(String.valueOf(i+1)).child("sum").setValue(textOut2.getText().toString());
        }
    }
}