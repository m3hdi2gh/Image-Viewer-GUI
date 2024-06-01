import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;

public class ImgViewer extends JFrame implements ActionListener {

    // Create buttons for the main panel
    JButton selectFileButton = new JButton("Choose Image");
    JButton showImageButton = new JButton("Show Image");
    JButton resizeButton = new JButton("Resize");
    JButton grayscaleButton = new JButton("Grayscale");
    JButton brightnessButton = new JButton("Brightness");
    JButton closeButton = new JButton("Exit");
    JButton showResizeButton = new JButton("Show Resized Image");
    JButton showBrightnessButton = new JButton("Show Brightness Image");
    JButton backButton = new JButton("Back");

    // Create text fields for the resize panel
    JTextField widthTextField;
    JTextField heightTextField;

    // Create text field for the brightness panel and a variable to store the brightness factor
    JTextField brightnessTextField;
    float brightenFactor = 1;

    // Create a file and file chooser for selecting an image
    File file;
    JFileChooser fileChooser = new JFileChooser();

    // Create colors for the background and buttons
    Color backgroundColor = new Color(238, 238, 238);
    Color buttonColor = new Color(255, 255, 255);

    // Create variables for the width and height of the image
    int width;
    int height;

    // Create variables for the image and the new width and height
    private Image img;
    int newWidth = 0;
    int newHeight = 0;

    // Constructor for the Image Viewer
    ImgViewer() {

        // Set properties of the frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Image Viewer");
        this.setSize(700, 300);
        this.setVisible(true);
        this.setResizable(true);

        // Set color for buttons
        this.selectFileButton.setBackground(buttonColor);
        this.showImageButton.setBackground(buttonColor);
        this.showResizeButton.setBackground(buttonColor);
        this.showBrightnessButton.setBackground(buttonColor);
        this.resizeButton.setBackground(buttonColor);
        this.grayscaleButton.setBackground(buttonColor);
        this.brightnessButton.setBackground(buttonColor);
        this.closeButton.setBackground(buttonColor);
        this.backButton.setBackground(buttonColor);

        // Add action listeners to buttons
        this.selectFileButton.addActionListener(this);
        this.showImageButton.addActionListener(this);
        this.resizeButton.addActionListener(this);
        this.grayscaleButton.addActionListener(this);
        this.brightnessButton.addActionListener(this);
        this.closeButton.addActionListener(this);
        this.showBrightnessButton.addActionListener(this);
        this.showResizeButton.addActionListener(this);

        mainPanel();
    }

    public void mainPanel() {

        // Create the main panel for adding to Frame
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(backgroundColor);

        // Create a Grid panel for adding buttons to it
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(3, 2));
        int buttonWidth = 300;
        int buttonHeight = 50;
        buttonsPanel.setBounds((700-buttonWidth*2)/2, (300-buttonHeight*2)/2, (buttonWidth*2), (buttonHeight*2));

        // Label of Image Viewer
        JLabel label = new JLabel("Image Viewer");
        label.setBounds(312, 50, 75, 30);

        // Adding all buttons to Grid panel
        buttonsPanel.add(this.selectFileButton);
        buttonsPanel.add(this.showImageButton);
        buttonsPanel.add(this.brightnessButton);
        buttonsPanel.add(this.grayscaleButton);
        buttonsPanel.add(this.resizeButton);
        buttonsPanel.add(this.closeButton);

        // add Grid panel that contains 6 buttons to main panel
        mainPanel.add(buttonsPanel);
        mainPanel.add(label);

        // add mainPanel to our frame
        this.add(mainPanel);
    }

    public void chooseFileImage() {

        // Open a file chooser dialog
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
        }

        // Create an image icon
        ImageIcon imageIcon = new ImageIcon(file.getAbsolutePath());

        // Resize the image to fit the panel
        this.width = imageIcon.getIconWidth();
        this.height = imageIcon.getIconHeight();
        double aspectRatio = (double) this.width / this.height;
        if (this.width > 1500) {
            newWidth = 1500;
            newHeight = (int) (newWidth / aspectRatio);
        }
        if (this.height > 800) {
            newHeight = 800;
            newWidth = (int) (newHeight * aspectRatio);
        }

        // Set the image to the new width and height
        this.img = imageIcon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
    }

    public void showOriginalImage() {

        // If no image is selected, show a message dialog
        if (this.img == null) {
            JOptionPane.showMessageDialog(null, "Please select an image first");
            return;
        }

        // Create a new frame and panel
        JFrame tempFrame = new JFrame();
        JPanel tempPanel = new JPanel();
        tempPanel.setBackground(backgroundColor);
        tempPanel.setLayout(null);

        // Add a back button
        this.backButton.setBounds(10, 10, 100, 30);
        tempPanel.add(this.backButton);

        // Action listener for back button
        this.backButton.addActionListener(_ -> {
            tempFrame.dispose();
            this.getContentPane().removeAll();
            this.mainPanel();
            this.revalidate();
            this.repaint();
        });

        // Align the image in the center of the panel
        ImageIcon newImage = new ImageIcon(this.img);
        JLabel label = new JLabel(newImage);
        label.setBounds((1500 - newWidth) / 2, (800 - newHeight) / 2, newWidth, newHeight);
        tempPanel.add(label);

        // Set size of panel and frame
        tempFrame.setTitle("Image Viewer");
        tempFrame.setSize(1500, 800);
        tempFrame.setVisible(true);
        tempFrame.setResizable(true);
        tempFrame.add(tempPanel);
        tempFrame.setContentPane(tempPanel);
    }

    public void grayScaleImage() {

        // If no image is selected, show a message dialog
        if (this.img == null) {
            JOptionPane.showMessageDialog(null, "Please select an image first");
            return;
        }

        // Create a new frame and panel
        JFrame tempFrame = new JFrame();
        JPanel tempPanel = new JPanel();
        tempPanel.setBackground(backgroundColor);
        tempPanel.setLayout(null);

        // Create a back button
        this.backButton.setBounds(10, 10, 100, 30);
        tempPanel.add(this.backButton);

        // Action listener for back button
        this.backButton.addActionListener(_ -> {
            tempFrame.dispose();
            this.getContentPane().removeAll();
            this.mainPanel();
            this.revalidate();
            this.repaint();
        });

        // convert image to grayscale
        ImageIcon newImage = new ImageIcon(this.img);
        Image image = newImage.getImage();
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_BYTE_GRAY);
        Graphics g = bufferedImage.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        ImageIcon grayImage = new ImageIcon(bufferedImage);

        // Add image to panel
        JLabel label = new JLabel(grayImage);
        label.setBounds((1500 - newWidth) / 2, (800 - newHeight) / 2, newWidth, newHeight);
        tempPanel.add(label);

        // Set size of panel and frame
        tempFrame.setTitle("Image Viewer");
        tempFrame.setSize(1500, 800);
        tempFrame.setVisible(true);
        tempFrame.setResizable(true);
        tempFrame.add(tempPanel);
        tempFrame.setContentPane(tempPanel);
    }

    public void brightnessPanel() {

        // Create a panel for the brightness
        JPanel brightnessPanel = new JPanel();
        JFrame brightnessFrame = new JFrame();
        brightnessPanel.setLayout(null);
        brightnessPanel.setBackground(backgroundColor);

        // Create text field and label for brightness
        this.brightnessTextField = new JTextField();
        JLabel brightnessLabel = new JLabel("Brightness");

        // Set bounds for text field, label, and buttons
        this.showBrightnessButton.setBounds(250, 150, 200, 30);
        this.backButton.setBounds(250, 200, 200, 30);
        brightnessLabel.setBounds(250, 100, 100, 30);
        this.brightnessTextField.setBounds(350, 100, 100, 30);

        // Add brightness text field, label, and buttons to brightnessPanel
        brightnessPanel.add(this.brightnessTextField);
        brightnessPanel.add(brightnessLabel);
        brightnessPanel.add(this.showBrightnessButton);
        brightnessPanel.add(this.backButton);

        // Add action listeners to buttons
        this.backButton.addActionListener(_ -> {
            brightnessFrame.setVisible(false);
            this.getContentPane().removeAll();
            this.mainPanel();
            this.revalidate();
            this.repaint();
        });

        // Add the brightness panel to our frame
        this.getContentPane().removeAll();
        this.add(brightnessPanel);
        this.revalidate();
        this.repaint();
    }

    public void showBrightnessImage(float factor) {

        // If no image is selected, show a message dialog
        if (this.img == null) {
            JOptionPane.showMessageDialog(null, "Please select an image first");
            return;
        }

        // Create a new frame and panel
        JFrame tempFrame = new JFrame();
        JPanel tempPanel = new JPanel();
        tempPanel.setBackground(backgroundColor);
        tempPanel.setLayout(null);

        // Add a back button
        this.backButton.setBounds(10, 10, 100, 30);
        tempPanel.add(this.backButton);

        // Action listener for back button to take back to the brightness panel
        this.backButton.addActionListener(_ -> {
            tempFrame.dispose();
            this.getContentPane().removeAll();
            this.brightnessPanel();
            this.revalidate();
            this.repaint();
        });

        // Change the brightness of the image based on the factor provided by the user in brightnessPanel
        Image newImg = this.img;
        BufferedImage bufferedImage = new BufferedImage(newImg.getWidth(null), newImg.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();
        g.drawImage(newImg, 0, 0, null);
        RescaleOp op = new RescaleOp(factor, 0, null);
        bufferedImage = op.filter(bufferedImage, null);
        ImageIcon newImage = new ImageIcon(bufferedImage);
        JLabel label = new JLabel(newImage);
        label.setBounds((1500 - newWidth) / 2, (800 - newHeight) / 2, newWidth, newHeight);
        tempPanel.add(label);

        // Set size of panel and frame
        tempPanel.setSize(1500, 800);
        tempFrame.setTitle("Image Viewer");
        tempFrame.setSize(1500, 800);
        tempFrame.setVisible(true);
        tempFrame.setResizable(true);
        tempFrame.add(tempPanel);
    }

    public void resizePanel() {

        // Create a panel for resizing the image
        JPanel resizePanel = new JPanel();
        resizePanel.setLayout(null);
        resizePanel.setBackground(backgroundColor);

        // Create text fields and labels for width and height
        this.widthTextField = new JTextField();
        this.heightTextField = new JTextField();
        JLabel widthLabel = new JLabel("Width");
        JLabel heightLabel = new JLabel("Height");

        // Set bounds for text fields, labels, and buttons which looks good and symmetrical
        this.showResizeButton.setBounds(250, 150, 200, 30);
        this.backButton.setBounds(250, 200, 200, 30);
        widthLabel.setBounds(250, 100, 100, 30);
        heightLabel.setBounds(250, 50, 100, 30);
        this.widthTextField.setBounds(350, 100, 100, 30);
        this.heightTextField.setBounds(350, 50, 100, 30);

        // Add text fields, labels, and buttons to resizePanel
        resizePanel.add(this.widthTextField);
        resizePanel.add(this.heightTextField);
        resizePanel.add(widthLabel);
        resizePanel.add(heightLabel);
        resizePanel.add(this.showResizeButton);
        resizePanel.add(this.backButton);

        // Add action listeners to buttons
        this.backButton.addActionListener(_ -> {
            resizePanel.setVisible(false);
            this.getContentPane().removeAll();
            this.mainPanel();
            this.revalidate();
            this.repaint();
        });

        // Add resize panel to our frame
        this.getContentPane().removeAll();
        this.add(resizePanel);
        this.revalidate();
        this.repaint();
    }

    public void showResizeImage(int height, int width) {

        // If no image is selected, show a message dialog
        if (this.img == null) {
            JOptionPane.showMessageDialog(null, "Please select an image first");
            return;
        }

        // Create a new frame and panel
        JFrame tempFrame = new JFrame();
        JPanel tempPanel = new JPanel();
        tempPanel.setBackground(backgroundColor);
        tempPanel.setLayout(null);

        // Add a back button
        this.backButton.setBounds(10, 10, 100, 30);
        tempPanel.add(this.backButton);

        // Action listener for back button to take back to the resize panel
        this.backButton.addActionListener(_ -> {
            tempFrame.dispose();
            this.getContentPane().removeAll();
            this.resizePanel();
            this.revalidate();
            this.repaint();
        });

        // Change the size of the image based on the width and height provided by the user in the last panel
        Image newImg = this.img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon newImage = new ImageIcon(newImg);
        JLabel label = new JLabel(newImage);
        label.setBounds((1500 - width) / 2, (800 - height) / 2, width, height);
        tempPanel.add(label);

        // Set size of panel and frame
        tempPanel.setSize(1500, 800);
        tempFrame.setTitle("Image Viewer");
        tempFrame.setSize(1500, 800);
        tempFrame.setVisible(true);
        tempFrame.setResizable(true);
        tempFrame.add(tempPanel);
    }

    public void actionPerformed(ActionEvent e) {

        // Action listeners for all buttons
        if (e.getSource() == resizeButton) {
            this.resizePanel();

        } else if (e.getSource() == showImageButton) {
            this.showOriginalImage();

        } else if (e.getSource() == grayscaleButton) {
            this.grayScaleImage();

        } else if (e.getSource() == showResizeButton) {
            String widthText = this.widthTextField.getText();
            String heightText = this.heightTextField.getText();

            // Check if width and height are empty
            if (widthText.isEmpty() || heightText.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter values for width and height");
                return;
            }

            // Get width and height from text fields
            int width = Integer.parseInt(this.widthTextField.getText());
            int height = Integer.parseInt(this.heightTextField.getText());
            this.showResizeImage(height, width);

        } else if (e.getSource() == brightnessButton) {
            this.brightnessPanel();

        } else if (e.getSource() == showBrightnessButton) {
            String brightnessText = this.brightnessTextField.getText();

            // Check if brightness is empty
            if (brightnessText.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a value for brightness");
                return;
            }

            // Get brightness factor from text field
            float factor = Float.parseFloat(this.brightnessTextField.getText());
            this.showBrightnessImage(factor);
            this.brightenFactor = factor;

        } else if (e.getSource() == selectFileButton) {
            this.chooseFileImage();

        } else if(e.getSource() == closeButton) {
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
    }

    public static void main(String[] args) {
        new ImgViewer();
    }
}