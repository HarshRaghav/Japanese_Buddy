package android.example.japanese;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    AudioManager jAudioManager;

    AudioManager.OnAudioFocusChangeListener jOnAudio = new AudioManager.OnAudioFocusChangeListener(){
        public void onAudioFocusChange(int focusChange){
            if(focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                mediaPlayer.pause();;
                mediaPlayer.seekTo(0);
            }
            else if(focusChange == AudioManager.AUDIOFOCUS_GAIN){
                mediaPlayer.start();
            }
            else if(focusChange == AudioManager.AUDIOFOCUS_LOSS){
                releaseMediaPlayer();
            }
        }
    };

    private MediaPlayer.OnCompletionListener jCompletionListener=new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        jAudioManager =(AudioManager)getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<Word>();

        words.add(new Word("one","wən",R.drawable.number_one,R.raw.onej));

        words.add(new Word("two","Ni",R.drawable.number_two,R.raw.twoj));
        words.add(new Word("three","San",R.drawable.number_three,R.raw.threej));
        words.add(new Word("four","shi",R.drawable.number_four,R.raw.fourj));
        words.add(new Word("five","Go",R.drawable.number_five,R.raw.fivej));
        words.add(new Word("six","siks",R.drawable.number_six,R.raw.sixj));
        words.add(new Word("seven","Sebun",R.drawable.number_seven,R.raw.sevenj));
        words.add(new Word("eight","āt",R.drawable.number_eight,R.raw.eightj));
        words.add(new Word("nine","Nain",R.drawable.number_nine,R.raw.ninej));
        words.add(new Word("ten","Jū",R.drawable.number_ten,R.raw.tenj));


        WordAdapter adapter = new WordAdapter(this, words,R.color.category_numbers);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word = words.get(position);
                releaseMediaPlayer();

                int result=jAudioManager.requestAudioFocus(jOnAudio, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    mediaPlayer = MediaPlayer.create(NumbersActivity.this, word.getjAudioResourceId());
                mediaPlayer.start();
                mediaPlayer.setOnCompletionListener(jCompletionListener);
            }}
        });
    }

    protected void onStop(){
        super.onStop();
        releaseMediaPlayer();
    }
    private void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;

            jAudioManager.abandonAudioFocus(jOnAudio);
        }
    }
}

