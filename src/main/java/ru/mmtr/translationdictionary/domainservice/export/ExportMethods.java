package ru.mmtr.translationdictionary.domainservice.export;

import ru.mmtr.translationdictionary.domain.common.GUIDResultModel;

public interface ExportMethods {
    // String or Enum
    GUIDResultModel createExport();

    ExportType getType();
}
