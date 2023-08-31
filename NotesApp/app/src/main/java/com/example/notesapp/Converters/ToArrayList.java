package com.example.notesapp.Converters;

import com.example.notesapp.pojo.NoteModel;

import java.util.ArrayList;
import java.util.List;

public class ToArrayList {

    public static ArrayList<NoteModel> convert(List<NoteModel> list){
        return new ArrayList<>(list);
    }
}
