*poly-on-fire* is a collection of proof-of-concept projects using:

 [Polymer](href="https://www.polymer-project.org/") + [Firebase](href="https://firebase.google.com/")

|[**_Pete Carapetyan_**](http://appwriter.com)|  [TL;DR? blog](https://betterologist.net/2018/04/poly-on-fire-polymer-on-firebase/) |[TL;DR? _video:_](https://youtu.be/P9DwkqqUxNs)|
| --- | --- | --- |
|<a href="http://appwriter.com"><img class="style-svg" src="https://betterologist.net/wp-content/uploads/2016/05/pete-300x297.jpg" alt="pete" width="116" height="115" /></a>|<a href="https://betterologist.net/2018/04/poly-on-fire-polymer-on-firebase/" ><img class="style-svg" src="http://docs.datafundamentals.com/txt.png" alt="jammazwanPhotoSmall" width="200" height="116" /></a>|<a href="https://youtu.be/P9DwkqqUxNs"><img class="style-svg" src="https://betterologist.net/wp-content/uploads/2016/05/jamzVid1.png" alt="about" width="115" height="115" /></a>|


##### A project for learning an aspect of developing a Polymer app, deployed on Firebase hosting.

The idea is to prove out an approach or component in the simplest project first, before combining it with other code in a real project.

----

# \<poly-on-fire-autopm\>

## What it does:

Unlike other *poly-on-fire* projects, autopm is not a client written in Polymer, but a back end server written in Java.
It uses the [Spring Boot](https://projects.spring.io/spring-boot/) and [Apache Camel](http://camel.apache.org/) stacks, in addition to the Firebase Admin SDK.

Autopm runs on a server and listens for database changes to respond to. Things that it does on such a change are many, 
but include these functions:

* Generating HTML pages and 
* Listening to file system changes and deploying html to a sibling firebase hosting project
* Firing off a daily email run to selected recipients on selected topics
* Misc admin such as responding to promotion requests


## Here is what it looks like when it is running

[youtube demo](blah)

blah

## How I Install and Run It

from my bash shell:

```
mvn clean install -DskipTests
java -jar target/autopm.jar
```



## Pre-requirements

You will need to have maven, java, and nvm installed, or else modify refresh_npm.sh accordingly to use your current version of npm. 
Before you run the above maven and jar commands, you also need to have the appropriate firebase hosting project running in a sibling folder, for the generated html files to be able to deploy properly.
Then, you run this script from the autopm folder, to initialize the sibling folder.

```
source refreshRmRf.sh
```

## Notes

Nvm sets node version to latest stable except when functions are used in the project, when it uses a lower version to match requirements of functions API.

This is a polymer2 app modeled somewhat after polycast 61 by rob, which is a polymer1 demo.

This app arbitrarily uses a hide function to do it's demo.

There are better ways to do this hide, but since that isn't the core of the functionality, doesn't matter here.

This demo barely touches the redux api. When I did a real implementation after this demo app, I had to do a lot more experimentation, specifically within the reducer.
