package dragger.universal.com.bookdatabase;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.Locale;

import dragger.universal.com.bookdatabase.database.BookModel;

/**
 * Created by Gheorghe Mitrea on 3/10/18.
 */

public class ShowDetails extends AppCompatActivity implements
        TextToSpeech.OnInitListener {
    private TextView editTextBookName;
    private TextView editTextBookTitle;
    private TextView editTextStoreName;
    private TextView editTextStoreAddress;
    private TextView editTextBookDescription;
    private TextView editTextfeedback;
    private TextView editTextDetail;
    private ImageView bookcover;
    private Button buttonIsshueBook;
    private ImageView bookMainPage;
    private TextToSpeech tts;
    @Override
    public void onDestroy() {
        // Don't forget to shutdown tts!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(this, MainActivity.class);

       startActivity(intent);
        finish();
    }

    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {

                //speakOut();
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }

    }
    void  menuDialog(View button1){

        PopupMenu popup = new PopupMenu(ShowDetails.this, button1);
        //Inflating the Popup using xml file
        popup.getMenuInflater().inflate(R.menu.mainactivity, popup.getMenu());

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getTitle().equals("Share")){



                }else if (item.getTitle().equals("Exit")){

                    System.exit(0);
                }
                return true;
            }
        });

        popup.show();//showing popup menu

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mainactivity, menu);//Menu Resource, Menu
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:

                String text ="Book name"+ book.getEditTextBookName()+" Description "+book.getEditTextBookDescription();
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, text);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                return true;
            case R.id.exit:
                finish();
                System.exit(0);
                return true;
                case R.id.play:
                    String text1 ="Book name"+ book.getEditTextBookName()+" Description "+book.getEditTextBookDescription();

                    tts.speak(text1, TextToSpeech.QUEUE_FLUSH, null);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    BookModel book;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_details);
        tts = new TextToSpeech(this, this);

        editTextBookName = (TextView) findViewById(R.id.editTextBookName);
        bookcover = (ImageView) findViewById(R.id.bookcover);
        editTextBookTitle = (TextView) findViewById(R.id.editTextBookTitle);
        editTextStoreName = (TextView) findViewById(R.id.editTextStoreName);
        editTextStoreAddress = (TextView) findViewById(R.id.editTextStoreAddress);
        editTextBookDescription = (TextView) findViewById(R.id.editTextBookDescription);
        editTextfeedback = (TextView) findViewById(R.id.editTextfeedback);
        editTextDetail = (TextView) findViewById(R.id.editTextDetail);
        buttonIsshueBook = (Button) findViewById(R.id.buttonIsshueBook);
        buttonIsshueBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ShowDetails.this,CustomerActivity.class);
                startActivity(intent);
            }
        });

      Long id=  getIntent().getLongExtra("id",0);
         book = BookModel.findById(BookModel.class, id);
        editTextBookName.setText(book.editTextBookName);
        editTextBookTitle.setText(book.editTextBookTitle);
        editTextStoreName.setText(book.editTextStoreName);
        editTextStoreAddress.setText(book.editTextStoreAddress);
        editTextBookDescription.setText(book.editTextBookDescription);
        editTextfeedback.setText(book.editTextfeedback);
        editTextDetail.setText(book.editTextDetail);
        RequestOptions requestOptions=new RequestOptions();

        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(bookcover).asBitmap().load(book.bookMainPage).apply(requestOptions).into(bookcover);


    }
}
