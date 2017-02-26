# How to manage String in Java

String은 `""`로 생성할 경우 `string constant pool`에 할당하고,

`new String()`으로 생성할 경우 `heap의 instance` 영역에 할당되며,

기존에는 perm영역에 있었으나 Java 7부터 heap으로 이동되었음(OOM 이슈때문에).

String은 `"문자열"`과 `new String("");`로 생성할 경우 다른 것은

관리되고 있는 참조영역이 다르기 때문이고,

컴파일러가 바이트 코드도 다르게 생성

(`""`은 `string constant pool의 xxx`, `new String()`은 `heap의 instance`)

`""`로 생성할 경우 `string constant pool`에서 

컴파일러가 애초에 동일한 것들끼리 묶어서 id만 지정해 준다.