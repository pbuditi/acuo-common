package com.acuo.common.specification;

import com.acuo.common.util.ArgChecker;

/**
 * AND specification, used to create a new specification that is the AND of two other
 * specifications.
 */
public class AndSpecification<T> extends AbstractSpecification<T>
{

	private final Specification<T>	spec1;
	private final Specification<T>	spec2;

	/**
	 * Create a new AND specification based on two other spec.
	 *
	 * @param spec1 Specification one.
	 * @param spec2 Specification two.
	 */
	public AndSpecification(final Specification<T> spec1, final Specification<T> spec2)
	{
		ArgChecker.notNull(spec1, "spec1");
		ArgChecker.notNull(spec2, "spec2");
		this.spec1 = spec1;
		this.spec2 = spec2;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isSatisfiedBy(final T t)
	{
		return spec1.isSatisfiedBy(t) && spec2.isSatisfiedBy(t);
	}
}
