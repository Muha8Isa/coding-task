package com.minnity.report;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ReportService {

  //task 1: Return number of requests that were made for each company. (e.g. companyId -> requestNumber)
  public Map<Integer, Long> calculateNumberOfRequestsPerCompany(List<RequestLog> requestLogs) {
    // throw new NotImplementedException("TODO implement this method");

    Map<Integer, Long> numberOfRequests = new HashMap<>(); /** I created an empty hashmap to store the number of requests. **/

    for (RequestLog reqLog : requestLogs) { /** Iterate through the request logs **/
      int id = reqLog.getCompanyId(); /** Get the company id **/
      numberOfRequests.put(id, numberOfRequests.getOrDefault(id, 0L) + 1); /** Increase request count by 1 for every request **/
    }
    return numberOfRequests;
  }

  //task 2: Count and return requests per company that finished with an error HTTP response code (>=400)
  public Map<Integer, RequestLog> findRequestsWithError(List<RequestLog> requestLogs) {
    // throw new NotImplementedException("TODO implement this method");
    Map<Integer, RequestLog> numberOfErrors = new HashMap<>();
    requestLogs.stream()
            .filter(n -> n.getRequestStatus() >= 400)
            .forEach(n -> numberOfErrors.put(n.getCompanyId(), n));
    return numberOfErrors;
  }

  //task 3: find and print API (requests path) that on average takes the longest time to process the request.
  public String findRequestPathWithLongestDurationTime(List<RequestLog> requestLogs) {
    // throw new NotImplementedException("TODO implement this method");

    RequestLog longestRequestPath = null;
    long longestTime = Long.MIN_VALUE;

    for (RequestLog reqLog : requestLogs) {
      long duration = reqLog.getRequestDuration();
      if (duration > longestTime) longestTime = duration;
      longestRequestPath = reqLog;
    }
    System.out.println("Request duration = " + longestRequestPath.getRequestDuration() + " ms, "
            + "Request path: " + longestRequestPath.getRequestPath());
    return longestRequestPath.getRequestPath();
  }
}
