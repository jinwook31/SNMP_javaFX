package sample;

/**
 * Created by jinWook on 2017-07-26.
 * https://technet.tmaxsoft.com/upload/download/online/jeus/pver-20150722-000001/snmp/chapter_JEUS_SNMP_Programming.html#sect_JEUS_SNMP_Programming_Introduction
 */
import java.net.*;
import java.util.*;
import org.snmp4j.*;
import org.snmp4j.PDU;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.transport.*;
import org.snmp4j.smi.*;

public class Snmpclient{
    static int defaultPort = 9999;
    static String defaultIP = "127.0.0.1";
    static String defaultOID = "1.3.6.1.4.1.14586.100.77.1";

    static Object testGetNext(String oid) throws java.io.IOException {
        //1. Make Protocol Data Unit
        PDU pdu = new PDU();
        pdu.add(new VariableBinding(new OID(defaultOID)));
        pdu.setType(PDU.GETNEXT);

        //2. Make target
        CommunityTarget target = new CommunityTarget();
        UdpAddress targetAddress = new UdpAddress();
        targetAddress.setInetAddress(InetAddress.getByName(defaultIP));
        targetAddress.setPort(defaultPort);
        target.setAddress(targetAddress);
        target.setCommunity(new OctetString("public"));
        target.setVersion(SnmpConstants.version1);

        //3. Make SNMP Message. Simple!
        Snmp snmp = new Snmp(new DefaultUdpTransportMapping());

        //4. Send Message and Recieve Response
        snmp.listen();
        ResponseEvent response = snmp.send(pdu, target);
        Object tmp = null;
        Boolean success = false;

        if (response.getResponse() == null) {
            System.out.println("Error: There is some problems.");
        } else {
            Vector variableBindings = response.getResponse().getVariableBindings();
            for( int i = 0; i < variableBindings.size(); i++){
                System.out.println(variableBindings.get(i));
                tmp = variableBindings.get(i);
                success = true;
            }
        }
        snmp.close();

        if(success) return tmp;
        else return "Error: There is some problems.";
    }

    public static Object SNMP(String ip, String port, String oid) throws java.io.IOException {
        // get the SNMP port number
        if (port.length() > 0) {
            defaultPort = Integer.parseInt (port);
        }
        System.out.println ("PORT : " + defaultPort);

        // get the ip address of the machine that the SNMP agent runs on
        if (ip.length() > 1) {
            defaultIP = ip;
        }
        System.out.println ("IP : " + defaultIP);

        // get the OID number that you want to get the value of
        if (oid.length() > 2) {
            defaultOID = oid;
        }
        System.out.println ("OID : " + defaultOID);

        try {
            return testGetNext(defaultOID);
        } catch (Exception ex) {
            System.out.println ("ex *** : " + ex);
            ex.printStackTrace ();
            return null;
        }
    }
}