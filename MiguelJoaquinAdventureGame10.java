
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.geom.RoundRectangle2D;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
 
/****************************************************************
 * class with the main method and "instantiates" the 
 *       JFrame with all components.
 ****************************************************************/   
public class MiguelJoaquinAdventureGame10
{ 
   static final long serialVersionUID = 1;
   
   public static void main(String args[]) throws Exception 
   { 
      AdventureGame frame = new AdventureGame();
      frame.setLocationRelativeTo(null);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.beginGame();
   }// end main method
}// end class 
  
 
  
  
/****************************************************************
 * Graphics Adventure Game Class
 ****************************************************************/    
class AdventureGame extends JFrame implements ActionListener
{  
   static final long serialVersionUID = 1;

   BufferedImage buffer;   // instance variable for double buffering
   final int WIDTH = 1250, HEIGHT = 700;   //to fit with resolution of monitor
   int picWidth;

   // Set up fonts for the picture area
   Font chillerFont = new Font("Chiller", Font.PLAIN, 48); 
   Font smallArialFont = new Font("Arial", Font.BOLD, 20); 
   Font largeArialFont = new Font("Arial", Font.BOLD, 40); 
   
   // GUI components that will need to be accessed from many methods
   JPanel interactionPanel;;
   String[] choices;
   JComboBox<String> choiceComboBox;
   JTextArea storyTextArea, inventoryTextArea;
   JPanel picturePanel;
   JComboBox choice;
   
   //arrays go here
   String[] collectables =  {"House Keys","crowbar", "glass bottle"};
   boolean[] haveCollected = {false, false, false};
   boolean[] events = {false, false};
   
   ///////////////////// END FIELDS ////////////////////////////
   
      
     
   /*************************************************************************
   *     Constructor                                                        *
   *     Builds the main window and all of the components that we see.      *
   *************************************************************************/ 
   public AdventureGame()
   {
      super ("This Mind of Lies");
      int interactionPanelWidth = 400;
      picWidth = WIDTH-interactionPanelWidth;
      buffer = new BufferedImage(picWidth-5,HEIGHT+15,BufferedImage.TYPE_INT_RGB);
      
      picturePanel = new JPanel();
      picturePanel.setDoubleBuffered(true);
            
   
      interactionPanel = new JPanel();
      interactionPanel.setLayout(new BorderLayout(5,10));
      interactionPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
      
      
      JPanel choicePanel = new JPanel();  
      Border blackline = BorderFactory.createLineBorder(Color.black);
      TitledBorder choiceTitle = BorderFactory.createTitledBorder(blackline, "What do you want to do? ");
      choicePanel.setBorder(choiceTitle);    
      String[] tempStrArray = {"Start the adventure.", "Quit."};
      choices = tempStrArray;

        
                     
                  
      choiceComboBox = new JComboBox<>(choices);
      choiceComboBox.setFont(new Font("Serif", Font.PLAIN, 20));
      choiceComboBox.setEditable(false);
      choiceComboBox.setPreferredSize(new Dimension(interactionPanelWidth-40, 30));                        
      choicePanel.add(choiceComboBox); 
      //choiceComboBox.addActionListener(new ActionListener());  
      choiceComboBox.addActionListener(this);
      //ActionListener choiceComboListener = choiceComboBox.getActionListener();
      
       
      
      storyTextArea = new JTextArea();
      storyTextArea.setBackground(new Color(119,247,200));
      storyTextArea.setMargin( new Insets(10,10,10,10) );
      storyTextArea.setFont(new Font("Serif", Font.PLAIN, 20));
      storyTextArea.setLineWrap(true);
      storyTextArea.setWrapStyleWord(true);
      storyTextArea.setEditable(false);
      storyTextArea.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5,5,5,5),BorderFactory.createRaisedBevelBorder()));      
      
      inventoryTextArea = new JTextArea();
      inventoryTextArea.setMargin( new Insets(10,10,10,10) );
      inventoryTextArea.setFont(new Font("Serif", Font.PLAIN, 20));
      inventoryTextArea.setLineWrap(true);
      inventoryTextArea.setWrapStyleWord(true);
      inventoryTextArea.setEditable(false);
      TitledBorder inventoryTitle = BorderFactory.createTitledBorder(blackline, " Inventory ");
      inventoryTextArea.setBorder(inventoryTitle);
      inventoryTextArea.setMinimumSize(new Dimension(interactionPanelWidth-40,80));
      setInventory();
      
      interactionPanel.add(choicePanel, BorderLayout.PAGE_START);
      interactionPanel.add(storyTextArea, BorderLayout.CENTER);
      interactionPanel.add(inventoryTextArea, BorderLayout.PAGE_END);
      
      
      picturePanel.setPreferredSize(new Dimension(WIDTH-interactionPanelWidth,HEIGHT));
      picturePanel.setMinimumSize(new Dimension(WIDTH-interactionPanelWidth,HEIGHT));
      picturePanel.setBorder(BorderFactory.createLineBorder(Color.black));
      
      interactionPanel.setPreferredSize(new Dimension(interactionPanelWidth,HEIGHT));
      interactionPanel.setMinimumSize(new Dimension(interactionPanelWidth,HEIGHT));
      interactionPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10,10,10,10),BorderFactory.createLoweredBevelBorder()));
      
      JPanel pane = (JPanel) getContentPane();
      pane.setLayout(new BoxLayout(pane, BoxLayout.LINE_AXIS));
      pane.add(picturePanel);
      pane.add(interactionPanel);          
                                    
      setSize(WIDTH, HEIGHT);
      pack();
      setLocationRelativeTo(null);
      setVisible(true);
   }
   
   
   public void drawScreen()
   {
      Graphics2D g = (Graphics2D)this.getGraphics();
      g.drawImage(buffer,10,10,this);
      Toolkit.getDefaultToolkit().sync();
      g.dispose();
      
   }
   
   
   public void paint(Graphics g)
   {
      try
      {
         Thread.sleep(1000);
         drawScreen();
         interactionPanel.repaint();
      }
      catch(Exception e)
      {
         System.out.println("Problem painting the screen:\n");
         e.printStackTrace();
      }
   }
   
   
   
   
   
   public void drawIntroPicture(Graphics2D b) //<-- layer by order
   {
   
      //triangle(b,Color.cyan,500,100,200,100,40,90);
     // write(b, Color.green, "Arial", Font.ITALIC, 100, "Bool", 200, 400);
      
      
      //oval(b,Color.blue(255,200,188),0, 130,550, 175,400, 220,500); <-- Doesn't Work
      oval(b,Color.blue,550, 175,400, 220,500);
   
      
      //arc(b,Color.white, 300, 300, 160, 160, 0, 180, 50);
      //rectangle(b,Color.blue,2,22,picWidth-10,HEIGHT-10,5);
      //rectangle(b,Color.yellow,100,100,200,100,0);
      rectangle(b,new Color(143, 227, 255),0,0,900,900,0);
      rectangle(b,new Color(124, 214, 148),0,550,900,100,200);   
      triangle(b,new Color(168, 173, 181),90,   130,550, 175,400, 220,550);
      triangle(b,new Color(105, 105, 105),90,   160+20,580+20, 205+20,430+20, 250+20,580+20);
      triangle(b,new Color(74, 74, 74),90,   230,650, 275,500, 320,650);
      oval(b,new Color(255, 229, 61),570, 50,220, 220,-2);
      addPicture(b, "main.jpg", WIDTH,HEIGHT,    0, 10);

      write(b,Color.red,chillerFont, "I Remember Everything...", 150,100);
   
   
      //line(new Color(168, 96, 24), 275,430,325,460,10);
      
   
      //b.setColor(Color.blue);
      //b.setStroke(new BasicStroke(5));
      //b.drawRect(2,22,picWidth-10,HEIGHT-10);
      
      //b.setColor(Color.yellow);
      //b.fillRect(100,100,200,100);
      
      //b.setColor(Color.cyan);
      //b.setStroke(new BasicStroke(40));
      //b.drawRect(500,100,200,100);   
   }
   
   
   
   
   public void triangle(Graphics2D b, Color c, int strokeSize, int x1, int y1, int x2, int y2, int x3, int y3)   
   {
      b.setColor(c);
      int[] xvalues = {x1, x2, x3};
      int[]yvalues = {y1, y2, y3};
      
      if(strokeSize < 0)
         b.fillPolygon(xvalues, yvalues, 90);
      else
      {
         b.setStroke(new BasicStroke(strokeSize));
         b.drawPolygon(xvalues,yvalues, 3);
      }
         
   }

   public void oval(Graphics2D b, Color c, int x, int y, int w, int h, int strokeSize)
   {
      b.setColor(c);
      if(strokeSize > -1)
      {
         b.setStroke(new BasicStroke(strokeSize));
         b.drawOval(x,y,w,h);
      }
      else
         b.fillOval(x,y,w,h);
   }


   
   public void rectangle(Graphics2D b,Color c,int x,int y,int w,int h,int strokeSize)
   {
      
      b.setColor(c);
      
      if (strokeSize > 0)
      {
         b.setStroke(new BasicStroke(strokeSize));
         b.drawRect(x,y,w,h); 
      }
      else
         b.fillRect(x,y,w,h);   
   }
   
   public void write(Graphics2D b, Color c, Font f, String msg, int x, int y)
   {
      b.setColor(c);
      b.setFont(f);
      b.drawString(msg,x,y);
   }
   
   public void addPicture(Graphics2D b, String picFileName, int width, int height, int x, int y)
   {
      BufferedImage img = null;
      Image newImage = null;
      try
      {
         img = ImageIO.read(getClass().getResource("res/"+picFileName));
         //img = ImageIO.read( new File(picFileName ));
         
         if (width != 0 && height !=0)
         {
            newImage = img.getScaledInstance(width, height, Image.SCALE_DEFAULT);
            b.drawImage(newImage, x,y, this);
         }
         else
            b.drawImage(img, x, y,  this);
      }
      catch(Exception e)
      {
         e.printStackTrace();
         String msg1 = "Runtime error caught in addPicture : \""+picFileName+"\" not found or read properly.";
         String msg2 = "Make sure that your picture fileis in the same folder as your .java file.";
         System.out.print(msg1+"\n"+msg2);
         //text(b, Color.ORANGE, msg1, 20, 50, new Font("Arial", Font.BOLD, 14));
         //text(b, Color.ORANGE, msg2, 20, 68, new Font("Arial", Font.BOLD, 14));
      
         
      }
   
   } 

   public void addBackgroundPicture(Graphics2D b, String picFileName)
   {
      addPicture(b, picFileName, picWidth, HEIGHT, 0, 22);
   }
 
   
   
   public void setChoices(String[] choices)
   {
      choiceComboBox.removeAllItems();
      for (int i = 0 ; i<choices.length ; i++)
         choiceComboBox.addItem(choices[i]);
   }
   
   
   public void setInventory()
   {
      String collected = "Collected ";
      String missing = "Need ";
      
      for(int i = 0 ; i<collectables.length ; i++)
         if (haveCollected[i])
         {
            if (collected.equals("Collected: "))
               collected = collected+collectables[i];            
            else
               collected = collected+", "+collectables[i];
         }
         else
         {
            if (missing.equals("Need: "))
               missing = missing+", "+collectables[i];
            else
               missing = missing+", "+collectables[i];
         }
      inventoryTextArea.setText(collected+"\n"+missing);
      interactionPanel.repaint();
   }
   
   public boolean haveItem(String theItem)
   {
      for (int i = 0 ; i<collectables.length ; i++)
         if (theItem.equals(collectables[i]))
            return haveCollected[i];
      return false;
   }

   public void take()
   {
      String[] item = collectables;
      for(int i = 0 ; i<collectables.length ; i++)
         if (collectables[i].equals(item))
            haveCollected[i] = true;
    }
      
//   public void arc(Graphics2D b,Color c, int x, int y, int w, int h, int startAngle, int angleSize, int strokeSize)
//   {
//      b.setColor(c);
//      
//      if(strokeSize >= 0)
//      {
//         
//         b.setStroke(new BasicStroke(strokeSize));
//         b.drawArc(x,y,w,h,startAngle,angleSize);
//      }
//      else
//         b.fillArc(x,y,w,h,startAngle,angleSize);
//   }
   
//   public void line(Graphics 2D b, Color c, int x1, int y1, int x2, int y2, int strokeSize)
//   {
//      b.setColor(c);
//      b.setStroke(new BasicStroke(strokeSize));
//      b.drawLine(x1,y1,x2,y2);
//   }

   public void actionPerformed(ActionEvent e)
   {
      if (e.getSource() == choiceComboBox)
      {
         String selected = choiceComboBox.getSelectedItem().toString();
         if (selected.equals("Start the adventure.") ||
            selected.equals("Go back"))
            dashBoard();
         else if (selected.equals("Check Phone"))
            checkPhone();
         else if (selected.equals("Check Radio"))
            checkRadio();
         else if (selected.equals("Look For House Keys"))
            lookForHouseKeys();
         else if (selected.equals("Look again"))
            cmonWhereIsIt();
         else if (selected.equals("Look again..."))
            oneLastTime();
         
         else if (selected.equals("Unlock door"))
            unlockDoor(); 
         else if (selected.equals("Look through window"))
            window();
         else if (selected.equals("Break window"))
            breakWindow(); 
         else if (selected.equals("Break door"))
            breakDoor();
         else if (selected.equals("Look around"))
            lookAround();
         else if (selected.equals("Go inside"))
            inside();
         else if (selected.equals("Look around..."))
            lookAround2();
         else if (selected.equals("investigate painting"))
            painting();
         
         
         
         else if (selected.equals("What the hell?"))
            iRememberEverything();
         else if (selected.equals("Keep listening"))
            listen();
         else if (selected.equals("I know now..."))
            theEnd();
         else if (selected.equals("It's over"))
            endScreen();
            
         
      
         
         
         
         
         /////
         else if (selected.equals("Go to front door") && haveCollected[0]==true)
            frontDoor();
         else
            storyTextArea.setText("You go to the front door, but it's locked...");            
         
            
            
                  
                                     
      } 
              
   }
   
  
  
  
   public void dashBoard()
   {
      Graphics2D b = buffer.createGraphics();
      
      //addBackgroundPicture(b, "1594522724_442009957.jpg");
      addPicture(b, "Resident-Evil-7-Mansion.jpg", picWidth, HEIGHT,    0, 10);
      storyTextArea.setText("You've been driving for six hours on a rainy night and finally "+
               "arrived at your old family home. Your mother told you that they wanted to sell the home, so you "+
               "wanted to get one last look at old memmories. You Turn off the car and sit for a minute...");
            
      String[] newItems = {"What do you do?", "Check Phone", "Check Radio", "Look For House Keys","Go to front door"};
      setChoices(newItems);
      
    
      
      drawScreen();
      b.dispose();
   
      
   }
   
   public void checkPhone()
   {
      Graphics2D b = buffer.createGraphics();
      
      //addBackgroundPicture(b, "1594522724_442009957.jpg");
      addPicture(b, "phone.jpg", WIDTH,HEIGHT,    0, 10);
   
      storyTextArea.setText("Hm, six hours and no notifications huh. Then again, it's 11:00PM.");
      
      String[]newItems = {"What do you do?","Go back"};
      setChoices(newItems);
      
      drawScreen();
      b.dispose();
   
   }
   
   
   public void checkRadio()
   {
      Graphics2D b = buffer.createGraphics();
      
      //addBackgroundPicture(b, "1594522724_442009957.jpg");
      addPicture(b, "radio.jpg", WIDTH,HEIGHT,    0, 10);
   
      storyTextArea.setText("The host is just talking about the grammys...");
      
      String[]newItems = {"What do you do?","Go back"};
      setChoices(newItems);
      
      drawScreen();
      b.dispose();
   
   }
   
   public void lookForHouseKeys()
   {
      Graphics2D b = buffer.createGraphics();
      
      //addBackgroundPicture(b, "1594522724_442009957.jpg");
      addPicture(b, "Glovebox.jpg", WIDTH,HEIGHT,    0, 10);
   
      storyTextArea.setText("You look into every compartment for the keys your sister gave you. "+ 
         "After seven minutes of searching...you couldn't find it.");
      
      String[]newItems = {"What do you do?","Look again"};
      setChoices(newItems);
      
      drawScreen();
      b.dispose();
   
   }
   
   public void cmonWhereIsIt()
   {
      Graphics2D b = buffer.createGraphics();
      
      //addBackgroundPicture(b, "1594522724_442009957.jpg");
      //addPicture(b, "1594522724_442009957.jpg", 500,300,    0, 10);
   
      storyTextArea.setText("You look again...and nothing");
    
      
      String[]newItems = {"What do you do?","Look again..."};
      setChoices(newItems);
      
      drawScreen();
      b.dispose();
   
   }
   
   public void oneLastTime()
   {
      Graphics2D b = buffer.createGraphics();
      
      //addBackgroundPicture(b, "1594522724_442009957.jpg");
      //addPicture(b, "1594522724_442009957.jpg", 500,300,    0, 10);
      
      
      storyTextArea.setText("You search one more time and finally remember, it's in your pocket...");
      haveCollected[0] = true;
      setInventory();
      
   
      String[]newItems = {"What do you do?","Go to front door"};
      setChoices(newItems);
      
      drawScreen();
      b.dispose();
   
   }
   
   public void frontDoor()
   {
      Graphics2D b = buffer.createGraphics();
      
      //addBackgroundPicture(b, "1594522724_442009957.jpg");
      addPicture(b, "frontDoor.jpg", WIDTH,HEIGHT,    0, 10);
   
      storyTextArea.setText("You sigh with disapointment while you take in the fact that you just spent 30 minutes looking for the most obivious set of keys you've ever seen. You step out of the car and walk across the stone path to the door.");
   
      
      String[]newItems = {"What do you do?","Unlock door"};
      setChoices(newItems);
      
      drawScreen();
      b.dispose();
   
   }
   
   public void unlockDoor()
   {
      Graphics2D b = buffer.createGraphics();
      addPicture(b, "keyindoor.jpg", WIDTH,HEIGHT,    0, 10);
      storyTextArea.setText("You put the key in the door and turn it, but suddenly, the key snaps. Maybe you can barge the door open...");
      
      String[]newItems = {"What do you do?","Look through window"};
      setChoices(newItems);
      drawScreen();
      b.dispose();
   }
   
   public void window()
   {
      Graphics2D b = buffer.createGraphics();
      storyTextArea.setText("You take a peak through the window, but it's very hard to see through. Well, at this point I can either break the door or smash the window.");
      addPicture(b, "window.jpg", WIDTH,HEIGHT,    0, 10);
      String[]newItems = {"What do you do?","Break window","Break door","Look around"};
      setChoices(newItems);
      drawScreen();
      b.dispose();

   }
   
   public void breakWindow()
   {
      Graphics2D b = buffer.createGraphics();
      storyTextArea.setText("You take a deep breath preparing and hyping yourself up to take the hardest right hook to this window. You step back and swing at it like Rocky in the ring, smashining the window to pieces, which is seeming like a horrible idea now. You take a minute to breathe, and man, your arm hurts...");
      addPicture(b, "goingin.jpg", WIDTH,HEIGHT,    0, 10);
      String[]newItems = {"What do you do?", "Go inside"};
      setChoices(newItems);
      drawScreen();
      b.dispose();

      
   }
   
   public void breakDoor()
   {
      Graphics2D b = buffer.createGraphics();
      storyTextArea.setText("You take a deep breath preparing and hyping yourself up to take the hardest kick to this door. You step back and take a kick to the door, but it doesn't even budge..."+
                            "'is there something I can use?'");
      addPicture(b, "kickopen.png", WIDTH,HEIGHT,    0, 10);
      String[]newItems = {"What do you do?", "Look around"};
      setChoices(newItems);
      drawScreen();
      b.dispose();   
   }
   
   public void lookAround()
   {
      Graphics2D b = buffer.createGraphics();
      storyTextArea.setText("You look around the entrance for something to pry open the door. After a few minutes of searching, you conveniently find a rusty crowbar! Odd, but lucky you!.\n You stick the bar into the door and open it...");
         
      addPicture(b, "crowbar.jpg", WIDTH,HEIGHT,    0, 10);
      String[]newItems = {"What do you do?", "Go inside"};
      haveCollected[1] = true;
      setInventory();
      setChoices(newItems);
      drawScreen();
      b.dispose();   
   
   }
   
   public void inside()
   {
      Graphics2D b = buffer.createGraphics();
      storyTextArea.setText("You finally managed to get inside. You stand there for a minute, taking everything in for a while.\n\n'Man, this will be all gone soon..'");                            
      addPicture(b, "house.jpg", WIDTH,HEIGHT,    0, 10);
      String[]newItems = {"What do you do?", "Look around..."};
      setChoices(newItems);
      drawScreen();
      b.dispose();   

   }
   
   public void lookAround2()
   {
      Graphics2D b = buffer.createGraphics();
      storyTextArea.setText("After staring for a bit, you notice that something doesn't seem right. You notice a large pile of vodka bottles on the ground and cinders near a painting as the paint melts off\n\n'What is going on, and why does my head hurt?'");                            
      addPicture(b, "house.jpg", WIDTH,HEIGHT,    0, 10);
      String[]newItems = {"What do you do?", "pick up a bottle", "investigate painting"};
      setChoices(newItems);
      drawScreen();
      b.dispose();   

   }
   
   public void bottle()
   {
      Graphics2D b = buffer.createGraphics();
      storyTextArea.setText("You go to grab one of the bottle...\n\n'Why are all the bottles still full??'\n\nSuddenly, you head start to scream in pain as everything flashes white...");                            
      addPicture(b, "botle2.jpg", WIDTH,HEIGHT,    0, 10);
      String[]newItems = {"What do you do?", "What the hell?"};
      setChoices(newItems);
      drawScreen();
      b.dispose();   

   }
   
   public void painting()
   {
      Graphics2D b = buffer.createGraphics();
      storyTextArea.setText("You try to touch the painting...\n\n'Why doesn't the painting burn me?'\n\nSuddenly, you head start to scream in pain as everything flashes white...");                            
      addPicture(b, "painting.jpg", WIDTH,HEIGHT,    0, 10);
      String[]newItems = {"What do you do?", "What the hell?"};
      setChoices(newItems);
      drawScreen();
      b.dispose();   

   }
   
   public void iRememberEverything()
   {
      Graphics2D b = buffer.createGraphics();
      storyTextArea.setText("open your eyes and everything is bright, your body is in pain, and you dont know where you are. As you lay there you slowly come to and notice that your in a hospital room and a group of doctors are talking around you...");                            
      addPicture(b, "hospital.jpg", WIDTH,HEIGHT,    0, 10);
      String[]newItems = {"What do you do?", "Keep listening"};
      setChoices(newItems);
      drawScreen();
      b.dispose();   

      
   }
   
   public void listen()
   {
      Graphics2D b = buffer.createGraphics();
      storyTextArea.setText("Doctor: Look, this guy has so much alcohol is his body that I'm suprised the house wasn't that didn't kill him, let alone the house fire.\n\n"+
      "Nurse: Is the mother okay?\n\n Doctor: No, she code blued 30 minutes ago...When he wakes up, he is going to have a lot to answer for.");                             
      addPicture(b, "doctor.jpg", WIDTH,HEIGHT,    0, 10);
      String[]newItems = {"What do you do?", "I know now..."};
      setChoices(newItems);
      drawScreen();
      b.dispose();   

  
   }
   
   public void theEnd()
   {
      Graphics2D b = buffer.createGraphics();
      storyTextArea.setText("Despite the growning pain you remember everything... That night, you never went to visit the home, you were already there, lungs deep in booze again. You're mother was there that night, only wanting you to stop drinking, to talk to your family again. Even after everything she said, you couldn't even pay attentoion. You went to lay on the couch and spilled you bottle in the process. Some of your vodka fell on a lit candle, instantly starting a fire. No one could stop it...");                             
      addPicture(b, "fire.png", WIDTH,HEIGHT,    0, 10);
      String[]newItems = {"What do you do?", "It's over"};
      setChoices(newItems);
      drawScreen();
      b.dispose();   

   }
   
   public void endScreen()
   {
      System.exit(0);
   }
   
  
   
   public void beginGame()
   {
      Graphics2D b = buffer.createGraphics();
      drawIntroPicture(b);
      b.dispose();
   }
  
   
   
} //end of class AdventureGame

