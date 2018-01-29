### Focus

This document focuses on both workflow, and also on server restart as it relates to not gumming up the works.

### Calls from the UI

It is interesting to note that all back end services in this system are called from the UI by adding, updating, or deleting a firebaseDb record. This so profoundly weird that it is almost upsetting to me. 

But it should work fine.

It is also notable that this design was iterative in nature and is completely different than I imagined it would be. So if you come back and review it months or years later and scratch your head, don't feel like the lone stranger. This event based CQRS stuff is really odd, and this is my first foray with same.

### General Workflow

##### UI Managed Workflow

 * UI initiates both topicSignup and emailStart from receipt of emailLog.add(), which ends up being a message, which is destroyed as soon as it is received.
 * Optional for prospect: Distributor enrolls someone in a topic manually via TopicSignup.add()
 * Emails to Downline: Some topics are for downline only.
    * This is an admin function only, and topicSignup is not involved.
    * Selection of group is implicit, via selection of topicType
 * Prospect unsubscribes via TopicSignup.unsubcribe=true
 
##### Daily Admin Background Functions
 
 * Actual subscribe of prospect happens via EmailStart.add() from TopicSignup.add(verify=true)
 * TopicDay is maintained in real time, in db, not in memory. This says that XyzTopic has emails going out on 1st, 5th, and 10th days. A change in TopicDay spawns immediate changes in all downstream Email records.TopicDay knows nothing about it's subscribers.
 * Email is maintained in real time, in db and also only partially in memory. This says that Jimmy gets an email from XyzTopic on March 3rd. But the in-memory map only includes that record on the actual day of March 2nd and March 3rd, until the email is sent. The in memory map is essentially a "day before" buffer. This is one of the more expensive and complex operations, as a change to either of TopicDay or EmailStart triggers changes to Email
 * An in memory function is always monitoring Email for bad combinations. If a downline is getting multiple emails in one day, maybe that's not so bad. But if Jimmy the prospect is getting more than one, something is probably very wrong and this function should over-ride it and prevent that, somehow.
 * A daily timed internal send() event batches emails by sending each email from Email, and logging that send into EmailBatch. This clears the memory buffer in EmailQueue, so now it is only holding new Email entries added since today's send.
 * Server restart is a special situation, see below for approaches to prevent chatter and erroneous/duplicate data.
 
### Which tables, and what portion of data, is also maintained in memory?

The short answer for which tables is:

 * As few as tables as possible.
 * Only that portion of data which is advantageous to maintain in memory.
 
The longer answer is - this is magic of the whole CQRS system. Hyperactive repeated queries of the same large data sets for frequently asked questions would seem to be a bad design smell. But if you maintain too much data in memory, that's a bad design smell too. Somehow ... there is a middle ground.

The key feature of this design is to eliminate un-necessary db chatter and/or memory footprint on the server.

### Email Batching Strategy

 * Daily streaming events fill up EmailQueue map.
 * An "R U Sure??" process does some kind of validation of the emails about to be sent, before sending.
    * You don't wan't to send the poor bloke 3 emails in one day. 
    * There may be other rules or filters that are applied.
 * When filters have been applied, and it's time to pull the trigger
    * The emails are sent
    * The map is cleared or emptied
   
By following this strategy, there doesn't have to be a lot of event chatter with the database. A small in memory data set maintains the "about to be sent" map, and all data passes one way.

What could possibly go wrong?

 * Woops: What happens when the server is restarted? All of the normal processes would load up the EmailQueue map as if it all needed to be sent again. So there would have to be a filter in that process, to keep this from happening.
 * A filter that checked every new entry against the existing EmailBatch persistence would do the trick, but that would mean supporting a ton of chatter for server startup, that would not, under normal operation, should not be necessary.

#### Workflow for Email Batching

Please note that much/most of this strategy is a remedy for possible duplication/error at unexpected server restarts.

* A global boolean **"isLoading"** switch is turned on for server startup, and once the initial "load" is completed, the switch is turned back off, until the next server restart.
* Once the **"isLoading"** switch is turned off, no more persistence checking against EmailBatch takes place. Thus the chatter goes down to zero.
* While **"isLoading"** switch is turned on, only emails that would be two days younger than today are even considered for inclusion in EmailBatchRepository memory map.
* While  **"isLoading"** switch is turned on, those same recent email candidates are checked against persistence of EmailBatch to make sure they haven't already been sent.
*  **"isLoading"** switch is maintained by some yet to be determined switch that detects when server load is complete, probably by some kind of time lapse check.

 