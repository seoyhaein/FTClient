package ai.icg.ftclient.fc;

import unlimited.fc.client.api.TransferMode;
import java.util.Queue;
import java.util.concurrent.*;

public class FCTaskMonitor {

    private ExecutorService executorService = Executors.newFixedThreadPool(5);
    private final ExecutorCompletionService<String> completionService = new ExecutorCompletionService<String>(executorService);
    private LinkedBlockingQueue<FCTasks> taskLinkedBlockingQueue = new LinkedBlockingQueue<FCTasks>();

    //TODO for debugging
    private FCConfig fcConfig = populateconfigmodel();
   // private FileList fileList = populatefilelistA();
    //**

    // 동시에 수행할 수 있는 테스크의 수
    private final int MaxTasks = 3;
    // 현재 수행중인 테스크 수
    private int CurrentTasks = 0;

    Future<String> future = null;

    public int getFileList(Queue<FileList> fileInfoList) {
        return fileInfoList.size();
    }

    public int getAvailableTask() {
        return MaxTasks - CurrentTasks;
    }

    public void AddCurrentTasks() {
        if (CurrentTasks > MaxTasks)
            return;
        CurrentTasks++;
    }

    public void RmCurrentTasks() {
        if (CurrentTasks < 0)
            return;
        CurrentTasks--;
    }

    // 최초 실행
    /*public int RunTask(Queue<FileList.FileInfo> fileInfoList) throws InterruptedException, ExecutionException {
        FileList.FileInfo fileInfo = null;
        if (getAvailableTask() > 0) {
            while (getAvailableTask() == 0) {

                fileInfo = fileInfoList.poll();

                if (fileInfo == null)
                    return CurrentTasks;
                else {
                    FCTasks fcTask = new FCTasks(fileInfo.filepath);
                    completionService.submit(fcTask, fcTask.getFilepath());
                    AddCurrentTasks();

                    future = completionService.take();
                    RmCurrentTasks();
                    FCTasks task = taskLinkedBlockingQueue.poll();
                    //RmCurrentTasks();
                    // 모든 task 수행 완료
                    if (task == null && future == null)
                        break;
                        // que 에 아직 남아있는 경우
                    else if (task != null && future != null) {
                        completionService.submit(task, task.getFilepath());
                        System.out.println(future.get());
                        AddCurrentTasks();
                    }
                    // que 를 다 비운 경우
                    else if (task == null && future != null) {
                        System.out.println(future.get());
                    }

                    return CurrentTasks;
                }
            }
        } else {
            fileInfo = null;
            for (int i = 0; i < fileInfoList.size(); i++) {
                fileInfo = fileInfoList.poll();
                if (fileInfo == null)
                    return CurrentTasks;
                else {
                    FCTasks fcTask = new FCTasks(fileInfo.filepath);
                    taskLinkedBlockingQueue.add(fcTask);
                    return CurrentTasks;
                }
            }
            //return CurrentTasks;
        }

        return CurrentTasks;
    }*/

    //TODO Boolean 으로 고쳐야함.
    public void RunTask(Queue<FileList> fileInfoList) throws InterruptedException, ExecutionException {
        FileList fileInfo = null;

        if (getAvailableTask() > 0) {
            //TODO while 구문 고쳐야함.
            while (getAvailableTask() > 0) {

                fileInfo = fileInfoList.poll();

                if (fileInfo == null)
                   return;
                else {
                    FCTasks fcTask = new FCTasks(fileInfo.filepath);
                    completionService.submit(fcTask, fcTask.getFilepath());
                    AddCurrentTasks();

                    future = completionService.take();
                    RmCurrentTasks();
                    FCTasks task = taskLinkedBlockingQueue.poll();
                    //RmCurrentTasks();
                    // 모든 task 수행 완료
                    if (task == null && future == null)
                        return;
                    // que 에 아직 남아있는 경우
                    else if (task != null && future != null) {
                        completionService.submit(task, task.getFilepath());
                        System.out.println(future.get());
                        AddCurrentTasks();
                    }
                    // que 를 다 비운 경우
                    else if (task == null && future != null) {
                        System.out.println(future.get());
                    }
                }
            }
        } else {
            fileInfo = null;
            for (int i = 0; i < fileInfoList.size(); i++) {
                fileInfo = fileInfoList.poll();
                if (fileInfo == null)
                    return;
                else {
                    FCTasks fcTask = new FCTasks(fileInfo.filepath);
                    taskLinkedBlockingQueue.add(fcTask);
                }
            }
        }
    }

    //TODO for debugging
    public void RunforDebug() throws ExecutionException, InterruptedException {
       // RunTask(fileList.FileInfoList);
    }

    public FCConfig populateconfigmodel() {
        FCConfig fccmodel = new FCConfig();

        fccmodel.setIp("15.164.166.2");
        fccmodel.setPort(991);
        fccmodel.setShowConsoleLog(true);
        fccmodel.setAutoResume(true);
        fccmodel.setId("ichthys");
        fccmodel.setPwd("123");
        fccmodel.setTransferMode(TransferMode.AUTO);
        fccmodel.setStartRate(10000);
        fccmodel.setTargetRate(10000);
        fccmodel.setVerifyIntegrity(true);
        fccmodel.setAutoResume(true);

        return fccmodel;
    }

    public void ShutDown() {
        executorService.shutdown();
    }
}
