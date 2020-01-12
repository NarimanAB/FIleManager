package com.nariman.filemanager.controller;

import com.nariman.filemanager.model.FileModel;
import com.nariman.filemanager.model.FolderModel;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @Value("${root.path}")
    private String rootPath;

    @GetMapping(value = "/index")
    public FolderModel index(@NonNull @RequestParam String path) throws IOException {

        Path fullPath = getFullPath(path);

        List<FolderModel> folders = new ArrayList<>();
        List<FileModel> files = new ArrayList<>();

        try (Stream<Path> paths = Files.list(fullPath)){
            paths.forEach(fileOrDir -> {
                if(Files.isRegularFile(fileOrDir)){
                    File file = fileOrDir.toFile();
                    files.add(
                            new FileModel(file.getName(), file.length())
                    );
                } else if (Files.isDirectory(fileOrDir)){
                    File file = fileOrDir.toFile();
                    folders.add(new FolderModel(file.getName()));
                }
            });
        }

        FolderModel folderModel = new FolderModel();

        String relativePath = fullPath.toFile().getAbsolutePath().replace(rootPath, "").replace("\\","/");

        if(relativePath.isEmpty()) relativePath = "/";

        folderModel.setFullPath(relativePath);
        folderModel.setName(fullPath.toFile().getName());

        folderModel.setFolders(folders);
        folderModel.setFiles(files);

        return folderModel;
    }

    private Path getFullPath(String pathString)
            throws IOException {
        if(pathString.contains("\\")){
            throw new RuntimeException("\\ symbol is not allowed");
        }

        if(!pathString.startsWith("/")){
            throw new RuntimeException("path must start with /");
        }

        if (File.separatorChar != '/'){
            pathString = pathString.replace("/", "\\");
        }

        Path fullPath = Path.of(rootPath, pathString);

        if(!fullPath.startsWith(Path.of(rootPath).toRealPath())){
            throw new RuntimeException("cannot go upper than /");
        }
        return fullPath;
    }

}
