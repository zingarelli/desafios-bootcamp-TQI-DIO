package one.digitalinnovation.gof.strategy;

public class DefensiveBehavior implements RobotBehavior{

    @Override
    public void move() {
        System.out.println("Robot is moving carefully.");
    }    
}
