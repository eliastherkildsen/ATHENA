package org.apollo.template.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import org.apollo.template.Model.ErrorReport;
import org.apollo.template.View.UI.ErrorReportComp;
import org.apollo.template.persistence.JDBC.DAO.DAO;
import org.apollo.template.persistence.JDBC.DAO.ErrorReportDAODB;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ViewErrorReportController implements Initializable {

    @FXML
    private VBox errorReportComps;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // fetching all error reports
        DAO<ErrorReport> dao = new ErrorReportDAODB();
        List<ErrorReport> errorReportList = dao.readAll();

        System.out.println(errorReportList.size());

        loadErrorReport(errorReportList);
    }

    private void loadErrorReport(List<ErrorReport> errorReportList) {
        // clearing error report vbox to avoid replica data.
        errorReportComps.getChildren().clear();

        for (ErrorReport errorReport : errorReportList) {
            errorReportComps.getChildren().add(new ErrorReportComp(errorReport));
        }
    }
}
