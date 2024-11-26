package com.fintech.queue;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BenchmarkResult {

    private String benchmarkLabel;

    private Double throughput;

    private Double latency;


    @Override
    public String toString() {
        return benchmarkLabel + ": " +
                " throughput = " + throughput +
                ", latency = " + latency + ";" + System.lineSeparator();
    }
}
