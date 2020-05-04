package com.example.android.miwok;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class AirportActivity extends AppCompatActivity {
    private MediaPlayer mMediaPlayer;

    /**
     * This listener gets triggered when the {@link MediaPlayer} has completed
     * playing the audio file.
     */
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            // Now that the sound file has finished playing, release the media player resources.
            releaseMediaPlayer();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word ("Taxi Driver Name", "905428820341", R.drawable.taxi, R.raw.color_red));
        words.add(new Word("Taxi Driver Name", "905428820341", R.drawable.taxi, R.raw.color_green));
        words.add(new Word("Taxi Driver Name", "905428820341", R.drawable.taxi, R.raw.color_green));
        words.add(new Word("Taxi Driver Name", "905428820341", R.drawable.taxi, R.raw.color_green));
        words.add(new Word("Taxi Driver Name", "905428820341", R.drawable.taxi, R.raw.color_green));


        WordAdapter adapter = new WordAdapter(this, words, R.color.category_family);
        ListView listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                releaseMediaPlayer();

                Word word = words.get(position);
                mMediaPlayer = MediaPlayer.create(AirportActivity.this, word.getAudioResourceId());
                mMediaPlayer.start();

                // Setup a listener on the media player, so that we can stop and release the
                // media player once the sound has finished playing.
                mMediaPlayer.setOnCompletionListener(mCompletionListener);
            }
        });
    }


    protected void onStop(){
        super.onStop();
        //When the activity is stopped release the media player
        releaseMediaPlayer();
    }



    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;
        }
    }
}
