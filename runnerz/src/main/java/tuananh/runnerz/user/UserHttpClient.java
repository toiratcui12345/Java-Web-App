package tuananh.runnerz.user;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;

public interface UserHttpClient {
    @GetExchange("/users")
    List<User> findAll();
    @HttpExchange("/users/{id}")
    User findById(@PathVariable Integer id);
}
