package com.example.autentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.autentication.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnIniciarSesion.setOnClickListener {
            login()
        }

        binding.btnRegistrar.setOnClickListener {
            registro()
        }
    }

    private fun login(){
        //Comprobamos que los campos de correo y password no estén vacios:
        if (binding.ETCorreo.text.isNotEmpty() && binding.ETContrasenia.text.isNotEmpty()){
            //Iniciamos sesión con el método signIn y enviamos a Firebase el correo y la contraseña
            FirebaseAuth.getInstance().signInWithEmailAndPassword(
                binding.ETCorreo.text.toString(),
                binding.ETContrasenia.text.toString()
            )

                .addOnCompleteListener{
                    //Si la autenticación tuvo éxito:
                    if(it.isSuccessful){
                        //Accedemos a la pantalla bienvenidaActivity, para dar la bienvenida al usuario.
                        val intent = Intent(this, bienvenida::class.java)
                        startActivity(intent)
                    }
                    else{
                        //Sino avisamos al usuario que ocurrio un problema
                        Toast.makeText(this, "Correo o password incorrecto", Toast.LENGTH_LONG).show()
                    }
                }
        }
        else{
            Toast.makeText(this, "Algún campo está vacio", Toast.LENGTH_LONG).show()
        }
    }

    private fun registro(){
        startActivity(Intent(this, RegistroActivity::class.java))
    }
}