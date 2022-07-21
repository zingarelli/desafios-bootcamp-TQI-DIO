package one.digitalinnovation.gof.strategy;

public class Robot {
    private RobotBehavior behavior;

    public void setBehavior(RobotBehavior behavior) {
        this.behavior = behavior;
    }

    public void move(){
        behavior.move(); //irá se mover de acordo com o comportamento a ser setado
    }
}
