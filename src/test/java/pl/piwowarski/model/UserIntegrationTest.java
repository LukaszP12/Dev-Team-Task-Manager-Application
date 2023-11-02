package pl.piwowarski.model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import pl.piwowarski.repository.UserRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void whenFindByName_thenReturnUser() {
        // given
        User jakob = new User("jakob12@onet.pl", "jakob12", "warszawa123");
        entityManager.persist(jakob);
        entityManager.flush();

        // when
        User found = userRepository.findByEmail(jakob.getEmail());

        // then
        Assert.assertEquals(found.getName(), jakob.getName());
    }

}
