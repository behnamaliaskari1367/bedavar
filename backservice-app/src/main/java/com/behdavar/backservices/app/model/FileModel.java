package com.behdavar.backservices.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileModel {

    private int id;
    private String name;
    @JsonIgnore
    private FileModel parent;
    @JsonIgnore
    private Path path;
    @JsonIgnore
    private List<FileModel> children;
    @JsonIgnore
    private boolean isDirectory;

    public FileModel() {
    }

    public FileModel(int id, FileModel parent, Path path, boolean isDirectory) {
        this.id = id;
        this.parent = parent;
        this.path = path;
        this.isDirectory = isDirectory;
    }

    public static FileModel ofFile(int id, FileModel parent, Path path){
        return new FileModel(id, parent, path, false);
    }

    public static FileModel ofDirectory(int id, FileModel parent, Path path){
        return new FileModel(id, parent, path, true);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public FileModel getParent() {
        return parent;
    }

    public void setParent(FileModel parent) {
        this.parent = parent;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public List<FileModel> getChildren() {
        if(Objects.isNull(children)){
            children = new ArrayList<>();
        }
        return children;
    }

    public void setChildren(List<FileModel> children) {
        this.children = children;
    }

    public boolean isDirectory() {
        return isDirectory;
    }

    public void setDirectory(boolean directory) {
        isDirectory = directory;
    }

    public String getName() {
        return path.getFileName().toString();
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileModel model = (FileModel) o;
        return id == model.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
