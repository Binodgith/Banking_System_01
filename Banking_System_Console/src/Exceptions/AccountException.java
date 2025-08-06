package Exceptions;

public class AccountException extends Exception{
    AccountException(){
        super();
    }
    public AccountException(String message){
        super(message);
    }
}
