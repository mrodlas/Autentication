package com.example.autentication

import android.content.Intent
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.autentication.databinding.ActivityBienvenidaBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class bienvenida : AppCompatActivity() {
    lateinit var binding: ActivityBienvenidaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBienvenidaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Bienvenida"

        val db = FirebaseFirestore.getInstance()

        //Añadir un nuevo coche conociendo su ID que es la matrícula:
        binding.btnGuardarcoche.setOnClickListener{
            if(binding.ETMarca.text.isNotEmpty() && binding.ETModelo.text.isNotEmpty() &&
                binding.ETMatricula.text.isNotEmpty() && binding.ETColor.text.isNotEmpty()){

                db.collection("coches").document(binding.ETMatricula.text.toString())
                    .set(mapOf(
                        "color" to binding.ETColor.text,
                        "marca" to binding.ETMarca.text,
                        "modelo" to binding.ETModelo.text))
            }else {
                Toast.makeText(this, "Algún campo está vacío", Toast.LENGTH_SHORT).show()}
        }

        //Eliminar un registro sabiendo su ID (matrícula del coche):
        binding.btnEliminar.setOnClickListener {
            db.collection("coches")
                .document(binding.ETMatricula.text.toString())
                .delete()
        }

        /*
        binding.btnGuardarcoche.setOnClickListener {
            //Si ningún campo está vacío:
            if(binding.ETMarca.text.isNotEmpty() && binding.ETModelo.text.isNotEmpty() &&
                binding.ETMatricula.text.isNotEmpty() && binding.ETColor.text.isNotEmpty()){
                db.collection("coches")
                    .add(mapOf(
                        "marca" to binding.ETMarca.text,
                        "modelo" to binding.ETModelo.text,
                        "matridula" to binding.ETMatricula.text,
                        "color" to binding.ETColor.text))
                    .addOnSuccessListener {documento ->
                        Log.d(TAG, "Nuevo coche añadido con id : ${documento.id}")
                    }
                    .addOnFailureListener {
                        Log.d(TAG, "Error el na inserción del nuevo registro")
                    }
            }else {
                Toast.makeText(this, "Algún campo está vacío", Toast.LENGTH_SHORT).show()}
        }
         */
        binding.btcerrarSesion.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            //volvemos a nuestra MainActivity
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.btnEditar.setOnClickListener {
            db.collection("coches")
                .whereEqualTo("matricula", binding.ETMatricula.text.toString())
                .get().addOnSuccessListener {
                    it.forEach{
                        binding.ETMarca.setText(it.get("marca") as String?)
                        binding.ETModelo.setText(it.get("modelo") as String?)
                        binding.ETColor.setText(it.get("color") as String?)
                    }
                }
        }
        /*
        binding.btnEliminar.setOnClickListener{
            db.collection("coches")
                .get()
                .addOnSuccessListener {
                    it.forEach{
                        it.reference.delete()
                    }
                }
        }
         */
    }
}