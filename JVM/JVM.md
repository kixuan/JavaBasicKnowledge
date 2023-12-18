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

​ 二进制字节码 jvm指令 java源代码

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