package com.example.android.miwok;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.Locale;

public class textToSpeech extends AppCompatActivity {


    private static final int REQUEST_CODE_SPEECH_INPUT = 1000 ;
    private TextToSpeech mTTS;

    private SeekBar mSeekBarPitch;
    private EditText mEditText;
    private SeekBar mSeekBarSpeed;
    private Button mButtonSpeak;
    private TextView txtSpeechInput;
    private ImageButton btnSpeak;
    private final int REQ_CODE_SPEECH_INPUT = 100;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_to_speech);


        txtSpeechInput = (TextView) findViewById(R.id.txtSpeechInput);
        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);

        // hide the action bar


        btnSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });







        mButtonSpeak = (Button) findViewById(R.id.button_speak);
        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = mTTS.setLanguage(Locale.ENGLISH);
                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {

                        Log.d("TTS", "Language Not Supported");

                    } else {

                        mButtonSpeak.setEnabled(true);
                    }


                }

            }
        });

        mEditText = (EditText) findViewById(R.id.edit_text);
        mSeekBarPitch = (SeekBar) findViewById(R.id.seek_bar_pitch);
        mSeekBarSpeed = (SeekBar) findViewById(R.id.seek_bar_speed);

        mButtonSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                speak();

            }
        });
    }

    private void speak() {

        String text = mEditText.getText().toString();
        float pitch = (float) mSeekBarPitch.getProgress() / 50;
        if (pitch < 0.1) pitch = 0.1f;

        float speed = (float) mSeekBarSpeed.getProgress() / 50;
        if (speed < 0.1) speed = 0.1f;

        mTTS.setPitch(pitch);
        mTTS.setSpeechRate(speed);
        mTTS.speak(text , TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    protected void onDestroy() {

        if(mTTS != null ){
            mTTS.stop();
            mTTS.shutdown();
        }
        super.onDestroy();
    }
    private void speak2(){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL , RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT , " speak to me ");
        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
        }
        catch (Exception e){
            Toast.makeText(this , ""+e.getMessage(), Toast.LENGTH_SHORT ).show();
        }

    }
    private void promptSpeechInput(){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (Exception a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txtSpeechInput.setText(result.get(0));
                }
                break;
            }

        }
    }


    }










