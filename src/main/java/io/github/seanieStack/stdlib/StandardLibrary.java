package io.github.seanieStack.stdlib;

import io.github.seanieStack.environments.Environment;
import io.github.seanieStack.stdlib.stdlib_functions.*;

/**
 * Registers all built-in native functions into the environment.
 * Called during interpreter initialization to make standard library functions available.
 */
public class StandardLibrary {

    /**
     * Registers all standard library functions into the given environment.
     *
     * @param env the environment to register functions into
     */
    public static void register(Environment env) {
        // I/O
        register(env, new PrintFunction());
        register(env, new InputFunction());

        // Type conversion
        register(env, new IntFunction());
        register(env, new FloatFunction());
        register(env, new StrFunction());
        register(env, new BoolFunction());

        // Type inspection
        register(env, new TypeOfFunction());
        register(env, new LenFunction());

        // Math
        register(env, new AbsFunction());
        register(env, new MinFunction());
        register(env, new MaxFunction());
        register(env, new SqrtFunction());
        register(env, new PowFunction());
        register(env, new RoundFunction());
        register(env, new FloorFunction());
        register(env, new CeilFunction());

        // Random
        register(env, new RandomFunction());
        register(env, new RandomIntFunction());

        // Utilities
        register(env, new RangeFunction());
    }

    private static void register(Environment env, NativeFunction function) {
        env.putFunction(function.name(), function);
    }
}
