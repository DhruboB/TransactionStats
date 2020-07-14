package in.dhrubo.demo.trxnstats.exception;

public class TransactionInvalidException extends Throwable {

  private AppExceptionMsg appExceptionMsg = new AppExceptionMsg();

  public TransactionInvalidException(){
    super();
  }

  public TransactionInvalidException(String message){
    super(message);
    appExceptionMsg.setCode("APP001");
    appExceptionMsg.setMessage("Invalid Transaction");
  }

  public AppExceptionMsg getAppExceptionMsg() {
    return appExceptionMsg;
  }
}
