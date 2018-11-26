package edu.ut.verify.example;

/**
 * Created by Jerry Wang on 2018/11/26.
 */
public class ResultMsg {

    private PathStatus pathStatus;

    private String returnMoney;

    public ResultMsg(){
        this.pathStatus = new PathStatus();
    }

    public boolean addPath(String path){
        return pathStatus.addPath(path);
    }

    public PathStatus getPathStatus() {
        return pathStatus;
    }

    public void setPathStatus(PathStatus pathStatus) {
        this.pathStatus = pathStatus;
    }

    public String getReturnMoney() {
        return returnMoney;
    }

    public void setReturnMoney(String returnMoney) {
        this.returnMoney = returnMoney;
    }
}
