package in.dhrubo.demo.trxnstats.controller;

import in.dhrubo.demo.trxnstats.bo.Statistics;
import in.dhrubo.demo.trxnstats.bo.Transaction;
import in.dhrubo.demo.trxnstats.cache.TrxnCache;
import in.dhrubo.demo.trxnstats.exception.TransactionInvalidException;
import org.springframework.web.bind.annotation.*;

@RestController
public class TrxnController {

  private static TrxnCache trxnCache = new TrxnCache();

  @GetMapping("/")
  public String index(){
    return "message: \"This is Transaction statistics app , please explore other APIs.\"";
  }

  @PostMapping("/transaction")
  public Transaction putTransactions(@RequestBody Transaction trxn) throws TransactionInvalidException {
    trxnCache.addTrxn(trxn);
    System.out.println("Transaction : " + trxn.getHashId() + " is added.");
    return trxn;
  }

  @GetMapping("/statistics")
  public Statistics getStatistics(){
    return trxnCache.getStatistics();
  }

  @DeleteMapping("/transaction")
  public String removeTransaction(@RequestBody Transaction trxn){
    trxnCache.removeTrxn(trxn);
    return "message: \"Transaction " + trxn.getHashId() + " is removed. \"";
  }

  @DeleteMapping("/transactions")
  public String removeTransactions(){
    trxnCache.removeAllTrxn();
    return "message: \" All prior Transactions are removed. \"";
  }
}
