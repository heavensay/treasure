package com.helix.datatrail.exception;

/**
 * @author lijianyu
 * @date 2019/1/14 11:09
 */
public class DataTrailException extends RuntimeException{

    public DataTrailException(String message) {
        super(message);
    }

    public DataTrailException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataTrailException(Throwable cause) {
        super(cause);
    }


}
