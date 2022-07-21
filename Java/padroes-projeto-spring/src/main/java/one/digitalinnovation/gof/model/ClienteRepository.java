package one.digitalinnovation.gof.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//Código copiado do repositório do instrutor do desafio: Venilton FalvoJr
//interface que irá prover todos os métodos para a entity Cliente
//CrudRepository é um design pattern Strategy
//annotation @Repository é redundante, mas foi mantido por questões de aprendizado
@Repository
public interface ClienteRepository extends CrudRepository<Cliente, Long> {

}