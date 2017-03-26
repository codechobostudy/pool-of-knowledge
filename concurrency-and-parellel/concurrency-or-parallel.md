# Concurrency or Parallel

동시성과 병렬성은 서로 다른 개념

동시성은

> 여러 개의 논리적 통제 흐름(`threads of control`)을 가짐. `Thread`는 병렬로 실행될 수도 있고 아닐 수도 있음

병렬성은

> 계산에 필요한 부분을 한꺼번에(병렬로) 실행하며 프로그램에서 사용하는 논리적 통제 흐름은 `n >= 1` 

_동시성_ 이라는 문제 자체가 가진 속성에서의 차이점을 보자

동시성은

> 여러 사건을 한꺼번에(혹은 거의 한꺼번에) 처리해야 하는 요구사항

병렬성은

> 문제가 아닌 해법이 가진 속성으로 프로그램 내의 다른 부분을 병렬로 실행함으로써 해법 자체의 처리 속도를 빠르게 함

동시성은 1개의 프로세스가 여러 개의 Thread를 실행시켜서 동시에 여러 일을 하는 것이고

병렬성은 n개의 프로세스 (`n > 1`) 에게 일을 할당시켜 한꺼번에 처리하는 것이다.

사람으로 생각하면

동시성은 1명이 여러개의 일을 한꺼번에 하는 것이고

병렬성은 여러명이 여러개의 일을 한꺼번에 하는 것이다.

롭 파이크에 의하면

> 동시성은 여러 일을 한꺼번에 다루는 데 관한 것

> 병렬성은 여러 일을 한꺼번에 실행하는데 관한 것

이라고 한다.

# Why confuse concurrency and parallel

전통적으로 사용하는 `Thread`와 잠금장치(`lock`)는ㄴㄴ 병렬성을 직접 지원하지 않기 때문

이들을 이용하여 멀티코어를 활용하는 유일한 방법은 동시적인 프로그램을 작성해 병렬로 동작하는 하드웨어에서 실행하는 것

하지만 이렇게 하기 쉽지 않은 이유는 **동시적인 프로그램**이 비결정적(non-deterministic)이기 때문

타이밍(사건이 일어나는 시점)에 따라 결과가 달라짐

# The architecture of parallel
 최근까지 개별 코어의 속도가 무어의 법칙에 따라 빨라질 수 있었던 것은 

 하나의 코어 내부에 bit와 명령어 수준에서 병렬로 처리되는 추가적인 트랜지스터를 사용할 수 있었기 때문

 
## Bit-level parallelism
32bit 컴퓨터가 8bit 컴퓨터보다 빠른 이유는 병렬성때문

8bit 컴퓨터가 32bit 수를 더하려면 8bit 명령들의 수열을 생성해야 됨

그러나 32bit 컴퓨터는 4byte(32bit)씩 할당해서 병렬로 처리할 수 있음(한 번에)

[Wikipedia](https://en.wikipedia.org/wiki/Bit-level_parallelism)

// TODO Further Information

## Instruction-level parallelism
현대 CPU는 다양한 병렬기법을 사용함

1. pipelining
1. out of order execution
1. speculative execution

row-level에서 Processor가 처리하므로 겉으로는 순차적으로 실행되는 것처럼 보인다(그렇기 때문에 프로그래머가 이 사실을 무시해도 된다고 하지만 알아야 된다고 생각함)

명령어들이 Multi CPU에서 실행됨에 따라서 순차적인 문제를 생각해야 함(e.g [super-scalar](https://en.wikipedia.org/wiki/Superscalar_processor))

// TODO Further information

// TODO Add memory visibility to this.

[Wikipedia](https://en.wikipedia.org/wiki/Instruction-level_parallelism)

## Data parallelism
[SIMD(Single Instruction, Multiple Data)](https://en.wikipedia.org/wiki/SIMD) architecture는 대량의 데이터에 대해 같은 작업을 병렬적인 방식으로 수행하는 것을 가능하게 함

대표적으로 GPU(Graphics Process Unit)이 있음

[Wikipedia](https://en.wikipedia.org/wiki/Data_parallelism)

// TODO Futher information

## Task parallelism

일명 Multi processor

개발자 관점에서 보았을 때,

multi-processor architecture가 지닌 가장 중요하고 특징적인 부분은

Memory Model 그 중에서 memory가 공유가 되는 지 아니면 분산이 되는 지 등과 관련된 내용

[Wikipedia](https://en.wikipedia.org/wiki/Task_parallelism)

### Shared memory
각 processor가 memory의 모든 부분에 자유롭게 접근할 수 있고 processor 끼리의 communication은 memory 자체를 통해서 함

// TODO Further information

### Distributed memory

processor가 memory를 할당받아서 별도로 가지고 있음

processor끼리의 communication은 주로 network를 이용함

network보다 memory를 이용한 communication이 속도가 더 빠르고 간단하기 때문에 

### Conclusion

`Shared memory` multi processor를 위한 코드를 작성하는 것이 더 쉽다

그러나 processor가 일정한 수를 넘어서면 병목(bottlenecks)가 생기므로,

이때는 `Distributed memory`를 사용할 수 밖에 없다.

hardware의 결함(error)에 대비한 장애 허용(`fault tolerant`) system 을 구축할 때도

`Distributed memory`를 사용하는 것은 불가피하다.


### Further more information

[Shared Memory on Wikipedia](https://en.wikipedia.org/wiki/Shared_memory)

[Distributed Memory on Wikipedia](https://en.wikipedia.org/wiki/Distributed_memory)

[Distributed Shared Memory on Wikipedia](https://en.wikipedia.org/wiki/Distributed_shared_memory)

-------
Written by @loustler at 25/03/17

COPYRIGHT loustler

[original post](https://github.com/loustler/learn-concurrency-parallel/wiki/Concurrency-or-Parallel)
