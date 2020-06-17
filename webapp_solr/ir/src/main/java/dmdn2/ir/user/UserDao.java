package dmdn2.ir.user;


import com.google.common.collect.*;
import org.mindrot.jbcrypt.BCrypt;

import java.util.*;
import java.util.stream.*;

public class UserDao {

    String newSalt = BCrypt.gensalt();
    String newHashedPassword = BCrypt.hashpw("progettoir2019",newSalt);
    private final List<User> users = ImmutableList.of(

            //                                                                   password: progettoir2019

            new User("admin",  newSalt, newHashedPassword)
    );

    public User getUserByUsername(String username) {
        return users.stream().filter(b -> b.getUsername().equals(username)).findFirst().orElse(null);
    }

    public Iterable<String> getAllUserNames() {
        return users.stream().map(User::getUsername).collect(Collectors.toList());
    }

}