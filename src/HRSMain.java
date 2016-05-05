import java.awt.EventQueue;

public class HRSMain {
//Known issue: appointment list doesn't refresh
	public static void main(String args[])
	{

        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                GUI newGUI = new GUI();
            }
        });
		ManageAppointments newApt = new ManageAppointments();
		newApt.viewAppointments();
    }

}