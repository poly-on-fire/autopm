package pull.repo;

import com.google.firebase.database.*;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import pull.domain.User;
import pull.util.Db;

import java.util.HashMap;
import java.util.Map;

@Service
public class UsersRepo {
    // TODO this would get to be a pretty big memory footprint if the app ever got big. Is this the righteous way to do this
    DatabaseReference ref = Db.coRef("users");
    Map<String, UserRepo> userRepoMap = new HashMap<String, UserRepo>();
    ApplicationContext applicationContext;

    public UsersRepo(ApplicationContext applicationContext) {
        init();
        this.applicationContext = applicationContext;
    }

    private void init() {
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                UserRepo userRepo = new UserRepo(dataSnapshot.getKey(), applicationContext);
                userRepoMap.put(dataSnapshot.getKey(), userRepo);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
                Map<String, User> users = dataSnapshot.getValue(new GenericTypeIndicator<Map<String, User>>() {
                });
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                userRepoMap.remove(dataSnapshot.getKey());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {
                //don't even know what this would be for?
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw new RuntimeException(databaseError.getMessage());
            }
        });
    }

    public void ack() {
        System.out.println("\n\n\n\nUsersRepo is now a thing\n\n\n\n");
    }


}
