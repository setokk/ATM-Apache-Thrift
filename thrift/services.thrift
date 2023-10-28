// Add package name declaration in case of Java
namespace java server.atm
namespace py atm

service ATM {
  double get_balance(1:i64 userId),
  i32 withdraw(1:i64 userId, 2:double amount),
  i32 deposit(1:i64 userId, 2:double amount)
}