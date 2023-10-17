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
