package one.digitalinnovation.gof.singleton;

/*
 * Singleton ansioso: instância já é criada antes mesmo de ser chamada,
 * ficando disponível logo que outra classe fizer a chamada pelo método público.
 */
public class EagerSingleton{
    private static EagerSingleton instance = new EagerSingleton();

    private EagerSingleton(){
        super();
    }

    public static EagerSingleton getInstance(){
        return instance;
    }
}
