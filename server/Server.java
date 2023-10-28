package server;

import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TSSLTransportFactory;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TSSLTransportFactory.TSSLTransportParameters;
import server.db.DBConfig;
import server.db.DatabaseDriver;
import server.atm.ATM;

import java.sql.SQLException;

public class Server {

  public static ATMImpl handler;
  public static ATM.Processor processor;
  public static final DBConfig DB_CONFIG =
      new DBConfig("jdbc:postgresql://db:5432/atm",
          "postgres", "root!238Ji*");

  public static void main(String[] args)
      throws SQLException, InterruptedException {

    /* Initialize DB */
    var db = new DatabaseDriver(DB_CONFIG);
    db.init();

    try {
      var handler = new ATMImpl();
      var processor = new ATM.Processor(handler);

      var serverTransport = new TServerSocket(9090);
      var server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));

      System.out.println("Starting the simple server...");
      server.serve();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
