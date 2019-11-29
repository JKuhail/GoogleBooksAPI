package com.jkuhail.android.myapplication.adapter;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.jkuhail.android.myapplication.R;
import com.jkuhail.android.myapplication.model.Book;

import java.util.ArrayList;

public class BookAdapter extends BaseAdapter {
    private Activity activity;
    public static ArrayList<Book> data;

    public BookAdapter(Activity activity , ArrayList<Book> data) {
        this.activity = activity;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private int getCharacterColor(char character) {

        int magnitudeColorResourceId;
        switch (character) {
            case 'A':
                magnitudeColorResourceId = R.color.character1;
                break;
            case 'B':
                magnitudeColorResourceId = R.color.character2;
                break;
            case 'C':
                magnitudeColorResourceId = R.color.character3;
                break;
            case 'D':
                magnitudeColorResourceId = R.color.character4;
                break;
            case 'E':
                magnitudeColorResourceId = R.color.character5;
                break;
            case 'F':
                magnitudeColorResourceId = R.color.character6;
                break;
            case 'G':
                magnitudeColorResourceId = R.color.character7;
                break;
            case 'H':
                magnitudeColorResourceId = R.color.character8;
                break;
            case 'I':
                magnitudeColorResourceId = R.color.character9;
                break;
            case 'J':
                magnitudeColorResourceId = R.color.character1;
                break;
            case 'K':
                magnitudeColorResourceId = R.color.character2;
                break;
            case 'L':
                magnitudeColorResourceId = R.color.character3;
                break;
            case 'M':
                magnitudeColorResourceId = R.color.character4;
                break;
            case 'N':
                magnitudeColorResourceId = R.color.character5;
                break;
            case 'O':
                magnitudeColorResourceId = R.color.character6;
                break;
            case 'P':
                magnitudeColorResourceId = R.color.character7;
                break;
            case 'Q':
                magnitudeColorResourceId = R.color.character8;
                break;
            case 'R':
                magnitudeColorResourceId = R.color.character9;
                break;
            case 'S':
                magnitudeColorResourceId = R.color.character1;
                break;
            case 'T':
                magnitudeColorResourceId = R.color.character2;
                break;
            case 'U':
                magnitudeColorResourceId = R.color.character3;
                break;
            case 'V':
                magnitudeColorResourceId = R.color.character4;
                break;
            case 'W':
                magnitudeColorResourceId = R.color.character5;
                break;
            case 'X':
                magnitudeColorResourceId = R.color.character6;
                break;
            case 'Y':
                magnitudeColorResourceId = R.color.character7;
                break;
            case 'Z':
                magnitudeColorResourceId = R.color.character8;
                break;
            default:
                magnitudeColorResourceId = R.color.character10plus;
                break;
        }
        return ContextCompat.getColor(activity, magnitudeColorResourceId);
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View root = LayoutInflater.from(activity).inflate(R.layout.single_book , null , false);

        final TextView characterView = root.findViewById(R.id.character);
        GradientDrawable characterCircle = (GradientDrawable) characterView.getBackground();
        String BookTitle = data.get(i).getBookTitle();
        char firstCharacter = BookTitle.charAt(0);
        int magnitudeColor = getCharacterColor(firstCharacter);
        characterCircle.setColor(magnitudeColor);



        final TextView bookTitle= root.findViewById(R.id.bookTitle);
        final TextView authorName= root.findViewById(R.id.authorName);
        final TextView publishedDate= root.findViewById(R.id.publishedDate);
        final TextView categories= root.findViewById(R.id.categories);

        bookTitle.setText(data.get(i).getBookTitle());
        authorName.setText(data.get(i).getAuthorName());
        publishedDate.setText(data.get(i).getPublishedDate());
        categories.setText(data.get(i).getCategories());
        characterView.setText(String.valueOf(firstCharacter));
        return root;
    }
}
