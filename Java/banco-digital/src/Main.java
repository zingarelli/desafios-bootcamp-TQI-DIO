import org.w3c.dom.ls.LSOutput;

import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        //Criação do Banco
        Banco banco = new Banco("Banco de Todos");

        //Criação dos clientes
        Cliente pessoa1 = new Cliente("Matheus", "123456978-9");
        Cliente pessoa2 = new Cliente("Sara", "968654931-6", "Professora", "16999888777");
        Cliente empresa1 = new Cliente("TQI Tecnologia", "129665874/0003-66"); //TODO: criar uma superclasse cliente e fazer pessoa física e jurídica

        //Criação das contas
        Conta pessoa1_cc = new ContaCorrente(pessoa1);
        Conta pessoa2_cc = new ContaCorrente(pessoa2);
        Conta pessoa2_poup = new ContaPoupanca(pessoa2);
        Conta empresa1_cc = new ContaCorrente(empresa1);

        //Atrelando clientes e contas ao banco
        banco.addConta(pessoa1_cc);
        banco.addConta(pessoa2_cc);
        banco.addConta(pessoa2_poup);
        banco.addConta(empresa1_cc);

        //teste com listas do banco
        Set<Cliente> clientes = banco.getClientes();
        List<Conta> contas = banco.getContas();
        imprimeClientes(clientes);
        imprimeContas(contas);

        /* Testes com operações bancárias */

        //Depósito
        empresa1_cc.depositar(10000);
        empresa1_cc.getExtrato();

        //Transferências
        empresa1_cc.transferir(3000, pessoa1_cc);
        empresa1_cc.transferir(5000, pessoa2_cc);
        empresa1_cc.getExtrato();
        pessoa1_cc.getExtrato();
        pessoa2_cc.getExtrato();

        //Saldo negativo em poupança
        pessoa2_poup.getExtrato();
        pessoa2_poup.sacar(1000);
        pessoa2_poup.transferir(1000, pessoa2_cc);
        pessoa2_poup.getExtrato();
        pessoa2_cc.transferir(4500, pessoa2_poup);
        pessoa2_poup.sacar(1000);
        pessoa2_cc.getExtrato();
        pessoa2_poup.getExtrato();

        //Saldo negativo em conta corrente
        pessoa1_cc.transferir(800, pessoa2_cc);
        pessoa1_cc.sacar(2500);
        pessoa1_cc.getExtrato();
        pessoa2_cc.getExtrato();
    }

    public static void imprimeClientes(Set<Cliente> clientes){
        System.out.println("=== CLIENTES CADASTRADOS ===");
        for (Cliente cliente: clientes) {
            String profissao = cliente.getProfissao();
            String telefone = cliente.getTelefone();

            System.out.println(cliente.getNome());
            System.out.println(cliente.getCpf());
            if (profissao != null) System.out.println(profissao);
            if (telefone != null) System.out.println(telefone);
            System.out.println("-----------");
        }
    }

    public static void imprimeContas(List<Conta> contas){
        System.out.println("=== CONTAS CADASTRADAS ===");
        for (Conta conta : contas) {
            System.out.println(conta.getTitular().getNome());
            System.out.println(String.format("Agência: %d", conta.getAgencia()));
            System.out.println(String.format("Conta: %d", conta.getNumero()));
            System.out.println("-----------");
        }
    }
}
