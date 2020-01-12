package com.nariman.filemanager.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FolderModel {

    private String fullPath;
    private String name;
    private List<FolderModel> folders = new ArrayList<>();
    private List<FileModel> files = new ArrayList<>();

    public FolderModel(String name){
        this.name = name;
    }

}

