package pull.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseCredentials;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Db {
	private static FirebaseDatabase pmFirebaseDatabase = null;
	private static FirebaseDatabase infoFirebaseDatabase = null;
	private static boolean initialized = false;
	
	private static void checkInit(){
		if (!initialized) {
			init();
		}
	}
	
//	pullmodel-5d998-firebase-adminsdk-0a7wq-e6cbda4a37

	private static void init() {
		FileInputStream coServiceAccount;
		FileInputStream infoServiceAccount;
		try {
			coServiceAccount = new FileInputStream(
					"../firebaseConfig/pullmodel-5d998-firebase-adminsdk-0a7wq-e6cbda4a37.json");
//					"../../../.cdsf/clouddancerco-firebase-adminsdk-a25d8-a3c770995c.json");
			infoServiceAccount = new FileInputStream(
					"../firebaseConfig/clouddancerinfo-firebase-adminsdk-3mhdi-0951bb76ab.json");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		FirebaseOptions coOptions = new FirebaseOptions.Builder()
				.setCredential(FirebaseCredentials.fromCertificate(coServiceAccount))
				.setDatabaseUrl("https://pullmodel-5d998.firebaseio.com/").build();
		FirebaseOptions infoOptions = new FirebaseOptions.Builder()
				.setCredential(FirebaseCredentials.fromCertificate(infoServiceAccount))
				.setDatabaseUrl("https://clouddancerinfo.firebaseio.com/").build();
		FirebaseApp coApp = FirebaseApp.initializeApp(coOptions);
		FirebaseApp infoApp = FirebaseApp.initializeApp(infoOptions, "infodb");
		FirebaseAuth coAuth = FirebaseAuth.getInstance(coApp);
		FirebaseAuth infoAuth = FirebaseAuth.getInstance(infoApp);
		pmFirebaseDatabase = FirebaseDatabase.getInstance(coApp);
		infoFirebaseDatabase = FirebaseDatabase.getInstance(infoApp);
		initialized = true;
	}

	public static FirebaseDatabase co() {
		checkInit();
		return pmFirebaseDatabase;
	}
	public static FirebaseDatabase info() {
		checkInit();
		return infoFirebaseDatabase;
	}

	public static DatabaseReference coRef(String path) {
		checkInit();
		return pmFirebaseDatabase.getReference(path);
	}
	public static DatabaseReference infoRef(String path) {
		checkInit();
		return infoFirebaseDatabase.getReference(path);
	}

}
