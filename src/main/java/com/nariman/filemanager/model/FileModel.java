package com.nariman.filemanager.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileModel {

    private String name;
    private long size;
}
