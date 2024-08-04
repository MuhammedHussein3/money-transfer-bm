package com.bm.transfer.Favorite.exceptions;

public class FavoriteNotFoundException extends RuntimeException{

    public FavoriteNotFoundException(){
        super();
    }

    public FavoriteNotFoundException(String msg){
        super(msg);
    }
}
