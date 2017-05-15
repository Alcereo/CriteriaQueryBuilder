package tests;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.alcereo.criteria.Path;
import ru.alcereo.criteria.PathPointData;
import ru.alcereo.criteria.QueryBuilder;
import tests.entities.CommandsEntity;
import tests.entities.ParametersEntity;
import tests.entities.ProcessorsEntity;
import tests.entities.ProcessorsVersionsEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by maxim_000 on 15.05.2017.
 */
@Configuration
public abstract class TestConfig {
    @Bean
    public QueryBuilder queryBuilder(SessionFactory sessionFactory,
                                     @Qualifier("pathsMap") Map<Class, List<Path>> pathsMap){
        return new QueryBuilder(sessionFactory, pathsMap);
    }

    @Bean
    public abstract SessionFactory sessionFactory();

    @Bean
    public Map<Class, List<Path>> pathsMap(){
        Map<Class, List<Path>> pathMap = new HashMap<>();

        Path path1 = new Path();
        ArrayList<PathPointData> pathPoints = new ArrayList<>();
        path1.setPathPointDataList(pathPoints);
        path1.setPathName("P1");

        pathPoints.add(
                new PathPointData(
                        "processorsUsed",
                        ProcessorsEntity.class
                )
        );

        pathPoints.add(
                new PathPointData(
                        "commands",
                        CommandsEntity.class
                )
        );

        pathPoints.add(
                new PathPointData(
                        "parameters",
                        ParametersEntity.class
                )
        );

        path1.updatePathPoints();

        ArrayList<Path> paths = new ArrayList<>();
        paths.add(path1);

        pathMap.put(ProcessorsVersionsEntity.class, paths);

        return pathMap;
    }
}
