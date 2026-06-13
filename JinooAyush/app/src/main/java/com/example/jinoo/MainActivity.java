package com.example.jinoo;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    ImageView iv_speak;
    int processID=100;
    MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        iv_speak=findViewById(R.id.iv_speak);
        // to click on speak image
        iv_speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //to recognize the voice
                Intent voice=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                //to get all the languages
                voice.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                //to get the select language from device
                voice.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                //to show the message for speak
                voice.putExtra(RecognizerIntent.EXTRA_PROMPT,"Speak Now");
                //pass the intent
                startActivityForResult(voice,processID);
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    //to get the os output voice to text format


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK && requestCode==processID && data!=null);
        {
            //to get the text from intent
            ArrayList<String>list=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            Toast.makeText(this,list.get(0).toString(), Toast.LENGTH_SHORT).show();
            //to pass the number of operation based on statements
            switch (list.get(0).toString())
            {
                case"open camera":
                Intent camera=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivity(camera);
                break;

                default:
                    Intent share=new Intent(Intent.ACTION_SEND);
                    //to attach the message with intent
                    share.putExtra(Intent.EXTRA_TEXT,list.get(0).toString());
                    //to define the data type
                    share.setType("text/plain");
                    startActivity(Intent.createChooser(share,"share via"));




            }
        }
    }
}