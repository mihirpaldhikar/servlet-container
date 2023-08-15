# Servlet Container

While developing a Servlet Project, it is a tedious task to configure Server.

This library reduces the hassle to configure the Server by creating an abstraction.

## Usage

`pom.xml`

```xml
<dependency>
    <groupId>com.mihirpaldhikar</groupId>
    <artifactId>servlet-container</artifactId>
    <version>${latest_version}</version>
</dependency>
```

`Main.java`
```java
import com.mihirpaldhikar.servlet.container.ContainerConfig;
import com.mihirpaldhikar.servlet.container.ServletContainer;

public class Main {
    public static void main(String[] args) {
        ContainerConfig config = new ContainerConfig(8080, "com.mihirpaldhikar.Main", "/src/main/webapp");
        ServletContainer container = new ServletContainer(config);
        container.start();
    }
}
```