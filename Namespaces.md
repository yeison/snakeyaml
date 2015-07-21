# Namespaces for tags in SnakeYAML #

**Tags for dumping (serializing) by default (without any extra definition)**

  * Standard Java classes which have one-to-one YAML representative (`String, Integer, List, Map`)

`!!str '123'`, or no tag because it is implicit


  * Standard Java classes which do not have one-to-one YAML representative (`Long, SortedSet, BigInteger`)

`!!java.lang.Long`


  * `JavaBeans` from JRE (`java.awt.Point`)

`!!java.awt.Point`


  * Custom `JavaBeans` (`com.package.ClassName`)

`!!com.package.ClassName`