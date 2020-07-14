package in.dhrubo.demo.trxnstats.cache;

import in.dhrubo.demo.trxnstats.bo.Statistics;
import in.dhrubo.demo.trxnstats.bo.Transaction;
import in.dhrubo.demo.trxnstats.exception.TransactionInvalidException;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableScheduling
public class TrxnCache {

  private static Statistics statistics = new Statistics();
  private long statsBasedOnSeconds = 600000;

  private static final List<Transaction> transactions = new ArrayList<>();

  public Transaction addTrxn(Transaction trxn) throws TransactionInvalidException {

    if ((System.currentTimeMillis() - trxn.getTimestamp().getTime()) > statsBasedOnSeconds) {
      return trxn;
    }
    synchronized (statistics) {
      trxn.setValid(true);
      this.transactions.add(trxn);
      statistics.calculate(transactions);
    }
    return trxn;
  }

  public List<Transaction> getTransactions() {
    return transactions;
  }

  public Transaction removeTrxn(Transaction trxn){
    synchronized (statistics) {
      this.transactions.remove(trxn.getHashId());
      statistics.calculate(transactions);
    }
    return trxn;
  }

  public void removeAllTrxn(){
    synchronized (statistics) {
      transactions.clear();
      statistics.resetStats();
    }
  }

  public Statistics getStatistics() {
    return statistics;
  }

  @Scheduled(fixedRate = 60000)
  public void filterCache() {
    synchronized (statistics) {
      System.out.println("Number of transactions B4 filtering ... " + transactions.size());
      transactions.removeIf(
          transcation -> (System.currentTimeMillis() - transcation.getTimestamp().getTime()) > statsBasedOnSeconds);
      System.out.println("Number of transactions after filtering ... " + transactions.size());
      statistics.calculate(transactions);
    }
  }
}
