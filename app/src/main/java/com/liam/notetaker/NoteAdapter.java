package com.liam.notetaker;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by liam on 18-Jul-16.
 */
public class NoteAdapter extends ArrayAdapter<Note> {

    //this class holds all the ref of each note
    public static class ViewHolder{
        TextView title;
        TextView note;
        ImageView noteIcon;
    }

    public NoteAdapter(Context context,ArrayList<Note> notes){
        super(context,0,notes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        //get the data item for this position
        Note note=getItem(position);

        ViewHolder viewHolder;

        //check if an existing view is being reused, otherwise inflate a new view from custom layout
        if (convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.list_row,parent,false);

            viewHolder=new ViewHolder();

            viewHolder.title=(TextView)convertView.findViewById(R.id.listItemNoteTitle);
            viewHolder.note=(TextView)convertView.findViewById(R.id.listItemNoteBody);
            viewHolder.noteIcon=(ImageView)convertView.findViewById(R.id.listItemNoteImg);

            //use set tag to remember our view holder which is holding our references to our widgets
            convertView.setTag(viewHolder);
        }else{
            //we already have a view so just go to our view holder and grab the widgets from it
            viewHolder=(ViewHolder)convertView.getTag();
        }

        //fill each new ref views with data
        viewHolder.title.setText(note.getTitle());
        viewHolder.note.setText(note.getMessage());
        viewHolder.noteIcon.setImageResource(note.getAssociatedDrawable());


        //return the view
        return convertView;
    }
}
