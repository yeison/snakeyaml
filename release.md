# Info #

Steps to release SnakeYAML:
  * check system date & time
  * add info to changes.xml & announcement.msg
  * hg pull -u
  * mvn clean package
  * mvn release:prepare -Dusername=uuu -Dpassword=ppp (to change, commit and push the changes to POM). User credentials are for the Google Mercurial repository, they can be stored in the local settings.xml file
  * mvn release:perform (to build and upload to the Sonatype staging repository)

Promote the published artifacts from the staging to the release repository (http://central.sonatype.org/pages/releasing-the-deployment.html)
  * close staging repository
  * release staging repository

  * mvn deploy -Prelease (build and deploy the next SNAPSHOT)

Get the source by the latest tag, build and upload the ZIP archive
  * hg update TAG
  * mvn clean package site
  * remove 'bin' folder and create ZIP
  * zip SnakeYAML-all-VERSION.zip -r snakeyaml
  * upload the archive to the project space

Apply new report
  * change the project reports at http://code.google.com/p/snakeyaml/source/checkout?repo=appengine
  * remove old folder under ${base}\snakeyamlrepo\www\releases\
  * add new generated site folder under ${base}\snakeyamlrepo\www\releases\
  * change ${base}\snakeyamlrepo\www\index.html to point to the new folder
  * deploy project reports at `AppEngine`

Announce to the community
  * apply info in wiki: changes and home
  * announce the release in yaml-core and snakeyaml mailing lists