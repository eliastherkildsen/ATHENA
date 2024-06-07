package org.apollo.template.Service;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.apollo.template.View.UI.CompColors;
import org.apollo.template.View.UI.DefualtComponent;

import java.util.ArrayList;
import java.util.List;

public class ComponentVbox extends VBox {

    private List<DefualtComponent> components = new ArrayList<>();
    private DefualtComponent selectedComponent = null;

    public ComponentVbox(List<DefualtComponent> components) {
        this.components = components;
    }

    private void attatchOnAction(){
        for (DefualtComponent component : components) {
            component.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent mouseEvent) {
                    // setting the selected component color to normal
                    if (selectedComponent != null) {
                        selectedComponent.setCompColor(CompColors.NORMAL);
                    }
                    // setting the selected componet to the one witchs was just clicked.
                    selectedComponent = component;
                    selectedComponent.setCompColor(CompColors.SELECTED);
                }
            });
        }
    }

    public List<DefualtComponent> getComponents() {
        return components;
    }

    public DefualtComponent getSelectedComponent() {
        return selectedComponent;
    }





}
