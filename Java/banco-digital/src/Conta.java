public class Conta {
    private static final int AGENCIA_PADRAO = 0001;
    private static int SEQUENCIAL = 1;

    private int agencia;
    private int numero; //numero da conta bancária
    private double saldo = 0;
    private Cliente titular;

    //uma conta sempre estará associada a um cliente
    public Conta(Cliente cliente){
        this.agencia = AGENCIA_PADRAO;
        this.numero = SEQUENCIAL++;
        this.titular = cliente;
    }

    public void sacar(double valor){
        saldo -= valor; //assumindo que a conta pode ficar negativa
    }

    public void depositar(double valor){
        saldo += valor;
    }

    public void transferir(double valor, Conta contaDestino){
        this.sacar(valor);
        contaDestino.depositar(valor);
    }

    protected void getExtrato(){
        System.out.println(String.format("Cliente: %s", titular.getNome()));
        System.out.println(String.format("Agência: %d", agencia));
        System.out.println(String.format("Número: %d", numero));
        System.out.println(String.format("Saldo: %.2f", saldo));
        System.out.println("-------------");
    }


    public int getAgencia() {
        return agencia;
    }

    public int getNumero() {
        return numero;
    }

    public double getSaldo() {
        return saldo;
    }

    public Cliente getTitular() {
        return titular;
    }
}