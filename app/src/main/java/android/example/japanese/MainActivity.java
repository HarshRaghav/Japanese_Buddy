package android.example.japanese;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //for opening  numbers window
        TextView numbers=(TextView)findViewById(R.id.numbers);
        numbers.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent numbersIntent = new Intent(MainActivity.this,NumbersActivity.class);
                startActivity(numbersIntent);
            }
        });

        //for opening  numbers window
        TextView familyMembers=(TextView)findViewById(R.id.family);
        familyMembers.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent FamilyIntent = new Intent(MainActivity.this,FamilyActivity.class);
                startActivity(FamilyIntent);
            }
        });

        //for opening  numbers window
        TextView colors=(TextView)findViewById(R.id.colors);
        colors.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent colorsIntent = new Intent(MainActivity.this,ColorsActivity.class);
                startActivity(colorsIntent);
            }
        });

        //for opening  numbers window
        TextView phrases=(TextView)findViewById(R.id.phrases);
        phrases.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent phrasesIntent = new Intent(MainActivity.this,PhrasesActivity.class);
                startActivity(phrasesIntent);
            }
        });
    }

}