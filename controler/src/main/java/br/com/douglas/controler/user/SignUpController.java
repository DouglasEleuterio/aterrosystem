package br.com.douglas.controler.user;

import br.com.douglas.controler.core.BaseController;
import br.com.douglas.controler.mapper.mappers.user.UserMapper;
import br.com.douglas.controler.mapper.mappers.user.UserRequest;
import br.com.douglas.controler.mapper.mappers.user.UserResponse;
import br.com.douglas.entity.entities.User;
import br.com.douglas.service.interfaces.IBaseService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/signup")
public class SignUpController extends BaseController<User, UserRequest, UserResponse> {
    protected SignUpController(final IBaseService<User> service, final UserMapper mapper) {
        super(service, mapper);
    }
}
