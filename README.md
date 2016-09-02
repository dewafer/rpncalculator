RPN Calculator
--------------

使用逆波兰表达式（[Reverse Polish notation](https://en.wikipedia.org/wiki/Reverse_Polish_notation)）以及调度场算法（[Shunting-yard algorithm](https://en.wikipedia.org/wiki/Shunting-yard_algorithm)）实现的通用计算框架。暂不支持Function。

仅使用Java SE 6实现。（单元测试用到了mockito）

程序入口：
* `com.dewafer.rpncalculator.IntegerMathematicalDemo`：实现了加减乘除四则整数运算。
* `com.dewafer.rpncalculator.BooleanLogicalDemo`：实现了与或非条件以及非条件的逻辑运算。

或者查看算法的单元测试：
* `com.dewafer.rpncalculator.ReversePolishNotationExpressionProcessorUnitTest`
* `com.dewafer.rpncalculator.ShuntingYardTokenProcessorUnitTest`

概述
====

### 处理器`Processor<T, R>`

本项目内，每一个算法基本模块为一个处理器，实现`Processor<T, R>`接口。

一个处理器接受两个泛型参数`T`和`R`，分别为处理器接收处理的参数类型以及处理完成后的返回类型。

一个处理器只有2个方法，分别为`push(T t)`和`R done()`。
其中`push(T t)`方法表示将处理器能处理的`T`类型的处理对象压入处理器。
`R done()`表示本处理器处理完成，将类型为`R`的处理结果返回。

`push(T t)`方法的返回值为处理器对象本身，因此可以使用方法链将多次压入和完成串联起来，譬如：

```java
    Processor<String, String> processor = ...;
    processor
        .push("1")
        .push("+")
        .push("1")
        .push("=")
    .done();
```

原则上不限定`done`之前可以调用`push`的次数，意味着某些处理器可以不用压入参数，也可能要求强制压入固定数目的参数。
由处理器的具体实现本身附加限制（如`SingleStepProcessor`限制最多只能压入一次参数，如多次压入将会抛出`IllegalStateException`）。

原则上限定`push`必须在`done`之前进行，并且处理器`done`之后不应再次`push`或者`done`。

### 符号`Token`

符号`Token`为标识接口，标识该对象为一符号，譬如某一操作符、操作数、或者括号等。`Token`接口并无具体需要实现的方法。

由`Token`扩展的，表示更具体的接口有：

* `Parenthesis` 表示当前对象为一个括号
    * `LeftParenthesis` 表示当前对象为左括号
    * `RightParenthesis` 表示当前对象为右括号
* `Operand` 表示当前对象为一个操作数
* `Operator` 表示当前对象为一个操作符

这些具象接口可能会要求实现类实现某些方法。

### 符号读取器`TokenReader`

该类扩展了可迭代对象`Iterable`接口，表示实现了本接口的子类可以自迭代产生`Token`。
虽然本接口没有规定子类必须实现的方法，但因为扩展了`Iterable`接口，实现本接口的类必须实现`Iterable`中规定的方法。

因为`TokenReader`扩展了`Iterable`接口，也就意味着`TokenReader`可以放在迭代循环中使用：
```java
    TokenReader tokenReader = ...;
    for (Token token: tokenReader) {
        // process token...
    }
```

可以将`TokenReader`压入`TokenReaderProcessor`进行处理。


### 处理器串联

可以将多个处理器`Processor<T, R>`使用包装模式包装起来形成处理器链，如`RPNCalculator`中
```java
    readerProcessor = new TokenReaderProcessorImpl<Operand<R>>(
            new ShuntingYardTokenProcessor<Operand<R>>(
                    new ReversePolishNotationExpressionProcessor<R>()
            )
    );
```

`readerProcessor`是一个由`TokenReaderProcessorImpl`、`ShuntingYardTokenProcessor`和`ReversePolishNotationExpressionProcessor`
3个处理器组成的处理器链。当一个`TokenReader`被压入`readerProcessor`后，处理结果将会被依次从`TokenReaderProcessorImpl`压入`ShuntingYardTokenProcessor`
，然后再从`ShuntingYardTokenProcessor`压入`ReversePolishNotationExpressionProcessor`。

当`readerProcessor` `done`时，三个处理器同时完成并将最终结果返回。

处理器之间的顺序由包装的顺序决定，但处理器将结果压入下一个处理器的时机由处理器本身实现决定。