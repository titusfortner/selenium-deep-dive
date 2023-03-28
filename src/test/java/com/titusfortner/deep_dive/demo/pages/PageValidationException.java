package test.java.com.titusfortner.deep_dive.demo.pages;

public class PageValidationException extends RuntimeException {
    public PageValidationException(String message) {
        super(message);
    }
}
