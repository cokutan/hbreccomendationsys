package reccomendationengine;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)

public class SpringContextTests {


    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
    }
}