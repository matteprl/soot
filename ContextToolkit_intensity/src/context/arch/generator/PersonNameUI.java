package context.arch.generator;

import java.awt.*;
import java.util.Hashtable;
import context.arch.widget.WPersonNamePresence2;

public class PersonNameUI extends Frame {

  private final int WIDTH  = 140;
  private final int HEIGHT = 80;
  private final Color  outStatusColor  = Color.red;
  private final Color  inStatusColor   = Color.green;
  private final Color	 bgColor = Color.cyan;
  private int h, w;
  private String title = "Click Here";
  private int boxCount;	// the # of InOutBox displayed
  private final boolean DEBUG = false;
  public WPersonNamePresence2 parent;

  public PersonNameUI(WPersonNamePresence2 widget) {
    super();
    parent = widget;
    h = HEIGHT;
    w = WIDTH;
    setSize (w, h);
    setTitle (title);
    setBackground (bgColor);
  }
	
  public void setUI() {
    PDialog ctrls = new PDialog (this);
    ctrls.CreateControls();
  }

  // The handleEvent() method receives all events generated within the frame
  // window. You can use this method to respond to window events. To respond
  // to events generated by menus, buttons, etc. or other controls in the
  // frame window but not managed by the applet, override the window's
  // action() method.
  //--------------------------------------------------------------------------
  public boolean handleEvent(Event evt) {
    switch (evt.id) {
      // Application shutdown (e.g. user chooses Close from the system menu).
      //------------------------------------------------------------------
      case Event.WINDOW_DESTROY:
        // TODO: Place additional clean up code here
        dispose();
        System.exit(0);
        return true;      
      case Event.MOUSE_DOWN:
        System.out.println("mouse down");
        parent.setUser("Futakawa");
        parent.notify("update", null);
        return true;
      case Event.ACTION_EVENT :
        if (((String)evt.arg).equals("Gregory")) {
          System.out.println("Gregory");
          parent.setUser("Gregory Abowd");
          parent.notify("update", null);
        }
        else if (((String)evt.arg).equals("Anind")) {
          System.out.println("Anind");
          parent.setUser("Anind Dey");
          parent.notify("update", null);
        }
        else if (((String)evt.arg).equals("Daniel")) {
          System.out.println("Daniel");
          parent.setUser("Daniel Salber");
          parent.notify("update", null);
        }
        else if (((String)evt.arg).equals("Futakawa")) {
          System.out.println("Futakawa");
          parent.setUser("Futakawa");
          parent.notify("update", null);
        }
        else if (((String)evt.arg).equals("Ishiguro")) {
          System.out.println("Ishiguro");
	    parent.setUser("Ishiguro");
          parent.notify("update", null);
        }
        else if (((String)evt.arg).equals("Maria")) {
          System.out.println("Maria");
          parent.setUser("Maria Pimentel");
          parent.notify("update", null);
        }
        return true;		
      default:
        return super.handleEvent(evt);
    }			 
  }
}