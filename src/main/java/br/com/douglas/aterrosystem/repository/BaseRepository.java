package br.com.douglas.aterrosystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<R> extends JpaRepository<R, Long> {


}
