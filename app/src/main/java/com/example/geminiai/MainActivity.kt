package com.example.geminiai

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.google.firebase.Firebase
import com.google.firebase.ai.GenerativeModel
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend
import com.stfalcon.chatkit.messages.MessagesList
import com.stfalcon.chatkit.messages.MessagesListAdapter
import kotlinx.coroutines.launch
import java.util.Calendar


class MainActivity : AppCompatActivity() {
    lateinit var searchField:EditText
    lateinit var SendBtn:ImageButton
    lateinit var SendCard: CardView
    lateinit var model: GenerativeModel
    lateinit var messageList: MessagesList
    lateinit var us: User
    lateinit var gemini: User
    lateinit var adapter: MessagesListAdapter<Message>

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
        SendBtn=findViewById(R.id.imageButton)
        SendCard=findViewById(R.id.cardView2)
        messageList=findViewById(R.id.messagesList)

        us = User(id = "1", name = "Sriram", avatar = "")
        gemini = User(id = "2", name = "Gemini", avatar = "")

        adapter =
            MessagesListAdapter<Message>("1", null)
        messageList.setAdapter(adapter)

        SendBtn.setOnClickListener {
            lifecycleScope.launch {
                performAction(searchField.text.toString())
            }
        }

        SendCard.setOnClickListener {
            lifecycleScope.launch {
                performAction(searchField.text.toString())
            }
        }

        findViewById<ImageView>(R.id.imageView).setOnClickListener {
            

        }

        loadModel();


    }

    suspend fun performAction(question:String){
        searchField.text.clear()
        var message: Message = Message(id = "m1", text = question, user = us, createdAt = Calendar.getInstance().time)
        adapter.addToStart(message, true)
        val response = model.generateContent(question)
        var message2 = Message(id = "m2", text = response.text!!, user = gemini, createdAt = Calendar.getInstance().time)
        adapter.addToStart(message2, true)
        print(response.text)

    }

    fun loadModel(){
        model = Firebase.ai(backend = GenerativeBackend.googleAI())
            .generativeModel("gemini-2.5-flash")



    }




}