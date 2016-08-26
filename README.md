RPN Calculator
--------------

使用逆波兰表达式（[Reverse Polish notation](https://en.wikipedia.org/wiki/Reverse_Polish_notation)）以及调度场算法（[Shunting-yard algorithm](https://en.wikipedia.org/wiki/Shunting-yard_algorithm)）实现的同用计算框架。暂不支持Function。

仅使用Java SE 6实现。（单元测试用到了mockito）

程序入口：
* `com.dewafer.rpncalculator.IntegerMathematicalDemo`：实现了加减乘除四则整数运算。
* `com.dewafer.rpncalculator.BooleanLogicalDemo`：实现了与或非条件以及非条件的逻辑运算。

或者查看算法的单元测试：
* `com.dewafer.rpncalculator.ReversePolishNotationExpressionProcessorUnitTest`
* `com.dewafer.rpncalculator.ShuntingYardTokenProcessorUnitTest`