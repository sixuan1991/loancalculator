package exceptions;

import java.io.IOException;

public class CorruptedDataException extends IOException {
    public CorruptedDataException(String message) {
        super(message);
    }
}
