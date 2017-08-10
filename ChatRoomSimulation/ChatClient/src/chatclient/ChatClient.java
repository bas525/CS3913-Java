/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclient;

import java.net.Socket;
import javax.swing.JFrame;

/**
 *
 * @author brand
 */
public class ChatClient {
    static Socket server;
    static String username;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        StartupWindow s = new StartupWindow();
        s.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        s.setVisible(true);
        
    }
    
}
