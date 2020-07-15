package in.dhrubo.demo.trxnstats.bo;

import org.springframework.scheduling.annotation.Async;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class Statistics {

  private static Statistics self = new Statistics(); ;
  private BigDecimal sum = new BigDecimal(0.0).setScale(2, RoundingMode.HALF_UP);
  private BigDecimal avg = new BigDecimal(0.0).setScale(2, RoundingMode.HALF_UP);
  private BigDecimal max = new BigDecimal(0.0).setScale(2, RoundingMode.HALF_UP);
  private BigDecimal min = new BigDecimal(0.0).setScale(2, RoundingMode.HALF_UP);
  private long count;

  private Statistics() {
    super();
  }

  public static Statistics getInstance(){
    return self;
  }

  public Statistics(BigDecimal sum, BigDecimal avg, BigDecimal max, BigDecimal min, long count) {
    self = getInstance();
    self.sum = sum.setScale(2, RoundingMode.HALF_UP);
    self.avg = avg.setScale(2, RoundingMode.HALF_UP);
    self.max = max.setScale(2, RoundingMode.HALF_UP);
    self.min = min.setScale(2, RoundingMode.HALF_UP);
    self.count = count;
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
    sum = new BigDecimal(stat.getSum()).setScale(2, RoundingMode.HALF_UP);
    avg = new BigDecimal(stat.getAverage()).setScale(2, RoundingMode.HALF_UP);
    max = new BigDecimal(stat.getMax()).setScale(2, RoundingMode.HALF_UP);
    min = new BigDecimal(stat.getMin()).setScale(2, RoundingMode.HALF_UP);
    count = stat.getCount();
  }

  public void resetStats(){
    sum = new BigDecimal(0).setScale(2, RoundingMode.HALF_UP);;
    avg = new BigDecimal(0).setScale(2, RoundingMode.HALF_UP);;
    max = new BigDecimal(0).setScale(2, RoundingMode.HALF_UP);;
    min = new BigDecimal(0).setScale(2, RoundingMode.HALF_UP);;
    count = 0;
  }
}
