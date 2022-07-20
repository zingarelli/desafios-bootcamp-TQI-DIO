package main;
public class ContaPoupanca extends Conta{
    public ContaPoupanca(Cliente cliente) {
        super(cliente);
    }

    @Override
    protected void getExtrato() {
        System.out.println("=== EXTRATO CONTA POUPANÇA ===");
        super.getExtrato();
    }

    //Conta Poupança não pode ficar negativa
    @Override
    public void transferir(double valor, Conta contaDestino) {
        if (valor > this.getSaldo()) {
            System.out.println("Saldo insuficiente em Conta Poupança!");
            //TODO: tratar com exceção
        } else {
            super.transferir(valor, contaDestino);
        }
    }

    @Override
    public void sacar(double valor) {
        if (valor > this.getSaldo()) {
            System.out.println("Saldo insuficiente em Conta Poupança!");
            //TODO: tratar com exceção
        } else {
            super.sacar(valor);
        }
    }
}
