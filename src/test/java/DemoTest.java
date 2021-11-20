import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class DemoTest
{
    @Test
    @DisplayName("Test 1")
    void demoTestMethod()
    {
        fail();
    }

    @Test
    @DisplayName("Test 2")
    void demoTestMethod2()
    {
        assertTrue(true);
    }
}
