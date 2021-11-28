# Symbolic Calculator

# Operations
### Constants 
  `pi `- 3.14159 (π) <br>
  `e`  - 2.71828 (Euler Constant)
### Arithmetic operations
  `+` (addition), <br>
  `-` (subtraction),<br>
  `*` (multiplication),<br>
  `/` (division)<br>

  Example:

    3 + 2 - 1 * 4 / 5
    4.2
    
    3 + (2 - 1) * 4 / 5
    3.8

### Sin, Cos, Log, Exp

  Keywords:<br>
  `sin` <br>
  `cos`<br>
  `log`<br>
  `exp`

  Example:

    sin pi
    1

    cos pi
    -1

    log e
    1

    exp 1
    2.71828 (Euler Constant) 

### Variables
  Example:

    1 = x
    1.0

    2 = banana
    2.0

    x + banana
    3.0

### Conditionals and scopes
Keywords: <br>
`if` <br>
`else` <br>
`{}`  Scopes<br>
`<`   Less than <br>
`>`   Greater than <br>
`<=`  Less than or equal <br>
`>=`  Greater than or equal <br>
`==`  Equals

  Example:

    {1 = x} + {1 = x}
    2.0

    {1 = x} + {x}
    1.0 + x

    3 = x
    3.0

    4 = x
    4.0

    if x < y { 42 } else { 4711 }
    42.0

    if x > y { 42 } else { 4711 }


### Custom functions 
Keywords:<br>
`function` Starts decalaration of function<br>
`end` Ends declaration of function

  Example:

    function gcd(a, b)
    b - a = ba
    a - b = ab
      if a == b { a } else { if a < b { gcd(a, ba) } else { gcd (ab, b) } }
    end

    gcd(120, 55)
    5.0


### Misc 
`quit` - Exits the calculator<br>
`clear` - Clears all variable in a scope

# Usage
`make ` - compile <br>
`make run` - compiles and runs the program <br>
`make test` - compiles and runs the automatic tests <br>
`make testSystem` runs test with input.txt and give the output in output.txt<br>

## Note 
Originally created in 2019 as part of a university course in Java

