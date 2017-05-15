package tests.unitTests.criteria.fakes.stubs;

import org.hibernate.*;
import org.hibernate.engine.spi.RowSelection;
import org.hibernate.query.ParameterMetadata;
import org.hibernate.query.QueryParameter;
import org.hibernate.query.QueryProducer;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.type.Type;

import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.Parameter;
import javax.persistence.TemporalType;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Stream;

/**
 * Created by maxim_000 on 12.05.2017.
 */
public class QueryStub<TEntity> implements org.hibernate.query.Query<TEntity>{

    @Override
    public QueryProducer getProducer() {
        return null;
    }

    @Override
    public RowSelection getQueryOptions() {
        return null;
    }

    @Override
    public Optional<TEntity> uniqueResultOptional() {
        return null;
    }

    @Override
    public Stream<TEntity> stream() {
        return null;
    }

    @Override
    public org.hibernate.query.Query<TEntity> setParameter(Parameter<Instant> param, Instant value, TemporalType temporalType) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<TEntity> setParameter(Parameter<LocalDateTime> param, LocalDateTime value, TemporalType temporalType) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<TEntity> setParameter(Parameter<ZonedDateTime> param, ZonedDateTime value, TemporalType temporalType) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<TEntity> setParameter(Parameter<OffsetDateTime> param, OffsetDateTime value, TemporalType temporalType) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<TEntity> setParameter(String name, Instant value, TemporalType temporalType) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<TEntity> setParameter(String name, LocalDateTime value, TemporalType temporalType) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<TEntity> setParameter(String name, ZonedDateTime value, TemporalType temporalType) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<TEntity> setParameter(String name, OffsetDateTime value, TemporalType temporalType) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<TEntity> setParameter(int position, Instant value, TemporalType temporalType) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<TEntity> setParameter(int position, LocalDateTime value, TemporalType temporalType) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<TEntity> setParameter(int position, ZonedDateTime value, TemporalType temporalType) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<TEntity> setParameter(int position, OffsetDateTime value, TemporalType temporalType) {
        return null;
    }

    @Override
    public ScrollableResults scroll() {
        return null;
    }

    @Override
    public ScrollableResults scroll(ScrollMode scrollMode) {
        return null;
    }

    @Override
    public List<TEntity> list() {
        return new ArrayList<>();
    }

    @Override
    public TEntity uniqueResult() {
        return null;
    }

    @Override
    public FlushMode getHibernateFlushMode() {
        return null;
    }

    @Override
    public CacheMode getCacheMode() {
        return null;
    }

    @Override
    public String getCacheRegion() {
        return null;
    }

    @Override
    public Integer getFetchSize() {
        return null;
    }

    @Override
    public LockOptions getLockOptions() {
        return null;
    }

    @Override
    public String getComment() {
        return null;
    }

    @Override
    public String getQueryString() {
        return null;
    }

    @Override
    public ParameterMetadata getParameterMetadata() {
        return null;
    }

    @Override
    public String[] getNamedParameters() {
        return new String[0];
    }

    @Override
    public int executeUpdate() {
        return 0;
    }

    @Override
    public org.hibernate.query.Query<TEntity> setMaxResults(int maxResult) {
        return this;
    }

    @Override
    public int getMaxResults() {
        return 0;
    }

    @Override
    public org.hibernate.query.Query<TEntity> setFirstResult(int startPosition) {
        return this;
    }

    @Override
    public int getFirstResult() {
        return 0;
    }

    @Override
    public org.hibernate.query.Query<TEntity> setHint(String hintName, Object value) {
        return null;
    }

    @Override
    public Map<String, Object> getHints() {
        return null;
    }

    @Override
    public <T> org.hibernate.query.Query<TEntity> setParameter(Parameter<T> param, T value) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<TEntity> setParameter(Parameter<Calendar> param, Calendar value, TemporalType temporalType) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<TEntity> setParameter(Parameter<Date> param, Date value, TemporalType temporalType) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<TEntity> setParameter(String name, Object value) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<TEntity> setParameter(String name, Object val, Type type) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<TEntity> setParameter(String name, Calendar value, TemporalType temporalType) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<TEntity> setParameter(String name, Date value, TemporalType temporalType) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<TEntity> setParameter(int position, Object value) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<TEntity> setParameter(int position, Calendar value, TemporalType temporalType) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<TEntity> setParameter(int position, Date value, TemporalType temporalType) {
        return null;
    }

    @Override
    public Set<Parameter<?>> getParameters() {
        return null;
    }

    @Override
    public Parameter<?> getParameter(String name) {
        return null;
    }

    @Override
    public <T> Parameter<T> getParameter(String name, Class<T> type) {
        return null;
    }

    @Override
    public Parameter<?> getParameter(int position) {
        return null;
    }

    @Override
    public <T> Parameter<T> getParameter(int position, Class<T> type) {
        return null;
    }

    @Override
    public boolean isBound(Parameter<?> param) {
        return false;
    }

    @Override
    public <T> T getParameterValue(Parameter<T> param) {
        return null;
    }

    @Override
    public Object getParameterValue(String name) {
        return null;
    }

    @Override
    public Object getParameterValue(int position) {
        return null;
    }

    @Override
    public <T> org.hibernate.query.Query<TEntity> setParameter(QueryParameter<T> parameter, T val) {
        return null;
    }

    @Override
    public <P> org.hibernate.query.Query<TEntity> setParameter(int position, P val, TemporalType temporalType) {
        return null;
    }

    @Override
    public <P> org.hibernate.query.Query<TEntity> setParameter(QueryParameter<P> parameter, P val, Type type) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<TEntity> setParameter(int position, Object val, Type type) {
        return null;
    }

    @Override
    public <P> org.hibernate.query.Query<TEntity> setParameter(QueryParameter<P> parameter, P val, TemporalType temporalType) {
        return null;
    }

    @Override
    public <P> org.hibernate.query.Query<TEntity> setParameter(String name, P val, TemporalType temporalType) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<TEntity> setFlushMode(FlushModeType flushMode) {
        return null;
    }

    @Override
    public FlushModeType getFlushMode() {
        return null;
    }

    @Override
    public org.hibernate.query.Query<TEntity> setLockMode(LockModeType lockMode) {
        return null;
    }

    @Override
    public LockModeType getLockMode() {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> cls) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<TEntity> setReadOnly(boolean readOnly) {
        return null;
    }

    @Override
    public Type[] getReturnTypes() {
        return new Type[0];
    }

    @Override
    public org.hibernate.query.Query<TEntity> setHibernateFlushMode(FlushMode flushMode) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<TEntity> setCacheMode(CacheMode cacheMode) {
        return null;
    }

    @Override
    public boolean isCacheable() {
        return false;
    }

    @Override
    public org.hibernate.query.Query<TEntity> setCacheable(boolean cacheable) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<TEntity> setCacheRegion(String cacheRegion) {
        return null;
    }

    @Override
    public Integer getTimeout() {
        return null;
    }

    @Override
    public org.hibernate.query.Query<TEntity> setTimeout(int timeout) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<TEntity> setFetchSize(int fetchSize) {
        return null;
    }

    @Override
    public boolean isReadOnly() {
        return false;
    }

    @Override
    public org.hibernate.query.Query<TEntity> setLockOptions(LockOptions lockOptions) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<TEntity> setLockMode(String alias, LockMode lockMode) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<TEntity> setComment(String comment) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<TEntity> addQueryHint(String hint) {
        return null;
    }

    @Override
    public Iterator<TEntity> iterate() {
        return null;
    }

    @Override
    public <P> org.hibernate.query.Query<TEntity> setParameterList(QueryParameter<P> parameter, Collection<P> values) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<TEntity> setParameterList(String name, Collection values) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<TEntity> setParameterList(String name, Collection values, Type type) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<TEntity> setParameterList(String name, Object[] values, Type type) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<TEntity> setParameterList(String name, Object[] values) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<TEntity> setProperties(Object bean) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<TEntity> setProperties(Map bean) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<TEntity> setEntity(int position, Object val) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<TEntity> setEntity(String name, Object val) {
        return null;
    }

    @Override
    public Type determineProperBooleanType(int position, Object value, Type defaultType) {
        return null;
    }

    @Override
    public Type determineProperBooleanType(String name, Object value, Type defaultType) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<TEntity> setResultTransformer(ResultTransformer transformer) {
        return null;
    }

    @Override
    public String[] getReturnAliases() {
        return new String[0];
    }
}
