package sample;

/**
 * Created by jinWook on 2017-08-07.
 */
public class Printer {
    private String printerName;
    private String IP;
    private String port;
    private String OID;
    private String info;
    private String result;

    public Printer(String pName, String ip, String port, String oid, String info, String result){
        this.printerName = pName;
        this.IP = ip;
        this.port = port;
        this.OID = oid;
        this.info = info;
        this.result = result;
    }

    public void setPrinterName(String pName){
        this.printerName = pName;
    }

    public String getPrinterName(){
        return printerName;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getOID() {
        return OID;
    }

    public void setOID(String OID) {
        this.OID = OID;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
