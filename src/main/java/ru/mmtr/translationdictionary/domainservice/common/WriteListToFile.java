package ru.mmtr.translationdictionary.domainservice.common;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.mmtr.translationdictionary.domain.export.ExportDictionariesModel;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class WriteListToFile {
    public static void writeExportListToFile(String fileName, ArrayList<ExportDictionariesModel> modelList) throws Exception {
        Workbook workbook;

        if (fileName.endsWith("xlsx")) {
            workbook = new XSSFWorkbook();
        } else if (fileName.endsWith("xls")) {
            workbook = new HSSFWorkbook();
        } else {
            throw new Exception("Недопустимое расширение файла, необходимо xls или xlsx");
        }

        Sheet sheet = workbook.createSheet("Export");

        Iterator<ExportDictionariesModel> iterator = modelList.iterator();

        int rowIndex = 0;
        while (iterator.hasNext()) {
            ExportDictionariesModel exportDictionariesModel = iterator.next();
            Row row = sheet.createRow(rowIndex++);

            Cell cell0 = row.createCell(0);
            cell0.setCellValue(exportDictionariesModel.getFromLanguage());

            Cell cell1 = row.createCell(1);
            cell1.setCellValue(exportDictionariesModel.getToLanguage());

            Cell cell2 = row.createCell(2);
            cell2.setCellValue(exportDictionariesModel.getFullName());

            Cell cell3 = row.createCell(3);
            cell3.setCellValue(exportDictionariesModel.getEmail());

            Cell cell4 = row.createCell(4);
            cell4.setCellValue(exportDictionariesModel.getCreatedAt());

            Cell cell5 = row.createCell(5);
            cell5.setCellValue(exportDictionariesModel.getFullName());

            Cell cell6 = row.createCell(6);
            cell6.setCellValue(exportDictionariesModel.getModifiedAt());
        }

        FileOutputStream fos = new FileOutputStream(fileName);
        workbook.write(fos);
        fos.close();
    }
}
