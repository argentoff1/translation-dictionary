package test;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@ComponentScan()
@Component
public class Test {
    public void printTest() {
        System.out.println("asdasdasd");
    }
}
