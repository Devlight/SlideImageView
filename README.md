SlideImageView
===================
Simple and convenient library that allows you to slide images through a view.

![enter image description here](https://lh3.googleusercontent.com/-bzGxaA_Oyk4/VR-miVWxGrI/AAAAAAAAAZE/tuTVk2dcRyQ/s0/Untitled-4.png "logo_title.png")

U can check the sample app [here](https://github.com/GIGAMOLE/SlideImageView/tree/master/app).

Download
------------

You can download a .jar from GitHub's [releases page](https://github.com/GIGAMOLE/SlideImageView/releases).

Or use Gradle jCenter:

    dependencies {
        repositories {
            mavenCentral()
            maven {
                url  'http://dl.bintray.com/gigamole/maven/'
            }
        }
        compile 'com.github.gigamole.slideimageview:library:+'
    }

Or Gradle Maven Central:

    compile 'com.github.gigamole.slideimageview:library:1.0.0'

Or Maven:

    <dependency>
	    <groupId>com.github.gigamole.slideimageview</groupId>
	    <artifactId>library</artifactId>
	    <version>1.0.0</version>
	    <type>aar</type>
    </dependency>

Android SDK Version
=========
SlideImageView requires a minimum sdk version of 10.

Sample
========

You can set such parameters as:

 - source
 - duration
 - slowdown

Check out in code init:

    SlideImageView slidedImageView = (SlideImageView) findViewById(R.id.img_slide);
    slidedImageView.setDuration(50000);
    slidedImageView.setSource(R.drawable.background);
    slidedImageView.setSlowdown(false);

And XML init:

    <com.gigamole.slideimageview.lib.SlideImageView
        android:id="@+id/img_slide"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        slide:source="@drawable/background"
        slide:duration="50000"
        slide:slowdown="false"/>

Getting Help
======

To report a specific problem or feature request, [open a new issue on Github](https://github.com/GIGAMOLE/Mill-Spinners/issues/new). 

License
======
Apache 2.0. See LICENSE file for details.


Author
=======
Basil Miller - @gigamole