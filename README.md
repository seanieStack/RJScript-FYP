# RJScript

A simple scripting language built with Java and ANTLR4. RJScript compiles source files to an AST and runs them on a tree-walking interpreter.

## Building

```bash
./gradlew jar
```

## Running

```bash
sudo dnf install -y java-21-openjdk-devel #fedora
sudo apt install -y openjdk-21-jdk #ubuntu/debian
sudo pacman -S jdk21-openjdk #arch

git clone https://github.com/seanieStack/RJScript-FYP

cd RJScript

./run.sh <script>
# or 

./gradle build
java -jar build/libs/FYP-RJScript-0.0.1.jar <script>

```

In the case that you get a build error when running `./gradle build` 
you need to set the path to the Java 21 JDK inside `gradle.properties`

```bash
ls /usr/lib/jvm/ | grep java-21
```

which in ubuntu for example will say `java-21-openjdk-amd64`

this should be added to the `gradle.properties` files

```
echo "org.gradle.java.home=/path/to/your/java-21" >> gradle.properties
./gradlew --stop
./gradlew jar
```

### Debug Flags

| Flag | Description |
|------|-------------|
| `--ast` / `-a` | Print the AST before execution |
| `--tokens` / `-t` | Print the lexer token stream |
| `--trace` / `-T` | Trace each interpreter visit call |
| `--env` / `-e` | Dump variables and functions after execution |

## Language Overview

### Variables and Types

```javascript
let name = "RJScript";
let pi = 3.14;
let count = 42;
let active = true;
let items = [1, 2, 3];
```

### Control Flow

```javascript
if (x > 0) {
    print("positive");
} else if (x == 0) {
    print("zero");
} else {
    print("negative");
}

while (n > 0) {
    n = n - 1;
}

for (let i = 0; i < 10; i = i + 1) {
    print(i);
}
```

### Functions

```javascript
function factorial(n) {
    if (n <= 1) {
        return 1;
    }
    return n * factorial(n - 1);
}

print(factorial(5)); // 120
```

### Arrays

```javascript
let arr = [1, 2, 3, 4, 5];
print(arr[0]);   // 1
print(arr[-1]);  // 5

push(arr, 6);
let last = pop(arr);
```

### Modules

```javascript
import readFile from io;
import split from strings;
import sort from arrays;

let content = readFile("data.txt");
let lines = split(content, "\n");
```

Available modules: `io`, `strings`, `math`, `arrays`, `time`.

## Built-in Functions

| Function | Description |
|----------|-------------|
| `print(value)` | Output to stdout |
| `input()` | Read user input |
| `len(value)` | Length of array or string |
| `typeof(value)` | Type as a string |
| `int(value)`, `float(value)`, `str(value)`, `bool(value)` | Type conversion |
| `abs(n)`, `min(a,b)`, `max(a,b)` | Basic math |
| `sqrt(n)`, `pow(b,e)`, `round(n)`, `floor(n)`, `ceil(n)` | Math utilities |
| `random()`, `randomInt(min,max)` | Random numbers |
| `push(arr,val)`, `pop(arr)` | Array mutation |
| `range(end)`, `range(start,end)`, `range(start,end,step)` | Generate arrays |

## Examples

The `examples/` directory contains complete programs:

- **expense-tracker** Parses a CSV of expenses, totals by category, and generates a report
- **log-analyzer**  Reads server logs and produces a summary of errors and warnings
- **adventOfCode2025**  An Advent of Code solution demonstrating file I/O and string parsing

## Java Embedding

RJScript can be embedded in Java applications. Custom modules can be created by implementing the `RJScriptModule` interface to expose Java functions to RJScript code. See `examples/embedding/` for a working example.