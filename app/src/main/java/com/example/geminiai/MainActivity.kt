package com.example.geminiai

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.ai.FirebaseAI
import com.google.firebase.ai.GenerativeModel
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var searchField:EditText
    lateinit var resultTV:TextView
    lateinit var SendBtn:ImageButton

    lateinit var model: GenerativeModel
    //AIzaSyDoNpQAha3g2S2WaE1hDSySU-8VUaxM6WY
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        searchField=findViewById(R.id.editTextText)
        resultTV=findViewById(R.id.textView)
        SendBtn=findViewById(R.id.imageButton)


        SendBtn.setOnClickListener {
            lifecycleScope.launch {
                performAction(searchField.text.toString())
            }
        }

        loadModel();


    }

    suspend fun performAction(question:String){
        val response = model.generateContent(question)
        print(response.text)
        resultTV.text=response.text

    }

    fun loadModel(){
        model = Firebase.ai(backend = GenerativeBackend.googleAI())
            .generativeModel("gemini-2.5-flash")



    }




}