package com.example.mobilecookbook;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CaptionedImagesAdapterBytes extends RecyclerView.Adapter<CaptionedImagesAdapterBytes.ViewHolder> {

    private ArrayList<String> captions;
    private ArrayList<byte[]> imageBytes;
    private ItemClickListener itemClickListener;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;

        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }

    public void setItemClickListener(ItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public CaptionedImagesAdapterBytes(ArrayList<byte[]> imageBytes, ArrayList<String> captions) {
        this.captions = captions;
        this.imageBytes = imageBytes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_captioned_image, parent, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        CardView cardView = holder.cardView;
        ImageView imageView = cardView.findViewById(R.id.info_image);
        TextView textView = cardView.findViewById(R.id.info_text);

        byte[] imageByte = imageBytes.get(position);

        Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
        imageView.setImageBitmap(bitmap);

        imageView.setContentDescription(captions.get(position));
        textView.setText(captions.get(position));

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return captions.size();
    }
}