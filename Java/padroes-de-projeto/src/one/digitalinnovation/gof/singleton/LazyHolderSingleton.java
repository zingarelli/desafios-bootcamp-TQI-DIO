package one.digitalinnovation.gof.singleton;

/*
 * Singleton preguiçoso "thread safe": é gerado dentro de uma classe estática interna,
 * a qual faz de fato a construção do singleton
 */
public class LazyHolderSingleton{
    private static class InstanceHolder{
        public static LazyHolderSingleton instance = new LazyHolderSingleton();
    }

    //impedindo o singleton de ser instanciado fora de sua própria classe
    private LazyHolderSingleton(){
        super();
    }

    public static LazyHolderSingleton getInstance(){
        return InstanceHolder.instance;
    }
}
