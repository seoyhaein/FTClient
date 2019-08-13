package ai.icg.ftclient.fc;

import java.util.LinkedList;
import java.util.Queue;

public class FileList {

    public Queue<FileInfo> FileInfoList = new LinkedList<FileInfo>();

    public final class FileInfo {

        public String filename;
        public String filepath;

        public FileInfo(String filename, String filepath) {
            this.filename = filename;
            this.filepath = filepath;
        }
    }

    public Queue<FileInfo> AddFileToList(FileInfo fileInfo) {

        FileInfoList.add(fileInfo);
        return FileInfoList;
    }
}

