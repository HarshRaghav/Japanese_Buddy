package android.example.japanese;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {

private int colorId;
    public WordAdapter(Activity context, ArrayList<Word> word ,int color){
        super(context,0,word);
        colorId=color;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView==null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        Word currentWord=getItem(position);
        TextView japaneseTextView=(TextView) listItemView.findViewById((R.id.japanese_text_view));
        japaneseTextView.setText(currentWord.getJapaneseTranslation());

        TextView defaultTextView=(TextView) listItemView.findViewById((R.id.default_text_view));
        defaultTextView.setText(currentWord.getDefaultTranslation());

        ImageView iconView = (ImageView) listItemView.findViewById(R.id.image);
        if(currentWord.hasImage()){
            iconView.setImageResource(currentWord.getImageResourceId());
            iconView.setVisibility(View.VISIBLE);
        }
        else{
            iconView.setVisibility(View.GONE);
        }
        View textId=listItemView.findViewById(R.id.text_container);
        int color= ContextCompat.getColor(getContext(),colorId);
        textId.setBackgroundColor(color);

        return listItemView;
    }
}
