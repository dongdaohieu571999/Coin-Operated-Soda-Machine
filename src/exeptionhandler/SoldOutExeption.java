package exeptionhandler;

public class SoldOutExeption extends RuntimeException{
    private String message;

    public SoldOutExeption(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
