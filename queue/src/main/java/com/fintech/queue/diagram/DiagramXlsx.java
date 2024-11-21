package com.fintech.queue.diagram;

import com.spire.xls.*;
import com.spire.xls.charts.ChartSerie;
import com.spire.xls.charts.ChartSeries;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.util.Map;

@NoArgsConstructor
public class DiagramXlsx {


    public static void executeClusteredColumn(boolean is3D, Map<String, Double> throughputMap) {
        Workbook workbook = new Workbook();
        Worksheet sheet = workbook.getWorksheets().get(0);
        sheet.setName("ClusteredColumn");
        createChartData(sheet, throughputMap);

        Chart chart = sheet.getCharts().add();
        chart.setDataRange(sheet.getCellRange("A1:C6"));
        chart.setSeriesDataFromRange(false);
        chart.setLeftColumn(1);
        chart.setTopRow(6);
        chart.setRightColumn(11);
        chart.setBottomRow(29);

        if (is3D) {
            chart.setChartType(ExcelChartType.Column3DClustered);
        } else {
            chart.setChartType(ExcelChartType.ColumnClustered);
        }

        chart.setChartTitle("Throughput Rabbit and Kafka");
        chart.getChartTitleArea().isBold(true);
        chart.getChartTitleArea().setSize(12);
        chart.getPrimaryCategoryAxis().setTitle("Label");
        chart.getPrimaryCategoryAxis().getFont().isBold(true);
        chart.getPrimaryCategoryAxis().getTitleArea().isBold(true);
        chart.getPrimaryValueAxis().setTitle("Throughput ops/s");
        chart.getPrimaryValueAxis().hasMajorGridLines(false);
        chart.getPrimaryValueAxis().setMinValue(0);
        chart.getPrimaryValueAxis().getTitleArea().isBold(true);
        chart.getPrimaryValueAxis().getTitleArea().setTextRotationAngle(90);

        ChartSeries series = chart.getSeries();
        for (int i = 0;i < series.size();i++) {
            ChartSerie cs = series.get(i);
            cs.getFormat().getOptions().isVaryColor(true);
            cs.getDataPoints().getDefaultDataPoint().getDataLabels().hasValue(true);
        }
        chart.getLegend().setPosition(LegendPositionType.Top);
        workbook.saveToFile("report/ThroughputReport.xlsx",ExcelVersion.Version2010);

    }

    private static void createChartData(Worksheet sheet, Map<String, Double> throughputMap) {
        sheet.getCellRange("A1").setValue("BenchmarksLabel");
        sheet.getCellRange("A2").setValue("Simple");
        sheet.getCellRange("A3").setValue("LoadBalancing");
        sheet.getCellRange("A4").setValue("MultipleConsumers");
        sheet.getCellRange("A5").setValue("LoadBalancingPlusMultipleConsumers");
        sheet.getCellRange("A6").setValue("StressTest");

        sheet.getCellRange("B1").setValue("Kafka");
        sheet.getCellRange("B2").setNumberValue(throughputMap.get("simpleKafka"));
        sheet.getCellRange("B3").setNumberValue(throughputMap.get("loadBalancingKafka"));
        sheet.getCellRange("B4").setNumberValue(throughputMap.get("multipleConsumersKafka"));
        sheet.getCellRange("B5").setNumberValue(throughputMap.get("loadBalancingPlusMultipleConsumersKafka"));
        sheet.getCellRange("B6").setNumberValue(throughputMap.get("stressTestKafka"));

        sheet.getCellRange("C1").setValue("Rabbit");
        sheet.getCellRange("C2").setNumberValue(throughputMap.get("simpleRabbit"));
        sheet.getCellRange("C3").setNumberValue(throughputMap.get("loadBalancingRabbit"));
        sheet.getCellRange("C4").setNumberValue(throughputMap.get("multipleConsumersRabbit"));
        sheet.getCellRange("C5").setNumberValue(throughputMap.get("loadBalancingPlusMultipleConsumersRabbit"));
        sheet.getCellRange("C6").setNumberValue(throughputMap.get("stressTestRabbit"));

        sheet.getCellRange("A1:C1").setRowHeight(15);
        sheet.getCellRange("A1:C1").getCellStyle().setColor(Color.darkGray);
        sheet.getCellRange("A1:C1").getCellStyle().getExcelFont().setColor(Color.white);
        sheet.getCellRange("A1:C1").getCellStyle().setVerticalAlignment(VerticalAlignType.Center);
        sheet.getCellRange("A1:C1").getCellStyle().setHorizontalAlignment(HorizontalAlignType.Center);
        sheet.getCellRange("B2:C5").getCellStyle().setNumberFormat("\"#,##0");
    }
}
