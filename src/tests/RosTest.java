package tests;

import ros.Publisher;
import ros.RosBridge;
import ros.RosListenDelegate;

import java.util.HashMap;
import java.util.Map;

/**
 * Example of connecting to rosbridge with publish/subscribe messages. Takes one argument:
 * the rosbridge websocket URI; for example: ws://localhost:9090.
 * @author James MacGlashan.
 */
public class RosTest {

	public static void main(String[] args) {

		if(args.length != 1){
			System.out.println("Need the rosbridge websocket URI provided as argument. For example:\n\tws://localhost:9090");
			System.exit(0);
		}

		RosBridge bridge = RosBridge.createConnection(args[0]);
		bridge.waitForConnection();

		/*
		bridge.subsribe("/burlap_chatter", "burlap_msgs/burlap_state",
				new RosListenDelegate() {
					@Override
					public void receive(Map<String, Object> data, String stringRep) {
						System.out.println("I received: " + stringRep);
					}
				});
		*/


		Publisher pub = new Publisher("/bridge", "std_msgs/String", bridge);
		final Map<String, String> strData = new HashMap<String, String>();

		for(int i = 0; i < 100; i++) {
			strData.put("data", "hello from java " + i);
			System.out.println("sending...");
			pub.publish(strData);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
