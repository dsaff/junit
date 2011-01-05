// Copyright 2011 Google Inc. All Rights Reserved.

package org.junit.runners.model;

import org.junit.internal.AssumptionViolatedException;
import org.junit.internal.runners.model.EachTestNotifier;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runner.notification.StoppedByUserException;

/**
 * Executes statements, sending notifications to a notifier. Useful for custom
 * runners that extend {@link org.junit.runners.ParentRunner ParentRunner}.
 */
public class StatementExecutor {
	private final RunNotifier fNotifier;

	/**
	 * Creates a statement executor.
	 *
	 * @param notifier the notifier to use to notify JUnit of the progress of running tests
	 */
	public StatementExecutor(RunNotifier notifier) {
		fNotifier= notifier;
	}

	/**
	 * Executes a statement that represents an atomic test.
	 *
	 * @param statement the statement to evaluate
	 * @param description the description to pass to the {@link RunNotifier notifier}. If the
	 *        description is not a test case, the statement will not be evaluated and
	 *        {@link RunNotifier#fireTestIgnored(Description)} will be called
	 * @throws StoppedByUserException thrown if a user has requested that the test run stop
	 */
	public void executeTest(Statement statement, Description description) {
		if (!description.isTest()) {
			fNotifier.fireTestIgnored(description);
			return;
		}
		EachTestNotifier eachNotifier= new EachTestNotifier(fNotifier, description);
		eachNotifier.fireTestStarted();
		try {
			statement.evaluate();
		} catch (AssumptionViolatedException e) {
			eachNotifier.addFailedAssumption(e);
		} catch (Throwable e) {
			eachNotifier.addFailure(e);
		} finally {
			eachNotifier.fireTestFinished();
		}
	}

	/**
	 * Executes a statement that represents a test suite.
	 *
	 * @param statement the statement to evaluate
	 * @param description the description to pass to the {@link RunNotifier notifier}. If the
	 *        description is not a suite, the statement will not be evaluated and
	 *        {@link RunNotifier#fireTestIgnored(Description)} will be called
	 * @throws StoppedByUserException thrown if a user has requested that the test run stop
	 */
	public void executeSuite(Statement statement, Description description) {
		if (!description.isSuite()) {
			fNotifier.fireTestIgnored(description);
			return;
		}
		EachTestNotifier eachNotifier= new EachTestNotifier(fNotifier, description);
		
		try {
			statement.evaluate();
		} catch (AssumptionViolatedException e) {
			eachNotifier.fireTestIgnored();
		} catch (StoppedByUserException e) {
			throw e;
		} catch (Throwable e) {
			eachNotifier.addFailure(e);
		}
	}
}
