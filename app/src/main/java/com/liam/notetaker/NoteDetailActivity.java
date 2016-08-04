package com.liam.notetaker;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class NoteDetailActivity extends AppCompatActivity {

    public static final String NEW_NOTE_EXTRA="new_note";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        createAndAddFragment();
    }

    private void createAndAddFragment(){

        //grab intent and fragment to lanch from our main activity list fragment
        Intent intent=getIntent();
        MainActivity.FragmentToLaunch fragmentToLaunch=
                (MainActivity.FragmentToLaunch) intent.getSerializableExtra(MainActivity.NOTE_FRAGMENT_TO_LOAD_EXTRA);

        //grabbing out fragment manger and our fragment transaction so that we can add our edit or view fragment dynmaicly
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();

        //choose the right fragment to load
        switch (fragmentToLaunch){
            case EDIT:
                //create a new fragment
                NoteEditFragment noteEditFragment=new NoteEditFragment();
                setTitle(R.string.edit_fragment_title);
                fragmentTransaction.add(R.id.note_container, noteEditFragment,"NOTE_EDIT_FRAGMENT");
                break;
            case VIEW:
                NoteViewFragment noteViewFragment=new NoteViewFragment();
                setTitle(R.string.viewFragmentTitle);
                fragmentTransaction.add(R.id.note_container, noteViewFragment,"NOTE_VIEW_FRAGMENT");
                break;

            case CREATE:
                NoteEditFragment noteCreateFragment=new NoteEditFragment();
                setTitle(R.string.create_fragment_title);
                Bundle bundle=new Bundle();
                bundle.putBoolean(NEW_NOTE_EXTRA, true);
                //associate bundle
                noteCreateFragment.setArguments(bundle);

                fragmentTransaction.add(R.id.note_container, noteCreateFragment,"NOTE_CREATE_FRAGMENT");
                break;
        }



        //make sure that the above happens
        fragmentTransaction.commit();
    }
}
