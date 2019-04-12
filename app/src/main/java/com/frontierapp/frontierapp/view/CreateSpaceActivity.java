package com.frontierapp.frontierapp.view;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.frontierapp.frontierapp.R;
import com.frontierapp.frontierapp.databinding.ActivityCreateSpaceBinding;
import com.frontierapp.frontierapp.datasource.Firestore;
import com.frontierapp.frontierapp.model.Space;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;
import java.util.Map;

public class CreateSpaceActivity extends AppCompatActivity {
    private static final String TAG = "CreateSpaceActivity";
    private ActivityCreateSpaceBinding createSpaceBinding;
    private Firestore<Space> spaceFirestore = new Firestore<>(Firestore.spaceCollection);
    private Space space;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createSpaceBinding = DataBindingUtil.setContentView(this, R.layout.activity_create_space);
        init();
    }


    public void init(){
        createSpaceBinding.createSpaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = createSpaceBinding.spaceNameEditText.getText().toString();
                String purpose = createSpaceBinding.spacePurposeEditText.getText().toString();
                boolean is_private = createSpaceBinding.spacePrivateCheckBox.isChecked();

                if(!(name.isEmpty()) && !(purpose.isEmpty())) {
                    //Add data from fields and create new space
                    space = new Space(name, purpose, is_private, "");
                    spaceFirestore.add(space).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(CreateSpaceActivity.this, "New space created!", Toast.LENGTH_SHORT).show();
                            //get the reference and path to the new space
                            DocumentReference documentReference = spaceFirestore.getDocumentReference();
                            String path = documentReference.getPath();
                            Log.i(TAG, "onClick: new space created - " + path);
                            space.setSpace_ref(documentReference);

                            Map<String, DocumentReference> map = new HashMap<>();
                            map.put("space_ref", space.getSpace_ref());
                            Firestore.userCollection
                                    .document(Firestore.currentUserId)
                                    .collection(Firestore.SPACE_REFERENCES).document().set(map);

                            Intent intent = new Intent(CreateSpaceActivity.this, SpaceActivity.class);
                            intent.putExtra("PATH", path);
                            startActivity(intent);
                            finish();
                        }
                    });


                }else{
                    Toast.makeText(CreateSpaceActivity.this, "Fill in both name and purpose", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
