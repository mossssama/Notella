package com.example.notesapp.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesapp.POJO.NoteModel;
import com.example.notesapp.R;

import java.util.ArrayList;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.NoteViewHolder> {
    Context context;
    private ArrayList<NoteModel> notesList;
    private final RecyclerViewItemClick recyclerViewItemClick;

    public RecyclerViewAdapter(Context context, ArrayList<NoteModel> notesList, RecyclerViewItemClick recyclerViewItemClick) {
        this.context = context;
        this.notesList = notesList;
        this.recyclerViewItemClick = recyclerViewItemClick;
    }

    /* Inflate the layout (Give a look to rows) */
    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.notes_rv_row,parent,false);
        return new NoteViewHolder(view);
    }

    /* Assign values to views we created in notes_rv_row.xml file based on the position of the recycler view */
    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.noteTitle.setText(notesList.get(position).getNoteTitle());
        holder.noteDescription.setText(notesList.get(position).getNoteContent());
    }

    /* Inform the recycler view with the number of items you want to display */
    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public void setNotesList(ArrayList<NoteModel> notesList){
        this.notesList=notesList;
        notifyDataSetChanged();
    }

    /* Grabe the views from notes_rv_row.xml (Like onCreate method) */
    public class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView noteTitle;
        TextView noteDescription;
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            noteTitle=itemView.findViewById(R.id.noteTitle);
            noteDescription=itemView.findViewById(R.id.noteDescription);

            itemView.setOnClickListener((View view)-> recyclerViewItemClick.onItemClick(getAdapterPosition()));

            itemView.setOnLongClickListener((View view) ->{
                    recyclerViewItemClick.onItemLongClick(getAdapterPosition());
                    return true;
            });

        }
    }
}
