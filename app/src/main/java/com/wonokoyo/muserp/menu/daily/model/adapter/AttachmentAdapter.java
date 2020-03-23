package com.wonokoyo.muserp.menu.daily.model.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wonokoyo.muserp.R;
import com.wonokoyo.muserp.menu.daily.model.Attachment;
import com.wonokoyo.muserp.util.ImageUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AttachmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Attachment> listAttachment;

    public AttachmentAdapter(Context context) {
        this.mContext = context;
        this.listAttachment = new ArrayList<>();
    }

    @Override
    public int getItemViewType(int position) {
        if (listAttachment.get(position).getType().equalsIgnoreCase("image"))
            return 0;
        else
            return 1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.daily_attachment_image, parent, false);
            return new ImageViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.daily_attachment_video, parent, false);
            return new VideoViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Attachment attachment = listAttachment.get(position);

        switch (holder.getItemViewType()) {
            case 0 :
                ImageViewHolder imageViewHolder = (ImageViewHolder) holder;
                File file = new File(attachment.getFileuri());
                Bitmap bitmap = new BitmapDrawable(mContext.getResources(), file.getAbsolutePath()).getBitmap();
                imageViewHolder.ivPhoto.setImageBitmap(ImageUtil.getBestImageOrientation(bitmap, attachment.getFileuri()));
                break;

            case 1:
                VideoViewHolder videoViewHolder = (VideoViewHolder) holder;
                Uri uri = Uri.parse(attachment.getFileuri());
                videoViewHolder.videoView.setVideoURI(uri);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return listAttachment.size();
    }

    public void addNewImage(List<Attachment> listAttachment) {
        this.listAttachment.clear();
        this.listAttachment.addAll(listAttachment);
        this.notifyDataSetChanged();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPhoto;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            ivPhoto = itemView.findViewById(R.id.imgViewPhoto);
        }
    }

    public static class VideoViewHolder extends RecyclerView.ViewHolder {
        VideoView videoView;
        ImageView ivPlayPause;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);

            videoView = itemView.findViewById(R.id.videoView);
            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    ivPlayPause.setVisibility(View.VISIBLE);
                }
            });

            ivPlayPause = itemView.findViewById(R.id.ivPlayPause);
            ivPlayPause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    videoView.start();
                    ivPlayPause.setVisibility(View.INVISIBLE);
                }
            });
        }
    }
}
