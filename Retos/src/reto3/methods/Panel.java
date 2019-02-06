package reto3.methods;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import reto3.data.Worker;

public class Panel {
    public void showWorker(Worker worker){
        
        JLabel[] labels = new JLabel[3];
        prepareLabels(labels, worker);
        
        ImageIcon picture = selectImage(worker);
        
        JOptionPane.showMessageDialog(null, labels, "Workers",
                JOptionPane.PLAIN_MESSAGE, picture);
    }
    
    private void prepareLabels(JLabel[] labels, Worker worker){
        labels[0] = new JLabel("Name: " + worker.getName());
        labels[0].setForeground(Color.RED);
        labels[0].setFont(new Font("Cantarell", Font.ITALIC, 28));
        
        labels[1] = new JLabel(
                "Work: " + worker.getWork() +
                "Birthday: " + worker.getBirthday()+
                "Sex: " + Character.toString(worker.getSex()));
        
        labels[2] = new JLabel("Income: " + worker.getIncome());
        labels[2].setForeground(Color.BLUE);
        labels[2].setFont(new Font("Monospaced", Font.BOLD, 30));
    }
    
    private ImageIcon selectImage(Worker worker){
        String path = "src/reto3/data/", type = ".png",
                womenImage = "F", menImage = "M";
        String name = worker.getName();
        char sex = worker.getSex();
        
        String image = (new File(path + name + type).exists())?name:
                (sex == 'F')?womenImage:menImage;
        
        return new ImageIcon(path + image + type);
    }
    
    public static void main(String [] args){
        for(Worker w:Worker.loadWorkers("src/reto3/data/workers.txt"))
            new Panel().showWorker(w);
    }
}
