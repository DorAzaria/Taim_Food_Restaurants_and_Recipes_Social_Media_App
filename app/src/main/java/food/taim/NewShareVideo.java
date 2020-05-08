package food.taim;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewShareVideo extends DialogFragment implements View.OnClickListener {


    private Callback callback;
    ListView listview;
    Button Addbutton;
    EditText GetValue;
    String[] ListElements = new String[] {

    };



    static NewShareVideo newInstance() {
        return new NewShareVideo();
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
        View view = inflater.inflate(R.layout.video_share_recipe, container, false);
        ImageButton close = view.findViewById(R.id.fullscreen_dialog_close3);
        TextView action = view.findViewById(R.id.fullscreen_dialog_action3);

        close.setOnClickListener(this);
        action.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {

            case R.id.fullscreen_dialog_close3:
                dismiss();
                break;

            case R.id.fullscreen_dialog_action3:
                callback.onActionClick("המצרכים עודכנו");
                dismiss();
                break;

        }

    }

    public interface Callback {

        void onActionClick(String name);

    }

}