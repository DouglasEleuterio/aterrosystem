package br.com.douglas.service.authority;

import br.com.douglas.entity.entities.Authority;
import br.com.douglas.repositories.BaseRepository;
import br.com.douglas.service.impls.BaseService;
import org.springframework.stereotype.Service;

@Service
public class AuthorityService extends BaseService<Authority> {
    protected AuthorityService(BaseRepository<Authority> repository) {
        super(repository);
    }
}
