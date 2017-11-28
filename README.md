# Functional Extension to the Java Validation API (JSR303)

This small extension allows the simple declaration of validation as functions that 
take allow validation to be done in the scope of the declaring class.

Where possible, pure functional notation is preffered, but some languages 
are better than others for pure functional constructs, so YMMV.

## Motivation

Validation is important, but often validation requires access to multiple 
members of a class, for example date ranges for an object representing
search parameters.  While there is a fully featured mechanism in JSR303 
it does require the definition of a constraint annotation and validator 
class for each validation rule.  

This leads to three clear problems:

* Sprawling codebase: each constraint requires a constraint annotation 
and a validator class for each validation rule.
* SPOT violation: The violation logic for your class is embodied elsewhere 
and often not immediately apparent, as opposed to the standard constraints like 
`@NotNull` which is self-explanatory.  Objects should express their own 
constraints.
* Low code reuse or brittle generalisation: for most use cases the validator class 
needs to either be tied to the concrete type requiring validation so cannot 
be reused elsewhere, require brittle reflection to look up members, or require
lifting access to an interface resulting in more code sprawl and possible
exposure of internal implementation details.

As languages start adopting functional features, using them as validators not only 
makes sense, it leads to smaller, more concise code.

## Notes

If you're adding this to a serialised DTO or a Persistence Object you'll need to 
advise your serialiser(s)/Persistence layer that the validation functions 
are `Transient` or `Ignored` or whatever the frameworks use.

## Building

Gradle wrapper is included for this project, with default tasks (in this case `build`). Just invoke the gradle wrapper `gradlew` in the project directory.

### Invocation

* Windows: `C:\repos\lambdavalidation\> gradlew`
* *nix: `~/repos/lambdavalidation/ $ ./gradlew`

:warning:

### Outputs

#### Artifacts
`./build/libs` will contain the core jar and the jars for each "flavour".

```
./build/libs/lambdavalidation.groovy-0.1.0.jar
./build/libs/lambdavalidation.java7-0.1.0.jar
./build/libs/lambdavalidation.java8-0.1.0.jar
./build/libs/lambdavalidation.core-0.1.0.jar
./build/libs/lambdavalidation.scala-0.1.0.jar
```
put core and one or more flavours on your classpath to use this.

#### Reports

Aggregate test and coverage reports are available in `./build/reports`

Findbugs is enabled for the java projects, codenarc for groovy, and scalastyle for scala.  You can find sepcific reports at `./${subproject}/build/reports/(findbugs|codenarc|scalastyle)`

### Publishing

No publish tasks have been defined.  Depending on your requirements, you can add one to publish to an in house dependency manager, or add one to publish it to a local maven/ivy repository.

There are no plans at this stage to publish it to mavencentral or similar.

## Core Components

Core components are written in Java7, to allow extension in other JVM languages to 
be simple.  

### Validation Constraint Function

`ConstraintFunction` is an interface that is used in conjunction with a `@ValidationFunction` annotation 
on a field who'se value is ideally a lambda function that returns true if the object it contains 
is valid. 

The `@ValidationFunction` annotation works with JSR303 validation groups.

See below for examples.

### Validation Service

`ValidationService` is a singleton class that contains two simple validators, one that returns the 
`ConstraintViolation`s as a set, and one that wraps them in a `ConstraintViolationException` and throws it.

This service is provider agnostic.

The provided implementation of `SelfValidating` use this validator, but you're free to make your own. 

### Self Validating Supertypes

`SelfValidating` is an interface that requires implementing classes to validate themselves (by default
with the provided `ValidationService` singleton).

`ThrowingSelfValidating` and `ReturningSelfValidating` are interfaces _NOT_ in the core components, but rather are the convention for extending the `SelfValidating` interface for cases where the behaviour is not explicitly required to be added (such as Scala traits or Java8 default methods).

## "Flavours"

Each "Flavour" is a language specific implementation of the core functionality, and tries to be a language-specific implementation of the languages' functional features.  Each flavour has it's own package to prevent namespace pollution (important if you use multiple languages for validatable objects), and 
so to make it clear what flavour you're using by import statements.

Generally a flavour provides an implementation of `ConstraintFunction` (named the same but in the flavours package) that allows constraints to be expressed as the flavours lambda syntax.


### Java 8/9 Flavour

An instance field of type `ConstraintFunction` can be assigned any function literal that can be cast to `BooleanSupplier`. While this could be used with a static field, the scope of the instance members used for validation would not be available, so it's not advisable.  It's advisable to make it `final` since constraints are immutable.

Two interfaces with default methods are provided: `ThrowingSelfValidating` and `ReturningSelfValidating`, which add self validating behaviour that throws exceptions or returns sets in that order.  Both these interfaces are extensions of `SelfValidating` interface in the core.

```java
public class Example implements ThrowingSelfValidator {
  protected Integer start, end;
  public Example(final Integer start, final Integer end) {
    this.start = start;
    this.end = end;
  }
   
  @ValidationFunction(message = "start must be less than end")
  private final ConstraintFunction startLessThanEnd = () -> start < end;
}
```

### Scala Flavour

The companion object `ConstraintFunction` has an `apply(=> Boolean)` function that can be used to create a constraint function that can then be assigned to a `val` with type inference, overall requiring very little boilerplate.
Alternatively a `val` of type `ConstraintFunction` can be assigned any function literal that is of type `() => Boolean`, but the companion object style requires less boilerplate and is generally prefferable.   
This must be a `val` not a `def` due to the way JSR303 works.  Should also work with a `var`, but that would break immutability (constraints should be immutable), and has not been tested.

Two traits with implemented methods are provided: `ThrowingSelfValidating` and `ReturningSelfValidating`, which add self validating behaviour that throws exceptions or returns sets in that order.  Both these interfaces extend the `SelfValidating` interface in the core.

```scala
case class Example(start: Int, end: Int) extends ThrowingSelfValidator {
  @ValidateWith(message = "start must be less than end")
  val startLessThanEnd = ConstraintFunction { start < end }
}
```

### Groovy Flavour

An instance field of type `ConstraintFunction` can be used with `constraingFunction(Closure)` provided by the `ConstraintFunctionBuilder` trait to create a validation function from a groovy closure. Due to groovy's closure's being implemented as classes, they could cause issues with multiple inheritance for implementing parties.  While there are facilities to coerce closure literals to a specific class using the `MetaClass` class and the `asType(Class)`, I could not get the necessary meta-object protocol magic to work.

As with Java8, while this could be used with a static field, the scope of the instance members used for validation would not be available, so it's not advisable.  Again, as with Java8 It's advisable to make it `final` since constraints are immutable.

Sepcific to Groovy `def` is treated as an `Object`, this means you need to declare constraint functions as type `ConstraintFunction`, or there will be a `ValidationException` because the JSR303 spec does not 
require the instance's runtime ttype type to be inspected, but uses the field's declared type.

Two traits with default methods are provided: `ThrowingSelfValidating` and `ReturningSelfValidating`, which add self validating behaviour that throws exceptions or returns sets in that order.  Both these traits are extensions of `SelfValidating` interface in the core.


```groovy
public class Example implements ThrowingSelfValidator, ConstraintFunctionBuilder {
  protected Integer start, end;
  public Example(final Integer start, final Integer end) {
    this.start = start;
    this.end = end;
  }
  @ValidationFunction(message = "start must be less than end")
  private final ConstraintFunction startLessThanEnd = constraintFunction { start < end }
}
```

#### Java7

Basically just the core components, with some extensions that make no change to the core components.  This is to prevent namespace pollution.  While this could be used with a static field, the scope of the instance members used for validation would not be available, so it's not advisable.

Because Java7 does not have a lambda function notation but does have anonymous class closures, you need to implement the `ConstraintFunction` interface manually for each constraint.  Additionally the lack of a mechanism to allow default behaviour of methods means that the `SelfValidate` interface needs to be manually implemented.


```java
public static class CaseClass extends BaseTestCase1 implements SelfValidate {
  protected Integer start, end;
  public BaseTestCase1(final Integer start, final Integer end) {
      this.start = start;
      this.end = end;
  }
  @Override
  public boolean validate() throws ConstraintViolationException {
    return ValidationHandler.instance().thrower.validate(this);
  }
  @Override
  public boolean validate(Class<?>... groups) throws ConstraintViolationException, ValidationException {
    return ValidationHandler.instance().thrower.validate(this);
  }
  @ValidateWith(message = "start must be less than end")
  private final ConstraintFunction startLessThanEnd = new ConstraintFunction() {
    @Override
    public boolean validate() {
      return start < end;
    }
  };
}
```

### Make your own flavour.

Like Kotlin or Clojure?  Just extend the `ConstraintFunction` core in 
whatever idiomatic way makes sense to you and the language.  

Basically there are just three things to do:
* Create a `ThrowingSelfValidating` interface/trait/mixin/whatever, which implements the 'throwing' validation behaviour. 
* Create a `ReturningSelfValidating` interface/trait/mixin/whatever, which implements the 'throwing' validation behaviour. 
* Create a `ConstraintFunction` that extends the `ConstraintFunction` from core which can be assigned any 
0-arg lamdba function (or similar) that returns boolean.  Delegate the `validate()` method to calling this closure.

Feel free to send me a pull request if you do this.

*--
