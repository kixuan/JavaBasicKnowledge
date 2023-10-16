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
3. **一般来说在程序中80%-90%都是查询，因此大部分情况下会选择ArravList**
4. 在一个项目中，根据业务灵活选择，也可能这样，一个模块使用的是ArrayList,另外一个模块是LinkedList, 也就是说，要根据业务来进行选择

### Set

