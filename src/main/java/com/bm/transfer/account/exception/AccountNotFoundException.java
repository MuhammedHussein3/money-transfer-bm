package com.bm.transfer.account.exception;

public class AccountNotFoundException extends RuntimeException{

    public AccountNotFoundException(){
        super();
    }
    public AccountNotFoundException(String msg){
        super(msg);
    }
}
