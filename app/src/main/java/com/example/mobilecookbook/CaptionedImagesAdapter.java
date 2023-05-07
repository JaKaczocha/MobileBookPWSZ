package com.example.mobilecookbook;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class CaptionedImagesAdapter extends RecyclerView.Adapter<CaptionedImagesAdapter.ViewHolder> {

    private String[] captions; // data storage
    private int[] imageIds; // -II-

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView; // we specify that the ViewHolder object will contain the CardView objects.
        public ViewHolder(CardView v) { // to display other types of data you need to define it here
            super(v);
            cardView = v;
        }
    }
    public CaptionedImagesAdapter(String[] captions,int [] imageIds) { // constructor adapter passing data
        this.captions = captions;
        this.imageIds = imageIds;
    }
    @Override
    public CaptionedImagesAdapter.ViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_captioned_image,parent,false); // specify what layout to use in
                                                                        // views stored in View Holder objects
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position ) {
        CardView cardView = holder.cardView;
        ImageView imageView = (ImageView) cardView.findViewById(R.id.info_image); // responsible for displaying the photo in the ImageView
        Drawable drawable = cardView.getResources().getDrawable(imageIds[position]);
        imageView.setImageDrawable(drawable);
        imageView.setContentDescription(captions[position]);
        TextView textView = (TextView) cardView.findViewById(R.id.info_text);
        textView.setText(captions[position]);

    }


    @Override
    public int getItemCount() {
        return captions.length; // the size of the array corresponds to the number of elements presented in recycleView
    }

}
