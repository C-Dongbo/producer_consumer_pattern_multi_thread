package src;

public class Result{
    private String input;
    private String result;
    private String status = "none";

    public Result(String input, String result, String status){
        this.input = input;
        this.result = result;
        this.status = status;
    }
    public String getInput(){
        return input;
    }
    public String getResult(){
        return result;
    }
    public String getStatus(){
        return status;
    }
}