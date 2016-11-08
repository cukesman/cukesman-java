package cukesman.reporter;

import cukesman.reporter.model.ExecutionReport;
import feign.Headers;
import feign.RequestLine;

@Headers("Accept: application/json;charset=UTF-8")
public interface CukesmanReportAPI {

    @RequestLine("POST /executions/report")
    @Headers("Content-Type: application/json;charset=UTF-8")
    void reportExecution(final ExecutionReport executionReport);

}
