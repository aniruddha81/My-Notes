package com.example.mynotes.Activity;

import android.graphics.drawable.Drawable;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.mynotes.Model.Notes;
import com.example.mynotes.R;
import com.example.mynotes.VIewModel.NotesViewModel;
import com.example.mynotes.databinding.ActivityUpdateNoteBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Date;

public class UpdateNoteActivity extends AppCompatActivity {
    ActivityUpdateNoteBinding binding;
    String priority = "1";
    NotesViewModel notesViewModel;
    String sTitle, sSubTitle, sNotes, sPriority;
    int iid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        iid = getIntent().getIntExtra("id", 0);
        sTitle = getIntent().getStringExtra("title");
        sSubTitle = getIntent().getStringExtra("subtitle");
        sPriority = getIntent().getStringExtra("priority");
        sNotes = getIntent().getStringExtra("note");

        binding.upTitle.setText(sTitle);
        binding.upSubTitle.setText(sSubTitle);
        binding.upNotes.setText(sNotes);

        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.baseline_done_24);

        switch (sPriority) {
            case "1":
                binding.greenPriority.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                break;
            case "2":
                binding.yellowPriority.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                break;
            case "3":
                binding.redPriority.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                break;
        }

        notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);


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

        binding.updateNotesBtn.setOnClickListener(v -> {
            String title = binding.upTitle.getText().toString();
            String subTitle = binding.upSubTitle.getText().toString();
            String notes = binding.upNotes.getText().toString();

            updateNotes(title, subTitle, notes);
        });
    }

    private void updateNotes(String title, String subTitle, String notes) {
        Date date = new Date();
        String formattedDate = new SimpleDateFormat("MMMM d, yyyy").format(date);

        Notes updateNotes = new Notes();

        updateNotes.id = iid;
        updateNotes.notesTitle = title;
        updateNotes.notesSubtitle = subTitle;
        updateNotes.notes = notes;
        updateNotes.notesPriority = priority;
        updateNotes.notesDate = formattedDate;

        notesViewModel.updateNote(updateNotes);

        Toast.makeText(this, "Notes Update Successfully", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delete_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.ic_delete) {
            BottomSheetDialog sheetDialog = new BottomSheetDialog(UpdateNoteActivity.this,R.style.BottomSheetStyle);

            View view = LayoutInflater.from(UpdateNoteActivity.this).inflate(R.layout.delete_bottom_sheet, findViewById(R.id.bottomSheet));
            sheetDialog.setContentView(view);
            TextView yes, no;
            yes = view.findViewById(R.id.delete_yes);
            no = view.findViewById(R.id.delete_no);

            yes.setOnClickListener(v -> {
                notesViewModel.deleteNote(iid);
                finish();
            });

            no.setOnClickListener(v -> sheetDialog.dismiss());
            sheetDialog.show();
        }
        return true;
    }
}