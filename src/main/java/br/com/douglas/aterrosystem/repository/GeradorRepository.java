package br.com.douglas.aterrosystem.repository;

import br.com.douglas.aterrosystem.entity.Gerador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeradorRepository extends JpaRepository<Gerador, Long> {
}
