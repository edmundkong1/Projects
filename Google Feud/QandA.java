public class QandA
{
    private String question;
    private String [] answer = new String[10];

    public QandA(String question, String[] answer)
    {
        this.question = question;
        for (int i = 0; i<10; i++)
            this.answer[i] = answer[i];
    }

    public int check (String guess)
    {
        int correct = -1;
        guess.replaceAll(" ", "");
        for(int i = 0; i<10; i++)
        {
            answer[i].replaceAll(" ","");
            if(answer[i].equalsIgnoreCase(guess))
                correct = i;
        }
        return correct;
    }

    public String getQuestion()
    {
        return question;
    }

    public String answer(int num)
    {
        return answer[num];
    }
}