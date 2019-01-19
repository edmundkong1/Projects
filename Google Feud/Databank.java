import java.util.*;
import java.io.*;
public class Databank
{
    // instance variables
    private ArrayList<QandA> data = new ArrayList<QandA>();
    private int counter;
    private Random r = new Random();

    public Databank(String category)
    {
        String line;
        String [] temp = new String[2];
        String [] answerGroup = new String[10];
        String question = new String();
        int counter = 0;
        try 
        {
            BufferedReader brQues = new BufferedReader(new FileReader(category));
            while ((line = brQues.readLine())!= null) 
            {
                temp = line.split(",");
                //"trump is","bad"
                //temp = {"trump is", "bad");
                //take index 1
                answerGroup [counter] = temp[1];
                question = temp[0];
                counter ++;  
                if(counter == 10)
                {
                    data.add(new QandA(question, answerGroup));
                    counter = 0;
                }
            }
        }
        catch (Exception ex) 
        {
            ex.printStackTrace();
        }
        shuffle(data);
    }

    public QandA get()
    {
        QandA out = data.get(counter);
        counter++;
        return out;
    }

    public QandA get(int a)
    {
        return data.get(a);
    }

    public void swap (ArrayList<QandA> arr, int a, int b)
    {
        QandA hold = arr.get(a);
        arr.set(a, arr.get(b));
        arr.set(b, hold);
    }

    public void shuffle (ArrayList<QandA> arr)
    {
        int a, b;
        for (int x = 0; x < 100; x++)
        {
            a = r.nextInt(arr.size());
            b = r.nextInt(arr.size());
            swap(arr, a, b);
        }
    }
}