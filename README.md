# JavaFX Template Menu

This is a simple structured JavaFX menu template.

## Features
1. **Debug Logging Integration:** Integrated debug logging via the DebugMessenger by Mads R. Knudsen.
2. **Alert Popup Feature:** Includes functionality for displaying alert popups.

## How It Works
1. **Create an FXML View:** Design your menu interface using FXML with a container as the root.
2. **Controller Creation:** Develop a controller for the view. Avoid linking the controller in the FXML directly; instead, use data binding later.
3. **Enum Attribute Addition:** Add a new Enum attribute in `View/ViewList.java`. Parse the FXML file name and controller name in the enum.

Example Enum Entry:
```java
MENU("MenuView.fxml", MenuController.getInstance());

```
Note that the controller in this example is a singleton. 

## `ViewList` Class:

The `ViewList` class defines an enumeration of views along with their corresponding FXML file names and controllers.

- Each enum constant represents a specific view in the application.
- The `FXML_FILE_NAME` field stores the name of the FXML file associated with the view.
- The `CONTROLLER` field stores the controller instance associated with the view.
- The constructor initializes the FXML file name and controller for each view.
- Getters (`getFxmlFileName()` and `getController()`) provide access to the FXML file name and controller for a given view.
- The `LOADER_LOCATION` constant specifies the location of FXML files within the project directory structure.

## `ViewLoader` Class:

The `ViewLoader` class provides a method `loadView()` for loading JavaFX views from FXML files.

- It utilizes the `FXMLLoader` class to load FXML files.
- The `loadView()` method takes a `ViewList` enum constant as input, representing the view to be loaded.
- Inside the method, the corresponding FXML file name and controller are retrieved from the `ViewList`.
- A `FXMLLoader` instance is configured with the retrieved controller and FXML file location.
- The method attempts to load the FXML file using the `FXMLLoader` and returns the loaded JavaFX node (typically an `AnchorPane`).
- In case of an `IOException`, an error message is logged, and the method returns `null`.

### Summary:

These classes work together to provide a convenient way to load JavaFX views from FXML files in the application. The `ViewList` enum centralizes the definitions of views and their associated resources, while the `ViewLoader` class encapsulates the loading logic, allowing for easy retrieval of JavaFX nodes representing views. Additionally, debug messages are logged to provide insight into the loading process, aiding in debugging and troubleshooting.

## Alerts

**Note:** Alerts are still under development and may undergo changes.

When creating a new alert, you need to provide the following arguments:

- **Duration:** The duration (in seconds) for which the alert will be visible on the screen.
- **Alert Image:** The image on which the alert text will be displayed. Supported alert images include:
    - AlertType.Error
    - AlertType.INFO
    - AlertType.Success
- **message:** the message as a String, with /n as new line. if newLine char is not used, this will end up with the scaling being wrong. 

```JAVA
// remember to import the class. 
// ex. 

import org.apollo.template.Service.Alert.Alert;

public void onBtnError() {
  new Alert(3, AlertType.ERROR, "This is a test of Error \n The test is to show of this Alert component \n Alert component").start();
}

``` 