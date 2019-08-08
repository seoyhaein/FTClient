package ai.icg.ftclient.model;

import java.util.ArrayList;
import java.util.List;

public class FileMetaDataModel {

    public final class FileInfo{
        public String filename;
        public String filepath;

        public FileInfo(String filename, String filepath) {
            this.filename = filename;
            this.filepath = filepath;
        }
    }

    public List<FileInfo> FileList = null;

    public List<FileInfo> AddFileToList(FileInfo fileInfo){

        FileList = new ArrayList<FileInfo>();
        FileList.add(fileInfo);

        return FileList;
    }
}

