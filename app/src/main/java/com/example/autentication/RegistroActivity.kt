package com.example.autentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.autentication.databinding.ActivityRegistroBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegistroActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegistroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val db = FirebaseFirestore.getInstance()

        // Comprobamos que los campos no están vacios:
        binding.btnRegistrarse.setOnClickListener {
            if (binding.nombre.text.isNotEmpty() && binding.apellidos.text.isNotEmpty() && binding.correoE.text.isNotEmpty()
                && binding.contrasenia.text.isNotEmpty()
            ) {
                // Accedemos a Firebase con el método createUser... y le pasamos el correo y el password
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    binding.correoE.text.toString(), binding.contrasenia.text.toString()
                )
                    .addOnCompleteListener {
                        // Si el usuario se ha registrado correctamente se muestra la pantalla de Bienvenida:
                        if (it.isSuccessful) {
                            db.collection("usuarios").document(binding.correoE.text.toString())
                                .set(
                                    mapOf(
                                        "nombre" to binding.nombre.text.toString(),
                                        "apellido" to binding.apellidos.text.toString()
                                    )
                                )

                            startActivity(Intent(this, bienvenida::class.java))
                        } else { // Si no, nos avisa de un error:
                            Toast.makeText(this, "No se ha podido registrar", Toast.LENGTH_LONG).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Algún campo está vacio", Toast.LENGTH_LONG).show()
            }
        }

    }
}
