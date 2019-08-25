/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import com.sun.javafx.scene.control.skin.ScrollBarSkin;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javafx.scene.control.ScrollBar;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.plaf.basic.BasicScrollBarUI;

/**
 *
 * @author Rajesh
 */
public class CustomScrollPane1 extends JScrollPane{

    
    
    
    public CustomScrollPane1 () {
    
        this.addMouseListener ( myScrollPaneMouseListener );
        this.addMouseListener ( myScrollPaneMouseExitListener );
     //   this.addMouseListener ( myScrollPaneMouseReleasedListener );
        this.horizontalScrollBar.addMouseListener ( myScrollPaneMouseListener );
        this.verticalScrollBar.addMouseListener ( myScrollPaneMouseListener );
        this.horizontalScrollBar.addMouseListener ( myScrollPaneMouseExitListener );
        this.verticalScrollBar.addMouseListener ( myScrollPaneMouseExitListener );
        
        
        //this.addMouseMotionListener ( myMouseMotionListener );
        
        this.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
        this.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 10));
        
            Color color1 = new Color(88,147,180);
        
        getVerticalScrollBar ().setUI ( new BasicScrollBarUI () {
            @Override
            protected JButton createDecreaseButton ( int orientation ) {
                return createZeroButton ();
            }

            @Override
            protected JButton createIncreaseButton ( int orientation ) {
                return createZeroButton ();
            }

                @Override 
                    protected void configureScrollBarColors(){
                        this.thumbColor = color1;
                    }            
            
            private JButton createZeroButton () {
                JButton jbutton = new JButton ();
                jbutton.setPreferredSize ( new Dimension ( 0 , 0 ) );
                jbutton.setMinimumSize ( new Dimension ( 0 , 0 ) );
                jbutton.setMaximumSize ( new Dimension ( 0 , 0 ) );
                return jbutton;
            }
            
        } );
        
        getHorizontalScrollBar ().setUI(
        
                new BasicScrollBarUI () {
                    @Override
                    protected JButton createDecreaseButton ( int orientation ) {
                        return createZeroButton ();
                    }

                    @Override
                    protected JButton createIncreaseButton ( int orientation ) {
                        return createZeroButton ();
                    }

                    @Override 
                    protected void configureScrollBarColors(){
                        this.thumbColor = color1;
                    }
        
                    private JButton createZeroButton () {
                        JButton jbutton = new JButton ();
                        jbutton.setPreferredSize ( new Dimension ( 0 , 0 ) );
                        jbutton.setMinimumSize ( new Dimension ( 0 , 0 ) );
                        jbutton.setMaximumSize ( new Dimension ( 0 , 0 ) );
                        return jbutton;
                    }
                } 
        );

    }
    
   
    
    
    MouseListener myScrollPaneMouseListener = new MouseListener () {
        @Override
        public void mouseClicked ( MouseEvent e ) {     }
        @Override
        public void mousePressed ( MouseEvent e ) {     }
        @Override
        public void mouseReleased ( MouseEvent e ) {        }

        @Override
        public void mouseEntered ( MouseEvent e ) {
            showScrolls ();
        }
        @Override
        public void mouseExited ( MouseEvent e ) {        }
    };
    
        MouseListener myScrollPaneMouseExitListener = new MouseListener () {
        @Override
        public void mouseClicked ( MouseEvent e ) {       }
        @Override
        public void mousePressed ( MouseEvent e ) {   }
        @Override
        public void mouseReleased ( MouseEvent e ) {   }
        @Override
        public void mouseEntered ( MouseEvent e ) {        }

        @Override
        public void mouseExited ( MouseEvent e ) {
            hideScrolls ();
        }
    };
        
        
    MouseListener myScrollPaneMousePressedListener = new MouseListener () {
        @Override
        public void mouseClicked ( MouseEvent e ) {       }
        @Override
        public void mousePressed ( MouseEvent e ) { 
            removeExitListener() ;
            removeAddListener() ;
            removeReleasedListener() ;
            showScrolls ();
        }
        @Override
        public void mouseReleased ( MouseEvent e ) {   }
        @Override
        public void mouseEntered ( MouseEvent e ) {        }

        @Override
        public void mouseExited ( MouseEvent e ) {        }
    };
    
    public void showScrolls(){
        setVerticalScrollBarPolicy (JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        setHorizontalScrollBarPolicy (JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }
 
    public void hideScrolls(){
        setVerticalScrollBarPolicy (JScrollPane.VERTICAL_SCROLLBAR_NEVER);
            setHorizontalScrollBarPolicy (JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }
    
    public void removeExitListener(){
        this.removeMouseListener ( myScrollPaneMouseExitListener );
        this.horizontalScrollBar.removeMouseListener ( myScrollPaneMouseExitListener );
        this.verticalScrollBar.removeMouseListener ( myScrollPaneMouseExitListener );
    }
     public void addExitListener(){
        this.addMouseListener ( myScrollPaneMouseExitListener );
        this.horizontalScrollBar.addMouseListener ( myScrollPaneMouseExitListener );
        this.verticalScrollBar.addMouseListener ( myScrollPaneMouseExitListener );
    }
     
     public void removeAddListener(){
        this.removeMouseListener ( myScrollPaneMouseListener );
               this.horizontalScrollBar.removeMouseListener ( myScrollPaneMouseListener );
        this.verticalScrollBar.removeMouseListener ( myScrollPaneMouseListener );

    }
     public void addAddListener(){
        this.addMouseListener ( myScrollPaneMouseListener );
                       this.horizontalScrollBar.addMouseListener ( myScrollPaneMouseListener );
        this.verticalScrollBar.addMouseListener ( myScrollPaneMouseListener );
    }
     
     public void removeReleasedListener(){
        this.removeMouseListener ( myScrollPaneMouseReleasedListener );
        this.horizontalScrollBar.removeMouseListener ( myScrollPaneMouseReleasedListener );
        this.verticalScrollBar.removeMouseListener ( myScrollPaneMouseReleasedListener );
    }
     public void addReleasedListener(){
        this.addMouseListener ( myScrollPaneMouseReleasedListener );
        this.horizontalScrollBar.addMouseListener ( myScrollPaneMouseReleasedListener );
        this.verticalScrollBar.addMouseListener ( myScrollPaneMouseReleasedListener );
    }
    
MouseListener myScrollPaneMouseReleasedListener = new MouseListener () {
        @Override
        public void mouseClicked ( MouseEvent e ) {       }
        @Override
        public void mousePressed ( MouseEvent e ) { 
        }
        @Override
        public void mouseReleased ( MouseEvent e ) {
                    setVerticalScrollBarPolicy (JScrollPane.VERTICAL_SCROLLBAR_NEVER);
                    setHorizontalScrollBarPolicy (JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            
            addExitListener() ;
            addAddListener() ;
        }
        @Override
        public void mouseEntered ( MouseEvent e ) {        }

        @Override
        public void mouseExited ( MouseEvent e ) {        }
    };
     
     
    
    MouseMotionListener myMouseMotionListener = new MouseMotionListener () {
        @Override
        public void mouseDragged ( MouseEvent e ) {
     //       setVerticalScrollBarPolicy (JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
     //       setHorizontalScrollBarPolicy (JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        }

        @Override
        public void mouseMoved ( MouseEvent e ) {
            
        }
    };

    
}

