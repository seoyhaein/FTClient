package ai.icg.ftclient.fc;

import ai.icg.ftclient.model.FCConfigModel;
import ai.icg.ftclient.model.FileMetaDataModel;
import javafx.util.Pair;
import unlimited.fc.client.api.FCClient;
import unlimited.fc.client.api.FileListData;
import unlimited.fc.client.api.TransferHook;
import unlimited.fc.client.api.TransferMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FC {

    public List<Pair<Integer,TransferHook>> list = null;

    public Pair<UUID,FCClient> CreateFCClient(FCConfigModel model) throws IOException {

        FCClient fc = new FCClient(model.getIp(), model.getPort());
        fc.setShowConsoleLog(model.getShowConsoleLog());

        fc.initialize();
        fc.setAutoResume(model.getAutoResume());

        fc.connect();
        fc.login(model.getId(), model.getPwd());
        fc.setMode(model.getTransferMode());

        fc.setStartRate(model.getStartRate());
        fc.setTargetRate(model.getTargetRate());

        fc.setVerifyIntegrity(model.getVerifyIntegrity());

        return new Pair<>(UUID.randomUUID(),fc);

    }

    public List<Pair<UUID,TransferHook>> FileUpload(Pair<UUID,FCClient> pair, FileMetaDataModel model){

        List<Pair<UUID,TransferHook>> list = new ArrayList<>();

        for (FileMetaDataModel.FileInfo n : model.FileList) {
            try {
                list.add(new Pair<>(pair.getKey(), pair.getValue().upload(n.filepath)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    //TODO 파일전송 진행 로그
    public void FcClientVerbose(){

    }

    public void FcClose(FCClient fcc) throws IOException {
        FCClient fc = fcc;
        fc.disconnect();
        // clean up existing connection.
        fc.finish();
        // Ask all the worker pools to close up.  We're done all transfers.
        fc.finishAll();
    }
}
