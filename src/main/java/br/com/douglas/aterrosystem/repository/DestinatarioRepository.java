package br.com.douglas.aterrosystem.repository;

import br.com.douglas.aterrosystem.entity.Destinatario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DestinatarioRepository extends JpaRepository<Destinatario, Long> {
}
