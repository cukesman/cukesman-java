package cukesman.reporter;

import cukesman.reporter.model.ExecutionReport;
import feign.RequestLine;

public interface CukesmanReportAPI {

    @RequestLine("POST /executions/report")
    void reportExecution(final ExecutionReport executionReport);

}
