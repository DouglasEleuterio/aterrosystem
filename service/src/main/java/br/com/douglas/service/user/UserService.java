package br.com.douglas.service.user;

import br.com.douglas.entity.entities.Authority;
import br.com.douglas.entity.entities.User;
import br.com.douglas.exception.exceptions.DomainException;
import br.com.douglas.message.messages.Message;
import br.com.douglas.repository.repositories.authority.AuthorityRepository;
import br.com.douglas.repository.repositories.user.UserRepository;
import br.com.douglas.rsql.jpa.util.SpecificationUtils;
import br.com.douglas.service.impls.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService extends BaseService<User> {

    private final UserRepository repository;
    private final AuthorityRepository authorityRepository;

    protected UserService(final UserRepository repository, AuthorityRepository authorityRepository) {
        super(repository);
        this.repository = repository;
        this.authorityRepository = authorityRepository;
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities() {
        return SecurityUtils.getCurrentUsername().flatMap(repository::findOneWithAuthoritiesByUsername);
    }

    public User findByUsername(String username) throws DomainException {
        return repository.findOne(SpecificationUtils.rsqlToSpecification("username==" + username))
                .orElseThrow(() -> new DomainException(Message.toLocale("user.not-found", username)) );
    }

    @Override
    public void validate(User entity) throws DomainException {
        validarSeSenhaForte(entity.getPassword());
        super.validate(entity);
    }

    @Override
    public User create(User entity) throws DomainException {
        Set<Authority> auths = new HashSet<>();
        for (Authority authority : entity.getAuthorities()) {
            auths.add(authorityRepository.findFromName(authority.getName()).orElseThrow(() -> new DomainException(Message.toLocale("authoritie.not-found"))));
        }
        entity.getAuthorities().clear();
        entity.setAuthorities(auths);
        return super.create(entity);
    }

    public void validarSeSenhaForte(String password) throws DomainException {

        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        boolean hasNumber = false;
        boolean hasSpecialChar = false;

        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);
            if (Character.isUpperCase(ch)) {
                hasUpperCase = true;
            } else if (Character.isLowerCase(ch)) {
                hasLowerCase = true;
            } else if (Character.isDigit(ch)) {
                hasNumber = true;
            } else if (!Character.isLetterOrDigit(ch)) {
                hasSpecialChar = true;
            }
        }
        if(!hasNumber || !hasUpperCase || !hasLowerCase || !hasSpecialChar || password.length() < 8)
            throw new DomainException(Message.toLocale("senha.invalida"));
    }
}