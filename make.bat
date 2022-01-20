jar cvmf manifest.txt executable.jar .
jar cvf ateam.zip .
java --module-path "C:\Users\txchf\Desktop\Spring 2020\CS 400\javafx-sdk-11.0.2\lib" --add-modules javafx.controls,javafx.fxml -jar executable.jar