package dragger.universal.com.bookdatabase.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.orm.SugarRecord;

import java.io.File;
import java.util.List;

/**
 * Created by Gheorghe Mitrea on 8/3/18.
 */

public class BookModel extends SugarRecord<BookModel> {
    public long employId;


    public long getEmployId() {
        return employId;
    }

    public void setEmployId(long employId) {
        this.employId = employId;
    }

    public String getEditTextBookName() {
        return editTextBookName;
    }

    public void setEditTextBookName(String editTextBookName) {
        this.editTextBookName = editTextBookName;
    }

    public String getEditTextBookTitle() {
        return editTextBookTitle;
    }

    public void setEditTextBookTitle(String editTextBookTitle) {
        this.editTextBookTitle = editTextBookTitle;
    }

    public String getEditTextStoreName() {
        return editTextStoreName;
    }

    public void setEditTextStoreName(String editTextStoreName) {
        this.editTextStoreName = editTextStoreName;
    }

    public String getEditTextStoreAddress() {
        return editTextStoreAddress;
    }

    public void setEditTextStoreAddress(String editTextStoreAddress) {
        this.editTextStoreAddress = editTextStoreAddress;
    }

    public String getBookcover() {
        return bookcover;
    }

    public void setBookcover(String bookcover) {
        this.bookcover = bookcover;
    }

    public String getBookMainPage() {
        return bookMainPage;
    }

    public void setBookMainPage(String bookMainPage) {
        this.bookMainPage = bookMainPage;
    }

    @ColumnInfo(name = "editTextBookName")
    public String editTextBookName;

    @ColumnInfo(name = "editTextBookTitle")
    public String editTextBookTitle;

    @ColumnInfo(name = "editTextStoreName")
    public String editTextStoreName;

    @ColumnInfo(name = "editTextStoreAddress")
    public String editTextStoreAddress;

    public String getEditTextBookDescription() {
        return editTextBookDescription;
    }

    public void setEditTextBookDescription(String editTextBookDescription) {
        this.editTextBookDescription = editTextBookDescription;
    }

    @ColumnInfo(name = "editTextBookDescription")
    public String editTextBookDescription;



    @ColumnInfo(name = "bookcover")
    public String bookcover;

    @ColumnInfo(name = "bookMainPage")
    public String bookMainPage;

    @ColumnInfo(name = "editTextfeedback")
    public String editTextfeedback;

    public String getEditTextfeedback() {
        return editTextfeedback;
    }

    public void setEditTextfeedback(String editTextfeedback) {
        this.editTextfeedback = editTextfeedback;
    }

    public String getEditTextDetail() {
        return editTextDetail;
    }

    public void setEditTextDetail(String editTextDetail) {
        this.editTextDetail = editTextDetail;
    }

    @ColumnInfo(name = "editTextDetail")
    public String editTextDetail;


    public static List<BookModel>  getByspeech(String s){

        return BookModel.findWithQuery(BookModel.class, "Select * from BOOK_MODEL where EDIT_TEXT_BOOK_NAME = ?", s);
    }

}
