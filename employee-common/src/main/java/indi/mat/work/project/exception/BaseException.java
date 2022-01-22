package indi.mat.work.project.exception;

/**
 * @author Mat
 * @version : BaseException, v 0.1 2022-01-22 18:36 Yang
 */
public class BaseException extends RuntimeException{

    private String[] args;

    public BaseException(String message) {
         super(message);
    }

    public BaseException(String message, String... args) {
        super(message);
        this.args = args;
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }


    public BaseException(String message, Throwable cause, String... args) {
        super(message, cause);
        this.args = args;
    }




    public String[] getArgs(){
        if(args == null || args.length == 0){
            args = new String[1];
            args[0] = "";
        }

        return args;
    }


    public void setArgs(String[] args){
        this.args = args;
    }
}
