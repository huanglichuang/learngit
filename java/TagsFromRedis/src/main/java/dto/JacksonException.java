package dto;

/**
 * Jackson的异常封装
 *
 * @author DAVID YIN
 * @date 2018/09/15
 */
public class JacksonException extends RuntimeException {

    public JacksonException() {
        super();
    }

    public JacksonException(String message, Throwable cause) {
        super(message, cause);
    }

    public JacksonException(String message) {
        super(message);
    }

    public JacksonException(Throwable cause) {
        super(cause);
    }

}
