package dragger.universal.com.bookdatabase.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import dragger.universal.com.bookdatabase.R;
import dragger.universal.com.bookdatabase.ShowDetails;
import dragger.universal.com.bookdatabase.database.BookModel;

/**
 * Created by Gheorghe Mitrea on 8/3/18.
 */
public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.MyViewHolder> implements Filterable {
    public List<BookModel> moviesList;
    public List<BookModel> mFilteredList;
    Activity activity;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
public Button buttonGetDetails;
public ImageView imageView;
        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.textView);
            imageView = (ImageView) view.findViewById(R.id.imageView);
            buttonGetDetails = (Button) view.findViewById(R.id.buttonGetDetails);
        }
    }


    public BookListAdapter(List<BookModel> moviesList, Activity activity) {
        this.moviesList = moviesList;
        this.mFilteredList = moviesList;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_book_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final BookModel movie = mFilteredList.get(position);
        holder.title.setText(movie.editTextBookName);

        holder.buttonGetDetails.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent=new Intent(v.getContext(), ShowDetails.class);
intent.putExtra("id",movie.getId());
                v.getContext().startActivity(intent);
                activity.finish();
            }
        });

   /*     Bitmap bitmap = BitmapFactory.decodeByteArray(movie.bookMainPage.getBytes(), 0, movie.bookMainPage.getBytes().length);

        holder.imageView.setImageBitmap(bitmap);*/

   if (movie.bookMainPage!=null){

       RequestOptions requestOptions=new RequestOptions();

       requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
       Glide.with(holder.imageView).asBitmap().load(movie.bookMainPage).apply(requestOptions).into(holder.imageView);


   }


    }

    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte=Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }


    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString().toLowerCase();

                if (charString.isEmpty()) {

                    mFilteredList = moviesList;
                } else {

                    ArrayList<BookModel> filteredList = new ArrayList<>();

                    for (BookModel androidVersion : moviesList) {

                        if (androidVersion.editTextBookName.toLowerCase().contains(charString)||androidVersion.editTextBookTitle.toLowerCase().contains(charString)) {

                            filteredList.add(androidVersion);
                        }
                    }

                    mFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<BookModel>) filterResults.values;
                notifyDataSetChanged();

            }
        };
    }

}
