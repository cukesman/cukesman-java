package cukesman.reporter;

import cukesman.reporter.model.ExecutionReport;
import feign.RequestLine;

public interface CukesmanReportAPI {

    @RequestLine("PUT /executions/report")
    void reportExecution(final ExecutionReport executionReport);

}
