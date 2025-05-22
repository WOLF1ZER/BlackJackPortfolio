import javax.swing.SwingUtilities;
import guis.LoginFormGUI;

public class App {

public static void main(String[] args){
SwingUtilities.invokeLater(new Runnable() {
    @Override
    public void run(){
     new LoginFormGUI().setVisible(true);



    }
});
}    
}
