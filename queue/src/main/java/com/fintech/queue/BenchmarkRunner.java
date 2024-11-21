package com.fintech.queue;

import lombok.RequiredArgsConstructor;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.profile.GCProfiler;
import org.openjdk.jmh.results.RunResult;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class BenchmarkRunner {

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .output("C:\\Users\\Admin\\Desktop\\GitHub\\Industrial-development\\queue\\src\\main\\resources\\report.txt")
                .warmupIterations(1)
                .warmupTime(TimeValue.seconds(1))
                .measurementIterations(1)
                .measurementTime(TimeValue.seconds(2))
                .addProfiler(GCProfiler.class)
                .mode(Mode.Throughput)
                .mode(Mode.AverageTime)
                .forks(1)
                .shouldFailOnError(true).build();

        Collection<RunResult> results = new Runner(opt).run();

        BenchmarkReportService reportService = new BenchmarkReportService();
        reportService.writeReportToFile(results);
    }
}
