package main;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Banco {
    private String nome;
    private List<Conta> contas;
    private Set<Cliente> clientes;

    public Banco(String nome){
        this.nome = nome;
        contas = new ArrayList<>();
        clientes = new HashSet<>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Conta> getContas() {
        return contas;
    }

    public Set<Cliente> getClientes() {
        return clientes;
    }

    public void addConta(Conta conta){
        this.contas.add(conta);
        this.clientes.add(conta.getTitular());
    }
}
