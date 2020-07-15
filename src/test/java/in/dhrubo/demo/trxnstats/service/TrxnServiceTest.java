package in.dhrubo.demo.trxnstats.service;

import in.dhrubo.demo.trxnstats.bo.Statistics;
import static org.junit.jupiter.api.Assertions.assertEquals;

import in.dhrubo.demo.trxnstats.bo.Transaction;
import in.dhrubo.demo.trxnstats.response.AppResponse;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TrxnServiceTest {

  @Test
  void testGetStatistics_withoutTrxn() {
    TrxnService trxnService = new TrxnService();
    Statistics stats = trxnService.getStatistics();
    assertEquals(0,stats.getCount());
    assertEquals(new BigDecimal(0).setScale(2, RoundingMode.HALF_UP),stats.getAvg());
    assertEquals(new BigDecimal(0).setScale(2, RoundingMode.HALF_UP),stats.getMax());
    assertEquals(new BigDecimal(0).setScale(2, RoundingMode.HALF_UP),stats.getMin());
    assertEquals(new BigDecimal(0).setScale(2, RoundingMode.HALF_UP),stats.getSum());
  }

  @Test
  void testGetStatistics_withInValidTrxn() throws ParseException {
    TrxnService trxnService = new TrxnService();
    String stringDate = "2020-07-15T15:29:15.953Z";
    String format = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    final java.util.Date date = sdf.parse(stringDate);
    AppResponse res = trxnService.addTrxn(new Transaction(11.3343, new Date(date.getTime())));
    Statistics stats = trxnService.getStatistics();
    assertEquals(0,stats.getCount());
    assertEquals(new BigDecimal(0.00).setScale(2, RoundingMode.HALF_UP),stats.getAvg());
    assertEquals(new BigDecimal(0.00).setScale(2, RoundingMode.HALF_UP),stats.getMax());
    assertEquals(new BigDecimal(0.00).setScale(2, RoundingMode.HALF_UP),stats.getMin());
    assertEquals(new BigDecimal(0.00).setScale(2, RoundingMode.HALF_UP),stats.getSum());
  }

  @Test
  void testGetStatistics_withValidTrxn() throws ParseException {
    TrxnService trxnService = new TrxnService();
    // Need to change below time within range of 60 seconds of current time
    // to make the Transaction Valid hence  to pass the unit test case
    String stringDate = "2020-07-15T15:29:15.953Z";
    String format = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    final java.util.Date date = sdf.parse(stringDate);
    AppResponse res = trxnService.addTrxn(new Transaction(11.3343, new Date(date.getTime())));
    Statistics stats = trxnService.getStatistics();
    assertEquals(1,stats.getCount());
    assertEquals(new BigDecimal(11.3343).setScale(2, RoundingMode.HALF_UP),stats.getAvg());
    assertEquals(new BigDecimal(11.3343).setScale(2, RoundingMode.HALF_UP),stats.getMax());
    assertEquals(new BigDecimal(11.3343).setScale(2, RoundingMode.HALF_UP),stats.getMin());
    assertEquals(new BigDecimal(11.3343).setScale(2, RoundingMode.HALF_UP),stats.getSum());
  }

  // TODO there could be many edge cases for unit test cases, hence INCOMPLETE
}
