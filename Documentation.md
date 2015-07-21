

# SnakeYAML Documentation #

_This documentation is very brief and incomplete. Feel free to fix or improve it._

## Installation ##
If you use Maven just add a dependency as described [here](http://code.google.com/p/snakeyaml/#Download_and_Installation).
If you do not use Maven download the latest JAR and put it to the classpath.

## Frequently Asked Questions ##

### Dictionaries without nested collections are not dumped correctly ###

_Why does_
```
Yaml yaml = new Yaml();
String document = "  a: 1\n  b:\n    c: 3\n    d: 4\n";
System.out.println(document);
System.out.println(yaml.dump(yaml.load(document)));
```
_give_
```
  a: 1
  b:
    c: 3
    d: 4

a: 1
b: {c: 3, d: 4}
```

It's a correct output despite the fact that the style of the nested mapping is different.

By default, SnakeYAML chooses the style of a collection depending on whether it has nested
collections. If a collection has nested collections, it will be assigned the block style.
Otherwise it will have the flow style.

If you want collections to be always serialized in the block style, set the parameter
`defaultFlowStyle` of `DumperOptions` to `block`. For instance,
```
DumperOptions options = new DumperOptions();
options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
Yaml yaml = new Yaml(options);
String document = "  a: 1\n  b:\n    c: 3\n    d: 4\n";
System.out.println(yaml.dump(yaml.load(document)));

a: 1
b:
  c: 3
  d: 4
```

You can find an example [here](http://code.google.com/p/snakeyaml/source/browse/src/test/java/examples/CollectionStyleTest.java)

### Binary Data ###
`byte[]` is represented as binary. Also when a `String` contains at least one non-printable character the
`!!binary` type is emitted.

Binary scalar is parsed as `byte[]`.

An example can found [here](http://code.google.com/p/snakeyaml/source/browse/src/test/java/org/yaml/snakeyaml/types/BinaryTagTest.java).

### JSON support ###
JSON is a subset of YAML 1.2, SnakeYAML implements YAML 1.1 at the moment. It means that not all the JSON documents can be parsed.

## Tutorial ##

Start with instantiating the `org.yaml.snakeyaml.Yaml` instance.

```
Yaml yaml = new Yaml();
```


### Loading YAML ###

**Warning: It is not safe to call `Yaml.load()` with any data received from an untrusted source!**

The method `Yaml.load()` converts a YAML document to a Java object.

```
Yaml yaml = new Yaml();
String document = "\n- Hesperiidae\n- Papilionidae\n- Apatelodidae\n- Epiplemidae";
List<String> list = (List<String>) yaml.load(document);
System.out.println(list);

['Hesperiidae', 'Papilionidae', 'Apatelodidae', 'Epiplemidae']
```
You can find an example [here](http://code.google.com/p/snakeyaml/source/browse/src/test/java/examples/LoadExampleTest.java)

`Yaml.load()` accepts a String or
an `InputStream` object. `Yaml.load(InputStream stream)` detects the encoding
by checking the **BOM** (byte order mark) sequence at the beginning of the
stream. If no **BOM** is present, the **utf-8** encoding is assumed.

`Yaml.load()` returns a Java object.

```
public void testLoadFromString() {
    Yaml yaml = new Yaml();
    String document = "hello: 25";
    Map map = (Map) yaml.load(document);
    assertEquals("{hello=25}", map.toString());
    assertEquals(new Long(25), map.get("hello"));
}

public void testLoadFromStream() throws FileNotFoundException {
    InputStream input = new FileInputStream(new File("src/test/resources/reader/utf-8.txt"));
    Yaml yaml = new Yaml();
    Object data = yaml.load(input);
    assertEquals("test", data);
    //
    data = yaml.load(new ByteArrayInputStream("test2".getBytes()));
    assertEquals("test2", data);
}
```

If a String or a stream contains several documents, you may load them all with the
`Yaml.loadAll()` method.
```
---
Time: 2001-11-23 15:01:42 -5
User: ed
Warning:
  This is an error message
  for the log file
---
Time: 2001-11-23 15:02:31 -5
User: ed
Warning:
  A slightly different error
  message.
---
Date: 2001-11-23 15:03:17 -5
User: ed
Fatal:
  Unknown variable "bar"
Stack:
  - file: TopClass.py
    line: 23
    code: |
      x = MoreObject("345\n")
  - file: MoreClass.py
    line: 58
    code: |-
      foo = bar
```
```
public void testLoadManyDocuments() throws FileNotFoundException {
    InputStream input = new FileInputStream(new File(
            "src/test/resources/specification/example2_28.yaml"));
    Yaml yaml = new Yaml();
    int counter = 0;
    for (Object data : yaml.loadAll(input)) {
        System.out.println(data);
        counter++;
    }
    assertEquals(3, counter);
}
```
```
{Time=Fri Nov 23 21:01:42 CET 2001, User=ed, Warning=This is an error message for the log file}
{Time=Fri Nov 23 21:02:31 CET 2001, User=ed, Warning=A slightly different error message.}
{Date=Fri Nov 23 21:03:17 CET 2001, User=ed, Fatal=Unknown variable "bar", Stack=[{file=TopClass.py, line=23, code=x = MoreObject("345\n")
}, {file=MoreClass.py, line=58, code=foo = bar}]}
```
A document is parsed only when the iterator is invoked.

SnakeYAML allows you [to construct](http://code.google.com/p/snakeyaml/source/browse/src/test/java/examples/AnyObjectExampleTest.java) a Java object of any type.

```
none: [~, null]
bool: [true, false, on, off]
int: 42
float: 3.14159
list: [LITE, RES_ACID, SUS_DEXT]
dict: {hp: 13, sp: 5}
```

```
public void testLoad() throws IOException {
    String doc = Util.getLocalResource("examples/any-object-example.yaml");
    Yaml yaml = new Yaml();
    Map<String, Object> object = (Map<String, Object>) yaml.load(doc);
    System.out.println(object);
}
```
```
{none=[null, null], bool=[true, false, true, false], int=42, float=3.14159, 
list=[LITE, RES_ACID, SUS_DEXT], dict={hp=13, sp=5}}
```

Even instances of custom Java classes [can be constructed](http://code.google.com/p/snakeyaml/source/browse/src/test/java/org/yaml/snakeyaml/constructor/ConstructorTest.java).
```
/**
 * create JavaBean
 */
public void testGetBeanAssumeClass() {
    String data = "--- !org.yaml.snakeyaml.constructor.Person\nfirstName: Andrey\nage: 99";
    Object obj = construct(data);
    assertNotNull(obj);
    assertTrue("Unexpected: " + obj.getClass().toString(), obj instanceof Person);
    Person person = (Person) obj;
    assertEquals("Andrey", person.getFirstName());
    assertNull(person.getLastName());
    assertEquals(99, person.getAge().intValue());
}

/**
 * create instance using constructor arguments
 */
public void testGetConstructorBean() {
    String data = "--- !org.yaml.snakeyaml.constructor.Person [ Andrey, Somov, 99 ]";
    Object obj = construct(data);
    assertNotNull(obj);
    assertTrue(obj.getClass().toString(), obj instanceof Person);
    Person person = (Person) obj;
    assertEquals("Andrey", person.getFirstName());
    assertEquals("Somov", person.getLastName());
    assertEquals(99, person.getAge().intValue());
}

/**
 * create instance using scalar argument
 */
public void testGetConstructorFromScalar() {
    String data = "--- !org.yaml.snakeyaml.constructor.Person 'Somov'";
    Object obj = construct(data);
    assertNotNull(obj);
    assertTrue(obj.getClass().toString(), obj instanceof Person);
    Person person = (Person) obj;
    assertNull("Andrey", person.getFirstName());
    assertEquals("Somov", person.getLastName());
    assertNull(person.getAge());
}
```

Note if you want to limit objects to standard Java objects like List or Long you need
[to use SafeConstructor](http://code.google.com/p/snakeyaml/source/browse/src/test/java/examples/SafeConstructorExampleTest.java).
```
Yaml yaml = new Yaml(new SafeConstructor());
```

#### Providing the top level type ####

It is possible to load a YAML document without any explicit tags. For instance, to load this document
(example 2.27 from the [YAML specification](http://yaml.org/spec/1.1/#id859060))
```
invoice: 34843
date   : 2001-01-23
billTo: &id001
    given  : Chris
    family : Dumars
    address:
        lines: |
            458 Walkman Dr.
            Suite #292
        city    : Royal Oak
        state   : MI
        postal  : 48046
shipTo: *id001
product:
    - sku         : BL394D
      quantity    : 4
      description : Basketball
      price       : 450.00
    - sku         : BL4438H
      quantity    : 1
      description : Super Hoop
      price       : 2392.00
tax  : 251.42
total: 4443.52
comments:
    Late afternoon is best.
    Backup contact is Nancy
    Billsmer @ 338-4338.
```
into Invoice, Person, Address, Product instances the top level class in the object hierarchy
[must be provided](http://code.google.com/p/snakeyaml/source/browse/src/test/java/org/yaml/snakeyaml/Example2_27Test.java):
```
Yaml yaml = new Yaml(new Constructor(Invoice.class));
```
SnakeYAML is using Reflection API to find out the class for all the properties (setters and public fields) on Invoice.
Unfortunately because of erasure it is not possible to identify classes for type safe collections at runtime. The
class information between <> is only available at compile time.

#### Implicit types ####
When a tag for a scalar node is not explicitly defined, SnakeYAML tries to detect
[the type](http://www.yaml.org/type/) applying regular expressions to the content of the scalar node.
```
1.0 -> Float
42 -> Integer
2009-03-30 -> Date
```

An example how to change the default implicit types can be found [here](http://code.google.com/p/snakeyaml/source/browse/src/test/java/examples/resolver/CustomResolverTest.java)

### Type safe collections ###

When type-safe (generic) collections are `JavaBean` properties `SnakeYAML` dynamically detects the required class.
A lot of examples can be found [here](http://code.google.com/p/snakeyaml/source/browse/#hg/src/test/java/examples/collections)

It does not work if generic type is abstract class (interface). You have to put an explicit tag in the YAML or provide
the explicit `TypeDescription`.
[TypeDescription](http://code.google.com/p/snakeyaml/source/browse/src/main/java/org/yaml/snakeyaml/TypeDescription.java)
serves the goal to collect more information and use it while loading/dumping.

Let's say we have this document:
```
plate: 12-XP-F4
wheels:
- {id: 1}
- {id: 2}
- {id: 3}
- {id: 4}
- {id: 5}
```
and we would like to load this class
```
public class Car {
    private String plate;
    private List<Wheel> wheels;

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public List<Wheel> getWheels() {
        return wheels;
    }

    public void setWheels(List<Wheel> wheels) {
        this.wheels = wheels;
    }
}
```
where 'wheels' property is a List of Wheel. In order to load Car (and create `List<Wheel>`)
`TypeDescription` must be provided:
```
Constructor constructor = new Constructor(Car.class);//Car.class is root
TypeDescription carDescription = new TypeDescription(Car.class);
carDescription.putListPropertyType("wheels", Wheel.class);
constructor.addTypeDefinition(carDescription);
Yaml yaml = new Yaml(constructor);
```
The full example can be found
[here](http://code.google.com/p/snakeyaml/source/browse/src/test/java/org/yaml/snakeyaml/constructor/TypeSafeCollectionsTest.java)
(`testTypeSafeList()`).

A similar approach works for Maps. Please note that both keys and values of the Map can be of any type:
```
plate: 00-FF-Q2
wheels:
  ? {brand: Pirelli, id: 1}
  : 2008-01-16
  ? {brand: Dunkel, id: 2}
  : 2002-12-24
  ? {brand: Pirelli, id: 3}
  : 2008-01-16
  ? {brand: Pirelli, id: 4}
  : 2008-01-16
  ? {brand: Pirelli, id: 5}
  : 2008-01-16
```
The class to be loaded:
```
public class MyCar {
    private String plate;
    private Map<MyWheel, Date> wheels;

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public Map<MyWheel, Date> getWheels() {
        return wheels;
    }

    public void setWheels(Map<MyWheel, Date> wheels) {
        this.wheels = wheels;
    }
}
```
The [code](http://code.google.com/p/snakeyaml/source/browse/src/test/java/org/yaml/snakeyaml/constructor/TypeSafeCollectionsTest.java):
```
Constructor constructor = new Constructor(MyCar.class);
TypeDescription carDescription = new TypeDescription(MyCar.class);
carDescription.putMapPropertyType("wheels", MyWheel.class, Object.class);
constructor.addTypeDefinition(carDescription);
Yaml yaml = new Yaml(constructor);
MyCar car = (MyCar) yaml.load(<see above>);
```

### Dumping YAML ###

The `Yaml.dump(Object data)` method accepts a Java object and produces a YAML document.
(the source is [here](http://code.google.com/p/snakeyaml/source/browse/src/test/java/examples/DumpExampleTest.java))
```
public void testDump() {
    Map<String, Object> data = new HashMap<String, Object>();
    data.put("name", "Silenthand Olleander");
    data.put("race", "Human");
    data.put("traits", new String[] { "ONE_HAND", "ONE_EYE" });
    Yaml yaml = new Yaml();
    String output = yaml.dump(data);
    System.out.println(output);
}
```
```
name: Silenthand Olleander
traits: [ONE_HAND, ONE_EYE]
race: Human
```

`Yaml.dump(Object data, Writer output)` will write the produced YAML document into
the specified file/stream.

```
public void testDumpWriter() {
    Map<String, Object> data = new HashMap<String, Object>();
    data.put("name", "Silenthand Olleander");
    data.put("race", "Human");
    data.put("traits", new String[] { "ONE_HAND", "ONE_EYE" });
    Yaml yaml = new Yaml();
    StringWriter writer = new StringWriter();
    yaml.dump(data, writer);
    System.out.println(writer.toString());
}
```

If you need to dump several YAML documents to a single stream, use the method
`Yaml.dumpAll(Iterator<Object> data)`. It accepts an Iterator of
Java objects to be serialized into a YAML document. A Writer can also be used.

```
public void testDumpMany() {
    List<Integer> docs = new LinkedList<Integer>();
    for (int i = 1; i < 4; i++) {
        docs.add(i);
    }
    DumperOptions options = new DumperOptions();
    options.explicitStart(true);
    Yaml yaml = new Yaml(options);
    System.out.println(yaml.dump(docs));
    System.out.println(yaml.dumpAll(docs.iterator()));
}
```
```
--- [1, 2, 3]

--- 1
--- 2
--- 3
```

You may even dump instances of `JavaBeans`.

```
public void testDumpCustomJavaClass() {
    Hero hero = new Hero("Galain Ysseleg", -3, 2);
    Yaml yaml = new Yaml();
    String output = yaml.dump(hero);
    System.out.println(output);
    assertEquals("!!examples.Hero {hp: -3, name: Galain Ysseleg, sp: 2}\n", output);
}
```
```
!!examples.Hero {hp: -3, name: Galain Ysseleg, sp: 2}
```

As you can see the `JavaBean` data is sorted althabetically.

`DumperOptions` specifies
formatting details for the emitter. For instance, you may set the
preferred intendation and width, use the canonical YAML format or
force preferred style for scalars and collections.
```
public void testDumperOptions() {
    List<Integer> data = new LinkedList<Integer>();
    for (int i = 0; i < 50; i++) {
        data.add(i);
    }
    Yaml yaml = new Yaml();
    String output = yaml.dump(data);
    System.out.println(output);
    //
    DumperOptions options = new DumperOptions();
    options.setWidth(50);
    options.setIndent(4);
    yaml = new Yaml(options);
    output = yaml.dump(data);
    System.out.println(output);
}
```
```
[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22,
  23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42,
  43, 44, 45, 46, 47, 48, 49]

[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
    16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27,
    28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39,
    40, 41, 42, 43, 44, 45, 46, 47, 48, 49]
```
Canonical output:
```
public void testDumperOptionsCanonical() {
    List<Integer> data = new LinkedList<Integer>();
    for (int i = 0; i < 5; i++) {
        data.add(i);
    }
    DumperOptions options = new DumperOptions();
    options.setCanonical(true);
    Yaml yaml = new Yaml(options);
    String output = yaml.dump(data);
    System.out.println(output);
}
```
```
---
!!seq [
  !!int "0",
  !!int "1",
  !!int "2",
  !!int "3",
  !!int "4",
]
```
```
public void testDumperOptionsFlowStyle() {
    List<Integer> data = new LinkedList<Integer>();
    for (int i = 0; i < 5; i++) {
        data.add(i);
    }
    DumperOptions options = new DumperOptions();
    options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
    Yaml yaml = new Yaml(options);
    String output = yaml.dump(data);
    System.out.println(output);
}
```
```
- 0
- 1
- 2
- 3
- 4
```
```
public void testDumperOptionsStyle() {
    List<Integer> data = new LinkedList<Integer>();
    for (int i = 0; i < 5; i++) {
        data.add(i);
    }
    DumperOptions options = new DumperOptions();
    options.setDefaultScalarStyle(DumperOptions.ScalarStyle.DOUBLE_QUOTED);
    Yaml yaml = new Yaml(options);
    String output = yaml.dump(data);
    System.out.println(output);
}
```
```
- !!int "0"
- !!int "1"
- !!int "2"
- !!int "3"
- !!int "4"
```

### Dumping a custom YAML document ###

The main goal of dumping in SnakeYAML is to produce a YAML document which can be loaded back to the instance. There are cases where a created YAML document does not look as expected:

  * immutable object. Immutable objects may have getters but they do not have setters. By default it is emitted as a map and therefore it can not be parsed later. Immutable objects must be emitted as a sequence where the order is very important
  * comments. Having comments all over the place help humans to edit the YAML document
  * skip [some JavaBean properties](http://code.google.com/p/snakeyaml/source/browse/src/test/java/org/yaml/snakeyaml/representer/FilterPropertyToDumpTest.java) ([empty collections, null](http://code.google.com/p/snakeyaml/source/browse/src/test/java/org/yaml/snakeyaml/issues/issue60/SkipBeanTest.java), calculated properties etc).
  * define [order for `JavaBean` properties](http://code.google.com/p/snakeyaml/source/browse/src/test/java/org/yaml/snakeyaml/issues/issue60/CustomOrderTest.java). Order may be very informative: for instance ID or name are normally expected to be the first. (Currently it is alphabetically sorted by default.)
  * since [standard types](http://www.yaml.org/type/) have more then one value it is possible to configure `Representer` to use different values. See examples for [`!!null`](http://code.google.com/p/snakeyaml/source/browse/src/test/java/org/yaml/snakeyaml/types/NullTagTest.java) and for [`!!bool`](http://code.google.com/p/snakeyaml/source/browse/src/test/java/org/yaml/snakeyaml/types/BoolTagTest.java)

Since a YAML document is nothing but a text document any [Template processor](http://en.wikipedia.org/wiki/Template_processor) can be used.

General algorithm:
  1. Try to find out whether it is possible to use only `DumperOptions`
  1. If not, use `Yaml.dumpAs(obj, Tag.MAP)` to see how YAML should look like.
  1. Pick up your favourite template engine and go ahead.
  1. Write a test which parses the generated YAML document back to the instance to be reminded by the test to follow the code changes.
  1. You can always consider a possibility to extend SnakeYAML code. Take a look at org.yaml.snakeyaml.representer.Representer. If you find your changes useful do not forget to contribute the enhancements back to SnakeYAML so anybody can benefit.
  1. Implement your own way to serialize an instance into a text YAML document

An  example of using templates is [here](http://code.google.com/p/snakeyaml/source/browse/src/test/java/org/yaml/snakeyaml/emitter/template/VelocityTest.java). Please note that you can use SnakeYAML to produce parts of the result and then provide these parts to the template.

### `JavaBeans` ###

The spec says - "One of the main goals of the `JavaBeans` architecture is to provide a platform neutral component
architecture."

Avoiding global tags significantly improves ability to exchange the YAML documents between different
platforms and languages.

If the custom Java class conforms to the `JavaBean` specification it can be loaded and dumped
without any extra code. For instance this `JavaBean`
```
public class CarWithWheel {
    private String plate;
    private String year;
    private Wheel wheel;
    private Object part;
    private Map<String, Integer> map;

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public Wheel getWheel() {
        return wheel;
    }

    public void setWheel(Wheel wheel) {
        this.wheel = wheel;
    }

    public Map<String, Integer> getMap() {
        return map;
    }

    public void setMap(Map<String, Integer> map) {
        this.map = map;
    }

    public Object getPart() {
        return part;
    }

    public void setPart(Object part) {
        this.part = part;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
```
```
CarWithWheel car1 = new CarWithWheel();
car1.setPlate("12-XP-F4");
Wheel wheel = new Wheel();
wheel.setId(2);
car1.setWheel(wheel);
Map<String, Integer> map = new HashMap<String, Integer>();
map.put("id", 3);
car1.setMap(map);
car1.setPart(new Wheel(4));
car1.setYear("2008");
String output = new Yaml().dump(car1);
```
will be dumped as
```
!!package.CarWithWheel
map: {id: 3}
part: !!org.yaml.snakeyaml.constructor.Wheel {id: 4}
plate: 12-XP-F4
wheel: {id: 2}
year: '2008'
```
Note that the 'part' property still has a global tag but the 'wheel' property does not
(because the wheel's runtime class is the same as it is defined in the `CarWithWheel` class).

The example for the above `JavaBean` can be found
[here](http://code.google.com/p/snakeyaml/source/browse/src/test/java/org/yaml/snakeyaml/constructor/ImplicitTagsTest.java)
The preferred way to dump a `JavaBean` is  [to use Yaml.dumpAs(obj, Tag.MAP)](http://code.google.com/p/snakeyaml/source/browse/src/test/java/org/yaml/snakeyaml/javabeans/HouseTest.java). This utility is emitting with the block layout and does not emit the root global tag with the class name (using implicit !!map tag).

The preferred way to parse a `JavaBean` is to use `Yaml.loadAs()`. It eliminates the need to cast returned instances to the specified class.

By default standard `JavaBean` properties and public fields are included. [BeanAccess.FIELD](http://code.google.com/p/snakeyaml/source/browse/src/main/java/org/yaml/snakeyaml/introspector/BeanAccess.java) makes is possible [to use private fields](http://code.google.com/p/snakeyaml/source/browse/src/test/java/org/yaml/snakeyaml/issues/issue55/FieldListTest.java) directly.

### Shortcuts ###

There is a way to define local tags for custom classes.
```
!!org.yaml.snakeyaml.constructor.Car
plate: 12-XP-F4
wheels:
- !!org.yaml.snakeyaml.constructor.Wheel {id: 1}
- !!org.yaml.snakeyaml.constructor.Wheel {id: 2}
- !!org.yaml.snakeyaml.constructor.Wheel {id: 3}
- !!org.yaml.snakeyaml.constructor.Wheel {id: 4}
- !!org.yaml.snakeyaml.constructor.Wheel {id: 5}
```
To eliminate long names while dumping Yaml
[should be configured](http://code.google.com/p/snakeyaml/source/browse/src/test/java/org/yaml/snakeyaml/constructor/ClassTagsTest.java)
to use shortcuts:
```
Representer representer = new Representer();
representer.addClassTag(Car.class, new Tag("!car"));
representer.addClassTag(Wheel.class, Tag.MAP);;
Yaml yaml = new Yaml(representer, new DumperOptions());
String output = yaml.dump(car);
```
This is the resulting output:
```
!car
plate: 12-XP-F4
wheels:
- {id: 1}
- {id: 2}
- {id: 3}
- {id: 4}
- {id: 5}
```
Loader can be configured in a similar way:
```
Constructor constructor = new Constructor();
constructor.addTypeDescription(new TypeDescription(Car.class, "!car"));
Yaml yaml = new Yaml(constructor);
```

### Constructors, representers, resolvers ###

You may define your own application-specific tags. (the example's source is
[here](http://code.google.com/p/snakeyaml/source/browse/src/test/java/examples/DiceExampleTest.java))

For instance, you may want to add a constructor
and a representer for the following [Dice](http://code.google.com/p/snakeyaml/source/browse/src/test/java/examples/Dice.java) class:
```
public class Dice {
    private Integer a;
    private Integer b;

    public Dice(Integer a, Integer b) {
        super();
        this.a = a;
        this.b = b;
    }

    public Integer getA() {
        return a;
    }

    public Integer getB() {
        return b;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Dice) {
            return toString().equals(obj.toString());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public String toString() {
        return "Dice " + a + "d" + b;
    }
}
```


The default representation for `Dice` objects is not nice:
```
public void testRepresenter() throws IOException {
    Dice dice = new Dice(3, 6);
    Yaml yaml = new Yaml();
    String output = yaml.dump(dice);
    System.out.println(output);
}
```
```
!!examples.Dice {a: 3, b: 6}
```

Suppose you want a `Dice` object to represented as `AdB` in YAML:
```
System.out.println(yaml.dump(new Dice(3,6)));

3d6
```

First we define a representer that convert a dice object to scalar node
with the tag `!dice` and register it.
```
class DiceRepresenter extends Representer {
    public DiceRepresenter() {
        this.representers.put(Dice.class, new RepresentDice());
    }

    private class RepresentDice implements Represent {
        public Node representData(Object data) {
            Dice dice = (Dice) data;
            String value = dice.getA() + "d" + dice.getB();
            return representScalar(new Tag("!dice"), value);
        }
    }
}
```

Now you may dump an instance of the `Dice` object:
```
public void testDiceRepresenter() throws IOException {
    Dice dice = new Dice(3, 6);
    Map<String, Dice> data = new HashMap<String, Dice>();
    data.put("gold", dice);
    Yaml yaml = new Yaml(new DiceRepresenter(), new DumperOptions());
    String output = yaml.dump(data);
    System.out.println(output);
}
```
```
{gold: !dice '10d6'}
```

Let us add the code to construct a Dice object:
```
class DiceConstructor extends Constructor {
    public DiceConstructor() {
        this.yamlConstructors.put(new Tag("!dice"), new ConstructDice());
    }

    private class ConstructDice extends AbstractConstruct {
        public Object construct(Node node) {
            String val = (String) constructScalar(node);
            int position = val.indexOf('d');
            Integer a = Integer.parseInt(val.substring(0, position));
            Integer b = Integer.parseInt(val.substring(position + 1));
            return new Dice(a, b);
        }
    }
}
```

Then you may load a `Dice` object as well:
```
public void testConstructor() throws IOException {
    Yaml yaml = new Yaml(new DiceConstructor());
    Object data = yaml.load("{initial hit points: !dice '8d4'}");
    Map<String, Dice> map = (Map<String, Dice>) data;
    assertEquals(new Dice(8, 4), map.get("initial hit points"));
}
```

You might want to not specify the tag `!dice` everywhere. There is a way
to teach SankeYAML that any untagged plain scalar that looks like XdY has
the implicit tag `!dice`. Use `Yaml.addImplicitResolver(String tag, Pattern regexp, String first)`
then you don't have to specify the tag to define a `Dice` object:
```
public void testImplicitResolver() throws IOException {
    Yaml yaml = new Yaml(new DiceConstructor(), new DiceRepresenter(), new DumperOptions());
    yaml.addImplicitResolver(new Tag("!dice"), Pattern.compile("\\d+d\\d+"), "123456789");
    // dump
    Map<String, Dice> treasure = (Map<String, Dice>) new HashMap<String, Dice>();
    treasure.put("treasure", new Dice(10, 20));
    String output = yaml.dump(treasure);
    System.out.println(output);
    assertEquals("{treasure: 10d20}\n", output);
    // load
    Object data = yaml.load("{damage: 5d10}");
    Map<String, Dice> map = (Map<String, Dice>) data;
    assertEquals(new Dice(5, 10), map.get("damage"));
}
```
```
{treasure: 10d20}
```

### Enum ###
SnakeYAML treats `Enum`s in a special way. (an example can be found
[here](http://code.google.com/p/snakeyaml/source/browse/src/test/java/org/yaml/snakeyaml/EnumTest.java))

Normally an `Enum` requires an explicit global tag:
```
public void testDumpEnum() {
    Yaml yaml = new Yaml();
    String output = yaml.dump(Suit.CLUBS);
    assertEquals("!!org.yaml.snakeyaml.Suit 'CLUBS'\n", output);
}

public void testLoadEnum() {
    Yaml yaml = new Yaml();
    Suit suit = (Suit) yaml.load("!!org.yaml.snakeyaml.Suit 'CLUBS'");
    assertEquals(Suit.CLUBS, suit);
}
```

But if the Enum is a `JavaBean` property (and the class is implicitly defined) then the tags are not used:
```
public void testDumpEnumBean() {
    DumperOptions options = new DumperOptions();
    options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
    Yaml yaml = new Yaml(options);
    EnumBean bean = new EnumBean();
    bean.setId(17);
    bean.setSuit(Suit.SPADES);
    String output = yaml.dump(bean);
    System.out.println(output);
}
```
```
!!org.yaml.snakeyaml.EnumBean
id: 17
suit: SPADES
```

The same for loading:
```
public void testLoadEnumBean() {
    Yaml yaml = new Yaml();
    EnumBean bean = (EnumBean) yaml.load("!!org.yaml.snakeyaml.EnumBean\nid: 174\nsuit: CLUBS");
    assertEquals(Suit.CLUBS, bean.getSuit());
    assertEquals(174, bean.getId());
}
```

### Threading ###

Threads must have separate Yaml instances. Instances are cheap both in terms of time to create
and memory to occupy. Only the very first instance is heavy because of static initializers for
constants and regular expressions.

### Spring ###

Example of [Spring definition](http://code.google.com/p/snakeyaml/source/browse/src/test/resources/examples/spring.xml):
(note: the scope is always 'prototype')
```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:util="http://www.springframework.org/schema/util" xmlns:p="http://www.springframework.org/schema/p"
    xsi:schemaLocation="
http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-2.0.xsd
http://www.springframework.org/schema/util http://www.springframework.org/schema/util/spring-util-2.0.xsd">

    <!-- the most powerful way -->
    <bean id="yamlConstructor" class="examples.CustomConstructor" scope="prototype" />
    <bean id="yamlRepresenter" class="org.yaml.snakeyaml.representer.Representer" scope="prototype" />
    <bean id="yamlOptions" class="org.yaml.snakeyaml.DumperOptions" scope="prototype">
        <property name="indent" value="2" />
    </bean>
    <bean id="snakeYaml" class="org.yaml.snakeyaml.Yaml" scope="prototype">
        <constructor-arg ref="yamlConstructor" />
        <constructor-arg ref="yamlRepresenter" />
        <constructor-arg ref="yamlOptions" />
    </bean>

    <!-- for a single JavaBean -->
    <bean id="beanConstructor" class="org.yaml.snakeyaml.constructor.Constructor" scope="prototype">
        <constructor-arg value="org.yaml.snakeyaml.Invoice" />
    </bean>
    <bean id="javabeanYaml" class="org.yaml.snakeyaml.Yaml" scope="prototype">
        <constructor-arg ref="beanConstructor" />
    </bean>

    <!-- the simplest way -->
    <bean id="standardYaml" class="org.yaml.snakeyaml.Yaml" scope="prototype" />
</beans>
```

### Low Level API ###

It is possible to [parse](http://yaml.org/spec/1.1/#id860341) or [compose](http://yaml.org/spec/1.1/#id860452)
the incoming stream of characters. Examples can be found
[here](http://code.google.com/p/snakeyaml/source/browse/src/test/java/org/yaml/snakeyaml/YamlParseTest.java)
for parsing
or [here](http://code.google.com/p/snakeyaml/source/browse/src/test/java/org/yaml/snakeyaml/YamlComposeTest.java)
for composing.

The input must be specified as java.io.Reader. Use
[UnicodeReader.java](http://code.google.com/p/snakeyaml/source/browse/src/main/java/org/yaml/snakeyaml/reader/UnicodeReader.java)
if the input is `java.io.InputStream` (for proper BOM support).

### Generics ###
Not all JVM implementations provide a reliable way to recognize classes for `JavaBean` properties at runtime if a `JavaBean` is a generic class. `Introspector.getBeanInfo()` returns a `PropertyDescriptor` with Object as type instead of the real runtime class. It leads to emitting unnecessary global tags with class names.
As a consequence a YAML document produced in Java cannot be consumed by Python.
See some info here http://bugs.sun.com/view_bug.do?bug_id=6528714 .

On the other hand missing global tags will cause parsing exception when constructing a `JavaBean`.

The easiest solution is to follow the simple rule: DO NOT use Generic class definitions (http://en.wikipedia.org/wiki/Generics_in_Java) at all.
But if you have to:
  * Read [the test](http://code.google.com/p/snakeyaml/source/browse/src/test/java/org/yaml/snakeyaml/generics/BirdTest.java) to see the difference in behavior
  * Experiment with JVMs you use on both sides (loading and dumping)
  * To avoid parsing error one may need to provide custom Contructor or Representor to synchronize tags generation and consumption
  * Check [issue 107](http://code.google.com/p/snakeyaml/issues/detail?id=107) for the context

### Immutable instances ###
The only way to create immutable instance is to provide all the required arguments for the appropriate constructor. In a YAML document it will be represented as a sequence.
```
!!org.yaml.snakeyaml.immutable.Point [1.17, 3.14]
```
An example can be found [here](http://code.google.com/p/snakeyaml/source/browse/src/test/java/org/yaml/snakeyaml/immutable/ShapeImmutableTest.java)

### Compact Object Notation ###

[CompactObjectNotation](CompactObjectNotation.md)

### Custom Class Loader ###

In mobile applications one may need to use classes [obtained from the network](http://java.sun.com/docs/books/tutorial/ext/basics/load.html). It is possible to provide a [custom class loader](http://code.google.com/p/snakeyaml/source/browse/src/main/java/org/yaml/snakeyaml/constructor/CustomClassLoaderConstructor.java) when parsing a YAML document. An example can be found [here](http://code.google.com/p/snakeyaml/source/browse/src/test/java/org/yaml/snakeyaml/constructor/CustomClassLoaderConstructorTest.java).

### Restrict classes to be loaded ###

A public web-service may wish to restrict the classes to be loaded from a YAML document.
For instance, this should be avoided:

`!!java.io.FileOutputStream [woops.txt]`

`SnakeYAML` [provides a way](http://code.google.com/p/snakeyaml/source/browse/src/test/java/org/yaml/snakeyaml/constructor/FilterClassesConstructorTest.java) to filter classes to be loaded.

### Tags ###
Explicit tags can be local (begin with single exclamation mark '!car') and global (begin with 2 exclamation marks '!!com.Car'). Global tags work well within a single programming language, because they can be parsed without extra configuration.
Local tags require additional context. Since every parser must provide more information at runtime to create instances, local tags help a YAML document to be exchanged with other languages (every parser is free to define what to do with `!car`)

Implicit tags are assigned to every scalar and they are determined from the [regular expressions](http://yaml.org/type/index.html). It is possible to add custom implicit tags.
When a scalar is created the following order is used:
  1. explicit tag - when it is present.
  1. implicit tag - when the runtime class of the instance is unknown
It means that the implicit tag is ignored as soon as any other information about a scalar is available.

## Deviation from PyYAML ##

  * SnakeYAML does not emit '...' (end of document) for simple documents
  * SnakeYAML emits Unicode (not ASCII) by default
  * Global tags do not contain a "`namespace`" but only the full class name
  * '8e-06' and '8e6' scalars use implicit !!float tag in SnakeYAML. This is how it is defined in the YAML 1.2 specification ([issue](http://code.google.com/p/snakeyaml/issues/detail?id=130)). Since the values do not match the regular expressions for floats, PyYAML is parsing these values as strings.
  * SnakeYAML (and libyaml) [allows](http://code.google.com/p/snakeyaml/issues/detail?id=136) TAB characters to appear inside a plain scalar

## YAML syntax ##

A good introduction to the YAML syntax is
[Chapter 2 of the YAML specification](http://yaml.org/spec/1.1/#id857168).

Here we present most common YAML constructs together with the corresponding Java objects.


### Documents ###

YAML stream is a collection of zero or more documents. An empty stream contains no documents.
Documents are separated with `---`. Documents may optionally end with `...`.
A single document may or may not be marked with `---`.

Example of an implicit document:
```
- Multimedia
- Internet
- Education
```

Example of an explicit document:
```
---
- Afterstep
- CTWM
- Oroborus
...
```

Example of several documents in the same stream:
```
---
- Ada
- APL
- ASP

- Assembly
- Awk
---
- Basic
---
- C
- C#    # Note that comments are denoted with ' #' (space and #).
- C++
- Cold Fusion
```


### Block sequences ###

In the block context, sequence entries are denoted by `- ` (dash and space):
```
# YAML
- The Dagger 'Narthanc'
- The Dagger 'Nimthanc'
- The Dagger 'Dethanc'
```
```
# Java
["The Dagger 'Narthanc'", "The Dagger 'Nimthanc'", "The Dagger 'Dethanc'"]
```

Block sequences can be nested:
```
# YAML
-
  - HTML
  - LaTeX
  - SGML
  - VRML
  - XML
  - YAML
-
  - BSD
  - GNU Hurd
  - Linux
```
```
# Java
[['HTML', 'LaTeX', 'SGML', 'VRML', 'XML', 'YAML'], ['BSD', 'GNU Hurd', 'Linux']]
```

It's not necessary to start a nested sequence with a new line:
```
# YAML
- 1.1
- - 2.1
  - 2.2
- - - 3.1
    - 3.2
    - 3.3
```
```
# Java
[1.1, [2.1, 2.2], [[3.1, 3.2, 3.3]]]
```

A block sequence may be nested to a block mapping. Note that in this
case it is not necessary to indent the sequence.
```
# YAML
left hand:
- Ring of Teleportation
- Ring of Speed

right hand:
- Ring of Resist Fire
- Ring of Resist Cold
- Ring of Resist Poison
```
```
# Java
{'right hand': ['Ring of Resist Fire', 'Ring of Resist Cold', 'Ring of Resist Poison'],
'left hand': ['Ring of Teleportation', 'Ring of Speed']}
```


### Block mappings ###

In the block context, keys and values of mappings are separated by `: ` (colon and space):
```
# YAML
base armor class: 0
base damage: [4,4]
plus to-hit: 12
plus to-dam: 16
plus to-ac: 0
```
```
# Java
{'plus to-hit': 12, 'base damage': [4, 4], 'base armor class': 0, 'plus to-ac': 0, 'plus to-dam': 16}
```

Complex keys are denoted with `? ` (question mark and space):
```
# YAML
? !!python/tuple [0,0]
: The Hero
? !!python/tuple [0,1]
: Treasure
? !!python/tuple [1,0]
: Treasure
? !!python/tuple [1,1]
: The Dragon
```
```
# Java
{(0, 1): 'Treasure', (1, 0): 'Treasure', (0, 0): 'The Hero', (1, 1): 'The Dragon'}
```

Block mapping can be nested:
```
# YAML
hero:
  hp: 34
  sp: 8
  level: 4
orc:
  hp: 12
  sp: 0
  level: 2
```
```
# Java
{'hero': {'hp': 34, 'sp': 8, 'level': 4}, 'orc': {'hp': 12, 'sp': 0, 'level': 2}}
```

A block mapping may be nested in a block sequence:
```
# YAML
- name: PyYAML
  status: 4
  license: MIT
  language: Python
- name: PySyck
  status: 5
  license: BSD
  language: Python
```
```
# Java
[{'status': 4, 'language': 'Python', 'name': 'PyYAML', 'license': 'MIT'},
{'status': 5, 'license': 'BSD', 'name': 'PySyck', 'language': 'Python'}]
```


### Flow collections ###

The syntax of flow collections in YAML is very close to the syntax of list and
dictionary constructors in Python:
```
# YAML
{ str: [15, 17], con: [16, 16], dex: [17, 18], wis: [16, 16], int: [10, 13], chr: [5, 8] }
```
```
# Java
{'dex': [17, 18], 'int': [10, 13], 'chr': [5, 8], 'wis': [16, 16], 'str': [15, 17], 'con': [16, 16]}
```


### Scalars ###

There are 5 styles of scalars in YAML: plain, single-quoted, double-quoted, literal, and folded:
```
# YAML
plain: Scroll of Remove Curse
single-quoted: 'EASY_KNOW'
double-quoted: "?"
literal: |    # Borrowed from http://www.kersbergen.com/flump/religion.html
  by hjw              ___
     __              /.-.\
    /  )_____________\\  Y
   /_ /=== == === === =\ _\_
  ( /)=== == === === == Y   \
   `-------------------(  o  )
                        \___/
folded: >
  It removes all ordinary curses from all equipped items.
  Heavy or permanent curses are unaffected.
```
```
# Java
{'plain': 'Scroll of Remove Curse',
'literal':
    'by hjw              ___\n'
    '   __              /.-.\\\n'
    '  /  )_____________\\\\  Y\n'
    ' /_ /=== == === === =\\ _\\_\n'
    '( /)=== == === === == Y   \\\n'
    ' `-------------------(  o  )\n'
    '                      \\___/\n',
'single-quoted': 'EASY_KNOW',
'double-quoted': '?',
'folded': 'It removes all ordinary curses from all equipped items. Heavy or permanent curses are unaffected.\n'}
```

Each style has its own quirks. A plain scalar does not use indicators to denote its
start and end, therefore it's the most restricted style. Its natural applications are
names of attributes and parameters.

Using single-quoted scalars, you may express any value that does not contain special characters.
No escaping occurs for single quoted scalars except that duplicate quotes `''` are replaced
with a single quote `'`.

Double-quoted is the most powerful style and the only style that can express any scalar value.
Double-quoted scalars allow _escaping_. Using escaping sequences `\x**` and `\u****`,
you may express any ASCII or Unicode character.

There are two kind of block scalar styles: **literal** and **folded**. The literal style is
the most suitable style for large block of text such as source code. The folded style is similar
to the literal style, but two consequent non-empty lines are joined to a single line separated
by a space character.


### Aliases ###

Using YAML you may represent objects of arbitrary graph-like structures. If you want to refer
to the same object from different parts of a document, you need to use anchors and aliases.

Anchors are denoted by the `&` indicator while aliases are denoted by `*`. For instance,
the document
```
left hand: &A
  name: The Bastard Sword of Eowyn
  weight: 30
right hand: *A
```
expresses the idea of a hero holding a heavy sword in both hands.

SnakeYAML now fully supports recursive objects. For instance, the document
```
&A [ *A ]
```
will produce a list object containing a reference to itself.


### Tags ###

Tags are used to denote the type of a YAML node. Standard YAML tags are defined at
http://yaml.org/type/index.html.

Tags may be implicit:
```
boolean: true
integer: 3
float: 3.14
```
```
#!python
{'boolean': True, 'integer': 3, 'float': 3.14}
```

or explicit:
```
boolean: !!bool "true"
integer: !!int "3"
float: !!float "3.14"
```
```
#!python
{'boolean': True, 'integer': 3, 'float': 3.14}
```

Plain scalars without explicitly defined tag are subject to implicit tag
resolution. The scalar value is checked against a set of regular expressions
and if one of them matches, the corresponding tag is assigned to the scalar.
SnakeYAML allows an application to add custom implicit tag resolvers.


## YAML tags and Java types ##

The following table describes how nodes with different tags are converted
to Java objects.

|        **YAML tag**               |           **Java type**             |
|:----------------------------------|:------------------------------------|
| _Standard YAML tags_              |                                     |
| `!!null`                          | `null`                              |
| `!!bool`                          | `Boolean`                           |
| `!!int`                           | `Integer, Long, BigInteger`         |
| `!!float`                         | `Double`                            |
| `!!binary`                        | `String`                            |
| `!!timestamp`                     | `java.util.Date`, `java.sql.Date`, `java.sql.Timestamp` |
| `!!omap`, `!!pairs`               | `List` of `Object[]`                |
| `!!set`                           | `Set`                               |
| `!!str`                           | `String`                            |
| `!!seq`                           | `List`                              |
| `!!map`                           | `Map`                               |

An example of loading and dumping [Global tags](http://yaml.org/spec/1.1/#id858961) can be found
[here](http://code.google.com/p/snakeyaml/source/browse/src/test/java/org/yaml/snakeyaml/Example2_24Test.java).

## Collections ##

Default implementations of collections are:
  * _List_: `ArrayList`
  * _Map_: `LinkedHashMap` (the order is implicitly defined)

It is possible to define other default implementations. An example can be found
[here](http://code.google.com/p/snakeyaml/source/browse/src/test/java/examples/CustomListExampleTest.java) for List and
[here](http://code.google.com/p/snakeyaml/source/browse/src/test/java/examples/CustomMapExampleTest.java) for Map

## Merge ##

YAML has a "merge" specification: http://yaml.org/type/merge.html

SnakeYAML supports merging one or more mappings.

The syntax for a standard mapping is as follows:
```
- &item001
  boolean: true
  integer: 3
- <<: *item001
  integer: 7
```

The resulting YAML document will be a List of two Maps. The first one will map "boolean" to Boolean(true) and "integer" to Integer(3). The second will map "boolean" to Boolean(true) and "integer" to Integer(7) . Note that the updated values will overwrite the "merged" values.

In addition, a sequence of merges may be specified. In this case, the merged value is the first value found in the list of merged maps. For example:

```
- &item001
  boolean: true
  integer: 7
  float: 3.14
- &item002
  boolean: false
- <<: [ *item002, *item001 ]
```

The resulting list contains 3 maps. The first is {"boolean":Boolean(true), "integer":Integer(7), "float":3.14}. The second is {"boolean":Boolean(false)}. The third will be {"boolean":Boolean(false), "integer":Integer(7), "float":Float(3.14)}.

Note that resulting mapping will refer to the exact same object as the merged mapping does. In this example, there are 2 Booleans, 1 Integer and 1 Float.

Introduced in version 1.8 of SnakeYAML is the ability to merge mappings that are then converted into custom Java objects (as opposed to simple Map objects). A merged Java object is very similar, except that the type of object must be specified. An example, using a Weapon class with properties name, range and damage:

```
- !!Weapon
  &sword
  name: Sword
  range: 5
  damage: 3
- !!Weapon
  <<: *sword
  name: Heavy Sword
  damage: 4
```

The resulting List will contain two Weapon objects. The first will be a "Sword" with range 5 and damage 3. The second will be a "Heavy Sword" with range 5 and damage 4.

The tags of the two merged mappings do not need to be the same. The following is also allowed:

```
- !!Weapon
  &shortsword
  name: Short Sword
  range: 5
  damage: 3
- !!MagicWeapon
  <<: *shortsword
  name: Short Sword +1
  bonus: 1
```

The resulting list contains a `Weapon` named "Short Sword" and a `MagicWeapon` named "Short Sword +1".

The Java types of the merged objects do not actually need to be the same, or even related:

```
- !!Person
  &the_queen
  name: Queen Elizabeth II
- !!Ship
  <<: *the_queen
  designation: HMS
```

This document specifies a Person named "Queen Elizabeth II" and a Ship with designation "HMS" and name "Queen Elizabeth II": the ship named after the Queen. In this example, we assume that Person and Ship have no common ancestor except java.lang.Object, and so the properties are declared separately.

A few examples can be found [here](http://code.google.com/p/snakeyaml/source/browse/src/test/java/org/yaml/snakeyaml/issues/issue100/MergeJavaBeanTest.java)

## Deviations from the specification ##

_need to update this section_

  * rules for tabs in YAML are confusing. We are close, but not there yet. Perhaps both the spec and the parser should be fixed. Anyway, the best rule for tabs in YAML is to not use them at all.
  * Byte order mark. The initial BOM is stripped, but BOMs inside the stream are considered as parts of the content. It can be fixed, but it's not really important now.
  * ~~Empty plain scalars are not allowed if alias or tag is specified.~~ This is done to prevent anomalities like `[ !tag, value]`, which can be interpreted both as `[ !<!tag,> value ]` and `[ !<!tag> "", "value" ]`. The spec should be fixed.
  * Indentation of flow collections. The spec requires them to be indented more than their block parent node. Unfortunately this rule renders many intuitively correct constructs invalid, for instance,
```
block: {
} # this is indentation violation according to the spec.
```
  * ':' is not allowed for plain scalars in the flow mode. ~~`{1:2}` is interpreted as `{ 1 : 2 }`.~~
  * scalars 'x' and 'y' are not parsed as booleans but as strings. It is the same how it is done in other parsers and it is closer to [how it is defined](http://www.yaml.org/spec/1.2/spec.html#id2803629) in the coming 1.2 specification