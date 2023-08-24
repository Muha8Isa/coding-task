package com.minnity.report;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class ReportServiceTest {

  List<RequestLog> requestLogs;
  ReportService reportService;

  @Before
  public void setup() {
    requestLogs = new ArrayList<>();
    RequestLog sampleRequest1 = RequestLog.RequestLogBuilder.aRequestLog()
            .withCompanyId(1).withRequestStatus(201).withRequestDuration(200).withRequestPath("api/user").build();
    RequestLog sampleRequest2 = RequestLog.RequestLogBuilder.aRequestLog()
            .withCompanyId(1).withRequestStatus(404).withRequestDuration(150).withRequestPath("api/user").build();
    RequestLog sampleRequest3 = RequestLog.RequestLogBuilder.aRequestLog()
            .withCompanyId(2).withRequestStatus(201).withRequestDuration(100).withRequestPath("api/admin").build();
    RequestLog sampleRequest4 = RequestLog.RequestLogBuilder.aRequestLog()
            .withCompanyId(3).withRequestStatus(500).withRequestDuration(300).withRequestPath("api/person").build();
    requestLogs.add(sampleRequest1);
    requestLogs.add(sampleRequest2);
    requestLogs.add(sampleRequest3);
    requestLogs.add(sampleRequest4);
  }

  @Test
  public void calculateNumberOfRequestsPerCompanyTest() {
    reportService = new ReportService();
    Map<Integer, Long> test = reportService.calculateNumberOfRequestsPerCompany(requestLogs);
    long expected1 = 2;
    long expected2 = 1;
    assertEquals(expected1, test.get(1).longValue());
    assertEquals(expected2, test.get(2).longValue());
    assertEquals(expected2, test.get(3).longValue());
  }

  @Test
  public void findRequestsWithErrorTest() {
    reportService = new ReportService();
    Map<Integer, RequestLog> test = reportService.findRequestsWithError(requestLogs);
    for (RequestLog reqLog : requestLogs) {
      int id = reqLog.getCompanyId();
      if (reqLog.getRequestStatus() >= 400) {
        assertTrue(test.containsKey(id));
        System.out.println("Company id: " + id + " request: " + reqLog.getRequestStatus() + ": has error: " + reqLog);
      } else System.out.println("Company id: " + id + " request: " + reqLog.getRequestStatus() + ": has no error");
    }
  }

  @Test
  public void findRequestPathWithLongestDurationTimeTest() {
    RequestLog longestRequestPath = null;
    long longestTime = Long.MIN_VALUE;

    for (RequestLog reqLog : requestLogs) {
      long duration = reqLog.getRequestDuration();
      if (duration > longestTime) longestTime = duration;
      longestRequestPath = reqLog;
    }
    System.out.println("Request duration = " + longestRequestPath.getRequestDuration() + " ms, "
            + "Request path: " + longestRequestPath.getRequestPath());
  }

} //