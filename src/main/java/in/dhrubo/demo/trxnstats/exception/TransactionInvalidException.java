package in.dhrubo.demo.trxnstats.exception;

import in.dhrubo.demo.trxnstats.response.AppResponse;

public class TransactionInvalidException extends Throwable {

  private AppResponse exceptionResponse = new AppResponse(AppResponse.JSON_INVALID_FIELDS_CODE,"Invalid Transaction",null);

  public TransactionInvalidException(){
    super();
  }

  public TransactionInvalidException(String message){
    super(message);
  }

  public AppResponse getExceptionResponse() {
    return exceptionResponse;
  }
}
