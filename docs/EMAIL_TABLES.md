###  emailLog 

 * write: true, read:false
 * Raw data is collected from .info before enrolled or logged in
 * deleted as soon as it is read, and moved to a safer place: **topicSignup**
 * assumed that this always the .info page that adds this information
 
Fields
 * email
 * topic
 * timestamp

###  user/emailContent

 * write: user, read:false
 * actual content of an email
    
Fields
 * topicKey
 * day
 * content
 * subject
 * productCategory
 
###  user/emailLookup

 * write: false, read:false
 * used to populate the list display for email selection
    
Fields
 * topicKey
 * topicName
 * topicType
 * content - actually, a substring 
 * subject - actually, a substring
  
### topicSignup

 * write:false, read:false
 * when any other function of email
 * on write to emailTopicDateBy
                passed verify step
 * on email send, first lookup email
                if it is on this list and not unsubscribe... 
                
Fields
 * email
 * verified boolean
 * unsubscribe timestamp
                
                
### emailStart

 * write: false, read:false
 * emails subscribed to a specific topic. This is the main point of entry from the outside of the system.
            
Fields
 * emailAddress - recipient
 * topicKey
 * date - of day 0
 * by - null for conventional enrollment, or Key of distributor who manually added
            
            
### topicDay

 * write: user, read:false
 * answers the question "is there an email that goes out for this day of the topic?"
 * is this data? Or a function?
    * it would probably be faster as data
    * but would then require more careful re-writes every time changes, thus, do this later when worried about running program fast 
    
Fields
 * topicKey
 * day
 * emailKey
    
                    
### email

 * write:false, read:false
 * historical log of email only, of questionable purpose.
 * drives the batch process
 * in-memory EmailTopicDateRepository map is cleared every day, as data is dumped into EmailBatchRepository.
 
Fields
 * email - recipient
 * emailKey
 * topicKey
 * hashcode - of email text - functions as boolean (is same as current or not)
 * date
   