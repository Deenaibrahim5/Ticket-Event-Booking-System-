@echo off
cd /d "c:\Users\deena\OneDrive\المستندات\NetBeansProjects\EventBookingSystem"
"C:\Program Files\Common Files\Oracle\Java\javapath\java.exe" --module-path "C:/Program Files/Java/javafx-sdk-25/lib" --add-modules javafx.controls,javafx.fxml -cp "build/classes;c:/app/deena/product/21c/dbhomeXE/jdbc/lib/ojdbc11.jar" eventbookingsystem.EventBookingSystem
pause
