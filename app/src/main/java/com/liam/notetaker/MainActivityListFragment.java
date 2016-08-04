package com.liam.notetaker;


import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivityListFragment extends ListFragment {

    private static final String TAG="Debug";
    private ArrayList<Note> notes;
    private NoteAdapter noteAdapter;
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG,"MainActivityListFragment onActivityCreated method fired");
        NoteTakerDBAdapter dbAdapter=new NoteTakerDBAdapter(getActivity().getBaseContext());

        dbAdapter.open();
        notes=dbAdapter.getAllNotes();

        dbAdapter.close();

        noteAdapter=new NoteAdapter(getActivity(),notes);

        setListAdapter(noteAdapter);

        registerForContextMenu(getListView());
    }

    @Override
    public void onListItemClick(ListView l,View v,int position,long id){
        super.onListItemClick(l,v,position,id);

        launchNoteDetailActivity(MainActivity.FragmentToLaunch.VIEW,position);

    }

    //Allows to create a context menu for our fragment
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu,v,menuInfo);

        MenuInflater menuInflater=getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.long_press_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){

        //give me the position of whatever item i long pressed on
        AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int rowPosition=info.position;
        Note note=(Note)getListAdapter().getItem(rowPosition);
        //returns to us the id of whatever menu item we selected
        switch (item.getItemId()){
            case R.id.edit:
                launchNoteDetailActivity(MainActivity.FragmentToLaunch.EDIT,rowPosition);
                Log.d("Menu clicks", "We pressed edit");
                return true;
            case  R.id.delete:
                Log.d("Menu clicks", "We pressed delete");
                NoteTakerDBAdapter dbAdapter=new NoteTakerDBAdapter(getActivity().getBaseContext());
                dbAdapter.open();
                dbAdapter.deleteNote(note.getId());

                notes.clear();
                notes.addAll(dbAdapter.getAllNotes());
                noteAdapter.notifyDataSetChanged();

                dbAdapter.close();
        }

        return super.onContextItemSelected(item);
    }

    private void launchNoteDetailActivity(MainActivity.FragmentToLaunch ftl,int position){

        //grab note info associated with the note we clicked on
        Note note=(Note)getListAdapter().getItem(position);

        //Create a new intent
        Intent intent=new Intent(getActivity(),NoteDetailActivity.class);

        //pass along the information of the note
        intent.putExtra(MainActivity.NOTE_TITLE_EXTRA, note.getTitle());
        intent.putExtra(MainActivity.NOTE_MESSAGE_EXTRA, note.getMessage());
        intent.putExtra(MainActivity.NOTE_CATEGORY_EXTRA, note.getCategory());
        intent.putExtra(MainActivity.NOTE_ID_EXTRA, note.getId());

        switch (ftl){
            case VIEW:
                intent.putExtra(MainActivity.NOTE_FRAGMENT_TO_LOAD_EXTRA,MainActivity.FragmentToLaunch.VIEW);
                break;
            case EDIT:
                intent.putExtra(MainActivity.NOTE_FRAGMENT_TO_LOAD_EXTRA,MainActivity.FragmentToLaunch.EDIT);
                break;
        }
        startActivity(intent);
    }

}
