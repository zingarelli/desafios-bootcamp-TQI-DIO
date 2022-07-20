package main;
public class Cliente {
    private String nome;
    private String cpf;
    private String profissao;
    private String telefone;

    //assumindo que profissao e telefone são opcionais
    public Cliente(String nome, String cpf){
        this.nome = nome;
        this.cpf = cpf;
    }

    public Cliente(String nome,  String cpf, String profissao, String telefone) {
        this.nome = nome;
        this.cpf = cpf;
        this.profissao = profissao;
        this.telefone = telefone;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
