Testing Java EE Applications using Arquillian
================================================================
This application demonstrates how Java EE applications can be effectively 
tested using Arquillian. The application contains a 
set of JUnit tests that you should examine. The tests will be run as part 
of the Maven build. We used JBoss EAP 7 for the application (see setup 
instructions below) but it should be easy to port to any Java EE 7 application
server such as GlassFish, WildFly, WebLogic or WebSphere.

Setup
-----
* Install JBoss EAP 7. It should be a simple matter of downloading the installation 
  jar and running the GUI wizard (you will need a Red Hat account). Just accept 
  all the defaults. Remember the admin password you choose.
* Please download this repository. You can use Git or just the simple zip
  download.
* The demo is just a simple Maven project under the [actionbazaar](actionbazaar)
  directory. You should be able to open it up in any Maven capable IDE, we used
  NetBeans.
* If desired setup JBoss EAP in your IDE; we did. At the current time, NetBeans does
  not directly support JBoss EAP 7 yet. You can use the NetBeans features for
  WildFly 10; it worked fine for us.
* Make sure JBoss EAP 7 is up and running before you run the tests.
* The tests in the Maven build are executed against a running JBoss EAP instance.
  You will need to configure 
  [this file] (actionbazaar/src/test/resources/arquillian.xml) with the details
  of your JBoss EAP installation. You must set the admin password for JBoss EAP.
* If desired, you can deploy and run the application manually. We did this both
  via NetBeans and by using the plain Maven generated war file.