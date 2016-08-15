# benchmarks

contains the monitor and lock benchmarks described in [Performance improvements of java monitor from JDK 6 to 9](http://vmlens.com/articles/performance-improvements-of-java-monitor)
Build with maven and run with `java  -jar target/benchmarks.jar  -i 30   -wi 20  -f 3  -t 8  -si true`. See [jmh](http://openjdk.java.net/projects/code-tools/jmh/) for call parameter details.
