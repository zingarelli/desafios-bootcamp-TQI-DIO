package one.digitalinnovation.gof.strategy;

public class AggressiveBehavior implements RobotBehavior{

    @Override
    public void move() {
        System.out.println("Robot is moving at full speed.");
    }    
}
