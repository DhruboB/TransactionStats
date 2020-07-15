package in.dhrubo.demo.trxnstats.response;

public class AppResponse {

  public static final String SUCCESS_CODE = "201";
  public static final String OLD_TRXN_CODE = "204";
  public static final String INVALID_JSON_CODE = "400";
  public static final String JSON_INVALID_FIELDS_CODE = "422";

  private String code = "";
  private String message = "";
  private Object data = "";

  public AppResponse(String code, String message, Object obj) {
    this.code = code;
    this.message = message;
    if(obj != null) {
      this.data = obj;
    }
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }
}
