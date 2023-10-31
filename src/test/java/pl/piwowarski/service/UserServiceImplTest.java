package pl.piwowarski.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.piwowarski.model.User;
import pl.piwowarski.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Test
    public void given_no_input_keyword_find_all() {
        // given
        User user = new User("lukaszp4@onet.eu", "LukaszP12", "pass123");
        User user1 = new User("richard@onet.eu", "Richard", "pass123");
        List<User> list = new ArrayList<>();
        list.add(user);
        list.add(user1);

        UserService usi = new UserService(userRepository);

        // when
        when(userRepository.findAll()).thenReturn(list);
        List<User> allFoundProducts = usi.findAllUsersByKeyword(null);

        // then
        Assert.assertEquals(list, allFoundProducts);
    }

}