# [ 자바 멀티쓰레드 용어 정리 ]

## 프로세스와 쓰레드

프로세스는 독립된 메모리 공간을 가지기 때문에 서로 다른 프로세스가 각자의 메모리에 접근할 수 없지만 스레드는 메모리를 공유해서 사용하므로 올바르게 베타제어를 해야한다.

프로세스는 컨텍스트 정보가 많기 때문에 컨텍스트 스위칭이 쓰레드보다 무겁다.

## - 병행 ( concurrent ) 처리 -
쓰레드가 하나인 프로그램을 수행하면 하나의 쓰레드가 처음부터 끝까지 프로그램을 순서대로 수행한다. 반면에 여러개의 쓰레드를 만들어서 작업을 수행시키면 쓰레드들이 번갈아 가면서 각자 맡은 작업을 조금씩 수행하며 프로그램을 수행한다. 이렇게 수행하는것을 병행 처리라고 한다. 여러 작업을 동시에 실행하는 병렬(parallel)처리와는 다르다.

### synchronized 메소드

한번에 한개 쓰레드만 실행 가능한 메소드이다.

### synchronized 블럭

한번에 한개 쓰레드만 블록 내부의 코드를 실행시킬 수 있다.

### 락

락은 인스턴스마다 존재하며 쓰레드의 배타제어를 구현하기 위한 장치이다.

* 쓰레드가 synchronized 메소드나 블럭을을 수행하려면 반드시 락을 확보하고 있어야 한다.

* 락을 확보하여 동기화 메소드나 블럭의 수행이 완료되면 쓰레드는 락을 반납한다.

### wait 셋

wait 메소드를 실행한 후 동작을 정지하고 있는 쓰레드가 대기하고 있는 논리적인 공간이다.

### wait

현재 쓰레드를 wait 셋으로 보낸다.

### notify

wait 셋에서 1개의 쓰레드를 깨운다. 어떤 쓰레드를 깨울지는 정해져 있지 않다.

### notifyall

wait 셋에 있는 모든 쓰레드를 깨운다.

* [ wait, notify, notifyall ] 메소드는 락을 확보한 상태에서 호출해야 한다.

* 락을 가지지 못한 쓰레드가 [ wait, notify, notifyall ] 메소드를 호출하면 java.lang.IligalMonitorStateException 예외가 통보된다.

* notify, notifyall 에 의해서 wait 셋을 빠져나온 쓰레드는 lock 을 확보하기 위해 block 한다.

* block 하고 있는 상태의 쓰레드에 대해 interrupt 를 수행하더라도 인터럽트 예외가 통보되지 않는다.
  반드시 락을 취한 후 인터럽트 상태를 의식할 수 있는 메소드(wait, sleep, join)를 호출하거나
  isInterrupted, interrupt 메소드를 사용해 인터럽트 상태를 조사하고 직접 throw 해야 한다.

### 안정성(safety)

안전성이란 객체를 사용하는 도중에 객체의 필드가 예상 외의 값을 가지지 않는 것을 말한다. 복수의 쓰레드가 이용해도 안전성이 유지되는 클래스를 thread-safe 클래스라고 한다.

### 생존성(liveness)

프로그램이 중단되지 않고 필요한 처리가 언젠가 반드시 이루어지는 것을 말한다.

 * 멀티쓰레드 프로그램에서 안정성과 생존성은 필수적인 요소이다. *

### 데드락

서로 다른 쓰레드가 락을 각각 하나씩 확보한 채로 서로가 상대 방이 가지고 있는 락을 확보하기 위해 무한히 대기하는 상황이다.


## - Interrupt 처리 -

### interrupt

interrupt 메소드는 쓰레드를 인터럽트 상태로 설정하는 메소드이다.
interrupt 메소드는 Thread 클래스의 인스턴스 메소드이며 호출할 때 락을 확보할 필요가 없다. 즉 언제 어떤 쓰레드라도 임의의 쓰레드에 대해 interrupt 메소드를 호출할 수 있다.

 ### InterruptedException

InterruptedException 을 던지는 메소드는 시간이 걸릴 수 있고, 취소가 가능한 메소드 이다.
Object 클래스의 wait, Thread 클래스의 sleep, join 메소드는 메소드가 수행중일 때 내부적으로 인터럽트 상태를 조사해 InterruptedException 을 던진다.
따라서 wait, sleep, join 메소드를 호출하던가 명시적으로 쓰레드의 인터럽트 상태를 조사해서 InterruptException 을 통보하는 코드를 작성해야만 예외를 통보할 수 있다.

* sleep 하고 있거나 join 메소드에 의해 다른 쓰레드가 완료되기를 기다리고있는 쓰레드는 interrupt 메소드가 호출되면 일시 정지를 중단하고 InterruptedException 을 통보한다.

* wait 메소드에 의해 대기중인 스레드에 대해 interrupt 메소드가 실행되면 해당 쓰레드는 wait 셋에 빠져나오고 이후에 락을 다시 취하게 되면 InterruptException을 통보한다.
  즉 wait 셋에서 대기 중인 쓰레드는   interrupt 메소드가 호출되도 락을 취하기 전까지 예외를 통보할 수 없다.

### isInterrupted

해당 쓰레드의 인터럽트 상태를 조사하는 메소드로 인터럽트의 상태를 바꾸지는 않는다.

### Thread.interrupted

현재 쓰레드의 인터럽트 상태를 조사하고 인터럽트 상태를 삭제하는 클래스 메소드이다.
즉 인터럽트 상태를 조사한 이후 현재 쓰레드를 비인터럽트 상태로 만든다.

## 병행 처리 패턴

### single threaded execution

synchronized 메소드나 블럭을 이용해 배타제어가 필요한 코드를 한번에 하나의 쓰레드만 실행하게 하는 패턴이다.

### before / after

lock() 과 unlock() 처럼 서로 대칭을 이루는 메소드가 있을 때 lock() 을 호출한 이후에 무슨 일이 있더라도 unlock() 메소드의 호출을 보장하는 패턴이다. finally구문을 이용해 구현한다.

### Immutable

인스턴스의 초기화 이후 상태가 변하지 않도록 하여 따로 베타제어를 하지 않아도 쓰레드 안전성이 유지되는 패턴이다.
final 키워드 등을 사용해 구현한다.

### guarded suspension

쓰레드 안전한 상태로 조건에 따라 대기하거나 재 수행하도록 하는 패턴이다. Wait() 과 notify() 메소드를 이용해 구현한다.

### balking

쓰레드 안전성을 유지한 채로 조건이 충족될 경우에만 수행되도록 하는 패턴이다.

### producer-consumer

복수의 쓰레드에서 데이터를 생산하고 소비할 때 쓰레드 안전성을 유지할 수 있도록 해주는 패턴이다.
생산자와 소비자 중간에 중개 역할(채널)이 존재해 양쪽 쓰레드간의 처리 속도 차이를 메우고 베타제어 처리를 채널 안에 캡슐화한다.

### read-write

복수개의 쓰레드가 읽고 쓸때 읽는 처리와 쓰는 처리를 나누어서 하나의 쓰레드가 쓰고 있는 경우에는 다른 쓰레드들이 대기하지만 여러 개의 쓰레드가 동시에 읽는 것은 가능하도록 만드는 패턴이다.
읽기 처리끼리는 충돌하지 않는 점을 이용하기 때문에 읽기 빈도가 쓰기 빈도보다 높을 경우 적용하면 수행 능력을 높일 수 있다.

### thread per message

메세지를 처리할 때 쓰레드를 만들어서 처리하여 응답성이 높아지게 만드는 패턴이다.

### worker thread

복수의 작업들을 처리할 때 작업을 처리하는 쓰레드를 재활용 하여 작업 처리 능력을 높이고 작업의 요청과 수행을 분리하여 응답성이 높아지게 만드는 패턴이다.

### future

반환 값을 얻어오는데 시간이 걸리는 처리를 백그라운드에서 thread-safe 하게 처리하고 처리가 완료되면 값을 넣어주어 응답성을 높게 만드는 패턴이다.

### two phase termination

쓰레드를 종료하는 방법을 2단계로 나누어 안전하게 종료하게 만드는 패턴이다.

### thread specific storage

쓰레드 마다 독립된 저장소를 만들어 다른 쓰레드가 엑세스하지 못하게 만드는 패턴이다.
자바 구현체로 Java.lang.ThreadLocal 클래스가 있다.

## - 병행 처리 패키지 ( java.util.concurrent ) -

### ThreadFactory

쓰레드 생성을 추상화한 인터페이스

### Executor

쓰레드 실행을 추상화한 인터페이스

### ExecutorService

재사용 되는 쓰레드 실행을 추상화한 인터페이스

### Executors

병행 처리 인터페이스 구현체를 모아놓은 유틸리티 클래스

## [ 자바 메모리 모델 ]

### reorder

컴파일러나 자바가상기계가 최적화를 위해 프로그램 처리 순서를 임의로 변경하는 것이다.
멀티쓰레드 프로그램에서는 reorder 가 원인이 되어 쓰레드 안전성을 훼손시킬 수 있다.

### visibility

다른 쓰레드가 입력한 값을 또 다른 쓰레드가 정확하게 읽을 수 있는 성질을 말한다.
동기화 되지 않은 필드를 읽거나 쓰는 연산은 캐시를 매개로 이루어 지므로 그 값이 항상 최신임을 보장하지 않는다.
반면에 synchronized 나 volatile 을 이용해 필드 값을 동기화하면 visibility 가 보장된다.

### volatile

volatile 은 쓰레드의 visibility 만을 보장한다.
아래와 같은 특성을 이용하면 베타제어를 수행할 수 있다.

* 특정 쓰레드가 volatile 필드에 write 연산을 하면 해당 필드 뿐만 아니라 그 이전에 이뤄진 모든 write 연산을 다른 쓰레드가 볼 수 있다.
* volatile 필드에 read/write 하기 전과 후에는 reorder 가 일어나지 않는다.

### final
final로 선언된 필드는 인스턴스의 생성자가 끝나면 쓰레드 안전하다.
따라서 final 필드로만 선언된 인스턴스는 synchronized 나 volatile 키워드를 사용할 필요가 없다.
( ConcurrentHashMap 은 final 과 volatile 의 성질을 이용해 synchronized 를 거의 쓰지 않고 멀티쓰레드 환경에서 사용할 수 있는 성능 좋은 Map 을 구현하고 있다. )

* synchronized 로 동기화한 메소드나 블럭은 visibility 를 보장하고 베타제어를 수행한다.

* 초기화가 끝난 final 필드는 생성자가 종료되어야 visibility 가 보장된다.

* 복수 쓰레드에서 공유하는 필드는 synchronized 또는 volatile 로 보호한다.

* 불변 필드는 final 로 보호한다.

* 생성자 안에서 this 를 참조하지 않는다.

###  지연된 인스턴스 초기화가 적용된 쓰레드 안전한 싱글톤 객체 생성 패턴

* 자바의 사양상 클래스의 초기화는 쓰레드 안전성이 보장됨을 이용하여 synchronized 를 사용하지 않고 싱글톤 객체 를 생성할 수 있다.

```
class  LazySingleton {
	private LazySingleton(){}
	private static Class Holder {
		 public static LazySingleton instance = new LazySingleton();
	}
	public static LazySingleton getInstance() {
		 return Holder.instance;
	}
}
```

* 자바 언어로 배우는 디자인 패턴 입문 멀티쓰레드편 (유키히로시 저) 에 나오는 멀티쓰레드 관련 내용들을 정리해 보았습니다.
* 잘못된 부분이 있으면 수정해주세요.  
