package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class Controller {
    @FXML
    private TextField txt_ip;
    @FXML
    private TextField txt_port;
    @FXML
    private TextField txt_oid;

    @FXML
    private Label lb_result = new Label();

    @FXML
    private Button btn_run;

    @FXML
    private void getValue(ActionEvent event) throws IOException{
        Snmpclient client = new Snmpclient();
        Object res = client.SNMP(txt_ip.getText(), txt_port.getText(), txt_oid.getText());
        lb_result.setText(""+res.toString());
    }
}
