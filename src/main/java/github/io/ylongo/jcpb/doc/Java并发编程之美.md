> 《Java并发编程之美》

## 1.1 什么是线程

程序计数器：记录该编程让出CPU时的执行地址，待再次分配到时间片时线程就可以从自己私有的计数器指定地址继续执行。如果执行的是`native`方法，记录的是`undefined`地址，执行Java代码记录的是下一条指令的地址。

## 1.3 线程通知与等待

- `wait()`函数

  当一个线程调用一个共享变量的`wait()`方法时，该线程会被阻塞挂起，直到：

  - 其它线程调用了该共享对象的`notify()`或者`notifyAll()`方法
  - 其它线程调用了该线程的`interrupt()`方法，该线程抛出`InterruptedException`异常返回

如果调用`wait()`方法的线程没有先获取共享变量的监视器锁，则在调用时会抛出`IllegalMonitorStateException`异常。

获取共享变量监视器锁的方式为：

- 执行`synchronized`同步代码块时，使用该共享变量作为参数

  ```java
  synchronized (共享变量) {
  	
  }
  ```

- 调用该共享变量的方法，该方法使用`synchronized`修饰

`虚假唤醒`：一个线程在没有其他线程调用该线程的`notify()`/`notifyAll()`方法，没有被中断，没有等待超时，却从挂起状态变为可运行状态。

> 根本原因就是操作系统底层的`wait()`方法没有保证每次唤醒都是由`notify`触发，而是交给了上层应用去处理

调用共享变量`wait()`方法的经典代码如下：

```java
synchronized (共享变量) {
    while (条件) {
        共享变量.wait();
    }
}
```

当前线程调用共享变量的`wait()`方法后只会释放当前共享变量上的锁，如果当前线程还持有其他共享变量的锁，这些锁不会被释放。

- `notify()`函数

  一个线程调用共享变量的`notify()`方法后，会唤醒一个在该共享变量上调用`wait()`方法后被挂起的线程。

  一个共享变量上可能有多个线程在等待，具体唤醒哪一个线程是随机的。

  被唤醒的线程不会马上从`wait()`方法返回继续执行，还需要与其它线程一起竞争监视器锁，在获得了锁之后才可以继续执行。

  只有当前线程获取到了共享变量的监视器锁之后，才可以调用`notify()`方法，否则会抛出`IllegalMonitorStateException`异常。

- `notifyAll()`函数

  会唤醒所有在该共享变量上调用`wait()`方法而被挂起的线程。

## 1.4 `join()`方法

`Thread`类中的`join()`方法会等待线程执行完成后再继续往下执行。

当线程A调用线程B的`join()`方法后会被阻塞，当其它线程调用线程A的`interrupt()`方法中断线程A时，线程A会抛出`InterruptedException`异常。

## 1.5 `sleep()`方法

当线程调用了`Thread`类中静态的`sleep()`方法后，该线程会暂时让出指定时间的执行权，在这个期间不参与CPU的调度，但是该线程所有拥有的监视器锁是不会让出的。

指定时间到了后，线程处于就绪状态，继续参与CPU的调度。

如果在睡眠期间，其它线程调用了该线程的`interrupt()`方法，则该线程会抛出`InterruptedException`异常。

## 1.6 `yield()`方法

当一个线程调用`Thread`类中静态的`yield()`方法时，会让出自己当前的时间片以及CPU的使用权，然后处于就绪状态，让线程调度器开始下一轮的调度。

线程调度器会从线程就绪队列中获取一个优先级最高的线程，也有可能是该线程。

与`sleep()`方法的区别为：`sleep`方法会让调用线程阻塞指定的时间，在这期间，线程调度器不会去调度该线程。`yield()`方法会调用线程让出剩余的时间片，并没有被阻塞挂起，而是出于就绪状态，线程调度器下一次调度时可能调度当前线程执行。

  