package one.digitalinnovation.gof.strategy;

/*
 * Padrão de Design Strategy: cria uma interface que será o "contrato", que então 
 * poderá ser implementada de diferentes formas (estratégias)
 */
public interface RobotBehavior {
    void move();
}
