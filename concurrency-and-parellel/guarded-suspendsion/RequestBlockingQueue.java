package multithread.guaredSuspension;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class RequestBlockingQueue {
  private final BlockingQueue<Request> queue = new LinkedBlockingQueue<Request>();

  public Request getReuqest() {
    Request req = null;
    while (queue.peek() == null) {
      try {
        req = queue.take();
      } catch (InterruptedException e) {

      }
    }
    return req;
  }

  public void putRequest(Request request) {
    try {
      queue.put(request);
    } catch (InterruptedException e) {

    }
  }
}
