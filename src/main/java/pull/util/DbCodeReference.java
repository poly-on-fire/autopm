package pull.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class DbCodeReference {
	void go() throws Exception {
		DatabaseReference ref = Db.info().getReference("somerealthing");
		DatabaseReference usersRef = ref.child("users");
		Map<String, User> users = new HashMap<String, User>();
		users.put("alanisawesome", new User("June 24, 1912", "Alan Turing"));
		users.put("gracehop", new User("December 10, 1906", "Grace Hopper"));
		usersRef.setValue(users);
		users.put("maryant", new User("November 2, 1755", "Marie Antoinette"));
		usersRef.setValue(users);
		DatabaseReference hopperRef = usersRef.child("gracehop");
		Map<String, Object> hopperUpdates = new HashMap<String, Object>();
		hopperUpdates.put("nickname", "Amazing Grace");
		hopperRef.updateChildren(hopperUpdates);
		Map<String, Object> userUpdates = new HashMap<String, Object>();
		userUpdates.put("alanisawesome/nickname", "Alan The Machine");
		userUpdates.put("gracehop/nickname", "Truly Amazing Grace");
		usersRef.updateChildren(userUpdates);
		Map<String, Object> userNicknameUpdates = new HashMap<String, Object>();
		userNicknameUpdates.put("alanisawesome", new User(null, null, "Alan The Machine"));
		userNicknameUpdates.put("gracehop", new User(null, null, "Amazing Grace"));
		usersRef.updateChildren(userNicknameUpdates);
		DatabaseReference dataRef = ref.child("data");
		dataRef.setValue("I'm writing data", new DatabaseReference.CompletionListener() {
			@Override
			public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
				if (databaseError != null) {
					System.out.println("Data could not be saved " + databaseError.getMessage());
				} else {
					System.out.println("Data saved successfully.");
				}
			}
		});
		DatabaseReference someOtherRealThingRef = Db.info().getReference("someOtherRealThing");
		DatabaseReference postsRef = someOtherRealThingRef.child("posts");
		DatabaseReference newPostRef = postsRef.push();
		newPostRef.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				Post post = dataSnapshot.getValue(Post.class);
				System.err.println(post);
			}

			@Override
			public void onCancelled(DatabaseError databaseError) {
				System.err.println("The read failed: " + databaseError.getCode());
			}
		});
		newPostRef.setValue(new Post("gracehop", "Announcing COBOL, a New Programming Language"));
		postsRef.push().setValue(new Post("alanisawesome", "The Turing Machine"));
		DatabaseReference pushedPostRef = postsRef.push();
		String postId = pushedPostRef.getKey();
		System.err.println(postId);
		final AtomicInteger count = new AtomicInteger();
		postsRef.addChildEventListener(new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
				Post newPost = dataSnapshot.getValue(Post.class);
				System.out.println("Author:           " + newPost.author);
				System.out.println("Title:            " + newPost.title);
				System.out.println("Previous Post ID: " + prevChildKey);
				int newCount = count.incrementAndGet();
				System.out.println("Added " + dataSnapshot.getKey() + ", count is " + newCount);
			}

			@Override
			public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
				Post changedPost = dataSnapshot.getValue(Post.class);
				System.out.println("The updated post title is: " + changedPost.title);
				long numChildren = dataSnapshot.getChildrenCount();
				System.out.println(count.get() + " == " + numChildren);
			}

			@Override
			public void onChildRemoved(DataSnapshot dataSnapshot) {
				Post removedPost = dataSnapshot.getValue(Post.class);
				System.out.println("The blog post titled " + removedPost.title + " has been deleted");
			}

			@Override
			public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {
			}

			@Override
			public void onCancelled(DatabaseError databaseError) {
			}
		});
		/*
		 * Reading Data Once
		 * 
		 * In some cases it may be useful for a callback to be called once and
		 * then immediately removed. We've created a helper function to make
		 * this easy:
		 */
		// The number of children will always be equal to 'count' since the
		// value of
		// the dataSnapshot here will include every child_added event triggered
		// before this point.
		ref.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				long numChildren = dataSnapshot.getChildrenCount();
				System.out.println(count.get() + " == " + numChildren);
			}

			@Override
			public void onCancelled(DatabaseError databaseError) {
			}
		});

		// Jan 26 2017 peeled out stuff up to the Querying Data section.
		// It appears that this page is in active revision so ....
	}

	public static class User {

		public String date_of_birth;
		public String full_name;
		public String nickname;

		public User(String date_of_birth, String full_name) {
			this.date_of_birth = date_of_birth;
			this.full_name = full_name;
		}

		public User(String date_of_birth, String full_name, String nickname) {
			this.date_of_birth = date_of_birth;
			this.full_name = full_name;
			this.nickname = nickname;
		}

	}

	public static class Post {

		public void setAuthor(String author) {
			this.author = author;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String author;
		public String title;

		public Post() {
		}

		public Post(String author, String title) {
			this.author = author;
			this.title = title;
		}

		public String toString() {
			return author + " wrote " + title;
		}

	}
}
