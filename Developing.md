

# How to develop SnakeYAML in Eclipse #

## Prerequisites ##
  * [Maven 2](http://maven.apache.org/docs/2.2.1/release-notes.html) or Maven 3
  * [Mercurial 1.5 or above](http://mercurial.selenic.com/wiki/Download)


## Getting source ##
Enable [Mercurial EolExtension](http://mercurial.selenic.com/wiki/EolExtension). _$HOME/.hgrc (Mac/Linux) or %USERPROFILE%\.hgrc (Windows)_
```
[extensions]
eol =
```
Clone the master repository:
```
hg clone http://snakeyaml.googlecode.com/hg/ my_snakeyaml 
```

To get the latest changes run the following command anywhere inside the project folder
```
hg pull -u http://snakeyaml.googlecode.com/hg/
```

## Building ##
To build your own release first change pom.xml to have the proper version
```
<version>1.8.555</version>
```

Run in the folder with source (where pox.xml is located):
```
mvn package
```

## Using Eclipse (or other IDEs) ##
Let Maven generate project files for Eclipse:
```
mvn eclipse:eclipse
```
or for IDEA
```
mvn idea:idea
```

Import the project to your IDE. Enjoy.

## Contributing  (without remote clone) ##
Make your changes. Run tests. **Write your tests**. When you are finished make a patch:
```
hg diff > my_patch.txt
```
Create [an issue](http://code.google.com/p/snakeyaml/issues/list) and attach your patch. If you are not sure [ask first](http://groups.google.com/group/snakeyaml-core).

## Contributing  (with remote clone) ##
  * make your own [remote repository](http://code.google.com/p/snakeyaml/source/clones)
  * 'hg clone URL'
To find out the URL click on the 'Checkout' link of your remote clone.
  * Change the source
  * [Commit your changes](http://mercurial.selenic.com/wiki/Commit) to your local repository
  * 'hg push URL' your changes to your remote repository
  * Let others know you have something to share. They can pull your changes.
You are free to do with your clone whatever you wish. But if you expect your changes to be accepted in the master repository please be aware of some important steps:
  * project commiters do not accept code which they do not fully understand.
  * discuss every change with other project participants. Mailing list or issue tracker are made for that. Why do you need the change ? What are the benefits ? Often a test is the best way to explain what you need to achieve.
  * do not commit further changes before you get the green light for already committed stuff (otherwise nothing will be accepted and you have to do the exercise once again in another clone)
  * do not forget to pull the changes from the master repository. The longer the clone stands alone the more difficult it is to pull the incoming changes from the master repository (you may need to merge).

## Generating reports ##

Run
```
mvn site
```
(coverage report helps to identify code which is not run in tests )

Run
```
mvn clean site -Pfast
```
to skip stress tests and build quickly coverage report

## Code conventions ##
Import this file for Eclipse formatter:

src/etc/Eclipse-format.xml

## Testing ##
Try to develop a test with a clear input and output expectation before implementing a feature. When developing a test, name the test class (Feature)Test.java and place it in the most relevant package under the src/java/test folder. This is important: if your file name does not end in "Test.java", then it will not be run as one of the automated tests!

To run all tests together:
```
mvn test
```

This command produces a lot of output, so it is typically helpful to pipe it to a file, or a utility like "less" or "more".

# Architecture #
In general SnakeYAML tries to follow the recommendation:
http://yaml.org/spec/1.1/#id859333

Package names normally co-inside with the task (compose, construct)

# Communication #
When you communicate via the mailing list or the issue tracker try to respect some basic rules. They really help to build and maintain the Open Source community spirit:
  * if you expect that community is going to spend some time to answer to your question then the community expects that you will spend some time to give your feedback
  * when you began to communicate to not disappear without any notice. The community needs to decide what to do with your message or your issue. It should be clear how to continue: close, fix, investigate further, etc
  * in general, it more polite to at least say 'thank you' if you received the help you were asking for. It also shows that you do not expect any further assistance.
  * sign the message with your name. Then later it is easier to track who said what.

# Contribute #
If you have a question on a topic which requires discussion or clarification send it to [the mailing list](http://groups.google.com/group/snakeyaml-core).

If you found a bug or you have a clear feature request [create an issue](http://code.google.com/p/snakeyaml/issues/entry).

The code may be submitted as a Mercurial patch or as a [clone](http://code.google.com/p/snakeyaml/source/clones).

If you wish to contribute to SnakeYAML and _you expect your changes to be accepted_ try to follow some simple steps:
  * Talk to people. Before you start anything raise a question in the mailing list or create an issue. The problem you wish to fix may not be a bug but a feature !
  * Be patient. It takes time to read, understand and respond to your messages.
  * Be responsive. If you do not have time just say you will return later and do not stay silent for weeks when somebody expects your feedback.
  * Respect the code conventions. (see above)
  * Make a separate remote repository for each logical change.
  * Do not put a few independent changes to a single changeset. It makes it simpler to understand the change. It also makes it possible to discuss each change separately. (Mercurial merges are also easier to perform on small changes.)
  * Please do not put your name into the source files. But feel free to update the AUTHORS list
  * Do not forget to remove remote repositories when they are not needed any longer. They consume some project's space which is restricted.

**Your contribution is always welcome !!!**