### autocsdf NOTES:

In the interest of expediency and pragmatism, I broke a ridiculous number of design standards which I might normally hold dear to my heart as an architect.

They are listed below. 

##### Design includes questionable use of

 * **camel** where straight java would have worked
 * **spring** where straight java would have worked
 * **spring boot** where straight java would have worked
 * **maven** to run the jar where git into eclipse is expected deployment model
 * **a desktop machine** rather than running on a remote server
 * **5 minute delay** to prevent hyperactive firebase deploys
 * **firebase deploy** where a more incremental deploy would be smarter
 * **static AppCtx.java** file instead of figuring out why I couldn't autowire
 * **in-memory firebase database listeners** when I have no idea how burdensome that will end up being
 * **non-compliant package naming conventions** where co.clouddancer... would be correct but longer
 * **untested classes** where would TDD would have been better
 * **public String ...** non-beans for firebase only data structures
 * **wide open** firebase database rules where tighter rules would be logical
 * **admin APIs** and usage where UI for data entry would be more helpful
 * **values as keys** in simpler, static data such as nslevel
 * **half assed firebase listeners** where more carefully designed and consistent listeners would be quite helpful
 * **inconsistent spring configuration** from both/either xml and java files
 
 Rather than justify each of the above, the short explanation is that I wrote this software for free with time stolen from other, more important pursuits. Every consideration was given to getting the project done in a half assed but sufficient manner.
 
 If the project is a huge success, it will fund fixing these technical debt issues as is appropriate. If not, it won't matter how well it was designed anyway.