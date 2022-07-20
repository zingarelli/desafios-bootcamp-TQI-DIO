package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import main.*;

//testando a geração automática de teste pelo VS Code
public class ContaTest {
    //Criação dos clientes
    Cliente pessoa1 = new Cliente("Matheus", "123456978-9");
    Cliente pessoa2 = new Cliente("Sara", "968654931-6", "Professora", "16999888777");

    //Criação das contas
    Conta pessoa1_cc = new ContaCorrente(pessoa1);
    Conta pessoa2_cc = new ContaCorrente(pessoa2);
    Conta pessoa2_poup = new ContaPoupanca(pessoa2);   

    @Test
    void testDepositar() {
        Assertions.assertEquals(0, pessoa1_cc.getSaldo());
        pessoa1_cc.depositar(100);
        // Assertions.assertTrue(pessoa1_cc.getSaldo() == 100);
        Assertions.assertEquals(100, pessoa1_cc.getSaldo());
    }

    @Test
    void testTransferir() {
        Assertions.assertEquals(0, pessoa1_cc.getSaldo());
        Assertions.assertEquals(0, pessoa2_cc.getSaldo());
        pessoa1_cc.transferir(10, pessoa2_cc);
        Assertions.assertEquals(-10, pessoa1_cc.getSaldo());
        Assertions.assertEquals(10, pessoa2_cc.getSaldo());
    }
    
    @Test
    void testSacarContaNormal() {
        Assertions.assertEquals(0, pessoa1_cc.getSaldo());
        pessoa1_cc.sacar(5000);
        Assertions.assertEquals(-5000, pessoa1_cc.getSaldo());
    }

    @Test
    void testSacarContaPoupanca(){
        //conta poupança não pode ficar negativa; saque não deve ser feito
        Assertions.assertEquals(0, pessoa2_poup.getSaldo());
        pessoa2_poup.sacar(1);
        Assertions.assertEquals(0, pessoa2_poup.getSaldo());
    }

    @Test
    void testTransferirContaPoupanca(){
        //conta poupança não pode ficar negativa; transferência não deve ser feita
        Assertions.assertEquals(0, pessoa1_cc.getSaldo());  
        Assertions.assertEquals(0, pessoa2_poup.getSaldo());
        pessoa2_poup.transferir(772, pessoa1_cc);
        Assertions.assertEquals(0, pessoa2_poup.getSaldo());        
        Assertions.assertEquals(0, pessoa1_cc.getSaldo());        
    }
}
