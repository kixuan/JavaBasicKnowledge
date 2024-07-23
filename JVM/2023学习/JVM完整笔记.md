![在这里插入图片描述](https://img-
blog.csdnimg.cn/f10e586ae9624fb6979ff44f2a87716c.png#pic_center)

>
>
这是我在看课程《[黑马程序员JVM完整教程](https://www.bilibili.com/video/BV1yE411Z7AP/?vd_source=3eaa9d17f2454e1ae80abc50d16e66b5)
》过程中记的笔记。我觉得该课程总时不长，并且理论+实战是一个入门JVM的好课程。  
> 若你看完该课程可以看下面几个参看书进一步深入了解JVM
>
>   * 深入理解Java虚拟机（第二版）
>   * 实战Java虚拟机
>   * 深入JAVA虚拟机第二版
>

>
> 这三本参考书的pdf版本已经放在下面的网盘中(只限个人看)  
> 链接：https://pan.baidu.com/s/1iNJcbSecMwnOQuaJNxzrRQ  
> 提取码：wcar

## 1.内存结构

### 1.1 程序计数器

![image-20220720222405638](https://img-
blog.csdnimg.cn/img_convert/025701ff1110cb1ca3801ae56f125e67.png)

Program Counter Register 程序计数器（寄存器）

* 作用，是记住下一条jvm指令的执行地址
* 特点
    * 是线程私有的
    * 不会存在内存溢出

下面是一条Java程序的指令：  
![image-20220720221813565](https://img-
blog.csdnimg.cn/img_convert/a0d504b441fe13598aca723af9dd8f21.png)

### 1.2 虚拟机栈

![image-20220720230437288](https://img-
blog.csdnimg.cn/img_convert/bffcfbfca00b55471ab06fbec7bcdc3c.png)

**定义：**Java Virtual Machine Stacks （Java 虚拟机栈）

* 栈-每个线程运行时需要的内存空间。所以跟程序计数器一样线程私有的结构。
* 栈由多个栈帧组成，每个栈帧对应每个方法运行时需要的内存，包含方法参数，局部变量，返回地址等信息。
* 每个线程只能有一个活动栈帧，对应着当前正在执行的那个方法

![image-20220720223105927](https://img-
blog.csdnimg.cn/img_convert/e1e2ea31ac529938fc638988b41375b1.png)

**问题辨析：**

1.垃圾回收是否涉及栈内存？

答案：不会

2.内存分配越大越好吗？

答案：不是，-Xss 参数来设置栈空间，如果栈空间设置过大，会降低线程数，因为机子的物理内存空间大小固定的。

3.方法内的局部变量是否线程安全？

* 如果方法内局部变量没有逃离方法的作用访问，它是线程安全的
* 如果是局部变量引用了对象，并逃离方法的作用范围，需要考虑线程安全

**栈内存溢出**

> java.lang.StackOverflowError

* 栈帧过多导致栈内存溢出
* 栈帧过大导致栈内存溢出

**线程运行诊断**

* cpu占用过多案例
    * 用top定位哪个进程对cpu的占用过高
    * ps H -eo pid,tid,%cpu | grep 进程id （用ps命令进一步定位是哪个线程引起的cpu占用过高）
    * jstack 进程id
        * 可以根据线程id 找到有问题的线程，进一步定位到问题代码的源码行号
* 程序运行很长时间没有结果案例
    * jstack 进程id 来查看是否出现死锁问题

### 1.3 本地方法栈

![image-20220720230522325](https://img-
blog.csdnimg.cn/img_convert/64b5cd4608c3af65b06fe5c690a0e589.png)

* 作用：给本地方法提供运行空间

### 1.4 堆

![image-20220720230902088](https://img-
blog.csdnimg.cn/img_convert/dbdd8c22d46bae24d21523028b9b0109.png)

**Heap 堆**

* 通过 new 关键字，创建对象都会使用堆内存

**特点**

* 它是线程共享的，堆中对象都需要考虑线程安全的问题
* 有垃圾回收机制

**堆内存溢出**

> java.lang.OutOfMemoryError:Java head space

![image-20220720231131516](https://img-
blog.csdnimg.cn/img_convert/ba2c150d41d970eb6015723026f7815e.png)

* -Xmm 设置堆空间

**堆内存诊断**

* jps 工具

查看当前系统中有哪些 java 进程

* jmap 工具

查看堆内存占用情况 jmap - heap 进程id

* jconsole 工具

图形界面的，多功能的监测工具，可以连续监测

**案例**

* 垃圾回收后，内存占用依然很高
    * jps:查看当前系统中有哪些 java 进程
    * jmap:查看堆内存占用情况 jmap - heap 进程id
    * jconsole :执行垃圾回收，并实时查看
    * jvisualvm:可视化的方式显现，jvm内存结构

### 1.5 方法区

![image-20220720232505950](https://img-
blog.csdnimg.cn/img_convert/fc747b554896d8993b47dc41d1deab6c.png)

**定义**

* Java 虚拟机有一个在所有 Java
  虚拟机线程之间共享的方法区。方法区类似于传统语言的编译代码的存储区，或者类似于操作系统进程中的“文本”段。它存储每个类的结构，例如运行时常量池、字段和方法数据，以及方法和构造函数的代码，包括类和实例初始化和接口初始化中使用的特殊方法。
*
方法区是在虚拟机启动时创建的。尽管方法区在逻辑上是堆的一部分，但简单的实现可能会选择不进行垃圾收集或压缩它。本规范不要求方法区域的位置或用于管理已编译代码的策略。方法区域可以是固定大小，也可以根据计算需要扩大，如果不需要更大的方法区域，可以缩小。方法区的内存不需要是连续的。
* Java 虚拟机实现可以为程序员或用户提供对方法区域初始大小的控制，以及在方法区域大小可变的情况下，对最大和最小方法区域大小的控制。

**组成：**

![image-20220720233029357](https://img-
blog.csdnimg.cn/img_convert/f6d1430b4d9631bc9c94cb5eabfc91d6.png)

**方法区内存溢出**

* 1.8 以前会导致永久代内存溢出

![image-20220720233539735](https://img-
blog.csdnimg.cn/img_convert/47a4b2a8f4946120aa752cf094d8e239.png)

* 1.8 之后会导致元空间内存溢出

![image-20220720233618338](https://img-
blog.csdnimg.cn/img_convert/62f4b56409809783757d95170efa88fc.png)

**运行时常量池**

* 常量池，就是一张表，虚拟机指令根据这张常量表找到要执行的类名、方法名、参数类型、字面量等信息
* 运行时常量池，常量池是 *.class 文件中的，当该类被加载，它的常量池信息就会放入运行时常量池，并把里面的符号地址变为真实地址

**StringTable**

先看几道面试题：

![image-20220720235226829](https://img-
blog.csdnimg.cn/img_convert/2e1355d54dcc435706c4e1f29f3c9922.png)

* 常量池中的字符串仅是符号，第一次用到时才变为对象
* 利用串池的机制，来避免重复创建字符串对象
* 字符串变量拼接的原理是 StringBuilder （1.8）
* 字符串常量拼接的原理是编译期优化
* 可以使用 intern 方法，主动将串池中还没有的字符串对象放入串池
    * 1.8 将这个字符串对象尝试放入串池，如果有则并不会放入，如果没有则放入串池， 会把串池中的对象返回
    * 1.6 将这个字符串对象尝试放入串池，如果有则并不会放入，如果没有会把此对象复制一份，放入串池， 会把串池中的对象返回
* 调整 -XX:StringTableSize=桶个数

### 1.6 直接内存

**定义** ：Direct Memory

* 常见于 NIO 操作时，用于数据缓冲区
* 分配回收成本较高，但读写性能高
* 不受 JVM 内存回收管理

**基本原理**

* 不使用直接内存的时候I/O操作过程

![image-20230322131846007](https://img-
blog.csdnimg.cn/img_convert/51ec00d01dc513292cc5c22b5c0536e6.png)

* 使用直接内存

![image-20230322132043963](https://img-
blog.csdnimg.cn/img_convert/6862b95a3333b7077245af1d3372b0bb.png)

**分配和回收原理**

* 使用了 Unsafe 对象完成直接内存的分配回收，并且回收需要主动调用 freeMemory 方法
* ByteBuffffer 的实现类内部，使用了 Cleaner （虚引用）来监测 ByteBuffffer 对象，一旦ByteBuffffer 对象被垃圾回收，那么就会由
  ReferenceHandler 线程通过 Cleaner 的 clean 方法调用 freeMemory 来释放直接内存

## 2.垃圾回收器

![](https://img-
blog.csdnimg.cn/img_convert/818ab96e8f5115c6abd58148637abed3.png)

### 2.1 如何判断对象可以回收

**方法一：引用计数法 （早期的Python虚拟机用过）**

**概念：**

* 如果一个对象被引用了那么对应的引用计数器加一，反正减一，当等于零时说明该对象可以回收。

**好处：**

* 简单易解

**坏处：**

* 如果存在循环引用，那么该方法失效了

![image-20230322134208697](https://img-
blog.csdnimg.cn/img_convert/3e1ccbd7c1ec37248bdae4b0119c686a.png)

**方法二：可达性分析算法 **

* Java 虚拟机中的垃圾回收器采用可达性分析来探索所有存活的对象
* 扫描堆中的对象，看是否能够沿着 GC Root对象 为起点的引用链找到该对象，找不到，表示可以回收
* 哪些对象可以作为 GC Root ?
    * 那些肯定不能当作垃圾来回收的对象

**四种引用**

![image-20230322140044715](https://img-
blog.csdnimg.cn/img_convert/5e167035dc054edfb34e2cfb3837b9df.png)

1. **强引用**

只有所有 GC Roots 对象都不通过【强引用】引用该对象，该对象才能被垃圾回收

2. **软引用（SoftReference）**

仅有软引用引用该对象时，在垃圾回收后， **内存仍不足** 时会再次出发垃圾回收，回收软引用对象可以配合引用队列来释放软引用自身

3. **弱引用（WeakReference）**

仅有弱引用引用该对象时，在垃圾回收时，无论内存是否充足，都会回收弱引用对象可以配合引用队列来释放弱引用自身

4. **虚引用（PhantomReference）**

必须配合引用队列使用，主要配合 ByteBuffffer 使用，被引用对象回收时，会将虚引用入队，由 Reference Handler
线程调用虚引用相关方法释放直接内存

5. **终结器引用（FinalReference）**

无需手动编码，但其内部配合引用队列使用，在垃圾回收时，终结器引用入队（被引用对象暂时没有被回收），再由 Finalizer
线程通过终结器引用找到被引用对象并调用它的 finalize方法，第二次 GC 时才能回收被引用对象

### 2.2 垃圾回收算法

**算法一：标记清除算法**

**定义：**

* Mark Sweep，第一步用可达性标记算法标记可回收和不可回收对象。第二步，删除那些可回收的对象。

![image-20230322143909247](https://img-
blog.csdnimg.cn/img_convert/50be1121a65d9daf0600cc5ded6f942e.png)

**优点：**

* 速度较快

**缺点：**

* 容易产生内存碎片

**算法二：标记整理算法**

**原理：**

* 第一步用可达性标记算法标记可回收和不可回收对象。第二步，可用对象向前移动，不可用的对象删除达到整理效果。

![image-20230322144403896](https://img-
blog.csdnimg.cn/img_convert/f9bf6e8bd3e6dac65d16c95ec0295c45.png)

**好处：**

* 没有内存碎片

**坏处：**

* 速度慢

**算法三：复制算法**

**原理：**

* 第一步用可达性标记算法标记可回收和不可回收对象。From中的可用对象复制到TO内存块中，最后交换from和to的位置

![image-20230322144856800](https://img-
blog.csdnimg.cn/img_convert/62763aa21cbd285c4c361b814bbfba7b.png)

**好处：**

* 不会有内存碎片

**坏处：**

* 需要占用双倍内存空间

### 2.3 分代垃圾回收

![image-20230322145333822](https://img-
blog.csdnimg.cn/img_convert/7ed761835da4c93315fe71256a7beedc.png)

* 对象首先分配在伊甸园区域

* 新生代空间不足时，触发 minor gc，伊甸园和 from 存活的对象使用 copy 复制到 to 中，存活的对象年龄加 1并且交换 from to

* minor gc 会引发 stop the world，暂停其它用户的线程，等垃圾回收结束，用户线程才恢复运行当对象寿命超过阈值时，会晋升至老年代，最大寿命是15（4bit）

* 当老年代空间不足，会先尝试触发 minor gc，如果之后空间仍不足，那么触发 full gc，STW的时间更长

**相关参数**

 堆初始大小            | -Xms                                                        
------------------|-------------------------------------------------------------  
 堆最大大小            | -Xmx 或 -XX:MaxHeapSize=size                                 
 新生代大小            | -Xmn 或 (-XX:NewSize=size + -XX:MaxNewSize=size )            
 幸存区比例（动态）        | -XX:InitialSurvivorRatio=ratio 和 -XX:+UseAdaptiveSizePolicy 
 幸存区比例            | -XX:SurvivorRatio=ratio                                     
 晋升阈值             | -XX:MaxTenuringThreshold=threshold                          
 晋升详情             | -XX:+PrintTenuringDistribution                              
 GC详情             | -XX:+PrintGCDetails -verbose:gc                             
 FullGC 前 MinorGC | -XX:+ScavengeBeforeFullGC                                   

### 2.4 垃圾回收器

1. **串行**

> -XX:+UseSerialGC = Serial + SerialOld

![image-20230322152839472](https://img-
blog.csdnimg.cn/img_convert/38fcc3861923ac37186660e2ac689240.png)

    * 单线程
    * 堆内存较小，适合个人电脑

2. **吞吐量优先**

> -XX:+UseParallelGC ~ -XX:+UseParallelOldGC
>
> -XX:GCTimeRatio=ratio
>
> -XX:MaxGCPauseMillis=ms
>
> -XX:ParallelGCThreads=n

![image-20230322152942559](https://img-
blog.csdnimg.cn/img_convert/a24f319e99a0f3e9c455ccbe2b6ce32e.png)

    * 多线程
    * 堆内存较大，多核 cpu
    * 让单位时间内，STW 的时间最短 0.2 0.2 = 0.4，垃圾回收时间占比最低，这样就称吞吐量高

3. **响应时间优先**

> -XX:+UseConcMarkSweepGC ~ -XX:+UseParNewGC ~ SerialOld
>
> -XX:ParallelGCThreads=n ~ -XX:ConcGCThreads=threads
>
> -XX:CMSInitiatingOccupancyFraction=percent
>
> -XX:+CMSScavengeBeforeRemark

![image-20230322153241451](https://img-
blog.csdnimg.cn/img_convert/2ba15359b4a40c6581d4ec7c4108e911.png)

    * 多线程

    * 堆内存较大，多核 cpu

    * 尽可能让单次 STW 的时间最短 0.1 0.1 0.1 0.1 0.1 = 0.5

4. **G1**

**定义：Garbage First**

    * 2004 论文发布

    * 2009 JDK 6u14 体验

    * 2012 JDK 7u4 官方支持

    * 2017 JDK 9 默认

**适用场景**

    * 同时注重吞吐量（Throughput）和低延迟（Low latency），默认的暂停目标是 200 ms

    * 超大堆内存，会将堆划分为多个大小相等的 Region

    * 整体上是 标记+整理 算法，两个区域之间是 复制 算法

**相关 JVM 参数**

-XX:+UseG1GC

-XX:G1HeapRegionSize=size

-XX:MaxGCPauseMillis=time

**G1** **垃圾回收阶段**

![image-20230322155624122](https://img-
blog.csdnimg.cn/img_convert/86f96a1c7478c642dfd66de3c8145d11.png)

    1. **Young Collection**

      * 会 STW
      * ![image-20230322155748974](https://img-blog.csdnimg.cn/img_convert/c942dd94dd4197efd9bdb74722e3ef30.png)
      * ![image-20230322155855560](https://img-blog.csdnimg.cn/img_convert/5de594aec34bcb743773842cf0d3924c.png)
      * ![image-20230322155911310](https://img-blog.csdnimg.cn/img_convert/17b8009756f589f90bebb4adf5ef54e7.png)
    2. **Young Collection + CM**

      * 在 Young GC 时会进行 GC Root 的初始标记

      * 老年代占用堆空间比例达到阈值时，进行并发标记（不会 STW），由下面的 JVM 参数决定

![image-20230322160103156](https://img-
blog.csdnimg.cn/img_convert/a4ebf7a0ba99ca370aeafef039c04656.png)

      * ![image-20230322160122063](https://img-blog.csdnimg.cn/img_convert/dd77355fbedae4acd2f839e000fe19bd.png)

    3. **Mixed Collection**

      * 会对 E、S、O 进行全面垃圾回收

        * 最终标记（Remark）会 STW
        * 拷贝存活（Evacuation）会 STW
      * -XX:MaxGCPauseMillis=ms

      * ![image-20230322160312398](https://img-blog.csdnimg.cn/img_convert/e2d12cfacf395ceb7ef5ee0d073811d2.png)

**Full GC**

* SerialGC

    * 新生代内存不足发生的垃圾收集 - minor gc
    * 老年代内存不足发生的垃圾收集 - full gc
* ParallelGC

    * 新生代内存不足发生的垃圾收集 - minor gc

    * 老年代内存不足发生的垃圾收集 - full gc

* CMS

    * 新生代内存不足发生的垃圾收集 - minor gc

    * 老年代内存不足

* G1

    * 新生代内存不足发生的垃圾收集 - minor gc

    * 老年代内存不足

**Young Collection** **跨代引用**

* 新生代回收的跨代引用（老年代引用新生代）问题

![image-20230322161255491](https://img-
blog.csdnimg.cn/img_convert/bfa059eb1393669a25058f80354d566f.png)

* 卡表与 Remembered Set

* 在引用变更时通过 post-write barrier + dirty card queue

* concurrent refinement threads 更新 Remembered Set

![image-20230322161347989](https://img-
blog.csdnimg.cn/img_convert/3f46d124b138b1c65d1c095d87f732e1.png)

**Remark**

* pre-write barrier + satb_mark_queue

![image-20230322161535125](https://img-
blog.csdnimg.cn/img_convert/befa27e99bfcd8a3fa00f673cac9a159.png)

**JDK 8u20** **字符串去重**

* 优点：节省大量内存

* 缺点：略微多占用了 cpu 时间，新生代回收时间略微增加

* -XX:+UseStringDeduplication

*     String s1 = new String("hello"); // char[]{'h','e','l','l','o'}
  String s2 = new String("hello"); // char[]{'h','e','l','l','o'}


* 将所有新分配的字符串放入一个队列

* 当新生代回收时，G1并发检查是否有字符串重复

* 如果它们值一样，让它们引用同一个 char[]

* 注意，与 String.intern() 不一样

    * String.intern() 关注的是字符串对象

    * 而字符串去重关注的是 char[]

    * 在 JVM 内部，使用了不同的字符串表

**JDK 8u40** **并发标记类卸载**

所有对象都经过并发标记后，就能知道哪些类不再被使用，当一个类加载器的所有类都不再使用，则卸载它所加载的所有类
-XX:+ClassUnloadingWithConcurrentMark 默认启用

**JDK 8u60** **回收巨型对象**

* 一个对象大于 region 的一半时，称之为巨型对象

* G1 不会对巨型对象进行拷贝

* 回收时被优先考虑

* G1 会跟踪老年代所有 incoming 引用，这样老年代 incoming 引用为0 的巨型对象就可以在新生代垃圾回收时处理掉

**JDK 9** **并发标记起始时间的调整**

* 并发标记必须在堆空间占满前完成，否则退化为 FullGC

* JDK 9 之前需要使用 -XX:InitiatingHeapOccupancyPercent

* JDK 9 可以动态调整

    * -XX:InitiatingHeapOccupancyPercent 用来设置初始值
    * 进行数据采样并动态调整
    * 总会添加一个安全的空档空间

**JDK 9** **更高效的回收**

* 250+增强
* 180+bug修复
* https://docs.oracle.com/en/java/javase/12/gctuning

### 2.5 垃圾回收调优

**预备知识**

* 掌握 GC 相关的 VM 参数，会基本的空间调整
* 掌握相关工具
* 明白一点：调优跟应用、环境有关，没有放之四海而皆准的法则

**调优领域**

* 内存
* 锁竞争
* cpu 占用
* io

**确定目标**

* 【低延迟】还是【高吞吐量】，选择合适的回收器
* CMS，G1，ZGC
* ParallelGC
* Zing

**最快的** **GC 是不发生 GC**

* 查看 FullGC 前后的内存占用，考虑下面几个问题

    * 数据是不是太多？

        * resultSet = statement.executeQuery(“select * from 大表 limit n”)
    * 数据表示是否太臃肿？

        * 对象图
        * 对象大小 16 Integer 24 int 4
    * 是否存在内存泄漏？

        * static Map map
        * 软
        * 弱
        * 第三方缓存实现

**新生代调优**

_新生代的特点_

* 所有的 new 操作的内存分配非常廉价
    * TLAB thread-local allocation buffer
* 死亡对象的回收代价是零
* 大部分对象用过即死
* Minor GC 的时间远远低于 Full GC

_越大越好吗？_

    -Xmn 
    Sets the initial and maximum size (in bytes) of the heap for the young generation (nursery).GC is performed in this region more often than in other regions. If the size for the young generation is too small, then a lot of minor garbage collections are performed. If the size is too large, then only full garbage collections are performed, which can take a long time to complete.Oracle recommends that you keep the size for the young generation greater than 25% and less than 50% of the overall heap size.

* 新生代能容纳所有【并发量 * (请求-响应)】的数据

* 幸存区大到能保留【当前活跃对象+需要晋升对象】

* 晋升阈值配置得当，让长时间存活对象尽快晋升

-XX:MaxTenuringThreshold=threshold

-XX:+PrintTenuringDistribution

    Desired survivor size 48286924 bytes, new threshold 10 (max 10)
    - age 1: 28992024 bytes, 28992024 total
    - age 2: 1366864 bytes, 30358888 total
    - age 3: 1425912 bytes, 31784800 total
    ...

**老年代调优**

以 CMS 为例

* CMS 的老年代内存越大越好

* 先尝试不做调优，如果没有 Full GC 那么已经…，否则先尝试调优新生代

* 观察发生 Full GC 时老年代内存占用，将老年代内存预设调大 1/4 ~ 1/3

-XX:CMSInitiatingOccupancyFraction=percent

**案例**

* 案例1 Full GC 和 Minor GC频繁

* 案例2 请求高峰期发生 Full GC，单次暂停时间特别长 （CMS）

* 案例3 老年代充裕情况下，发生 Full GC （CMS jdk1.7）

## 3.类加载与字节码技术

![image-20230322174528353](https://img-
blog.csdnimg.cn/img_convert/3204b65a29e06f467600f7f5c5bca64c.png)

### 3.1 类文件结构

**一个简单的 HelloWorld.java**

    package org.example;
    
    /**
     * HelloWorld 示例
     */
    public class HelloWorld {
        public static void main(String[] args) {
            System.out.println("hello world");
        }
    }

**编译为 HelloWorld.class 在Idea中打开后是这个样子的：**

![image-20230322221345650](https://img-
blog.csdnimg.cn/img_convert/970493ceb5319f904f32b34e76accd27.png)

**HelloWorld.class 文件转二进制文件：**

![image-20230322182734241](https://img-
blog.csdnimg.cn/img_convert/57856278e6a62ad23324636c9790cd8e.png)

**根据 JVM 规范，类文件结构如下**

    ClassFile {
    	u4			magic; 									//魔数
    	u2			minor_version; 			 				 //jdk的副版本号
    	u2 			major_version：		    				//jdk的主版本号
    	u2 			constant_pool_count：					//常量池数量
    	cp_info 	constant_pool[constant_pool_count-1]：	  //常量池具体信息
    	u2   		access_flags：						    //访问权限标识
    	u2      	this_class：								//类名
    	u2 			super_class：                              //父类名
    	u2       	interfaces_count：                         //接口数量
    	u2       	interfaces[interfaces_count]：			  //接口类信息
    	u2 			fields_count：      						//字段数量
    	field_info 	fields[fields_count]：				     //字段信息
    	u2 			methods_count：							//方法数量
    	method_info  methods[methods_count]：				  //方法具体信息
    	u2 			attributes_count：						//属性数量
    	attribute_info attributes[attributes_count]：           //属性具体信息
    }

1. **魔数**

    * 0~3 字节，表示它是否是【class】类型的文件

![image-20230322182955796](https://img-
blog.csdnimg.cn/img_convert/08e2c11299067425b9f9f69c3d8e046d.png)

2. **版本**

    * 4~7 字节，表示类的版本 00 34（52） 表示是 Java 8

![image-20230322183208061](https://img-
blog.csdnimg.cn/img_convert/48034789e3893996b3f5480cdeb6b972.png)

3. **常量池**

 Constant Type               | Value 
-----------------------------|-------  
 CONSTANT_Class              | 7     
 CONSTANT_Fieldref           | 9     
 CONSTANT_Methodref          | 10    
 CONSTANT_InterfaceMethodref | 11    
 CONSTANT_String             | 8     
 CONSTANT_Integer            | 3     
 CONSTANT_Float              | 4     
 CONSTANT_Long               | 5     
 CONSTANT_Double             | 6     
 CONSTANT_NameAndType        | 12    
 CONSTANT_Utf8               | 1     
 CONSTANT_MethodHandle       | 15    
 CONSTANT_MethodType         | 16    
 CONSTANT_InvokeDynamic      | 18    

    * 8~9 字节，表示常量池长度，00 22（34） 表示常量池有 #1~#33项，注意 #0 项不计入，也没有值

![image-20230322184540536](https://img-
blog.csdnimg.cn/img_convert/8f2d194fe338f9bfc1f8133dccbce57b.png)

    * 第#1项 0a 表示常量池中的类型，因为0a(10) 对应上面表格中的CONSTANT_Methodref 类型，所以它表示 Method 信息，00 06 和 00 14（20） 表示它引用了常量池中 #6 和 #20 项来获得这个方法的【所属类】和【方法名】

![image-20230322185150186](https://img-
blog.csdnimg.cn/img_convert/8caecd6461b60f5920b86fab13757a72.png)

    * 第#2项 09 表示一个 Field 信息，00 15（21）和 00 16（22） 表示它引用了常量池中 #21 和 # 22 项来获得这个成员变量的【所属类型】和【成员变量名】

![image-20230322190403165](https://img-
blog.csdnimg.cn/img_convert/d44c746c2f2cdd278b70994889d8a84b.png)

    * 第#3项 08 表示一个字符串常量名称，00 17（23）表示它引用了常量池中 #23 项

![image-20230322190647360](https://img-
blog.csdnimg.cn/img_convert/3c8c6501275ca1295fb45231effeec0a.png)

    * 第#4项 0a 表示一个 Method 信息，00 18（24） 和 00 19（25）表示它引用了常量池中 #24 和 #25项来获得这个方法的【所属类】和【方法名】

![image-20230322190807282](https://img-
blog.csdnimg.cn/img_convert/dc3f61673298928d5fff204a47d51930.png)

    * 第#5项 07 表示一个 Class 信息，00 1a（26） 表示它引用了常量池中 #26 项

![image-20230322191054297](https://img-
blog.csdnimg.cn/img_convert/9edcc0b98c8275c199de10d8070bad95.png)

    * 第#6项 07 表示一个 Class 信息，00 1b（27） 表示它引用了常量池中 #27 项

![image-20230322191226622](https://img-
blog.csdnimg.cn/img_convert/7452e1fe9dc568673439e7d65206f7c4.png)

    * 第#7项 01 表示一个 utf8 串，00 06 表示长度，3c 69 6e 69 74 3e 是【 】

![image-20230322191353244](https://img-
blog.csdnimg.cn/img_convert/439e1953d21c9686ad6804d835d148d0.png)

    * 第#8项 01 表示一个 utf8 串，00 03 表示长度，28 29 56 是【()V】其实就是表示无参、无返回值

![image-20230322191903217](https://img-
blog.csdnimg.cn/img_convert/5080cbaaf19a1b0a23660d5b8b31ef80.png)

    * 第#9项 01 表示一个 utf8 串，00 04 表示长度，43 6f 64 65 是【Code】

![image-20230322191958863](https://img-
blog.csdnimg.cn/img_convert/ecc137c0370f4371a9c4812c0f0a162d.png)

    * 第#10项 01 表示一个 utf8 串，00 0f（15） 表示长度，4c 69 6e 65 4e 75 6d 62 65 72 54 61 62 6c 65是【LineNumberTable】

![image-20230322192123600](https://img-
blog.csdnimg.cn/img_convert/0d3697271a754c4a5531e64cf839d013.png)

    * 第#11项 01 表示一个 utf8 串，00 12（18） 表示长度，4c 6f 63 61 6c 56 61 72 69 61 62 6c 65 54 61 62 6c 65是【LocalVariableTable】

![image-20230322192333501](https://img-
blog.csdnimg.cn/img_convert/be5dd1082ec04ddd6e7f5d6f9eb4c404.png)

    * 第#12项 01 表示一个 utf8 串，00 04 表示长度，74 68 69 73 是【this】

![image-20230322192553931](https://img-
blog.csdnimg.cn/img_convert/cdbe9eb67dee3f841125ef6e5ac72722.png)

    * 第#13项 01 表示一个 utf8 串，00 18（24） 表示长度，是【Lorg/example/HeloWorld;】

![image-20230322192722291](https://img-
blog.csdnimg.cn/img_convert/aa234764aab4b97b023ca4cdc2da3898.png)

    * 第#14项 01 表示一个 utf8 串，00 04 表示长度，6D 61 69 6E是【main】

![image-20230322193142581](https://img-
blog.csdnimg.cn/img_convert/b0184ed970fc601e2a5b184fb842fa6b.png)

    * 第#15项 01 表示一个 utf8 串，00 16（22） 表示长度，是【([Ljava/lang/String;)V】其实就是参数为字符串数组，无返回值

![image-20230322193348562](https://img-
blog.csdnimg.cn/img_convert/1b8672d5820b02732f9344c4003fa45b.png)

    * 第#16项 01 表示一个 utf8 串，00 04 表示长度，是【args】

![image-20230322193635966](https://img-
blog.csdnimg.cn/img_convert/3562ee0c958b74bfbbe645dd98a69dd0.png)

    * 第#17项 01 表示一个 utf8 串，00 13（19） 表示长度，是【[Ljava/lang/String;】

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-
GHE5d5zF-1679980231499)(https://pic-1307534554.cos.ap-
chongqing.myqcloud.com/img/image-20230322193804022.png)]

    * 第#18项 01 表示一个 utf8 串，00 0A（10） 表示长度，是【SourceFile】

![image-20230322194200172](https://img-
blog.csdnimg.cn/img_convert/cc24653306aa17ab1e086750ec1c2120.png)

    * 第#19项 01 表示一个 utf8 串，00 0f（15） 表示长度，是【HelloWorld.java】

![image-20230322194729624](https://img-
blog.csdnimg.cn/img_convert/3b7bcd2ecaa9b3d4f56e11a8f6b63f24.png)

    * 第#20项0c 表示一个 【名+类型】，00 07 00 08 引用了常量池中 #7 #8 两项

![image-20230322194910846](https://img-
blog.csdnimg.cn/img_convert/d3d00b92c6d7feb225f00acb26f1e8e5.png)

    * 第#21项 07 表示一个 Class 信息，00 1c（28） 引用了常量池中 #28 项

![image-20230322195113486](https://img-
blog.csdnimg.cn/img_convert/f0f69d8ce21ceaf58b17d2d19b5e414d.png)

    * 第#22项 0c 表示一个【名+类型】00 1d（29） 00 1e （30）引用了常量池中 #29 #30两项

![image-20230322195215374](https://img-
blog.csdnimg.cn/img_convert/4c3b627c6c7f52d4af194a0eacb3479b.png)

    * 第#23项 01 表示一个 utf8 串，00 0b（11） 表示长度，是【hello world】

![image-20230322195444628](https://img-
blog.csdnimg.cn/img_convert/6cefcc7210a7ef6f4673bb060abb5092.png)

    * 第#24项 07 表示一个 Class 信息，00 1F（31） 引用了常量池中 #31 项

![image-20230322195606493](https://img-
blog.csdnimg.cn/img_convert/ad3c457d6ae627a821f3313c97c2d1cb.png)

    * 第#25项 0c 表示一个【名+类型】00 20（32） 00 21（33）引用了常量池中 #32 #33 两项

![image-20230322195830948](https://img-
blog.csdnimg.cn/img_convert/b122494716943385d04b25055f99e14f.png)

    * 第#26项 01 表示一个 utf8 串，00 16（22） 表示长度，是【org/example/HelloWorld】

![image-20230322200020321](https://img-
blog.csdnimg.cn/img_convert/bea8d25a83b099b576ace8d6c9d3cc01.png)

    * 第#27项 01 表示一个 utf8 串，00 10（16） 表示长度，是【java/lang/Object】

![image-20230322200337409](https://img-
blog.csdnimg.cn/img_convert/6af8326a62bc29a5ef7ff6fba1d5a0ca.png)

    * 第#28项 01 表示一个 utf8 串，00 10（16） 表示长度，是【java/lang/System】

![image-20230322200448928](https://img-
blog.csdnimg.cn/img_convert/a1cdd21f00831a0528579ace6b0e9889.png)

    * 第#29项 01 表示一个 utf8 串，00 03 表示长度，是【out】

![image-20230322200551345](https://img-
blog.csdnimg.cn/img_convert/bf1370c7f46c8bc23e9d2edcf8617034.png)

    * 第#30项 01 表示一个 utf8 串，00 15（21） 表示长度，是【Ljava/io/PrintStream;】

![image-20230322200648865](https://img-
blog.csdnimg.cn/img_convert/4b698def1a557f5f21e9b3f426ee5395.png)

    * 第#31项 01 表示一个 utf8 串，00 13（19） 表示长度，是【java/io/PrintStream】

![image-20230322200842783](https://img-
blog.csdnimg.cn/img_convert/9276e420fcc6f41346b070f9ca7a0fd1.png)

    * 第#32项 01 表示一个 utf8 串，00 07 表示长度，是【println】

![image-20230322201004865](https://img-
blog.csdnimg.cn/img_convert/3f48e516cee05a559de6b904e7512ce5.png)

    * 第#33项 01 表示一个 utf8 串，00 15（21） 表示长度，是【(Ljava/lang/String;)V】

![image-20230322201114055](https://img-
blog.csdnimg.cn/img_convert/30bbbfd48dad3132aaa55417bb45c107.png)

4. **访问标识与继承信息**

**访问标识符：**

00 21 表示等价于【0x0001 + 0x0020】表示该Class是一个公共的类【public class】

![image-20230322210828403](https://img-
blog.csdnimg.cn/img_convert/a99b288132b7c60c5e7f1a0b2a6509d5.png)

 **Flag Name** | **Value** | **Interpretation**                                 
---------------|-----------|----------------------------------------------------  
 ACC_PUBLIC    | 0x0001    | Declared public ; may be accessed from outside its 

package.  
ACC_FINAL| 0x0010| Declared final ; no subclasses allowed.  
ACC_SUPER| 0x0020| Treat superclass methods specially when invoked by the
_invokespecial_ instruction.  
ACC_INTERFACE| 0x0200| Is an interface, not a class.  
ACC_ABSTRACT| 0x0400| Declared abstract ; must not be instantiated.  
ACC_SYNTHETIC| 0x1000| Declared synthetic; not present in the source code.  
ACC_ANNOTATION| 0x2000| Declared as an annotation type.  
ACC_ENUM| 0x4000| Declared as an enum type.

**类名：**

00 05 表示根据常量池中 #5 找到本类全限定名

![image-20230322211439671](https://img-
blog.csdnimg.cn/img_convert/b83eedc319fe5fbb8d1b5deed8a49888.png)

**父类名：**

00 06 表示根据常量池中 #6 找到父类全限定名

![image-20230322211531895](https://img-
blog.csdnimg.cn/img_convert/19f53fe55ca0a48f9afc803483dd6bbf.png)

**接口数量：**

00 00 表示接口的数量，本类为 0

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-
qEDlWg4V-1679980231512)(https://pic-1307534554.cos.ap-
chongqing.myqcloud.com/img/image-20230322211704581.png)]

5. **Field 信息**

00 00 表示成员变量数量，本类为 0

![image-20230322212055037](https://img-
blog.csdnimg.cn/img_convert/61ceea6352f4b4d7fc0340c1ef7e6c95.png)

 **FieldType** | **Type** | **Interpretation**                                                   
---------------|----------|----------------------------------------------------------------------  
 B             | byte     | signed byte                                                          
 C             | char     | Unicode character code point in the Basic Multilingual Plane,encoded 

with UTF-16  
D| double| double-precision flfloating-point value  
F| float| single-precision flfloating-point value  
I| int| integer  
J| long| long integer  
L _ClassName_ ;| reference| an instance of class _ClassName_  
S| short| signed short  
Z| boolean| true or false  
[| reference| one array dimension

6. **Method** **信息**

**方法数量:**

00 02 表示方法数量，本类为 2

![image-20230322212741225](https://img-
blog.csdnimg.cn/img_convert/3187e9a62644a47afcde65883c54508d.png)

**方法信息：**

一个方法由 访问修饰符，名称，参数描述，方法属性数量，方法属性组成：

_**方法一：**_

    * 访问修饰符： 00 01 ，本类中是 【ACC_PUBLIC 】public

![image-20230322214356199](https://img-
blog.csdnimg.cn/img_convert/8e57d8a75ac543e1779bf2496d165c51.png)

    * 方法名称：00 07 【】 代表引用了常量池 #07 项作为方法名称

![image-20230322214706845](https://img-
blog.csdnimg.cn/img_convert/bb8f6f11a9336fd9df51219625d750e1.png)

    * 参数描述：00 08 【()V】代表引用了常量池 #08 项作为方法参数描述

![image-20230322214944575](https://img-
blog.csdnimg.cn/img_convert/a9e3213a5adeeaf64bd7d87b0ac6a77b.png)

    * 方法属性数量：00 01 代表方法属性数量，本方法是 1

![image-20230322215324390](https://img-
blog.csdnimg.cn/img_convert/bdddff603fe83f1f418fd688f8240f1c.png)

    * 代表方法属性：

      * 00 09 表示引用了常量池 #09 项，发现是【Code】属性

![image-20230322215439589](https://img-
blog.csdnimg.cn/img_convert/f486416d06dc08a34e73d79e3ec5558b.png)

      * 00 00 00 2f 表示此属性的长度是 47

![image-20230322215556222](https://img-
blog.csdnimg.cn/img_convert/d4d2939062c95949e40709d281f1a208.png)

      * 00 01 表示【操作数栈】最大深度 本方法是 1

![image-20230322215732004](https://img-
blog.csdnimg.cn/img_convert/c1282d9f152a715e39ecd3d55f842fba.png)

      * 00 01 表示【局部变量表】最大槽（slot）数 本方法是1

![image-20230322220215816](https://img-
blog.csdnimg.cn/img_convert/8fc267e6216d77a499343dbff8ddcaf6.png)

      * 00 00 00 05 表示字节码长度，本例是 5

![image-20230322220229046](https://img-
blog.csdnimg.cn/img_convert/2e24c73f764a241a255e22cd21dd2776.png)

      * 2A B7 00 01 B1 是字节码指令

![image-20230322220254073](https://img-
blog.csdnimg.cn/img_convert/ac5fabcccab476727c81ccdb53f1a574.png)

      * 00 00 00 02 表示方法细节属性数量，本例是 2

![image-20230322220750842](https://img-
blog.csdnimg.cn/img_convert/00c72946a09a77099d15a705d1a9c998.png)

      * 00 0A 表示引用了常量池 #10 项，发现是【LineNumberTable】属性

![image-20230322220824191](https://img-
blog.csdnimg.cn/img_convert/9ef2bb4e87afec52583b7dae23061931.png)

        * 00 00 00 06 表示此属性的总长度，本例是 6

![image-20230322220934196](https://img-
blog.csdnimg.cn/img_convert/881a400dad9398350302a0ca8fc35077.png)

        * 00 01 表示【LineNumberTable】长度

![image-20230322221004311](https://img-
blog.csdnimg.cn/img_convert/d3680cd30a53e13ad5e5a055923ba14b.png)

        * 00 00 表示【字节码】行号 00 09 表示【java 源码】行号

![image-20230322221029665](https://img-
blog.csdnimg.cn/img_convert/86039265d278109382b3c0d84a21b99b.png)

![image-20230322221038072](https://img-
blog.csdnimg.cn/img_convert/df263f5fecf9318b7c7e36813fb17c80.png)

      * 00 0b 表示引用了常量池 #11 项，发现是【LocalVariableTable】属性

![image-20230322221438278](https://img-
blog.csdnimg.cn/img_convert/739c90b6c6082989c2b2ea7384774653.png)

        * 00 00 00 0c 表示此属性的总长度，本例是 12

![image-20230322222009659](https://img-
blog.csdnimg.cn/img_convert/b463982adc160d126689ed5c137cc6a6.png)

        * 00 01 表示【LocalVariableTable】长度

![image-20230322222019094](https://img-
blog.csdnimg.cn/img_convert/eac61d8a5c40e27da26e61fae4232a50.png)

        * 00 00 表示局部变量生命周期开始，相对于字节码的偏移量

![image-20230322222027355](https://img-
blog.csdnimg.cn/img_convert/8e82e75edb631a1634502cb3fc83da83.png)

        * 00 05 表示局部变量覆盖的范围长度

![image-20230322222037416](https://img-
blog.csdnimg.cn/img_convert/7f44a7acae5bc8e4f78ea0743819cf9d.png)

        * 00 0c 表示局部变量名称，本例引用了常量池 #12 项，是【this】

![image-20230322222044734](https://img-
blog.csdnimg.cn/img_convert/d999dc442d8582339fedc77ec8192f6e.png)

        * 00 0d 表示局部变量的类型，本例引用了常量池 #13 项，是【Lorg/example/HeloWorld;】

![image-20230322222052895](https://img-
blog.csdnimg.cn/img_convert/270000413d054dfebf30a561eec349b1.png)

        * 00 00 表示局部变量占有的槽位（slot）编号，本例是 0

![image-20230322222104380](https://img-
blog.csdnimg.cn/img_convert/a590df5ddde303a94e21177fd53a31f7.png)

_**方法二：**_

    * 访问修饰符：00 09 ，本类中是【00 01 + 00 08】 public static
    * 名称：00 0E 代表引用了常量池 #14 项作为方法名称【main】
    * 参数描述：00 0F 代表引用了常量池 #15 项作为方法参数描述【([Ljava/lang/String;)V】
    * 方法属性数量: 00 01 代表方法属性数量，本方法是 1
    * 方法属性组成: 
      * 00 09 表示引用了常量池 #09 项，发现是【Code】属性
      * 00 00 00 37 表示此属性的长度是 55
      * 00 02 表示【操作数栈】最大深度
      * 00 01 表示【局部变量表】最大槽（slot）数
      * 00 00 00 09 表示字节码长度，本例是 9
      * b2 00 02 12 03 b6 00 04 b1 是字节码指令
      * 00 00 00 02 表示方法细节属性数量，本例是 2
      * 00 0a 表示引用了常量池 #10 项，发现是【LineNumberTable】属性 
        * 00 00 00 0a 表示此属性的总长度，本例是 10
        * 00 02 表示【LineNumberTable】长度
        * 00 00 表示【字节码】行号 00 0B 表示【java 源码】行号
        * 00 08 表示【字节码】行号 00 0C 表示【java 源码】行号
      * 00 0b 表示引用了常量池 #11 项，发现是【LocalVariableTable】属性 
        * 00 00 00 0c 表示此属性的总长度，本例是 12
        * 00 01 表示【LocalVariableTable】长度
        * 00 00 表示局部变量生命周期开始，相对于字节码的偏移量
        * 00 09 表示局部变量覆盖的范围长度
        * 00 10 表示局部变量名称，本例引用了常量池 #16 项，是【args】
        * 00 11 表示局部变量的类型，本例引用了常量池 #17 项，是【[Ljava/lang/String;】
        * 00 00 表示局部变量占有的槽位（slot）编号，本例是 0

7. **附加属性**

    * 00 01 表示附加属性数量

    * 00 12 表示引用了常量池 #18 项，即【SourceFile】

    * 00 00 00 02 表示此属性的长度

    * 00 13 表示引用了常量池 #19 项，即【HelloWorld.java】

![image-20230322224439774](https://img-
blog.csdnimg.cn/img_convert/42627177d59a6cc04a6e199e8184fe87.png)

### 3.2 字节码指令

#### **3.2.1 入门**

接着上一节，研究一下两组字节码指令，一个是

public org.example.HeloWorld();构造方法的字节码指令: 2A B7 00 01 B1

1. 2a => _aload_0_ 加载 slot 0 的局部变量，即 this，做为下面的 _invokespecial_ 构造方法调用的参数
2. b7 => _invokespecial_ 预备调用构造方法，哪个方法呢？
3. 00 01 引用常量池中 #1 项，即【 Method java/lang/Object.“”😦)V 】
4. b1 表示返回

另一个是 public static void main(java.lang.String[]); 主方法的字节码指令:

    b2 00 02 12 03 b6 00 04 b1

1. b2 => _getstatic_ 用来加载静态变量，哪个静态变量呢？
2. 00 02 引用常量池中 #2 项，即【Field java/lang/System.out:Ljava/io/PrintStream;】
3. 12 => _ldc_ 加载参数，哪个参数呢？
4. 03 引用常量池中 #3 项，即 【String hello world】
5. b6 => _invokevirtual_ 预备调用成员方法，哪个方法呢？
6. 00 04 引用常量池中 #4 项，即【Method java/io/PrintStream.println:(Ljava/lang/String;)V】
7. b1 表示返回

参考指令：https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-6.html

#### **3.2.2 javap 工具**

* 自己分析类文件结构太麻烦了，Oracle 提供了 javap 工具来反编译 class 文件

      PS F:\code\01-jdk8\target\classes\org\example> javap -v HelloWorld.class
  Classfile /F:/code/01-jdk8/target/classes/org/example/HelloWorld.class
  Last modified 2023-3-22; size 557 bytes
  MD5 checksum 57c168fae73b3812b396f260776e1773
  Compiled from "HelloWorld.java"
  public class org.example.HelloWorld
  minor version: 0
  major version: 52
  flags: ACC_PUBLIC, ACC_SUPER
  Constant pool:
  #1 = Methodref #6.#20 // java/lang/Object."":()V
  #2 = Fieldref #21.#22 // java/lang/System.out:Ljava/io/PrintStream;
  #3 = String #23 // hello world
  #4 = Methodref #24.#25 // java/io/PrintStream.println:(Ljava/lang/String;)V
  #5 = Class #26 // org/example/HelloWorld
  #6 = Class #27 // java/lang/Object
  #7 = Utf8               
  #8 = Utf8               ()V
  #9 = Utf8 Code
  #10 = Utf8 LineNumberTable
  #11 = Utf8 Lo alVariableTable
  #12 = Utf8 this
  #13 = Utf8 Lorg/example/HelloWorld;
  #14 = Utf8 main
  #15 = Utf8               ([Ljava/lang/String;)V
  #16 = Utf8 args
  #17 = Utf8               [Ljava/lang/String;
  #18 = Utf8 SourceFile
  #19 = Utf8 HelloWorld.java
  #20 = NameAndType #7:#8 // "":()V
  #21 = Class #28 // java/lang/System
  #22 = NameAndType #29:#30 // out:Ljava/io/PrintStream;
  #23 = Utf8 hello world
  #24 = Class #31 // java/io/PrintStream
  #25 = NameAndType #32:#33 // println:(Ljava/lang/String;)V
  #26 = Utf8 org/example/HelloWorld
  #27 = Utf8 java/lang/Object
  #28 = Utf8 java/lang/System
  #29 = Utf8 out
  #30 = Utf8 Ljava/io/PrintStream;
  #31 = Utf8 java/io/PrintStream
  #32 = Utf8 println
  #33 = Utf8               (Ljava/lang/String;)V
  {
  public org.example.HelloWorld();
  descriptor: ()V
  flags: ACC_PUBLIC
  Code:
  stack=1, locals=1, args_size=1
  0: aload_0
  1: invokespecial #1 // Method java/lang/Object."":()V
  4: return
  LineNumberTable:
  line 9: 0
  Error: unknown attribute
  Lo alVariableTable: length = 0xC
  00 01 00 00 00 05 00 0C 00 0D 00 00

  public static void main(java.lang.String[]);
  descriptor: ([Ljava/lang/String;)V
  flags: ACC_PUBLIC, ACC_STATIC
  Code:
  stack=2, locals=1, args_size=1
  0: getstatic #2 // Field java/lang/System.out:Ljava/io/PrintStream;
  3: ldc #3 // String hello world
  5: invokevirtual #4 // Method java/io/PrintStream.println:(Ljava/lang/String;)V
  8: return
  LineNumberTable:
  line 11: 0
  line 12: 8
  Error: unknown attribute
  Lo alVariableTable: length = 0xC
  00 01 00 00 00 09 00 10 00 11 00 00
  }
  SourceFile: "HelloWorld.java"

#### **3.2.3 图解方法执行流程**

1. **原始** **java** **代码**

   package org.example;
   /**
   *演示 字节码指令 和 操作数栈、常量池的关系
   */
   public class Demo3_2_3 {
   public static void main(String[] args) {
   int a = 10;
   int b = Short.MAX_VALUE + 1;
   int c = a + b;
   System.out.println(c);
   }
   }


2. **编译后的字节码文件**

   F:\code\01-jdk8\target\classes\org\example>javap -v Demo3_2_3.class
   Classfile /F:/code/01-jdk8/target/classes/org/example/Demo3_2_3.class
   Last modified 2023-3-22; size 613 bytes
   MD5 checksum fe8db4afd14d848f69b4b945e4e93751
   Compiled from "Demo3_2_3.java"
   public class org.example.Demo3_2_3
   minor version: 0
   major version: 52
   flags: ACC_PUBLIC, ACC_SUPER
   Constant pool:
   #1 = Methodref #7.#25 // java/lang/Object."":()V
   #2 = Class #26 // java/lang/Short
   #3 = Integer 32768
   #4 = Fieldref #27.#28 // java/lang/System.out:Ljava/io/PrintStream;
   #5 = Methodref #29.#30 // java/io/PrintStream.println:(I)V
   #6 = Class #31 // org/example/Demo3_2_3
   #7 = Class #32 // java/lang/Object
   #8 = Utf8               
   #9 = Utf8               ()V
   #10 = Utf8 Code
   #11 = Utf8 LineNumberTable
   #12 = Utf8 LocalVariableTable
   #13 = Utf8 this
   #14 = Utf8 Lorg/example/Demo3_2_3;
   #15 = Utf8 main
   #16 = Utf8               ([Ljava/lang/String;)V
   #17 = Utf8 args
   #18 = Utf8               [Ljava/lang/String;
   #19 = Utf8 a
   #20 = Utf8 I
   #21 = Utf8 b
   #22 = Utf8 c
   #23 = Utf8 SourceFile
   #24 = Utf8 Demo3_2_3.java
   #25 = NameAndType #8:#9 // "":()V
   #26 = Utf8 java/lang/Short
   #27 = Class #33 // java/lang/System
   #28 = NameAndType #34:#35 // out:Ljava/io/PrintStream;
   #29 = Class #36 // java/io/PrintStream
   #30 = NameAndType #37:#38 // println:(I)V
   #31 = Utf8 org/example/Demo3_2_3
   #32 = Utf8 java/lang/Object
   #33 = Utf8 java/lang/System
   #34 = Utf8 out
   #35 = Utf8 Ljava/io/PrintStream;
   #36 = Utf8 java/io/PrintStream
   #37 = Utf8 println
   #38 = Utf8               (I)V
   {
   public org.example.Demo3_2_3();
   descriptor: ()V
   flags: ACC_PUBLIC
   Code:
   stack=1, locals=1, args_size=1
   0: aload_0
   1: invokespecial #1 // Method java/lang/Object."":()V
   4: return
   LineNumberTable:
   line 5: 0
   LocalVariableTable:
   Start Length Slot Name Signature
   0 5 0 this Lorg/example/Demo3_2_3;

   public static void main(java.lang.String[]);
   descriptor: ([Ljava/lang/String;)V
   flags: ACC_PUBLIC, ACC_STATIC
   Code:
   stack=2, locals=4, args_size=1
   0: bipush 10
   2: istore_1
   3: ldc #3 // int 32768
   5: istore_2
   6: iload_1
   7: iload_2
   8: iadd
   9: istore_3
   10: getstatic #4 // Field java/lang/System.out:Ljava/io/PrintStream;
   13: iload_3
   14: invokevirtual #5 // Method java/io/PrintStream.println:(I)V
   17: return
   LineNumberTable:
   line 7: 0
   line 8: 3
   line 9: 6
   line 10: 10
   line 11: 17
   LocalVariableTable:
   Start Length Slot Name Signature
   0 18 0 args   [Ljava/lang/String;
   3 15 1 a I
   6 12 2 b I
   10 8 3 c I
   }
   SourceFile: "Demo3_2_3.java"


3. **常量池载入运行时常量池**

![image-20230322232553830](https://img-
blog.csdnimg.cn/img_convert/b0e1ceb9883b1ee3c97ce1f5048ec7d6.png)

4. **方法字节码载入方法区**

![image-20230322232951272](https://img-
blog.csdnimg.cn/img_convert/a9199ff491f746c49a92badb7d9f0d54.png)

5. **main** **线程开始运行，分配栈帧内存**

（stack=2，locals=4）

![image-20230322233027223](https://img-
blog.csdnimg.cn/img_convert/06f208f29f916f43e2b6ae2c3fc1aa19.png)

6. **执行引擎开始执行字节码**

**bipush 10**

    * 将一个 byte 压入操作数栈（其长度会补齐 4 个字节），类似的指令还有
    * sipush 将一个 short 压入操作数栈（其长度会补齐 4 个字节）
    * ldc 将一个 int 压入操作数栈
    * ldc2_w 将一个 long 压入操作数栈（分两次压入，因为 long 是 8 个字节）
    * 这里小的数字都是和字节码指令存在一起，超过 short 范围的数字存入了常量池

![image-20230322233213607](https://img-
blog.csdnimg.cn/img_convert/dfdb08553a1f15d612e9e527d6ffd74b.png)

**istore_1**

    * 将操作数栈顶数据弹出，存入局部变量表的 slot 1

![image-20230322233352360](https://img-
blog.csdnimg.cn/img_convert/abf9045646aabd36098f1ee0a872d6d8.png)

**ldc #3**

    * 从常量池加载 #3 数据到操作数栈

    * **注意** Short.MAX_VALUE 是 32767，所以 32768 = Short.MAX_VALUE + 1 实际是在编译期间计算好的

![image-20230322233708705](https://img-
blog.csdnimg.cn/img_convert/94a569353d524d971d5331bcde669c24.png)

**istore_2**

    * 将操作数栈顶数据弹出，存入局部变量表的 slot 2

![image-20230322233750833](https://img-
blog.csdnimg.cn/img_convert/ccd3876aa7db438bf82ee58046d23ab2.png)

**iload_1**

![image-20230322233833279](https://img-
blog.csdnimg.cn/img_convert/881f0f2f21496af7c23a2d51d7f12e2f.png)

**iload_2**

![image-20230322233853006](https://img-
blog.csdnimg.cn/img_convert/8db84985a16583f6e189c564c127ff46.png)

**iadd**

![image-20230322233918425](https://img-
blog.csdnimg.cn/img_convert/014e006e4bac8fec6440dd312c7b6cf3.png)

**istore_3**

    * 将操作数栈顶数据弹出，存入局部变量表的 slot 3

![image-20230322233934574](https://img-
blog.csdnimg.cn/img_convert/d3feb38f3f9bd7ad76cbccb6e4374e77.png)

![image-20230322234001854](https://img-
blog.csdnimg.cn/img_convert/263e69ced05dafeef0ccfe26801dff7e.png)

**getstatic #4**

![image-20230322234023327](https://img-
blog.csdnimg.cn/img_convert/1826fea9f97cb7a3ae292d42906d581b.png)

![image-20230322234102629](https://img-
blog.csdnimg.cn/img_convert/45639121e735bac0079074d4035ad3ad.png)

**iload_3**

![image-20230322234135800](https://img-
blog.csdnimg.cn/img_convert/d1b6627c0c789f7ddbedbe23824e134f.png)

**invokevirtual #5**

    * 找到常量池 #5 项
    * 定位到方法区 java/io/PrintStream.println:(I)V 方法
    * 生成新的栈帧（分配 locals、stack等）
    * 传递参数，执行新栈帧中的字节码

![image-20230322234246703](https://img-
blog.csdnimg.cn/img_convert/d12b1c07f2c75833b0d5be37ca562f3f.png)

    * 执行完毕，弹出栈帧

    * 清除 main 操作数栈内容

![image-20230322234344280](https://img-
blog.csdnimg.cn/img_convert/2507728c34757973c370ea4f3c2a4253.png)

**return**

    * 完成 main 方法调用，弹出 main 栈帧
    * 程序结束

#### 3.2.4 **分析** **i++**

> 目的：从字节码角度分析 a++ 相关题目

1. 源码：

   package org.example;
   /**
    * 从字节码角度分析 a++
      */
      public class Demo3_2_4 {
      public static void main(String[] args) {
      int a = 10;
      int b = a++ + ++a + a--;
      System.out.println(a);
      System.out.println(b);
      }
      }


2. 字节码：

   public static void main(java.lang.String[]);
   descriptor: ([Ljava/lang/String;)V
   flags: ACC_PUBLIC, ACC_STATIC
   Code:
   stack=2, locals=3, args_size=1
   0: bipush 10
   2: istore_1
   3: iload_1
   4: iinc 1, 1
   7: iinc 1, 1
   10: iload_1
   11: iadd
   12: iload_1
   13: iinc 1, -1
   16: iadd
   17: istore_2
   18: getstatic #2 // Field java/lang/System.out:Ljava/io/PrintStream;
   21: iload_1
   22: invokevirtual #3 // Method java/io/PrintStream.println:(I)V
   25: getstatic #2 // Field java/lang/System.out:Ljava/io/PrintStream;
   28: iload_2
   29: invokevirtual #3 // Method java/io/PrintStream.println:(I)V
   32: return
   LineNumberTable:
   line 7: 0
   line 8: 3
   line 9: 18
   line 10: 25
   line 11: 32
   LocalVariableTable:
   Start Length Slot Name Signature
   0 33 0 args   [Ljava/lang/String;
   3 30 1 a I
   18 15 2 b I


3. 分析：

    * 注意 iinc 指令是直接在局部变量 slot 上进行运算
    * a++ 和 ++a 的区别是先执行 iload 还是 先执行 iinc

![image-20230322235442105](https://img-
blog.csdnimg.cn/img_convert/a709d73a73c6aaeea1d3655493c45f57.png)

![image-20230322235621083](https://img-
blog.csdnimg.cn/img_convert/9c488d8952c96d749d7583ed42c5090e.png)

![image-20230322235728800](https://img-
blog.csdnimg.cn/img_convert/056295a7b0b3e0c33be70fe8c1d45c86.png)

![image-20230322235809539](https://img-
blog.csdnimg.cn/img_convert/628110f2561b61cd4c32464831e5fce1.png)

![image-20230322235852668](https://img-
blog.csdnimg.cn/img_convert/45cb1ca595d2cfa303d9d0ae7d58cc0d.png)

![image-20230323000030541](https://img-
blog.csdnimg.cn/img_convert/888c28c995e26c86b7f56d112fbfa718.png)

![image-20230323000042668](https://img-
blog.csdnimg.cn/img_convert/3ddd80cbb9d897b09aaff0a6280f43a0.png)

![image-20230323000103174](https://img-
blog.csdnimg.cn/img_convert/9390ff44eef1225b22c4a25f5a6515d8.png)

![image-20230323000114597](https://img-
blog.csdnimg.cn/img_convert/4544290bfc72088353d10bcec92e8915.png)

![image-20230323000130301](https://img-
blog.csdnimg.cn/img_convert/5fe38b3f8cc6214eb8039a7821f682a4.png)

![image-20230323000141764](https://img-
blog.csdnimg.cn/img_convert/ae412a7a7679386969d5b08ddf9d0f37.png)

#### 3.2.5 **条件判断指令**

 指令   | 助记符       | 含义           
------|-----------|--------------  
 0x99 | ifeq      | 判断是否 == 0    
 0x9a | ifne      | 判断是否 != 0    
 0x9b | iflt      | 判断是否 < 0     
 0x9c | ifge      | 判断是否 >= 0    
 0x9d | ifgt      | 判断是否 > 0     
 0x9e | ifle      | 判断是否 <= 0    
 0x9f | if_icmpeq | 两个int是否 ==   
 0xa0 | if_icmpne | 两个int是否 !=   
 0xa1 | if_icmplt | 两个int是否 <    
 0xa2 | if_icmpge | 两个int是否 >=   
 0xa3 | if_icmpgt | 两个int是否 >    
 0xa4 | if_icmple | 两个int是否 <=   
 0xa5 | if_acmpeq | 两个引用是否 ==    
 0xa6 | if_acmpne | 两个引用是否 !=    
 0xc6 | ifnull    | 判断是否 == null 
 0xc7 | ifnonnull | 判断是否 != null 

> 几点说明：
>
>   * byte，short，char 都会按 int 比较，因为操作数栈都是 4 字节
>   * goto 用来进行跳转到指定行号的字节码
>

**源码：**

    package org.example;
    /**
     * 条件判断指令:练习
     */
    public class Demo3_2_5 {
        public static void main(String[] args) {
            int a = 0;
            if (a == 0) {
                a = 10;
            } else {
                a = 20;
            }
        }
    }

**字节码并分析：**

    		0: iconst_0   //把0入操作符栈中
             1: istore_1  //栈顶的数据赋值给slot 1中的a (a=0)
             2: iload_1		//slot_1中的数据入栈
             3: ifne          12 //判断栈顶的数据是否等于0，如果等于零往下执行，反之跳转到								指令位置12（ 12: bipush        20）
             6: bipush        10 //10入栈
             8: istore_1		//栈顶的数据赋值给slot_1中的a (a=10)
             9: goto          15 //直接跳转到指令位置 15（15: return）
            12: bipush        20 //20入栈
            14: istore_1		//栈顶的数据赋值给slot_1中的a (a=20)
            15: return			//退出程序

> 思考：以上比较指令中没有 long，flfloat，double 的比较，那么它们要比较怎么办？参考
> https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-6.html#jvms-6.5.lcmp

#### 3.2.6 **循环控制指令**

**源码：**

    public class Demo_01 {
        public static void main(String[] args) {
            int a = 0;
            while (a < 10) {
                a++;
            }
        }
    }

**字节码并分析**

      		0: iconst_0      		//入栈0
             1: istore_1			//出栈，并赋值给a a=0
             2: iload_1				//入栈a的值
             3: bipush        10	//入栈10
             5: if_icmpge     14	//出栈两个数比较，如果成立继续往下执行，反之跳转到14:return
             8: iinc          1, 1  //slot[1]位置的数+1 ，就是a=a+1
            11: goto          2		//跳转到2：iload_1
            14: return				//结束方法

* do-while,for执行的字节码也可以按照上面分析，并且比较 while 和 for 的字节码，发现它们是一模一样的，殊途也能同归

**练习：**

_源码：_

    public class Demo_02 {
        public static void main(String[] args) {
            int i = 0;
            int x = 0;
            while (i < 10) {
                x = x++;
                i++;
            }
            System.out.println(x);//输出：0
        }
    }

字节码并分析：

    0: iconst_0					//入栈：0
    1: istore_1					//出栈，并赋值给slot[1] i=0
    2: iconst_0					//入栈：0
    3: istore_2					//出栈，并赋值slot[2] x=0
    4: iload_1					//slot[1]的值入栈
    5: bipush        10			//入栈10
    7: if_icmpge     21			//出栈两个数并比较，满足条件继续，反之跳转到21:getstatic     #2 
    10: iload_2					//slot[2]的值入栈
    11: iinc          2, 1		//slot[2]值自增
    14: istore_2				//出栈并赋值给slot[2] 
    15: iinc          1, 1		//slot[1]值自增
    18: goto          4			//跳转到4：
    21: getstatic     #2          // System.out对象入栈Field java/lang/System.out:Ljava/io/PrintStream;
    24: iload_2					//slot[2]的值入栈
    25: invokevirtual #3                  //Method java/io/PrintStream.println:(I)V
    28: return

#### 3.2.7 构造方法

**第一类：()V**

_**源码：**_

    public class Demo_03 {
        static int i = 10;
    
        static {
            i = 20;
        }
    
        static {
            i = 30;
        }
    }

> Java是懒加载，创建的类不会直接编译并产生对应的class文件，所以为了得到这个类的class，在其他的类中创建该类的对应的对象就好了：Demo_03
> demo_03 = new Demo_03();

_**字节码并分析：**_

* 编译器会按从上至下的顺序，收集所有 static 静态代码块和静态成员赋值的代码，合并为一个特殊的方  
  法 ()V ：

    {
      static int i;
        descriptor: I
        flags: ACC_STATIC
    
      public com.exp.test01.Demo_03();
        descriptor: ()V
        flags: ACC_PUBLIC
        Code:
          stack=1, locals=1, args_size=1
             0: aload_0
             1: invokespecial #1          //调用方法 Method java/lang/Object."":()V
             4: return
          LineNumberTable:
            line 3: 0
          LocalVariableTable:
            Start  Length  Slot  Name   Signature
                0       5     0  this   Lcom/exp/test01/Demo_03;
    
      static {};
        descriptor: ()V
        flags: ACC_STATIC
        Code:
          stack=1, locals=0, args_size=0
             0: bipush        10		//入栈10
             2: putstatic     #2         //出栈，并该值赋值到常量池中的变量 Field i:I
             5: bipush        20		//入栈20
             7: putstatic     #2          // 出栈，并该值赋值到常量池中的变量  Field i:I
            10: bipush        30		//入栈20
            12: putstatic     #2         //出栈，并该值赋值到常量池中的变量 Field i:I
            15: return
          LineNumberTable:
            line 4: 0
            line 7: 5
            line 11: 10
            line 12: 15
    }

> 可以调整一下 static 变量和静态代码块的位置，观察字节码的改动（下面是static 变量放到最下面得到的字节码）
>
> 0: bipush 20  
> 2: putstatic #2 // Field i:I  
> 5: bipush 30  
> 7: putstatic #2 // Field i:I  
> 10: bipush 10  
> 12: putstatic #2 // Field i:I  
> 15: return

**第二类：()V**

_源码：_

    public class Demo_04 {
        private String a = "s1";
    
        {
            b = 20;
        }
    
        private int b = 10;
    
        {
            a = "s2";
        }
    
        public Demo_04(String a, int b) {
            this.a = a;
            this.b = b;
        }
    
        public static void main(String[] args) {
            Demo_04 demo_04 = new Demo_04("ss", 27);
            System.out.println(demo_04.a); // ss
            System.out.println(demo_04.b);// 27
        }
    }

_字节码并分析_

* 编译器会按从上至下的顺序，收集所有 {} 代码块和成员变量赋值的代码，形成新的构造方法，但原始构造方法内的代码总是在最后

    {
      public com.exp.test01.Demo_04(java.lang.String, int);
        descriptor: (Ljava/lang/String;I)V
        flags: ACC_PUBLIC
        Code:
          stack=2, locals=3, args_size=3
             0: aload_0				//this 入栈
             1: invokespecial #1      //调用父类构造：super.()V Methodjava/lang/Object."":()V
             4: aload_0				//this 入栈
             5: ldc           #2      // s1 入栈
             7: putfield      #3       //出栈并赋值给成员变量this.a = "s1": Field a:Ljava/lang/String;
            10: aload_0				//this 入栈
            11: bipush        20		//入栈;20
            13: putfield      #4         // 出栈并赋值给成员变量this.b = 20 Field b:I
            16: aload_0				   //this 入栈
            17: bipush        10		//入栈10
            19: putfield      #4         // 出栈并赋值给成员变量this.b = 10 Field b:I
            22: aload_0					//this 入栈
            23: ldc           #5         // "s2" 入栈 String s2
            25: putfield      #3         // 出栈并赋值给成员变量this.a = "s2" Field a:Ljava/lang/String;
            28: aload_0					//---------------------旧的构造方法-------------------------
            29: aload_1					//slot[1]入栈 :"ss"
            30: putfield      #3           // 出栈并赋值给成员变量this.a = "ss"Field a:Ljava/lang/String;
            33: aload_0						//this 入栈
            34: iload_2						slot[2]入栈 :27
            35: putfield      #4            // 出栈并赋值给成员变量this.b = 27 Field b:I------------------
            38: return
          LineNumberTable:
            line 16: 0
            line 4: 4
            line 7: 10
            line 10: 16
            line 13: 22
            line 17: 28
            line 18: 33
            line 19: 38
          LocalVariableTable:
            Start  Length  Slot  Name   Signature
                0      39     0  this   Lcom/exp/test01/Demo_04;
                0      39     1     a   Ljava/lang/String;
                0      39     2     b   I
    
      public static void main(java.lang.String[]);
        descriptor: ([Ljava/lang/String;)V
        flags: ACC_PUBLIC, ACC_STATIC
        Code:
          stack=4, locals=2, args_size=1
             0: new           #6                  // class com/exp/test01/Demo_04
             3: dup
             4: ldc           #7                  // String ss
             6: bipush        27
             8: invokespecial #8                  // Method "":(Ljava/lang/String;I)V
            11: astore_1
            12: getstatic     #9                  // Field java/lang/System.out:Ljava/io/PrintStream;
            15: aload_1
            16: getfield      #3                  // Field a:Ljava/lang/String;
            19: invokevirtual #10                 // Method java/io/PrintStream.println:(Ljava/lang/String;)V
            22: getstatic     #9                  // Field java/lang/System.out:Ljava/io/PrintStream;
            25: aload_1
            26: getfield      #4                  // Field b:I
            29: invokevirtual #11                 // Method java/io/PrintStream.println:(I)V
            32: return
          LineNumberTable:
            line 22: 0
            line 23: 12
            line 24: 22
            line 25: 32
          LocalVariableTable:
            Start  Length  Slot  Name   Signature
                0      33     0  args   [Ljava/lang/String;
               12      21     1 demo_04   Lcom/exp/test01/Demo_04;
    }

#### 3.2.8 方法调用

**源码：**

    public class Demo_05 {
        public Demo_05() {
        }
    
        private void test01() {
        }
    
        private final void test02() {
        }
    
        public void test03() {
        }
    
        public static void test04() {
    
        }
    
        public static void main(String[] args) {
            Demo_05 demo_05 = new Demo_05();
            demo_05.test01();
            demo_05.test02();
            demo_05.test03();
            demo_05.test04();
            Demo_05.test04();
        }
    }

**字节码并分析：**

     0: new           #2                  // class com/exp/test01/Demo_05
    3: dup
    4: invokespecial #3                  // Method "":()V
    7: astore_1
    8: aload_1
    9: invokespecial #4                  // Method test01:()V
    12: aload_1
    13: invokespecial #5                  // Method test02:()V
    16: aload_1
    17: invokevirtual #6                  // Method test03:()V
    20: aload_1
    21: pop
    22: invokestatic  #7                  // Method test04:()V
    25: invokestatic  #7                  // Method test04:()V
    28: return

* new 是创建【对象】，给对象分配堆内存，执行成功会将【对象引用】压入操作数栈
* dup 是赋值操作数栈栈顶的内容，本例即为【对象引用】，为什么需要两份引用呢，一个是要配合 invokespecial 调用该对象的构造方法
  “”😦)V （会消耗掉栈顶一个引用），另一个要配合 astore_1 赋值给局部变量
* 最终方法（final），私有方法（private），构造方法都是由 invokespecial 指令来调用，属于静态绑定
* 普通成员方法是由 invokevirtual 调用，属于动态绑定，即支持多态
* 成员方法与静态方法调用的另一个区别是，执行方法前是否需要【对象引用】比较有意思的是 d.test4();
  是通过【对象引用】调用一个静态方法，可以看到在调用invokestatic 之前执行了 pop 指令，把【对象引用】从操作数栈弹掉了
* 还有一个执行 invokespecial 的情况是通过 super 调用父类方法

#### 3.2.9 多态原理

_**源码：**_

    package com.exp.test01;
    
    import java.io.IOException;
    
    /**
     *演示多态原理，注意先加上一下JVM参数，禁用指针压缩
     * -XX:-UseCompressedOops -XX:-UseCompressedClassPointers
     */
    public class Demo_06 {
        public static void test(Animal animal) {
            animal.eat();
            System.out.println(animal);
        }
    
        public static void main(String[] args) throws IOException {
            Animal cat = new Cat();
            Animal dog = new Dog();
            test(cat);
            test(dog);
            System.in.read();
        }
    }
    
    abstract class Animal {
        public abstract void eat();
    
        @Override
        public String toString() {
            return "我是" + this.getClass().getSimpleName();
        }
    }
    
    class Dog extends Animal {
        @Override
        public void eat() {
            System.out.println("啃骨头");
        }
    }
    
    class Cat extends Animal {
        @Override
        public void eat() {
            System.out.println("吃鱼");
        }
    }

1. 运行代码

    * 停在 System.in.read() 方法上，这时运行 jps 获取进程 id

![image-20230327012536023](https://img-
blog.csdnimg.cn/img_convert/a93043b1447f88e820df3034572ae36d.png)

2. 运行 HSDB 工具

    * 进入 JDK 安装目录，执行

`java -cp ./lib/sa-jdi.jar sun.jvm.hotspot.HSDB`

    * 进入图形界面 attach 进程 id

![image-20230327004943965](https://img-
blog.csdnimg.cn/img_convert/75cc2614d592858a208779edfed62a82.png)

3. 查找某个对象

    * 打开 Tools -> Find Object By Query

输入 select d from com.exp.test01.Dog d 点击 Execute 执行 点击 Execute 执行

查到的是该对象的内存中指针地址

![image-20230327012708354](https://img-
blog.csdnimg.cn/img_convert/3b120dfedf35685e5fe8fb0080a94024.png)

4. 查看对象内存结构

    * 点击超链接可以看到对象的内存结构，此对象没有任何属性，因此只有对象头的 16 字节，前 8 字节是MarkWord，后 8 字节就是对象的 Class 指针。但目前看不到它的实际地址。

![image-20230327012746849](https://img-
blog.csdnimg.cn/img_convert/2293a18eb657710c6a944e7980dedf10.png)

5. 查看对象 Class 的内存地址

方法一：

    * 可以通过 Windows -> Console 进入命令行模式，执行

mem 0x0000000741354d00 2

mem 有两个参数，参数 1 是对象地址，参数 2 是查看 2 行（即 16 字节）

_结果中第二行 0x00000000f800c1c1 即为 Class 的内存地址。。。_

> 发现我查出来的内存地址不对，暂时没查到原因 正确地址应该是：
> [0x00000007c0060e08](klass=0x00000007c0060e08)

方法二：

    * Tools -> Class Browser 输入 Dog 查找，可以得到相同的结果

![image-20230327013356596](https://img-
blog.csdnimg.cn/img_convert/634f40ba82d0848d49c126125207b234.png)

6. 查看类的 vtable

    * 无论通过哪种方法找到内存地址，都可以找到得到内存地址0x00000007c0060e08

    * Tools ->Inspector [ Alt+R] 进入 Inspector 工具，输入刚才的 Class 内存地址

![image-20230327013305508](https://img-
blog.csdnimg.cn/img_convert/97d84ec31d354186195c0d9080d5d637.png)

    * 然后 看到Dog Class 的 vtable 长度为 6，意思就是 Dog 类有 6 个虚方法（多态相关的，final，static 不会列入）。

![image-20230327013544678](https://img-
blog.csdnimg.cn/img_convert/f2ef22ef66e29c2f7c358a8794abf2ff.png)

那么这 6 个方法都是谁呢？从 Class 的起始地址开始算，偏移 0x1b8 就是 vtable 的起始地址，进行计算得到：

        0x00000007c0060e08
                0x1b8 +
    --------------------
    0x00000007c0060fc0

通过 Windows -> Console 进入命令行模式，执行

        mem 0x00000007c0060fc0 6
    0x00000007c0060fc0: 0x000002c9d3790c10 
    0x00000007c0060fc8: 0x000002c9d37906e8 
    0x00000007c0060fd0: 0x000002c9d3b93088 
    0x00000007c0060fd8: 0x000002c9d3790640 
    0x00000007c0060fe0: 0x000002c9d3790778 
    0x00000007c0060fe8: 0x000002c9d3b93658 

就得到了 6 个虚方法的入口地址

7. 验证方法地址

    * 通过 Tools -> Class Browser 查看每个类的方法定义，比较可知

![image-20230327020141911](https://img-
blog.csdnimg.cn/img_convert/5af5ae642f61dbf854866c212548d6cf.png)

发现eat() 方法是 Dog 类自己的

![image-20230327020433084](https://img-
blog.csdnimg.cn/img_convert/e005d21fbe9734bd43313ff6eea583a7.png)

发现toString()方法是 Animal 类的

![image-20230327020848676](https://img-
blog.csdnimg.cn/img_convert/af46dfb72aa712c3c301fc769ccee6ba.png)

发现剩余的四个方法（finalize，equals，hashCode，clone）都是调用Object类中的方法。

8. 小结：

当执行 invokevirtual 指令时，

    1. 先通过栈帧中的对象引用找到对象
    2. 分析对象头，找到对象的实际 Class
    3. Class 结构中有 vtable，它在类加载的链接阶段就已经根据方法的重写规则生成好了
    4. 查表得到方法的具体地址
    5. 执行方法的字节码

#### 3.2.10 异常处理

**1.try-catch**

**源码：**

    public class Demo_07 {
        public static void main(String[] args) {
            int i = 0;
            try {
                i = 10;
            } catch (Exception e) {
                i = 20;
            }
        }
    }

**字节码并分析：**

    {
      public com.exp.test01.Demo_07();
        descriptor: ()V           //构造方法，本例中不重要
        flags: ACC_PUBLIC
        Code:
          stack=1, locals=1, args_size=1
             0: aload_0
             1: invokespecial #1                  // Method java/lang/Object."":()V
             4: return
          LineNumberTable:
            line 3: 0
          LocalVariableTable:
            Start  Length  Slot  Name   Signature
                0       5     0  this   Lcom/exp/test01/Demo_07;
    
      public static void main(java.lang.String[]);
        descriptor: ([Ljava/lang/String;)V
        flags: ACC_PUBLIC, ACC_STATIC
        Code:
          stack=1, locals=3, args_size=1
             0: iconst_0			//0->操作符栈
             1: istore_1			//0<-操作符栈 <=> i=0
             2: bipush        10	//10->操作符栈
             4: istore_1			//10<-操作符栈 <=> i=10
             5: goto          12	//-->return
             8: astore_2			//e->操作符栈
             9: bipush        20	//20->操作符栈
            11: istore_1			//20<-操作符栈 <=> i=20
            12: return				//结束程序
          Exception table:		//异常表
             from    to  target type
                 2     5     8   Class java/lang/Exception //说明：要检测第二行到第五行的代码[2,5)，如果
                      									//出现异，先异常类型匹配，然后进入第八行继续执行
          LineNumberTable:
            line 5: 0
            line 7: 2
            line 10: 5
            line 8: 8
            line 9: 9
            line 11: 12
          LocalVariableTable:
            Start  Length  Slot  Name   Signature
                9       3     2     e   Ljava/lang/Exception;
                0      13     0  args   [Ljava/lang/String;
                2      11     1     i   I
          StackMapTable: number_of_entries = 2
            frame_type = 255 /* full_frame */
              offset_delta = 8
              locals = [ class "[Ljava/lang/String;", int ]
              stack = [ class java/lang/Exception ]
            frame_type = 3 /* same */
    }

* 可以看到多出来一个 Exception table 的结构，[from, to) 是前闭后开的检测范围，一旦这个范围内的字节码执行出现异常，则通过
  type 匹配异常类型，如果一致，进入 target 所指示行号
* 8 行的字节码指令 astore_2 是将异常对象引用存入局部变量表的 slot 2 位置

**2.多个 single-catch 块的情况**

**源码：**

    public class Demo_08 {
        public static void main(String[] args) {
            int i = 0;
            try {
                i = 10;
            } catch (ArithmeticException e) {
                i = 30;
            } catch (NullPointerException e) {
                i = 40;
            } catch (Exception e) {
                i = 50;
            }
        }
    }

**字节码并分析：**

      	stack=1, locals=3, args_size=1
             0: iconst_0
             1: istore_1
             2: bipush        10
             4: istore_1
             5: goto          26
             8: astore_2
             9: bipush        30
            11: istore_1
            12: goto          26
            15: astore_2
            16: bipush        40
            18: istore_1
            19: goto          26
            22: astore_2
            23: bipush        50
            25: istore_1
            26: return
          Exception table: //异常表：可以发现他们都是检测同一个代码段，并且根据type匹配并跳转到对应的位置继续执行
             from    to  target type   
                 2     5     8   Class java/lang/ArithmeticException
                 2     5    15   Class java/lang/NullPointerException
                 2     5    22   Class java/lang/Exception
          LineNumberTable:
            line 5: 0
            line 7: 2
            line 14: 5
            line 8: 8
            line 9: 9
            line 14: 12
            line 10: 15
            line 11: 16
            line 14: 19
            line 12: 22
            line 13: 23
            line 15: 26
          LocalVariableTable: //局部变量表：发现e的存储过程中应用了卡槽的复用
            Start  Length  Slot  Name   Signature
                9       3     2     e   Ljava/lang/ArithmeticException; 
               16       3     2     e   Ljava/lang/NullPointerException;
               23       3     2     e   Ljava/lang/Exception;
                0      27     0  args   [Ljava/lang/String;
                2      25     1     i   I

**3.multi-catch 的情况**

源码：

    public class Demo_09 {
        public static void main(String[] args) {
            try {
                Method test = Demo_09.class.getMethod("test");
                test.invoke(null);
            } catch (NoSuchMethodException | IllegalAccessException |
                     InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    
        public static void test() {
            System.out.println("ok");
        }
    }

字节码并分析：

     Code:
          stack=3, locals=2, args_size=1
             0: ldc           #2                  // class com/exp/test01/Demo_09
             2: ldc           #3                  // String test
             4: iconst_0
             5: anewarray     #4                  // class java/lang/Class
             8: invokevirtual #5                  // Method java/lang/Class.getMethod:(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
            11: astore_1
            12: aload_1
            13: aconst_null
            14: iconst_0
            15: anewarray     #6                  // class java/lang/Object
            18: invokevirtual #7                  // Method java/lang/reflect/Method.invoke:(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
            21: pop
            22: goto          30
            25: astore_1
            26: aload_1
            27: invokevirtual #11                 // Method java/lang/ReflectiveOperationException.printStackTrace:()V
            30: return
          Exception table:
             from    to  target type
                 0    22    25   Class java/lang/NoSuchMethodException
                 0    22    25   Class java/lang/IllegalAccessException
                 0    22    25   Class java/lang/reflect/InvocationTargetException
          LineNumberTable:
            line 9: 0
            line 10: 12
            line 14: 22
            line 11: 25
            line 13: 26
            line 15: 30
          LocalVariableTable:
            Start  Length  Slot  Name   Signature
               12      10     1  test   Ljava/lang/reflect/Method;
               26       4     1     e   Ljava/lang/ReflectiveOperationException;
                0      31     0  args   [Ljava/lang/String;

**4.finally**

源码：

    public class Demo_10 {
        public static void main(String[] args) {
            int i = 0;
            try {
                i = 10;
            } catch (Exception e) {
                i = 20;
            } finally {
                i = 30;
            }
        }
    }

字节码并分析：

     Code:
          stack=1, locals=4, args_size=1
             0: iconst_0			
             1: istore_1			//0-> i
             2: bipush        10	//try-----------------begin----------
             4: istore_1			//10->i
             5: bipush        30	//finally
             7: istore_1			//30->i
             8: goto          27	//return----------------end--------
            11: astore_2			//catch Exception ——> e -------
            12: bipush        20	//
            14: istore_1			//20->i
            15: bipush        30	//finally
            17: istore_1			//30->i
            18: goto          27	//return------------------------
            21: astore_3			//catch any ——> slot[3] -------
            22: bipush        30	//finally
            24: istore_1			//30->i
            25: aload_3				//<-slot[3] 
            26: athrow				//throw-----------------------
            27: return
          Exception table:
             from    to  target type
                 2     5    11   Class java/lang/Exception
                 2     5    21   any	//剩余的异常类型如error
                11    15    21   any	//剩余的异常类型如error
          LineNumberTable:
            line 5: 0
            line 7: 2
            line 11: 5
            line 12: 8
            line 8: 11
            line 9: 12
            line 11: 15
            line 12: 18
            line 11: 21
            line 12: 25
            line 13: 27
          LocalVariableTable:
            Start  Length  Slot  Name   Signature
               12       3     2     e   Ljava/lang/Exception;
                0      28     0  args   [Ljava/lang/String;
                2      26     1     i   I

* 可以看到 finally 中的代码被复制了 3 份，分别放入 try 流程，catch 流程以及 catch 剩余的异常类型流程

#### 3.2.11 finally相关面试题

**1.finally 出现了 return**

源码：

    public class Demo_11 {
        public static void main(String[] args) {
            int result = test();
            System.out.println(result); //20
        }
    
        public static int test() {
            try {
                return 10;
            } finally {
                return 20;
            }
        }
    }

字节码并分析：

     stack=1, locals=2, args_size=0
             0: bipush        10	//<-10 入栈
             2: istore_0			//10 -> slot[0]
             3: bipush        20	//<-20 入栈
             5: ireturn				//返回栈顶元素 int(20)
             6: astore_1			//catch any->slot[1]----------
             7: bipush        20	//<-20 入栈
             9: ireturn				//返回栈顶元素 int(20)
          Exception table:
             from    to  target type
                 0     3     6   any

* 由于 finally 中的 ireturn 被插入了所有可能的流程，因此返回结果肯定以 finally 的为准

* 至于字节码中第 2 行，似乎没啥用，且留个伏笔，看下个例子

* 跟上例中的 finally 相比，发现没有 athrow 了，这告诉我们：如果在 finally 中出现了 return，会吞掉异常

      public class Demo_12 {
      public static void main(String[] args) {
          int result = test();
          System.out.println(result);//20
      }

      public static int test() {
          try {
              int i = 1 / 0; //没有异常throw ,原因是在finally中的return吞掉该异常
              return 10;
          } finally {
              return 20;
          }
      }
  }

2.finally 对返回值影响

源码：

    public class Demo_13 {
        public static void main(String[] args) {
            int result = test();
            System.out.println(result);//10
        }
    
        public static int test() {
            int i = 10;
            try {
                return i;
            } finally {
                i = 20;
            }
        }
    }

字节码并分析：

    stack=1, locals=3, args_size=0
             0: bipush        10	//<- 10 放入栈顶
             2: istore_0			//10->i
             3: iload_0				//<- i(10)
             4: istore_1			//10 -> slot 1，暂存至 slot 1，目的是为了固定返回值
             5: bipush        20	//<- 20 放入栈顶
             7: istore_0			// 20 -> i
             8: iload_1				// <- slot 1(10) 载入 slot 1 暂存的值
             9: ireturn				// 返回栈顶的 int(10)
            10: astore_2			//catch any引用-> 入栈 ---------
            11: bipush        20	//执行finally 
            13: istore_0			//20->i
            14: aload_2				//any引用<- 出栈
            15: athrow				//抛异常
          Exception table:
             from    to  target type
                 3     5    10   any

#### 3.2.12 synchronized

源码：

    public class Demo_14 {
        public static void main(String[] args) {
            Object lock = new Object();
            synchronized (lock) {
                System.out.println("ok");
            }
        }
    }

字节码指令和分析：

     public static void main(java.lang.String[]);
        descriptor: ([Ljava/lang/String;)V
        flags: ACC_PUBLIC, ACC_STATIC
        Code:
          stack=2, locals=4, args_size=1
             0: new           #2                  // class java/lang/Object
             3: dup
             4: invokespecial #1                  // Method java/lang/Object."":()V
             7: astore_1						//lock:引用 ->lock
             8: aload_1							//<-lock --------synchronized-----
             9: dup								//复制lock引用：一个加锁用，另一个解锁用
            10: astore_2						//一份lock引用，暂时出栈并暂存到slot 2中
            11: monitorenter					//monitorenter[lock]:加锁操作
            12: getstatic     #3                  // Field java/lang/System.out:Ljava/io/PrintStream;
            15: ldc           #4                  // String ok
            17: invokevirtual #5                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
            20: aload_2							//前面暂存到slot 2中lock引用入栈
            21: monitorexit						//monitorexit[lock]:解锁操作
            22: goto          30				//return
            25: astore_3						//catch：any - > slot 3
            26: aload_2							//前面暂存到slot 2中lock引用入栈
            27: monitorexit						//monitorexit[lock]:解锁操作
            28: aload_3							//<- slot 3
            29: athrow
            30: return
          Exception table:
             from    to  target type
                12    22    25   any
                25    28    25   any
          LineNumberTable:
            line 5: 0
            line 6: 8
            line 7: 12
            line 8: 20
            line 9: 30
          LocalVariableTable:
            Start  Length  Slot  Name   Signature
                0      31     0  args   [Ljava/lang/String;
                8      23     1  lock   Ljava/lang/Object;

* 方法级别的 synchronized 不会在字节码指令中有所体现

### 3.3 编译期处理

> 所谓的 语法糖 ，其实就是指 java 编译器把 *.java 源码编译为 *.class
> 字节码的过程中，自动生成和转换的一些代码，主要是为了减轻程序员的负担，算是 java 编译器给我们的一个额外福利（给糖吃嘛）  
> 注意，以下代码的分析，借助了 javap 工具，idea 的反编译功能，idea 插件 jclasslib 等工具。另外，编译器转换的结果直接就是
> class 字节码， **只是为了便于阅读，给出了 几乎等价 的 java 源码方式，并不是编译器还会转换出中间的 java 源码，切记。**

#### 3.3.1 默认构造器

源码：

    public class Candy01 {
    }

编程成Class后的代码并分析：

    public class Candy01 {
        // 这个无参构造是编译器帮助我们加上的
        public Candy01() {
            super(); // 即调用父类 Object 的无参构造方法，即调用 java/lang/Object."":()V
        }
    }

#### 3.3.2 自动拆装箱

* 这个特性是 JDK 5 开始加入的， 代码片段1 ：

      public class Candy02 {
      public static void main(String[] args) {
          Integer x = 1;
          int y = x;
      }
  }


* 这段代码在 JDK 5 之前是无法编译通过的，必须改写为 代码片段2 :

      public class Candy2 {
  	public static void main(String[] args) {
  		Integer x = Integer.valueOf(1);
  		int y = x.intValue();
  	}
  }


* 显然之前版本的代码太麻烦了，需要在基本类型和包装类型之间来回转换(尤其是集合类中操作的都是包装类型)
  。因此这些转换的事儿在JDK5以后都是由编译器在编译阶段完成。即代码片段1都会在编译阶段被转化为代码片段2。

#### 3.3.3 泛型集合取值

* 泛型也是在 JDK 5 开始加入的特性，但 java 在编译泛型代码后会执行 **泛型擦除** 的动作，即泛型信息在编译为字节码之后就丢失了，实际的类型都当做了
  Object 类型来处理：

      public class Candy03 {

      public static void main(String[] args) {
          List<Integer> list = new ArrayList<>();
          list.add(10); // 实际调用的是 List.add(Object e)
          Integer x = list.get(0); // 实际调用的是 Object obj = List.get(int index);
      }
  }


* 所以在取值时，编译器真正生成的字节码中，还要额外做一个类型转换的操作：

      // 需要将 Object 转为 Integer
  Integer x = (Integer)list.get(0);


* 如果前面的 x 变量类型修改为 int 基本类型那么最终生成的字节码是：

      // 需要将 Object 转为 Integer, 并执行拆箱操作
  int x = ((Integer)list.get(0)).intValue();


* 擦除的是字节码上的泛型信息，可以看到 LocalVariableTypeTable 仍然保留了方法参数泛型的信息

      {
  public com.exp.test02.Candy03();
  descriptor: ()V
  flags: ACC_PUBLIC
  Code:
  stack=1, locals=1, args_size=1
  0: aload_0
  1: invokespecial #1 // Method java/lang/Object."":()V
  4: return
  LineNumberTable:
  line 6: 0
  LocalVariableTable:
  Start Length Slot Name Signature
  0 5 0 this Lcom/exp/test02/Candy03;

  public static void main(java.lang.String[]);
  descriptor: ([Ljava/lang/String;)V
  flags: ACC_PUBLIC, ACC_STATIC
  Code:
  stack=2, locals=3, args_size=1
  0: new #2 // class java/util/ArrayList
  3: dup
  4: invokespecial #3 // Method java/util/ArrayList."":()V
  7: astore_1
  8: aload_1
  9: bipush 10
  11: invokestatic #4 --装箱 // Method java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
  14: invokeinterface #5, 2 --类型被消除了 // InterfaceMethod java/util/List.add:(Ljava/lang/Object;)Z
  19: pop
  20: aload_1
  21: iconst_0
  22: invokeinterface #6, 2 --类型被消除了 // InterfaceMethod java/util/List.get:(I)Ljava/lang/Object;
  27: checkcast #7 --类型强制转换 // class java/lang/Integer
  30: astore_2
  31: return
  LineNumberTable:
  line 9: 0
  line 10: 8
  line 11: 20
  line 12: 31
  LocalVariableTable:
  Start Length Slot Name Signature  
  0 32 0 args   [Ljava/lang/String;
  8 24 1 list Ljava/util/List;
  31 1 2 x Ljava/lang/Integer;
  LocalVariableTypeTable: //局部变量类型表
  Start Length Slot Name Signature
  8 24 1 list Ljava/util/List<Ljava/lang/Integer;>;
  }


* 使用反射，仍然能够获得这些信息：

      public Set test(List list,Map map){
      return new HashSet();
  }

  public static void main(String[] args) throws NoSuchMethodException {
  Method test = Candy03.class.getMethod("test", List.class, Map.class);
  Type[] types = test.getGenericParameterTypes();
  for (Type type :
  types) {
  if (type instanceof ParameterizedType){
  ParameterizedType parameterizedType = (ParameterizedType) type;
  System.out.println("原始类型 - "+parameterizedType.getRawType());
  Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
  for (int i = 0; i < actualTypeArguments.length; i++) {
  System.out.printf("泛型参数[%d] - %s\n",i,actualTypeArguments[i]);
  }
  }
  }
  }

输出：

![image-20230327134849182](https://img-
blog.csdnimg.cn/img_convert/330219aa8013763e750d790c13fd54bd.png)、

#### 3.3.4 可变参数

源码：

* 可变参数也是 JDK 5 开始加入的新特性：

      public class Candy04 {
      public static void foo(String... args) {
          String[] array = args; // 直接赋值
          System.out.println(array);
      }

      public static void main(String[] args) {
          foo("hello", "world");
      }
  }


* 可变参数 String… args 其实是一个 String[] args ，从代码中的赋值语句中就可以看出来。 同样 java 编译器会在编译期间将上述代码变换为：

      public class Candy04 {
      public static void foo(String[] args) {
          String[] array = args; // 直接赋值
          System.out.println(array);
      }

      public static void main(String[] args) {
          foo(new String["hello", "world"]);
      }
  }

> 注意 如果调用了 foo() 则等价代码为 foo(new String[]{}) ，创建了一个空的数组，而不会传递 null 进去

#### 3.3.5 foreach 循环

* 仍是 JDK 5 开始引入的语法糖，数组的循环：

      public class Demo_05 {
      public static void main(String[] args) {
          int[] array = {1, 2, 3, 4, 5}; // 数组赋初值的简化写法也是语法糖哦
          for (int e : array) {
              System.out.println(e);
          }
      }
  }

编译器优化后的：

        public class Demo_05 {
        public Demo_05() {
        }
        public static void main(String[] args) {
            int[] array = new int[]{1, 2, 3, 4, 5};
            int[] var2 = array;
            int var3 = array.length;
            for(int var4 = 0; var4 < var3; ++var4) {
                int e = var2[var4];
                System.out.println(e);
            }
        }
    }

* 而集合的循环：

      public class Demo_06 {
      public static void main(String[] args) {
          List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
          for (Integer i : list) {
              System.out.println(i);
          }
      }
  }

编译器优化后的：

        public class Demo_06 {
        public Demo_06() {
        }
        public static void main(String[] args) {
            List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
            Iterator var2 = list.iterator();
            while(var2.hasNext()) {
                Integer i = (Integer)var2.next();
                System.out.println(i);
            }
        }
    }

> 注意 foreach 循环写法，能够配合数组，以及所有实现了 Iterable 接口的集合类一起使用，其中 Iterable 用来获取集合的迭代器（
> Iterator ）

#### 3.3.6 switch 字符串

从 JDK 7 开始，switch 可以作用于字符串和枚举类，这个功能其实也是语法糖，例如：

    public class Demo_07 {
        public static void choose(String str) {
            switch (str) {
                case "hello": {
                    System.out.println("h");
                    break;
                }
                case "world": {
                    System.out.println("w");
                    break;
                }
            }
        }
    }

> **注意** switch 配合 String 和枚举使用时，变量不能为null，原因分析完语法糖转换后的代码应当自然清楚

会被编译器转换为：

    public class Demo_07{
        public Demo_07(){
        }
        public static void choose(String str){
            String s = str;
            byte byte0 = -1;
            switch(s.hashCode()){
            case 99162322:  //hello的hashCode
                if(s.equals("hello"))
                    byte0 = 0;
                break;
    
            case 113318802: //world的hashCode
                if(s.equals("world"))
                    byte0 = 1;
                break;
            }
            switch(byte0){
            case 0: // '\0'
                System.out.println("h");
                break;
    
            case 1: // '\001'
                System.out.println("w");
                break;
            }
        }
    }

可以看到，执行了两遍 switch，第一遍是根据字符串的 hashCode 和 equals 将字符串的转换为相应byte 类型，第二遍才是利用 byte
执行进行比较。  
为什么第一遍时必须既比较 hashCode，又利用 equals 比较呢？hashCode 是为了提高效率，减少可能的比较；而 equals 是为了防止
hashCode 冲突，例如 BM 和 C. 这两个字符串的hashCode值都是2123 ，如果有如下代码：

    public class Demo_08 {
        public static void choose(String str) {
            switch (str) {
                case "BM": {
                    System.out.println("h");
                    break;
                }
                case "C.": {
                    System.out.println("w");
                    break;
                }
            }
        }
    }

会被编译器转换为：

    public class Demo_08{
        public Demo_08(){
        }
        public static void choose(String str){
            String s = str;
            byte byte0 = -1;
            switch(s.hashCode()){
            case 2123:   // hashCode 值可能相同，需要进一步用 equals 比较
                if(s.equals("C."))
                    byte0 = 1;
                else
                if(s.equals("BM"))
                    byte0 = 0;
                break;
            }
            switch(byte0){
            case 0: // '\0'
                System.out.println("h");
                break;
            case 1: // '\001'
                System.out.println("w");
                break;
            }
        }
    }

#### 3.3.7 switch枚举

源码：

    public enum Sex {
        MALE,FEMALE
    }
    
    
    
    public class Demo_09 {
        public static void foo(Sex sex) {
            switch (sex) {
                case MALE:
                    System.out.println("男");
                    break;
                case FEMALE:
                    System.out.println("女");
                    break;
            }
        }
    }

转字节码后：

    public class Demo_09{
        public Demo_09(){}
        public static void foo(Sex sex){
            /**
            *定义了一个合成类（仅JVM使用，对我们看不见）
            *用来映射枚举的ordinal与数组元素的关系
            *枚举的 ordinal 表示枚举对象的序号，从 0 开始
            *即 MALE 的 ordinal()=0，FEMALE 的 ordinal()=1
            *下面为了看的清楚把原来的变量名等简化了
            * map = $SwitchMap$com$exp$test02$Sex
            */
            static class _cls1{
    		   //static final int $SwitchMap$com$exp$test02$Sex[];
                static final int map[];
                static {
                    //数组大小即为枚举元素个数，里面存储case用来对比的数字
                    map = new int[Sex.values().length];
                    try{
                        map[Sex.MALE.ordinal()] = 1;//map[0] = 1
                    }
                    catch(NoSuchFieldError nosuchfielderror) { }
                    try{
                        map[Sex.FEMALE.ordinal()] = 2; //map[1] =2
                    }
                    catch(NoSuchFieldError nosuchfielderror1) { }
                }
            }
    
            switch(_cls1.map[sex.ordinal()]){
            case 1: // '\001'
                System.out.println("\u7537");
                break;
    
            case 2: // '\002'
                System.out.println("\u5973");
                break;
            }
        }
    }

#### 3.3.8 枚举类

> JDK 7 新增了枚举类，以前面的性别枚举为例：

源码：

    public enum Sex {
        MALE,FEMALE
    }

转码后：

    public final class Sex extends Enum{
        public static Sex[] values(){
            return (Sex[])$VALUES.clone();
        }
        public static Sex valueOf(String name){
            return (Sex)Enum.valueOf(com/exp/test02/Sex, name);
        }
        //私有构造方法
        private Sex(String s, int i){
            super(s, i);
        }
        public static final Sex MALE;
        public static final Sex FEMALE;
        private static final Sex $VALUES[];
        static 
        {
            MALE = new Sex("MALE", 0);
            FEMALE = new Sex("FEMALE", 1);
            $VALUES = (new Sex[] {
                MALE, FEMALE
            });
        }
    }

#### 3.3.9 try-with-resources

JDK 7 开始新增了对需要关闭的资源处理的特殊语法 try-with-resources`：

    try(资源变量 = 创建资源对象){
    } catch( ) {
    }

其中 **资源对象需要实现 AutoCloseable 接口** ，例如 InputStream 、 OutputStream 、Connection 、
Statement 、 ResultSet 等接口都实现了 AutoCloseable ，使用 try-withresources 可以不用写
finally 语句块，编译器会帮助生成关闭资源代码，例如：

    public class Demo_10 {
        public static void main(String[] args) {
            try (InputStream is = new FileInputStream("d:\\1.txt")) {
                System.out.println(is);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

转化后：

    public class Demo_10{
        public Demo_10(){}
        public static void main(String args[]){
            try{
            	InputStream is;
            	is = new FileInputStream("d:\\1.txt");
            	Throwable t = null;
            	try{
                	System.out.println(is);
            	} catch (Throwable e1) {
    				// t 是我们代码出现的异常
    				t = e1;
    				throw e1;
    			} finally {
    				// 判断了资源不为空
    				if (is != null) {
    					// 如果我们代码有异常
    					if (t != null) {
    						try {
    							is.close();
    						} catch (Throwable e2) {
    							// 如果 close 出现异常，作为被压制异常添加
    							t.addSuppressed(e2);
    						}
    				} else {
    					// 如果我们代码没有异常，close 出现的异常就是最后 catch 块中的 e
    					is.close();
    				}
    			}
    		} catch (IOException e) {
    				e.printStackTrace();
    		}
    	}
    }

为什么要设计一个 addSuppressed(Throwable e) （添加被压制异常）的方法呢？是为了防止异常信息的丢失（想想 try-with-
resources 生成的 fianlly 中如果抛出了异常）：

    public class Demo_11 {
        public static void main(String[] args) {
            try (MyResource resource = new MyResource()) {
                int i = 1/0;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    class MyResource implements AutoCloseable {
        public void close() throws Exception {
            throw new Exception("close 异常");
        }
    }

输出结果：

    java.lang.ArithmeticException: / by zero
    	at com.exp.test02.Demo_11.main(Demo_11.java:6)
    	Suppressed: java.lang.Exception: close 异常
    		at com.exp.test02.MyResource.close(Demo_11.java:14)
    		at com.exp.test02.Demo_11.main(Demo_11.java:7)

#### 3.3.10 方法重写时的桥接方法

我们都知道，方法重写时对返回值分两种情况：

* 父子类的返回值完全一致
* 子类返回值可以是父类返回值的子类（比较绕口，见下面的例子）

    class A {
    	public Number m() {
    		return 1;
    	}
    }
    class B extends A {
    	@Override
    	// 子类 m 方法的返回值是 Integer 是父类 m 方法返回值 Number 的子类
    	public Integer m() {
    		return 2;
    	}
    }

对于子类，java 编译器会做如下处理：

    class B extends A {
    	public Integer m() {
    		return 2;
    	}
    	// 此方法才是真正重写了父类 public Number m() 方法
    	public synthetic bridge Number m() {
    		// 调用 public Integer m()
    		return m();
    	}
    }

其中桥接方法比较特殊，仅对 java 虚拟机可见，并且与原来的 public Integer m() 没有命名冲突，可以  
用下面反射代码来验证：

    for (Method m : B.class.getDeclaredMethods()) {
    	System.out.println(m);
    }

会输出：

    public java.lang.Integer test.candy.B.m()
    public java.lang.Number test.candy.B.m()

#### 3.3.11 匿名内部类

源码：

    public class Candy11 {
        public static void main(String[] args) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    System.out.println("ok");
                }
            };
        }
    }

转换后：

    // 额外生成的类
    final class Candy11$1 implements Runnable {
    	Candy11$1() {
    	}
    	public void run() {
    		System.out.println("ok");
    	}
    }
    public class Candy11 {
    	public static void main(String[] args) {
    		Runnable runnable = new Candy11$1();
    	}
    }

引用局部变量的匿名内部类，源代码：

    public class Candy11 {
    	public static void test(final int x) {
    		Runnable runnable = new Runnable() {
    			@Override
    			public void run() {
    				System.out.println("ok:" + x);
    			}
    		};
    	}
    }

转换后：

    // 额外生成的类
    final class Candy11$1 implements Runnable {
    	int val$x;
    	Candy11$1(int x) {
    		this.val$x = x;
    	}
    	public void run() {
    		System.out.println("ok:" + this.val$x);
    	}
    }
    public class Candy11{
    	public static void test(final int x) {
    		Runnable runnable = new Candy11$1(x);
    	}
    }

> 注意 这同时解释了为什么匿名内部类引用局部变量时，局部变量必须是 final 的：因为在创建Candy11$1 对象时，将 x 的值赋值给了
> Candy11$1 对象的 valx属 性 ， x不 应 该 再 发 生 变 了 ， 如 果 变 了， 那 么 valx 属性没有机会再跟着一起变化

### 3.4 类加载阶段

#### 3.4.1 加载

* 将类的字节码载入方法区中，内部采用 C++ 的 instanceKlass 描述 java 类，它的重要 field 有：
    * _java_mirror 即 java 的类镜像，例如对 String 来说，就是 String.class，作用是把 klass 暴  
      露给 java 使用

    * _super 即父类
    * _fields 即成员变量
    * _methods 即方法
    * _constants 即常量池
    * _class_loader 即类加载器
    * _vtable 虚方法表
    * _itable 接口方法表
* 如果这个类还有父类没有加载，先加载父类
* 加载和链接可能是交替运行的

> 注意  
> instanceKlass 这样的【元数据】是存储在方法区（1.8 后的元空间内），但 _java_mirror  
> 是存储在堆中可以通过前面介绍的 HSDB 工具查看

![image-20230327175031337](https://img-
blog.csdnimg.cn/img_convert/9c0780bf84d3711c48076b24ce7aed4f.png)

#### 3.4.2 链接

**验证**

* 验证类是否符合 JVM规范，安全性检查  
  用 UE 等支持二进制的编辑器修改 HelloWorld.class 的魔数(CA -> AA)，在控制台运行

![image-20230327181134925](https://img-
blog.csdnimg.cn/img_convert/bac67414e32808812c686d9e4545220f.png)

![image-20230327181240368](https://img-
blog.csdnimg.cn/img_convert/b8655c7fea6efe1f5a260e421d589110.png)

**准备** : 为 static 变量分配空间，设置默认值

* static 变量在 JDK 7 之前存储于 instanceKlass 末尾，从 JDK 7 开始，存储于 _java_mirror 末尾
* static 变量分配空间和赋值是两个步骤，分配空间在准备阶段完成，赋值在初始化阶段完成
* 如果 static 变量是 final 的基本类型，以及字符串常量，那么编译阶段值就确定了，赋值在准备阶段完成  
  如果 static 变量是 final 的，但属于引用类型，那么赋值也会在初始化阶段完成

**解析：**将常量池中的符号引用解析为直接引用

    package com.exp.test03;
    import java.io.IOException;
    /**
     * 解析的含义
     */
    public class Load_01 {
    
        public static void main(String[] args) throws ClassNotFoundException,
                IOException {
            ClassLoader classloader = Load_01.class.getClassLoader();
            // loadClass 方法不会导致类的解析和初始化
            Class<?> c = classloader.loadClass("com.exp.test03.C");
            // new C();
            System.in.read();
        }
    }
    
    class C {
        D d = new D();
    }
    
    class D {
    }

#### 3.4.3 初始化

**()V 方法**

* 初始化即调用 ()V ，虚拟机会保证这个类的『构造方法』的线程安全

**发生的时机**

* 概括得说，类初始化是【懒惰的】

    * main 方法所在的类，总会被首先初始化
    * 首次访问这个类的静态变量或静态方法时
    * 子类初始化，如果父类还没初始化，会引发
    * 子类访问父类的静态变量，只会触发父类的初始化
    * Class.forName
    * new 会导致初始化
* 不会导致类初始化的情况

    * 访问类的 static final 静态常量（基本类型和字符串）不会触发初始化
    * 类对象.class 不会触发初始化
    * 创建该类的数组不会触发初始化
    * 类加载器的loadClass方法
    * Class.forName的参数2为false时
* 实验

      class A {
  	static int a = 0;
  	static {
  		System.out.println("a init");
  	}
  }
  class B extends A {
  final static double b = 5.0;
  static boolean c = false;
  static {
  System.out.println("b init");
  }
  }

验证（实验时请先全部注释，每次只执行其中一个）

        public class Load3 {
    	static {
    		System.out.println("main init");
    	}
    	public static void main(String[] args) throws ClassNotFoundException {
    		// 1. 静态常量（基本类型和字符串）不会触发初始化
    		System.out.println(B.b);
    		// 2. 类对象.class 不会触发初始化
    		System.out.println(B.class);
    		// 3. 创建该类的数组不会触发初始化
    		System.out.println(new B[0]);
    		// 4. 不会初始化类 B，但会加载 B、A
    		ClassLoader cl = Thread.currentThread().getContextClassLoader();
    		cl.loadClass("cn.itcast.jvm.t3.B");
    		// 5. 不会初始化类 B，但会加载 B、A
    		ClassLoader c2 = Thread.currentThread().getContextClassLoader();
    		Class.forName("cn.itcast.jvm.t3.B", false, c2);
    		// 1. 首次访问这个类的静态变量或静态方法时
    		System.out.println(A.a);
    		// 2. 子类初始化，如果父类还没初始化，会引发
    		System.out.println(B.c);
    		// 3. 子类访问父类静态变量，只触发父类初始化
    		System.out.println(B.a);
    		// 4. 会初始化类 B，并先初始化类 A
    		Class.forName("cn.itcast.jvm.t3.B");
    	}
    }

#### 3.4.4 练习

从字节码分析，使用 a，b，c 这三个常量是否会导致 E 初始化：

    public class Load_02 {
        public static void main(String[] args) {
            // static final 的基本类型，以及字符串常量，那么编译阶段值就确定，赋值在准备阶段完成,所以不会初始化
            System.out.println(E.a); 
            System.out.println(E.b);
            //static final 的引用类型，那么赋值也会在初始化阶段完成，导致E初始化
            System.out.println(E.c);
        }
    }
    
    class E {
        public static final int a = 10;
        public static final String b = "hello";
        public static final Integer c = 20;
    }

典型应用 - 完成懒惰初始化单例模式

    public final class Singleton {
    	private Singleton() { }
    	// 内部类中保存单例
    	private static class LazyHolder {
    		static final Singleton INSTANCE = new Singleton();
    	}
    	// 第一次调用 getInstance 方法，才会导致内部类加载和初始化其静态成员
    	public static Singleton getInstance() {
    		return LazyHolder.INSTANCE;
    	}
    }

以上的实现特点是：

* 懒惰实例化
* 初始化时的线程安全是有保障的

### 3.5 类加载器

> 以 JDK 8 为例：
>
> **名称**|  **加载哪的类**|  **说明**  
> ---|---|---  
> Bootstrap ClassLoader| JAVA_HOME/jre/lib| 无法直接访问  
> Extension ClassLoader| JAVA_HOME/jre/lib/ext| 上级为 Bootstrap，显示为 null  
> Application ClassLoader| classpath| 上级为 Extension  
> 自定义类加载器| 自定义| 上级为 Application  

#### 3.5.1 启动类加载器

用 Bootstrap 类加载器加载类：

![image-20230327192925798](https://img-
blog.csdnimg.cn/img_convert/9cb7665dc051ea5d405fb7268cc0bfd2.png)

执行:

![image-20230327192936777](https://img-
blog.csdnimg.cn/img_convert/025e948ce6bec5f0daf3b1d067dc729b.png)

输出：

![image-20230327193001422](https://img-
blog.csdnimg.cn/img_convert/e9bddb87a4ae2186a1bbd5b66a1d923a.png)

* -Xbootclasspath 表示设置 bootclasspath
* 其中 /a:. 表示将当前目录追加至 bootclasspath 之后
* 可以用这个办法替换核心类
    * java -Xbootclasspath:
    * java -Xbootclasspath/a:<追加路径>
    * java -Xbootclasspath/p:<追加路径>

#### 3.5.2 扩展类加载器

![image-20230327193202952](https://img-
blog.csdnimg.cn/img_convert/36b6144ee56102fd5aed02a0fa3f279c.png)

执行：

![image-20230327193222976](https://img-
blog.csdnimg.cn/img_convert/9354be94c0ce228a7dc84ee61d5f2e81.png)

输出：

![image-20230327193241968](https://img-
blog.csdnimg.cn/img_convert/ffe67487bfa89f254945ba6c4dfa1dfa.png)

写一个同名的类：

![image-20230327193304817](https://img-
blog.csdnimg.cn/img_convert/c3ee20cc20e6d6c9c7572b7e720cd692.png)

打个 jar 包

![image-20230327193328586](https://img-
blog.csdnimg.cn/img_convert/813469cba82858c5d28cfa1598ddbe95.png)

将 jar 包拷贝到 JAVA_HOME/jre/lib/ext

重新执行 Load5_2

输出

![image-20230327193355581](https://img-
blog.csdnimg.cn/img_convert/4d8690455f01751e4b27c4b4205b1df8.png)

#### 3.5.3 双亲委派模式

所谓的双亲委派，就是指调用类加载器的 loadClass 方法时，查找类的规则

> 注意：这里的双亲，翻译为上级似乎更为合适，因为他们没有直接的继承关系

    protected Class<?> loadClass(String name, boolean resolve)
        throws ClassNotFoundException
    {
        synchronized (getClassLoadingLock(name)) {
            //  1. 检查该类是否已经加载
            Class<?> c = findLoadedClass(name);
            if (c == null) {
                long t0 = System.nanoTime();
                try {
                    if (parent != null) {
                        // 2. 有上级的话，委派上级 loadClass
                        c = parent.loadClass(name, false);
                    } else {
                        //如果没有上级了（ExtClassLoader），则委派BootstrapClassLoader
                        c = findBootstrapClassOrNull(name);
                    }
                } catch (ClassNotFoundException e) {
                    // ClassNotFoundException thrown if class not found
                    // from the non-null parent class loader
                }
    
                if (c == null) {
                    // 4. 每一层找不到，调用 findClass 方法（每个类加载器自己扩展）来加载
                    long t1 = System.nanoTime();
                    c = findClass(name);
    
                    // 记录耗时
                    sun.misc.PerfCounter.getParentDelegationTime().addTime(t1 - t0);
                    sun.misc.PerfCounter.getFindClassTime().addElapsedTimeFrom(t1);
                    sun.misc.PerfCounter.getFindClasses().increment();
                }
            }
            if (resolve) {
                resolveClass(c);
            }
            return c;
        }
    }

例如：

![image-20230327203838949](https://img-
blog.csdnimg.cn/img_convert/8847991a30d5248c39c72116c653741a.png)

1. sun.misc.Launcher$AppClassLoader //1 处， 开始查看已加载的类，结果没有
2. sun.misc.Launcher A p p C l a s s L o a d e r / / 2 处，委派上级 s u n . m i s c . L a u n c h e r AppClassLoader // 2
   处，委派上级sun.misc.Launcher AppClassLoader//2处，委派上级sun.misc.LauncherExtClassLoader.loadClass()
3. sun.misc.Launcher$ExtClassLoader // 1 处，查看已加载的类，结果没有
4. BootstrapClassLoader 是在 JAVA_HOME/jre/lib 下找 H 这个类，显然没
5. sun.misc.Launcher E x t C l a s s L o a d e r / / 4 处，调用自己的 f i n d C l a s s 方法，是在 J A V A H O M E / j r
   e / l i b / e x t 下找 H 这个类，显然没有，回到 s u n . m i s c . L a u n c h e r ExtClassLoader // 4 处，调用自己的
   findClass 方法，是在JAVA_HOME/jre/lib/ext 下找 H 这个类，显然没 有，回到 sun.misc.Launcher
   ExtClassLoader//4处，调用自己的findClass方法，是在JAVAH​OME/jre/lib/ext下找H这个类，显然没有，回到sun.misc.LauncherAppClassLoader的 //
   2 处
6. 继续执行到 sun.misc.Launcher$AppClassLoader // 4 处，调用它自己的 findClass 方法，在 classpath 下查找，找到了

![image-20230327205344294](https://img-
blog.csdnimg.cn/img_convert/816ff0ef3cecc918678534be6f5c0b9d.png)

#### 3.5.4 线程上下文类加载器

我们在使用 JDBC 时，都需要加载 Driver 驱动，不知道你注意到没有，不写

`Class.forName("com.mysql.jdbc.Driver")`

也是可以让 com.mysql.jdbc.Driver 正确加载的，你知道是怎么做的吗？

让我们追踪一下源码：

    public class DriverManager {
    	// 注册驱动的集合
    	private final static CopyOnWriteArrayList<DriverInfo> registeredDrivers
    		= new CopyOnWriteArrayList<>();
    	// 初始化驱动
    	static {
    		loadInitialDrivers();
    		println("JDBC DriverManager initialized");
    	}

先不看别的，看看 DriverManager 的类加载器：

`System.out.println(DriverManager.class.getClassLoader());`

打印 null，表示它的类加载器是 Bootstrap ClassLoader，会到 JAVA_HOME/jre/lib
下搜索类，但JAVA_HOME/jre/lib 下显然没有 mysql-connector-java-5.1.47.jar
包，这样问题来了，在DriverManager 的静态代码块中，怎么能正确加载com.mysql.jdbc.Driver 呢？

继续看 loadInitialDrivers() 方法：

    private static void loadInitialDrivers() {
        String drivers;
        try {
            drivers = AccessController.doPrivileged(new PrivilegedAction<String>() {
                public String run() {
                    return System.getProperty("jdbc.drivers");
                }
            });
        } catch (Exception ex) {
            drivers = null;
        }
        //  1）使用 ServiceLoader 机制加载驱动，即SPI
        AccessController.doPrivileged(new PrivilegedAction<Void>() {
            public Void run() {
                ServiceLoader<Driver> loadedDrivers = ServiceLoader.load(Driver.class);
                Iterator<Driver> driversIterator = loadedDrivers.iterator();
                try{
                    while(driversIterator.hasNext()) {
                        driversIterator.next();
                    }
                } catch(Throwable t) {
                // Do nothing
                }
                return null;
            }
        });
    
        println("DriverManager.initialize: jdbc.drivers = " + drivers);
       //2）使用 jdbc.drivers 定义的驱动名加载驱动
        if (drivers == null || drivers.equals("")) {
            return;
        }
        String[] driversList = drivers.split(":");
        println("number of Drivers:" + driversList.length);
        for (String aDriver : driversList) {
            try {
                println("DriverManager.Initialize: loading " + aDriver);
                // 这里的 ClassLoader.getSystemClassLoader() 就是应用程序类加载器
                Class.forName(aDriver, true,
                        ClassLoader.getSystemClassLoader());
            } catch (Exception ex) {
                println("DriverManager.Initialize: load failed: " + ex);
            }
        }
    }

先看 2）发现它最后是使用 Class.forName 完成类的加载和初始化，关联的是应用程序类加载器，因此  
可以顺利完成类加载  
再看 1）它就是大名鼎鼎的 Service Provider Interface （SPI）  
约定如下，在 jar 包的 META-INF/services 包下，以接口全限定名名为文件，文件内容是实现类名称

![image-20230327210701474](https://img-
blog.csdnimg.cn/img_convert/8c8dd238bbab7ee3e671ebe0667b7628.png)

这样就可以使用

![image-20230327210803395](https://img-
blog.csdnimg.cn/img_convert/ff71a6f7313e9484f5c4c205f7774f6d.png)

来得到实现类，体现的是【面向接口编程+解耦】的思想，在下面一些框架中都运用了此思想：

* JDBC
* Servlet 初始化器
* Spring 容器
* Dubbo（对 SPI 进行了扩展）

接着看 ServiceLoader.load 方法：

![image-20230327210921875](https://img-
blog.csdnimg.cn/img_convert/2f4ea434fc5358e76adcc1a581697e9d.png)

线程上下文类加载器是当前线程使用的类加载器，默认就是应用程序类加载器，它内部又是由Class.forName 调用了线程上下文类加载器完成类加载，具体代码在
ServiceLoader 的内部类LazyIterator 中：

    private S nextService() {
        if (!hasNextService())
            throw new NoSuchElementException();
        String cn = nextName;
        nextName = null;
        Class<?> c = null;
        try {
            c = Class.forName(cn, false, loader);
        } catch (ClassNotFoundException x) {
            fail(service,
                 "Provider " + cn + " not found");
        }
        if (!service.isAssignableFrom(c)) {
            fail(service,
                 "Provider " + cn  + " not a subtype");
        }
        try {
            S p = service.cast(c.newInstance());
            providers.put(cn, p);
            return p;
        } catch (Throwable x) {
            fail(service,
                 "Provider " + cn + " could not be instantiated",
                 x);
        }
        throw new Error();          // This cannot happen
    }

#### 3.5.5 自定义类加载器

问问自己，什么时候需要自定义类加载器  
1）想加载非 classpath 随意路径中的类文件  
2）都是通过接口来使用实现，希望解耦时，常用在框架设计  
3）这些类希望予以隔离，不同应用的同名类都可以加载，不冲突，常见于 tomcat 容器  
步骤：

1. 继承 ClassLoader 父类
2. 要遵从双亲委派机制，重写 findClass 方法
   * 注意不是重写 loadClass 方法，否则不会走双亲委派机制
3. 读取类文件的字节码
4. 调用父类的 defineClass 方法来加载类
5. 使用者调用该类加载器的 loadClass 方法

示例：

* 准备好两个类文件放入 E:\myclasspath，它实现了 java.util.Map 接口，可以先反编译看一下：

![image-20230327211855052](https://img-
blog.csdnimg.cn/img_convert/a07a643009911dbeb768c57b822d92e9.png)

![image-20230327212059396](https://img-
blog.csdnimg.cn/img_convert/9ef69d9d1c5170b7e3727d4598201fa6.png)

    public class Load7 {
        public static void main(String[] args) throws Exception {
            MyClassLoader classLoader = new MyClassLoader();
            Class<?> c1 = classLoader.loadClass("MapImpl1");
            Class<?> c2 = classLoader.loadClass("MapImpl1");
            System.out.println(c1 == c2);
    
            MyClassLoader classLoader2 = new MyClassLoader();
            Class<?> c3 = classLoader2.loadClass("MapImpl1");
            System.out.println(c1 == c3);
    
            c1.newInstance();
        }
    }
    
    class MyClassLoader extends ClassLoader {
    
        @Override // name 就是类名称
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            String path = "e:\\myclasspath\\" + name + ".class";
    
            try {
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                Files.copy(Paths.get(path), os);
    
                // 得到字节数组
                byte[] bytes = os.toByteArray();
    
                // byte[] -> *.class
                return defineClass(name, bytes, 0, bytes.length);
    
            } catch (IOException e) {
                e.printStackTrace();
                throw new ClassNotFoundException("类文件未找到", e);
            }
        }
    }

### 3.6 运行期优化

#### 3.6.1 即时编译

**分层编译（TieredCompilation）**

先来个例子:

    public class JIT1 {
        public static void main(String[] args) {
            for (int i = 0; i < 200; i++) {
                long start = System.nanoTime();
                for (int j = 0; j < 1000; j++) {
                    new Object();
                }
                long end = System.nanoTime();
                System.out.printf("%d\t%d\n",i,(end - start));
            }
        }
    }
    
    
    
    0	41900
    1	159700
    2	43200
    .........
    195	400
    196	400
    197	400
    198	400
    199	300

原因是什么呢？

JVM 将执行状态分成了 5 个层次：

* 0 层，解释执行（Interpreter）
* 1 层，使用 C1 即时编译器编译执行（不带 profifiling）
* 2 层，使用 C1 即时编译器编译执行（带基本的 profifiling）
* 3 层，使用 C1 即时编译器编译执行（带完全的 profifiling）
* 4 层，使用 C2 即时编译器编译执行

> profiling 是指在运行过程中收集一些程序执行状态的数据，例如【方法的调用次数】，【循环的  
> 回边次数】等

即时编译器（JIT）与解释器的区别:

* 解释器是将字节码解释为机器码，下次即使遇到相同的字节码，仍会执行重复的解释
* JIT 是将一些字节码编译为机器码，并存入 Code Cache，下次遇到相同的代码，直接执行，无需再编译
* 解释器是将字节码解释为针对所有平台都通用的机器码
* JIT 会根据平台类型，生成平台特定的机器码

对于占据大部分的不常用的代码，我们无需耗费时间将其编译成机器码，而是采取解释执行的方式运行；另一方面，对于仅占据小部分的热点代码，我们则可以将其编译成机器码，以达到理想的运行速度。
执行效率上简单比较一下 Interpreter < C1 < C2，总的目标是发现热点代码（hotspot名称的由来），优化之

刚才的一种优化手段称之为【逃逸分析】，发现新建的对象是否逃逸。可以使用 -XX:-DoEscapeAnalysis 关闭逃逸分析，再运行刚才的示例观察结果

参考资料：https://docs.oracle.com/en/java/javase/12/vm/java-hotspot-virtual-
machineperformance-enhancements.html#GUID-D2E3DC58-D18B-4A6C-8167-4A1DFB4888E4

**方法内联 （Inlining）**

![image-20230327214550574](https://img-
blog.csdnimg.cn/img_convert/d7891a88fa2d57694a78cf9faa34b6ac.png)

如果发现 square 是热点方法，并且长度不太长时，会进行内联，所谓的内联就是把方法内代码拷贝、粘贴到调用者的位置：

![image-20230327214711679](https://img-
blog.csdnimg.cn/img_convert/d05c96aad9edbc643a53f47ecf7d3bd9.png)

还能够进行常量折叠（constant folding）的优化

`System.out.println(81);`

实验：

![image-20230327214933949](https://img-
blog.csdnimg.cn/img_convert/c430760805ce213b61ac61203d5d6ce7.png)

**字段优化**

JMH 基准测试请参考：http://openjdk.java.net/projects/code-tools/jmh/

创建 maven 工程，添加依赖如下:

![image-20230327215541348](https://img-
blog.csdnimg.cn/img_convert/a8f27313d00a421777b0bfb59266df47.png)

编写基准测试代码：

    @Warmup(iterations = 2, time = 1)
    @Measurement(iterations = 5, time = 1)
    @State(Scope.Benchmark)
    public class Benchmark1 {
    	int[] elements = randomInts(1_000);
    	private static int[] randomInts(int size) {
    		Random random = ThreadLocalRandom.current();
    		int[] values = new int[size];
    		for (int i = 0; i < size; i++) {
    			values[i] = random.nextInt();
    		}
    		return values;
    	}
    	@Benchmark
    	public void test1() {
    		for (int i = 0; i < elements.length; i++) {
    			doSum(elements[i]);
    		}
    	}
    	@Benchmark
    	public void test2() {
    		int[] local = this.elements;
    		for (int i = 0; i < local.length; i++) {
    			doSum(local[i]);
    		}
    	}
    	@Benchmark
    	public void test3() {
    		for (int element : elements) {
    			doSum(element);
    		}
    	}
    	static int sum = 0;
    	@CompilerControl(CompilerControl.Mode.INLINE)
    	static void doSum(int x) {
    		sum += x;
    	}
    	public static void main(String[] args) throws RunnerException {
    		Options opt = new OptionsBuilder()
                			.include(Benchmark1.class.getSimpleName())
                			.forks(1)
                			.build();
    		new Runner(opt).run();
        }
    }

首先启用 doSum 的方法内联，测试结果如下（每秒吞吐量，分数越高的更好）：

![image-20230327220218664](https://img-
blog.csdnimg.cn/img_convert/87c80eb7613bd2cfc63fc1faaf9e3472.png)

接下来禁用 doSum 方法内联

![image-20230327220347450](https://img-
blog.csdnimg.cn/img_convert/2daa988b92b08910c31cf939acf6a23c.png)

测试结果如下：

![image-20230327220411931](https://img-
blog.csdnimg.cn/img_convert/780c9b7cbdcb930b2a1e83da98a5ceac.png)

分析：  
在刚才的示例中，doSum 方法是否内联会影响 elements 成员变量读取的优化：  
如果 doSum 方法内联了，刚才的 test1 方法会被优化成下面的样子（伪代码）：

![image-20230327220527299](https://img-
blog.csdnimg.cn/img_convert/b0dff044678769230c0cd7793d736614.png)

可以节省 1999 次 Field 读取操作  
但如果 doSum 方法没有内联，则不会进行上面的优化

#### 3.6.2 反射优化

    public class Reflect1 {
        public static void foo() {
            System.out.println("foo...");
        }
    
        public static void main(String[] args) throws Exception {
            Method foo = Reflect1.class.getMethod("foo");
            for (int i = 0; i <= 16; i++) {
                System.out.printf("%d\t", i);
                foo.invoke(null);
            }
            System.in.read();
        }
    }

foo.invoke 前面 0 ~ 15 次调用使用的是 MethodAccessor 的 NativeMethodAccessorImpl 实现:

    class NativeMethodAccessorImpl extends MethodAccessorImpl {
        private final Method method;
        private DelegatingMethodAccessorImpl parent;
        private int numInvocations;//记录调用次数
        NativeMethodAccessorImpl(Method var1) {
            this.method = var1;
        }
        public Object invoke(Object var1, Object[] var2) throws IllegalArgumentException, InvocationTargetException {
            //超过15=ReflectionFactory.inflationThreshold()时进入if语句
            if (++this.numInvocations > ReflectionFactory.inflationThreshold() && !ReflectUtil.isVMAnonymousClass(this.method.getDeclaringClass())) {
                MethodAccessorImpl var3 = (MethodAccessorImpl)(new MethodAccessorGenerator()).generateMethod(this.method.getDeclaringClass(), this.method.getName(), this.method.getParameterTypes(), this.method.getReturnType(), this.method.getExceptionTypes(), this.method.getModifiers());
                this.parent.setDelegate(var3);
            }
            return invoke0(this.method, var1, var2);
        }
        void setParent(DelegatingMethodAccessorImpl var1) {
            this.parent = var1;
        }
        private static native Object invoke0(Method var0, Object var1, Object[] var2);
    }

当调用到第 16 次（从0开始算）时，会采用运行时生成的类代替掉最初的实现，可以通过 debug 得到  
类名为 sun.reflect.GeneratedMethodAccessor1

可以使用阿里的 arthas 工具：

![image-20230327231358569](https://img-
blog.csdnimg.cn/img_convert/3305d3bc852949a43b735c0ce22f92b8.png)

再输入【jad + 类名】来进行反编译

    [arthas@7508]$ jad sun.reflect.GeneratedMethodAccessor1
    ClassLoader:
    +-sun.reflect.DelegatingClassLoader@7ea987ac
      +-sun.misc.Launcher$AppClassLoader@18b4aac2
        +-sun.misc.Launcher$ExtClassLoader@7f31245a
    Location:
    /*
     * Decompiled with CFR.
     *
     * Could not load the following classes:
     *  com.exp.test03.Reflect1
     */
    package sun.reflect;
    
    import com.exp.test03.Reflect1;
    import java.lang.reflect.InvocationTargetException;
    import sun.reflect.MethodAccessorImpl;
    
    public class GeneratedMethodAccessor1
    extends MethodAccessorImpl {
        /*
         * Loose catch block
         */
        public Object invoke(Object object, Object[] objectArray) throws InvocationTargetException {
            // 比较奇葩的做法，如果有参数，那么抛非法参数异常
            block4: {
                if (objectArray == null || objectArray.length == 0) break block4;
                throw new IllegalArgumentException();
            }
            try {
                // 可以看到，已经是直接调用了
                Reflect1.foo();
                // 因为没有返回值
                return null;
            }
            catch (Throwable throwable) {
                throw new InvocationTargetException(throwable);
            }
            catch (ClassCastException | NullPointerException runtimeException) {
                throw new IllegalArgumentException(super.toString());
            }
        }
    }

> 注意  
> 通过查看 ReflectionFactory 源码可
>
>   * sun.reflect.noInflation 可以用来禁用膨胀（直接生成 GeneratedMethodAccessor1，但首  
      > 次生成比较耗时，如果仅反射调用一次，不划算）
>   * sun.reflect.inflationThreshold 可以修改膨胀阈值
>

## 4.内存模型

### 4.1 **java** 内存模型

* 很多人将【java 内存结构】与【java 内存模型】傻傻分不清，【java 内存模型】是 Java MemoryModel（JMM）的意思。
* 简单的说，JMM 定义了一套在多线程读写共享数据时（成员变量、数组）时，对数据的可见性、有序性、和原子性的规则和保障

#### 4.1.1 原子性

* **原子性的操作是不可被中断的一个或一系列操作。**
* 个人理解，严格的原子性的操作，其他线程获取操作的变量时，只能获取操作前的变量值和操作后的变量值，不能获取到操作过程中的中间值，在操作过程中其他操作需要获取变量值，需要进入阻塞状态等待操作结束。
* 问题提出:两个线程对初始值为 0 的静态变量一个做自增，一个做自减，各做 5000 次，结果是 0 吗？

#### 4.1.2 问题分析

以上的结果可能是正数、负数、零。为什么呢？因为 Java 中对静态变量的自增，自减并不是原子操作。  
例如对于 i++ 而言（i 为静态变量），实际会产生如下的 JVM 字节码指令：

![image-20230327233314192](https://img-
blog.csdnimg.cn/img_convert/57547421b1b395b5e63e58b0b6018edf.png)

而对应 i-- 也是类似：

![image-20230327233354675](https://img-
blog.csdnimg.cn/img_convert/379992e3f01470dcd17bed84473f5fd9.png)

而Java的内存模型如下，完成静态变量的自增，自减需要在主内存和线程内存中进行数据交换：

![image-20230327233634479](https://img-
blog.csdnimg.cn/img_convert/3cdc1b391f1e1dc62bcaa7e7143cfec6.png)

如果是单线程以上 8 行代码是顺序执行（不会交错）没有问题：

![image-20230327233710432](https://img-
blog.csdnimg.cn/img_convert/8904d378a8cb51820428605e88cee749.png)

但多线程下这 8 行代码可能交错运行（为什么会交错？思考一下）：

出现负数的情况：

![image-20230327233747417](https://img-
blog.csdnimg.cn/img_convert/548316999c05fb72797738b673c1aa98.png)

出现正数的情况：

![image-20230327233812792](https://img-
blog.csdnimg.cn/img_convert/fc842e977f5668117b71987f3ab1d226.png)

#### 4.1.3 解决方法

**synchronized(同步关键字)**

同步代码块语法

    synchronized( 对象 ) {
    	要作为原子操作代码
    }

用 synchronized 解决并发问题：

![image-20230327234445600](https://img-
blog.csdnimg.cn/img_convert/7ade7e65c41eb3e78b7613ebd95126eb.png)

* 如何理解呢：你可以把 obj 想象成一个房间，线程 t1，t2 想象成两个人。
* 当线程 t1 执行到 synchronized(obj) 时就好比 t1 进入了这个房间，并反手锁住了门，在门内执行count++ 代码。
* 这时候如果 t2 也运行到了 synchronized(obj) 时，它发现门被锁住了，只能在门外等待。
* 当 t1 执行完 synchronized{} 块内的代码，这时候才会解开门上的锁，从 obj 房间出来。t2 线程这时才可以进入 obj 房间，反锁住门，执行它的
  count-- 代码。

> 注意：上例中 t1 和 t2 线程必须用 synchronized 锁住同一个 obj 对象，如果 t1 锁住的是 m1 对  
> 象，t2 锁住的是 m2 对象，就好比两个人分别进入了两个不同的房间，没法起到同步的效果。

### 4.2 可见性

#### 4.2.1 退不出的循环

先来看一个现象，main 线程对 run 变量的修改对于 t 线程不可见，导致了 t 线程无法停止：

![image-20230327234748608](https://img-
blog.csdnimg.cn/img_convert/bbfe5e3da3610de418cd29e598016b92.png)

为什么呢？分析一下：

1. 初始状态， t 线程刚开始从主内存读取了 run 的值到工作内存。

![image-20230327234902164](https://img-
blog.csdnimg.cn/img_convert/76949336be898a4e6bfb547ae3335b3e.png)

2. 因为 t 线程要频繁从主内存中读取 run 的值，JIT 编译器会将 run 的值缓存至自己工作内存中的高速缓存中，减少对主存中 run
   的访问，提高效率

![image-20230327235046185](https://img-
blog.csdnimg.cn/img_convert/51ee0046806cf2a3828a57ad55dc2ab0.png)

3. 1 秒之后，main 线程修改了 run 的值，并同步至主存，而 t 是从自己工作内存中的高速缓存中读取这个变量的值，结果永远是旧值

![image-20230327235130049](https://img-
blog.csdnimg.cn/img_convert/5b3e487828c311cba2516ee65ebfac3e.png)

#### 4.2.2 解决方法

volatile（易变关键字）  
它可以用来修饰成员变量和静态成员变量，他可以避免线程从自己的工作缓存中查找变量的值，必须到主存中获取它的值，线程操作
volatile 变量都是直接操作主存

#### 4.2.3 可见性

前面例子体现的实际就是可见性，它保证的是在多个线程之间，一个线程对 volatile 变量的修改对另一个线程可见，
不能保证原子性，仅用在一个写线程，多个读线程的情况： 上例从字节码理解是这样的：

![image-20230327235410198](https://img-
blog.csdnimg.cn/img_convert/8b7114546b52693efd2b7caa4cb5e869.png)

比较一下之前我们将线程安全时举的例子：两个线程一个 i++ 一个 i-- ，只能保证看到最新值，不能解  
决指令交错

> 注意 synchronized
> 语句块既可以保证代码块的原子性，也同时保证代码块内变量的可见性。但缺点是synchronized是属于重量级操作，性能相对更低  
> 如果在前面示例的死循环中加入 System.out.println() 会发现即使不加 volatile 修饰符，线程 t 也  
> 能正确看到对 run 变量的修改了，想一想为什么

### 4.3 有序性

#### 4.3.1 诡异的结果

![](https://img-
blog.csdnimg.cn/img_convert/630747049e627f066bc3a2ec2db003a9.png)

I_Result 是一个对象，有一个属性 r1 用来保存结果，问，可能的结果有几种？  
分析：

* 情况1：线程1 先执行，这时 ready = false，所以进入 else 分支结果为 1
* 情况2：线程2 先执行 num = 2，但没来得及执行 ready = true，线程1 执行，还是进入 else 分支，结果为1
* 情况3：线程2 执行到 ready = true，线程1 执行，这回进入 if 分支，结果为 4（因为 num 已经执行过了）

结果还有可能是 0 ，这种情况下：线程2执行read =
true,切换到线程1，进入if分支相加为0，再切换到线程2执行num=2。这种现象叫做指令重排，是 JIT
编译器在运行时的一些优化，这个现象需要通过大量测试才能复现：借助 java 并发压测工具 jcstress
https://wiki.openjdk.java.net/display/CodeTools/jcstress

![image-20230328105302625](https://img-
blog.csdnimg.cn/img_convert/fc224f0e3ea26f12bc6147af7992d6e0.png)

创建 maven 项目，提供如下测试类

![image-20230328105349589](https://img-
blog.csdnimg.cn/img_convert/4618fc205316b5b7ea1e10747ae7f802.png)

执行

![image-20230328105451158](https://img-
blog.csdnimg.cn/img_convert/e5d5c2278efbc31aa7cd65f8c8eeebbb.png)

会输出我们感兴趣的结果，摘录其中一次结果：

![微信图片_20230328105955](https://img-
blog.csdnimg.cn/img_convert/b22002b46a20affaf2cf4be7f9f48b13.jpeg)

可以看到，出现结果为 0 的情况有 3712次，虽然次数相对很少，但毕竟是出现了。

#### 4.3.2 解决方法

volatile 修饰的变量，可以禁用指令重排：

![](https://img-
blog.csdnimg.cn/img_convert/6569d2b4107d7bb9d4bac84e34df76bd.png)

结果为：

![image-20230328110542087](https://img-
blog.csdnimg.cn/img_convert/06e78f3d2d5dbdfa9d20e886cefcb821.png)

#### 4.3.3 有序性理解

同一个线程内，JVM会在不影响正确性的前提下，可以调整语句的执行顺序，思考下面一段代码：

    static int i;
    static int j;
    //在某个线程内执行如下赋值操作
    i=....;//较为耗时的操作
    j=...;

可以看到，至于是先执行 i 还是 先执行 j ，对最终的结果不会产生影响。所以，上面代码真正执行时，既可以是

![image-20230328111152242](https://img-
blog.csdnimg.cn/img_convert/6eabdeb382578d21b6fbbc229e895840.png)

也可以是:

![image-20230328111208494](https://img-
blog.csdnimg.cn/img_convert/559eac0bbf020ad088a908fdec8e17d1.png)

这种特性称之为『指令重排』，多线程下『指令重排』会影响正确性，例如著名的 double-checked locking 模式实现单例

![image-20230328111327326](https://img-
blog.csdnimg.cn/img_convert/68dd0e5678c4e880d4cad36b8384d0a7.png)

以上的实现特点是：

* 懒惰实例化
* 首次使用 getInstance() 才使用 synchronized 加锁，后续使用时无需加锁

但在多线程环境下，上面的代码是有问题的， INSTANCE = new Singleton() 对应的字节码为：

![image-20230328111855490](https://img-
blog.csdnimg.cn/img_convert/44afe5eddb8b664f6cb6b5c3e770dd2b.png)

其中4，7两步的顺序不是固定的，也许jvm会优化为：先将引用地址赋值给INSTANCE变量后，再执行构造方法，如果两个线程t1,t2按如下时间序列执行：

![image-20230328112200906](https://img-
blog.csdnimg.cn/img_convert/122b60ad9b02e1a8127534fc502c89c8.png)

这时 t1 还未完全将构造方法执行完毕，如果在构造方法中要执行很多初始化操作，那么 t2 拿到的是将是一个未初始化完毕的单例。对
INSTANCE 使用
volatile 修饰即可，可以禁用指令重排，但要注意在 JDK 5 以上的版本的 volatile 才  
会真正有效。

#### 4.3.4 happens-before

happens-before 规定了哪些写操作对其它线程的读操作可见，它是可见性与有序性的一套规则总结，抛开以下 happens-before 规则，JMM
并不能保证一个线程对共享变量的写，对于其它线程对该共享变量的读可见

* 线程解锁 m 之前对变量的写，对于接下来对 m 加锁的其它线程对该变量的读可见

![image-20230328112737782](https://img-
blog.csdnimg.cn/img_convert/63227da2a99b2a1e49f46cae12f02bb4.png)

* 线程对 volatile 变量的写，对接下来其它线程对该变量的读可见

![image-20230328112819301](https://img-
blog.csdnimg.cn/img_convert/8bf63bd8ec70b2190b41e4c94cbc1e4a.png)

* 线程start前对变量的写，对该线程开始后对该变量的可见

![image-20230328113152000](https://img-
blog.csdnimg.cn/img_convert/61d1425c5c43ab26b9c6cdaeac2ed928.png)

* 线程结束前对变量的写，对其它线程得知它结束后的读可见（比如其它线程调用 t1.isAlive() 或t1.join()等待它结束）

![image-20230328112932026](https://img-
blog.csdnimg.cn/img_convert/5681db5965953d86ad4872cc33421c19.png)

* 线程 t1 打断 t2（interrupt）前对变量的写，对于其他线程得知 t2 被打断后对变量的读可见（通过t2.interrupted 或
  t2.isInterrupted）

![a89b11b5e07bd5cc9d881a4e796bc6f](https://img-
blog.csdnimg.cn/img_convert/e08e9ad5bfd9e87bd37d3effec4835a0.jpeg)

* 对变量默认值（0，false，null）的写，对其它线程对该变量的读可见

* 具有传递性，如果 x hb-> y 并且 y hb-> z 那么有 x hb-> z

### 4.4 CAS与原子类

#### 4.4.1 CAS

CAS 即 Compare and Swap ，它体现的一种乐观锁的思想，比如多个线程要对一个共享的整型变量执行 +1 操作：

![image-20230328113649463](https://img-
blog.csdnimg.cn/img_convert/f86d7f4dff7cf13e6af90ae3a5c07356.png)

获取共享变量时，为了保证该变量的可见性，需要使用 volatile 修饰。结合 CAS 和 volatile 可以实现无锁并发，适用于竞争不激烈、多核
CPU 的场景下。

* 因为没有使用 synchronized，所以线程不会陷入阻塞，这是效率提升的因素之一
* 但如果竞争激烈，可以想到重试必然频繁发生，反而效率会受影响

CAS 底层依赖于一个 Unsafe 类来直接调用操作系统底层的 CAS 指令，下面是直接使用 Unsafe 对象进行线程安全保护的一个例子

    public class TeatCAS {
        public static void main(String[] args) throws InterruptedException {
            DataContainer dc = new DataContainer();
            int count = 5;
            Thread t1 = new Thread(() -> {
                for (int i = 0; i < count; i++) {
                    dc.increase();
                }
            });
            t1.start();
            t1.join();
            System.out.println(dc.getData());
        }
    }
    
    class DataContainer {
        private volatile int data;
        static final Unsafe unsafe;
        static final long DATA_OFFSET;
    
        static {
            try {
                // Unsafe 对象不能直接调用，只能通过反射获得
                Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
                theUnsafe.setAccessible(true);
                unsafe = (Unsafe) theUnsafe.get(null);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new Error(e);
            }
            try {
                // data 属性在 DataContainer 对象中的偏移量，用于 Unsafe 直接访问该属性
                DATA_OFFSET =
                        unsafe.objectFieldOffset(DataContainer.class.getDeclaredField("data"));
            } catch (NoSuchFieldException e) {
                throw new Error(e);
            }
        }
    
        public void increase() {
            int oldValue;
            while (true) {
                // 获取共享变量旧值，可以在这一行加入断点，修改 data 调试来加深理解
                oldValue = data;
                // cas 尝试修改 data 为 旧值 + 1，如果期间旧值被别的线程改了，返回 false
                if (unsafe.compareAndSwapInt(this, DATA_OFFSET, oldValue, oldValue +
                        1)) {
                    return;
                }
            }
        }
    
        public void decrease() {
            int oldValue;
            while (true) {
                oldValue = data;
                if (unsafe.compareAndSwapInt(this, DATA_OFFSET, oldValue, oldValue -
                        1)) {
                    return;
                }
            }
        }
    
        public int getData() {
            return data;
        }
    }

#### 4.4.2 乐观锁与悲观锁

* CAS 是基于乐观锁的思想：最乐观的估计，不怕别的线程来修改共享变量，就算改了也没关系，我吃亏点再重试呗。
* synchronized 是基于悲观锁的思想：最悲观的估计，得防着其它线程来修改共享变量，我上了锁你们都别想改，我改完了解开锁，你们才有机会。

#### 4.4.3 原子操作类

juc（java.util.concurrent）中提供了原子操作类，可以提供线程安全的操作，例如：AtomicInteger、AtomicBoolean等，它们底层就是采用
CAS 技术 + volatile 来实现的。可以使用 AtomicInteger 改写之前的例子：

![7ad53cfcff370f23b097032f11f0a21](https://img-
blog.csdnimg.cn/img_convert/772fa6ee32abf791fd88f4a1771ee266.jpeg)

### 4.5 synchronized优化

> Java HotSpot 虚拟机中，每个对象都有对象头（包括 class 指针和 Mark Word）。Mark Word 平时存储这个对象的 哈希码
> 、 分代年龄 ，当加锁时，这些信息就根据情况被替换为 标记位 、 线程锁记录指针 、 重量级锁指针 、 线程ID 等内容

#### 4.5.1 轻量级锁

如果一个对象虽然有多线程访问，但多线程访问的时间是错开的（也就是没有竞争），那么可以使用轻量级锁来优化。这就好比：学生（线程
A）用课本占座，上了半节课，出门了（CPU时间到），回来一看，发现课本没变，说明没  
有竞争，继续上他的课。 如果这期间有其它学生（线程 B）来了，会告知（线程A）有并发访问，线程A
随即升级为重量级锁，进入重量级锁的流程。而重量级锁就不是那么用课本占座那么简单了，可以想象线程 A
走之前，把座位用一个铁栅栏围起来假设有两个方法同步块，利用同一个对象加锁。

![image-20230328120142478](https://img-
blog.csdnimg.cn/img_convert/dc8b2579bb939d1c41eb840f7dcc32bb.png)

每个线程都的栈帧都会包含一个锁记录的结构，内部可以存储锁定对象的 Mark Word

![image-20230328120607393](https://img-
blog.csdnimg.cn/img_convert/eb2e90e9ef6b1bbc8a5573084f89e04f.png)

#### 4.5.2 锁膨胀

如果在尝试加轻量级锁的过程中，CAS 操作无法成功，这时一种情况就是有其它线程为此对象加上了轻量级锁（有竞争），这时需要进行锁膨胀，将轻量级锁变为重量级锁

![微信图片_20230328123314](https://img-
blog.csdnimg.cn/img_convert/35e4381a6ce2520d155a342191990e66.jpeg)

![image-20230328123407807](https://img-
blog.csdnimg.cn/img_convert/6aee81c051769b02a74e6268369ee94d.png)

#### 4.5.3 重量锁

* 重量级锁竞争的时候，还可以使用自旋来进行优化，如果当前线程自旋成功（即这时候持锁线程已经退出了同步块，释放了锁），这时当前线程就可以避免阻塞。
* 在 Java 6 之后自旋锁是自适应的，比如对象刚刚的一次自旋操作成功过，那么认为这次自旋成功的可能性会高，就多自旋几次；反之，就少自旋甚至不自旋，总之，比较智能
    * 自旋会占用CPU时间，单核CPU自旋就是浪费，多核CPU自旋才能发挥优势
    * 好比等红路灯汽车是否熄火，不熄火相当于自旋（等待时间短划算），熄灭了相当于阻塞（等待时间长了不划算）
    * Java 7 之后不能控制是否开启自旋功能

自旋重试成功的情况

![image-20230328123837873](https://img-
blog.csdnimg.cn/img_convert/15d25cbc30864ed82c4002bc182f6924.png)

自旋重试失败的情况

![image-20230328124417855](https://img-
blog.csdnimg.cn/img_convert/c907989f5f91dddc3d66ae6c4b0c9a40.png)

#### 4.5.4 偏向锁

是自己的就表示没有竞争，不用重新 CAS.

* 撤销偏向需要将持锁线程升级为轻量级锁，这个过程中所有线程需要暂停（STW）
* 访问对象的 hashCode 也会撤销偏向锁
* 如果对象虽然被多个线程访问，但没有竞争，这时偏向了线程 T1 的对象仍有机会重新偏向 T2，重偏向会重置对象的 Thread ID
* 撤销偏向和重偏向都是批量进行的，以类为单位
* 如果撤销偏向到达某个阈值，整个类的所有对象都会变为不可偏向的
* 可以主动使用 -XX:-UseBiasedLocking 禁用偏向锁

可以参考这篇论文：https://www.oracle.com/technetwork/java/biasedlocking-
oopsla2006-wp-149958.pdf  
假设有两个方法同步块，利用同一个对象加锁

![image-20230328124656991](https://img-
blog.csdnimg.cn/img_convert/5f2c2a551487a0adeda822bc43249022.png)

![image-20230328124719565](https://img-
blog.csdnimg.cn/img_convert/83ccbf085b3dfcc40dd6d9312ddddbca.png)

#### 4.5.5 其它优化

1. **减少上锁时间**  
   同步代码块中尽量短

2. **减少锁的粒度**

将一个锁拆分为多个锁提高并发度，例如：

    * ConcurrentHashMap
    * LongAdder分为base和cell,没有并发争用的时候或者是cell数组正在初始化的时候，会使用CAS来累加到bese,有并发争用，会初始化cell数组，数组多有个cell,就允许有多少线程并发修改，最后将数组中每个cell累加，在加上base就是最终的值。
    * LinkedBlockingQueue 入队和出队使用不同的锁，相对于LinkedBlockingArray只有一个锁效率要高

3. **锁粗化**

多次循环进入同步块不如同步块内多次循环 另外 JVM 可能会做如下优化，把多次 append
的加锁操作粗化为一次（因为都是对同一个对象加锁，没必要重入多次）

![image-20230328125236232](https://img-
blog.csdnimg.cn/img_convert/a5adffbec4fd1772b371202998ab001d.png)

4. **锁消除**

JVM 会进行代码的逃逸分析，例如某个加锁对象是方法内局部变量，不会被其它线程所访问到，这时候就会被即时编译器忽略掉所有同步操作。

5. **读写分离**

CopyOnWriteArrayList

ConyOnWriteSet  
参考：  
https://wiki.openjdk.java.net/display/HotSpot/Synchronization  
https://www.infoq.cn/article/java-se-16-synchronized  
https://www.jianshu.com/p/9932047a89be  
https://www.cnblogs.com/sheeva/p/6366782.html  
https://stackoverflow.com/questions/46312817/does-java-ever-rebias-an-
individual-lock

