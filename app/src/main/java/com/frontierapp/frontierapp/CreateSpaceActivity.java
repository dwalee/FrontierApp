package com.frontierapp.frontierapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

public class CreateSpaceActivity extends AppCompatActivity {
    Toolbar createSpaceToolbar;
    AppCompatEditText spaceNameEditText, spacePurposeEditText;
    AppCompatCheckBox spacePrivateCheckBox;
    AppCompatButton createSpaceButton;

    private UserFirestore userFirestore = new UserFirestore();

    private final Context context = this;

    private final String GENERATED_SPACE_ID = "generatedSpaceId";
    private final String SPACE_NAME = "spaceName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_space);

        instantiateViews();
    }

    public void instantiateViews() {
        createSpaceToolbar = (Toolbar) findViewById(R.id.createSpaceToolbar);
        createSpaceToolbar.setTitle("Create New Space");

        setSupportActionBar(createSpaceToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        spaceNameEditText = (AppCompatEditText) findViewById(R.id.spaceNameEditText);
        spacePurposeEditText = (AppCompatEditText) findViewById(R.id.spacePurposeEditText);
        spacePrivateCheckBox = (AppCompatCheckBox) findViewById(R.id.spacePrivateCheckBox);
        createSpaceButton = (AppCompatButton) findViewById(R.id.createSpaceButton);
        createSpaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spaceNameEditText.getText().toString().isEmpty()
                        || spacePurposeEditText.getText().toString().isEmpty()) {
                    Toast.makeText(context, "Both fields need to be filled!", Toast.LENGTH_LONG).show();
                    return;
                }

                String name = spaceNameEditText.getText().toString();
                String purpose = spacePurposeEditText.getText().toString();
                Boolean isPrivate = spacePrivateCheckBox.isChecked();

                Space space = new Space();
                space.setName(name);
                space.setPurpose(purpose);
                space.setPrivate(isPrivate);

                SpaceFirestore spaceFirestore = new SpaceFirestore(context);
                spaceFirestore.createSpace(space, userFirestore.getCurrentUserId());

                Intent addSpaceMemberIntent = new Intent(context, AddSpaceMembersActivity.class);
                addSpaceMemberIntent.putExtra(GENERATED_SPACE_ID, spaceFirestore.getCurrentSpaceId());
                addSpaceMemberIntent.putExtra(SPACE_NAME, spaceFirestore.getCurrentSpaceName());

                finish();
                startActivity(addSpaceMemberIntent);
            }
        });
    }

}
