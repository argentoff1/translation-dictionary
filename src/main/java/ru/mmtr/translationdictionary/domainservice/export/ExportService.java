package ru.mmtr.translationdictionary.domainservice.export;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.mmtr.translationdictionary.domain.common.GUIDResultModel;
import ru.mmtr.translationdictionary.domain.export.ExportCreateModel;
import ru.mmtr.translationdictionary.domain.export.ExportType;
import ru.mmtr.translationdictionary.infrastructure.FileStoreService;
import test.Test;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: parinos.ma.kst
 * \* Date: 9/11/2023
 * \* Description:
 * \  Сервис для взаимодействия с файлами экспорта
 */

@Service
public class ExportService {
    private final FileStoreService fileStoreService;

    @Autowired
    private Test test;
    private final Map<ExportType, ExportStrategy> strategies;

    public ExportService(FileStoreService fileStoreService, Collection<ExportStrategy> exportStrategies) {
        this.fileStoreService = fileStoreService;
        strategies = exportStrategies.stream().collect(Collectors.toMap(ExportStrategy::getType, Function.identity()));
    }

    public GUIDResultModel createExport(ExportCreateModel model) {
        var createdWorkBook = strategies.get(model.getExportType()).createExport(model);
        test.test();;
        return fileStoreService.createFile(createdWorkBook);
    }

    public MultipartFile getFile(UUID id) {
        return fileStoreService.getFile(id);
    }

    public InputStreamResource getInputStreamResource(MultipartFile multipartFile) {
        return fileStoreService.getInputStreamResource(multipartFile);
    }
}
