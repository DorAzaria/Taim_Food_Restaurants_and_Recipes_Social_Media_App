package food.taim;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.webkit.WebChromeClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class NewPhotoUpload extends DialogFragment implements View.OnClickListener {


    private Callback callback;
    ListView listview;
    private Button Addbutton;
    private ImageView imageView;
    EditText GetValue;
    StorageReference mStorageReference;
    private StorageTask uploadTask;
    public Uri img;
    private String recipeKey;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseReference;
    String[] ListElements = new String[] {

    };


    private final int GALLERY_PICK = 1;
    static NewPhotoUpload newInstance() {
        return new NewPhotoUpload();
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
        View view = inflater.inflate(R.layout.camera_upload_recipe, container, false);
        ImageButton close = view.findViewById(R.id.fullscreen_dialog_close2);
        TextView action = view.findViewById(R.id.pictureUploadFinal);
        mStorageReference = FirebaseStorage.getInstance().getReference("ImagesRecipes");
        Addbutton = view.findViewById(R.id.uploadPictures);
        img = null;
        recipeKey = getArguments().getString("key");
        mAuth = FirebaseAuth.getInstance();
        imageView = view.findViewById(R.id.image_view_1);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("MRecipes").child(mAuth.getUid().toString()).child(recipeKey.toString());
        Addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent,"בחר תמונה"),GALLERY_PICK);
            }
        });
        close.setOnClickListener(this);
        action.setOnClickListener(this);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_PICK && resultCode == RESULT_OK){
            img = data.getData();
            StorageReference reference = mStorageReference.child("Recipes" + ".jpg");
            reference.putFile(img).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    final String downloadUrl = taskSnapshot.getUploadSessionUri().toString();
                    Map<String,String> map = new HashMap<>();
                    map.put("image",downloadUrl);
                    mDatabaseReference.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Picasso.get().load(downloadUrl).into(imageView);
                            }
                        }
                    });
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {

            case R.id.fullscreen_dialog_close2:
                dismiss();
                break;

            case R.id.pictureUploadFinal:
                callback.onActionClick("התמונות הועלו");
                dismiss();
                break;

        }

    }

    public interface Callback {

        void onActionClick(String name);

    }

}