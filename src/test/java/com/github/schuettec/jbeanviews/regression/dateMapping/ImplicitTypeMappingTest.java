package com.github.schuettec.jbeanviews.regression.dateMapping;

import static org.junit.Assert.assertSame;

import java.util.Date;

import org.junit.Test;

import com.github.schuettec.jbeanviews.api.BeanView;
import com.github.schuettec.jbeanviews.api.BeanViews;

public class ImplicitTypeMappingTest {

	@Test
	public void shouldMapDateImplicitlyAndNotDateAttributes() {
		BeanView<Source, View> beanView = BeanViews.of(Source.class).toView(View.class).get();

		Date date = new Date();
		Source source = new Source(date);
		View view = beanView.toView(source);
		assertSame(date, view.getDate());
	}

}
