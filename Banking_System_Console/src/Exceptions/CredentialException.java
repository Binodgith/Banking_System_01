package Exceptions;

public class CredentialException extends Exception{
    CredentialException(){
        super();
    }

    public CredentialException(String message){
        super(message);
    }
}
