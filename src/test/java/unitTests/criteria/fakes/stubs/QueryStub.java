package unitTests.criteria.fakes.stubs;

import org.hibernate.*;
import org.hibernate.engine.spi.RowSelection;
import org.hibernate.query.ParameterMetadata;
import org.hibernate.query.QueryParameter;
import org.hibernate.query.QueryProducer;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.type.Type;
import ru.alcereo.entities.CommandsEntity;

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
public class QueryStub implements org.hibernate.query.Query<CommandsEntity>{

    @Override
    public QueryProducer getProducer() {
        return null;
    }

    @Override
    public RowSelection getQueryOptions() {
        return null;
    }

    @Override
    public Optional<CommandsEntity> uniqueResultOptional() {
        return null;
    }

    @Override
    public Stream<CommandsEntity> stream() {
        return null;
    }

    @Override
    public org.hibernate.query.Query<CommandsEntity> setParameter(Parameter<Instant> param, Instant value, TemporalType temporalType) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<CommandsEntity> setParameter(Parameter<LocalDateTime> param, LocalDateTime value, TemporalType temporalType) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<CommandsEntity> setParameter(Parameter<ZonedDateTime> param, ZonedDateTime value, TemporalType temporalType) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<CommandsEntity> setParameter(Parameter<OffsetDateTime> param, OffsetDateTime value, TemporalType temporalType) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<CommandsEntity> setParameter(String name, Instant value, TemporalType temporalType) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<CommandsEntity> setParameter(String name, LocalDateTime value, TemporalType temporalType) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<CommandsEntity> setParameter(String name, ZonedDateTime value, TemporalType temporalType) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<CommandsEntity> setParameter(String name, OffsetDateTime value, TemporalType temporalType) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<CommandsEntity> setParameter(int position, Instant value, TemporalType temporalType) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<CommandsEntity> setParameter(int position, LocalDateTime value, TemporalType temporalType) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<CommandsEntity> setParameter(int position, ZonedDateTime value, TemporalType temporalType) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<CommandsEntity> setParameter(int position, OffsetDateTime value, TemporalType temporalType) {
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
    public List<CommandsEntity> list() {
        return new ArrayList<>();
    }

    @Override
    public CommandsEntity uniqueResult() {
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
    public org.hibernate.query.Query<CommandsEntity> setMaxResults(int maxResult) {
        return this;
    }

    @Override
    public int getMaxResults() {
        return 0;
    }

    @Override
    public org.hibernate.query.Query<CommandsEntity> setFirstResult(int startPosition) {
        return this;
    }

    @Override
    public int getFirstResult() {
        return 0;
    }

    @Override
    public org.hibernate.query.Query<CommandsEntity> setHint(String hintName, Object value) {
        return null;
    }

    @Override
    public Map<String, Object> getHints() {
        return null;
    }

    @Override
    public <T> org.hibernate.query.Query<CommandsEntity> setParameter(Parameter<T> param, T value) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<CommandsEntity> setParameter(Parameter<Calendar> param, Calendar value, TemporalType temporalType) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<CommandsEntity> setParameter(Parameter<Date> param, Date value, TemporalType temporalType) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<CommandsEntity> setParameter(String name, Object value) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<CommandsEntity> setParameter(String name, Object val, Type type) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<CommandsEntity> setParameter(String name, Calendar value, TemporalType temporalType) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<CommandsEntity> setParameter(String name, Date value, TemporalType temporalType) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<CommandsEntity> setParameter(int position, Object value) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<CommandsEntity> setParameter(int position, Calendar value, TemporalType temporalType) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<CommandsEntity> setParameter(int position, Date value, TemporalType temporalType) {
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
    public <T> org.hibernate.query.Query<CommandsEntity> setParameter(QueryParameter<T> parameter, T val) {
        return null;
    }

    @Override
    public <P> org.hibernate.query.Query<CommandsEntity> setParameter(int position, P val, TemporalType temporalType) {
        return null;
    }

    @Override
    public <P> org.hibernate.query.Query<CommandsEntity> setParameter(QueryParameter<P> parameter, P val, Type type) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<CommandsEntity> setParameter(int position, Object val, Type type) {
        return null;
    }

    @Override
    public <P> org.hibernate.query.Query<CommandsEntity> setParameter(QueryParameter<P> parameter, P val, TemporalType temporalType) {
        return null;
    }

    @Override
    public <P> org.hibernate.query.Query<CommandsEntity> setParameter(String name, P val, TemporalType temporalType) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<CommandsEntity> setFlushMode(FlushModeType flushMode) {
        return null;
    }

    @Override
    public FlushModeType getFlushMode() {
        return null;
    }

    @Override
    public org.hibernate.query.Query<CommandsEntity> setLockMode(LockModeType lockMode) {
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
    public org.hibernate.query.Query<CommandsEntity> setReadOnly(boolean readOnly) {
        return null;
    }

    @Override
    public Type[] getReturnTypes() {
        return new Type[0];
    }

    @Override
    public org.hibernate.query.Query<CommandsEntity> setHibernateFlushMode(FlushMode flushMode) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<CommandsEntity> setCacheMode(CacheMode cacheMode) {
        return null;
    }

    @Override
    public boolean isCacheable() {
        return false;
    }

    @Override
    public org.hibernate.query.Query<CommandsEntity> setCacheable(boolean cacheable) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<CommandsEntity> setCacheRegion(String cacheRegion) {
        return null;
    }

    @Override
    public Integer getTimeout() {
        return null;
    }

    @Override
    public org.hibernate.query.Query<CommandsEntity> setTimeout(int timeout) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<CommandsEntity> setFetchSize(int fetchSize) {
        return null;
    }

    @Override
    public boolean isReadOnly() {
        return false;
    }

    @Override
    public org.hibernate.query.Query<CommandsEntity> setLockOptions(LockOptions lockOptions) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<CommandsEntity> setLockMode(String alias, LockMode lockMode) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<CommandsEntity> setComment(String comment) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<CommandsEntity> addQueryHint(String hint) {
        return null;
    }

    @Override
    public Iterator<CommandsEntity> iterate() {
        return null;
    }

    @Override
    public <P> org.hibernate.query.Query<CommandsEntity> setParameterList(QueryParameter<P> parameter, Collection<P> values) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<CommandsEntity> setParameterList(String name, Collection values) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<CommandsEntity> setParameterList(String name, Collection values, Type type) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<CommandsEntity> setParameterList(String name, Object[] values, Type type) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<CommandsEntity> setParameterList(String name, Object[] values) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<CommandsEntity> setProperties(Object bean) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<CommandsEntity> setProperties(Map bean) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<CommandsEntity> setEntity(int position, Object val) {
        return null;
    }

    @Override
    public org.hibernate.query.Query<CommandsEntity> setEntity(String name, Object val) {
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
    public org.hibernate.query.Query<CommandsEntity> setResultTransformer(ResultTransformer transformer) {
        return null;
    }

    @Override
    public String[] getReturnAliases() {
        return new String[0];
    }
}
