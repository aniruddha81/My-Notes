package com.example.mynotes;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.mynotes.Activity.InsertNoteActivity;
import com.example.mynotes.Adapter.NotesAdapter;
import com.example.mynotes.Model.Notes;
import com.example.mynotes.VIewModel.NotesViewModel;
import com.example.mynotes.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding mainBinding;
    NotesViewModel notesViewModel;
    NotesAdapter adapter;
    List<Notes> filterNotesAllList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        mainBinding.nofilter.setBackgroundResource(R.drawable.filter_selected_shape);
        mainBinding.nofilter.setOnClickListener(v -> {
            loadData(0);
            mainBinding.hightolow.setBackgroundResource(R.drawable.filter_un_shape);
            mainBinding.lowtohigh.setBackgroundResource(R.drawable.filter_un_shape);
            mainBinding.nofilter.setBackgroundResource(R.drawable.filter_selected_shape);
        });
        mainBinding.hightolow.setOnClickListener(v -> {
            loadData(1);
            mainBinding.hightolow.setBackgroundResource(R.drawable.filter_selected_shape);
            mainBinding.lowtohigh.setBackgroundResource(R.drawable.filter_un_shape);
            mainBinding.nofilter.setBackgroundResource(R.drawable.filter_un_shape);
        });
        mainBinding.lowtohigh.setOnClickListener(v -> {
            loadData(2);
            mainBinding.hightolow.setBackgroundResource(R.drawable.filter_un_shape);
            mainBinding.lowtohigh.setBackgroundResource(R.drawable.filter_selected_shape);
            mainBinding.nofilter.setBackgroundResource(R.drawable.filter_un_shape);
        });

        notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);

        mainBinding.newNotesBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, InsertNoteActivity.class)));

        notesViewModel.getAllNotes.observe(this, notes -> {
            setAdapter(notes);
            filterNotesAllList = notes;
        });
    }

    private void loadData(int i) {
        if (i == 0) {
            notesViewModel.getAllNotes.observe(this, notes -> {
                setAdapter(notes);
                filterNotesAllList = notes;
            });
        } else if (i == 1) {
            notesViewModel.highToLow.observe(this, notes -> {
                setAdapter(notes);
                filterNotesAllList = notes;
            });
        } else if (i == 2) {
            notesViewModel.lowToHigh.observe(this, notes -> {
                setAdapter(notes);
                filterNotesAllList = notes;
            });
        }
    }

    public void setAdapter(List<Notes> notes) {
        mainBinding.notesRecycler.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        adapter = new NotesAdapter(this, notes);
        mainBinding.notesRecycler.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_notes, menu);
        MenuItem menuItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        assert searchView != null;
        searchView.setQueryHint("Search Notes...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                NotesFilter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    public void NotesFilter(String newText) {
        ArrayList<Notes> FilterNames = new ArrayList<>();

        for (Notes notes : this.filterNotesAllList) {
            if (notes.notesTitle.contains(newText) || notes.notesSubtitle.contains(newText)) {
                FilterNames.add(notes);
            }
        }
        this.adapter.searchNotes(FilterNames);
    }
}