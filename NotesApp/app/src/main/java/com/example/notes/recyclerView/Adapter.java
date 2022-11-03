package com.example.notes.recyclerView;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.R;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private final Activity activity;
    private final ArrayList<NoteModel> items;

    private final RecyclerViewInterface recyclerViewInterface;

    public Adapter(Activity activity, ArrayList<NoteModel> items, RecyclerViewInterface recyclerViewInterface) {
        this.activity = activity;
        this.items = items;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=activity.getLayoutInflater();
        View view=inflater.inflate(R.layout.recycler_view_item,parent,false); // conect recycler_view_item.xml to View; so we can deal with it with java/kotlin
        return new ViewHolder(view,recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) { //rebind view on scrolling
        holder.tvNoteTitle.setText(items.get(position).getNoteTitle());
        holder.tvNoteContent.setText(items.get(position).getNoteContent());
    }

    @Override
    public int getItemCount() {return items.size();}     //Adapter needs to know total number of items to be viewed

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvNoteTitle;          //recycler_view_item contains textView & imageView
        private TextView tvNoteContent;

        public ViewHolder(@NonNull View itemView,RecyclerViewInterface recyclerViewInterface) { //Holder of RecyclerViewSingleItem;
            super(itemView);
            tvNoteContent =itemView.findViewById(R.id.itemContent);                 // convert view to object
            tvNoteTitle =itemView.findViewById(R.id.itemTitle);  // convert view to object

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                if(recyclerViewInterface!=null){
                    int pos=getAbsoluteAdapterPosition();
                    if(pos!=RecyclerView.NO_POSITION) recyclerViewInterface.onItemLongClick(pos);
                }
                return true;
            }
        });

            itemView.setOnClickListener((View view) -> {
                if(recyclerViewInterface!=null){
                    int pos=getAbsoluteAdapterPosition();
                    if(pos!=RecyclerView.NO_POSITION) recyclerViewInterface.onItemClick(pos);
                }
            });

        }
    }
}