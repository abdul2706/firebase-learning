package com.example.firebaselearning;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private FirebaseDatabase database;
    private DatabaseReference reference;
    private ArrayList<String> usernames = new ArrayList<>();
    private ArrayList<String> passwords = new ArrayList<>();
    private int usersCount = 0;

    //Views
//    private Button buttonLogin, buttonSignUp;
    private Button buttonUploadGPA;
//    private EditText editTextUsername, editTextPassword;
    private EditText editTextName, editTextGPA;
//    private TextView textViewFirebaseData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setup Views
//        buttonLogin = findViewById(R.id.buttonLogin);
//        buttonSignUp = findViewById(R.id.buttonSignUp);
        buttonUploadGPA = findViewById(R.id.buttonUploadGPA);
//        editTextUsername = findViewById(R.id.editTextUsername);
//        editTextPassword = findViewById(R.id.editTextPassword);
        editTextName = findViewById(R.id.editTextName);
        editTextGPA = findViewById(R.id.editTextGPA);
//        textViewFirebaseData = findViewById(R.id.textViewFirebaseData);

        //Setup Database
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        //Setup Listeners
//        reference.addValueEventListener(new ReadListener());
//        buttonLogin.setOnClickListener(new LoginListener());
//        buttonSignUp.setOnClickListener(new SignUpListener());
        buttonUploadGPA.setOnClickListener(new UploadGPAListener());
    }

    private class UploadGPAListener implements View.OnClickListener {
        private static final String TAG = "UploadGPAListener";

        @Override
        public void onClick(View v) {
            Log.d(TAG, "onClick: starts");
            String fullName = editTextName.getText().toString();
            String gpa = editTextGPA.getText().toString();
            String key = reference.push().getKey();
            if(key != null) {
                reference.child("fullName").child(key).setValue(fullName)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "onSuccess: starts");
                                Toast.makeText(MainActivity.this, "fullName SuccessListener", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "onFailure: starts");
                                Toast.makeText(MainActivity.this, "fullName FailureListener", Toast.LENGTH_SHORT).show();
                            }
                        });
                reference.child("GPA").child(key).setValue(gpa)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "onSuccess: starts");
                                Toast.makeText(MainActivity.this, "GPA SuccessListener", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "onFailure: starts");
                                Toast.makeText(MainActivity.this, "GPA FailureListener", Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                Toast.makeText(MainActivity.this, "Key is null", Toast.LENGTH_SHORT).show();
            }
        }
    }

//    private class LoginListener implements View.OnClickListener {
//        private static final String TAG = "LoginListener";
//
//        @Override
//        public void onClick(View v) {
//            Log.d(TAG, "onClick: starts");
//            String username = editTextUsername.getText().toString();
//            String password = editTextPassword.getText().toString();
//            if(usernames.contains(username) && passwords.contains(password)) {
//                Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
//            } else if(usernames.contains(username) && !passwords.contains(password)) {
//                Toast.makeText(MainActivity.this, "Password didn't Matched\nTry Again", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(MainActivity.this, "Username not found\nKindly SignUp", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

//    private class SignUpListener implements View.OnClickListener {
//        private static final String TAG = "SignUpListener";
//
//        @Override
//        public void onClick(View v) {
//            Log.d(TAG, "onClick: starts");
//            String username = editTextUsername.getText().toString();
//            String password = editTextPassword.getText().toString();
//            if(!usernames.contains(username)) {
//                String key = reference.push().getKey();
//                if(key != null) {
//                    reference.child("username").child(key).setValue(username)
//                            .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void aVoid) {
//                                    Log.d(TAG, "onSuccess: starts");
//                                    Toast.makeText(MainActivity.this, "Username SuccessListener", Toast.LENGTH_SHORT).show();
//                                }
//                            })
//                            .addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                    Log.d(TAG, "onFailure: starts");
//                                    Toast.makeText(MainActivity.this, "Username FailureListener", Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                    reference.child("password").child(key).setValue(password)
//                            .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void aVoid) {
//                                    Log.d(TAG, "onSuccess: starts");
//                                    Toast.makeText(MainActivity.this, "Password SuccessListener", Toast.LENGTH_SHORT).show();
//                                }
//                            })
//                            .addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                    Log.d(TAG, "onFailure: starts");
//                                    Toast.makeText(MainActivity.this, "Password FailureListener", Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                } else {
//                    Toast.makeText(MainActivity.this, "Key is null", Toast.LENGTH_SHORT).show();
//                }
//            } else {
//                Toast.makeText(MainActivity.this, "Username Already Occupied\nKindly Login", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

//    private class ReadListener implements ValueEventListener {
//        private static final String TAG = "ReadListener";
//
//        @Override
//        @SuppressWarnings("unchecked")
//        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//            try {
//                Log.d(TAG, "onDataChange: starts");
//                Log.d(TAG, "onDataChange: dataSnapshot -> " + dataSnapshot);
//                HashMap<String, Object> firebaseData = (HashMap<String, Object>) dataSnapshot.getValue();
//                StringBuilder stringBuilder = new StringBuilder();
//                usernames = new ArrayList<>();
//                passwords = new ArrayList<>();
//                Set<String> keys = firebaseData.keySet();
//                Log.d(TAG, "onDataChange: keys -> " + keys);
//                for (String key : keys) {
//                    HashMap<String, Object> value = (HashMap<String, Object>) firebaseData.get(key);
//                    Set<String> keys2 = value.keySet();
//                    Log.d(TAG, "onDataChange: keys2 -> " + keys2);
//                    if(key.toLowerCase().equals("username")) {
//                        for(String key2 : keys2) {
//                            usernames.add(String.valueOf(value.get(key2)));
//                        }
//                    }
//                    if(key.toLowerCase().equals("password")) {
//                        for(String key2 : keys2) {
//                            passwords.add(String.valueOf(value.get(key2)));
//                        }
//                    }
//                }
//                usersCount = usernames.size();
//                for(int i = 0; i < usernames.size(); i++) {
//                    Log.d(TAG, "onDataChange: user#" + i + ":" + usernames.get(i));
//                    Log.d(TAG, "onDataChange: user#" + i + ":" + passwords.get(i));
//                    stringBuilder.append("user#")
//                            .append(i)
//                            .append(":")
//                            .append(usernames.get(i))
//                            .append(";")
//                            .append(passwords.get(i))
//                            .append("\n");
//                }
//                textViewFirebaseData.setText(stringBuilder.toString());
//            } catch(Exception e) {
//                Log.e(TAG, "onDataChange: e -> " + e.getMessage(), e);
//            }
//        }
//
//        @Override
//        public void onCancelled(@NonNull DatabaseError databaseError) {
//            Log.d(TAG, "onCancelled: starts");
//        }
//    }

}
