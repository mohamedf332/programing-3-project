#!/bin/bash

VIEW_DIR="src/main/resources/view"
CTRL_DIR="src/main/java/controller"

declare -A pages=(
  ["students"]="Students"
  ["courses"]="Courses"
  ["enrollment"]="Enrollment"
)

for key in "${!pages[@]}"; do
  NAME=${pages[$key]}

  echo "🔧 Applying Back button to $NAME..."

  # ===== FXML =====
  cat > "$VIEW_DIR/$key.fxml" <<EOF
<?xml version="1.0" encoding="UTF-8"?>

<?import javafx.scene.control.*?>
<?import javafx.scene.layout.*?>

<BorderPane xmlns="http://javafx.com/javafx/17"
            xmlns:fx="http://javafx.com/fxml/1"
            fx:controller="controller.${NAME}Controller">

    <top>
        <HBox spacing="10"
              style="-fx-padding: 10; -fx-background-color: #2f80ed;">
            <Button text="← Back"
                    onAction="#backToDashboard"/>
            <Label text="$NAME Page"
                   style="-fx-text-fill: white; -fx-font-size: 18px;"/>
        </HBox>
    </top>

    <center>
        <Label text="$NAME Content Here"
               style="-fx-font-size: 18px;"/>
    </center>

</BorderPane>
EOF

  # ===== Controller =====
  cat > "$CTRL_DIR/${NAME}Controller.java" <<EOF
package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;

public class ${NAME}Controller {

    @FXML
    private void backToDashboard(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/view/dashboard.fxml")
            );

            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) ((Node) event.getSource())
                    .getScene().getWindow();

            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
EOF

done

echo "✅ Back buttons applied to all tabs successfully!"
