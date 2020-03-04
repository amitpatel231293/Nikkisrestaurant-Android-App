package com.example.Nikkisrestaurant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Nikkisrestaurant.Model.Users;
import com.example.Nikkisrestaurant.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {

    private EditText InputNumber, InputPassword;
    private Button LoginButton;
    private ProgressDialog loadingBar;
    private TextView AdminLink, NotAdminLink;


    private String parentDbName = "Users";
    private CheckBox checkboxRememberMe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        LoginButton = findViewById(R.id.login_button);
        InputPassword = findViewById(R.id.login_password_input);
        InputNumber = findViewById(R.id.login__phone_number_input);
        AdminLink = findViewById(R.id.admin_panel_link);
        NotAdminLink = findViewById(R.id.not_admin_panel_link);


        loadingBar = new ProgressDialog(this);

        checkboxRememberMe = findViewById(R.id.remember_me_checkbox);

        Paper.init(this);


        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUser();
            }
        });

        AdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginButton.setText("Login Admin");
                AdminLink.setVisibility(View.INVISIBLE);
                NotAdminLink.setVisibility(View.VISIBLE);
                parentDbName = "Admins";


            }
        });

        NotAdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginButton.setText("Login");
                AdminLink.setVisibility(View.VISIBLE);
                NotAdminLink.setVisibility(View.INVISIBLE);
                parentDbName = "Users";

            }
        });

    }

    private void LoginUser() {
        String phone = InputNumber.getText().toString();
        String password = InputPassword.getText().toString();

        if (TextUtils.isEmpty(phone)){
            Toast.makeText(this, "Please Write Your phone number..", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please Write Your password..", Toast.LENGTH_SHORT).show();
        }

        else{
            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please Wait While we are checking the credentials");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            AllowAccessToAccount(phone,password);

        }
    }

    private void AllowAccessToAccount(final String phone, final String password) {

        if(checkboxRememberMe.isChecked()){

            Paper.book().write(Prevalent.UserPhonekey, phone);
            Paper.book().write(Prevalent.UserPasswordKey, password);

        }

        final DatabaseReference Rootref;
        Rootref = FirebaseDatabase.getInstance().getReference();

        Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child(parentDbName).child(phone).exists()){

                    Users usersData = dataSnapshot.child(parentDbName).child(phone).getValue(Users.class);

                    if(usersData.getPhone().equals(phone)){

                        if (usersData.getPassword().equals(password)){

                            if (parentDbName.equals("Admins")){

                                Toast.makeText(LoginActivity.this, "Welcome Admin, you are logged in Sucessfully", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(LoginActivity.this, AdminCategoryActivity.class);
                                startActivity(intent);

                            }
                            else if (parentDbName.equals("Users")){

                                Toast.makeText(LoginActivity.this, "Logged in Sucessfully", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);

                                Prevalent.currentOnlineUser = usersData;
                                startActivity(intent);



                            }

                        }
                        else {
                            loadingBar.dismiss();
                            Toast.makeText(LoginActivity.this, "Password is incorrect", Toast.LENGTH_SHORT).show();

                        }
                    }

                }
                else{
                    Toast.makeText(LoginActivity.this, "Account with this " +phone+ " number does not exit", Toast.LENGTH_SHORT).show();
                    Toast.makeText(LoginActivity.this, "You have to create a new account", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
