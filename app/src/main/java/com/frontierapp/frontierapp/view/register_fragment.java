package com.frontierapp.frontierapp.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.frontierapp.frontierapp.R;
import com.frontierapp.frontierapp.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class register_fragment extends Fragment {
    private EditText userPasswordEditText, userEmailEditText, firstNameEditText, lastNameEditText, conPasswordEditText, dateOfBirthEditText;
    private Button nextButton;
    private String TAG;
    private CheckBox manGenderCheckBox, womanGenderCheckBox;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private String userID;
    private ProgressDialog progressDialog;
    private DatabaseReference databaseUser;
    private FirebaseUser user;
    private FirebaseFirestore firebaseFireStore = FirebaseFirestore.getInstance();
    private User newUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view;
        view = inflater.inflate(R.layout.fragment_register_basic_info, container,false);


        userPasswordEditText = (EditText) view.findViewById(R.id.userPassword);
        userEmailEditText = (EditText) view.findViewById(R.id.userEmailEditText);
        firstNameEditText = (EditText) view.findViewById(R.id.firstNameEditText);
        lastNameEditText = (EditText) view.findViewById(R.id.lastNameEditText);
        conPasswordEditText = (EditText) view.findViewById(R.id.userConPassword);
        dateOfBirthEditText = (EditText) view.findViewById(R.id.userBirthday);
        manGenderCheckBox = (CheckBox) view.findViewById(R.id.manGenderCheckBox);
        womanGenderCheckBox = (CheckBox) view.findViewById(R.id.womanGenderCheckBox);





                return view;
    }
}
