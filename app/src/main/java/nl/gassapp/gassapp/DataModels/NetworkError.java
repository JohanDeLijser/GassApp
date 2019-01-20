package nl.gassapp.gassapp.DataModels;

public class NetworkError {

    public static final Integer NETWORK_ERROR = 0;
    public static final Integer OK = 200;
    public static final Integer BAD_REQUEST = 400;
    public static final Integer NOT_AUTHORIZED = 401;
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
