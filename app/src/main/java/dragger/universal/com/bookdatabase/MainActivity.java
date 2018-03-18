package dragger.universal.com.bookdatabase;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import dragger.universal.com.bookdatabase.adapter.BookListAdapter;
import dragger.universal.com.bookdatabase.database.BookModel;

/**
 * Created by naseem on 9/3/18.
 */

public class MainActivity extends AppCompatActivity {


    private RecyclerView recycleview;
    private TextView textViewNoDataFound;
    private ImageView imageViewVoice;
    private EditText edit_query;
    private ImageView imageViewAdd;
    private ImageView imageViewMore;
BookListAdapter bookListAdapter;
    List<BookModel> books;
    @Override
    protected void onResume() {
        super.onResume();

    }

    void  menuDialog(View button1){

        PopupMenu popup = new PopupMenu(MainActivity.this, button1);
        //Inflating the Popup using xml file
        popup.getMenuInflater().inflate(R.menu.mainactivity, popup.getMenu());

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getTitle().equals("Share")){



                }else if (item.getTitle().equals("Exit")){

                    finish();
                }
                Toast.makeText(MainActivity.this,"You Clicked : " + item.getTitle(),Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        popup.show();//showing popup menu

    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booklist_layout);
        initView();
getSupportActionBar().hide();

        imageViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuDialog(v);
            }
        });
        imageViewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,AddBookInfromationActivity.class);
                startActivity(intent);
            }
        });

        recycleview.setLayoutManager(new LinearLayoutManager(this));

        imageViewVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput();

            }
        });
        books = BookModel.listAll(BookModel.class);

        if (books.size() != 0) {
            bookListAdapter=new BookListAdapter(books,this);
            textViewNoDataFound.setVisibility(View.GONE);
            recycleview.setVisibility(View.VISIBLE);
            recycleview.setAdapter(bookListAdapter);

            edit_query.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    Log.e("","");

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    Log.e("","");

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (fromspeech) {

                        fromspeech = false;
                    } else {
                        bookListAdapter.getFilter().filter(s);
                    }
                }
            });
        } else {
            textViewNoDataFound.setVisibility(View.VISIBLE);
            recycleview.setVisibility(View.GONE);
        }

    }

    /**
     * Showing google speech input dialog
     * */
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }
    private final int REQ_CODE_SPEECH_INPUT = 100;


    /**
     * Receiving speech input
     * */
    boolean fromspeech=false;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    fromspeech=true;
                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    edit_query.setText(result.get(0));

                    bookListAdapter.mFilteredList=BookModel.getByspeech(result.get(0));
                    bookListAdapter.notifyDataSetChanged();

                }
                break;
            }

        }
    }


    private void initView() {
        recycleview = (RecyclerView) findViewById(R.id.recycleview);
        imageViewAdd = (ImageView) findViewById(R.id.imageViewAdd);
        imageViewMore = (ImageView) findViewById(R.id.imageViewMore);
        imageViewVoice = (ImageView) findViewById(R.id.imageViewVoice);
        textViewNoDataFound = (TextView) findViewById(R.id.textViewNoDataFound);
        edit_query = (EditText) findViewById(R.id.edit_query);
    }
}
