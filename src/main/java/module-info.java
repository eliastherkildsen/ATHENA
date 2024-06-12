module org.apollo.template {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires java.logging;
    requires java.desktop;
    requires jdk.compiler;
    requires org.jetbrains.annotations;

    opens org.apollo.template to javafx.fxml;
    exports org.apollo.template;
    exports org.apollo.template.Controller;
    opens org.apollo.template.Controller to javafx.fxml;
    exports org.apollo.template.Controller.InventoryItems;
    opens org.apollo.template.Controller.InventoryItems to javafx.fxml;
    exports org.apollo.template.Controller.Team;
    opens org.apollo.template.Controller.Team to javafx.fxml;
    exports org.apollo.template.Controller.Booking;
    opens org.apollo.template.Controller.Booking to javafx.fxml;
    exports org.apollo.template.Controller.Room;
    opens org.apollo.template.Controller.Room to javafx.fxml;
    exports org.apollo.template.Controller.Menu;
    opens org.apollo.template.Controller.Menu to javafx.fxml;
}