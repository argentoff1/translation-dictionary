package ru.mmtr.translationdictionary.domainservice.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import ru.mmtr.translationdictionary.domain.export.ExportDictionaryModel;
import ru.mmtr.translationdictionary.domain.export.ExportLanguageModel;
import ru.mmtr.translationdictionary.domain.export.ExportUserModel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Slf4j
public class WriteListToFile {
    public static void workbookDictionaryCreateHeadersIfRequired(List<ExportDictionaryModel> modelList, Workbook workbook) {
        for (var exportDictionariesModel : modelList) {
            String sheetName = exportDictionariesModel.getFromLanguageName() + "-" +
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
    }

    public static void fillDictionaryWorkbook(List<ExportDictionaryModel> modelList, Workbook workbook) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

        for (var exportDictionariesModel : modelList) {
            var sheetName = exportDictionariesModel.getFromLanguageName() + "-" +
                    exportDictionariesModel.getToLanguageName();

            var sheet = workbook.getSheet(sheetName);

            int rowIndex = sheet.getLastRowNum() + 1;

            Row row = sheet.createRow(rowIndex);

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

    public static void workbookLanguageCreateHeadersIfRequired(Workbook workbook) {
        String sheetName = "Языки";
        var sheet = workbook.getSheet(sheetName);

        if (sheet == null) {
            sheet = workbook.createSheet(sheetName);

            sheet.setDefaultColumnWidth(17);

            Row headerRow = sheet.createRow(0);
            Cell headerCell0 = headerRow.createCell(0);
            headerCell0.setCellValue("Идентификатор языка");
            Cell headerCell1 = headerRow.createCell(1);
            headerCell1.setCellValue("Язык");
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

    public static void fillLanguageWorkbook(List<ExportLanguageModel> modelList, Workbook workbook) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

        for (var exportDictionariesModel : modelList) {
            String sheetName = "Языки";

            var sheet = workbook.getSheet(sheetName);

            int rowIndex = sheet.getLastRowNum() + 1;

            Row row = sheet.createRow(rowIndex);

            Cell cell0 = row.createCell(0);
            cell0.setCellValue(String.valueOf(exportDictionariesModel.getLanguageId()));

            Cell cell1 = row.createCell(1);
            cell1.setCellValue(exportDictionariesModel.getLanguageName());

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

    public static void workbookUserCreateHeadersIfRequired(Workbook workbook) {
        String sheetName = "Пользователи";
        var sheet = workbook.getSheet(sheetName);

        if (sheet == null) {
            sheet = workbook.createSheet(sheetName);

            sheet.setDefaultColumnWidth(17);

            Row headerRow = sheet.createRow(0);
            Cell headerCell0 = headerRow.createCell(0);
            headerCell0.setCellValue("Идентификатор");

            Cell headerCell1 = headerRow.createCell(1);
            headerCell1.setCellValue("Логин");

            Cell headerCell2 = headerRow.createCell(2);
            headerCell2.setCellValue("ФИО");

            Cell headerCell3 = headerRow.createCell(3);
            headerCell3.setCellValue("Электронная почта");

            Cell headerCell4 = headerRow.createCell(4);
            headerCell4.setCellValue("Номер телефона");

            Cell headerCell5 = headerRow.createCell(5);
            headerCell5.setCellValue("Дата добавления");

            Cell headerCell6 = headerRow.createCell(6);
            headerCell6.setCellValue("Дата изменения");

            Cell headerCell7 = headerRow.createCell(7);
            headerCell7.setCellValue("Дата архивации");

            Cell headerCell8 = headerRow.createCell(8);
            headerCell8.setCellValue("Роль");
        }
    }

    public static void fillUserWorkbook(List<ExportUserModel> modelList, Workbook workbook) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

        for (var exportUserModel : modelList) {
            String sheetName = "Пользователи";

            var sheet = workbook.getSheet(sheetName);

            int rowIndex = sheet.getLastRowNum() + 1;

            Row row = sheet.createRow(rowIndex);

            Cell cell0 = row.createCell(0);
            cell0.setCellValue(String.valueOf(exportUserModel.getId()));

            Cell cell1 = row.createCell(1);
            cell1.setCellValue(exportUserModel.getLogin());

            Cell cell2 = row.createCell(2);
            cell2.setCellValue(exportUserModel.getFullName());

            Cell cell3 = row.createCell(3);
            cell3.setCellValue(exportUserModel.getEmail());

            Cell cell4 = row.createCell(4);
            cell4.setCellValue(exportUserModel.getPhoneNumber());

            Cell cell5 = row.createCell(5);
            if (exportUserModel.getCreatedAt() == null) {
                cell5.setCellValue("");
            } else {
                cell5.setCellValue(exportUserModel.getCreatedAt().format(formatter));
            }

            Cell cell6 = row.createCell(6);
            if (exportUserModel.getModifiedAt() == null) {
                cell6.setCellValue("");
            } else {
                cell6.setCellValue(exportUserModel.getModifiedAt().format(formatter));
            }

            Cell cell7 = row.createCell(7);
            if (exportUserModel.getArchiveDate() == null) {
                cell7.setCellValue("");
            } else {
                cell7.setCellValue(exportUserModel.getArchiveDate().format(formatter));
            }

            Cell cell8 = row.createCell(8);
            cell8.setCellValue(exportUserModel.getRoleName());
        }
    }

    public static UUID writeInFile(String filePath, Workbook workbook) {
        UUID exportFileName = UUID.randomUUID();

        try (var fos = new FileOutputStream(filePath + exportFileName + ".xlsx")) {
            workbook.write(fos);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        return exportFileName;
    }
}
