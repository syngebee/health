import com.itheima.health.utils.sms.SMSUtils;
import org.junit.jupiter.api.Test;

public class SmsTest {

    @Test
    public void test1(){
        SMSUtils.registerSendCode("13916427105","123456");
    }
}
