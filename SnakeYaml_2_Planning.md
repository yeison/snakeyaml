# Feature Requests #

## API Upgrades ##
Users have repeatedly asked for kinds of customization that are difficult or impossible as-is. Although the library is reasonably flexible, it could be more flexible.

API Goals:
  * Provide as much information about the underlying YAML document as possible.
    * For example, track implicit / explicit tags separately.
  * Support customization by dependency injection (rather than extension).
  * Clearly define which classes are user-extensible and which classes should not be modified.
  * Streamline the public API: minimize the number of classes users must interact with for any given task.
  * Clearly define which classes belong to which "level" (low-level, high-level) of the API.

## Performance ##

SnakeYAML is not performance critical. However, we should develop reliable speed metrics and track performance changes between versions.

For the sake of performance, it may be prudent to offload some of the reflection work to a library:
  * http://code.google.com/p/reflectasm/
  * http://code.google.com/p/reflectify-protocol/

On the other hand, this would add a dependency to SnakeYAML, which has never had any before.

## Portability ##

SnakeYAML should support Java 6+ as well as Android. The only obstacle here seems to be the use of Introspector, which is not available on Android. It looks like Introspector could be replaced with portable code in all cases.

If one of the libraries mentioned under Performance is used, it may supply tools for dealing with portability issues. Or, it may complicate portability, depending on whether Android supports the dynamic bytecode generation facilities those libraries rely on.

## Build System Support ##

Requests have been made to support build systems (or at least dependency-management systems) other than Maven. See this thread: https://groups.google.com/forum/?fromgroups#!topic/snakeyaml-core/Es2amiNZpPc

Possible systems to support:
  * Ivy
  * Ant
  * Maven
  * Gradle
  * SBT
  * Polyglot Maven

# Documentation Requirements #

## Introduction ##

SnakeYAML's webpage needs a full introduction to how the library works. This must be in addition to the existing FAQ-style pages.

At minimum, these should cover the following concepts:
  * Multi-layer approach: there are "low level" and "high level" sections of SnakeYAML's API, and they must be clearly explained and delineated.
  * The (Read -> Scan -> Parse -> Compose -> Construct) and (Represent -> Emit -> Serialize) pathways.
  * How do users interact with SnakeYAML?
    * Which classes should be extended? Which should not?
    * Which classes support which options / customization? How?
  * Naming scheme

In general, documentation should assume only a familiarity with the YAML specifications. _Library users should NOT need to know the exact specification of the language in order to use the library._

## Tutorials ##

Tutorials should be full, separate pages (on the Wiki?) which provide detailed explanations and readable code snippets for common use-cases.

Example Use-cases:
  * Loading files to beans. Dumping beans to files.
  * Loading multi-document streams. Dumping multi-document streams.
  * Loading YAML to a Node tree, manipulating it, and dumping it back out.
  * Sending / receiving messages over a stream.
  * Communicating with other prominent YAML implementations.


## JavaDoc ##

The entire SnakeYAML project needs up-to-date, accurate JavaDoc. Particular attention should be paid to the most commonly-used classes.