package ru.mmtr.translationdictionary.domainservice.export;

import org.apache.poi.ss.usermodel.Workbook;
import ru.mmtr.translationdictionary.domain.export.ExportCreateModel;
import ru.mmtr.translationdictionary.domain.export.ExportType;

public interface ExportStrategy {
    Workbook createExport(ExportCreateModel model);
    ExportType getType();
}
