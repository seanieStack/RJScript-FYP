package io.github.seanieStack.stdlib;

import io.github.seanieStack.stdlib.builtin_modules.arrays.*;
import io.github.seanieStack.stdlib.builtin_modules.io.*;
import io.github.seanieStack.stdlib.builtin_modules.math.*;
import io.github.seanieStack.stdlib.builtin_modules.strings.*;
import io.github.seanieStack.stdlib.builtin_modules.time.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Registry that maps module names to their available functions.
 * Used by the import system to resolve function references.
 */
public class BuiltinModuleRegistry {

    private static final Map<String, Map<String, NativeFunction>> modules = new HashMap<>();

    static {
        // io module
        registerModule("io",
                new ReadFileFunction(),
                new WriteFileFunction(),
                new AppendFileFunction(),
                new DeleteFileFunction(),
                new FileExistsFunction(),
                new GetEnvFunction()
        );

        // strings module
        registerModule("strings",
                new SplitFunction(),
                new CharAtFunction(),
                new SubstringFunction(),
                new IndexOfFunction(),
                new ReplaceFunction(),
                new TrimFunction(),
                new UppercaseFunction(),
                new LowercaseFunction(),
                new StartsWithFunction(),
                new EndsWithFunction()
        );

        // math module
        registerModule("math",
                new SineFunction(),
                new CosineFunction(),
                new TangentFunction(),
                new AsinFunction(),
                new AcosFunction(),
                new AtanFunction(),
                new ExpFunction(),
                new LogFunction(),
                new Log10Function()
        );

        // arrays module
        registerModule("arrays",
                new SortFunction(),
                new ReverseFunction(),
                new SliceFunction(),
                new ContainsFunction(),
                new ArrayIndexOfFunction(),
                new JoinFunction(),
                new FirstFunction(),
                new LastFunction(),
                new IsEmptyFunction()
        );

        // time module
        registerModule("time",
                new NowFunction(),
                new SleepFunction(),
                new DayFunction(),
                new MonthFunction(),
                new YearFunction(),
                new HourFunction(),
                new MinuteFunction(),
                new SecondFunction()
        );
    }

    private static void registerModule(String moduleName, NativeFunction... functions) {
        Map<String, NativeFunction> module = new HashMap<>();
        for (NativeFunction function : functions) {
            module.put(function.name(), function);
        }
        modules.put(moduleName, module);
    }

    /**
     * Looks up a function by module and function name.
     *
     * @param moduleName   the name of the module
     * @param functionName the name of the function
     * @return the NativeFunction instance
     * @throws RuntimeException if the module or function is not found
     */
    public static NativeFunction getFunction(String moduleName, String functionName) {
        Map<String, NativeFunction> module = modules.get(moduleName);
        if (module == null) {
            throw new RuntimeException("Unknown module: '" + moduleName + "'");
        }

        NativeFunction function = module.get(functionName);
        if (function == null) {
            throw new RuntimeException("Unknown function '" + functionName + "' in module '" + moduleName + "'");
        }

        return function;
    }
}