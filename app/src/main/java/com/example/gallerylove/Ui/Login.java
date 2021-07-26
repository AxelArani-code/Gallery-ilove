package com.example.gallerylove.Ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gallerylove.R;
import com.example.gallerylove.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    ActivityLoginBinding binding;

    //Recursos
    FirebaseAuth mAuth;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();


    }




    private void loginUseAccount(){
        String email, password;
        email = binding.editEmail.getText().toString().trim();
        password = binding.EditConstaseA.getText().toString().trim();

        if (email.isEmpty()){
            binding.editEmail.setError("Email es incorrecto");
            binding.editEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.editEmail.setError("Porfavor ingrese su email");
            binding.editEmail.requestFocus();
            return;
        }
        if (password.isEmpty()){
            binding.EditConstaseA.setError("Contraseñas incorrecta");
            binding.EditConstaseA.requestFocus();
            return;
        }
        if (password.length()<5){
            binding.EditConstaseA.setError("Ingrese mas caracteres");
            binding.EditConstaseA.requestFocus();
            return;
        }

    mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        Toast.makeText(Login.this, "Inicio de sesión exitoso!!", Toast.LENGTH_LONG).show();
                        //progressBa.setVisibility(View.GONE);
                        //startActivity(new Intent(LoginActivity.this,MainActivity.class));
                        ProgressBarStart_Login();
                    }
                    else {
                        Toast.makeText(Login.this, "¡Error de inicio de sesion! Por favor, inténtelo de nuevo más tarde"+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        //progressBa.setVisibility(View.GONE);

                    }
                }
            });
}


    public void onClick(View view){
    switch (view.getId()) {
        case R.id.btn_Crear_cuenta:
            ProgressBarStart_Register();
            break;
        case R.id.btn_iniciar :
            loginUseAccount();
            break;

        }
    }



    private void ProgressBarStart_Login() {
        progressDialog = new ProgressDialog(Login.this);

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

    private void ProgressBarStart_Register() {
        progressDialog = new ProgressDialog(Login.this);

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
                    Intent intent = new Intent(getApplicationContext(), Register.class);
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