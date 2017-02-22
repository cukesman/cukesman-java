package cukesman.reporter;

import cukesman.reporter.model.ExecutionReport;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

@Headers("Accept: application/json;charset=UTF-8")
public interface CukesmanReportAPI {

    @RequestLine("POST /executions/report")
    @Headers("Content-Type: application/json;charset=UTF-8")
    void reportExecution(final ExecutionReport executionReport);

    @RequestLine("POST /executions/oneoff/{id}/report")
    @Headers("Content-Type: application/json;charset=UTF-8")
    void reportOneOffExecution(@Param("id") final String id, final ExecutionReport executionReport);

}
