### 003 JVM类加载机制

一个类从加载到使用，一般会经历如下过程：

加载 -> 验证 -> 准备 -> 解析 -> 初始化 -> 使用 -> 卸载

加载：在代码中用到这个类时，“.class”字节码文件会加载这个类到JVM内存里面来。

验证：校验加载进来的“.class”文件是否符合JVM规范。

准备：给类分配内存空间，并对类变量分配内存空间，并赋默认值。

解析：把符号引用替换为直接引用。

初始化：执行类的初始化代码。这个时候才会真正的给变量赋我们编码的值。

初始化类的时机：

1. 通过关键词`new`实例化类时
2. 包含`main()`方法的主类

初始化一个类时，如果它的父类还没初始化，则必须先初始化他的父类。

类加载器：

1. **启动类加载器（Bootstrap ClassLoader）**

   加载Java安装目录下`lib`目录中的核心类库

2. **扩展类加载器（Extension ClassLoader）**

   加载Java安装目录下`lib/ext`目录中的类

3. **应用程序类加载器（Application ClassLoader）**

   加载`classpath`路径中的类。可以理解为加载我们写好的那些类

4. **自定义类加载器**

   自己定义，按照我们的需求去加载类

双亲委派模型：以上类加载器按照加载类的优先级从上到下一次降低。

类加载器在加载一个类时，会先委托给自己的父类加载器，最终会委托到顶层的类加载器，如果类加载器在自己负责加载的范围内没有找到这个类，才会让自己的子类加载器去加载。

如何对`.class`文件进行处理而不会被反编译？

在编译时，对字节码进行加密，在加载时使用自定义的类加载器来解密文件。



---



### 004 JVM内存区域

**方法区：**存放从`.class`文件中加载进来的类，还有常量池。

> JDK 1.8之后叫做`Metaspace`，元数据空间

**程序计数器：**记录当前执行的字节码指令的位置。

每一个线程都会有一个自己的程序计数器，专门用于记录当前线程目前执行到哪条字节码指令。

**Java虚拟机栈：**保存每个方法内局部变量等数据。

每个线程都有自己的虚拟机栈，每执行一个方法，就会创建一个对应的**栈帧**。栈帧里面有方法的局部变量表、操作数栈、动态链接、方法出口等。

**Java堆内存：**存放创建的各种对象。

**本地方法栈：**存放各种`native`方法中的局部变量等数据。

每个线程都有对应的本地方法栈，与Java虚拟机栈类似。

**堆外内存：**这块内存区域不属于JVM，通过`NIO`的`API`可以在Java堆外分配内存空间，然后通过Java虚拟机中的`DirectByteBuffer`来引用和操作堆外内存空间。

一个对象占用的内存空间大致分为两块：

- 对象自己本身的信息
- 对象的实例作为数据占用的空间

> 对象头在64位的Linux上，占用16个字节。`int`类型的实例变量占`4`个字节，`long`类型的占`8`个字节



---



### 008 JVM分代模型

根据对象的生存周期不同，JVM将Java堆内存分为两个区域：年轻代、老年代。

年轻代：创建和使用完之后需要进行回收的对象在里面

老年代：创建之后会长期存在的对象在里面

永久代：其实就是方法区，用来存放类信息



---



### 009 对象在JVM内存中的分配

大部分对象都是优先在新生代分配内存。

新生代内存的垃圾回收被称为`Minor GC`或者`Young GC`。

如果一个实例对象在新生代中，在一定次数的垃圾回收之后，还没有被回收掉，会被转移到老年代中。



---



### 010 设置JVM内存大小

核心参数（括号内是JDK 1.8的参数）：

- `-Xms（-XX:InitialHeapSize）`：堆内存大小

- `-Xmx（-XX:MaxHeapSize）`：堆内存的最大大小

- `-Xmn（-XX:NewSize）`：堆内存中新生代大小。减去这个值就是老年代大小

  > （`-XX:MaxNewSize`）新生代最大大小

- `-XX:PermSize`：永久代大小

- `-XX:MaxPermSize`：永久代最大大小

  > JDK 1.8之后，这个两个参数改为：`-XX:MetaspaceSize`与`-XX:MaxMetaspaceSize`

- `-Xss`：每个线程的栈内存大小



---



### 015 对象被回收的时机

JVM通过**可达性分析算法**来判断对象是否可以被回收。

判断每个对象是否还有一个`GC Roots`，如果有则不会被回收。

在JVM规范中，局部变量、静态变量可以看做是一种`GC Roots`。

Java中不同的引用类型：

- **强引用**：普通的变量引用一个对象
- **软引用**：`SoftReference`修饰的对象
- **弱引用**：`WeakReference`修饰的对象
- **虚引用**：

> 强引用表示绝对不能回收的对象
>
> 软引用表示可有可无的对象，在内存不够的情况下，可以回收
>
> 弱引用就跟没引用一样，发生垃圾回收时可以回收掉

如果某个对象重写了`Object`类中的`finalize()`方法，则会调用该方法去判断实例对象是否被某个`GC Roots`变量重新引用了，如果是则不会被回收。



------



### 016 垃圾回收算法

新生代的回收算法 — **复制算法**

**假的复制算法**：把新生代划分为两块内存区域，只使用其中一块，等这块内存快满时，把里面存活的对象一次性转移到另一块内存区域，这样可以保证没有内存碎片。接着一次性回收原来那块内存中的垃圾对象，再次空出来一块内存区域。两块内存交替使用。

缺点：新生代内存只有一半可以使用，内存使用率低。

优化：一次垃圾回收后，大部分的对象都被垃圾回收，只有小部分对象存活。这部分对象可能是一些长期存活的对象或者还没被使用完的对象。

**真正的复制算法**：把新生代划分为三块：1个`Eden`区，2个`Survivor`区。`Eden`区占80%的空间，每一块`Survivor`区占10%的空间。

对象分配都是在`Eden`区，如果`Eden`区快满了，触发垃圾回收会把`Eden`区中存活的对象转移到一块空着的`Survivor`区。然后清空`Eden`区，接着对象继续分配在`Eden`区，再次垃圾回收会把`Eden`区与使用中的`Survivor`区中存活的对象转移到另一块空的`Survivor`区中，然后清空垃圾对象。如此重复使用三块内存区域。

优点：只有10%的空间被浪费，大部分内存都在使用。



---



### 017 老年代垃圾回收算法

- 默认情况下，当对象躲过了`15`次GC后，会被转移到老年代中

  可以通过参数`-XX:MaxTenuringThreshold`来设置。

- 动态年龄判断：年龄1 + 年龄2 + … + 年龄n的对象总和超过了`Survivior`区域50%，则会把年龄**大于等于n**的对象都放入老年代

- 大对象直接进入老年代

  可以通过参数`-XX:PretenureSizeThreshold`设置，默认为`0`，单位为字节。意思是不管多大都先在`Eden`中分配内存

- `Minor GC`后的对象太多无法放入`Survivor`区，则直接转移到老年代中

老年代空间**分配担保规则：**

- 在执行任何一次`Minor GC`之前，JVM会先检查**老年代可用的内存空间是否大于新生代所有对象的总大小**。

  - 如果大于则执行`Minor GC`

  - 如果小于则会检查`-XX:-HandlePromotionFailure`参数是否设置。
    
    - 如果设置了，则会看**老年代内存大小是否大于之前每次`Minor GC`后进入老年代的对象的平均大小**。
      - 如果大于，则执行`Minor GC`
        - 执行之后发现存活对象大于`Survivor`，也大于老年代可用内存，会产生`Handle Promotion Failure`异常，触发`Full GC`
      - 如果小于，则执行`Full GC`
    - 如果没设置，则执行`Full GC`
    
    如果执行完`Full GC`后，老年代还是没有足够的空间存放`Minor GC`后的存活对象，则会导致`OOM`异常。

老年代垃圾回收算法 — **标记整理算法**

- 标记老年代当前存活的对象，把存活对象移动到一边，让这些对象紧凑的靠在一起，避免垃圾回收后产生过多的内存碎片。

> `-XX:HandlePromotionFailure`在JDK 1.6之后被废弃了。JDK 1.6之后，只要判断“老年代可用空间” > “新生代对象总和”或者“老年代可用空间” > "`Minor GC`后进入老年代对象的平均大小"，两个条件满足一个，就会进行`Minor GC`，不会触发`Full GC` — （025）

---



### 018 常见垃圾回收器

`Serial`、`Serial Old`垃圾回收器：分别用来回收新生代、老年代的垃圾对象

> 单线程运行，垃圾回收时会停止应用的线程，再进行垃圾回收。（现在一般不用）

`ParNew`垃圾回收器一般用于新生代。`CMS`垃圾回收器用于老年代。

> 它们都是多线程并发回收，大大缩短回收的时间

`G1`垃圾回收器统一收集新生代和老年代。



---



### 022 年轻代垃圾回收器ParNew

使用`-XX:+UseParNewGC`指定使用`ParNew`垃圾回收器对新生代进行垃圾回收。

`ParNew`垃圾回收器默认的线程数量为CPU的**核数**。

> 可通过`-XX:ParallelGCThreads`参数来指定



---



### 023 老年代垃圾回收器CMS

`CMS`使用的是标记整理算法。

在`Stop the World`的时候，如果采用标记整理算法去回收垃圾，会导致系统长时间卡顿。所以`CMS`采取的是**垃圾回收线程与应用工作线程尽量同时执行的模式来处理。**

因此`CMS`执行垃圾回收时会分为4个阶段：

- 初始标记

  会让应用的线程全部停止，进入`Stop the Wolrd`状态。标记所有`GC Roots`**直接**引用的对象

  > 方法的局部变量和类的静态变量是`GC Roots`，类的实例变量不是`GC Roots`
  >
  > 虽然会进入`Stop the World`状态，但是速度非常快，因为仅仅标记`GC Roots`直接引用的对象

- 并发标记

  让应用继续运行，对老年代里所有间接引用的对象进行`GC Roots`追踪。最耗时

- 重新标记

  进入`Stop the World`状态，对第二阶段产生的对象重新标记

  > 速度较快，因为只是对变动过的对象进行标记

- 并发清理

  让应用继续运行，清理掉标记为垃圾的对象



---



### 024 设置垃圾回收参数

垃圾回收器在并发标记与并发清理阶段，会占用CPU资源。`CMS`默认启动的垃圾回收线程数量为：`(CPU核数 + 3) / 4`

> 所以`CMS`的第一个问题是会消耗CPU资源

**浮动垃圾** — 在并发清理阶段，可能会有对象进入老年代并变成垃圾对象。

所以为了给老年代预留一点空间，当老年代占用一定比例时会触发`CMS`的垃圾回收。

> 通过`-XX:CMSInitiatingOccupancyFaction`进行设置
>
> JDK 1.6的默人值为`92%`

如果在`CMS`垃圾回收期间，要进入老年代的对象大于可用内存，会发生`Concurrent Mode Failure`异常。这时会用`Serial Old`垃圾回收器代替`CMS`，直接强制系统进入`Stop the World`状态，重新进行`GC Roots`追踪，标记全部垃圾对象，然后再回收，再恢复应用运行。

`CMS`通过如下两个参数来控制内存的整理：

- `-XX:+CMSCompactAtFullCollection`：表示`Full GC`之后进入`Stop the World`，停止工作线程，进行碎片整理，把存活对象移到一起。

  默认打开。

- `-XX:+CMSFullGCsBeforeCompaction`：表示执行多少次`Full GC`后再执行一次内存碎片整理。

  默认为`0`，即每次都进行一次内存整理



---



### 029 G1垃圾回收器的工作原理

`G1`垃圾回收器可以同时回收新生代和老年代的对象。它把Java堆内存分为多个大小相等的`Region`，每个`Region`可能属于新生代，也可能属于老年代，由`G1`自动控制。

我们可以设置垃圾回收的预期停顿时间。`G1`会追踪每个`Region`中可回收对象的大小和预估时间，在垃圾回收时，尽量把垃圾回收对应用造成的影响控制在指定的时间范围内，在有限的时间内尽可能多的回收垃圾对象。



---



### 030 G1的内存模型和分配规则

可以通过`-XX:+UseG1GC`来指定使用`G1`垃圾回收器。

JVM最多有`2048`个`Region`，每个`Region`的大小必须是`2`的倍数。默认情况下，每个`Region`的大小就是堆内存除以`2048`。

可以通过`-XX:G1HeapRegionSize`来指定每个`Region`的大小，取值范围从`1M`到`32M`，且是`2`的倍数。

默认情况下，新生代占堆内存的`5%`，可以通过`-XX:G1NewSizePercent`来指定新生代初始占比，通过`-XX:G1MaxNewSizePercent`来指定新生代的最大占比，默认为`60%`。

还是通过`-XX:SurvivorRatio`来区分`Eden`和`Survivor`的比例。

一旦新生代在堆内存中的占比达到最大大小，会触发`G1`新生代垃圾回收，回收算法为复制算法。通过`-XX:MaxGCPauseMillis`来设置GC的停顿时间，默认为`200ms`。

对象从新生代进入老年代的条件：

- 对象的年龄达到了指定的值
- 动态年龄判断

`G1`提供了专门的`Region`来存放大对象，而不是让大对象直接进入老年代的`Region`中。如果一个对象的大小超过了`Region`大小的`50%`，就会被当作大对象。

如果一个大对象超过了一个`Region`的大小，则会被放入多个`Region`中。在新生代、老年代进行回收时，会对大对象`Region`一起回收。



---



### 031 G1垃圾回收机制

当老年代占了堆内存`45%`的`Region`时，会触发新生代与老年代的混合回收（`Mixed GC`）。会对新生代、老年代、大对象进行回收。

这个占比可以通过`-XX:InitiatingHeapOccupancyPercent`进行设置，默认值为`45%`。

`G1`混合回收过程：

- 初始标记

  进入`Stop the World`状态，仅仅标记一下`GC Roots`直接引用的对象

- 并发标记

  允许应用继续运行，同时进行`GC Roots`追踪所有存活的对象

- 最终标记

  进入`Stop the World`状态，根据并发标记阶段记录的对象，标记对象是否存活

- 混合回收

  计算老年代中每个`Region`中存活对象的数量、占比，垃圾回收的预期性能和效率。停止应用，进行垃圾回收，为了让垃圾回收的停顿时间控制在我们指定的范围内，并尽可能的回收多的垃圾，可能只会选择部分`Region`进行回收。
  
  这个阶段可以执行多次。这样是为了不让应用停顿时间过长。可以通过参数`-XX:G1MixedGCCountTarget`来控制回收次数，默认值为`8`

`-XX:G1HeapWastePercent`用来控制在垃圾回收阶段中，当空闲出来的`Region`数量占堆内存一定比例时，就会停止混合回收。默认值为`5%`。

`-XX:G1MixedGCLiveThresholdPercent`用来控制`Region`中存活对象达到一定比例才进行回收。默认值为`85%`。

在进行`Mixed GC`时，无论是年轻代还是老年代都基于复制算法进行回收，需要把各个`Region`的存活对象拷贝到别的`Region`中去。如果没有空闲的`Region`，则会导致GC失败，立即停止应用运行，然后采用`Serial Old`对老年代进行标记、清理、整理，空闲出来一批`Region`。



---



### 044 查看Young GC日志

```java
CommandLine flags: -XX:InitialHeapSize=... -XX:MaxHeapSize=... ...
```

表示程序运行时的JVM参数。

```java
0.313: [GC (Allocation Failure) 0.313: [ParNew: 4096K->511K(4608K), 0.0048371 secs] 4096K->1472K(9728K), 0.0051507 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
```

- `0.313`：表示系统运行多少秒后发生了本次GC
- `GC (Allocation Failure)`：表示`Eden`区内存不够了，对象分配失败，所以触发了一次`Young GC`
- `ParNew: 4096K->511K(4608K), 0.0048371 secs`：
  - `ParNew`：表示使用的是`ParNew`垃圾回收器
  - `4096K`：表示年轻代已使用的空间
  - `511K`：表示GC之后存活下来的对象
  - `4608K`：表示年轻代可用空间，`Eden`区 + `1个Survivor`区
  - `0.0048371 secs`：表示本次GC的耗时
- `4096K->1472K(9728K), 0.0051507 secs`：
  - `4096K`：表示GC之前堆内存的已占用空间
  - `1472K`：表示GC之后堆内存中存活下来的对象
  - `9728K`：堆内存的可用空间
- `Times: user=0.00 sys=0.00, real=0.01 secs`：表示本次GC的耗时

```java
Heap
 par new generation   total 4608K, used 2101K ...
  eden space 4096K,  51% used ...
  from space 512K,   0% used ...
  to   space 512K,   0% used ...
 concurrent mark-sweep generation total 5120K, used 1577K ...
 Metaspace       used 3523K, capacity 4498K, committed 4864K, reserved 1056768K
  class space    used 387K, capacity 390K, committed 512K, reserved 1048576K
```

JVM在退出时打印的堆内存的使用情况。

- `par new generation total 4608K, used 2101K`：表示`ParNew`垃圾回收器负责的年轻代总共为`4608K`，目前使用了`2101K`

  - `eden space 4096K,  51% used`：表示`Eden`区的内存占用

  - `from space 512K,   0% used`、`to   space 512K,   0% used`：表示两个`Survivor`区为空

  - `concurrent mark-sweep generation total 5120K, used 1577K`：表示`CMS`垃圾回收器负责的老年代总共为`5120K`，已经使用了`1577K`

  - `Metaspace`、`class space`：表示`MetaSpace`元数据空间和`Class`空间存放的类信息、常量池等，他们的总容量、使用内存等

    > JDK 1.8取消了`PermGen Space`，取而代之的是`MetaSpace`，方法区移至`MetaSpace`
    >
    > JDK 1.8把类的元数据放入本地内存（native heap），称之为`MetaSpace`
    >
    > `MetaSpace`由一个或多个虚拟空间组成。虚拟空间是操作系统的连续存储空间，按需分配。分配时，虚拟空间会向操作系统预留（reserve）空间，但还没有被提交（committed）
    >
    > `MetaSpace`是预留空间是全部虚拟空间的大小。虚拟空间的最小分配单位是`MeatChunk`（也可以说是`Chunk`）。当新的`Chunk`被分配到虚拟空间时，与`Chunk`相关的内存空间就被提交了。
    >
    > `MetaSpace`的`committed`指的是所有`Chunk`的大小。当类加载器被回收时，所有与之相关的`Chunk`都会被释放，被放入一个全局的列表中进行维护。`capacity`表示所有被分配过，但是没有被释放过`Chunk`的大小



---



### 046 模拟对象进入老年代的场景

`Young GC`后存活的对象，如果`Survivor`区域放不下，会有部分对象进行老年代。



---



### 050 jstat

`jstat -gc PID` 查看Java进程的内存和GC情况

> `jps`可以看到Java进程的PID

|      |                                                |
| :--: | :--------------------------------------------- |
| S0C  | From Surviror区的大小                          |
| S1C  | To Survivor区的大小                            |
| S0U  | From Survivor区当前已使用的内存大小            |
| S1U  | To Survivor区当前已使用的内存大小              |
|  EC  | Eden区的大小                                   |
|  EU  | Eden区当前已使用的内存大小                     |
|  OC  | 老年代的大小                                   |
|  OU  | 老年代当前已使用的内存大小                     |
|  MC  | 方法区（永久代、元数据区）的大小               |
|  MU  | 方法区（永久代、元数据区）当前已使用的内存大小 |
| YGC  | 系统运行到现在的Young GC次数                   |
| YGCT | Young GC的耗时                                 |
| FGC  | 系统运行到现在的Full GC次数                    |
| FGCT | Full GC的耗时                                  |
| GCT  | 所有GC的总耗时                                 |



---



### 051 使用jmap

- `jmap -histo PID`

  打印各种对象占用内存空间的大小，按照降序排序

- `jmap -dump:live,format=b,file=fileName.hprof PID`

  在当前目录下生成一个`fileName.hprof`的内存快照



---



### 058 优化FullGC的性能

`-XX:+CMSParallelInitialMarkEnabled`在`CMS`垃圾回收器的`初始标记`阶段开启对线程并发执行。

`-XX:+CMSScavengeBeforeRemark`在`CMS`的`重新标记`阶段之前，尽量先执行一次`Young GC`， 回收掉年轻代中没人引用的对象，那么在`CMS`的`重新标记`阶段就可以少扫描一些对象，减少耗时。

> 之所以`CMS`会扫描到年轻代是因为可能有些年轻代的对象引用了老年代的对象，提前做`Young GC`可以回收掉年轻代中的对象，减少耗时



------



### 059 频繁Full GC的不合理参数

`-XX:SoftRefLRUPolicyMSPerMB`表示每1MB的空闲内存空间允许`SoftReference`对象存活多久，默认为`1000`毫秒

> JVM在反射过程中动态生成的类`Class`对象都是通过`SoftReference`修饰的软引用对象

GC时判断`SoftReference`对象是否需要回收通过如下公式进行判断：

`clock - timestamp <= freespace * SoftRefLRUPolicyMSPerMB` 

- `clock - timestamp`表示一个软引用对象多久没有被访问过了
- `freespace`表示JVM中空闲的内存空间



------



### 064 内存泄漏分析

频繁导致`Full GC`的可能性如下：

- 内存分配不合理，导致对象频繁进入老年代，进而引发`Full GC`
- 存在内存泄漏。也就是内存里面驻留了大量的对象塞满了老年代，导致稍微有一些对象进入老年代就会触发`Full GC`
- 大量使用反射导致永久代里面的类太多，触发了`Full GC`
- 代码中调用`System.gc()`

通过`jmap`导出内存快照，再使用`MAT`进行分析内存是否泄漏



---



### 082 JVM内存溢出时dump内存快照

`-XX:+HeapDumpOnOutOfMemoryError`：`OOM`时自动dump内存快照

`-XX:HeapDumpPath=file_path`：内存快照存放的路径

`-XX:+PrintGCDetails`：打印GC日志

`-Xloggc:file_path`：GC日志存放路径



### 097_G1垃圾回收器的分区

Region是G1垃圾回收器内存管理的基本单位，也是最小单位

分区类型：

- 新生代分区：Young Heap Region（YHR）

- 自由分区：Free Heap Region（FHR）

- 老年代分区：Old Heap Region（OHR）

- 大对象分区：Humongous Heap Region（HHR）

---

#### ParNew +CMS 与G1的内存模型对比

![ParNew+CMS与G1垃圾祸首的分代模型](images/ParNew+CMS与G1垃圾祸首的分代模型.png "ParNew+CMS与G1垃圾祸首的分代模型")

- 优势
  - 它不用做很多复杂分区管理的相关的东西
  - 并且，小内存的时候，垃圾回收其实不会造成很大的停顿

- 劣势

  用ParNew +CMS，大内存回收GC时间长

内存分代模型中，传统的分代模型是按照块儿状来做内存分配，这种分配管理的方式，会有一些不足。比如在大内存机器的场景下，会出现一次GC时间过长，导致stop the world时间较长，严重的情况下，会对用户体验造成比较大的影响

针对大内存场景，诞生了G1这种垃圾回收器，G1管理内存的时候，化整为零，直接将内存块儿，分割成了n个小内存块儿，根据需求，动态的分配给新生代，老年代，大对象来使用，同时G1 会根据垃圾回收的情况动态改变新生代（包括老年代、大对象分区）的大小（region个数）

---

#### G1如何设置Region的大小？

- 手动式

  通过参数设置`-XX:G1HeapRegionSize`，默认为0

  Region的大小限制，范围为`[1-32MB]`，并且要满足是2的n次方，即`1 2 4 8 16 32`

  如果指定的值不是这个范围内的值，G1会根据一定的算法规则自动调整

- 启发式推断

  G1本身通过算法自动计算 G1根据内存（堆内存大小、分区的个数）的情况自动计算出region大小

---

#### Region的大小会对垃圾回收造成什么影响？

- 如果Region过小，比如说，256KB

  会造成对象分配的性能问题

  系统运行，创建的对象，一般也有可能是几十到几百KB

  - 第一，找到一个可以使用的region难度增加

    分区越小会导致region数量越多，随着程序的运行，分配对象找到一个剩余内存可用region的次数越来越多

  - 第二，跨分区存储的概率增加

    稍微大一点的对象，就超过了一个region的大小，需要跨区存储

- 如果Region过大，比如说，64MB，128MB，256MB

  造成GC性能问题

  - 第一：region回收价值的判定很麻烦

    内存越大做回收价值判断比小region难度大

  - 第二：回收的判定过程会更加复杂

    GC ROOTs，追踪标记对象，然后标记垃圾对象，遇到跨代，跨区的存储，还要做一些额外的其他处理，导致回收需要的时间更长

    在做垃圾回收的时候，是不是需要判断那些对象，需要回收。这个过程就会更加复杂

 为了平衡分配效率和回收的效率。在保证两者的性能的同时，设置一个合理的region分区大小

---

#### regionSize为什么要设置成2的n次方？

- 第一，造成内存碎片，内存浪费的问题

  如果regionSize不是2的n次方，就不能被整除，会导致分区数量不是整数，那么会导致内存碎片，有一部分没有利用上

- 第二，计算机底层无法利用2进制计算速度快的特性

---

#### 默认情况下，regionSize到底是如何计算的？

 计算公式：`region_size = max((InitialHeapSize + MaxHeapSize) / 2 / 2048, 1MB)`

- 堆分区的个数

  默认2048个，这个数字会根据具体的内存大小自动计算

  计算regionsize的时候，会直接用这个默认的2048这个值

- 堆内存的大小

  默认最大96MB，最小为0MB

  设置`InitialHeapSize`相当于设置`Xms`，设置`MaxHeapSize`相当于设置`Xmx` 

举几个例子：

- 第一种，只指定region大小

  假如region大小设置为2MB，则G1的总内存大小为，2048 * 2MB = 4GB

- 第二种，指定堆内存大小，且最大值等于最小值

  假如设置堆内存大小： Xms,Xmx，并且Xms = Xmx=32GB，则regionSize = max((32GB+32GB)/2 / 2048),1MB) = 16MB

- 第三种，指定堆内存大小，且最大值不等于最小值

  假如设置堆内存大小Xms,Xmx，并且Xms = 32GB，Xmx=128GB，则RegionSize = max((32GB+128GB)/2/2048,1MB) = 32MB

  并且由于G1垃圾回收器会自动计算分区个数，在这个例子中，分区的个数范围在32GB/32MB=1024 ~ 128GB/32MB=4096之间

---

### 098_region大小的C++源码实现剖析

#### regionSize如果不符合规则，G1是怎么处理的？

G1会自动和2^n对齐

---

#### 对齐的规则是什么？超过了大小限制，会怎么做？

向2的n次幂对齐

计算regionSize得到的结果不是2^n的话，就会向2^n对齐，具体的对齐规则就是，从计算得到的数字中，找到数字里包含的最大的2^n幂

---

从源码中探索一下具体的regionSize实现

```c++
void HeapRegion::setup_heap_region_size(size_t initial_heap_size, size_t max_heap_size) {

    uintx region_size = G1HeapRegionSize;

     if (FLAG_IS_DEFAULT(G1HeapRegionSize)) { // 如果是默认的
      	size_t average_heap_size = (initial_heap_size + max_heap_size) / 2;
		region_size = MAX2(average_heap_size / TARGET_REGION_NUMBER, (uintx) MIN_REGION_SIZE);
     }

	// 2MB = 2 * 1024 * 1024 B = 2^21  -->  21
	// 1.5MB = 1.5 * 1024 * 1024 = 2^20 < 1.5 * 2^20 < 2 ^ 21  -->  20
	// 3MB = 3 * 1024 * 1024  2^21 < 1.5 * 2 * 1024 * 1024 < 2^22  -->  21
	// 64MB = 64 * 1024 * 1024 = 2^26  -->  26
	// 取对数（离最近的2^N中N的值）
	int region_size_log = log2_long((jlong) region_size);

    // 计算region_size的值
	region_size = ((uintx)1 << region_size_log);

	// Now make sure that we don't go over or under our limits.[1,32]
    if (region_size < MIN_REGION_SIZE) { // 1
		region_size = MIN_REGION_SIZE;
    } else if (region_size > MAX_REGION_SIZE) { // 32
		region_size = MAX_REGION_SIZE;
	}

	// 重新计算regionSize的2的指数
	region_size_log = log2_long((jlong) region_size);
}
```

#### 基于regionSize分区数量的变动过程

首先要明确一点，我们从源码中可以看出来，在计算regionSize的时候，会使用一个默认的2048这么一个参数来计算regionSize，然后regionSize会被动态调整成一个合理的值。所以说我们所说的2048只是一个默认值，也就是说，在使用2048这个值计算完成之后，如果regionSize没有调整，并且堆内存不会动态扩展的时候，堆分区的数量才是2048这个值！

> G1不能手动指定分区个数

### 099_新生代分区及自动扩展

####  新生代内存分配方式

- 参数指定方式

  - 第一种：指定堆内存新生代的大小，具体参数：`MaxNewSize`、`NewSize`

    需要注意的是，如果只设置了`Xmn`这个参数，在G1里面，默认是认为相当于设置了`MaxNewSize = NewSize = Xmn`，说明新生代内存的大小是固定的

  - 第二种：指定了新生代的占比，具体参数：`NewRatio`

    这个参数是用来设置老年代比新生代的比例的

    > 例如，-XX:NewRatio=4，则代表老年代：新生代=4:1

    - 如果只设置了`NewRatio`，则对于新生代而言，`MaxNewSize = NewSize`，也就是新生代最大值最小值是相等的

      即新生代空间大小 = heapSize / (NewRatio+1)

    - 如果设置了`MaxNewSize`、`NewSize`，同时又设置了`NewRatio`，此时`NewRatio`会被忽略

  **一般来说，在G1垃圾回收器里面，我们不推荐直接自己指定新生代的大小，并且指定成一个固定值**

- G1启发式推断

  - 第三种：没有指定新生代`MaxNewSize`最大值和`NewSize`最小值，或者只设置了其中一个

    G1会根据`G1MaxNewSizePercent`（默认60%）和`G1NewSizePercent`（5%），来计算新生代内存大小

    此时新生代初始化的大小就是5%的堆内存空间，然后最大就是60%

**一般来说，都是设定好堆内存的大小，然后新生代比例，新生代内存的大小，让G1自动推断**

除非，你系统运行了很长时间，你发现了一个非常合理的**新生代的范围**，那么你可以考虑，把新生代的内存设置一下，一定是`MaxNewSize`和`NewSize`不相等

#### 老年代内存是多少

老年代内存没有一个固定的大小，也没有具体的参数来设置，除非是设置了`NewRatio`这个参数，会间接设置老年带的大小

`-XX:InitiatingHeapOccupancyPercent`这个参数，代表老年代45%的内存占用的时候会触发mixedgc，也就是混合回收

老年代内存使用的比例，默认最高是45%

> `InitiatingHeapOccupancyPercent`在JDK8b12之前表示的是整个堆，之后表示的老年代占比
>
> 具体见：[关于G1收集器参数InitiatingHeapOccupancyPercent的正确认知](https://doudaxia.club/index.php/archives/212/)

##### 如何满足G1新生代的动态扩展机制？

不要指定新生代的大小为一个固定值，不要直接指定`Xmn`，也不要直接只设置一个`NewRatio`，或者指定MaxNewSize = NewSize

如果真的需要设置新生代的值，可以设置成范围，比如，MaxNewSize=100 NewSize=10，但是这个范围如果你设置的不是很合理，很有可能还是会有性能问题

##### 为什么要满足G1新生代的动态扩展？

为了满足用户设定的停顿时间，也就是期望停顿时间，满足期望停顿时间，就需要做一个垃圾回收时间和程序运行时间的平衡

控制回收时间在一个范围内，就只能根据我们回收的时间，回收的内存空间的大小来做综合计算。动态调整内存分区的占比，来满足回收时间

如果不做动态调整，那么GC时间过长，就没办法满足停顿时间。

动态增加，动态减少，才能调整到一个合理的值。一旦超过了时间范围，就再调整一下

G1的新生代的动态扩展，可以帮助我们做到，动态调整YGC所需要的时间

##### 新生代的动态扩展是怎么实现的？

每一种类型的分区有一个分区列表，新生代分区列表，老年代分区列表，大对象，自由分区。如果说新生代需要扩展的时候，此时就从自由分区拿一些region，加入到新生代分区列表中

如果说，自由分区没有了，无法给新生代提供分区了，这个时候怎么办？找JVM去拓展新的分区，然后加入到新生代分区列表中，然后继续分配对象

#### G1是怎么扩展新分区的？有什么规则限制？

##### 扩展新分区的规则是什么？

是根据`-XX:GCTimeRatio`这个参数去控制，这个参数表示，GC与应用的耗费时间比

G1中默认这个值是99

如果G1 GC时间与应用运行的时间占比不超过1%的时候，不需要动态扩展，如果GC时间占比超过了这个阈值，就需要做动态扩展

##### 扩展的内存大小分区数量有什么限制？

有一个参数`G1ExpandByPercentOfAvailable`（默认是20）

每次扩展的时候都从未使用的内存中申请20%的空间。并且，最小不能小于1MB，最大不能超过已经使用内存的一倍

