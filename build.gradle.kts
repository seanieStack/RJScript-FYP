
plugins {
    id("java")
    antlr
}

group = "io.github.seanieStack"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    antlr("org.antlr:antlr4:4.13.2")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

tasks {

    generateGrammarSource {
        arguments = arguments + listOf("-visitor")
    }

    jar {
        manifest.attributes["Main-Class"] = "io.github.seanieStack.RJScript"


        // REF: https://www.youtube.com/watch?v=FCfiCPIeE2Y
        val deps = configurations
            .runtimeClasspath
            .get()
            .map { zipTree(it) }

        from(deps)
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }
}