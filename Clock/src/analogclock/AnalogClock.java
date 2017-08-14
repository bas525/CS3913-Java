/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analogclock;

import java.awt.Color;
import java.awt.Graphics;
import static java.lang.Thread.sleep;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.Calendar;

/**
 *
 * @author brand
 */
public class AnalogClock {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        JFrame jf = new JFrame("Analog Clock");
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setSize(500, 500);
        GraphicsPanel gp= new GraphicsPanel();
        new UpdatePanel(gp).start();
        jf.add(gp);
        jf.setVisible(true);
    }
    
}

class UpdatePanel extends Thread{
    GraphicsPanel jp;
    UpdatePanel(GraphicsPanel newjp){jp=newjp;}
    public void run(){
        try{
            while(true){
                jp.updateTime();
                sleep(1);
            }
        }
        catch(Exception e){}
    }
}

class Line{
    int[] x;
    int[] y;
    Line(int xx, int yy) {
        x = new int[2];
        y = new int[2];
        x[0] = 200;
        y[0] = 200;
        x[1] = xx;
        y[1] = yy;
    }
    Line(int x1, int x2, int y1, int y2) {
        x = new int[2];
        y = new int[2];
        x[0] = x1;
        y[0] = y1;
        x[1] = x2;
        y[1] = y2;
    }
}

class Point{
    int x;
    int y;
    Point(int xx, int yy) {
        x = xx;
        y = yy;
    }
}

class GraphicsPanel extends JPanel{

    /*
    Line[] Seconds;
    Line[] Minutes;
    Line[] Hours;
    */

    Line[] Dashes;
    Point[] Numbers;
    int curSec;
    int curMin;
    int curHour;
    double smoothSec;
    double smoothMin;
    double smoothHour;
    int secDist;
    int minDist;
    int hourDist;
    int cx;
    int cy;
    GraphicsPanel(){

        /*
        Seconds = new Line[60];
        Minutes = new Line[60];
        Hours = new Line[12];
        */
        Dashes = new Line[60];
        Numbers = new Point[12];
        curSec = 0;
        curMin = 0;
        curHour = 0;
        smoothSec = 0;
        smoothMin = 0;
        smoothHour = 0;
        secDist = 180;
        minDist = 170;
        hourDist = 140;
        int dashDistS = 190;
        int dashDistH = 183;
        int numDist = 175;
        int numDiffX = -3;
        int numDiffY = 5;
        cx = 200;
        cy = 200;
        
        for (int i = 0; i < 60; i++) {

            //Seconds[i] = new Line((int) (cx + secDist*Math.sin(Math.toRadians(i*6))), (int) (cy - secDist*Math.cos(Math.toRadians(i*6))));
            //Minutes[i] = new Line((int) (cx + secDist*Math.sin(Math.toRadians(i*6))), (int) (cy - secDist*Math.cos(Math.toRadians(i*6))));
            if (i%5 == 0){
                Dashes[i] = new Line((int) (cx + 200*Math.sin(Math.toRadians(i*6))), (int) (cx + dashDistH*Math.sin(Math.toRadians(i*6))), 
                                            (int) (cy - 200*Math.cos(Math.toRadians(i*6))), (int) (cy - dashDistH*Math.cos(Math.toRadians(i*6))));
            }
            else {
                Dashes[i] = new Line((int) (cx + 200*Math.sin(Math.toRadians(i*6))), (int) (cx + dashDistS*Math.sin(Math.toRadians(i*6))), 
                                            (int) (cy - 200*Math.cos(Math.toRadians(i*6))), (int) (cy - dashDistS*Math.cos(Math.toRadians(i*6))));
            }
        }
        
        for (int i = 0; i < 12; i++) {
            //Hours[i] = new Line((int) (cx + hourDist*Math.sin(Math.toRadians(i*30))), (int) (cy - hourDist*Math.cos(Math.toRadians(i*30))));
            Numbers[i] = new Point((int) (cx + numDiffX + numDist*Math.sin(Math.toRadians(i*30))), (int) (cy + numDiffY - numDist*Math.cos(Math.toRadians(i*30))));
        }

        /*
        Seconds = new Line[360];
        for (int i=0; i < 360; i+=1){
            Seconds[i] = new Line((int)(cx + secDist*Math.sin(Math.toRadians(i))), (int) (cy - secDist*Math.cos(Math.toRadians(i))));
        }
        repaint();
        */
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        g.drawOval(0, 0, 400, 400);
        

        /*
        for (int j = 0; j < Seconds.length; j++) {
            g.drawPolygon(Seconds[j].x, Seconds[j].y, 2);
        }       
        for (int j = 0; j < 16; j++) {
            g.drawPolygon(Seconds[j].x, Seconds[j].y, 2);
        }
        */
        
        g.setColor(Color.BLACK);
        g.drawString("12 ", Numbers[0].x, Numbers[0].y);
        for (int j = 1; j < 12; j++) {
            g.drawString(Integer.toString(j), Numbers[j].x, Numbers[j].y);
        }
        for (int j = 0; j < 60; j++) {
            g.drawPolygon(Dashes[j].x, Dashes[j].y, 2);
        }

        //g.drawPolygon(Minutes[curMin].x, Minutes[curMin].y, 2);
        //g.drawPolygon(Hours[curHour].x, Hours[curHour].y, 2);
        //g.setColor(Color.RED);
        //g.drawPolygon(Seconds[curSec].x, Seconds[curSec].y, 2);

        Line hour = new Line((int) (cx + hourDist*Math.sin(Math.toRadians(smoothHour*30))), (int) (cy - hourDist*Math.cos(Math.toRadians(smoothHour*30))));
        g.drawPolygon(hour.x, hour.y, 2);
        
        
        Line min = new Line((int) (cx + minDist*Math.sin(Math.toRadians(smoothMin*6))), (int) (cy - minDist*Math.cos(Math.toRadians(smoothMin*6))));
        g.drawPolygon(min.x, min.y, 2);
        
        g.setColor(Color.RED);
        Line sec = new Line((int) (cx + secDist*Math.sin(Math.toRadians(smoothSec*6))), (int) (cy - secDist*Math.cos(Math.toRadians(smoothSec*6))));
        g.drawPolygon(sec.x, sec.y, 2);
    }
    
    void updateTime(){
        Calendar c = Calendar.getInstance();
        curSec = c.get(Calendar.SECOND);
        curMin = c.get(Calendar.MINUTE);
        curHour = c.get(Calendar.HOUR);
        smoothSec = (double) curSec + ((double) c.get(Calendar.MILLISECOND))/1000;
        smoothMin = (double) curMin + ((double) curSec)/60;
        smoothHour = (double) curHour + ((double) curMin)/60;
        repaint();
    }
    
}
