#### JVM

![在这里插入图片描述](https://img-blog.csdnimg.cn/cf08c495437d43fdb46f07639b1103b6.png)

## 一、内存结构

**5)、StringTable**

```java
String s1 = "a";
String s2 = "b";
String s3 = "a" + "b";
String s4 = s1 + s2;
String s5 = "ab";
String s6 = s4.intern();
// 问
System.out.println(s3 == s4);
System.out.println(s3 == s5);
System.out.println(s3 == s6);
String x2 = new String("c") + new String("d");
String x1 = "cd";
x2.intern();
// 问，如果调换了【最后两行代码】的位置呢，如果是jdk1.6呢
System.out.println(x1 == x2);
```

**分析：**

1、a、b、ab 都是常量，直接加到常量池里  
![在这里插入图片描述](https://img-
blog.csdnimg.cn/f0527c43d7024d9fbc407f174fe0d339.png)2、变量s1+变量s2 拼接相当于
StringBuilder

![在这里插入图片描述](https://img-blog.csdnimg.cn/5672e89bbb914006b2fc9e43785252a6.png)  
而StringBuilder的toString方法会创建一个新String，也就是 new String(“ab”)，说明是存在堆里面的  
![在这里插入图片描述](https://img-blog.csdnimg.cn/3cb530804b5940f3864997390019ca02.png)  
说明 以下判断是 false,因为s3对象是存在常量池的，s4 new了一个对象存在堆里的，位置不一样，是两个对象

![在这里插入图片描述](https://img-blog.csdnimg.cn/3f5ade6dc6e74e659ca8020418271d36.png)

3、常量 a + 常量b 拼接，发现延用了常量池已有的ab字符串对象  
![在这里插入图片描述](https://img-blog.csdnimg.cn/f92a055d8a664cd3a1850e398c2981ba.png)  
说明常量拼接结果为true![在这里插入图片描述](https://img-
blog.csdnimg.cn/22e509398bdb43f797013615ef0a54b9.png)  
4、字符串延迟加载

![在这里插入图片描述](https://img-blog.csdnimg.cn/dcbf751ab4ae45ceae640eb4c35c57b0.png)  
**6）、StringTable特性**

* 常量池中的字符串仅是符号，第一次用到时才变为对象
* 利用串池的机制，来避免重复创建字符串对象
* 字符串变量拼接的原理是 StringBuilder （1.8）
* 字符串常量拼接的原理是编译期优化
* 可以使用 intern 方法，主动将串池中还没有的字符串对象放入串池,1.8 将这个字符串对象尝试放入串池，如果有则并不会放入，如果没有则放入串池，
  会把串池中的对象返回

![在这里插入图片描述](https://img-blog.csdnimg.cn/dd92ff38a2f245858b13edc14cb68dd5.png)  
**7）、StringTable 的位置**

jdk1.6 StringTable 位置是在永久代中，1.8 StringTable 位置是在堆中。

**8）、StringTable垃圾回收**

-Xmx10m 指定堆内存大小  
-XX:+PrintStringTableStatistics 打印字符串常量池信息  
-XX:+PrintGCDetails  
-verbose:gc 打印 gc 的次数，耗费时间等信息

![在这里插入图片描述](https://img-
blog.csdnimg.cn/06e12a33acf94b18991ee9fa04489a0d.png)发现StringTable触发了垃圾回收

**9）、StringTable 性能调优**

* 调整 -XX:StringTableSize=桶个数，个数至少为1009
* 考虑将字符串对象是否入池（使用intern）

## 二、垃圾回收

### 1、如何判断对象可以回收

#### 1.1、引用计数法

当一个对象被引用时，就当引用对象的值加一，当值为 0 时，就表示该对象不被引用，可以被垃圾收集器回收。  
这个引用计数法听起来不错，但是有一个弊端，如下图所示，循环引用时，两个对象的计数都为1，导致两个对象都无法被释放。  
![在这里插入图片描述](https://img-blog.csdnimg.cn/1ecc3a9760be45fc8b764cd4a344a974.png)

#### 1.2、 可达性分析算法

* Java 虚拟机中的垃圾回收器采用可达性分析来探索所有存活的对象
* 扫描堆中的对象，看是否能够沿着 GC Root（根）对象 为起点的引用链找到该对象，找不到，表示可以回收

演示：哪些对象可以作为根对象

![在这里插入图片描述](https://img-blog.csdnimg.cn/4aa73ecc43174599a4e977c8b0c37dfc.png)  
步骤1： 使用jps看下进程id

![在这里插入图片描述](https://img-blog.csdnimg.cn/e7552afc06bd43ba8d97587e562e25df.png)  
步骤2：使用 jmap -dump:format=b,live,file=1.bin 21384 命令转储文件 断点 到list=null
再次转储为2.bin

dump：转储文件  
format=b：二进制文件，live抓存活的  
file：文件名  
21384：进程的id

![在这里插入图片描述](https://img-blog.csdnimg.cn/056312db5a1c4133824fba97ae44e78a.png)  
步骤3：使用Eclipse Memory Analyzer工具 对 1.bin 文件进行分析。

![在这里插入图片描述](https://img-blog.csdnimg.cn/c83c5306c09544238555c5e8af8e41a4.png)

![在这里插入图片描述](https://img-blog.csdnimg.cn/e447392be91746829ab6d46fc0a7df6e.png)

gc roots分析的1.bin，找到了 ArrayList 对象，然后将 list
置为null，再次转储,也就是2.bin文件，发现arrayList没有了，说明 list 对象被回收。

#### 1.3、四种引用

![在这里插入图片描述](https://img-blog.csdnimg.cn/37ecfa2ddf1e41d7ad33cc80acfe20c9.png)  
![在这里插入图片描述](https://img-blog.csdnimg.cn/a9608436011a41ffa696ebf48ed4b486.png)

##### 1、强引用

**特点：** 只要不为null，GC时，永远不会被回收

强引用有引用变量指向时永远不会被垃圾回收，JVM宁愿抛出OutOfMemory错误也不会回收这种对象。可以将引用赋值为null，这样一来的话，JVM在合适的时间就会回收该对象。

比如：

​    
Object object = new Object();
String str = "hello"

强引用也是导致内存泄露的主要原因

##### 2、软引用（SoftReference）

**特点：** 内存不足时（自动触发GC），会被回收

* 仅有软引用引用该对象时，在垃圾回收后，内存仍不足时会再次出发垃圾回收，回收软引用  
  对象

* 可以配合引用队列来释放软引用自身

软引用可用来实现内存敏感的高速缓存,比如网页缓存、图片缓存等。使用软引用能防止内存泄露，增强程序的健壮性。

示例：  
设置参数：-Xms10m -Xmx10m -XX:+PrintGCDetails

演示1：强引用，发生内存溢出，方便与软引用比较  
![在这里插入图片描述](https://img-blog.csdnimg.cn/9e3565d2dcd34904b9830d15d75e5b9b.png)  
软引用示例：说明内存不够时，会回收软引用  
![在这里插入图片描述](https://img-
blog.csdnimg.cn/151d981ff774421dbd4134fdfa599362.png)![在这里插入图片描述](https://img-
blog.csdnimg.cn/28992556e7274361a654cba35aa68a45.png)  
演示2：软引用配合引用队列，来释放软引用自身

![在这里插入图片描述](https://img-
blog.csdnimg.cn/b1fdc5cd060b45ff838128deaf919331.png)
循环结束，只剩最后一个，四个null被移除掉了![在这里插入图片描述](https://img-
blog.csdnimg.cn/85dbc6bd833043b8a07348c2fcac71ea.png)

##### 3、弱应用（WeakReference）

**特点：** 无论内存是否充足，只要进行GC，都会被回收

* 仅有弱引用引用该对象时，在垃圾回收时，无论内存是否充足，都会回收弱引用对象
* 可以配合引用队列来释放弱引用自身

演示：示例与软引用类似  
![在这里插入图片描述](https://img-
blog.csdnimg.cn/6b3774327b1044c6b17318cfbe833708.png)![在这里插入图片描述](https://img-
blog.csdnimg.cn/b880c7ba730f4d069f48e2f0465f1eb5.png)

##### 4、虚引用（PhantomReference）

**特点：** 如同虚设，和没有引用没什么区别

* 必须配合引用队列使用，主要配合 ByteBuffer 使用，被引用对象回收时，会将虚引用入队，由 Reference Handler 线程调用虚引用相关方法释放直接内存

虚引用和前面的软引用、弱引用不同，它并不影响对象的生命周期。在java中用java.lang.ref.PhantomReference类表示。如果一个对象与虚引用关联，则跟没有引用与之关联一样，在任何时候都可能被垃圾回收器回收。

![在这里插入图片描述](https://img-blog.csdnimg.cn/75814dc2c586420aa2dc2bf538c637a6.png)

##### 5、终结器引用（FinalReference）

无需手动编码，但其内部配合引用队列使用，在垃圾回收时，终结器引用入队（被引用对象  
暂时没有被回收），再由 Finalizer 线程通过终结器引用找到被引用对象并调用它的 finalize  
方法，第二次 GC 时才能回收被引用对象

### 2、回收算法

#### 1、标记清除法

标记清除法是先找到内存里的存活对象并对其进行标记，然后统一把未标记的对象统一的清理。

**特点：**

* 速度较快。适合存活对象多，需要回收的对象少的场景。
*
会造成内存碎片。就像下图清除后的内存区域一样，清除后内存会有很多不连续的空间，这也就是我们常说的空间碎片，这样的空间碎片太多不仅不利于我们下次分配，而且当有大对象创建的时候，我们明明有可以容纳的总空间，但是空间都不是连续的造成对象无法分配，从而不得不提前触发GC。

![在这里插入图片描述](https://img-blog.csdnimg.cn/ec5e0c45b32346669bc3dfb36753e87f.png)

#### 2、标记整理法

标记整理法分为标记和整理两个阶段：  
1、标记阶段会先把存活的对象和可回收的对象标记出来；  
2、标记完再对内存对象进行整理

标记复制法算是完美的补齐了标记清除法的短板，既解决了空间碎片的问题，又适合使用在大部分对象都是可回收的场景。
不过标记复制法也有不完美的地方，一方面是需要空闲出一块内存空间用来腾挪对象，另外一方面它在存活对象比较多的场景也不是太适合，而存活对象多的场景通常适合使用标记清除法，但是标记清除法会产生空间碎片又是一个无法忍受的问题。

![在这里插入图片描述](https://img-blog.csdnimg.cn/b0fecdf8dfd643f18e1bf27e057eb897.png)

#### 3、复制算法

在GC开始的时候，对象只会存在于Eden区和名为“From”的Survivor区，Survivor区“To”是空的。紧接着进行GC，Eden区中所有存活的对象都会被复制到“To”，而在“From”区中，仍存活的对象会根据他们的年龄值来决定去向。年龄达到一定值(
年龄阈值，可以通过-
XX:MaxTenuringThreshold来设置)
的对象会被移动到年老代中，没有达到阈值的对象会被复制到“To”区域。经过这次GC后，Eden区和From区已经被清空。这个时候，“From”和“To”会交换他们的角色，也就是新的“To”就是上次GC前的“From”，新的“From”就是上次GC前的“To”。不管怎样，都会保证名为To的Survivor区域是空的。

**特点：**

* 不会有内存碎片
* 需要占用双倍内存空间

![在这里插入图片描述](https://img-blog.csdnimg.cn/e843f0988d4a419b98bc5723bbf7de50.png)  
![在这里插入图片描述](https://img-blog.csdnimg.cn/0d5b2323374c4eef8246b09bfd7628cd.png)

#### 3、分代垃圾回收

![在这里插入图片描述](https://img-
blog.csdnimg.cn/9c12cd0fd8ab4ac2a584077d953237b7.png)垃圾回收流程：

1. 对象首先分配在伊甸园区域
2. 新生代空间不足时，触发 minor gc，伊甸园和 from 存活的对象使用 复制算法 复制到 to 中，存活的对象年龄加 1并且交换 from to
3. minor gc 会引发 stop the world，暂停其它用户的线程，等垃圾回收结束，用户线程才恢复运行
4. 当对象寿命超过阈值时，会晋升至老年代，最大寿命是15（4bit）
5. 当老年代空间不足，会先尝试触发 minor gc，如果之后空间仍不足，那么触发 full gc，STW的时间更长，要是full GC之后还是不够，则报错
   OutOfMemeryError

##### 3.1、相关VM参数

 含义               | 参数                                                          
------------------|-------------------------------------------------------------  
 堆初始大小            | -Xms                                                        
 堆最大大小            | -Xmx 或 -XX:MaxHeapSize=size                                 
 新生代大小            | -Xmn 或 (-XX:NewSize=size + -XX:MaxNewSize=size )            
 幸存区比例（动态）        | -XX:InitialSurvivorRatio=ratio 和 -XX:+UseAdaptiveSizePolicy 
 幸存区比例            | -XX:SurvivorRatio=ratio                                     
 晋升阈值             | -XX:MaxTenuringThreshold=threshold                          
 晋升详情             | -XX:+PrintTenuringDistribution                              
 GC详情             | -XX:+PrintGCDetails -verbose:gc                             
 FullGC 前 MinorGC | -XX:+ScavengeBeforeFullGC                                   

##### 3.2、GC分析

设置参数 -Xms20m -Xmx20m -Xmn10m -XX:+UseSerialGC -XX:+PrintGCDetails -verbose:gc

没运行任何代码时，堆内存占用情况  
![在这里插入图片描述](https://img-blog.csdnimg.cn/0430feca4a9d46dd953a14a0218f94db.png)  
加了7M后，垃圾回收发生的变化  
![在这里插入图片描述](https://img-blog.csdnimg.cn/ad10da6e08c74b7a84f8a6ed27f15c0d.png)

### 3、垃圾回收器

![在这里插入图片描述](https://img-blog.csdnimg.cn/382ef40cfedf443888acedc6f43975c2.png)  
图中展示了7种作用于不同分代的收集器，如果两个收集器之间存在连线，则说明它们可以搭配使用。虚拟机所处的区域则表示它是属于新生代还是老年代收集器。

**新生代收集器：** Serial、ParNew、Parallel Scavenge

**老年代收集器：** CMS、Serial Old、Parallel Old

**整堆收集器：** G1

几个相关概念：

* 并行收集：指多条垃圾收集线程并行工作，但此时用户线程仍处于等待状态。

* 并发收集：指用户线程与垃圾收集线程同时工作（不一定是并行的可能会交替执行）。用户程序在继续运行，而垃圾收集程序运行在另一个CPU上。

* 吞吐量：即CPU用于运行用户代码的时间与CPU总消耗时间的比值（吞吐量 = 运行用户代码时间 / ( 运行用户代码时间 +
  垃圾收集时间 )）。例如：虚拟机共运行100分钟，垃圾收集器花掉1分钟，那么吞吐量就是99%

#### 3.1、Serial 收集器

Serial收集器是最基本的、发展历史最悠久的收集器。

**特点：**
单线程、简单高效（与其他收集器的单线程相比），对于限定单个CPU的环境来说，Serial收集器由于没有线程交互的开销，专心做垃圾收集自然可以获得最高的单线程收集效率。收集器进行垃圾回收时，必须暂停其他所有的工作线程，直到它结束（Stop
The World）。

**应用场景：** 适用于Client模式下的虚拟机。

Serial / Serial Old收集器运行示意图

![在这里插入图片描述](https://img-blog.csdnimg.cn/2c4010f8f4f7483bbacf6eb1ddbc21e3.png)

#### 3.2、ParNew收集器

ParNew收集器其实就是Serial收集器的多线程版本。除了使用多线程外其余行为均和Serial收集器一模一样（参数控制、收集算法、Stop The
World、对象分配规则、回收策略等）。

**特点：** 多线程、ParNew收集器默认开启的收集线程数与CPU的数量相同，在CPU非常多的环境中，可以使用-
XX:ParallelGCThreads参数来限制垃圾收集的线程数。

和Serial收集器一样存在Stop The World问题

**应用场景：**
ParNew收集器是许多运行在Server模式下的虚拟机中首选的新生代收集器，因为它是除了Serial收集器外，唯一一个能与CMS收集器配合工作的。

ParNew/Serial Old组合收集器运行示意图如下：

![在这里插入图片描述](https://img-blog.csdnimg.cn/74be55b18777459a84ef6a697bc5bdb5.png)

#### 3.3、Parallel Scavenge 收集器

与吞吐量关系密切，故也称为吞吐量优先收集器。

**特点：** 属于新生代收集器也是采用复制算法的收集器，又是并行的多线程收集器（与ParNew收集器类似）。

Parallel Scavenge收集器使用两个参数控制吞吐量：

1. XX:MaxGCPauseMillis 控制最大的垃圾收集停顿时间
2. XX:GCRatio 直接设置吞吐量的大小。

#### 3.4、Serial Old 收集器

Serial Old是Serial收集器的老年代版本。

**特点：** 同样是单线程收集器，采用标记-整理算法。

**应用场景：** 主要也是使用在Client模式下的虚拟机中。也可在Server模式下使用。

Server模式下主要的两大用途：

1. 在JDK1.5以及以前的版本中与Parallel Scavenge收集器搭配使用。
2. 作为CMS收集器的后备方案，在并发收集Concurent Mode Failure时使用。

Serial / Serial Old收集器工作过程图（Serial收集器图示相同）：  
![在这里插入图片描述](https://img-blog.csdnimg.cn/d9341370e3b945218655448c837c9486.png)

#### 3.5、Parallel Old 收集器

是Parallel Scavenge收集器的老年代版本。

**特点：** 多线程，采用标记-整理算法。

**应用场景：** 注重高吞吐量以及CPU资源敏感的场合，都可以优先考虑Parallel Scavenge+Parallel Old 收集器。

Parallel Scavenge/Parallel Old收集器工作过程图：  
![在这里插入图片描述](https://img-blog.csdnimg.cn/d4a09005a6294180a7ea9afecd634ffb.png)

#### 3.6、CMS收集器

一种以获取最短回收停顿时间为目标的收集器。

**特点：** 基于标记-清除算法实现。收集区域在老年代，并发收集、低停顿。

**应用场景：** 适用于注重服务的响应速度，希望系统停顿时间最短，给用户带来更好的体验等场景下。如web程序、b/s服务。

CMS收集器的运行过程分为下列4步：

1. 初始标记：标记GC Roots能直接到的对象。速度很快但是仍存在Stop The World问题。

2. 并发标记：进行GC Roots Tracing 的过程，找出存活对象且用户线程可并发执行。

3. 重新标记：为了修正并发标记期间因用户程序继续运行而导致标记产生变动的那一部分对象的标记记录。仍然存在Stop The World问题。

4. 并发清除：对标记的对象进行清除回收。

CMS收集器的内存回收过程是与用户线程一起并发执行的。

CMS收集器的工作过程图：  
![在这里插入图片描述](https://img-blog.csdnimg.cn/0eea24e6fda5472a8fecb26af071a76d.png)

CMS收集器的缺点：

1. 对CPU资源非常敏感。
2. 无法处理浮动垃圾，可能出现Concurrent Model Failure失败而导致另一次Full GC的产生。
3. 因为采用标记-清除算法所以会存在空间碎片的问题，导致大对象无法分配空间，不得不提前触发一次Full GC。

标记清除法有个缺点就是存在内存碎片的问题，那么CMS有个参数设置-
XX:+UseCMSCompactAtFullCollecion可以使CMS回收完成之后进行一次碎片整理。

#### 3.7、G1（Garbage First）

**1）、简介**

G1首先吸取了CMS优良的思路，还是使用并发收集的模式，但是更重要的是G1摒弃了原来的物理分区，而是把整个内存分成若干个大小的Region区域，然后由不同的Region在逻辑上来组合成各个分代，这样做的好处是G1进行垃圾回收的时候就可以用Region作为单位来进行更细粒度的回收了，每次回收可以只针对某一个或多个Region来进行回收。

适用场景：

* 同时注重吞吐量（Throughput）和低延迟（Low latency），默认的暂停目标是 200 ms
* 超大堆内存，会将堆划分为多个大小相等的 Region
* 整体上是 标记+整理 算法，两个区域之间是 复制 算法

相关 JVM 参数：

* -XX:+UseG1GC // 启动G1,因为jdk1.8不是默认
* -XX:G1HeapRegionSize=size
* -XX:MaxGCPauseMillis=time

**2）、G1垃圾回收阶段**

![在这里插入图片描述](https://img-blog.csdnimg.cn/7bba705a8c4a4e9797c7c63b470054f8.png)  
**2.1）、Young Collection（新生代）**

会Stop the world

1、在新生代，当伊甸园(E)区满了后，会用复制算法，将对象复制到幸存区(S)，  
2、再工作一段时间，幸存区也多了，再触发垃圾回收，不够年龄的拷贝到另一个幸存区也就是from和eden区拷贝到to区，若超过一定年龄的，会晋升到老年代。

![在这里插入图片描述](https://img-blog.csdnimg.cn/7eabe70e789a4eaca5be8a581c82e837.png)

**2.2）、Young Collection +CM（新生代和并发标记）**

* 在 Young GC 时会进行 GC Root 的初始标记
* 老年代占用堆空间比例达到阈值时，进行并发标记（不会 STW），由下面的 JVM 参数决定

-XX:InitiatingHeapOccupancyPercent=percent （默认45%）

![在这里插入图片描述](https://img-
blog.csdnimg.cn/11cca0bc7c92459eafb1a17abe0aea84.png)E：eden，S：幸存区，O：老年代

**2.3）、Mixed Collection（混合回收）**

会对 E、S、O 进行全面垃圾回收

* 最终标记（Remark）会 STW
* 拷贝存活（Evacuation）会 STW

-XX:MaxGCPauseMillis=ms

![在这里插入图片描述](https://img-blog.csdnimg.cn/da8ff1d48eac4fcdbc1c7c1a3f89ee0a.png)

**3)、Full GC**

* SerialGC

1.新生代内存不足发生的垃圾收集 - minor gc  
2.老年代内存不足发生的垃圾收集- full gc

* ParallelGC

1.新生代内存不足发生的垃圾收集 - minor gc  
2.老年代内存不足发生的垃圾收集- full gc

* CMS

1.新生代内存不足发生的垃圾收集 - minor gc  
2.老年代内存不足时（老年代所占内存超过阈值，阈值可用 -XX:CMSInitiatingoccupancyFraction来指定，默认为68%）  
如果垃圾产生速度慢于垃圾回收速度，不会触发 Full GC，还是并发地进行清理  
如果垃圾产生速度快于垃圾回收速度，便会触发 Full GC，然后退化成 serial Old
收集器串行的收集，这会导致应用程序中断，直到垃圾回收完成后才会正常工作。

* G1

1.新生代内存不足发生的垃圾收集 - minor gc  
2.老年代内存不足时（老年代所占内存超过阈值）  
如果垃圾产生速度慢于垃圾回收速度，不会触发 Full GC，还是并发地进行清理  
如果垃圾产生速度快于垃圾回收速度，便会触发 Full GC，然后退化成 serial Old
收集器串行的收集，这会导致应用程序中断，直到垃圾回收完成后才会正常工作。

**4）、Young Collection跨代引用**

新生代回收的跨代引用（老年代引用新生代）问题。

就是用可达性分析找到存活对象，复制到幸存区的时候要时候要找到新生代的根对象，根对象有一部分存活在了老年代，如果遍历老年代寻找根对象，效率就很低，因此采用了一种`卡表`
的技术，把老年代的区细分，分成一个个card，每个car大约是512k，如果老年代其中有一个对象引用了新生代，那么就标记为`脏卡`
，这样就不用去找老年代了，只需要找这些标记的。  
![在这里插入图片描述](https://img-blog.csdnimg.cn/d519a68771df4b5abea6a991803826e5.png)

* 卡表 与 Remembered Set

Remembered Set 存在于E中，用于保存新生代对象对应的脏卡  
脏卡：O 被划分为多个区域（一个区域512K），如果该区域引用了新生代对象，则该区域被称为脏卡

* 在引用变更时通过 post-write barried + dirty card queue

* concurrent refinement threads 更新 Remembered Set

**5）、Remark（重新标记阶段）**

重新标记阶段采用3色标记法  
就是用3种颜色来标记对象  
1）白色：未被标记的对象  
2）灰色；自身被标记，成员变量未被标记  
3）黑色：自身和成员变量都已标记完成（代表存活对象）

标记最大的难题就是边标记垃圾，边生产垃圾，即并发标记。并发标记会产生2个问题：浮动垃圾和漏标

1）多标-浮动垃圾  
在并发标记的时候，标记了GCRoot这个对象为起点向下搜索引用的对象，这个时候栈帧出栈了，那么其引用的对象之前已经标记为非垃圾对象，浮动垃圾下次再收集。  
比如：栈帧 --引用对象A --引用对象B  
GC线程从GCRoot开始标记，标记到对象B结束。认为A、B是活对象。突然间应用线程把栈帧出栈了。

2）漏标  
由于并发的原因，原本是存活的对象，却被GC线程回收了。

JVM采用了3色标记法，解决标记的2大难题

步骤1：初始化阶段，所有对象都是白色，并记录在白色集合里面。  
步骤2：处理GCRoot直接引用对象，把GC Roots直接引用到的A、B对象挪到灰色集合中  
步骤3：将灰色集合的A、B挪到黑色集合中，然后把A、B引用的其他对象（C），全部挪到灰色集合中。  
步骤4：递归将灰色集合的C挪到黑色集合中，然后把C引用的其他对象D、E全部挪到灰色集合中。  
步骤5：递归将灰色集合的D、E挪到黑色集合中，由于D、E没有其他引用的对象，故标记结束。  
经过以上的标注后，黑色集合A、B、C、D、E为存活对象，白色集合F、G、H为不可达对象（可回收对象）

![在这里插入图片描述](https://img-blog.csdnimg.cn/29843693db6c472ebecc27f8eec9ea79.png)

写屏障+SATB，解决漏标的问题（G1技术方案）：  
SATB的全称是Snapchat At The
Beginning，原理是，当GC开始之前，复制一份引用关系快照，即当成员变量的引用改变时，记录该成员旧的引用对象，保存到satb_mark_queue中  
![在这里插入图片描述](https://img-blog.csdnimg.cn/6c715e246d144788bd3b6ea281821896.png)  
把E存起来是增量更新，把objC.fieldE存起来是SATB  
例如，上图中，当对象C、B的成员变量E改变时，采用写屏障把对象E记录到satb_mark_queue队列中。  
每条GC线程都自带一个satb_mark_queue队列，在并发阶段会处理satb_mark_queue中的对象，处理的方法是把satb_mark_queue队列中的对象当做根重新扫描一遍，以解决白色对象引用被修改产生的漏标问题。  
缺点：  
如果被修改引用的白色对象（例如E对象）就是要被收集的垃圾，SATB的标记会让它躲过GC，这就是浮动垃圾。因为SATB的做法精度比较低，所以造成的浮动垃圾也会比较多。

**6）、JDK 8u20字符串去重**

* 优点：节省大量内存
* 缺点：略微多占用了 cpu 时间，新生代回收时间略微增加

开启：-XX:+UseStringDeduplication

​    
String s1 = new String("hello"); // char[]{'h','e','l','l','o'}
String s2 = new String("hello"); // char[]{'h','e','l','l','o'}

过程：

1. 将所有新分配的字符串（底层是 char[] ）放入一个队列
2. 当新生代回收时，G1 并发检查是否有重复的字符串
3. 如果字符串的值一样，就让他们引用同一个字符串对象  
   注意，其与 String.intern() 的区别

    * String.intern() 关注的是字符串对象
    * 字符串去重关注的是 char[]
    * 在 JVM 内部，使用了不同的字符串标

**7)、JDK 8u40 并发标记类卸载**

所有对象都经过并发标记后，就能知道哪些类不再被使用，当一个类加载器的所有类都不再使用，则卸  
载它所加载的所有类  
-XX:+ClassUnloadingWithConcurrentMark 默认启用

**8）、 JDK 8u60 回收巨型对象**

* 一个对象大于 region 的一半时，称之为巨型对象
* G1 不会对巨型对象进行拷贝
* 回收时被优先考虑
* G1 会跟踪老年代所有 incoming 引用，这样老年代 incoming 引用为0 的巨型对象就可以在新生代垃圾回收时处理掉

![在这里插入图片描述](https://img-blog.csdnimg.cn/23758942c935431db910bbe509bbe04f.png)

**9）、 JDK 9 并发标记起始时间的调整**

* 并发标记必须在堆空间占满前完成，否则退化为 FullGC
* JDK 9 之前需要使用 -XX:InitiatingHeapOccupancyPercent
* JDK 9 可以动态调整
    * -XX:InitiatingHeapOccupancyPercent 用来设置初始值
    * 进行数据采样并动态调整
    * 总会添加一个安全的空档空间

### 4、垃圾回收调优

查看虚拟机运行的相关垃圾回收的参数

​    
"C:\Program Files\Java\jdk1.8.0_281\bin\java" -XX:+PrintFlagsFinal -version | findstr "GC"

#### 4.1、调优领域

* 内存
* 锁竞争
* cpu 占用
* io

#### 4.2、确定目标

【低延迟】还是【高吞吐量】，选择合适的回收器

* CMS，G1，ZGC（响应时间优先）
* ParallelGC
* Zing

#### 4.3、最快的GC是不发生GC

**查看 FullGC 前后的内存占用，考虑下面几个问题：**

* 数据是不是太多？

    * resultSet = statement.executeQuery(“select * from 大表 limit n”)
* 数据表示是否太臃肿？

    * 对象图
    * 对象大小 16 Integer 24 int 4
* 是否存在内存泄漏？

    * 不断的放数据 static Map map =
    * 软
    * 弱
    * 不要使用java来做缓存，最好用第三方缓存实现

#### 4.4、新生代调优

排除代码问题后，再来进行调优，建议从新生代开始

新生代的特点：

* 所有的new操作的内存分配非常廉价
* 死亡对象的回收代价是零
* 大部分对象用过即死
* Minor Gc的时间远远低于Full GC

调优参数：`-Xmn` 设置新生代的大小

**设置越大越好吗？**

不是。官方说明如下  
![在这里插入图片描述](https://img-blog.csdnimg.cn/1ac08dea6db640dd98440cca6efcf924.png)

* 新生代内存太小：频繁触发 Minor GC ，Minor会 stop the world暂停 ，会使得吞吐量下降
* 新生代内存太大：老年代内存占比有所降低，会更频繁地触发 Full GC。而且触发 Minor GC 时，清理新生代所花费的时间会更长

oracle建议你的新生代内存大于整个堆的25%。小于堆的50%。

调优要考虑条件：

* 新生代能容纳所有【并发量 * (请求-响应)】的数据
* 幸存区大到能保留【当前活跃对象+需要晋升对象】
* 晋升阈值配置得当，让长时间存活对象尽快晋升  
  调整最大晋升阈值：-XX:MaxTenuringThreshold=threshold  
  垃圾回收时打印存活对象详情：-XX:+PrintTenuringDistribution

#### 4.5、老年代调优

以 CMS 为例：

* CMS 的老年代内存越大越好
* 先尝试不做调优，如果没有 Full GC 那么已经…，否则先尝试调优新生代
* 观察发生 Full GC 时老年代内存占用，将老年代内存预设调大 1/4 ~ 1/3  
  老年代空间占用达到多少比例时触发垃圾回收参数：-XX:CMSInitiatingOccupancyFraction=percent

#### 4.6、案例

**案例1：Full GC 和Minor GC 频繁**  
说明空间紧张，如果是新生代空间紧张，当业务高峰期来了，大量对象被创建，很快新生代空间满，会经常Minor
GC，而原本很多生存周期短的对象也会被晋升到老年代，老年代存了大量的生存周期短的对象，导致频繁触发Full
GC,所以应该先调整新生代的内存空间大一点，让对象尽可能的在新生代。

**案例2：请求高峰期发生Full GC，单次暂停时间特别长。（CMS）**

分析在哪一部分耗时较长，通过查看GC日志，比较慢的通常会是在重新标记阶段，重新标记会扫描整个堆内存，根据对象找引用，所以解决办法是在重新标记之前，先在新生代进行垃圾回收，这样就会减少重新标记的耗时时间，通过
-XX:+CMSScavengeBeforeRemark 参数在重新标记之前进行垃圾回收

## 三、类加载与字节码技术

### 1、类加载阶段

#### 1.1、加载

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

注意：

instanceKlass 这样的【元数据】是存储在方法区（1.8 后的元空间内），但 _java_mirror是存储在堆中  
可以通过前面介绍的 HSDB 工具查看

![在这里插入图片描述](https://img-blog.csdnimg.cn/ba84cef5e5e04666b8aa53400fe57444.png)

#### 1.2、链接

**验证**

验证类是否符合 JVM规范，安全性检查  
用 UE 等支持二进制的编辑器修改 HelloWorld.class 的魔数，在控制台运行

![在这里插入图片描述](https://img-blog.csdnimg.cn/3a522532b6cf431d9740fb36b200d893.png)  
**准备**

为 static 变量分配空间，设置默认值

* static 变量在 JDK 7 之前存储于 instanceKlass 末尾，从 JDK 7 开始，存储于 _java_mirror 末尾
* static 变量分配空间和赋值是两个步骤，分配空间在准备阶段完成，赋值在初始化阶段完成
* 如果 static 变量是 final 的基本类型，以及字符串常量，那么编译阶段值就确定了，赋值在准备阶  
  段完成

* 如果 static 变量是 final 的，但属于引用类型，那么赋值也会在初始化阶段完成

**解析**

将常量池中的符号引用解析为直接引用

![在这里插入图片描述](https://img-blog.csdnimg.cn/a69c0adad59e4a4a8be3893bc36743fc.png)

#### 1.3、初始化

()V 方法  
初始化即调用 ()V ，虚拟机会保证这个类的『构造方法』的线程安全

**发生的时机**

概括得说，类初始化是【懒惰的】

* main 方法所在的类，总会被首先初始化
* 首次访问这个类的静态变量或静态方法时
* 子类初始化，如果父类还没初始化，会引发
* 子类访问父类的静态变量，只会触发父类的初始化
* Class.forName
* new 会导致初始化

不会导致类初始化的情况

* 访问类的 static final 静态常量（基本类型和字符串）不会触发初始化
* 类对象.class 不会触发初始化
* 创建该类的数组不会触发初始化
* 类加载器的 loadClass 方法
* Class.forName 的参数 2 为 false 时

### 2、类加载器

以 JDK 8 为例：

 名称                               | 加载哪的类                 | 说明                     
----------------------------------|-----------------------|------------------------  
 Bootstrap ClassLoader（启动类加载器）    | JAVA_HOME/jre/lib     | 无法直接访问                 
 Extension ClassLoader（扩展类加载器）    | JAVA_HOME/jre/lib/ext | 上级为 Bootstrap，显示为 null 
 Application ClassLoader（应用程序加载器） | classpath             | 上级为 Extension          
 自定义类加载器                          | 自定义                   | 上级为 Application        

#### 2.1、启动类加载器

用 Bootstrap 类加载器加载类：

​    
public class F {
static {
System.out.println("bootstrap F init");
}
}

执行

​    
public class Load5_1 {
public static void main(String[] args) throws ClassNotFoundException {
Class<?> aClass = Class.forName("cn.itcast.jvm.t3.load.F");
System.out.println(aClass.getClassLoader());
}
}

输出

​    
E:\git\jvm\out\production\jvm>java -Xbootclasspath/a:.
cn.itcast.jvm.t3.load.Load5
bootstrap F init
null

* -Xbootclasspath 表示设置 bootclasspath
* 其中 /a:. 表示将当前目录追加至 bootclasspath 之后
* 可以用这个办法替换核心类
    * java -Xbootclasspath:
    * java -Xbootclasspath/a:<追加路径>
    * java -Xbootclasspath/p:<追加路径>

#### 2.2、扩展类加载器

用扩展类加载器加载类：  
classpath 和 JAVA_HOME/jre/lib/ext 下有同名类 G,执行会发现加载了扩展类的加载器

#### 2.3、双亲委派机制

**定义：**
当一个类加载器收到了类加载的请求的时候，他不会直接去加载指定的类，而是把这个请求委托给自己的父加载器去加载。只有父加载器无法加载这个类的时候，才会由当前这个加载器来负责类的加载。

所谓的双亲委派，就是指调用类加载器的 loadClass 方法时，查找类的规则

注意：这里的双亲，翻译为上级似乎更为合适，因为它们并没有继承关系

源码：  
![在这里插入图片描述](https://img-blog.csdnimg.cn/8ebfcb834bae4678aee4a817a015ebca.png)  
**执行流程为：**

1. sun.misc.Launcher$AppClassLoader //1 处， 开始查看已加载的类，结果没有
2. sun.misc.Launcher $ AppClassLoader // 2 处，委派上级sun.misc.Launcher$ExtClassLoader.loadClass()
3. sun.misc.Launcher$ExtClassLoader // 1 处，查看已加载的类，结果没有
4. sun.misc.Launcher$ExtClassLoader // 3 处，没有上级了，则委派 BootstrapClassLoader  
   查找

5. BootstrapClassLoader 是在 JAVA_HOME/jre/lib 下找 H 这个类，显然没有
6. sun.misc.Launcher $ ExtClassLoader // 4 处，调用自己的 findClass 方法，是在JAVA_HOME/jre/lib/ext 下找 H 这个类，显然没有，回到
   sun.misc.Launcher$AppClassLoader的 // 2 处
7. 继续执行到 sun.misc.Launcher$AppClassLoader // 4 处，调用它自己的 findClass 方法，在  
   classpath 下查找，找到了

#### 2.4、线程上下文类加载器

我们在使用 JDBC 时，都需要加载 Driver 驱动，不知道你注意到没有，不写

​    
Class.forName("com.mysql.jdbc.Driver")

也是可以让 com.mysql.jdbc.Driver 正确加载的，你知道是怎么做的吗？  
让我们追踪一下源码：  
![在这里插入图片描述](https://img-blog.csdnimg.cn/0e4b3bddd5b044eb8caa449c6466af2c.png)  
先不看别的，看看 DriverManager 的类加载器：

​    
System.out.println(DriverManager.class.getClassLoader());

打印 null，表示它的类加载器是 Bootstrap ClassLoader，会到 JAVA_HOME/jre/lib
下搜索类，但JAVA_HOME/jre/lib 下显然没有 mysql-connector-java-5.1.47.jar
包，这样问题来了，在DriverManager 的静态代码块中，怎么能正确加载 com.mysql.jdbc.Driver 呢？

继续看 loadInitialDrivers() 方法：  
![在这里插入图片描述](https://img-blog.csdnimg.cn/43b90d0bb6c44edf9b662716bc4beaee.png)

先看 2）发现它最后是使用 Class.forName 完成类的加载和初始化，关联的是应用程序类加载器，因此  
可以顺利完成类加载  
再看 1）它就是大名鼎鼎的 Service Provider Interface （SPI）  
约定如下，在 jar 包的 META-INF/services 包下，以接口全限定名名为文件，文件内容是实现类名称

![在这里插入图片描述](https://img-blog.csdnimg.cn/015a273f68f146c5a0ed14a2458f6071.png)  
这样就可以使用

​    
ServiceLoader<接口类型> allImpls = ServiceLoader.load(接口类型.class);
Iterator<接口类型> iter = allImpls.iterator();
while(iter.hasNext()) {
iter.next();
}

来得到实现类，体现的是【面向接口编程+解耦】的思想，在下面一些框架中都运用了此思想：

* JDBC
* Servlet 初始化器
* Spring 容器
* Dubbo（对 SPI 进行了扩展）

接着看 ServiceLoader.load 方法：  
![在这里插入图片描述](https://img-
blog.csdnimg.cn/a8195ab1c3504f9793eeafa8de35b9f9.png)
线程上下文类加载器是当前线程使用的类加载器，默认就是应用程序类加载器，它内部又是由  
Class.forName 调用了线程上下文类加载器完成类加载，具体代码在 ServiceLoader 的内部类  
LazyIterator 中：

![在这里插入图片描述](https://img-blog.csdnimg.cn/ce222be24af544fa94c97788b1483b0f.png)

#### 2.5、自定义类加载器

问问自己，什么时候需要自定义类加载器

1. 想加载非 classpath 随意路径中的类文件
2. 都是通过接口来使用实现，希望解耦时，常用在框架设计
3. 这些类希望予以隔离，不同应用的同名类都可以加载，不冲突，常见于 tomcat 容器

步骤：

1. 继承 ClassLoader 父类
2. 要遵从双亲委派机制，重写 findClass 方法  
   注意不是重写 loadClass 方法，否则不会走双亲委派机制

3. 读取类文件的字节码
4. 调用父类的 defineClass 方法来加载类
5. 使用者调用该类加载器的 loadClass 方法

示例：加载此路径下的class文件

![在这里插入图片描述](https://img-blog.csdnimg.cn/03861bb090cf4316b9859e261d5f8456.png)

代码：  
![在这里插入图片描述](https://img-blog.csdnimg.cn/e07646e5befb4224a5ca6cb5f4b37e09.png)  
运行：因为第一次加载了以后会放在自定义加载器的缓存中，多次调用也不会重复加载，所以输出true

![在这里插入图片描述](https://img-blog.csdnimg.cn/314f3c89fc264ba5898ee0f95e88db2d.png)

