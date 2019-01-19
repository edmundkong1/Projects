public class Environment implements Display
{
    // instance variables
    private int type;
    private int counter;
    private int lifespan;

    public Environment(int type, int lifespan)
    {
        // initialise instance variables
        this.type = type;
        this.lifespan = lifespan;
        counter = 0;
    }

    public int getType()
    {
        return type;
    }
    
    public void iterate()
    {
        counter++;
        if (counter > lifespan)
        {
            type = 0;
        }
    }
}
