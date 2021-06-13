[![Maven Central](https://img.shields.io/maven-central/v/com.github.schuettec/jbeanviews.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.github.schuettec%22%20AND%20a:%22jbeanviews%22)
![Build status](https://github.com/schuettec/jbeanviews/actions/workflows/build.yml/badge.svg)


# JBeanViews - A declarative view builder for Java Beans

# Table of Contents

# Long story short

This library simplifies mapping of Java Beans. The destination of a mapping is called a bean view because the structure and data types of the view's attributes might strongly differ. Every property of a bean view can be bound to a property of a source bean, transitively!

JBeanViews provides a heuristic approach to build implicit view property bindings. This results in a minimum of mapping code that has to be written.

# Example

Let's have a look at a short example:

Consider the following source bean:

```
public class Customer {
  private Person person;
  private List<Address> addresses;
  private String number;
  private boolean deleted;
  // Constructor, getter/setter, etc
}

public class Person {
  private String forename;
  private String name;
  // Constructor, getter/setter, etc
}

public class Address {
  private String street;
  private String houseNumber;
  private String city;
  private String zipCode;
  // Constructor, getter/setter, etc
}
```

You can map the Java Bean `Customer` to a bean view that looks quite different:

```
public class CustomerView {
  private String forename;
  private String name;
  private List<AddressView> addresses;
  private MetaDataView metaData;
  // Constructor, getter/setter, etc
}

public class AddressView {
  private String street;
  private String houseNumber;
  private String city;
  private String zipCode;
}

public class MetaDataView {
  private String number;
  private boolean deleted;
}
```

Let's say the above mapping from `Customer` to `CustomerView` should work the following way:
- `CustomerView.forename` and `name` should be mapped to the persons's forename and name: `Customer.getPerson().getForename()` and `Customer.getPerson().getName()`
- `CustomerView.addresses` should be mapped to the customer's list of addresses because both typed contain exact the same properties, even though the types `AddressView` and `Address` differ.
- The properties `Customer.number` and `Customer.deleted` should be restructured to a complex type in `CustomerView.metaData`, so they are no root propertis in the view anymore.

**Now the fun part:** The above mapping can be achieve without code that goes beyond declaration of the types to map:

```
BeanView<Customer, CustomerView> beanView = BeanViews.of(Customer.class)
                                                     .toView(CustomerView.class)
                                                     .get();
```

**Why is that even possible?**

Bean views are created using a simple guessing rule:
- If field names have the same name they are candidates for a binding.
    - If the types are the same, the binding is created automatically
    - If the types differ
      - but an explicit type/property conversion was registered, the binding is created
      - a bean view can be created automatically using implicit bindings, the binding is created

This also applies to transitive properties. As you can see in the example, the property `CustomerView.forename` can be automatically bound to `Person.forename` because property name and type are equal.

Implicit transitive bindings are only created if unambiguous.
If a property with name `forname` occurs twice, the binding would be ambiguous. In this case the heuristic picks up a property `forename` by comparing the transitive nesting. Only if exactly one property `forname` with the same level as the view property is available, the binding is created.

This way you get unambiguous mappings.

# Bindings

The declaration of bindings is very easy:

## Singular properties

You can bind singular properties like
```
.bind(ListSizeView::getListSize)
.to(Customer::getAddresses)
.map(List::size)
```

This example adds a field conversion to the binding to map the list size to the view's property.

## Plural properties

You can bind plural properties with the following pattern:
```
.bindCollection(PluralView::getViewCollection)
.to(PluralSource::getSourceCollection)
.map(element -> convertListElements(element))
```

This time the specified conversion function is applied to the list elements.

## Omit view properties

You can omit view property by using `.omit(View::getViewProperty)`

# Global and field specific conversion

You can specify type conversion on a field basis using a binding. The declaration
```
.bind(View::getViewProperty)
.to(Source::getSourceProperty)
.map(conversionFunction)
```
only converts the specified property.

To re-use conversion functions, you can use global type conversions:
```
.typeConversion(conversion -> conversion.fromSource(List.class)
                                        .toView(int.class)
                                        .applying(List::size)
                                        .unidirectional())
```
The above example registers a global conversion function to convert lists to list sizes whenever a view property of type `int` is bound to a source property of type `java.util.List`.

# Check the mapping

An instance of `BeanView` can be checked easily by generating a text representation of the mapping. You can simply print out the result of
```
BeanView.toString();
```
The output of the demo test case will looke like this:
```
Bean view com.github.schuettec.jbeanviews.demo.CustomerView
       of com.github.schuettec.jbeanviews.demo.Customer
       with mappings:
       - Singular view property 'name' represents singular source property 'person.name' mapped by reference (no conversion),
       - Singular view property 'forename' represents singular source property 'person.forename' mapped by reference (no conversion),
       - Plural view property 'addresses' represents plural source property 'addresses' mapped by auto conversion using implicit mappings,
       - Singular view property 'metaData.number' represents singular source property 'number' mapped by reference (no conversion),
       - Singular view property 'metaData.deleted' represents singular source property 'deleted' mapped by reference (no conversion)
```

# Meta model

JBeanViews provides a way to analyze the mapping model. This might be useful to integrate JBeanViews into a reflective framework.
You can access the view model using
```
com.github.schuettec.jbeanviews.api.BeanView.getViewModel()
```
For further information please have a look at the Java Doc of the view model.

# How to contribute
Please refer to the project's [contribution guide](CONTRIBUTE.md)