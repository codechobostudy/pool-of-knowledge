# 준비될때 까지 기다려주세요. (Guarded Suspension Pattern)

> 쓰레드를 기다리게 하여 인스턴스의 안전성을 지킨다. Guarded wait, spin lock 이라고도 불리운다.


[![쓰레드시퀀스다이어그램](http://cfile24.uf.tistory.com/image/127681284AE5A4DB3F7B18)]

> `synchronized` 메소드의 while문을 살펴보고 `wait()` 와 `notifyAll()`이 어떻게 놓여져 있는지  
예제 코드에서 잘 확인하는 것이 중요하다.  그 밖에  *`BlockingQueue` 를 이용하는 방법도 있으니 참고한다.* 