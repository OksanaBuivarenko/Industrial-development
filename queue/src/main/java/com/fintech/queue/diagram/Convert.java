package com.fintech.queue.diagram;


import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;

public class Convert {

    public void convertTxtToPdf() throws Exception {
        Workbook workbook = new Workbook("C:\\Users\\Admin\\Desktop\\GitHub\\Industrial-development\\queue\\report\\ThroughputReport.xlsx");
        Worksheet worksheet = workbook.getWorksheets().get(0);
        if(worksheet.getCharts().getCount() > 0)
        {
            com.aspose.cells.Chart chart = worksheet.getCharts().get(0);
            chart.toPdf("C:\\Users\\Admin\\Desktop\\GitHub\\Industrial-development\\queue\\report\\ThroughputReport.pdf");
        }

    }
}
