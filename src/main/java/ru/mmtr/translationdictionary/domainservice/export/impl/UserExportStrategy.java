package ru.mmtr.translationdictionary.domainservice.export.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import ru.mmtr.translationdictionary.domain.common.PageResultModel;
import ru.mmtr.translationdictionary.domain.export.ExportCreateModel;
import ru.mmtr.translationdictionary.domain.export.ExportType;
import ru.mmtr.translationdictionary.domain.export.ExportUserModel;
import ru.mmtr.translationdictionary.domain.user.UserModel;
import ru.mmtr.translationdictionary.domain.user.UserPageRequestModel;
import ru.mmtr.translationdictionary.domainservice.common.WriteListToFile;
import ru.mmtr.translationdictionary.domainservice.export.ExportStrategy;
import ru.mmtr.translationdictionary.domainservice.user.UserService;

import java.util.List;

@Service
@Slf4j
public class UserExportStrategy implements ExportStrategy {
    private final UserService userService;
    private static final int PAGE_SIZE = 100;

    public UserExportStrategy(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Workbook createExport(ExportCreateModel model) {
        var userCriteria = new UserPageRequestModel();
        userCriteria.setPageNum(0);
        userCriteria.setPageSize(PAGE_SIZE);
        PageResultModel<UserModel> page;

        List<ExportUserModel> exportUserModels;
        Workbook workbook = new XSSFWorkbook();
        do {
            page = userService.getPage(userCriteria);
            userCriteria.setPageNum(userCriteria.getPageNum() + 1);

            exportUserModels = page.getResultList().stream().map(userModel -> {
                var exportUserModel = new ExportUserModel();

                exportUserModel.setId(userModel.getUserId());
                exportUserModel.setLogin(userModel.getLogin());
                exportUserModel.setLastName(userModel.getLastName());
                exportUserModel.setFirstName(userModel.getFirstName());
                exportUserModel.setFatherName(userModel.getFatherName());
                if (exportUserModel.getLastName() != null && exportUserModel.getFirstName() != null) {
                    exportUserModel.setFullName(exportUserModel.getLastName() + " "
                            + exportUserModel.getFirstName());
                }
                exportUserModel.setEmail(userModel.getEmail());
                exportUserModel.setPhoneNumber(userModel.getPhoneNumber());
                exportUserModel.setCreatedAt(userModel.getCreatedAt());
                exportUserModel.setModifiedAt(userModel.getModifiedAt());
                exportUserModel.setArchiveDate(userModel.getArchiveDate());
                exportUserModel.setRoleName(userModel.getRoleName());

                return exportUserModel;
            }).toList();

            WriteListToFile.workbookUserCreateHeadersIfRequired(workbook);

            try {
                WriteListToFile.fillUserWorkbook(exportUserModels, workbook);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        } while (page.getResultList().size() == PAGE_SIZE);

        return workbook;
    }

    @Override
    public ExportType getType() {
        return ExportType.USER;
    }
}
