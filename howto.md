
# Step-by-step instructions for common tasks #

## How to substitute object in YAML document with a custom object ##
Sometimes you need to instantiate a different object then encoded in the YAML document. For instance for the test you may need to look at the 'ID' property and instead of creating the object from YAML definition fetch it from the database using the provided ID as the key.
This is how the custom constructor may look like:
```
class SelectiveConstructor extends Constructor {
    public SelectiveConstructor() {
        // define a custom way to create a mapping node
        yamlClassConstructors.put(NodeId.mapping, new MyPersistentObjectConstruct());
    }

    class MyPersistentObjectConstruct extends Constructor.ConstructMapping {
        @Override
        protected Object constructJavaBean2ndStep(MappingNode node, Object object) {
            Class type = node.getType();
            if (type.equals(MyPersistentObject.class)) {
                // create a map
                Map map = constructMapping(node);
                String id = (String) map.get("id");
                return new MyPersistentObject(id, 17);
            } else {
                // create JavaBean
                return super.constructJavaBean2ndStep(node, object);
            }
        }
    }
}
```

This is the code:
```
public void testConstructor() throws IOException {
    Yaml yaml = new Yaml(new SelectiveConstructor());
    List<?> data = (List<?>) yaml
            .load("- 1\n- 2\n- !!examples.MyPersistentObject {amount: 222, id: persistent}");
    // System.out.println(data);
    assertEquals(3, data.size());
    MyPersistentObject myObject = (MyPersistentObject) data.get(2);
    assertEquals(17, myObject.getAmount());
    assertEquals("persistent", myObject.getId());
}
```
The complete source can be found [here](http://code.google.com/p/snakeyaml/source/browse/src/test/java/examples/SelectiveConstructorTest.java).

## How to use YAML with Spring to load properties ##
The [Spring](http://www.springsource.org/) framework doesn’t have a YAML-based `PropertyPlaceholderConfigurer` to load nested properties from a YAML property file.

[This is](http://dev.bostone.us/2011/02/03/use-yaml-with-spring-to-load-properties/#awp::2011/02/03/use-yaml-with-spring-to-load-properties/) an example of how it can be solved.

## How to use SnakeYAML under OSGi ##
[Use CustomClassLoaderConstructor](http://stackoverflow.com/questions/4940379/using-snakeyaml-under-osgi)

## How to avoid implicit types ##
By default SnakeYAML tries to create implicit types (Date, Number) when the scalar matches the corresponding regular expression. It is possible [(see examples)](http://code.google.com/p/snakeyaml/source/browse/#hg%2Fsrc%2Ftest%2Fjava%2Fexamples%2Fresolver) to configure [Resolver](http://code.google.com/p/snakeyaml/source/browse/src/test/java/examples/resolver/CustomResolver.java)
```
public class CustomResolver extends Resolver {

    /*
     * do not resolve float and timestamp
     */
    protected void addImplicitResolvers() {
        addImplicitResolver(Tag.BOOL, BOOL, "yYnNtTfFoO");
        // addImplicitResolver(Tags.FLOAT, FLOAT, "-+0123456789.");
        addImplicitResolver(Tag.INT, INT, "-+0123456789");
        addImplicitResolver(Tag.MERGE, MERGE, "<");
        addImplicitResolver(Tag.NULL, NULL, "~nN\0");
        addImplicitResolver(Tag.NULL, EMPTY, null);
        // addImplicitResolver(Tags.TIMESTAMP, TIMESTAMP, "0123456789");
        addImplicitResolver(Tag.VALUE, VALUE, "=");
    }
}
```
to [parse these scalars as Strings](http://code.google.com/p/snakeyaml/source/browse/src/test/java/examples/resolver/CustomResolverTest.java#44).
```
    public void testResolverToLoad() {
        Yaml yaml = new Yaml(new Constructor(), new Representer(), new DumperOptions(),
                new CustomResolver());
        Map<Object, Object> map = (Map<Object, Object>) yaml.load("1.0: 2009-01-01");
        assertEquals(1, map.size());
        assertEquals("2009-01-01", map.get("1.0"));
    }
```

## How to skip a `JavaBean` property ##
Sometimes there is a need to avoid dumping some `JavaBean` properties. The requirement may be dynamic, based on the value of the property. For instance empty collections or 'null' values should not be dumped (serialized).
[This is](http://code.google.com/p/snakeyaml/source/browse/src/test/java/org/yaml/snakeyaml/issues/issue133/StackOverflowTest.java) an example how it can be implemented:
```
    class PointRepresenter extends Representer {

        @Override
        protected NodeTuple representJavaBeanProperty(Object javaBean, Property property,
                Object propertyValue, Tag customTag) {
            if (javaBean instanceof Point && "location".equals(property.getName())) {
                return null;
            } else {
                return super.representJavaBeanProperty(javaBean, property, propertyValue, customTag);
            }
        }
    }
```
where
  * `javaBean` - is the actual instance of the `JavaBean`
  * `property` - internal representation of the `JavaBean` property
  * `propertyValue` - the actual value which holds the property (in this example it is not used)

## How to configure scalar style or flow style for `JavaBean` properties ##
During serialisation (dumping), it may be desired to affect scalar style of flow style of selected `JavaBean` properties. This is
[an example](http://code.google.com/p/snakeyaml/source/browse/src/test/java/org/yaml/snakeyaml/issues/issue29/FlexibleScalarStylesInJavaBeanTest.java) how to configure it.
```
if (property.getName().equals("description")) {
    // if description contains ':' use folded scalar style and
    // not single quoted scalar style
    if (bean.getDescription().indexOf(':') > 0) {
        ScalarNode n = (ScalarNode) standard.getValueNode();
        return new NodeTuple(standard.getKeyNode(), new ScalarNode(n.getTag(),
               n.getValue(), n.getStartMark(), n.getEndMark(), '>'));
    }
}
```
The example also shows how the default order can be changed (see `PropertyComparator`).
## How to parse `JodaTime` ##
Since `JodaTime` is no `JavaBean` (because it does not have an empty constructor), it requires some extra treatment when parsing.
The example [is here](http://code.google.com/p/snakeyaml/source/browse/#hg%2Fsrc%2Ftest%2Fjava%2Fexamples%2Fjodatime).
```
private class ConstructJodaTimestamp extends ConstructYamlTimestamp {
    public Object construct(Node node) {
        Date date = (Date) super.construct(node);
        return new DateTime(date, DateTimeZone.UTC);
    }
}
```
When the `JodaTime` instance is the `JavaBean` property you can use [the following](http://code.google.com/p/snakeyaml/source/browse/src/test/java/examples/jodatime/JodaTimeExampleTest.java#165):
```
Yaml y = new Yaml(new JodaPropertyConstructor());
```
```
class JodaPropertyConstructor extends Constructor {
    public JodaPropertyConstructor() {
        yamlClassConstructors.put(NodeId.scalar, new TimeStampConstruct());
    }

    class TimeStampConstruct extends Constructor.ConstructScalar {
        @Override
        public Object construct(Node nnode) {
            if (nnode.getTag().equals("tag:yaml.org,2002:timestamp")) {
                Construct dateConstructor = yamlConstructors.get(Tag.TIMESTAMP);
                Date date = (Date) dateConstructor.construct(nnode);
                return new DateTime(date, DateTimeZone.UTC);
            } else {
                return super.construct(nnode);
            }
        }

    }
}
```
## Create Long instead of Integer ##
SnakeYAML creates `java.lang.Integer` for `!!int`.
When the value does not fit then it tries to create `java.lang.Long` and `java.math.BigInteger`.
It possible to configure a custom way to create numbers. See [the example](http://code.google.com/p/snakeyaml/source/browse/src/test/java/examples/jodatime/JodaTimeImplicitContructor.java) for `!!timestamp`. Any implicit tag can be re-defined.

## Android ##
Now SnakeYAML's pom contains [android](http://www.android.com/) profile to build up-to-date Android-"compatible" jar.
```
mvn -Pandroid clean package
```

Related
  * [stackoverflow:is-there-a-good-yaml-library-for-android](http://stackoverflow.com/questions/4054083/is-there-a-good-yaml-library-for-android)
  * [issue 92](https://code.google.com/p/snakeyaml/issues/detail?id=92)
  * [blog-post:yaml-parser-for-android](http://aleksmaus.blogspot.com/2012/01/yaml-parser-for-android.html)


## To be done ##
  * JavaBeans
  * JavaBeans to support SortedSet property when it is encoded as a sequence
  * order of JavaBean properties
  * Context for error reporting consumes a lot of resources
  * Add examples to create scalars that match custom regular expression
  * BeanAccess.FIELD (Allow direct field access bypassing setters and getters)
  * Provide sequence support for loading java.util.Set
  * JodaTime
  * Add examples for dumping custom values for !!bool and !!null
  * Provide example for skipping null and empty collections
  * Enhancement for a pretty format that combines BLOCK and FLOW
  * Don't dump read-only properties by default. DumperOptions gets a setting to include read-only JavaBean properties
  * Add example to ignore unknown tags (2009-12-08)
  * Introduce multi contructors (tag prefix). A family of tags may be processed by a single constructor (2009-11-25)
  * Add Velocity example
  * Extend Resolver to support custom implicit types
  * Add possibility to define a custom Class Loader
  * Deliver possibility to load immutable instances with no global tags. Reflection for constructor arguments is used to get the runtime classes (2009-08-04)
  * Add a simple test for Java Generics (BirdTest). Unfortunately it shows that some JVM implementations do not recognise classes for JavaBean properties at runtime. It leads to unnecessary global tags. See http://code.google.com/p/snakeyam/wiki/Documentation#Generics for details (2009-07-13)
  * [issue 99](https://code.google.com/p/snakeyaml/issues/detail?id=99): binary tag without escaping line breaks
  * JavaBean without empty constructor but encoded as mapping (BeanAccess.FIELD and private empty constructor)
  * [configgy](https://github.com/robey/configgy) ?