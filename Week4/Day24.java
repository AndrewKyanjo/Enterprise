// Day24.java
/**
 * Day 24: Build Tool Awareness – Maven & Gradle
 * 
 * This file is not a real build script, but it demonstrates the kind of configuration
 * that Maven (pom.xml) and Gradle (build.gradle) use.
 * 
 * Run this program to see a comparison.
 */
public class Day24 {
    public static void main(String[] args) {
        System.out.println("=== Build Tool Awareness ===");
        System.out.println("\nMaven:");
        System.out.println("  - Uses pom.xml for configuration");
        System.out.println("  - Convention over configuration");
        System.out.println("  - Lifecycle: compile, test, package, install, deploy");
        System.out.println("  - Dependency management from Maven Central");

        System.out.println("\nGradle:");
        System.out.println("  - Uses build.gradle (Groovy) or build.gradle.kts (Kotlin)");
        System.out.println("  - Based on Apache Ant and Maven concepts");
        System.out.println("  - More flexible, uses incremental builds");
        System.out.println("  - Supports both Groovy and Kotlin DSL");

        System.out.println("\nSample Maven pom.xml dependency:");
        System.out.println("  <dependency>");
        System.out.println("      <groupId>org.apache.commons</groupId>");
        System.out.println("      <artifactId>commons-lang3</artifactId>");
        System.out.println("      <version>3.12.0</version>");
        System.out.println("  </dependency>");

        System.out.println("\nSample Gradle build.gradle dependency:");
        System.out.println("  dependencies {");
        System.out.println("      implementation 'org.apache.commons:commons-lang3:3.12.0'");
        System.out.println("  }");
    }
}