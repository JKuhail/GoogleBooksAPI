package com.jkuhail.android.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.jkuhail.android.myapplication.adapter.BookAdapter;
import com.jkuhail.android.myapplication.app.AppController;
import com.jkuhail.android.myapplication.model.Book;

public class BookDetailsActivity extends AppCompatActivity {

    TextView bookTitle_details;
    TextView authorName_details;
    TextView categories_details;
    TextView publishedDate_details;
    TextView pageCount_details;
    TextView description_details;
    TextView url_details;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        final Book book = intent.getParcelableExtra("book");
        setTitle(book.getBookTitle());
        setContentView(R.layout.activity_book_details);
        bookTitle_details = findViewById(R.id.bookTitle_details);
        authorName_details = findViewById(R.id.authorName_details);
        categories_details = findViewById(R.id.categories_details);
        publishedDate_details = findViewById(R.id.publishedDate_details);
        pageCount_details = findViewById(R.id.pageCount_details);
        description_details = findViewById(R.id.description_details);
        url_details = findViewById(R.id.url_details);




        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        NetworkImageView book_cover = findViewById(R.id.book_cover);

        book_cover.setImageUrl(book.getImage(), imageLoader);
        bookTitle_details.setText(book.getBookTitle());
        authorName_details.setText(book.getAuthorName());
        categories_details.setText(book.getCategories());
        publishedDate_details.setText(book.getPublishedDate());
        pageCount_details.setText(String.valueOf(book.getPageCount()));
        description_details.setText(book.getDescription());
        url_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri bookUri = Uri.parse(book.getUrl());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, bookUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });


    }
}
