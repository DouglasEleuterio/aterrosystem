package br.com.douglas.controller.authority;

import br.com.douglas.controller.core.BaseController;
import br.com.douglas.controller.mapper.BaseMapper;
import br.com.douglas.controller.mapper.mappers.authoritie.AuthorityRequest;
import br.com.douglas.controller.mapper.mappers.authoritie.AuthorityResponse;
import br.com.douglas.entity.entities.Authority;
import br.com.douglas.service.interfaces.IBaseService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/authority")
public class AuthorityControler extends BaseController<Authority, AuthorityRequest, AuthorityResponse> {
    protected AuthorityControler(IBaseService<Authority> service, BaseMapper<Authority, AuthorityRequest, AuthorityResponse> responseMapper) {
        super(service, responseMapper);
    }
}
