package com.example.gallerylove.Ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.gallerylove.R;
import com.example.gallerylove.databinding.ActivityRegisterBinding;
import com.example.gallerylove.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    ActivityRegisterBinding binding;


    FirebaseAuth mAuth;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_Black:
                startActivity(new Intent(this,Login.class));
                break;
            case R.id.btn_Registrar:
                registerNewUser();
                break;
        }
    }

    public void registerNewUser(){
        String userName,email, password, sexo;
        email = binding.editCreateEmail.getText().toString().trim();
        password = binding.editCreatePassword.getText().toString().trim();
        sexo= binding.editSexo.getText().toString().trim();
        userName =  binding.editUserName.getText().toString().trim();

        if (userName.isEmpty()){
            binding.editUserName.setError("Ingrese un nombre");
            binding.editUserName.requestFocus();return;
        }

        if (sexo.isEmpty()){
            binding.editSexo.setError("Ingrese su genero");
            binding.editSexo.requestFocus();
            return;
        }

        if (email.isEmpty()){
            binding.editCreateEmail.setError("Email es erroneo");
            binding.editCreateEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.editCreateEmail.setError("Ingrese Email");
            binding.editCreateEmail.requestFocus();
            return;
        }
        if (password.isEmpty()){
            binding.editCreatePassword.setError("Contraseña incorrecta");
            binding.editCreatePassword.requestFocus();
            return;
        }
        if (password.length()<5){
            binding.editCreatePassword.setError("Ingrese mas caracteres");
            binding.editCreatePassword.requestFocus();
            return;
        }


        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    User user = new User( userName,email, password, sexo);
                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Register.this, "El usuario se ha registrado correctamente", Toast.LENGTH_LONG).show();
                                //mProgressBar.setVisibility(View.GONE);

                                //Redirecto to login Latout
                                //startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                                ProgressBarStart();

                            }else {
                                Toast.makeText(Register.this,"Error de registro intentalo", Toast.LENGTH_LONG).show();
                                //mProgressBar.setVisibility(View.GONE);
                            }
                        }
                    });
                }else {
                    Toast.makeText(Register.this,"Error de registro", Toast.LENGTH_LONG).show();
                    //mProgressBar.setVisibility(View.GONE);
                }
            }
        });
    }



    private void ProgressBarStart() {
        progressDialog = new ProgressDialog(Register.this);

        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);

        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );

        Thread timer = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(3500);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    progressDialog.dismiss();
                    super.run();
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
        timer.start();
    }

}