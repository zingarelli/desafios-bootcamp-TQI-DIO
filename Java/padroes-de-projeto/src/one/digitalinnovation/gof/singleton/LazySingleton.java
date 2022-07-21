package one.digitalinnovation.gof.singleton;

/*
 * Singleton preguiçoso: é gerado internamente somente quando chamada pelo método público,
 * caso ainda não tenha sido criada
 */
public class LazySingleton{
    private static LazySingleton instance;

    //impedindo o singleton de ser instanciado fora de sua própria classe
    private LazySingleton(){
        super();
    }

    public static LazySingleton getInstance(){
        if(instance == null){
            instance = new LazySingleton();
        }
        return instance;
    }
}
