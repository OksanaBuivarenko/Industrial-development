package com.fintech.queue.diagram;

import com.spire.doc.*;
import com.spire.doc.documents.Paragraph;
import com.spire.doc.fields.ShapeObject;
import com.spire.doc.fields.shapes.charts.*;
import java.util.Map;

public class WordDiagram {

    public void createDiagram(Map<String, Double> throughputMap) {
        Document document = new Document();
        Section section = document.addSection();
        Paragraph paragraph = section.addParagraph();
        ShapeObject shape = paragraph.appendChart(ChartType.Column, 490, 250);
        Chart chart = shape.getChart();
        chart.getSeries().clear();

        chart.getSeries().add("Kafka",
                new String[]{"Simple", "LoadBalancing", "MultipleConsumers",
                        "LoadBalancingPlusMultipleConsumers", "StressTest"},
                new double[]{throughputMap.get("simpleKafka"),
                        throughputMap.get("loadBalancingKafka"),
                        throughputMap.get("multipleConsumersKafka"),
                        throughputMap.get("loadBalancingPlusMultipleConsumersKafka"),
                        throughputMap.get("stressTestKafka")
                });

        chart.getSeries().add("Rabbit",
                new String[]{"Simple", "LoadBalancing", "MultipleConsumers",
                        "LoadBalancingPlusMultipleConsumers", "StressTest"},
                new double[]{throughputMap.get("simpleRabbit"),
                        throughputMap.get("loadBalancingRabbit"),
                        throughputMap.get("multipleConsumersRabbit"),
                        throughputMap.get("loadBalancingPlusMultipleConsumersRabbit"),
                        throughputMap.get("stressTestRabbit")});


        chart.getTitle().setText("Throughput Rabbit and Kafka");

        chart.getAxisY().getNumberFormat().setFormatCode("#,##0");
        chart.getLegend().setPosition(LegendPosition.Bottom);
        document.saveToFile("report/ThroughputReport.docx", FileFormat.Docx_2019);

        document.dispose();
    }

    public void convertTxtToPdf() {
        Document doc = new Document();
        doc.loadFromFile("report/ThroughputReport.docx");
        ToPdfParameterList parameters = new ToPdfParameterList();
        parameters.setPdfConformanceLevel(PdfConformanceLevel.Pdf_A_1_A);
        doc.saveToFile("ThroughputReport.pdf", parameters);
        doc.dispose();
    }
}
