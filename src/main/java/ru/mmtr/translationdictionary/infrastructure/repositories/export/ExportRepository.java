package ru.mmtr.translationdictionary.infrastructure.repositories.export;

import org.springframework.stereotype.Repository;
import ru.mmtr.translationdictionary.domain.export.ExportCreateModel;
import ru.mmtr.translationdictionary.domain.export.ExportModel;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ExportRepository {
    /*public ExportModel save(ExportSaveModel model) {
        var entity = new ExportEntity();
        entity.setExportId();
        entity.setType();
        entity.setCreateModel();
        entity.setUserCreatedId();
        entity.setCreatedAt();

        var resultModel = getModel(entity);

        return null;
    }*/

    public List<ExportCreateModel> filterByDate(ExportCreateModel model,
                                                LocalDateTime startDate, LocalDateTime endDate) {
        /*List<ExportCreateModel> result;
        return result.stream()
                .filter(obj -> obj.)*/
        return null;
    }

    private ExportModel getModel(ExportEntity entity) {
        if (entity == null) {
            return null;
        }

        var model = new ExportModel();
        model.setExportId(entity.getExportId());
        model.setType(entity.getType());
        model.setCreateModel(entity.getCreateModel());
        model.setUserCreatedId(entity.getUserCreatedId());
        model.setCreatedAt(LocalDateTime.now());

        return model;
    }
}
