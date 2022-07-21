package one.digitalinnovation.gof.strategy;

public class NormalBehavior implements RobotBehavior{

    @Override
    public void move() {
        System.out.println("Robot is moving at a normal pace.");
    }    
}
