package com.acuo.common.specification;

import com.acuo.common.util.ArgChecker;

/**
 * NOT decorator, used to create a new specification that is the inverse (NOT) of the given
 * specification.
 */
public class NotSpecification<T> extends AbstractSpecification<T>
{

	private final Specification<T> spec1;

	/**
	 * Create a new NOT specification based on another spec.
	 *
	 * @param spec1 Specification instance to not.
	 */
	public NotSpecification(final Specification<T> spec1)
	{
		ArgChecker.notNull(spec1, "spec1");
		this.spec1 = spec1;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isSatisfiedBy(final T t)
	{
		return !spec1.isSatisfiedBy(t);
	}
}