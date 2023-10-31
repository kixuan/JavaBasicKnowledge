## 第10章

不记得什么时候做的笔记了、、、

### 10.1类变量和类方法

类变量：public **static** int age =18；
类方法：修饰符 static 数据返回类型 方法名(){};

特点：
1. 所有类对象共享
2. 在类加载的时候就已经创建了，类加载是会创建一个class对象，包括了类变量，所以不依赖于对象——可以直接用`类.类变量名`
   访问，也可以用`对象名.类变量名`（前提是满足修饰符访问权限，反正都是一个值
2. jdk8以后放在堆区域
2. 生命周期和类一样
2. 静态方法可以访问静态变量，但是没有this、super等和对象有关的关键词

当方法中不涉及到任何和对象相关的成员/不创建实例，则可以将方法设计成静态方法，当成一个工具去使用，提高开发效率

静态方法只能访问静态成员
非静态方法，都可以访问

### 10.2 理解 main 方法语法

public static void main(String[] args){}

（1）main方法是虚拟机调用；

（2）java虚拟机需要调用类的main()方法，所以该方法的访问权限必须是public；

（3）java虚拟机在执行main()方法时不必创建对象，所以该方法必须是static；

（4）该方法接受String类型的数组参数，该数组中保存执行java命令时传递给所运行的类的参数；

（5）例如：java 执行的程序 参数1 参数2 参数3


### 10.3 代码块

（1）相当于另外一种形式的构造器（对构造器的补充机制），可以做初始化的操作；

（2）使用场景：如果多个构造器中都有重复的语句，可以抽取到初始化块中，提高代码重用性；

（3）不管调用哪个代码块，创建对象，都会先调用代码块的内容；

（4）代码块调用的顺序优先于构造器。

细节讨论：

（1）static代码块也叫静态代码块，作用就是对类进行初始化，而且它随着类的加载而执行，并且只会执行一次。（因为类只会加载一次）如果是普通代码块，每创建一个对象，就执行。

（2）类什么时候被加载

① 创建对象实例时（new）；

② 创建子类对象实例，父类也会被加载；

③ 使用类的静态成员时（静态属性，静态方法）。

④ 使用子类的静态成员时，父类也会被加载。

## 第11章 枚举和注解

### 枚举

枚举属于一种特殊的类，里面只包含一组**有限的** **特定的**对象

**特点**

1、有限值

2、只读，不需要修改

**实现方式**

1、自定义类实现枚举

```java
//1. 将构造器私有化,目的防止直接 new
//2. 在Season 内部，通过为对象添加 public final static 修饰符对外暴露对象创建的对象
//3. 只提供get方法，不要提供set方法, 防止属性被修改
//4. 优化，可以加入 final 修饰符

class Season {
   private final String name;
   private final String desc;

   //定义了四个对象, 固定.
   public static final Season SPRING = new Season("春天", "温暖");
   public static final Season WINTER = new Season("冬天", "寒冷");
   public static final Season AUTUMN = new Season("秋天", "凉爽");
   public static final Season SUMMER = new Season("夏天", "炎热");


   private Season(String name, String desc) {
      this.name = name;
      this.desc = desc;
   }

   public String getName() {
      return name;
   }

   public String getDesc() {
      return desc;
   }

   @Override
   public String toString() {
      return "Season{" +
              "name='" + name + '\'' +
              ", desc='" + desc + '\'' +
              '}';
   }
}
```

2、 使用 enum 关键字实现枚举

```java
// 1. 使用关键字 enum 替代 class 默认继承 Enum 类，而且是个final类
// 2. 使用常量名(实参列表)简化psf定义，使用 ,号间隔
// 3. 定义常量对象必须写在前面
enum Season2 {
   SPRING("春天", "温暖"),
   WINTER("冬天", "寒冷"),
   AUTUMN("秋天", "凉爽"),
   SUMMER("夏天", "炎热"),
   what();   // 默认调用无参构造器，也可以省略小括号

   private String name;
   private String desc;

   private Season2() {
   }

   private Season2(String name, String desc) {
      this.name = name;
      this.desc = desc;
   }

   public String getName() {
      return name;
   }

   public String getDesc() {
      return desc;
   }

   @Override
   public String toString() {
      return "Season{" +
              "name='" + name + '\'' +
              ", desc='" + desc + '\'' +
              '}';
   }
}
```

**ENUM 常用方法**

1. **toString**：Enum 类已经重写过了，返回的是当前对象 名,子类可以重写该方法，用于返回对象的属性信息
2. **name**：返回当前对象名（常量名），子类中不能重写
3. ordinal：返回当前对象的位置号，默认从 0 开始
4. **values**：返回当前枚举类中所有的常量
5. valueOf：将字符串转换成枚举对象，要求字符串必须 为已有的常量名，否则报异常！
6. compareTo：比较两个枚举常量，**比较的就是编号/位置号**

**使用细节**

1. 使用 enum 关键字后，就不能再继承(extend)其它类了，因为 enum 会隐式继承 Enum，而 Java 是单继承机制。

2. 枚举类和普通类一样，可以实现接口(implements)

   ```java
   interface IPlaying {
       public void playing();
   }
   enum Music implements IPlaying {
       CLASSICMUSIC;
       @Override
       public void playing() {
           System.out.println("播放好听的音乐...");
       }
   }
   ```

**tips：使用javap进行反编译**

进入out文件夹，cmd进入需要反编译的文件夹，使用javap xxxx.class进行反编译

![image-20231010154956410](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20231010154956410.png)

### 注解

注解(Annotation)也被称为元数据(Metadata)，用于修饰解释 包、类、方法、属性、构造器、局部变量等数据信息。

和注释一样，注解不影响程序逻辑，但注解可以被编译或运行，相当于嵌入在代码中的补充信息。

三个基本的 Annotation:

1) @Override： 限定某个方法，是重写父类方法, 该注解只能用于方法
   1) @interface 表示一个 注解类，
2) @Deprecated:：用于表示某个程序元素(类, 方法等)已过时
   1) 即不在推荐使用，但是仍然可以使用，可以修饰方法，类，字段, 包, 参数等，
   2) 可以做版本升级过渡使用
3) @SuppressWarnings：抑制编译器警告
   1) all，抑制所有警告
   2) boxing，抑制与封装/拆装作业相关的警告
   3) cast，抑制与强制转型作业相关的警告
   4) dep-ann，抑制与淘汰注释相关的警告
   5) deprecation，抑制与淘汰的相关警告
   6) fallthrough，抑制与 switch 陈述式中遗漏 break 相关的警告
   7) finally，抑制与未传回 finally 区块相关的警告
   8) hiding，抑制与隐藏变数的区域变数相关的警告
   9) incomplete-switch，抑制与 switch 陈述式(enum case)中遗漏项目相关的警告
   10) javadoc，抑制与 javadoc 相关的警告
   11) null，抑制与空值分析相关的警告
   12) serial，抑制与可序列化的类别遗漏 serialVersionUID 栏位相关的警告
   13) unused，抑制与未用的程式码及停用的程式码相关的警告
   14) ...
   15) 作用范围是和你放置的位置相关

### 元注解

1. Retention //指定注解的作用范围，三种 SOURCE,CLASS,RUNTIME
2. Target // 指定注解可以在哪些地方使用
3. Documented //指定该注解是否会在 javadoc 体现
4. Inherited //子类会继承父类注解

## 第12章 异常

### 1、基本概念

Java语言中，将程序执行中发生的不正常情况称为“异常”。(开发过程中的语法错误和逻辑错误不是异常)

执行过程中所发生的异常事件可分为两大类：

- Error(错误)： **Java虚拟机无法解决的严重问题，程序会崩溃**。比如: StackOverflowError[栈溢出]和OOM(out ofmemory)。
- Exception: **其它因编程错误或偶然的外在因素导致的一般性问题**，可以使用针对性的代码进行处理。例如空指针访问，试图读取不存在的文件，网络连接中断等等

### 2、异常分类

Exception 分为两大类：运行时异常和编译时异常

**运行时异常**：编译器检查不出来。一般是指编程时的逻辑错误，是程序员应该避免其出现的异常。**java.lang.RuntimeException**
类及它的子类都是运行时异常对于运行时异常，可以不作处理，因为这类异常很普遍，若全处理可能会对程序的可读性和运行效率产生影响

- **NullPointerException** 空指针异常 ：试图在需要对象的地方使用null

- **ArithmeticException** 数学运算异常 ：例除以零

- **ArrayIndexOutOfBoundsException** 数组下标越界异常 ：用非法索引访问数组时抛出的异常

- **ClassCastException** 类型转换异常 ：将对象强制转换为不是实例的子类时

- **NumberFormatException** 数字格式不正确异常：将字符串转换成一种数值类型，但该字符串不能转换为适当格式时

**编译时异常**：编程时编译器检查出的异常，是编译器要求必须处置的异常

- **SQLException** //操作数据库时，查询表可能发生异常
- **IOException** //操作文件时，发生的异常
- **FileNotFoundException** //当操作一个不存在的文件时，发生异常
- **ClassNotFoundException** //加载类，而该类不存在时，异常
- **EOFException** // 操作文件，到文件未尾，发生异常
- **lllegalArguementException** //参数异常

tips：巧用图表获取类的继承关系

空格搜索

![image-20231011130956821](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20231011130956821.png)

异常体系图：【重要捏】

![image-20231011130553903](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20231011130553903.png)

### 3、异常处理方法

二选一，默认throws

1) try-catch-finally

- 快捷键 ctrl + alt + t -> 选中 try-catch
- 如果异常没有发生，则顺序执行try的代码块，不会进入到catch
- 异常发生了，则直接进入到catch块
- 不管是否发生异常，都执行某段代码(比如关闭连接，释放资源等)则使用finally，但它不是必要的

```java
        try{
        String str="韩顺平";
        int a=Integer.parseInt(str);
        System.out.println("数字："+a);
        }catch(NumberFormatException e){
        System.out.println("异常信息="+e.getMessage());
        }finally{
        System.out.println("finally代码块被执行...");
        }

        System.out.println("程序继续...");
```

- 可以有多个catch语句，捕获不同的异常(进行不同的业务处理)，要求父类异常在后，子类异常在前)

```java
        //1.如果try代码块有可能有多个异常
//2.可以使用多个catch 分别捕获不同的异常，相应处理
//3.要求子类异常写在前面，父类异常写在后面
        try{
                Person person=new Person();
                //person = null;
                System.out.println(person.getName());//NullPointerException
                int n1=10;
                int n2=0;
                int res=n1/n2;//ArithmeticException
                }catch(NullPointerException e){
                System.out.println("空指针异常="+e.getMessage());
                }catch(ArithmeticException e){
                System.out.println("算术异常="+e.getMessage());
                }catch(Exception e){
                System.out.println(e.getMessage());
                }finally{
                }
```

- 可以进行 try-finally 配合使用,这种用法相当于没有捕获异常，因此程序会直接崩掉/退出。应用场景:就是执行一段代码，*
  *不管是否发生异常，都必须执行某个业务逻辑**


2. throws

将发生的异常抛出，交给调用者(方法)来处理，最顶级的处理者就是JVM

- 如果一个方法不能确定如何处理这种异常，则显示地声明抛出异常，表明该方法将不对这些异常进行处理，而由该方法的调用者负责处理异常进行处理，

![image-20231011132550543](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20231011132550543.png)

- 在方法声明中用throws语句可以声明抛出异常的列表，throws后面的异常类型可以是方法中产生的异常类型，也可以是它的父类。

```java
    public void f2()throws FileNotFoundException,NullPointerException,ArithmeticException{
        FileInputStream fis=new FileInputStream("d://aa.txt");
        }
```

使用细节：

1. 对于编译异常，程序中必须处理，比如 try-catch 或者 throws
2. 对于运行时异常，程序中如果没有处理，默认就是throws的方式处理
3. 子类重写父类的方法时，对抛出异常的规定:**子类重写的方法，所抛出的异常类型要么和父类抛出异常一致，要么为父类抛出异常类型的子类型
   **
4. 在throws 过程中，如果有方法 try-catch，就相当于处理异常，就可以不必throws【二选一】
5. 编译异常和运行异常的区别：子类抛出编译异常时父类也必须抛出，子类抛出运行异常时父类可以不抛出

### 4、自定义异常

当程序中出现了某些“错误”但该错误信息并没有在Throwable子类中描述处理，这个时候可以自己设计异常类，用于描述该错误信息。

步骤：

1. 定义类:自定义异常类名(程序员自己写)继承Exception或RuntimeException
2. 如果继承Exception，属于编译异常
3. 如果继承RuntimeException，属于运行异常**(一般来说，继承RuntimeException)**

```java
public class CustomException {
   public static void main(String[] args) /*throws AgeException*/ {

      int age = 180;
      //要求范围在 18 – 120 之间，否则抛出一个自定义异常
      if (!(age >= 18 && age <= 120)) {
         //这里我们可以通过构造器，设置信息
         throw new AgeException("年龄需要在 18~120之间");
      }
      System.out.println("你的年龄范围正确.");
   }
}

//自定义一个异常
//老韩解读
//1. 一般情况下，我们自定义异常是继承 RuntimeException
//2. 即把自定义异常做成 运行时异常，好处时，我们可以使用默认的处理机制,比较方便
class AgeException extends RuntimeException {
   public AgeException(String message) {//构造器
      super(message);
   }
}
```

tips：注意throw和throws的区别

- throw 用于方法体中，后面跟异常对象
  `throw new AgeException("年龄需要在 18~120之间")`
- throws 用于方法声明处，后面跟异常类型
  `public void f2() throws FileNotFoundException`

练习：判断语句输出顺序

```java
public class Homework03 {
   public static void func() {//静态方法
      try {
         throw new RuntimeException();
      } finally {
         System.out.println("B");
      }
   }

   public static void main(String[] args) {//main方法
      try {
         func();
         System.out.println("A");
      } catch (Exception e) {
         System.out.println("C");
      }
      System.out.println("D");
   }
}

// B C D
```

## 第13章 常用类

### 1、包装类

分类：

| 基本数据类型  |    包装类    |
|:-------:|:---------:|
| boolean |  Boolen   |
|  char   | Character |
|  byte   |   Byte    |
|  short  |   Short   |
|   int   |  Integer  |
|  long   |   Long    |
|  float  |   Float   |
| double  |  Double   |

继承关系图：

![image-20231012210106815](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20231012210106815.png)

包装类 和 基本数据类型的相互转换：

1. 装箱: 基本类型->包装类型,反之，拆箱
2. jdk5 前使用手动装箱和拆箱方式【new Integer()和Integer。valueof()】
3. jdk5 以后(含idk5) 的自动装箱和拆箱方式【直接Integer a = 1】，自动装箱底层调用的是valueof方法

包装类型和 String 类型的相互转换

```java
// Integer转String的三种方法
Integer i=100;
        String str1=i+"";
        String str2=i.toString()
        String str3=String.valueOf(i)

// String转Integer的两种方法
        String str4="12345";
        Integer i2=Integer.parseInt(str4);//使用到自动装箱
        Integer i3=new Integer(str4);//构造器
```

Integer 类和 Character 类的常用方法

【笑死我了，韩老师：天哪这么多方法我的心都要碎了】

![image-20231012224553102](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20231012224553102.png)

要用再查，不用背捏

```java
    public static void main(String[]args){
        System.out.println(Integer.MIN_VALUE); //返回最小值
        System.out.println(Integer.MAX_VALUE);//返回最大值

        System.out.println(Character.isDigit('a'));//判断是不是数字
        System.out.println(Character.isLetter('a'));//判断是不是字母
        System.out.println(Character.isUpperCase('a'));//判断是不是大写
        System.out.println(Character.isLowerCase('a'));//判断是不是小写

        System.out.println(Character.isWhitespace('a'));//判断是不是空格
        System.out.println(Character.toUpperCase('a'));//转成大写
        System.out.println(Character.toLowerCase('A'));//转成小写

        }
```

#### Integer

创建机制：【有点重要】

```java
public class WrapperExercise02 {
    public static void main(String[] args) {
        Integer i = new Integer(1);
        Integer j = new Integer(1);
        // 对象 == 判断就是判断是否是同一个对象
        System.out.println(i == j);  //False

        Integer m = 1; //底层 Integer.valueOf(1);
        Integer n = 1;
        System.out.println(m == n); //T
        //  阅读源码Integer valueof(int)的源码/打断点
        //  1. 如果i 在 IntegerCache.low(-128)~IntegerCache.high(127),就直接从数组返回
        //  2. 如果不在 -128~127,就直接 new Integer(i)
        //  public static Integer valueOf(int i) {
        //     if (i >= IntegerCache.low && i <= IntegerCache.high)
        //         return IntegerCache.cache[i + (-IntegerCache.low)];
        //     return new Integer(i);
        // }

        //所以，这里主要是看范围 -128 ~ 127 就是直接返回；否则，就new Integer(xx);
        Integer x = 128;//底层Integer.valueOf(128);
        Integer y = 128;
        System.out.println(x == y);//False
    }
}
```

打断点看是否在同一个地址：

![image-20231012222906878](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20231012222906878.png)

只要有基本数据类型，== 判断的就是值是否相等

#### String

使用细节：

1. String 对象用于保存字符串，也就是一组字符序列

2. String类有**很多构造器**

```java
// 常用构造器
String s1=new String();
        String s2=new String(String original);
        String s3=new String(char[]a);
        String s4=new String(char[]a,int startIndex,int count)
        String s5=new String(byte[]b)
```

3. 实现了接口Serializable【String 可以串行化：可以在网络传输】
   接口 Comparable 【String 对象可以**比较大小**】

4. String 是final 类，不**能被其他的类继承**

5. 一定要注意：value 是一个final类型， 不可以修改：**即value不能指向新的地址，但是单个字符内容是可以变化**

创建方式

1. 直接赋值 String s ="hsp”
2. 调用构造器 String s2 = new String("hsp");

方式1:：先从常量池查看是否有”hsp”数据空间，如果有，直接指向; 如果s最终指向的是常量池的空间地址没有则重新创建，然后指向是*
*常量池的空间地址**。

方式2:：先在堆中创建空间，里面维护了value属性，指向常量池的hsp空间如果常量池没有"hsp"，重新创建，**如果有直接通过value指向常量池的地址
**。最终指向的是**堆中的空间地址**。

地址练习

```java
    public static void main(String[]args){
        String a="hsp"; //a 指向 常量池的 “hsp”
        String b=new String("hsp");//b 指向堆中对象
        System.out.println(a.equals(b)); //T，只比较值
        System.out.println(a==b); //F，比较指向对象
        // 当调用 intern 方法时，如果池已经包含一个等于此 String 对象的字符串 (用equals(Obiect) 方法确定)，则返回池中的字符串。
        // 否则，将此 String 对象添加到池中，并返回此 String 对象的引用
        // b.intern() 方法返回常量池地址
        System.out.println(a==b.intern()); //T，因为常量池已经有“hsp”了
        System.out.println(b==b.intern()); //F，b对象在堆中，b.intern()返回常量池地址，和b的堆中地址不一样
        }
```

字符串的特性

```java
String s1="hello";
        s1="haha":
// 创建了两个对象

        String s2="hello"+"abc";
// 创建了一个对象，编译器在底层自行优化

        String a="hello";
        String b="haha":
        String c=a+b;
// 创建了三个对象，底层是StringBuilder
//1. 先创建一个 StringBuilder sb = StringBuilder()
//2. 执行sb.append("hello");
//3. 执行sb.append("abc");
//4. String c= sb.toString()
// 最后其实是 c 指向堆中的对象的value[]指向池中 "helloabc"

        String d="helloabc";
        System.out.println(c==d);//F，d指向常量池地址，c指向堆中地址
        String e="hello"+"abc";//e指向常量池
        System.out.println(d==e);//T

//【重要规则】：常量相加看的是池，变量相加是在堆中
```

常用方法

String类是保存字符串常量的。每次更新都需要重新开辟空间，效率较低，因此java设计者还提供了StringBuilder 和 StringBuffer
来增强String的功能并提高效率。

- equals ()： 区分大小写，判断内容是否相等
- equalslgnoreCase ()：忽略大小写的判断内容是否相等
- length ()： 获取字符的个数，字符串的长度
- indexof()：获取字符在字符串中第1次出现的索引，索引从0开始,如果找不到,返回-1
- lastindexof()：获取字符在字符串中最后1次出现的索引，索引从0开始,如找不到,返回-1
- substring()：截取指定范围的子串，name.substring(6) 从索引6开始截取后面所有的内容，左开右闭
- trim ()：去前后空格
- charAt:获取某索引处的字符，**注意不能使用Str[index] 这种方式**
- toUpperCase()：转换成大写
- toLowerCase()：转换成小写
- concat()：拼接字符，可以连用
- replace()：替换字符串中的字符
- split()：分割字符串，以x为标准对String进行分割，返回一个数组，特殊字符需要转义
- compareTo ()：比较两个字符串的大小，前者大返回正数，相等返回0；长度相同返回字符ascii差值，不相同返回长度差值
- toCharArray ()：转换成字符数组
- format ()：格式字符串，%s 字符串 %c 字符 %d 整型 %.2f 浮点型

## 第14章 集合

**优点：**

1. 可以动态保存任意多个对象，使用比较方便!
2. 提供了一系列方便的操作对象的方法: add、remove、set、get等
3. 使用集合添加,删除新元素的示意代码- 简洁了

**分类：**【重要捏】

1. 集合主要是两组(单列集合 , 双列集合)
2. Collection 接口有两个重要的子接口 List＆Set , 他们的实现子类都是单列集合
3. Map 接口的实现子类 是双列集合，存放的 K-V

![image-20231015141656371](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20231015141656371.png)

### Collection

1. collection实现子类可以存放多个元素，每个元素可以是Object
2. 有些Collection的实现类，可以存放重复的元素，有些不可以
3. 有些Collection的实现类，有些是有序的(List)，有些不是有序(Set)
4. Collection接口没有直接的实现子类，是通过它的子接口Set 和 List 来实现的

1、常用方法

```java
// add:添加单个元素
list.add("jack");
// remove:删除指定元素
        list.remove(0);//删除第一个元素
        list.remove(true);//指定删除某个元素
//contains:查找元素是否存在
        System.out.println(list.contains("jack"));//T
//isEmpty:判断是否为空
        System.out.println(list.isEmpty());//F
//clear:清空
        list.clear();
//addAll:添加多个元素
        list.addAll(list2);
//containsAll:查找多个元素是否都存在
        System.out.println(list.containsAll(list2));//T
// removeAll：删除多个元素
        list.removeAll(list2);
```

2、遍历

```java
//1、使用 Iterator(迭代器)遍历
// 先得到 col 对应的 迭代器
Collection list=new ArrayList()
        Iterator iterator=list.iterator();
// 使用 while 循环遍历（itit快捷键）
        while(iterator.hasNext()){//判断是否还有数据
        //返回下一个元素，类型是 Object
        Object obj=iterator.next();
        System.out.println("obj="+obj);
        }

//2、for增强
        for(Object ob:list){
        System.out.println("Object="+ob);
        }
```

### List

Collection子类

1. List集合类中元素有序(即添加顺序和取出顺序一致)、且可重复
2. List集合中的每个元素都有其对应的顺序索引，即支持索引。
3. List容器中的元素都对应一个整数型的序号记载其在容器中的位置，可以根据序号存取容器中的元素。
4. 常用子类：ArrayList、LinkedList、Vector

因为是Collection子类，所以Collection有的它都有

```java
list.add(1,"韩顺平");// 增加索引位置，默认的话就是最后一个
        list.addAll(1,list2);
        list.set(1,"玛丽");// 修改
        list.subList(0,2);// 返回截断部分，闭区间
```

遍历方法和Collection一样的啦

#### ArrayList

List子类

1. permits all elements, including null, ArrayList 可以入null,并且多个
2. ArrayList 是由数组来实现数据存储的
3. ArrayList 基本等同于Vector，除了 ArrayList是线程不安全(执行效率高，但源码没有synchronized)
   。在多线程情况下，不建议使用ArrayList，用Vector

源码分析：

1. ArrayList中维护了一个Object类型的数组elementData。**transient** Objectl elementData；//transient 表示短暂的，表示该属性不会被序列化
2. 当创建ArrayList对象时，如果使用的是无参构造器，则初始elementData容量为0，第1次添加，则扩容elementData为10，如需要再次扩容，则扩容elementData为1.5倍
3. 如果使用的是指定大小的构造器，则初始elementData容量为指定大小，如果需要扩容则直接扩容elementData为1.5倍。
   老师建议: 自己去debug 一把我们的ArrayList的创建和扩容的流程

![image-20231015144910866](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20231015144910866.png)

#### Vector

List子类

1. Vector底层也是一个对象数组，protected Objectl] elementData;
2. Vector 是线程同步的，即线程安全,Vector类的操作方法带有synchronized
3. 在开发中需要线程同步安全时，考虑使用Vector

#### LinkedList

List子类

1. LinkedList底层实现了双向链表和双端队列特点
    1. LinkedList中维护了两个属性first和last分别指向 首节点和尾节点
    2. 每个节点 (Node对象)，里面又维护了prev、next、item三个属性，其中通过prev指向前一个，通过next指向后一个节点。最终实现双向链表.
    3. 所以LinkedList的元素的添加和删除，不是通过数组完成的，相对来说效率较高
2. 可以添加任意元素(元素可以重复)，包括null
3. 线程不安全，没有实现同步

LinkedList和ArrayList比较

![image-20231015145551359](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20231015145551359.png)

如何选择ArrayList和LinkedList:

1. 如果我们改查的操作多，选择ArrayList
2. 如果我们增删的操作多，选择LinkedList
3. **一般来说在程序中80%-90%都Set 接口也是 Collection 的子接口，因此，常用方法和 Collection 接口一是查询，因此大部分情况下会选择ArravList
   **
4. 在一个项目中，根据业务灵活选择，也可能这样，一个模块使用的是ArrayList,另外一个模块是LinkedList, 也就是说，要根据业务来进行选择

### Set

1. 无序(添加和取出的顺序不一致，但是取出顺序是固定的) ，**没有索引**，所以不能通过索引获取，遍历只能通过for和迭代器
2. 不允许重复元素，所以最多包含一个null
3. Set 接口也是 Collection 的子接口，因此，常用方法和 Collection 接口一样

#### HashSet

1. HashSet实现了Set接口
2. HashSet底层是HashMap，HashMap底层是数组+链表+红黑树
3. 同Set1、2

常用方法

```java
//1. 在执行 add 方法后，会返回一个 boolean 值
//2. 再说一遍，不可以添加相同元素
//3. 可以通过 remove 指定删除哪个对象
set=new Hash();

        set.add(new Dog("tom"));//OK
        set.add(new Dog("tom"));//Ok

        set.add(new String("hsp"));//ok
        set.add(new String("hsp"));//加入不辽，因为走的是常量池
```

add方法底层实现（hash() + equal()）

1. HashSet 底层是 HashMap
2. 添加一个元素时，先得到hash值 -会转成-> 索引值
3. 找到存储数据表table，看这个索引位置是否已经存放的有元素
4. 如果没有，直接加入
5. 如果有，调用 equals 比较，如果相同，就放弃添加，如果不相同，则添加到最后
6. 在Java8中，如果一条链表的元素个数到达 TREEIFY THRESHOLD(默认是 8)，并且table的大小 >=MIN TREEIFY CAPACITY(默认64)
   就会进行树化(红黑树)

haspMap扩容机制

1. HashSet底层是HashMap,第一次添加时，table 数组扩容到 16，临界值(threshold)是 16*加载因子(loadFactor)是0.75 = 12
2. 如果table 数组使用到了临界值 12,就会扩容到 16*2 = 32,新的临界值就是32*0.75 = 24,依次类推
3. 在Java8中，如果一条链表的元素个数到达 TREEIFY THRESHOLD(默认是 8)并且table的大小 >=MIN TREEIFY CAPACITY(默认64)
   就会进行树化(红黑树),否则仍然采用数组扩容机制

**通过重写hash()方法和equal()方法，可以改变HashSet的add机制**

```java
class Employee {
    private String name;
    private int age;

    public Employee(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }


    //如果name 和 age 值相同，则返回相同的hash值
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return age == employee.age &&
                Objects.equals(name, employee.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
}

```

#### LinkedHashSet

1. LinkedHashSet 是 HashSet 的子类
2. LinkedHashSet 底层是一个 LinkedHashMap，底层维护了一个 数组+ 双向链表
3. LinkedHashSet 根据元素的 hashCode 值来决定元素的存储位置，同时使用链表维护元素的次序，这使得元素**看起来是以插入顺序保存的
   **
4. LinkedHashSet 不允许添重复元素

#### TreeSet

1. 当我们使用无参构造器，创建 TreeSet 时，仍然是无序
2. 使用 TreeSet 提供的一个构造器，可以传入一个比较器(匿名内部类)，默认按照字符串大小来排序，也可以自己定义

```java
TreeSet treeSet=new TreeSet(new Comparator(){
@Override
public int compare(Object o1,Object o2){
        //下面 调用String的 compareTo方法进行字符串大小比较
        //return ((String) o2).compareTo((String) o1);

        //加入的元素按照长度大小排序
        return((String)o1).length()-((String)o2).length();
        }
        });
//添加数据
        treeSet.add("sp");
        treeSet.add("a");
        treeSet.add("abc");//3

        System.out.println("treeSet="+treeSet);
```

### Map[实用]

1. Map与Collection并列存在。用于保存具有映射关系的数据:Key-Value
2. Map 中的 key 和 value 可以是任何引用类型的数据，会封装到HashMap$Node对象中
3. Map 中的 key 不允许重复，原因和HashSet 一样，前面分析过源码；value 可以重复
4. Map 的key 和value 可以为null
5. 常用String类作为Map的 key
6. key 和 value 之间存在单向一对一关系，即通过指定的 key 总能找到对应的 value
7. Map存放数据的key-value示意图,一对 k-v 是放在一个HashMap$Node中的，有因为Node 实现了 Entry 接口，有些书上也说
   一对k-v就是一个Entry

常用方法：

```java
Map map=new HashMap();
        map.put("no1","韩顺平");//k-v
        map.put("no1","张三丰");//当有相同的 k , 就等价于替换
        map.put(null,null); //k-v
        map.put(new Object(),"金毛狮王");//k-v

        Object var=map.get("no1"));//张三丰
        map.remove(null); //remove:根据键删除映射关系
        int size=map.size();// size:获取元素个数
        boolean flag=map.isEmpty() //判断个数是否为 0
        boolean flag2=map.containsKey("hsp") //containsKey:查找键是否存在
        map.clear();//clear:清除 k-v
```

遍历方法

```java
Map map=new HashMap();
// 一、先取出所有的 Key , 通过 Key 取出对应的 Value
        Set keyset=map.keySet()

        //(1) 增强 for
        for(Object key:keyset){
        System.out.println(key+"-"+map.get(key));
        }

// (2) 迭代器
        Iterator iterator=keyset.iterator();
        while(iterator.hasNext()){
        Object key=iterator.next();
        System.out.println(key+"-"+map.get(key));
        }

// 二、把所有的 values 取出
        Collection values=map.value();
//这里可以使用所有的 Collections 使用的遍历方法
//(1) 增强 for
        for(Object value:values){
        System.out.println(value);
        }
//(2) 迭代器
        Iterator iterator2=values.iterator();
        while(iterator2.hasNext()){
        Object value=iterator2.next();
        System.out.println(value);
        }

//三、通过 EntrySet 来获取
        Set entrySet=map.entrySet();// EntrySet<Map.Entry<K,V>>
// (1) 增强 for
        for(Object entry:entrySet){
        //将 entry 转成 Map.Entry
        Map.Entry m=(Map.Entry)entry;
        System.out.println(m.getKey()+"-"+m.getValue());
        }
// (2) 迭代器
        Iterator iterator3=entrySet.iterator();
        while(iterator3.hasNext()){
        Object entry=iterator3.next();
        //System.out.println(next.getClass());//HashMap$Node -实现-> Map.Entry (getKey,getValue)
        //向下转型 Map.Entry
        Map.Entry m=(Map.Entry)entry;
        System.out.println(m.getKey()+"-"+m.getValue());
        }
```

#### HashMap

HashMap没有实现同步，因此是线程不安全的,方法没有做同步互斥的操作,没有
synchronized

扩容机制 [和HashSet相同]

1. HashMap底层维护了Node类型的数组table，默认为null
2. 当创建对象时，将加载因子(loadfactor)初始化为0.75
3. 当添加key-val时，通过key的哈希值得到在table的索引。然后判断该索引处是否有元素如果没有元素直接添加。如果该索引处有元素，继续判断该元素的key和准备加入的key相是否等，如果相等，则直接替换val;
   如果不相等需要判断是树结构还是链表结构，做出相应处理。如果添加时发现容量不够，则需要扩容。
4. 第1次添加，则需要扩容table容量为16，临界值(threshold)为12 (16*0.75)
5. 以后再扩容，则需要扩容table容量为原来的2倍(32)，临界值为原来的2倍,即24,依次类推
6. 在Java8中,如果一条链表的元素个数超过 TREEIFY THRESHOLD(默认是8)，并且table的大小>= MIN TREEIFY CAPACITY(默认64)
   ,就会进行树化(红黑树)

#### HashTable

1. 存放的元素是键值对: 即 K-V
2. hashtable的键和值都不能为null，否则会抛出NullPointerException
3. hashTable 使用方法基本上和HashMap一样
4. hashTable 是线程安全的(synchronized)，hashMap 是线程不安全的

和 HashMap 对比

![image-20231017191945109](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20231017191945109.png)

#### Properties

1. Properties类继承自Hashtable类并且实现了Map接口，也是使用一种键值对的形式来保存数据。
2. key 和 value 不能为 null
3. Properties 还可以用于 从 xxx.properties 文件中，加载数据到Properties类对象并进行读取和修改
4. 工作后 xxx.properties 文件通常作为配置文件，这个知识点在IO流举例,有兴趣可先看文章

#### TreeMap

1. 使用默认的构造器，创建TreeMap是无序的(也没有排序)
1. 可以改写构造器实现不同的排序

```java
TreeMap treeMap=new TreeMap(new Comparator(){
@Override
public int compare(Object o1,Object o2){
        //按照K(String) 的长度大小排序
        return((String)o2).length()-((String)o1).length();
        }
        });
        treeMap.put("jack","杰克");
        treeMap.put("tom","汤姆");
        treeMap.put("kristina","克瑞斯提诺");
        treeMap.put("smith","斯密斯");
        treeMap.put("hsp","韩顺平");//加入不了

        System.out.println("treemap="+treeMap);
```

### 总结-开发中如何选择集合实现类

![image-20231017192350086](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20231017192350086.png)

### Collections工具类

1. Collections 是一个操作 Set、List 和 Map 等集合的工具类
   、查询和修改等操作
2. Collections 中提供了一系列静态的方法对集合元素进行排序

常用方法：

1. reverse(List): 反转 List 中元素的顺序
2. shuffle(List):对 List 集合元素进行随机排序
3. sort(List): 根据元素的自然顺序对指定 List 集合元素按升序排序
4. sort(List，Comparator): 根据指定的 Comparator 产生的顺序对 List 集合元素进行排序
5. swap(List，int，int): 将指定 list 集合中的i处元素和j处元素进行交换
6. Object max(Collection): 根据元素的自然顺序，返回给定集合中的最大元素
7. Object max(Collection,Comparator): 根据 Comparator 指定的顺序,
   返回给定集合中的最大元素Object min(Collection)
8. Object min(Collection, Comparator)
9. int frequency(Collection，Object): 返回指定集合中指定元素的出现次数
10. void copy(List dest,List src): 将src中的内容复制到dest中
11. boolean replaceAll(List list， Object oldVal，Object newVal): 使用新值替换 List 对象的所有旧值应用案例演示

```java
List list=new ArrayList();
        Collections.reverse(list);    //反转 List 中元素的顺序
        Collections.shuffle(list);    //对 List 集合元素进行随机排序
        Collections.sort(list);       //根据元素的自然顺序对指定 List 集合元素按升序排序
//根据指定的 Comparator 产生的顺序对 List 集合元素进行排序
        Collections.sort(list,new Comparator(){
@Override
public int compare(Object o1,Object o2){
        return((String)o2).length()-((String)o1).length();
        }
        });
        Collections.swap(list,0,1);   //将指定 list 集合中的 i 处元素和 j 处元素进行交换
        Collections.max(list)
        Collections.min(list)


        System.out.println("tom出现次数="+Collections.frequency(list,"tom"));//返回指定集合中指定元素的出现次数

        Collections.copy(dest,list);  // 拷贝，记得先赋值，大小要一样
        Collections.replaceAll(list,"tom","汤姆"); // 替换
```

## 第15章 泛型

### 简单使用

使用传统方法的问题分析

1. 不能对加入到集合 ArrayList中的数据类型进行约束(不安全)
2. 遍历的时候，需要进行类型转换，如果集合中的数据量较大，对效率有影响

泛型的好处

1. 编译时检查添加元素的类型，提高了安全性
2. 减少了类型转换的次数,提高效率
3. 不再提示编译警告ClassCastException

泛型的声明

```java
interface 接口<T>{}和 class 类<KV>{}
//其中，T,K,V不代表值，而是表示类型
///任意字母都可以。常用T表示，是Type的缩写
```

泛型的实例化：

1. 要在类名后面指定类型参数的值/类型
2. 注意只能是引用类型，像Integer，不能是int
3. 不写的话默认就是Object

```java
	List<String> strList = new ArrayList<String>();
	Iterator< Customer> iterator = customers.iterator():

//来个粒子
	HashSet<Student> students = new HashSet<Student>
    HashSet<Student> students = new HashSet<>  // 简写
//使用泛型方式给 HashMap 放入学生对象  
	HashMap<String, Student> hm = new HashMap<String, Student>
	hm.put("milan", new Student("milan", 38)
	Iterator<Map.Entry<String, Student>> iterator = entries.iterator();
	System.out.println("==============================");
	while (iterator.hasNext()) {
		Map.Entry<String, Student> next = iterator.next();
		System.out.println(next.getKey() + "-" + next.getValue());
}
```

### 自定义泛型

#### 自定义泛型类

基本语法：

```java
class 类名<T,R...>{}//...表示可以有多个泛型成员

// 来个粒子
class Tiger<T, R, M> {
    String name;
    R r;
    M m;
    T t;
}
public Tiger(String name, R r, M m, T t) {//构造器使用泛型
    this.name = name;
    this.r = r;
    this.m = m;
    this.t = t;
}
public void setR(R r) {//方法使用到泛型
	this.r = r;
}
public M getM() {//返回类型可以使用泛型
    return m;
}
```

使用细节：

1. 普通成员可以使用泛型(属性、方法)
2. 使用泛型的数组，不能初始化（不确定分配多少内存）
3. 静态方法中不能使用类的泛型（因为静态是和类相关的，在类加载时，对象还没有创建）
4. 泛型类的类型，是在创建对象时确定的(因为创建对象时需要指定确定类型)
5. 如果在创建对象时，没有指定类型，默认为Object

#### 自定义泛型接口

1. 接口中，静态成员也不能使用泛型(这个和泛型类规定一样)
2. 泛型接口的类型，在继承接口或者实现接口时确定
3. 没有指定类型，默认为object

```java
interface IUsb<U, R> {
    void run(R r1, R r2, U u1, U u2);
}

interface IA extends IUsb<String, Double> {
}

class AA implements IA {
    @Override
    public void run(Double r1, Double r2, String u1, String u2) {
    }
}
```

#### 自定义泛型方法

```java
//修饰符 <T,R..>返回类型 方法名(参数列表){}

class Fish<T, R> {//泛型类

    public <U, M> void eat(U u, M m) {
    }//泛型方法 

    public <K> void hello(R r, K k) {
        System.out.println(r.getClass());
        System.out.println(k.getClass());
    }

    public static void main(String[] args) {
        Fish<String, ArrayList> fish = new Fish<>();
        fish.hello(new ArrayList(), 11.3f);
    }
```

注意细节

1. 泛型方法，可以定义在普通类中,也可以定义在泛型类中
2. 当泛型方法被调用时，类型会确定
3. public void eat(E e)，修饰符后没有<T,R..> eat方法不是泛型方法，而是使用了泛型

### 泛型的继承和通配符

```java
//1. 泛型不具备继承性
   List<Object> list = new ArrayList<String>(); // 错误
//2. <?>:支持任意泛型类型
//3. <? extends A>:支持A类以及A类的子类，规定了泛型的上限
//4. <? super A>:支持A类以及A类的父类，不限于直接父类，规定了泛型的下限
```

## 第17章 多线程基础

### 相关概念

程序：是为完成特定任务、用某种语言编写的一组指令的集合。简单的说:就是我们写的代码

进程：是程序的一次执行过程，或是正在运行的一个程序。是动态过程: 有它自身的产生、存在和消亡的过程

线程：线程由进程创建的，是进程的一个实体。一个进程可以拥有多个线程,如下图

- 单线程:同一个时刻，只允许执行一个线程
- 多线程: 同一个时刻，可以执行多个线程，比如: 一个qq进程，可以同时打开多个聊天窗口。

并发: 同一个时刻，多个任务交替执行，造成一种“貌似同时”的错觉，简单的说
单核cpu实现的多任务就是并发。

并行: 同一个时刻，多个任务同时执行。多核cpu可以实现并行。 

### 基本使用

#### 创建线程的两种方式

【threaduse.Thread02.java】

1. 继承Thread 类，该类就可以当作线程使用，重写 run方法【真正实现多线程的效果， 是 start0(), 而不是 run()】
2. 实现Runnable接口，重写 run方法
   - 如果这个类已经继承方法了，因为Java是单继承，就不能再继承Thread了，所以就采用这个方法
   - 正式调用可以创建一个Thread对象，把类（实现Runnable）放进去；
   - 也可以再创建一个线程代理类ThreadProxy（实现Runnable），通过这个ThreadProxy运行类（未实现Runnable）】

![image-20231020113225018](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20231020113225018.png)

区别：从java的设计来看，通过继承Thread或者实现Runnable接口来创建线程本质上没有区别。Thread类本身就实现了Runnable接口

**实现Runnable接口方式更加适合多个线程共享一个资源的情况，并且避免了单继承的限制,建议使用Runnable**

### 线程终止

1. 当线程完成任务后，会自动退出。
2. 还可以通过使用变量来控制run方法退出的方式停止线程，即通知方式【while (loop) + 外层修改 loop的值】

### 线程常用方法

1. setName：设置线程名称，使之与参数 name 相同
2. getName ：返回该线程的名称
3. start ：使该线程开始执行; Java 虚拟机底层调用该线程的 start0 方法
4. run ：调用线程对象 run 方法
5. setPriority ：更改线程的优先级
6. getPriority ：获取线程的优先级
7. sleep ：在指定的毫秒数内让当前正在执行的线程休眠(暂停执行)
8. interrupt：中断线程≠结束线程
9. yield：线程的礼让。让出cpu，让其他线程执行，但礼让的时间不确定，所以也不一定礼让成功
10. join：线程的插队。插队的线程一旦插队成功，则肯定先执行完插入的线程所有的任务

Tips：start 底层会创建新的线程，调用run，run 就是一个简单的方法调用，不会启动新线程

### 用户线程和守护线程

1. 用户线程：也叫工作线程，当线程的任务执行完或通知方式结束守护线程
2. 守护线程：一般是为工作线程服务的，当所有的用户线程结束，守护线程自动结束【Thread.setDaemon(true);】
3. 常见的守护线程: 垃圾回收机制

### 线程的生命周期

Thread.State（枚举类）：线程可以处于以下状态之一:
NEW：尚未启动的线程
RUNNABLE：在Java虚拟机中执行的线程
BLOCKED：被阻塞等待监视器锁定的线程
WAITING：正在等待另一个线程执行特定动作的线程
TIMED WAITING：正在等待另一个线程执行动作达到指定等待时间的线程
TERMINATED：已退出的线程

直接调用thread.getState()方法获取线程现在的状态

线程状态转换图

![image-20231021234314939](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20231021234314939.png)

### 线程的同步

1. 在多线程编程，一些敏感数据不允许被多个线程同时访问，此时就使用同步访问技术，保证数据在任何同一时刻，最多有一个线程访问，以保证数据的完整性。
2. 也可以这里理解：线程同步，即当有一个线程在对内存进行操作时，其他线程都不可以对这个内存地址进行操作，直到该线程完成操作，其他线程才能对该内存地址进行操作.

### Synchronized

```java
// 1.同步代码块
synchronized (对象){ // 得到对象的锁，才能操作同步代码
	// 需要被同步代码;
}

// 2.synchronized还可以放在方法声明中，表示整个方法-为同步方法
public synchronized void m (String name){
    	//需要被同步的代码
}
```

### 互斥锁

1. Java语言中，引入了对象互斥锁的概念，来保证共享数据操作的完整性。
2. 每个对象都对应于一个可称为“互斥锁”的标记，这个标记用来保证在任一时刻，只能有一个线程访问该对象。
3. 关键字synchronized 来与对象的互斥锁联系。当某个对象用synchronized修饰时3表明该对象在任一时刻只能由一个线程访问
4. 同步的局限性:导致程序的执行效率要降低
5. 同步方法(非静态的)的锁可以是this,也可以是其他对象(要求是同一个对象)
6. 同步方法(静态的)的锁为当前类本身。

### 线程的死锁

多个线程都占用了对方的锁资源，但不肯相让，导致了死锁，在编程是一定要避
免死锁的发生。

### 释放锁

1. 当前线程的同步方法、同步代码块执行结束
2. 在同步代码块、同步方法中遇到break、return
3. 当前线程在同步代码块同步方法中出现了未处理的Error或Exception，导致异常结束
4. 当前线程在同步代码块、同步方法中执行了线程对象的wait())方法，当前线程暂停，并释放锁。

不释放锁的两种情景：

1. 线程执行同步代码块或同步方法时，程序调用Thread.sleep()、Thread.yield()方法暂停当前线程的执行,不会释放锁
2. 线程执行同步代码块时，其他线程调用了该线程的suspend()方法将该线程挂起该线程不会释放锁。提示: 应尽量避免使用suspend())
   和resume()来控制线程，方法不再推荐使用

## 第19章 IO流

### 基本操作

文件：保存数据的地方

文件流：数据在数据源(文件)和程序(内存)之间经历的路径输入流，文件在程序中是以流的形式来操作的

输入流：数据从数据源(文件)到程序(内存)的路径

输出流：数据从程序(内存)到数据源(文件)的路径

【注意这里的输入输出是相对于内存而言的】

创建文件对象的三种方式：

1. `new File(String pathname) `//根据路径构建一个File对象
2. `new File(File parent,String child)`//根据父目录文件+子路径构建
3. `new File(String parent,String child)`//根据父目录+子路径构建

获取文件的相关信息

```java
    public void info(){
        File file=new File("e:\\news1.txt");
        try{
        file.createNewFile();
        }catch(IOException e){
        throw new RuntimeException(e);
        }
        System.out.println("文件名字="+file.getName());
        System.out.println("文件绝对路径="+file.getAbsolutePath());
        System.out.println("文件父级目录="+file.getParent());
        System.out.println("文件大小(字节)="+file.length());
        System.out.println("文件是否存在="+file.exists());//T
        System.out.println("是不是一个文件="+file.isFile());//T
        System.out.println("是不是一个目录="+file.isDirectory());//F
        }
```

目录的操作和文件删除

```java
exists();   //是否存在
        delete();   //删除
        mkdir();    // 创建一级目录
        mkdirs();   // 创建多级目录
```

### IO流

#### 流的分类

- 按操作数据单位不同分为: 字节流(8 bit) 二进制文件，字符流(按字符)文本文件

- 按数据流的流向不同分为: 输入流，输出流

- 按流的角色的不同分为: 节点流，处理流/包装流

![image-20231023235019574](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20231023235019574.png)

#### IO流体系图

这个图很重要捏

![image-20231023235048655](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20231023235048655.png)

### 1、FileInputStream & FileOutputStream

字节输入流 文件--> 程序

1. FileInputStream 代码示例

```java
        // 使用 read(byte[] b) 读取文件，提高效率
		String filePath="e:\\hello.txt";
                byte[]buf=new byte[8]; //一次读取8个字节.
                int readLen=0;
                FileInputStream fileInputStream=null;
                try{
                //创建 FileInputStream 对象，用于读取 文件
                fileInputStream=new FileInputStream(filePath);
                //从该输入流读取最多b.length字节的数据到字节数组。 此方法将阻塞，直到某些输入可用。
                //如果返回-1 , 表示读取完毕
                while((readLen=fileInputStream.read(buf))!=-1){
                System.out.print(new String(buf,0,readLen));
                }
                }catch(IOException e){
                e.printStackTrace();
                }finally{
                try{
                fileInputStream.close();
                }catch(IOException e){
                e.printStackTrace();
                }
                }
```

2. FileOutputStream代码示例

```java
String filePath="e:\\a.txt";
        FileOutputStream fileOutputStream=null;
        try{
        // 有true的话就是：写入内容是追加到文件后面，没有覆盖
        fileOutputStream=new FileOutputStream(filePath,true);
        String str="hsp,worl
        fileOutputStream.write(str.getBytes(),0,3);
        }catch(IOException e){
        e.printStackTrace();
        }finally{
        try{
        fileOutputStream.close();
        }catch(IOException e){
        e.printStackTrace();
        }
        }
```

3. 结合示例：拷贝啦

```java
    public static void main(String[]args){
        //1. 创建文件的输入流 , 将文件读入到程序
        //2. 创建文件的输出流， 将读取到的文件数据，写入到指定的文件.
        String srcFilePath="e:\\Koala.jpg";
        String destFilePath="e:\\Koala3.jpg";
        FileInputStream fileInputStream=null;
        FileOutputStream fileOutputStream=null;
        try{
        fileInputStream=new FileInputStream(srcFilePath);
        fileOutputStream=new FileOutputStream(destFilePath);
        //定义一个字节数组,提高读取效果
        byte[]buf=new byte[1024];
        int readLen=0;
        while((readLen=fileInputStream.read(buf))!=-1){
        //读取到后，就写入到文件 通过 fileOutputStream
        fileOutputStream.write(buf,0,readLen);//一定要使用这个方法
        }
        System.out.println("拷贝ok~");
        }catch(IOException e){
        e.printStackTrace();
        }finally{
        try{
        if(fileInputStream!=null){
        fileInputStream.close();
        }
        if(fileOutputStream!=null){
        fileOutputStream.close();
        }
        }catch(IOException e){
        e.printStackTrace();
        }
        }
        }
```

### 2、FileReader & FileWriter

FileReader ：怎么感觉和FileInputStream差不多的

FileWriter：使用后，必须要关闭(close)或刷新(flush)，否则写入不到指定的文件!

### 节点流和处理流

定义：

节点流：可以从一个特定的数据源读写数据，如FileReader、FileWriter

处理流(/包装流)：是“连接”在已存在的流(节点流或处理流)之上，为程序提供更为强大的读写功能，也更加灵活,如BufferedReader、BufferedWriter

节点流和处理流的区别和联系：

1. 节点流是底层流/低级流,直接跟数据源相接。便的方法来完成输入输出。
2. 处理流(包装流)包装节点流，既可以消除不同节点流的实现差异，也可以提供更方便

处理流的作用：

1. 性能的提高:主要以增加缓冲的方式来提高输入输出的效率用更加灵活方便
2. 操作的便捷:处理流可能提供了一系列便捷的方法来一次输入输出大批量的数据，使

节点流和处理流一览图

![image-20231024001941083](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20231024001941083.png)

#### 1、BufferedReader & BufferedWriter

怎么感觉也是一样的—。—

就是close的时候不用判断罢了

注意事项：不要去操作 二进制文件[声音，视频，doc, pdf ], 可能造成文件损坏

#### 2、BufferedInputStream & BufferedOutputStream

BufferedInputStream是字节流在创建 BufferedInputStream时，会创建一个内部缓冲区数组

BufferedOutputStream是字节流，实现缓冲的输出流，可以将多个字节写入底层输出流中，而不必对每次字节写入调用底层系统

这个就可以操作二进制文件[声音，视频，doc, pdf ]

#### 3、ObjectInputStream & ObjectOutputStream

对象流

序列化和反序列化

- 序列化就是在保存数据时，保存数据的值和数据类型

- 反序列化就是在恢复数据时，恢复数据的值和数据类型

- 需要让某个对象支持序列化机制，则必须让其类是可序列化的，为了让某个类是可序列化的，该类必须实现如下接口：`Serializable`
- ObjectInputStream 实现了序列化，ObjectOutputStream实现了反序列化

注意事项和细节说明

- 读写顺序要一致
- 要求序列化或反序列化对象 ，需要 实现 Serializable
- 序列化的类中建议添加SerialVersionUID,为了提高版本的兼容性
- 序列化对象时，默认将里面所有属性都进行序列化，但除了static或transient修饰的成员
- 序列化对象时，要求里面属性的类型也需要实现序列化接口
- 序列化具备可继承性,也就是如果某类已经实现了序列化，则它的所有子类也已经默认实现了序列化

```java
// 1. 创建对象流
ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(filePath));
        oos.writeInt(100);// int -> Integer (实现了 Serializable)
        oos.writeBoolean(true);// boolean -> Boolean (实现了 Serializable)
        oos.writeChar('a');// char -> Character (实现了 Serializable)
        oos.writeDouble(9.5);// double -> Double (实现了 Serializable)
        oos.writeUTF("韩顺平教育");//String
//保存一个 dog 对象
        oos.writeObject(new Dog("旺财",10,"日本","白色"));
        oos.close();

// 2. 读取对象流
        ObjectInputStream ois=new ObjectInputStream(new FileInputStream(filePath));
// 注意顺序
        System.out.println(ois.readInt());
        System.out.println(ois.readBoolean());
        System.out.println(ois.readChar());
        System.out.println(ois.readDouble());
        System.out.println(ois.readUTF());
        System.out.println(ois.readObject());
        oos.close();
```

#### 4、标准输入输出流

|                 | 类型          | 默认设备 |
|-----------------|-------------|------|
| System.in 标准输入  | InputStream | 键盘   |
| System.out 标准输出 | PrintStream | 显示器  |

System.out.println(“”); 是使用 out 对象将 数据输出到 显示器
Scanner 是从 标准输入 键盘接收数据

#### 5、InputStreamReader & OutputStreamWriter

转换流：解决文件乱码问题，底层原理是将字符文件转为二进制文件

InputStreamReader ：将字节流 FileInputStream 转成字符流，还可以指定编码gbk/utf-8

```java
//1. 把 FileInputStream 转成 InputStreamReader，并指定编码 gbk
InputStreamReader isr=new InputStreamReader(new FileInputStream(filePath),"gbk");
//3. 把 InputStreamReader 传入 BufferedReader
        BufferedReader br=new BufferedReader(isr);

        String s=br.readLine();
        System.out.println("读取内容="+s);
        br.close();
```

OutputStreamWriter：将字节流 FileOutputStream 包装成(转换成)字符流OutputStreamWriter，对文件进行写入(
按照gbk格式,可以指定其他，比如utf-8)

```java
String filePath="e:\\xxz.txt";
        String charSet="utf-8";
        OutputStreamWriter osw=new OutputStreamWriter(new FileOutputStream(filePath),charSet);
        osw.write("hi, 迅迅子");
        osw.close();
        System.out.println("按照 "+charSet+" 保存文件成功");
```

#### 6、PrintStream & PrintWrite

PrintStream extends FilterOutputStream

打印流，只有输出流，没有输入流

```java
PrintStream out=System.out;
        out.print("john, hello");
        out.write("xxz,你好".getBytes());
        out.close();
// 修改打印流输出的位置/设备
        System.setOut(new PrintStream("e:\\f1.txt"));
        System.out.println("hello, xxz");
```

### Properties 类

更加方便地解析Properties 文件

键值对不需要有空格，值不需要用引号一起来。默认类型是String

常用方法：

- load: 加载配置文件的键值对到Properties对象
- list:将数据显示到指定设备
- getProperty(key):根据键获取值
- setProperty(key;value):设置键值对到Properties对象
- store:将Properties中的键值对存储到配置文件,在idea 中，保存信息到配置文件，如果含有中文，会存储为unicode码

```java
   //1. 创建Properties 对象
        Properties properties=new Properties();
                //2. 加载指定配置文件
                properties.load(new FileReader("E:\\Project\\mysql.properties"));
                //3. 把k-v显示控制台
                properties.list(System.out);
                //4. 根据key 获取对应的值
                String user=properties.getProperty("user");
                String pwd=properties.getProperty("pwd");
                System.out.println("用户名="+user);
                System.out.println("密码是="+pwd);

                properties.setProperty("user","迅迅子");//注意保存时，是中文的 unicode码值
                properties.store(new FileOutputStream("E:\\Project\\mysql3.properties"),null);
```

## 第21章 网络编程

### 相关概念

网络通信：将数据通过网络从一台设备传输到另一台设备

网络：两台或多台设备通过一定物理设备连接起来构成了网络

- 根据网络的覆盖范围不同，对网络进行分类:
- 局域网：覆盖范围最小，仅仅覆盖一个教室或一个机房
- 城域网： 覆盖范围较大，可以覆盖一个城市
- 广域网：覆盖范围最大，可以覆盖全国，甚至全球，万维网是广域网的代表

ip 地址：用于唯一标识网络中的每台计算机/主机（ipconfig）

- ip地址的组成=网络地址+主机地址，比如: 192.168.16.69
- IPv4网络地址资源有限，所以现在开始使用IPv6

域名：将ip地址映射成域名（为了方便记忆，解决记ip的困难

端口号：用于标识计算机上某个特定的网络程序

- 表示形式: 以整数形式，端口范围0~655352个字节表示端口 [0~2^16-1]
- 0~1024已经被占用，比如 ssh 22,ftp 21,smtp 25 http 80
- 常见的网络程序端口号:
    - tomcat :8080
    - mysql:3306
    - oracle:1521
    - sqlserver:1433

网络通信协议

- 协议TCP/IP (Transmission ControlProtocol/Internet Protocol)
  ，网络通讯协议，这个协议是Internet最基本的协议、Internet国际互联网络的基础，简单地说，就是由网络层的IP协议和传输层的TCP协议组成的。
  ![image-20231030152621939](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20231030152621939.png)
- ![image-20231030152714825](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20231030152714825.png)

TCP 和 UDP

- TCP协议：传输控制协议【better】
    1. 使用TCP协议前，须先建立TCP连接，形成传输数据通道
    2. 传输前，采用”三次握手"方式，是可靠的
    3. TCP协议进行通信的两个应用进程: 客户端、服务端
    4. 在连接中可进行大数据量的传输
    5. 传输完毕，需释放已建立的连接，效率低
- UDP协议：用户数据协议
    1. 将数据、源、目的封装成数据包，不需要建立连接
    2. 每个数据报的大小限制在64K内,不适合传输大量数据
    3. 因无需连接，故是不可靠的
    4. 发送数据结束时无需释放资源(因为不是面向连接的)，速度快

### InetAddress 类

相关方法

```java
//1. 获取本机的InetAddress 对象
InetAddress localHost=InetAddress.getLocalHost();
        System.out.println(localHost);//LAPTOP-AV2CUG49/192.168.97.86

//2. 根据指定主机名 获取 InetAddress对象
        InetAddress host1=InetAddress.getByName("LAPTOP-AV2CUG49");
        System.out.println("host1="+host1);//LAPTOP-AV2CUG49/192.168.97.86

//3. 根据域名返回 InetAddress对象, 比如 www.baidu.com 对应
        InetAddress host2=InetAddress.getByName("www.baidu.com");
        System.out.println("host2="+host2);//www.baidu.com / 14.119.104.227

//4. 通过 InetAddress 对象，获取对应的地址
        String hostAddress=host2.getHostAddress();
        System.out.println("host2 对应的ip = "+hostAddress);//110.242.68.4

//5. 通过 InetAddress 对象，获取对应的主机名/或者的域名
        String hostName=host2.getHostName();
        System.out.println("host2对应的主机名/域名="+hostName); // www.baidu.com
```

### Socket

1. 套接字(Socket)开发网络应用程序被广泛采用，以至于成为事实上的标准。
2. 通信的**两端都要有Socket**，是两台机器间通信的端点
3. 网络通信其实就是Socket间的通信。
4. Socket允许程序把网络连接当成一个流，数据在两个Socket间通过IO传输
5. 一般主动发起通信的应用程序属客户端，等待通信请求的为服务端

![image-20231030153500542](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20231030153500542.png)

【示例】编写一个服务端和一个客户端，服务器端在 9999端口监听，客户端连接到服务端，发送“hello, server”，并接收服务器端回发的"
hello,client”再退出服务器端接收到客户端发送的信息，输出，并发送“hello,client”，再退出

【服务端代码】

```java

@SuppressWarnings({"all"})
public class SocketTCP03Server {
    public static void main(String[] args) throws IOException {
        //思路
        //1. 在本机 的9999端口监听, 等待连接
        //   细节: 要求在本机没有其它服务在监听9999
        //   细节：这个 ServerSocket 可以通过 accept() 返回多个Socket[多个客户端连接服务器的并发]
        ServerSocket serverSocket = new ServerSocket(9999);
        System.out.println("服务端，在9999端口监听，等待连接..");
        //2. 当没有客户端连接9999端口时，程序会 阻塞, 等待连接
        //   如果有客户端连接，则会返回Socket对象，程序继续
        Socket socket = serverSocket.accept();
        System.out.println("服务端 socket =" + socket.getClass());

        //3. 通过socket.getInputStream() 读取客户端写入到数据通道的数据, 显示
        InputStream inputStream = socket.getInputStream();
        //4. IO读取, 使用字符流, 老师使用 InputStreamReader 将 inputStream 转成字符流
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String s = bufferedReader.readLine();
        System.out.println(s);//输出

        //5. 获取socket相关联的输出流
        OutputStream outputStream = socket.getOutputStream();
        //    使用字符输出流的方式回复信息
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
        bufferedWriter.write("hello client 字符流");
        bufferedWriter.newLine();// 插入一个换行符，表示回复内容的结束
        bufferedWriter.flush();//注意需要手动的flush


        //6.关闭流和socket
        bufferedWriter.close();
        bufferedReader.close();
        socket.close();
        serverSocket.close();//关闭

    }
```

【客户端代码】

```java

@SuppressWarnings({"all"})
public class SocketTCP03Client {
    public static void main(String[] args) throws IOException {
        //思路
        //1. 连接服务端 (ip , 端口）
        //解读: 连接本机的 9999端口, 如果连接成功，返回Socket对象
        Socket socket = new Socket(InetAddress.getLocalHost(), 9999);
        System.out.println("客户端 socket返回=" + socket.getClass());
        //2. 连接上后，生成Socket, 通过socket.getOutputStream()
        //   得到 和 socket对象关联的输出流对象
        OutputStream outputStream = socket.getOutputStream();
        //3. 通过输出流，写入数据到 数据通道, 使用字符流
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
        bufferedWriter.write("hello, server 字符流");
        bufferedWriter.newLine();//插入一个换行符，表示写入的内容结束, 注意，要求对方使用readLine()!!!!
        bufferedWriter.flush();// 如果使用的字符流，需要手动刷新，否则数据不会写入数据通道


        //4. 获取和socket关联的输入流. 读取数据(字符)，并显示
        InputStream inputStream = socket.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String s = bufferedReader.readLine();
        System.out.println(s);

        //5. 关闭流对象和socket, 必须关闭
        bufferedReader.close();//关闭外层流
        bufferedWriter.close();
        socket.close();
        System.out.println("客户端退出.....");
    }
}
```

### TCP 网络通信编程

1. 基于客户端一服务端的网络通信
2. 底层使用的是TCP/IP协议
3. 应用场景举例: 客户端发送数据，服务端接受并显示控制台
4. 基于Socket的TCP编程

netstat 指令

- netstat -an 可以查看当前主机网络情况，包括端口监听情况和网络连接情况
- netstat -an | more 可以分页显示
- 说明：
    - Listening 表示某个端口在监听
    - 如果有一个外部程序(客户端)连接到该端口，就会显示一条连接信息
    - ctrl + c 退出指令

## 第23章 反射

使用情况：通过外部文件配置，在不修改源码情况下来控制程序，也符合设计模式的 ocp原则(开闭原则: 不修改源码，扩容功能)

### 反射机制

允许程序在执行期借助于ReflectionAPI取得任何类的内部信息(比如成员变量，构造器，成员方法等等)，并能操作对象的属性及方法。反射在设计模式和框架底层都会用到

加载完类之后，在堆中就产生了一个Class类型的对象 (一个类只有一个Class对象)
，这个对象包含了类的完整结构信息。通过这个对象得到类的结构。这个Class对象就像一面镜子，透过这个镜子看到类的结构，所以，形象的称之为:
反射

反射机制原理图：

![image-20231030154654814](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20231030154654814.png)

反射可以完成：

1. 在运行时判断任意一个对象所属的类（newInstance()）
2. 在运行时构造任意一个类的对象（getMethod()）
3. 在运行时得到任意一个类所具有的成员变量和方法（getField("age")）
4. 在运行时调用任意一个对象的成员变量和方法（getConstructor()）
5. 生成动态代理

```java
// String classfullpath = "com.hspedu.Cat"
// String methodName = "hi"
    //2. 使用反射机制解决
        //(1) 加载类, 返回Class类型的对象cls
        Class<?> cls = Class.forName(classfullpath);
        //(2) 通过 cls 得到你加载的类 com.hspedu.Cat 的对象实例
        Object o = cls.newInstance();
        System.out.println("o的运行类型=" + o.getClass()); //运行类型
        //(3) 通过 cls 得到你加载的类 com.hspedu.Cat 的 methodName"hi"  的方法对象
        //    即：在反射中，可以把方法视为对象（万物皆对象）
        Method method1 = cls.getMethod(methodName);
        //(4) 通过method1 调用方法: 即通过方法对象来实现调用方法
        method1.invoke(o); //传统方法 对象.方法() , 反射机制 方法.invoke(对象)

        //java.lang.reflect.Field: 代表类的成员变量, Field对象表示某个类的成员变量
        //得到name字段
        //getField不能得到私有的属性
        Field nameField = cls.getField("age"); //
        System.out.println(nameField.get(o)); // 传统写法 对象.成员变量 , 反射 :  成员变量对象.get(对象)

        //java.lang.reflect.Constructor: 代表类的构造方法, Constructor对象表示构造器
        Constructor<?> constructor = cls.getConstructor(); //()中可以指定构造器参数类型, 返回无参构造器
        System.out.println(constructor);//Cat()


        Constructor<?> constructor2 = cls.getConstructor(String.class); //这里老师传入的 String.class 就是String类的Class对象
        System.out.println(constructor2);//Cat(String name)
```

优点: 可以动态的创建和使用对象(也是框架底层核心)，使用灵活,没有反射机制，框架技术就失去底层支撑。

缺点: 使用反射基本是解释执行，对执行速度有影响

### Class类

1. Class也是类，因此也继承Object类
2. Class类对象不是new出来的，而是系统创建的
3. 对于某个类的Class类对象，在内存中只有一份，因为类只加载一次
4. 每个类的实例都会记得自己是由哪个 Class 实例所生成
5. 通过Class对象可以完整地得到一个类的完整结构,通过一系列APIClass对象是存放在堆的
6. 类的字节码二进制数据，是放在方法区的，有的地方称为类的元数据(包括
   方法代码变量名，方法名，访问权限等等) https://www.zhihu.com/question/38496907

常用方法

![image-20231031150834722](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20231031150834722.png)

获取class类对象的方法

1. 已知一个类的全类名，且该类在类路径下，可通过Class类的静态方法forName0获取

   实例：Class cls1 =Class.forName( "iava.lang.Cat”)

   应用场景：多用于配置文件,读取类全路径，加载类

2. 若已知具体的类，通过类的class 获取，该方式 最为安全可靠，程序性能最高

实例：`Class cls2 = Cat.class`

应用场景：多用于参数传递，比如通过反射得到对应构造器对象

3. 已知某个类的实例，调用该实例的getClass(方法获取Class对象

   实例：`Class clazz = 对象.getClass0://运行类型`

   应用场景: 通过创建好的对象，获取Class对象

4. 其他方式：`ClassLoader cl = 对象.getClass(.getClassLoader0;Class clazz4 = cl.loadClass(“类的全类名”);`

5. 基本数据(int, char,boolean,float,double,byte,long,short) 按如下方式得到Class类对象

   `Class cls = 基本数据类型.class`

6. 基本数据类型对应的包装类，可以通过.TYPE 得到Class类对象

   `Class cls = 包装类.TYPE`

哪些类型有 Class 对象

1. 外部类，成员内部类，静态内部类，局部内部类，匿名内部类
2. interface : 接口
3. 数组
4. enum :枚举
5. annotation :注解
6. 基本数据类型
7. void

### 类加载

反射机制是 java实现动态语言的关键，也就是通过反射实现类动态加载。

静态加载：编译时加载相关的类，如果没有则报错，依赖性大强

动态加载：运行时加载需要的类，如果运行时不用该类，即使不存在该类，则不报错，降低了依赖性

#### 类加载时机

1. 创建对象时 (new)   --》静态加载
2. 子类被加载时，父类也加载 --》静态加载
3. 调用类中的静态成员时 --》静态加载
4. 通过反射 --》动态加载

#### 类加载过程图

![image-20231031152213956](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20231031152213956.png)

![image-20231031152650461](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20231031152650461.png)

#### 类加载各阶段完成任务

- 加载阶段：JVM 在该阶段的主要目的是将字节码从不同的数据源 (可能是 class 文件、也可能是jar 包，甚至网络)
  转化为二进制字节流加载到内存中，并生成一个代表该类的java.lang.Class 对象
- 准备阶段：
   - 目的是为了确保 Class 文件的字节流中包含的信息符合当前虚拟机的要求，并且不会危害虚拟机自身的安全。
   - 包括: 文件格式验证(是否以魔数 oxcafebabe开头)、元数据验证、字节码验证和符号引用验证
   - 可以考虑使用 -Xverify:none 参数来关闭大部分的类验证措施，缩短虚拟机类加载的时间。

```java
class A {
    //属性-成员变量-字段
    //老韩分析类加载的链接阶段-准备 属性是如何处理
    //1. n1 是实例属性, 不是静态变量，因此在准备阶段，是不会分配内存
    //2. n2 是静态变量，分配内存 n2 是默认初始化 0 ,而不是20
    //3. n3 是static final 是常量, 他和静态变量不一样, 因为一旦赋值就不变 n3 = 30
    public int n1 = 10;
    public static  int n2 = 20;
    public static final  int n3 = 30;
}
```

- 连接阶段——解析：虚拟机将常量池内的符号引用替换为直接引用的过程
- Initialization （初始化）
   - 到初始化阶段，才真正开始执行类中定义的 Java 程序代码，此阶段是执行<clinit>0 方法的过程。
   - <clinit>0 方法是由编译器按语句在源文件中出现的顺序，依次自动收集类中的所有静态变量的赋值动作和静态代码块中的语句，并进行合并。
   - 虚拟机会保证一个类的 <clinit>0
     方法在多线程环境中被正确地加锁、同步，如果多个线程同时去初始化一个类，那么只会有一个线程去执行这个类的 <clinit>0
     方法，其他线程都需要阻塞等待，直到活动线程执行 <clinit>0 方法完毕

```java
public class ClassLoad03 {
    public static void main(String[] args) throws ClassNotFoundException {
        //1. 加载B类，并生成 B的class对象
        //2. 连接阶段 num = 0
        //3. 初始化阶段：依次自动收集类中的所有静态变量的赋值动作和静态代码块中的语句,并合并
        /*
                clinit() {
                    System.out.println("B 静态代码块被执行");
                    num = 300;
                    num = 100;
                }
                合并: num = 100
         */
         B b = new B();
    }
}

class B {
    static {
        System.out.println("B 静态代码块被执行");
        num = 300;
    }
    static int num = 100;
    public B() {//构造器
        System.out.println("B() 构造器被执行");
    }
}
```

### 通过反射获取类的结构信息

java.lang.Class类

- getName:获取全类名
- getSimpleName:获取简单类名
- getFields:获取所有public修饰的属性，包含本类以及父类的
- getDeclaredFields:获取本类中所有属性
- getMethods:获取所有public修饰的方法，包含本类以及父类的
- getDeclaredMethods:获取本类中所有方法
- getConstructors: 获取本类所有public修饰的构造器
- getDeclaredConstructors;获取本类中所有构造器
- getPackage:以Package形式返回 包信息10.getSuperClass:以Class形式返回父类信息
- getInterfaces:以Class[形式返回接口信息
- getAnnotations:以Annotationl] 形式返回注解信息

java.lang.reflect.Field 类

- getModifiers: 以int形式返回修饰符【说明: 默认修饰符 是0，public 是1 ，private 是 2，protected 是 4static 是 8 ，final 是
  16 , public(1) + static (8) = 9】
- getType:以Class形式返回类型
- getName:返回属性名

java.lang.reflect.Method 类

- getModifiers:以int形式返回修饰符【说明: 默认修饰符 是0，public 是1，private 是2，protected 是 4static 是8，final是 16】
- getReturnType:以Class形式获取 返回类型
- getName:返回方法名
- getParameterTypes:以Class[]返回参数类型数组

java.lang.reflect.Constructor 类

- getModifiers: 以int形式返回修饰符
- getName:返回构造器名 (全类名)
- getParameterTypes:以Class[]返回参数类型数组

### 通过反射创建对象

方式一: 调用类中的public修饰的无参构造器【newInstance】

方式二:调用类中的指定构造器【getDeclaredConstructor】

1. Class类相关方法
   newlnstance : 调用类中的无参构造器，获取对应类的对象getConstructor(Class...clazz):根据参数列表，获取对应的public构造器对象
   getDecalaredConstructor(Class...clazz):根据参数列表，获取对应的**所有构造器对象**
2. Constructor类相关方法
   setAccessible：暴破
   newInstance(Object...obj)：调用构造器

### 通过反射访问类中的成员

1. 获取Field对象：Field f = clazz.getDeclaredField(属性名)
   获取Method方法对象 ：
   Method m =clazz.getDeclaredMethod(方法名，XX.class);
   Object o=clazz.newInstance0;

2. **暴破 : f.setAccessible(true);**

3. 访问f.set(o,值); // o 表示对象

   sout(f.get(o));//o 表示对象

4. 注意: 如果是**静态属性，则set和get中的参数o，可以写成null**
