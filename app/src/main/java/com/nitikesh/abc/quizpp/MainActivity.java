package com.nitikesh.abc.quizpp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nitikesh.abc.quizpp.Model.User;
import com.rengwuxian.materialedittext.MaterialEditText;

public class MainActivity extends AppCompatActivity {
    MaterialEditText editNewUserName,editNewPassword,editNewEmail; //for signUp
    MaterialEditText editUsername,editPassword; //for signIn
    Button signUpButton,signInButton;
    FirebaseDatabase database;
    DatabaseReference users;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        database=FirebaseDatabase.getInstance();
        users=database.getReference("Users");

        editUsername=(MaterialEditText)findViewById(R.id.editUsername);
        editPassword=(MaterialEditText)findViewById(R.id.editPassword);
        signUpButton=(Button)findViewById(R.id.signUp_button);
        signInButton=(Button)findViewById(R.id.signIn_button);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signin(editUsername.getText().toString(),editPassword.getText().toString());
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSignUpDialog();
            }
        });}

    private void signin(final String user, final String pwd) {
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(user).exists()){
                    if(!user.isEmpty()){
                        User login=dataSnapshot.child(user).getValue(User.class);
                        if(login.getPassword().equals(pwd)){
                            Intent homeActivity=new Intent(MainActivity.this,Home.class);
                            startActivity(homeActivity);
                            finish();
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Please Enter Your UserName", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(MainActivity.this, "User is not exists", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void showSignUpDialog(){
            AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Sign Up");
            builder.setMessage("Please fill up all information");
            LayoutInflater inflater=this.getLayoutInflater();
            View sign_up_layout=inflater.inflate(R.layout.sign_up,null);

            editNewUserName=(MaterialEditText)sign_up_layout.findViewById(R.id.editNewUsername);
            editNewPassword=(MaterialEditText)sign_up_layout.findViewById(R.id.editNewPassword);
            editNewEmail=(MaterialEditText)sign_up_layout.findViewById(R.id.editNewEmail);

            builder.setView(sign_up_layout);
            builder.setIcon(R.drawable.ic_account_circle_black_24dp);
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    final User user = new User(editNewUserName.getText().toString(), editNewPassword.getText().toString(), editNewEmail.getText().toString());
                    users.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child(user.getUserName()).exists()) {
                                Toast.makeText(MainActivity.this, "User Already Exists", Toast.LENGTH_SHORT).show();
                            } else {
                                users.child(user.getUserName()).setValue(user);
                                Toast.makeText(MainActivity.this, "User Register Successfully", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    dialog.dismiss();

                }
            });
            builder.show();

    }

    }

