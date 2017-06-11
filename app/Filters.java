

import javax.inject.Inject;

import play.filters.gzip.GzipFilter;
import play.http.HttpFilters;
import play.mvc.EssentialFilter;

public class Filters implements HttpFilters {

	private EssentialFilter[] filters;

    @Inject
    public Filters(GzipFilter gzipFilter) {
        this.filters = new EssentialFilter[] { gzipFilter.asJava() };
    }

    public EssentialFilter[] filters() {
        return this.filters;
    }
}