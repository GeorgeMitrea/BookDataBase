package dragger.universal.com.bookdatabase;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import dragger.universal.com.bookdatabase.database.BookModel;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

/**
 * Created by Gheorghe Mitrea on 8/3/18.
 */

public class AddBookInfromationActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextBookName;
    private EditText editTextBookTitle;
    private EditText editTextStoreName;
    private EditText editTextStoreAddress;
    private EditText editTextBookDescription;
    private EditText editTextfeedback;
    private EditText editTextDetail;
    private ImageView imageViewBookPhoto;
    private Button buttonSelectBookPhoto;
    private ImageView imageViewBookCoverPhoto;
    private Button buttonSelectBookCover;
    private Button buttonAddDetails;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_bookdetails_activity);
        initView();

    }

    private void initView() {
        editTextBookName = (EditText) findViewById(R.id.editTextBookName);
        editTextBookTitle = (EditText) findViewById(R.id.editTextBookTitle);
        editTextStoreName = (EditText) findViewById(R.id.editTextStoreName);
        editTextStoreAddress = (EditText) findViewById(R.id.editTextStoreAddress);
        editTextfeedback = (EditText) findViewById(R.id.editTextfeedback);
        editTextDetail = (EditText) findViewById(R.id.editTextDetail);
        editTextBookDescription = (EditText) findViewById(R.id.editTextBookDescription);
        imageViewBookPhoto = (ImageView) findViewById(R.id.imageViewBookPhoto);
        buttonSelectBookPhoto = (Button) findViewById(R.id.buttonSelectBookPhoto);
        imageViewBookCoverPhoto = (ImageView) findViewById(R.id.imageViewBookCoverPhoto);
        buttonSelectBookCover = (Button) findViewById(R.id.buttonSelectBookCover);
        buttonAddDetails = (Button) findViewById(R.id.buttonAddDetails);

        buttonSelectBookPhoto.setOnClickListener(this);
        buttonSelectBookCover.setOnClickListener(this);
        buttonAddDetails.setOnClickListener(this);
    }

    private void submit() {
        // validate
        String editTextBookName = this.editTextBookName.getText().toString().trim();
        if (TextUtils.isEmpty(editTextBookName)) {
            Toast.makeText(this, "Enter Book Name", Toast.LENGTH_SHORT).show();
            return;
        }

        String editTextBookTitle = this.editTextBookTitle.getText().toString().trim();
        if (TextUtils.isEmpty(editTextBookTitle)) {
            Toast.makeText(this, "Enter Book Title", Toast.LENGTH_SHORT).show();
            return;
        }

        String editTextStoreName = this.editTextStoreName.getText().toString().trim();
        if (TextUtils.isEmpty(editTextStoreName)) {
            Toast.makeText(this, "Enter Store Name", Toast.LENGTH_SHORT).show();
            return;
        }
  String editTextBookDescription = this.editTextBookDescription.getText().toString().trim();
        if (TextUtils.isEmpty(editTextBookDescription)) {
            Toast.makeText(this, "Enter Book Description", Toast.LENGTH_SHORT).show();
            return;
        } String editTextfeedback = this.editTextfeedback.getText().toString().trim();
        if (TextUtils.isEmpty(editTextBookDescription)) {
            Toast.makeText(this, "Enter Book Description", Toast.LENGTH_SHORT).show();
            return;
        } String editTextDetail = this.editTextDetail.getText().toString().trim();
        if (TextUtils.isEmpty(editTextBookDescription)) {
            Toast.makeText(this, "Enter Book Detail", Toast.LENGTH_SHORT).show();
            return;
        }

        String editTextStoreAddress = this.editTextStoreAddress.getText().toString().trim();
        if (TextUtils.isEmpty(editTextStoreAddress)) {
            Toast.makeText(this, "Enter Store Address", Toast.LENGTH_SHORT).show();
            return;
        }


        InsertDataBase(editTextBookName,editTextBookTitle,editTextStoreName,editTextStoreAddress,editTextBookDescription,editTextfeedback,editTextDetail);

        // TODO validate success, do something


    }


    void InsertDataBase(String editTextBookName,String editTextBookTitle,String editTextStoreName,String editTextStoreAddress,String editTextBookDescription,String editTextfeedback,String editTextDetail){

        BookModel employee = new BookModel();
        employee.setEditTextBookName(editTextBookName);
        employee.setEditTextBookTitle(editTextBookTitle);
        employee.setEditTextStoreName(editTextStoreName);
        employee.setEditTextStoreAddress(editTextStoreAddress);
        employee.setEditTextBookDescription(editTextBookDescription);
        employee.setBookcover(coverphoto);
        employee.setBookMainPage(mainphoto);
        employee.setEditTextfeedback(editTextfeedback);
        employee.setEditTextDetail(editTextDetail);


        employee.save();
        Toast.makeText(this, "Saved successfully", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(this, MainActivity.class);

        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonSelectBookPhoto:


                EasyImage.openChooserWithGallery(this,"Select Main Photo", 100);

                break;
            case R.id.buttonSelectBookCover:


                EasyImage.openChooserWithGallery(this,"Select Cover Photo", 200);


                break;
            case R.id.buttonAddDetails:
                submit();
                break;
        }
    }


    public String mainphoto;
    public String coverphoto="";
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handling



            }

            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
                if (type==100) {


                    mainphoto=imageFile.getAbsolutePath();



                    Picasso.get().load(imageFile).into( imageViewBookPhoto);

                }
                else {

                    coverphoto=imageFile.getAbsolutePath();


                    Picasso.get().load(imageFile).into( imageViewBookCoverPhoto);





                }

            }


        });
    }


    byte[]  filetoBytes( File file ) {


        byte[] b = new byte[(int) file.length()];
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(b);

            for (int i = 0; i < b.length; i++) {
                System.out.print((char) b[i]);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found.");
            e.printStackTrace();
        } catch (IOException e1) {
            System.out.println("Error Reading The File.");
            e1.printStackTrace();
        }

        return b;

    }


    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp=Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }




}
