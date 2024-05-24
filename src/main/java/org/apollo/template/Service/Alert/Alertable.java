package org.apollo.template.Service.Alert;

import org.apollo.template.View.UI.AlertComp;

public interface Alertable <T>{

    void getAlertArea();
    void clearAlertArea();
    void addAlert(AlertComp alertComp);
    void removeAlert(AlertComp alertComp);

}
