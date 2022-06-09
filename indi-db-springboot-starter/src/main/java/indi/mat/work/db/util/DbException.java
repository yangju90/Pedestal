package indi.mat.work.db.util;

/**
 * @author Mat
 * @version : DbException, v 0.1 2022-06-10 0:22 Yang
 */
public class DbException extends RuntimeException{
    public DbException() {
        super();
    }

    public DbException(String message) {
        super(message);
    }
}
