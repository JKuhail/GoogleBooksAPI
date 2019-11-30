package com.jkuhail.android.myapplication.ui.search;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.jkuhail.android.myapplication.R;
import com.jkuhail.android.myapplication.adapter.BookAdapter;
import com.jkuhail.android.myapplication.app.AppController;
import com.jkuhail.android.myapplication.model.Book;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class SearchFragment extends Fragment {
    private static final String BASE_API_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    private static final String LOG_TAG = "jehad";
    private ArrayList<Book> data = new ArrayList<>();
    private BookAdapter bookAdapter;
    private ListView booksListView;
    private TextView empty;
    private EditText search;
    private String author;
    private String category;
    private long delay = 1000; // 1 seconds after user stops typing
    private long last_text_edit = 0;
    private Handler handler = new Handler();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_search, container, false);

        search = root.findViewById(R.id.search);

        empty = root.findViewById(R.id.empty);
        booksListView = root.findViewById(R.id.list);
        booksListView.setEmptyView(empty);
        bookAdapter = new BookAdapter(getActivity(), data);
        booksListView.setAdapter(bookAdapter);



        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged (CharSequence s,int start, int count,
                                           int after){
            }
            @Override
            public void onTextChanged ( final CharSequence s, int start, int before,
                                        int count){
                //You need to remove this to run only once
                handler.removeCallbacks(input_finish_checker);
            }
            @Override
            public void afterTextChanged ( final Editable s){
                //avoid triggering event when text is empty
                if (s.length() > 0) {
                    last_text_edit = System.currentTimeMillis();
                    handler.postDelayed(input_finish_checker, delay);
                }
            }
        }

        );
        return root;
    }
    private Runnable input_finish_checker = new Runnable() {
        public void run() {
            if (System.currentTimeMillis() > (last_text_edit + delay - 500)) {
                // TODO: do what you need here
                // ............
                // ............
                data.clear();
                search(search.getText().toString());
            }
        }
    };


    private void search( String bookTitle){
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
                        }
                        bookAdapter.notifyDataSetChanged();

                    }
                },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder add = new AlertDialog.Builder(getContext());
                add.setMessage(error.getMessage()).setCancelable(true);
                AlertDialog alert = add.create();
                alert.setTitle("Error!");
                alert.setMessage("Please check your internet connection.");
                alert.show();
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

    }
