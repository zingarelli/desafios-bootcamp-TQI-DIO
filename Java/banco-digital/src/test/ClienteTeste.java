package test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import main.Cliente;

public class ClienteTeste {
    
    //aprendendo teste unitário com JUnit
    @Test
    void validaConstrutor(){
        Cliente cliente = new Cliente("Matheus", "111222333-44");
        Assertions.assertEquals("Matheus", cliente.getNome());
        Assertions.assertEquals("111222333-44", cliente.getCpf());
    }

    @Test
    void validaConstrutorCompleto(){
        Cliente cliente = new Cliente("Matheus", "111222333-44", "Desenvolvedor", "912345678");
        Assertions.assertEquals("Matheus", cliente.getNome());
        Assertions.assertEquals("111222333-44", cliente.getCpf());
        Assertions.assertEquals("Desenvolvedor", cliente.getProfissao());
        Assertions.assertEquals("912345678", cliente.getTelefone());
    }
}
