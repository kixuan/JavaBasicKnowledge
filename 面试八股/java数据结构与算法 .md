# 数据结构与算法

## 算法时间复杂度

### 度量一个程序(算法)执行时间的两种方法

事后的统计方法:

这种方法可行, 但是有两个问题：**一是要想对设计的算法的运行性能进行评测，需要实际运行该程序**；*
*二是所得时间的统计量依赖于计算机的硬件、软件等环境因素, 这种方式，要在同一台计算机的相同状态下运行，才能比较那个算法速度更快。
**

事前估算的方法：

通过分析某个算法的时间复杂度来判断哪个算法更优.

#### 时间频度

基本介绍

时间频度：

一个算法花费的时间与算法中语句的执行次数成正比，那个算法中语句执行的次数多，它花费的时间就多，一个算法中的语句执行次数称为语句频度或者时间频度，记作T(
n)。

#### 举例说明-时间频度

比如计算1-100所有数字之和，我们设计两种算法：

```java
int total = 0;
int end = 100;
for(int i;i<=end;i++){
	total += i;
}
//这里这个语句就执行了100次
//T(n) = n+1;
//直接计算
total = (1+end) * end/2
//T(n) = 1;
```

#### 举例说明-忽略常数项

![image-20221016091928616](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/dopisz-2.png)

结论:

1. **2n+20 和 2n 随着n 变大，执行曲线无限接近, 20可以忽略**
2. **3n+10 和 3n 随着n 变大，执行曲线无限接近, 10可以忽略**

#### 举例说明-忽略低次项

![image-20221016092213284](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/dp2x9a-2.png)

结论:

1. **2n^2+3n+10 和 2n^2 随着n 变大, 执行曲线无限接近, 可以忽略 3n+10**
2. **n^2+5n+20 和 n^2 随着n 变大,执行曲线无限接近, 可以忽略 5n+20**

#### 举例说明-忽略系数

![image-20221120213038551](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/dp395z-2.png)

结论:

1. **随着n值变大，5n^2+7n 和 3n^2 + 2n ，执行曲线重合, 说明 这种情况下, 5和3可以忽略。**
2. **而n^3+5n 和 6n^3+4n ，执行曲线分离，说明多少次方式关键**

### 时间复杂度的计算方式

#### 时间复杂度的介绍

一般情况下，算法中的基本操作语句的重复执行次数是问题规模n的某个函数，用T(n)表示，若有某个辅助函数f(n)，使得当n趋近于无穷大时，
**T(n) / f(n) 的极限值为不等于零的常数**，则称f(n)是T(n)的同数量级函数。**记作 T(n)=Ｏ( f(n) )，称Ｏ( f(n) )**
为算法的渐进时间复杂度，简称时间复杂度。

**T(n) 不同，但时间复杂度可能相同**。 如：T(n)=n²+7n+6 与 T(n)=3n²+2n+2 它们的T(n) 不同，但时间复杂度相同，都为O(n²)。

计算时间复杂度的方法：

用常数1代替运行时间中的所有加法常数 T(n)=3n²+2n+2 => T(n)=3n²+2n+1
修改后的运行次数函数中，只保留最高阶项 T(n)=3n²+2n+1 => T(n) = 3n²
去除最高阶项的系数 T(n) = 3n² => T(n) = n² => O(n²)

#### 常见的时间复杂度

![image-20221016095009793](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/dp3pzr-2.png)

1. 常数阶O(1)
2. 对数阶O(log2n)
3. 线性阶O(n)
4. 线性对数阶O(nlog2n)
5. 平方阶O(n^2)
6. 立方阶O(n^3)
7. k次方阶O(n^k)
8. 指数阶O(2^n)

说明：

1. 常见的算法时间复杂度由小到大依次为：**Ο(1)＜Ο(log2n)＜Ο(n)＜Ο(nlog2n)＜Ο(n2)＜Ο(n3)＜ Ο(nk) ＜Ο(2n)**
   ，随着问题规模n的不断增大，上述时间复杂度不断增大，算法的执行效率越低
2. 从图中可见，我们应该尽可能避免使用指数阶的算法

#### 时间复杂度的举例说明（常见）

##### 常数阶O(1)

无论代码执行了多少行，只要是没有循环等复杂结构，那这个代码的时间复杂度就都是O(1)

```java
int i = 1;
int j = 2;
++i;
++j;
int m  = i+j;
//这里的代码不管i,j是多少他都执行一次所以这里的时间复杂度就为o
```

##### 对数阶O(log2n)

```java
int i = 1;

while(i<n){
	i = i * 2;
}
//在while循环里面，每次都将 i 乘以 2，乘完之后，i 距离 n 就越来越近了。假设循环x次之后，i 就大于 2 了，此时这个循环就退出了，也就是说 2 的 x 次方等于 n，那么 x = log2n也就是说当循环 log2n 次以后，这个代码就结束了。因此这个代码的时间复杂度为：O(log2n)  。 O(log2n) 的这个2 时间上是根据代码变化的，i = i * 3 ，则是 O(log3n) 
```

##### 线性复杂度O(n)

```java
int j = 0;
for(i = 1; i<=n;++i){
	j=i;
	j++;
}
//这段代码，for循环里面的代码会执行n遍，因此它消耗的时间是随着n的变化而变化的，因此这类代码都可以用O(n)来表示它的时间复杂度
```

##### 平方阶O(n²)

```java
for(i=1; i <= n; i++){
	for(j =1; j<=i; j++){
		j=i;
		j++;
	}
}
//平方阶O(n²) 就更容易理解了，如果把 O(n) 的代码再嵌套循环一遍，它的时间复杂度就是 O(n²)，这段代码其实就是嵌套了2层n循环，它的时间复杂度就是 O(n*n)，即  O(n²) 如果将其中一层循环的n改成m，那它的时间复杂度就变成了 O(m*n)
```

#### 线性阶对数阶(n log N)

```java
for(int m=1;m<n;m++){
	i=1;
	while(i<n){
		i = i*2;
	}
}
//线性对数阶O(nlogN) 其实非常容易理解，将时间复杂度为O(logn)的代码循环N遍的话，那么它的时间复杂度就是 n * O(logN)，也就是了O(nlogN)
```

### 平均时间复杂度和最坏时间复杂度

1. 平均时间复杂度是指所有可能的输入实例均以等概率出现的情况下，该算法的运行时间。
2. 最坏情况下的时间复杂度称最坏时间复杂度。一般讨论的时间复杂度均是最坏情况下的时间复杂度。
   这样做的原因是：最坏情况下的时间复杂度是算法在任何输入实例上运行时间的界限，这就保证了算法的运行时间不会比最坏情况更长。
3. 平均时间复杂度和最坏时间复杂度是否一致，和算法有关(如图:)。

![image-20221016100219172](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/dp549g-2.png)

## 空间复杂度

### 基本介绍

1. 类似于时间复杂度的讨论，一个算法的空间复杂度(Space Complexity)定义为该算法所耗费的存储空间，它也是问题规模n的函数。
2. 空间复杂度(Space Complexity)
   是对一个算法在运行过程中临时占用存储空间大小的量度。有的算法需要占用的临时工作单元数与解决问题的规模n有关，它随着n的增大而增大，当n较大时，将占用较多的存储单元，例如快速排序和归并排序算法就属于这种情况
3. 在做算法分析时，主要讨论的是时间复杂度。从用户使用体验上看，更看重的程序执行的速度。一些缓存产品(redis, memcache)和算法(
   基数排序)本质就是用空间换时间.

## 稀疏数组

### 基本介绍

当一个数组中大部分元素为0，或者为同一个值得数组时，可以用稀疏数组来进行压缩储存

### 处理方法

1)记录数组**一共有几行几列，有多少个不同**的值

2)把具有不同值得元素的行列及值记录在一个小规模的数组中，从而**缩小程序**的规模

### 实际应用（棋盘上的棋子作为案例）

![image-20220928120235935](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/dp5agj-2.png)

![image-20220928120633574](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/dppmzq-2.png)

#### 二维数组转稀疏数组的思路

1.遍历二维数组中的所有的有效值并统计为一个sum

2.根据sum可以创建对应的稀疏数组的行

```java
sparseArr int[sum+1][3]
```

3.根据二维数组中的有效数据存入到稀疏数组中,分别对应的是 实际的行减一就是下标不是从0开始区分数学上的行，所以上图的就是

【0】（一维数组的索引值） row（这个矩阵有多少行） col（列） val (有多少个有效值)（这一行可以说是一个总结，下面就是单独拎出来）

​ 11 11 2

【1】 row（有效值的行位置） col(列)        val（这个位置对应的有效值）

​ 1 2 1

【2】 2 3 2

##### 代码实现

```java
//先创建一个二维数组,表示棋盘
        //0：表示没有棋子，1表示黑子，2表示篮子
        int[][] ChessArray = new int[11][11];
        ChessArray[1][2] = 1;
        ChessArray[2][3] = 2;
        ChessArray[5][8] = 2;
        //原始的二维数组
        System.out.println("原始二维数组");
        for (int[] ints : ChessArray) {
            for (int anInt : ints) {
                System.out.print(anInt + " ");
            }
            System.out.println(" ");
        }
        //二维数组转化为稀疏数组
        /**
         * 先遍历所有的值，去除大于零和相同的值
         * 将有效值个数用sum来表示
         * 稀疏数组的行数就为sum+1，列为3
         */
        int sum = 0;

        for (int i = 0; i < ChessArray.length; i++) {
            for (int j = 0; j < ChessArray[i].length; j++) {
                if (ChessArray[i][j] != 0) {
                    sum++;
                }
            }
        }
        //创建对应的稀疏数组
        int[][] Spares = new int[sum + 1][3];
        //给稀疏数组赋值
        Spares[0][0] = 11;
        Spares[0][1] = 11;
        Spares[0][2] = sum;
        //遍历二维数组将非0的数值放入稀疏数组中
        int count = 0;
        for (int i = 0; i < ChessArray.length; i++) {
            for (int j = 0; j < ChessArray[i].length; j++) {
                if (ChessArray[i][j] != 0) {
                    count++;
                    Spares[count][0] = i;
                    Spares[count][1] = j;
                    Spares[count][2] = ChessArray[i][j];
                }
            }
        }
        //输出稀疏数组
        System.out.println();
        System.out.println("得到的稀疏数组为");
        for (int[] spare : Spares) {
            for (int i : spare) {
                System.out.print(i+"\t");
            }
            System.out.println();
        }
```

#### 稀疏数组转二位数组思路

先将稀疏数组的第一行读出来，根据第一行的数据来创建二维数组比如上面的棋盘

```java
 int [][] ChrreyAarry = new int [11][11]
```

然后将稀疏数组后的每一行的数据放回到二维数组当中

```java
 charreyAarry[1][2] = 1;
 charreyAarry[2][3] = 2;
```

```java
//将稀疏数组转化为二位数组
        /**
         * 先将稀疏数组中的第一行的数据拿出来作为二维数组的行和列
         * 然后将稀疏数组后的每一行的数据放回到二维数组当中
         */
        int row = Spares[0][0];
        int col = Spares[0][1];

        int[][] ChessArray1= new int[row][col];

        for (int i = 1; i < Spares.length; i++) {
            int Crow = Spares[i][0];
            int Ccol = Spares[i][1];
            int Val =Spares[i][2];
            ChessArray1[Crow][Ccol] = Val;
        }

        System.out.println();

        System.out.println("恢复后的二维数组");

        for (int[] ints : ChessArray1) {
            for (int anInt : ints) {
                System.out.print(anInt+" ");
            }
            System.out.println();
        }
```

## 队列

### 队列介绍

1. 队列是一个有序列表，可以用数组或者链表来实现
2. 遵循先进先出的原则，即：先存入队列的数据，要先取出，后存入的数据要后取出

示意图：（使用数组模拟队列示意图）

![image-20220929145108000](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/dpt1u4-2.png)

### 数组模拟队列

1. 队列本身是一个有序列表，若使用数组的结构来储存队列的数据，则队列数组的申明如下图，其中max Size是该队列的最大容量。
2. 因为队列的输出，输入是分别从前后端来处理，因此需要两个变量front及rear分别记录队列前后端的下标，**front会随着数据的输出而改变
   **，**而rear则是随着数据输入而改变**。

如图：

![image-20220929145108000](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/dps4rh-2.png)

​ 3.当我们将数据存入队列时被称为”addQueue“,addQueue的处理需要有两个步骤：

#### 思路分析

1）将尾指针往后移：rear+1 当front == rear 数组为【空】

2）若尾指针rear小于队列的最大下标 maxSize-1,则将数据存入rear所指的数组元素中，否则无法存入数据，rear == maxSize -
1 [队列满]。front不包含队列头部的第一个数据，rear包含队列尾部最后的一个数据

#### 代码实现

```java
class ArrayQueue {
    private int MaxSize;//这个数组的最大容量
    private int front;//这个数组的尾指针
    private int rear;//这个数组的前指针
    private int[] arr;//创建一个数组变量

    //创建数组的队列数组的构造器
    public ArrayQueue(int maxSize) {
        this.MaxSize = maxSize;
        arr = new int[maxSize];
        front = -1;//不包含队列头的数据
        rear = -1;//包含队列尾部的数据
    }

    //判断队列是否为满
    public boolean IsFull() {
        return rear == MaxSize - 1;
    }

    //判断队列是否为空

    public boolean IsEmpty() {
        return rear == front;
    }

    //添加数据到队列
    public void AddQueue(int n){
        if (IsFull()){
            System.out.println("队列满不能加入数据");
            return;
        }
        rear++;//让他后移
        arr[rear] = n;
    }

    //让数据出队列
    public int getQueue(){
        //判断是否为空
        if (IsEmpty()){
            throw new RuntimeException("队列为空无法取出数据");
        }
        front++;
        return arr[front];
    }
    //展示所有数据
    public void showQueue(){
        //遍历
        if (IsEmpty()){
            System.out.println("队列为空，拿不出来呀");
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }
    //显示头部数据,不是取出数据
    public int showHead(){
        if (IsEmpty()){
            throw new RuntimeException("队列为空，拿不出来呀");
        }
        return arr[front+1];

    }

}
```

#### 完整代码展示

```JAVA
public class ArrayQueueDemo {
    public static void main(String[] args) {
        //创建一个队列
        ArrayQueue arrayQueue = new ArrayQueue(3);
        String key = "";
        Scanner scanner = new Scanner(System.in);
        boolean flag = true;
        while (flag){
        System.out.println("s (show)显示队列");
        System.out.println("e (exist)退出程序");
        System.out.println("a (add) 添加数据");
        System.out.println("g (get)从队列取出数据");
        System.out.println("gd (showHead)得到数据");

        key = scanner.next();
        switch (key){
            case "s":
                arrayQueue.showQueue();
                break;
            case "a":
                System.out.println("请输入你要添加的数据");
                int num = scanner.nextInt();
                arrayQueue.AddQueue(num);
                break;
            case "g":
                try{
                    int a = arrayQueue.getQueue();
                    System.out.println("取出的数据是"+a);
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            case"gd":
                try{
                    int a = arrayQueue.showHead();
                    System.out.println("取出的数据是"+a);
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            case "e":
                flag = false;
                break;
            default:
                System.out.println("你的输入有误请重新输入");
                break;
        }}

    }
}

//使用数组模拟队列
class ArrayQueue {
    private int MaxSize;//这个数组的最大容量
    private int front;//这个数组的尾指针
    private int rear;//这个数组的前指针
    private int[] arr;//创建一个数组变量

    //创建数组的队列数组的构造器
    public ArrayQueue(int maxSize) {
        this.MaxSize = maxSize;
        arr = new int[maxSize];
        front = -1;//不包含队列头的数据
        rear = -1;//包含队列尾部的数据
    }

    //判断队列是否为满
    public boolean IsFull() {
        return rear == MaxSize - 1;
    }

    //判断队列是否为空
    public boolean IsEmpty() {
        return rear == front;
    }

    //添加数据到队列
    public void AddQueue(int n) {
        if (IsFull()) {
            System.out.println("队列满不能加入数据");
            return;
        }
        rear++;//让他后移
        arr[rear] = n;
    }

    //让数据出队列
    public int getQueue() {
        //判断是否为空
        if (IsEmpty()) {
            throw new RuntimeException("队列为空无法取出数据");
        }
        front++;
        return arr[front];
    }

    //展示所有数据
    public void showQueue() {
        //遍历
        if (IsEmpty()) {
            System.out.println("队列为空，拿不出来呀");
        }else {
            for (int i = 0; i < arr.length; i++) {
                System.out.printf("arr[%d] = %d",i,arr[i]);
                System.out.println();
        }
        }
    }

    //显示头部数据,不是取出数据
    public int showHead() {
        if (IsEmpty()) {
            throw new RuntimeException("队列为空，拿不出来呀");
        }
        return arr[front + 1];
    }

}
```

#### 问题分析

但是这个数据队列有一个很大的缺陷，这个数组只能使用一次，会有假溢出，而不能重复使用，如果要重复使用就要用到环形队列。

而环形队列则是用取模来实现%

取模运算的步骤：

(1) - 求整数商

c = a / b

(2) - 求模

r = a - c * b

### 数组模拟环形队列

#### 使用数组模拟环形队列的思路分析

思路如下：

1. front的含义发生变化，现在的front由指向数组的第一个的前一个改变为指向数组的第一个
2. front = 0（初始值）
3. rear的含义发生变化，rear指向队列的后一个元素的后一个位置，流出一块空间作为约束
4. rear = 0（初始值）
5. 当队列满时，判断满的方法为（rear+1）% MaxSize = front【满】（此时的rear在元素最后的一个位置，rear+1预留出一个空间，这时 (
   rear+1) %MaxSize为0 == front 这时候就为满 ）
6. 当队列空时，判断空的方法为 rear == front
7. 判断数组中的有效数据，(rear + MaxSize - front)  % MaxSize (加MaxSize是为了避免rear - front是负数，因为这是一个环形队列)

#### 仔细分析

队列最大长度匹配数组容量导致一种错误的解决方案

这就会有一个问题，随着队列中元素的不断更迭，front和rear很快就会超过数组容量，造成数组索引越界

![c](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/dpure2-2.png)

比如上图所示，front=2，也就是说已经有两个元素出列了，因此rear=5与rear=6对应的两个元素理应可以入列，但是我们发现数组maxsize=5，不存在索引位5和6，强行对这两个下标赋值会造成索引越界异常indexOutException
。但是我们发现此时数组中索引位0和1都空着，完全可以将这两个位置利用起来，因此我们可以想办法让实际的rear值转化为等效的rear值，也就是是让rear=5转化为rear=0，同理rear6转化为rear=1。怎么做到呢？无疑是通过取余！

每次新元素入队后， 执行**rear=(rear)%maxSize**操作，**随后执行rear++操作右移rear指针**,但是这种做法是有缺陷的。

像上图中的rear=rear%5乍一看好像没问题，但实际上这种取余方式是有问题的，出现这种取余方式的根源在于我们想让队列最大长度与数组容量保持一致，

我们怎么判断队列为空呢？

如果我们按照指针从左到右的方向移动，当front指针和rear指针重合时，front指针对应的索引位之前的索引位都已经出列完毕，而rear指针对应的索引位以及之后的所有索引位还未有元素入列。

**所以队列是否为空的判别：front==rear**

<img src="http://8.137.11.22/i/2022/11/21/vtmwau-2.png" alt="xpng" style="zoom:80%;" />

**rear=rear%maxSize解决方案的问题**

下图展示了maxSize=5的数组中，front=0保持不变，元素依次入列直到满载，rear指针的移动情况：

------

![cx](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vtnodv-2.png)

可以看到，如果我们认为队列容量与数组容量应该持平，那么当第五个元素50入列后，本来rear=4执行了rear++的操作后，rear=5，随后rear将会通过取余算法rear=rear%maxSize重置为0。

但关键点就在这里，**我们发现空载时front=rear=0，满载时依然有front=rear=0！**
这样子我们就无法判断front=rear时，队列是空还是满，因此rear=rear%maxSize这种解决方案是不被允许的。
新的解决方案：置空位的引入
每次新元素入队后， **执行rear=(rear+1)%maxSize操作，该操作包含rear++操作**，如果这里不在括号内加1，**那么最后一个位置就不会置空并且会由元素入列。
**

并且我们人为规定，数组中必须留有一个索引位不得放置元素，必须置空！！！如何实现我们的人为规定呢？那就要先探索当数组满载后front和rear指针之间有啥关系。

入队图示

下图展示了maxSize=5的数组中，front=0保持不变，元素依次入列直到满载，rear指针的移动情况：

------

![a](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vto9ic-2.png)

人为的让最后一位置空，所以当元素40入列后，数组已经满载

       满载后数据之间的关系：

- front=0
- rear=(rear+1)%maxSize=(3+1)%5=4  (注： 执行完arr[rear]=40，再执行 rear=(rear+1)%maxSize)
- (rear+1)%maxSize=(4+1)%5=0==front

当我们认为的满载发生后，最后一位置空，发现此时rear和front之间的关系为(rear+1)%maxSize=(4+1)%5=0==front，因此这个关系可以作为满载的条件

因为处于满载状态，我们无法再往队列添加元素，只能从队列取出元素，也就是进行出列的操作，而一旦我们执行了出列的操作，比如将索引位i=0上的元素10出列后，则front右移，即执行front=(
front+1)%maxSize操作，最终front=1。

若随后又添加元素入列，即在索引位i=4上添加元素50，随后又会执行rear=(rear+1)%maxSize操作，最终rear=0。

rear=0≠front=1，此时就不会出现之前那种错误方案中 rear=front=0导致歧义的情况，而一旦 rear=front=0，必然表示队列为空，因此这种解决方案是行得通的

**队列为满的判别**

当我们认为的满载发生后，最后一位置空，发现此时rear和front之间的关系为**(rear+1)%maxSize=(4+1)%5=0==front**
，因此这个关系可以作为满载的条件：（rear + 1）%maxSize = front

**队列中元素的个数**

numValid=**(rear+maxSize-front)%maxSize**，大家可以带入数据验证一下

实际上：

- 当rear在front之后(**这里指的是数组中索引位的前后，并非逻辑上的前后**)，有效数据个数=rear-front=(rear+maxSize-front)
  %maxSize

- 当rear在front之前(**这里指的是数组中索引位的前后，并非逻辑上的前后**)，有效数据个数=(rear+maxSize-front)%maxSize

- ​ 这里主要防止**rear - front为负数**，取模的话也是负数。

##### 小细节

置空位虽然是人为引入的，但这不意味这置空位的位置是随意的，实际上，只有队列满后才会将剩下的位置作为置空位，一旦置空位出现，rear和front永远不可能指向同一个索引位，因为你会惊奇的发现置空位恰号将rear和front隔开了.

置空位就像一把锁，一旦上锁就只能通过出队列操作解锁

继续执行获取元素操作出队列（解锁）：

![x](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vtpeg9-2.png)

上图中60入列后满载，可以看到置空位再次出现，但**30➡40➡50➡60➡置空位** 形成了逻辑上的闭环。

#### 完整代码

```java
public class CircleArrayQueueDemo {


    public static void main(String[] args) {
        //创建一个队列
        CircleArray circleArray = new CircleArray(5);
        String key = "";
        Scanner scanner = new Scanner(System.in);
        boolean flag = true;
        while (flag) {
            System.out.println("s (show)显示队列");
            System.out.println("e (exist)退出程序");
            System.out.println("a (add) 添加数据");
            System.out.println("g (get)从队列取出数据");
            System.out.println("gd (showHead)得到数据");

            key = scanner.next();
            switch (key) {
                case "s":
                    circleArray.showQueue();
                    break;
                case "a":
                    System.out.println("请输入你要添加的数据");
                    int num = scanner.nextInt();
                    circleArray.AddQueue(num);
                    break;
                case "g":
                    try {
                        int a = circleArray.getQueue();

                        System.out.println("取出的数据是" + a);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "gd":
                    try {
                        int a = circleArray.showHead();

                        System.out.println("取出的数据是" + a);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "e":
                    flag = false;
                    break;
                default:
                    System.out.println("你的输入有误请重新输入");
                    break;

            }
        }
    }
}

class CircleArray {
    private int MaxSize;//这个数组的最大容量
    private int front;//这个从0开始
    private int rear;//这个从0开始，到数组最后一个的前一个，留出一个置空位，判断是否满载
    private int[] arr;//创建一个数组变量

    public CircleArray(int maxSize) {
        MaxSize = maxSize;
        arr = new int[maxSize];
    }


    //判断队列是否为满
    public boolean IsFull() {
        return (rear + 1) % MaxSize == front;
    }

    //判断队列是否为空
    public boolean IsEmpty() {
        return rear == front;
    }

    //添加数据
    public void AddQueue(int n) {
        if (IsFull()) {
            System.out.println("队列满不能加入数据");
            return;
        }
        arr[rear] = n;
        //通过取模防止下标越界
        rear = (rear + 1) % MaxSize;
    }

    public int getQueue() {
        //判断是否为空
        if (IsEmpty()) {
            throw new RuntimeException("队列为空无法取出数据");
        }
        //这里要做一个做一个变量去保留拿出来的值

        int val = arr[front];
        //同rear一样防止索引溢出
        front = (front + 1) % MaxSize;
        return val;
    }

    //展示所有数据
    public void showQueue() {
        //遍历
        if (IsEmpty()) {
            System.out.println("队列为空，拿不出来呀");
        } else {
            //加上front的原因就是因为只有加上了front后才能将整个有效的数据遍历完
            for (int i = front; i <= (rear + MaxSize - front) % MaxSize + front ; i++) {
                System.out.printf("arr[%d] = %d", i % MaxSize, arr[i % MaxSize]);
                System.out.println();
            }
        }
    }

    public int showHead() {
        if (IsEmpty()) {
            throw new RuntimeException("队列为空，拿不出来呀");
        }
        return arr[front];
    }


}
```

## 链表（Linked List）

### 链表的介绍

链表是有序的列表，但是它在内存中的存储是如下的

![image-20221001222730999](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vtqjxr-2.png)

上图小结：

1. 链表是以节点的方式来存储，是链式存储
2. 每一个节点包含data域，next域：指向下一个节点
3. 如图：发现链表的**各个节点不一定是连续存储**
4. 链表分**带头节点的链表**和**没有头节点的链表**，根据实际的需求来确定

单链表（带头结点）逻辑结构示意图如下（**他只是逻辑上有序的，但是物理上他不是有序的**）

![image-20221001223606704](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vtr2ae-2.png)

### 单链表的创建思路

![image-20221002142909693](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vtrhhf-2.png)

添加（创建）

1.先创建一个head头节点，作用就是表示单链表的头

2.后面我们每添加一个节点，就直接加入到链表的后面

遍历：

1.通过一个辅助变量遍历，帮助遍历整个链表

不考虑编号问题的写法

#### 完整代码

```java
public class SingleListLinkedDemo {
    public static void main(String[] args) {
        //进行测试
        //先创建节点
        HeroNode hero1 = new HeroNode(1,"宋江","及时雨");
        HeroNode hero2 = new HeroNode(2,"卢俊义","玉麒麟");
        HeroNode hero3 = new HeroNode(3,"吴用","智多星");
        HeroNode hero4 = new HeroNode(4,"林冲","豹子头");
        //创建链表
        SingleLinkedList singleLinkedList = new SingleLinkedList();
        //添加英雄
        singleLinkedList.AddHero(hero1);
        singleLinkedList.AddHero(hero2);
        singleLinkedList.AddHero(hero3);
        singleLinkedList.AddHero(hero4);
        //显示英雄
        singleLinkedList.ShowLinkedList();
    }
}



//定义一个SingleLinkedList管理英雄
class SingleLinkedList{
//创建一个头节点，头节点不能动否则就无法找到其他的节点
    private HeroNode Head = new HeroNode(0,"0","");

    //创建一个辅助变量来管理头节点
    //添加节点到链表当中
    //1.不考虑编号添加的时候全部添加到节点的末尾
    //2.通过循环找到循环的末尾
    public void AddHero(HeroNode heroNode){
        HeroNode tmp = Head;
        //通过循环找到链表的末尾
        while (tmp.next != null) {
            //如果是链表的末尾那么就直接结束循环
            //然后将节点后移
            tmp = tmp.next;
        }
        tmp.next = heroNode;
    }

    public void ShowLinkedList(){
        //用头节点来判断下一个存不存在
        if (Head.next == null){
            System.out.println("链表为空，没有数据");
            return;
        }

        //因为头节点不能动所以只能用一个辅助变量来遍历
        HeroNode tmp = Head;
        while (tmp.next != null) {
            //一定要记得将节点下移不然就会死循环
            tmp = tmp.next;
            System.out.println(tmp.toString());
        }
    }



}


class HeroNode{
    public int no;
    public String name;
    public String nickName;
    public HeroNode next;

    public HeroNode(int no, String name, String nickName) {
        this.no = no;
        this.name = name;
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return "HeroNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}
```

#### 考虑编号问题的思路

![image-20221002154211551](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vtseq6-2.png)

1. 首先找到要添加节点的位置，我们需要一个辅助变量（指针来作为插入数据的前一个数据），通过遍历来实现
2. 当找到位置后就通过 新的节点.next = temp.next来指向4的位置
3. temp.next = 新的节点；

#### 编号问题代码实现

```java
public void addByOrder(HeroNode heroNode){
    //因为是单链表所以我们只能找要添加地方的前一个节点不然无法添加了
    //通过一个标识来判断是否要添加
    boolean flag = false;
    HeroNode tmp = Head;
    while(true){
        if (tmp.next == null){
            break;//当遍历完后就直接结束
        }else if (tmp.next.no > heroNode.no){
            //当遇到当前节点的next的no大于我们要插入的节点时就break
            break;
        }else if (tmp.next.no == heroNode.no){
            //说明已经放入过该编号了就要改变flag的值
            flag = true;
            break;
        }
        tmp = tmp.next;//一定要向后移动一位
    }
    //然后判断flag的状态
    if (flag){
        System.out.println("准备加入的编号"+heroNode.no+"已经存在了");
    }
    heroNode.next = tmp.next;//新节点的next指向tmp.next
    tmp.next = heroNode;//之前的的节点指向新的节点
}
```

#### 修改信息

思路+完整代码

这里有几个小细节，首先这个变量指向的是头节点的下一个节点，而不是头节点，这样比较快一点

如果这里还用tmp.next的话最后一个的next就是null就直接break了，有一个节点就不会被操作，所以这里只能是tmp ==null就代表遍历完了。

```java
public void upData(HeroNode NewHeroNode){
        boolean flag = false;
        if (Head.next == null){
            System.out.println("链表为空，不能修改");
            return;
        }
        //还是要用一个临时变量来作为辅助
        HeroNode tmp = Head.next;
        while(true){
            if (tmp == null){
                //如果这里还用tmp.next的话最后一个的next就是null就直接break了，所以这里只能是tmp ==null就代表遍历完了
                break;
            }
            else if (tmp.no == NewHeroNode.no){
                flag=true;
                break;
            }
            tmp = tmp.next;//继续往下移动
        }
        if (flag){
        tmp.name = NewHeroNode.name;
        tmp.nickName = NewHeroNode.nickName;
    }else {
            System.out.println("你要修改的"+NewHeroNode.no+"不存在");
        }
    }
```

#### 删除信息

思路

![image-20221004220133279](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vvjxna-2.png)

1. 我们先找到 需要删除的这个节点的前一个节点 temp

2. temp.next = temp.next.next

3. 被删除的节点，将不会有其它引用指向，**会被垃圾回收机制回收**

注意的细节

首先tmp指向的必须是头节点如果指向的是Head.next那么第一个节点就会被跳过，就会删除不到

必须是tmp.next == null 如果不是那么在else if (tmp.next.no == no)
中就会有空指针异常，应为在最后指针才会后移，所以当到林冲的时候指针没有移动如果是tmp == null 那么这里就不会break

```java
public void del(int no){
        HeroNode tmp = Head;
        boolean flag = false;
        while (true){
            if (tmp.next == null){
                break;
            }else if (tmp.next.no == no){
                flag = true;
                break;
            }
            tmp = tmp.next;
        }
        if (flag){
            tmp.next = tmp.next.next;//这里的tmp.next就是3指向了null
        }else {
            System.out.println("你要删除的节点不存在"+"---"+no);
        }
```

### 单链表算法问题

------

#### 查找单链表的倒数第k个【新浪面试题】

思路：

1. 接收head节点，同时接收一个index
2. index，表示的是倒数第index个节点
3. 先把链表重头到尾遍历一次，等到总长度getlength
4. 得到size后 ，我们从链表的第一个开始遍历（size - index）个，就可以得到
5. 如果找到了就返回该节点，否则就是null

完整代码

```java
public static HeroNode FindLastIndexNode(HeroNode head, int index){
    //index表示的是第几个节点
    //先把链表从头到位遍历一次，得到size
    //然后在返回（size - index）的位置的节点
    int size = getLength(head);
    if (head.next == null){
        //空节点直接返回空
        return null;
    }else if (index < 0 || index > size ){
        return null;
    }
    //这里用一个辅助变量来接受后面的节点
    HeroNode temp = head.next;
    for (int i = 0; i < (size - index) ; i++) {
        //让指针往下移，找到我们要找的位置
        temp = temp.next;
    }
    return temp;
}

//计算链表的长度
public static int getLength(HeroNode head){
        int len = 0;
        //判断是否为空，如果为空的话就直接返回
        if (head.next == null){
            return 0;
        }
        HeroNode node  = head.next;
        while (node != null){
            len++;
            node = node.next;
        }
        return len;
    }
```

------

#### 单链表反转（腾讯面试题，有点难度）

![image-20221004220706985](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vvkv7l-2.png)

思路：

1. **先创建一个reverseHead的新节点，遍历每一个节点，然后取出每一个节点**
2. **将取出的节点放到reverse的最前端，先取出来的往后放**
3. **然后将head.next = reverseHead.next;**

![image-20221004221827104](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vvlwg4-2.png)

完整代码

```java
public static void ReverseNode(HeroNode head) {
        if (head.next == null || head.next.next == null) {
            System.out.println("链表为空，无法反转");
            return;
        }
        //创建一个新的头节点
        HeroNode reverseHead = new HeroNode(0, "", "");
        HeroNode tmp = head.next;
        HeroNode next = null;//这里要有next如果取出来一个节点没有指向的话链表会断掉,指向当前tmp的下一个节点
        while (tmp != null) {
            next = tmp.next;//让next指向下一个节点，作为保留变量
            tmp.next = reverseHead.next;//这里的=不能看做赋值而是要看作指向，就意味着tmp.next原来的指向发生了改变，指向了null       
            reverseHead.next = tmp;//然后reverse.next在指向tmp。
            tmp = next;//tmp指针向后移动
        }
        head.next = reverseead.next;
    }
```

------

#### 单链表从尾到头打印链表（百度的面试题）

![image-20221005145202903](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vvmckj-2.png)

思路：

1. 方式一：可以先将数组反转，然后在依次的打印出节点，**但是不推荐，因为这样会破环原有的数据结构**。
2. 方式二：利用栈的先进先出的特点，将每一个元素压栈，然后依次弹出就是倒序打印了

完整的代码实现

```java
public static void ReverseOut(HeroNode hero){
        Stack<HeroNode> stack = new Stack<>();
        HeroNode tmp = hero.next;
        while (tmp != null){
            stack.add(tmp);
            tmp = tmp.next;
        }
        while (stack.size() > 0){
            HeroNode reverseHero = stack.pop();
            System.out.println(reverseHero);
        }
    }
```

------

#### 合并两个有序链表

思路：

1. 首先先创建一个新的节点，用新的节点将两个链表连起来
2. 比较两个节点的大小，如果有一个节点比前一个节点大那么就用创建的节点连接，新的节点向后移动
3. 最后加判断，防止两个链表的长度不一致，有的遍历完了有的确没有遍历完，最后直接让新节点的下一个节点等于没有遍历完的节点

```java
class Solution {
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
       ListNode pre = new ListNode(-1),p = pre;
       ListNode p1 = list1;
       ListNode p2 = list2;
       while(p1 != null && p2 != null){
           if(p1.val >= p2.val){
               p.next = p2;
               p2 = p2.next;
           }else{
               p.next = p1;
               p1 = p1.next;
           }
           p = p.next;
       }
       if(p1 != null){
           p.next = p1;
       }if(p2 !=null){
           p.next = p2;
       }
       p1 =null;
       p2 = null;
       p = null; 
       System.gc();
       return pre.next;
    }
```

------

#### 删除链表中的元素

![image-20221006090032668](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vvn1au-2.png)

![image-20221006090058005](D:\数据结构与算法笔记\image-20221006090058005.png)!思路：

首先要考虑全是重复的元素怎么删除

第二还要创建一个辅助变量来进行移除

另外还要注意一些小细节

完整代码

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode removeElements(ListNode head, int val) {
         while(head != null && head.val == val ){
          head = head.next;
        }//这里是为了防止元素一致的
        if(head == null){
            return head;
        }//要先把元素一致的元素删除在返回，应为如果head本身是null的话前面就不会执行了，如果前面的语句放到返回语句的后面的话，下面的while语句就会报错，因为此时的这里head已经为null了
        ListNode tmp = head;
        while(tmp.next != null){
            if(tmp.next.val == val){
                tmp.next = tmp.next.next;
            }else{
                tmp =tmp.next;//因为前面用了tmp.next，这里就要判断一下否要移动，如果这里不加else那么当tmp已经为null在进行tmp.next !=null判断的时候就会报错，但是前面直接用tmp！=null继续判断当到最后一个节点的时候if语句就会报错。
            }
            
        }
        tmp = null;
        System.gc();
        return head;
    }
}
```

------

#### 环形链表

![image-20221009124014320](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vvw1gl-2.png)

![image-20221009124052618](D:\数据结构与算法笔记\image-20221009124052618.png)

思路：

这个问题可以类比为**摩托车追小车**

1. 小汽车和自行车从跑道的起点同时出发
2. 如果没有环道，那么小汽车永远离自行车而去
3. 如果有环道，最终小汽车最终会追上自行车

如果是应用到代码当中，就创建两个辅助指针让他们同时指向head，其中让一个指针移动快一点，一个指针移动慢一点

如果是一个环的话那么这个最终这个快的就会追上这个慢的。

代码实现

------

```java
public class Solution {
    public boolean hasCycle(ListNode head) {
        ListNode last = head;
        ListNode first = head;
        while(first !=null && first.next != null ){
           last = last.next;
           first = first.next.next;
           if(last == first){
               return true;
           }
        }
        last = null;
        first = null;
        System.gc();
        return false;
    }
}
```

### 双向链表

![image-20221006223737639](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vvx6ul-2.png)

分析双向链表的遍历，添加，修改，删除的操作思路

**遍历**和单向链表一样，但是双向链表可以从末尾向前遍历，也可从前向后遍历。

**添加**（默认添加到双向链表的末尾）：

就是将LastNode.next = NewNode;

NewNode.pre = LastNode;

修改和单链表的修改一样

**删除**

1. 因为是双向链表，所以我们可以不用指向要删除链表的前一个，而是可以直接指向要删除的节点
2. 就将DelNode.pre.next = DelNode.next;
3. Delnode.next.pre =DelNode.pre;

------

#### 双向链表完整代码

```java
public class DoubleLinkedListDemo {
    public static void main(String[] args) {
        //双向链表的测试
        HeroNode2 hero1 = new HeroNode2(1, "宋江", "及时雨");
        HeroNode2 hero2 = new HeroNode2(2, "卢俊义", "玉麒麟");
        HeroNode2 hero3 = new HeroNode2(3, "吴用", "智多星");
        HeroNode2 hero4 = new HeroNode2(7, "林冲", "豹子头");


        DoubleLinkedList doubleLinkedList = new DoubleLinkedList();
        doubleLinkedList.AddHero(hero1);
        doubleLinkedList.AddHero(hero2);
        doubleLinkedList.AddHero(hero3);
        doubleLinkedList.AddHero(hero4);
        //展示列表
        doubleLinkedList.ShowLinkedList();


    }
}


class DoubleLinkedList {
    //创建一个头节点，头节点不能动否则就无法找到其他的节点
    private HeroNode2 Head = new HeroNode2(0, "0", "");

    public HeroNode2 getHead() {
        return Head;
    }

    public void ShowLinkedList() {
        //用头节点来判断下一个存不存在
        if (Head.next == null) {
            System.out.println("链表为空，没有数据");
            return;
        }

        //因为头节点不能动所以只能用一个辅助变量来遍历
        HeroNode2 tmp = Head;
        while (tmp.next != null) {
            //一定要记得将节点下移不然就会死循环
            tmp = tmp.next;
            System.out.println(tmp.toString());
        }
    }

    //创建一个辅助变量来管理头节点
    //添加节点到双向链表当中
    //1.不考虑编号添加的时候全部添加到节点的末尾
    //2.通过循环找到循环的末尾
    public void AddHero(HeroNode2 heroNode) {
        HeroNode2 tmp = Head;
        //通过循环找到链表的末尾
        while (tmp.next != null) {
            //如果是链表的末尾那么就直接结束循环
            //然后将节点后移
            tmp = tmp.next;
        }
        tmp.next = heroNode;
        //新的节点的前一个节点指向tmp
        heroNode.pre = tmp;
    }

    //修改节点信息，双向链表几乎和单链表的一样
    //根据no来修改人物信息，no不能动
    public void upData(HeroNode NewHeroNode) {
        boolean flag = false;
        if (Head.next == null) {
            System.out.println("链表为空，不能修改");
            return;
        }
        //还是要用一个临时变量来作为辅助
        HeroNode2 tmp = Head.next;
        while (true) {
            if (tmp == null) {
                //如果这里还用tmp.next的话最后一个的next就是0就直接break了，所以这里只能是tmp ==null就代表遍历完了
                break;
            } else if (tmp.no == NewHeroNode.no) {
                flag = true;
                break;
            }
            tmp = tmp.next;//继续往下移动
        }
        if (flag) {
            tmp.name = NewHeroNode.name;
            tmp.nickName = NewHeroNode.nickName;
        } else {
            System.out.println("你要修改的" + NewHeroNode.no + "不存在");
        }
    }
    //双向链表删除节点，可以不用找到前一个节点而是可以直接找到要删除的节点
    public void del(int no) {

        HeroNode2 tmp = Head.next;
        boolean flag = false;
        while (true) {
            if (tmp == null) {//已经找到链表最后的后一个了
                break;
            } else if (tmp.no == no) {
                flag = true;
                break;
            }
            tmp = tmp.next;
        }
        if (flag) {
          tmp.pre.next = tmp.next;
          //如果我们正好要删除的节点是最后一个
          if (tmp.next != null){
              //那么这里的tmp.next就会报错（因为此时的tmp为null），所以这里要加上条件判断
              tmp.next.pre = tmp.pre;
          }
        } else {
            System.out.println("你要删除的节点不存在" + "---" + no);
        }
    }


}

class HeroNode2 {
    public int no;
    public String name;
    public String nickName;
    public HeroNode2 next;
    //指向前一个节点
    public HeroNode2 pre;

    public HeroNode2(int no, String name, String nickName) {
        this.no = no;
        this.name = name;
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return "HeroNode2{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}
```

------

#### 双向链表有序添加元素

```java
public void AddByOrder(HeroNode2 heroNode2){
         HeroNode2 tmp = Head;
         while (true){
             if (tmp.next == null){
                 break;
             }
             //如果寻找的是当前节点的话那么。就会写四个指向的语句这样就会形成闭环，就会死循环
             if (heroNode2.no <= tmp.next.no){
                 break;
             }
             tmp = tmp.next;
         }

    
        if (tmp.next == null){
            tmp.next = heroNode2;
            heroNode2.pre = tmp;
            return;
        }
        heroNode2.next = tmp.next;
        tmp.next = heroNode2;
        heroNode2.pre = tmp;
    }
```

### 约瑟夫问题分析

------

#### 单向环形链表的实现

![image-20221007224619248](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vvycj3-2.png)

思路分析：

创建第一过节点，first让指向该节点，并形成闭环

后面当我们每创建一个新的节点，就把该节点，加入到已有的闭环中

遍历：

通过一个curboy（辅助变量），指向first节点

通过while循环来进行遍历，当curboy .next == first的时候就是已经遍历完成

#### 生成环形链表完整代码

```java
class CircleSingleLinked{
    //先设置一个头指针
    private Boy first = null;

    public void addBoy(int nums){
        //通过for循环来添加小孩
        //先对数据进行判断，必须至少有一个小孩
        if (nums < 1){
            System.out.println("nums的值不正确");
            return;
        }

        Boy curBoy  = null;//辅助指针
        for (int i =1 ; i <= nums ; i++) {
            //如果i==1 那么就将一个boy节点指向自己
            Boy boy = new Boy(i);
            if (i == 1){
               first = boy;
               first.setNext(boy);//构成环
                curBoy = first;//辅助指针指向第一个小孩
            }else {
                curBoy.setNext(boy);
                //后面加的人连上第一个小孩形成环状
                boy.setNext(first);
                //cur指针向后移动
                curBoy = curBoy.getNext();
            }
        }
    }

    public void showBoy(){
        if (first == null){
            System.out.println("没有小孩");
            return;
        }
        //通过
        Boy curBoy = null;
        curBoy = first;
        while (true) {
            System.out.println("小孩编号---" + curBoy.getNo());
            if (curBoy.getNext().getNo() == first.getNo()){
                break;
            }
            curBoy = curBoy.getNext();
        }
    }
}



class Boy{
    private int no;
    private Boy next;

    public Boy(int no) {
        this.no = no;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public Boy getNext() {
        return next;
    }

    public void setNext(Boy next) {
        this.next = next;
    }
}
```

#### 小孩出圈问题

思路分析

根据用户的输入，生成一个小孩出圈的顺序

n = 5 , 即有5个人

k = 1, 从第一个人开始报数

m = 2, 数2下

![image-20221008205643021](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vvz2ne-2.png)

1. 首先创建两个指针一个头指针（first），一个为helper（始终在first的尾部，事先应该指向环形链表的最后这个节点）

2. 当小孩开始数数的时候先让f，h移动k -1次（f,要在k之后，因为这里k刚好为1，所以不需要移动如果k为3，那么需要移动2次如下图）

   ![image-20221023151927127](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vvzhjt-2.png)


3. 当开始报数的时候，first和helper相继移动m-1次

   ![image-20221023151146187](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vw07vx-2.png)

4. 然后在将first指针下移**first = first.next;**， **help.next = first;**(当2没有引用的时候gc自动将他回收)

5. 这个时候小孩就会出圈

![image-20221008210452631](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vw0cce-2.png)

#### 完整代码实现

```java
public void showBoy(){
        if (first == null){
            System.out.println("没有小孩");
            return;
        }
        //通过辅助指针来遍历
        Boy curBoy = null;
        curBoy = first;
        while (true) {
            System.out.println("小孩编号---" + curBoy.getNo());
            if (curBoy.getNext().getNo() == first.getNo()){
                break;
            }
            curBoy = curBoy.getNext();
        }
    }
	/**
     *
     * @param StartNum 表示从第几个小孩开始数数*
     * @param endNum 表示数几下
     * @param nums 表示最初有几个小孩在圈中
     */
    public void countBoy(int StartNum, int endNum,int nums){
        if (StartNum < 1 || StartNum > nums || first == null){
            System.out.println("输入的参数有误");
        }
        //创建一个辅助指针helper
        Boy helper = first;
        while (true){
            //当helper指针的后一个是first时那么说明helper已经位于first之后
            if (helper.getNext() == first){
                break;
            }
            //如果不是那helper继续移动
            helper = helper.getNext();
        }
        //当小孩开始数数之前先让两指针移动，StartNum-1的次数
        for (int i = 0; i < StartNum - 1 ; i++) {
            first = first.getNext();
            helper = helper.getNext();
        }
        //小孩开始数数的时候让f,h移动endNum - 1 ,并且让小孩出圈
        while (true){
            if (helper == first){
                //当其他小孩都出圈后，就剩一个小孩，那么就结束循环
                break;
            }
            for (int i = 0; i < endNum - 1 ; i++) {
                //first要移动到要出圈小孩的位置
                first = first.getNext();
                helper = helper.getNext();
            }
            //要出圈的小孩
            System.out.println("出圈的小孩---------"+first.getNo());
            //让出圈的小孩出圈
            first = first.getNext();
            helper.setNext(first);

        }
        System.out.println("最后留在圈中的小孩"+helper.getNo());
```

## 栈

### 栈的引入

![image-20221009084233489](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vw18vg-2.png)

------

### 栈的介绍

1. 栈的英文为（stack）
2. 栈是一个先入后出（FILO模式）的有序列表
3. 栈是限制线性列表中元素的插入和删除只能在线性表的同一端进行的一种特殊线性表，允许插入的删除的一端，为变化的一端，称为*
   *栈顶（Top）**,另外一端为固定的一端，称为**栈底（Bottom）**.
4. 根据栈的定义可知，最先放入栈中的元素在栈底，最后放入的元素在栈顶，而删除元素刚好相反，最后放入的元素最先删除，最先放入的元素最后删除。
5. 出栈（pop）和入栈（push）

![image-20221008223013229](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vw2ipz-2.png)

------

### 栈的应用场景

1)子程序的调用：在跳往子程序前，会先将下个指令的地址存到堆栈中，直到子程序执行完后再将地址取出，以回到原来的程序中。

2)处理递归调用：和子程序的调用类似，只是除了储存下一个指令的地址外，也将参数、区域变量等数据存入堆栈中。

3)表达式的转换[中缀表达式转后缀表达式]与求值(实际解决)。

4)二叉树的遍历。

5)图形的深度优先(depth一first)搜索法。

------

### 栈的快速入门

用数组模拟栈的使用，由于栈是一种有序列表，当然可以使用数组的结构来储存栈的数据内容，接下来就用栈来模拟栈的出栈和入栈操作

![image-20221009154422502](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vw2zh3-2.png)

实现栈的思路分析：

1. 用数组来模拟出栈和入栈
2. 先定义一个Top从-1开始，来表示栈顶
3. 入栈的时候TOP++；Static[Top] = data;
4. 出栈的时候value = Static[Top] ;Top--; return value

完整代码实现

```java
class ArrayStack {
        private int Maxsize;
        private int[] Stack;
        private int Top = -1;

        //初始化

        public ArrayStack(int maxsize) {
            this.Maxsize = maxsize;
            Stack = new int[maxsize];
        }

        //判断是否为空
        private boolean isEmpty() {
            return Top == -1;
        }

        //判断是否为满
        private boolean isFull() {
            return Top == Maxsize - 1;
        }

        //入栈(push)
        public void push(int no) {
            //先判断是否为满
            if (isFull()) {
                System.out.println("栈已满，无法入栈");
                return;
            }
            Top++;
            Stack[Top] = no;
        }

        //出栈(pop)
        public int pop() {
            //判断是否为空
            if (isEmpty()) {
                throw new RuntimeException("栈为空，没有数据");
            }
            int value = Stack[Top];
            Top--;
            return value;
        }

        //展示栈（遍历）
        public void showStack() {
            //判断是否为空
            if (isEmpty()) {
                System.out.println("数组为空，无法遍历");
                return;
            }
            //循环
            for (int i = Top; i >= 0; i--) {
                System.out.printf("Stack[%d]=%d", i, Stack[i]);
                System.out.println();
            }
        }
    }
```

------

### 使用栈完成计算一个表达式结果

#### 思路

1.先创建一个数栈（numStack）和一个符号栈(operatStack)

![image-20221009215208420](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vw52ln-2.png)

2.创建一个index的指针来进行扫描，如果是数字那么直接入数栈，如果是符号就入符号栈

![image-20221009215256914](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vw5omg-2.png)

3.入符号栈分两种情况

3.1当入符号栈的时候判断符号栈是否为空

3.1.1如果为空那么就直接入栈

3.1.2如果不为空那么，那先判断符号的优先级，如果扫描到的符号的优先级小于已入符号栈的符号的优先级，那么就先pop（）数栈的两个数字进行运算，然后在pop（）符号栈的符号然后进行运算，将运算的结果重新入栈，然后再将等了很久的符号入栈。

3.1.3如果扫描到的符号优先级小于已入栈的符号优先级，那么就直接加入符号栈中。

4.依次pop出栈中的数字和符号进行运算，将最后的运算结果放入数栈当中。

#### 代码实现（在之前的栈新加一些功能）

```java
//首先先创建两个詹，一个用于存放数字，一个用于存放符号
        ArrayStack NumberStack = new ArrayStack(10);
        ArrayStack operStack = new ArrayStack(10);
        //要计算的式子
            String s = "70+3-1";
            String keepNum = "";
        //设置一个扫描器
        int index = 0;
        //相加的数字
        int num1 = 0;
        int num2 = 0;
        //运算符
        int oper = 0;
        //要存入的字符
        char ch = ' ';//每次扫描都存入到char
        //结果
        int res = 0;
        while(true){
            //开始扫描,查看s中每一个字符
            ch = s.substring(index,index+1).charAt(0);
            //判断ch是什么如果是一个运算符就做相应的操作
            if (operStack.isOperate(ch)){
                if (!operStack.isEmpty()){
                    //如果不为空做相应的操作
                    //首先先判断符号的优先级,如果大于之前的运算符就直接放入，如果不是大于的话就先pop出一个符号和两个数进行运算；
                    int priority = operStack.priority(ch);
                    System.out.println(ch);
                    if (priority  <=  operStack.priority((char)operStack.showTop())){
                        oper = operStack.pop();
                        num1 = NumberStack.pop();
                        num2 = NumberStack.pop();
                        res = NumberStack.Cal(num1,num2, (char) oper);
                        //将运算结果放到数栈当中
                        NumberStack.push(res);
                        operStack.push(ch);
                    }else {
                        operStack.push(ch);
                    }
                }else {
                    //为空直接入栈
                    operStack.push(ch);
                }
            }else {
                //如果不是运算符的话就直接入数栈，并且这里要注意这里的 1 并不是真的意义上的一而是字符 字符1 对应的ASCII码是49 相差48
                //NumberStack.push(ch - 48);（这里有点问题）
                //思路这里当作一个多位数处理不能发现了数字就直接加入到数栈当中，先往下继续扫描，如果是数字那么就将两个数进行拼接
                //创建一个变量来进行字符串拼接
                //然后判断表达式中index后面的字符串是不是符号，如果不是就不能入栈，是才入栈
                /**
                如果没有这个keepNum变量来储存那么这个计算机就只能计算一位数
                例如不经过判断直接把字符加进去那么就只能做个位数计算
           		
                **/
                keepNum += ch;
                if (index == s.length() -1){
                    //应为运算符最后就是数字，如何index到了最后一位就直接入栈
                    NumberStack.push(Integer.parseInt(String.valueOf(keepNum)));
                }else {
                    //往后面看一下是否为运算符如果是运算符就直接将前面的数入栈
                    if (operStack.isOperate(s.substring(index+1,index+2).charAt(0))){
                        NumberStack.push(Integer.parseInt(String.valueOf(keepNum)));
                        //这里把keepNum加进去后要重置keepNum;
                        keepNum = "";
                    }
                }

            }
            //判断是否扫描完成
            index++;
            if (index >= s.length()){
                break;
            }
        }
        //扫描完成后依次从中取出相应的符号和数字进行一次的计算
        num1 = NumberStack.pop();
        num2 = NumberStack.pop();
        oper = operStack.pop();
        res = NumberStack.Cal(num1,num2,(char) oper);
        NumberStack.push(res);
        System.out.println(NumberStack.showTop());
    }

class ArrayStack {
        private int Maxsize;
        private int[] Stack;
        private int Top = -1;


        //初始化

        public ArrayStack(int maxsize) {
            this.Maxsize = maxsize;
            Stack = new int[maxsize];
        }


        //判断是否为空
        public boolean isEmpty() {
            return Top == -1;
        }

        //判断是否为满
        public boolean isFull() {
            return Top == Maxsize - 1;
        }

        //入栈(push)
        public void push(int no) {
            //先判断是否为满
            if (isFull()) {
                System.out.println("栈已满，无法入栈");
                return;
            }
            Top++;
            Stack[Top] = no;
        }

        //出栈(pop)
        public int pop() {
            //判断是否为空
            if (isEmpty()) {
                throw new RuntimeException("栈为空，没有数据");
            }
            int value = Stack[Top];
            Top--;
            return value;
        }

        //展示栈（遍历）
        public void showStack() {
            //判断是否为空
            if (isEmpty()) {
                System.out.println("数组为空，无法遍历");
                return;
            }
            //循环
            for (int i = Top; i >= 0; i--) {
                System.out.printf("Stack[%d]=%d", i, Stack[i]);
                System.out.println();
            }
        }

        //设置每个符号的优先级，可以用人为操控
        public int priority(char oper){
            if (oper == '*' || oper == '/'){
                return 1;
            }else if (oper == '+' || oper == '-'){
                return 0;
            }else {
                return -1;//目前只计算+ - * /
            }
        }
        //判断是不是一个运算符
        public boolean isOperate(char val){
            if (val == '*' || val == '/' || val == '+' ||val == '-'){
                return true;
            }
            return false;
        }

        //计算方法
        public int Cal(int num1,int num2,char oper){
            int res = 0;
            switch (oper){
                case '*' :
                    res = num1 * num2;
                    break;
                case '-':
                    res = num2 - num1;//被减数先入栈，减数后入栈，所以要后面的减前面的
                    break;
                case '/':
                    res = num2/num1;
                    break;
                case '+':
                    res = num1 + num2;
                    break;
                default:
                    System.out.println("特殊错误");
                    break;
            }
            return res;
        }

        //增加一个方法可以看到栈顶的元素
        public int showTop(){
            return Stack[Top];
        }
```

### 前缀，中缀，后缀表达式

#### 前缀表达式（波兰表达式）

1. 前缀表达式又称波兰表达式，前缀表达式的运算符位于操作数之前
2. **举例说明**：(3+4) x5-6对应的前缀表达式- x 3 4 5 6

从右至左扫描表达式，遇到数字时，将数字压入堆栈，遇到运算符时，弹出栈顶的两个数，用运算符对它们做相应的计算（栈顶元素 和
次顶元素），并将结果入栈；重复上述过程直到表达式最左端，最后运算得出的值即为表达式的结果。
例如: **(3+4)×5-6** 对应的前缀表达式就是 **- × + 3 4 5 6** , 针对前缀表达式求值步骤如下:
从右至左扫描，将6、5、4、3压入堆栈
遇到+运算符，因此弹出3和4（3为栈顶元素，4为次顶元素），计算出3+4的值，得7，再将7入栈
接下来是×运算符，因此弹出7和5，计算出7×5=35，将35入栈
最后是-运算符，计算出35-6的值，即29，由此得出最终结果。

#### 中缀表达式

1. 中缀表达式就是常见的运算表达式，**如(3+4)×5-6**
2. 中缀表达式的求值是我们人最熟悉的，但是对计算机来说**却不好操作**(前面我们讲的案例就能看的这个问题)
   ，因此，在计算结果时，往往会将中缀表达式转成其它表达式来操作(一般转成后缀表达式.)

#### 后缀表达式

1. 后缀表达式又称逆波兰表达式,与前缀表达式相似，只是运算符位于操作数之后
2. 举例说明： (3+4)×5-6 对应的后缀表达式就是 3 4 + 5 × 6 –
3. 计算方法：从左至右扫描表达式，遇到数字时，将数字压入堆栈，遇到运算符时，弹出栈顶的两个数，用运算符对它们做相应的计算（次顶元素
   和 栈顶元素），并将结果入栈；重复上述过程直到表达式最右端，最后运算得出的值即为表达式的结果。
4. 再比如:4586082x/++-

| 正常表达式     | 逆波兰表达式  |
|:----------|---------|
| a+b       | ab+     |
| a+(b-c)   | abc-+   |
| a+(b-c)*d | abc-d*+ |
| a+d*(b-c) | adbc-*+ |
| a=1+3     | a13+=   |

### 逆波兰计算器

我们完成一个逆波兰计算器，要求完成如下任务:

1. 输入一个逆波兰表达式(后缀表达式)，使用栈(Stack), 计算其结果
2. 只支持对整数的计算。
3. 例如计算：(3+4) x5-6 ---->后缀表达式 == 3 4 + 5 x 6 -

思路：

先创建一个ArrayList来存放数字和符号，然后通过方法将ArrayList传入到stack中

从左到右进行扫描，将3和4压入栈

遇到+运算符，因此弹出4(栈顶)3(次栈顶)进行相加为7，7入栈

5入栈

继续扫描遇到+弹出5和7进行相乘为35，将35入栈

6入栈

遇到- 计算35-6的值，得29

```java
public static void main(String[] args) {
        //先定义逆波兰表达式
        //(3+4)x5-6 ---> 3 4 + 5 x 6 -;

        //先定义一个表达式
        String ReverseExpression = "3 4 + 5 x 6 -";
        int res = Calculate(getString(ReverseExpression));
        System.out.printf("res=%d",res);
    }

    public static List<String> getString(String expression) {
        //先创建一个ArrayList,将每一个元素添加进去
        //通过空格隔开
        String[] spit = expression.split(" ");
        return new ArrayList<>(Arrays.asList(spit));
    }

    public static int Calculate(List<String> list) {
        /**
         * 从左至右扫描，将3和4压入堆栈；
         * 遇到+运算符，因此弹出4和3（4为栈顶元素，3为次顶元素），计算出3+4的值，得7，再将7入栈；
         * 将5入栈；
         * 接下来是×运算符，因此弹出5和7，计算出7×5=35，将35入栈；
         * 将6入栈；
         * 最后是-运算符，计算出35-6的值，即29，由此得出最终结果
         */
        //先创建一个栈
        Stack<Integer> stack = new Stack<>();
        //扫描ArrayList，通过正则表达式
        for (String item : list) {
            if (item.matches("\\d+")) {
                //有至少一个数字就直接入栈
                stack.add(Integer.parseInt(item));
            } else {
                int res = 0;
                //因为除法和减法都是后弹出来的除/减第一个所以这么num1作为后弹出来的·
                int num2 = stack.pop();
                int num1 = stack.pop();
                if (item.equals("+")) {
                    res = num1 + num2;
                } else if (item.equals("-")) {
                    res = num1 - num2;
                }else if (item.equals("x")){
                    res = num1 * num2;
                }else if (item.equals("/")){
                    res = num1/num2;
                }else {
                    throw new RuntimeException("无效符号");
                }
                //将res入栈
                stack.push(res);
            }

        }
        return stack.pop();
    }
```

```
结果为res = 29
```

## 递归

### 递归的应用场景

实际应用场景，迷宫问题(回溯)， 递归(Recursion)

![1665666530751](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vxa1iv-2.png)

### 递归的概念

简单的说：递归就是方法自己调用自己，每次调用时传入不同的变量，递归有助于编程者解决复杂问题，同时可以让代码变得简洁。

### 递归的调用机制

1. 打印问题
2. 阶乘问题

### 递归的调用规则

1. 程序执行到一个方法的时候就开辟一块独立的空间（栈空间）。
2. 每个空间的数据(局部变量)，是独立的。
3. 当一个方法执行完毕的时候，或者是遇到return的时候，就会返回，遵守谁调用就将结果返回给谁，同时当方法执行完毕或者返回时候，该方法也执行完毕，只有上一个方法执行完毕（出栈）后，后面的方法才会继续执行。
4. 递归必须向递归退出的条件无限逼近不然的话就会di di di下去了最后就会导致栈溢出。
5. 如果方法中使用的是引用数据类型(比如数组)，就会共享该引用数据类型的数据。

![1665668362887](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vxantt-2.jpg)

```java
//打印问题
    public static void test(int n) {
        if (n > 2) {
            test(n - 1);
        }
        System.out.println("n=" + n);
    }

//阶乘
public static int factorial(int n) {
        if (n == 1) {
            return 1;
        } else {
            return factorial(n - 1) * n;
        }}
```

### 递归能解决的问题

1. 各种数学问题如: 8皇后问题 , 汉诺塔, 阶乘问题, 迷宫问题, 球和篮子的问题(google编程大赛)
2. 各种算法中也会使用到递归，比如快排，归并排序，二分查找，分治算法等.
3. 将用栈解决的问题-->第归代码比较简洁

### 递归迷宫问题

![1665752852123](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vxazul-2.png)

说明：

1. 小球得到的路径，和程序员设置的找路策略有关即：找路的上下左右的顺序相关
2. 再得到小球路径时，可以先使用(下右上左)，再改成(上右下左)，看看路径是不是有变化
3. 测试回溯现象
4. 思考: 如何求出最短路径?

#### 代码实现

```java
public class MiGong {
    public static void main(String[] args) {
        //先初始化一个数组
        int[][] map = new int[8][7];
        //将第一行和第七行的每一列都用1来封上
        for (int i = 0; i < 7; i++) {
            map[0][i] = 1;
            map[7][i] = 1;
        }
        //第一列和第七列的每一行用1封上
        for (int i = 0; i < 8; i++) {
            map[i][0] = 1;
            map[i][6] = 1;
        }
        //设置挡板
        map[3][1] = 1;
        map[3][2] = 1;


        //输出地图
        System.out.println("最开始的地图");
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();

        }
        SetWay(map,1,1);
        System.out.println("小球走完后的---地图");
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();

        }

    }


    //1.map表示地图
    //2.i, j 表示小球的坐标（1，1）
    //3.当map[6][5] =2的时候表示路已经找到结束
    //4.约定:当map[i][j] 为1 的时候表示围墙，为2的时候表示该路走过了，为3的时候表示该路走过了但是走不通
    //5.指定找路的步骤 上 --- 右 ---- 下 ---- 左

    /**
     * @param map 传入的地图
     * @param i   小球的横坐标
     * @param j   小球的纵坐标
     */
    public static boolean SetWay(int[][] map, int i, int j) {
        if (map[6][5] == 2){//如果终点已经走到了那么就直接结束
            return true;
        }else {
            if (map[i][j] == 0){
                //想尝试去走
                map[i][j] = 2;
                //走着的路线上 --- 右 ---- 下 ---- 左
                if (SetWay(map,i-1,j+1)){//往上走
                    return true;
                }else if (SetWay(map,i,j+1)){//往右走
                    return true;
                }else if (SetWay(map,i+1,j)){//往下走
                    return true;
                }else if (SetWay(map,i,j-1)){//往左走
                    return true;
                }else{
                    //如果这个路线都走不通那么就是死路返回3
                    map[j][i] = 3;
                    return false;
                }

                }else {//map[j][i] != 0 ,可能是1，2(表示已经走过了不用重复再走），3
                return false;
            }

        }

    }
}
```

### 八皇后问题（回溯算法）

![image-20221015103105119](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vxbw1u-2.png)

#### 问题介绍

八皇后问题，是一个古老而著名的问题，是回溯算法的典型案例。该问题是国际西洋棋棋手马克斯·贝瑟尔于1848年提出：在8×8格的国际象棋上摆放八个皇后，
**使其不能互相攻击，即：任意两个皇后都不能处于同一行、同一列或同一斜线上**，问有多少种摆法。

#### 八皇后问题算法分析

1. 第一个皇后先放第一行第一列
2. 第二个皇后放在第二行第一列、然后判断是否OK， 如果不OK，继续放在第二列、第三列、依次把所有列都放完，找到一个合适
3. 继续第三个皇后，还是第一列、第二列……直到第8个皇后也能放在一个不冲突的位置，算是找到了一个正确解
4. 当得到一个正确解时，在栈回退到上一个栈时，就会开始回溯，即将第一个皇后，放到第一列的所有正确解，全部得到.
5. 然后回头继续第一个皇后放第二列，后面继续循环执行 1,2,3,4的步骤

说明：理论上应该创建一个二维数组来表示棋盘，但是实际上可以通过算法，用一个一维数组即可解决问题. arr[8] = {0 , 4, 7, 5, 2,
6, 1, 3} //对应 arr 下标 表示第几行，即第几个皇后，arr[i] = val ,  **每一个val对应的都是i+1个皇后**
，每一行对应一个皇后，放在第i+1行的第val+1列

#### 完整代码加分析

```java
public class Queue8 {
    //先初始化数组
    private int Max = 8;
    //保存皇后放置后的正确的位置 arr = {0,4,7,5,2,6,1,3}
    private int[] arr = new int[Max];
    private static int count = 0;
    private static int JudgeCount = 0;
    public static void main(String[] args) {
        new Queue8().Check(0);
        System.out.println("一共有"+count+"解法");
        System.out.println("一共回溯"+JudgeCount+"次");
    }



    //开始摆放
    private void Check(int n){
        //如果n == 8的时候就直接退出表明已经找了正确的放法
        if (n == Max){
            ShowSolution();//找到正确的放法后就输出答案
            return;
        }
        for (int i = 0; i < Max; i++) {
            //开始摆放棋子,把第一个皇后放在第一行的第一列
            arr[n] = i;
            if (judge(n)){
                Check(n+1);//这里是判断是否于遇上一个棋子产生冲突，
                // 如果与上一个产生了冲突那么这个位置就会在同行的基础上移动到下一列然后在继续判断直到不会参数冲突为止.
                //即在for循环中继续进行移动判断
            }
        }
    }
    //判断放置的位置是否正确
    /**
     *
     * @param n 表示第n个皇后
     * @return 是否和条件有冲突
     */
    private boolean judge(int n){
        JudgeCount++;
        for (int i = 0; i < n ; i++) {
            //第一个条件判断是否是在同一列
            //第二个条件判断是否是在同一斜线
            //加绝对值是为了防止产生负数
            if (arr[i] == arr[n] || Math.abs(n-i) == Math.abs(arr[n] - arr[i])){
                return false;
            }
        }
        return true;
    }

    //写一个方法来展示每一种解法
    private void ShowSolution(){
        count++;
        for (int i = 0; i < Max; i++) {
            System.out.print(arr[i]+ " ");
        }
        System.out.println();
    }
```

## 排序算法

### 排序算法的介绍

排序也称排序算法(Sort Algorithm)，排序是将一组数据，依指定的顺序进行排列的过程

内部排序：

指将需要处理的所有数据都加载到内部存储器中进行排序

外部排序法：

数据量过大，无法全部加载到内存中，需要借助外部存储进行排序

常见的排序算法

![image-20221016085749693](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vxcixn-2.png)

### 冒泡排序

#### 基本介绍

冒泡排序（Bubble Sorting）的基本思想是：

通过对待排序序列从前向后（从下标较小的元素开始）,依次比较相邻元素的值，若发现逆序则交换，使值较大的元素逐渐从前移向后部，就像水底下的气泡一样逐渐向上冒。

#### 冒泡排序实例

我们将五个无序的数：3, 9, -1, 10, -2 使用冒泡排序法将其排成一个从小到大的有序数列。

![image-20221016122815413](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vxdi92-2.png)

小结冒泡排序的规则

1. 一共进行数组的大小-1次大的循环
2. 每一趟排序的次数在逐渐的减少
3. 如果我们发现在某趟排序中，没有发生一次交换，可以提前结束冒泡排序（优化）

#### 代码实现（未优化版）

```java
public class Bubble {
    public static void main(String[] args) {
        //创建好要排序的数组
        int[] arr = new int[]{3, 9, -1, 10, -2};
        int tmp = 0;
        //直接再用一个for循环将他括起来
        //时间复杂度O(n^2)
        for (int j = 0; j < arr.length - 1; j++) {
            for (int i = 0; i < arr.length - 1 -j; i++) {
                if (arr[i] > arr[i + 1]) {//减1就是为了防止arr[i+1]数组越界
                    tmp = arr[i + 1];
                    arr[i + 1] = arr[i];
                    arr[i] = tmp;
                }
            }
            System.out.printf("第%d趟排序的数组",j+1);
            System.out.println(Arrays.toString(arr));
        }



        //由于后面for都是一样的arr.length - 1-1只是每一次都是相继减1所以可以把for循环套起来
        /*
         for (int i = 0; i < arr.length - 1 - 0; i++) {
                if (arr[i] > arr[i + 1]) {//减1就是为了防止arr[i+1]数组越界
                    flag = true;
                    tmp = arr[i + 1];
                    arr[i + 1] = arr[i];
                    arr[i] = tmp;
                }
            }
            System.out.printf("第1趟排序的数组",j+1);
        //减2因为10已经排过了就不用继续参与进来排序
        for (int i = 0; i < arr.length - 1-1; i++) {
            int tmp = 0;
            if (arr[i] > arr[i+1]){//减1就是为了防止arr[i+1]数组越界
                tmp = arr[i+1];
                arr[i+1] = arr[i];
                arr[i] = tmp;
            }
        }
        System.out.println("第二趟排序的数组");
        System.out.println(Arrays.toString(arr));

        for (int i = 0; i < arr.length - 1-2; i++) {
            int tmp = 0;
            if (arr[i] > arr[i+1]){//减1就是为了防止arr[i+1]数组越界
                tmp = arr[i+1];
                arr[i+1] = arr[i];
                arr[i] = tmp;
            }
        }
        System.out.println("第3趟排序的数组");
        System.out.println(Arrays.toString(arr));

        for (int i = 0; i < arr.length - 1-3; i++) {
            int tmp = 0;
            if (arr[i] > arr[i+1]){//减1就是为了防止arr[i+1]数组越界
                tmp = arr[i+1];
                arr[i+1] = arr[i];
                arr[i] = tmp;
            }
        }
        System.out.println("第4趟排序的数组");
        System.out.println(Arrays.toString(arr));
         */
    }

}
```

#### 代码实现（优化版）

```java
public class Bubble {
    public static void main(String[] args) {
        //创建好要排序的数组
        int[] arr = new int[]{3, 9, -1, 10, 20};
        int tmp = 0;
        //直接再用一个for循环将他括起来
        //时间复杂度O(n^2)
        //优化的先创建一个flag,来表示是否交换，如果有趟都没有交换就直接结束循环
        boolean flag = false;
        for (int j = 0; j < arr.length - 1; j++) {
            for (int i = 0; i < arr.length - 1 - j; i++) {
                if (arr[i] > arr[i + 1]) {//减1就是为了防止arr[i+1]数组越界
                    flag = true;
                    tmp = arr[i + 1];
                    arr[i + 1] = arr[i];
                    arr[i] = tmp;
                }
            }
            System.out.printf("第%d趟排序的数组",j+1);
            System.out.println(Arrays.toString(arr));
            if (!flag){
                break;
            }else {
                flag = false;
            }
        }
```

![image-20221017192407164](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vxeplt-2.png)

这是冒泡排序所需要的时间

### 选择排序

#### 基本介绍

选择式排序也属于内部排序法，是从欲排序的数据中，按指定的规则选出某一元素，再依规定交换位置后达到排序的目的。

#### 选择排序思想

选择排序（select sorting）也是一种简单的排序方法。它的基本思想是：第一次从arr[0]~arr[n-1]中选取最小值，与arr[0]
交换，第二次从arr [1] ~ arr[n-1]中选取最小值，与arr[1]交换，第三次从 arr [2]~arr[n-1]中选取最小值，与arr[2]交换，…，第 i
次从arr[i-1]~arr[n-1]中选取最小值，与arr[i-1]交换，…, 第n-1次从arr[n-2]~arr[n-1]中选取最小值，与arr[n-2]
交换，总共通过n-1次，得到一个按排序码从小到大排列的有序序列。

![image-20221017091455336](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vxfe1u-2.png)

选择排序小结：

选择排序一共有数组大小 -1轮排序

每一轮排序，又是一个循环。

循环规则：

1. 先假定当前这个数就是最小数
2. 然后和后面的每一个数进行比较，如果发现有比当前数更小的数，就重新确定最小数，并得到下标。
3. 当遍历到数组的最后时，就得到本轮最小数和下标
4. 就开始进行交换

#### 代码实现（未优化）

```java
public class SelectSort {
    public static void main(String[] args) {
        int[] arr = new int[]{101, 34, 119, 1};
        select(arr);

    }

    public static void select(int[] arr){
        //测试数组 {101, 34, 119, 1}
        //为了方便理解先进行一轮一轮排序来操作
        //首先我们先假定这个数组中的最小值就是第一个
        int minIndex = 0;
        int min = arr[minIndex];
        //进行循环比较找出真正的最小值
        //这里因为已经假定了一个所以为了不重复比较i就从1开始
        for (int i = 1+0; i < arr.length; i++) {
            //如果我们假定的最小值不是真正的最小值那么就把比较后的最小值作为我们的最小值
            if(min > arr[i]){
                min = arr[i];
                minIndex = i;
            }
            //如果找到了最小值就开始进行交换;
        }
        arr[minIndex] = arr[0];
        arr[0] = min;
        System.out.println("第一趟排序");
        System.out.println(Arrays.toString(arr));



        minIndex=1;
        min = arr[minIndex];
        for (int i = 2; i < arr.length; i++) {
            //如果我们假定的最小值不是真正的最小值那么就把比较后的最小值作为我们的最小值
            if(min > arr[i]){
                min = arr[i];
                minIndex = i;
            }
            //如果找到了最小值就开始进行交换;
        }
        arr[minIndex] = arr[1];
        arr[1] = min;
        System.out.println("第二趟排序");
        System.out.println(Arrays.toString(arr));//由于第二轮交换的时候，

        minIndex=2;
        min = arr[minIndex];
        for (int i = 3; i < arr.length; i++) {
            //如果我们假定的最小值不是真正的最小值那么就把比较后的最小值作为我们的最小值
            if(min > arr[i]){
                min = arr[i];
                minIndex = i;
            }
            //如果找到了最小值就开始进行交换;
        }
        arr[minIndex] = arr[2];
        arr[2] = min;
        System.out.println("第3趟排序");
        System.out.println(Arrays.toString(arr));//由于第二轮交换的时候，交换的时候有一个数字其实已经排好了，所以可以不用在交换
    }
}


```

#### 代码实现（优化）

```java
public class SelectSort {
    public static void main(String[] args) {
        int[] arr = new int[]{101, 34, 119, 1};
        select(arr);

    }

    public static void select(int[] arr){
        //测试数组 {101, 34, 119, 1}
        //为了方便理解先进行一轮一轮排序来操作
        //首先我们先假定这个数组中的最小值就是第一个
        for (int j = 0; j < arr.length -1; j++) {
            //找到规律后就可以直接嵌套一个循环
            int minIndex = j;
            int min = arr[minIndex];
            //进行循环比较找出真正的最小值
            //这里因为已经假定了一个所以为了不重复比较i就从1开始
            for (int i = 1+j; i < arr.length; i++) {
                //如果我们假定的最小值不是真正的最小值那么就把比较后的最小值作为我们的最小值
                if(min > arr[i]){
                    min = arr[i];
                    minIndex = i;
                }
                //如果找到了最小值就开始进行交换;
            }
            if (minIndex != j){
                //有可能有的元素位置是已经排好了的这个时候就可以不用在进行排序，所以这里加上判断就可以优化一下
                arr[minIndex] = arr[j];
                arr[j] = min;
            }
        }
        
```

![image-20221017192234483](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vxoypd-2.png)

这是选择排序排8w数据所需要的时间

### 插入排序

#### 插入排序介绍

插入式排序属于内部排序法，是对于欲排序的元素以插入的方式找寻该元素的适当位置，以达到排序的目的。

#### 插入排序思想

插入排序（Insertion
Sorting）的基本思想是：把n个待排序的元素看成为一个有序表和一个无序表，开始时有序表中只包含一个元素，无序表中包含有n-1个元素，排序过程中每次从无序表中取出第一个元素，把它的排序码依次与有序表元素的排序码进行比较，将它插入到有序表中的适当位置，使之成为新的有序表。

#### 插入排序图解

![image-20221017195251715](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vxpv7v-2.png)

#### 分部实现代码

```java
public class InsertSort {
    public static void main(String[] args) {
        Insert(new int[] {101, 34, 119, 1});
    }

    public static void Insert(int[] arr){
        //为了方便理解我们一轮一轮来(同时方便我的理解)
        //测试数组{101, 34, 119, 1}
        //将数组的第一个值看作一个有序数组，他后面的为无序数组，从这个无序的数组中抽出第一个来进行插入
        //先储存要插入的值，
        int indexValue = arr[1];//第一个看作一个值的数组所以从第二个值开始比较插入
        int index = 1-1;
        while(index>=0 && indexValue < arr[index]){
            //index>=0就是为了防止索引越界，为-1;
            //indexValue < arr[index] 找到了待插入的数，就要让比待插入的数大的值往后移
            //同时也是为了退出循环
            arr[index+1] = arr[index];
            index--;
        }
        arr[index+1] = indexValue;//当while循环结束的时候，说明插入的位置找到index就要+1；
        System.out.println("第一轮排序");
        System.out.println(Arrays.toString(arr));


        indexValue = arr[2];//第一个看作一个值的数组所以从第二个值开始比较插入
        index =2-1;
        while(index>=0 && indexValue < arr[index]){
            //index>=0就是为了防止索引越界，为-1;
            //indexValue < arr[index] 找到了待插入的数，就要让比待插入的数大的值往后移
            //同时也是为了退出循环
            arr[index+1] = arr[index];
            index--;
        }
        arr[index+1] = indexValue;//当while循环结束的时候，说明插入的位置找到index就要+1；
        System.out.println("第2轮排序");
        System.out.println(Arrays.toString(arr));


        indexValue = arr[3];//第一个看作一个值的数组所以从第二个值开始比较插入
        index =3-1;
        while(index>=0 && indexValue < arr[index]){
            //index>=0就是为了防止索引越界，为-1;
            //indexValue < arr[index] 找到了待插入的数，就要让比待插入的数大的值往后移
            //同时也是为了退出循环
            arr[index+1] = arr[index];
            index--;
        }
        arr[index+1] = indexValue;//当while循环结束的时候，说明插入的位置找到index就要+1；
        System.out.println("第3轮排序");
        System.out.println(Arrays.toString(arr));
    }



}
```

#### 完整代码

```java
public class InsertSort {
    public static void main(String[] args) {
        int[] arr = {101, 34, 119, 1};
        Insert(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void Insert(int[] arr){
        for (int i = 1; i < arr.length ; i++) {
            int indexValue = arr[i];
            int index = i-1;
            while (index>=0 && indexValue<arr[index]){
                arr[index+1] = arr[index];
                index--;
            }
            if (index+1 != i){
            arr[index+1] = indexValue;
        }
        }
```

![image-20221017224828027](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vxqjt1-2.png)

插入排序大概我这里就是一秒钟，就可以完成

### 希尔排序

#### 希尔排序的引入

简单插入排序存在的问题
我们看简单的插入排序可能存在的问题.
数组 arr = {2,3,4,5,6,1} 这时需要插入的数 1(最小), 这样的过程是：
{2,3,4,5,6,6}
{2,3,4,5,5,6}
{2,3,4,4,5,6}
{2,3,3,4,5,6}
{2,2,3,4,5,6}
{1,2,3,4,5,6}
结论: 当需要插入的数是较小的数时，后移的次数明显增多，对效率有影响.

#### 希尔排序的介绍

希尔排序是希尔（Donald Shell）于1959年提出的一种排序算法。希尔排序也是一种插入排序，它是简单插入排序经过改进之后的一个更高效的版本，也称为
**缩小增量排序**

#### 希尔排序的思想

希尔排序是把记录按下标的一定增量分组，对每组使用直接插入排序算法排序；随着增量逐渐减少，每组包含的关键词越来越多，当增量减至1时，整个文件恰被分成一组，算法便终止。即就是将数组.length/2,进行分组。进行宏观调控

#### 希尔排序图解

操作步骤：

​ 初始时，有一个大小为 10 的无序序列。

1. 在第一趟排序中，我们不妨设 gap1 = N / 2 = 5，即相隔距离为 5 的元素组成一组，可以分为 5 组。

2. 接下来，按照直接插入排序的方法对每个组进行排序。

3. 在第二趟排序中，我们把上次的 gap 缩小一半，即 gap2 = gap1 / 2 = 2 (取整数)。这样每相隔距离为 2 的元素组成一组，可以分为 2
   组。

4. 按照直接插入排序的方法对每个组进行排序。

5. 在第三趟排序中，再次把 gap 缩小一半，即gap3 = gap2 / 2 = 1。 这样相隔距离为 1 的元素组成一组，即只有一组。

6. 按照直接插入排序的方法对每个组进行排序。此时，排序已经结束。

   ------------------------------------------------

![image-20221018093416901](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vxr3nh-2.png)

![image-20221018093430594](D:\数据结构与算法笔记\image-20221018093430594.png)

#### 分步代码

```java
public class ShellSort {
    public static void main(String[] args) {
        //{8,9,1,7,2,3,5,4,6,0}
        int[] arr = {8, 9, 1, 7, 2, 3, 5, 4, 6, 0};
        Sort(arr);
        System.out.println(Arrays.toString(arr));
    }


    public static void Sort(int[] arr) {
        //为了方便理解，还是一步一步来实现
        //第一轮排序
        int tmp = 0;
        for (int i = 5; i < arr.length; i++) {
            //可以把i=5 i=6代入进去就能理解了 这个j-5是为了退出这一次内循环，进行下一次分组
            for (int j = i - 5; j >= 0; j -= 5) {
                //即相隔距离为 5 的元素组成一组,所以这里要j+5，j+x,x是看分成多少组
                if (arr[j] > arr[j + 5]) {
                    tmp = arr[j + 5];//小
                    arr[j + 5] = arr[j];//后面的位置是大的位置
                    arr[j] = tmp;//小的位置是小的
                }
            }
        }
        //第二轮，因为之前已经分为5组那么现在就是5/2为两组即相邻两个为一组
        for (int i = 2; i < arr.length; i++) {
            //可以把i=5 i=6代入进去就能理解了 这个j-2是为了退出这一次内循环，进行下一次分组
            for (int j = i - 2; j >= 0; j -= 2) {
                //即相隔距离为 2 的元素组成一组,所以这里要j+2，j+x,x是看分成多少组
                if (arr[j] > arr[j + 2]) {
                    tmp = arr[j + 2];
                    arr[j + 2] = arr[j];
                    arr[j] = tmp;
                }
            }
        }

        //第二轮，因为之前已经分为2组那么现在就是2/2 =1 所以就还有1组
        for (int i = 1; i < arr.length ; i++) {
            //可以把i=5 i=6代入进去就能理解了 这个j-1是为了退出这一次内循环，进行下一次分组
            for (int j = i-1; j >=0 ; j-=1) {
                //即相隔距离为 1 的元素组成一组,所以这里要j+1，j+x,x是看分成多少组
                if (arr[j] > arr[j+1] ){
                    tmp = arr[j+1];
                    arr[j+1] = arr[j];
                    arr[j] = tmp;
                }
            }
    }
}}
```

#### 完整代码(交换式)

```java
public static void Sort(int[] arr) {
        int tmp = 0;
    	//跟据上面的分步代码就可以看出来，我们创建一个循环将整个排序嵌套，就可以完成交换式的希尔排序
        for (int grp = arr.length/2; grp >0 ; grp/=2) {
            for (int i = grp; i < arr.length; i++) {
                //可以把i=5 i=6代入进去就能理解了 这个j-5是为了退出这一次内循环，进行下一次分组
                for (int j = i - grp; j >= 0; j -= grp) {
                    //即相隔距离为 5 的元素组成一组,所以这里要j+5，j+x,x是看分成多少组
                    if (arr[j] > arr[j + grp]) {
                        tmp = arr[j + grp];//小
                        arr[j + grp] = arr[j];//后面的位置是大的位置
                        arr[j] = tmp;//小的位置是小的
                    }
                }
            }
        }
    //测试代码
    /**
    	int[] arr = new int[80000];
        for (int i = 0; i < 80000; i++) {
            arr[i] = (int) (Math.random()*800000);
        }
        String patter = "YYYY-MM-dd HH:mm:ss";
        DateFormat dateFormat = new SimpleDateFormat(patter);
        Date date1 = new Date();
        System.out.println(dateFormat.format(date1));
        Sort(arr);
        Date date2 = new Date();
        System.out.println(dateFormat.format(date2));
        **/
```

希尔排序（交换式）会比较慢

![image-20221018105539168](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vxsalf-2.png)

耗时4s排完8w的数据

建议希尔排序自己debug看一遍就会明白了

#### 优化代码移动式

```java
public static void ShellSort2(int[] arr){
    for (int gar = arr.length; gar >0 ; gar/=2) {
        //从第gap个元素，逐个对其所在组进行直接插入排序操作
        for (int i = gar; i < arr.length ; i++) {
            int tmp = arr[i];
            int j= i;
            if (arr[i] < arr[j-gar]){
                while(j-gar >=0 && tmp < arr[j-gar]){
                    arr[j] = arr[j-gar];//移动
                    j-=gar;
                }
                arr[j] = tmp;//这里减去了相隔的距离
        }
    }
        //还是建议debug自己观看
```

![image-20221019134820223](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/w3vm1g-2.png)

移动式排800w的数据需要2s。

### 快速排序

#### 快速排序法介绍

快速排序（Quicksort）是对冒泡排序的一种改进。基本思想是：通过一趟排序将要排序的数据分割成独立的两部分，其中一部分的所有数据都比另外一部分的所有数据都要小，然后再按此方法对这两部分数据分别进行快速排序，整个排序过程可以递归进行，以此达到整个数据变成有序序列，

#### 快速排序法示意图

![image-20221019122657840](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vxt9xu-2.png)

#### 快速排序图解+思路

分别从初始序列“6 1 2 7 9 3 4 5 10
8”两端开始“探测”。先从右往左找一个小于6的数，再从左往右找一个大于6的数，然后交换他们。这里可以用两个变量i和j，分别指向序列最左边和最右边。我们为这两个变量起个好听的名字“哨兵i”和“哨兵j”。刚开始的时候让哨兵i指向序列的最左边（即i=1），指向数字6。让哨兵j指向序列的最右边（即j=10），指向数字。

![image-20221019123527459](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vxu4ym-2.png)

首先哨兵j开始出动。因为此处设置的基准数是最左边的数，所以需要让哨兵j先出动，这一点非常重要（原因看后面解释）。哨兵j一步一步地向左挪动（即j–），直到找到一个小于6的数停下来。接下来哨兵i再一步一步向右挪动（即i++），直到找到一个数大于6的数停下来。最后哨兵j停在了数字5面前，哨兵i停在了数字7面前。

![image-20221019124539927](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vxuk0n-2.png)

​

到此，第一次交换结束。接下来开始哨兵j继续向左挪动（再友情提醒，每次必须是哨兵j先出发）。他发现了4（比基准数6要小，满足要求）之后停了下来。哨兵i也继续向右挪动的，他发现了9（比基准数6要大，满足要求）之后停了下来。此时再次进行交换

![image-20221019124854252](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vxv2ta-2.png)

交换之后的序列如下：

6 1 2 5 4 3 9 7 10 8

第二次交换结束，“探测”继续。哨兵j继续向左挪动，他发现了3（比基准数6要小，满足要求）之后又停了下来。哨兵i继续向右移动，糟啦！此时哨兵i和哨兵j相遇了，哨兵i和哨兵j都走到3面前。说明此时“探测”结束。我们将基准数6和3进行交换。交换之后的序列如下：

3 1 2 5 4 6 9 7 10 8

到此第一轮“探测”真正结束。此时以基准数6为分界点，6左边的数都小于等于6，6右边的数都大于等于6。回顾一下刚才的过程，其实哨兵j的使命就是要找小于基准数的数，而哨兵i的使命就是要找大于基准数的数，直到i和j碰头为止。

接下来就是重复找基点，按照之前的排序进行就可以将整个数字

```java
public class QuickSort {
    public static void main(String[] args) {
        int[] arr = {6,1,2,7,9,3,4,5,10,8};
        quick(arr,  0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }


    public static void quick(int[] arr, int left, int right) {
        //如果左边大于右边的话就是不合法的就直接返还
        if (left > right) {
            return;
        }
        //我们这里找基点一般从最左边开始
        //确定基准点
        int base = arr[left];
        //定义变量j指向最左边
        int i = left;
        //定义变量j指向最右边
        int j = right;
        //当j和i相等的时候就跳出循环
        while (j != i) {
            //先由i从右往左找到比基数小的，找到了就停下没有找到就继续找,这里必须要让j先移动，如果先让i移动的话
            while (arr[j] >= base && j < i) {
                //如果找不到比基数小的数那么就，j就继续移动
                j--;
            }
            //这里是找比基数大的，如果没有找到那么i就向前移动，找到了就停下
            while (arr[i] <= base && j < i) {
                i++;//从左往有检索
            }
            //交换两个数字
            int temp = arr[j];//这个是比基数小的数，先用变量保存下来，他现在是在左边
            arr[j] = arr[i];//arr[i]这个比基数大的数。现在在左边，要把他移动到右边去，所以就j的位置就变成比基点大的数
            arr[i] = temp;//i的位置就变成小的数
        }
        //当j,i相等的时候就，上面的while就会跳出循环，如果不成立那么那个while就会继续执行
        arr[left] = arr[j];//把相遇的元素赋值给基数的位置
        arr[j] = base;
        //递归来排左边的位置
        quick(arr,left,j-1);
        //递归排右边的位置
        quick(arr,i+1,right);
    }
}
```

#### 快速排序为什么要从基准数位置的另一侧先开始查找

**原因**：从基准数位置的另一侧先开始查找，可以保证在两侧“探兵”相遇时，新基准值不会大于旧基准值(默认规则是升序排列)。

以 6 1 2 7 9为例

![image-20221019132443815](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vxvu07-2.png)

i先走 走到i = 3 的时候满足了比基点大这一个准则，所以这个while循环就会退出进入到下一个循环

![image-20221019132623207](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vxvzia-2.png)

当 j 走到7的时候，前面那个条件是满足的但是他已经不能满足j> i 这个条件所以他就会在这里进行交换，但是这个时候的顺序7 1 2 6 9

所以在于当我们先从在边开始时，那么 i 所停留的那个位置肯定是大于基数6的，为了满足 i<j 于是 j也停留在7的位置，但最后交换回去的时候，7就到了左边。

所以这就是为什么要先从基点的另外一侧开始查找。

![image-20221019134620899](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/w3ygi3-2.png)

快速排序排800w的数据1s不到就能排完

### 归并排序

#### 归并排序介绍

归并排序（MERGE-SORT）是利用归并的思想实现的排序方法，该算法采用**经典的分治（divide-and-conquer）策略**（分治法将问题分(
divide)成一些小的问题然后递归求解，而治(conquer)的阶段则将分的阶段得到的各答案"修补"在一起，即分而治之)。

#### 归并排序的思想

![image-20221019140810586](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vxxetn-2.png)

#### 归并排序思想示意图2-合并相邻有序子序列（最后一个阶段）

再来看看治阶段，我们需要将两个已经有序的子序列合并成一个有序序列，比如上图中的最后一次合并，要将[4,5,7,8]和[1,2,3,6]
两个已经有序的子序列，合并为最终序列[1,2,3,4,5,6,7,8]，来看下实现步骤

![image-20221019141840740](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vxy1kw-2.png)

这里是分别有两个指针，i，j。先比较j和i对应的值，如果 j 的值比i小那么 i 就放到临时数组中，j就向前移动一位，如果 i 的值比 j 小

那么就 i 对应的值放到临时数组当中去，如果j中已经没有数据了那么就按照顺序将i中后面的数据依次放进去。

#### 完整代码实现

```java
public class MargetSort {
    public static void main(String[] args) {
        int [] arr = new int[8000000];
        for (int i = 0; i < 8000000; i++) {
            arr[i] = (int) (Math.random()*8000000);
        }
        int[] temp = new int[arr.length];
        long time1 = System.currentTimeMillis();
        mergeSort(arr,0, arr.length-1,temp);
        System.out.println(System.currentTimeMillis() - time1+"ms");
    }
    //分+合并的方法
    public static void mergeSort(int[] arr,int left,int right,int[] temp){
        if (left< right){
            int mid = (left+right)/2;
            //向左递归进行分解
            mergeSort(arr,left,mid,temp);
            //向右分递归进行分解
            mergeSort(arr,mid+1,right,temp);
            //开始合并
            merge(arr,left,mid,right,temp);
        }
    }

    /**
     * 
     * @param arr 排序的原始数组
     * @param left 数组的开始索引
     * @param mid 中间位置的索引
     * @param right 左边的索引
     * @param temp 临时的数组
     */
    public static void merge(int[] arr,int left,int  mid,int right,int[] temp){
        int i = left;//先初始化下标i
        int j = mid + 1;//这是第二部分数组的下标
        int t = 0;//临时数组的下标
        //1)先把左右两边（有序的数组）的数据按照规则填充到temp数组
        //直到左右两边都为有序序列，有一边处理完为止
        while (i <= mid && j <= right){
            //说明数组还没有排序完成
            if (arr[i] >= arr[j]){
                //当右边的数大于左边的数的时候，就把左边比右边小的数加入到临时数组中
                temp[t] = arr[j];
                t+=1;
                j+=1;
            }else {
                temp[t] = arr[i];
                t++;
                i+=1;
            }
        }
        //2)将剩下没有添加完的数据按顺序添加到temp中去
        while (i<=mid){
            temp[t] = arr[i];
            i++;
            t++;

        }
        while (j <= right){
            temp[t] = arr[j];
            t++;
            j++;
        }

        //3)将所以排好序的数组一次复制回原数组
        //注意，并不是拷贝所有
        int tempLeft = left;//临时的变量
        while (tempLeft <= right){
            arr[tempLeft] = temp[t];
            tempLeft+=1;
            t+=1;
        }
    }
}
```

![image-20221020193743086](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vxzqng-2.png)

归并排序同等800w数据他用了1064ms

### 基数排序

#### 基数排序介绍

**基数排序**（radix sort）属于“分配式排序”（distribution sort），又称“桶子法”（bucket sort）或bin sort，顾名思义，它是通过键值的各个位的值，将要排序的
**元素分配**至某些“桶”中，达到排序的作用

基数排序法是属于稳定性的排序，基数排序法的是效率高的稳定性排序法

基数排序(Radix Sort)是**桶排序**的扩展

基数排序是1887年赫尔曼·何乐礼发明的。它是这样实现的：将整数按位数切割成不同的数字，然后按每个位数分别比较。

#### 基数排序基本思想

将所有待比较数值统一为同样的数位长度，数位较短的数前面补零。然后，从最低位开始，依次进行一次排序。这样从最低位排序一直到最高位排序完成以后,
数列就变成一个有序序列。

#### 基数排序图解

将数组 {53, 3, 542, 748, 14, 214} 使用基数排序, 进行升序排序。

数组的初始状态
arr = {53, 3, 542, 748, 14, 214}

说明：因为有个位数含10位数，所以就要准备10个桶来存放数字

第1轮排序：

1. 将每个元素的个位数取出，然后看这个数应该放在哪个对应的桶（一个一维数组，在代码中用一个二维数组来表示10个桶）
2. 按照这个桶的顺序（一维数组的下标依次取出数据，放入原来的数组）

![image-20221020195956661](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vy0iv5-2.png)

第2轮排序：

1. 将每个元素的十位数取出，然后看这个数应该放在哪个对应的桶(一个一维数组)
2. 按照这个桶的顺序(一维数组的下标依次取出数据，放入原来数组)

![image-20221020200140337](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vy9nij-2.png)

第3轮排序:

1. 将每个元素百位数取出，然后看这个数应该放在哪个对应的桶(一个一维数组)
2. 按照这个桶的顺序(一维数组的下标依次取出数据，放入原来数组)

![image-20221020200223974](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vyabjw-2.png)

#### 基数排序逐步代码

```java
public class RadixSort {
    public static void main(String[] args) {
        int[] arr = {53, 3, 542, 748, 14, 214};
        radixSort(arr);
    }
    //基数排序方法
    public static void radixSort(int[] arr){
        //定义一个二维数组，表示10个桶，每一个桶就是一个二维数 组
        //说明
        //1.二维数组包含了10个一维数组
        //2.为了防止在放入数的时候，数据溢出，则每一个一维数组的长度就为arr.length
        //3.基数排序就是典型的空间换时间的算法。
        int[][] bucket = new int[10][arr.length];
        //创建一个一维数组来存放每一个桶中到底放了多少个元素
        //分别记录了10个桶中元素的个数
        int[] ElementCount = new int[10];
        //将要排序的数组中的数按个位数放入到桶中

        //第一轮(针对每一个元素的个位进行排序处理)
        for (int i = 0; i < arr.length; i++) {
            //获取数组中每一个元素的个位数，按照个位数对应的数字放入到对应桶中
            int digit = arr[i] /1 % 10;
            //说明：
            // ElementCount[digit]是初始的数组，没有加入数据之前默认每一个位置为0
            //所以ElementCount[digit]这个时候就为0，第一次加入进来数放在第一位
            //举个例子，如果第一次余了1，那么bucketCounts【1】的值是0，因此之前没有其他数余1,在一维数组（即第一个桶）的首位就会放一个数
            //然后ElementCount[digit]++，表示了第一个桶中，元素的个数为1
            bucket[digit][ElementCount[digit]] = arr[i];
            ElementCount[digit]++;
        }
        //依次将桶中的元素取出然后放入原数组中
        int index = 0;
        for (int k = 0; k < ElementCount.length; k++) {
            //遍历每一个桶查看桶的元素,先判断是否有元素，如果放了值就不是0
            if (ElementCount[k] != 0){
                //ElementCount[k]这里得到的时候桶中元素的个数通过ElementCount记录的，所以j只要遍历小于桶中数量就行
                for (int j = 0; j < ElementCount[k]; j++) {
                    arr[index] = bucket[k][j];
                    index++;
                }
                //每次取出来过后要将之前的用来计数每一个桶中的元素的数量进行一个清0！！！！
                ElementCount[k] = 0;
            }
        }
        System.out.println(Arrays.toString(arr));

        //==================================第二轮的处理==============================
        for (int i = 0; i < arr.length; i++) {
            //获取数组中每一个元素的十位数，按照十位数对应的数字放入到对应桶中
            int digit = arr[i] /10 % 10; //-> 748/10 = 74%10 = 4
            //说明：
            // ElementCount[digit]是初始的数组，没有加入数据之前默认每一个位置为0
            //所以ElementCount[digit]这个时候就为0，第一次加入进来数放在第一位
            //举个例子，如果第一次余了1，那么bucketCounts【1】的值是0，因此之前没有其他数余1,在一维数组（即第一个桶）的首位就会放一个数
            //然后ElementCount[digit]++，表示了第一个桶中，元素的个数为1
            bucket[digit][ElementCount[digit]] = arr[i];
            ElementCount[digit]++;
        }
        //依次将桶中的元素取出然后放入原数组中
        index = 0;
        for (int k = 0; k < ElementCount.length; k++) {
            //遍历每一个桶查看桶的元素,先判断是否有元素，如果放了值就不是0
            if (ElementCount[k] != 0){
                //ElementCount[k]这里得到的时候桶中元素的个数通过ElementCount记录的，所以j只要遍历小于桶中数量就行
                for (int j = 0; j < ElementCount[k]; j++) {
                    arr[index] = bucket[k][j];
                    index++;
                }
            }
            ElementCount[k] = 0;
        }
        System.out.println(Arrays.toString(arr));

        //=============================第三轮处理==============================
        for (int i = 0; i < arr.length; i++) {
            //获取数组中每一个元素的百位数，按照百位数对应的数字放入到对应桶中
            int digit = arr[i] /100 % 10; //-> 748/10 = 74%10 = 4
            //说明：
            // ElementCount[digit]是初始的数组，没有加入数据之前默认每一个位置为0
            //所以ElementCount[digit]这个时候就为0，第一次加入进来数放在第一位
            //举个例子，如果第一次余了1，那么bucketCounts【1】的值是0，因此之前没有其他数余1,在一维数组（即第一个桶）的首位就会放一个数
            //然后ElementCount[digit]++，表示了第一个桶中，元素的个数为1
            bucket[digit][ElementCount[digit]] = arr[i];
            ElementCount[digit]++;
        }
        //依次将桶中的元素取出然后放入原数组中
        index = 0;
        for (int k = 0; k < ElementCount.length; k++) {
            //遍历每一个桶查看桶的元素,先判断是否有元素，如果放了值就不是0
            if (ElementCount[k] != 0){
                //ElementCount[k]这里得到的时候桶中元素的个数通过ElementCount记录的，所以j只要遍历小于桶中数量就行
                for (int j = 0; j < ElementCount[k]; j++) {
                    arr[index] = bucket[k][j];
                    index++;
                }
            }
            ElementCount[k] = 0;
        }
        System.out.println(Arrays.toString(arr));

    }
}
```

#### 完整代码

```java
public class RadixSort {
    public static void main(String[] args) {
        //如果是8kw的数据
        //80000000 * 11 * 4 /1024/1024/1024 = 3.3g,直接导致堆内存爆炸
        int [] arr = new int[8000000];
        for (int i = 0; i < 8000000; i++) {
            arr[i] = (int) (Math.random()*80000000);
        }
        int[] temp = new int[arr.length];
        long time1 = System.currentTimeMillis();
        radixSort(arr);
        System.out.println(System.currentTimeMillis() - time1+"ms");

    }
    //基数排序方法
    public static void radixSort(int[] arr){

        //根据规律我们就能开始处理
        //我们先找到这个数组中的最大位数，这决定了我们将要轮几次将这个数组排序完成
        //我们先假定第一个是最大的
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (max < arr[i]){
                max = arr[i];
            }
        }
        //拿到max值的位数
        int length = (max + "").length();
        //定义一个二维数组，表示10个桶，每一个桶就是一个二维数组
        //说明
        //1.二维数组包含了10个一维数组
        //2.为了防止在放入数的时候，数据溢出，则每一个一位数组的长度就为arr.length
        //3.基数排序就是典型的空间换时间的算法。
        int[][] bucket = new int[10][arr.length];
        //创建一个一维数组来存放每一个桶中到底放了多少个元素
        //分别记录了10个桶中元素的个数
        int[] ElementCount = new int[10];
        //将要排序的数组中的数按个位数放入到桶中

        //通过一个for循环来执行，现在改第一轮的代码来实现
        for (int m = 0,n = 1; m < length ; m++,n*=10) {
            for (int i = 0; i < arr.length; i++) {
                //获取数组中每一个元素的对应的数位的值，按照对应的数字放入到对应桶中
                int digit = arr[i] / n % 10;
                //说明：
                // ElementCount[digit]是初始的数组，没有加入数据之前默认每一个位置为0
                //所以ElementCount[digit]这个时候就为0，第一次加入进来数放在第一位
                //举个例子，如果第一次余了1，那么bucketCounts【1】的值是0，因此之前没有其他数余1,在一维数组（即第一个桶）的首位就会放一个数
                //然后ElementCount[digit]++，表示了第一个桶中，元素的个数为1
                bucket[digit][ElementCount[digit]] = arr[i];
                ElementCount[digit]++;
            }
            //依次将桶中的元素取出然后放入原数组中
            int index = 0;
            for (int k = 0; k < ElementCount.length; k++) {
                //遍历每一个桶查看桶的元素,先判断是否有元素，如果放了值就不是0
                if (ElementCount[k] != 0){
                    //ElementCount[k]这里得到的时候桶中元素的个数通过ElementCount记录的，所以j只要遍历小于桶中数量就行
                    for (int j = 0; j < ElementCount[k]; j++) {
                        arr[index] = bucket[k][j];
                        index++;
                    }
                    //每次取出来过后要将之前的用来计数每一个桶中的元素的数量进行一个清0！！！！
                    ElementCount[k] = 0;
                }
            }

        }
```

------

![image-20221025221506417](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vybhvy-2.png)

### 堆排序（先看完二叉树）

##### 堆排序介绍

1. 堆排序是利用堆这种数据结构而设计的一种排序算法，堆排序是一种选择排序，它的最坏，最好，平均时间复杂度均为O(nlogn)
   ，它也是不稳定排序。

2. 堆是具有以下性质的完全二叉树：每个结点的值都大于或等于其左右孩子结点的值，称为大顶堆, 注意 : 没有要求结点的左孩子的值和右孩子的值的大小关系。

3. 每个结点的值都小于或等于其左右孩子结点的值，称为小顶堆

   大顶堆举例说明：

   ![image-20221028204320371](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vyc0eu-2.png)

大顶堆特点：arr[i] >= ***arr[2i+1] && arr[i] >= arr[2i+2]***  // i 对应第几个节点，i从0开始编号

小顶堆图示：

![image-20221028204424807](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vycnzs-2.png)

小顶堆：arr[i] **<= arr[2*i+1] && arr[i] <= arr[2*i+2]** // i 对应第几个节点，i从0开始编号
一般升序采用大顶堆，降序采用小顶堆

##### 堆排序的基本思想

**堆排序的基本思想是**:

1. 将待排序序列构造成一个大顶堆（这个过程是反复进行的）
2. 此时，整个序列的最大值就是堆顶的根节点。
3. 将其与末尾元素进行交换，此时末尾就为最大值。
4. 然后将剩余n-1 个元素重新构造成-一个堆，这样会得到n个元素的次小值。如此反复执行，便能得到一个有序序列了。

可以看到在构建大顶堆的过程中，元素的个数逐渐减少，最后就得到一个有序序列了。

##### 图解

要求:给你一个数组{4,6,8,5,9}, 要求使用堆排序法，将数组升序排序。

1. .假设给定无序序列结构如下

   ![image-20221029152316803](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vyd6t6-2.png)

2. 此时我们从最后一个非叶子节点开始(叶结点自然不用调整,第一个非叶子结点
   arr.length/2-1=5/2-1=1 ,也就是下面的6结点) , 从左至右,从下至上进行调整。

   ![image-20221029152445775](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vydkeu-2.png)

​ 找到6后，先让他的左右两个节点比较发现9比5大，不移动，然后在比较6，9，9比6大，就把9放到6的位置。

3.找到第二个非叶节点4,由于[4,9,8]中9元素最大, 4和9交换。

![image-20221029152702647](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vye0s2-2.png)

4.这时，交换导致了子根[4,5,6]结构混乱,继续调整, [4,5,6]中6最大,交换4和6。（这里还比较了左右节点谁比较大）

![image-20221029152818898](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vyeh6o-2.png)

这个时候我们就将一个无序的序列构造成了一个大顶堆。

**步骤二：**

这个时候就要将末尾和第一个位置的元素进行交换，使末尾的元素最大。**然后继续调整堆,再将堆顶元素与末尾元素交换** ，*
*得到第二大元素。如此反复进行交换、重建、交换。**

1.将堆顶元素9和末尾元素4进行交换

![image-20221029153118553](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vyfgd1-2.png)

2.重新调整结构,使其继续满足大顶堆定义

![image-20221029153155642](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vyg05r-2.png)

3.再将堆顶元素8与末尾元素5进行交换,得到第二大元素8

![image-20221029153320952](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vygel7-2.png)

4.后续过程,继续进行调整,交换,如此反复进行,最终使得整个序列有序

![image-20221029153340217](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vyh639-2.png)

最终我们得到了一个有序的序列。

##### 完整代码

```java
public class HeapSort {
    public static void main(String[] args) {
        int [] arr = new int[8000000];
        for (int i = 0; i < 8000000; i++) {
            arr[i] = (int) (Math.random()*8000000);
        }
        long time1 = System.currentTimeMillis();
        heapSort(arr);
        System.out.println(System.currentTimeMillis() - time1+"ms");
    }
    public static void heapSort(int[] arr){
        int tem = 0;
        //分部完成
//        adjustHeap(arr,1,arr.length);
//        System.out.println("第一次调整"+ Arrays.toString(arr));//4,9,8,5,6
//        adjustHeap(arr,0,arr.length);//这里还没有找到最大值就不用减1
//        System.out.println("第二次调整"+ Arrays.toString(arr));
        //终极代码
        for (int i = arr.length / 2 - 1; i >= 0 ; i--) {
            adjustHeap(arr,i,arr.length);
        }
        /**
         * 将堆顶的元素与末尾的元素进行交换，将最大值“沉”到数组的末端，即数组长度减1
         * 重新调整结构使其满足大顶堆的结构，重复以上操作
         */
        for (int i = arr.length -1; i >0  ; i--) {
            tem = arr[i];
            arr[i] = arr[0];
            arr[0] = tem;
            adjustHeap(arr,0,i);//这里填0，是应为除开根节点以下的节点已经排序为大顶堆的样子了所以不用在调整，只需调整根节点即可，因为我们是从下往上调整，所以就填0.
        }

    }

    /**
     * 将一个数组（二叉树）调整成一个大根堆
     * 功能：完成将以i对应的非叶子结点的树调整成大顶堆
     * 举例int arr[]={4,6,8,5,9};=>i=1=> adjustHeap=>得到{4,9,8,5,6}
     * 如果我们再次调用adjustHeap 传入的是i=0=>得到{4,9,8,5,6}=> {9,6,8,5,4}
     *
     * @param arr    传入一个数组
     * @param i      表示第几个非子叶节点
     * @param length 表示数组的长度，这个长度是逐渐减少的应为找到一个最大的数之后，就不在管他，长度对应减一
     */
    public static void adjustHeap(int[] arr, int i, int length) {
        int tem = arr[i];//表示对应的非叶子节点的值
        for (int k = i * 2 + 1; k < length; k = i * 2 + 1) {
            //这里的k是对应非子叶节点的左子节点
            if (k + 1 < length && arr[k] < arr[k + 1]) {
                k++;//非叶子节点的左右节点进行比较如果右节点大于左节点，那么k就要向右移动一位
            }
            if (tem < arr[k]){
                arr[i] = arr[k];//如果非叶子节点的左右节点比他大，那么就将大的值与其互换
                i = k;//然后将k的值赋给i
            }else {
                break;
            }
        }
        arr[i] = tem;//这里就是进行交换
    }
}
```

## 查找算法

### 查找算法介绍

​ 在java中，我们常用的查找有四种:

1. 顺序(线性)查找
2. 二分查找/折半查找

3. 插值查找

4. 斐波那契查找

### 线性查找

有一个数列： {1,8, 10, 89, 1000, 1234} ，判断数列中是否包含此名称【顺序查找】

要求: 如果找到了，就提示找到，并给出下标值。

```java
public class SeqSearch {
    public static void main(String[] args) {
        int[] arr = {11,12,11,13,14,15,17,20};
        System.out.println(seqSearch(arr, 11));
    }

    public static int seqSearch(int[] arr,int value){
        for (int i = 0; i < arr.length; i++) {
           if (value == arr[i]){
               return i;
           }

        }
        return -1;
    }


}
```

### 二分查找

请对一个**有序数组**进行二分查找 {1,8, 10, 89, 1000, 1234}
，输入一个数看看该数组是否存在此数，并且求出下标，如果没有就提示"没有这个数"。(有序数组这很重要)

#### 二分查找思路

1. 首先确定该数组的中间的下标
2. mid = (left + right) / 2
3. 然后让需要查找的数 findVal 和 arr[mid] 比较
4. findVal > arr[mid] , 说明你要查找的数在mid 的右边, 因此需要递归的向右查找
5. findVal < arr[mid], 说明你要查找的数在mid 的左边, 因此需要递归的向左查找
6. findVal == arr[mid] 说明找到，就返回

什么时候我们需要结束递归.

1) 找到就结束递归
2) 递归完整个数组，仍然没有找到findVal ，也需要结束递归 当 left > right 就需要退出

#### 未完善代码

```java
public class binarySearch {
    public static void main(String[] args) {
        int[] arr = {1,8, 10, 89, 1000, 1234};
        System.out.println(TowSearch(arr, 0, arr.length - 1, 1000));

    }

    /**
     * 注意二分查找要求的数组是有序的
     * @param arr 要搜寻的数组
     * @param left 左边的指针
     * @param right 右边的指针
     * @param value 需要查找的值
     */
    public static int TowSearch(int[] arr,int left,int right,int value){
        if (left > right){
            return -1;
        }
        int mid = (left+right)/2;
        if (value > arr[mid]){
            //如果查找的值大于了中间的数那么就要向右进行递归
            return TowSearch(arr,mid+1,right,value);
        }else if (value < arr[mid]){
            //如果查找的值大于了中间的数那么就要向左进行递归
            return TowSearch(arr,left,mid-1,value);
        }else {
            return mid;
        }
    }
```

#### 完善代码

```java
public static List<Integer> TowSearch2(int[] arr, int left, int right, int value){
        if (left > right){
            return new ArrayList<>();
        }
        int mid = (left+right)/2;
        if (value > arr[mid]){
            //如果查找的值大于了中间的数那么就要向右进行递归
            return TowSearch2(arr,mid+1,right,value);
        }else if (value < arr[mid]){
            //如果查找的值大于了中间的数那么就要向左进行递归
            return TowSearch2(arr,left,mid-1,value);
        }else {
//            1.首先找到了那个mid后不用急着返回
//            2.先继续扫描mid的左右两边，然后用一个ArrayList的集合将所有复合条件的索引装起来然后返回
            //先创建一个ArrayList集合
            List<Integer> list = new ArrayList<>();
            int temp = mid+1;
            while (true){
                //如果找到了后就先向mid的右边扫描，看看是否有符合的数

                if (temp > right || arr[temp] != value){
                    break;
                }
                list.add(temp);
                temp+=1;
        }
            list.add(mid);
            temp = mid-1;
            while (true){
                //如果找到了后就先向mid的左边扫描，看看是否有符合的数
                if (temp < 0 || arr[temp] != value){
                    break;
                }
                list.add(temp);
                temp-=1;
            }
            return list;
    }
    }
```

### 插值查找（二分查找的一个改进）

插值查找算法类似于二分查找，不同的是插值查找每次从自适应mid处开始查找。
将折半查找中的求mid 索引的公式 , low 表示左边索引left, high表示右边索引right.key 就是前面我们讲的 findVal

![image-20221021195317271](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vyiij5-2.png)

int mid = low + (high - low) * (key - arr[low]) / (arr[high] - arr[low])  ;/*插值索引*/

对应前面的代码公式：int mid = left + (right – left) * (findVal – arr[left]) / (arr[right] – arr[left])

#### 插值查找的特点

（1）查找的序列必须有序

（2）对于**数据量大**的以及关键字**分布均匀**的有序序列来说，插值查找的速度较快。

（3）对于**分布不均匀**的有序序列来说，该算法不一定比二分查找要好。

![image-20221021195927387](http://8.137.11.22/i/2022/11/21/vyj07d-2.png)

如果对插值查找的公式推导感兴趣的话可以去这里看看

https://www.cnblogs.com/euler0525/p/16533592.html

#### 完整代码

```java
public class InsertValueSearch {
    public static void main(String[] args) {
        int[] arr = {1,8, 10,89, 1000,1000,1234};
//        System.out.println(TowSearch(arr, 0, arr.length - 1, 1000));
        System.out.println(insetValueSearch(arr, 0, arr.length - 1, 1000));
    }

    public static int insetValueSearch(int[] arr, int left, int right, int value) {
        //先判断
        //value < arr[0] || value > arr[arr.length -1],提高效率，并且如果输入的数太大防止数组越界
        if (left > right || value < arr[0] || value > arr[arr.length - 1]) {
            return -1;
        }
        int mid = left + (right - left) * (value - arr[left]) / (arr[right] - arr[left]);
        if (value > arr[mid]) {
            return insetValueSearch(arr, mid + 1, right, value);
        } else if (value < arr[mid]) {
            return insetValueSearch(arr, left, mid - 1, value);
        }else {
            return mid;
        }
    }
}
```

### 斐波那契(黄金分割法)查找

#### 斐波那契(黄金分割法)原理:

斐波那契查找原理与前两种相似，仅仅改变了中间结点（mid）的位置，mid不再是中间或插值得到，而是位于黄金分割点附近，即mid=low+F(
k-1)-1(F代表斐波那契数列),如下图所示

![image-20221022140800577](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vyjtol-2.png)

**对F(k-1)-1的理解：**

首先由斐波那契数列的特性, f(k)=f(k-1)+f(k-2), **规定待排序列长度为f[k]-1是为了格式上的统一**，以方便递归或者循环程序的编写

因为数组的下标都是从0开始的。

**mid = low+F(k - 1) - 1的解释**：

表中的数据是f[k]-1个，使用mid值进行分割又用掉一个，那么剩下f[k]-2个。正好分给两个子序列，每个子序列的个数分别是f[k-1]
-1与f[k-2]-1个，格式上与之前是统一的。

**在后续的分割区间并查找比对时, 当 查找值key < temp[mid]时, k -= 1, 当 查找值key > temp[mid]时, k-=2; 为什么会这样呢?**

因为当我们计算完黄金分割点的时候，用黄金分割点的下标对应的值进行判断

1. 如果key < temp[mid] 那么表明我们要查找的值在整个数组的左边那么就要到F[k-1] -1 这个序列里面找这个时候k-=1
2. 如果key > temp[mid] 那么表明我们要查找的值在整个数组的右边那么就要到F[k-2] -1 这个序列里面找这个时候k-=2

#### 完整代码实现

```java
public class FibonacciSearch {

	public static int maxSize = 20;
	public static void main(String[] args) {
		int [] arr = {1,8, 10, 89, 1000, 1234};
		
		System.out.println("index=" + fibSearch(arr, 189));// 0
		
	}
	//因为后面我们mid=low+F(k-1)-1，需要使用到斐波那契数列，因此我们需要先获取到一个斐波那契数列
	//非递归方法得到一个斐波那契数列
	public static int[] fib() {
		int[] f = new int[maxSize];
		f[0] = 1;
		f[1] = 1;
		for (int i = 2; i < maxSize; i++) {
			f[i] = f[i - 1] + f[i - 2];
		}
		return f;
	}
	
	//编写斐波那契查找算法
	//使用非递归的方式编写算法
	/**
	 * 
	 * @param a  数组
	 * @param key 我们需要查找的关键码(值)
	 * @return 返回对应的下标，如果没有-1
	 */
	public static int fibSearch(int[] a, int key) {
		int low = 0;
		int high = a.length - 1;
		int k = 0; //表示斐波那契分割数值的下标
		int mid = 0; //存放mid值
		int f[] = fib(); //获取到斐波那契数列
		//获取到斐波那契分割数值的下标，即新数组的长度
		while(high > f[k] - 1) {
			k++;
		}
		//因为 f[k] 值 可能大于 a 的 长度，因此我们需要使用Arrays类，构造一个新的数组，并指向temp[]
		//不足的部分会使用0填充
		int[] temp = Arrays.copyOf(a, f[k]);
		//实际上需求使用a数组最后的数填充 temp
		//举例:
		//temp = {1,8, 10, 89, 1000, 1234, 0, 0}  => {1,8, 10, 89, 1000, 1234, 1234, 1234,}
		for(int i = high + 1; i < temp.length; i++) {
			temp[i] = a[high];
		}
		
		// 使用while来循环处理，找到我们的数 key
		while (low <= high) { // 只要这个条件满足，就可以找
			mid = low + f[k - 1] - 1;
			if(key < temp[mid]) { //我们应该继续向数组的前面查找(左边)
				high = mid - 1;
				//为甚是 k--
				//说明
				//1. 全部元素 = 前面的元素 + 后边元素
				//2. f[k] = f[k-1] + f[k-2]
				//因为 前面有 f[k-1]个元素,所以可以继续拆分 f[k-1] = f[k-2] + f[k-3]
				//即 在 f[k-1] 的前面继续查找 k--
				//即下次循环 mid = f[k-1-1]-1
				k--;
			} else if ( key > temp[mid]) { // 我们应该继续向数组的后面查找(右边)
				low = mid + 1;
				//为什么是k -=2
				//说明
				//1. 全部元素 = 前面的元素 + 后边元素
				//2. f[k] = f[k-1] + f[k-2]
				//3. 因为后面我们有f[k-2] 所以可以继续拆分 f[k-1] = f[k-3] + f[k-4]
				//4. 即在f[k-2] 的前面进行查找 k -=2
				//5. 即下次循环 mid = f[k - 1 - 2] - 1
				k -= 2;
			} else { //找到
				//需要确定，返回的是哪个下标
				if(mid <= high) {
					return mid;
				} else {
					return high;
				}
			}
		}
		return -1;
	}
}
```

如果还是不明白可以去看看这篇文章https://www.cnblogs.com/sha-Pao-Zi/p/16315064.html，注意这个查找也需要数列是有序的。

## 哈希表

#### 哈希表的基本介绍

散列表（Hash table，也叫哈希表），是根据**关键码值**(Key value)而直接进行访问的数据结构。也就是说，它通过把关键码值映射到表中一个位置来访问记录，以加快查找的速度。这个映射函数叫做
**散列函数**，存放记录的数组叫做**散列表**。

![image-20221023135452464](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vylam9-2.png)

#### 哈希表(散列)-应用实例

有一个公司,当有新的员工来报道时,要求将该员工的信息加入(id,性别,年龄,名字,住址..),当输入该员工的id时,要求查找到该员工的
所有信息.

#### 使用哈希表的具体思路分析

![image-20221023140159836](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vylxz2-2.png)

HashTable管理着每一个EmpLinkedList[]数字中的每一个对象，每一个EmpLinkedList又带有每一个员工的具体信息

#### 完整代码

```java
public class HashTabDemo {


    public static void main(String[] args) {
        HashTab hashTab = new HashTab(7);
        //写一个简单的菜单
        String key = "";
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("add:  添加雇员");
            System.out.println("list: 显示雇员");
            System.out.println("find: 查找雇员");
            System.out.println("del:删除雇员");
            System.out.println("exit: 退出系统");


            key = scanner.next();
            switch (key) {
                case "add":
                    System.out.println("输入id");
                    int id = scanner.nextInt();
                    System.out.println("输入名字");
                    String name = scanner.next();
                    //创建 雇员
                    emp emp = new emp(id, name);
                    hashTab.add(emp);
                    break;
                case "list":
                    hashTab.list();
                    break;
                case "find":
                    System.out.println("输入id");
                    id = scanner.nextInt();
                    hashTab.findByNo(id);
                    break;
                case "del":
                    System.out.println("输入id");
                    id = scanner.nextInt();
                    hashTab.delById(id);
                    break;
                case "exit":
                    scanner.close();
                    System.exit(0);
                default:
                    break;
            }
        }
    }

}


class HashTab {
    private EmpLinkedList[] empLinkedArray;
    private int size;

    public HashTab(int size) {
        this.size = size;
        empLinkedArray = new EmpLinkedList[size];
        //数组里面的对象没有初始化全部都是为null，这个时候就要初始化,添加EmpLinkedList对象
        for (int i = 0; i < size; i++) {
            empLinkedArray[i] = new EmpLinkedList();
        }
    }


    public void add(emp emp) {//添加员工
        empLinkedArray[HsFUN(emp.id)].add(emp);
    }

    public void list() {
        //展示每一个表的员工的信息
        for (int i = 0; i < empLinkedArray.length; i++) {
            empLinkedArray[i].list(i + 1);
        }
    }

    //写一个散列函数来计算每一个员工应该放到hash表中的数组链表的那个位置，这里就用取模法
    public int HsFUN(int no) {
        return no % size;
    }

    public void findByNo(int no) {
        emp emp = empLinkedArray[HsFUN(no)].findEmpById(no);
        if (emp == null) {
            System.out.printf("没有找到员工号为%d的员工", no);
            System.out.println();
        } else {
            System.out.println(emp.name);
        }
    }

    public void delById(int no) {
        empLinkedArray[HsFUN(no)].delById(no);
    }
}


//创建一个员工对象
class emp {
    public int id;
    public String name;
    public emp next;//默认为null

    public emp(int id, String name) {
        this.id = id;
        this.name = name;
    }
}

class EmpLinkedList {
    //头指针这里直接指向第一个员工
    private emp head;

    //添加员工到列表
    public void add(emp emp) {
        if (head == null) {
            head = emp;//如果链表的一个为空的话那么，头节点就是第一个员工
            return;//这里务必要加上return否则的话这个链表的下一个节点就指向了自己在遍历的时候就会不断死循环
        }
        //用一个辅助指针变量
        emp temp = head;
        while (temp.next != null) {
            temp = temp.next;
        }
        temp.next = emp;
    }


    public void list(int no) {
        if (head == null) {
            System.out.println("第" + no + "条链表为空");
            return;
        }
        System.out.print("第" + no + "条链表信息为:");
        emp curEmp = head;
        while (curEmp != null) {
            System.out.printf("===>员工的id%d,姓名%s\t", curEmp.id, curEmp.name);
            curEmp = curEmp.next;//后移
        }
        System.out.println();
    }

    public emp findEmpById(int id) {
        if (head == null) {
            return null;
        }
        //创建一个辅助指针
        emp cur = head;
        while (true) {
            if (cur.id == id) {
                break;
            }
            if (cur.next == null) {
                cur = null;//如果下一个是空就直接结束
                break;
            }
            cur = cur.next;//向后移动,找到了就直接返回
        }
        return cur;

    }

    public void delById(int no) {
        boolean flag = false;
        if (head == null) {
            System.out.println("链表为空");
            return;
        }
        emp temp = head;
        while (true) {
            if (temp.next == null) {
                break;
            } else if (temp.next.id == no) {
                flag = true;
                break;
            }
            temp = temp.next;
        }
        if (!flag) {
            System.out.printf("你要删除的员工号%d不存在", no);
        } else {
            temp.next = temp.next.next;
        }

    }
}
```

## 树

### 树的引入

**数组存储方式的分析**

优点：通过下标方式访问元素，速度快。对于有序数组，还可使用二分查找提高检索速度。

缺点：如果要检索具体某个值，或者插入值(按一定顺序)会整体移动，效率较低

**链式存储方式的分析**

优点：在一定程度上对数组存储方式有优化(比如：插入一个数值节点，只需要将插入节点，链接到链表中即可， 删除效率也很好)。

缺点：在进行检索时，效率仍然较低，比如(检索某个值，需要从头节点开始遍历)）

**树存储方式的分析**

能提高数据存储，读取的效率, 比如利用 二叉排序树(Binary Sort Tree)，既可以保证数据的检索速度，同时也可以保证数据的插入，删除，修改的速度。

### 树的定义

树（tree）是n（n>= 0）个节点的有限集， 当n = 0 时，称为空树。在任意一个非空树中，有如下特点：

1.**有且仅有一个特定的称为根的节点。**

2.**当n > 1时，其余节点可分为m（m>0）个互不相交的有限集，每一个集合本身又，是一个树，并称根的子树。**

### 树的示意图

![image-20221024182910535](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vywy8r-2.png)

**树的常用术语**

1. 节点
2. 根节点
3. 父节点
4. 子节点
5. 叶子节点 (没有子节点的节点)
6. 节点的权(节点值)
7. 路径(从root节点找到该节点的路线)
8. 层
9. 子树
10. 树的高度(最大层数)
11. 森林 :多颗子树构成森林

### 二叉树

#### 二叉树的概念

树有很多种，每个节点最多只能有两个子节点的一种形式称为二叉树。

二叉树的子节点分为左节点和右节点。

![image-20221024183517046](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vyxblt-2.png)

#### 特殊的二叉树

##### 满二叉树

如果该二叉树的所有叶子节点都在最后一层，并且结点总数= 2^n -1 , n 为层数，则我们称为满二叉树。（所有分支节点都有两个孩子）

![image-20221024185755348](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vyxv9e-2.png)

##### 完全二叉树

如果该二叉树的所有叶子节点都在最后一层或者倒数第二层，而且最后一层的叶子节点在左边连续，倒数第二层的叶子节点在右边连续，我们称为完全二叉树。

(前n-1层为满的。

最后一层不满，但是最后一层从左往右是连续的。)

![image-20221024185811617](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vyyeg1-2.png)

#### 二叉树的遍历

对下列二叉树进行遍历

![image-20221024190327600](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vyyxik-2.png)

前序遍历: 先输出父节点，再遍历左子树和右子树
中序遍历: 先遍历左子树，再输出父节点，再遍历右子树
后序遍历: 先遍历左子树，再遍历右子树，最后输出父节点

**小结:** 看输出父节点的顺序，就确定是前序，中序还是后序

##### 完整代码

```java
public class BinaryTreeDemo {
    public static void main(String[] args) {
        BinaryTree binaryTree = new BinaryTree();
        HeroNode root = new HeroNode(1,"宋江");
        HeroNode node1= new HeroNode(2,"吴用");
        HeroNode node2 = new HeroNode(3,"卢俊义");
        HeroNode node3  = new HeroNode(4,"林冲");
        HeroNode node4  = new HeroNode(5,"关胜");
        //手动设置节点
        root.setLeft(node1);
        root.setRight(node2);
        node2.setRight(node3);
        node2.setLeft(node4);


class BinaryTree{
    private HeroNode root;
    

    public void setRoot(HeroNode root) {
        this.root = root;
    }

    public void preOrder(){
        if (this.root !=null){
        this.root.preOrder();
    }else {
            System.out.println("二叉树为空，无法遍历");
        }
    }

    public void infixOrder(){
        if (this.root != null){
            this.root.infixOrder();
        }else {
            System.out.println("二叉树为空，无法遍历");
        }

    }
    public void postOrder(){
        if (this.root != null){
            this.root.postOrder();
        }else {
            System.out.println("二叉树为空，无法遍历");
        }

    }

class HeroNode{
    private int id;
    private String name;
    private HeroNode left;//默认为空
    private HeroNode right;


    public HeroNode(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "HeroNode{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HeroNode getLeft() {
        return left;
    }

    public void setLeft(HeroNode left) {
        this.left = left;
    }

    public HeroNode getRight() {
        return right;
    }

    public void setRight(HeroNode right) {
        this.right = right;
    }

    //前序遍历
    public void preOrder(){
        //先输出根节点
        System.out.println(this);
        //在输出左节点
        if (this.left != null){
            this.left.preOrder();
        }
        if (this.right != null){
            this.right.preOrder();
        }
    }
    //中序遍历
    public void infixOrder(){
        //先输出左节点
        if (this.left != null){
            this.left.infixOrder();
        }
        System.out.println(this);
        if (this.right != null){
            this.right.infixOrder();
        }
    }

    //后续遍历
    public void postOrder(){
        if (this.left != null){
            this.left.postOrder();
        }
        if (this.right != null){
            this.right.postOrder();
        }
        System.out.println(this);
    }

    //前序查找
    public HeroNode preSearch(int no){
        //先判断当前节点
        if (this.id == no){
            return this;
        }
        HeroNode curs = null;
        //如果不相等就向左前序递归查找
        if (this.left != null){
            curs = this.left.preSearch(no);
        }
        if (curs != null){//说明左子树找到了
            return curs;
        }
        if (this.right != null){
           curs  =  this.right.preSearch(no);
        }
        return curs;
    }
```

#### 二叉树查找

![image-20221025175926370](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vyzod7-2.png)

二叉树查找的思路：

**前序查找**：

1. 先判断当前节点是否是要查找的目标。
2. 如果相等就直接返回当前节点
3. 如果不是就判断左节点，如果左节点不为空就按前序递归查找，如果找到就返回
4. 如果为空那么就查询右节点，如果右节点不为空那么，则继续前序递归查找，这个时候不管找没有找到都直接返回。

**中序查找**：

1. 先判断当前节点的左节点是否为空如果不为空就按左递归中序查找。
2. 找到了就直接返回，如果没有找到就和当前节点比较，否者就进行右递归进行中序查找。
3. 如果找到了就直接返回没有找到就直接返回null。

**后序查找**：

1. 先判断当前节点的左节点是否为空，如果不为空就按左递归的后序查找。
2. 如果找到就直接返回如果没有找到，就判断当前右节点是否为空，如果不为空按照右递归的后序查找，找到了就直接返回。
3. 如果都没有找到就比较当前节点。

##### 前中后序查找代码

```java
//前序查找
    public HeroNode preSearch(int no){
        //先判断当前节点
        if (this.id == no){
            return this;
        }
        HeroNode curs = null;
        //如果不相等就向左前序递归查找
        if (this.left != null){
            curs = this.left.preSearch(no);
        }
        if (curs != null){//说明左子树找到了,找到了就直接返回，没有找到就判断右节点是否为空不为空就进行右递归，如果右递归都没有找到就直接返回null
            return curs;
        }
        if (this.right != null){
           curs  =  this.right.preSearch(no);
        }
        return curs;
    }

    //中序查找
    public HeroNode infixSearch(int no){
        //如果左边节点不为空就进行左递归中序查找
        HeroNode cur = null;
        if (this.left != null){
           cur =  this.left.infixSearch(no);
        }
        if (cur !=null){
            return cur;
        }
        if(this.id == no){
            return this;//和当前节点比较，如果相等就直接返回
        }
        if (this.right != null){
            cur = this.right.infixSearch(no);//左节点没有找到进行右递归的中序查找
        }
        return cur;//不管找没有找到都直接返回
    }

    //后序查找
    public HeroNode postSearch(int no){
        HeroNode cur = null;
        if (this.left !=null){
            cur = this.left.postSearch(no);//向左递归的中序查找
        }
        if (cur != null){
            return cur;//如果找到了就直接返回
        }

        if (this.right != null){
            cur = this.right.postSearch(no);//如果左递归没有找到就判断右节点是否为空，如果不为空就进行右递归的后序查找
        }

        if (this.id == no){
            return this;//如果是就返回，没有就直接返回null
        }
        return cur;
    }
```

#### 删除节点

要求：

1. 如果删除的节点是叶子节点，则删除该节点
2. 如果删除的节点是非叶子节点，则删除该子树.
3. 测试，删除掉 5号叶子节点 和 3号子树.

![image-20221026143309904](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vz0gdb-2.png)

思路：

首先我们先判断根节点的左右节点是否为为空，如果是那么就等价将二叉树置空

1. 因为二叉树是单项的，所以我们是判断当前节点的子节点是否是需要删除的节点，而不能去判断当前这个节点是不是需要删除的节点
2. 如果当前节点的左子节点不为空，并且左子节点就是要删除的节点，就将this.left = null 并且返回（结束递归删除）;
3. 如果当前节点的右子节点不为空，并且右子节点就是要删除的节点，就将this.right = null;，并且就返回（结束递归删除）；
4. 2，3都没有删除子节点，那么我们就需要向左子树进行递归删除
5. 如果第四步也没有删除节点，则应当向右子树进行递归删除

##### 完整代码

```java
	public void del(int no){
        if (this.root != null){
            if (this.root.getId() == no){
                this.root = null;
            }else {
                this.root.del(no);
            }
        }else {
            System.out.println("节点为空，无法删除");
        }
    }
/**
     * 1. 因为二叉树是单项的，所以我们是判断当前节点的子节点是否是需要删除的节点，而不能去判断当前这个节点是不是需要删除的节点
     * 2. 如果当前节点的左子节点不为空，并且左子节点就是要删除的节点，就将this.left = null 并且返回（结束递归删除）;
     * 3. 如果当前节点的右子节点不为空，并且右子节点就是要删除的节点，就将this.right = null;，并且就返回（结束递归删除）；
     * 4. 2，3都没有删除子节点，那么我们就需要向左子树进行递归删除
     * 5. 如果第四步也没有删除节点，则应当向右子树进行递归删除
     * @param no 要删除的节点
     */
    public void del(int no){
        //如果当前节点的左子节点不为空，并且左子节点就是要删除的节点，就将this.left = null 并且返回（结束递归删除）;
         if (this.left !=null && this.left.id == no ){
             this.left = null;
             return;
         }
         //如果当前节点的右子节点不为空，并且右子节点就是要删除的节点，就将this.right = null;，并且就返回（结束递归删除）；
         if (this.right != null && this.right.id == no){
             this.left = null;
         }
         //2，3都没有删除子节点，那么我们就需要向左子树进行递归删除
         if (this.left != null ){
             this.left.del(no);
         }

        //如果第四步也没有删除节点，则应当向右子树进行递归删除
        if (this.right != null ){
            this.right.del(no);
        }
    }
//二叉树-删除节点,保留子叶节点
public void del2(int no){
        //如果当前节点的左子节点不为空，并且左子节点就是要删除的节点，就将this.left = null 并且返回（结束递归删除）;
        if (this.left !=null && this.left.id == no ){
                //先判断要删除节点的左节点是否为空，不为空那么删除当前节点后，他的左子节点直接挂上，如果右节点也不为空，那么右子节点就直接挂在，之前左节点上
                HeroNode temp = this.left;
                if (this.left.left != null){
                    this.left = temp.left;
                    if (temp.right != null){
                        this.right.right = temp.right;
                    }
                }else{
                    this.right = temp.right;
                }
            return;
        }
        //如果当前节点的右子节点不为空，并且右子节点就是要删除的节点，就将this.right = null;，并且就返回（结束递归删除）；
        if (this.right != null && this.right.id == no){
            //先判断要删除节点的左节点是否为空，不为空那么删除当前节点后，他的左子节点直接挂上，如果右节点也不为空，那么右子节点就直接挂在，之前左节点上
            HeroNode temp = this.right;
            if (this.right.left != null){
                this.right = temp.left;
                if (temp.right != null){
                    this.right.right = temp.right;
                }
            }else{
                this.right = temp.right;
            }
            return;
        }
        //2，3都没有删除子节点，那么我们就需要向左子树进行递归删除
        if (this.left != null ){
            this.left.del(no);
        }

        //如果第四步也没有删除节点，则应当向右子树进行递归删除
        if (this.right != null ){
            this.right.del(no);
        }
    }
```

#### 顺序存储二叉树

**基本说明**：

从数据存储来看，数组存储方式和树的存储方式可以相互转换，即数组可以转换成树，树也可以转换成数组，看示意图。

![image-20221026223738967](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vz11ic-2.png)

要求:
右图的二叉树的结点，要求以数组的方式来存放 arr : [    ]
要求在遍历数组 arr时，仍然可以以前序遍历，中序遍历和后序遍历的方式完成结点的遍历

**顺序存储二叉树的特点**:

1. 顺序二叉树通常只考虑完全二叉树
2. 第n个元素的左子节点为 2 * n + 1
3. 第n个元素的右子节点为 2 * n + 2
4. 第n个元素的父节点为  (n-1) / 2
5. n : 表示二叉树中的第几个元素(按0开始编号如图所示)

##### 完整代码

```java
public class ArrayBinaryTreeDemo {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7};
        ArrBinaryTree arrBinaryTree = new ArrBinaryTree(arr);
        arrBinaryTree.infixOrder();

    }


}



class ArrBinaryTree {
    private int[] arr;

    public ArrBinaryTree(int[] arr) {
        this.arr = arr;
    }

    public void preOrder() {
        preOrder(0);
    }

    public void infixOrder() {
        infixOrder(0);
    }


    /**
     *
     */

    public void preOrder(int index) {
        //先判断数组是否为空，或者数组是否为0
        if (arr == null || arr.length == 0) {
            System.out.println("数组为空，无法遍历");
        }
        System.out.println(arr[index]);
        //向左遍历
        if ((index * 2) + 1 < arr.length) {
            preOrder((index * 2) + 1);
        }
        //向右遍历
        if ((index * 2) + 2 < arr.length) {
            preOrder((index * 2) + 2);
        }
    }

    public void infixOrder(int index) {
        if (arr == null || arr.length == 0) {
            System.out.println("数组为空，无法遍历");
        }
        if ((index * 2) + 1 < arr.length) {
            infixOrder((index * 2) + 1);
        }
        System.out.println(arr[index]);
        //向右遍历
        if ((index * 2) + 2 < arr.length) {
            infixOrder((index * 2) + 2);
        }
    }

    public void postOrder(int index) {
        if (arr == null || arr.length == 0) {
            System.out.println("数组为空，无法遍历");
        }
        if ((index * 2) + 1 < arr.length) {
            infixOrder((index * 2) + 1);
        }
        //向右遍历
        if ((index * 2) + 2 < arr.length) {
            infixOrder((index * 2) + 2);
        }
        System.out.println(arr[index]);
    }
}
```

#### 线索化二叉树

##### 线索化二叉树引入

将数列 {1, 3, 6, 8, 10, 14 } 构建成一颗二叉树

![image-20221027194119630](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vz269a-2.png)

1. 当我们对上面的二叉树进行中序遍历时，数列为 {8, 3, 10, 1, 6, 14 }
2. 但是 6, 8, 10, 14 这几个节点的 左右指针，并没有完全的利用上.
3. 如果我们希望充分的利用 各个节点的左右指针， 让各个节点可以指向自己的前后节点,怎么办?
4. 解决方案-线索二叉树

##### 线索二叉树介绍

n个结点的二叉链表中含有n+1 【公式 2n-(n-1)=n+1】
个空指针域。利用二叉链表中的空指针域，存放指向该结点在某种遍历次序下的前驱和后继结点的指针（这种附加的指针称为"线索"）

这种加上了线索的二叉链表称为线索链表，相应的二叉树称为线索二叉树(Threaded BinaryTree)
。根据线索性质的不同，线索二叉树可分为前序线索二叉树、中序线索二叉树和后序线索二叉树三种。

一个结点的前一个结点，称为前驱结点
一个结点的后一个结点，称为后继结点（通过观看他们的数组排序对应元素的前后的值就是他们前驱和后驱节点，前驱节点放到左节点，后驱是放到右节点）。

![image-20221027195308753](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vz2tfa-2.png)

总结：

- 若当前节点左指针域非空时，保留原指针不变；若左指针域为空时，添加该节点在相应遍历序列中前驱节点地址。
- 若当前结点右指针域非空时，保留原指针不变；若右指针域为空时，添加该节点在相应遍历序列中后继节点地址。

这里要注意一下，我们怎么判断左/右指针域指向的是原指针还是前驱/后继节点?

![image-20221027200723467](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vz32lf-2.png)

线索化：中序线索化二叉树，就按中序遍历，遍历的过程中在当前节点的左或右空指针域中添加前驱或后继节点。为了保留遍历过程中访问节点的前驱与后继关系，需要设置一个前一节点变量 `pre`
始终指向刚访问过的节点。就是要创建指向当前节点的前驱节点的指针。

在进行递归线索话的时候pre保留前一个节点。

##### 完整代码

```java
public void ThreadedTree(HeroNode2 Node){
        //如果Node 为空就不进行线索化
        if (Node == null){
            return;
        }
        //先线索化左子树
        ThreadedTree(Node.getLeft());


        //{8, 3, 10, 1, 14, 6}
        //如果是按照8来理解的话
        //当前的节点指向8，他的前驱节点就是null也就是pre
        //线索化当前节点
        if (Node.getLeft() == null){
            //让当前的节点的左节点指向前驱节点
            Node.setLeft(pre);
            //修改当前节点的类型指向是指向前驱节点
            Node.setLeftType(1);
        }
        //处理后续节点
        if (pre != null && pre.getRight() == null){
            //让前驱节点的右指针指向当前节点
            pre.setRight(Node);//相当于这里前驱节点pre从null --> 变为了8，这时的Node递归到了3的位置因为8的后驱是3所以这时8的右节点就指向Node(3)
            pre.setRightType(1);
        }
        //这里就要把递归前的pre指向8,如果把这个写在前面就会闭环
        pre = Node;
        //在线索化右子树
        ThreadedTree(Node.getRight());
    }
```

#### 中序遍历线索二叉树

```java
public void ThreadInfixOrder(){
        HeroNode2 heroNode2 = root;
        while (heroNode2 != null){
            //通过循环找到当前LeftType == 1 的节点，8
            //后面随着遍历而变化，因为当leftType == 1 时，说明该节点是按照线索化的
            //处理后序节点
            while (heroNode2.getLeftType() ==0){
                heroNode2 = heroNode2.getLeft();
            }
            System.out.println(heroNode2);
            while (heroNode2.getRightType() == 1){
               heroNode2 =heroNode2.getRight() ;
               System.out.println(heroNode2);
            }
            //这里就要替换成下一个节点
            heroNode2 = heroNode2.getRight();
        }
    }
```

#### 前序线索二叉树

线索化：和中序线索化实现一样(把中序遍历改为先序遍历)。
因为遍历的顺序不一样，所以需要注意的先序和中序有个地方不一样，先序我们要判断是否是左子树/右子树在进行递归，不然会出现死递归。

判断是否是左子树(即不是前驱节点)：先序的遍历顺序是"根左右"，在对"左"节点进行遍历的时候当"左"节点没有左子树时，“左"
节点的前驱节点就设置为"根"节点，此时在遍历”左“节点的"左"节点，就又会遍历"根节点”，就会形成死循环。

##### 完整代码

```java
public void PreThreadedTree(HeroNode2 Node){
        //如果Node 为空就不进行线索化
        if (Node == null){
            return;
        }
        //先线索化当前节点
        if (Node.getLeft() == null){
            Node.setLeft(pre);
            Node.setLeftType(1);
        }

        if (pre != null && pre.getRight() == null){
            pre.setRight(Node);
            pre.setRightType(1);
        }
        pre = Node;
        //线索化左节点,先序我们要判断是否是左子树/右子树在进行递归，不然会出现死递归
        //判断是否是左子树(即不是前驱节点)：先序的遍历顺序是"根左右"，在对"左"节点进行遍历的时候当"左"节点没有左子树时，“左"节点的前驱节点就设置为"根"节点，此时在遍历”左“节点的"左"节点，就又会遍历"根节点”，就会形成死循环。
        //
        if (Node.getLeftType() == 0){
        PreThreadedTree(Node.getLeft());
        }
        //线索化右节点
        if (Node.getRightType() == 0){
        PreThreadedTree(Node.getRight());
    }
    }
//遍历前序线索化二叉树
public void PreThreadedTreeOrder(){
        HeroNode2 heroNode2 = root;
        while (heroNode2 != null) {
            while (heroNode2.getLeftType() == 0){
                System.out.println(heroNode2);
                heroNode2 = heroNode2.getLeft();
            }
            System.out.println(heroNode2);
            while (heroNode2.getRightType() == 1){
                heroNode2 = heroNode2.getRight();
                System.out.println(heroNode2);
            }
            heroNode2 = heroNode2.getRight();
        }
    }
```

### 赫夫曼树（二叉树的一种）

#### 基本介绍

1. 给定n个权值作为n个叶子结点，构造一棵二叉树，若该树的带权路径长度(wpl)
   达到最小，称这样的二叉树为最优二叉树，也称为哈夫曼树(Huffman Tree), 还有的书翻译为霍夫曼树。
2. 赫夫曼树是带权路径长度最短的树，权值较大的结点离根较近。

**赫夫曼树几个重要概念和举例说明**

1. 路径和路径长度：在一棵树中，从一个结点往下可以达到的孩子或孙子结点之间的通路，称为路径。通路中分支的数目称为路径长度。*
   *若规定根结点的层数为1，则从根结点到第L层结点的路径长度为L-1**
2. 结点的权及带权路径长度：若将树中结点赋给一个有着某种含义的数值，则这个数值称为该结点的权。*
   *结点的带权路径长度为：从根结点到该结点之间的路径长度与该结点的权的乘积**
3. **树的带权路径长度：树的带权路径长度规定为所有叶子结点的带权路径长度之和**，记为WPL(weighted path length) ,权值越大的结点
   **离根结点越近的二叉树才是最优二叉树**。
4. **WPL最小的就是赫夫曼树**

![image-20221030142233885](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vz45va-2.png)

#### 构建赫夫曼树的思路图解

给你一个数列 {13, 7, 8, 3, 29, 6, 1}，要求转成一颗赫夫曼树

**构成赫夫曼树的步骤**：

1. 从小到大进行排序，每一个数据都能看作一个节点，每一个节点都能看作一颗最简单的二叉树，并且数据的大小作为他的权值
2. 取出根节点最小的两颗二叉树
3. 组成一颗新的二叉树，该新的二叉树的权值是前面两颗二叉树根节点权值的和
4. 将新的二叉树的权值又与之前的数列进行排序不断重复 1-2-3-4的步骤，直到每一个树都被处理，就能得到一颗赫夫曼树。

图解：

1.先排序，然后找到权值最小的两个二叉树，在将两个根节点的权相加得到一个新的二叉树，再将这个二叉树的权值进行重新排序

![image-20221030144823539](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vzgpg3-2.png)

2.排序好在与6构成一个新的节点，10然后在进行排序。

![image-20221030145329128](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vzho8z-2.png)

重复以上步骤

#### 完整代码

```java
public class huffmanTree {
    public static void main(String[] args) {
        int[] arr = {13, 7, 8, 3, 29, 6, 1};
        Node node  = creatHuffmanTree(arr);
        preOrder(node);
    }

    public static void preOrder(Node root){
        if (root != null){
            root.pre();
        }else {
            System.out.println("树为空，不能遍历");
        }
    }

    public static Node creatHuffmanTree(int[] arr){
        //先遍历arr数组
        //将数组的值构成一个节点
        //将每一个节点放入到ArrayList中进行排序
        //通过Collections来进行排序
        ArrayList<Node> arrayList = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            arrayList.add(new Node(arr[i]));
        }
        while (arrayList.size() > 1) 
            //当列表中只剩一个根节点的时候就说明已经构成了一颗树
            Collections.sort(arrayList);
            //从数组中取出最小的两个数
            //然后组成一个新的节点
            Node LeftNode = arrayList.get(0);
            Node RightNode = arrayList.get(1);
            //两个节点相加组成一个新的节点
            Node parent = new Node(LeftNode.value + RightNode.value);
            parent.left = LeftNode;
            parent.Right = RightNode;
            //删除已经处理过的节点
            arrayList.remove(LeftNode);
            arrayList.remove(RightNode);
            //加入新的节点
            arrayList.add(parent);
        }
        return arrayList.get(0);
    }


}



//创建Node节点类
//实现一个Comparable接口，方便进行大小比较
//同时让Node对象持续排序Collections
class Node implements Comparable<Node>{
    int value;
    Node left;
    Node Right;

    //前序遍历
    public void pre(){
        System.out.println(this);
        if (this.left != null){
            this.left.pre();
        }
        if (this.Right != null){
            this.Right.pre();
        }
    }

    public Node(int value) {
        this.value = value;
    }


    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }

    @Override
    public int compareTo(Node o) {
        return this.value - o.value;
    }
}
```

### 二叉排序树(BST)

需求：

给你一个数列 (7, 3, 10, 12, 5, 1, 9)，要求能够高效的完成对数据的查询和添加。

#### 二叉排序树的介绍

二叉排序树：BST: (Binary Sort(Search) Tree), 对于二叉排序树的任何一个非叶子节点，**要求左子节点的值比当前节点的值小，右子节点的值比当前节点的值大。
**
特别说明：**如果有相同的值，可以将该节点放在左子节点或右子节点**
比如针对前面的数据 (7, 3, 10, 12, 5, 1, 9) ，对应的二叉排序树为：

![image-20221104180946421](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vzinpt-2.png)

#### 二叉排序树创建和遍历

一个数组创建成对应的二叉排序树，并使用中序遍历二叉排序树，比如: 数组为 Array(7, 3, 10, 12, 5, 1, 9) ，
创建成对应的二叉排序树为 :

```java
public class BinarySortTreeDemo {
    public static void main(String[] args) {
        int[] arr = {7, 3, 10, 12, 5, 1, 9};
        BinarySortTree binarySortTree = new BinarySortTree();
        for (int i = 0; i < arr.length; i++) {
            binarySortTree.add(new Node(arr[i]));
        }

        binarySortTree.infixOrder();
    }
}



class BinarySortTree{
    private Node root;

    public void infixOrder(){
        if (root == null){
            System.out.println("二叉树为空不能遍历");
        }else {
            this.root.infixOrder();
        }
    }

    public void add(Node node){
        // 如果是一颗空树，则直接赋给root即可.
        if (root == null) {
            root = node;
        } else {
            root.add(node);
        }
    }

}


//先创建一个二叉树
class Node{
    int value;
    Node leftNode;
    Node rightNode;
    //添加节点
    public void add(Node node){
        //先判断节点是否为空
        if (node == null){
            return;
        }
        if (node.value < this.value){
            //如果大于就就添加到左节点
            if (this.leftNode != null){
                //判断当前左节点是否有子节点如果有就继续向左递归添加
                this.leftNode.add(node);
            }else {
                //没有就直接添加
                this.leftNode = node;
            }
        }else {
            if (this.rightNode != null){
                this.rightNode.add(node);
            }else {
                this.rightNode = node;
            }
        }

    }
    //中序遍历
    public void infixOrder(){
        if (this.leftNode != null){
            this.leftNode.infixOrder();
        }
        System.out.println(this);
        if (this.rightNode !=null){
            this.rightNode.infixOrder();
        }
    }

    public Node(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }
}
```

#### 二叉排序树的删除

二叉排序树的删除情况比较复杂，有下面三种情况需要考虑:

1. 删除叶子节点 (比如：2, 5, 9, 12)
2. 删除只有一颗子树的节点 (比如：1)
3. 删除有两颗子树的节点. (比如：7, 3，10 )

![image-20221104183747970](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vzj8ey-2.png)

**情况1思路：**

1. 先去找到要删除的节点， targetNode
2. 找到targetNode的父节点parent
3. 确定targetNode是parent的左节点还是右节点
4. 根据前面的情况来删除
    1. 左节点：parent.left = null;
    2. 右节点:   parent.right = null;

**情况2思路：**

1. 先去找到要删除的节点， targetNode
2. 找到targetNode的父节点parent
3. 确定targetNode 的子结点是左子结点还是右子结点
4. targetNode 是 parent的右节点还是左节点
    1. 如果targetNode有左节点并且targetNode是parent的左节点：parent.left = targetNode.left;
    2. 如果 targetNode 是 parent 的右子节点：parent.right = targetNode.left;
    3. 如果targetNode 有右子结点并且 targetNode 是 parent 的左子结点：parent.left = targetNode.right;
    4. 如果 targetNode 是 parent 的右子节点：parent.right = targetNode.right

**情况3思路:**

1. 需求先去找到要删除的结点 targetNode
2. 找到targetNode 的 父结点 parent
3. 从targetNode 的右子树找到最小的结点
4. 用一个临时变量，将 最小结点的值保存 temp = 11
5. 删除该最小结点
6. targetNode.value = temp

#### 完整代码

```java
public class BinarySortTreeDemo {
    public static void main(String[] args) {
        int[] arr = {7, 3, 10, 12, 5,1,9,2};
        BinarySortTree binarySortTree = new BinarySortTree();
        for (int i = 0; i < arr.length; i++) {
            binarySortTree.add(new Node(arr[i]));
        }


        binarySortTree.del(10);
        binarySortTree.del(1);
        binarySortTree.del(2);
        binarySortTree.del(3);
        binarySortTree.del(5);
        binarySortTree.del(6);
        binarySortTree.del(7);
        binarySortTree.del(12);
//        binarySortTree.del(9);
        binarySortTree.infixOrder();
    }
}


class BinarySortTree {
    private Node root;

    //查找要删除的节点
    public Node Search(int value) {
        if (root == null) {
            return null;
        } else {
            return root.searchTargetNode(value);
        }
    }

    //查找要删除节点的父节点
    public Node SearchParent(int value) {
        if (root == null) {
            return null;
        } else {
            return root.searchParent(value);
        }

    }

    //删除节点
    public void del(int value) {
        if (root == null) {
            return;
        } else {
            //找到要删除的节点
            Node target = Search(value);
            //判断叶子节点是否为空
            if (target == null) {
                return;
            }
            //如果找到的节点没有父节点就表明是根节点，就直接置空当前节点
            if (root.leftNode == null && root.rightNode == null) {
                root = null;
                return;
            }
            //如果不为空就找到他的父节点
            Node parent = SearchParent(value);
            //判断删除的节点是否为叶子节点
            if (target.leftNode == null && target.rightNode == null) {
                //在判断要删除的节点是父节点的左节点，还是右节点
                if (parent.leftNode != null && parent.leftNode.value == target.value) {
                    parent.leftNode = null;
                } else if (parent.rightNode != null && parent.rightNode.value == target.value) {
                    parent.rightNode = null;
                }
            }else if (target.leftNode != null && target.rightNode != null){
                //表明左右都有节点，为第三种情况
                target.value  = delGetMinValue(target.rightNode);
            }else {
                //表示只有一个左节点或者是右节点
                if (target.leftNode !=null){ //表明要删除的节点有左节点
                    if (parent !=null){//这里还要判断一下parent是否为空，否则就会报空指针异常
                   //在判断是删除的节点是父节点的左边还是右边
                    if (parent.leftNode.value == target.value){
                        parent.leftNode = target.leftNode;
                    }else {
                        parent.rightNode = target.leftNode;
                    }}else {
                        root = target.leftNode;
                    }
                }else {
                    //这里就表明只有右节点
                    if (parent != null){
                    if (parent.leftNode.value == target.value){
                        parent.leftNode = target.rightNode;
                    }else {
                        parent.rightNode = target.rightNode;
                    }
                }else {
                        root = target.rightNode;
                    }
                }
            }
        }
    }

    //中序遍历
    public void infixOrder() {
        if (root == null) {
            System.out.println("二叉树为空不能遍历");
        } else {
            this.root.infixOrder();
        }
    }

    public void add(Node node) {
        // 如果是一颗空树，则直接赋给root即可.
        if (root == null) {
            root = node;
        } else {
            root.add(node);
        }
    }
    //向要删除的节点的右子节点找到最小的子节点，并且删除最小的节点,并且返回最小节点的值
    public int delGetMinValue(Node node){
        //用一个临时变量来接受找的那个节点
        Node tem = node;//应为后面要传入一个目标节点的右节点所以这里向左查找
        while (tem.leftNode !=null){
            tem = tem.leftNode;
        }
       del(tem.value);
        return tem.value;
    }

}


//先创建一个二叉树
class Node {
    int value;
    Node leftNode;
    Node rightNode;

    //查找要删除结点的父结点

    /**
     * @param value 要找到的结点的值
     * @return 返回的是要删除的结点的父结点，如果没有就返回null
     */
    public Node searchParent(int value) {
        if ((this.leftNode != null && this.leftNode.value == value) || (this.rightNode != null && this.rightNode.value == value)) {
            return this;
        } else {
            if (this.leftNode != null && this.value > value) {
                return this.leftNode.searchParent(value);//向左子树递归查找
            } else if (this.rightNode != null && this.value < value) {
                return this.rightNode.searchParent(value);//向右子树递归查找
            } else {
                return null;// 没有找到父结点
            }
        }
    }




    /**
     * @param value 希望删除的结点的值
     * @return 如果找到返回该结点，否则返回null
     */
    public Node searchTargetNode(int value) {
        if (this.value == value) {
            return this;
        }
        if (this.value > value) {
            //如果左节点为空就直接返回null
            if (this.leftNode == null) {
                return null;
            }
            //如果当前值小于根节点，就向左递归查找
            return this.leftNode.searchTargetNode(value);
        } else {
            if (this.rightNode == null) {
                return null;
            }
            //如果当前值大于根节点，就向右递归查找
            return this.rightNode.searchTargetNode(value);
        }
    }


    //添加节点
    public void add(Node node) {
        //先判断节点是否为空
        if (node == null) {
            return;
        }
        if (node.value < this.value) {
            //如果大于就就添加到左节点
            if (this.leftNode != null) {
                //判断当前左节点是否有子节点如果有就继续向左递归添加
                this.leftNode.add(node);
            } else {
                //没有就直接添加
                this.leftNode = node;
            }
        } else {
            if (this.rightNode != null) {
                this.rightNode.add(node);
            } else {
                this.rightNode = node;
            }
        }
    }

    //中序遍历
    public void infixOrder() {
        if (this.leftNode != null) {
            this.leftNode.infixOrder();
        }
        System.out.println(this);
        if (this.rightNode != null) {
            this.rightNode.infixOrder();
        }
    }




    public Node(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }
}
```

### 平衡二叉树AVL

给你一个数列{1,2,3,4,5,6}，要求创建一颗二叉排序树(BST), 并分析问题所在.

![image-20221105200644350](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vzkh9p-2.png)

**左边BST 存在的问题分析:**

左子树全部为空，从形式上看，更像一个单链表.
插入速度没有影响
查询速度明显降低(因为需要依次比较), 不能发挥BST 的优势，因为每次还需要比较左子树，其查询速度比 单链表还慢
解决方案-平衡二叉树(AVL)

**平衡二叉树的介绍**：

1. 平衡二叉树也叫平衡二叉搜索树（Self-balancing binary search tree）又被称为AVL树， 可以保证查询效率较高。
2. 具有以下特点：它是一 棵空树或它的左右两个子树的高度差的**绝对值不超过1**，并且左右两个子树都是一棵平衡二叉树。平衡二叉树的常用实现方法有
   **红黑树、AVL、替罪羊树、Treap、伸展树**等。

![image-20221105201300033](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vzlgz3-2.png)

**怎么处理-进行左旋转**：

1. 创建一个新的节点，新的节点的值为旧树的根节点的值

2. 把新节点的左子树设置为旧树的左子树

   newNode.left = left;

3. 把新节点的右子树设置为旧树的右子树的左子树

   newNode.right = right.left;

4. 把旧树的根节点的值设置为旧树的右子树的值

   value = right.value;

5. 把当前节点（指的是当前的根节点）的右子树设置为旧树的右子树的右子树

   right = right.right

6. 当前节点的左子树设置为新节点

   left = newNode;

   ![image-20221105202625344](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vzlq9k-2.png)

在旋转之前我们还要求出树的高，左子树的高和右子树的高度差，如果差值的绝对值大于1那么就要进行左旋转

#### 求高度代码

```java
//遍历左子树的高度
    public int leftHeight(){
        if (this.leftNode == null){
            return 0;
        }
        return this.leftNode.height();
    }
//遍历右子树的高度
    public int rightHeight(){
        if (this.rightNode == null){
            return 0;
        }
        return this.rightNode.height();
    }
    //以当前根节点为起始点的树的高度
    public int height(){
        return Math.max(this.leftNode == null ? 0: this.leftNode.height(),this.rightNode == null ? 0: this.rightNode.height())+1;
    }
```

#### 左旋转代码

```java
public void leftRotate(){
        //1. 创建一个新的节点，新的节点的值为旧树的根节点的值
        Node1 node1 = new Node1(this.value);
        //2. 把新节点的左子树设置为旧树的左子树
        node1.leftNode = this.leftNode;
        //把新节点的右子树设置为旧树的右子树的左子树
        node1.rightNode = this.rightNode.leftNode;
        //把旧树的根节点的值设置为旧树的右子树的值
        this.value = this.rightNode.value;
        //把当前节点（指的是当前的根节点）的右子树设置为旧树的右子树的右子树
        this.rightNode = this.rightNode.rightNode;
        //当前节点的左子树设置为新节点
        this.leftNode = node1;
    }
```

![image-20221106145013574](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vznd6b-2.png)

#### 右旋转

当左子树的高度大于右子树的高度的时候，这个时候就要进行右旋转

1. 创建一个新的节点，新的节点的值为旧树的根节点的值

2. 把新节点的右子树设置为旧树的右子树

   newNode.right = right;

3. 把新节点的右子树设置为旧树的左子树的右子树

   newNode.left = left.right;

4. 把旧树的根节点的值设置为旧树的左子树的值

   value = left.value;

5. 把当前节点（指的是当前的根节点）的左子树设置为旧树的左子树的左子树

   left = left.left

6. 当前节点的右子树设置为新节点

   right = newNode;

![image-20221106145031674](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vznray-2.png)

#### 右旋转代码

```java
public void rightRotate(){
        //1. 创建一个新的节点，新的节点的值为旧树的根节点的值
        Node1 node1 = new Node1(this.value);
        //2. 把新节点的右子树设置为旧树的左子树
        node1.rightNode = this.rightNode;
        //把新节点的左子树设置为旧树的左子树的右子树
        node1.leftNode = this.leftNode.rightNode;
        //把旧树的根节点的值设置为旧树的左子树的值
        this.value = this.leftNode.value;
        //把当前节点（指的是当前的根节点）的左子树设置为旧树的左子树的左子树
        this.leftNode = this.leftNode.leftNode;
        //当前节点的右子树设置为新节点
        this.rightNode = node1;

    }
```

前面的两个数列，进行单旋转(即一次旋转)就可以将非平衡二叉树转成平衡二叉树,但是在某些情况下，单旋转不能完成平衡二叉树的转换。比如数列
int[] arr = { 10, 11, 7, 6, 8, 9 }; 运行原来的代码可以看到，并没有转成 AVL树.

![image-20221106151914536](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vzo4vy-2.png)

问题分析出来:  在满足右旋转条件时，要判断
(1)如果 根节点的 左子树的 右子树高度 大于左子树的左子树时：
(2)就是 对 当前根节点的左子树，先进行 左旋转，
(3)然后， 在对当前根节点进行右旋转即可
否则，直接对当前节点（根节点）进行右旋转.即可.

![image-20221106152236781](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vzon98-2.png)

#### 双旋转判断代码

```java
        //当添加完后就继续判断是否右子树高度是否大于左子树
        if (this.rightHeight() - this.leftHeight() > 1){
            //就进行左旋转
            //判断如果当前条件满足左旋转，在判断当前节点的右子树的左子树高度是否大于右子树的右子树，如果大于就先对当前节点的左子树进行右旋转
            if (this.rightNode!= null &&this.rightNode.leftHeight() > this.rightNode.rightHeight()){
                this.rightNode.rightRotate();
                leftRotate();
            }else {
                leftRotate();
            }
            return;
        }
        //右旋转
        if (this.leftHeight() - this.rightHeight() > 1){
            //判断如果当前条件满足左旋转，在判断当前节点的左子树的右子树高度是否大于左子树的左子树，如果大于先对当前节点的右子树进行左旋转
            if (this.leftNode!= null&&this.leftNode.leftHeight() < this.leftNode.rightHeight()){
                this.leftNode.leftRotate();
                rightRotate();
            }else {
                rightHeight();
            }
        }
    }
```

### 多路查找树

#### 二叉树的问题分析

![image-20221107143435102](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vzpbc6-2.png)

二叉树需要加载到内存的，如果二叉树的节点少，没有什么问题，但是如果二叉树的节点很多(比如1亿)， 就存在如下问题:

- 问题1：在构建二叉树时，需要多次进行i/o操作(海量数据存在数据库或文件中)，节点海量，构建二叉树时，速度有影响
- 问题2：节点海量，也会造成二叉树的高度很大，会降低操作速度.

#### 多叉树

在二叉树中，每个节点有数据项，最多有两个子节点。如果允许每个节点可以有更多的数据项和更多的子节点，就是多叉树（multiway tree）
2-3树，2-3-4树就是多叉树，多叉树通过重新组织节点，减少树的高度，能对二叉树进行优化。

![image-20221107143615891](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vzpohh-2.png)

##### 2-3树应用案例

将数列{16, 24, 12, 32, 14, 26, 34, 10, 8, 28, 38, 20} 构建成2-3树，并保证数据插入的大小顺序。(演示一下构建2-3树的过程.)

**插入规则**:

1. 2-3树的所有叶子节点都在同一层.(只要是B树都满足这个条件)
2. 有两个子节点的节点叫二节点，二节点要么没有子节点，要么有两个子节点.
3. 有三个子节点的节点叫三节点，三节点要么没有子节点，要么有三个子节点
4. 当按照规则插入一个数到某个节点时，不能满足上面三个要求，就需要拆，先向上拆，如果上层满，则拆本层，拆后仍然需要满足上面3个条件。
5. 对于三节点的子树的值大小仍然遵守(BST 二叉排序树)的规则

**{16, 24, 12, 32, 14, 26, 34, 10, 8, 28, 38, 20}**

![image-20221107144448902](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vzqa8s-2.png)

![image-20221107143936883](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vzreo3-2.png)

![image-20221107143954281](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/vzrzuc-2.png)

除了23树，还有234树等，概念和23树类似，也是一种B树。 如图:

![image-20221107144645962](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/w02zl8-2.png)

##### B树的介绍

B-tree树即B树，B即Balanced，平衡的意思。有人把B-tree翻译成B-树，容易让人产生误解。会以为B-树是一种树，而B树又是另一种树。实际上，B-tree就是指的B树。

![image-20221107144737179](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/w03l3t-2.png)

**B树的说明:**

1. B树的阶：节点的最多子节点个数。比如2-3树的阶是3，2-3-4树的阶是4
2. B-树的搜索，从根结点开始，对结点内的关键字（有序）序列进行二分查找，如果命中则结束，否则进入查询关键字所属范围的儿子
3. 结点；重复，直到所对应的儿子指针为空，或已经是叶子结点
4. 关键字集合分布在整颗树中, 即叶子节点和非叶子节点都存放数据.
   搜索有可能在非叶子结点结束。
5. 其搜索性能等价于在关键字全集内做一次二分查找

##### B+树的介绍

![image-20221107144929324](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/w043na-2.png)

**B+树的说明:**

1. B+树的搜索与B树也基本相同，区别是B+树只有达到叶子结点才命中（B树可以在非叶子结点命中），其性能也等价于在关键字全集做一次二分查找
2. 所有关键字都出现在叶子结点的链表中（即数据只能在叶子节点【也叫稠密索引】），且链表中的关键字(数据)恰好是有序的。
   不可能在非叶子结点命中
3. 非叶子结点相当于是叶子结点的索引（稀疏索引），叶子结点相当于是存储（关键字）数据的数据层
   更适合文件索引系统
4. B树和B+树各有自己的应用场景，不能说B+树完全比B树好，反之亦然.

**B*树的介绍**
B*树是B+树的变体，在B+树的非根和非叶子结点再增加指向兄弟的指针。

![image-20221107145051150](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/w04mfa-2.png)

**B树的说明:**
B树定义了**非叶子结点关键字个数至少为(2/3)M**（ 阶数表示了一个结点**最多** 有多少个孩子结点，一般用字母 M
表示阶数。），即块的最低使用率为2/3，而B+树的块的最低使用率为B+树的1/2。
从第1个特点我们可以看出，B*树分配新结点的概率比B+树要低，空间使用率更高。

## 赫夫曼编码

### 基本介绍

赫夫曼编码也翻译为 哈夫曼编码(Huffman Coding)，又称霍夫曼编码，是一种编码方式, 属于一种程序算法
赫夫曼编码是赫哈夫曼树在电讯通信中的经典的应用之一。

赫夫曼编码广泛地用于数据文件压缩。其压缩率通常在20%～90%之间
赫夫曼码是可变字长编码(VLC)的一种。Huffman于1952年提出一种编码方法，称之为最佳编码

### 编码发展史

**通信领域中信息的处理方式1-定长编码**

i like like like java do you like a java // 共40个字符(包括空格)  
105 32 108 105 107 101 32 108 105 107 101 32 108 105 107 101 32 106 97 118 97 32 100 111 32 121 111 117 32 108 105 107
101 32 97 32 106 97 118 97 //对应Ascii码

01101001 00100000 01101100 01101001 01101011 01100101 00100000 01101100 01101001 01101011 01100101 00100000 01101100
01101001 01101011 01100101 00100000 01101010 01100001 01110110 01100001 00100000 01100100 01101111 00100000 01111001
01101111 01110101 00100000 01101100 01101001 01101011 01100101 00100000 01100001 00100000 01101010 01100001 01110110
01100001 //对应的二进制
按照二进制来传递信息，总的长度是 359   (包括空格)
在线转码 工具 ：http://www.esjson.com/unicodeEncode.html

**通信领域中信息的处理方式2-变长编码**

i like like like java do you like a java // 共40个字符(包括空格)

d:1 y:1 u:1 j:2 v:2 o:2 l:4 k:4 e:4 i:5 a:5   :9 // 各个字符对应的个数
0= , 1=a, 10=i, 11=e, 100=k, 101=l, 110=o, 111=v, 1000=j, 1001=u, 1010=y, 1011=d 说明：按照各个字符出现的次数进行编码，原则是出现次数越多的，则编码越小，比如
空格出现了9 次， 编码为0 ,其它依次类推.

按照上面给各个字符规定的编码，则我们在传输  "i like like like java do you like a java" 数据时，编码就是 10010110100...

比如这里的**i**对应的编码为**10**可是**l**对应的编码是**101**这里**i的编码就是l的前缀**如果解码的时候就会产生歧义

字符的编码都不能是其他字符编码的前缀，符合此要求的编码叫做前缀编码， 即不能匹配到重复的编码

**通信领域中信息的处理方式3-赫夫曼编码**

i like like like java do you like a java // 共40个字符(包括空格)

d:1 y:1 u:1 j:2 v:2 o:2 l:4 k:4 e:4 i:5 a:5   :9 // 各个字符对应的个数
按照上面字符出现的次数构建一颗赫夫曼树, 次数作为权值.

### 赫夫曼编码图解

传输的 字符串

1) i like like like java do you like a java

2) d:1 y:1 u:1 j:2 v:2 o:2 l:4 k:4 e:4 i:5 a:5   :9 // 各个字符对应的个数

3) 按照上面字符出现的次数构建一颗赫夫曼树, 次数作为权值

步骤：
构成赫夫曼树的步骤：

1) 从小到大进行排序, 将每一个数据，每个数据都是一个节点 ， 每个节点可以看成是一颗最简单的二叉树
2) 取出根节点权值最小的两颗二叉树
3) 组成一颗新的二叉树, 该新的二叉树的根节点的权值是前面两颗二叉树根节点权值的和
4) 再将这颗新的二叉树，以根节点的权值大小 再次排序， 不断重复 1-2-3-4 的步骤，直到数列中，所有的数据都被处理，就得到一颗赫夫曼树

![image-20221031165439570](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/w05ddh-2.png)

4) 根据赫夫曼树，给各个字符,规定编码 (前缀编码)， 向左的路径为0 向右的路径为1 ， 编码如下:
   o: 1000 u: 10010 d: 100110 y: 100111 i: 101
   a : 110 k: 1110 e: 1111 j: 0000 v: 0001
   l: 001          : 01

5) 按照上面的赫夫曼编码，我们的"i like like like java do you like a java"   字符串对应的编码为 (
   注意这里我们使用的无损压缩)
   1010100110111101111010011011110111101001101111011110100001100001110011001111000011001111000100100100110111101111011100100001100001110
   通过赫夫曼编码处理 长度为 133

长度为 ： 133

说明：

原来长度是 359 , 压缩了  (359-133) / 359 = 62.9%
此编码满足前缀编码, 即字符的编码都不能是其他字符编码的前缀。不会造成匹配的多义性
赫夫曼编码是无损处理方案

**注意, 这个赫夫曼树根据排序方法不同，也可能不太一样，这样对应的赫夫曼编码也不完全一样，但是wpl 是一样的，都是最小的, 比如:
如果我们让每次生成的新的二叉树总是排在权值相同的二叉树的最后一个，则生成的二叉树为:**

![image-20221031174932405](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/w06160-2.png)

因为有可能生成的权值可能是一样的所以就导致了一样的权值位置摆放的不同导致了生成的赫夫曼树的不同，但是他们的wpl是一样的。

### 前置代码

```java
public class HuffmanCode {
    public static void main(String[] args) {
        String s = "i like like like java do you like a java";
        byte[] codeBytes = s.getBytes();

        creatHuffmanTree(getNodes(codeBytes)).preOrder();
    }


    //构建赫夫曼树
    public static Node creatHuffmanTree(List<Node> nodes) {
        while (nodes.size() > 1){
        //先对nodes进行排序,使用collections工具类
        Collections.sort(nodes);
        //拿到前两个权值最小的节点
        Node leftNode = nodes.get(0);
        Node rightNode = nodes.get(1);
        //通过两个节点构成一个新的节点，注意注意只有最开始的节点有data，其他新构建的节点没有data
        Node parentNode = new Node(null, leftNode.weight + rightNode.weight);
        //清除前面两个节点
        nodes.remove(leftNode);
        nodes.remove(rightNode);
        //设置新节点的左右节点
        parentNode.leftNode = leftNode;
        parentNode.rightNode = rightNode;
        //添加新的节点
        nodes.add(parentNode);
        }
        return nodes.get(0);

    }

    //创建一个Arraylist,来存放每一个节点的数据
    public static List<Node> getNodes(byte[] bytes) {
        //创建一个Map来记录每一个字符出现的数量
        Map<Byte, Integer> hashMap = new HashMap<>();
        List<Node> nodes = new ArrayList<>();
        //记录每一个字符对应的byte所出现的次数
        for (byte aByte : bytes) {
            hashMap.put(aByte, hashMap.getOrDefault(aByte, 0) + 1);
        }
        for (Map.Entry<Byte, Integer> byteIntegerEntry : hashMap.entrySet()) {
            nodes.add(new Node(byteIntegerEntry.getKey(), byteIntegerEntry.getValue()));
        }
        return nodes;

    }
}


//重新创建一个Node
class Node implements Comparable<Node> {
    Byte data;
    int weight;
    Node leftNode;
    Node rightNode;
    //写一个前序遍历
    public void preOrder(){
        System.out.println(this);
        if (this.leftNode != null){
            this.leftNode.preOrder();
        }
        if (this.rightNode != null){
            this.rightNode.preOrder();
        }
    }


    public Node(Byte data, int weight) {
        this.data = data;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                ", weight=" + weight +
                '}';
    }

    @Override
    public int compareTo(Node o) {
        return this.weight - o.weight;
    }
}
```

### 数据压缩最终代码

```java
public class HuffmanCode {
    public static void main(String[] args) {
        String s = "i like like like java do you like a java";
        System.out.println(Arrays.toString(zip(s)));

    }

    public static byte[] zip(String s){
        //将这些封装成方法
        byte[] codeBytes = s.getBytes();
        Node node = creatHuffmanTree(getNodes(codeBytes));
        Map<Byte, String> hash = getCode(node);
        return zip(codeBytes,hash);
    }
    //通过hashHuffman来生成编码字符串,返回一个字节数组
    /**
     * @param contentsBytes 需要一个之前的传进来的原始字符串的bytes数组
     * @param hashHuffman   遍历之前的对应字符存入的编码
     * @return 返回一个压缩之后的Bytes数组
     */
    public static byte[] zip(byte[] contentsBytes, Map<Byte, String> hashHuffman) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte contentsByte : contentsBytes) {
            stringBuilder.append(hashHuffman.get(contentsByte));
        }
        //拿到字符编码后，压缩为byte数组，返回一个byte数组
        //这里我们每八位作为一个字符
        int len = 0;
        if (stringBuilder.length() % 8 == 0){
            len = stringBuilder.length()/8;
        }else {
            len = stringBuilder.length()/8 + 1;
        }
        ////创建 存储压缩后的 byte数组
        byte[] bytes = new byte[len];
        int index = 0;
        //这里后移八位
        for (int i = 0; i < stringBuilder.length() ; i+=8) {
            //当后移八位的时候，如果长度不够继续就要进行一个判断，这样就能防止越界。
            String strByte;
            if (i+8 > stringBuilder.length()){
                strByte = stringBuilder.substring(i);
            }else {
                strByte = stringBuilder.substring(i,i+8);
            }
            bytes[index++] = (byte) Integer.parseInt(strByte,2);
        }
        return bytes;

    }


    //构建赫夫曼树
    public static Node creatHuffmanTree(List<Node> nodes) {
        while (nodes.size() > 1) {
            //先对nodes进行排序,使用collections工具类
            Collections.sort(nodes);
            //拿到前两个权值最小的节点
            Node leftNode = nodes.get(0);
            Node rightNode = nodes.get(1);
            //通过两个节点构成一个新的节点，注意注意只有最开始的节点有data，其他新构建的节点没有data
            Node parentNode = new Node(null, leftNode.weight + rightNode.weight);
            //清除前面两个节点
            nodes.remove(leftNode);
            nodes.remove(rightNode);
            //设置新节点的左右节点
            parentNode.leftNode = leftNode;
            parentNode.rightNode = rightNode;
            //添加新的节点
            nodes.add(parentNode);
        }
        return nodes.get(0);

    }


    //创建一个Arraylist,来存放每一个节点的数据
    public static List<Node> getNodes(byte[] bytes) {
        //创建一个Map来记录每一个字符出现的数量
        Map<Byte, Integer> hashMap = new HashMap<>();
        List<Node> nodes = new ArrayList<>();
        //记录每一个字符对应的byte所出现的次数
        for (byte aByte : bytes) {
            hashMap.put(aByte, hashMap.getOrDefault(aByte, 0) + 1);
        }
        for (Map.Entry<Byte, Integer> byteIntegerEntry : hashMap.entrySet()) {
            nodes.add(new Node(byteIntegerEntry.getKey(), byteIntegerEntry.getValue()));
        }
        return nodes;
    }


    //生成一组赫夫曼编码
    //1. 将赫夫曼编码表存放在 Map<Byte,String> 形式
    //   生成的赫夫曼编码表{32=01, 97=100, 100=11000, 117=11001, 101=1110, 118=11011, 105=101, 121=11010, 106=0010, 107=1111, 108=000, 111=0011}
    static Map<Byte, String> hashHuffman = new HashMap<>();
    static StringBuilder stringBuilder = new StringBuilder();

    //为了调用方便我们写一个方法的重载
    public static Map<Byte, String> getCode(Node node) {
        if (node == null) {
            return null;
        } else {
            //如果不为空那么就处理左子树
            getCode(node.leftNode, "0", stringBuilder);
            //处理右子树
            getCode(node.rightNode, "1", stringBuilder);
        }
        return hashHuffman;
    }

    /**
     * 功能：将传入的node结点的所有叶子结点的赫夫曼编码得到，并放入到huffmanCodes集合
     *
     * @param node          传入结点
     * @param code          路径： 左子结点是 0, 右子结点 1
     * @param stringBuilder 用于拼接路径
     */
    public static void getCode(Node node, String code, StringBuilder stringBuilder) {
        StringBuilder stringBuilder1 = new StringBuilder(stringBuilder);
        stringBuilder1.append(code);
        if (node.data == null) {
            //先向左递归找
            getCode(node.leftNode, "0", stringBuilder1);
            //然后在右递归
            getCode(node.rightNode, "1", stringBuilder1);
        } else {
            //说明是一个子叶节点，就直接将对应的编码放入到节点当中
            hashHuffman.put(node.data, stringBuilder1.toString());
        }
    }

}


//重新创建一个Node
class Node implements Comparable<Node> {
    Byte data;
    int weight;
    Node leftNode;
    Node rightNode;

    //写一个前序遍历
    public void preOrder() {
        System.out.println(this);
        if (this.leftNode != null) {
            this.leftNode.preOrder();
        }
        if (this.rightNode != null) {
            this.rightNode.preOrder();
        }
    }


    public Node(Byte data, int weight) {
        this.data = data;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                ", weight=" + weight +
                '}';
    }

    @Override
    public int compareTo(Node o) {
        return this.weight - o.weight;
    }
}
```

### 数据压缩解压终极版（老师修复版）

```java
public class HuffmanCode {
    static int zeroCount= 0;
    public static void main(String[] args) {
        String s = "你好";
        byte[] zip = zip(s);
        System.out.println(new String (decode(hashHuffman, zip)));
    }

    public static byte[] zip(String s){
        //将这些封装成方法
        byte[] codeBytes = s.getBytes();
        Node node = creatHuffmanTree(getNodes(codeBytes));
        Map<Byte, String> hash = getCode(node);
        return zip(codeBytes,hash);
    }
    //通过hashHuffman来生成编码字符串,返回一个字节数组
    /**
     * @param contentsBytes 需要一个之前的传进来的原始字符串的bytes数组
     * @param hashHuffman   遍历之前的对应字符存入的编码
     * @return 返回一个压缩之后的Bytes数组
     */
    public static byte[] zip(byte[] contentsBytes, Map<Byte, String> hashHuffman) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte contentsByte : contentsBytes) {
            stringBuilder.append(hashHuffman.get(contentsByte));
        }
        System.out.println(stringBuilder);
        //拿到字符编码后，压缩为byte数组，返回一个byte数组
        //这里我们每八位作为一个字符
        int len = 0;
        if (stringBuilder.length() % 8 == 0){
            len = stringBuilder.length()/8;
        }else {
            len = stringBuilder.length()/8 + 1;
        }
        ////创建 存储压缩后的 byte数组
        byte[] bytes = new byte[len];
        int index = 0;
        //这里后移八位

        for (int i = 0; i < stringBuilder.length() ; i+=8) {
            //当后移八位的时候，如果长度不够继续就要进行一个判断，这样就能防止越界。
            String strByte;
            if (i+8 > stringBuilder.length()){
                strByte = stringBuilder.substring(i);
                for (int j = 0; j < strByte.length(); j++) {
                    if (strByte.length() == 1){
                        break;
                    }
                    if (strByte.charAt(j) == '0'){
                        zeroCount++;//记录当不够八位的时候是否要补零，补多少个零
                    }else {
                        break;
                    }
                }
            }else {
                strByte = stringBuilder.substring(i,i+8);
            }
            bytes[index++] = (byte) Integer.parseInt(strByte,2);
        }
        return bytes;

    }


    //构建赫夫曼树
    public static Node creatHuffmanTree(List<Node> nodes) {
        while (nodes.size() > 1) {
            //先对nodes进行排序,使用collections工具类
            Collections.sort(nodes);
            //拿到前两个权值最小的节点
            Node leftNode = nodes.get(0);
            Node rightNode = nodes.get(1);
            //通过两个节点构成一个新的节点，注意注意只有最开始的节点有data，其他新构建的节点没有data
            Node parentNode = new Node(null, leftNode.weight + rightNode.weight);
            //清除前面两个节点
            nodes.remove(leftNode);
            nodes.remove(rightNode);
            //设置新节点的左右节点
            parentNode.leftNode = leftNode;
            parentNode.rightNode = rightNode;
            //添加新的节点
            nodes.add(parentNode);
        }
        return nodes.get(0);

    }


    //创建一个Arraylist,来存放每一个节点的数据
    public static List<Node> getNodes(byte[] bytes) {
        //创建一个Map来记录每一个字符出现的数量
        Map<Byte, Integer> hashMap = new HashMap<>();
        List<Node> nodes = new ArrayList<>();
        //记录每一个字符对应的byte所出现的次数
        for (byte aByte : bytes) {
            hashMap.put(aByte, hashMap.getOrDefault(aByte, 0) + 1);
        }
        for (Map.Entry<Byte, Integer> byteIntegerEntry : hashMap.entrySet()) {
            nodes.add(new Node(byteIntegerEntry.getKey(), byteIntegerEntry.getValue()));
        }
        return nodes;
    }


    //生成一组赫夫曼编码
    //1. 将赫夫曼编码表存放在 Map<Byte,String> 形式
    //   生成的赫夫曼编码表{32=01, 97=100, 100=11000, 117=11001, 101=1110, 118=11011, 105=101, 121=11010, 106=0010, 107=1111, 108=000, 111=0011}
    static Map<Byte, String> hashHuffman = new HashMap<>();
    static StringBuilder stringBuilder = new StringBuilder();

    //为了调用方便我们写一个方法的重载
    public static Map<Byte, String> getCode(Node node) {
        if (node == null) {
            return null;
        } else {
            //如果不为空那么就处理左子树
            getCode(node.leftNode, "0", stringBuilder);
            //处理右子树
            getCode(node.rightNode, "1", stringBuilder);
        }
        return hashHuffman;
    }

    /**
     * 功能：将传入的node结点的所有叶子结点的赫夫曼编码得到，并放入到huffmanCodes集合
     *
     * @param node          传入结点
     * @param code          路径： 左子结点是 0, 右子结点 1
     * @param stringBuilder 用于拼接路径
     */
    public static void getCode(Node node, String code, StringBuilder stringBuilder) {
        StringBuilder stringBuilder1 = new StringBuilder(stringBuilder);
        stringBuilder1.append(code);
        if (node.data == null) {
            //先向左递归找
            getCode(node.leftNode, "0", stringBuilder1);
            //然后在右递归
            getCode(node.rightNode, "1", stringBuilder1);
        } else {
            //说明是一个子叶节点，就直接将对应的编码放入到节点当中
            hashHuffman.put(node.data, stringBuilder1.toString());
        }
    }

    //编写一个方法，完成对压缩数据的解码
    /**
     *
     * @param huffmanCodes 赫夫曼编码表 map
     * @param huffmanBytes 赫夫曼编码得到的字节数组
     * @return 就是原来的字符串对应的数组
     */

    public static byte[] decode(Map<Byte,String> huffmanCodes, byte[] huffmanBytes){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < huffmanBytes.length; i++) {
            byte b = huffmanBytes[i];
            boolean flag = (i == huffmanBytes.length - 1);
            stringBuilder.append(byteToBitString(!flag,b));
        }
        System.out.println(stringBuilder);

        Map<String,Byte> map = new HashMap<>();
        //把字符串按照指定的赫夫曼编码进行解码
        //把赫夫曼编码进行调换，因为反向查询a - > 100 就是转回原来的ASCII码
        for (Map.Entry<Byte, String> stringByteEntry : huffmanCodes.entrySet()) {
            map.put(stringByteEntry.getValue(),stringByteEntry.getKey());
        }
        //创建一个集合来存放byte
        List<Byte> list = new ArrayList<>();
        for (int i = 0; i< stringBuilder.length();){
            int count = 1;//写到外面去的话这个count就不能置1了
            Byte b = null;
            boolean flag = true;
            while (flag){
                String s = stringBuilder.substring(i,i+count);
                b = map.get(s);
                if (b == null){
                    count++;
                }else {
                   flag = false;

                }
            }
            list.add(b);
            i += count;//这里就直接移动i到count的位置
        }
        byte[] bytes = new byte[list.size()];

        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = list.get(i);
        }
        return bytes;
    }

    /**
     * 将一个byte 转成一个二进制的字符串, 如果看不懂，可以参考我讲的Java基础 二进制的原码，反码，补码
     * @param b 传入的 byte
     * @param flag 标志是否需要补高位如果是true ，表示需要补高位，如果是false表示不补, 如果是最后一个字节，无需补高位
     * @return 是该b 对应的二进制的字符串，（注意是按补码返回）
     */
    public static String byteToBitString(boolean flag,byte b){
        int tmp = b;//将byte转为int
        if (tmp > 0){
            if (flag){//最后一位不用补位，前面只要不是负数就要补高位
            tmp |= 256;
        }}
        String s = Integer.toBinaryString(tmp);
        if (s.length() > 8){
            return s.substring(s.length() - 8);//取后八位
        }else {
            for (int i = 0; i < zeroCount; i++) {
                s = "0" + s;
            }
        }
        return s;

    }
}


//重新创建一个Node
class Node implements Comparable<Node> {
    Byte data;
    int weight;
    Node leftNode;
    Node rightNode;

    //写一个前序遍历
    public void preOrder() {
        System.out.println(this);
        if (this.leftNode != null) {
            this.leftNode.preOrder();
        }
        if (this.rightNode != null) {
            this.rightNode.preOrder();
        }
    }


    public Node(Byte data, int weight) {
        this.data = data;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                ", weight=" + weight +
                '}';
    }

    @Override
    public int compareTo(Node o) {
        return this.weight - o.weight;
    }
}
```

### 文件的解压和压缩

```java
public static void zipFile(String sfcFile,String detFile){
        //创建输出流
        FileOutputStream os = null;
        //创建输入流
        FileInputStream in = null;
        //创建一个对象输出流
        ObjectOutputStream opj = null;
        try {
            in = new FileInputStream(sfcFile);
            //用一个byte数组去接受这个一个文件输入流
            byte[] fileBytes = new byte[in.available()];
            //将数据写入到byte数组中
            in.read(fileBytes);
            //压缩成赫夫曼编码
            byte[] bytes = HuffmanZip(fileBytes);
            //写入文件
            os = new FileOutputStream(detFile);
            opj = new ObjectOutputStream(os);
            //将byte写出
            opj.writeObject(bytes);
            //写入对应的huffman编码表,不然后续文件会恢复不了
            opj.writeObject(hashHuffman);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


public static void  Decompression(String sfcFile,String DFile){
        //创建对象输入流
        ObjectInputStream opi = null;
        //创建一个输出流
        FileOutputStream fo = null;
        //创建一个输入流
        InputStream ip = null;

        try {
            ip = new FileInputStream(sfcFile);
            opi = new ObjectInputStream(ip);
            //通过对象输出流拿到对应的bytes数组，对应的huffman编码
            byte[] bytes = (byte[]) opi.readObject();
            Map<Byte,String> huffmanCodes = (Map<Byte,String>) opi.readObject();
            //解码
            byte[] decode = decode(huffmanCodes, bytes);
            //写入到文件中
            fo = new FileOutputStream(DFile);
            fo.write(decode);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                assert fo != null;
                fo.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
```

**赫夫曼编码注意事项**：

1. 如果文件本身就是经过压缩处理的，那么使用赫夫曼编码再压缩效率不会有明显变化, 比如视频,ppt 等等文件。
2. 赫夫曼编码是按字节来处理的，因此可以处理所有的文件(二进制文件、文本文件)。
3. 如果一个文件中的内容，重复的数据不多，压缩效果也不会很明显.

## 图

### 图的举例说明

图是一种数据结构，其中结点可以具有零个或多个相邻元素。两个结点之间的连接称为边。 结点也可以称为顶点。如图：

![image-20221107150449766](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/w08h0u-2.png)

![image-20221107150502083](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/w08xic-2.png)

### 图的常用概念

1. 顶点(vertex)
2. 边(edge)
3. 路径
4. 无向图(右图)
5. 有向图
6. 带权图

![image-20221107150648187](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/w09bo5-2.png)

**无向图**： 顶点之间的连接没有方向，比如A-B,
即可以是 A-> B 也可以 B->A .

**路径**:  比如从 D -> C 的路径有

1) D->B->C
2) D->A->B->C

![image-20221107150821932](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/w09rhf-2.png)

**有向图**： 顶点之间的连接有方向，比如A-B,
只能是 A-> B 不能是 B->A

![image-20221107150836980](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/w0ajeh-2.png)

**带权图**：这种边带权值的图也叫网

### 图的表示方式

图的表示方式有两种：二维数组表示（邻接矩阵）；链表表示（邻接表）。

**邻接矩阵**
邻接矩阵是表示图形中顶点之间相邻关系的矩阵，对于n个顶点的图而言，矩阵是的row和col表示的是1....n个点。

![image-20221107150954985](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/w0digi-2.png)

![image-20221107151006346](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/w0dujs-2.png)

![image-20221107151015504](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/w0e71g-2.png)

这里表示的就是如果两个点之间相通那么，列的位置对应的点和行的位置对应的值就为1.

**邻接表**

1. 邻接矩阵需要为每个顶点都分配n个边的空间，其实有很多边都是不存在,会造成空间的一定损失.
2. 邻接表的实现只关心存在的边，不关心不存在的边。因此没有空间浪费，邻接表由数组+链表组成

![image-20221107151216492](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/w0ncn7-2.png)

![image-20221107151238107](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/w0nq02-2.png)

![image-20221107151242878](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/w0o8ka-2.png)

**说明**:

1. 标号为0的结点的相关联的结点为 1 2 3 4
2. 标号为1的结点的相关联结点为0 4，
3. 标号为2的结点相关联的结点为 0 4 5

### 图的快速入门代码

```java
public class Graph {
    //创建一个二维数组来表示矩阵
    private int[][] edges;
    //创建一个Arraylist来保存顶点vertx的集合
    private ArrayList<String> vertxList;
    //来记录边的数量
    private int numOfEdges;
    public static void main(String[] args) {
        Graph graph = new Graph(5);
        String[] Vertex = {"A","B","C","D","E"};
        for (String vertex : Vertex) {
            graph.insertVertex(vertex);
        }

        graph.insertEdges(0,1,1); //A-B
        graph.insertEdges(0,2,1);
        graph.insertEdges(1,0,1);
        graph.insertEdges(1,2,1);
        graph.insertEdges(1,3,1);
        graph.insertEdges(1,4,1);
        graph.insertEdges(2,0,1);
        graph.insertEdges(2,1,1);
        graph.insertEdges(3,1,1);
        graph.insertEdges(4,1,1);
        graph.showEdges();




    }

    //图常用的方法
    //返回结点的个数
    public int VertexNum(){
        return this.vertxList.size();
    }

    //返回边的条数
    public int EdgeNums(){
        return numOfEdges;
    }

    //返回对应的结点
    public String vertex(int i){
        return vertxList.get(i);
    }

    //返回对应节点的权值
    public int getWeight(int v1,int v2){
        return edges[v1][v2];
    }

    //显示图对应的矩阵
    public void showEdges(){
        for (int[] edge : edges) {
            System.out.println(Arrays.toString(edge));
        }
    }


    public Graph(int n) {
        this.edges = new int[n][n];
        this.vertxList = new ArrayList<>(n);
        this.numOfEdges = 0;
    }

    //添加顶点的方法
    public void insertVertex(String vertex){
        vertxList.add(vertex);
    }
    //添加边

    /**
     *
     * @param v1 表示点的下标即是第几个顶点
     * @param v2 表示第二个顶点对应的下标
     * @param weight 表示每一个边的权值为1就表示两个点之间有连接，0就表示没有连接
     *
     *     A   B   C   D   E
     * A   0   1   1   0   0
     * B   1   0   1   1   1
     * C   1   1   0   0   0
     * D   0   1   0   0   0
     * E   0   1   0   0   0
     */
    public void insertEdges(int v1,int v2,int weight){
        this.edges[v1][v2] = weight;//这里就是表示两个顶点之间是否有边连接
        this.edges[v2][v1] = weight;
        numOfEdges++;
    }
}
```

### 图的深度遍历

**深度优先遍历算法步骤**

1. 访问初始结点v，并标记结点v为已访问。
2. 查找结点v的第一个邻接结点w。
3. 若w存在，则继续执行4，如果w不存在，则回到第1步，将从v的下一个结点继续。
4. 若w未被访问，对w进行深度优先遍历递归（即把w当做另一个v，然后进行步骤123）。
5. 查找结点v的w邻接结点的下一个邻接结点，转到步骤3。

**完整代码**

```java
//图的深度遍历
    //这里先访问他的第一个节点如果第一个节点没有访问到那么就返回-1，这里传入第一个节点
    public int getFirstNeighbor(int i){
        for (int j = 0; j < vertxList.size(); j++) {
            if (edges[i][j] > 0){
                return j;
            }
        }
        return -1;
    }

    //拿到当前节点的下一个节点
    public int getNextNeighbor(int v1,int v2){
        for (int i = v2 + 1; i < vertxList.size(); i++) {
            if (edges[v1][i] > 0 ){
                return i;
            }
        }
        return -1;
    }
/**
     *
     * @param v1 表示点的下标即是第几个顶点
     * @param v2 表示第二个顶点对应的下标
     * @param weight 表示每一个边的权值为1就表示两个点之间有连接，0就表示没有连接
     *
     *     A   B   C   D   E
     * A   0   1   1   0   0
     * B   1   0   1   1   1
     * C   1   1   0   0   0
     * D   0   1   0   0   0
     * E   0   1   0   0   0
     */
    //开始深度遍历
    public void dfs(boolean[] isVisited,int i){
        System.out.print(vertex(i));
        //先遍历第一个节点，再用isVisited保存证明遍历过
        isVisited[i] = true;
        //查找第第一个邻接节点
        int w = getFirstNeighbor(i);
        //然后在判断
        while(w != -1){//如果找到了他的一个邻接节点就找下一个邻接节点，就比如拿上图举例，当找到A的时候他的第一个邻接点为B，应为找到的w不为-1，这时进入while循环，在判断B点是否已经访问过，如果没有访问过就把B作为当前结点继续向下找到B的第一个邻接点，找到C但是C后面已经没有连结的节点了，就之间结束c的方法回溯到B，B就找他的相邻下一个节点(w = getNextNeighbor(i,w) 即这个方法)，找到了D，D也没有下一个节点，回溯到B，继续找到了E，然后相继输出.
            //继续判断C是否访问过，没有访问就继续
            if (!isVisited[w]){
                dfs(isVisited,w);
            }
            w = getNextNeighbor(i,w);
        }

    }

    public void dfs(){
        for (int i = 0; i < VertexNum(); i++) {
            //这里重载的原因是应为A的点的邻接节点B，连接了剩下的节点，如果B没有连接剩下的节点，dfs这个方法在A点没有找到剩下的节点的时候就会在A点直接结束，剩下的结点就不会再去遍历。
            if (!isVisited[i]){
                dfs(isVisited,i);
            }
        }
    }
```

### 图的广度优先遍历

**广度优先遍历基本思想**：

​ 图的广度优先搜索(Broad First Search) 。
​ 类似于一个分层搜索的过程，广度优先遍历需要使用一个队列以保持访问过的结点的顺序，以便按这个顺序来访问这些结点的邻接结点

**广度优先遍历算法步骤：**

1. 访问初始结点v并标记结点v为已访问。
2. 结点v入队列
3. 当队列非空时，继续执行，否则算法结束。
4. 出队列，取得队头结点u。
5. 查找结点u的第一个邻接结点w。
6. 若结点u的邻接结点w不存在，则转到步骤3；否则循环执行以下三个步骤：
   6.1 若结点w尚未被访问，则访问结点w并标记为已访问。
   6.2 结点w入队列 。
   6.3 查找结点u的继w邻接结点后的下一个邻接结点w，转到步骤6。

```java
/**
     * 1. 访问初始结点v并标记结点v为已访问。
     * 2. 结点v入队列
     * 3. 当队列非空时，继续执行，否则算法结束。
     * 4. 出队列，取得队头结点u。
     * 5. 查找结点u的第一个邻接结点w。
     * 6. 若结点u的邻接结点w不存在，则转到步骤3；否则循环执行以下三个步骤：
     * 6.1 若结点w尚未被访问，则访问结点w并标记为已访问。
     * 6.2 结点w入队列 。
     * 6.3 查找结点u的继w邻接结点后的下一个邻接结点w，转到步骤6。
     *
     * @param i 对应的第一个节点
     */
    public void bfs(boolean[] isVisited, int i) {
        int v;//表示第一个节点
        int w;//表示第一个节点的邻接点
        //先创建一个队列
        LinkedList<Integer> queue = new LinkedList<>();
        //输出第一个结点
        System.out.print(vertex(i) + "->");
        //标记第一个结点
        isVisited[i] = true;
        //结点入队列
        queue.addLast(i);
        while (!queue.isEmpty()) {
            v = queue.removeFirst();
            w = getFirstNeighbor(v);
            while (w != -1) {
                if (!isVisited[w]) {
                    //如果该节点没有被访问过就继续输出该节点,然后将该节点入列，然后继续寻找A后面的结点，直到A没有可以连接的结点为止
                   System.out.print(vertex(w)+"->");
                    isVisited[w] = true;
                    queue.addLast(w);
                }
                w = getNextNeighbor(v, w);
            }
        }
    }

    //遍历所有的结点，都进行广度优先搜索
    public void bfs() {
        boolean[] isVisited = new boolean[n];
        for (int i = 0; i < VertexNum(); i++) {
            if (!isVisited[i]) {
                bfs(isVisited, i);
            }
        }

    }
```

## 程序员常用10大算法

### 二分查找（非递归）

```java
public class BinarySearchNoRecur {
    public static void main(String[] args) {
        int[] arr = {1,3,8,10, 11, 67, 100};
        System.out.println(binarySearch(arr, 3));
    }
    public static int binarySearch(int[] arr,int target){
        int left = 0;
        int right = arr.length - 1;
        while (left <= right){
            int mid = (left + right)/2;
            if (arr[mid] == target){
                return mid;
            }else if (arr[mid] < target) {
                left = mid + 1;
            }else {
                right = mid - 1;
            }
        }
        return -1;
    }
}
```

### 分治算法(汉诺塔)

```java
public class HanoiTower {
    public static void main(String[] args) {
        new HanoiTower().Han(2,'A','B','C');
    }
    public void Han(int n,char a, char b , char c){
        if (n == 1){
            System.out.println("第一个盘"+a +"->"+ c );
        }else {
            //如果我们有 n >= 2 情况，我们总是可以看做是两个盘 1.最下边的一个盘 2. 上面的所有盘
            //1. 先把 最上面的所有盘 A->B， 移动过程会使用到 c,这里的c就是传入的'B'，因为第一个判断第一个盘移动
            Han(n-1,a,c,b);
            System.out.println("第" + n + "个盘从 " + a + "->" + c);
            //3. 把B塔的所有盘 从 B->C , 移动过程使用到 a塔
            Han(n - 1, b, a, c);
        }
    }
}
```

### 动态规划-01背包问题

**动态规划算法的概述**：

（1）动态规划算法的思想是：将大问题分为小问题进行解决，使得一步步获得最优解的处理算法。

（2）动态规范算法与分治算法很类似，思想都是以待解决问题先分解成 n 个子问题，先求解子问题，然后从子问题中得到原问题的解。

（3）动态规划求解的问题与分治算法不同的点在于，经过分解出来的子问题不是相互独立的，下一个子问题的求解是建立在上一个子问题解决的求解基础上的，依次递进获取最终解。

（4）动态规划可以通过填表的方式一步步推进，最终得到最优解。

**2、背包问题**

有一个容量为4磅的背包，需要装入如下表1的物品，怎样才能使装入包中的商品最值钱且不超过4磅重？

| 商品名称 | 商品重量（单位磅） | 商品价值 |
|------|-----------|------|
| 鞋子   | 1         | 1500 |
| 音响   | 4         | 3000 |
| 电脑   | 3         | 2000 |

**动态规划算法解决背包问题**

上面的背包问题，放入商品的总质量不能超过4磅且要实现背包装入商品价值的最大化，这里假设放入的商品是不能重复的， 可用一个二维表格来表示。

**3、1 不可重复装入商品**

我先画一个表格2，然后会对这个表格2进行详细的说明，如下所示：

| 物品\背包容量  | 背包最大能装0磅  | 背包最大能装1磅 | 背包最大能装2磅 | 背包最大能装3磅 | 背包最大能装4磅 |
|----------|-----------|----------|----------|----------|----------|
| 没有物品表示0磅 | 0（第一行第一列） | 0        | 0        | 0        | 0        |
| 鞋子重1磅    | 0         | 1500     | 1500     | 1500     | 1500     |
| 音响重4磅    | 0         | 1500     | 1500     | 1500     | 3000     |
| 电脑重3磅    | 0         | 1500     | 1500     | 2000     | 3500     |

说明：列表示背包能装多大的重量，横表示物品的类型和商品数量，行和列交叉而出现的数据表示背包能装的物品总和的最大价值。

我们现在对表2的数据分析一下，第一行的数据全部为0，因为不管背包能放多大的重量，只要不放入物品，那就是0咯；第一列的数据全部为0，是因为背包能装0磅；我们看第二行第二列的数据到第二行第五列的数据，首先第二行能放的物品只能是鞋子。且不能重复

那不管背包（装的重量大于等于1磅）能装多少磅的物品，都是只能放1磅的鞋子对不对？那自然就是1500了。

3、2 思路分析

（1）利用动态规划来解决，假设给定的 n 个物品，设 v[i]、 w[i] 分 别为第 i 个商品的价值和重量，C 为背包的容量。

（2）每次遍历到的第 i 个商品，根据 w[i] 和 v[i]
来确定是否需要将该商品放入背包中；这句话说的是什么意思呢？我举个例子，你们就理解了，看表2的第四行的第四列的2000这个数据，首先第四列背包最大容量是3磅，第四行能放的商品有鞋子、音响和电脑，但是音响比背包的容量更大，所以就只能放鞋子和电脑，鞋子和电话的重量和超过3磅，所以又只能从鞋子和电脑里面挑选一个放进去，由于电脑比鞋子更值钱，所以放电脑价值更大，所以是根据
w[i] 和 v[i] 来确定是否需要将该商品放入背包中。

（3）再令 v【i】【j】表示在前 i 个商品中能够装入容量为 j 的背包中的最大价值；这句话又是什么意思啊？我再举个例子，看表2的第三行第五列的3000，这时候
i 就是2，j 就是4，v[i】[j] 就是 v[2】[4]，也就是 v[2】[4]
为3000；第二行只能装的商品是鞋子对不对？第三行能装入的商品包含第二行装入的商品，也就是说第三行能选择装入的商品是鞋子和音响；如果鞋子和音响同时放入背包（背包容量为4磅，j=4）肯定是装不下的对不对？所以从鞋子和音响里面选最值钱的放入背包中，所以就是
v[i】[j] 表示在前 i 个商品中能够装入容量为 j 的背包中的最大价值。

现在我们从思路分析和表2中能够总结出以下几条公式；

（1）v[i】[0] = v[0】[j] = 0，其中 i 表示第几行，j 表示第几列；看表2，第一列的数据是不是0？第一行的数据是不是也是0？

】2）当 w[i] > j 时，有 v[i】[j] = v[i-1】[j]，w 表示第 i+1 行商品的重量 ；举例：看表2中的第三行的第二列，i = 2，w[i] = 4，j = 1，v[
2】[1] = v[2-1】[1]  = 1500 。

（3）当j >= w[i] 时，有 v[i][j]=max{v[i-1】[j],v[i-1】[j-w[i]]+v[i]} ，v[i] 表示第 i+1
行商品的价格；举例：看表2，看第四行的第五列数据，j = 4，i = 3，w[3] = 3，v[3] = 2000，那么 v[3】[4] = max{v[3-1】[4] , v[3-1】[
4-w[3]]+v[3]} = max{3000 , 3500}，所以 v[3】[4] = 3500 。

```java
package Algorithm;

public class KnapsackProblem {
    public static void main(String[] args) {
        //先定义物品重量
        int[] w ={1,4,3};
        //定义物品的价格
        int[] value = {1500,3000,2000};
        //定义物品的名称
        String[] name = {"鞋子","音响","电脑"};
        //定义背包的容量
        int packageCapacity = 4;
        //定义一个二维价格表
        int[][] v = new int[name.length+1][packageCapacity+1];
        // 构建可能装入背包的二维数组
        // 值为0时说明不会装进背包, 值为1说明可能装入背包
        int[][] content = new int[name.length+1][packageCapacity+1];
        for (int i = 1; i < v.length ; i++) {
            for (int j = 1; j < v[i].length; j++) {
                if (w[i-1] > j){//因为这里i是从1开始的所以v[i] -> v[i-1]
                    v[i][j] = v[i-1][j];//应为初始化的二位数组全是0最开始就不用进行初始化
                }else if (w[i-1] <= j){
                    int price = value[i-1];
                    int NewPrice = v[i-1][j-w[i-1]]+value[i-1];//这里的i-1在放入最后一个商品的时候，
                    // 如果还有剩余的空间就看前面表的重量放入当前商品后背包容量减去放入物品的容量剩下的容量在前面i-1物品的最大价值，在加上
                    // 能放入的最大商品重量对应的价格，就为背包的总价值。
                    //这个时候这个位置就是当前背包对应重量能拿到的最大值
                    v[i][j] = Math.max(price,NewPrice);
                    if (v[i][j] == NewPrice){
                        content[i][j] = 1;
                    }
                }
            }
        }
        //这里拿到最大的行,即最后一行
        int i = v.length-1;
        //这里拿到最大的列,也表示的是背包的容量
        int j = v[0].length -1;
        //总价钱
        int totalPrice = 0;
        while (i > 0 && j>0){
            if (content[i][j] == 1){
                j -= w[i -1];
                totalPrice += value[i-1];
                System.out.println(name[i-1]+"放入了背包");
            }
            i--;
        }
        System.out.println(totalPrice);
    }
```

### KMP算法

KMP算法中最重要的就是拿到字符的前缀表，但是何为前缀表，听我道来

**前缀与后缀**：

为了知道何为**前缀后缀的公共元素的最大长度表**我们需要知道，前缀与后缀到底是什么

所谓前缀就是字符串中除了尾字符外的所有子串，后缀就是首字符外的所有子串，例如下图

![image-20221112205012646](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/w0qe8a-2.png)

“前缀后缀的公共元素的最大长度表”就是”前缀”和”后缀”的最长的共有元素的长度。以”ABCDABD”为例

| 模式串的各个字串 |                前缀                |             后缀             | 最大公共元素长度 |
|:--------:|:--------------------------------:|:--------------------------:|:--------:|
|    A     |                空                 |             空              |    0     |
|    AB    |                A                 |             B              |    0     |
|   ABC    |               A,AB               |           C ,BC            |    0     |
|   ABCD   |             A,AB,ABC             |          D,BC,BCD          |    0     |
|  ABCDA   |        **A**,AB,ABC,ABCD         |     **A**,DA,CDA,BCDA      |    1     |
|  ABCDAB  |  A,**AB**,ABC,ABCD,ABCDA,ABCDA   |  B,**AB**,DAB,CDAB,BCDAB   |    2     |
| ABCDABD  | A,AB,ABC,ABCD,ABCDA,ABCDA,ABCDAB | D,BD,ABD,DABD,CDABD,BCDABD |    0     |

也就是说，原模式串子串对应的各个前缀后缀的公共元素的最大长度表为（这里就是以各个字符串结尾的前缀后缀的公共元素的最大长度表）

![image-20221112210126740](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/w0qvx4-2.png)

为了得到这个表我们可以使用如下代码

```java
public static int[] getNext(String s){
        //初始化j
        int j =0;
        //初始化next
        int[] next = new int[s.length()];
        next[0] = 0;
        //为了进行比较i从1开始
        for (int i = 1; i < s.length() ; i++) {
            //当j和i对应的字符不相等的时候，那么j就要向后回退,如果一直没有找到j就会退到最开始的位置
            while (j > 0 && s.charAt(j) != s.charAt(i)){
                j = next[j-1];//退回是核心代码。
            }
            //先考虑j和i相同的情况,例如"AA",j指向第一个A，i指向第二个i，这个时候j就要j++，这时候他们的最大相等前缀长度就是1
            if (s.charAt(j) == s.charAt(i)){
                j++;
            }
            next[i] = j;
        }
    return next;
    }
```

**完整代码**

```java
public class KMP {
    public static void main(String[] args) {
        String s1 = "BBC ABCDAB ABCDABCDABDE";
        String s2 = "ABCDABD";
        int[] next = getNext("ABCDABD");
        System.out.println(Arrays.toString(next));
        System.out.println(KMPSearch(s1,s2,next));
    }

    //开始写Kmp
    public static int KMPSearch(String s1,String s2,int[] next){
        for (int i = 0, j = 0; i < s1.length(); i++) {
            //当两个字符串不相等的时候
            while (j > 0 && s1.charAt(i) != s2.charAt(j)){
                j = next[j-1];//当没有匹配到的时候就让j退回上一个最大前缀相等表的上一个值，再去判断j和i对应的字符是否相等。
            }
            //当两个字符串相等的时候
            if (s1.charAt(i) == s2.charAt(j)){
                j++;
            }
            if (j == s2.length()){
                return i-j+1;
            }
        }
        return -1;
    }


    //最大相等前缀表
    public static int[] getNext(String s){
        //初始化j
        int j =0;
        //初始化next
        int[] next = new int[s.length()];
        next[0] = 0;
        //为了进行比较i从1开始
        for (int i = 1; i < s.length() ; i++) {
            //当j和i对应的字符不相等的时候，那么j就要向后回退,如果一直没有找到j就会退到最开始的位置
            while (j > 0 && s.charAt(j) != s.charAt(i)){
                j = next[j-1];
            }
            //先考虑j和i相同的情况,例如"AA",j指向第一个A，i指向第二个i，这个时候j就要j++，这时候他们的最大相等前缀长度就是1
            if (s.charAt(j) == s.charAt(i)){
                j++;
            }
            next[i] = j;
        }
    return next;

    }
}
```

### 贪心算法

**贪心算法介绍**

贪婪算法(贪心算法)是指在对问题进行求解时，在每一步选择中都采取最好或者最优(即最有利)的选择，从而希望能够导致结果是最好或者最优的算法。

贪婪算法所得到的结果不一定是最优的结果(有时候会是最优解)，但是都是**相对近似(接近)最优解的结果**

**实际案例**

假设存在如下表的需要付费的广播台，以及广播台信号可以覆盖的地区。 如何选择最少的广播台，让所有的地区都可以接收到信号。

![image-20221114133928196](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/w0rslo-2.png)

**思路分析:**
使用贪心算法，效率高:
目前并没有算法可以快速计算得到准备的值，
使用贪心算法，则可以得到非常接近的解，并且效率高。选择策略上，因为需要覆盖全部地区的最小集合:
遍历所有的广播电台, 找到一个覆盖了最多未覆盖的地区的电台(此电台可能包含一些已覆盖的地区，但没有关系）
将这个电台加入到一个集合中(比如ArrayList), 想办法把该电台覆盖的地区在下次比较时去掉。
重复第1步直到覆盖了全部的地区。（意思就是选一个广播站，看他们的交集是否是最多的如果是的话那么就选择交集最多的一个广播站，选出来后在将已经覆盖到的广播站删除，就不断重复以上步骤）。

![image-20221114135407427](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/w0s82h-2.png)

这里的maxkey就是为了记录最大的交集所对应的key，当找到这个key的时候就会从一个Map中取出对应的广播地区

```java

public class greedyAlgorithm {
    public static void main(String[] args) {
        //先创建一个hashset来存放所有的地区
        HashSet<String> allAreas = new HashSet<>();
        //创建每一个广播的地区
        HashSet<String> hashSet = new HashSet<>();
        HashSet<String> hashSet2 = new HashSet<>();
        HashSet<String> hashSet3 = new HashSet<>();
        HashSet<String> hashSet4 = new HashSet<>();
        HashSet<String> hashSet5 = new HashSet<>();
        hashSet.add("北京");
        hashSet.add("上海");
        hashSet.add("天津");
        hashSet2.add("广州");
        hashSet2.add("北京");
        hashSet2.add("深圳");
        hashSet3.add("成都");
        hashSet3.add("上海");
        hashSet3.add("杭州");
        hashSet4.add("上海");
        hashSet4.add("天津");
        hashSet5.add("杭州");
        hashSet5.add("大连");
        //创建一个每一个广播对应的的地区
        HashMap<String,HashSet<String>> broadcasts = new HashMap<>();
        //将对应的广播地点传入map
        broadcasts.put("k1",hashSet);
        broadcasts.put("k2",hashSet2);
        broadcasts.put("k3",hashSet3);
        broadcasts.put("k4",hashSet4);
        broadcasts.put("k5",hashSet5);
        for (HashSet<String> value : broadcasts.values()) {
            allAreas.addAll(value);
        }
        //创建一个ArrayList来存放每一个广播站
        ArrayList<String> selects = new ArrayList<>();
        //定义一个临时的集合，在遍历的过程中存放当前电台没有覆盖的地区
        HashSet<String> tmpSet = new HashSet<>();
        while (allAreas.size() >0){//如果allAreas的长度不为0，说明还没有覆盖到全部地区就继续遍历直到覆盖所有的地区
            int maxTemp = 0;
            //定义一个maxKey来存放当前与要覆盖的地区交集最多的电台，如果有多个交集数量一致那么就取第一个
            String maxKey = null;
            for (String key : broadcasts.keySet()) {
                tmpSet.clear();//每一次使用完tmpSet都要清空！！！！
                HashSet<String> hashSet1 = broadcasts.get(key);
                //将当前key能够覆盖的地区都添加到临时的set中
                tmpSet.addAll(hashSet1);
                //求出tempSet 和 allAreas 集合的交集，交集会赋给tempSet
                tmpSet.retainAll(allAreas);
                //如果当tmp.size大于0那么就表明对应的key中还要没有覆盖到的地方
                //这里定义一个变量来记录最大的广播站交集。如果遍历到后面还有最大的交集数的话即tmpSet.size() > maxTemp,那么就继续交换
                if (tmpSet.size() > 0 && (maxKey == null || tmpSet.size() > maxTemp)){
                    maxKey = key;
                    maxTemp =tmpSet.size();
                }
            }
            //将找到的maxKey加入到选择当中去
            selects.add(maxKey);
            //然后再将广播能够覆盖到的地区移除
            allAreas.removeAll(broadcasts.get(maxKey));
            //将对应的maxKey删除
            broadcasts.remove(maxKey);
        }
        System.out.println(selects);
    }
}
```

### Prim算法

**应用场景-修路问题**

![image-20221114140348449](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/w0tyqq-2.png)

有7个村庄(A, B, C, D, E, F, G) ，现在需要修路把7个村庄连通
各个村庄的距离用边线表示(权) ，比如 A – B 距离 5公里
问：如何修路保证各个村庄都能连通，并且总的修建公路总里程最短?

**最小生成树**

修路问题本质就是就是最小生成树问题， 先介绍一下最小生成树(Minimum Cost Spanning Tree)，简称MST。
给定一个带权的无向连通图,如何选取一棵生成树,使树上所有边上权的总和为最小,这叫最小生成树
N个顶点，一定有N-1条边
包含全部顶点
N-1条边都在图中
举例说明(如图:

![image-20221114142627217
](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/w0wxeq-2.png)

求最小生成树的算法主要是普里姆算法和克鲁斯卡尔算法。

**普里姆算法介绍**：
普利姆(Prim)算法求最小生成树，也就是在包含n个顶点的连通图中，找出只有(n-1)条边包含所有n个顶点的连通子图，也就是所谓的极小连通子图

**思路分析加图解**：

设G=(V,E)是连通网，T=(U,D)是最小生成树，V,U是顶点集合，E,D是边的集合
若从顶点u开始构造最小生成树，则从集合V中取出顶点u放入集合U中，标记顶点v的visited[u]=1
若集合U中顶点ui与集合V-U中的顶点vj之间存在边，则寻找这些边中权值最小的边，但不能构成回路，将顶点vj加入集合U中，将边（ui,vj）加入集合D中，标记visited[vj]
=1
重复步骤②，直到U与V相等，即所有顶点都被标记为访问过，此时D中有n-1条边（看不懂没有关系，直接上图解）

![image-20221114181143838](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/w0xopw-2.png)

1. 先从顶点<A>开始，找到与A点连通的所有节点B,C,G,分别计算出他们的权值
2. A-C [7] **A-G[2]** A-B[5]  然后取最小的的值放到顶点的集合当中去<A,G>.
3. <A,G> 开始 , 将A 和 G 顶点和他们相邻的还没有访问的顶点进行处理 =>  A-C[7]  A-B[5]  **G-B[3]** G-E[4] G-F[6]  == ><
   A,G,B>
4. <A,G,B> 开始，将A,G,B 顶点 和他们相邻的还没有访问的顶点进行处理=><A,G,B,E>
   A-C[7] **G-E[4]** G-F[6] B-D[9]
5. {A,G,B,E}->F//第4次大循环 ， 对应 边<E,F> 权值：5
6. {A,G,B,E,F}->D//第5次大循环 ， 对应 边<F,D> 权值：4
7. {A,G,B,E,F,D}->C//第6次大循环 ， 对应 边<A,C> 权值：7 ===> <A,G,B,E,F,D,C>

最后找到的每一个权值最小的就组成了一颗最小生成树

```JAVA
public class primAlgorithm {
    public static void main(String[] args) {
        char[] data = {'A','B','C','D','E','F','G'};
        int [][]weight=new int[][]{
                {10000,5,7,10000,10000,10000,2},
                {5,10000,10000,9,10000,10000,3},
                {7,10000,10000,10000,8,10000,10000},
                {10000,9,10000,10000,10000,4,10000},
                {10000,10000,8,10000,10000,5,4},
                {10000,10000,10000,4,5,10000,6},
                {2,3,10000,10000,4,6,10000},};
        int vertex = data.length;
        MGraph mGraph = new MGraph(vertex);
        minTree minTree = new minTree();
        minTree.GreatGraph(mGraph,vertex,weight,data);
        minTree.ShowGraph(mGraph);
        minTree.primTree(mGraph,0);
    }
}
class minTree{
    //传入一个MGraph
    private MGraph mGraph;

    //创建图的邻接矩阵
    /**
     *
     * @param mGraph 图对象
     * @param vertex 图对应的顶点个数
     * @param data 图的各个顶点的值
     * @param weight 图的邻接矩阵
     */
    public void GreatGraph(MGraph mGraph , int vertex ,int[][] weight,char[] data){
        int i,j;
        for (i = 0; i < vertex; i++) {
                mGraph.data[i] = data[i];
            for (j = 0; j < vertex; j++) {
                mGraph.weight[i][j] = weight[i][j];
            }
        }
    }

    //输出一个邻接矩阵
    public void ShowGraph(MGraph mGraph){
        for (int[] weight: mGraph.weight) {
            System.out.println(Arrays.toString(weight));
        }
    }

    /**
     *
     * @param mGraph 传入一个图
     * @param v  表明是从第几个节点开始
     */
    //编写prim算法，得到最小生成树
    public void primTree(MGraph mGraph,int v){
        //创建一个数组，来判断该节点是否访问过
        int[] isVisited = new int[mGraph.Vertex];
        //用0来表示未访问，应为java中创建的数组，默认值就为0所有就不需要初始化
        isVisited[v] = 1;
        //这两个变量，来标记是两个节点相连
        int h1 = -1;
        int h2 = -1;
        //然后人工定义一个最小的权值
        int minWeight = 10000;
        for (int k = 1; k < mGraph.Vertex;k++){//这里就是当prim算法结束后，就已经生成mGraph.Vertex 的顶点数减一条边
            for (int i = 0; i < mGraph.Vertex; i++) {// i结点表示被访问过的结点
                //这里就是从第一个顶点开始找和它相邻的两条边
                for (int j = 0; j < mGraph.Vertex; j++) {//j结点表示还没有访问过的结点
                    if (isVisited[i] == 1 && isVisited[j] == 0 && mGraph.weight[i][j] < minWeight ){
                        //替换minWeight(寻找已经访问过的结点和未访问过的结点间的权值最小的边)
                        minWeight =mGraph.weight[i][j];
                        h1 = i;
                        h2 = j;
                    }
                }
            }
            //找到一条边是最小
            System.out.println("边<" + mGraph.data[h1] + mGraph.data[h2] + ">权值"+minWeight);
            //找到第一条边之后要将minWeight置为最大值
            minWeight = 10000;
            //就要将节点置为访问过
            isVisited[h2] = 1;
        }

    }

}

class MGraph{
    //确定顶点的个数
     int Vertex;
    //创建一个data来存放每一个顶点值
    char[] data;
    //创建每一个节点之间连接的权值
    int[][] weight;

    public MGraph(int vertex) {
        Vertex = vertex;
        this.data = new char[vertex];
        this.weight = new int[vertex][vertex];
    }
}
```

### 克鲁斯卡尔算法

克鲁斯卡尔最佳实践-公交站问题

![image-20221115190841482](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/w0ynjp-2.png)

有北京有新增7个站点(A, B, C, D, E, F, G) ，现在需要修路把7个站点连通
各个站点的距离用边线表示(权) ，比如 A – B 距离 12公里
问：如何修路保证各个站点都能连通，并且总的修建公路总里程最短?

思路：

在含有n个顶点的连通图中选择n-1条边，构成一棵极小连通子图，并使该连通子图中n-1条边上权值之和达到最小，则称其为连通网的最小生成树。

对于如上图G4所示的连通网可以有多棵权值总和不相同的生成树：

![image-20221115190920657](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/w0z0tk-2.png)

以上图G4为例，来对克鲁斯卡尔进行演示(假设，用数组R保存最小生成树结果)。

![image-20221115190951148](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/w0zgmp-2.png)

**第1步**：将边<E,F>加入R中。
边<E,F>的权值最小，因此将它加入到最小生成树结果R中。
**第2步**：将边<C,D>加入R中。
上一步操作之后，边<C,D>的权值最小，因此将它加入到最小生成树结果R中。
**第3步**：将边<D,E>加入R中。
上一步操作之后，边<D,E>的权值最小，因此将它加入到最小生成树结果R中。
**第4步**：将边<B,F>加入R中。
上一步操作之后，边<C,E>的权值最小，但<C,E>会和已有的边构成回路；因此，跳过边<C,E>。同理，跳过边<C,F>。将边<B,F>加入到最小生成树结果R中。
**第5步**：将边<E,G>加入R中。
上一步操作之后，边<E,G>的权值最小，因此将它加入到最小生成树结果R中。
**第6步**：将边<A,B>加入R中。
上一步操作之后，边<F,G>的权值最小，但<F,G>会和已有的边构成回路；因此，跳过边<F,G>。同理，跳过边<B,C>。将边<A,B>加入到最小生成树结果R中。

此时，最小生成树构造完成！它包括的边依次是：**<E,F> <C,D> <D,E> <B,F> <E,G> <A,B>**。

根据前面介绍的克鲁斯卡尔算法的基本思想和做法，我们能够了解到，克鲁斯卡尔算法重点需要解决的以下两个问题：

问题一很好解决，采用排序算法进行排序即可。

问题二，处理方式是：记录顶点在"最小生成树"中的终点，顶点的终点是"在最小生成树中与它连通的最大顶点"
。然后每次需要将一条边添加到最小生存树时，判断该边的两个顶点的终点是否重合，重合的话则会构成回路。

![image-20221115191051115](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/w18yos-2.png)

在将<E,F> <C,D> <D,E>加入到最小生成树R中之后，这几条边的顶点就都有了终点：

**(01)** C的终点是F。
**(02)** D的终点是F。
**(03)** E的终点是F。
**(04)** F的终点是F。（没有连接之前自己本身就是终点）

就是将所有顶点按照从小到大的顺序排列好之后；某个顶点的终点就是"与它连通的最大顶点"。

因此，接下来，虽然<C,E>是权值最小的边。但是C和E的终点都是F，即它们的终点相同，因此，将<C,E>
加入最小生成树的话，会形成回路。这就是判断回路的方式，我们加入的边的两个顶点不能都指向同一个终点否则将构成回路。

```java
public class KruskalAlgorithm {
    //创建一个变量来表示最大的值，来表示不连通
    static final int INF = 1000;
    //创建一个vertex来存储顶点
    char[] vertex;
    //创建邻接矩阵
    int[][] weight;
    //记录每一条边的数量
    int edges;
    public static void main(String[] args) {
        char[] vertex = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        int[][] weight = {
                /*A*//*B*//*C*//*D*//*E*//*F*//*G*/
                /*A*/ {0, 12, INF, INF, INF, 16, 14},
                /*B*/ {12, 0, 10, INF, INF, 7, INF},
                /*C*/ {INF, 10, 0, 3, 5, 6, INF},
                /*D*/ {INF, INF, 3, 0, 4, INF, INF},
                /*E*/ {INF, INF, 5, 4, 0, 2, 8},
                /*F*/ {16, 7, 6, INF, 2, 0, 9},};
        new KruskalAlgorithm(vertex,weight).kruskal();
    }
    public KruskalAlgorithm(char[] vertex, int[][] weight) {
        this.vertex = vertex;
        this.weight = weight;
        //统计边的条数
        for (int i = 0;i<weight.length;i++) {
            for (int j = i+1 ; j < weight[i].length; j++) {
                if (weight[i][j]!= INF) {
                    edges++;
                }
            }
        }
    }

    public void kruskal() {
        int index = 0;//表示最后结果数组的索引
        int[] end = new int[edges];//用于保存"已有最小生成树" 中的每个顶点在最小生成树中的终点
        //创建结果数组, 保存最后的最小生成树
        EData[] ret = new EData[edges];
        //获取所以的边的集合
        EData[] edge = getEdges();
        System.out.println(Arrays.toString(edge));
        //遍历edges 数组，将边添加到最小生成树中时，判断是准备加入的边否形成了回路，如果没有，就加入 rets, 否则不能加入
        for (int i = 0; i <edges ; i++) {
            //拿到对应顶点的下标
            int p1 = getPosition(edge[i].start);
            int p2 = getPosition(edge[i].end);
            //获取p1这个顶点在已有最小生成树中的终点
            int m = getEnd(end,p1);
            //获取p2这个顶点在已有最小生成树中的终点
            int n = getEnd(end,p2);
            if (m !=n){
                end[m] = n;// 设置m 在"已有最小生成树"中的终点 <E,F> [0,0,0,0,5,0,0,0,0,0,0,0]
                ret[index++] = edge[i];
            }
        }
        System.out.println(Arrays.toString(ret));
        


    }
    /**
     * 功能：对边进行排序处理, 冒泡排序
     * @param edges 边的集合
     */
    private void sortEdges(EData[] edges) {
        for (int i = 1;i < edges.length;i++){
            EData indexValue = edges[i];
            int index = i-1;
            while (index >= 0 && indexValue.weight < edges[index].weight){
                edges[index+1] = edges[index];
                index-=1;
            }
            edges[index+1] = indexValue;
        }
    }
    /**
     *
     * @param ch 顶点的值，比如'A','B'
     * @return 返回ch顶点对应的下标，如果找不到，返回-1
     */
    private int getPosition(char ch){
        for (int i = 0; i < vertex.length; i++) {
            if (ch == vertex[i]){
                return i;
            }
        }
        return -1;
    }

    /**
     * 功能: 获取图中边，放到EData[] 数组中，后面我们需要遍历该数组
     * 是通过weight 邻接矩阵来获取
     * EData[] 形式 [['A','B', 12], ['B','F',7], .....]
     * @return
     */
    private EData[] getEdges(){
        EData[] eData = new EData[edges];
        int index = 0;
        for (int i = 0; i < weight.length; i++) {
            for (int j = i+1; j < weight[i].length; j++) {
                if (weight[i][j] != INF){
                    eData[index++] = new EData(vertex[i],vertex[j],weight[i][j]);
                }
            }
        }
        sortEdges(eData);
        return eData;
    }

    /**
     * 功能: 获取下标为i的顶点的终点(), 用于后面判断两个顶点的终点是否相同
     * @param ends ： 数组就是记录了各个顶点对应的终点是哪个,ends 数组是在遍历过程中，逐步形成
     * @param i : 表示传入的顶点对应的下标
     * @return 返回的就是 下标为i的这个顶点对应的终点的下标
     */

    private int getEnd(int[] ends ,int i){
        while (ends[i] != 0){
            i = ends[i];
        }
        return i;
    }


    public void print(){
        for (int[] ints : weight) {
            System.out.println(Arrays.toString(ints));
        }
        System.out.println();
    }


}



//这是来记录每一条边的权值
class EData{
    //起始点
    char start;
    //终点
    char end;
    //权值
    int weight;


    public EData(char start, char end, int weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "EData{" +"<"+
                 start +","+ end + ">" +
                ", weight=" + weight + '}';
    }
}
```

### 迪杰斯特拉算法

应用场景-最短路径问题

![image-20221117202830995](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/w1aif1-2.png)

迪杰斯特拉(Dijkstra)算法是典型最短路径算法，用于计算一个结点到其他结点的最短路径。
它的主要特点是以起始点为中心向外层层扩展(广度优先搜索思想)
，直到扩展到终点为止。（相当于就是从G点开始找如果G后面有相连的节点那么就继续寻找，直到G点没有可以相通的节点,就找到和G点最近的那个节点作为下一个节点）。

**迪杰斯特拉(Dijkstra)算法过程**

设置出发顶点为v，顶点集合V{v1,v2,vi...}，v到V中各顶点的距离构成距离集合Dis，Dis{d1,d2,di...}，Dis集合记录着v到图中各顶点的距离(
到自身可以看作0，v到vi距离对应为di)
从Dis中选择值最小的di并移出Dis集合，同时移出V集合中对应的顶点vi，此时的v到vi即为最短路径。

更新Dis集合，更新规则为：比较v到V集合中顶点的距离值，与v通过vi到V集合中顶点的距离值，保留值较小的一个(
同时也应该更新顶点的前驱节点为vi，表明是通过vi到达的)
重复执行两步骤，直到最短路径顶点为目标顶点即可结束。

```java
public class DijkstraAlgorithm {
    public static void main(String[] args) {
        char[] vertex = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        int[][] matrix = new int[vertex.length][vertex.length];
        final int N = 65535;
        matrix[0] = new int[]{N, 5, 7, N, N, N, 2};
        matrix[1] = new int[]{5, N, N, 9, N, N, 3};
        matrix[2] = new int[]{7, N, N, N, 8, N, N};
        matrix[3] = new int[]{N, 9, N, N, N, 4, N};
        matrix[4] = new int[]{N, N, 8, N, N, 5, 4};
        matrix[5] = new int[]{N, N, N, 4, 5, N, 6};
        matrix[6] = new int[]{2, 3, N, N, 4, 6, N};
        new Graph(vertex, matrix).dsj(6);

    }
}


class Graph {
    char[] vertex;
    int[][] matrix;
    VisitedVertex vv;

    public Graph(char[] vertex, int[][] matrix) {
        this.vertex = vertex;
        this.matrix = matrix;
    }

    public void print() {
        for (int[] m : matrix) {
            System.out.println(Arrays.toString(m));
        }
    }

    /**
     * @param index 表示起始点的下标
     */
    public void dsj(int index) {
        vv = new VisitedVertex(vertex.length, 6);
        //这里就是先初始化最开始的起点坐标
        updateDis(index);
        for (int i = 1; i < vertex.length ; i++) {
            int j = vv.updateArr();
            updateDis(j);
        }
        vv.show();
    }
    /**
    updateDis(int index)方法解释
    首先我们先定义一个len设置为0来表示初始的值
    然后我们遍历整个邻边矩阵
    将起始点周围可以联通的点的距离全部更新到dis中，
    然后在设置联通节点的前驱节点为当前的顶点。
    len = matrix[index][i] + vv.getLen(index);
    其中vv.getLen(index)中拿到的值就是之前起始点到最初各个顶点的距离，再加上各个顶点到其他点的距离，就是起始点到那个点的距离
    **/

    private void updateDis(int index) {
        int len = 0;
        for (int i = 0; i < matrix[index].length; i++) {
            // len 含义是 : 出发顶点到index顶点的距离 + 从index顶点到j顶点的距离的和
            len = matrix[index][i] + vv.getLen(index);
            if (!vv.in(i) && len < vv.getLen(i)){//这里就是要判断当前节点是否被访问过，并且两个节点之间的长度是要最短,不能改成||切记切记
                //如果改了，就是同一个节点距离不断重复的相加
                vv.updateDis(len, i);
                vv.updatePre(i, index);
               
            }
        }
    }
}

// 已访问顶点集合
class VisitedVertex {
    // 记录各个顶点是否访问过 1表示访问过,0未访问,会动态更新
    public int[] already_arr;
    // 每个下标对应的值为前一个顶点下标, 会动态更新
    public int[] pre_visited;
    // 记录出发顶点到其他所有顶点的距离,比如G为出发顶点，就会记录G到其它顶点的距离，会动态更新，求的最短距离就会存放到dis
    public int[] dis;

    /**
     * @param length 顶点的个数
     * @param index  起始点的下标
     */
    public VisitedVertex(int length, int index) {
        this.already_arr = new int[length];
        this.pre_visited = new int[length];
        this.dis = new int[length];
        Arrays.fill(dis, 65535);
        dis[index] = 0;
        already_arr[index] = 1;
    }

    /**
     * @param index 对应节点的下标
     * @return 判断该节点是否已经被访问过
     */
    public boolean in(int index) {
        return already_arr[index] == 1;
    }

    /**
     * @param pre   要更新的节点
     * @param index 这个是pre对应的前驱节点的下标值
     */
    public void updatePre(int pre, int index) {
        pre_visited[pre] = index;
    }

    /**
     * @param len   跟对应的节点的长度
     * @param index 对应节点的下标
     */
    public void updateDis(int len, int index) {
        dis[index] = len;
    }

    /**
     * @param index 起始点和终点的对应下标值
     * @return 返回起始点和对应节点之间的距离
     */
    public int getLen(int index) {
        return dis[index];
    }

    /**
     *这里就是更新访问的节点，并且找到
     * @return 继续选择并且返回新的访问节点，比如G完后就是A点作为新的访问节点(注意不是新的起始点)
     */
    public int updateArr(){
        int min = 65535;
        int index = 0;
        for (int i = 0; i < already_arr.length;i++){
            if (already_arr[i] == 0 && dis[i] < min ){
                //判断当前节点是否被访问过，如果没有被访问过并且他的最小距离还最短就返回当前坐标的起始点
                min = dis[i];
                index = i;
            }
        }
        already_arr[index] = 1;
        return index;
    }

    public void show(){
        char[] vertex = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        int index = 0;
        for (int di : dis) {
            if (di != 65535){
                System.out.println(vertex[index]+"===============" + di);
            }
            index++;
        }
    }
}
```

### 弗罗伊德算法

**弗洛伊德(Floyd)算法介绍**：

1. 和Dijkstra算法一样，弗洛伊德(Floyd)算法也是一种用于寻找给定的加权图中顶点间最短路径的算法。该算法名称以创始人之一、
2. 1978年图灵奖获得者、斯坦福大学计算机科学系教授罗伯特·弗洛伊德命名
3. 弗洛伊德算法(Floyd)计算图中各个顶点之间的最短路径
4. 迪杰斯特拉算法用于计算图中某一个顶点到其他顶点的最短路径。
5. 弗洛伊德算法 VS
   迪杰斯特拉算法：迪杰斯特拉算法通过选定的被访问顶点，求出从出发访问顶点到其他顶点的最短路径；弗洛伊德算法中每一个顶点都是出发访问点，所以需要将每一个顶点看做被访问顶点，求出从每一个顶点到其他顶点的最短路径。

![image-20221119151053796](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/w1btym-2.png)

思路分析：

1.设置顶点vi到顶点vk的最短路径已知为Lik，顶点vk到vj的最短路径已知为Lkj，顶点vi到vj的路径为Lij，则vi到vj的最短路径为：min((
Lik+Lkj),Lij)，vk的取值为图中所有顶点，则可获得vi到vj的最短路径。

2.至于vi到vk的最短路径Lik或者vk到vj的最短路径Lkj，是以同样的方式获得。

**这里的k就是充当着一个中间节点的作用。**

在本算法中我们需要维护的是两张表

![image-20221119151416029](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/w1cfs4-2.png)

![image-20221119151420659](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/w1ctpy-2.png)

一张是各顶点之间的距离表，另外一张是初始顶点的前驱关系。

第一轮循环中，以A(下标为：0)作为中间顶点，距离表和前驱关系更新为：

![image-20221119151824121](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/w1d9p7-2.png)

1) 以A顶点作为中间顶点是，B->A->C的距离由N->9，同理C到B；C->A->G的距离由N->12，同理G到C

2) 更换中间顶点，循环执行操作，直到所有顶点都作为中间顶点更新后，计算结束。

```java
public class Floyd {
    public static void main(String[] args) {
        char[] vertex = {'A','B','C','D','E','F','G'};
        int[][] matrix = new int[vertex.length][];
        final int N = 65535;
        matrix[0] = new int[] { 0, 5, 7, N, N, N, 2 };
        matrix[1] = new int[] { 5, 0, N, 9, N, N, 3 };
        matrix[2] = new int[] { 7, N, 0, N, 8, N, N };
        matrix[3] = new int[] { N, 9, N, 0, N, 4, N };
        matrix[4] = new int[] { N, N, 8, N, 0, 5, 4 };
        matrix[5] = new int[] { N, N, N, 4, 5, 0, 6 };
        matrix[6] = new int[] { 2, 3, N, N, 4, 6, 0 };
        Graph graph = new Graph(vertex.length, vertex, matrix);
        graph.floyd();
        graph.show();
    }
}


class Graph{
    //先创建顶点的字符数组
    char[] vertex;
    //创建邻接矩阵
    int[][] matrix;
    //创建前驱节点，为了显示方便我这里直接用了char数组来进行展示
    char[][] pre;

    //初始化

    public Graph(int len ,char[] vertex, int[][] matrix) {
        this.vertex = vertex;
        this.matrix = matrix;
        this.pre = new char[len][len];
        for (int i = 0; i < vertex.length; i++) {
            Arrays.fill(pre[i],vertex[i]);
        }
    }

    public void show(){
        for (int i = 0; i < vertex.length; i++) {
            for (int j = 0; j < vertex.length; j++) {
                System.out.print(pre[i][j]);
            }
            System.out.println();
            for (int j = 1+i; j < vertex.length; j++) {
                System.out.println(""+vertex[i]+"到"+vertex[j]+"最短的距离为"+matrix[i][j]);
            }
        }
    }

    public void floyd(){
        //先创建一个len表示长度
        int len =0;
        for (int k = 0; k < vertex.length; k++) {//对中间顶点遍历， k 就是中间顶点的下标 [A, B, C, D, E, F, G]
            for (int i = 0; i < vertex.length; i++) {//从i顶点开始出发 [A, B, C, D, E, F, G],表示的是起始点
                for (int j = 0; j < vertex.length; j++) {
                    //到达j顶点 // [A, B, C, D, E, F, G]终点
                    len = matrix[i][k] + matrix[k][j]; //=> 求出从i 顶点出发，经过 k中间顶点，到达 j 顶点距离
                    if (len < matrix[i][j]){
                        matrix[i][j] = len;
                        pre[i][j] = pre[k][j];
                    }
                }
            }

        }
    }

}
```

但是注意该算法的事件复杂度是非常高的。

### 马踏棋盘算法

马踏棋盘算法也被称为骑士周游问题
将马随机放在国际象棋的8×8棋盘Board【0～7】[0～7]的某个方格中，马按走棋规则(马走日字)进行移动。要求每个方格只进入一次，走遍棋盘上全部64个方格

![image-20221119211940445](https://my-notes-li.oss-cn-beijing.aliyuncs.com/li/w1e55f-2.png)

马踏棋盘游戏代码实现：

马踏棋盘问题(骑士周游问题)实际上是图的深度优先搜索(DFS)的应用。

如果使用回溯（就是深度优先搜索）来解决，假如马儿踏了53个点，如图：走到了第53个，坐标（1,0），发现已经走到尽头，没办法，那就只能回退了，查看其他的路径，就在棋盘上不停的回溯……

骑士周游问题的解决步骤和思路：

1. 创建棋盘 chessBoard , 是一个二维数组
2. 将当前位置设置为已经访问，然后根据当前位置，计算马儿还能走哪些位置，并放入到一个集合中(ArrayList), 最多有8个位置，
   每走一步，就使用step+1
3. 遍历ArrayList中存放的所有位置，看看哪个可以走通 , 如果走通，就继续，走不通，就回溯.
4. 判断马儿是否完成了任务，使用 step 和应该走的步数比较 ， 如果没有达到数量，则表示没有完成任务，将整个棋盘置0

注意：**马儿不同的走法（策略），会得到不同的结果，效率也会有影响(优化)**

有可能马儿走的那一步是需要回溯最多次的那个一步，这样的话时候就会浪费很多所以因此就要做一下优化，优先遍历回溯次数最少的，这样就能大大的减少时间。可以使用lambda表达式进行排序操作

```java
public class HorseChess {
    static int X;//来表示列
    static int Y;//来表示行
    static boolean[] visited;//用一个boolean数组来存放当前的格子是否被访问过
    static boolean flag = false;//来表示下一步能否继续走决定回溯


    public static void main(String[] args) {
        X = 6;
        Y = 6;
        int[][] chess = new int[Y][X];
        int row = 1;//马儿的行位置从1开始编号
        int col = 6;//马儿的列位置从1开始编号
        visited = new boolean[X*Y];//默认为false
        long time1 = System.currentTimeMillis();
        horseChess(chess,row-1,col-1,0);
        System.out.println(System.currentTimeMillis() - time1+"ms");
        for (int[] ints : chess) {
            for (int anInt : ints) {
                System.out.print(anInt+"\t");
            }
            System.out.println();
        }
    }

    /**
     *
     * @param chess 棋盘
     * @param row 马儿当前的位置行开始为0
     * @param col 马儿开始的位置的列开始为0
     * @param step 马儿开始的步数从1开始
     */
    public static void horseChess(int[][] chess,int row,int col,int step){
        //把马儿开始走的那一步在棋盘上标记出来
        chess[row][col] = step;
        //把马儿走的哪个位置标记起来
        visited[row * X + col] = true;
        //将这个起始点传进去拿到一个ps的集合
        ArrayList<Point> ps = next(new Point(col, row));
        //做一个排序减少回溯的次数
        ps.sort((x,y) -> next(x).size() - next(y).size());
        while (!ps.isEmpty()){
            Point p1 = ps.remove(0);//取出下一个访问的位置,应为ps中只有一个元素所以这里就是index填的是0
            if (!visited[p1.y * X+p1.x]){
                horseChess(chess,p1.y,p1.x,++step);//这里如果换成了++step，完全可以大大的提高算法运行的效率
                ++操作是速率最快的。
            }
        }
        //应该有可能执行到最后step的步数不一定可以满足要求所以这里就要进行应该判断
        if (step < X*Y && !flag){
            chess[row][col] = 0;//就将棋盘上对应的点给置为0
            visited[row * X + col] = false;//将这个访问的点从新设置为未访问
        }else {
            flag = true;
        }
    }

    public static ArrayList<Point> next(Point curPoint){
        //创建一个ArrayList来储存马能走的每一个点
        ArrayList<Point> ps = new ArrayList<>();
        //创建一个点
        Point p1 = new Point();
        //表示马儿可以走5这个位置
        if((p1.x = curPoint.x - 2) >= 0 && (p1.y = curPoint.y -1) >= 0) {
            ps.add(new Point(p1));
        }
        //判断马儿可以走6这个位置
        if((p1.x = curPoint.x - 1) >=0 && (p1.y=curPoint.y-2)>=0) {
            ps.add(new Point(p1));
        }
        //判断马儿可以走7这个位置
        if ((p1.x = curPoint.x + 1) < X && (p1.y = curPoint.y - 2) >= 0) {
            ps.add(new Point(p1));
        }
        //判断马儿可以走0这个位置
        if ((p1.x = curPoint.x + 2) < X && (p1.y = curPoint.y - 1) >= 0) {
            ps.add(new Point(p1));
        }
        //判断马儿可以走1这个位置
        if ((p1.x = curPoint.x + 2) < X && (p1.y = curPoint.y + 1) < Y) {
            ps.add(new Point(p1));
        }
        //判断马儿可以走2这个位置
        if ((p1.x = curPoint.x + 1) < X && (p1.y = curPoint.y + 2) < Y) {
            ps.add(new Point(p1));
        }
        //判断马儿可以走3这个位置
        if ((p1.x = curPoint.x - 1) >= 0 && (p1.y = curPoint.y + 2) < Y) {
            ps.add(new Point(p1));
        }
        //判断马儿可以走4这个位置
        if ((p1.x = curPoint.x - 2) >= 0 && (p1.y = curPoint.y + 1) < Y) {
            ps.add(new Point(p1));
        }
        return ps;
    }


}
```

