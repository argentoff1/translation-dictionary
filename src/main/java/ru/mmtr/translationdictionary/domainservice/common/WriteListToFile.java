package ru.mmtr.translationdictionary.domainservice.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import ru.mmtr.translationdictionary.domain.export.ExportDictionariesModel;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Slf4j
public class WriteListToFile {
    public static FileOutputStream createFile(String fileName) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(fileName);
        } catch (FileNotFoundException e) {
            log.error(e.getMessage(), e);
        }

        // Возможно поток нужно закрывать
        /*try {
            fileOutputStream.close();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }*/

        return fileOutputStream;
    }

    public static void workbookCreateHeadersIfRequired(List<ExportDictionariesModel> modelList, Workbook workbook) {
        //Sheet sheet = workbook.createSheet("Англо-русский");
        for(var exportDictionariesModel : modelList) {
            var sheetName = exportDictionariesModel.getFromLanguageName() + "-" +
                    exportDictionariesModel.getToLanguageName();
            var sheet = workbook.getSheet(sheetName);

            if (sheet == null) {
                sheet = workbook.createSheet(sheetName);

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
            }
        }

        //return workbook;
    }

    public static void fillWorkbook(List<ExportDictionariesModel> modelList, Workbook workbook) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

        int rowIndex = 1;
        for (var exportDictionariesModel : modelList) {
            var sheetName = exportDictionariesModel.getFromLanguageName() + "-" +
                    exportDictionariesModel.getToLanguageName();

            var sheet = workbook.getSheet(sheetName);

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
    }

    public static UUID writeInFile(Workbook workbook) {
        UUID exportFileName = UUID.randomUUID();

        try {
            FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\parinos.ma.kst\\" +
                    "IdeaProjects\\" + "translation-dictionary\\src\\main\\resources\\export\\"
                    + exportFileName + ".xlsx");

            workbook.write(fileOutputStream);
            fileOutputStream.close();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        return exportFileName;
    }
}
