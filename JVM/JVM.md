# JVM

## 零、引言

### 什么是 JVM ?

定义：Java Virtual Machine - java 程序的运行环境（java 二进制字节码的运行环境）

好处：

- 一次编写，到处运行
- 自动内存管理，垃圾回收功能
- 数组下标越界检查
- 多态

比较： jvm jre jdk

![image-20231218201804229](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20231218201804229.png)

### 常见的 JVM

![image-20231218201544705](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20231218201544705.png)

[Comparison of Java virtual machines - Wikipedia](https://en.wikipedia.org/wiki/Comparison_of_Java_virtual_machines)

### 学习路线

类都是放在**方法区**的

类创造的实例、对象都放在**堆**里面

对象在调用方法时会用到**虚拟机栈、程序计数器和本地方法栈**

方法执行时是由**解释器**逐行逐行执行

热点代码（经常被调用的）会被**即时编译器**优化

堆里面不再使用的对象会被**垃圾回收**

有一些方法不好实现，就会调用**本地方法接口**实现和操作系统的对接

![image-20231122132137941](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20231122132137941.png)

https://www.javainterviewpoint.com/java-virtual-machine-architecture-in-java/

## 一、内存结构

### 1、程序计数器

**1）定义：**

Program Counter Register 程序计数器（寄存器）

**2）作用**：记住下一条jvm指令的执行地址

- 解释器会解释指令为机器码交给 cpu 执行，程序计数器会记录下一条指令的地址行号，**解释器解释完当条指令后，再到程序计数器取地址执行下一条指令
  **（如执行0时，程序计数器存的就是3）
- 程序计数器在java中就是寄存器，因为这个动作非常频繁，寄存器的速度很快

**3）特点：**

1. 是线程私有的 —— 多线程的环境下，如果两个线程发生了上下文切换，那么程序计数器会记录线程下一行指令的地址行号，以便于接着往下执行。
2. 不会存在内存溢出——只有它不会溢出

二进制字节码 jvm指令 java源代码

![image-20231122134815855](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20231122134815855.png)



---

### 2、虚拟机栈

**1）定义**（JVM Stacks）

* **每个线程运行时所需要的内存空间，称为虚拟机栈**。虚拟机栈的主要作用是处理方法执行的内存分配，包括方法调用和方法执行完成后的退出处理。
* **每个栈由多个栈帧（Frame）组成。**每当一个新的方法被调用时，JVM
  就会创建一个新的栈帧，并将其压入对应线程的虚拟机栈中。**每个栈帧对应一个方法调用，包含了这个方法调用所需的各种数据，如参数、局部变量、中间计算结果和控制流信息。
  **
* **每个线程只能有一个活动栈帧，对应着当前正在执行的那个方法。**
  当这个方法调用其他方法时，一个新的栈帧被创建并成为新的活动栈帧。当方法执行完成后，它的栈帧被弹出栈，控制权返回到调用该方法的栈帧。

![image-20231122135307221](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20231122135307221.png)

示例：

![image-20231122140104535](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20231122140104535.png)

问题辨析：

1. 垃圾回收是否涉及栈内存？

   答：栈内存主要用于存储方法调用的上下文，包括局部变量、参数和返回地址。*
   *栈内存的清理是自动的，当方法执行完成，对应的栈帧就会被移除。这不是垃圾回收的一部分，而是线程执行的一部分。**

   **垃圾回收（GC）主要关注的是堆内存（Heap）**，这是对象实例的主要存储区域。当一个对象不再被任何引用指向时，垃圾回收器会清理这些对象以释放内存空间。

2. 栈内存分配越大越好吗？

   答：不对，因为物理内存是一定的，栈内存越大，可以支持更多的递归调用，但是可执行的线程数目就会越少；栈太小，可能会导致 `StackOverflowError`

3. 方法内的局部变量是否线程安全？

    * 如果**方法内局部变量没有逃离方法的作用访问**，它是线程安全的

    * 如果是局部变量**引用了对象**，并**逃离方法的作用范围**，需要考虑线程安全

   ![image-20231122140458640](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20231122140458640.png)

    - **共享实例**: 如果返回的 `StringBuilder`
      实例在多个线程间共享，并且这些线程对它进行修改，那么就可能会出现线程安全问题。`StringBuilder`
      类本身不是线程安全的，因为它的方法没有进行同步控制，所以在多线程环境中对同一个`StringBuilder` 实例进行操作可能会导致不一致的状态。
    - **解决措施：**避免共享（static）、使用`StringBuffer`、同步控制

**2)栈内存溢出**

- java.lang.stackOverflowError 问题出现原因：
    1. 栈帧过多，可能是递归太多，或写了个死循环
    2. 栈帧过大（一般不会出现
  3. 调用第三方类库操作【类之间循环引用】
- 解决措施：设置栈内存大小：`-Xss1024k`

![image-20231218211147125](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20231218211147125.png)

**3）线程运行诊断**

案例1： cpu 占用过多

定位 【linux命令】

- 用top定位哪个进程对cpu的占用过高
- ps H -eo pid,tid,%cpu | grep 进程id （用ps命令进一步定位是哪个线程引起的cpu占用过高）
- jstack 进程id
    - 可以根据线程id 找到有问题的线程，进一步定位到问题代码的源码行号

案例2：程序运行很长时间没有结果

出现死锁情况

### 3、本地方法栈

（Native Method Stacks）

**本地方法**：是用非 Java 语言编写的方法，通常是 C 或 C++。这些方法不在 JVM 上运行，而是直接在底层操作系统上执行。因为
JAVA没法直接和操作系统底层交互，所以需要用到本地方法栈，服务于带 native 关键字的方法。

**本地方法栈**：**是专门用于支持本地方法调用的内存区域。**当 JVM 调用本地方法时，这些方法的栈帧不在 Java 虚拟机栈上，而是在本地方法栈上。

![image-20231122141631326](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20231122141631326.png)

### 4、堆

**1）定义**（Heap）

* 通过 new 关键字创建对象都会使用堆内存

**特点：**

* 堆是线程共享的——》堆中对象都需要考虑线程安全的问题
* 有垃圾回收机制

**2）堆内存溢出**

报错信息：java.lang.OutofMemoryEjamrror ：java heap space

**出现原因：一直创建对象而且在使用，没法进行垃圾回收**

解决方法：指定堆内存大小:

* 堆的最小值：-Xms 如`-Xms2m`【用于排除问题，有时候内存默认内存过大不容易出现溢出问题，这个时候我们就要主动设置小一点及时发现问题】
* 堆的最大值 -Xmx 如 `-Xmx8m`

**3）堆内存诊断工具**

终端输入相关命令即可

* jps 工具

  查看当前系统中有哪些 java 进程，获取进程id方便后续操作


* jmap 工具

  查看堆内存占用情况 `jhsdb jmap --heap --pid [进程id]`【只能查询某一时刻的内存情况，如果要长时间的话还是得jconsole】

  使用jmap报错：

  ![image-20231122152815302](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20231122152815302.png)

  ```java
  java -version 1  // 查看JVM版本
  Java HotSpot(TM) 64-Bit Server VM (build 19.0.2+7-44, mixed mode, sharing)
  ```

  **解决方法：JDK也改成19版本的**

  **找到Heap Usage的Eden Space的used，查看堆占用内存**


* jconsole 工具

  图形界面的，多功能的监测工具，实时监控 Java 应用程序的资源消耗，包括 CPU 使用率、内存占用、线程使用情况等。

  ![image-20231122161247134](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20231122161247134.png)


* jvisualvm 工具

  多功能的可视化工具，用于**深入分析 Java 应用程序。**

排除问题：【场景】有一个进程占用内存很大，我们并不知道是哪一个

- jps找到进程id

- jmap看eden和old占用内存大小

- jconsole 执行gc，但是old内存下降的并不多

- jvisualvm 监视页面执行`堆Dump`，抓取快照，查看最大的20个对象，点进对象查看什么占用最大，再根据这个看看是不是代码哪里出力了问题

### 5、方法区

**1）定义**（Method Area）

在 Java 虚拟机（JVM）的内存模型中，是一个特殊的内存区域，用于存储类信息、常量、静态变量、即时编译后的代码等。

[JVM规范-方法区定义](https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-2.html)

**2）组成**

方法区存放的数据主要是被类加载器加载后的类信息，运行时常量池、静态变量等等。

![image-20231122170217270](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20231122170217270.png)

**3）方法区内存溢出**

- 1.8 以前会导致永久代内存溢出

- 1.8 之后会导致元空间内存溢出

原因：过多的类加载、大量常量、 内存泄漏、配置问题、字节码动态生成大量类或使用代理（Spring和Mybatis）【Demo1_8.java】

可用 `-XX:MaxMetaspaceSize=8m `指定元空间大小

现在用元空间一般不会出现溢出（java.lang.OutOfMemoryError: Metaspace），

**4）运行时常量池**

* 常量池，就是一张表，虚拟机指令根据这张常量表找到要执行的类名、方法名、参数类型、字面量等信息
* 运行时常量池，常量池是 *.class 文件中的，当该类被加载，它的常量池信息就会放入运行时常量池，并把里面的符号地址变为真实地址

```java
// 二进制字节码（类基本信息，常量池，类方法定义，包含了虚拟机指令）
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("hello world");
    }
}
```

javap -v HelloWorld.class查看二进制字节码

前面四行就是虚拟机指令 ，**#后面的就是要找的常量池位置**

![image-20231122172324943](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20231122172324943.png)

**5）StringTable**

```java
String s1 = "a";
String s2 = "b";
String s3 = "a" + "b";
String s4 = s1 + s2;
String s5 = "ab";
String s6 = s4.intern();

System.out.println(s3 == s4);
System.out.println(s3 == s5);
System.out.println(s3 == s6);
String x2 = new String("c") + new String("d");
String x1 = "cd";
x2.intern();
// 问，如果调换了【最后两行代码】的位置呢，如果是jdk1.6呢
System.out.println(x1 == x2);
```

分析：

1. a、b、ab 都是常量，直接加到常量池里
2. 变量s1+变量s2 拼接相当于 StringBuilder，而StringBuilder的toString方法会创建一个新String，也就是 new String(“ab”)
   ，说明是存在堆里面的
3. 所以s3≠s4，s3对象是存在常量池的，s4 new了一个对象存在堆里的，位置不一样
4. 常量 a + 常量b 拼接，发现延用了常量池已有的ab字符串对象，s3＝s5
5.

### 6、直接内存

**1）定义**

在 Java 中通常是指不由 Java 虚拟机（JVM）的垃圾回收器管理的内存区域，在操作系统里面

* 常见于 NIO 操作时，用于数据缓冲区
* 分配回收成本较高，但**读写性能高**
* **不受 JVM 内存回收管理**

-

**2）直接内存的分配**

传统io读取文件：

- 流程：因为 java 不能直接操作文件管理，需要切换到内核态，使用本地方法进行操作，然后读取磁盘文件，会在系统内存中创建一个缓冲区，将数据读到系统缓冲区，
  然后在**将系统缓冲区数据，复制到 java缓冲区存在堆内存中。**缺点是**数据存储了两份，在系统内存中有一份，java
  堆中有一份，造成了不必要的复制。**

  ![image-20231122164246429](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20231122164246429.png)

**直接内存的分配**

- **通过 `ByteBuffer` 分配**：直接内存主要通过 `ByteBuffer.allocateDirect(int capacity)`
  方法分配。这种方式创建的缓冲区会在操作系统的本地内存中分配空间，而不是在 JVM 的堆内存上。无需将代码从系统内存复制到
  Java堆内存，从而提高了效率。

- **绕过 JVM 堆管理**：直接分配的内存不在 JVM 堆上，因此不受 JVM 垃圾回收器的管理。这意味着它的分配和回收不会触发 JVM
  垃圾收集的过程，从而减少了垃圾收集的开销。

  ![image-20231122164401293](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20231122164401293.png)

**3）直接内存的回收**

* 使用了 **Unsafe 对象完成直接内存的分配回收，并且回收需要主动调用 freeMemory 方法**
* ByteBuffer 的实现类内部，使用了 Cleaner （虚引用）来监测 ByteBuffer 对象，一旦ByteBuffer 对象被垃圾回收，那么就会由ReferenceHandler
  线程通过 Cleaner 的 clean 方法调用 freeMemory 来释放直接内存

- `-XX:+DisableExplicitGC`：禁用显示的垃圾回收——System.gc()无效

## 二、垃圾回收

### 1、如何判断对象可以回收

#### **1）引用计数法**

如果一个对象被其他变量所引用，则让该对象的引用计数+1，依次类推。某个变量不再引用该对象，则让该对象的引用计数-1，当该对象的引用计数变为0时，则表示该对象没用被其他变量所引用，这时候该对象就可以被作为垃圾进行回收。

**弊端：循环引用时，两个对象的引用计数都为1，导致两个对象都无法被释放回收。最终就会造成内存泄漏！**

![image-20231219130924683](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20231219130924683.png)

#### **2）可达性分析算法**

可达性分析算法就是JVM中判断对象是否是垃圾的算法：该算法首先要确定GC Root(根对象，就是肯定不会被当成垃圾回收的对象)。

在垃圾回收之前，JVM会先对堆中的所有对象进行扫描，判断每一个对象是否能被GC
Root直接或者间接的引用，如果能被根对象直接或间接引用则表示该对象不能被垃圾回收，反之则表示该对象可以被回收：

- JVM中的垃圾回收器通过可达性分析来探索所有存活的对象。

- 扫描堆中的对象，看能否沿着GC Root为起点的引用链找到该对象，如果找不到，则表示可以回收，否则就可以回收。

- 可以作为GC Root的对象：

    - 虚拟机栈（栈帧中的本地变量表）中引用的对象。

    - 方法区中类静态属性引用的对象。

    - 方法区中常量引用的对象
    - 本地方法栈中JNI（即一般说的Native方法）引用的对象。

#### 3）四种引用

![image-20231219131039609](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20231219131039609.png)

1. **强引用**

上图实心线表示强引用：比如，**new 一个对象M**，将对象M通过=赋值给某个变量m，则变量m就强引用了对象M。

**强引用的特点：只要沿着GC Root的引用链能够找到该对象，就不会被垃圾回收；**只有当GC
Root都不引用该对象时，才会回收强引用对象。如上图：当B、C对象都不引用A1对象时，A1对象才会被回收。

2. **软引用**

上图中宽虚线所表示的就是软引用：

软引用的特点：当GC Root指向软引用对象时，若**内存不足**，则会**回收软引用所引用的对象**。

**如上图：如果B对象不再引用A2对象且内存不足时，软引用所引用的A2对象就会被回收。**

基本使用：【Demo2_4.java】

```JAVA
public static void main(String[]args){
        // list -->(强引用) SoftReference --> (软引用) byte[]
        // 软引用对象内部包装new byte[_4M]对象
        List<SoftReference<byte[]>>list=new ArrayList<>();

        // 如果在垃圾回收时发现内存不足，在回收软引用所指向的对象时，软引用本身不会被清理。
        // 如果想要清理软引用，需要使用引用队列，用于移除引用为空的软引用对象
        ReferenceQueue<byte[]>queue=new ReferenceQueue<>();

        for(int i=0;i< 5;i++){
        // 关联了引用队列， 当软引用所关联的 byte[]被回收时，软引用自己会加入到 queue 中去
        SoftReference<byte[]>ref=new SoftReference<>(new byte[_4MB],queue);
        System.out.println(ref.get());
        list.add(ref);
        System.out.println(list.size());
        }

        // 从队列中获取无用的 软引用对象，并移除
        Reference<?extends byte[]>poll=queue.poll();
        while(poll!=null){
        list.remove(poll);
        poll=queue.poll();
        }

        System.out.println("===========================");
        for(SoftReference<byte[]>reference:list)
        System.out.println(reference.get());
        }
```

3. **弱引用**

只有当弱引用引用该对象，在垃圾回收时，**无论内存是否充足**，都会回收弱引用所引用的对象。如上图如果B对象不再引用A3对象，则A3对象会被回收。

基本使用：【Demo2_5.java】

```java
public static void main(String[]args){
        //  弱引用的使用和软引用类似，只是将 SoftReference 换为了 WeakReference。
        //  list --> WeakReference --> byte[]
        List<WeakReference<byte[]>>list=new ArrayList<>();
        for(int i=0;i< 10;i++){
        WeakReference<byte[]>ref=new WeakReference<>(new byte[_4MB]);
        list.add(ref);
        for(WeakReference<byte[]>w:list)
        System.out.print(w.get()+" ");
        System.out.println();
        }
        }
```

**
软引用和弱引用类似，只要该对象没有被直接强引用（也就是B指向A2,A3的线消失了，只有C对象指向的虚线），才可能被回收。垃圾回收发生时弱引用对象直接释放，内存不够时，软引用对象释放。**
两个都涉及到了引用队列

4. **虚引用**

![image-20231220154928648](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20231220154928648.png)

总计：虚引用就是用ByteBufffer，ByteBufffer会开一块直接内存。B对象不再引用ByteBuffer对象，ByteBuffer就会被回收。但是直接内存中的内存还未被回收。这时需要将虚引用对象Cleaner放入引用队列中，然后调用它的`clean`
方法（`Unsafe.freeMemory()`）来释放直接内存。

5. **终结器引用**

​ 所有的类都继承自Object类，Object类有一个`finalize()`
方法。当某个对象不再被其他的对象所引用时，会先将终结器引用对象放入引用队列中，然后根据终结器引用对象找到它所引用的对象，然后调用该对象的`finalize()`
方法。调用以后，该对象就可以被垃圾回收了。

​
B对象不再引用A4对象。这是终结器对象就会被放入引用队列中，引用队列会根据它，找到它所引用的对象。然后调用被引用对象的`finalize()`
方法。调用以后，该对象就可以被垃圾回收了。

![image-20231220155132763](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20231220155132763.png)

但是我们不推荐用finallize()释放资源

**引用队列**

- 软引用和弱引用可以配合引用队列(也可以不配合)：

    - 在弱引用和虚引用所引用的对象被回收以后，会将这些引用放入引用队列中，方便一起回收这些软/弱引用对象。

- 虚引用和终结器引用**必须配合引用队列**：

    - 虚引用和终结器引用在使用时会关联一个引用队列。

`-XX:+PrintGCDetails -verbose:gc` 打印垃圾回收的详细信息

**五种引用总结**

![image-20231220153819038](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20231220153819038.png)

- 强引用：无论内存是否足够，不会回收。

- 软引用：内存不足时，回收该引用关联的对象。(可以选择配合引用队列使用，也可以不配合)。

- 弱引用：垃圾回收时，无论内存是否足够，都会回收。(可以选择配合引用队列使用，也可以不配合)。

- 虚引用：任何时候都可能被垃圾回收器回收。(必须配合引用队列使用)。

### 2、垃圾回收算法

#### 1）标记清除

<img src="https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20231220155805311.png" alt="image-20231220155805311" style="zoom:50%;" />

定义：标记清除算法顾名思义，是指在虚拟机执行垃圾回收的过程中，先采用标记算法确定可回收对象，然后垃圾收集器根据标识，清除相应的内容，给堆内存腾出相应的空间。

- 这里的腾出内存空间并不是将内存空间的字节清 0，而是记录下这段内存的起始结束地址，下次分配内存的时候，会直接覆盖这段内存。

缺点：容易产生大量的**内存碎片**，可能**无法满足大对象的内存分配**
，一旦导致无法分配对象，那就会导致jvm启动gc，一旦启动gc，我们的应用程序就会暂停，这就导致应用的响应速度变慢。

#### 2）标记整理

![image-20231220155844656](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20231220155844656.png)

定义：会将不被GC Root引用的对象回收，清除其占用的内存空间。然后整理剩余的对象，可以有效**避免因内存碎片**
而导致的问题，但是牵扯到对象的整理移动，需要消耗一定的时间，所以效率较低。

#### 3）复制

![image-20231220160102441](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20231220160102441.png)

定义：将内存分为等大小的两个区域，**FROM**和**TO**（TO中为空）。先将被GC Root引用的对象从FROM放入TO中，再回收不被GC
Root引用的对象。然后交换FROM和TO。**这样也可以避免内存碎片的问题，但是会占用双倍的内存空间**。

**总结：**

| 标记清除  | 标记整理   | 复制         |
|-------|--------|------------|
| 速度快   | 速度慢    | 不会有内存碎片    |
| 有内存碎片 | 没有内存碎片 | 需要占用双倍内存空间 |

### 3、分代垃圾回收

长时间使用的对象放在老年代中（长时间回收一次，回收花费时间久）

用完即可丢弃的对象放在新生代中（频繁需要回收，回收速度相对较快）

![image-20231220162148438](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20231220162148438.png)

#### 1）回收流程

新创建的对象都被放在了**新生代的伊甸园**中

当伊甸园中的内存不足时，就会进行一次垃圾回收，这时的回收叫做 **Minor GC**【按照可达性算法】

Minor GC 会将**伊甸园和幸存区FROM**仍需要存活的对象**先**复制到 **幸存区 TO**中， 并让其**寿命加1**，清除伊甸园中不需要存活的对象，再
**交换FROM和TO**。【复制算法】

捋一下过程：

1. 伊甸园满了，进行第一次MinorGC：把伊甸园和幸存区FROM的对象放到TO区，并且寿命+1，清除伊甸园的对象和FROM区对象

![image-20231220162527949](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20231220162527949.png)

2. 交换FROM和TO区，这个时候伊甸园位置够了，可以继续放新对象了

![image-20231220162552294](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20231220162552294.png)

3. 如果伊甸园又满了，则进行第二次MinorGC，流程大致相同，存活对象寿命+1，以此类推

4. 如果幸存区中的对象的**寿命超过某个阈值**（最大为15，4bit），就会被**放入老年代**中

5. 如果新生代老年代中的内存都满了，就会先触发Minor Gc，再触发**Full GC**，扫描**新生代和老年代中**所有不再使用的对象并回收

**小结：**

- 对象首先分配在伊甸园区域
- 新生代空间不足时，触发 minor gc，伊甸园和 from 存活的对象使用 copy 复制到 to 中，存活的 对象年龄加 1并且交换 from to
- minor gc 会引发 stop the world，暂停其它用户的线程，等垃圾回收结束，用户线程才恢复运行
- 当对象寿命超过阈值时，会晋升至老年代，最大寿命是15（4bit）
- 当老年代空间不足，会先尝试触发 minor gc，如果之后空间仍不足，那么触发 full gc，STW的时间更长

**大对象处理策略：**
当遇到一个较大的对象时，就算新生代的伊甸园为空，也无法容纳该对象时，会将该对象直接晋升为老年代。

**线程内存溢出：**
某个线程的内存溢出了而抛异常（out of memory），不会让其他的线程结束运行。这是因为当一个线程抛出OOM异常后，它所占据的内存资源会全部被释放掉，从而不会影响其他线程的运行，进程依然正常。

#### 2）相关VM参数

| 含义               | 参数                                                          |
|:-----------------|:------------------------------------------------------------|
| 堆初始大小            | -Xms                                                        |
| 堆最大大小            | -Xmx 或-XX:MaxHeapSize=size                                  |
| 新生代大小            | -Xmn 或 (-XX:NewSize=size + -XX:MaxNewSize=size )            |
| 幸存区比例 (动态)       | -XX:InitialSurvivorRatio=ratio 和 -XX:+UseAdaptiveSizePolicy |
| 幸存区比例            | -XX:SurvivorRatio=ratio                                     |
| 晋升阈值             | -XX:MaxTenuringThreshold=threshold                          |
| 晋升详情             | -XX:+PrintTenuringDistribution                              |
| GC详情             | -XX:+PrintGCDetails -verbose:go                             |
| FullGC 前 MinorGC | -XX:+ScavengeBeforeFullGC                                   |

[JVM常用内存参数配置_jvm内存参数-CSDN博客](https://blog.csdn.net/wang379275614/article/details/78471604)

### 4、垃圾回收器

#### 1）串行

- 单线程

- 适用场景：内存较小，个人电脑（CPU核数较少）。

- 串行垃圾回收器开启语句：`-XX:+UseSerialGC = Serial + SerialOld`

  `Serial`：表示新生代，采用**复制算法**；`SerialOld`：表示老年代，采用的是**标记整理算法**。

![image-20231220172851652](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20231220172851652.png)

**安全点**：让其他线程都在这个点停下来，以免垃圾回收时移动对象地址，使得其他线程找不到被移动的对象。

因为是串行的，所以只有一个垃圾回收线程。且在该线程执行回收工作时，其他线程进入**阻塞**状态。

#### 2）吞吐量优先

- 多线程
- 适用场景：堆内存较大，多核CPU
- **单位时间内**，让STW（stop the world，停掉其他所有工作线程）时间最短【总垃圾回收时间占比低】
- **JDK1.8默认使用**的垃圾回收器

![image-20231220173238051](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20231220173238051.png)

```java
// 1.吞吐量优先垃圾回收器开关：（默认开启）
-XX:+UseParallelGC~-XX:+UseParallelOldGC
// 2.采用自适应的大小调整策略：调整新生代(伊甸园 + 幸存区FROM、TO)内存的大小
        -XX:+UseAdaptiveSizePolicy
// 3.调整吞吐量的目标：吞吐量 = 垃圾回收时间/程序运行总时间
        -XX:GCTimeRatio=ratio    //  公式1/(1+ratio)
// 4.垃圾收集最大停顿毫秒数：默认值是200ms
        -XX:MaxGCPaiseMillis=ms
// 5.控制ParallelGC运行时的线程数
        -XX:ParallelGCThreads=n
```

#### 3）响应时间优先

- 多线程
- 适用场景：堆内存较大，多核CPU
- 尽可能让**单次STW时间**变短（不影响其他线程运行）【单次垃圾回收时间低】

<img src="https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20231220173439807.png" alt="image-20231220173439807" style="zoom:150%;" />

```java
// 开关：
-XX:+UseConMarkSweepGC~-XX:+UseParNewGC~SerialOld
// ParallelGCThreads=n并发线程数 
// ConcGCThreads=threads并行线程数
        -XX:ParallelGCThreads=n~-XX:ConcGCThreads=threads
// 执行CMS垃圾回收的内存占比：预留一些空间保存浮动垃圾
        -XX:CMSInitiatingOccupancyFraction=percent
// 重新标记之前，对新生代进行垃圾回收
        -XX:+CMSScavengeBeforeRemark
```

CMS 收集器：Concurrent Mark Sweep，一种以获取**最短回收停顿时间**为目标的**老年代**收集器

#### 4）G1

取代了cms

适用场景

- 同时注重吞吐量（Throughput）和低延迟（Low latency），默认的暂停目标是 200 ms
- 超大堆内存，会将堆划分为多个**大小相等**的 Region
- 整体上是 **标记+整理** 算法，**两个区域之间是 复制 算法**

**相关参数**：JDK8 并不是默认开启的，需要参数开启：

```java
// G1开关
-XX:+UseG1GC
// 所划分的每个堆内存大小：
        -XX:G1HeapRegionSize=size
// 垃圾回收最大停顿时间
        -XX:MaxGCPauseMillis=time
```

##### G1垃圾回收阶段

<img src="https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20231222162657865.png" alt="image-20231222162657865" style="zoom:50%;" />

**新生代伊甸园垃圾回收**—–>**内存不足，新生代回收+并发标记**—–>**回收新生代伊甸园、幸存区、老年代内存**——>**新生代伊甸园垃圾回收
**(重新开始)。

##### Young Collection 新生代垃圾回收

**分区算法region**

分代是按对象的生命周期划分，分区则是将堆空间划分连续几个不同小区间，每一个小区间独立回收，可以控制一次回收多少个小区间，方便控制
GC 产生的停顿时间。

E：伊甸园 S：幸存区 O：老年代

- 垃圾回收时，会把伊甸园(E)的幸存对象复制到幸存区(S)：
- 当幸存区(s)中的对象也比较多触发垃圾回收，且幸存对象寿命超过阈值时，幸存区(S)中的一部分对象(寿命达到阈值)会晋升到老年代(
  O)，寿命未达到阈值的会被再次复制到另一个幸存区(S)：

![image-20231222163010470](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20231222163010470.png)

##### Young Collection + CM 新生代垃圾回收和并发标记

CM：并发标记！

- 在 Young GC 时会对 GC Root 进行初始标记。
- 在**老年代占用堆内存的比例达到阈值时，进行并发标记**（不会STW），阈值可以根据用户来进行设定：

```java
-XX:InitiatingHeapOccupancyPercent=percent // 默认值45%
```

##### Mixed Collection 混合收集

会对E、S 、O 进行**全面的回收**。

- 最终标记
- **拷贝**存活

```java
//  用于指定GC最长的停顿时间
-XX:MaxGCPauseMillis=ms
```

**问**：为什么有的老年代被拷贝了，有的没拷贝？

因为指定了最大停顿时间，如果对所有老年代都进行回收，耗时可能过高。为了保证时间不超过设定的最大停顿时间，会根据最大停顿时间，有选择的
**回收最有价值的老年代**（回收后，能够得到更多内存）。

![image-20231222163130275](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20231222163130275.png)

G1在老年代内存不足时（老年代所占内存超过阈值）：

- 如果垃圾产生速度慢于垃圾回收速度，不会触发Full GC，还是并发地进行清理。
- 如果垃圾产生速度快于垃圾回收速度，便会触发Full GC。

##### Full GC

- SerialGC
    - 新生代内存不足发生的垃圾收集 - minor gc
    - 老年代内存不足发生的垃圾收集 - full gc
- ParallelGC
    - 新生代内存不足发生的垃圾收集 - minor gc
    - 老年代内存不足发生的垃圾收集 - full gc
- CMS
    - 新生代内存不足发生的垃圾收集 - minor gc
    - 老年代内存不足发生的垃圾收集，需要分2种情况，这里不做详细介绍
- G1
    - 新生代内存不足发生的垃圾收集 - minor gc
    - 老年代内存不足发生的垃圾收集，需要分2种情况，这里不做详细介绍

##### Young Collection 跨代引用

- 新生代回收的跨代引用（老年代引用新生代）问题：
- 卡表与Remembered Set
    - Remembered Set 存在于E中，用于保存新生代对象对应的脏卡：
        - 脏卡：O被划分为多个区域（一个区域512K），如果该区域引用了新生代对象，则该区域被称为脏卡。
- 在引用变更时通过`post-write barried + dirty card queue`。
- concurrent refinement threads 更新 Remembered Set。

![image-20231222163202606](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20231222163202606.png)

##### Remark

- 重新标记阶段
- 在垃圾回收时，收集器处理对象的过程中：
    - 黑色：已被处理，需要保留的
    - 灰色：正在处理中的
    - 白色：还未处理的

但是在**并发标记过程中**，有可能A被处理了以后未引用C，但该处理过程还未结束，在处理过程结束之前A引用了C，这时就会用到remark。

过程如下：

- 之前C未被引用，这时A引用了C，就会给C加一个写屏障，写屏障的指令会被执行，将C放入一个队列当中，并将C变为 处理中 状态。
- 在**并发标记**阶段结束以后，重新标记阶段会STW，然后将放在该队列中的对象重新处理，发现有强引用引用它，就会处理它。

##### JDK 8u20 字符串去重

**优点与缺点**：

- 优点：节省了大量内存。
- 缺点：新生代回收时间增加，导致略微多占用CPU。

字符串去重开启指令 `-XX:+UseStringDeduplication`：

案例分析：

```java
String s1=new String("hello");// 底层存储为：char[]{'h','e','l','l','o'}
        String s2=new String("hello");// 底层存储为：char[]{'h','e','l','l','o'}
        12
```

- 将所有新分配的字符串（底层是`char[]`）放入一个队列。
- 当新生代回收时，G1并发检查是否有重复的字符串。
- 如果字符串的值一样，就让他们**引用同一个字符串对象**。
- 注意，其与 String.intern()  的区别：

    - `intern`关注的是字符串对象。
    - 字符串去重关注的是`char[]`数组。
    - 在JVM内部，使用了不同的字符串标。

##### JDK 8u40 并发标记类卸载

在所有对象经过并发标记阶段以后，就能知道哪些类不再被使用。如果一个类加载器的所有类都不在使用时，则卸载它所加载的所有类。

并发标记类卸载开启指令：`-XX:+ClassUnloadWithConcurrentMark` 默认启用。

##### JDK 8u60 回收巨型对象

- H表示巨型对象，当一个对象占用大于region的一半时，就称为巨型对象。
- G1不会对巨型对象进行拷贝。
- 回收时被优先考虑。
- G1会跟踪老年代所有incoming引用，如果老年代incoming引用为0的巨型对象就可以在新生代垃圾回收时处理掉。
- 巨型对象越早回收越好，最好是在新生代的垃圾回收就回收掉

![image-20231222163330525](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20231222163330525.png)

##### JDK 9 并发标记起始时间的动态调整

- 并发标记必须在堆空间占满前完成，否则就退化为 Full GC。
- JDK 9 之前需要使用 `-XX:InitiatingHeapOccupancyPercent` 设置阈值，默认是 `45%`。
- JDK 9 可以动态调整：(目的是尽可能的避免并发标记退化成 Full GC)
    - `-XX:InitiatingHeapOccupancyPercent`：用来设置**初始**阈值。
    - 在进行垃圾回收时，会进行数据采样并**动态调整阈值**。
    - 总会添加一个安全的空挡空间，用来容纳那些浮动的垃圾。

### 5、垃圾回收调优

-

查看虚拟机参数命令：`& "C:\Users\醒酒器\.jdks\corretto-1.8.0_372\bin\java" -XX:+PrintFlagsFinal -version | findstr "GC"`

![image-20231222161730841](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20231222161730841.png)

#### 1） 调优领域

- 内存
- 锁竞争
- CPU占用
- IO
- GC

#### 2）确定目标

低延迟/高吞吐量？ 根据需求选择合适的GC

- CMS、G1、ZGC
- ParallelGC
- Zing

#### 3） 最快的GC是不发生GC

首先排除减少因为自身编写的代码而引发的内存问题：

- 查看Full GC前后的内存占用，考虑以下几个问题：
    - 数据是不是太多？
        - `resultSet = statement.executeQuery("select * from 大表");`
        - 少写selete *
    - 数据表示是否太臃肿？
        - 对象图
        - 对象大小，Java中**new**一个Object或者包装类型对象，至少16字节，所以能用普通类还是普通类
    - 是否存在内存泄漏？
        - `static Map map = HashMap()`，当我们不断的向静态的map中新增对象且不移除，就可能造成内存吃紧。
        - 可以使用软引用，弱引用来解决上面的问题，因为它们可以在内存吃紧时，会被定期回收。也可以使用第三方的缓存中间件来存储上面的map中的数据。

#### 4）新生代调优

- 新生代的特点：

    - 当我们new一个对象时，会先在伊甸园中进行分配，所有的new 操作分配内存都是非常廉价且速度极快的

        - TLAB(Thread-Location Allocation Buffer)：当我们`new`一个对象时，会先检查TLAB缓冲区中是否有可用内存，如果有则优先在TLAB中进行对象分配。

    - 死亡对象回收的代价为零

    - 大部分对象用过即死

    - MInor GC 所用时间远小于Full GC

- 新生代内存越大越好么？

    - 不是：
        - 新生代内存太小：频繁触发Minor GC，会 STW，会使得吞吐量下降。
        - 新生代内存太大：老年代内存占比有所降低，会更频繁地触发Full GC。而且触发Minor GC时，清理新生代所花费的时间会更长。
    - 新生代内存设置为能容纳 `并发量*(请求-响应)` 的数据为宜。

#### 5） 幸存区调优

- 幸存区最大能够保存 **当前活跃对象**+**需要晋升的对象**。
- 晋升阈值配置得当，**让长时间存活的对象尽快晋升。**

#### 6）老年代调优

以CMS为例：

- CMS的老年代内存**越大越好**。

- 先尝试不做调优，如果没有 Full GC 那么说明当前系统暂时不需要优化，否则，就先尝试调优新生代。【尽量不要调优老年代，先去调优新生代】

- 观察发生Full GC 时老年代的内存占用，将老年代内存预设调大 1/4 ~ 1/3

- `-XX:CMSInitiatingOccupancyFraction=percent`【什么时候触发FullGC】

#### 7）案例

- 当Full GC 和 Minor GC 调用频繁。【设置的内存太小了】
- 当请求高峰期发生Full GC，单次暂停时间特别长(CMS)【降为串行的模式了】
- 在老年代充裕的情况下，发生Full GC (jdk1.7)
