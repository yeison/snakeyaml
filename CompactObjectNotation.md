## Compact Object Notation ##


One of the common usages of YAML is a human-readable format to encode Java objects. Standard `JavaBeans` are supported but this approach requires an empty constructor to create an instance.
SnakeYAML provides a special mark-up to encode object in a compact form:

```
package.Name(argument1, property1=value1, property2=value2)
```
When explicitly enabled this scalar is interpreted as following:

  1. Everything before '(' is a prefix (by default is serves as a full class Name, but it can be changed)
  1. ')' must be the last character of the scalar
  1. ',' splits the content  between brackets into tokens
  1. When a token does not have '=' then it is considered as a parameter for a constructor to create an instance
  1. When a token has '=' then it is considered as a `JavaBean` property
  1. Tokens may not contain ',' or '='
  1. Tokens are trimmed so these two are identical:
```
package.Name ( argument1 , property1 = value1 , property2 = value2 )
```
```
package.Name(argument1,property1=value1,property2=value2)
```
  1. implicit types do not work for tokens, all the values are always Strings

It gives a very powerful way to create object with just one line of YAML:

```
org.package.Container(id123)
```
```
org.package.Container(name=parent, title=A title)
```
```
org.package.Container(id123, name=parent, title=A title)
```

## Mapping ##
Scalar may be combined with mapping:
```
org.package.Container(id123):
  name: parent
  title: More text
```
```
org.package.Container(id123, name=parent):
  title: A bit more text which can be very long
  number: 17
  description: |-
    many more lines
    can be used with
    this layout
```

In this case the content of the mapping node is used to define more `JavaBean` properties. Implicit types work as usual, so number is parsed as Integer

## Sequence ##
Scalar may be combined with sequence:
```
Container(id12): 
  - Item(id111, description = I think; therefore I am.)
  - Item(id222, description = Life is 10% what happens to me and 90% how I react to it)
  - Item(id333, description = Success consists of going from failure to failure without loss of enthusiasm)
```

By default SnakeYAML tries to locate a property which gets the sequence of Items. It must be a java.lang.List. If there is exactly one such a property then no configuration is required.

When the default behaviour does not work override
`applySequence(Object bean, List<?> value)`

## Example ##

```
Container(id12): 
  - Item(id111, description = I think; therefore I am.): {size: 15, ratio: 0.125}
  - Item(id222):
      size: 17
      ratio: 0.333
      description: >
        We do not need new lines
        here, just replace them
        all with spaces
  - Item(id333):
      size: 52
      ratio: 0.88
      description: |-
        Please preserve all
        the lines because they may be
        important, but do not include the last one !!!
```

Source code [example](http://code.google.com/p/snakeyaml/source/browse/src/test/java/org/yaml/snakeyaml/extensions/compactnotation/CompactConstructorExampleTest.java)