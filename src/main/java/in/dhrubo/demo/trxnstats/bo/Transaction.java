package in.dhrubo.demo.trxnstats.bo;

import com.google.common.hash.Hashing;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Objects;

public class Transaction {

  private double amount = 0.0;
  private Date timestamp;
  private String hash;
  private boolean isValid = false;
  private SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS'Z'");

  public Transaction(){
    super();
  }

  public Transaction(double amount, Date timestamp) {
    super();
    this.amount = amount;
    this.timestamp = timestamp;
    this.hash = getHashId();
  }

  public double getAmount() {
    return amount;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public String getHashId(){
    if(hash != null){
      return this.hash;
    }
    hash = Hashing.sha256()
        .hashString( ""+this.timestamp+this.amount, StandardCharsets.UTF_8)
        .toString();
    return hash;
  }

  public boolean isValid() {
    return isValid;
  }

  public void setValid(boolean valid) {
    isValid = valid;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Transaction that = (Transaction) o;
    return Objects.equals(amount, that.amount) &&
        Objects.equals(timestamp, that.timestamp);
  }

  @Override
  public int hashCode() {
    return Objects.hash(amount, timestamp);
  }

  @Override
  public String toString() {
    return "{" +
        "\"amount\"=\"" + amount + "\"," +
        "\"timestamp\"=\""  + output.format(timestamp) + "\"," +
        "\"hash\"=\""  + hash + "\"" +
        "}";
  }
}
