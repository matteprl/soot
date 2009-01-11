package context.arch.generator;

import java.util.Date;
import java.util.Vector;
import java.util.Hashtable;

import context.arch.widget.Widget;

import context.arch.widget.WTourRegistration;
import context.arch.widget.WTourDemo;
import context.arch.widget.WTourEnd;
import context.arch.widget.WDisplay;
import context.arch.widget.WidgetHandles;
import context.arch.server.STourId;
import context.apps.Tour.TourApp;


import context.arch.interpreter.IDemoRecommender;


import context.test.util.*;
import context.test.*;


/**
 * This class acts as a wrapper around a Java iButton reader.  Whenever
 * an iButton docks with the reader, the location of the reader, the
 * iButton id and the docking timestamp are stored and made available
 * to the context-aware infrastructure for polling, and can notify 
 * context widgets when data changes.
 *
 * @see com.ibutton.oc.JibMultiListener
 * @see context.arch.widget.Widget
 */
public class PositionIButton {

  private String location = ""; 
  private String currentid = "";
  private long currentTime = 0;

  //2008/7/9	
  private static PositionIButton sensor = null;  
  private Vector widgets = new Vector(); 
  //private Thread runner = null;  
  private static IButtonData data = null;
  private TestCase eventSequences = null;
  
  
  
  private PositionIButton(){
	  
  }
  
 
  
  
  public static PositionIButton getInstance(){
	  if(sensor == null){
		  sensor = new PositionIButton();
		  String currentid = toHexString(new int[]{1,2,3,4});
		  String location = "test";
		  data = new IButtonData(location, currentid, Long.toString(new Date().getTime()));	  
	  }
	  return sensor;
  }
  
  public void addListensor(Widget widget){
	  widgets.add(widget);
  }

  private void startSimulate(){
	  	 Widget widget = null;	
	  
		 ContextEvent event = new ContextEvent(0, 1, WTourRegistration.UPDATE);
		  
		 String info = event.context;
		 int duration = event.duration;
		  
		 for(int j = 0; j < widgets.size(); j ++){
			widget = (Widget)widgets.get(j);
			widget.notify(info, data);
		 }		 		 	
		 try{
			  Thread.sleep((long)10000);  
		 }catch(Exception e){
			System.out.println(e);  
		 }
		 		 
		 event = new ContextEvent(1, 1, WTourDemo.VISIT);
		 info = event.context;
		 duration = event.duration;		 
		 for(int j = 0; j < widgets.size(); j ++){
			widget = (Widget)widgets.get(j);
			widget.notify(info, data);
		 }		 
		 try{
			  Thread.sleep((long)10000);  
		 }catch(Exception e){
			System.out.println(e);  
		 }
		 		 
		 event = new ContextEvent(1, 1, WTourEnd.END);
		 info = event.context;
		 duration = event.duration;		 
		 for(int j = 0; j < widgets.size(); j ++){
			widget = (Widget)widgets.get(j);
			widget.notify(info, data);
		 }		 		 		 				 		 			
  }
  
  //notify widgets about every event in sequences
  public void startSampling(){
	  Widget widget = null;		  	
	  for(int i = 0; i < eventSequences.length; i ++){
		  ContextEvent event = (ContextEvent)eventSequences.get(i);
		  String info = event.context;
		  int duration = event.duration;
		  
		  for(int j = 0; j < widgets.size(); j ++){
			widget = (Widget)widgets.get(j);
			widget.notify(info, data);
		  }
		  try{
			  Thread.sleep((long)duration*1000);  			  
		  }catch(Exception e){
			System.out.println(e);  
		  }			 
	  }
  }
  
  /*//2008/7/9: a thread version
  public void quit(){	  
	  runner.stop();
  }
*/

  public static String toHexString(int[] arr) {
    String str = "";
    for (int i = 0;i < arr.length;i++) {
      if (arr[i] < 0x10) {
        str += "0";
      }
      str += Integer.toHexString(arr[i]).toUpperCase();
    }
    return str;
  }

  public IButtonData pollData() {
	    IButtonData data = new IButtonData(location, currentid, Long.toString(currentTime));
	    return data;
  }

  public static void main(String[] args){
	  try{
		  if(args.length == 1){
			  //1.retrieve test cases firstly
			  TestCaseGenerator maker = new TestCaseGenerator();		  
			  String file = Constant.baseFolder + "TestCase.txt";		  
			  Vector testSuite = maker.retrieveTestCases(file);
			  		  
			  //2.start widgets(This can done by ant, but it will make different outputs for different test cases)		  		
			  WTourRegistration tourStart = new WTourRegistration("test", 5700,false);
			  
			  WTourDemo	tourDemo = new WTourDemo("test", "http://127.0.0.1:5800/" +
						"C:/WangHuai/Martin/Eclipse3.3/eclipse/ICSE'09/ConfigFile.txt",
					"file:///" + System.getProperty("user.dir")+ "/DemoInfoFile.txt", false);
			  
			  WTourEnd	tourEnd = new WTourEnd("test", 5900,false);
			  
			  IDemoRecommender recommender = new IDemoRecommender(7000);
			  
			  WDisplay display = new WDisplay("test", 8000, "100", "200", "graphics",false);
			  
			    STourId server = new STourId(6200, "01020304", new WidgetHandles(),
					  "http://127.0.0.1:5700/"+ System.getProperty("user.dir") + "/ConfigFile.txt");
			  
			  TourApp tour = new TourApp(9999, "01020304",  "http://127.0.0.1:5700/"+ System.getProperty("user.dir") +
			  		"/ConfigFile.txt", "file:///" + System.getProperty("user.dir")+ "/DemoInfoFile.txt");
			  
			  
			  //3.start to simulate event sequences			  
			  TestCase testCase = (TestCase)testSuite.get(Integer.parseInt(args[0]));
			  sensor = PositionIButton.getInstance();
			  sensor.eventSequences = testCase;
			  sensor.startSampling();			  
			  //sensor.startSimulate();
			  
			  
			  
			  //4.stop widgets		  	
			  tourStart.quit();
			  tourDemo.quit();
			  tourEnd.quit();							 
			  recommender.quit();
			  display.quit();
			  server.quit();
			  tour.quit();
		  }
	  }catch(Exception e){
		  System.out.println(e);
	  }
	  
	  
  }
}
