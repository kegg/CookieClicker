package com.dreamsense.cookie;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Kyle Eggleston on 6/29/20 at 5:37 PM.
 * Project: CookieClicker
 */
public class CookieClicker extends JFrame {
  
  private StatusBar statusBar;
  private JButton clickerButton;
  private JButton grandmaButton;
  private JButton factoryButton;
  private JButton farmButton;
  private JLabel counter;
  private JLabel autoClickerLabel;
  private JLabel grandmaClickerLabel;
  private JLabel factoryClickerLabel;
  private JLabel farmClickerLabel;
  private int clicker = 0;
  private int totalClicks = 0;
  private int autoClicker = 0;
  private int grandmaClicker = 0;
  private int factoryClicker = 0;
  private int farmClicker = 0;
  
  public CookieClicker() {
    super("Cookie Clicker");
  }
  
  public void init() {
    statusBar = new StatusBar();
    JButton cookie = new JButton();
    cookie.setBorderPainted(false);
    cookie.setFocusPainted(false);
    counter = new JLabel("0");
    counter.setFont(new Font("Helvetica", Font.BOLD, 18));
    counter.setForeground(Color.blue);
    
    ImageIcon icon = getIcon("cookie");
    cookie.setIcon(icon);
    cookie.addActionListener(e -> {
      incrementClicker();
      processClickerButton();
      
      String[] responses = {
        "Much Wow",
        "Much Amaze",
        "Much Great",
        "Such Drift",
        "Very Leader",
        "Much Infinity",
        "Amaze Cookie"
      };
      
      if (clicker % 100 == 0) {
        statusBar.setMessage(responses[getRandomNumber(0, responses.length)]);
      }
    });
    
    setLayout(new BorderLayout());
    setIconImage(icon.getImage());
    
    JPanel counterPanel = new JPanel();
    counterPanel.add(counter);
    
    JPanel rewardsPanel = createRewardsPanel();
    JPanel statusPanel = createStatusPanel();
    
    add(counterPanel, BorderLayout.NORTH);
    add(cookie, BorderLayout.CENTER);
    add(statusPanel, BorderLayout.WEST);
    add(rewardsPanel, BorderLayout.EAST);
    add(statusBar, BorderLayout.SOUTH);
    
    setJMenuBar(createJMenuBar());
    
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    pack();
    setLocationRelativeTo(null);
  }

  private ImageIcon getIcon(String image) {
    ImageIcon icon = null;
    try (InputStream stream = getClass().getResourceAsStream(String.format("/resources/%s.png", image))){
        assert stream != null;
        icon = new ImageIcon(ImageIO.read(stream));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return icon;
  }
  
  public int getRandomNumber(int min, int max) {
    return (int) ((Math.random() * (max - min)) + min);
  }
  
  private JMenuBar createJMenuBar() {
    JMenuBar menuBar = new JMenuBar();

    JMenu fileMenu = getFileMenu();

    menuBar.add(fileMenu);
    menuBar.add(Box.createHorizontalGlue());
    
    JMenu helpMenu = new JMenu("Help");
    helpMenu.setMnemonic(KeyEvent.VK_H);
    JMenuItem aboutMenuItem = new JMenuItem("About");
    aboutMenuItem.setMnemonic(KeyEvent.VK_A);
    aboutMenuItem.addActionListener(e ->
        JOptionPane.showMessageDialog(this,
            "Version: 1.0 Alpha",
            "About Cookie Clicker",
            JOptionPane.PLAIN_MESSAGE));
    
    JMenuItem howToPlayMenuItem = new JMenuItem("How To Play");
    howToPlayMenuItem.setMnemonic(KeyEvent.VK_P);
    howToPlayMenuItem.addActionListener(e ->
        JOptionPane.showMessageDialog(this,
            "You click the cookie. Isn't that " +
                "obvious enough?\nI mean, it's called " +
                "Cookie Clicker for a reason,\nso click the " +
                "cookie...",
                "How To Play",
            JOptionPane.PLAIN_MESSAGE));
    
    helpMenu.add(aboutMenuItem);
    helpMenu.add(howToPlayMenuItem);
    menuBar.add(helpMenu);
    
    return menuBar;
  }

  private JMenu getFileMenu() {
    JMenu fileMenu = new JMenu("File");
    fileMenu.setMnemonic(KeyEvent.VK_F);
    JMenuItem quitMenuItem = new JMenuItem("Quit");
    quitMenuItem.setMnemonic(KeyEvent.VK_Q);
    quitMenuItem.addActionListener(e -> System.exit(0));

    JMenuItem totalMenuItem = new JMenuItem("Total Clicks");
    totalMenuItem.setMnemonic(KeyEvent.VK_C);
    totalMenuItem.addActionListener(e ->
        JOptionPane.showMessageDialog(this,
            "Total Clicks: " + totalClicks,
            "Cookie Clicker",
            JOptionPane.PLAIN_MESSAGE));

    fileMenu.add(totalMenuItem);
    fileMenu.add(quitMenuItem);
    return fileMenu;
  }

  private JPanel createStatusPanel() {
    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(4,1));

    autoClickerLabel = new JLabel("Auto Clicker (0)");
    autoClickerLabel.setIcon(getIcon("auto"));
    panel.add(autoClickerLabel);
    grandmaClickerLabel = new JLabel("Grandma Clicker (0)");
    grandmaClickerLabel.setIcon(getIcon("grandma"));
    panel.add(grandmaClickerLabel);
    factoryClickerLabel = new JLabel("Factory Clicker (0)");
    factoryClickerLabel.setIcon(getIcon("factory"));
    panel.add(factoryClickerLabel);
    farmClickerLabel = new JLabel("Farm Clicker (0)");
    farmClickerLabel.setIcon(getIcon("farm"));
    panel.add(farmClickerLabel);
    
    JPanel mainStatusPanel = new JPanel();
    mainStatusPanel.add(panel);
    
    return mainStatusPanel;
  }
  
  private JPanel createRewardsPanel() {
    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(4,1));
    
    clickerButton = new JButton("Auto (1)");
    clickerButton.setEnabled(false);
    clickerButton.addActionListener(e -> {
      autoClicker++;
      autoClickerLabel.setText("Auto Clicker (" + autoClicker + ")");
      clicker = clicker - 100;
      processClickerButton();
      
      Timer timer = new Timer(5000, e1 -> {
        incrementClicker();
        processClickerButton();
      });
      
      timer.start();
    });
    
    grandmaButton = new JButton("Grandma (1)");
    grandmaButton.setEnabled(false);
    grandmaButton.addActionListener(e -> {
      grandmaClicker++;
      grandmaClickerLabel.setText("Grandma Clicker (" + grandmaClicker + ")");
      clicker = clicker - 1000;
      processClickerButton();
  
      Timer timer = new Timer(2500, e12 -> {
        incrementClicker();
        processClickerButton();
      });
      timer.start();
    });
  
    factoryButton = new JButton("Factory (1)");
    factoryButton.setEnabled(false);
    factoryButton.addActionListener(e -> {
      factoryClicker++;
      factoryClickerLabel.setText("Factory Clicker (" + factoryClicker + ")");
      clicker = clicker - 1500;
      processClickerButton();
    
      Timer timer = new Timer(1000, e13 -> {
        incrementClicker();
        processClickerButton();
      });
      timer.start();
    });
  
    farmButton = new JButton("Farm (1)");
    farmButton.setEnabled(false);
    farmButton.addActionListener(e -> {
      farmClicker++;
      farmClickerLabel.setText("Farm Clicker (" + farmClicker + ")");
      clicker = clicker - 2000;
      processClickerButton();
    
      Timer timer = new Timer(500, e13 -> {
        incrementClicker();
        processClickerButton();
      });
      timer.start();
    });
    
    panel.add(clickerButton);
    panel.add(grandmaButton);
    panel.add(factoryButton);
    panel.add(farmButton);
    
    return panel;
  }
  
  private void incrementClicker() {
    clicker++;
    totalClicks++;
    counter.setText(String.valueOf(clicker));
  }
  
  private void processClickerButton() {
    clickerButton.setEnabled(clicker >= 100);
    grandmaButton.setEnabled(clicker >= 1000);
    factoryButton.setEnabled(clicker >= 1500);
    farmButton.setEnabled(clicker >= 2000);

    counter.setText(String.valueOf(clicker));
  }
  
  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
             UnsupportedLookAndFeelException e) {
      e.printStackTrace();
    }
      CookieClicker cc = new CookieClicker();
    cc.init();
    cc.setVisible(true);
  }
  
  static class StatusBar extends JLabel {
    
    public StatusBar() {
      super();
      super.setPreferredSize(new Dimension(100, 16));
      setMessage("Ready");
    }
    
    public void setMessage(String message) {
      setText(" "+message);
    }
  }
}
