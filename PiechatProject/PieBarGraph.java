/** *******************************************************************
 * File: PieBarGraph.java
 *
 * Author: Ronald S. Holland
 * Total Application Works
 *
 * This program takes user input and creates a pie and/or bar chart
 * Copyright (c) 2002-2009 Advanced Applications Total Applications Works.
 * (AATAW)  All Rights Reserved.
 *
 * 
 *
 * AATAW grants you ("licensee") a non-exclusive, royalty free, license to use,
 * modify and redistribute this software in source and binary code form,
 * provided that i) this copyright notice and license appear on all copies of
 * the software; and ii) licensee does not utilize the software in a manner
 * which is disparaging to AATAW.
 *
 * This software is provided "AS IS," without a warranty of any kind. all
 * express or implied conditions, representations and warranties, INCLUDING any
 * implied warranty of merchantability, fitness for a particular purpose or
 * non-infringement, are hereby excluded. AATAW and its licensors shall not BE
 * liable For any damages suffered by licensee as a result of using, modifing
 * or distributing the software or its derivatives. In no event will AATAW or its
 * licensors be liable for any lost revenue, profit or data, or for direct,
 * indirect, special, consequential, incidental or punitive damages, however
 * caused and regardless of the theory of liability, arising out of the use of
 * or inability to use software, even if AATAW has been advised of the
 * possibility of such damages.
 *
 * This software is not designed or intended for use in on-line control of
 * aircraft, air traffic, aircraft navigation or aircraft communications; or in
 * the design, construction, operation or maintenance of any nuclear
 * facility. licensee represents and warrants that it will not use or
 * redistribute the software for such purposes.
 ********************************************************************************/


import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.image.*;
import java.awt.print.*;
import java.util.*;
import java.io.*;
import javax.swing.JOptionPane ;


/** ***************************************************************
 *  class PieChart is used to create a simple pie chart based on
 *  user data input.
 **************************************************************** */
public class PieBarGraph extends JApplet implements ActionListener , Printable {
   private JFrame app ;
   private PrinterJob job ;
   private Font         font;
   private JPanel buttonPanel, textPanel ;
   private JTextField text1, text2, text3, text4, text5 ;
   private Container c ;
   private int width = 500, height = 400;
   private int butChoice = -1 , prevChoice = -4 , curChart = 0  ;
   private Component componentToBePrinted;
   private int intField1 , intField2 , intField3 , intField4 , intField5 ;
   private int chgField1 = -1, chgField2 = -1 , chgField3 = -1 , chgField4 = -1 ,
                     chgField5 = -1 ;
   private JLabel label1;
   private  Graphics gVal;
   private JButton pieButton , barButton , printButton , helpButton ,
                    aboutButton , clearButton ,  exitButton ;
   private ChartPanel myChart ;
   private boolean debug = true , fieldNull , fieldNot_A_Digit , notInitPass = false;
   private String strField1 = "-1", strField2 = "-1", strField3 = "-1",
                       strField4 = "-1",  strField5 = "-1" ;

   /** ********************************************************
    * Initialization method init() calls the following methods:
    * 1- setupPanels() - creates the JPanels
    * 2- setupText() - creates lables and entry fields
    * 3- setupButtons() - creates push buttons
    *
    * In addition, it obtains the context for which to anchor
    * the JPanels and add the GUI.
    **********************************************************/
   public void init()   {

      setupPanels() ;

      setupText() ;

      setupButtons();

      c = getContentPane();

      c.add( textPanel,  BorderLayout.NORTH );
      c.add( buttonPanel, BorderLayout.SOUTH );
      c.add( myChart, BorderLayout.CENTER );

   }

   /**  ****************************************************
    *  The setupPanels() method is used to create the JPanels
    *  and JLabels used in this application/applet.
    *  1- Declare a JPanel - textPanel
    *  2- Set Layout Manager for textPanel to GridLayout
    *  3- Declare a JPanel - buttonPanel
    *  4- Declare a JPanel - myChart - area where pie chart 
    *     or bar chart is rendered
    *  
    ********************************************************/
   public void setupPanels() {
      textPanel   = new JPanel();
      textPanel.setLayout(  new GridLayout( 1, 8 ) );

      buttonPanel = new JPanel();

      myChart = new ChartPanel( this );

   }

   /**  ****************************************************
    * The setupText() method allocates and sets up the entry
    * text fields used to gather user input.
    * 1- Declare a JLabel
    * 2- Declare 5 JTextFields
    * 3- Add the label to the text panel
    * 4- Add text fields to the text panel
    *  
    ********************************************************/
   public void setupText() {

      label1      = new JLabel("Enter 5 numbers.");

      text1       = new JTextField(5);
      text2       = new JTextField(5);
      text3       = new JTextField(5);
      text4       = new JTextField(5);
      text5       = new JTextField(5);

      textPanel.add( label1 );
      textPanel.add( text1 );
      textPanel.add( text2  );
      textPanel.add( text3  );
      textPanel.add( text4  );
      textPanel.add( text5  );

   }

   /**  ****************************************************
    * The setupButtons() method allocates and sets up the
    * JButtons.
    * 1- Declare the push buttons
    * 2- Set the background colors for the push buttons
    * 3- Add the buttons to the button panel
    * 4- Add event handlers to each button
    *  
    ********************************************************/
   public void setupButtons() {
      pieButton    = new JButton( "Create Pie Chart" ) ;
      barButton    = new JButton( "Create Bar Chart" ) ;
      printButton  = new JButton( "Print" ) ;
      helpButton   = new JButton( "Help" ) ;
      aboutButton  = new JButton( "About" ) ;
      clearButton  = new JButton( "Clear" ) ;
      exitButton   = new JButton( "Exit" ) ;
      helpButton.setBackground( Color.blue ) ;
      helpButton.setForeground( Color.white ) ;
      aboutButton.setBackground( Color.blue ) ;
      aboutButton.setForeground( Color.white ) ;
      clearButton.setBackground( Color.white ) ;
      clearButton.setForeground( Color.blue ) ;
      exitButton.setBackground( Color.red ) ;
      exitButton.setForeground( Color.white ) ;

      buttonPanel.add( pieButton ) ;
      buttonPanel.add( barButton ) ;
      buttonPanel.add( printButton ) ;
      buttonPanel.add( helpButton ) ;
      buttonPanel.add( aboutButton ) ;
      buttonPanel.add( clearButton ) ;
      buttonPanel.add( exitButton ) ;

      pieButton.addActionListener( this ) ;
      barButton.addActionListener( this ) ;
      printButton.addActionListener( this ) ;
      helpButton.addActionListener( this ) ;
      aboutButton.addActionListener( this ) ;
      clearButton.addActionListener( this ) ;
      exitButton.addActionListener( this ) ;
   }

   /**  ****************************************************
    * Set the width of frame to 600 if w less than 0
    ***************************************************** */
   public void setWidth( int w )
      { width = ( w >= 0 ? w : 600 ); }

   /** ****************************************************
    * Set the height to 600 if h less than 0
    ***************************************************** */
   public void setHeight( int h )
      { height = ( h >= 0 ? h : 400 ); }

   /** ****************************************************
    * Set the current value of the frame reference
    ***************************************************** */
   public void setFrameRef( JFrame appM )
      { app = appM ; }


   /** */
   public void setGraphicsVal( Graphics g )     {
      gVal = g;
   }

   /**
   public Graphics getGraphicsVal(  )      {
      return gVal ;
   }  */

   /**   ******************************************************
    * The checkFields() method ensures that the data is not null
    * 1- Obtain the data from each field
    * 2- Determine if any field is null
    * 3- Verify that each piece of data is a digit  
    * 
    ***********************************************************/
   public boolean checkFields( ) {
      boolean checkedOK = false;

      sysPrint ( "checkFields() 1: Starting checkFields() method." ) ;

      strField1  = text1.getText()  ;
      strField2  = text2.getText()  ;
      strField3  = text3.getText()  ;
      strField4  = text4.getText()  ;
      strField5  = text5.getText()  ;


      if ( strField1.equals("") ||
               strField2.equals("") ||
               strField3.equals("") ||
               strField4.equals("") ||
               strField5.equals("") ) {

         JOptionPane.showMessageDialog(null,
                    "At least one of the entry fieds is null/blank.",
                    "Fields Cannot be Null", JOptionPane.INFORMATION_MESSAGE) ;
         fieldNull = true;
      }
      else if( !checkDigit()  ) {

         intField1 = Integer.parseInt( strField1 );
         intField2 = Integer.parseInt( strField2 );
         intField3 = Integer.parseInt( strField3 );
         intField4 = Integer.parseInt( strField4 );
         intField5 = Integer.parseInt( strField5 );

         fieldNot_A_Digit = true;
      }
      else
         //repaint() ;
         checkedOK = true;

      return  checkedOK ;
   }

   /** ****************************************************
    * checkDigit()
    * This method checks to ensure that the data entered
    * in the text fields are all digits. The field is parsed
    * character by character to ensure each is a digit.
    * 1- Obtain each value in order
    * 2- Determine the length of each value in order 
    * 3- Parse is value a character at a time
    * 4- If value is not a digit, determine which field
    *    - return value indicating value is not a digit 
    * 5- Otherwise, indicate all values are digits
    * 
    ***************************************************** */
   public boolean  checkDigit( ) {

       int strLength = 0 , ii = 0 ;
       boolean isDig = true;
       String str , whichfield = "", strArray[] = { strField1 , strField2 ,
                           strField3 ,  strField4 , strField5 } ;

       for ( ii = 0; ii < 5 && isDig ; ii++) {
          String strVal = strArray[ ii ] ;
          strLength = strVal.length();
          sysPrint( "checkDigit( ) 1: The value being checked is " + strVal +
                     " and the value of ii is " + ii + " and the length is " + strLength ) ;

          for (int iii = 0; iii < strLength; iii++) {
             if (!Character.isDigit( strVal.charAt(iii) ) ) {
                isDig = false;    // character is not a digit

                switch ( ii ) {   // which field is not a digit
                   case 0:
                      whichfield = "first" ;
                   break ;
                   case 1:
                      whichfield = "second" ;
                   break ;
                   case 2:
                      whichfield = "third" ;
                   break ;
                   case 3:
                      whichfield = "fourth" ;
                   break ;
                   case 4:
                      whichfield = "fifth" ;
                   break ;
                }

                JOptionPane.showMessageDialog(null,
                    "The number entered in the " + whichfield + " entry field is not a whole number." +
                    "\nPlease enter only whole numbers e.g. 55.",
                    "Fields Must ne Contain a number", JOptionPane.INFORMATION_MESSAGE) ;
                break;
             } // ............................ end of if construct
          } // ............................... end of inner for construct
       } //... ............................... end of outer for construct

       intField1 = Integer.parseInt( strField1  ) ;
       intField2 = Integer.parseInt( strField2  ) ;
       intField3 = Integer.parseInt( strField3  ) ;
       intField4 = Integer.parseInt( strField4  ) ;
       intField5 = Integer.parseInt( strField5  ) ;

       return isDig;
   }

   /** **************************************************
    *  The checkFieldsChange() method checks if data in
    *  the text fields has changed since the last check.
    ****************************************************/
   public void checkFieldsChange( ActionEvent e ) {

       sysPrint ( "checkFieldsChange() 1: Starting checkFieldsChange() method." ) ;

       sysPrint ( "checkFieldsChange() 2: intField1 =s " + intField1 + " chgField1 =s " + chgField1 +
                   "\nintField2 =s " + intField2 + " chgField2 =s " + chgField2 +
                   "\nintField3 =s " + intField3 + " chgField3 =s " + chgField3 +
                   "\nintField4 =s " + intField4 + " chgField4 =s " + chgField4 +
                   "\nintField5 =s " + intField5 + " chgField5 =s " + chgField5   ) ;

       if ( !(intField1 == chgField1 ) ||
                  !(intField2 == chgField2 )  ||
                  !(intField3 == chgField3 ) ||
                  !(intField4 == chgField4 ) ||
                  !(intField5 == chgField5 ) ) {

          sysPrint ( "checkFieldsChange() 3: In checkFieldsChange() method." ) ;

          getTextValues() ;

          chgField1  = intField1 ;
          chgField2  = intField2 ;
          chgField3  = intField3 ;
          chgField4  = intField4 ;
          chgField5  = intField5 ;
       }

       return;
   }

   /**  **************************************************
    * The getTextValues() method retrieves data from the
    * text entry fields.
    ****************************************************/
   public void getTextValues() {

      sysPrint ( "getTextValues() 1: Starting getTextValues() method." ) ;

      intField1 = Integer.parseInt( strField1  = text1.getText()  ) ;
      intField2 = Integer.parseInt( strField2  = text2.getText()  ) ;
      intField3 = Integer.parseInt( strField3  = text3.getText()  ) ;
      intField4 = Integer.parseInt( strField4  = text4.getText()  ) ;
      intField5 = Integer.parseInt( strField5  = text5.getText()  ) ;

      //repaint() ;

      return;
   }


   /** **************************************************
    *  actionPerformed() is the event handler that responds
    *  to the events caused by pressing the buttons. The events
    *  caused by pressing the push buttons are:
    *  1- Exit button causes the application to exit
    *  2- The Pie Button causes the application to gather the
    *     data the user entered to create a Pie Chart.
    *  3- The Pie Button causes the application to gather the
    *     data the user entered to create a Bar Chart.
    *  4- The Print Button causes the application to print the
    *     the chart being displayed.
    *  5- The Help Button causes the application to call the
    *     Internet Explorer to display a Help file.
    *  6- The About Button causes the application to try to connect
    *     to my site.
    *  7- The Clear Button causes the application to clear any
    *     chart being displayed and the text entry fields.
    *  8- The Exit Button causes the application to
    *     shut down/exit.
    ****************************************************/
   public void actionPerformed( ActionEvent e )  {

      if ( e.getSource() == exitButton ) {
            sysPrint ( "actionPerformed() 1b: The exit button was pressed." ) ;
            sysExit( 0 );
      }
      else if ( e.getSource() == pieButton ) {
         sysPrint( "actionPerformed() 2: The create Pie Chart button was pressed." ) ;
         notInitPass = true ;
         sysPrint( "actionPerformed() 3: The value of notInitPass is " + notInitPass ) ;
         if ( checkFields( ) ) {
            butChoice = 0 ;
            curChart = 0 ;
            repaint() ;
         }
      }
      else if ( e.getSource() == barButton ) {
         sysPrint( "actionPerformed() 4: The create Bar Chart button was pressed." ) ;
         notInitPass = true ;
         sysPrint( "actionPerformed() 5: The value of notInitPass is " + notInitPass ) ;
         if ( checkFields( ) ) {
            butChoice = 1 ;
            curChart = 1 ;
            app.setTitle( "Bar Chart" );
            repaint() ;
         }
      }
      else if ( e.getSource() == printButton ) {
         sysPrint( "actionPerformed() 6: The Print button was pressed." ) ;
         if ( checkFields( ) ) {
            prevChoice = butChoice  ;
            butChoice = 2 ;
            app.setTitle( "Printing Chart" );
            printFrame( gVal ) ;
         }
      }
      else if ( e.getSource() == helpButton ) {
         sysPrint( "actionPerformed() 7: The help button was pressed." ) ;
         File hd = new File("PieChart_III_Help.html");

         Runtime rt = Runtime.getRuntime();
         String[] callAndArgs = { "c:\\Program Files\\Internet Explorer\\IEXPLORE.exe" ,
                         "" + hd.getAbsolutePath() };


         butChoice = 3 ;
         try {
            Process child = rt.exec(callAndArgs) ;
            child.waitFor();
            sysPrint ("Process exit code is: " +
                                 child.exitValue());
         }
         catch(IOException e2) {
            sysPrint(
                  "IOException starting process!");
         }
         catch(InterruptedException e3) {
            sysPrint( "Interrupted waiting for process!" ) ;
         }
      }
      else if ( e.getSource() == aboutButton ) {
         sysPrint( "actionPerformed() 8: The about button was pressed." ) ;
         Runtime rt = Runtime.getRuntime();
         String[] callAndArgs = { "c:\\Program Files\\Internet Explorer\\IEXPLORE.exe" ,
                           "http://www.sumtotalz.com/TotalAppsWorks/index.html" };

         butChoice = 4 ;
         try {
            Process child = rt.exec(callAndArgs) ;
            child.waitFor();
            sysPrint ("Process exit code is: " +
                                 child.exitValue());
         }
         catch(IOException e2) {
            sysPrint(
                  "actionPerformed() 8b: IOException starting process!");
         }
         catch(InterruptedException e3) {
            sysPrint( "Interrupted waiting for process!" ) ;
         }
      }
      else if ( e.getSource() == clearButton ) {
         sysPrint( "actionPerformed() 9: The clear button was pressed." ) ;
         butChoice = 5 ;
         text1.setText("")  ;
         text2.setText("")  ;
         text3.setText("")  ;
         text4.setText("")  ;
         text5.setText("")  ;

         repaint() ;
      }
   }

   /** ***********************************************************
    * The sysExit() method is called in response to a close
    * application event.
    ************************************************************* */
   public void sysExit( int ext ) {
      System.exit( ext ) ;
   }

   /** **************************************************
    * drawPie uses the user input to draw the pie chart
    ****************************************************/

      public void drawPie( Graphics g) {

         double floatVal[] = { 0.0, 0.0, 0.0, 0.0, 0.0 } ;
         double totVal = 0.0;
         Font font ;

            /** create 2D by casting g to Graphics2D */
         Graphics2D g2d = ( Graphics2D ) g;

         totVal   = (double) intField1 + intField2
                  + intField3 + intField4  + intField5  ;
         floatVal[0] = (double) (intField1 / totVal);
         floatVal[1] = (double) (intField2 / totVal);
         floatVal[2] = (double) (intField3 / totVal);
         floatVal[3] = (double) (intField4 / totVal);
         floatVal[4] = (double) (intField5 / totVal);

         sysPrint("paintComponent() 2: The value of intField1 is " + intField1 +
                      "\nintField2 is " + intField2  +
                      "\nintField3 is " + intField3  +
                      "\nintField4 is " + intField4  +
                      "\nintField5 is " + intField5 ) ;

         Arrays.sort (floatVal);



            /** draw 2D pie-shaped arc in white */
         g2d.setPaint( Color.white );
         g2d.setStroke( new BasicStroke( 6.0f ) );
         g2d.fillArc( 90, 80, 300, 300, 0,  (int) ( floatVal[ 4 ] * 360 ) );
         g2d.fillRect( 440, 80, 40, 20 )  ;
         g.setFont(  new Font( "", Font.BOLD + Font.ITALIC, 18 ) );
         g2d.setPaint( Color.black );
         g.drawString("- " +
                  (java.lang.Math.round( floatVal[ 4 ] * 100.0 ))   +
                     " %",  490, 95);


         g2d.setPaint( Color.green );
         g2d.fillArc( 90, 80, 300, 300, (int) ( floatVal[ 4 ] * 360 ),
                          ( int )( floatVal[ 3 ] * 360 ) );
         g2d.fillRect( 440, 120, 40, 20 )  ;
         g.setFont( new Font( "", Font.BOLD + Font.ITALIC, 18 ) );
         g2d.setPaint( Color.black );
         g.drawString("- " + ( java.lang.Math.round( floatVal[ 3 ]  * 100))
                        + " %",  490, 135);

         g2d.setPaint( Color.blue );
         g2d.fillArc( 90, 80, 300, 300,
                  (int)(floatVal[ 4 ] * 360) +
                     (int)( floatVal[ 3 ] * 360 ) ,
                        (int)(floatVal[ 2 ] * 360 ) );
         g2d.fillRect( 440, 160, 40, 20 )  ;
         g.setFont( new Font( "", Font.BOLD + Font.ITALIC, 18 ) );
         g2d.setPaint( Color.black );
         g.drawString("- " + ( java.lang.Math.round( floatVal[ 2 ]  * 100)) + " %",
                       490, 175);

         g2d.setPaint( Color.black );
         g2d.fillArc( 90, 80, 300, 300,
                  (int)(floatVal[ 4 ] * 360) +
                     (int)( floatVal[ 3 ] * 360 ) +
                        (int)( floatVal[ 2 ] * 360) ,
                           (int) (floatVal[ 1 ] * 360 ) + 2 ) ;
         g2d.fillRect( 440, 190, 40, 20 )  ;
         g.setFont(
                    new Font( "", Font.BOLD + Font.ITALIC, 18 ) );
         g2d.setPaint( Color.black );
         g.drawString("- " + ( java.lang.Math.round( floatVal[ 1 ]  * 100)) + " %",
                       490, 205);

         g2d.setPaint( Color.red );
         g2d.fillArc( 90, 80, 300, 300,
                  (int)(floatVal[ 4 ] * 360) +
                     (int)( floatVal[ 3 ] * 360 ) +
                        (int)( floatVal[ 2 ] * 360) +
                           (int)( floatVal[ 1 ] * 360) ,
                              (int) (floatVal[ 0 ] * 360 ) + 2 ) ;
         g2d.fillRect( 440, 220, 40, 20 )  ;
         g.setFont(
                    new Font( "", Font.BOLD + Font.ITALIC, 18 ) );
         g2d.setPaint( Color.black );
         g.drawString("- " + ( java.lang.Math.round( floatVal[ 0 ]  * 100)) + " %",
                       490, 235);

         g2d.setStroke( new BasicStroke( 1.0f ) );
         g.drawArc( 90, 80, 300, 300, 0, 360 );

         notInitPass = true ;
      }

   /** **************************************************
    * drawBar uses the user input to draw the bar chart
    ****************************************************/
   public void drawBar( Graphics g) {
      /** create 2D by casting g to Graphics2D */
      Graphics2D g2d = ( Graphics2D ) g;

            /** Draw the x-coordinate line */
      g.setColor(Color.black);
      g.drawLine ( 5, 406, 450, 406);

            /** draw 2D Bars in red */
      g2d.setColor( Color.red );
      g2d.setStroke( new BasicStroke( 10.0f ) );
      g2d.draw( new Rectangle2D.Double( 50, 400 - intField1, 5,
                   intField1 ) );
      g.drawString("Red ", 45,
                   400 - intField1 - 40);
      g.drawString("" + intField1, 45,
                   400 - intField1 - 20);

      g2d.setColor( Color.green );
      g2d.setStroke( new BasicStroke( 10.0f ) );
      g2d.draw( new Rectangle2D.Double( 100, 400 - intField2,
                        5, intField2) );
      g.drawString( "Green ", 90,
                400 - intField2 - 40);
      g.drawString( "" + intField2, 90,
                400 - intField2 - 20);

      g2d.setColor( Color.yellow );
      g2d.setStroke( new BasicStroke( 10.0f ) );
      g2d.draw( new Rectangle2D.Double( 150, 400 - intField3,
                     5, intField3 ) );
      g.drawString("Yellow ", 140,
                          400 - intField3 - 40);
      g.drawString("" + intField3, 140,
                          400 - intField3 - 20);

      g2d.setColor( Color.blue );
      g2d.setStroke( new BasicStroke( 10.0f ) );
      g2d.draw( new Rectangle2D.Double( 200, 400 - intField4,
                    5, intField4 ) );
      g.drawString("Blue " , 190,
                      400 - intField4 - 40);
      g.drawString("" + intField4 , 190,
                      400 - intField4 - 20);

      g2d.setColor( Color.white );
      g2d.setStroke( new BasicStroke( 10.0f ) );
      g2d.draw( new Rectangle2D.Double( 250, 400 - intField5,
                    5, intField5 ) );
      g.drawString("White " , 240,
                      400 - intField5 - 40);
      g.drawString("" + intField5 , 240,
                      400 - intField5 - 20);
   }

   /** *********************************************************
    * This method prints the contents that are displayed
    * on the frame
    ************************************************************/
   public void printFrame( Graphics g ) {
      sysPrint("printFrame() 1:  prevChoice is " +
                          prevChoice  + " butChoice is " + butChoice );

      if ( ( prevChoice == 0 )  ||  ( prevChoice == 1 )  ) {

         //prevChoice = butChoice   ;

         /** Start a new print job */
         job = PrinterJob.getPrinterJob() ;
         job.setPrintable( this );
         if ( job.printDialog()) {
            try {
               job.print();
            }
            catch (PrinterException e) {
               e.printStackTrace();
            }
         }
         sysPrint("printFrame() 3: - butChoice is " +
               butChoice + " job is " + job);
      }
   }



   public int print(Graphics g, PageFormat pf, int pageIndex) throws PrinterException {
      // Stop after first page (index 0)
      if (pageIndex >= 1) {
         return Printable.NO_SUCH_PAGE;
      }

      // Translate coordinates to drawable area

      gVal.translate( (int) pf.getImageableX(), (int) pf.getImageableY() ) ;
      gVal = this.getGraphics() ;
      Graphics2D g2d = (Graphics2D)gVal;
      if ( prevChoice == 0 ) {
         sysPrint("\n1c- Currently in print() butChoice is " +
               butChoice );
         drawPie(  g ) ;
      }
      else if ( prevChoice == 1 ) {
         drawBar(  g ) ;
         sysPrint("\n1d- Currently in print() butChoice is " +
               butChoice );
      }
      else if ( butChoice == 2 ) {
         printFrame( g ) ;
         sysPrint("\n1d- Currently in print() butChoice is " +
               butChoice );
      }

      return Printable.PAGE_EXISTS;
   }


   /** ****************************************************
    * sysPrint() is used to print out debugging messages.
    ***************************************************** */
   public void sysPrint( String str ) {
      if( debug )
         System.out.println( str ) ;
   }

   /** ********************************************************************
    *  main(String args[] ) : this is the entry point for this application
    *                         that Java calls when starting this program
    *                         execution when it runs as an application.
    *********************************************************************/
   public static void main( String args[] )
   {
      int width = 790, height = 550;

      // create window in which applet will execute
      JFrame appMain =
         new JFrame( "Pie Chart" );

      /** create one applet instance */
      final PieBarGraph appPie = new PieBarGraph();
      appPie.setWidth( width );
      appPie.setHeight( height );
      appPie.setFrameRef( appMain ) ;
      /** call applet's init and start methods */
      appPie.init();
      appPie.start();

      /** attach applet to center of window */
      appMain.getContentPane().add( appPie );


      appMain.addWindowListener(
         new WindowAdapter() {
            public void windowClosing( WindowEvent e )
            {
               appPie.sysExit( 0 );
            }
         }
      );

      /** set the window's size */
      appMain.setSize( appPie.width, appPie.height );

      /**
       * showing the window causes all GUI components
       * attached to the window to be painted
       */

      appMain.show();
   }


   /** ********************************************************
    *  subclass of JPanel to allow drawing in a separate area
    ***********************************************************/
   public class ChartPanel extends JPanel  {
      private PieBarGraph pbg ;

      /** **************************************************
       * The ChartPanel( PieChart_II pc_II ) method is a constructor for the
       * ChartPanel class.
       ****************************************************/
      public ChartPanel( PieBarGraph pbg_III ) {

         pbg_III.sysPrint("ChartPanel() 1: Entered. ") ;
         pbg = pbg_III ;

      }

      /** ******************************************************
       *  paintComponent() is called when a paint operation is
       *                   required by the subclass MyPanel which
       *                   extends the JPanel class. The method
       *                   writes the help instructions on the
       *                   initial pass. It then does determines
       *                   a) - If any of the fields were null, which
       *                        will generate a fieldNull error.
       *                   b) - If any of the fields are not a digit,
       *                        which will generate a fieldNot_A_Digit
       *                        error.
       *
       *                   If steps a) and b) do not generate an error,
       *                   the code determines which button was pressed.
       *                   If butChoice equals
       *
       *                   0- The pie chart button was pressed.
       *                   1- The bar chart button was pressed.
       *                   2- The print button was pressed.
       *                   3- The help button was pressed.
       *                   4- The about button was pressed.
       *                   5- The exit button are not processed by this
       *                      paintcomponent because nothing needs to
       *                      be printed on the JPanel.
       ********************************************************/
      public void paintComponent( Graphics g )  {

         super.paintComponent( g );

         sysPrint("paintComponent() 1: Entered. The value of notInitPass is " + notInitPass ) ;
         sysPrint("paintComponent() 2: Entered. The value of butChoice is " + butChoice +
             " prevChoice is " + prevChoice ) ;

         setGraphicsVal( g ) ;

         if ( notInitPass ) {

            switch ( curChart ) {
               case 0:
                  drawPie( g )  ;
                  prevChoice = 0 ;
               break ;
               case 1:
                  drawBar( g )  ;
                  prevChoice = 1 ;
               break ;
               case 2:
                  printFrame( g )  ;
               break ;
            }
         }
      }
   }
}
