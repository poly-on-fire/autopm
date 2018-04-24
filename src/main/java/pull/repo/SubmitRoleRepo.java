package pull.repo;

import com.google.firebase.database.*;
import com.google.firebase.database.core.Path;
import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;
import org.springframework.context.ApplicationContext;
import pull.domain.Role;
import pull.domain.SubmitRole;
import pull.util.Db;

/*
This entire class is a total effing throw-away planning on replacing it with JWT system later
there's a lot of stuff here that is stupid and illogical but just leaving it here because
it's going to be replaced with something more rational.
 */
public class SubmitRoleRepo {
    private DatabaseReference submitRoleRef;
    private String path;
    private ApplicationContext applicationContext;


    public SubmitRoleRepo(String path, ApplicationContext applicationContext) {
        super();
        this.applicationContext = applicationContext;
        submitRoleRef = Db.coRef(path);
        this.path = path;
        init();
    }


    private void init() {

        submitRoleRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                SubmitRole submitRole = null;
                try {
                    submitRole = dataSnapshot.getValue(SubmitRole.class);
                    if (submitRole.uid != null) {
                        processSubmittal(submitRole,dataSnapshot.getKey());
                    } else {
                        //TODO log properly
                        //these happened before then quit happening not sure why but still keeping an eye out
                        System.out.println("\n\nWEIRD FAIL\n" + "\nDATA SNAPSHOT\n" + dataSnapshot.toString() + "\nPATH\n" + path + "\nSUBMITROLE\n" + submitRole);
                    }
                } catch (Exception e) {
                    //TODO log properly
                    System.out.println("\n\nWEIRD EXCEPTION\n" + e.getMessage() + "\nDATA SNAPSHOT\n" + dataSnapshot.toString() + "\nPATH\n" + path + "\nSUBMITROLE\n" + submitRole);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
                SubmitRole submitRole = dataSnapshot.getValue(SubmitRole.class);
                System.out.println(submitRole.toString());
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw new RuntimeException(databaseError.getMessage());
            }
        });
    }

    private void processSubmittal(SubmitRole submitRole, String key) {
        DatabaseReference ref = Db.coRef("/users/" + submitRole.uid);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.child("role").exists() && !submitRole.processed && submitRole.uid != null) {
                    String itemPath =  dataSnapshot.getRef().getPath() +"/submitRole/" +  key;
                    try {
                        DatabaseReference submitRoleRef = Db.coRef(itemPath);
                        submitRoleRef.child("processed").setValue(true);
                        ref.child("role").setValue(submitRole.role);
                    } catch (Exception e) {
                        //TODO log properly
                        System.out.println("PLEASE INVESTIGATE STACK TRACE");
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError arg0) {
            }
        });
    }

}
