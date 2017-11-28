package org.shl.lambdavalidation.java7;

/**
 * SelfValidate Java 7 version: basically, just here for the package.
 * 
 * Because java 7 doesn't have constructs like traits or default interface methods, 
 * must be implemented for each object (choosing which validator "flavour" to use)
 */
public interface SelfValidating extends org.shl.lambdavalidation.SelfValidating{
}
