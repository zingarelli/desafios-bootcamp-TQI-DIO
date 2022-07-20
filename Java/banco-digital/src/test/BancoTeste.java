package test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import main.*;

public class BancoTeste {
    //Criação do Banco
    Banco banco = new Banco("Banco de Todos");

    //Criação dos clientes
    Cliente pessoa1 = new Cliente("Matheus", "123456978-9");
    Cliente pessoa2 = new Cliente("Sara", "968654931-6", "Professora", "16999888777");
    Cliente empresa1 = new Cliente("TQI Tecnologia", "129665874/0003-66"); 

    //Criação das contas
    Conta pessoa1_cc = new ContaCorrente(pessoa1);
    Conta pessoa2_cc = new ContaCorrente(pessoa2);
    Conta pessoa2_poup = new ContaPoupanca(pessoa2);
    Conta empresa1_cc = new ContaCorrente(empresa1);

    @Test
    void validaListasContasEClientes(){ 
        //Contas e clientes a serem testados
        List<Conta> contas = new ArrayList<>();
        Set<Cliente> clientes = new HashSet<>();

        //populando as listas do objeto da classe Banco
        banco.addConta(pessoa1_cc);
        banco.addConta(pessoa2_cc);
        banco.addConta(pessoa2_poup);
        banco.addConta(empresa1_cc);

        //inserindo nas listas que simulam as listas do objeto da classe Banco
        contas.add(pessoa1_cc);
        contas.add(pessoa2_cc);
        contas.add(pessoa2_poup);
        contas.add(empresa1_cc);

        clientes.add(pessoa1);
        clientes.add(pessoa2);
        clientes.add(empresa1);

        Assertions.assertEquals(contas, banco.getContas());
        Assertions.assertEquals(clientes, banco.getClientes());
    }

    @Test
    void validaInsercaoConta(){
        Assertions.assertFalse(banco.getContas().contains(pessoa1_cc));
        Assertions.assertFalse(banco.getClientes().contains(pessoa1));
        banco.addConta(pessoa1_cc);
        Assertions.assertTrue(banco.getContas().contains(pessoa1_cc));
        Assertions.assertTrue(banco.getClientes().contains(pessoa1));
    }
}
