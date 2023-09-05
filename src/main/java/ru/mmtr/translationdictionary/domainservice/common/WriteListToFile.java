package ru.mmtr.translationdictionary.domainservice.common;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.mmtr.translationdictionary.domain.export.ExportDictionariesModel;

import java.io.FileOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;

public class WriteListToFile {
    public static void writeExportListToFile(String fileName, List<ExportDictionariesModel> modelList) throws Exception {
        Workbook workbook = new XSSFWorkbook();

        ExportDictionariesModel exportDictionariesModel;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

        Iterator<ExportDictionariesModel> iterator = modelList.iterator();

        exportDictionariesModel = iterator.next();
        Sheet sheet = workbook.createSheet(exportDictionariesModel.getFromLanguageName() + "-" +
                exportDictionariesModel.getToLanguageName());

        sheet.setDefaultColumnWidth(17);

        Row headerRow = sheet.createRow(0);
        Cell headerCell0 = headerRow.createCell(0);
        headerCell0.setCellValue(exportDictionariesModel.getFromLanguageName());
        Cell headerCell1 = headerRow.createCell(1);
        headerCell1.setCellValue(exportDictionariesModel.getToLanguageName());
        Cell headerCell2 = headerRow.createCell(2);
        headerCell2.setCellValue("Добавил");
        Cell headerCell3 = headerRow.createCell(3);
        headerCell3.setCellValue("Дата добавления");
        Cell headerCell4 = headerRow.createCell(4);
        headerCell4.setCellValue("Изменил");
        Cell headerCell5 = headerRow.createCell(5);
        headerCell5.setCellValue("Дата изменения");

        int rowIndex = 1;
        while (iterator.hasNext()) {
            exportDictionariesModel = iterator.next();
            Row row = sheet.createRow(rowIndex++);

            Cell cell0 = row.createCell(0);
            cell0.setCellValue(exportDictionariesModel.getWord());

            Cell cell1 = row.createCell(1);
            cell1.setCellValue(exportDictionariesModel.getTranslation());

            Cell cell2 = row.createCell(2);
            cell2.setCellValue(exportDictionariesModel.getFullName());

            Cell cell3 = row.createCell(3);
            if (exportDictionariesModel.getCreatedAt() == null) {
                cell3.setCellValue("");
            } else {
                cell3.setCellValue(exportDictionariesModel.getCreatedAt().format(formatter));
            }

            Cell cell4 = row.createCell(4);
            cell4.setCellValue(exportDictionariesModel.getFullName());

            Cell cell5 = row.createCell(5);
            if (exportDictionariesModel.getModifiedAt() == null) {
                cell5.setCellValue("");
            } else {
                cell5.setCellValue(exportDictionariesModel.getModifiedAt().format(formatter));
            }
        }

        FileOutputStream fos = new FileOutputStream(fileName);
        workbook.write(fos);
        fos.close();
    }
}
