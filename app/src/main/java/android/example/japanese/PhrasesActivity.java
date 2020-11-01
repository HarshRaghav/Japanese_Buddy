package android.example.japanese;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class PhrasesActivity extends AppCompatActivity {
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
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        jAudioManager =(AudioManager)getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<Word>();

        words.add(new Word("Where are you going?","Doko ni iku no?",R.raw.where_are_you_going_j));
        words.add(new Word("What is your name?","Onamaehanandesuka?",R.raw.what_is_your_name_j));
        words.add(new Word("My name is..","Watashinonamaeha..",R.raw.my_name_is_j));
        words.add(new Word("How are you feeling?","Go kibun wa ikagadesu ka?",R.raw.how_are_you_feeling_j));
        words.add(new Word("I’m feeling good.","Watashi wa kibungayoidesu.",R.raw.i_m_feeling_good_j));
        words.add(new Word("Are you coming","Kimasu ka?",R.raw.are_u_coming_j));
        words.add(new Word("Yes, I’m coming.","Hai, kimasu.",R.raw.yes_i_m_coming_j));
        words.add(new Word("I’m coming.","Ima okonatteru.",R.raw.i_m_coming_j));
        words.add(new Word("Let’s go.","Ikou.",R.raw.lets_go_j));
        words.add(new Word("Come here.","Koko ni kite.",R.raw.come_here_j));

        WordAdapter adapter = new WordAdapter(this, words,R.color.category_phrases);

        ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word = words.get(position);
                releaseMediaPlayer();

                int result=jAudioManager.requestAudioFocus(jOnAudio, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    mediaPlayer = MediaPlayer.create(PhrasesActivity.this, word.getjAudioResourceId());
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