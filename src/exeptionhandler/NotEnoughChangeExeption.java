package exeptionhandler;

public class NotEnoughChangeExeption extends RuntimeException{
    private String message;

    public NotEnoughChangeExeption(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
