package server;

import server.db.DatabaseDriver;
import server.atm.ATM;

import java.sql.SQLException;

public class ATMImpl implements ATM.Iface {
  @Override
  public double get_balance(long userId) throws org.apache.thrift.TException {
    try {
      var db = new DatabaseDriver(Server.DB_CONFIG);
      return db.balance(userId);
    } catch (SQLException e) {
      return -1;
    }
  }

  @Override
  public int withdraw(long userId, double amount) throws org.apache.thrift.TException {
    try {
      var db = new DatabaseDriver(Server.DB_CONFIG);
      return db.withdraw(userId, amount);
    } catch (SQLException e) {
      return -1;
    }
  }

  @Override
  public int deposit(long userId, double amount) throws org.apache.thrift.TException {
    try {
      var db = new DatabaseDriver(Server.DB_CONFIG);
      return db.deposit(userId, amount);
    } catch (SQLException e) {
      return -1;
    }
  }
}
