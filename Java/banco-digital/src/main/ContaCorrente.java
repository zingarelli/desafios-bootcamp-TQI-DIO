package main;
public class ContaCorrente extends Conta{
    public ContaCorrente(Cliente cliente){
        super(cliente);
    }

    @Override
    protected void getExtrato() {
        System.out.println("=== EXTRATO CONTA CORRENTE ===");
        super.getExtrato();
    }
}
