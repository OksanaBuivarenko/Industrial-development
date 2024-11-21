package com.fintech.queue;

import com.fintech.queue.diagram.DiagramXlsx;
import com.fintech.queue.diagram.WordDiagram;
import org.openjdk.jmh.results.RunResult;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class BenchmarkReportService {

    HashMap<String, Double> throughputMap = new HashMap<>();

    public List<BenchmarkResult> getBenchmarkResultList(Collection<RunResult> results) {
        List<BenchmarkResult> benchmarkResultList = new ArrayList<>();

        for (RunResult result: results) {
            Boolean benchmarkContains = false;
            for (BenchmarkResult res: benchmarkResultList) {
                if (res.getBenchmarkLabel().equals(result.getParams().getBenchmark()
                        .replace("com.fintech.queue.rabbit.RabbitBenchmark.", "")
                        .replace("com.fintech.queue.kafka.KafkaBenchmark.", ""))) {
                    benchmarkContains = true;
                    setPrimaryResult(res, result);
                }
            }
            if (!benchmarkContains) {
                BenchmarkResult benchmarkResult = new BenchmarkResult();
                benchmarkResult.setBenchmarkLabel(result.getParams().getBenchmark()
                        .replace("com.fintech.queue.rabbit.RabbitBenchmark.", "")
                        .replace("com.fintech.queue.kafka.KafkaBenchmark.", ""));
                setPrimaryResult(benchmarkResult, result);
                benchmarkResultList.add(benchmarkResult);
            }
        }
        return benchmarkResultList;
    }

    public void setPrimaryResult(BenchmarkResult benchmarkResult, RunResult result) {
        if (result.getPrimaryResult().toString().contains("ops/s")) {
            benchmarkResult.setThroughput(result.getPrimaryResult().getScore());
            throughputMap.put(result.getParams().getBenchmark()
                    .replace("com.fintech.queue.rabbit.RabbitBenchmark.", "")
                    .replace("com.fintech.queue.kafka.KafkaBenchmark.", ""),
                    result.getPrimaryResult().getScore());
        }
        if (result.getPrimaryResult().toString().contains("s/op")) {
            benchmarkResult.setLatency(result.getPrimaryResult().getScore());
        }
    }

    public void getDiagram() {
        DiagramXlsx diagram = new DiagramXlsx();
        diagram.executeClusteredColumn(true, throughputMap);
        WordDiagram wordDiagram = new WordDiagram();
        wordDiagram.createDiagram(throughputMap);
        wordDiagram.convertTxtToPdf();
    }

    public void writeReportToFile(Collection<RunResult> results) {
        getBenchmarkResultList(results).forEach(benchmarkResult -> writeStringToFile(benchmarkResult.toString()));
    }

    public void writeStringToFile(String string) {
        try(FileWriter writer = new FileWriter("C:\\Users\\Admin\\Desktop\\GitHub\\Industrial-development\\queue\\report\\CustomReport.txt", true)) {
            writer.write(string);
        } catch (IOException e) {
           e.printStackTrace();
        }
    }
}
