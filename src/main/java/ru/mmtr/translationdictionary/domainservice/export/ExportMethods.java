package ru.mmtr.translationdictionary.domainservice.export;

import ru.mmtr.translationdictionary.domain.common.GUIDResultModel;

public interface ExportMethods {
    GUIDResultModel createExport();
    String getType();
}
