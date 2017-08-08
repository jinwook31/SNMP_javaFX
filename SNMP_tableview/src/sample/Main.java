package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

import javafx.event.EventHandler;
import javafx.stage.WindowEvent;

import javax.imageio.ImageIO;

public class Main extends Application {

    private boolean firstTime;
    private TrayIcon trayIcon;

    @Override
    public void start(Stage primaryStage) throws Exception{
        createTrayIcon(primaryStage);
        firstTime = true;
        Platform.setImplicitExit(false);
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("SNMP");
        primaryStage.setScene(new Scene(root, 1100, 700));
        primaryStage.show();
    }

    public void createTrayIcon(final Stage stage) {
        if (SystemTray.isSupported()) {
            // get the SystemTray instance
            SystemTray tray = SystemTray.getSystemTray();
            // load an image
            java.awt.Image image = null;
            try {
                URL url = new URL("http://www.tv.com/public/images/lists/ajax-loader-blue.gif");
                image = ImageIO.read(url);
            } catch (IOException ex) {
                System.out.println(ex);
            }


            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent t) {
                    hide(stage);
                }
            });
            // create a action listener to listen for default action executed on the tray icon
            final ActionListener closeListener = new ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    System.exit(0);
                }
            };

            ActionListener showListener = new ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            stage.show();
                        }
                    });
                }
            };
            // create a popup menu
            PopupMenu popup = new PopupMenu();

            MenuItem showItem = new MenuItem("Show");
            showItem.addActionListener(showListener);
            popup.add(showItem);

            MenuItem closeItem = new MenuItem("Close");
            closeItem.addActionListener(closeListener);
            popup.add(closeItem);
            /// ... add other items
            // construct a TrayIcon
            trayIcon = new TrayIcon(image, "SNMP", popup);
            // set the TrayIcon properties
            trayIcon.addActionListener(showListener);
            // ...
            // add the tray image
            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                System.err.println(e);
            }
            // ...
        }
    }

    public void showProgramIsMinimizedMsg() {
        if (firstTime) {
            trayIcon.displayMessage("SNMP on Tray",
                    "SNMP on Tray",
                    TrayIcon.MessageType.INFO);
            firstTime = false;
        }
    }

    private void hide(final Stage stage) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (SystemTray.isSupported()) {
                    stage.hide();
                    showProgramIsMinimizedMsg();
                } else {
                    System.exit(0);
                }
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
