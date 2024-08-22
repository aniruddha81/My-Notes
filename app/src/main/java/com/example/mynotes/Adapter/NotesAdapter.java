package com.example.mynotes.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynotes.Activity.UpdateNoteActivity;
import com.example.mynotes.MainActivity;
import com.example.mynotes.Model.Notes;
import com.example.mynotes.R;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {
    MainActivity mainActivity;
    List<Notes> notes;
    List<Notes> allNotesItem;


    public NotesAdapter(MainActivity mainActivity, List<Notes> notes) {
        this.mainActivity = mainActivity;
        this.notes = notes;
        allNotesItem = new ArrayList<>(notes);
    }

    public void searchNotes(List<Notes> filteredName){
        this.notes = filteredName;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotesViewHolder(LayoutInflater.from(mainActivity).inflate(R.layout.item_notes, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        Notes note = notes.get(position);
        switch (note.notesPriority) {
            case "1":
                holder.notesPriority.setBackgroundResource(R.drawable.green_shape);
                break;
            case "2":
                holder.notesPriority.setBackgroundResource(R.drawable.yellow_shape);
                break;
            case "3":
                holder.notesPriority.setBackgroundResource(R.drawable.red_shape);
                break;
        }
        holder.title.setText(note.notesTitle);
        holder.subTitle.setText(note.notesSubtitle);
        holder.notesDate.setText(note.notesDate);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(mainActivity, UpdateNoteActivity.class);
            intent.putExtra("id", note.id);
            intent.putExtra("title",note.notesTitle);
            intent.putExtra("subtitle",note.notesSubtitle);
            intent.putExtra("priority",note.notesPriority);
            intent.putExtra("note",note.notes);

            mainActivity.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    static class NotesViewHolder extends RecyclerView.ViewHolder {

        TextView title, subTitle, notesDate;
        View notesPriority;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.notesTitle);
            subTitle = itemView.findViewById(R.id.notesSubTitle);
            notesDate = itemView.findViewById(R.id.notesDate);
            notesPriority = itemView.findViewById(R.id.notesPriority);
        }
    }
}