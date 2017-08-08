package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.*;
import java.io.IOException;

public class Controller {
    @FXML
    private TextField txt_ip;
    @FXML
    private TextField txt_port;
    @FXML
    private TextField txt_nameoid;
    @FXML
    private TextField txt_oid;

    @FXML
    private TableView<Printer> tb_oid = new TableView<>();
    @FXML
    private TableColumn printerNameCol = new TableColumn("Printer Name");
    @FXML
    private TableColumn ipCol = new TableColumn("IP");
    @FXML
    private TableColumn portCol = new TableColumn("Port");
    @FXML
    private TableColumn oidCol = new TableColumn("OID");
    @FXML
    private TableColumn infoCol = new TableColumn("비고");
    @FXML
    private TableColumn resultCol = new TableColumn("Result");

    private ObservableList<Printer> data;
    private boolean init = true;

    @FXML
    private void refresh(ActionEvent event) throws IOException{
        if(init) {
            init = false;
            tb_oid.setEditable(true);

            printerNameCol.setMinWidth(170);
            printerNameCol.setCellValueFactory(new PropertyValueFactory<>("printerName"));

            ipCol.setMinWidth(220);
            ipCol.setCellValueFactory(new PropertyValueFactory<>("IP"));

            portCol.setMinWidth(80);
            portCol.setCellValueFactory(new PropertyValueFactory<>("port"));

            oidCol.setMinWidth(240);
            oidCol.setCellValueFactory(new PropertyValueFactory<>("OID"));

            infoCol.setMinWidth(80);
            infoCol.setCellValueFactory(new PropertyValueFactory<>("info"));

            resultCol.setMinWidth(80);
            resultCol.setCellValueFactory(new PropertyValueFactory<>("result"));
        }else{
            //전체 지우고 다시 업로드
            data.removeAll(data);
            tb_oid.getColumns().clear();
        }
        getData();
        tb_oid.setItems(data);
        tb_oid.getColumns().addAll(printerNameCol, ipCol, portCol, oidCol, infoCol, resultCol);
    }

    private void getData() throws IOException {
        Snmpclient client = new Snmpclient();
        try {
            BufferedReader in = new BufferedReader(new FileReader("oid_list.txt"));
            String s;
            String[] tmp;
            boolean first = true;

            while ((s = in.readLine()) != null) {
                tmp = s.split("\t");
                if(first){
                    data = FXCollections.observableArrayList(new Printer(tmp[0],tmp[1],tmp[2],tmp[3],tmp[4],client.SNMP(tmp[1], tmp[2], tmp[3]).toString().split("=")[1]));
                    first = false;
                }
                else data.add(new Printer(tmp[0],tmp[1],tmp[2],tmp[3],tmp[4], client.SNMP(tmp[1], tmp[2], tmp[3]).toString().split("=")[1]));
            }
            in.close();
        } catch (IOException e) {
            System.err.println(e); // 에러가 있다면 메시지 출력
            System.exit(1);
        }
    }

    @FXML
    private void getValue(ActionEvent event) throws IOException{
        Snmpclient client = new Snmpclient();
        Object res = client.SNMP(txt_ip.getText(), txt_port.getText(), txt_oid.getText());
        if(!res.equals("Error: There is some problems.")) {
            try {
                BufferedWriter out = new BufferedWriter(new FileWriter("oid_list.txt", true));
                out.write(client.SNMP(txt_ip.getText(), txt_port.getText(), txt_nameoid.getText()).toString().split("=")[1] + "\t" + txt_ip.getText() + "\t" +txt_port.getText() +"\t"+txt_oid.getText() +"\t"+ "test" +"\t"+res.toString().split("=")[1]);
                out.newLine();
                out.close();
            } catch (IOException e) {
                System.err.println(e); // 에러가 있다면 메시지 출력
                System.exit(1);
            }
        }
    }
}
