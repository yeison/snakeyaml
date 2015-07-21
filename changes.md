_`SnakeYAML` is normally released twice a year: August and February_
# Changes #
**1.16 (in Mercurial)**
  * Fix [issue 212](http://code.google.com/p/snakeyaml/issues/detail?id=212): Fix a typo in an error message in `ScannerImpl` (thanks to  michael koch)
  * Fix [issue 209](http://code.google.com/p/snakeyaml/issues/detail?id=209): Do not print special characters in the error message (thanks to joseph.gregory)
  * Investigate [issue 199](http://code.google.com/p/snakeyaml/issues/detail?id=199): Duplicate mapping keys
**1.15 (2015-02-19)**
  * Use Maven 3
  * Added split lines option to `DumperOptions` to allow line splitting to be disabled. (thanks to Ville Koskela)
  * Fix [issue 201](http://code.google.com/p/snakeyaml/issues/detail?id=201): Fix grammar error in exception message (thanks to eric.c.dahl and matthew.boston)
**1.14 (2014-08-29)**
  * Fix [issue 197](http://code.google.com/p/snakeyaml/issues/detail?id=197): Improve loading `JavaBeans` with fields using custom constructors (thanks to dev at minecrell.net)
  * Fix [issue 192](http://code.google.com/p/snakeyaml/issues/detail?id=192): Do not implement "Value Key Language-Independent Type" to make '=' a standard character (thanks to Marc)
  * Fix [issue 188](http://code.google.com/p/snakeyaml/issues/detail?id=188): Improve error message for invalid YAML document (thanks to shtilman)
  * Fix [issue 183](http://code.google.com/p/snakeyaml/issues/detail?id=183): Support Number class (thanks to darren.janeczek)
  * Fix [issue 182](http://code.google.com/p/snakeyaml/issues/detail?id=182): Double.POSITIVE\_INFINITY applied to float fields (thanks to darren.janeczek)
**1.13 (2013-09-05)**
  * Java 6 is now used to build the package. The source code stays compatible with Java 5
  * Fix [issue 178](http://code.google.com/p/snakeyaml/issues/detail?id=178): OSGi - Specify version for exported packages (thanks to Lukasz Dywicki)
  * Fix [issue 177](http://code.google.com/p/snakeyaml/issues/detail?id=177): Improve error report while parsing a `JavaBean` (thanks to Eric Brown)
  * Fix [issue 135](http://code.google.com/p/snakeyaml/issues/detail?id=135): Arrays of primitives are now fully supported (thanks to Jordan)
  * Fix [issue 174](http://code.google.com/p/snakeyaml/issues/detail?id=174): Duplicate anchors in an input document should not be an error (thanks to  llasram)
  * Fix [issue 172](http://code.google.com/p/snakeyaml/issues/detail?id=172): Using a locale with minimum number fraction digits breaks anchor generation (thanks to Michael Simons)
  * Fix [issue 171](http://code.google.com/p/snakeyaml/issues/detail?id=171): Use more generic generics in `BaseRepresenter` (thanks to Ash2kk)
**1.12 (2013-03-02)**
  * Fix [issue 169](http://code.google.com/p/snakeyaml/issues/detail?id=169): Make Constructor.typeDefinitions protected to be more flexible (thanks to Alexey)
  * Improve the error message when a TAB is used as indentation (due to enormous amount of misunderstanding). It now says: `"found character \t '\t(TAB)' that cannot start any token. (Do not use \t(TAB) for indentation)"`
**1.11 (2012-09-30)**
  * Fix [issue 158](http://code.google.com/p/snakeyaml/issues/detail?id=158): improve support for 32-bit characters (UTF-16 surrogate pairs) (thanks to Charlie)
  * Fix [issue 146](http://code.google.com/p/snakeyaml/issues/detail?id=146): empty tags should not force explicit document start (thanks to Charlie)
  * Fix [issue 156](http://code.google.com/p/snakeyaml/issues/detail?id=156): `setSkipMissingProperties` should not fail for non-scalar values (thanks to  Blake Matheny)
  * Fix [issue 155](http://code.google.com/p/snakeyaml/issues/detail?id=155): any generated output must be parsed without errors (thanks to  Robert Fletcher)
  * Fix [issue 154](http://code.google.com/p/snakeyaml/issues/detail?id=154): Add option to skip missing `JavaBean` properties when deserializing YAML into Java object (thanks to Shawn Lauzon)
  * Fix [issue 149](http://code.google.com/p/snakeyaml/issues/detail?id=149): Directives are no longer lost between documents (thanks to robinETmiller)
  * Fix [issue 147](http://code.google.com/p/snakeyaml/issues/detail?id=147): Serialized representation of character U+FFFD causes exception on deserialization (thanks to johnkarp)
  * Fix [issue 145](http://code.google.com/p/snakeyaml/issues/detail?id=145): exception.getMessage() must show the line number as exception.toString() does (thanks to theaspect)
  * Fix [issue 144](http://code.google.com/p/snakeyaml/issues/detail?id=144): improve type inference for [Compact Object Notation](http://code.google.com/p/snakeyaml/wiki/CompactObjectNotation) (thanks to tommy.odom)
  * Better [support](http://code.google.com/p/snakeyaml/wiki/howto#Android) for Android

**1.10 (2012-02-12)**
  * Apply some minor `FindBugs` and `PMD` recommendations
  * Fix [issue 141](http://code.google.com/p/snakeyaml/issues/detail?id=141): `TimeZone` is configurable in `DumperOptions` (thanks to Yaroslav)
  * Fix [issue 139](http://code.google.com/p/snakeyaml/issues/detail?id=139): merge should favor last key in map (thanks to davidahelder)
  * Fix [issue 136](http://code.google.com/p/snakeyaml/issues/detail?id=136): TAB is allowed inside a plain scalar. This is yet another [deviation from PyYAML](http://code.google.com/p/snakeyaml/wiki/Documentation#Deviation_from_PyYAML) (thanks to Uwe Kubosch)
  * Fix [issue 138](http://code.google.com/p/snakeyaml/issues/detail?id=138): Expose internal data of `ReaderException` (thanks to  asari.ruby)
  * Fix [issue 137](http://code.google.com/p/snakeyaml/issues/detail?id=137): Respect supplementary characters (thanks to joshhoyt)
  * Fix [issue 66](http://code.google.com/p/snakeyaml/issues/detail?id=66): use literal scalar style when the scalar contains multiple lines
**1.9 (2011-08-15)**
  * Fix [issue 130](http://code.google.com/p/snakeyaml/issues/detail?id=130): apply regular expression for !!float from YAML 1.2 specification (thanks to `xandey`)
  * Fix [issue 127](http://code.google.com/p/snakeyaml/issues/detail?id=127): `BaseRepresenter.representData(Object data)` has been made 'final' to avoid confusion
  * **Update public API** [issue 124](http://code.google.com/p/snakeyaml/issues/detail?id=124): Introduce `Yaml.loadAs()`, `Yaml.dumpAs()` and `Yaml.dumpAsMap()` methods. `JavaBeanLoader` and `JavaBeanDumper` are deprecated (thanks to `JordanAngold`)
  * Fix [issue 129](http://code.google.com/p/snakeyaml/issues/detail?id=129): Provide low level methods from objects to Nodes and Events (represent(Object), serialize(Node))
  * Fix [issue 125](http://code.google.com/p/snakeyaml/issues/detail?id=125): Provide Manen 3 support
  * Fix [issue 116](http://code.google.com/p/snakeyaml/issues/detail?id=116): Improved support for empty `JavaBeans` (thanks to Jim Peterson)
  * Fix [issue 121](http://code.google.com/p/snakeyaml/issues/detail?id=121): Close files in tests to avoid a possible file handle limit (thanks to Jaromir)
  * Fix [issue 112](http://code.google.com/p/snakeyaml/issues/detail?id=112): Improved support for parameterised types in collections (thanks to Lethargish)
  * Fix [issue 115](http://code.google.com/p/snakeyaml/issues/detail?id=115): parametrized `JavaBeans` fail to load and dump because they are treated as Maps (thanks to elkniwt)
  * Fix [issue 114](http://code.google.com/p/snakeyaml/issues/detail?id=114): do not remove root tags of `JavaBeans` when it is not explicitly requested (thanks to gileadis)
  * Fix [issue 111](http://code.google.com/p/snakeyaml/issues/detail?id=111): Long escaped tag URI sequences throw `BufferOverflowException` (thanks to `JordanAngold`)
  * Fix [issue 110](http://code.google.com/p/snakeyaml/issues/detail?id=110): introduce a package for external libraries and move there the 64Coder and the Google's URL encoder (thanks to dmitry.s.mamonov)
  * Fix [issue 96](http://code.google.com/p/snakeyaml/issues/detail?id=96): generate OSGi bundle (thanks to thegreendragon and longkerdandy)
  * Fix [issue 109](http://code.google.com/p/snakeyaml/issues/detail?id=109): ancient years must be dumped with leading zeros (thanks to cjalmeida)
  * Fix [issue 108](http://code.google.com/p/snakeyaml/issues/detail?id=108): Enum's name property shall be dumped instead of the 'toString()' output (thanks to `JordanAngold`)

**1.8 (2011-02-15)**
  * Fix [issue 104](http://code.google.com/p/snakeyaml/issues/detail?id=104): Expose Mark.index
  * Fix [issue 100](http://code.google.com/p/snakeyaml/issues/detail?id=100): `!!merge` tag properly merges also `JavaBeans` (thanks to `JordanAngold`)
  * [Issue 99](http://code.google.com/p/snakeyaml/issues/detail?id=99): Add an example for escaping line breaks in binary content
  * Fix [issue 59](http://code.google.com/p/snakeyaml/issues/detail?id=59): Simplify the way to dump `JavaBean` properties in a custom order
  * [Issue 97](http://code.google.com/p/snakeyaml/issues/detail?id=97): Add [an example](http://code.google.com/p/snakeyaml/source/browse/src/test/java/org/yaml/snakeyaml/issues/issue97/YamlSortedSetTest.java) to show how to use `JavaBean` property which is a `SortedSet` but encoded as a `YAML` sequence
  * Fix [issue 95](http://code.google.com/p/snakeyaml/issues/detail?id=95): Loading of generic collections with Array parameter(s)
  * [Issue 87](http://code.google.com/p/snakeyaml/issues/detail?id=87): Implement [Compact Object Notation](CompactObjectNotation.md)
  * Fix [issue 69](http://code.google.com/p/snakeyaml/issues/detail?id=69): `Iterable` is no longer serialised as sequence
  * Fix [issue 88](http://code.google.com/p/snakeyaml/issues/detail?id=88): Custom tag erased when referenced from generic collection
  * Fix [issue 82](http://code.google.com/p/snakeyaml/issues/detail?id=82): When object referenced from generic collection dumping may erase tags and prevent future loading
  * Fix [issue 79 and 101](http://code.google.com/p/snakeyaml/issues/detail?id=79): Context for error reporting consumes a lot of resources
  * Fix [issue 81](http://code.google.com/p/snakeyaml/issues/detail?id=81): Fix minor bug in Representer
  * Fix [issue 80](http://code.google.com/p/snakeyaml/issues/detail?id=80): Timestamp is not parsed properly when milliseconds start with 0 (thanks to `Sebastien Rainville`)
  * Cleanup unused code in deprecated Loader and Dumper

**1.7 (2010-08-12)**
  * **Update public API** ([issue 77](http://code.google.com/p/snakeyaml/issues/detail?id=77)): Loader and Dumper are deprecated. All the functionality was moved to the [Yaml class](http://code.google.com/p/snakeyaml/source/browse/src/main/java/org/yaml/snakeyaml/Yaml.java).
  * Fix [issue 74](http://code.google.com/p/snakeyaml/issues/detail?id=74): Do not use redundant tags for arrays which are `JavaBean` properties
  * Add examples to create scalars that match custom regular expression ([issue 75](http://code.google.com/p/snakeyaml/issues/detail?id=75)): [when the runtime class is not defined](http://code.google.com/p/snakeyaml/source/browse/src/test/java/examples/CustomImplicitResolverTest.java), and [when the runtime class is defined in the JavaBean](http://code.google.com/p/snakeyaml/source/browse/src/test/java/examples/CustomBeanResolverTest.java) (thanks to jon.p.hermes)
  * Fix [issue 73](http://code.google.com/p/snakeyaml/issues/detail?id=73): Provide support for [loading](http://code.google.com/p/snakeyaml/source/browse/src/test/java/org/yaml/snakeyaml/issues/issue73/SetAsSequenceTest.java) java.util.Set [as a sequence](http://code.google.com/p/snakeyaml/source/browse/src/test/resources/issues/issue73-3.txt). Also provide [an example](http://code.google.com/p/snakeyaml/source/browse/src/test/java/org/yaml/snakeyaml/issues/issue73/DumpSetAsSequenceExampleTest.java) to serialise a java.util.Set as a sequence. (thanks to birnbuazn)
  * Fix [issue 72](http://code.google.com/p/snakeyaml/issues/detail?id=72): Support `java.util.Collection` as a parent for List and Set (thanks to birnbuazn)
  * Fix [issue 55](http://code.google.com/p/snakeyaml/issues/detail?id=55): Allow direct field access bypassing setters and getters (thanks to mju and birnbuazn)
  * Fix [issue 69](http://code.google.com/p/snakeyaml/issues/detail?id=69): Serialise `Iterator` and `Iterable` as sequences
  * Add `JodaTime` [example](http://code.google.com/p/snakeyaml/source/browse/src/test/java/examples/jodatime/JodaTimeExampleTest.java) (thanks to Antony Stubbs)
  * Fix generic collections which contain other collections (thanks to Alex Maslov)
  * Fix [issue 67](http://code.google.com/p/snakeyaml/issues/detail?id=67): java classes containing non-ASCII characters in names are incorrectly encoded (thanks to Manuel Sugawara)
  * Fix [issue 65](http://code.google.com/p/snakeyaml/issues/detail?id=65): add checks for null arguments for `JavaBeanDumper` (thanks to lerch.johannes)
  * Fix [issue 64](http://code.google.com/p/snakeyaml/issues/detail?id=64): `ClassCastException` in `Representer` when working with `ParameterizedType` (thanks to Maxim)
  * Fix [issue 63](http://code.google.com/p/snakeyaml/issues/detail?id=63): Problems with recursive links when included in an array (thanks to Udo and Alex)
  * Add examples for dumping custom values for [!!bool](http://code.google.com/p/snakeyaml/source/browse/src/test/java/org/yaml/snakeyaml/types/BoolTagTest.java) and [!!null](http://code.google.com/p/snakeyaml/source/browse/src/test/java/org/yaml/snakeyaml/types/NullTagTest.java)
  * Fix [issue 61](http://code.google.com/p/snakeyaml/issues/detail?id=61): `ClassCastException` when dumping generic bean (thanks to Udo)
  * Fix [issue 58](http://code.google.com/p/snakeyaml/issues/detail?id=58): `JavaBeanDumper.dump` throws `NullPointerException` on list property with null element (thanks to jeff.caulfiel)
  * Add [an example](http://code.google.com/p/snakeyaml/source/browse/src/test/java/org/yaml/snakeyaml/issues/issue56/PerlTest.java) of parsing a YAML document generated by Perl
  * Fix [issue 56](http://code.google.com/p/snakeyaml/issues/detail?id=56): Make constructors in [SafeConstructor](http://code.google.com/p/snakeyaml/source/browse/src/main/java/org/yaml/snakeyaml/constructor/SafeConstructor.java) public
  * [Releases are available](http://repo1.maven.org/maven2/org/yaml/snakeyaml/) in the central Maven repository (thanks to [Sonatype](https://docs.sonatype.org/display/Repository/Sonatype+OSS+Maven+Repository+Usage+Guide))
  * Fix [issue 54](http://code.google.com/p/snakeyaml/issues/detail?id=54): deploy [artifacts](http://oss.sonatype.org/content/groups/public/org/yaml/snakeyaml) in the Sonatype [repository](http://oss.sonatype.org/content/groups/public). (thanks to David Bernard)
  * Fix [issue 53](http://code.google.com/p/snakeyaml/issues/detail?id=53): Enhancement for a pretty format that combines BLOCK and FLOW. (thanks to obastard)
  * Fix [issue 50](http://code.google.com/p/snakeyaml/issues/detail?id=50): Unable to dump `JavaBean` that inherits from a protected base class. (thanks to sualeh.fatehi)

**1.6 (2010-02-26)**
  * Fix [issue 47](http://code.google.com/p/snakeyaml/issues/detail?id=47): Don't dump read-only `JavaBean` properties by default. This is backwards-**incompatible** change. (thanks to obastard)
  * Fix [issue 49](http://code.google.com/p/snakeyaml/issues/detail?id=49): Support `GregorianCalendar` as regular `Timestamp` (thanks to obastard)
  * Fix [issue 51](http://code.google.com/p/snakeyaml/issues/detail?id=51): do not escape non-ASCII characters in double quoted scalar style (thanks to Johann Werner)
  * Fix [issue 48](http://code.google.com/p/snakeyaml/issues/detail?id=48): Introduce representJavaBeanProperty() method in Representer. (thanks to obastard)
  * Representer.representJavaBean() returns `MappingNode`
  * Add [an example](http://code.google.com/p/snakeyaml/source/browse/src/test/java/org/yaml/snakeyaml/issues/issue46/FileTest.java) of serialising `java.io.File` as scalar node for [issue 46](http://code.google.com/p/snakeyaml/issues/detail?id=46)
  * Refactoring of scanner and parser. Low-level API is not affected.
  * Improve performance: use `StringBuilder` instead of `StringBuffer`
  * Introduce String constants in scanner and emitter to improve readability and performance
  * Fix [issue 43](http://code.google.com/p/snakeyaml/issues/detail?id=43): introduce `Tag` class. This is backwards-**incompatible** change.
  * Fix [issue 42](http://code.google.com/p/snakeyaml/issues/detail?id=42): `BaseConstructor.getConstructor(Node node)` is now 'protected' to get more flexibility at runtime. (thanks to Artem)
  * Fix [issue 40](http://code.google.com/p/snakeyaml/issues/detail?id=40): Ignore tags when they are compatible with the `JavaBean` runtime class (thanks to sitrious)
  * Add [an example](http://code.google.com/p/snakeyaml/source/browse/src/test/java/examples/IgnoreTagsExampleTest.java) how to ignore unknown tags (for [issue 31](http://code.google.com/p/snakeyaml/issues/detail?id=31) and [issue 39](http://code.google.com/p/snakeyaml/issues/detail?id=39))
  * Add [an example](http://code.google.com/p/snakeyaml/source/browse/src/test/java/org/yaml/snakeyaml/ruby/RubyTest.java) how to work with [Ruby YAML documents](http://code.google.com/p/snakeyaml/source/browse/src/test/resources/ruby/ruby1.yaml)
  * Do not omit the tag for `JavaBean` properties when the tag is explicitly defined
  * Fix [issue 38](http://code.google.com/p/snakeyaml/issues/detail?id=38): Fix ID format for numbers over 999 (thanks to gchpaco)
  * Fix [issue 29](http://code.google.com/p/snakeyaml/issues/detail?id=29): it is possible to define which scalar style will be used when a scalar is emitted
  * Fix [issue 36](http://code.google.com/p/snakeyaml/issues/detail?id=36): process a family of tags which have the same prefix with one constructor
  * Add [an example](http://code.google.com/p/snakeyaml/source/browse/src/test/java/org/yaml/snakeyaml/constructor/FilterClassesConstructorTest.java) to demonstrate how to [restrict classes to be loaded](http://code.google.com/p/snakeyaml/wiki/Documentation?ts=1258544453&updated=Documentation#Restrict_classes_to_be_loaded)
  * Improve `JavaDoc` (thanks to Stefan)
  * add examples for [flexible YAML dumping](http://code.google.com/p/snakeyaml/wiki/Documentation#Dumping_a_custom_YAML_document) [using a template engine](http://code.google.com/p/snakeyaml/source/browse/src/test/java/org/yaml/snakeyaml/emitter/template/VelocityTest.java)
**1.5 (2009-10-30)**
  * Fix [issue 27](http://code.google.com/p/snakeyaml/issues/detail?id=27): Extend Resolver to support custom implicit types
  * Fix [issue 25](http://code.google.com/p/snakeyaml/issues/detail?id=25): Improve usage of generic collections in `JavaBeans` (thanks to the Polyglot Maven team and especially to Benjamin Bentmann)
  * Fix [issue 26](http://code.google.com/p/snakeyaml/issues/detail?id=26): Parse different Map implementations (`SortedMap`, `Properties`) without global tags if they are `JavaBean` properties (thanks to Benjamin Bentmann)
  * SnakeYAML 1.4 is available in [the central Maven repository](http://mirrors.ibiblio.org/pub/mirrors/maven2/org/yaml/snakeyaml/)
  * Fix [issue 24](http://code.google.com/p/snakeyaml/issues/detail?id=24): Line numbers reported in Exceptions are Zero based, should be 1 based (thanks to shrode)
  * Fix [issue 21](http://code.google.com/p/snakeyaml/issues/detail?id=21): Support arrays of reference types as `JavaBean` properties (thanks to ashwin.jayaprakash)
  * Fix [issue 17](http://code.google.com/p/snakeyaml/issues/detail?id=17): Respect root tag for sequences (thanks to jcucurull)
  * Fix [issue 18](http://code.google.com/p/snakeyaml/issues/detail?id=18): `SafeRepresenter `respects custom tags for standard Java classes where standard tag has more then one Java implementation available (Long, List, Map, Date etc) (thanks to creiniger)
  * Add [possibility to define](http://code.google.com/p/snakeyaml/wiki/Documentation?ts=1251794510&updated=Documentation#Custom_Class_Loader) a custom [Class Loader](http://java.sun.com/docs/books/tutorial/ext/basics/load.html)
  * Fix a scanner error not reported when there is no line break at the end of the stream. Import the fix from PyYAML 3.09 {[ticket 118](http://pyyaml.org/ticket/118)}
  * Fix [issue 16](http://code.google.com/p/snakeyaml/issues/detail?id=16): Cache `JavaBean` class properties (thanks to infinity0x)
  * Fix [issue 14](http://code.google.com/p/snakeyaml/issues/detail?id=14): `ArrayList` is more efficient than `LinkedList` (thanks to infinity0x)
**1.4 (2009-08-26)**
  * [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html) applied
  * Fix [issue 13](http://code.google.com/p/snakeyaml/issues/detail?id=13): Provide javadocs link to Sun Java API (thanks to infinity0x)
  * Fix [issue 11](http://code.google.com/p/snakeyaml/issues/detail?id=11): create a Java instance with the following priority to choose the class: Explicit tag -> Runtime class (defined in `JavaBean`) -> implicit tag (thanks to infinity0x)
  * Fix [issue 9](http://code.google.com/p/snakeyaml/issues/detail?id=9): Bean with no property cannot be instantiated (thanks to wwagner4)
  * [Better support](http://code.google.com/p/snakeyaml/wiki/Documentation?ts=1251358934&updated=Documentation#Immutable_instances) for loading [immutable instances](http://www.javapractices.com/topic/TopicAction.do?Id=29)
  * Major refactoring: Constructor is completely rewritten to prepare better support for immutable objects
  * Change Maven repository path: groupId=`org.yaml`, artifactId=`snakeyaml`
  * Fix [issue 10](http://code.google.com/p/snakeyaml/issues/detail?id=10): dump omits `JavaBean` class name when used with an alias (thanks to derrick.rice)
  * JAR packages with source and `Javadoc` will also be uploaded to Maven repository
  * Introduce [JavaBeanLoader](http://code.google.com/p/snakeyaml/source/browse/src/main/java/org/yaml/snakeyaml/JavaBeanDumper.java) and [JavaBeanDumper](http://code.google.com/p/snakeyaml/source/browse/src/main/java/org/yaml/snakeyaml/JavaBeanLoader.java). Deprecate `JavaBeanParser`
  * Fix [issue 8](http://code.google.com/p/snakeyaml/issues/detail?id=8): Representer was keeping state between invocations (thanks to Alan Gutierrez)

**1.3 (2009-07-20)**
  * Add [guideline](http://code.google.com/p/snakeyaml/wiki/Documentation#Generics) about Generic class definitions (http://en.wikipedia.org/wiki/Generics_in_Java) for `JavaBeans`
  * Fix [issue 5](http://code.google.com/p/snakeyaml/issues/detail?id=5): set the "cause" field for MarkedYAMLException (thanks to infinity0x)
  * Fix [issue 1](http://code.google.com/p/snakeyaml/issues/detail?id=1): recursive object are now fully supported (thanks to Alexander Maslov)
  * Fix [issue 3](http://code.google.com/p/snakeyaml/issues/detail?id=3): does not dump maps of beans correctly (thanks to infinity0x)
  * Project migrated to [Google code](http://code.google.com/p/snakeyaml/) (thanks to Google for supporting Mercurial)
  * Fix: null as a `JavaBean` property was not handled properly (thanks to Magne)
  * Fix [ticket #40](http://trac-hg.assembla.com/snakeyaml/ticket/40): java.sql.Timestamp was not handled properly (thanks to Magne)

**1.2 (2009-04-27)**
  * add [info for low-level API](http://code.google.com/p/snakeyaml/wiki/Documentation#Low_Level_API)
  * Add `Yaml.parse()` method which return Events to support low level YAML processing
  * Add `Yaml.compose()` methods which return Nodes
  * Rename `LineBreak`.LINUX to `LineBreak`.UNIX
  * Refactor: rename enums in `DumperOptions` to make the names consistent
  * Add possibility to parse all scalars as Strings
  * Respect `DumperOptions` with a custom Representer
  * Represent TAB as '\t' instead of '(9' in an error message

**1.1 (2009-03-14)**
  * Test coverage reached 98%
  * `byte[]` is used for type `binary`
  * Better Spring support: the root `JavaBean` class can be specified as a String
  * Performance: fix a bug with expanding Regular Expressions (thanks to Christophe Desguez)
  * Fix [ticket #4](http://trac-hg.assembla.com/snakeyaml/ticket/4): java.sql.Date was not handled properly (thanks to Christophe Desguez)
  * Introduce `Enums` in `DumperOptions`
  * Minor refactoring and bug fixes
  * Add [Threads](http://code.google.com/p/snakeyaml/wiki/Documentation#Threading) and [Spring](http://code.google.com/p/snakeyaml/wiki/Documentation#Spring) sections to the wiki documentation

**1.0.1 (2009-02-18)**
  * Proper `Enum` [support](http://code.google.com/p/snakeyaml/wiki/Documentation#Enum) (thanks to James Nissel)
  * The mailing list is renamed to `snakeyaml-core` to avoid a name conflict in `Google` `AppEngine`
  * Provide possibility to define/eliminate the root tag for `JavaBeans`
  * Arrays as `JavaBens` properties are properly supported
  * Do not emit redundant tags for `JavaBeans`
  * Respect public fields in `JavaBeans`
  * Import changes from [PyYAML 3.07/3.08](http://pyyaml.org/wiki/PyYAML#History)
  * Use global tags (with !!) to load/dump Java custom classes
  * Fix parsing Long.MIN\_VALUE
  * When constructing integers try to create the first in the following order: Integer -> Long -> `BigInteger`
  * Add possibility to define an implicit resolver
  * Add possibility to define an explicit constructor
  * Java objects can be constructed from mapping (javabean), from sequence (constructor), from scalar (constructor)
  * fix omap and pairs tags
  * Implement possibility to define a custom List or Map implementation
  * Implement possibility to define a custom Representer
  * Support arrays of reference types
  * Import latest changes from PyYAML (after 3.06 was released)
  * Fix Node identity to avoid aliases for simple types - `[1, 1]`
  * Recursive objects can be represented (but not yet constructed)

**0.4 (2008-11-11)**
  * Fix a deviation with PyYAML in method scanBlockScalar().
  * Fix a bug in [JvYaml](https://jvyaml.dev.java.net/) that the trailing '\n' in a block scalar was removed
  * Restore from PyYAML the way the keys are parsed. (Restored methods are stalePossibleSimpleKeys() and removePossibleSimpleKey().)
  * Fix issue http://code.google.com/p/jvyamlb/issues/detail?id=6
  * Change public interface. Rename YAML to Yaml. Remove all static methods from Yaml. Factory and configuration must be injected at the constructor. Yaml loadAll() and dumpAll() methods work with Iterable instead of List. This way is closer to PyYAML API
  * Reader as in PyYAML is implemented. BOM is properly supported. Fix a known [bug](http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4508058) in Java
  * Fix issue: https://jvyaml.dev.java.net/issues/show_bug.cgi?id=11
  * Respect Unicode characters. Fix issue: https://jvyaml.dev.java.net/issues/show_bug.cgi?id=10
  * Respect sign for float. Fix issue: https://jvyaml.dev.java.net/issues/show_bug.cgi?id=13
  * Binary data is represented as [ByteBuffer](http://java.sun.com/javase/6/docs/api/java/nio/ByteBuffer.html)
  * When parsed, a timestamp in the canonical form (i.e, 2001-12-15T02:59:43.1Z) is interpreted as if it is in the default time zone. Fix issue: https://jvyaml.dev.java.net/issues/show_bug.cgi?id=7
  * Restore Mark from PyYAML to show a snippet of YAML in case of invalid data
  * Reformat the source files and provide formatter for Eclipse
  * Mavenize the project and apply Maven standard folder structure
  * Import [JvYaml](https://jvyaml.dev.java.net) source from CVS to Mercurial