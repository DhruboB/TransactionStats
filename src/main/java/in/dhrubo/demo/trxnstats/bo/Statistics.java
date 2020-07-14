package in.dhrubo.demo.trxnstats.bo;

import org.springframework.scheduling.annotation.Async;

import java.math.BigDecimal;
import java.util.*;

public class Statistics {

  private BigDecimal sum = new BigDecimal(0.0);
  private BigDecimal avg = new BigDecimal(0.0);
  private BigDecimal max = new BigDecimal(0.0);
  private BigDecimal min = new BigDecimal(0.0);
  private long count;

  public Statistics() {
    super();
  }

  public Statistics(BigDecimal sum, BigDecimal avg, BigDecimal max, BigDecimal min, long count) {
    new Statistics();
    this.sum = sum;
    this.avg = avg;
    this.max = max;
    this.min = min;
    this.count = count;
  }

  public BigDecimal getSum() {
    return sum;
  }

  public void setSum(BigDecimal sum) {
    this.sum = sum;
  }

  public BigDecimal getAvg() {
    return avg;
  }

  public void setAvg(BigDecimal avg) {
    this.avg = avg;
  }

  public BigDecimal getMax() {
    return max;
  }

  public void setMax(BigDecimal max) {
    this.max = max;
  }

  public BigDecimal getMin() {
    return min;
  }

  public void setMin(BigDecimal min) {
    this.min = min;
  }

  public long getCount() {
    return count;
  }

  public void setCount(long count) {
    this.count = count;
  }

  @Async
  public void calculate(List<Transaction> transactions)
  {
    if(transactions.size() == 0) {
      resetStats();
      return;
    }
    DoubleSummaryStatistics stat = transactions.stream().mapToDouble(Transaction::getAmount)
        .summaryStatistics();
    sum = new BigDecimal(stat.getSum());
    avg = new BigDecimal(stat.getAverage());
    max = new BigDecimal(stat.getMax());
    min = new BigDecimal(stat.getMin());
    count = stat.getCount();
  }

  public void resetStats(){
    sum = new BigDecimal(0);
    avg = new BigDecimal(0);
    max = new BigDecimal(0);
    min = new BigDecimal(0);
    count = 0;
  }
}
