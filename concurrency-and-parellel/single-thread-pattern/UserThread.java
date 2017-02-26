package multithread.sigleThreadedPattern;

public class UserThread extends Thread {
  private final Gate gate;
  private final String myname;
  private final String myaddress;

  public UserThread(Gate gate, String myname, String myaddress) {
    this.gate = gate;
    this.myname = myname;
    this.myaddress = myaddress;
  }

  public void run() {
    System.out.println(myname + " BEGIN");
    while (true) {
      gate.pass(myname, myaddress);
    }
  }

  public static void main(String[] args) {
    Gate gate = new Gate();
    for (int i = 0; i < 100; i++) {
      new UserThread(gate, i + "홍길동", i + "홍인천").start();
      new UserThread(gate, i + "김길동", i + "김인천").start();
      new UserThread(gate, i + "정길동", i + "정인천").start();
    }

  }
}
