package com.example.mynotes.Activity;

import android.graphics.drawable.Drawable;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.mynotes.Model.Notes;
import com.example.mynotes.R;
import com.example.mynotes.VIewModel.NotesViewModel;
import com.example.mynotes.databinding.ActivityInsertNoteBinding;

import java.util.Date;

public class InsertNoteActivity extends AppCompatActivity {
    ActivityInsertNoteBinding binding;
    String title, subTitle, notes;
    NotesViewModel notesViewModel;
    String priority = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInsertNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);

        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.baseline_done_24);
        binding.greenPriority.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);

        binding.greenPriority.setOnClickListener(v -> {
            binding.greenPriority.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
            binding.yellowPriority.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            binding.redPriority.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);

            priority = "1";
        });
        binding.yellowPriority.setOnClickListener(v -> {
            binding.greenPriority.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            binding.yellowPriority.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
            binding.redPriority.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);

            priority = "2";
        });
        binding.redPriority.setOnClickListener(v -> {
            binding.greenPriority.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            binding.yellowPriority.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            binding.redPriority.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);

            priority = "3";
        });

        binding.doneNotesBtn.setOnClickListener(v -> {
            title = binding.notesTitle.getText().toString();
            subTitle = binding.notesSubTitle.getText().toString();
            notes = binding.notesData.getText().toString();

            createNotes(title, subTitle, notes);
        });
    }

    private void createNotes(String title, String subTitle, String notes) {
        Date date = new Date();
        String formattedDate = new SimpleDateFormat("MMMM d, yyyy").format(date);

        Notes notes1 = new Notes();
        notes1.notesTitle = title;
        notes1.notesSubtitle = subTitle;
        notes1.notes = notes;
        notes1.notesDate = formattedDate;
        notes1.notesPriority = priority;

        notesViewModel.insertNote(notes1);

        Toast.makeText(this, "Notes Created Successfully", Toast.LENGTH_SHORT).show();
        finish();
    }
}