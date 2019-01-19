import java.util.Random;
import java.awt.*;
import java.awt.Component;
import java.awt.Container;
import javax.swing.BoxLayout;
import java.awt.Color; 
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.*;
import java.awt.event.*;  // Needed for ActionListener
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

class Google_Feud_GUI extends JFrame
{
    //======================================= instance variables
    private JButton cultureBtn = new JButton ("Culture"); //Create button for culture category
    private JButton peopleBtn = new JButton ("People"); //Create button for people category
    private JButton namesBtn = new JButton ("Names"); //Create button for names category
    private JButton questionsBtn = new JButton ("Questions"); //Create button for question category
    private JButton nextBtn; 

    private Databank culture = new Databank("culture.csv");     
    private Databank names = new Databank("names.csv");      
    private Databank questions = new Databank("questions.csv");      
    private Databank people = new Databank("people.csv");
    private JTextField inpTF = new JTextField (30);
    private JLabel inpPrompt = new JLabel ();
    private Random r = new Random(); //Create a Random object

    private JPanel ans1 = new JPanel(new CardLayout());
    private JPanel ans2 = new JPanel(new CardLayout());
    private JPanel ans3 = new JPanel(new CardLayout());
    private JPanel ans4 = new JPanel(new CardLayout());
    private JPanel ans5 = new JPanel(new CardLayout());
    private JPanel ans6 = new JPanel(new CardLayout());
    private JPanel ans7 = new JPanel(new CardLayout());
    private JPanel ans8 = new JPanel(new CardLayout());
    private JPanel ans9 = new JPanel(new CardLayout());
    private JPanel ans10 = new JPanel(new CardLayout());

    private JPanel grid = new JPanel();

    private JPanel score = new JPanel();  
    private JLabel round = new JLabel();
    private JLabel guess = new JLabel();
    private JLabel totalScore = new JLabel();
    private JLabel thisRound = new JLabel();

    private JPanel display = new JPanel (); 
    private JPanel topics = new JPanel (); // Create a topics panel
    private JPanel inp = new JPanel();
    private JPanel nest1 = new JPanel(new CardLayout ()); // Use CardLayout for prompts nest2
    private JPanel nest2 = new JPanel(new CardLayout ()); // Use CardLayout for Testfield and Buttons nest2
    private JPanel nest3 = new JPanel();

    private int guesses = 3;
    private int roundnum = 1;
    private int totalpoints = 0;
    private int newpoints = 0;

    private QandA qa;
    private JButton xDisplay;
    private Timer t;
    private JButton searchbutton;

    private boolean[] guessed = new boolean[10];

    static Google_Feud_GUI window; //Make the Google_Feud_GUI statid, and accessible throughout the package
    //======================================================== constructor
    public Google_Feud_GUI()
    {
        // all actionslistener classes 
        cultureBtn.addActionListener (new ConvertCultureBtnListener ()); // Connect button to culture listener class
        peopleBtn.addActionListener (new ConvertPeopleBtnListener ()); // Connect button to people listener class
        namesBtn.addActionListener (new ConvertNamesBtnListener ()); // Connect button to names listener class
        questionsBtn.addActionListener (new ConvertQuestionsBtnListener ()); // Connect button to questions listener class

        // declaring all variables 
        ImageIcon logo = new ImageIcon ("Google Feud.png"); 
        JLabel logolabel = new JLabel (logo);
        logolabel.setAlignmentX(Component.CENTER_ALIGNMENT); 

        ImageIcon numbers = new ImageIcon ("Numbers.png"); 
        JLabel numberslabel = new JLabel (numbers); 
        numberslabel.setAlignmentX(Component.CENTER_ALIGNMENT); 

        JLabel prompt = new JLabel ("CHOOSE A CATEGORY", SwingConstants.CENTER);
        prompt.setFont(prompt.getFont().deriveFont(Font.BOLD, 20f));

        JLabel prompt2 = new JLabel ("HOW DOES GOOGLE AUTOCOMPLETE THIS QUERY?", SwingConstants.CENTER);
        prompt2.setFont(prompt.getFont().deriveFont(20f)); 

        ImageIcon search = new ImageIcon ("Search.png"); 
        searchbutton = new JButton (search);
        searchbutton.addActionListener (new ConvertSearchBtnListener ());

        ImageIcon nextimage = new ImageIcon("Next.png"); 
        nextBtn = new JButton (nextimage); 
        nextBtn.addActionListener (new ConvertNextRoundBtn ()); 

        ImageIcon x = new ImageIcon(loadX());
        xDisplay = new JButton(x);
        xDisplay.setBorder(null);

        JPanel title = new JPanel (); // Create a title panel
        JPanel answers = new JPanel (); // Create an answers panel

        display.setLayout(new BoxLayout (display, BoxLayout.PAGE_AXIS));
        display.setPreferredSize(new Dimension(1000, 675)); 

        //adding buttons to the topics panel 
        topics.setLayout(new BoxLayout (topics, BoxLayout.X_AXIS)); 
        topics.setBackground(Color.WHITE); 

        topics.add (Box.createHorizontalGlue()); 
        cultureBtn.setPreferredSize(new Dimension(200,200)); 
        topics.add (cultureBtn);            // Add culture button

        topics.add (Box.createHorizontalGlue()); // Add gap
        peopleBtn.setPreferredSize(new Dimension(200,200)); 
        topics.add (peopleBtn);             // Add people button

        topics.add (Box.createHorizontalGlue());
        namesBtn.setPreferredSize(new Dimension(200,200)); 
        topics.add (namesBtn);              // Add names button
        topics.add (Box.createHorizontalGlue()); // Add gap
        questionsBtn.setPreferredSize(new Dimension(200,200)); 
        topics.add (questionsBtn);          // Add questions button
        topics.add (Box.createHorizontalGlue());        

        inpTF.addActionListener(new ConvertSearchBtnListener());
        inp.add (inpPrompt);
        inp.add (inpTF);
        inp.add (searchbutton);
        updateScore(0,0,0,0);
        inp.setBackground(Color.WHITE);

        //nest2 buttons with input textfield
        nest1.add(prompt);
        nest1.add(prompt2);
        nest1.setAlignmentX(Component.CENTER_ALIGNMENT);
        nest1.setBackground(Color.WHITE);
        nest2.add(topics);
        nest2.add(inp);
        nest2.setPreferredSize(new Dimension(40,80));

        // formatting and adding labels to the score panel
        score.setLayout(new BoxLayout(score, BoxLayout.X_AXIS));
        score.setBackground(Color.WHITE); 
        score.setAlignmentX(Component.CENTER_ALIGNMENT); 

        score.setBackground(Color.WHITE); 

        score.add(Box.createRigidArea(new Dimension(80,0)));
        score.add(round);
        score.add(guess);
        score.add(totalScore);
        score.add(thisRound);
        score.setAlignmentX(Component.CENTER_ALIGNMENT);

        // adding everything to display panel (window)
        display.setBackground(Color.WHITE);
        display.add (logolabel); 
        display.add (nest1);
        display.add (nest2);
        display.add (Box.createVerticalStrut(10)); 

        initializeTable();

        grid.add(ans1);
        grid.add(ans6);
        grid.add(ans2);
        grid.add(ans7);
        grid.add(ans3);
        grid.add(ans8);
        grid.add(ans4);
        grid.add(ans9);
        grid.add(ans5);
        grid.add(ans10);

        grid.setLayout(new GridLayout(5,2)); 
        grid.setBackground(Color.WHITE); 

        display.add(grid); 

        display.add(Box.createVerticalStrut(30));
        display.add(score); 

        //adding display to JFrame
        display.setLayout (new BoxLayout (display, BoxLayout.Y_AXIS));
        display.setBackground(Color.WHITE); 
        add(display);
        setBackground(Color.WHITE);

        //setting window's attributes 
        setSize(1440,850);
        setResizable(false);
        pack(); 
        setTitle ("Google Feud");
        setLayout(new FlowLayout()); 
        setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo (null);           // Center window
    }

    public void updateScore(int round1, int guess1, int totalScore1, int thisRound1)
    {
        score.setLayout(new BoxLayout(score, BoxLayout.X_AXIS));
        Color googleBlue = new Color(66,140,244);
        Font f = new Font("Helvetica", Font.BOLD, 30);

        round.setText("<html>ROUND<br>" + "<html><div style='text-align: center;'>" + round1);
        round.setFont(f);
        round.setForeground(googleBlue);

        guess.setText("<html>GUESS<br>" + "<html><div style='text-align: center;'>"+guess1);
        guess.setFont(f);
        guess.setForeground(googleBlue);

        totalScore.setText("<html>TOTAL SCORE<br>" +"<html><div style='text-align: center;'>"+ totalScore1);
        totalScore.setFont(f);
        totalScore.setForeground(googleBlue);

        thisRound.setText("<html>THIS ROUND<br>" + "<html><div style='text-align: center;'>"+thisRound1);
        thisRound.setFont(f);
        thisRound.setForeground(googleBlue);

    }

    public static BufferedImage loadX()
    {
        BufferedImage img = null;
        try
        {
            img = ImageIO.read(new File("x.png"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return img;
    }

    class ConvertCultureBtnListener implements ActionListener // Inner class for handling events for CultureBtn
    {
        public void actionPerformed (ActionEvent e)
        {
            CardLayout cl = (CardLayout)(nest1.getLayout());
            cl.next(nest1);
            CardLayout c2 = (CardLayout)(nest2.getLayout());
            c2.next(nest2);
            question(1);
        }
    }

    class ConvertPeopleBtnListener implements ActionListener // Inner class for handling events for PeopleBtn
    {
        public void actionPerformed (ActionEvent e)
        {
            CardLayout cl = (CardLayout)(nest1.getLayout());
            cl.next(nest1);
            CardLayout c2 = (CardLayout)(nest2.getLayout());
            c2.next(nest2);
            question(2);
        }
    }

    class ConvertNamesBtnListener implements ActionListener // Inner class for handling events for NamesBtn
    {
        public void actionPerformed (ActionEvent e)
        {
            CardLayout cl = (CardLayout)(nest1.getLayout());
            cl.next(nest1);
            CardLayout c2 = (CardLayout)(nest2.getLayout());
            c2.next(nest2);
            question(3);
        }
    }

    class ConvertQuestionsBtnListener implements ActionListener // Inner class for handling events for QuestionsBtn
    {
        public void actionPerformed (ActionEvent e)
        {
            CardLayout cl = (CardLayout)(nest1.getLayout());
            cl.next(nest1);
            CardLayout c2 = (CardLayout)(nest2.getLayout());
            c2.next(nest2);
            question(4);
        }
    }

    class ConvertSearchBtnListener implements ActionListener
    {
        public void actionPerformed (ActionEvent evt)
        {
            if (evt.getSource() == searchbutton || evt.getSource() == inpTF)
            {
                int index = qa.check(inpTF.getText());
                inpTF.setText("");
                if (index == -1 || guessed[index] == true)
                {
                    t = new Timer(1000,this);
                    t.setRepeats(false);
                    inp.add (xDisplay);
                    revalidate();
                    repaint();
                    t.start();
                }
                else
                {
                    guessed[index] = true;
                    showAns(index);
                    newpoints += (10-index)*1000;
                    totalpoints += (10-index)*1000;
                    if(newpoints >= 55000)
                    {
                        inp.remove(searchbutton); 
                        inp.add(nextBtn);
                        inpTF.disable();
                        inpTF.setEditable(false);
                        showAll();
                        revalidate();
                        repaint();
                    }
                }
            }
            else
            {
                if(guesses > 0)
                {
                    guesses--;
                    inp.remove(xDisplay);
                    revalidate();
                    repaint();
                    if(guesses <= 0)
                    {
                        inp.remove(searchbutton); 
                        inp.add(nextBtn);
                        inpTF.disable();
                        inpTF.setEditable(false); 
                        showAll();
                        revalidate();
                        repaint();
                    }
                }
            }
            updateScore(roundnum, guesses, totalpoints, newpoints);
            display.add(score);
        }
    }

    class ConvertNextRoundBtn implements ActionListener
    {
        public void actionPerformed (ActionEvent e) 
        {
            guessed = new boolean [10];
            inpTF.enable();
            cleanup();
            initializeTable();
            guesses = 3;
            newpoints = 0;
            roundnum++;

            inp.remove(nextBtn);
            inp.add(searchbutton); 
            inpTF.setEditable(true); 

            CardLayout cl = (CardLayout)(nest1.getLayout());
            cl.next(nest1);
            CardLayout c2 = (CardLayout)(nest2.getLayout());
            c2.next(nest2);
            revalidate();
            repaint();

            updateScore(roundnum, guesses, totalpoints, newpoints);
            display.add(score);
        }
    }
    //======================================================== method main
    public static void main (String[] args)
    {
        window = new Google_Feud_GUI ();
        window.setVisible (true);
    }

    private void question (int ctg)
    {
        qa = null;
        switch(ctg)
        {
            case 1:
            qa = culture.get();
            break;
            case 2:
            qa = people.get();
            break;
            case 3:
            qa = names.get();
            break;
            case 4:
            qa = questions.get();
            break;
        }
        inpPrompt.setText(qa.getQuestion());
    }

    private void showAns(int index)
    {
        Font f = new Font("Helvetica", Font.BOLD, 24);
        switch(index)
        {
            case 0:
            JLabel ans1Label = new JLabel(qa.answer(0), SwingConstants.CENTER);
            ans1Label.setFont(f);
            ans1.add(ans1Label);
            CardLayout cl = (CardLayout)(ans1.getLayout());
            cl.next(ans1);
            break;
            case 1:
            JLabel ans2Label = new JLabel(qa.answer(1), SwingConstants.CENTER);
            ans2Label.setFont(f);
            ans2.add(ans2Label);
            CardLayout c2 = (CardLayout)(ans2.getLayout());
            c2.next(ans2);
            break;
            case 2:
            JLabel ans3Label = new JLabel(qa.answer(2), SwingConstants.CENTER);
            ans3Label.setFont(f);
            ans3.add(ans3Label);
            CardLayout c3 = (CardLayout)(ans3.getLayout());
            c3.next(ans3);
            break;
            case 3:
            JLabel ans4Label = new JLabel(qa.answer(3), SwingConstants.CENTER);
            ans4Label.setFont(f);
            ans4.add(ans4Label);
            CardLayout c4 = (CardLayout)(ans4.getLayout());
            c4.next(ans4);
            break;
            case 4:
            JLabel ans5Label = new JLabel(qa.answer(4), SwingConstants.CENTER);
            ans5Label.setFont(f);
            ans5.add(ans5Label);
            CardLayout c5 = (CardLayout)(ans5.getLayout());
            c5.next(ans5);
            break;
            case 5:
            JLabel ans6Label = new JLabel(qa.answer(5), SwingConstants.CENTER);
            ans6Label.setFont(f);
            ans6.add(ans6Label);
            CardLayout c6 = (CardLayout)(ans6.getLayout());
            c6.next(ans6);
            break;
            case 6:
            JLabel ans7Label = new JLabel(qa.answer(6), SwingConstants.CENTER);
            ans7Label.setFont(f);
            ans7.add(ans7Label);
            CardLayout c7 = (CardLayout)(ans7.getLayout());
            c7.next(ans7);
            break;
            case 7:
            JLabel ans8Label = new JLabel(qa.answer(7), SwingConstants.CENTER);
            ans8Label.setFont(f);
            ans8.add(ans8Label);
            CardLayout c8 = (CardLayout)(ans8.getLayout());
            c8.next(ans8);
            break;
            case 8:
            JLabel ans9Label = new JLabel(qa.answer(8), SwingConstants.CENTER);
            ans9Label.setFont(f);
            ans9.add(ans9Label);
            CardLayout c9 = (CardLayout)(ans9.getLayout());
            c9.next(ans9);
            break;
            case 9:
            JLabel ans10Label = new JLabel(qa.answer(9), SwingConstants.CENTER);
            ans10Label.setFont(f);
            ans10.add(ans10Label);
            CardLayout cl0 = (CardLayout)(ans10.getLayout());
            cl0.next(ans10);
            break;
        }
    }

    private void showAll()
    {
        Font f = new Font("Helvetica", Font.BOLD, 24);

        JLabel ans1Label = new JLabel(qa.answer(0), SwingConstants.CENTER);
        ans1Label.setFont(f);
        ans1.add(ans1Label);
        CardLayout cl = (CardLayout)(ans1.getLayout());
        cl.next(ans1);

        JLabel ans2Label = new JLabel(qa.answer(1), SwingConstants.CENTER);
        ans2Label.setFont(f);
        ans2.add(ans2Label);
        CardLayout c2 = (CardLayout)(ans2.getLayout());
        c2.next(ans2);

        JLabel ans3Label = new JLabel(qa.answer(2), SwingConstants.CENTER);
        ans3Label.setFont(f);
        ans3.add(ans3Label);
        CardLayout c3 = (CardLayout)(ans3.getLayout());
        c3.next(ans3);

        JLabel ans4Label = new JLabel(qa.answer(3), SwingConstants.CENTER);
        ans4Label.setFont(f);
        ans4.add(ans4Label);
        CardLayout c4 = (CardLayout)(ans4.getLayout());
        c4.next(ans4);

        JLabel ans5Label = new JLabel(qa.answer(4), SwingConstants.CENTER);
        ans5Label.setFont(f);
        ans5.add(ans5Label);
        CardLayout c5 = (CardLayout)(ans5.getLayout());
        c5.next(ans5);

        JLabel ans6Label = new JLabel(qa.answer(5), SwingConstants.CENTER);
        ans6Label.setFont(f);
        ans6.add(ans6Label);
        CardLayout c6 = (CardLayout)(ans6.getLayout());
        c6.next(ans6);

        JLabel ans7Label = new JLabel(qa.answer(6), SwingConstants.CENTER);
        ans7Label.setFont(f);
        ans7.add(ans7Label);
        CardLayout c7 = (CardLayout)(ans7.getLayout());
        c7.next(ans7);

        JLabel ans8Label = new JLabel(qa.answer(7), SwingConstants.CENTER);
        ans8Label.setFont(f);
        ans8.add(ans8Label);
        CardLayout c8 = (CardLayout)(ans8.getLayout());
        c8.next(ans8);

        JLabel ans9Label = new JLabel(qa.answer(8), SwingConstants.CENTER);
        ans9Label.setFont(f);
        ans9.add(ans9Label);
        CardLayout c9 = (CardLayout)(ans9.getLayout());
        c9.next(ans9);

        JLabel ans10Label = new JLabel(qa.answer(9), SwingConstants.CENTER);
        ans10Label.setFont(f);
        ans10.add(ans10Label);
        CardLayout cl0 = (CardLayout)(ans10.getLayout());
        cl0.next(ans10);
    }

    private void cleanup ()
    {
        ans1.removeAll();
        ans2.removeAll();
        ans3.removeAll();
        ans4.removeAll();
        ans5.removeAll();
        ans6.removeAll();
        ans7.removeAll();
        ans8.removeAll();
        ans9.removeAll();
        ans10.removeAll();
    }

    private void initializeTable ()
    {
        ans1.add(new JLabel(new ImageIcon("1.png"))); 
        ans1.setBackground(Color.WHITE);
        ans6.add(new JLabel(new ImageIcon("6.png")));
        ans6.setBackground(Color.WHITE);
        ans2.add(new JLabel(new ImageIcon("2.png")));
        ans2.setBackground(Color.WHITE);
        ans7.add(new JLabel(new ImageIcon("7.png")));
        ans7.setBackground(Color.WHITE);
        ans3.add(new JLabel(new ImageIcon("3.png")));
        ans3.setBackground(Color.WHITE);
        ans8.add(new JLabel(new ImageIcon("8.png")));
        ans8.setBackground(Color.WHITE);
        ans4.add(new JLabel(new ImageIcon("4.png"))); 
        ans4.setBackground(Color.WHITE);
        ans9.add(new JLabel(new ImageIcon("9.png")));   
        ans9.setBackground(Color.WHITE);
        ans5.add(new JLabel(new ImageIcon("5.png"))); 
        ans5.setBackground(Color.WHITE);
        ans10.add(new JLabel(new ImageIcon("10.png"))); 
        ans10.setBackground(Color.WHITE);
    }

    private int getRandom (int low, int high) // Method that returns random interger from low to high and works for all cases
    {
        return (r.nextInt(high - low + 1) + low); // Pages read = last page - pages not read, lowest number is 0 + low
    }
}