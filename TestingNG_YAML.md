## YAML support (with SnakeYAML 1.7) ##
(see [the blog post](http://beust.com/weblog/2010/08/15/yaml-the-forgotten-victim-of-the-format-wars/comment-page-1/#comment-8391))
```
JavaBeanLoader<XmlSuite> loader = new JavaBeanLoader<XmlSuite>(XmlSuite.class);
if (is == null) is = new FileInputStream(new File(filePath));
XmlSuite result = loader.load(new UnicodeReader(is));//UnicodeReader used to respect BOM
```

# complete org.testng.internal.Yaml #

```
package org.testng.internal;

import java.io.File;

/**
 * YAML support for TestNG.
 * 
 * @author cbeust
 */
public class Yaml {

  private static void addToMap(Map suite, String name, Map target) {
    List<Map<String, String>> parameters = (List<Map<String, String>>) suite.get(name);
    if (parameters != null) {
      for (Map<String, String> parameter : parameters) {
        for (Map.Entry p : parameter.entrySet()) {
          target.put(p.getKey(), p.getValue().toString());
        }
      }
    }
  }

  private static void addToList(Map suite, String name, List target) {
    List<Map<String, String>> parameters = (List<Map<String, String>>) suite.get(name);
    if (parameters != null) {
      for (Map<String, String> parameter : parameters) {
        for (Map.Entry p : parameter.entrySet()) {
          target.add(p.getValue().toString());
        }
      }
    }
  }

  public static XmlSuite parse(String filePath, InputStream is)
      throws FileNotFoundException {
    JavaBeanLoader<XmlSuite> loader = new JavaBeanLoader<XmlSuite>(XmlSuite.class);
    if (is == null) is = new FileInputStream(new File(filePath));
    XmlSuite result = loader.load(new UnicodeReader(is));//UnicodeReader used to respect BOM
    result.setFileName(filePath);
    // DEBUG
//    System.out.println("[Yaml] " + result.toXml());

    // Adjust XmlTest parents
    for (XmlTest t : result.getTests()) {
      t.setSuite(result);
    }

    return result;
  }

  private static void setField(Object xml, Map<?, ?> map, String key, String methodName,
      Class<?> parameter) {
    Object o = map.get(key);
    if (o != null) {
      Method m;
      try {
        m = xml.getClass().getMethod(methodName, parameter);
        m.invoke(xml, o);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  private static void maybeAdd(StringBuilder sb, String key, Object value, Object def) {
    maybeAdd(sb, "", key, value, def);
  }

  private static void maybeAdd(StringBuilder sb, String sp, String key, Object value, Object def) {
    if (value != null && ! value.equals(def)) {
      sb.append(sp).append(key).append(": ").append(value.toString()).append("\n");
    }
  }

  public static StringBuilder toYaml(XmlSuite suite) {
    StringBuilder result = new StringBuilder();

    maybeAdd(result, "name", suite.getName(), null);
    maybeAdd(result, "junit", suite.isJUnit(), XmlSuite.DEFAULT_JUNIT);
    maybeAdd(result, "verbose", suite.getVerbose(), XmlSuite.DEFAULT_VERBOSE);
    maybeAdd(result, "threadCount", suite.getThreadCount(), XmlSuite.DEFAULT_THREAD_COUNT);
    maybeAdd(result, "dataProviderThreadCount", suite.getDataProviderThreadCount(),
        XmlSuite.DEFAULT_DATA_PROVIDER_THREAD_COUNT);
    maybeAdd(result, "timeOut", suite.getTimeOut(), null);
    maybeAdd(result, "parallel", suite.getParallel(), XmlSuite.DEFAULT_PARALLEL);
    maybeAdd(result, "skipFailedInvocationCounts", suite.skipFailedInvocationCounts(),
        XmlSuite.DEFAULT_SKIP_FAILED_INVOCATION_COUNTS);

    toYaml(result, "parameters", "", suite.getParameters());
    toYaml(result, suite.getPackages());

    if (suite.getListeners().size() > 0) {
      result.append("listeners:\n");
      toYaml(result, "  ", suite.getListeners());
    }

    if (suite.getPackages().size() > 0) {
      result.append("packages:\n");
      toYaml(result, suite.getPackages());
    }
    toYaml(result, "listeners", suite.getListeners());
    if (suite.getTests().size() > 0) {
      result.append("tests:\n");
      for (XmlTest t : suite.getTests()) {
        toYaml(result, "  ", t);
      }
    }

    if (suite.getChildSuites().size() > 0) {
      result.append("suite-files:\n");
      toYaml(result, "  ", suite.getSuiteFiles());
    }

    return result;
  }

  private static void toYaml(StringBuilder result, String sp, XmlTest t) {
    String sp2 = sp + "  ";
    result.append(sp).append("- name: ").append(t.getName()).append("\n");

    maybeAdd(result, sp2, "junit", t.isJUnit(), XmlSuite.DEFAULT_JUNIT);
    maybeAdd(result, sp2, "verbose", t.getVerbose(), XmlSuite.DEFAULT_VERBOSE);
    maybeAdd(result, sp2, "timeOut", t.getTimeOut(), null);
    maybeAdd(result, sp2, "parallel", t.getParallel(), XmlSuite.DEFAULT_PARALLEL);
    maybeAdd(result, sp2, "skipFailedInvocationCounts", t.skipFailedInvocationCounts(),
        XmlSuite.DEFAULT_SKIP_FAILED_INVOCATION_COUNTS);

    maybeAdd(result, "preserveOrder", sp2, t.getPreserveOrder(), XmlTest.DEFAULT_PRESERVE_ORDER);

    toYaml(result, "parameters", sp2, t.getTestParameters());

    if (t.getIncludedGroups().size() > 0) {
      result.append(sp2).append("includedGroups: [ ")
          .append(Utils.join(t.getIncludedGroups(), ","))
          .append(" ]\n");
    }

    if (t.getExcludedGroups().size() > 0) {
      result.append(sp2).append("excludedGroups: [ ")
          .append(Utils.join(t.getExcludedGroups(), ","))
          .append(" ]\n");
    }

    Map<String, List<String>> mg = t.getMetaGroups();
    if (mg.size() > 0) {
      result.append(sp2).append("metaGroups: { ");
      boolean first = true;
      for (String group : mg.keySet()) {
        if (! first) result.append(", ");
        result.append(group).append(": [ ")
        .append(Utils.join(mg.get(group), ",")).append(" ] ");
        first = false;
      }
      result.append(" }\n");
    }

    if (t.getXmlPackages().size() > 0) {
      result.append(sp2).append("xmlPackages:\n");
      for (XmlPackage xp : t.getXmlPackages())  {
        toYaml(result, sp2 + "  - ", xp);
      }
    }
    
    if (t.getXmlClasses().size() > 0) {
      result.append(sp2).append("classes:\n");
      for (XmlClass xc : t.getXmlClasses())  {
        toYaml(result, sp2 + "  ", xc);
      }
    }


    result.append("\n");
  }

  private static void toYaml(StringBuilder result, String sp2, XmlClass xc) {
    List<XmlInclude> im = xc.getIncludedMethods();
    List<String> em = xc.getExcludedMethods();
    String name = im.size() > 0 || em.size() > 0 ? "name: " : "";

    result.append(sp2).append("- " + name).append(xc.getName()).append("\n");
    if (im.size() > 0) {
      result.append(sp2 + "  includedMethods:\n");
      for (XmlInclude xi : im) {
        toYaml(result, sp2 + "    ", xi);
      }
    }

    if (em.size() > 0) {
      result.append(sp2 + "  excludedMethods:\n");
      toYaml(result, sp2 + "    ", em);
    }
  }

  private static void toYaml(StringBuilder result, String sp2, XmlInclude xi) {
    result.append(sp2 + "- " + xi.getName()).append("\n");
  }

  private static void toYaml(StringBuilder result, String sp, List<String> strings) {
    for (String l : strings) {
      result.append(sp).append("- ").append(l).append("\n");
    }
  }

  private static final String SP = "  ";

  private static void toYaml(StringBuilder sb, List<XmlPackage> packages) {
    if (packages.size() > 0) {
      sb.append("packages:\n");
      for (XmlPackage p : packages) {
        toYaml(sb, "  ", p);
      }
    }
    for (XmlPackage p : packages) {
      toYaml(sb, "  ", p);
    }
  }

  private static void toYaml(StringBuilder sb, String sp, XmlPackage p) {
    sb.append(sp).append("name: ").append(p.getName()).append("\n");

    generateIncludeExclude(sb, sp, "includes", p.getInclude());
    generateIncludeExclude(sb, sp, "excludes", p.getExclude());
  }

  private static void generateIncludeExclude(StringBuilder sb, String sp,
      String key, List<String> includes) {
    if (includes.size() > 0) {
      sb.append(sp).append("  ").append(key).append("\n");
      for (String inc : includes) {
        sb.append(sp).append("    ").append(inc);
      }
    }
  }

  private static void mapToYaml(Map<String, String> map, StringBuilder out) {
    if (map.size() > 0) {
      out.append("{ ");
      boolean first = true;
      for (Map.Entry<String, String> e : map.entrySet()) {
        if (! first) out.append(", ");
        first = false;
        out.append(e.getKey() + ": " + e.getValue());
      }
      out.append(" }\n");
    }
  }

  private static void toYaml(StringBuilder sb, String key, String sp,
      Map<String, String> parameters) {
    if (parameters.size() > 0) {
      sb.append(sp).append(key).append(": ");
      mapToYaml(parameters, sb);
    }
  }

  public static void main(String[] args)
      throws FileNotFoundException, ParserConfigurationException, SAXException, IOException {
    Collection<XmlSuite> s = new Parser(args[0]).parse();
    System.out.println(s.iterator().next().toXml());
  }
}

```