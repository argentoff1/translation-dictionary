package ru.mmtr.translationdictionary.domainservice.export;

import org.springframework.stereotype.Service;
import ru.mmtr.translationdictionary.domain.common.GUIDResultModel;

@Service
public class FileStorageService implements ExportMethods{
    @Override
    public GUIDResultModel createExport() {
        return null;
    }

    @Override
    public String getType() {
        return null;
    }
}