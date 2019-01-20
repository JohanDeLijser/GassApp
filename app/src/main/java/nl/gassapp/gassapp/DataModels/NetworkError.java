package nl.gassapp.gassapp.DataModels;

/**
 * NetworkError Modal
 *
 * Contains the api response code
 * contains a message to be shown the to user
 */
public class NetworkError {

    //CONST response codes
    public static final Integer OK = 200;
    public static final Integer BAD_REQUEST = 400;
    public static final Integer NOT_AUTHORIZED = 401;
    public static final Integer NOT_FOUND = 404;
    public static final Integer SERVER_ERROR = 500;

    private Integer code;
    private String message;

    public NetworkError(Integer code, String message) {

        this.code = code;
        this.message = message;

    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
