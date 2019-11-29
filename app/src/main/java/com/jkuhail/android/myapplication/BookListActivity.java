package com.jkuhail.android.myapplication;

import androidx.appcompat.app.AppCompatActivity;



import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.jkuhail.android.myapplication.adapter.BookAdapter;
import com.jkuhail.android.myapplication.app.AppController;
import com.jkuhail.android.myapplication.model.Book;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class BookListActivity extends AppCompatActivity {
    public static final String BASE_API_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    private static final String LOG_TAG = "jehad";
    private ArrayList<Book> data = new ArrayList<>();
    private BookAdapter bookAdapter;
    private ListView booksListView;
    TextView empty;
    ProgressDialog progressDialog;
    EditText search;

    Button searchButton;

    private String author;
    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        search = findViewById(R.id.search);
        searchButton = findViewById(R.id.search_button);

        empty = findViewById(R.id.empty);
        booksListView = findViewById(R.id.list);
        booksListView.setEmptyView(empty);
        bookAdapter = new BookAdapter(this, data);
        booksListView.setAdapter(bookAdapter);


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(search.getText().toString().equals(""))) {
                    data.clear();
                    search(search.getText().toString());
                }
            }
        });


    }


    public void search( String bookTitle){
        showDialog();
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, BASE_API_URL + bookTitle, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(LOG_TAG, response.toString());
                        try {
                            JSONArray booksArray = response.getJSONArray("items");

                            for (int i = 0; i < booksArray.length(); i++) {

                                JSONObject book_element = booksArray.getJSONObject(i);
                                JSONObject volumeInfo = book_element.getJSONObject("volumeInfo");
                                String title = volumeInfo.getString("title");

                                JSONArray authors = volumeInfo.getJSONArray("authors");
                                author = authors.getString(0);

                                String publishedDate = volumeInfo.getString("publishedDate");
                                String description = volumeInfo.getString("description");
                                int pageCount = volumeInfo.getInt("pageCount");

                                // I did this because some books don't have categories.
                                try{
                                JSONArray categories = volumeInfo.getJSONArray("categories");
                                category = categories.getString(0);
                                } catch (JSONException e){
                                    category = "No category";
                                }

                                JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");
                                String imageLink = imageLinks.getString("thumbnail");
                                String url = volumeInfo.getString("canonicalVolumeLink");

                                Book book = new Book(title , author , publishedDate , description , pageCount , category , imageLink , url);
                                data.add(book);
                            }

                        } catch (JSONException e) {
                            Log.e(LOG_TAG, "Problem parsing the earthquake JSON results", e);
                            hideDialog();
                        }
                        bookAdapter.notifyDataSetChanged();
                        hideDialog();

                    }
                },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder add = new AlertDialog.Builder(BookListActivity.this);
                add.setMessage(error.getMessage()).setCancelable(true);
                AlertDialog alert = add.create();
                alert.setTitle("Error!");
                alert.setMessage("something wrong :(");
                alert.show();
                hideDialog();
            }
        });
        AppController.getInstance().addToRequestQueue(objectRequest);

        booksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri bookUri = Uri.parse(BookAdapter.data.get(i).getUrl());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, bookUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });
    }

    public void showDialog() {
        progressDialog = new ProgressDialog(BookListActivity.this);
        progressDialog.setMessage("Data is loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
    public void hideDialog(){
        if(progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }

}
