package kfs.kfsusers.service.impl;

/**
 *
 * @author pavedrim
 */
public class KfsUsersException extends RuntimeException {
    public KfsUsersException(String msg) {
        super(msg);
    }
    public KfsUsersException(String msg, Throwable ex) {
        super(msg, ex);
    }
}
