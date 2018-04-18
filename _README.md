"poly-on-fire" is a collection of experimental projects using [Polymer](href="https://www.polymer-project.org/") + [Firebase](href="https://firebase.google.com/"), and is [explained in this blog](https://betterologist.net/2018/04/poly-on-fire-polymer-on-firebase/).

|[**_Pete Carapetyan_**](https://appwriter.com)| Science Experiments |[TL;DR? _about:_](https://youtu.be/vea51DzmXyA)|
| --- | --- | --- |
|<img class="style-svg" src="https://betterologist.net/wp-content/uploads/2016/05/pete-300x297.jpg" alt="pete" width="116" height="115" />|<img class="style-svg" src="http://docs.datafundamentals.com/txt.png" alt="jammazwanPhotoSmall" width="200" height="116" />|[<img class="style-svg" src="https://betterologist.net/wp-content/uploads/2016/05/jamzVid1.png" alt="about" width="115" height="115" />](https://youtu.be/vea51DzmXyA)|


##### A project for learning an aspect of developing a Polymer app, deployed on Firebase hosting.

----

# \<poly-on-fire-fit-test-df\>



## Here is what it looks like when it is running

[youtube demo](https://youtu.be/weDucpaEAac)

shown with dev tools open in chrome. If it fills with red you might check if you've run bower install

## How I Install and Run It

from my bash shell:

```
git clone git@github.com:poly-on-fire/poly-on-fire-fit-test-df.git
cd poly-on-fire-fit-test-df/
source refresh_npm.sh
cd public/
bower install
polymer serve
```

then run it from the browser

## Pre-requirements

You will need to have nvm installed, or else modify refresh_npm.sh accordingly to use your current version of npm. 

## Notes

Nvm sets node version to latest stable except when functions are used in the project, when it uses a lower version to match requirements of functions API.

This is a polymer2 app modeled somewhat after polycast 61 by rob, which is a polymer1 demo.

This app arbitrarily uses a hide function to do it's demo.

There are better ways to do this hide, but since that isn't the core of the functionality, doesn't matter here.

This demo barely touches the redux api. When I did a real implementation after this demo app, I had to do a lot more experimentation, specifically within the reducer.
