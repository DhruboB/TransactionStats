package in.dhrubo.demo.trxnstats.service;

import in.dhrubo.demo.trxnstats.bo.Statistics;
import in.dhrubo.demo.trxnstats.bo.Transaction;
import in.dhrubo.demo.trxnstats.response.AppResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableScheduling
public class TrxnService {

  private Statistics statistics = Statistics.getInstance();
  private long statsBasedOnSeconds = 60000;

  // In memory cache to hold all transactions. TODO > can be improved
  private static final List<Transaction> transactions = new ArrayList<>();

  public AppResponse addTrxn(Transaction trxn) {
    AppResponse res = new AppResponse(AppResponse.INVALID_JSON_CODE,"Transaction JSON is not valid",null);
    if(trxn == null){
      return res;
    }
    if(trxn.getTimestamp() == null ){
      res.setCode(AppResponse.JSON_INVALID_FIELDS_CODE);
      res.setMessage("Transaction time is not valid");
      return res;
    }
    long timeGap = System.currentTimeMillis() - trxn.getTimestamp().getTime();
    if (timeGap < 0 ) {
      res.setCode(AppResponse.JSON_INVALID_FIELDS_CODE);
      res.setMessage("Transaction time is in future.");
      return res;
    }
    if (timeGap > statsBasedOnSeconds) {
      res.setCode(AppResponse.OLD_TRXN_CODE);
      res.setMessage("Transaction is older than " + statsBasedOnSeconds / 1000 + " seconds.");
      return res;
    }
    synchronized (statistics) {
      trxn.setValid(true);
      transactions.add(trxn);
      statistics.calculate(transactions);
    }
    res.setCode(AppResponse.SUCCESS_CODE);
    res.setMessage("Transaction is valid and considered for statistics since last "+ statsBasedOnSeconds / 1000 + " seconds.");
    return res;
  }

  public List<Transaction> getTransactions() {
    return transactions;
  }

  public AppResponse removeTrxn(Transaction trxn){
    AppResponse res = new AppResponse(AppResponse.OLD_TRXN_CODE,"Transaction is removed.",null);
    synchronized (statistics) {
      this.transactions.remove(trxn.getHashId());
      statistics.calculate(transactions);
    }
    return res;
  }

  public AppResponse removeAllTrxn(){
    AppResponse res = new AppResponse(AppResponse.OLD_TRXN_CODE,"Transaction is removed.",null);
    synchronized (statistics) {
      transactions.clear();
      statistics.resetStats();
    }
    return res;
  }

  public Statistics getStatistics() {
    return statistics;
  }

  @Scheduled(fixedRate = 60000)
  public void filterCache() {
    synchronized (statistics) {
      System.out.println("Number of transactions B4 clearing old records ... " + transactions.size());
      transactions.removeIf(
          transcation -> (System.currentTimeMillis() - transcation.getTimestamp().getTime()) > statsBasedOnSeconds);
      System.out.println("Number of transactions after clearing old records ... " + transactions.size());
      statistics.calculate(transactions);
    }
  }
}
