package github.io.ylongo.ch08.account;

import github.io.ylongo.ch08.configurations.MockConfiguration;
import org.junit.jupiter.api.Test;

class TestDefaultAccountManager {
    
    @Test
    void testFindAccountByUser() {

        // 自定义mock的log与configuration实现
        MockLog mockLog = new MockLog();
        MockConfiguration mockConfiguration = new MockConfiguration();
        mockConfiguration.setSQL("SELECT * xxx");

        DefaultAccountManager2 accountManager2 = new DefaultAccountManager2(mockLog, mockConfiguration);
        accountManager2.findAccountForUser("user");
    }
}
