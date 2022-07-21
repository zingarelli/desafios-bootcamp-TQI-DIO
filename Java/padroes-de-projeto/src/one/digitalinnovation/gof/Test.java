package one.digitalinnovation.gof;

import one.digitalinnovation.gof.facade.Facade;
import one.digitalinnovation.gof.singleton.*;
import one.digitalinnovation.gof.strategy.*;

public class Test {
    public static void main(String[] args) {
        /*
        * Testes do padrão de design Singleton - 3 abordagens
        * as variáveis apontarão para um mesmo endereço de memória,
        * indicando que fazem referência sempre a uma mesma instância
        */
        LazySingleton firstLazy = LazySingleton.getInstance();
        System.out.println(firstLazy);
        LazySingleton secondLazy = LazySingleton.getInstance();
        System.out.println(secondLazy);

        EagerSingleton firstEager = EagerSingleton.getInstance();
        System.out.println(firstEager);
        EagerSingleton secondEager = EagerSingleton.getInstance();
        System.out.println(secondEager); 

        LazyHolderSingleton firstLazyHolder = LazyHolderSingleton.getInstance();
        System.out.println(firstLazyHolder);
        LazyHolderSingleton secondLazyHolder = LazyHolderSingleton.getInstance();
        System.out.println(secondLazyHolder); 

        /*
         * Testes do padrão de design Strategy
         */
        NormalBehavior normal = new NormalBehavior();
        DefensiveBehavior defensive = new DefensiveBehavior();
        AggressiveBehavior aggressive = new AggressiveBehavior();

        Robot robot = new Robot();
        robot.setBehavior(normal);
        robot.move();
        robot.setBehavior(defensive);
        robot.move();
        robot.setBehavior(aggressive);
        robot.move();

        /*
         * Teste do padrão de design Facade
         */
        Facade facade = new Facade();
        facade.changeCustomerZipCode("Matheus", "8693556");
    }
}
