package com.bm.transfer.account.exception;

public class PasswordException extends RuntimeException{

    public PasswordException(){
        super();
    }
    public PasswordException(String msg){
        super(msg);
    }
}
