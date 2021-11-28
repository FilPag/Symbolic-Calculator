DEPS = dependencies/
TESTPATH= classes;$(DEPS)junit-4.12.jar;$(DEPS)hamcrest-core-1.3.jar 
IO = IO/
AST = src/AST/
CALCULATOR = src/Calculator/
MAIN = calculator.parser.Calculator

all:
	javac -d classes -cp classes $(AST)*.java
	javac -d classes -cp classes $(CALCULATOR)*.java

run: all
	java  -cp classes $(MAIN)

test: all
	javac -d classes -cp $(TESTPATH) tests/TestAST.java
	java -cp $(TESTPATH) org.junit.runner.JUnitCore tests.TestAST

testSystem: all
	java -cp classes $(MAIN) < $(IO)input.txt > $(IO)output.txt
