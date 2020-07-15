package in.dhrubo.demo.trxnstats.controller;

import in.dhrubo.demo.trxnstats.bo.Statistics;
import in.dhrubo.demo.trxnstats.bo.Transaction;
import in.dhrubo.demo.trxnstats.exception.TransactionInvalidException;
import in.dhrubo.demo.trxnstats.response.AppResponse;
import in.dhrubo.demo.trxnstats.service.TrxnService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TrxnController {

  private TrxnService trxnService = new TrxnService();

  @GetMapping("/")
  public String index(){
    return "message: \"This is Transaction statistics app , please explore other APIs.\"";
  }

  @PostMapping("/transaction")
  public ResponseEntity<AppResponse> addTransactions(@RequestBody Transaction trxn) throws TransactionInvalidException {
    AppResponse res = trxnService.addTrxn(trxn);
    System.out.println("Transaction : " + trxn.getHashId() + " is processed.");
    return ResponseEntity.ok(res);
  }

  @GetMapping("/statistics")
  public ResponseEntity<Statistics> getStatistics(){
    Statistics stat = trxnService.getStatistics();
    return ResponseEntity.ok(stat);
  }

  @DeleteMapping("/transaction")
  public ResponseEntity<AppResponse> removeTransaction(@RequestBody Transaction trxn){
    AppResponse res = trxnService.removeTrxn(trxn);
    return ResponseEntity.ok(res);
  }

  @DeleteMapping("/transactions")
  public ResponseEntity<AppResponse> removeTransactions(){
    AppResponse res =  trxnService.removeAllTrxn();
    return ResponseEntity.ok(res);
  }
}
