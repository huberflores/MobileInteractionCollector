Mobile Interaction Collector
==========================

Developed at HKUST(The Hong Kong University of Science and Technology) by [SyMLab](http://symlab.ust.hk/index.html).

The application implements a data collector mechanism (ContentProvider) based on SQLite. The data collected consists of gesture, and sensor information provided by the user while using the application.

The aim of the application is to collect implicit feedback that can be utilized to improve the perception of the software developer for building applications with better QoE adaptability.

Installation
=============

- The project is mavenized, so you just need to import it into Eclipse.

- Set /libs folder in your classpath

Project -> right click -> properties -> Java build path -> Libraries -> Add class folder -> check libs/ folder

Alternatively, build in terminal using


```xml
$ git clone https://github.com/huberflores/XMPPNotificationServer.git
````

```xml
$ mvn install
````

Application flow
==============

This section shows the flow of the application.

![alt text](https://raw.github.com/huberflores/MobileInteractionCollector/master/Screenshots/flow1.png "1-2")

![alt text](https://raw.github.com/huberflores/MobileInteractionCollector/master/Screenshots/flow2.png "3-4")

![alt text](https://raw.github.com/huberflores/MobileInteractionCollector/master/Screenshots/flow3.png "5-6")

![alt text](https://raw.github.com/huberflores/MobileInteractionCollector/master/Screenshots/flow4.png "7-8")


