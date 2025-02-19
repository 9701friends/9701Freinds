package friends.aidelivery.common;

import friends.aidelivery.common.builder.TestFixtureBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public abstract class ServiceTest {

    @Autowired
    protected TestFixtureBuilder testFixtureBuilder;
}
