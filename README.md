SlideImageView
===================
Simple and convenient library that allows you to slide images through a view.

![enter image description here](https://lh5.googleusercontent.com/-2BiBFz-OW_s/VU_EPv33XMI/AAAAAAAAAcc/nGie_kBoZQ8/w244-h368-no/siv.gif)

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

    compile 'com.github.gigamole.slideimageview:library:1.1.2'

Or Maven:

    <dependency>
	    <groupId>com.github.gigamole.slideimageview</groupId>
	    <artifactId>library</artifactId>
	    <version>1.1.2</version>
	    <type>aar</type>
    </dependency>

Android SDK Version
=========
SlideImageView requires a minimum sdk version of 10.

Sample
========

You can set such parameters as:

 - source
 - axis (HORIZONTAL | VERTICAL)
 - rate

Check out in code init:

    SlideImageView slideImageView = (SlideImageView) findViewById(R.id.img_horizontal_slide);
    slideImageView.setSource(R.drawable.wide_background);
    slideImageView.setRate(0.3f);
    slideImageView.setAxis(SlideImageView.Axis.HORIZONTAL);

And XML init:

    <com.gigamole.slideimageview.lib.SlideImageView
        android:id="@+id/img_vertical_slide"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1"
        slide:source="@drawable/long_background"
        slide:axis="vertical"/>

Getting Help
======

To report a specific problem or feature request, [open a new issue on Github](https://github.com/GIGAMOLE/SlideImageView/issues/new).

License
======
Apache 2.0. See LICENSE file for details.


Author
=======
Basil Miller - @gigamole