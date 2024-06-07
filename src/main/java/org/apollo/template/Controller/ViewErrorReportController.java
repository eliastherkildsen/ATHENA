package org.apollo.template.Controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.apollo.template.Model.ErrorReport;
import org.apollo.template.Service.Alert.Alert;
import org.apollo.template.Service.Alert.AlertType;
import org.apollo.template.Service.Logger.LoggerMessage;
import org.apollo.template.View.BorderPaneRegion;
import org.apollo.template.View.UI.CompColors;
import org.apollo.template.View.UI.ErrorReportComp;
import org.apollo.template.View.ViewList;
import org.apollo.template.persistence.JDBC.DAO.DAO;
import org.apollo.template.persistence.JDBC.DAO.ErrorReportDAODB;
import org.apollo.template.persistence.JDBC.StoredProcedure.GetErrorReport;
import org.apollo.template.persistence.PubSub.MessagesBroker;
import org.apollo.template.persistence.PubSub.MessagesBrokerTopic;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ViewErrorReportController implements Initializable {

    @FXML
    private VBox errorReportComps;
    private DAO<ErrorReport> dao = new ErrorReportDAODB();
    private List<ErrorReportComp> errorReportCompList = new ArrayList<>();
    private ErrorReportComp selectedErrorReportComp;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // loading error reports.
        List<ErrorReport> errorReportList = GetErrorReport.getErrorReports();
        loadErrorReport(errorReportList);
    }

    @FXML
    protected void onButton_delete(){

        // checks if an error report has been selected.
        if (selectedErrorReportComp == null){
            new Alert(MainController.getInstance(), 5, AlertType.INFO, "Du har ikke valgt en error report.")
                    .start();
            return;
        }

        // deleting the error report thrugh DAO
        dao.delete(selectedErrorReportComp.getERROR_REPORT());
        new Alert(MainController.getInstance(), 5, AlertType.SUCCESS, "Du har nu slette fejlmeldingen.")
                .start();

        // remove error report comp from vbox
        errorReportComps.getChildren().remove(selectedErrorReportComp);


    }

    @FXML
    protected void on_buttonArchive(){

        // checks if an error report has been selected.
        if (selectedErrorReportComp == null){
            new Alert(MainController.getInstance(), 5, AlertType.INFO, "Du har ikke valgt en error report.")
                    .start();
            return;
        }

        // fetching the selected error report.
        ErrorReport selecctedErrorReport = selectedErrorReportComp.getERROR_REPORT();
        // setting the error report to archived
        selecctedErrorReport.setArchived(true);
        dao.update(selecctedErrorReport);

        new Alert(MainController.getInstance(), 5, AlertType.INFO, "Du har nu arkiveret fejlmeddelsen.")
                .start();

        // removeing the error report from the vox.
        errorReportComps.getChildren().remove(selectedErrorReportComp);

    }

    @FXML
    protected void onButton_edit(){

        // checks if an error report has been selected.
        if (selectedErrorReportComp == null){
            new Alert(MainController.getInstance(), 5, AlertType.INFO, "Du har ikke valgt en error report.")
                    .start();
            return;
        }

        // change view to edit errorreport view.
        MainController.getInstance().setView(ViewList.ERRORREPORTEDIT, BorderPaneRegion.CENTER);

        // publish ErrorReport.
        MessagesBroker.getInstance().publish(MessagesBrokerTopic.ERROR_REPORT, selectedErrorReportComp.getERROR_REPORT());

    }

    /**
     * Method for loading error report into the vbox
     * @param errorReportList List<ErrorReport>
     */
    private void loadErrorReport(List<ErrorReport> errorReportList) {
        LoggerMessage.debug(this,"Loading error report");
        // clearing error report vbox to avoid replica data.
        errorReportComps.getChildren().clear();
        LoggerMessage.debug(this, "Cleared errorReportComps");

        for (ErrorReport errorReport : errorReportList) {
            // checks if the error report is archived.
            if (!errorReport.isArchived()) {
                ErrorReportComp errorReportComp = new ErrorReportComp(errorReport);
                errorReportCompList.add(errorReportComp);

                // attaching on action
                errorReportComp.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        unselectAllErrorReportComps();
                        errorReportComp.setCompColor(CompColors.SELECTED);
                        selectedErrorReportComp = errorReportComp;
                    }
                });

                // adding error report comp to vbox.
                errorReportComps.getChildren().add(errorReportComp);

            }
        }
    }

    private void unselectAllErrorReportComps() {
        for (ErrorReportComp errorReportComp : errorReportCompList){
            errorReportComp.setCompColor(CompColors.NORMAL);
        }
    }

}
