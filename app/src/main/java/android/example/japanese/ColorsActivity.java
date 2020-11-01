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

public class ColorsActivity extends AppCompatActivity {
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

        //words.add(new Word("red","Aka",R.drawable.color_red,R.raw.redj));
        words.add(new Word("green","Midori",R.drawable.color_green,R.raw.greenj));
        words.add(new Word("brown","Kasshoku",R.drawable.color_brown,R.raw.brownj));
       // words.add(new Word("gray","Gurē",R.drawable.color_gray,R.raw.grayj));
        words.add(new Word("black","Kuro",R.drawable.color_black,R.raw.blackj));
        words.add(new Word("white","Shiroi",R.drawable.color_white,R.raw.whitej));
        words.add(new Word("dusty yellow","Ki",R.drawable.color_dusty_yellow,R.raw.dustyyellowj));
        words.add(new Word("mustard yellow","Aoi",R.drawable.color_mustard_yellow,R.raw.mustardyellowj));


        WordAdapter adapter = new WordAdapter(this, words,R.color.category_colors);

        ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Word word = words.get(position);
                    releaseMediaPlayer();

                    int result=jAudioManager.requestAudioFocus(jOnAudio, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                    if(result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                        mediaPlayer = MediaPlayer.create(ColorsActivity.this, word.getjAudioResourceId());
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
